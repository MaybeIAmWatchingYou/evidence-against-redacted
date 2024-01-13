package com.swdteam.common.item;

import com.swdteam.common.block.tardis.PoliceBoxDoorBlock;
import com.swdteam.common.block.tardis.RoundelDoorBlock;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.common.tileentity.tardis.DoubleDoorsTileEntity;
import com.swdteam.common.tileentity.tardis.RoundelDoorTileEntity;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.ItemUtils;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class TardisKeyItem
  extends Item
{
  String tardisLocation;
  
  public TardisKeyItem(Item.Properties properties, String tardisLocation) {
     super(properties.func_200917_a(1));
     this.tardisLocation = tardisLocation;
  }




  
  public ActionResultType func_195939_a(ItemUseContext context) {
     if (!(context.func_195991_k()).isRemote) {
       World world = context.func_195991_k();
       BlockPos pos = context.func_195995_a();
       BlockState blockState = world.getBlockState(pos);
      
       if (context.func_195996_i().func_77942_o() && context.func_195996_i().func_77978_p().func_74764_b(DMNBTKeys.LINKED_ID)) {
         if (blockState.getBlock() == DMBlocks.TARDIS.get() || blockState.getBlock() instanceof PoliceBoxDoorBlock || blockState.getBlock() instanceof RoundelDoorBlock) {
           TileEntity te = world.getTileEntity(pos);
           if (blockState.getBlock() instanceof PoliceBoxDoorBlock && blockState.get((Property)PoliceBoxDoorBlock.HALF) == DoubleBlockHalf.UPPER) {
             te = world.getTileEntity(pos.func_177977_b());
          }
           if (te instanceof TardisTileEntity) {
             TardisData data = DMTardis.getTardis(((TardisTileEntity)te).globalID);
             if (data.getGlobalID() == context.func_195996_i().func_77978_p().getInt(DMNBTKeys.LINKED_ID)) {
               data.setLocked(!data.isLocked());
               data.save();
              
               if (data.isLocked()) {
                 ((TardisTileEntity)te).closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);
              }
              
               world.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_LOCK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
               ChatUtil.sendCompletedMsg(context.func_195999_j(), "You have " + (data.isLocked() ? "locked" : "unlocked") + " your TARDIS!", ChatUtil.MessageType.STATUS_BAR);
            } else {
               ChatUtil.sendError(context.func_195999_j(), "This isn't the key for your TARDIS!", ChatUtil.MessageType.STATUS_BAR);
            } 
          } 
          
           if (te instanceof DoubleDoorsTileEntity || te instanceof RoundelDoorTileEntity) {
             TardisData data = DMTardis.getTardisFromInteriorPos(pos);
            
             if (data != null && data.getGlobalID() == context.func_195996_i().func_77978_p().getInt(DMNBTKeys.LINKED_ID)) {
               if (te instanceof RoundelDoorTileEntity && data != null) ((RoundelDoorTileEntity)te).setMain(true); 
               if (te instanceof DoubleDoorsTileEntity && data != null) ((DoubleDoorsTileEntity)te).setMain(true);
              
               data.setLocked(!data.isLocked());
               data.save();
              
               if (data.isLocked()) {
                 if (te instanceof DoubleDoorsTileEntity) ((DoubleDoorsTileEntity)te).setOpen(TardisDoor.BOTH, false); 
                 if (te instanceof RoundelDoorTileEntity) {
                   RoundelDoorTileEntity otherDoor1, otherDoor2, door = (RoundelDoorTileEntity)te;
                   door.setOpen(false);
                   int doorPart = ((Integer)blockState.get((Property)RoundelDoorBlock.DOOR_PART)).intValue();
                  
                   switch (doorPart) {
                    
                    default:
                       otherDoor1 = (RoundelDoorTileEntity)world.getTileEntity(pos.func_177984_a());
                       otherDoor2 = (RoundelDoorTileEntity)world.getTileEntity(pos.func_177984_a().func_177984_a());
                       if (otherDoor1 != null) otherDoor1.setOpen(false); 
                       if (otherDoor2 != null) otherDoor2.setOpen(false);
                      
                      break;
                    case 1:
                       otherDoor1 = (RoundelDoorTileEntity)world.getTileEntity(pos.func_177984_a());
                       otherDoor2 = (RoundelDoorTileEntity)world.getTileEntity(pos.func_177977_b());
                       if (otherDoor1 != null) otherDoor1.setOpen(false); 
                       if (otherDoor2 != null) otherDoor2.setOpen(false);
                      
                      break;
                    case 2:
                       otherDoor1 = (RoundelDoorTileEntity)world.getTileEntity(pos.func_177977_b());
                       otherDoor2 = (RoundelDoorTileEntity)world.getTileEntity(pos.func_177977_b().func_177977_b());
                       if (otherDoor1 != null) otherDoor1.setOpen(false); 
                       if (otherDoor2 != null) otherDoor2.setOpen(false);
                      
                      break;
                  } 

                
                } 
              } 
               world.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_LOCK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
               ChatUtil.sendCompletedMsg(context.func_195999_j(), "You have " + (data.isLocked() ? "locked" : "unlocked") + " your TARDIS!", ChatUtil.MessageType.STATUS_BAR);
            } else {
               ChatUtil.sendError(context.func_195999_j(), "This isn't the key for your TARDIS!", ChatUtil.MessageType.STATUS_BAR);
            } 
          } 
          
           return ActionResultType.CONSUME;
        }
      
       } else if (context.func_195999_j().func_225608_bj_() && 
         blockState.getBlock() == DMBlocks.TARDIS.get()) {
         TileEntity te = world.getTileEntity(pos);
         if (te instanceof TardisTileEntity) {
           TardisData data = DMTardis.getTardis(((TardisTileEntity)te).globalID);
           if (data.hasPermission(context.func_195999_j(), TardisData.PermissionType.DEFAULT)) {
             CompoundNBT tag = new CompoundNBT();
             tag.putInt(DMNBTKeys.LINKED_ID, data.getGlobalID());
             context.func_195996_i().func_77982_d(tag);
             ChatUtil.sendCompletedMsg(context.func_195999_j(), "Linked key to TARDIS " + data.getGlobalID(), ChatUtil.MessageType.CHAT);
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


