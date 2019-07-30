package naturix.divinerpg.objects.items.arcana;

import naturix.divinerpg.objects.entities.entity.arcana.DungeonConstructor;
import naturix.divinerpg.objects.items.base.ItemMod;
import naturix.divinerpg.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemChargedCollector extends ItemMod {

	public ItemChargedCollector(String name) {
		super(name);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack stack = new ItemStack(player.getHeldItemMainhand().getItem());
		int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        DungeonConstructor con = new DungeonConstructor(world);
		Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
		if(!world.isRemote){
			if(block == ModBlocks.dramixAltar){
				con.setLocationAndAngles(x + 0.5F, y + 1, z + 0.5F, 0.0F, 0.0F);
				if(world.getCollisionBoxes(con, con.getCollisionBoundingBox()).isEmpty()) { 
                    world.spawnEntity(con);
                    if(!player.capabilities.isCreativeMode)stack.shrink(1);
                }
				return EnumActionResult.PASS;
			}
		}
		return EnumActionResult.FAIL;
	}
		
	@Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}