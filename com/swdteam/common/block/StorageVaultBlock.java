package com.swdteam.common.block;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.tileentity.StorageVaultTileEntity;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class StorageVaultBlock extends ContainerBlock implements IDMTile {
   public static final BooleanProperty POWERED = BlockStateProperties.field_208194_u; public Supplier<TileEntity> tile;

  public StorageVaultBlock(Supplier<TileEntity> tile, AbstractBlock.Properties properties) {
     super(properties);
     this.tile = tile;
     func_180632_j((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a((Property)POWERED, Boolean.valueOf(false)));
  }

  protected void func_206840_a(StateContainer.Builder<Block, BlockState> state) {
     state.func_206894_a(new Property[] { (Property)POWERED });
  }


  public TileEntity func_196283_a_(IBlockReader reader) {
     return getTile().get();
  }



  public void func_196243_a(BlockState oldState, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
     if (oldState.getBlock() != newState.getBlock()) {
       TileEntity te = worldIn.getTileEntity(pos);
       if (te instanceof StorageVaultTileEntity) {
         InventoryHelper.func_219961_a(worldIn, pos, ((StorageVaultTileEntity)te).func_190576_q());
      }
    }
     super.func_196243_a(oldState, worldIn, pos, newState, isMoving);
  }


  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (!worldIn.isRemote) {
       TileEntity tileEntity = worldIn.getTileEntity(pos);
       if (tileEntity instanceof StorageVaultTileEntity) {
         if (((Boolean)state.get((Property)POWERED)).booleanValue() || player.func_175149_v()) {
           if (!player.func_175149_v()) worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.BLOCK_DEADLOCK_DOOR_OPEN.get(), SoundCategory.BLOCKS, 0.5F, 1.0F); 
           NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)tileEntity, pos);
        } else {
           player.func_146105_b((ITextComponent)new TranslationTextComponent("container.is_not_powered", new Object[] { ((LockableTileEntity)this.tile.get()).getName() }), true);
           player.func_213823_a(SoundEvents.field_187770_dm, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }
      }
    }

     return ActionResultType.SUCCESS;
  }

  public void func_180633_a(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
     if (stack.func_82837_s()) {
       TileEntity tileentity = world.getTileEntity(pos);
       if (tileentity instanceof StorageVaultTileEntity) {
         ((StorageVaultTileEntity)tileentity).func_213903_a(stack.func_200301_q());
      }
    }
  }


  public void func_220069_a(BlockState state, World world, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean isMoving) {
     boolean powered = world.func_175640_z(pos);
     if (powered != ((Boolean)state.get((Property)POWERED)).booleanValue()) world.setBlockState(pos, (BlockState)state.func_206870_a((Property)POWERED, Boolean.valueOf(powered)), 2); 
  }

  public void func_225534_a_(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
     boolean powered = world.func_175640_z(pos);
     if (powered != ((Boolean)state.get((Property)POWERED)).booleanValue()) world.setBlockState(pos, (BlockState)state.func_206870_a((Property)POWERED, Boolean.valueOf(powered)), 2); 
  }

  public BlockState func_196258_a(BlockItemUseContext context) {
     BlockPos blockpos = context.func_195995_a();
     World world = context.func_195991_k();
     boolean isPowered = world.func_175640_z(blockpos);
     return (BlockState)getDefaultState().func_206870_a((Property)POWERED, Boolean.valueOf(isPowered));
  }


  public Supplier<TileEntity> getTile() {
     return this.tile;
  }


  public BlockRenderType func_149645_b(BlockState state) {
     return BlockRenderType.MODEL;
  }

  public boolean func_149740_M(BlockState p_149740_1_) {
     return true;
  }

  public int func_180641_l(BlockState state, World world, BlockPos pos) {
     return Container.func_178144_a(world.getTileEntity(pos));
  }
}


