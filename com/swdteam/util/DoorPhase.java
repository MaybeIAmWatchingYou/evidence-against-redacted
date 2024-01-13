package com.swdteam.util;

import net.minecraft.util.IStringSerializable;

public enum DoorPhase implements IStringSerializable {
   OPEN("open"),
   MID("midphase"),
   CLOSED("closed");

  private final String name;

  DoorPhase(String name) {
     this.name = name;
  }

  public String toString() {
     return this.name;
  }

  public String func_176610_l() {
     return this.name;
  }
}


