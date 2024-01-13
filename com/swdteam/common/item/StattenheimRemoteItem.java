package com.swdteam.common.item;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.ItemUtils;
import com.swdteam.util.WorldUtils;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StattenheimRemoteItem
  extends Item {
  public StattenheimRemoteItem(Item.Properties properties) {
     super(properties);
  }


  
  public ActionResultType func_195939_a(ItemUseContext context) {
     if (!(context.func_195991_k()).isRemote) {
      
       World world = context.func_195991_k();
       BlockPos pos = context.func_195995_a();
       BlockState blockState = world.getBlockState(pos);
      
       if (context.func_195996_i().func_77942_o() && context.func_195996_i().func_77978_p().func_74764_b(DMNBTKeys.LINKED_ID)) {
         BlockPos posUp = context.func_195995_a().func_177984_a();
        
         boolean canContinue = false;
        
         if (WorldUtils.canPlace(world, pos, false)) {
           posUp = pos;
           canContinue = true;
         } else if (world.func_175623_d(posUp)) {
           canContinue = true;
        } 
        
         if (!world.getBlockState(posUp).func_196953_a((BlockItemUseContext)new DirectionalPlaceContext(world, posUp, Direction.NORTH, context.func_195996_i(), Direction.NORTH))) {
           return ActionResultType.CONSUME;
        }
        
         int tardisID = context.func_195996_i().func_77978_p().getInt(DMNBTKeys.LINKED_ID);
         TardisData data = DMTardis.getTardis(tardisID);
         MinecraftServer server = context.func_195999_j().getServer();
         ServerWorld tardisDim = server.getWorld(DMDimensions.TARDIS);
        
         context.func_195999_j().func_184811_cZ().func_185145_a(this, 400);
         context.func_195996_i().func_222118_a(1, (LivingEntity)context.func_195999_j(), player -> player.func_213334_d(context.func_221531_n()));


        
         if (tardisDim.func_195588_v(data.getInteriorSpawnPosition().toBlockPos())) {
           tardisDim.playSound(null, data.getInteriorSpawnPosition().func_82615_a(), data.getInteriorSpawnPosition().func_82617_b(), data.getInteriorSpawnPosition().func_82616_c(), (SoundEvent)DMSoundEvents.TARDIS_REMAT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
        
         if (!data.isInFlight()) {
           world.playSound(null, pos, (SoundEvent)DMSoundEvents.ENTITY_STATTENHEIM_REMOTE_SUMMON.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
           BlockPos currentPos = data.getCurrentLocation().getBlockPosition();
           ServerWorld serverWorld = server.getWorld(data.getCurrentLocation().dimensionWorldKey());
           TileEntity tileEntity = serverWorld.getTileEntity(currentPos);
           if (tileEntity instanceof TardisTileEntity) {
             if (TardisActionList.doAnimation(serverWorld, currentPos)) {
               ((TardisTileEntity)tileEntity).setState(TardisState.DEMAT);
            } else {
               serverWorld.setBlockState(currentPos, Blocks.AIR.getDefaultState());
            } 
          }
        } 
         TardisFlightPool.completeFlight(context.func_195999_j().getServer(), data);
        
         world.setBlockState(posUp, (BlockState)((Block)DMBlocks.TARDIS.get()).getDefaultState().func_206870_a((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf(world.getBlockState(posUp).getBlock() instanceof net.minecraft.block.FlowingFluidBlock)));
         data.setPreviousLocation(data.getCurrentLocation());
         data.setCurrentLocation(posUp, world.getDimensionKey());
        
         TileEntity te = world.getTileEntity(posUp);
         if (te instanceof TardisTileEntity) {
           TardisTileEntity tardis = (TardisTileEntity)te;
           tardis.globalID = tardisID;
           tardis.closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);
           tardis.rotation = context.func_195999_j().getRotationYawHead();
           tardis.setState(TardisState.REMAT);
          
           data.getCurrentLocation().setFacing(tardis.rotation);
        } 
        
         data.save();
      }
       else if (blockState.getBlock() == DMBlocks.TARDIS.get() && 
         context.func_195999_j() != null) {
         TileEntity te = world.getTileEntity(pos);
         if (te instanceof TardisTileEntity) {
           TardisData data = DMTardis.getTardis(((TardisTileEntity)te).globalID);
           if (data.hasPermission(context.func_195999_j(), TardisData.PermissionType.DEFAULT)) {
             world.playSound(null, pos, (SoundEvent)DMSoundEvents.ENTITY_STATTENHEIM_REMOTE_SYNC.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
             CompoundNBT tag = context.func_195996_i().func_196082_o().func_74737_b();
             tag.putInt(DMNBTKeys.LINKED_ID, data.getGlobalID());
             context.func_195996_i().func_77982_d(tag);
             ChatUtil.sendCompletedMsg(context.func_195999_j(), "Remote synced with TARDIS: " + data.getGlobalID(), ChatUtil.MessageType.CHAT);
          } else {
             data.noPermission(context.func_195999_j());
          } 
        } 
      } 
    } 

    
     return super.func_195939_a(context);
  }

  
  @OnlyIn(Dist.CLIENT)
  public void func_77624_a(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
     super.func_77624_a(stack, worldIn, tooltip, flagIn);
     if ((Minecraft.func_71410_x()).field_71474_y.field_82882_x && stack.func_77978_p() != null && stack.func_77978_p().func_74764_b(DMNBTKeys.LINKED_ID))
       ItemUtils.addText(tooltip, "ID: " + stack.func_77978_p().getInt(DMNBTKeys.LINKED_ID), TextFormatting.DARK_GRAY); 
  }
}


