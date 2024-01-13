package com.swdteam.common.container;

import com.swdteam.common.crafting.EngineeringTableRecipe;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMContainer;
import com.swdteam.common.init.DMCraftingTypes;
import com.swdteam.common.init.DMEngineeringTableRegistry;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class EngineeringTableContainer
  extends Container
{
  private final IWorldPosCallable access;
  private final World level;
  final Slot inputSlotA;
  
   public final IInventory container = (IInventory)new Inventory(3) {
      public void markDirty() {
         super.markDirty();
         EngineeringTableContainer.this.func_75130_a((IInventory)this);
         EngineeringTableContainer.this.slotUpdateListener.run();
      }
    }; final Slot inputSlotB; final Slot inputSlotC; final Slot outputSlot; private Runnable slotUpdateListener = () -> {
    
     }; private final CraftResultInventory resultContainer = new CraftResultInventory();
  
  public EngineeringTableContainer(int i, PlayerInventory inventory, PacketBuffer buffer) {
     this(i, inventory, IWorldPosCallable.field_221489_a);
  }
  
  public EngineeringTableContainer(int i, PlayerInventory inventory, IWorldPosCallable callable) {
     super((ContainerType)DMContainer.ENGINEERING_TABLE_CONTAINER.get(), i);
     this.access = callable;
     this.level = inventory.field_70458_d.world;
    
     this.inputSlotA = func_75146_a(new Slot(this.container, 0, 22, 35));
     this.inputSlotB = func_75146_a(new Slot(this.container, 1, 49, 35));
     this.inputSlotC = func_75146_a(new Slot(this.container, 2, 76, 35));
     this.outputSlot = func_75146_a(new Slot((IInventory)this.resultContainer, 3, 134, 35)
        {
          public boolean func_75214_a(ItemStack stack) {
             return false;
          }
          
          private void decreaseSlot(Slot slot, PlayerEntity entity) {
             ItemStack stack = slot.func_75211_c();
             if (stack.func_190916_E() > 1)
             { entity.addItemStackToInventory(stack.getContainerItem());
               slot.func_75209_a(1); }
             else { slot.func_75215_d(stack.getContainerItem()); }
          
          }
          public ItemStack func_190901_a(PlayerEntity entity, ItemStack stack) {
             stack.func_77980_a(entity.world, entity, stack.func_190916_E());
            
             decreaseSlot(EngineeringTableContainer.this.inputSlotA, entity);
             decreaseSlot(EngineeringTableContainer.this.inputSlotB, entity);
             decreaseSlot(EngineeringTableContainer.this.inputSlotC, entity);
            
             EngineeringTableContainer.this.func_75142_b();
            
             return super.func_190901_a(entity, stack);
          }
        });
    
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
     return func_216963_a(this.access, entity, (Block)DMBlocks.ENGINEERING_TABLE.get());
  }

  
  public void createResult() {
     List<EngineeringTableRecipe> list = this.level.func_199532_z().func_215370_b(DMCraftingTypes.ENGINEERING_RECIPE, this.container, this.level);
     list.addAll(DMEngineeringTableRegistry.getRecipesFor(this.container));
    
     if (list.isEmpty()) {
       this.resultContainer.func_70299_a(0, ItemStack.field_190927_a);
    } else {
       ItemStack itemstack = ((EngineeringTableRecipe)list.get(0)).func_77572_b(this.container);
       this.resultContainer.func_193056_a((IRecipe)list.get(0));
       this.resultContainer.func_70299_a(0, itemstack);
    } 
  }

  
  public void func_75130_a(IInventory inventory) {
     super.func_75130_a(inventory);
     if (inventory == this.container) createResult(); 
  }
  
  public ContainerType<?> func_216957_a() {
     return (ContainerType)DMContainer.ENGINEERING_TABLE_CONTAINER.get();
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
      
       if (slotIndex == 3) {
         int maxCrafted = this.inputSlotA.func_75211_c().func_190916_E();
         int slotBCount = this.inputSlotB.func_75211_c().func_190916_E();
         int slotCCount = this.inputSlotC.func_75211_c().func_190916_E();
        
         if (maxCrafted > slotBCount) maxCrafted = slotBCount; 
         if (maxCrafted > slotCCount) maxCrafted = slotCCount;
        
         ItemStack result = new ItemStack((IItemProvider)item.getItem(), maxCrafted * item.func_190916_E());
         result.func_77982_d(item.func_77978_p());
        
         if (func_75135_a(result, 4, 40, true)) {
           this.inputSlotA.func_75211_c().func_190918_g(maxCrafted);
           this.inputSlotB.func_75211_c().func_190918_g(maxCrafted);
           this.inputSlotC.func_75211_c().func_190918_g(maxCrafted);
          
           func_75130_a(this.container);
        } 
        
         return ItemStack.field_190927_a;
      } 
       if (slotIndex >= 4 && slotIndex < 40 && !func_75135_a(item, 0, 3, false)) return ItemStack.field_190927_a; 
       if (!func_75135_a(item, 4, 40, false)) return ItemStack.field_190927_a;
      
       if (item.func_190926_b()) { slot.func_75215_d(ItemStack.field_190927_a); }
       else { slot.func_75218_e(); }
      
       if (item.func_190916_E() == output.func_190916_E()) return ItemStack.field_190927_a;
      
       slot.func_190901_a(entity, item);
    } 
    
     return output;
  }
  
  public void func_75134_a(PlayerEntity entity) {
     super.func_75134_a(entity);
     this.access.func_221486_a((p_217079_2_, p_217079_3_) -> func_193327_a(entity, entity.world, this.container));
  }
}


