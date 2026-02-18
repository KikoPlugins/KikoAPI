package fr.kikoplugins.kikoapi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import fr.kikoplugins.kikoapi.menu.component.premade.ConfirmationMenu;
import fr.kikoplugins.kikoapi.menu.test.BasicTestMenu;
import fr.kikoplugins.kikoapi.menu.test.DynamicTestMenu;
import fr.kikoplugins.kikoapi.menu.test.PaginatedTestMenu;
import fr.kikoplugins.kikoapi.menu.test.PreviousTestMenu;
import fr.kikoplugins.kikoapi.utils.CommandUtils;
import fr.kikoplugins.kikoapi.utils.ItemBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KikoAPITestCommand {
    private KikoAPITestCommand() {
        throw new IllegalStateException("Command class");
    }

    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        return Commands.literal("test")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi.test", true))
                .then(basicCommand())
                .then(confirmationCommand())
                .then(dynamicCommand())
                .then(paginatorCommand())
                .then(previousCommand());
    }

    private static LiteralArgumentBuilder<CommandSourceStack> basicCommand() {
        return Commands.literal("basic")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi.test.basic", true))
                .executes(ctx -> {
                    Player player = (Player) ctx.getSource().getExecutor();
                    BasicTestMenu menu = new BasicTestMenu(player);
                    menu.open();

                    return Command.SINGLE_SUCCESS;
                });
    }

    private static LiteralArgumentBuilder<CommandSourceStack> confirmationCommand() {
        return Commands.literal("confirmation")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi.test.confirmation", true))
                .executes(ctx -> {
                    Player player = (Player) ctx.getSource().getExecutor();
                    ConfirmationMenu menu = new ConfirmationMenu(
                            player,
                            Component.text("Are you sure ?"),
                            ItemBuilder.of(Material.LIME_DYE).name(Component.text("Yes", NamedTextColor.GREEN)).build(),
                            ItemBuilder.of(Material.RED_DYE).name(Component.text("No", NamedTextColor.RED)).build(),
                            ItemBuilder.of(Material.OAK_SIGN).name(Component.text("This action is irreversible")).build(),
                            event -> event.getPlayer().sendRichMessage("You clicked <green>Yes"),
                            event -> event.getPlayer().sendRichMessage("You clicked <red>No")
                    );
                    menu.open();

                    return Command.SINGLE_SUCCESS;
                });
    }

    private static LiteralArgumentBuilder<CommandSourceStack> dynamicCommand() {
        return Commands.literal("dynamic")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi.test.dynamic", true))
                .executes(ctx -> {
                    Player player = (Player) ctx.getSource().getExecutor();
                    DynamicTestMenu menu = new DynamicTestMenu(player);
                    menu.open();

                    return Command.SINGLE_SUCCESS;
                });
    }

    private static LiteralArgumentBuilder<CommandSourceStack> paginatorCommand() {
        return Commands.literal("paginator")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi.test.paginator", true))
                .executes(ctx -> {
                    Player player = (Player) ctx.getSource().getExecutor();
                    PaginatedTestMenu menu = new PaginatedTestMenu(player);
                    menu.open();

                    return Command.SINGLE_SUCCESS;
                });
    }

    private static LiteralArgumentBuilder<CommandSourceStack> previousCommand() {
        return Commands.literal("previous")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi.test.previous", true))
                .executes(ctx -> {
                    Player player = (Player) ctx.getSource().getExecutor();
                    new PreviousTestMenu(player).open();

                    return Command.SINGLE_SUCCESS;
                });
    }
}
