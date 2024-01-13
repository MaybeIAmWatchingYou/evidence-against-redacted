package com.swdteam.common.tileentity;

import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMNBTKeys;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

public class ForceFieldFloorTileEntity
  extends DMTileEntityBase
  implements ITickableTileEntity
{
  private static final int TIME_LIMIT = 120;
   public ResourceLocation liquid = Fluids.field_204541_a.getRegistryName();

   int ticksLeft = 120;
  boolean tardisAbove = true;
  boolean hasBeenSteppedOn = false;

  public ForceFieldFloorTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_FORCE_FIELD.get());
  }

  public void removeBlock(boolean destroyOthers) {
     if (destroyOthers) {
       AxisAlignedBB floorAABB = new AxisAlignedBB(this.field_174879_c.func_177982_a(-1, 0, -1), this.field_174879_c.func_177982_a(1, 0, 1));

       BlockPos.func_239581_a_(floorAABB).forEach(pos -> {
            if (this.world.getBlockState(pos).getBlock() instanceof com.swdteam.common.block.ForceFieldFloorBlock) {
              ((ForceFieldFloorTileEntity)this.world.getTileEntity(pos)).removeBlock(false);
            }
          });
      return;
    }
     if (ForgeRegistries.FLUIDS.getValue(this.liquid) != null) {
       BlockState state = ((Fluid)ForgeRegistries.FLUIDS.getValue(this.liquid)).func_207188_f().func_206883_i();
       if (state != null) this.world.setBlockState(getPos(), state);
    } else {
       this.world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
    }
  }

  public CompoundNBT func_189515_b(CompoundNBT nbt) {
     super.func_189515_b(nbt);
     nbt.func_74778_a(DMNBTKeys.FORCE_FIELD_LIQUID, this.liquid.toString());

     return nbt;
  }


  public void read(BlockState state, CompoundNBT nbt) {
     if (nbt.func_74764_b(DMNBTKeys.FORCE_FIELD_LIQUID)) this.liquid = new ResourceLocation(nbt.func_74779_i(DMNBTKeys.FORCE_FIELD_LIQUID));

     super.read(state, nbt);
  }


  public void tick() {
     if (this.world.isRemote || !this.tardisAbove)
       return;  if (!(this.world.getBlockState(this.field_174879_c.func_177984_a()).getBlock() instanceof com.swdteam.common.block.tardis.TardisBlock)) this.tardisAbove = false;
     AxisAlignedBB aboveAABB = new AxisAlignedBB(this.field_174879_c.func_177982_a(2, 0, 2), this.field_174879_c.func_177982_a(-1, 3, -1));

     if (this.world.func_72839_b(null, aboveAABB).size() == 0) {
       if (this.hasBeenSteppedOn) this.ticksLeft--;
    } else {
       this.hasBeenSteppedOn = true;
       this.ticksLeft = 120;
    }
     if (this.ticksLeft <= 0) removeBlock(true);
  }
}


