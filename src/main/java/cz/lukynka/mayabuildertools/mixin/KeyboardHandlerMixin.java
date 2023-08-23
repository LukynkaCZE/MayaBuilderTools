package cz.lukynka.mayabuildertools.mixin;

import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Inject(at = @At("HEAD"), method = "keyPress", cancellable = true)
    public void keyPress(long l, int i, int j, int k, int m, CallbackInfo ci) {
    }
}
