package com.eternalcode.lobbysoftware.services;

import com.eternalcode.lobbysoftware.commands.GamemodeCommand;
import com.eternalcode.lobbysoftware.generators.ChunkGenerator;
import com.eternalcode.lobbysoftware.initializations.PlayerInit;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.optifine.OptifineSupport;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;

@Getter @Setter
public class KaskadaSoftwareService {

    public static void init() {
        // Create the instance
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        // commands manager
        CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.register(new GamemodeCommand());
        commandManager.setUnknownCommandCallback((sender, command) -> sender.sendMessage(Component.text("unknow command", NamedTextColor.RED)));

        // Set the ChunkGenerator
        instanceContainer.setChunkGenerator(new ChunkGenerator());

        // Shutdown task
        MinecraftServer.getSchedulerManager().buildShutdownTask(() -> System.out.println("Good night")).schedule();

        // Listeners manager
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
            System.out.println("KaskadaSoftware started successfully");
        });

        // Other initializations
        PlayerInit.init();
        OptifineSupport.enable();
        VelocityProxy.enable("");
        BungeeCordProxy.enable();
        MojangAuth.init();
    }
}
