package com.swdteam.common.sonic;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public enum SonicCategory
{
   REDSTONE("sonic.category.redstone", 0),
   TNT("sonic.category.tnt", 15),
   FARMLAND("sonic.category.farmland", 25);

  public int xpRequired;

  public String perm;

  static {
     map = new HashMap<>();






















































     for (SonicCategory cat : EnumSet.<SonicCategory>allOf(SonicCategory.class))
    {
       map.put(cat.perm, cat); }
  }

  public TranslationTextComponent permName;
  public TranslationTextComponent unlockName;
  private static Map<String, SonicCategory> map;

  public static boolean canExecute(ItemStack stack, SonicCategory cat) {
    if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("perms")) {
      ListNBT list = stack.func_77978_p().func_150295_c("perms", 10);
      for (int i = 0; i < list.size(); i++) {
        INBT nbt = list.get(i);
        if (nbt instanceof CompoundNBT && ((CompoundNBT)nbt).func_74764_b("perm") && ((CompoundNBT)nbt).func_74779_i("perm").equals(cat.perm))
          return true;
      }
    }
    return false;
  }

  public static void checkUnlock(PlayerEntity player, ItemStack stack) {
    if (stack.func_77942_o()) {
      int i = stack.func_77978_p().getInt("xp");
      for (SonicCategory cat : EnumSet.<SonicCategory>allOf(SonicCategory.class)) {
        if (i >= cat.xpRequired)
          if (!canExecute(stack, cat)) {
            player.func_146105_b((ITextComponent)(new StringTextComponent("Your sonic just leveled up! You unlocked: " + cat.permName.getString())).func_240699_a_(TextFormatting.GREEN), false);
            player.getEntityWorld().playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.field_187802_ec, player.func_184176_by(), 1.0F, 1.0F);
            if (stack.func_77978_p().func_74764_b("perms")) {
              ListNBT list = stack.func_77978_p().func_150295_c("perms", 10);
              CompoundNBT tag = new CompoundNBT();
              tag.func_74778_a("perm", cat.perm);
              list.add(tag);
              stack.func_77978_p().func_218657_a("perms", (INBT)list);
            }
          }
      }
    }
  }

  SonicCategory(String perm, int i) {
    this.xpRequired = i;
    this.perm = perm;
    this.permName = new TranslationTextComponent(perm);
    this.unlockName = new TranslationTextComponent(perm + "_locked", new Object[] { new StringTextComponent(this.xpRequired + "XP") });
  }
}


