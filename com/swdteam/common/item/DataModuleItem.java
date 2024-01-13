package com.swdteam.common.item;

import com.swdteam.common.init.DMItems;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;


public class DataModuleItem
  extends Item
{
   private ITextComponent txtNonRewritable = (ITextComponent)new StringTextComponent(TextFormatting.GOLD + "Type: " + TextFormatting.RED + "Non-rewritable");
   private ITextComponent txtRewritable = (ITextComponent)new StringTextComponent(TextFormatting.GOLD + "Type: " + TextFormatting.GREEN + "Rewritable");
   private ITextComponent txtFormatted = (ITextComponent)new StringTextComponent(TextFormatting.RED + "Formatted");

  public DataModuleItem(Item.Properties properties) {
     super(properties);
  }



  public void func_77624_a(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
     if (this == DMItems.DATA_MODULE_GOLD.get()) {
       tooltip.add(this.txtRewritable);
    } else {
       tooltip.add(this.txtNonRewritable);
    }

     if (stack.func_77942_o() && stack.func_77978_p().func_74767_n("formatted")) {
       tooltip.add(this.txtFormatted);
    }

     super.func_77624_a(stack, worldIn, tooltip, flagIn);
  }
}


