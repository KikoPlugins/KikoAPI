package fr.kikoplugins.kikoapi.menu.test;

import fr.kikoplugins.kikoapi.menu.Menu;
import fr.kikoplugins.kikoapi.menu.MenuContext;
import fr.kikoplugins.kikoapi.menu.component.MenuComponent;
import fr.kikoplugins.kikoapi.menu.component.display.Icon;
import fr.kikoplugins.kikoapi.menu.component.display.ProgressBar;
import fr.kikoplugins.kikoapi.menu.component.interactive.Button;
import fr.kikoplugins.kikoapi.menu.component.interactive.DoubleDropButton;
import fr.kikoplugins.kikoapi.menu.component.interactive.Selector;
import fr.kikoplugins.kikoapi.menu.component.interactive.Toggle;
import fr.kikoplugins.kikoapi.menu.component.layout.Grid;
import fr.kikoplugins.kikoapi.utils.ColorUtils;
import fr.kikoplugins.kikoapi.utils.Direction;
import fr.kikoplugins.kikoapi.utils.ItemBuilder;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

/**
 * A comprehensive test menu demonstrating various menu component functionalities.
 * <p>
 * This menu serves as a showcase for the different types of components available
 * in the menu system, including buttons, toggles, selectors, progress bars, and more.
 * It demonstrates both static and dynamic content, animations, and various interaction types.
 */
@NullMarked
public class BasicTestMenu extends Menu {

    /**
     * Constructs a new TestMenu for the specified player.
     *
     * @param player the player who will view this menu
     */
    public BasicTestMenu(Player player) {
        super(player);
    }

    /**
     * Creates a simple button with basic click and drop functionality.
     *
     * @return a button component that responds to clicks and drops
     */
    private static Button simpleButton() {
        return Button.create()
                .item(ItemStack.of(Material.APPLE))
                .onClick(click -> {
                    click.player().sendRichMessage("<rainbow>You clicked the apple!");
                })
                .onDrop(click -> {
                    click.player().sendRichMessage("<red>Newton");
                    click.player().closeInventory();
                })
                .build();
    }

    /**
     * Creates an animated button that cycles through different colored wool blocks.
     *
     * @return a 2x2 animated button with rainbow color progression
     */
    private static Button animatedButton() {
        return Button.create()
                .size(2, 2)
                .animationFrames(context ->
                        ObjectList.of(ItemStack.of(Material.RED_WOOL),
                                ItemStack.of(Material.ORANGE_WOOL),
                                ItemStack.of(Material.YELLOW_WOOL),
                                ItemStack.of(Material.LIME_WOOL),
                                ItemStack.of(Material.BLUE_WOOL),
                                ItemStack.of(Material.PURPLE_WOOL))
                )
                .animationInterval(5)
                .onClick(click -> {
                    click.player().sendRichMessage("<rainbow>You clicked the animated button!");
                })
                .build();
    }

    /**
     * Creates a dynamic button that displays the current seconds.
     *
     * @return a button that updates every second to show current seconds
     */
    private static Button dynamicButton() {
        return Button.create()
                .dynamicItem(context -> {
                    int seconds = (int) (System.currentTimeMillis() / 1000 % 60);
                    return ItemBuilder.of(Material.OAK_SIGN).name(
                            Component.text("Seconds: " + seconds)
                    ).build();
                })
                .updateInterval(20)
                .onClick(click -> {
                    click.player().sendRichMessage("<green>This button shows the current seconds!");
                })
                .build();
    }

    /**
     * Creates a dynamic button that displays the player's current coordinates.
     *
     * @return a button that updates every tick to show player coordinates
     */
    private static Button coordinatesDynamicButton() {
        return Button.create()
                .dynamicItem(context -> {
                    Player player = context.getMenu().getPlayer();
                    double x = player.getLocation().getX();
                    double y = player.getLocation().getY();
                    double z = player.getLocation().getZ();
                    return ItemBuilder.of(Material.COMPASS).name(
                            Component.text(String.format("Coordinates: (%.1f, %.1f, %.1f)", x, y, z))
                    ).build();
                })
                .updateInterval(1)
                .onClick(click -> {
                    click.player().sendRichMessage("<green>This button shows your current coordinates!");
                })
                .build();
    }

    /**
     * Creates a simple toggle switch with on/off states.
     *
     * @return a toggle component using lime and red dye items
     */
    private static Toggle toggle() {
        return Toggle.create()
                .onItem(ItemStack.of(Material.LIME_DYE))
                .offItem(ItemStack.of(Material.RED_DYE))
                .onToggle(event -> {
                    event.clickEvent().player().sendRichMessage("<bold>" + event.newState());
                })
                .build();
    }

    /**
     * Creates a static display icon with no interaction.
     *
     * @return an icon component displaying a bedrock item
     */
    private static Icon icon() {
        return Icon.create()
                .item(ItemBuilder.of(Material.BEDROCK)
                        .name(Component.text("Just a useless item"))
                        .build())
                .build();
    }

    /**
     * Creates a GameMode selector that allows cycling through game modes.
     *
     * @return a selector component that changes the player's game mode
     */
    private static Selector<GameMode> selector() {
        return Selector.<GameMode>create()
                .addOption(ItemBuilder.of(Material.WOODEN_SWORD).name(Component.text("Survival")).build(), GameMode.SURVIVAL)
                .addOption(ItemBuilder.of(Material.COMPASS).name(Component.text("Adventure")).build(), GameMode.ADVENTURE)
                .addOption(ItemBuilder.of(Material.DIAMOND_BLOCK).name(Component.text("Creative")).build(), GameMode.CREATIVE)
                .defaultOption(context -> context.getPlayer().getGameMode())
                .onSelectionChange(event -> event.context().getPlayer().setGameMode(event.newValue()))
                .build();
    }

    /**
     * Creates a double-drop button that requires two quick drop actions to trigger.
     *
     * @return a double-drop button that responds to rapid drop actions
     */
    private static DoubleDropButton doubleDropButton() {
        return DoubleDropButton.create()
                .item(ItemBuilder.of(Material.CHEST).name(Component.text("Just a chest")).build())
                .dropItem(ItemBuilder.of(Material.ALLAY_SPAWN_EGG).name(Component.text("Are you sure ?")).build())
                .onDoubleDrop(event -> {
                    Player player = event.player();
                    player.sendRichMessage("<gold>You have double-dropped the chest button!");
                })
                .build();
    }

    /**
     * Creates a horizontal progress bar showing 75% completion.
     *
     * @return a 4x2 progress bar extending to the right
     */
    private static ProgressBar rightProgressBar() {
        return ProgressBar.create()
                .doneItem(ItemStack.of(Material.LIME_CONCRETE))
                .currentItem(ItemStack.of(Material.ORANGE_CONCRETE))
                .notDoneItem(ItemStack.of(Material.RED_CONCRETE))
                .direction(Direction.Default.RIGHT)
                .percentage(0.75)
                .size(4, 2)
                .build();
    }

    /**
     * Creates a vertical progress bar showing 100% completion.
     *
     * @return a 1x5 progress bar extending downward
     */
    private static ProgressBar downProgressBar() {
        return ProgressBar.create()
                .doneItem(ItemStack.of(Material.LIME_CONCRETE))
                .currentItem(ItemStack.of(Material.ORANGE_CONCRETE))
                .notDoneItem(ItemStack.of(Material.RED_CONCRETE))
                .direction(Direction.Default.DOWN)
                .percentage(1)
                .size(1, 5)
                .build();
    }

    /**
     * Returns the title component for this test menu.
     *
     * @return a colorized title component
     */
    @Override
    protected Component getTitle() {
        return Component.text("Test Menu Hehe :3", ColorUtils.getPrimaryColor());
    }

    /**
     * Creates and returns the root component for this test menu.
     * <p>
     * The menu layout includes various component demonstrations:
     * - Simple button with click and drop handlers (slot 0)
     * - Animated button with color-changing frames (slot 2, 2x2 size)
     * - Dynamic button showing current seconds (slot 8)
     * - Coordinates display button (slot 13)
     * - Toggle switch (slot 15)
     * - Static icon (slot 16)
     * - GameMode selector (slot 18)
     * - Double-drop button (slot 20)
     * - Horizontal progress bar (slot 21, 4x2 size)
     * - Vertical progress bar (slot 17, 1x5 size)
     *
     * @param context the menu context
     * @return the root grid component containing all test components
     */
    @Override
    protected MenuComponent getRoot(MenuContext context) {
        return Grid.create()
                .size(9, 6)
                .add(0, simpleButton())
                .add(2, animatedButton())
                .add(8, dynamicButton())
                .add(13, coordinatesDynamicButton())
                .add(15, toggle())
                .add(16, icon())
                .add(18, selector())
                .add(20, doubleDropButton())
                .add(21, rightProgressBar())
                .add(17, downProgressBar())
                .build();
    }
}