package com.swdteam.common.block;
import com.swdteam.common.container.EngineeringTableContainer;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.state.Property;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class EngineeringTableBlock extends AbstractRotateableWaterLoggableBlock {
   public static final VoxelShape SHAPE_N = VoxelShapes.func_197872_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), VoxelShapes.func_197873_a(0.0D, 0.5D, 0.875D, 1.0D, 1.0D, 1.0D));
   public static final VoxelShape SHAPE_E = VoxelShapes.func_197872_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), VoxelShapes.func_197873_a(0.0D, 0.5D, 0.0D, 0.125D, 1.0D, 1.0D));
   public static final VoxelShape SHAPE_S = VoxelShapes.func_197872_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), VoxelShapes.func_197873_a(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.125D));
   public static final VoxelShape SHAPE_W = VoxelShapes.func_197872_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), VoxelShapes.func_197873_a(0.875D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D));

  public EngineeringTableBlock(AbstractBlock.Properties properties) {
     super(properties);
  }

  public ActionResultType func_225533_a_(BlockState blockState, World world, BlockPos blockPos, PlayerEntity entity, Hand hand, BlockRayTraceResult rayTrace) {
     if (world.isRemote) {
       return ActionResultType.SUCCESS;
    }
     entity.func_213829_a(blockState.func_215699_b(world, blockPos));
     return ActionResultType.CONSUME;
  }


  @Nullable
  public INamedContainerProvider func_220052_b(BlockState blockState, World world, BlockPos blockPos) {
     return (INamedContainerProvider)new SimpleNamedContainerProvider((p_220283_2_, p_220283_3_, p_220283_4_) -> new EngineeringTableContainer(p_220283_2_, p_220283_3_, IWorldPosCallable.func_221488_a(world, blockPos)), (ITextComponent)DMTranslationKeys.GUI_ENGINEERING_TABLE_NAME);
  }




  public VoxelShape func_220053_a(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
     Direction facing = (Direction)state.get((Property)FACING);
     switch (facing) { default:
         return SHAPE_N;
       case EAST: return SHAPE_E;
       case SOUTH: return SHAPE_S;
       case WEST: break; }  return SHAPE_W;
  }
}


