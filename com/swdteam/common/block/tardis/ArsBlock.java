package com.swdteam.common.block.tardis;

import com.swdteam.common.container.ArsContainer;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisInterior;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ArsBlock extends Block {
   public static final DirectionProperty FACING = HorizontalBlock.field_185512_D;
  
  public ArsBlock(AbstractBlock.Properties properties) {
     super(properties);
     func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a((Property)FACING, (Comparable)Direction.NORTH));
  }
  
  public BlockState func_196258_a(BlockItemUseContext context) {
     return (BlockState)getDefaultState().func_206870_a((Property)FACING, (Comparable)context.func_195992_f().getOpposite());
  }
  
  public BlockState func_185499_a(BlockState state, Rotation rot) {
     return (BlockState)state.func_206870_a((Property)FACING, (Comparable)rot.func_185831_a((Direction)state.get((Property)FACING)));
  }

  
  public BlockState func_185471_a(BlockState state, Mirror mirrorIn) {
     return state.func_185907_a(mirrorIn.func_185800_a((Direction)state.get((Property)FACING)));
  }
  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     builder.func_206894_a(new Property[] { (Property)FACING });
  }

  
  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }

  
  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
       NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 5));
       if (worldIn.getDimensionKey().equals(DMDimensions.TARDIS)) {
         TardisData data = DMTardis.getTardisFromInteriorPos(pos);
         if (data != null && data.hasPermission(player, TardisData.PermissionType.DEFAULT)) {
           if (data.getCurrentlyBuilding() != null && !DMTardisRegistry.getTardisInteriors().containsKey(new ResourceLocation(data.getCurrentlyBuilding()))) {
             data.setCurrentlyBuilding(null);
             data.save();
          } 
          
           if (data.getCurrentlyBuilding() != null) {
             TardisInterior interior = (TardisInterior)DMTardisRegistry.getTardisInteriors().get(new ResourceLocation(data.getCurrentlyBuilding()));
             if (interior.getRecipe() == null || interior.getRecipe().getParts() == null || (interior.getRecipe().getParts()).length <= 0) {
               data.setCurrentlyBuilding(null);
               data.save();
            } 
          } 
          
           if (data.getCurrentlyBuilding() == null) {
             NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 5));
          } else {
             NetworkHooks.openGui((ServerPlayerEntity)player, func_220052_b(state, worldIn, pos), pos);
          } 
        } 
      } 
    } 
     return ActionResultType.CONSUME;
  }
  
  @Nullable
  public INamedContainerProvider func_220052_b(BlockState blockState, World world, BlockPos blockPos) {
     return (INamedContainerProvider)new SimpleNamedContainerProvider((p_220283_2_, p_220283_3_, p_220283_4_) -> new ArsContainer(p_220283_2_, p_220283_3_, world, blockPos), (ITextComponent)DMTranslationKeys.GUI_ARS_NAME);
  }
}


