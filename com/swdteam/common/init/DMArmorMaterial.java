package com.swdteam.common.init;

import java.util.function.Supplier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DMArmorMaterial {
  public enum ArmorMaterial
    implements IArmorMaterial {
     CLOTH("cloth", 5, (String)new int[] { 1, 2, 3, 1 }, 15, (int[])SoundEvents.field_187728_s, 0.0F, 0.0F, () -> Ingredient.func_199804_a(new IItemProvider[] {
          
          (IItemProvider)Items.field_151007_F
        })),
     DALEKANIUM("dalekmod:dalekanium", 26, (String)new int[] { 2, 5, 6, 2 }, 9, (int[])SoundEvents.field_187725_r, 1.0F, 0.0F, () -> Ingredient.func_199804_a(new IItemProvider[] {
          
          (IItemProvider)DMItems.DALEKANIUM_INGOT.get()
        })),
     STEEL("dalekmod:steel", 28, (String)new int[] { 3, 6, 7, 3 }, 9, (int[])SoundEvents.field_187725_r, 2.0F, 0.3F, () -> Ingredient.func_199804_a(new IItemProvider[] {
          
          (IItemProvider)DMItems.STEEL_INGOT.get()
        })),
     METALERT("dalekmod:metalert", 28, (String)new int[] { 4, 7, 9, 4 }, 9, (int[])SoundEvents.field_187725_r, 3.0F, 0.1F, () -> Ingredient.func_199804_a(new IItemProvider[] { (IItemProvider)DMItems.METALERT.get() }));


    
     private static final int[] HEALTH_PER_SLOT = new int[] { 13, 15, 16, 11 };
    
    private final String name;
    
    private final int durabilityMultiplier;
    
    private final int[] slotProtections;
    
    private final int enchantmentValue;
    
    ArmorMaterial(String string, int i1, int[] i2, int i3, SoundEvent soundEvent, float f1, float f2, Supplier<Ingredient> ingredient) {
       this.name = string;
       this.durabilityMultiplier = i1;
       this.slotProtections = i2;
       this.enchantmentValue = i3;
       this.sound = soundEvent;
       this.toughness = f1;
       this.knockbackResistance = f2;
       this.repairIngredient = new LazyValue(ingredient);
    } private final SoundEvent sound; private final float toughness; private final float knockbackResistance; private final LazyValue<Ingredient> repairIngredient; static {
    
    } public int func_200896_a(EquipmentSlotType slot) {
       return HEALTH_PER_SLOT[slot.func_188454_b()] * this.durabilityMultiplier;
    }
    
    public int func_200902_b(EquipmentSlotType slot) {
       return this.slotProtections[slot.func_188454_b()];
    }
    
    public int func_200900_a() {
       return this.enchantmentValue;
    }
    
    public SoundEvent func_200899_b() {
       return this.sound;
    }
    
    public Ingredient func_200898_c() {
       return (Ingredient)this.repairIngredient.func_179281_c();
    }
    
    @OnlyIn(Dist.CLIENT)
    public String func_200897_d() {
       return this.name;
    }
    
    public float func_200901_e() {
       return this.toughness;
    }
    
    public float func_230304_f_() {
       return this.knockbackResistance;
    }
  }
}


