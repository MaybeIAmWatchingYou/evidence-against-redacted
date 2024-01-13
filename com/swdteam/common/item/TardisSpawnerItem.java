package com.swdteam.common.item;

import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.ItemUtils;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class TardisSpawnerItem
  extends Item
{
  public TardisSpawnerItem(Item.Properties properties) {
     super(properties.func_200917_a(1));
  }





  public ActionResultType func_195939_a(ItemUseContext context) {
     if (!(context.func_195991_k()).isRemote) {

       World world = context.func_195991_k();
       BlockPos pos = context.func_195995_a().func_177984_a();
       BlockState blockState = world.getBlockState(pos);

       if (blockState.getBlock() == Blocks.AIR) {

         world.setBlockState(pos, ((Block)DMBlocks.TARDIS.get()).getDefaultState());

         TileEntity tileEntity = world.getTileEntity(pos);
         if (tileEntity != null && tileEntity instanceof TardisTileEntity) {
           TardisTileEntity tileEntityTardis = (TardisTileEntity)tileEntity;

           TardisData data = DMTardis.addTardis(pos, context.func_195999_j());
           data.setCurrentLocation(pos, world.getDimensionKey());

           data.save();

           tileEntityTardis.globalID = data.getGlobalID();
           tileEntityTardis.pulses = 1.0F;
           tileEntityTardis.dematTime = 0.0F;
           tileEntityTardis.state = TardisState.NEUTRAL;



           tileEntityTardis.rotation = context.func_195999_j().getRotationYawHead();

           tileEntityTardis.sendUpdates();
           context.func_195996_i().func_190918_g(1);
        }
      }
    }

     return super.func_195939_a(context);
  }

  public void func_77624_a(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
     super.func_77624_a(stack, worldIn, tooltip, flagIn);
     ItemUtils.addText(tooltip, "Time And Relative Dimensions In Space", TextFormatting.BLUE);
  }
}


