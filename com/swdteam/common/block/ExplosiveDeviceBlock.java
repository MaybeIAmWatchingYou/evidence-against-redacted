package com.swdteam.common.block;

import com.swdteam.common.tileentity.ExplosiveDeviceTileEntity;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.HorizontalFaceBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ExplosiveDeviceBlock
  extends HorizontalFaceBlock
  implements IDMTile, IWaterLoggable
{
   public static final DirectionProperty FACING = HorizontalBlock.field_185512_D;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  
   public static final VoxelShape SHAPE_WALL_N = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.1875D, 0.0D, 0.75D, 0.8125D, 1.0D, 1.0D), new VoxelShape[0]);
   public static final VoxelShape SHAPE_WALL_E = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.1875D, 0.25D, 1.0D, 0.8125D), new VoxelShape[0]);
   public static final VoxelShape SHAPE_WALL_S = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.1875D, 0.0D, 0.0D, 0.8125D, 1.0D, 0.25D), new VoxelShape[0]);
   public static final VoxelShape SHAPE_WALL_W = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.75D, 0.0D, 0.1875D, 1.0D, 1.0D, 0.8125D), new VoxelShape[0]);
   public static final VoxelShape SHAPE_FLOOR = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.1875D, 1.0D, 0.25D, 0.8125D), new VoxelShape[0]);
   public static final VoxelShape SHAPE_FLOOR_ROTATED = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.1875D, 0.0D, 0.0D, 0.8125D, 0.25D, 1.0D), new VoxelShape[0]);
   public static final VoxelShape SHAPE_CEILING = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.75D, 0.1875D, 1.0D, 1.0D, 0.8125D), new VoxelShape[0]);
   public static final VoxelShape SHAPE_CEILING_ROTATED = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.1875D, 0.75D, 0.0D, 0.8125D, 1.0D, 1.0D), new VoxelShape[0]);
  
  private Supplier<TileEntity> tile;
  
  public ExplosiveDeviceBlock(AbstractBlock.Properties properties, Supplier<TileEntity> tileEntitySupplier) {
     super(properties);
     func_180632_j((BlockState)((BlockState)((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a((Property)FACING, (Comparable)Direction.NORTH)).func_206870_a((Property)field_196366_M, (Comparable)AttachFace.WALL)).func_206870_a((Property)WATERLOGGED, Boolean.valueOf(false)));
     this.tile = tileEntitySupplier;
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     AttachFace face = (AttachFace)state.get((Property)field_196366_M);
     Direction facing = (Direction)state.get((Property)FACING);
     Boolean rotated = Boolean.valueOf((facing == Direction.NORTH || facing == Direction.SOUTH));
     switch (face) {
      default:
         if (rotated.booleanValue()) return SHAPE_FLOOR_ROTATED; 
         return SHAPE_FLOOR;
      
      case CEILING:
         if (rotated.booleanValue()) return SHAPE_CEILING_ROTATED; 
         return SHAPE_CEILING;
      case WALL:
        break;
     }  switch (facing) { default:
         return SHAPE_WALL_N;
       case CEILING: return SHAPE_WALL_E;
       case WALL: return SHAPE_WALL_S;
       case null: break; }  return SHAPE_WALL_W;
  }




  
  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return VoxelShapes.func_197880_a();
  }
  
  public boolean func_196260_a(BlockState state, IWorldReader world, BlockPos pos) {
     return (!state.func_185897_m() && func_220185_b(world, pos, func_196365_i(state).getOpposite()));
  }
  
  public BlockState func_196271_a(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
     if (((Boolean)stateIn.get((Property)WATERLOGGED)).booleanValue()) {
       worldIn.func_205219_F_().func_205360_a(currentPos, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a((IWorldReader)worldIn));
    }
    
     if (facing.getOpposite() == stateIn.get((Property)FACING) && !stateIn.func_196955_c((IWorldReader)worldIn, currentPos)) {
       return Blocks.AIR.getDefaultState();
    }
     return super.func_196271_a(stateIn, facing, facingState, worldIn, currentPos, facingPos);
  }

  
  @Nullable
  public BlockState func_196258_a(BlockItemUseContext context) {
     BlockPos blockpos = context.func_195995_a();
     FluidState fluidstate = context.func_195991_k().func_204610_c(blockpos);
     if (super.func_196258_a(context) == null) return null; 
     return (BlockState)super.func_196258_a(context).func_206870_a((Property)WATERLOGGED, Boolean.valueOf((fluidstate.func_206886_c() == Fluids.field_204546_a)));
  }
  
  public FluidState func_204507_t(BlockState state) {
     return ((Boolean)state.get((Property)WATERLOGGED)).booleanValue() ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(state);
  }
  
  public BlockState func_185499_a(BlockState state, Rotation rot) {
     return (BlockState)state.func_206870_a((Property)FACING, (Comparable)rot.func_185831_a((Direction)state.get((Property)FACING)));
  }
  
  public BlockState func_185471_a(BlockState state, Mirror mirrorIn) {
     return state.func_185907_a(mirrorIn.func_185800_a((Direction)state.get((Property)FACING)));
  }
  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     builder.func_206894_a(new Property[] { (Property)FACING, (Property)field_196366_M, (Property)WATERLOGGED });
  }

  
  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }

  
  public boolean hasTileEntity(BlockState state) {
     return true;
  }

  
  public Supplier<TileEntity> getTile() {
     return this.tile;
  }

  
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
     return this.tile.get();
  }

  
  public void func_180652_a(World worldIn, BlockPos pos, Explosion explosionIn) {
     if (!worldIn.isRemote) {
       worldIn.createExplosion((Entity)explosionIn.func_94613_c(), pos.getX(), pos.getY(), pos.getZ(), 10.0F, Explosion.Mode.DESTROY);
    }
     super.func_180652_a(worldIn, pos, explosionIn);
  }


  
  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     igniteMe(worldIn, pos, (Entity)player);
     return ActionResultType.CONSUME;
  }
  
  public void igniteMe(World worldIn, BlockPos pos, Entity ent) {
     TileEntity e = worldIn.getTileEntity(pos);
     ent.func_184185_a(SoundEvents.field_187572_ar, 1.0F, 1.0F);
     if (e instanceof ExplosiveDeviceTileEntity) {
       ExplosiveDeviceTileEntity te = (ExplosiveDeviceTileEntity)e;
       te.activator = ent;
       te.lit = true;
    } 
  }

  
  public boolean func_149659_a(Explosion p_149659_1_) {
     return false;
  }
}


