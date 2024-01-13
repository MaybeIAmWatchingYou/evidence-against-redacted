package com.swdteam.common.init;

import com.swdteam.common.RegistryHandler;
import com.swdteam.common.crafting.EngineeringTableRecipe;
import com.swdteam.common.crafting.RoundelBuilderRecipe;
import java.util.function.Supplier;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;






public class DMCraftingTypes
{
   public static final RegistryObject<IRecipeSerializer<?>> ENGINEERING_SERIALIZER = register("engineering_table", com.swdteam.common.crafting.EngineeringTableRecipe.Serializer::new);
   public static final RegistryObject<IRecipeSerializer<?>> ROUNDEL_SERIALIZER = register("roundel_builder", com.swdteam.common.crafting.RoundelBuilderRecipe.Serializer::new);


   public static IRecipeType<EngineeringTableRecipe> ENGINEERING_RECIPE = register(EngineeringTableRecipe.TYPE_ID, (IRecipeType<EngineeringTableRecipe>)new EngineeringTableRecipe.EngineeringTableRecipeType());
   public static IRecipeType<RoundelBuilderRecipe> ROUNDEL_RECIPE = register(RoundelBuilderRecipe.TYPE_ID, (IRecipeType<RoundelBuilderRecipe>)new RoundelBuilderRecipe.RoundelBuilderRecipeType());


  public static <C extends net.minecraft.inventory.IInventory, T extends net.minecraft.item.crafting.IRecipe<C>> IRecipeType<T> register(ResourceLocation type, IRecipeType<T> recipeType) {
     return (IRecipeType<T>)Registry.func_218322_a(Registry.field_218367_H, type, recipeType);
  }

  public static RegistryObject<IRecipeSerializer<?>> register(String name, Supplier<IRecipeSerializer<?>> serializer) {
     return RegistryHandler.RECIPE_SERIALIZERS.register(name, serializer);
  }
}


