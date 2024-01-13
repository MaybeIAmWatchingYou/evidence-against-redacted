package com.swdteam.common.block.tardis;

import com.swdteam.common.block.TileEntityBaseBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.TeleportUtil;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TardisDoorBlock
  extends TileEntityBaseBlock {
  public TardisDoorBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }

  
  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (handIn == Hand.MAIN_HAND &&
       worldIn.getDimensionKey().equals(DMDimensions.TARDIS) &&
       !worldIn.isRemote) {
       TardisData data = DMTardis.getTardis(DMTardis.getIDForXZ(pos.getX(), pos.getZ()));
       if (data != null && !DMFlightMode.isInFlight(data.getGlobalID())) {
         ServerWorld serverWorld = player.getServer().getWorld(data.getCurrentLocation().dimensionWorldKey());
         TileEntity te = serverWorld.getTileEntity(data.getCurrentLocation().getBlockPosition());
         if (te != null && te instanceof TardisTileEntity) {
           TardisTileEntity tard = (TardisTileEntity)te;
           BlockPos tardisPosition = data.getCurrentLocation().getBlockPosition();
          
           TardisActionList.addForceField(serverWorld, data.getCurrentLocation().getBlockPosition());
          
           Vector3d look = Vector3d.fromPitchYaw(new Vector2f(45.0F, tard.rotation + 180.0F));
           float distance = 2.0F;
           double dx = tardisPosition.getX() + look.x * distance;
           double dy = (tardisPosition.getY() > 0) ? tardisPosition.getY() : 128.0D;
           double dz = tardisPosition.getZ() + look.z * distance;
           TeleportUtil.teleportPlayer((Entity)player, serverWorld.getDimensionKey(), (IPosition)new Vector3d(dx + 0.5D, dy, dz + 0.5D), tard.rotation + 180.0F);
        } 
      } 
    } 

    
     return ActionResultType.CONSUME;
  }


  
  public VoxelShape func_220071_b(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
     return VoxelShapes.func_197880_a();
  }

  
  public VoxelShape getRenderVoxelShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
     return VoxelShapes.func_197868_b();
  }

  
  public BlockRenderType func_149645_b(BlockState state) {
     return BlockRenderType.MODEL;
  }
  
  public boolean func_196266_a(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
     return true;
  }
}


