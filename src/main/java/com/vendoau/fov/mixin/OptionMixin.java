package com.vendoau.fov.mixin;

import com.vendoau.fov.FOV;
import net.minecraft.client.options.Option;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(Option.class)
public abstract class OptionMixin {

    @Invoker("<init>")
    private static Option newOption(String internalName, int internalId, String translationKey, boolean slider, boolean toggle) {
        throw new AssertionError();
    }

    @Shadow
    @Final
    @Mutable
    private static Option[] field_1113;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/client/options/Option;field_1113:[Lnet/minecraft/client/options/Option;", shift = At.Shift.AFTER))
    private static void addCustomOption(CallbackInfo ci) {
        List<Option> options = new ArrayList<>(Arrays.asList(field_1113));
        Option last = options.get(options.size() - 1);
        // This means our code will still work if other mods add more options
        Option fov = newOption("FOV", last.ordinal(), "option.fov", true, false);
        FOV.fovOption = fov;
        options.add(fov);
        field_1113 = options.toArray(new Option[0]);
    }
}
