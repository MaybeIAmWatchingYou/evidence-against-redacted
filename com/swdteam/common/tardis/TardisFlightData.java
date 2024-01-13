package com.swdteam.common.tardis;

import com.swdteam.main.DMConfig;
import java.io.Serializable;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.fml.server.ServerLifecycleHooks;







public class TardisFlightData
  implements Serializable
{
  private static final long serialVersionUID = 430270355854494695L;
  private float rotationAngle;
  private boolean recalculateLandingY = true;
  private boolean enteredFlight;
  private double xPos;
  private double yPos;
  private double zPos;
  private String dimensionKey;
  private long flightStartTime;
  private transient ListNBT waypoints;
  
  public TardisFlightData() {
     this.flightStartTime = System.currentTimeMillis() / 1000L;
     this.recalculateLandingY = true;
  }
  
  public RegistryKey<World> dimensionWorldKey() {
     return RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(this.dimensionKey));
  }
  
  public String getDimension() {
     return this.dimensionKey;
  }
  
  public double getPos(Direction.Axis axis) {
     switch (axis) {
      case X:
         return this.xPos;
      case Y:
         return this.yPos;
      case Z:
         return this.zPos;
    } 
     return 0.0D;
  }

  
  public void setPos(double amt, Direction.Axis axis) {
     switch (axis) {
      case X:
         this.xPos = amt;
        break;
      case Y:
         this.yPos = amt;
        break;
      case Z:
         this.zPos = amt;
        break;
    } 
     limitPos();
  }
  
  public void incrementPos(int amt, Direction.Axis axis) {
     switch (axis) {
      case X:
         this.xPos += amt;
        break;
      case Y:
         this.yPos += amt;
        break;
      case Z:
         this.zPos += amt;
        break;
    } 
     limitPos();
  }
  
  private void limitPos() {
     if (ServerLifecycleHooks.getCurrentServer() != null) {
       WorldBorder border = ServerLifecycleHooks.getCurrentServer().getWorld(dimensionWorldKey()).func_175723_af();
       if (this.xPos >= border.func_177728_d()) this.xPos = border.func_177728_d() - 1.0D;
       if (this.zPos >= border.func_177733_e()) this.zPos = border.func_177733_e() - 1.0D;
       if (this.xPos <= border.func_177726_b()) this.xPos = border.func_177726_b() + 1.0D;
       if (this.zPos <= border.func_177736_c()) this.zPos = border.func_177736_c() + 1.0D;
    
    } 
     if (this.yPos > 255.0D) this.yPos = 255.0D;
     if (this.yPos < ((Integer)DMConfig.COMMON.tardisYMin.get()).intValue()) this.yPos = ((Integer)DMConfig.COMMON.tardisYMin.get()).intValue();
  }
  
  public void setDimensionKey(RegistryKey<World> key) {
     this.dimensionKey = key.getRegistryLocation().toString();
  }
  
  public long getFlightStartTime() {
     return this.flightStartTime;
  }
  
  public boolean shouldRecalculateLanding() {
     return this.recalculateLandingY;
  }






  
  public void recalculateLanding(boolean recalculateLandingY) {
     this.recalculateLandingY = recalculateLandingY;
  }
  
  public boolean hasEnteredFlight() {
     return this.enteredFlight;
  }
  
  public void setEnteredFlight(boolean enteredFlight) {
     this.enteredFlight = enteredFlight;
  }
  
  public float getRotationAngle() {
     return this.rotationAngle;
  }
  
  public void setRotationAngle(float rotationAngle) {
     this.rotationAngle = rotationAngle;
  }
  
  public void setFlightStartTime() {
     this.flightStartTime = System.currentTimeMillis() / 1000L;
  }
  
  public ListNBT getWaypoints() {
     return this.waypoints;
  }
  
  public void setWaypoints(ListNBT waypoints) {
     this.waypoints = waypoints;
  }
  
  public BlockPos getPos() {
     return new BlockPos(this.xPos, this.yPos, this.zPos);
  }
  
  public void setPos(BlockPos pos) {
     this.xPos = pos.getX();
     this.yPos = pos.getY();
     this.zPos = pos.getZ();
  }
}


