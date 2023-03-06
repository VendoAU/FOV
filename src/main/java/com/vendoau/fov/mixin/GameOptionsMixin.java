package com.vendoau.fov.mixin;

import com.vendoau.fov.FOV;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.BufferedReader;
import java.io.PrintWriter;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {

    @Shadow
    protected abstract float parseFloat(String string);

    @Inject(method = "method_1228", at = @At("HEAD"))
    public void setFloatValue(Option option, float value, CallbackInfo ci) {
        if (option == FOV.fovOption) {
            FOV.fovValue = value;
        }
    }

    @Inject(method = "getFloatValue", at = @At("HEAD"), cancellable = true)
    public void getFloatValue(Option option, CallbackInfoReturnable<Float> cir) {
        if (option == FOV.fovOption) {
            cir.setReturnValue(FOV.fovValue);
        }
    }

    @Inject(method = "getTranslatedValue", at = @At("HEAD"), cancellable = true)
    public void getTranslatedValue(Option option, CallbackInfoReturnable<String> cir) {
        if (option == FOV.fovOption) {
            float value = FOV.fovValue;
            if (value == 0) {
                cir.setReturnValue("FOV: Normal");
            } else if (value == 1) {
                cir.setReturnValue("FOV: Quake Pro");
            } else {
                cir.setReturnValue("FOV: " + FOV.getFOV());
            }
        }
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Ljava/lang/String;split(Ljava/lang/String;)[Ljava/lang/String;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void load(CallbackInfo ci, BufferedReader bufferedReader, String string) {
        final String[] stringArray = string.split(":");
        if (stringArray[0].equals("fov")) {
            FOV.fovValue = parseFloat(stringArray[1]);
        }
    }

    @Inject(method = "saveOptions", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void saveOptions(CallbackInfo ci, PrintWriter printWriter) {
        printWriter.println("fov:" + FOV.fovValue);
    }
}