package divinerpg.objects.blocks;

import java.util.Random;

import divinerpg.enums.EnumBlockType;
import divinerpg.objects.items.arcana.ItemArcanaSeeds;
import divinerpg.registry.ModBlocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class BlockModGrass extends BlockMod implements IGrowable {
    protected BlockMod dirt;

    public BlockModGrass(BlockMod dirt, String name, float hardness) {
        super(EnumBlockType.GRASS, name, hardness);
        this.dirt = dirt;
        setTickRandomly(true);
        setHarvestLevel("shovel", 3);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(dirt);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote) {
            if (!world.isAreaLoaded(pos, 3))
                return;
            if (world.getLightFromNeighbors(pos.up()) < 4
                    && world.getBlockState(pos.up()).getLightOpacity(world, pos.up()) > 2) {
                world.setBlockState(pos, dirt.getDefaultState());
            } else {
                for (int l = 0; l < 4; ++l) {
                    BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                    if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !world.isBlockLoaded(blockpos)) {
                        return;
                    }

                    IBlockState iblockstate = world.getBlockState(blockpos.up());
                    IBlockState iblockstate1 = world.getBlockState(blockpos);

                    if (iblockstate1.getBlock() == dirt && world.getLightFromNeighbors(blockpos.up()) >= 4
                            && iblockstate.getLightOpacity(world, pos.up()) <= 2) {
                        world.setBlockState(blockpos, this.getDefaultState());
                    }
                }
            }
        }
    }

    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {

    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
            IPlantable plantable) {

    	if(this == ModBlocks.arcanaGrass && plantable instanceof ItemArcanaSeeds) return true;
        return false;
    }
}