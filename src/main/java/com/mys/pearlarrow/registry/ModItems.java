package com.mys.pearlarrow.registry;

import com.mys.pearlarrow.PearlArrowMod;
import com.mys.pearlarrow.item.PearlArrowItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {
    public static final Item PEARL_ARROW = new PearlArrowItem(new Item.Settings());

    private ModItems() {
    }

    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(PearlArrowMod.MOD_ID, "pearl_arrow"), PEARL_ARROW);
    }
}
