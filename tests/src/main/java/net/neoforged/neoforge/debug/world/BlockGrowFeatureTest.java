/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.debug.world;

import net.minecraft.data.worldgen.features.TreeFeatures;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.level.SaplingGrowTreeEvent;
import org.apache.logging.log4j.LogManager;

@Mod("block_grow_feature_test")
@Mod.EventBusSubscriber
public class BlockGrowFeatureTest {
    public static final boolean ENABLE = true;

    @SubscribeEvent
    public static void onGrowReplaceFeature(SaplingGrowTreeEvent event) {
        if (ENABLE) {
            event.setFeature(TreeFeatures.BIRCH_BEES_005);
            if (event.getFeature().equals(TreeFeatures.BIRCH_BEES_005)) {
                LogManager.getLogger().info("BlockGrowFeatureTest replaced feature");
            }
        }
    }
}
