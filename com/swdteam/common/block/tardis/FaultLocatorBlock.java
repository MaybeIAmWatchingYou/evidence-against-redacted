package com.swdteam.common.block.tardis;

import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.container.FaultLocatorContainer;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tileentity.tardis.FaultLocatorTileEntity;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FaultLocatorBlock extends RotatableTileEntityBase {
  public FaultLocatorBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }

  @Nullable
  public INamedContainerProvider func_220052_b(BlockState p_220052_1_, World p_220052_2_, BlockPos p_220052_3_) {
     return (INamedContainerProvider)new SimpleNamedContainerProvider((p_220283_2_, p_220283_3_, p_220283_4_) -> { TileEntity te = p_220052_2_.getTileEntity(p_220052_3_); return (Container)((te instanceof FaultLocatorTileEntity) ? new FaultLocatorContainer(p_220283_2_, p_220283_3_, (FaultLocatorTileEntity)te) : null); }(ITextComponent)DMTranslationKeys.GUI_FAULT_LOCATOR_NAME);
  }







  public ActionResultType func_225533_a_(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
     if (p_225533_2_.isRemote) {
       return ActionResultType.SUCCESS;
    }
     TileEntity te = p_225533_2_.getTileEntity(p_225533_3_);
     if (te != null && te instanceof FaultLocatorTileEntity) {
       NetworkHooks.openGui((ServerPlayerEntity)p_225533_4_, (INamedContainerProvider)te, p_225533_3_);
    }
     return ActionResultType.CONSUME;
  }



  public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
     if (!world.isRemote) {
       TileEntity te = world.getTileEntity(pos);
       if (te != null && te instanceof FaultLocatorTileEntity) {
         FaultLocatorTileEntity faultLocator = (FaultLocatorTileEntity)te;
         ItemStack itemstack = faultLocator.getFuelSlot();
         ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, world);
         itemEntity.func_70080_a((pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), 0.0F, 0.0F);

         itemEntity.setItem(itemstack);
         world.addEntity((Entity)itemEntity);
      }
    }

     return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
  }


  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }
}


