package com.swdteam.common.container;

import com.swdteam.common.container.slot.KerblamBoxSlot;
import com.swdteam.common.init.DMContainer;
import com.swdteam.common.tileentity.KerblamBoxTileEntity;
import java.util.Objects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class KerblamBoxContainer
  extends Container {
  private final IInventory container;

  public KerblamBoxContainer(int id, PlayerInventory player, PacketBuffer buffer) {
     this(id, player, (IInventory)getBlockEntity(player, buffer));
  }

  public KerblamBoxContainer(int id, PlayerInventory player, IInventory inv) {
     super((ContainerType)DMContainer.KERBLAM_BOX_CONTAINER.get(), id);
     checkContainerSize(inv, 9);
     this.container = inv;
     inv.func_174889_b(player.field_70458_d);
     int i = -54;

     for (int k = 0; k < 9; k++) {
       func_75146_a((Slot)new KerblamBoxSlot(inv, k, 8 + k * 18, 18));
    }


     for (int l = 0; l < 3; l++) {
       for (int j1 = 0; j1 < 9; j1++) {
         func_75146_a(new Slot((IInventory)player, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
      }
    }

     for (int i1 = 0; i1 < 9; i1++) {
       func_75146_a(new Slot((IInventory)player, i1, 8 + i1 * 18, 161 + i));
    }
  }


  public boolean func_75145_c(PlayerEntity p_75145_1_) {
     return this.container.func_70300_a(p_75145_1_);
  }

  public ItemStack func_82846_b(PlayerEntity p_82846_1_, int p_82846_2_) {
     ItemStack itemstack = ItemStack.field_190927_a;
     Slot slot = this.field_75151_b.get(p_82846_2_);
     if (slot != null && slot.func_75216_d()) {
       ItemStack itemstack1 = slot.func_75211_c();
       itemstack = itemstack1.func_77946_l();
       if (p_82846_2_ < 9) {
         if (!func_75135_a(itemstack1, 9, this.field_75151_b.size(), true)) {
           return ItemStack.field_190927_a;
        }
       } else if (!func_75135_a(itemstack1, 0, 9, false)) {
         return ItemStack.field_190927_a;
      }

       if (itemstack1.func_190926_b()) {
         slot.func_75215_d(ItemStack.field_190927_a);
      } else {
         slot.func_75218_e();
      }
    }

     return itemstack;
  }

  private static KerblamBoxTileEntity getBlockEntity(PlayerInventory playerInventory, PacketBuffer data) {
     Objects.requireNonNull(playerInventory, "playerInventory cannot be null!");
     Objects.requireNonNull(data, "data cannot be null!");
     TileEntity tileAtPos = playerInventory.field_70458_d.world.getTileEntity(data.func_179259_c());
     if (tileAtPos instanceof KerblamBoxTileEntity) return (KerblamBoxTileEntity)tileAtPos; 
     throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
  }

  protected static void checkContainerSize(IInventory p_216962_0_, int p_216962_1_) {
     int i = p_216962_0_.func_70302_i_();
     if (i < p_216962_1_)
       throw new IllegalArgumentException("Container size " + i + " is smaller than expected " + p_216962_1_); 
  }
}


