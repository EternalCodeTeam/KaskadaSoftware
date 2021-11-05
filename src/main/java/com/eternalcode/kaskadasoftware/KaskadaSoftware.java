package com.eternalcode.kaskadasoftware;

import com.eternalcode.kaskadasoftware.services.KaskadaSoftwareService;
import net.minestom.server.MinecraftServer;

public class KaskadaSoftware {
    public static void main(String[] strings) {
        // Initialization
        System.out.println("Starting KaskadaSoftware...");
        MinecraftServer minecraftServer = MinecraftServer.init();
        KaskadaSoftwareService.init();

        // Start the server on port 25565
        minecraftServer.start("0.0.0.0", 25565);
    }
}