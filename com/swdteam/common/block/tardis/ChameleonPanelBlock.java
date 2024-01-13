package com.swdteam.common.block.tardis;

import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import com.swdteam.network.packets.PacketXPSync;
import com.swdteam.util.ChatUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ChameleonPanelBlock
  extends AbstractRotateableWaterLoggableBlock {
   public static final VoxelShape PANEL_SHAPE_BASE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
   protected static final VoxelShape TEST = VoxelShapes.func_216384_a(PANEL_SHAPE_BASE, new VoxelShape[0]);
  
  public ChameleonPanelBlock(AbstractBlock.Properties properties) {
     super(properties);
  }

  
  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }


  
  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return TEST;
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return TEST;
  }



  
  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (!worldIn.isRemote && 
       worldIn.getDimensionKey().equals(DMDimensions.TARDIS)) {
      
       ItemStack stack = player.getHeldItem(handIn);
       if (stack != null && stack.getItem() == DMItems.CHAMELEON_CATRTIDGE.get() && 
         stack.func_77942_o() && stack.func_77978_p().func_74764_b("skin_id")) {
         ResourceLocation skinID = new ResourceLocation(stack.func_77978_p().func_74779_i("skin_id"));
         if (DMTardisRegistry.getRegistry().containsKey(skinID)) {
          
           TardisData data = DMTardis.getTardisFromInteriorPos(pos);
           if (data != null && 
             !data.isUnlocked(skinID)) {
             Tardis tardis = DMTardisRegistry.getExterior(skinID);
             if (tardis != null) {
               data.unlockExterior(skinID);
               ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)new TranslationTextComponent("msg.dalekmod.tardis.chameleon_unlocked", new Object[] { tardis.getData().getExteriorName() }), ChatUtil.MessageType.CHAT);
               player.getEntityWorld().playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CHAMELEON_PANEL_UNLOCK.get(), SoundCategory.BLOCKS, 0.5F, 1.0F);
               if (!player.isCreative()) {
                 stack.func_190918_g(1);
              }
               data.save();
               return ActionResultType.CONSUME;
            } 
          } 
        } else {
          
           ChatUtil.sendError(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_CHAMELEON_UNKNOWN, ChatUtil.MessageType.CHAT);
           return ActionResultType.FAIL;
        } 
      } 

      
       NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketXPSync(player.experienceTotal, false));
       NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 1));
       return ActionResultType.CONSUME;
    } 
    
     return ActionResultType.CONSUME;
  }
  
  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }
  
  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }
}


