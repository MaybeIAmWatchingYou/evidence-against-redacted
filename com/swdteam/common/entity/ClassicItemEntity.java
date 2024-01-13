package com.swdteam.common.entity;

import com.swdteam.common.init.DMEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class ClassicItemEntity
  extends ItemEntity {
  public ClassicItemEntity(World worldIn, double x, double y, double z) {
     this((EntityType<? extends ClassicItemEntity>)DMEntities.CLASSIC_ITEM.get(), worldIn);
     setPosition(x, y, z);
     this.field_70177_z = this.field_70146_Z.nextFloat() * 360.0F;
     setMotion(this.field_70146_Z.nextDouble() * 0.2D - 0.1D, 0.2D, this.field_70146_Z.nextDouble() * 0.2D - 0.1D);
  }
  
  public ClassicItemEntity(EntityType<? extends ClassicItemEntity> p_i50217_1_, World world) {
     super(p_i50217_1_, world);
  }
  
  public ClassicItemEntity(World worldIn, double x, double y, double z, ItemStack stack) {
     this(worldIn, x, y, z);
     setItem(stack);
     this.lifespan = (stack.getItem() == null) ? 6000 : stack.getEntityLifespan(worldIn);
  }

  
  public IPacket<?> func_213297_N() {
     return NetworkHooks.getEntitySpawningPacket((Entity)this);
  }
  
  @OnlyIn(Dist.CLIENT)
  private ClassicItemEntity(ClassicItemEntity entity) {
     super((EntityType)DMEntities.CLASSIC_ITEM.get(), entity.world);
     setItem(entity.getItem().func_77946_l());
     copyLocationAndAnglesFrom((Entity)entity);
  }


  
  @OnlyIn(Dist.CLIENT)
  public ItemEntity func_234273_t_() {
     return new ClassicItemEntity(this);
  }
}


