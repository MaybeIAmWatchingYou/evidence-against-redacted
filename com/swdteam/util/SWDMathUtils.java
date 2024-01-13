package com.swdteam.util;

import com.swdteam.util.math.Position;
import java.util.Random;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;



public class SWDMathUtils
{
  public static Random RANDOM;

  public SWDMathUtils() {
     RANDOM = new Random();
  }

  public static Random getRandom() {
     return RANDOM;
  }

  public static float randomRange(float min, float max) {
     return min + (int)(Math.random() * (max - min + 1.0F));
  }

  public static double randomDouble(double min, double max) {
     return min + Math.random() * (max - min);
  }

  public static BlockPos getBlockPosFromPosition(IPosition vector3) {
     return new BlockPos(vector3.func_82615_a(), vector3.func_82617_b(), vector3.func_82616_c());
  }

  public static Position getPositionFromBlockPos(BlockPos blockPos) {
     return new Position(blockPos.getX(), blockPos.getY(), blockPos.getZ());
  }

  public static String rotationToCardinal(float rotation) {
     int a = Math.round(rotation / 45.0F);
     while (a < 0 || a > 7) {
       if (a < 0) a += 8; 
       if (a > 7) a -= 8; 
    }
     switch (a) {
      case 0:
         return "NORTH";
      case 1:
         return "NORTHEAST";
      case 2:
         return "EAST";
      case 3:
         return "SOUTHEAST";
      case 4:
         return "SOUTH";
      case 5:
         return "SOUTHWEST";
      case 6:
         return "WEST";
      case 7:
         return "NORTHWEST";
    }
     return "NORTH";
  }


  public static float cardinalToFloat(String cardinal) {
     String c = cardinal.toUpperCase();
     switch (c) {
      case "SOUTH":
         return 0.0F;
      case "SOUTHWEST":
         return 45.0F;
      case "WEST":
         return 90.0F;
      case "NORTHWEST":
         return 135.0F;
      case "NORTH":
         return 180.0F;
      case "NORTHEAST":
         return 225.0F;
      case "EAST":
         return 270.0F;
      case "SOUTHEAST":
         return 315.0F;
      case "RANDOM":
         return (float)(Math.random() * 360.0D);
    }
    try {
       return SnapRotationToCardinal(Integer.valueOf(cardinal).intValue());
     } catch (Exception e) {
      try {
         return SnapRotationToCardinal(Float.valueOf(cardinal).floatValue());
       } catch (Exception e2) {
         return 0.0F;
      }
    }
  }


  public static Vector3i cardinalToVec(String cardinal) {
     String c = cardinal.toUpperCase();
     switch (c) {
      case "SOUTH":
         return new Vector3i(0, 0, 1);
      case "SOUTHWEST":
         return new Vector3i(-1, 0, 1);
      case "WEST":
         return new Vector3i(-1, 0, 0);
      case "NORTHWEST":
         return new Vector3i(-1, 0, -1);
      case "NORTH":
         return new Vector3i(0, 0, -1);
      case "NORTHEAST":
         return new Vector3i(1, 0, -1);
      case "EAST":
         return new Vector3i(1, 0, 0);
      case "SOUTHEAST":
         return new Vector3i(1, 0, 1);
    }
     return new Vector3i(0, 0, 0);
  }


  public static String rotationToCardinal4(float rotation) {
     int a = Math.round(rotation / 90.0F);
     while (a < 0 || a > 3) {
       if (a < 0) a += 4; 
       if (a > 3) a -= 4; 
    }
     switch (a) {
      case 0:
         return "SOUTH";
      case 1:
         return "WEST";
      case 2:
         return "NORTH";
      case 3:
         return "EAST";
    }
     return "SOUTH";
  }


  public static float SnapRotationToCardinal(float rotation) {
     int a = Math.round(rotation / 45.0F);
     while (a < 0 || a > 7) {
       if (a < 0) a += 8; 
       if (a > 7) a -= 8; 
    }
     return (a * 45);
  }
}


