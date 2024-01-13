package com.swdteam.common.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;

public class KnockbackWeaponItem
  extends SwordItem
{
  private final Multimap<Attribute, AttributeModifier> defaultModifiers;

  public KnockbackWeaponItem(IItemTier tier, int f, float attackSpeedIn, Item.Properties properties) {
     super(tier, f, attackSpeedIn, properties);
     ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
     builder.put(Attributes.field_233823_f_, new AttributeModifier(field_111210_e, "Weapon modifier", f, AttributeModifier.Operation.ADDITION));
     builder.put(Attributes.field_233825_h_, new AttributeModifier(field_185050_h, "Weapon modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
     builder.put(Attributes.field_233824_g_, new AttributeModifier("Weapon modifier", 5.0D, AttributeModifier.Operation.ADDITION));
     this.defaultModifiers = (Multimap<Attribute, AttributeModifier>)builder.build();
  }


  public void func_77622_d(ItemStack stack, World worldIn, PlayerEntity playerIn) {
     super.func_77622_d(stack, worldIn, playerIn);
  }



  public Multimap<Attribute, AttributeModifier> func_111205_h(EquipmentSlotType slot) {
     return (slot == EquipmentSlotType.MAINHAND) ? this.defaultModifiers : super.func_111205_h(slot);
  }
}


