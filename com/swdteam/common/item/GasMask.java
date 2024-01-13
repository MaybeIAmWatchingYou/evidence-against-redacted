package com.swdteam.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class GasMask extends ClothesItem {
  private long startTime;
  private boolean bufferWorn;
  private boolean worn = false;

  public GasMask(Item.Properties properties, EquipmentSlotType slot) {
     super(properties, slot, Effects.field_76440_q);
  }

  public GasMask(EquipmentSlotType slot) {
     super(slot, Effects.field_76440_q);
  }



  public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
     if (!this.worn) {
       this.startTime = world.getGameTime();

       if (player.func_70644_a(Effects.field_76436_u)) {
         this.startTime += player.func_70660_b(Effects.field_76436_u).func_76459_b();
      }
       this.worn = true;
    }

     super.onArmorTick(stack, world, player);
  }


  public void func_77663_a(ItemStack itemstack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
     this.bufferWorn = false;
     entity.getArmorInventoryList().forEach(item -> {
          if (item.getItem() instanceof GasMask && this.worn) {
            this.bufferWorn = true;
          }
        });

     this.worn = this.bufferWorn;
     super.func_77663_a(itemstack, world, entity, p_77663_4_, p_77663_5_);
  }



  public void runEffect(LivingEntity entity) {
     if (entity.func_70644_a(Effects.field_76436_u) && entity.world.getGameTime() > this.startTime) {
       entity.func_195063_d(Effects.field_76436_u);
       if (entity.func_110143_aJ() != 1.0F) entity.func_70691_i(1.0F);

    }
     super.runEffect(entity);
  }
}


