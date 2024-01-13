package com.swdteam.common.tileentity.tardis;

import com.mojang.authlib.GameProfile;
import com.swdteam.common.init.DMBlockEntities;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tileentity.DMTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.BlockPos;

public class TardisPlantTileEntity extends DMTileEntityBase {
  private GameProfile owner;
   private int age = 0;

  public TardisPlantTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_TARDIS_PLANT.get());
  }


  public CompoundNBT func_189515_b(CompoundNBT compound) {
     compound.putInt(DMNBTKeys.AGE, this.age);

     if (this.owner != null) {
       CompoundNBT compoundnbt = new CompoundNBT();
       NBTUtil.func_180708_a(compoundnbt, this.owner);
       compound.func_218657_a(DMNBTKeys.OWNER, (INBT)compoundnbt);
    }

     return super.func_189515_b(compound);
  }


  public void read(BlockState state, CompoundNBT compound) {
     if (compound.func_74764_b(DMNBTKeys.AGE)) {
       this.age = compound.getInt(DMNBTKeys.AGE);
    }

     if (compound.func_150297_b(DMNBTKeys.OWNER, 10)) {
       setOwner(NBTUtil.func_152459_a(compound.func_74775_l(DMNBTKeys.OWNER)));
     } else if (compound.func_150297_b(DMNBTKeys.EXTRA, 8)) {
       String s = compound.func_74779_i(DMNBTKeys.EXTRA);
       if (!StringUtils.func_151246_b(s)) {
         setOwner(new GameProfile((UUID)null, s));
      }
    }

     super.read(state, compound);
  }

  public void setOwner(GameProfile owner) {
     this.owner = owner;
  }

  public int getAge() {
     if (this.age > 6) {
       this.age = 6;
    }

     return this.age;
  }

  public void addAge() {
     if (this.age + 1 > 6) {
       BlockPos pos = getPos();

       this.world.setBlockState(pos, ((Block)DMBlocks.TARDIS.get()).getDefaultState(), 3);
       TileEntity te = this.world.getTileEntity(pos);
       if (te != null && te instanceof TardisTileEntity) {
         TardisTileEntity tardis = (TardisTileEntity)te;
         TardisData data = DMTardis.addTardis(pos, null);
         data.setCurrentLocation(pos, this.world.getDimensionKey());

         if (getOwner().isComplete()) {
           data.setOwnerUUID(getOwner().getId());
           data.setOwnerName(getOwner().getName());
        }

         data.save();
         tardis.globalID = data.getGlobalID();
         tardis.pulses = 1.0F;
         tardis.dematTime = 0.0F;
         tardis.state = TardisState.NEUTRAL;
      }
    } else {

       this.age++;
    }
     sendUpdates();
  }

  public GameProfile getOwner() {
     return this.owner;
  }
}


