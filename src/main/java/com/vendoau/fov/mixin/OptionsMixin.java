package com.vendoau.fov.mixin;

import com.vendoau.fov.FOV;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.menu.Options;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;

@Mixin(Options.class)
public abstract class OptionsMixin extends ScreenBase {

    @Shadow
    private static Option[] OPTIONS;

    static {
        OPTIONS = Arrays.copyOf(OPTIONS, OPTIONS.length + 1);
        OPTIONS[OPTIONS.length - 1] = FOV.fovOption;
    }
}
