package com.swdteam.common.tileentity;

import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.util.SWDMathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.Property;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.Explosion;

public class Nitro9TileEntity extends DMTileEntityBase implements ITickableTileEntity {
  int timer;
  public Entity activator;
  public boolean lit = false;
  
  public Nitro9TileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_NITRO_9.get());
  }


  
  public AxisAlignedBB getRenderBoundingBox() {
     return super.getRenderBoundingBox();
  }

  
  public void tick() {
     if (this.lit) {
       for (int i = 0; i < 2; i++) {
         this.activator.world.addParticle((IParticleData)ParticleTypes.SMOKE, getPos().getX() + 0.5D, getPos().getY() + 0.4D, getPos().getZ() + 0.5D, 0.0D, 0.1D, 0.0D);
      }
       this.timer++;
    } 
    
     if (this.timer > 60 && !this.world.isRemote) {
       BlockState state = this.world.getBlockState(getPos());
       if (state.getBlock() instanceof com.swdteam.common.block.Nitro9Block) this.world.setBlockState(getPos(), (((Boolean)state.get((Property)BlockStateProperties.WATERLOGGED)).booleanValue() == true) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState());
      
       if (this.activator instanceof PlayerEntity) {
         PlayerEntity p = (PlayerEntity)this.activator;
         if (p.getUniqueID().toString().equals("94e96cfa-02e5-4231-9b35-4fb39d0912f5") || p.getUniqueID().toString().equals("9d7f31b4-17cb-4ee5-bf00-1652682083ae")) {
           for (int i = 0; i < 100; i++) {
             CowEntity cow = new CowEntity(EntityType.COW, this.world);
             cow.setPosition(getPos().getX() + 0.5D, getPos().getY(), getPos().getZ() + 0.5D);
             cow.addVelocity(SWDMathUtils.randomRange(-0.2F, 0.2F), 0.0D, SWDMathUtils.randomRange(-0.2F, 0.2F));
             this.world.addEntity((Entity)cow);
          } 
           this.world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
           this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 1.0F, Explosion.Mode.NONE);
        } 
         if (p.getUniqueID().toString().equals("15efe577-a5ad-465c-9843-000a47f80a02") || p.getUniqueID().toString().equals("21658c61-4583-4b93-a0f9-7b92442b01c9") || p.getUniqueID().toString().equals("9d7f31b4-17cb-4ee5-bf00-1652682083ae")) {
           this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 40.0F, Explosion.Mode.DESTROY);
        } else {
           this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 10.0F, Explosion.Mode.DESTROY);
        } 
      } else {
         this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 10.0F, Explosion.Mode.DESTROY);
      } 
    } 
  }
}


