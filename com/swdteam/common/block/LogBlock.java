package com.swdteam.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

public class LogBlock extends RotatedPillarBlock {
  private RegistryObject<Block> stripped;
  
  public LogBlock(AbstractBlock.Properties properties, RegistryObject<Block> block) {
     super(properties);
     this.stripped = block;
  }

  
  private BlockState getLogState(RegistryObject<Block> block) {
     return ((Block)block.get()).getDefaultState();
  }

  
  public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
     return (toolType == ToolType.AXE) ? getLogState(this.stripped) : null;
  }
}


