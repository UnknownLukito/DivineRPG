package naturix.divinerpg.entities.entity;

import naturix.divinerpg.entities.entity.vanilla.AyeracoBlue;
import naturix.divinerpg.entities.entity.vanilla.AyeracoGreen;
import naturix.divinerpg.entities.entity.vanilla.AyeracoPurple;
import naturix.divinerpg.entities.entity.vanilla.AyeracoRed;
import naturix.divinerpg.entities.entity.vanilla.AyeracoYellow;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public abstract class EntityDivineRPGBoss extends EntityDivineRPGMob {
	private BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.BLUE, BossInfo.Overlay.PROGRESS));

	public EntityDivineRPGBoss(World par1World) {
		super(par1World);
	}

    @Override
    protected boolean isValidLightLevel() {
        return true;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

	@Override
	public boolean canDespawn() {
		return false;	
	}

    @Override
	public boolean isNonBoss() {
		return false;
	}
	
	public Color getBarColor()
	{
		return Color.BLUE;
	}

	@Override
	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		bossInfo.setColor(getBarColor());
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}
}