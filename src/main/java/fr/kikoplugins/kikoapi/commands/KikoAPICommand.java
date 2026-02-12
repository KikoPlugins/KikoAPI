package fr.kikoplugins.kikoapi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.kikoplugins.kikoapi.KikoAPI;
import fr.kikoplugins.kikoapi.lang.Lang;
import fr.kikoplugins.kikoapi.utils.CommandUtils;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;

import static fr.kikoplugins.kikoapi.KikoAPI.LANG;

public class KikoAPICommand {
    private KikoAPICommand() {
        throw new IllegalStateException("Command class");
    }

    public static LiteralCommandNode<CommandSourceStack> get() {
        return Commands.literal("kikoapi")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi"))
                .then(reloadCommand())
                .then(sendTestMessageCommand())
                .build();
    }

    private static LiteralArgumentBuilder<CommandSourceStack> reloadCommand() {
        return Commands.literal("reload")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi.reload"))
                .executes(ctx -> {
                    CommandSender sender = CommandUtils.sender(ctx);

                    long startMillis = System.currentTimeMillis();
                    KikoAPI.getInstance().reload();
                    long timeTaken = System.currentTimeMillis() - startMillis;

                    LANG.sendMessage(sender, "kikoapi.command.reload.done",
                            Lang.numberPlaceholder("time_ms", timeTaken)
                    );

                    return Command.SINGLE_SUCCESS;
                });
    }

    private static LiteralArgumentBuilder<CommandSourceStack> sendTestMessageCommand() {
        return Commands.literal("sendtestcommand")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi.sendtestmessage"))
                .then(Commands.argument("key", StringArgumentType.word())
                        .executes(ctx -> {
                            CommandSender sender = CommandUtils.sender(ctx);
                            String key = ctx.getArgument("key", String.class);

                            LANG.sendMessage(sender, key);

                            return Command.SINGLE_SUCCESS;
                        })
                );
    }
}
