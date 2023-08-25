package cz.lukynka.mayabuildertools;

import cz.lukynka.mayabuildertools.Updater.UpdateManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.ModContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.loader.api.FabricLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MayaBuilderTools implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(MayaBuilderTools.class);
    public static String customOrientationDirection = "DEFAULT";
    public static int customOrientationDirectionIndex = 4;
    public static List<String> customOrientationDirectionList = new ArrayList<>();
    public static String version = "";
    public static String updateTag = "";
    public static boolean isPreRelease = false;
    public static UpdateManager updateManager = new UpdateManager();
    public static boolean updateAvailable = false;
    public static String[] allowedCustomOrientationBlocks = {"glazed", "carved", "jack", "cutter", "bee", "furnace", "dispenser", "dropper", "chest", "anvil", "smoker", "grind", "observer", "rail"};

    @Override
    public void onInitialize() {

        //Get the current version string
        Optional<ModContainer> container = FabricLoader.getInstance().getModContainer("maya-builder-tools");
        container.ifPresent(modContainer -> version = modContainer.getMetadata().getVersion().getFriendlyString());

        LOGGER.info("Loaded Maya Builder Tools");
        customOrientationDirectionList.add("NORTH");
        customOrientationDirectionList.add("SOUTH");
        customOrientationDirectionList.add("EAST");
        customOrientationDirectionList.add("WEST");
        customOrientationDirectionList.add("DEFAULT");

        // Check for updates
        updateManager.runUpdateCheck();
    }
}