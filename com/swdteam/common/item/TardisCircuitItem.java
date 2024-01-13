package com.swdteam.common.item;

import com.swdteam.common.init.DMTranslationKeys;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;


public class TardisCircuitItem
  extends Item
{
   TranslationTextComponent dimensionalCircuit = DMTranslationKeys.ITEM_DIMENSIONAL_CIRCUIT_DESC;
   TranslationTextComponent chameleonCircuit = DMTranslationKeys.ITEM_CHAMELEON_CIRCUIT_DESC;
  
   private ITextComponent txtDimensionalCircuit = (ITextComponent)new StringTextComponent(TextFormatting.GOLD + "Type: " + TextFormatting.BLUE + this.dimensionalCircuit.getString());
   private ITextComponent txtChameleonCircuit = (ITextComponent)new StringTextComponent(TextFormatting.GOLD + "Type: " + TextFormatting.BLUE + this.chameleonCircuit.getString());
  
  public TardisCircuitItem(Item.Properties properties) {
     super(properties);
  }
  
  public boolean hasEffect(ItemStack stack) {
     return true;
  }


  
  public void func_77624_a(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
     String[] groups = { "dalekmod:dimensional_circuit", "dalekmod:chameleon_circuit" };
     String itemRegistry = stack.getItem().getRegistryName().toString();
     System.out.println(itemRegistry);
    
     for (String group : groups) {
       if (itemRegistry.contains(group))
         switch (group) { case "dalekmod:dimensional_circuit":
             tooltip.add(this.txtDimensionalCircuit); break;
           case "dalekmod:chameleon_circuit": tooltip.add(this.txtChameleonCircuit); break; }
         
    } 
     super.func_77624_a(stack, worldIn, tooltip, flagIn);
  }
}


