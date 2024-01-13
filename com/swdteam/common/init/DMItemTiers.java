package com.swdteam.common.init;

import java.util.function.Supplier;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.LazyValue;

public enum DMItemTiers
  implements IItemTier {
   STEEL(2, 300, 6.0F, 2.0F, 14, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.STEEL_INGOT.get()

      })),
   STAINLESS_STEEL(2, 450, 6.5F, 2.5F, 15, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.STAINLESS_STEEL_INGOT.get()

      })),
   DALEKANIUM(2, 600, 6.5F, 2.5F, 15, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.DALEKANIUM_INGOT.get()

      })),
   REFINED_DALEKANIUM(2, 900, 7.5F, 3.0F, 14, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.REFINED_DALEKANIUM_INGOT.get()

      })),
   PURE_DALEKANIUM(2, 1500, 9.5F, 2.0F, 13, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.DALEKANIUM_CONCENTRATE.get()

      })),
   METALERT(4, 5000, 9.5F, 5.0F, 10, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.METALERT.get()

      })),
   DALEK_GUNSTICK(0, 40, 3.5F, 1.0F, 5, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.DALEKANIUM_INGOT.get()

      })),
   DEAGLE(0, 300, 3.5F, 1.0F, 5, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)Items.field_151042_j

      })),
   DALEK_CANNON(0, 20, 4.5F, 1.0F, 10, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.DALEKANIUM_INGOT.get()

      })),
   DALEK_PLUNGER(0, 10, 1.0F, 2.0F, 35, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.DALEKANIUM_INGOT.get() }));

  private final int harvestLevel;

  private final int maxUses;

  private final float efficiency;
  private final float attackDamage;
  private final int enchantability;
  private final LazyValue<Ingredient> repairMaterial;

  DMItemTiers(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
     this.harvestLevel = harvestLevelIn;
     this.maxUses = maxUsesIn;
     this.efficiency = efficiencyIn;
     this.attackDamage = attackDamageIn;
     this.enchantability = enchantabilityIn;
     this.repairMaterial = new LazyValue(repairMaterialIn);
  }



  public int func_200926_a() {
     return this.maxUses;
  }

  public float func_200928_b() {
     return this.efficiency;
  }

  public float func_200929_c() {
     return this.attackDamage;
  }

  public int func_200925_d() {
     return this.harvestLevel;
  }

  public int func_200927_e() {
     return this.enchantability;
  }

  public Ingredient func_200924_f() {
     return (Ingredient)this.repairMaterial.func_179281_c();
  }
}


