package de.dafuqs.revelationary;

import de.dafuqs.revelationary.api.advancements.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.*;
import net.fabricmc.fabric.api.resource.*;
import net.fabricmc.loader.api.*;
import net.minecraft.resource.*;
import org.slf4j.*;

public class Revelationary implements ModInitializer {
    public static final String MOD_ID = "revelationary";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void logInfo(String message) {
        LOGGER.info("[Revelationary] {}", message);
    }

    public static void logWarning(String message) {
        LOGGER.warn("[Revelationary] {}", message);
    }

    public static void logError(String message) {
        LOGGER.error("[Revelationary] {}", message);
    }
    public static void logException(Throwable t) {
        LOGGER.error("[Revelationary] ", t);
    }

    @Override
    public void onInitialize() {
        logInfo("Starting Common Startup");

        RevelationaryNetworking.register();

        AdvancementCriteria.register();
        CommandRegistrationCallback.EVENT.register(Commands::register);
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(RevelationDataLoader.INSTANCE);

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            RevelationRegistry.addRevelationAwares();
            RevelationRegistry.deepTrim();
        });
        if (FabricLoader.getInstance().isModLoaded("sodium")) {
            logWarning("Sodium detected. Chunk rebuilding will be done in cursed mode.");
        }

        logInfo("Common startup completed!");
    }
}
