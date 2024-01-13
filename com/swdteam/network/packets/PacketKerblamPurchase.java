package com.swdteam.network.packets;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMKerblamStock;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.kerblam.KerblamItem;
import com.swdteam.common.kerblam.KerblamPurchase;
import com.swdteam.main.DalekMod;
import com.swdteam.network.NetworkHandler;
import com.swdteam.util.ChatUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketKerblamPurchase
{
  private Map<String, Integer> data;
  
  public PacketKerblamPurchase(Map<String, Integer> data) {
     this.data = data;
  }
  
  public static void encode(PacketKerblamPurchase msg, PacketBuffer buf) {
    try {
       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       ObjectOutputStream oos = new ObjectOutputStream(bos);
       oos.writeObject(msg.data);
       oos.close();
      
       buf.func_179250_a(bos.toByteArray());
     } catch (IOException e) {
       e.printStackTrace();
    } 
  }


  
  public static PacketKerblamPurchase decode(PacketBuffer buf) {
     Map<String, Integer> kerblamData = null;
    
     if (buf.readableBytes() > 0) {
       ByteArrayInputStream ins = new ByteArrayInputStream(buf.func_179251_a());
      try {
         ObjectInputStream ois = new ObjectInputStream(ins);
         Object o = ois.readObject();
         if (o != null && o instanceof Map) kerblamData = (Map<String, Integer>)o; 
       } catch (IOException|ClassNotFoundException e) {
         e.printStackTrace();
      } 
    } 
    
     return new PacketKerblamPurchase(kerblamData);
  }
  
  public static void handle(PacketKerblamPurchase msg, Supplier<NetworkEvent.Context> ctx) {
     if (((NetworkEvent.Context)ctx.get()).getDirection() == NetworkDirection.PLAY_TO_SERVER) {
       ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ServerPlayerEntity player = ((NetworkEvent.Context)ctx.get()).getSender();
            
            if (msg.data.size() <= 0) {
              ChatUtil.sendError((PlayerEntity)player, "Purchase cannot be blank", ChatUtil.MessageType.CHAT);
              
              return;
            } 
            
            int total = 0;
            
            for (Map.Entry<String, Integer> entry : msg.data.entrySet()) {
              int amount = ((Integer)entry.getValue()).intValue();
              
              ResourceLocation key = new ResourceLocation(entry.getKey());
              
              KerblamItem item = (KerblamItem)DMKerblamStock.getItems().get(key);
              
              if (item != null) {
                total += item.getPrice() * amount;
                
                continue;
              } 
              
              ChatUtil.sendError((PlayerEntity)player, (IFormattableTextComponent)DMTranslationKeys.INVALID_ITEM, ChatUtil.MessageType.CHAT);
              
              return;
            } 
            
            if (total <= player.experienceTotal || player.isCreative()) {
              KerblamPurchase pur = new KerblamPurchase();
              
              for (Map.Entry<String, Integer> entry : msg.data.entrySet()) {
                int amount = ((Integer)entry.getValue()).intValue();
                
                ResourceLocation key = new ResourceLocation(entry.getKey());
                KerblamItem item = (KerblamItem)DMKerblamStock.getItems().get(key);
                int stacks = (int)Math.ceil((amount / item.getItemStack().func_77976_d()));
                for (int i = 0; i < stacks; i++) {
                  if (i == stacks - 1) {
                    if (amount % item.getItemStack().func_77976_d() != 0) {
                      pur.getItems().add(new ItemStack((IItemProvider)item.getItemStack().getItem(), amount % item.getItemStack().func_77976_d()));
                    } else {
                      pur.getItems().add(new ItemStack((IItemProvider)item.getItemStack().getItem(), item.getItemStack().func_77976_d()));
                    } 
                  } else {
                    pur.getItems().add(new ItemStack((IItemProvider)item.getItemStack().getItem(), item.getItemStack().func_77976_d()));
                  } 
                } 
              } 
              int totalChestsNeeded = (int)Math.ceil((pur.getItems().size() / 8.0F));
              Inventory[] inventories = new Inventory[totalChestsNeeded];
              for (int boxIndex = 0; boxIndex < totalChestsNeeded; boxIndex++) {
                inventories[boxIndex] = new Inventory(9);
                int randPos = DalekMod.RANDOM.nextInt(9);
                int itemIndex = 0;
                while (itemIndex < 8 && itemIndex + boxIndex * 8 < pur.getItems().size()) {
                  int actualIndex = (itemIndex >= randPos) ? (itemIndex + 1) : itemIndex;
                  inventories[boxIndex].func_70299_a(actualIndex, pur.getItems().get(itemIndex + boxIndex * 8));
                  itemIndex++;
                } 
                inventories[boxIndex].func_70299_a(randPos, new ItemStack((IItemProvider)((Block)DMBlocks.BUBBLE_WRAP.get()).func_199767_j(), DalekMod.RANDOM.nextInt(4) + 1));
              } 
              if (!player.isCreative()) {
                player.experienceTotal -= total;
                NetworkHandler.sendTo(player, new PacketXPSync(player.experienceTotal, false));
              } 
              ChatUtil.sendCompletedMsg((PlayerEntity)player, (IFormattableTextComponent)DMTranslationKeys.KERBLAM_PURCHASE, ChatUtil.MessageType.CHAT);
              DMKerblamStock.makeDelivery((PlayerEntity)player, inventories);
              player.func_213823_a(SoundEvents.field_187802_ec, SoundCategory.PLAYERS, 1.0F, 1.0F);
              NetworkHandler.sendTo(player, new PacketClearKerblamBasket());
            } else {
              ChatUtil.sendError((PlayerEntity)player, (IFormattableTextComponent)DMTranslationKeys.KERBLAM_NOT_ENOUGH_XP, ChatUtil.MessageType.CHAT);
            } 
          });
    }
     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }
}


