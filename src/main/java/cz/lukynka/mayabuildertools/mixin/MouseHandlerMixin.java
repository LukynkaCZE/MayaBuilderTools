package cz.lukynka.mayabuildertools.mixin;

import cz.lukynka.mayabuildertools.UI.GlazedTerracottaPicker;
import cz.lukynka.mayabuildertools.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cz.lukynka.mayabuildertools.MayaBuilderTools.*;

@Mixin(net.minecraft.client.MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(at = @At("HEAD"), method = "onScroll", cancellable = true)
    private void onScroll(long l, double d, double e, CallbackInfo ci) {

        var player = Minecraft.getInstance().player;

        if(player == null) return;

        var scrollingUp = Utils.isNegative(e);

        var heldItem = Minecraft.getInstance().player.getMainHandItem().getItem().toString();
        if(heldItem.contains("glazed") && Screen.hasControlDown()) {
            ci.cancel();
            if(scrollingUp) {
                if(glazedTerracottaDirectionsIndex == 4) {
                    glazedTerracottaDirectionsIndex = 0;
                } else {
                    glazedTerracottaDirectionsIndex++;
                }
            } else {
                if(glazedTerracottaDirectionsIndex == 0) {
                    glazedTerracottaDirectionsIndex = 4;
                } else {
                    glazedTerracottaDirectionsIndex--;
                }
            }
            glazedTerracottaDirection = glazedTerracottaDirections.get(glazedTerracottaDirectionsIndex);
            Minecraft.getInstance().setScreen(new GlazedTerracottaPicker());
            //Utils.debug("scrollingUp: " +scrollingUp + " | index: " +glazedTerracottaDirectionsIndex +" | direction: " +glazedTerracottaDirection);
        }
    }
}