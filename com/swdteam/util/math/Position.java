package com.swdteam.util.math;

import java.io.Serializable;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.math.BlockPos;



public class Position
  implements Serializable, IPosition
{
  private static final long serialVersionUID = 5003712931359763244L;
  private double xPos;
  private double yPos;
  private double zPos;

  public Position(double x, double y, double z) {
     this.xPos = x;
     this.yPos = y;
     this.zPos = z;
  }

  public BlockPos toBlockPos() {
     return new BlockPos(func_82615_a(), func_82617_b(), func_82616_c());
  }


  public double func_82615_a() {
     return this.xPos;
  }


  public double func_82617_b() {
     return this.yPos;
  }


  public double func_82616_c() {
     return this.zPos;
  }

  public void setxPos(double xPos) {
     this.xPos = xPos;
  }

  public void setyPos(double yPos) {
     this.yPos = yPos;
  }

  public void setzPos(double zPos) {
     this.zPos = zPos;
  }
}


