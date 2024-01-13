package com.swdteam.common.tileentity.tardis;

import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;

public class SonicInterfaceTileEntity
  extends DMTileEntityBase
{
  private ItemStack screwdriver;
  
  public SonicInterfaceTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_SONIC_INTERFACE.get());
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.ITEM)) {
       this.screwdriver = ItemStack.func_199557_a(compound.func_74775_l(DMNBTKeys.ITEM));
    }
     super.read(state, compound);
  }

  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     if (this.screwdriver != null) {
       CompoundNBT tag = new CompoundNBT();
       this.screwdriver.func_77955_b(tag);
       compound.func_218657_a(DMNBTKeys.ITEM, (INBT)tag);
    } else {
       compound.func_82580_o(DMNBTKeys.ITEM);
    } 
     return super.func_189515_b(compound);
  }
  
  public ItemStack getScrewdriver() {
     return this.screwdriver;
  }
  
  public void setScrewdriver(ItemStack screwdriver) {
     this.screwdriver = screwdriver;
  }
  
  public boolean isUnlocked(ItemStack stack) {
     return isUnlocked(stack.getItem().getRegistryName());
  }
  
  public boolean isUnlocked(ResourceLocation stack) {
     if (getScrewdriver() != null && getScrewdriver().getItem() instanceof com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem &&
       getScrewdriver().func_77942_o() &&
       getScrewdriver().func_77978_p().func_74764_b("unlocked")) {
       ListNBT list = getScrewdriver().func_77978_p().func_150295_c("unlocked", 10);
       for (int i = 0; i < list.size(); i++) {
         INBT nbt = list.get(i);
         if (nbt instanceof CompoundNBT && (
           (CompoundNBT)nbt).func_74764_b("skin") && ((CompoundNBT)nbt).func_74779_i("skin").equals(stack.toString())) {
           return true;
        }
      } 
    } 



    
     return false;
  }
  
  public void unlockSkin(ResourceLocation rl) {
     if (!isUnlocked(rl) &&
       getScrewdriver().func_77942_o() && getScrewdriver().func_77978_p().func_74764_b("unlocked")) {
       ListNBT list = getScrewdriver().func_77978_p().func_150295_c("unlocked", 10);
       CompoundNBT tag = new CompoundNBT();
       tag.func_74778_a("skin", rl.toString().toLowerCase());
       list.add(tag);
    } 
    
     sendUpdates();
  }
}


