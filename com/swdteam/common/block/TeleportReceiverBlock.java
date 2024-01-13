package com.swdteam.common.block;

import com.swdteam.common.tardis.Location;
import com.swdteam.common.tileentity.TeleportPadTileEntity;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TeleportReceiverBlock
  extends Block
{
  public TeleportReceiverBlock(AbstractBlock.Properties properties) {
     super(properties);
  }


  public void func_180633_a(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
     if (!world.isRemote &&
       entity instanceof PlayerEntity) {
       PlayerEntity player = (PlayerEntity)entity;

       if (TeleportPadTileEntity.getPads().containsKey(player)) {
         Location location = (Location)TeleportPadTileEntity.getPads().get(player);
         ServerWorld level = player.getServer().getWorld(location.dimensionWorldKey());

         if (level != null) {
           TileEntity te = level.getTileEntity(location.getBlockPosition());

           if (te != null && te instanceof TeleportPadTileEntity) {
             TeleportPadTileEntity pad = (TeleportPadTileEntity)te;
             pad.setExitDimension(world.getDimensionKey().getRegistryLocation());
             pad.setExitPosition(pos.func_177984_a());
             pad.sendUpdates();
             ChatUtil.sendCompletedMsg(player, "Link established", ChatUtil.MessageType.CHAT);
             TeleportPadTileEntity.getPads().remove(player);
          }
        }
      }
    }

     super.func_180633_a(world, pos, state, entity, stack);
  }
}


