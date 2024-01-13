package com.swdteam.common.block;
import com.swdteam.common.container.RoundelContainer;
import com.swdteam.common.init.DMTranslationKeys;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
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
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RoundelBuilderBlock extends AbstractRotateableWaterLoggableBlock {
   public static final VoxelShape SHAPE_N = VoxelShapes.func_197873_a(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
   public static final VoxelShape SHAPE_E = VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
   public static final VoxelShape SHAPE_S = VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
   public static final VoxelShape SHAPE_W = VoxelShapes.func_197873_a(1.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
  
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.field_208163_P;
  
  public RoundelBuilderBlock(AbstractBlock.Properties properties) {
     super(properties);
     func_180632_j((BlockState)getDefaultState().func_206870_a((Property)HALF, (Comparable)DoubleBlockHalf.LOWER));
  }
  
  public BlockState func_196258_a(BlockItemUseContext context) {
     return (BlockState)super.func_196258_a(context).func_206870_a((Property)HALF, (Comparable)DoubleBlockHalf.LOWER);
  }
  
  public ActionResultType func_225533_a_(BlockState blockState, World world, BlockPos blockPos, PlayerEntity entity, Hand hand, BlockRayTraceResult rayTrace) {
     if (world.isRemote) return ActionResultType.SUCCESS;
    
     entity.func_213829_a(blockState.func_215699_b(world, blockPos));
     return ActionResultType.CONSUME;
  }
  
  @Nullable
  public INamedContainerProvider func_220052_b(BlockState blockState, World world, BlockPos blockPos) {
     return (INamedContainerProvider)new SimpleNamedContainerProvider((p_220283_2_, p_220283_3_, p_220283_4_) -> new RoundelContainer(p_220283_2_, p_220283_3_, IWorldPosCallable.func_221488_a(world, blockPos)), (ITextComponent)DMTranslationKeys.GUI_ROUNDEL_BUILDER_NAME);
  }



  
  public VoxelShape func_220053_a(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
     if (state.get((Property)HALF) == DoubleBlockHalf.UPPER) {
       Direction facing = (Direction)state.get((Property)FACING);
       switch (facing) { default:
           return SHAPE_N;
         case EAST: return SHAPE_E;
         case SOUTH: return SHAPE_S;
         case WEST: break; }  return SHAPE_W;
    } 
    
     return VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 0.9999D, 1.0D, 0.9999D);
  }
  
  public boolean func_220074_n(BlockState blockState) {
     return true;
  }
  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     super.func_206840_a(builder);
     builder.func_206894_a(new Property[] { (Property)HALF });
  }
  
  public boolean func_196266_a(BlockState state, IBlockReader reader, BlockPos pos, PathType type) {
     return false;
  }

  
  public boolean func_196260_a(BlockState state, IWorldReader worldIn, BlockPos pos) {
     BlockPos blockpos = pos.func_177984_a();
     BlockState blockstate = worldIn.getBlockState(blockpos);
     return !blockstate.func_224755_d((IBlockReader)worldIn, blockpos, Direction.DOWN);
  }
  
  public void func_180633_a(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
     FluidState fluidstate = worldIn.func_204610_c(pos.func_177984_a());
     worldIn.setBlockState(pos.func_177984_a(), (BlockState)((BlockState)state.func_206870_a((Property)WATERLOGGED, Boolean.valueOf((fluidstate.func_206886_c() == Fluids.field_204546_a)))).func_206870_a((Property)HALF, (Comparable)DoubleBlockHalf.UPPER), 3);
  }

  
  public void func_176208_a(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
     if (!worldIn.isRemote) {
       if (state.get((Property)HALF) == DoubleBlockHalf.LOWER) {
         worldIn.func_175655_b(pos.func_177984_a(), false);
      } else {
         worldIn.func_175655_b(pos.func_177977_b(), false);
      } 
    }
     super.func_176208_a(worldIn, pos, state, player);
  }


  
  public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
     if (!world.isRemote) {
       if (state.get((Property)HALF) == DoubleBlockHalf.LOWER) {
         world.func_175655_b(pos.func_177984_a(), false);
      } else {
         world.func_175655_b(pos.func_177977_b(), false);
      } 
    }
     super.onBlockExploded(state, world, pos, explosion);
  }

  
  public PushReaction func_149656_h(BlockState p_149656_1_) {
     return PushReaction.BLOCK;
  }
}


