package com.eternalcode.kaskadasoftware.commands;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.utils.entity.EntityFinder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class GamemodeCommand extends Command {

    public GamemodeCommand() {
        super("gamemode", "gm");

        ArgumentEnum<GameMode> gamemode = ArgumentType.Enum("gamemode", GameMode.class).setFormat(ArgumentEnum.Format.LOWER_CASED);
        gamemode.setCallback((sender, exception) -> sender.sendMessage(
                Component.text("Invalid gamemode ", NamedTextColor.RED)
                        .append(Component.text(exception.getInput(), NamedTextColor.WHITE))
                        .append(Component.text("!")), MessageType.SYSTEM));

        ArgumentEntity player = ArgumentType.Entity("targets").onlyPlayers(true);

        setDefaultExecutor((sender, context) -> {
            String commandName = context.getCommandName();
            sender.sendMessage(Component.text("Usage: /" + commandName + " <gamemode> [targets]", NamedTextColor.RED), MessageType.SYSTEM);
        });

        addSyntax((sender, context) -> {
            if (!sender.isPlayer()) {
                sender.sendMessage(Component.text("Please run this command in-game.", NamedTextColor.RED));
                return;
            }

            GameMode mode = context.get(gamemode);
            executeSelf(sender.asPlayer(), mode);
        }, gamemode);

        //Command Syntax for /gamemode <gamemode> [targets]
        addSyntax((sender, context) -> {
            EntityFinder finder = context.get(player);
            GameMode mode = context.get(gamemode);

            //Set the gamemode for the targets
            executeOthers(sender, mode, finder.find(sender));
        }, gamemode, player);
    }

    private void executeOthers(CommandSender sender, GameMode mode, @NotNull List<Entity> entities) {
        if (entities.size() == 0) {
            if (sender.isPlayer())
                sender.sendMessage(Component.translatable("argument.entity.notfound.player", NamedTextColor.RED), MessageType.SYSTEM);
            else sender.sendMessage(Component.text("No player was found", NamedTextColor.RED), MessageType.SYSTEM);
        } else for (Entity entity : entities) {
            if (entity instanceof Player p) {
                if (p == sender) {
                    executeSelf(sender.asPlayer(), mode);
                } else {
                    p.setGameMode(mode);

                    String gamemodeString = "gameMode." + mode.name().toLowerCase(Locale.ROOT);
                    Component gamemodeComponent = Component.translatable(gamemodeString);
                    Component playerName = p.getDisplayName() == null ? p.getName() : p.getDisplayName();

                    //Send a message to the changed player and the sender
                    p.sendMessage(Component.translatable("gameMode.changed", gamemodeComponent), MessageType.SYSTEM);
                    sender.sendMessage(Component.translatable("commands.gamemode.success.other", playerName, gamemodeComponent), MessageType.SYSTEM);
                }
            }
        }
    }

    private void executeSelf(Player sender, GameMode mode) {
        sender.setGameMode(mode);
        String gamemodeString = "gameMode." + mode.name().toLowerCase(Locale.ROOT);
        Component gamemodeComponent = Component.translatable(gamemodeString);

        sender.sendMessage(Component.translatable("commands.gamemode.success.self", gamemodeComponent), MessageType.SYSTEM);
    }
}