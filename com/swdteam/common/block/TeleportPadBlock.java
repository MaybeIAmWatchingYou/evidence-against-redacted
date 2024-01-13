package com.swdteam.common.block;

import com.swdteam.common.tardis.Location;
import com.swdteam.common.tileentity.TeleportPadTileEntity;
import com.swdteam.util.ChatUtil;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;


public class TeleportPadBlock
  extends TileEntityBaseBlock.WaterLoggable
{
   public static final VoxelShape SHAPE = VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.13D, 1.0D);

  public TeleportPadBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }


  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }




  public void func_180633_a(World world, BlockPos p_180633_2_, BlockState p_180633_3_, LivingEntity entity, ItemStack p_180633_5_) {
     if (!world.isRemote &&
       entity instanceof PlayerEntity) {
       TeleportPadTileEntity.getPads().put((PlayerEntity)entity, new Location(p_180633_2_, world.getDimensionKey()));
       ChatUtil.sendCompletedMsg((PlayerEntity)entity, "Place teleport reciever to initiate link", ChatUtil.MessageType.CHAT);
    }


     super.func_180633_a(world, p_180633_2_, p_180633_3_, entity, p_180633_5_);
  }
}


