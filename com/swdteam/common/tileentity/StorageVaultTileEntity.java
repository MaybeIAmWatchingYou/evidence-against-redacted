package com.swdteam.common.tileentity;
import com.swdteam.common.block.StorageVaultBlock;
import com.swdteam.common.container.itemhandler.StorageVaultItemHandler;
import com.swdteam.common.init.DMBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class StorageVaultTileEntity extends LockableLootTileEntity {
   private NonNullList<ItemStack> items = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
  private LazyOptional<IItemHandlerModifiable> itemHandler;
  
  protected StorageVaultTileEntity(TileEntityType<?> tileEntityTypeIn) {
     super(tileEntityTypeIn);
  }
  
  public StorageVaultTileEntity() {
     this((TileEntityType)DMBlockEntities.TILE_STORAGE_VAULT.get());
  }
  
  public int func_70302_i_() {
     return 27;
  }
  
  public int addItem(ItemStack p_146019_1_) {
     for (int i = 0; i < this.items.size(); i++) {
       if (((ItemStack)this.items.get(i)).func_190926_b()) {
         func_70299_a(i, p_146019_1_);
         return i;
      } 
    } 
    
     return -1;
  }
  
  protected ITextComponent func_213907_g() {
     return (ITextComponent)new TranslationTextComponent("container.dalekmod.storage_vault");
  }
  
  public void read(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
     super.read(p_230337_1_, p_230337_2_);
     this.items = NonNullList.func_191197_a(func_70302_i_(), ItemStack.field_190927_a);
     if (!func_184283_b(p_230337_2_)) {
       ItemStackHelper.func_191283_b(p_230337_2_, this.items);
    }
  }

  
  public CompoundNBT func_189515_b(CompoundNBT p_189515_1_) {
     super.func_189515_b(p_189515_1_);
     if (!func_184282_c(p_189515_1_)) {
       ItemStackHelper.func_191282_a(p_189515_1_, this.items);
    }
    
     return p_189515_1_;
  }
  
  public NonNullList<ItemStack> func_190576_q() {
     return this.items;
  }
  
  protected void func_199721_a(NonNullList<ItemStack> p_199721_1_) {
     this.items = p_199721_1_;
  }
  
  protected Container func_213906_a(int p_213906_1_, PlayerInventory p_213906_2_) {
     return (Container)ChestContainer.func_216992_a(p_213906_1_, p_213906_2_, (IInventory)this);
  }

  
  public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
     boolean powered = ((Boolean)func_195044_w().get((Property)StorageVaultBlock.POWERED)).booleanValue();
     if (!powered) return false;
    
     return super.func_94041_b(p_94041_1_, p_94041_2_);
  }

  
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
     if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
       if (this.itemHandler == null) createHandler();
       if (this.itemHandler != null) return this.itemHandler.cast();
    } 
     return super.getCapability(cap, side);
  }
  
  private void createHandler() {
     this.itemHandler = LazyOptional.of(() -> new StorageVaultItemHandler((IInventory)this));
  }
}


