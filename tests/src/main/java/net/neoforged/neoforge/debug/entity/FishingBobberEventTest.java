/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.neoforged.neoforge.debug.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

@Mod("fishing_bobber_event_test")
public class FishingBobberEventTest {
    public static final boolean ENABLE = true;

    public FishingBobberEventTest() {
        if (ENABLE) {
            NeoForge.EVENT_BUS.addListener(FishingBobberEventTest::handleImpact);
        }
    }

    public static void handleImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof FishingHook fishingHook)) return;
        HitResult trace = event.getRayTraceResult();
        if (trace.getType() == HitResult.Type.ENTITY) {
            Vec3 vector3d = fishingHook.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(0.6);
            if (vector3d.lengthSqr() > 0.0D) {
                Entity entity = ((EntityHitResult) trace).getEntity();
                if (entity instanceof LivingEntity) {
                    entity.push(vector3d.x, 0.3D, vector3d.z);
                }
            }
        }
    }
}
