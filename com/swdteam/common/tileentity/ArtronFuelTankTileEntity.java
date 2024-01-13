package com.swdteam.common.tileentity;
import com.swdteam.common.container.ArtronFuelTankContainer;
import com.swdteam.common.container.slot.ArtronSlot;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMNBTKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;

public class ArtronFuelTankTileEntity extends LockableTileEntity {
   public double charge = 0.0D;
   public ItemStack fuelSlot = ItemStack.field_190927_a;
  
  public ArtronFuelTankTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_ARTRON_FUEL_TANK.get());
  }

  
  public CompoundNBT func_189517_E_() {
     return func_189515_b(new CompoundNBT());
  }

  
  public SUpdateTileEntityPacket func_189518_D_() {
     CompoundNBT nbt = new CompoundNBT();
     func_189515_b(nbt);
    
     return new SUpdateTileEntityPacket(getPos(), 0, nbt);
  }

  
  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
     read(this.world.getBlockState(getPos()), pkt.func_148857_g());
  }

  
  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.ATRON_CHARGE)) {
       this.charge = compound.func_74769_h(DMNBTKeys.ATRON_CHARGE);
    }
     if (compound.func_74764_b(DMNBTKeys.ITEM)) {
       this.fuelSlot = ItemStack.func_199557_a(compound.func_74775_l(DMNBTKeys.ITEM));
    }
     super.read(state, compound);
  }

  
  public CompoundNBT func_189515_b(CompoundNBT compound) {
     compound.func_74780_a(DMNBTKeys.ATRON_CHARGE, this.charge);
     if (this.fuelSlot != null) {
       CompoundNBT tag = new CompoundNBT();
       this.fuelSlot.func_77955_b(tag);
       compound.func_218657_a(DMNBTKeys.ITEM, (INBT)tag);
    } 
     return super.func_189515_b(compound);
  }
  
  public ItemStack getFuelSlot() {
     if (this.fuelSlot == null) {
       return ItemStack.field_190927_a;
    }
     return this.fuelSlot;
  }
  
  public void setFuelSlot(ItemStack fuelSlot) {
     this.fuelSlot = fuelSlot;
  }

  
  public AxisAlignedBB getRenderBoundingBox() {
     return super.getRenderBoundingBox().func_72314_b(0.0D, 6.0D, 0.0D);
  }

  
  public ITextComponent func_145748_c_() {
     return (ITextComponent)DMTranslationKeys.GUI_FAULT_LOCATOR_NAME;
  }
  
  public ItemStack fillGlass(ItemStack stack) {
     if (this.charge < 1.0D) return stack;
    
     CompoundNBT tag = stack.func_77942_o() ? stack.func_77978_p() : new CompoundNBT();
     double chargeUsed = this.charge;
     double chargeAdded = this.charge;
    
     ItemStack item = new ItemStack((IItemProvider)DMItems.ARTRON.get());
    
     if (tag.func_74764_b(DMNBTKeys.ATRON_CHARGE)) {
       double currentCharge = tag.func_74769_h(DMNBTKeys.ATRON_CHARGE);
       if (currentCharge + this.charge > 100.0D) chargeUsed = 100.0D - currentCharge; 
       chargeAdded = currentCharge + chargeUsed;
    } 
    
     tag.func_74780_a(DMNBTKeys.ATRON_CHARGE, chargeAdded);
     item.func_77982_d(tag);
    
     this.charge -= chargeUsed;
     sendUpdates();
    
     return item;
  }
  
  public void sendUpdates() {
     this.world.func_225319_b(getPos(), this.world.getBlockState(getPos()), this.world.getBlockState(getPos()));
     this.world.func_184138_a(getPos(), this.world.getBlockState(getPos()), this.world.getBlockState(getPos()), 3);
     markDirty();
  }

  
  public Container func_213906_a(int id, PlayerInventory inv) {
     return (Container)new ArtronFuelTankContainer(id, inv, this);
  }

  
  public int func_70302_i_() {
     return 1;
  }

  
  public boolean func_191420_l() {
     return this.fuelSlot.func_190926_b();
  }

  
  public ItemStack func_70301_a(int p_70301_1_) {
     return this.fuelSlot;
  }

  
  public ItemStack func_70298_a(int slot, int amnt) {
     ItemStack stack = this.fuelSlot.func_77946_l();
     if (amnt > this.fuelSlot.func_190916_E()) amnt = this.fuelSlot.func_190916_E(); 
     int remove = this.fuelSlot.func_190916_E() - amnt;
    
     this.fuelSlot.func_190920_e(remove);
     markDirty();
     stack.func_190920_e(amnt);
     return stack;
  }

  
  public ItemStack func_70304_b(int p_70304_1_) {
     ItemStack stack = this.fuelSlot.func_77946_l();
     this.fuelSlot = new ItemStack((IItemProvider)Blocks.AIR);
     return stack;
  }

  
  public void func_70299_a(int p_70299_1_, ItemStack stack) {
     this.fuelSlot = stack;
     markDirty();
  }

  
  public boolean func_70300_a(PlayerEntity p_70300_1_) {
     return true;
  }

  
  public void func_174888_l() {
     func_70304_b(0);
     markDirty();
  }

  
  protected ITextComponent func_213907_g() {
     return (ITextComponent)((Block)DMBlocks.ARTRON_FUEL_TANK.get()).func_235333_g_();
  }

  
  public boolean func_94041_b(int p_94041_1_, ItemStack p_94041_2_) {
     return (ArtronSlot.isValid(p_94041_2_) && this.fuelSlot.func_190926_b());
  }
}


