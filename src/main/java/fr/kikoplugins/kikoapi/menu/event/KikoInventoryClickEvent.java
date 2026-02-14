package fr.kikoplugins.kikoapi.menu.event;

import com.google.common.base.Preconditions;
import fr.kikoplugins.kikoapi.menu.MenuContext;
import fr.kikoplugins.kikoapi.utils.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

/**
 * A specialized inventory click event for Niveria menu interactions.
 * <p>
 * This class extends InventoryClickEvent to provide additional functionality
 * specific to menu components, including context access and convenient
 * item modification methods.
 */
@NullMarked
public class KikoInventoryClickEvent extends InventoryClickEvent {
    private final MenuContext context;

    /**
     * Creates a new KikoInventoryClickEvent from an existing InventoryClickEvent.
     *
     * @param event   the original InventoryClickEvent to wrap
     * @param context the menu context associated with this event
     * @throws NullPointerException if context is null
     */
    @SuppressWarnings("UnstableApiUsage")
    public KikoInventoryClickEvent(InventoryClickEvent event, MenuContext context) {
        super(event.getView(), event.getSlotType(), event.getSlot(), event.getClick(), event.getAction());

        Preconditions.checkNotNull(context, "context cannot be null");

        this.context = context;
    }

    /**
     * Changes the item in the clicked slot to the specified ItemStack.
     *
     * @param newItem the new ItemStack to set, or null to clear the slot
     */
    public void changeItem(@Nullable ItemStack newItem) {
        this.setCurrentItem(newItem);
    }

    /**
     * Modifies the item in the clicked slot using the provided ItemBuilder modifier.
     * <p>
     * If the current item is null, this method does nothing. Otherwise, it creates
     * an ItemBuilder from the current item, applies the modifier, and sets the
     * resulting item back to the slot.
     *
     * @param modifier a function that modifies the ItemBuilder
     * @throws NullPointerException if modifier is null
     */
    public void changeItem(Consumer<ItemBuilder> modifier) {
        Preconditions.checkNotNull(modifier, "modifier cannot be null");

        ItemStack item = this.getCurrentItem();
        if (item == null)
            return;

        ItemBuilder builder = ItemBuilder.of(item);
        modifier.accept(builder);
        this.setCurrentItem(builder.build());
    }

    /**
     * Returns the player who clicked in the inventory.
     *
     * @return the player who performed the click action
     */
    public Player getPlayer() {
        return (Player) getWhoClicked();
    }

    /**
     * Returns the menu context associated with this event.
     *
     * @return the menu context
     */
    public MenuContext getContext() {
        return this.context;
    }
}