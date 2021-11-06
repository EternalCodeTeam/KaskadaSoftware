package com.eternalcode.kaskadasoftware;

import com.eternalcode.kaskadasoftware.services.KaskadaSoftwareService;
import net.minestom.server.MinecraftServer;

public class KaskadaSoftware {
    public static void main(String[] strings) {
        System.out.println("Starting KaskadaSoftware...");

        // Initialization
        MinecraftServer.init();
        KaskadaSoftwareService.init();

        // Start the server on port 25565
        MinecraftServer.init().start("0.0.0.0", 25565);
    }
}