package com.swdteam.common.entity;

import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.ItemStackHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeEntity;

public class KerblamManEntity
  extends LivingEntity implements IForgeEntity {
   private List<ItemStack> items = new ArrayList<>();
   private float spawnTime = 0.0F;
  public boolean lookingAt = false;
   private static final DataParameter<Boolean> HOLDING_BOX = EntityDataManager.func_187226_a(KerblamManEntity.class, DataSerializers.field_187198_h);
   private static final DataParameter<Optional<UUID>> DELIVERY_FOR = EntityDataManager.func_187226_a(KerblamManEntity.class, DataSerializers.field_187203_m);
   private final NonNullList<ItemStack> handItems = NonNullList.func_191197_a(2, ItemStack.field_190927_a);
   private final NonNullList<ItemStack> armorItems = NonNullList.func_191197_a(4, ItemStack.field_190927_a);
  
  public KerblamManEntity(EntityType<? extends LivingEntity> type, World worldIn) {
     super(type, worldIn);
  }
  
  public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
     return MobEntity.func_233666_p_()
       .func_233815_a_(Attributes.field_233821_d_, 0.0D)
       .func_233815_a_(Attributes.field_233818_a_, 16.0D);
  }
  
  public void setDeliveryFor(PlayerEntity deliveryFor) {
     func_184212_Q().func_187227_b(DELIVERY_FOR, Optional.ofNullable(deliveryFor.getGameProfile().getId()));
     this.field_70180_af.func_187227_b(HOLDING_BOX, Boolean.valueOf(true));
     this.lookingAt = false;
  }
  
  protected void func_70088_a() {
     super.func_70088_a();
     this.field_70180_af.func_187214_a(DELIVERY_FOR, Optional.empty());
     this.field_70180_af.func_187214_a(HOLDING_BOX, Boolean.valueOf(false));
  }
  
  public void func_213281_b(CompoundNBT tag) {
     super.func_213281_b(tag);
    
     if (((Optional)this.field_70180_af.func_187225_a(DELIVERY_FOR)).isPresent()) tag.func_186854_a(DMNBTKeys.DELIVER_PLAYER, ((Optional<UUID>)this.field_70180_af.func_187225_a(DELIVERY_FOR)).get()); 
     if (((Boolean)this.field_70180_af.func_187225_a(HOLDING_BOX)).booleanValue()) tag.func_74757_a(DMNBTKeys.HOLDING_BOX, ((Boolean)this.field_70180_af.func_187225_a(HOLDING_BOX)).booleanValue()); 
     tag.func_74776_a(DMNBTKeys.SPAWN_TIME, this.spawnTime);
    
     ItemStackHelper.saveAllItems(tag, this.items, true);
  }
  
  public void func_70037_a(CompoundNBT tag) {
     super.func_70037_a(tag);
    
     if (tag.func_74764_b(DMNBTKeys.DELIVER_PLAYER)) this.field_70180_af.func_187227_b(DELIVERY_FOR, Optional.ofNullable(tag.func_186857_a(DMNBTKeys.DELIVER_PLAYER))); 
     if (tag.func_74764_b(DMNBTKeys.HOLDING_BOX)) this.field_70180_af.func_187227_b(HOLDING_BOX, Boolean.valueOf(tag.func_74767_n(DMNBTKeys.HOLDING_BOX))); 
     if (tag.func_74764_b(DMNBTKeys.SPAWN_TIME)) this.spawnTime = tag.func_74760_g(DMNBTKeys.SPAWN_TIME);

    
     ItemStackHelper.loadAllItems(tag, this.items);
  }
  
  public boolean func_70104_M() {
     return true;
  }
  protected void func_82167_n(Entity entity) {
     super.func_82167_n(entity);
  }
  
  public ActionResultType func_184199_a(PlayerEntity player, Vector3d vector, Hand hand) {
     if (!player.world.isRemote && 
       !this.items.isEmpty()) {
       player.addItemStackToInventory(this.items.get(0));
       this.items.remove(0);
      
       if (this.items.isEmpty()) {
         this.spawnTime = 0.0F;
         this.field_70180_af.func_187227_b(HOLDING_BOX, Boolean.valueOf(false));
        
         ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)DMTranslationKeys.KERBLAM_DELIVERY_FULFILLED, ChatUtil.MessageType.CHAT);
         ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)DMTranslationKeys.KERBLAM_IT, ChatUtil.MessageType.CHAT);
      } else {
         ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)new TranslationTextComponent("notice.dalekmod.kerblam.kerblam_it", new Object[] { Integer.valueOf(this.items.size()) }), ChatUtil.MessageType.CHAT);
      } 
    } 
    
     return ActionResultType.CONSUME;
  }
  
  public boolean isHoldingBox() {
     return ((Boolean)this.field_70180_af.func_187225_a(HOLDING_BOX)).booleanValue();
  }
  
  public boolean func_70097_a(DamageSource source, float f) {
     return super.func_70097_a(source, f);
  }
  
  @OnlyIn(Dist.CLIENT)
  public boolean func_70112_a(double d) {
     return true;
  }
  
  public void func_181013_g(float f) {
     this.field_70760_ar = this.field_70126_B = f;
     this.field_70758_at = this.field_70759_as = f;
  }
  
  public void func_70034_d(float f) {
     this.field_70760_ar = this.field_70126_B = f;
     this.field_70758_at = this.field_70759_as = f;
  }
  
  public void func_70071_h_() {
     super.func_70071_h_();
    
     if (this.spawnTime < 1.0F) {
      
       this.spawnTime += 0.025F;
      
       if (this.world.isRemote) {
         if (!((Boolean)this.field_70180_af.func_187225_a(HOLDING_BOX)).booleanValue() && this.spawnTime > 0.5F) {
           for (int i = 0; i < 10; i++) {
             this.world.addParticle((IParticleData)ParticleTypes.field_197598_I, (getPositionVec()).x - 0.5D + this.world.rand.nextFloat(), (getPositionVec()).y + 1.75D, (getPositionVec()).z - 0.5D + this.world.rand.nextFloat(), 0.0D, -(0.1F + this.world.rand.nextFloat() / 10.0F), 0.0D);
          }
         } else if (((Boolean)this.field_70180_af.func_187225_a(HOLDING_BOX)).booleanValue() && this.spawnTime < 0.5F) {
           for (int i = 0; i < 10; i++) {
             this.world.addParticle((IParticleData)ParticleTypes.field_197598_I, (getPositionVec()).x - 0.5D + this.world.rand.nextFloat(), (getPositionVec()).y + 1.75D, (getPositionVec()).z - 0.5D + this.world.rand.nextFloat(), 0.0D, -(0.1F + this.world.rand.nextFloat() / 10.0F), 0.0D);
          }
        } 
      }
    } else {
       this.spawnTime = 1.0F;
       if (!((Boolean)this.field_70180_af.func_187225_a(HOLDING_BOX)).booleanValue()) {
         remove();
      }
    } 
    
     if (this.world.isRemote)
       this.field_70761_aq = this.field_70759_as; 
  }
  
  public void func_174812_G() {
     remove();
  }

  
  protected void func_213345_d(DamageSource p_213345_1_) {
     super.func_213345_d(p_213345_1_);
     for (ItemStack stack : this.items) {
       ItemEntity itemEnt = new ItemEntity(EntityType.ITEM, this.world);
       itemEnt.copyLocationAndAnglesFrom((Entity)this);
       itemEnt.setItem(stack);
       this.world.addEntity((Entity)itemEnt);
    } 
  }

  
  public void func_241847_a(ServerWorld p_241847_1_, LivingEntity p_241847_2_) {
     super.func_241847_a(p_241847_1_, p_241847_2_);
  }

  
  protected boolean func_230282_cS_() {
     return true;
  }
  
  public boolean func_180427_aV() {
     return true;
  }
  
  public boolean func_85031_j(Entity entity) {
     return (entity instanceof PlayerEntity && !this.world.func_175660_a((PlayerEntity)entity, getPosition()));
  }

  
  public void func_241841_a(ServerWorld world, LightningBoltEntity entity) {}
  
  public boolean func_184603_cC() {
     return false;
  }
  
  public void func_184206_a(DataParameter<?> data) {
     if (!((Boolean)this.field_70180_af.func_187225_a(HOLDING_BOX)).booleanValue()) {
       this.spawnTime = 0.0F;
    }
     super.func_184206_a(data);
  }
  
  public boolean func_190631_cK() {
     return true;
  }
  public Iterable<ItemStack> func_184214_aD() {
     return (Iterable<ItemStack>)this.handItems;
  }
  
  public Iterable<ItemStack> getArmorInventoryList() {
     return (Iterable<ItemStack>)this.armorItems;
  }
  
  public ItemStack getItemStackFromSlot(EquipmentSlotType slot) {
     switch (slot.func_188453_a()) {
      case HAND:
         return (ItemStack)this.handItems.get(slot.func_188454_b());
      case ARMOR:
         return (ItemStack)this.armorItems.get(slot.func_188454_b());
    } 
     return ItemStack.field_190927_a;
  }

  
  public void func_184201_a(EquipmentSlotType slot, ItemStack stack) {
     switch (slot.func_188453_a()) {
      case HAND:
         this.handItems.set(slot.func_188454_b(), stack);
        break;
      case ARMOR:
         this.armorItems.set(slot.func_188454_b(), stack);
        break;
    } 
  }
  
  public HandSide func_184591_cq() {
     return HandSide.RIGHT;
  }
  
  public void addPackages(ItemStack... stack) {
     System.out.println(stack.length);
     this.items = new ArrayList<>();
     for (int i = 0; i < stack.length; i++) {
       this.items.add(stack[i]);
       System.out.println("Added: " + stack);
    } 
    
     if (stack != null && stack.length > 0)
       this.field_70180_af.func_187227_b(HOLDING_BOX, Boolean.valueOf(true)); 
  }
}


