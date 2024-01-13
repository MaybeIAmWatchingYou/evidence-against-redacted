package com.swdteam.common.container;

import com.swdteam.common.container.slot.ArtronSlot;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMContainer;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.tileentity.ArtronFuelTankTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketFuelTankSlotUpdate;
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
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArtronFuelTankContainer
  extends Container
{
  private final IWorldPosCallable access;
  private final World level;
  private PlayerEntity player;
  public ArtronFuelTankTileEntity tile;
  private boolean init = false;
  final Slot inputSlot;
  private Runnable slotUpdateListener = () -> {
    
    };
  
   public final IInventory container = (IInventory)new Inventory(1) {
      public void markDirty() {
         super.markDirty();
         ArtronFuelTankContainer.this.func_75130_a((IInventory)this);
         ArtronFuelTankContainer.this.slotUpdateListener.run();
      }
      
      public int func_70297_j_() {
         return 1;
      }
    };
  public ArtronFuelTankContainer(int i, PlayerInventory inventory, PacketBuffer buffer) {
     this(i, inventory, getBlockEntity(inventory, buffer));
  }
  
  public ArtronFuelTankContainer(int i, PlayerInventory inventory, ArtronFuelTankTileEntity tileEntity) {
     super((ContainerType)DMContainer.FAULT_LOCATOR_CONTAINER.get(), i);
     this.player = inventory.field_70458_d;
     this.tile = tileEntity;
     this.access = IWorldPosCallable.func_221488_a(tileEntity.getWorld(), tileEntity.getPos());
     this.level = inventory.field_70458_d.world;
    
     this.inputSlot = func_75146_a((Slot)new ArtronSlot(this.container, 0, 125, 52));
     this.container.func_70299_a(0, this.tile.getFuelSlot());

    
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
  
  private static ArtronFuelTankTileEntity getBlockEntity(PlayerInventory playerInventory, PacketBuffer data) {
     Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
     Objects.requireNonNull(data, "data cannot be null!");
     TileEntity tileAtPos = playerInventory.field_70458_d.world.getTileEntity(data.func_179259_c());
     if (tileAtPos instanceof ArtronFuelTankTileEntity)
       return (ArtronFuelTankTileEntity)tileAtPos;
     throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
  }
  
  public boolean func_75145_c(PlayerEntity entity) {
     return func_216963_a(this.access, entity, (Block)DMBlocks.ARTRON_FUEL_TANK.get());
  }

  
  public void func_75130_a(IInventory inventory) {
     if (this.init &&
       !this.level.isRemote && this.tile.charge >= 1.0D) {
       ItemStack stackInSlot1 = inventory.func_70301_a(0);
       if (stackInSlot1 != null && (
         stackInSlot1.getItem() instanceof net.minecraft.item.GlassBottleItem || stackInSlot1.getItem() == DMItems.ARTRON.get())) {
         if (stackInSlot1.getItem() == DMItems.ARTRON.get() && stackInSlot1.func_77978_p().func_74764_b(DMNBTKeys.ATRON_CHARGE) && stackInSlot1.func_77978_p().func_74769_h(DMNBTKeys.ATRON_CHARGE) >= 100.0D)
           return;  this.container.func_70299_a(0, stackInSlot1 = this.tile.fillGlass(stackInSlot1));
         this.player.world.playSound(null, this.tile.getPos(), (SoundEvent)DMSoundEvents.TARDIS_FUEL_REFILL.get(), SoundCategory.BLOCKS, 0.3F, 1.0F);
         NetworkHandler.sendTo((ServerPlayerEntity)this.player, new PacketFuelTankSlotUpdate(0, stackInSlot1));
      } 
    } 


    
     this.tile.setFuelSlot(inventory.func_70301_a(0));
  }
  
  public ContainerType<?> func_216957_a() {
     return (ContainerType)DMContainer.ARTRON_FUEL_CONTAINER.get();
  }
  
  @OnlyIn(Dist.CLIENT)
  public void registerUpdateListener(Runnable runnable) {
     this.slotUpdateListener = runnable;
  }
  
  public boolean isValidForSlot(int slot, ItemStack stack) {
     return true;
  }

  
  public ItemStack func_82846_b(PlayerEntity entity, int slotIndex) {
     ItemStack output = ItemStack.field_190927_a;
     Slot slot = this.field_75151_b.get(slotIndex);
    
     if (slot != null && slot.func_75216_d()) {
       ItemStack item = slot.func_75211_c();
       output = item.func_77946_l();
      
       if (slotIndex >= 1 && slotIndex < 37) {
         if (item.getItem() == DMItems.ARTRON.get() && !func_75135_a(item, 0, 1, false)) return ItemStack.field_190927_a;
         if (item.getItem() == Items.field_151069_bo && !func_75135_a(item, 0, 1, false)) return ItemStack.field_190927_a;
        
         if (slotIndex >= 1 && slotIndex < 28 && !func_75135_a(item, 28, 37, false)) return ItemStack.field_190927_a;
         if (slotIndex >= 28 && slotIndex < 37 && !func_75135_a(item, 1, 28, false)) return ItemStack.field_190927_a;
      
      } 
       if (!func_75135_a(item, 1, 37, false)) return ItemStack.field_190927_a;
      
       if (item.func_190926_b()) { slot.func_75215_d(ItemStack.field_190927_a); }
       else { slot.func_75218_e(); }
      
       if (item.func_190916_E() == output.func_190916_E()) return ItemStack.field_190927_a;
      
       slot.func_190901_a(entity, item);
    } 
    
     return output;
  }
}


