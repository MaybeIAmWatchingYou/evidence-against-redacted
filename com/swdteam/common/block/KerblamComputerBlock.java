package com.swdteam.common.block;

import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import com.swdteam.network.packets.PacketXPSync;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.state.Property;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class KerblamComputerBlock
  extends AbstractRotateableWaterLoggableBlock {
   protected static final VoxelShape SHAPE_NORTH = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.0D, 0.25D, 0.5D, 1.0D, 1.0D, 1.0D), VoxelShapes.func_197873_a(0.375D, 0.125D, 0.625D, 0.625D, 0.25D, 0.875D) });
   protected static final VoxelShape SHAPE_EAST = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.0D, 0.25D, 0.0D, 0.5D, 1.0D, 1.0D), VoxelShapes.func_197873_a(0.125D, 0.125D, 0.375D, 0.375D, 0.25D, 0.625D) });
   protected static final VoxelShape SHAPE_SOUTH = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 0.5D), VoxelShapes.func_197873_a(0.375D, 0.125D, 0.125D, 0.625D, 0.25D, 0.375D) });
   protected static final VoxelShape SHAPE_WEST = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.5D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D), VoxelShapes.func_197873_a(0.625D, 0.125D, 0.375D, 0.875D, 0.25D, 0.625D) });

  public KerblamComputerBlock(AbstractBlock.Properties properties) {
     super(properties);
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return getShape((Direction)state.get((Property)FACING));
  }

  private VoxelShape getShape(Direction direction) {
     switch (direction) { default:
         return SHAPE_NORTH;
       case EAST: return SHAPE_EAST;
       case SOUTH: return SHAPE_SOUTH;
       case WEST: break; }  return SHAPE_WEST;
  }



  public BlockRenderType func_149645_b(BlockState state) {
     return BlockRenderType.MODEL;
  }


  public ActionResultType func_225533_a_(BlockState state, World level, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult result) {
     if (!level.isRemote) {
       NetworkHandler.sendTo((ServerPlayerEntity)entity, new PacketXPSync(entity.experienceTotal, false));
       NetworkHandler.sendTo((ServerPlayerEntity)entity, new PacketOpenGui(pos, 8));
    }
     return ActionResultType.CONSUME;
  }
}


