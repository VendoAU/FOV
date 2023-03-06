package com.vendoau.fov.mixin;

import com.vendoau.fov.FOV;
import net.minecraft.sortme.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "method_1848", at = @At("HEAD"), cancellable = true)
    private void getFOVModifier(float f, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue((float) FOV.getFOV());
    }
}
