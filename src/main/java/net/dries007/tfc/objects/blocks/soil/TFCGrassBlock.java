package net.dries007.tfc.objects.blocks.soil;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import net.dries007.tfc.util.tags.TFCBlockTags;

@ParametersAreNonnullByDefault
public class TFCGrassBlock extends Block
{
    // Used for connected textures only.
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");

    @Nullable
    private static BooleanProperty getPropertyForFace(Direction direction)
    {
        switch (direction)
        {
            case NORTH:
                return NORTH;
            case EAST:
                return EAST;
            case WEST:
                return WEST;
            case SOUTH:
                return SOUTH;
            default:
                return null;
        }
    }

    public TFCGrassBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean isSolid(BlockState state)
    {
        // this method defaults to using the renderlayer to check whether it's solid.
        // the block is solid for all intents and purposes, but returns false because we changed the render layer
        return true;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        // change the render layer so that grass edges get rendered on top of each other.
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState getExtendedState(BlockState state, IBlockReader world, BlockPos pos)
    {
        pos = pos.down();
        return state.with(NORTH, TFCBlockTags.GRASS.contains(world.getBlockState(pos.offset(Direction.NORTH)).getBlock()))
            .with(EAST, TFCBlockTags.GRASS.contains(world.getBlockState(pos.offset(Direction.EAST)).getBlock()))
            .with(WEST, TFCBlockTags.GRASS.contains(world.getBlockState(pos.offset(Direction.WEST)).getBlock()))
            .with(SOUTH, TFCBlockTags.GRASS.contains(world.getBlockState(pos.offset(Direction.SOUTH)).getBlock()));
    }
}
