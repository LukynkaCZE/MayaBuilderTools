package cz.lukynka.mayabuildertools.mixin;

import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(UseOnContext.class)
public class UseOnContextMixin {

    @Inject(at = @At("HEAD"), method = "getRotation", cancellable = true)
    public void getRotation(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(1F);
    }
}
