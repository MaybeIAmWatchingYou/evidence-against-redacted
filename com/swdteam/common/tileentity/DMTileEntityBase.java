package com.swdteam.common.tileentity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;





public class DMTileEntityBase
  extends TileEntity
{
  public DMTileEntityBase(TileEntityType<?> tileEntityTypeIn) {
     super(tileEntityTypeIn);
  }


  public CompoundNBT func_189517_E_() {
     return func_189515_b(new CompoundNBT());
  }


  public SUpdateTileEntityPacket func_189518_D_() {
     CompoundNBT nbt = new CompoundNBT();
     func_189515_b(nbt);

     return new SUpdateTileEntityPacket(getPos(), 0, nbt);
  }


  public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
     read(this.world.getBlockState(getPos()), pkt.func_148857_g());
  }

  public void sendUpdates() {
     this.world.func_225319_b(getPos(), this.world.getBlockState(getPos()), this.world.getBlockState(getPos()));
     this.world.func_184138_a(getPos(), this.world.getBlockState(getPos()), this.world.getBlockState(getPos()), 3);
     markDirty();
  }
}


