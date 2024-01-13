package com.swdteam.common.item.sonics;

import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.sonic.SonicCategory;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class SonicScrewdriverCustomizedItem extends Item {
  private RegistryObject<SoundEvent> sonicSound;

  public SonicScrewdriverCustomizedItem(RegistryObject<SoundEvent> _sonicSound, int xpValue) {
     super((new Item.Properties()).func_200917_a(1));
     DMSonicRegistry.registerSonic(new ItemStack((IItemProvider)this), xpValue);
     this.sonicSound = _sonicSound;
  }

  public SonicScrewdriverCustomizedItem(int xpValue) {
     super((new Item.Properties()).func_200917_a(1));
     DMSonicRegistry.registerSonic(new ItemStack((IItemProvider)this), xpValue);
  }

  public SonicScrewdriverCustomizedItem(ItemGroup itemGroup, int xpValue) {
     super((new Item.Properties()).func_200917_a(1).func_200916_a(itemGroup));
     DMSonicRegistry.registerSonic(new ItemStack((IItemProvider)this), xpValue);
  }

  private SoundEvent getSonicSound() {
     return (SoundEvent)this.sonicSound.get();
  }

  public ActionResult<ItemStack> func_77659_a(World worldIn, PlayerEntity playerIn, Hand handIn) {
     if (!worldIn.isRemote) {
       checkIsSetup(playerIn.getHeldItem(handIn));
       worldIn.playSound(null, playerIn.getPosition(), getSonicSound(), SoundCategory.PLAYERS, 0.5F, 1.0F);
    }
     return super.func_77659_a(worldIn, playerIn, handIn);
  }

  public void setSonicSound(RegistryObject<SoundEvent> sonicSound) {
     this.sonicSound = sonicSound;
  }


  public void checkIsSetup(ItemStack stack) {
     if (!stack.func_77942_o()) {
       stack.func_77982_d(new CompoundNBT());
    }

     if (!stack.func_77978_p().func_74764_b("xp")) {
       stack.func_77978_p().putInt("xp", 0);
    }

     if (!stack.func_77978_p().func_74764_b("unlocked")) {
       ListNBT list = new ListNBT();
       CompoundNBT tag = new CompoundNBT();
       tag.func_74778_a("skin", stack.getItem().getRegistryName().toString().toLowerCase());
       list.add(tag);
       stack.func_77978_p().func_218657_a("unlocked", (INBT)list);
    }

     if (!stack.func_77978_p().func_74764_b("perms")) {
       ListNBT list = new ListNBT();
       CompoundNBT tag = new CompoundNBT();
       tag.func_74778_a("perm", SonicCategory.REDSTONE.perm);
       list.add(tag);
       stack.func_77978_p().func_218657_a("perms", (INBT)list);
    }
  }



  public void func_77624_a(ItemStack stack, World w, List<ITextComponent> list, ITooltipFlag flag) {
     if (!stack.func_77942_o()) {
       list.add((new StringTextComponent("Edit with Sonic Interface")).func_240699_a_(TextFormatting.GRAY));
    }
     if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("xp")) {
       list.add((new StringTextComponent("XP: " + stack.func_77978_p().getInt("xp"))).func_240699_a_(TextFormatting.GOLD));
    }

     list.add((new StringTextComponent("Abilities")).func_240699_a_(TextFormatting.GOLD).func_240699_a_(TextFormatting.BOLD));
     for (int i = 0; i < (SonicCategory.values()).length; i++) {
       SonicCategory cat = SonicCategory.values()[i];
       if (SonicCategory.canExecute(stack, cat) || cat.xpRequired <= 0) {
         list.add(cat.permName.func_240699_a_(TextFormatting.GREEN));
      } else {
         list.add(cat.unlockName.func_240699_a_(TextFormatting.RED));
      }
    }
  }
}


