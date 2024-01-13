package com.swdteam.common.block;

import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMSoundTypes;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.Property;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BetaChestBlock extends ChestBlock implements IDMTile {
  public Supplier<TileEntity> tile;
  
   private static final TileEntityMerger.ICallback<ChestTileEntity, Optional<INamedContainerProvider>> MENU_PROVIDER_COMBINER = new TileEntityMerger.ICallback<ChestTileEntity, Optional<INamedContainerProvider>>() {
      public Optional<INamedContainerProvider> acceptDouble(final ChestTileEntity p_225539_1_, final ChestTileEntity p_225539_2_) {
         final DoubleSidedInventory iinventory = new DoubleSidedInventory((IInventory)p_225539_1_, (IInventory)p_225539_2_);
         return Optional.of(new INamedContainerProvider() {
              @Nullable
              public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                 if (p_225539_1_.func_213904_e(p_createMenu_3_) && p_225539_2_.func_213904_e(p_createMenu_3_)) {
                   p_225539_1_.func_184281_d(p_createMenu_2_.field_70458_d);
                   p_225539_2_.func_184281_d(p_createMenu_2_.field_70458_d);
                   return (Container)ChestContainer.func_216984_b(p_createMenu_1_, p_createMenu_2_, iinventory);
                } 
                 return null;
              }

              
              public ITextComponent func_145748_c_() {
                 if (p_225539_1_.func_145818_k_()) {
                   return p_225539_1_.func_145748_c_();
                }
                 return p_225539_2_.func_145818_k_() ? p_225539_2_.func_145748_c_() : (ITextComponent)new TranslationTextComponent("container.dalekmod.beta_chest_double");
              }
            });
      }

      
      public Optional<INamedContainerProvider> acceptSingle(ChestTileEntity p_225538_1_) {
         return (Optional)Optional.of(p_225538_1_);
      }
      
      public Optional<INamedContainerProvider> acceptNone() {
         return Optional.empty();
      }
    };
  
  public BetaChestBlock(Supplier<TileEntity> tileEntitySupplier) {
     super(AbstractBlock.Properties.func_200945_a(Material.field_151575_d).func_200947_a((SoundType)DMSoundTypes.CLASSIC_WOOD), () -> (TileEntityType)DMBlockEntities.TILE_BETA_CHEST.get());

    
     this.tile = tileEntitySupplier;
     func_180632_j((BlockState)((BlockState)((BlockState)this.field_176227_L.func_177621_b()).func_206870_a((Property)field_176459_a, (Comparable)Direction.NORTH)).func_206870_a((Property)field_196314_b, (Comparable)ChestType.SINGLE));
  }

  
  @Nullable
  public INamedContainerProvider func_220052_b(BlockState p_220052_1_, World p_220052_2_, BlockPos p_220052_3_) {
     return ((Optional<INamedContainerProvider>)func_225536_a_(p_220052_1_, p_220052_2_, p_220052_3_, false).apply(MENU_PROVIDER_COMBINER)).orElse((INamedContainerProvider)null);
  }
  
  public Supplier<TileEntity> getTile() {
     return this.tile;
  }
  
  public TileEntity func_196283_a_(IBlockReader reader) {
     return getTile().get();
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader block, BlockPos pos, ISelectionContext context) {
     return Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
  }
  
  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }
  
  public boolean func_204510_a(IBlockReader p_204510_1_, BlockPos p_204510_2_, BlockState p_204510_3_, Fluid p_204510_4_) {
     return false;
  }
}


