package cz.lukynka.mayabuildertools.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import cz.lukynka.mayabuildertools.UI.GlazedTerracottaPicker;
import cz.lukynka.mayabuildertools.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cz.lukynka.mayabuildertools.MayaBuilderTools.allowedBlocks;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void render(PoseStack poseStack, float f, CallbackInfo ci) {
        assert Minecraft.getInstance().player != null;
        if(Screen.hasAltDown() && Utils.heldItemContains(allowedBlocks)) {
            if(!(Minecraft.getInstance().screen instanceof GlazedTerracottaPicker)) {
                var screen = new GlazedTerracottaPicker();
                screen.passEvents = true;
                Minecraft.getInstance().setScreen(new GlazedTerracottaPicker());
            }
        } else {
            if(Minecraft.getInstance().screen instanceof GlazedTerracottaPicker) {
                Minecraft.getInstance().setScreen(null);
            }
        }
    }
}
