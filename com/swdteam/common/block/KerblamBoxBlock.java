package com.swdteam.common.block;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.tileentity.KerblamBoxTileEntity;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class KerblamBoxBlock extends ContainerBlock implements IDMTile {
   public static final DirectionProperty FACING = HorizontalBlock.field_185512_D;
  public Supplier<TileEntity> tile;
   public static final ResourceLocation CONTENTS = new ResourceLocation("contents");

  public KerblamBoxBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties prop) {
     super(prop);
     this.tile = tileEntitySupplier;
  }

  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }

  public BlockState func_196258_a(BlockItemUseContext context) {
     return (BlockState)getDefaultState().func_206870_a((Property)FACING, (Comparable)context.func_195992_f().func_176746_e());
  }

  public BlockState func_185499_a(BlockState state, Rotation rot) {
     return (BlockState)state.func_206870_a((Property)FACING, (Comparable)rot.func_185831_a((Direction)state.get((Property)FACING)));
  }


  public boolean hasTileEntity(BlockState state) {
     return true;
  }


  @Nullable
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
     return this.tile.get();
  }

  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     builder.func_206894_a(new Property[] { (Property)FACING });
  }


  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (!worldIn.isRemote) {
       TileEntity tileEntity = worldIn.getTileEntity(pos);
       if (tileEntity instanceof KerblamBoxTileEntity) {
         if (!player.func_175149_v()) worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.BLOCK_CARDBOARD_OPEN.get(), SoundCategory.BLOCKS, 0.5F, 1.0F); 
         NetworkHooks.openGui((ServerPlayerEntity)player, (INamedContainerProvider)tileEntity, pos);
      }
    }
     return ActionResultType.SUCCESS;
  }

  public void func_180633_a(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, LivingEntity p_180633_4_, ItemStack p_180633_5_) {
     if (p_180633_5_.func_82837_s()) {
       TileEntity tileentity = p_180633_1_.getTileEntity(p_180633_2_);
       if (tileentity instanceof KerblamBoxTileEntity) {
         ((KerblamBoxTileEntity)tileentity).func_213903_a(p_180633_5_.func_200301_q());
      }
    }
  }



  public ItemStack func_185473_a(IBlockReader p_185473_1_, BlockPos p_185473_2_, BlockState p_185473_3_) {
     ItemStack itemstack = super.func_185473_a(p_185473_1_, p_185473_2_, p_185473_3_);
     KerblamBoxTileEntity tile = (KerblamBoxTileEntity)p_185473_1_.getTileEntity(p_185473_2_);
     CompoundNBT compoundnbt = tile.saveToTag(new CompoundNBT());
     if (!compoundnbt.isEmpty()) {
       itemstack.func_77983_a("BlockEntityTag", (INBT)compoundnbt);
    }

     return itemstack;
  }


  public void func_176208_a(World p_176208_1_, BlockPos p_176208_2_, BlockState p_176208_3_, PlayerEntity p_176208_4_) {
     TileEntity tileentity = p_176208_1_.getTileEntity(p_176208_2_);
     if (tileentity instanceof KerblamBoxTileEntity) {
       KerblamBoxTileEntity tile = (KerblamBoxTileEntity)tileentity;
       if (!p_176208_1_.isRemote && p_176208_4_.isCreative() && !tile.func_191420_l()) {
         ItemStack itemstack = func_185473_a((IBlockReader)p_176208_1_, p_176208_2_, p_176208_3_);
         CompoundNBT compoundnbt = tile.saveToTag(new CompoundNBT());
         if (!compoundnbt.isEmpty()) {
           itemstack.func_77983_a("BlockEntityTag", (INBT)compoundnbt);
        }

         if (tile.func_145818_k_()) {
           itemstack.func_200302_a(tile.func_200201_e());
        }

         ItemEntity itementity = new ItemEntity(p_176208_1_, p_176208_2_.getX() + 0.5D, p_176208_2_.getY() + 0.5D, p_176208_2_.getZ() + 0.5D, itemstack);
         itementity.setDefaultPickupDelay();
         p_176208_1_.addEntity((Entity)itementity);
      } else {
         tile.func_184281_d(p_176208_4_);
      }
    }

     super.func_176208_a(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);
  }


  public List<ItemStack> func_220076_a(BlockState p_220076_1_, LootContext.Builder p_220076_2_) {
     TileEntity tileentity = (TileEntity)p_220076_2_.func_216019_b(LootParameters.field_216288_h);
     if (tileentity instanceof KerblamBoxTileEntity) {
       KerblamBoxTileEntity tile = (KerblamBoxTileEntity)tileentity;
       p_220076_2_ = p_220076_2_.func_216017_a(CONTENTS, (p_220168_1_, p_220168_2_) -> {
            for (int i = 0; i < tile.func_70302_i_(); i++) {
              p_220168_2_.accept(tile.func_70301_a(i));
            }
          });
    }


     return super.func_220076_a(p_220076_1_, p_220076_2_);
  }


  public Supplier<TileEntity> getTile() {
     return this.tile;
  }
  public boolean func_149740_M(BlockState state) {
     return true;
  }

  public int func_180641_l(BlockState state, World world, BlockPos pos) {
     return Container.func_178144_a(world.getTileEntity(pos));
  }


  public TileEntity func_196283_a_(IBlockReader p_196283_1_) {
     return getTile().get();
  }
}


