package com.swdteam.common.item;

import com.swdteam.common.init.DMTabs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ClothesItem
  extends Item {
  private EquipmentSlotType slot;
  private Effect effect;
   private static int defaultDuration = 76;

  public ClothesItem(Item.Properties properties, EquipmentSlotType slot, Effect effect) {
     super(properties);
     this.slot = slot;
     this.effect = effect;
  }

  public ClothesItem(EquipmentSlotType slot, Effect effect) {
     this((new Item.Properties()).func_200916_a(DMTabs.DM_CLOTHES).func_200917_a(1), slot, effect);
  }

  public ClothesItem(EquipmentSlotType slot) {
     this(slot, null);
  }


  public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
     return this.slot;
  }

  public ActionResult<ItemStack> func_77659_a(World world, PlayerEntity player, Hand hand) {
     ItemStack clothingItemstack = player.getHeldItem(hand);
     EquipmentSlotType equipmentSlotType = clothingItemstack.getEquipmentSlot();
     ItemStack equipmentItemstack = player.getItemStackFromSlot(equipmentSlotType);
     if (equipmentItemstack.func_190926_b()) {
       player.func_184201_a(equipmentSlotType, clothingItemstack.func_77946_l());
       clothingItemstack.func_190920_e(0);
       return ActionResult.func_233538_a_(clothingItemstack, world.isRemote());
    }
     return ActionResult.func_226251_d_(clothingItemstack);
  }


  public ItemStack func_77654_b(ItemStack stack, World world, LivingEntity entity) {
     PlayerEntity playerentity = (entity instanceof PlayerEntity) ? (PlayerEntity)entity : null;
     if (playerentity instanceof ServerPlayerEntity) {
       CriteriaTriggers.field_193138_y.func_193148_a((ServerPlayerEntity)playerentity, stack);
    }

     if (!world.isRemote) {
       for (EffectInstance effectinstance : PotionUtils.func_185189_a(stack)) {
         if (effectinstance.func_188419_a().func_76403_b()) {
           effectinstance.func_188419_a().func_180793_a((Entity)playerentity, (Entity)playerentity, entity, effectinstance.func_76458_c(), 1.0D); continue;
        }
         entity.func_195064_c(new EffectInstance(effectinstance));
      }
    }

     return stack;
  }


  public void runEffect(LivingEntity entity) {
     if (this.effect != null && entity != null && !entity.func_233643_dh_() && ((entity
       .func_70644_a(this.effect) && entity.func_70660_b(this.effect).func_76459_b() < defaultDuration) || !entity.func_70644_a(this.effect)))
       entity.func_195064_c(new EffectInstance(this.effect, defaultDuration, 0, false, false, true));
  }
}


