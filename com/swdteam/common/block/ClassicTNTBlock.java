package com.swdteam.common.block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class ClassicTNTBlock extends Block {
  public ClassicTNTBlock(AbstractBlock.Properties properties) {
     super(properties);
  }

  
  public void func_176206_d(IWorld worldIn, BlockPos pos, BlockState state) {
     if (!worldIn.isRemote()) {
       TNTEntity tnt = new TNTEntity(EntityType.field_200735_aa, (World)worldIn);
       tnt.func_70080_a((pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), 0.0F, 0.0F);
       tnt.addVelocity(0.0D, 0.25D, 0.0D);
       worldIn.addEntity((Entity)tnt);
    } 
     super.func_176206_d(worldIn, pos, state);
  }

  
  public void func_180652_a(World worldIn, BlockPos pos, Explosion explosionIn) {
     TNTEntity tnt = new TNTEntity(EntityType.field_200735_aa, worldIn);
     tnt.func_184534_a(10 + worldIn.rand.nextInt(10));
     tnt.func_70080_a((pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), 0.0F, 0.0F);
     tnt.addVelocity(0.0D, 0.25D, 0.0D);
     worldIn.addEntity((Entity)tnt);
     super.func_180652_a(worldIn, pos, explosionIn);
  }

  
  public boolean func_149659_a(Explosion p_149659_1_) {
     return false;
  }
}


