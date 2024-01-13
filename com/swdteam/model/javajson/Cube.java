package com.swdteam.model.javajson;


public class Cube
{
  private int[] uv;
  private float[] size;
  private float[] origin;
  private float inflate;
  private boolean mirror;
  
  public Cube(int u, int v, float x, float y, float z, float w, float h, float d, float inflate, boolean mirror) {
     this.uv = new int[] { u, v };
     this.origin = new float[] { x, y, z };
     this.size = new float[] { w, h, d };
     this.inflate = inflate;
     this.mirror = mirror;
  }
  
  public float getInflate() {
     return this.inflate;
  }
  
  public float[] getOrigin() {
     return this.origin;
  }
  
  public float[] getSize() {
     return this.size;
  }
  
  public int[] getUv() {
     return this.uv;
  }
  
  public boolean isMirrored() {
     return this.mirror;
  }
}


