package com.swdteam.common.tileentity.tardis;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.tardis.data.TardisLocationRegistry;
import com.swdteam.common.tileentity.DMTileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;

public class DimensionSelectorTileEntity extends DMTileEntityBase {
  private int index;

  public DimensionSelectorTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_DIMENSION_SELECTOR.get());


     this.index = 0;
  }
  private ResourceLocation texturePath;

  public CompoundNBT func_189515_b(CompoundNBT compound) {
     if (this.texturePath != null) {
       compound.func_74778_a(DMNBTKeys.DIM_SELECTOR_TEXTURE, this.texturePath.getNamespace() + ":" + this.texturePath.getPath());
       compound.putInt(DMNBTKeys.DIM_SELECTOR_INDEX, this.index);
    } else {
       ResourceLocation rl = ((TardisLocationRegistry.TardisLocation)TardisLocationRegistry.getLocationRegistryAsList().get(0)).getDimensionImage();
       compound.func_74778_a(DMNBTKeys.DIM_SELECTOR_TEXTURE, rl.getNamespace() + ":" + rl.getPath());
       compound.putInt(DMNBTKeys.DIM_SELECTOR_INDEX, 0);
    }

     return super.func_189515_b(compound);
  }


  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.DIM_SELECTOR_TEXTURE)) {
       this.texturePath = new ResourceLocation(compound.func_74779_i(DMNBTKeys.DIM_SELECTOR_TEXTURE));
    }

     if (compound.func_74764_b(DMNBTKeys.DIM_SELECTOR_INDEX)) {
       this.index = compound.getInt(DMNBTKeys.DIM_SELECTOR_INDEX);
    }

     super.read(state, compound);
  }

  public ResourceLocation getTexturePath() {
     return this.texturePath;
  }

  public void setTexturePath(ResourceLocation texturePath) {
     this.texturePath = texturePath;
  }

  public int getIndex() {
     return this.index;
  }

  public void setIndex(int index) {
     this.index = index;
  }
}


