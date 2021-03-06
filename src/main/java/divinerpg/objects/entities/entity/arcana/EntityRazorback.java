package divinerpg.objects.entities.entity.arcana;

import divinerpg.objects.entities.entity.EntityDivineRPGMob;
import divinerpg.registry.DRPGLootTables;
import divinerpg.registry.ModSounds;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityRazorback extends EntityDivineRPGMob {

    public EntityRazorback(World world) {
        super(world);
        this.setSize(0.6F, 0.5F);
    }

    @Override
    public float getEyeHeight() {
        return 0.225F;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        addAttackingAI();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.RAZORBACK;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource s) {
        return ModSounds.RAZORBACK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.RAZORBACK_HURT;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return DRPGLootTables.ENTITIES_RAZORBACK;
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.posY < 40.0D && super.getCanSpawnHere();
    }
}