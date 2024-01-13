package com.swdteam.common.item;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FluidLinkItem
  extends Item
{
  private TranslationTextComponent translation;

  public FluidLinkItem(Item.Properties properties, TranslationTextComponent translation) {
     super(properties);
     this.translation = translation;
  }


  public void func_77624_a(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
     tooltip.add(new StringTextComponent(TextFormatting.GOLD + "Type: " + TextFormatting.BLUE + this.translation.getString()));
     super.func_77624_a(stack, worldIn, tooltip, flagIn);
  }
}


