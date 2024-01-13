package com.swdteam.common.item;

import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.util.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

public class GunItem
  extends Item {
  private RegistryObject<SoundEvent> chargeSound;
  private RegistryObject<SoundEvent> shootSound;
  private final DMProjectiles.Laser laserType;
  private final IItemTier tier;
  public float requiredChargeTime;
  public float attackDamage;

  public GunItem(IItemTier tier, float chargeInSeconds, DMProjectiles.Laser laserType, RegistryObject<SoundEvent> lasergunChargeSound, RegistryObject<SoundEvent> lasergunShootSound, Item.Properties properties) {
     super(properties.func_200915_b(tier.func_200926_a()));
     this.tier = tier;
     this.laserType = laserType;

     this.requiredChargeTime = chargeInSeconds * 20.0F;
     this.chargeSound = lasergunChargeSound;
     this.shootSound = lasergunShootSound;
  }

  public IItemTier getTier() {
     return this.tier;
  }

  private SoundEvent getChargeSound() {
     return (SoundEvent)this.chargeSound.get();
  }

  private SoundEvent getShootSound() {
     return (SoundEvent)this.shootSound.get();
  }

  public void setChargeSound(RegistryObject<SoundEvent> chargeSound) {
     this.chargeSound = this.shootSound;
  }

  public void setShootSound(RegistryObject<SoundEvent> shootSound) {
     this.shootSound = shootSound;
  }


  public UseAction func_77661_b(ItemStack stack) {
     return UseAction.BOW;
  }





  public int func_77626_a(ItemStack stack) {
     return 72000;
  }


  public ActionResult<ItemStack> func_77659_a(World worldIn, PlayerEntity playerIn, Hand handIn) {
     playerIn.func_184598_c(handIn);

     if (!worldIn.isRemote) {
       worldIn.playSound(null, playerIn.getPosition(), getChargeSound(), SoundCategory.PLAYERS, 0.5F, 1.0F);
    }
     return super.func_77659_a(worldIn, playerIn, handIn);
  }


  public void func_77615_a(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
     super.func_77615_a(stack, worldIn, entityLiving, timeLeft);

     this.attackDamage = 6.0F;

     if (hasAmmo(entityLiving, stack))
    {
       if (!worldIn.isRemote) {
         int timeUsed = func_77626_a(stack) - timeLeft;
         if (timeUsed < this.requiredChargeTime)
           return;  worldIn.playSound(null, entityLiving.getPosX(), entityLiving.getPosY(), entityLiving.getPosZ(), getShootSound(), SoundCategory.NEUTRAL, 1.0F, 1.0F);
         LaserEntity laser = new LaserEntity(worldIn, entityLiving, 0.0F, this.attackDamage);
         laser.setLaserType(this.laserType);
         laser.setEmitsSmoke(true);
         laser.setDamageSource((DamageSource)new EntityDamageSource("dalekgun", (Entity)entityLiving));
         laser.shoot((Entity)entityLiving, entityLiving.field_70125_A, entityLiving.field_70177_z, 0.0F, 2.5F, 0.0F);
         worldIn.addEntity((Entity)laser);
         if (entityLiving instanceof PlayerEntity) {
           PlayerUtil.consumeItem((PlayerEntity)entityLiving, (Item)DMItems.BULLET.get(), 1);
        }
         if (entityLiving instanceof ServerPlayerEntity && !((ServerPlayerEntity)entityLiving).isCreative()) {
           stack.func_222118_a(1, entityLiving, player -> player.func_213334_d(player.getActiveHand()));
        }
      }
    }
  }






  public boolean hasAmmo(LivingEntity entity, ItemStack stack) {
     if (stack.getItem() == DMItems.GUNSTICK.get())
    {
       return true;
    }
     if (stack.getItem() == DMItems.CANNON.get())
    {
       return true;
    }
     if (entity instanceof PlayerEntity) {

       PlayerEntity player = (PlayerEntity)entity;

       if (player.isCreative())
      {
         return true;
      }



       if (player.field_71071_by.func_213901_a((Item)DMItems.BULLET.get()) > 1)
      {
         return true;
      }
       return false;
    }


     return false;
  }


  public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
     return super.onItemUseFirst(stack, context);
  }

  public boolean func_82789_a(ItemStack stack1, ItemStack stack2) {
     return (this.tier.func_200924_f().test(stack2) || super.func_82789_a(stack1, stack2));
  }
}


