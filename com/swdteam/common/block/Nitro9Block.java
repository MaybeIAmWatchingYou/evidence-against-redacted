package com.swdteam.common.block;

import com.swdteam.common.tileentity.Nitro9TileEntity;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;


public class Nitro9Block
  extends TileEntityBaseBlock.WaterLoggable
{
   protected static final VoxelShape SHAPE = Block.func_208617_a(6.0D, 0.0D, 6.0D, 10.0D, 12.0D, 10.0D);

  public Nitro9Block(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }


  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }


  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }



  public void func_180652_a(World worldIn, BlockPos pos, Explosion explosionIn) {
     if (!worldIn.isRemote) {
       worldIn.createExplosion((Entity)explosionIn.func_94613_c(), pos.getX(), pos.getY(), pos.getZ(), 10.0F, Explosion.Mode.DESTROY);
    }
     super.func_180652_a(worldIn, pos, explosionIn);
  }



  public void func_196262_a(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
     if (entityIn instanceof net.minecraft.entity.projectile.ArrowEntity) {
       igniteMe(worldIn, pos, entityIn);
    }
     super.func_196262_a(state, worldIn, pos, entityIn);
  }


  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     igniteMe(worldIn, pos, (Entity)player);


     return ActionResultType.CONSUME;
  }


  public void igniteMe(World worldIn, BlockPos pos, Entity ent) {
     TileEntity e = worldIn.getTileEntity(pos);
     ent.func_184185_a(SoundEvents.field_187572_ar, 1.0F, 1.0F);
     if (e instanceof Nitro9TileEntity) {
       Nitro9TileEntity te = (Nitro9TileEntity)e;
       te.activator = ent;
       te.lit = true;
    }
  }

  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }

  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }


  public boolean func_149659_a(Explosion p_149659_1_) {
     return false;
  }
}


