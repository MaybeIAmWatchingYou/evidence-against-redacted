package com.swdteam.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedItem
  extends Item {
  public EnchantedItem(Item.Properties properties) {
     super(properties);
  }

  public boolean hasEffect(ItemStack stack) {
     return true;
  }
}


