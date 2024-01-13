package com.swdteam.common.container;

import com.google.common.collect.Lists;
import com.swdteam.common.crafting.RoundelBuilderRecipe;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMContainer;
import com.swdteam.common.init.DMCraftingTypes;
import com.swdteam.common.init.DMRoundelRegistry;
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
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RoundelContainer extends Container {
  private final IWorldPosCallable access;
   private final IntReferenceHolder selectedRecipeIndex = IntReferenceHolder.func_221492_a();
  private final World level;
   private List<RoundelBuilderRecipe> recipes = Lists.newArrayList();
  
  private long lastSoundTime;
  
  final Slot inputSlotA;
  final Slot inputSlotB;
  final Slot resultSlot;
   private ItemStack inputA = ItemStack.field_190927_a;
   private ItemStack inputB = ItemStack.field_190927_a;


  
   public final IInventory container = (IInventory)new Inventory(2) {
      public void markDirty() {
         super.markDirty();
         RoundelContainer.this.func_75130_a((IInventory)this);
         RoundelContainer.this.slotUpdateListener.run();
      }
    }; private Runnable slotUpdateListener = () -> {
    
     }; private final CraftResultInventory resultContainer = new CraftResultInventory();
  
  public RoundelContainer(int i, PlayerInventory inv, PacketBuffer buffer) {
     this(i, inv, IWorldPosCallable.field_221489_a);
  }
  
  public RoundelContainer(int i, PlayerInventory inv, final IWorldPosCallable callable) {
     super((ContainerType)DMContainer.CRAFTING_CONTAINER.get(), i);
     this.access = callable;
     this.inputSlotA = func_75146_a(new Slot(this.container, 0, 28, 18));
     this.inputSlotB = func_75146_a(new Slot(this.container, 1, 28, 50));
     this.level = inv.field_70458_d.world;
     this.resultSlot = func_75146_a(new Slot((IInventory)this.resultContainer, 2, 143, 33) {
          public boolean func_75214_a(ItemStack p_75214_1_) {
             return false;
          }
          
          private void decreaseSlot(Slot slot, PlayerEntity entity) {
             ItemStack stack = slot.func_75211_c();
             if (stack.func_190916_E() > 1)
             { entity.addItemStackToInventory(stack.getContainerItem());
               slot.func_75209_a(1); }
             else { slot.func_75215_d(stack.getContainerItem()); }
          
          }
          public ItemStack func_190901_a(PlayerEntity player, ItemStack stack) {
             stack.func_77980_a(player.world, player, stack.func_190916_E());
             RoundelContainer.this.resultContainer.func_201560_d(player);
             Slot slotA = RoundelContainer.this.inputSlotA;
             Slot slotB = RoundelContainer.this.inputSlotB;
            
             decreaseSlot(slotA, player);
             decreaseSlot(slotB, player);
            
             ItemStack itemstackA = slotA.func_75211_c();
             ItemStack itemstackB = slotB.func_75211_c();
            
             if (!itemstackA.func_190926_b() && !itemstackB.func_190926_b()) RoundelContainer.this.setupResultSlot();

            
             callable.func_221486_a((p_216954_1_, p_216954_2_) -> {
                  long l = p_216954_1_.getGameTime();
                  
                  if (RoundelContainer.this.lastSoundTime != l) {
                    p_216954_1_.playSound((PlayerEntity)null, p_216954_2_, SoundEvents.field_219719_ml, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    RoundelContainer.this.lastSoundTime = l;
                  } 
                });
             return super.func_190901_a(player, stack);
          }
        });
    
     for (int i2 = 0; i2 < 3; i2++) {
       for (int j = 0; j < 9; j++) {
         func_75146_a(new Slot((IInventory)inv, j + i2 * 9 + 9, 8 + j * 18, 84 + i2 * 18));
      }
    } 
    
     for (int k = 0; k < 9; k++) {
       func_75146_a(new Slot((IInventory)inv, k, 8 + k * 18, 142));
    }
     func_216958_a(this.selectedRecipeIndex);
  }
  
  @OnlyIn(Dist.CLIENT)
  public int getSelectedRecipeIndex() {
     return this.selectedRecipeIndex.func_221495_b();
  }
  
  @OnlyIn(Dist.CLIENT)
  public List<RoundelBuilderRecipe> getRecipes() {
     return this.recipes;
  }
  
  @OnlyIn(Dist.CLIENT)
  public int getNumRecipes() {
     return this.recipes.size();
  }
  
  @OnlyIn(Dist.CLIENT)
  public boolean hasInputItem() {
     return (this.inputSlotA.func_75216_d() && this.inputSlotB.func_75216_d() && !this.recipes.isEmpty());
  }
  
  public boolean func_75145_c(PlayerEntity p_75145_1_) {
     return func_216963_a(this.access, p_75145_1_, (Block)DMBlocks.ROUNDEL_BUILDER.get());
  }
  
  public boolean func_75140_a(PlayerEntity p_75140_1_, int p_75140_2_) {
     if (isValidRecipeIndex(p_75140_2_)) {
       this.selectedRecipeIndex.func_221494_a(p_75140_2_);
       setupResultSlot();
    } 
     return true;
  }
  
  private boolean isValidRecipeIndex(int p_241818_1_) {
     return (p_241818_1_ >= 0 && p_241818_1_ < this.recipes.size());
  }
  
  public void func_75130_a(IInventory inventory) {
     ItemStack itemstackA = this.inputSlotA.func_75211_c();
     ItemStack itemstackB = this.inputSlotB.func_75211_c();
     if (itemstackA.getItem() != this.inputA.getItem() || itemstackB.getItem() != this.inputB.getItem()) {
       this.inputA = itemstackA.func_77946_l();
       this.inputB = itemstackB.func_77946_l();
       setupRecipeList(inventory);
    } 
  }
  
  public void setupRecipeList(IInventory inventory) {
     this.recipes.clear();
     this.selectedRecipeIndex.func_221494_a(-1);
     this.resultSlot.func_75215_d(ItemStack.field_190927_a);
     if (this.inputSlotA.func_75211_c().func_190926_b() || this.inputSlotB.func_75211_c().func_190926_b())
       return;  this.recipes.addAll(this.level.func_199532_z().func_215370_b(DMCraftingTypes.ROUNDEL_RECIPE, this.container, this.level));
     this.recipes.addAll(DMRoundelRegistry.getRecipesFor(this.container));
  }
  
  private void setupResultSlot() {
     if (!this.recipes.isEmpty() && isValidRecipeIndex(this.selectedRecipeIndex.func_221495_b())) {
       RoundelBuilderRecipe recipe = this.recipes.get(this.selectedRecipeIndex.func_221495_b());
      
       this.resultSlot.func_75215_d(recipe.func_77571_b().func_77946_l());
    } else {
       this.resultSlot.func_75215_d(ItemStack.field_190927_a);
    } 
     func_75142_b();
  }
  
  public ContainerType<?> func_216957_a() {
     return (ContainerType)DMContainer.CRAFTING_CONTAINER.get();
  }
  
  @OnlyIn(Dist.CLIENT)
  public void registerUpdateListener(Runnable p_217071_1_) {
     this.slotUpdateListener = p_217071_1_;
  }
  
  public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_) {
     return (p_94530_2_.field_75224_c != this.resultContainer && super.func_94530_a(p_94530_1_, p_94530_2_));
  }

  
  public ItemStack func_82846_b(PlayerEntity entity, int slotIndex) {
     ItemStack output = ItemStack.field_190927_a;
     Slot slot = this.field_75151_b.get(slotIndex);
     if (slot != null && slot.func_75216_d()) {
       ItemStack item = slot.func_75211_c();
       output = item.func_77946_l();
       if (slotIndex == 2) {
        
         int maxCrafted = this.inputSlotA.func_75211_c().func_190916_E();
         int slotBCount = this.inputSlotB.func_75211_c().func_190916_E();
         if (maxCrafted > slotBCount) maxCrafted = slotBCount;
        
         ItemStack result = new ItemStack((IItemProvider)item.getItem(), maxCrafted * item.func_190916_E());
         result.func_77982_d(item.func_77978_p());
        
         if (func_75135_a(result, 3, 39, true)) {
           this.inputSlotA.func_75211_c().func_190918_g(maxCrafted);
           this.inputSlotB.func_75211_c().func_190918_g(maxCrafted);
          
           func_75130_a(this.container);
        } 
         return ItemStack.field_190927_a;
      } 
       if (slotIndex >= 3 && slotIndex < 39 && !func_75135_a(item, 0, 2, false)) return ItemStack.field_190927_a; 
       if (!func_75135_a(item, 3, 39, false)) return ItemStack.field_190927_a; 
       if (item.func_190926_b()) { slot.func_75215_d(ItemStack.field_190927_a); }
       else { slot.func_75218_e(); }
        if (item.func_190916_E() == output.func_190916_E()) return ItemStack.field_190927_a; 
       slot.func_190901_a(entity, item);
    } 
     return output;
  }
  
  public void func_75134_a(PlayerEntity p_75134_1_) {
     super.func_75134_a(p_75134_1_);
     this.resultContainer.func_70304_b(1);
     this.access.func_221486_a((p_217079_2_, p_217079_3_) -> func_193327_a(p_75134_1_, p_75134_1_.world, this.container));
  }
}


