package cz.lukynka.mayabuildertools;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class Utils {

    //I stole this from island utils ok
    public static final String prefix = "&8[&bBetterSavedHotbars&8]";
    public static String translate(String s) {
        return s.replaceAll("&", "§");
    }

    public static void send(String s) {
        send(Component.literal(translate(prefix + " " + s)));
    }

    public static void debug(String s, Object... args) {
        debug(String.format(s, args));
    }

    public static void debug(String s) {
        send(Component.literal(translate("&7[DEBUG] " + s)));
    }

    public static void debug(Component component) {
        send(Component.literal(translate("&7[DEBUG] ")).append(component));
    }

    public static void send(Component component) {
        Minecraft.getInstance().getChatListener().handleSystemMessage(component, false);
    }

    public static boolean isNegative(double d) {
        return Double.compare(d, 0.0) < 0;
    }

    public static String toStrictProperCase(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    public static boolean heldItemContains(String[] names) {
        var returnValue = false;
        assert Minecraft.getInstance().player != null;
        var heldItemString = Minecraft.getInstance().player.getMainHandItem().toString();
        for (String name : names) {
            if (heldItemString.contains(name)) {
                returnValue = true;
                break;
            }
        }
        return returnValue;
    }

}