package net.divinerpg.entities.vethea;

import net.divinerpg.api.entity.EntityDivineRPGMob;
import net.divinerpg.libs.Sounds;
import net.divinerpg.utils.items.VetheaItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGalroid extends VetheaMob {
	
    private int invulnTicks;

    public EntityGalroid(World var1) {
        super(var1);
        addAttackingAI();
    }

    @Override
    public int getSpawnLayer() {
    	return 3;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.rand.nextInt(200) == 0 && !this.isEntityInvulnerable()) {
            this.invulnTicks = 200;
        }
        else if (this.isEntityInvulnerable()) {
            this.invulnTicks--;
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(net.divinerpg.api.entity.EntityStats.galroidHealth);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(net.divinerpg.api.entity.EntityStats.galroidDamage);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(net.divinerpg.api.entity.EntityStats.galroidSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(net.divinerpg.api.entity.EntityStats.galroidFollowRange);
    }

    @Override
    protected float getSoundVolume() {
        return 0.7F;
    }

    @Override
    protected String getLivingSound() {
        return Sounds.galroid.getPrefixedName();
    }

    @Override
    protected String getHurtSound() {
        return Sounds.galroidHurt.getPrefixedName();
    }

    @Override
    protected String getDeathSound() {
        return getHurtSound();
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity) {
        float var2 = 25;

        if (this.isPotionActive(Potion.damageBoost)) {
            var2 += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
        }

        if (this.isPotionActive(Potion.weakness)) {
            var2 -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
        }

        int var3 = 0;

        if (par1Entity instanceof EntityLiving) {
            var2 += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLiving)par1Entity);
            var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLiving)par1Entity);
        }

        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);

        if (var4) {
            if (var3 > 0) {
                par1Entity.addVelocity(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * var3 * 0.5F, 0.4D, MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * var3 * 0.5F);
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int var5 = EnchantmentHelper.getFireAspectModifier(this);

            if (var5 > 0) {
                par1Entity.setFire(var5 * 4);
            }
        }

        return var4;
    }

    @Override
    public boolean isEntityInvulnerable() {
        return (this.invulnTicks > 1);
    }

    @Override
    protected void dropFewItems(boolean par1, int par2) {
        this.dropItem(VetheaItems.polishedPearls, 1);
    }

	@Override
	public String mobName() {
		return "Glariod";
	}
}