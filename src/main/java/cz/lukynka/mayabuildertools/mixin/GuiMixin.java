package cz.lukynka.mayabuildertools.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import cz.lukynka.mayabuildertools.UI.CustomOrientationPickerScreen;
import cz.lukynka.mayabuildertools.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cz.lukynka.mayabuildertools.MayaBuilderTools.allowedCustomOrientationBlocks;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void render(PoseStack poseStack, float f, CallbackInfo ci) {
        assert Minecraft.getInstance().player != null;
        if(Screen.hasAltDown() && Utils.heldItemContains(allowedCustomOrientationBlocks)) {
            if(!(Minecraft.getInstance().screen instanceof CustomOrientationPickerScreen)) {
                var screen = new CustomOrientationPickerScreen();
                screen.passEvents = true;
                Minecraft.getInstance().setScreen(new CustomOrientationPickerScreen());
            }
        } else {
            if(Minecraft.getInstance().screen instanceof CustomOrientationPickerScreen) {
                Minecraft.getInstance().setScreen(null);
            }
        }
    }
}
