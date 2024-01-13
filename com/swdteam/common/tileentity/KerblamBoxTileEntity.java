package com.swdteam.common.tileentity;

import com.swdteam.common.container.KerblamBoxContainer;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;

public class KerblamBoxTileEntity
  extends LockableLootTileEntity
{
   private NonNullList<ItemStack> chestContents = NonNullList.func_191197_a(9, ItemStack.field_190927_a);
  protected int numPlayersUsing;
  
  public KerblamBoxTileEntity(TileEntityType<?> typeIn) {
     super(typeIn);
  }
  
  public KerblamBoxTileEntity() {
     this((TileEntityType)DMBlockEntities.TILE_KERBLAM_BOX.get());
  }

  
  public int func_70302_i_() {
     return 9;
  }


  
  public NonNullList<ItemStack> func_190576_q() {
     return this.chestContents;
  }

  
  public void func_199721_a(NonNullList<ItemStack> itemsIn) {
     this.chestContents = itemsIn;
  }
  
  public CompoundNBT saveToTag(CompoundNBT p_190580_1_) {
     if (!func_184282_c(p_190580_1_)) {
       ItemStackHelper.func_191281_a(p_190580_1_, this.chestContents, false);
    }
    
     return p_190580_1_;
  }

  
  protected ITextComponent func_213907_g() {
     return (ITextComponent)new TranslationTextComponent("Kerblam Box");
  }

  
  protected Container func_213906_a(int id, PlayerInventory player) {
     return (Container)new KerblamBoxContainer(id, player, (IInventory)this);
  }

  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     super.func_189515_b(compound);
     if (!func_184282_c(compound)) {
       ItemStackHelper.func_191282_a(compound, this.chestContents);
    }
     return compound;
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     super.read(state, compound);
     this.chestContents = NonNullList.func_191197_a(func_70302_i_(), ItemStack.field_190927_a);
     if (!func_184283_b(compound)) {
       ItemStackHelper.func_191283_b(compound, this.chestContents);
    }
  }

  
  public boolean func_145842_c(int id, int type) {
     if (id == 1) {
       this.numPlayersUsing = type;
       return true;
    } 
     return super.func_145842_c(id, type);
  }


  
  public void func_174889_b(PlayerEntity player) {
     if (!player.func_175149_v()) {
       player.func_184185_a((SoundEvent)DMSoundEvents.BLOCK_CARDBOARD_OPEN.get(), 0.5F, 1.0F);
       if (this.numPlayersUsing < 0) {
         this.numPlayersUsing = 0;
      }
      
       this.numPlayersUsing++;
       onOpenOrClose();
    } 
  }

  
  public void func_174886_c(PlayerEntity player) {
     if (!player.func_175149_v()) {
       player.func_184185_a((SoundEvent)DMSoundEvents.BLOCK_CARDBOARD_CLOSE.get(), 0.5F, 1.0F);
       this.numPlayersUsing--;
       onOpenOrClose();
    } 
  }
  
  protected void onOpenOrClose() {
     Block block = func_195044_w().getBlock();
     if (block instanceof com.swdteam.common.block.KerblamBoxBlock) {
       this.world.func_175641_c(getPos(), block, 1, this.numPlayersUsing);
       this.world.func_195593_d(getPos(), block);
    } 
  }
  
  public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
     BlockState blockstate = reader.getBlockState(pos);
     if (blockstate.hasTileEntity()) {
       TileEntity tileentity = reader.getTileEntity(pos);
       if (tileentity instanceof KerblamBoxTileEntity) {
         return ((KerblamBoxTileEntity)tileentity).numPlayersUsing;
      }
    } 
     return 0;
  }
  
  public static void swapContents(KerblamBoxTileEntity te, KerblamBoxTileEntity otherTe) {
     NonNullList<ItemStack> list = te.func_190576_q();
     te.func_199721_a(otherTe.func_190576_q());
     otherTe.func_199721_a(list);
  }
  
  public static boolean validItem(ItemStack stack) {
     if (stack.getItem() instanceof BlockItem) {
       Block block = ((BlockItem)stack.getItem()).func_179223_d();
       if (block instanceof net.minecraft.block.ShulkerBoxBlock || block instanceof com.swdteam.common.block.KerblamBoxBlock) return false; 
    } 
     return true;
  }

  
  public boolean func_94041_b(int slot, ItemStack stack) {
     if (!validItem(stack)) return false; 
     return super.func_94041_b(slot, stack);
  }
}


