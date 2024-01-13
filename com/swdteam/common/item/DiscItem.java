package com.swdteam.common.item;
import java.util.function.Supplier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;

public class DiscItem extends MusicDiscItem {
  public DiscItem(int comparatorValue, Supplier<SoundEvent> soundSupplier, Item.Properties properties) {
     super(comparatorValue, soundSupplier, properties);
  }

  
  public int getItemStackLimit(ItemStack stack) {
     return 1;
  }
}


