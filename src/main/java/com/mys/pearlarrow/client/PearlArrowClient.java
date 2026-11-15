package com.mys.pearlarrow.client;

import com.mys.pearlarrow.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.ArrowEntityRenderer;

public class PearlArrowClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.PEARL_ARROW, ArrowEntityRenderer::new);
    }
}
