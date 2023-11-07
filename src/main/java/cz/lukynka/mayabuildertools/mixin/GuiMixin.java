package cz.lukynka.mayabuildertools.mixin;

import cz.lukynka.mayabuildertools.UI.CustomOrientationPickerScreen;
import cz.lukynka.mayabuildertools.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cz.lukynka.mayabuildertools.MayaBuilderTools.allowedCustomOrientationBlocks;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void render(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        assert Minecraft.getInstance().player != null;
        var screen = Minecraft.getInstance().screen;
        if(Screen.hasAltDown() && Utils.heldItemContains(allowedCustomOrientationBlocks)) {
            if(screen == null) {
                Minecraft.getInstance().setScreen(new CustomOrientationPickerScreen());
            }
        } else {
            if(Minecraft.getInstance().screen instanceof CustomOrientationPickerScreen) {
                Minecraft.getInstance().setScreen(null);
            }
        }
    }
}
