package com.swdteam.util;

import net.minecraft.util.IStringSerializable;

public enum LightAlternatorToggle implements IStringSerializable {
   LEFT,
   RIGHT;

  public String toString() {
     return func_176610_l();
  }

  public String func_176610_l() {
     return (this == LEFT) ? "left" : "right";
  }

  public LightAlternatorToggle invert() {
     return (this == LEFT) ? RIGHT : LEFT;
  }
}


