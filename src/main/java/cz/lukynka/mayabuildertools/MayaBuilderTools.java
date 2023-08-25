package cz.lukynka.mayabuildertools;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MayaBuilderTools implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger(MayaBuilderTools.class);

    public static String glazedTerracottaDirection = "DEFAULT";
    public static int glazedTerracottaDirectionsIndex = 4;
    public static List<String> glazedTerracottaDirections = new ArrayList<>();

    public static String[] allowedBlocks = {"glazed", "carved", "jack", "cutter", "bee", "furnace", "dispenser", "dropper", "chest", "anvil", "smoker", "grind", "observer", "rail"};

    @Override
    public void onInitialize() {
        LOGGER.info("Loaded Maya Builder Tools");
        glazedTerracottaDirections.add("NORTH");
        glazedTerracottaDirections.add("SOUTH");
        glazedTerracottaDirections.add("EAST");
        glazedTerracottaDirections.add("WEST");
        glazedTerracottaDirections.add("DEFAULT");
    }
}
