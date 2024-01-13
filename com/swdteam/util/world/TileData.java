package com.swdteam.util.world;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.Serializable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;


public class TileData
  implements Serializable
{
  private static final long serialVersionUID = 1852411413481453497L;
   private int[] pos = new int[3];
  
  private String nbtData;
  
  private transient CompoundNBT nbtDataObject;

  
  public CompoundNBT getNbtData() {
     if (this.nbtDataObject == null) {
      try {
         this.nbtDataObject = JsonToNBT.getTagFromJson(this.nbtData);
       } catch (CommandSyntaxException e) {
         e.printStackTrace();
      } 
    }
     return this.nbtDataObject;
  }
  
  public int[] getPos() {
     return this.pos;
  }
  
  public void setNbtData(CompoundNBT nbtData) {
     this.nbtData = nbtData.toString();
  }
  
  public void setPos(int[] pos) {
     this.pos = pos;
  }
  
  public String getNbtDataString() {
     return this.nbtData;
  }
}


