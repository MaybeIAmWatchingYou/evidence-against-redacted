package com.swdteam.common.tardis;

import java.io.Serializable;
import net.minecraft.util.ResourceLocation;


public class Tardis
  implements Serializable
{
  private static final long serialVersionUID = 920819315931307870L;
  private String regName;
  private Data data;
  private transient ResourceLocation registryName;

  public Tardis(String s) {
     this.regName = s;
  }

  public Data getData() {
     return this.data;
  }

  public String getRegName() {
     return this.regName;
  }

  public void setData(Data data) {
     this.data = data;
  }

  public ResourceLocation getRegistryName() {
     if (this.registryName == null) {
       this.registryName = new ResourceLocation(getRegName());
    }

     return this.registryName;
  }
}


