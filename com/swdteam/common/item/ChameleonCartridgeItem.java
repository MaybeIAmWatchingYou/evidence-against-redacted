package com.swdteam.common.item;

import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

public class ChameleonCartridgeItem
  extends Item
{
  public ChameleonCartridgeItem(Item.Properties properties) {
     super(properties);
  }

  
  public void func_77624_a(ItemStack stack, World world, List<ITextComponent> component, ITooltipFlag flag) {
     super.func_77624_a(stack, world, component, flag);
  }

  
  public ActionResult<ItemStack> func_77659_a(World world, PlayerEntity entity, Hand hand) {
     return super.func_77659_a(world, entity, hand);
  }
}


