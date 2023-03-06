package com.vendoau.fov;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.options.Option;

public class FOV implements ClientModInitializer {

    public static Option fovOption;
    public static float fovValue;

    @Override
    public void onInitializeClient() {}

    public static int getFOV() {
        return Math.round(70 + fovValue * 40);
    }
}
