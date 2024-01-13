package com.swdteam.common.block.tardis;

import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.tileentity.ScannerTileEntity;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class ScannerBlock
  extends RotatableTileEntityBase
{
  public ScannerBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }



  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (handIn == Hand.MAIN_HAND && !worldIn.isRemote && worldIn.getDimensionKey() == DMDimensions.TARDIS) {
       TileEntity te = worldIn.getTileEntity(pos);
       if (te instanceof ScannerTileEntity) {
         if (player.func_225608_bj_()) { ((ScannerTileEntity)te).changeScreenRelative(-1); }
         else { ((ScannerTileEntity)te).changeScreenRelative(1); }
          ((ScannerTileEntity)te).sendUpdates();
         worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_SCANNER.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
    }
     return ActionResultType.CONSUME;
  }
}


