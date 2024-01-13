package com.swdteam.common.item;

import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMNBTKeys;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ArtronItem
  extends FoodItem {
  public ArtronItem(Item.Properties properties) {
     super(properties, true);
  }



  public void func_77624_a(ItemStack stack, World world, List<ITextComponent> text, ITooltipFlag tooltip) {
     if (stack.func_77942_o() && stack.func_77978_p().func_74764_b(DMNBTKeys.ATRON_CHARGE)) {
       double fuel = Math.floor(stack.func_77978_p().func_74769_h(DMNBTKeys.ATRON_CHARGE) * 100.0D) / 100.0D;
       text.add(new StringTextComponent(TextFormatting.GOLD + "Fuel: " + TextFormatting.WHITE + fuel + "%"));
    }
     super.func_77624_a(stack, world, text, tooltip);
  }


  public void func_77663_a(ItemStack stack, World world, Entity entity, int slot, boolean u_0) {
     if (stack.getItem() == DMItems.ARTRON.get() && stack.func_77942_o() && stack.func_77978_p().func_74764_b(DMNBTKeys.ATRON_CHARGE) &&
       Math.floor(stack.func_77978_p().func_74769_h(DMNBTKeys.ATRON_CHARGE) * 100.0D) / 100.0D >= 100.0D) {
       ItemStack newStack = new ItemStack((IItemProvider)DMItems.FULL_ARTRON.get(), stack.func_190916_E());
       newStack.deserializeNBT(stack.serializeNBT());
       entity.func_174820_d(slot, newStack);
    }

     super.func_77663_a(stack, world, entity, slot, u_0);
  }
}


