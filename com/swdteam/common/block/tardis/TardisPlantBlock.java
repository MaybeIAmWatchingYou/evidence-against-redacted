package com.swdteam.common.block.tardis;

import com.swdteam.common.block.IDMTile;
import com.swdteam.common.tileentity.tardis.TardisPlantTileEntity;
import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TardisPlantBlock
  extends Block
  implements IDMTile
{
   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
       Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), 
       Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), 
       Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D), 
       Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D), 
       Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D), 
       Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D), 
       Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D), 
       Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D)
    };
  public Supplier<TileEntity> tileEntitySupplier;

  public TardisPlantBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(properties);
     this.tileEntitySupplier = tileEntitySupplier;
     func_180632_j((BlockState)this.field_176227_L.func_177621_b());
  }




  public void func_180633_a(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack item) {
     if (!world.isRemote && entity instanceof ServerPlayerEntity) {
       TileEntity tile = world.getTileEntity(pos);
       if (tile instanceof TardisPlantTileEntity) {
         TardisPlantTileEntity plant = (TardisPlantTileEntity)tile;
         plant.setOwner(((ServerPlayerEntity)entity).getGameProfile());
      }
    }

     super.func_180633_a(world, pos, state, entity, item);
  }

  public VoxelShape func_220053_a(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
     if (world.getTileEntity(pos) instanceof TardisPlantTileEntity) {
       TardisPlantTileEntity tile = (TardisPlantTileEntity)world.getTileEntity(pos);
       return SHAPE_BY_AGE[tile.getAge()];
    }
     return SHAPE_BY_AGE[0];
  }


  public VoxelShape func_220071_b(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
     if (world.getTileEntity(pos) instanceof TardisPlantTileEntity) {
       TardisPlantTileEntity tile = (TardisPlantTileEntity)world.getTileEntity(pos);
       if (tile.getAge() >= 4) return Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 31.0D, 16.0D); 
    }
     return VoxelShapes.func_197880_a();
  }

  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     mayPlaceOn(reader.getBlockState(pos.func_177977_b()), (IBlockReader)reader, pos.func_177977_b());
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }

  protected boolean mayPlaceOn(BlockState p_200014_1_, IBlockReader p_200014_2_, BlockPos p_200014_3_) {
     return (p_200014_1_.func_203425_a(Blocks.field_196658_i) || p_200014_1_.func_203425_a(Blocks.field_150346_d) || p_200014_1_.func_203425_a(Blocks.field_196660_k) || p_200014_1_.func_203425_a(Blocks.field_196661_l) || p_200014_1_.func_203425_a(Blocks.field_150458_ak));
  }


  public boolean hasTileEntity(BlockState state) {
     return true;
  }


  public Supplier<TileEntity> getTile() {
     return this.tileEntitySupplier;
  }


  @Nullable
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
     return this.tileEntitySupplier.get();
  }


  public boolean func_149653_t(BlockState state) {
     return true;
  }


  public void func_225542_b_(BlockState state, ServerWorld world, BlockPos pos, Random random) {
     if (world.getTileEntity(pos) instanceof TardisPlantTileEntity) {
       TardisPlantTileEntity tile = (TardisPlantTileEntity)world.getTileEntity(pos);
       if (random.nextInt() % 2 == 0) {
         tile.addAge();
      }
    }
     super.func_225542_b_(state, world, pos, random);
  }


  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.INVISIBLE;
  }


  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }
}


