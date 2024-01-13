package com.swdteam.common.block.tardis;

import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.TeleportUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;



public class FlightPanelBlock
  extends AbstractRotateableWaterLoggableBlock
{
   protected static final VoxelShape SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);

  public FlightPanelBlock(AbstractBlock.Properties properties) {
     super(properties);
  }



  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
     if (handIn == Hand.MAIN_HAND &&
       !worldIn.isRemote) {
       TardisData data = DMTardis.getTardisFromInteriorPos(pos);

       if (data != null && !data.isInFlight() && data.getCurrentLocation() != null && !DMFlightMode.isInFlight(data.getGlobalID())) {
         ServerWorld world = worldIn.getServer().getWorld(data.getCurrentLocation().dimensionWorldKey());
         world.setBlockState(data.getCurrentLocation().getBlockPosition(), Blocks.AIR.getDefaultState());
         DMFlightMode.addFlight(player, new DMFlightMode.FlightModeData(data.getGlobalID(), player.getPosX(), player.getPosY(), player.getPosZ()));

         TeleportUtil.teleportPlayer((Entity)player, data.getCurrentLocation().dimensionWorldKey(), (IPosition)new Vector3d(data.getCurrentLocation().getPosition().func_82615_a(), data.getCurrentLocation().getPosition().func_82617_b(), data.getCurrentLocation().getPosition().func_82616_c()), 0.0F);
         player.abilities.allowEdit = false;

         if (data.getFuel() > 0.0D) {
           player.abilities.isFlying = true;
           player.abilities.allowFlying = true;
        } else {
           player.abilities.isFlying = false;
           player.abilities.allowFlying = false;
        }

         player.sendPlayerAbilities();
      }
       else if (data.isInFlight()) {
         ChatUtil.sendError(player, "TARDIS is currently in flight", ChatUtil.MessageType.CHAT);
      }
    }


     return ActionResultType.CONSUME;
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }


  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }

  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }
}


