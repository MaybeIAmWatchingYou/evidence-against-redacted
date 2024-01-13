package com.swdteam.network.packets;

import com.swdteam.client.gui.GuiWaypointWriter;
import com.swdteam.common.block.tardis.DataWriterBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.tardis.DataWriterTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.util.ChatUtil;
import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketEjectWaypointCartridge
{
  private String name;
  private BlockPos blockPos;
  
  public PacketEjectWaypointCartridge(String data, BlockPos pos) {
     this.name = data;
     this.blockPos = pos;
  }
  
  public static void encode(PacketEjectWaypointCartridge msg, PacketBuffer buf) {
     buf.func_179255_a(msg.blockPos);
     buf.func_180714_a(msg.name);
  }
  
  public static PacketEjectWaypointCartridge decode(PacketBuffer buf) {
     BlockPos pos = buf.func_179259_c();
     String s = buf.func_150789_c(32767);
    
     return new PacketEjectWaypointCartridge(s, pos);
  }

  
  public static void handle(PacketEjectWaypointCartridge msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity serverPlayerEntity = ((NetworkEvent.Context)ctx.get()).getSender();

            
            ServerWorld world = serverPlayerEntity.getServer().getWorld(DMDimensions.TARDIS);

            
            if (world != null) {
              TileEntity te = world.getTileEntity(msg.blockPos);

              
              if (te != null && te instanceof DataWriterTileEntity) {
                TardisData data = DMTardis.getTardisFromInteriorPos(msg.blockPos);

                
                if (data != null && data.getCurrentLocation() != null) {
                  DataWriterTileEntity writer = (DataWriterTileEntity)te;
                  
                  ItemStack stack = writer.cartridge;
                  
                  if (stack != null && stack.getItem() instanceof com.swdteam.common.item.DataModuleItem && stack.getItem() == DMItems.DATA_MODULE.get() && stack.func_77942_o() && stack.func_77978_p().func_74767_n("written")) {
                    ChatUtil.sendError((PlayerEntity)serverPlayerEntity, "This data madule has alread been used", ChatUtil.MessageType.STATUS_BAR);
                    
                    return;
                  } 
                  
                  if (!msg.name.startsWith("/")) {
                    stack.func_200302_a((ITextComponent)new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Waypoint: " + msg.name));
                    
                    addLocation(stack, msg.name, data, true);
                    
                    stack.func_77978_p().func_74778_a("cart_name", msg.name);
                    
                    writer.markDirty();
                    
                    DataWriterBlock.eject(world.getBlockState(msg.blockPos), (World)world, msg.blockPos, writer);
                  } else if (stack.getItem() == DMItems.DATA_MODULE_GOLD.get()) {
                    String[] args = msg.name.split(" ");
                    
                    if (args.length > 0) {
                      if (args[0].equalsIgnoreCase("/name")) {
                        if (args.length > 1) {
                          String name = msg.name.replaceFirst(args[0] + " ", "");
                          
                          stack.func_200302_a((ITextComponent)new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Waypoint: " + name));
                          
                          sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), "Module name changed");
                        } else {
                          sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), "Name cannot be blank");
                        } 
                      } else if (args[0].equalsIgnoreCase("/add")) {
                        if (args.length > 1) {
                          String name = msg.name.replaceFirst(args[0] + " ", "");
                          
                          addLocation(stack, name, data, false);
                          
                          sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), "Location added to module");
                        } else {
                          sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), "Name cannot be blank");
                        } 
                      } else if (args[0].equalsIgnoreCase("/format")) {
                        stack.func_135074_t();
                        
                        if (!stack.func_77942_o()) {
                          stack.func_77982_d(new CompoundNBT());
                        }
                        
                        stack.func_77978_p().func_82580_o("location");
                        
                        stack.func_77978_p().func_82580_o("cart_name");
                        
                        stack.func_77978_p().func_82580_o("written");
                        
                        stack.func_77978_p().func_74757_a("formatted", true);
                        
                        sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), "Module has been formatted");
                      } else if (args[0].equalsIgnoreCase("/eject")) {
                        writer.markDirty();
                        
                        DataWriterBlock.eject(world.getBlockState(msg.blockPos), (World)world, msg.blockPos, writer);
                        
                        NetworkHandler.sendTo(((NetworkEvent.Context)ctx.get()).getSender(), new PacketOpenGui(msg.blockPos, -1));
                      } else if (args[0].equalsIgnoreCase("/remove")) {
                        if (args.length > 1) {
                          String name = msg.name.replaceFirst(args[0] + " ", "");
                          
                          boolean b = removeLocation(stack, name, data, false);
                          
                          sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), b ? "Location has been removed" : "Unable to find location");
                        } else {
                          sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), "Name cannot be blank");
                        } 
                      } else if (args[0].equalsIgnoreCase("/help") || args[0].equalsIgnoreCase("/?")) {
                        String name = msg.name.replaceFirst(args[0] + " ", "");
                        
                        sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), "/add, /remove, /format, /eject, /name");
                      } else {
                        sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), "Command not found");
                      } 
                      
                      ((NetworkEvent.Context)ctx.get()).getSender().getEntityWorld().playSound((PlayerEntity)null, msg.blockPos.getX(), msg.blockPos.getY(), msg.blockPos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_MODULE_WRITE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                    } 
                  } else {
                    sendResponse(((NetworkEvent.Context)ctx.get()).getSender(), "Module does not support this feature");
                  } 
                } 
              } 
            } 
          } else {
            GuiWaypointWriter.COMMAND_RESPONSE = msg.name;
          } 
        });
    
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
  
  private static void sendResponse(ServerPlayerEntity player, String msg) {
     NetworkHandler.sendTo(player, new PacketEjectWaypointCartridge(msg, BlockPos.field_177992_a));
  }

  
  private static boolean removeLocation(ItemStack stack, String name, TardisData data, boolean overwrite) {
     if (!stack.func_77942_o()) {
       stack.func_77982_d(new CompoundNBT());
       return false;
    } 
    
     if (stack.func_77978_p().func_74764_b("location")) {
       ListNBT tags = stack.func_77978_p().func_150295_c("location", 10);
       boolean found = false;
       for (int i = 0; i < tags.size(); i++) {
         CompoundNBT tag1 = tags.func_150305_b(i);
         if (tag1.func_74779_i("loc_name").equalsIgnoreCase(name)) {
           tags.remove(i);
           found = true;
        } 
      } 
       return found;
    } 
     return false;
  }

  
  private static void addLocation(ItemStack stack, String name, TardisData data, boolean overwrite) {
     ListNBT list = null;
     CompoundNBT tag = new CompoundNBT();
    
     if (!stack.func_77942_o()) {
       stack.func_77982_d(new CompoundNBT());
    }
    
     stack.func_77978_p().func_74757_a("written", true);
    
     tag.func_74778_a("loc_name", name);
     tag.func_74778_a("location", data.getCurrentLocation().getDimension());
     tag.putInt("pos_x", data.getCurrentLocation().getBlockPosition().getX());
     tag.putInt("pos_y", data.getCurrentLocation().getBlockPosition().getY());
     tag.putInt("pos_z", data.getCurrentLocation().getBlockPosition().getZ());
     tag.func_74776_a("facing", data.getCurrentLocation().getFacing());
    
     if (stack.func_77978_p().func_74764_b("location")) {
       list = stack.func_77978_p().func_150295_c("location", 10);
    } else {
       list = new ListNBT();
    } 
    
     if (overwrite) {
       list = new ListNBT();
    }
    
     list.add(tag);
    
     stack.func_77978_p().func_218657_a("location", (INBT)list);
  }
}


