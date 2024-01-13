package com.swdteam.common.item;

import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class PhoneItem
  extends Item {
  public PhoneItem(Item.Properties i) {
     super(i);
  }


  public ActionResult<ItemStack> func_77659_a(World worldIn, PlayerEntity playerIn, Hand handIn) {
     playerIn.world.playSound(playerIn, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), (SoundEvent)DMSoundEvents.PLAYER_PHONE.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
     return super.func_77659_a(worldIn, playerIn, handIn);
  }
}


