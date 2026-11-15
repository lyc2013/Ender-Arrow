package com.mys.pearlarrow.entity;

import com.mys.pearlarrow.registry.ModEntities;
import com.mys.pearlarrow.registry.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * An arrow that pulls its shooter to the point of impact.
 */
public class PearlArrowEntity extends ArrowEntity {
    private static final int IMPACT_PARTICLES = 48;
    private static final int TRAIL_PARTICLES_PER_TICK = 2;

    public PearlArrowEntity(EntityType<? extends PearlArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    public PearlArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.PEARL_ARROW, world);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.1D, owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient && !this.inGround) {
            this.spawnTrailParticles();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity target = entityHitResult.getEntity();
        Vec3d impact = entityHitResult.getPos();
        boolean hitOwner = target == this.getOwner();

        super.onEntityHit(entityHitResult);

        if (!this.getWorld().isClient && !hitOwner) {
            this.teleportOwnerTo(impact);
            this.discard();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        // Step slightly out of the surface so the shooter does not end up inside the block.
        Vec3d impact = blockHitResult.getPos()
                .add(Vec3d.of(blockHitResult.getSide().getVector()).multiply(0.35D));

        super.onBlockHit(blockHitResult);

        if (!this.getWorld().isClient) {
            this.teleportOwnerTo(impact);
            this.discard();
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.PEARL_ARROW);
    }

    private void teleportOwnerTo(Vec3d impact) {
        Entity owner = this.getOwner();
        if (!(owner instanceof LivingEntity livingOwner) || !livingOwner.isAlive()) {
            this.spawnImpactEffects(impact);
            return;
        }

        World world = this.getWorld();
        if (!(world instanceof ServerWorld serverWorld)) {
            return;
        }

        Vec3d origin = livingOwner.getPos();
        Vec3d destination = this.findSafeDestination(livingOwner, impact);

        if (livingOwner.hasVehicle()) {
            livingOwner.stopRiding();
        }

        if (livingOwner instanceof ServerPlayerEntity player) {
            player.teleport(serverWorld, destination.x, destination.y, destination.z,
                    player.getYaw(), player.getPitch());
        } else {
            livingOwner.requestTeleport(destination.x, destination.y, destination.z);
        }

        livingOwner.setVelocity(Vec3d.ZERO);
        livingOwner.fallDistance = 0.0F;

        this.spawnImpactEffects(origin);
        this.spawnImpactEffects(impact);
    }

    /**
     * Keeps lifting the destination until the entity fits, falling back to the raw impact point.
     */
    private Vec3d findSafeDestination(LivingEntity entity, Vec3d target) {
        EntityDimensions dimensions = entity.getDimensions(EntityPose.STANDING);
        for (int i = 0; i <= 8; i++) {
            Vec3d candidate = target.add(0.0D, i * 0.5D, 0.0D);
            Box box = dimensions.getBoxAt(candidate);
            if (this.getWorld().isSpaceEmpty(entity, box)) {
                return candidate;
            }
        }
        return target;
    }

    private void spawnTrailParticles() {
        for (int i = 0; i < TRAIL_PARTICLES_PER_TICK; i++) {
            this.getWorld().addParticle(ParticleTypes.PORTAL,
                    this.getX() + (this.random.nextDouble() - 0.5D) * 0.4D,
                    this.getY() + (this.random.nextDouble() - 0.5D) * 0.4D,
                    this.getZ() + (this.random.nextDouble() - 0.5D) * 0.4D,
                    (this.random.nextDouble() - 0.5D) * 0.1D,
                    (this.random.nextDouble() - 0.5D) * 0.1D,
                    (this.random.nextDouble() - 0.5D) * 0.1D);
        }
    }

    private void spawnImpactEffects(Vec3d pos) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.PORTAL,
                    pos.x, pos.y + 0.5D, pos.z,
                    IMPACT_PARTICLES, 0.3D, 0.5D, 0.3D, 0.2D);
            serverWorld.playSound(null, pos.x, pos.y, pos.z,
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
    }
}
