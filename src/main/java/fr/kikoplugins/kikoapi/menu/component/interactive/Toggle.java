package fr.kikoplugins.kikoapi.menu.component.interactive;

import com.google.common.base.Preconditions;
import fr.kikoplugins.kikoapi.menu.MenuContext;
import fr.kikoplugins.kikoapi.menu.component.MenuComponent;
import fr.kikoplugins.kikoapi.menu.event.KikoInventoryClickEvent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An interactive toggle component that switches between two states (on/off).
 * <p>
 * The Toggle component displays different items based on its current state and
 * automatically switches between states when clicked. It supports customizable
 * on/off items, click sounds, and can span multiple slots with configurable
 * width and height.
 */
@NullMarked
public class Toggle extends MenuComponent {
    private Function<MenuContext, ItemStack> onItem, offItem;
    @Nullable
    private Consumer<ToggleEvent> onToggle;
    @Nullable
    private Sound sound;
    private boolean currentState;

    /**
     * Constructs a new Toggle with the specified configuration.
     *
     * @param builder the builder containing the toggle configuration
     */
    private Toggle(Builder builder) {
        super(builder);
        this.onItem = builder.onItem;
        this.offItem = builder.offItem;

        this.onToggle = builder.onToggle;

        this.sound = builder.sound;

        this.currentState = builder.currentState;
    }

    /**
     * Creates a new Toggle builder instance.
     *
     * @return a new Toggle.Builder for constructing toggles
     */
    @Contract(value = "-> new", pure = true)
    public static Builder create() {
        return new Builder();
    }

    /**
     * Handles click events on this toggle.
     * <p>
     * When clicked, the toggle switches its state, plays a sound (if configured),
     * and triggers a re-render to update the displayed item.
     *
     * @param event   the inventory click event
     * @param context the menu context
     */
    @Override
    public void onClick(KikoInventoryClickEvent event, MenuContext context) {
        if (!this.isInteractable())
            return;

        if (this.sound != null)
            context.getPlayer().playSound(this.sound, Sound.Emitter.self());

        this.currentState = !this.currentState;
        this.render(context);

        if (this.onToggle != null) {
            ToggleEvent toggleEvent = new ToggleEvent(event, this.currentState);
            this.onToggle.accept(toggleEvent);
        }
    }

    /**
     * Returns the items to be displayed by this toggle.
     * <p>
     * The toggle fills all slots within its widthxheight area with the
     * current state item (on or off). Returns an empty map if not visible.
     *
     * @param context the menu context
     * @return a map from slot indices to ItemStacks
     */
    @Override
    public Int2ObjectMap<ItemStack> getItems(MenuContext context) {
        Int2ObjectMap<ItemStack> items = new Int2ObjectOpenHashMap<>();
        if (!this.isVisible())
            return items;

        ItemStack baseItem = this.getCurrentItem(context);
        int baseSlot = this.getSlot();
        int rowLength = 9;

        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                int slot = baseSlot + col + (row * rowLength);
                items.put(slot, baseItem);
            }
        }

        return items;
    }

    /**
     * Gets the ItemStack to display based on the current toggle state.
     *
     * @param context the menu context
     * @return the appropriate ItemStack for the current state
     */
    private ItemStack getCurrentItem(MenuContext context) {
        return currentState ? this.onItem.apply(context) : this.offItem.apply(context);
    }

    /**
     * Sets the ItemStack to display when the toggle is in the "on" state.
     *
     * @param onItem the ItemStack for the "on" state
     * @return this selector for method chaining
     * @throws NullPointerException if onItem is null
     */
    @Contract(value = "_ -> this", mutates = "this")
    public Toggle onItem(ItemStack onItem) {
        Preconditions.checkNotNull(onItem, "onItem cannot be null");

        this.onItem = context -> onItem;
        return this;
    }

    /**
     * Sets the ItemStack to display when the toggle is in the "off" state.
     *
     * @param offItem the ItemStack for the "off" state
     * @return this selector for method chaining
     * @throws NullPointerException if offItem is null
     */
    @Contract(value = "_ -> this", mutates = "this")
    public Toggle offItem(ItemStack offItem) {
        Preconditions.checkNotNull(offItem, "offItem cannot be null");

        this.offItem = context -> offItem;
        return this;
    }

    /**
     * Sets a function to provide the ItemStack for the "on" state.
     *
     * @param onItem function that returns the ItemStack for the "on" state
     * @return this selector for method chaining
     * @throws NullPointerException if onItem is null
     */
    @Contract(value = "_ -> this", mutates = "this")
    public Toggle onItem(Function<MenuContext, ItemStack> onItem) {
        Preconditions.checkNotNull(onItem, "onItem cannot be null");

        this.onItem = onItem;
        return this;
    }

    /**
     * Sets a function to provide the ItemStack for the "off" state.
     *
     * @param offItem function that returns the ItemStack for the "off" state
     * @return this selector for method chaining
     * @throws NullPointerException if offItem is null
     */
    @Contract(value = "_ -> this", mutates = "this")
    public Toggle offItem(Function<MenuContext, ItemStack> offItem) {
        Preconditions.checkNotNull(offItem, "offItem cannot be null");

        this.offItem = offItem;
        return this;
    }

    /**
     * Sets the toggle state change handler.
     *
     * @param onToggle the consumer to handle toggle state changes
     * @return this selector for method chaining
     * @throws NullPointerException if onToggle is null
     */
    @Contract(value = "_ -> this", mutates = "this")
    public Toggle onToggle(Consumer<ToggleEvent> onToggle) {
        Preconditions.checkNotNull(onToggle, "onToggle cannot be null");

        this.onToggle = onToggle;
        return this;
    }

    /**
     * Sets the sound to play when the toggle is clicked.
     *
     * @param sound the sound to play, or null for no sound
     * @return this selector for method chaining
     */
    @Contract(value = "_ -> this", mutates = "this")
    public Toggle sound(@Nullable Sound sound) {
        this.sound = sound;
        return this;
    }

    /**
     * Sets the initial state of the toggle.
     *
     * @param state true for "on" state, false for "off" state
     * @return this selector for method chaining
     */
    @Contract(value = "_ -> this", mutates = "this")
    public Toggle currentState(boolean state) {
        this.currentState = state;
        return this;
    }

    public record ToggleEvent(KikoInventoryClickEvent clickEvent, boolean newState) {}

    /**
     * Builder class for constructing Toggle instances with a fluent interface.
     */
    public static class Builder extends MenuComponent.Builder<Builder> {
        private Function<MenuContext, ItemStack> onItem = context -> ItemStack.of(Material.STONE);
        private Function<MenuContext, ItemStack> offItem = context -> ItemStack.of(Material.STONE);

        @Nullable
        private Consumer<ToggleEvent> onToggle;

        @Nullable
        private Sound sound = Sound.sound(
                Key.key("minecraft", "ui.button.click"),
                Sound.Source.UI,
                1F,
                1F
        );

        private boolean currentState;

        /**
         * Sets the ItemStack to display when the toggle is in the "on" state.
         *
         * @param onItem the ItemStack for the "on" state
         * @return this builder for method chaining
         * @throws NullPointerException if onItem is null
         */
        @Contract(value = "_ -> this", mutates = "this")
        public Builder onItem(ItemStack onItem) {
            Preconditions.checkNotNull(onItem, "onItem cannot be null");

            this.onItem = context -> onItem;
            return this;
        }

        /**
         * Sets the ItemStack to display when the toggle is in the "off" state.
         *
         * @param offItem the ItemStack for the "off" state
         * @return this builder for method chaining
         * @throws NullPointerException if offItem is null
         */
        @Contract(value = "_ -> this", mutates = "this")
        public Builder offItem(ItemStack offItem) {
            Preconditions.checkNotNull(offItem, "offItem cannot be null");

            this.offItem = context -> offItem;
            return this;
        }

        /**
         * Sets a function to provide the ItemStack for the "on" state.
         *
         * @param onItem function that returns the ItemStack for the "on" state
         * @return this builder for method chaining
         * @throws NullPointerException if onItem is null
         */
        @Contract(value = "_ -> this", mutates = "this")
        public Builder onItem(Function<MenuContext, ItemStack> onItem) {
            Preconditions.checkNotNull(onItem, "onItem cannot be null");

            this.onItem = onItem;
            return this;
        }

        /**
         * Sets a function to provide the ItemStack for the "off" state.
         *
         * @param offItem function that returns the ItemStack for the "off" state
         * @return this builder for method chaining
         * @throws NullPointerException if offItem is null
         */
        @Contract(value = "_ -> this", mutates = "this")
        public Builder offItem(Function<MenuContext, ItemStack> offItem) {
            Preconditions.checkNotNull(offItem, "offItem cannot be null");

            this.offItem = offItem;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder onToggle(Consumer<ToggleEvent> onToggle) {
            Preconditions.checkNotNull(onToggle, "onToggle cannot be null");

            this.onToggle = onToggle;
            return this;
        }

        /**
         * Sets the sound to play when the toggle is clicked.
         *
         * @param sound the sound to play, or null for no sound
         * @return this builder for method chaining
         */
        @Contract(value = "_ -> this", mutates = "this")
        public Builder sound(@Nullable Sound sound) {
            this.sound = sound;
            return this;
        }

        /**
         * Sets the initial state of the toggle.
         *
         * @param state true for "on" state, false for "off" state
         * @return this builder for method chaining
         */
        @Contract(value = "_ -> this", mutates = "this")
        public Builder currentState(boolean state) {
            this.currentState = state;
            return this;
        }

        /**
         * Builds and returns the configured Toggle instance.
         *
         * @return a new Toggle with the specified configuration
         */
        public Toggle build() {
            return new Toggle(this);
        }
    }
}