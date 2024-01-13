package com.swdteam.common.block;
import com.swdteam.common.container.ArtronFuelTankContainer;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tileentity.ArtronFuelTankTileEntity;
import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class ArtronFuelTankBlock extends TileEntityBaseBlock.WaterLoggable {
   public static final VoxelShape SHAPE = VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 0.9999D, 1.5D, 0.9999D);
  
  public ArtronFuelTankBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }

  
  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return SHAPE;
  }

  
  public BlockRenderType func_149645_b(BlockState state) {
     return BlockRenderType.INVISIBLE;
  }

  
  public boolean func_149653_t(BlockState state) {
     return true;
  }

  
  public void func_225542_b_(BlockState state, ServerWorld world, BlockPos pos, Random random) {
     if (world.getTileEntity(pos) instanceof ArtronFuelTankTileEntity) {
       ArtronFuelTankTileEntity tile = (ArtronFuelTankTileEntity)world.getTileEntity(pos);
       if (tile.charge < 100.0D) {
         tile.charge += 0.5D;
         if (tile.charge > 100.0D) tile.charge = 100.0D; 
         if (!tile.fuelSlot.func_190926_b()) tile.fuelSlot = tile.fillGlass(tile.fuelSlot); 
         tile.sendUpdates();
      } 
    } 
     super.func_225542_b_(state, world, pos, random);
  }
  
  @Nullable
  public INamedContainerProvider func_220052_b(BlockState state, World world, BlockPos pos) {
     return (INamedContainerProvider)new SimpleNamedContainerProvider((p_220283_2_, p_220283_3_, p_220283_4_) -> { TileEntity te = world.getTileEntity(pos); return (Container)((te instanceof ArtronFuelTankTileEntity) ? new ArtronFuelTankContainer(p_220283_2_, p_220283_3_, (ArtronFuelTankTileEntity)te) : null); }(ITextComponent)DMTranslationKeys.GUI_FAULT_LOCATOR_NAME);
  }






  
  public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult rayTraceResult) {
     if (world.isRemote) {
       return ActionResultType.SUCCESS;
    }
     TileEntity te = world.getTileEntity(pos);
     if (te != null && te instanceof ArtronFuelTankTileEntity) {
       NetworkHooks.openGui((ServerPlayerEntity)entity, (INamedContainerProvider)te, pos);
    }
     return ActionResultType.CONSUME;
  }
  
  public boolean func_149740_M(BlockState state) {
     return true;
  }
  
  public int func_180641_l(BlockState state, World world, BlockPos pos) {
     if (world.getTileEntity(pos) instanceof ArtronFuelTankTileEntity) {
       ArtronFuelTankTileEntity tile = (ArtronFuelTankTileEntity)world.getTileEntity(pos);
       return (int)(tile.charge / 100.0D * 16.0D);
    } 
     return 0;
  }
}


