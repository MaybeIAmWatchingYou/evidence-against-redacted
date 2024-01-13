package com.swdteam.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;

public class FoodItem extends Item {
  boolean isDrink;
  
  public FoodItem(Item.Properties properties, boolean isDrink) {
     super(properties);
     this.isDrink = isDrink;
  }
  
  public FoodItem(Item.Properties properties) {
     super(properties);
     this.isDrink = false;
  }

  
  public boolean func_219971_r() {
     return true;
  }

  
  public int func_77626_a(ItemStack stack) {
     return 32;
  }

  
  public UseAction func_77661_b(ItemStack stack) {
     return this.isDrink ? UseAction.DRINK : UseAction.EAT;
  }
}


