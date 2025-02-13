/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.debug.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

@Mod(CustomMobBucketTest.MODID)
public class CustomMobBucketTest {
    public static final String MODID = "custom_mob_bucket_test";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final boolean ENABLED = true;

    public static final RegistryObject<Item> COW_BUCKET = ITEMS.register("cow_bucket", () -> new MobBucketItem(
            () -> EntityType.COW,
            () -> Fluids.WATER,
            () -> SoundEvents.BUCKET_EMPTY_FISH,
            (new Item.Properties()).stacksTo(1)));

    public CustomMobBucketTest() {
        if (ENABLED) {
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
            ITEMS.register(modEventBus);
            modEventBus.addListener(this::addCreative);
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
            event.accept(COW_BUCKET);
    }
}
