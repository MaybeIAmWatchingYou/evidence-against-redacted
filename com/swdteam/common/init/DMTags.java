package com.swdteam.common.init;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;


public class DMTags
{
  public static class Blocks
  {
     public static final Tags.IOptionalNamedTag<Block> STEEL_BLOCKS = createTag("steel");
     public static final Tags.IOptionalNamedTag<Block> STEEL_BLOCKS_STAINLESS = createTag("stainless_steel");
     public static final Tags.IOptionalNamedTag<Block> STEEL_BLOCKS_DIAMONDPLATE = createTag("diamond_plated_steel");
     public static final Tags.IOptionalNamedTag<Block> STEEL_BLOCKS_PLATE = createTag("plated_steel");
     public static final Tags.IOptionalNamedTag<Block> DALEKANIUM_BLOCKS = createTag("dalekanium");
     public static final Tags.IOptionalNamedTag<Block> THALMA_LOGS = createTag("thalma_logs");
     public static final Tags.IOptionalNamedTag<Block> PLASTIC_BLOCKS = createTag("plastic");
     public static final Tags.IOptionalNamedTag<Block> SMOOTH_PLASTIC = createTag("smooth_plastic");
     public static final Tags.IOptionalNamedTag<Block> KALETITE_BRICKS = createTag("kaletite_bricks");
     public static final Tags.IOptionalNamedTag<Block> TARDIS_CONTROLS = createTag("tardis_controls");
     public static final Tags.IOptionalNamedTag<Block> RUSTED_BLOCKS = createTag("rusted");


    private static Tags.IOptionalNamedTag<Block> createTag(String name, Boolean itemTag) {
       if (itemTag.booleanValue()) ItemTags.createOptional(new ResourceLocation("dalekmod", name)); 
       return BlockTags.createOptional(new ResourceLocation("dalekmod", name));
    }

    private static Tags.IOptionalNamedTag<Block> createTag(String name) {
       return createTag(name, Boolean.valueOf(true));
    }
  }

  public static class Items
  {
     public static final Tags.IOptionalNamedTag<Item> SONICS = createTag("sonic_screwdriver");
     public static final Tags.IOptionalNamedTag<Item> MOSTLY_GOLD_ITEMS = createTag("mostly_gold");
     public static final Tags.IOptionalNamedTag<Item> ARTRON = createTag("artron");


    private static Tags.IOptionalNamedTag<Item> createTag(String name) {
       return ItemTags.createOptional(new ResourceLocation("dalekmod", name));
    }
  }

  public static class EntityTypes {
     public static final Tags.IOptionalNamedTag<EntityType<?>> GOLD = createTag("gold");


    private static Tags.IOptionalNamedTag<EntityType<?>> createTag(String name) {
       return EntityTypeTags.createOptional(new ResourceLocation("dalekmod", name));
    }
  }

  public static class Fluids
  {
    private static Tags.IOptionalNamedTag<Fluid> createTag(String name) {
       return FluidTags.createOptional(new ResourceLocation("dalekmod", name));
    }
  }
}


