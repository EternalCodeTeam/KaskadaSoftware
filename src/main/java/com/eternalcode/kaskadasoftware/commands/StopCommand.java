package com.eternalcode.kaskadasoftware.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import org.jetbrains.annotations.NotNull;

public class StopCommand extends Command {

    public StopCommand() {
        super("shutdown", "stop");
        addSyntax(this::execute);
    }

    private void execute(@NotNull CommandSender commandSender, @NotNull CommandContext commandContext) {
        commandSender.sendMessage("stopping server...");
        MinecraftServer.stopCleanly();
    }
}