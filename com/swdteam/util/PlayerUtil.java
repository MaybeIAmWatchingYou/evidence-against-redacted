package com.swdteam.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PlayerUtil
{
  public static class Inventory {
    public static ItemStack findItemStackFromItem(PlayerEntity playerEntity, Item item) {
       for (int i = 0; i < playerEntity.field_71071_by.func_70302_i_(); i++) {
         ItemStack itemstack = playerEntity.field_71071_by.func_70301_a(i);
        
         if (itemstack.getItem() == item) {
           return itemstack;
        }
      } 
      
       return ItemStack.field_190927_a;
    }
  }


  
  public static void consumeItem(PlayerEntity player, Item findItem, int quant) {
     for (int i = 0; i < player.field_71071_by.func_70302_i_(); i++) {
       ItemStack slot = player.field_71071_by.func_70301_a(i);
       if (slot.getItem().equals(findItem)) {
         slot.func_190918_g(1);
        break;
      } 
    } 
  }
  
  public static void sendMessage(PlayerEntity playerEntity, String msg) {
     playerEntity.func_146105_b((ITextComponent)new StringTextComponent(msg), false);
  }
  
  public static void sendMessage(PlayerEntity playerEntity, String msg, boolean status) {
     playerEntity.func_146105_b((ITextComponent)new StringTextComponent(msg), status);
  }
  
  public static void sendMessageTranslation(PlayerEntity playerEntity, String msg) {
     playerEntity.func_146105_b((ITextComponent)new TranslationTextComponent(msg), false);
  }
  
  public static void sendMessageTranslation(PlayerEntity playerEntity, String msg, boolean status) {
     playerEntity.func_146105_b((ITextComponent)new TranslationTextComponent(msg), status);
  }
}


