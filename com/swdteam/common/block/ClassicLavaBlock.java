package com.swdteam.common.block;

import java.util.Collections;
import java.util.List;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ClassicLavaBlock extends Block {
  public ClassicLavaBlock(AbstractBlock.Properties properties) {
     super(properties);
  }





  public boolean func_200123_i(BlockState state, IBlockReader reader, BlockPos pos) {
     return false;
  }

  public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
     return false;
  }

  @OnlyIn(Dist.CLIENT)
  public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
     return (adjacentBlockState.getBlockState() == state);
  }

  public BlockRenderType func_149645_b(BlockState state) {
     return BlockRenderType.MODEL;
  }

  public List<ItemStack> func_220076_a(BlockState state, LootContext.Builder builder) {
     return Collections.emptyList();
  }

  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return VoxelShapes.func_197880_a();
  }

  public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
     entityIn.func_233571_b_((ITag)FluidTags.field_206960_b);
  }
}


