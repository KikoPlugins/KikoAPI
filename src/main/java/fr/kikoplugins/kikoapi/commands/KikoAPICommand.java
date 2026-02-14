package fr.kikoplugins.kikoapi.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fr.kikoplugins.kikoapi.KikoAPI;
import fr.kikoplugins.kikoapi.lang.Lang;
import fr.kikoplugins.kikoapi.utils.CommandUtils;
import fr.kikoplugins.kikoapi.utils.MathUtils;
import fr.kikoplugins.kikoapi.utils.Task;
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
                .then(KikoAPITestCommand.get())
                .build();
    }

    private static LiteralArgumentBuilder<CommandSourceStack> reloadCommand() {
        return Commands.literal("reload")
                .requires(css -> CommandUtils.defaultRequirements(css, "kikoapi.command.kikoapi.reload"))
                .executes(ctx -> {
                    CommandSender sender = CommandUtils.sender(ctx);

                    LANG.sendMessage(sender, "command.reload.start");
                    long startNanos = System.nanoTime();

                    Task.async(task -> {
                        try {
                            KikoAPI.getInstance().reload();
                            double timeTaken = (System.nanoTime() - startNanos) / 1_000_000D;

                            LANG.sendMessage(sender, "command.reload.done",
                                    Lang.numberPlaceholder("time_ms", MathUtils.decimalRound(timeTaken, 2))
                            );
                        } catch (Exception e) {
                            KikoAPI.getInstance().getSLF4JLogger().error("Failed to reload KikoAPI", e);
                            LANG.sendMessage(sender, "command.reload.error");
                        }
                    }, KikoAPI.getInstance());

                    return Command.SINGLE_SUCCESS;
                });
    }

    private static LiteralArgumentBuilder<CommandSourceStack> sendTestMessageCommand() {
        return Commands.literal("sendtestmessage")
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
