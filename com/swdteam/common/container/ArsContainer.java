package com.swdteam.common.container;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMContainer;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisInterior;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketSendTardisRecipeSync;
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
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArsContainer
  extends Container {
  private final IWorldPosCallable access;
  private final World level;
  final Slot inputSlotA;
  public BlockPos blockPos;
  public PlayerEntity player;
  private Runnable slotUpdateListener = () -> {

    };

   public final IInventory container = (IInventory)new Inventory(1) {
      public void markDirty() {
         super.markDirty();
         ArsContainer.this.func_75130_a((IInventory)this);
         ArsContainer.this.slotUpdateListener.run();
      }
    };

  public ArsContainer(int p_i50059_1_, PlayerInventory p_i50059_2_, PacketBuffer buffer) {
     this(p_i50059_1_, p_i50059_2_, p_i50059_2_.field_70458_d.world, buffer.func_179259_c());
  }

  public ArsContainer(int i1, PlayerInventory inventory, World level, BlockPos pos) {
     super((ContainerType)DMContainer.ARS_CONTAINER.get(), i1);
     this.access = IWorldPosCallable.func_221488_a(level, pos);
     this.level = inventory.field_70458_d.world;
     this.blockPos = pos;
     this.inputSlotA = func_75146_a(new Slot(this.container, 0, 10, 29));
     this.player = inventory.field_70458_d;

     for (int i2 = 0; i2 < 3; i2++) {
       for (int j = 0; j < 9; j++) {
         func_75146_a(new Slot((IInventory)inventory, j + i2 * 9 + 9, 8 + j * 18, 84 + i2 * 18));
      }
    }

     for (int k = 0; k < 9; k++) {
       func_75146_a(new Slot((IInventory)inventory, k, 8 + k * 18, 142));
    }
  }

  public boolean func_75145_c(PlayerEntity entity) {
     return func_216963_a(this.access, entity, (Block)DMBlocks.ARS.get());
  }

  public void func_75130_a(IInventory inventory) {
     ItemStack itemstackA = this.inputSlotA.func_75211_c();

     if (!this.level.isRemote) {
       TardisData data = DMTardis.getTardisFromInteriorPos(this.blockPos);
       if (data != null) {
         TardisInterior interior = (TardisInterior)DMTardisRegistry.getTardisInteriors().get(new ResourceLocation(data.getCurrentlyBuilding()));
         for (TardisInterior.BuildingRecipe.BuildingRecipePart part : interior.getRecipe().getParts()) {
           if (itemstackA.getItem().getRegistryName().toString().equals(part.getItem())) {

             int stackSize = itemstackA.func_190916_E();
             int need = part.getQuantity() - data.getRecipeTotal(part.getItem());
             if (need > 0) {
               if (stackSize > need) {
                 itemstackA.func_190920_e(stackSize - need);
                 data.addItemToRecipe(itemstackA, need);
                 NetworkHandler.sendTo((ServerPlayerEntity)this.player, new PacketSendTardisRecipeSync(part.getItem(), itemstackA, data.getRecipeTotal(itemstackA.getItem().getRegistryName().toString())));
              } else {
                 data.addItemToRecipe(itemstackA, stackSize);
                 this.container.func_70304_b(0);
                 NetworkHandler.sendTo((ServerPlayerEntity)this.player, new PacketSendTardisRecipeSync(part.getItem(), ItemStack.field_190927_a, data.getRecipeTotal(itemstackA.getItem().getRegistryName().toString())));
              }
            }
          }
        }
      }
    }
  }

  public ContainerType<?> func_216957_a() {
     return (ContainerType)DMContainer.ARS_CONTAINER.get();
  }

  @OnlyIn(Dist.CLIENT)
  public void registerUpdateListener(Runnable runnable) {
     this.slotUpdateListener = runnable;
  }

  public ItemStack func_82846_b(PlayerEntity entity, int i) {
     return ItemStack.field_190927_a;
  }

  public void func_75134_a(PlayerEntity entity) {
     super.func_75134_a(entity);

     this.access.func_221486_a((p_217079_2_, p_217079_3_) -> func_193327_a(entity, entity.world, this.container));
  }
}


