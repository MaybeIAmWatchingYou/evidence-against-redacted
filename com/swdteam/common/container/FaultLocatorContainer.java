package com.swdteam.common.container;

import com.swdteam.common.container.slot.ArtronSlot;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMContainer;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTags;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.tardis.FaultLocatorTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketFaultLocatorSlotUpdate;
import java.util.Objects;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;





public class FaultLocatorContainer
  extends Container
{
  private final IWorldPosCallable access;
  private final World level;
  private PlayerEntity player;
  public FaultLocatorTileEntity tile;
  private boolean init = false;
  
   public final IInventory container = (IInventory)new Inventory(4) {
      public void markDirty() {
         super.markDirty();
         FaultLocatorContainer.this.func_75130_a((IInventory)this);
         FaultLocatorContainer.this.slotUpdateListener.run();
      }
    }; final Slot inputSlotA; final Slot faultSlotFlight; final Slot faultSlotFuel; final Slot faultSlotAccuracy; private Runnable slotUpdateListener = () -> {
    
    }; public FaultLocatorContainer(int i, PlayerInventory inventory, PacketBuffer buffer) {
     this(i, inventory, getBlockEntity(inventory, buffer));
  }
  
  public FaultLocatorContainer(int i, PlayerInventory inventory, FaultLocatorTileEntity tileEntity) {
     super((ContainerType)DMContainer.FAULT_LOCATOR_CONTAINER.get(), i);
     this.player = inventory.field_70458_d;
     this.tile = tileEntity;
     this.access = IWorldPosCallable.func_221488_a(tileEntity.getWorld(), tileEntity.getPos());
     this.level = inventory.field_70458_d.world;
    
     this.container.func_70299_a(0, this.tile.getFuelSlot());
    
     this.inputSlotA = func_75146_a((Slot)new ArtronSlot(this.container, 0, 17, 52));
     this.faultSlotFlight = func_75146_a(new Slot(this.container, 1, 116, 12));
     this.faultSlotFuel = func_75146_a(new Slot(this.container, 2, 116, 32));
     this.faultSlotAccuracy = func_75146_a(new Slot(this.container, 3, 116, 52));
    
     for (int i2 = 0; i2 < 3; i2++) {
       for (int j = 0; j < 9; j++) {
         func_75146_a(new Slot((IInventory)inventory, j + i2 * 9 + 9, 8 + j * 18, 84 + i2 * 18));
      }
    } 
    
     for (int k = 0; k < 9; k++) {
       func_75146_a(new Slot((IInventory)inventory, k, 8 + k * 18, 142));
    }
     this.init = true;
  }
  
  private static FaultLocatorTileEntity getBlockEntity(PlayerInventory playerInventory, PacketBuffer data) {
     Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
     Objects.requireNonNull(data, "data cannot be null!");
     TileEntity tileAtPos = playerInventory.field_70458_d.world.getTileEntity(data.func_179259_c());
     if (tileAtPos instanceof FaultLocatorTileEntity)
       return (FaultLocatorTileEntity)tileAtPos;
     throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
  }
  
  public boolean func_75145_c(PlayerEntity entity) {
     return func_216963_a(this.access, entity, (Block)DMBlocks.FAULT_LOCATOR.get());
  }

  
  public void func_75130_a(IInventory inventory) {
     if (this.init) {
       ItemStack stackInSlot1 = inventory.func_70301_a(0);
       ItemStack stackInFaultSlotFlight = inventory.func_70301_a(1);
       ItemStack stackInFaultSlotFuel = inventory.func_70301_a(2);
       ItemStack stackInFaultSlotAccuracy = inventory.func_70301_a(3);
      
       if (!this.level.isRemote) {
         TardisData data = DMTardis.getTardisFromInteriorPos(this.tile.getPos());
        
         if (data != null) {
           if (stackInFaultSlotFlight.getItem() == DMItems.FLIGHT_FLUID_LINK.get()) {
             int stackSize = stackInFaultSlotFlight.func_190916_E();
             int need = 100 - data.getFluidLinkFlightTime();
            
             int i = stackSize - need;
             if (i < 0) {
               i = 0;
            }
            
             stackInFaultSlotFlight.func_190920_e(i);
             data.setFluidLinkFlightTime(data.getFluidLinkFlightTime() + stackSize - i);
             if (need > 0) {
               this.player.world.playSound(null, this.tile.getPos(), (SoundEvent)DMSoundEvents.TARDIS_FAULT_LOCATOR_INSERT.get(), SoundCategory.BLOCKS, 0.3F, 1.0F);
            }
             data.save();
             NetworkHandler.sendTo((ServerPlayerEntity)this.player, new PacketFaultLocatorSlotUpdate(1, stackInFaultSlotFlight));
          } 
           if (stackInFaultSlotFuel.getItem() == DMItems.FUEL_FLUID_LINK.get()) {
             int stackSize = stackInFaultSlotFuel.func_190916_E();
             int need = 100 - data.getFluidLinkFuelConsumption();
            
             int i = stackSize - need;
             if (i < 0) {
               i = 0;
            }
            
             stackInFaultSlotFuel.func_190920_e(i);
             if (need > 0) {
               this.player.world.playSound(null, this.tile.getPos(), (SoundEvent)DMSoundEvents.TARDIS_FUEL_REFILL.get(), SoundCategory.BLOCKS, 0.3F, 1.0F);
            }
             data.setFluidLinkFuelConsumption(data.getFluidLinkFuelConsumption() + stackSize - i);
            
             data.save();
             NetworkHandler.sendTo((ServerPlayerEntity)this.player, new PacketFaultLocatorSlotUpdate(2, stackInFaultSlotFuel));
          } 
          
           if (stackInFaultSlotAccuracy.getItem() == DMItems.ACCURACY_FLUID_LINK.get()) {
             int stackSize = stackInFaultSlotAccuracy.func_190916_E();
             int need = 100 - data.getFluidLinkAccuracy();
            
             int i = stackSize - need;
             if (i < 0) {
               i = 0;
            }
            
             stackInFaultSlotAccuracy.func_190920_e(i);
             if (need > 0) {
               this.player.world.playSound(null, this.tile.getPos(), (SoundEvent)DMSoundEvents.TARDIS_FAULT_LOCATOR_INSERT.get(), SoundCategory.BLOCKS, 0.3F, 1.0F);
            }
             data.setFluidLinkAccuracy(data.getFluidLinkAccuracy() + stackSize - i);
            
             data.save();
             NetworkHandler.sendTo((ServerPlayerEntity)this.player, new PacketFaultLocatorSlotUpdate(3, stackInFaultSlotAccuracy));
          } 
        } 
        
         this.tile.setFuelSlot(stackInSlot1);
         if (!stackInSlot1.func_190926_b() && stackInSlot1.getItem() instanceof com.swdteam.common.item.ArtronItem &&
           data != null)
        {
           if (data.getFuel() < 100.0D) {
             double fuel = (stackInSlot1.func_77942_o() && stackInSlot1.func_77978_p().func_74764_b(DMNBTKeys.ATRON_CHARGE)) ? stackInSlot1.func_77978_p().func_74769_h(DMNBTKeys.ATRON_CHARGE) : 100.0D;
             double fuelReturn = 0.0D;
            
             if (fuel > 100.0D - data.getFuel()) {
               fuelReturn = fuel - 100.0D - data.getFuel();
            }
            
             data.addFuel(fuel - fuelReturn);
             this.player.world.playSound(null, this.tile.getPos(), (SoundEvent)DMSoundEvents.TARDIS_FUEL_REFILL.get(), SoundCategory.BLOCKS, 0.3F, 1.0F);
            
             if (fuelReturn > 0.0D) {
               if (!stackInSlot1.func_77942_o()) {
                 stackInSlot1.func_77982_d(new CompoundNBT());
              }
              
               stackInSlot1.func_77978_p().func_74780_a(DMNBTKeys.ATRON_CHARGE, fuelReturn);
            } else {
               this.container.func_70299_a(0, new ItemStack((IItemProvider)Items.field_151069_bo));
            } 
            
             this.tile.setFuelSlot(this.container.func_70301_a(0));
             NetworkHandler.sendTo((ServerPlayerEntity)this.player, new PacketFaultLocatorSlotUpdate(0, inventory.func_70301_a(0)));
             data.save();
          } 
        }
      } 
    } 
  }


  
  public ContainerType<?> func_216957_a() {
     return (ContainerType)DMContainer.FAULT_LOCATOR_CONTAINER.get();
  }
  
  @OnlyIn(Dist.CLIENT)
  public void registerUpdateListener(Runnable runnable) {
     this.slotUpdateListener = runnable;
  }
  
  public boolean isValidForSlot(int slot, ItemStack stack) {
     if (slot == 0) return (stack.getItem().func_206844_a((ITag)DMTags.Items.ARTRON) || stack.getItem() == Items.field_151069_bo);
     return true;
  }

  
  public ItemStack func_82846_b(PlayerEntity entity, int slotIndex) {
     ItemStack output = ItemStack.field_190927_a;
     Slot slot = this.field_75151_b.get(slotIndex);
    
     if (slot != null && slot.func_75216_d()) {
       ItemStack item = slot.func_75211_c();
       output = item.func_77946_l();

      
       if (slotIndex >= 4 && slotIndex < 40) {
         if (item.getItem().func_206844_a((ITag)DMTags.Items.ARTRON) || (item.getItem() instanceof net.minecraft.item.GlassBottleItem && !func_75135_a(item, 0, 1, false))) return ItemStack.field_190927_a;
         if (item.getItem() == DMItems.FLIGHT_FLUID_LINK.get() && !func_75135_a(item, 1, 2, false)) return ItemStack.field_190927_a;
         if (item.getItem() == DMItems.FUEL_FLUID_LINK.get() && !func_75135_a(item, 2, 3, false)) return ItemStack.field_190927_a;
         if (item.getItem() == DMItems.ACCURACY_FLUID_LINK.get() && !func_75135_a(item, 3, 4, false)) return ItemStack.field_190927_a;
        
         if (slotIndex >= 4 && slotIndex < 31 && !func_75135_a(item, 31, 40, false)) return ItemStack.field_190927_a;
         if (slotIndex >= 31 && slotIndex < 40 && !func_75135_a(item, 4, 31, false)) return ItemStack.field_190927_a;
      
      } 
       if (!func_75135_a(item, 4, 40, false)) return ItemStack.field_190927_a;
      
       if (item.func_190926_b()) { slot.func_75215_d(ItemStack.field_190927_a); }
       else { slot.func_75218_e(); }
      
       if (item.func_190916_E() == output.func_190916_E()) return ItemStack.field_190927_a;
      
       slot.func_190901_a(entity, item);
    } 
    
     return output;
  }
}


