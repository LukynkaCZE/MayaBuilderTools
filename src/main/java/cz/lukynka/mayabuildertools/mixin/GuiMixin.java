package cz.lukynka.mayabuildertools.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import cz.lukynka.mayabuildertools.UI.GlazedTerracottaPicker;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(at = @At("HEAD"), method = "render")
    public void render(PoseStack poseStack, float f, CallbackInfo ci) {
        assert Minecraft.getInstance().player != null;
        var heldItem = Minecraft.getInstance().player.getMainHandItem().getItem().toString();
        if(Screen.hasControlDown() && heldItem.contains("glazed")) {
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
