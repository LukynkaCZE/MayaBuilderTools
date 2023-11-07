package cz.lukynka.mayabuildertools.mixin;

import cz.lukynka.mayabuildertools.UI.CustomOrientationPickerScreen;
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
        var screen = Minecraft.getInstance().screen;

        if(!(screen instanceof CustomOrientationPickerScreen)) return;
        if(player == null) return;

        var scrollingUp = Utils.isNegative(e);

        if(Utils.heldItemContains(allowedCustomOrientationBlocks) && Screen.hasAltDown()) {
            ci.cancel();
            if(scrollingUp) {
                if(customOrientationDirectionIndex == 4) {
                    customOrientationDirectionIndex = 0;
                } else {
                    customOrientationDirectionIndex++;
                }
            } else {
                if(customOrientationDirectionIndex == 0) {
                    customOrientationDirectionIndex = 4;
                } else {
                    customOrientationDirectionIndex--;
                }
            }
            customOrientationDirection = customOrientationDirectionList.get(customOrientationDirectionIndex);
            Minecraft.getInstance().setScreen(new CustomOrientationPickerScreen());
        }
    }
}