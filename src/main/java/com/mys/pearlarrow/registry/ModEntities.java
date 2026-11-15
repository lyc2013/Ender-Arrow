package com.mys.pearlarrow.registry;

import com.mys.pearlarrow.PearlArrowMod;
import com.mys.pearlarrow.entity.PearlArrowEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModEntities {
    public static final EntityType<PearlArrowEntity> PEARL_ARROW = EntityType.Builder
            .<PearlArrowEntity>create(PearlArrowEntity::new, SpawnGroup.MISC)
            .setDimensions(0.5F, 0.5F)
            .maxTrackingRange(4)
            .trackingTickInterval(20)
            .build(PearlArrowMod.id("pearl_arrow").toString());

    private ModEntities() {
    }

    public static void register() {
        Registry.register(Registries.ENTITY_TYPE, new Identifier(PearlArrowMod.MOD_ID, "pearl_arrow"), PEARL_ARROW);
    }
}
