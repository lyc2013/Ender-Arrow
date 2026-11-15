package com.mys.pearlarrow;

import com.mys.pearlarrow.registry.ModEntities;
import com.mys.pearlarrow.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PearlArrowMod implements ModInitializer {
    public static final String MOD_ID = "pearlarrow";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModItems.register();
        ModEntities.register();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register(entries -> entries.add(ModItems.PEARL_ARROW));

        LOGGER.info("Pearl Arrow initialized");
    }
}
