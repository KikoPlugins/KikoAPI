package fr.kikoplugins.kikoapi.menu.test;

import fr.kikoplugins.kikoapi.menu.Menu;
import fr.kikoplugins.kikoapi.menu.MenuContext;
import fr.kikoplugins.kikoapi.menu.component.MenuComponent;
import fr.kikoplugins.kikoapi.menu.component.display.Icon;
import fr.kikoplugins.kikoapi.menu.component.interactive.Button;
import fr.kikoplugins.kikoapi.menu.component.layout.Grid;
import fr.kikoplugins.kikoapi.utils.ColorUtils;
import fr.kikoplugins.kikoapi.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

/**
 * Example menu using the previousMenus system.
 */
@NullMarked
public class PreviousTestMenu extends Menu {

    public PreviousTestMenu(Player player) {
        super(player);
    }

    public PreviousTestMenu(Player player, MenuContext context) {
        super(player, context);
    }

    private static Button previousMenuButton() {
        return Button.create()
                .item(ItemBuilder.of(Material.ARROW)
                        .name(Component.text("Go to Previous Menu"))
                        .build())
                .onClick(event -> {
                    Menu previous = event.context().previousMenu();
                    if (previous == null) {
                        event.player().sendMessage(Component.text("No previous menu found!", NamedTextColor.RED));
                        return;
                    }

                    previous.open();
                })
                .build();
    }

    private static Button nextMenuButton() {
        return Button.create()
                .item(ItemBuilder.of(Material.ARROW)
                        .name(Component.text("Go to Next Menu"))
                        .build())
                .onClick(event -> {
                    new PreviousTestMenu(event.player(), event.context()).open();
                })
                .build();
    }

    @Override
    protected Component title() {
        return Component.text("Menu ID: " + System.identityHashCode(this), ColorUtils.primaryColor());
    }

    @Override
    protected MenuComponent root(MenuContext context) {
        return Grid.create()
                .size(9, 3)
                .add(0, previousMenuButton())
                .add(4, Icon.create()
                        .item(ItemBuilder.of(Material.BOOK)
                                .name(Component.text("Current Menu ID: " + System.identityHashCode(this)))
                                .build())
                        .build())
                .add(8, nextMenuButton())
                .build();
    }
}