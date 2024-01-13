package com.swdteam.model.javajson;

import java.util.ArrayList;
import java.util.List;

public class ModelRenderer
{
  public String group_name;
   public List<Float> rotation = new ArrayList<>();


   private List<ModelRenderer> children = new ArrayList<>();

   public List<Cube> cubes = new ArrayList<>();


  private float[] pivot;


  public ModelRenderer(ModelData model) {}


  public void setRotationPoint(float x, float y, float z) {
     this.pivot = new float[] { x, y, z };
  }

  public void addCube(int u, int v, float x, float y, float z, float w, float h, float d, float inflate, boolean mirror) {
     this.cubes.add(new Cube(u, v, x, y, z, w, h, d, inflate, mirror));
  }

  public void addCube(int u, int v, float x, float y, float z, float w, float h, float d, float inflate) {
     this.cubes.add(new Cube(u, v, x, y, z, w, h, d, inflate, false));
  }

  public void addCube(int u, int v, float x, float y, float z, float w, float h, float d) {
     this.cubes.add(new Cube(u, v, x, y, z, w, h, d, 0.0F, false));
  }

  public void addChild(ModelRenderer e) {
     this.children.add(e);
  }

  public float[] getPivot() {
     return this.pivot;
  }

  public List<ModelRenderer> getChildren() {
     return this.children;
  }

  public List<Cube> getCubes() {
     return this.cubes;
  }

  public float getxRot() {
     if (this.rotation == null || this.rotation.get(0) == null) return 0.0F;
     return (float)Math.toRadians(((Float)this.rotation.get(0)).floatValue());
  }

  public float getyRot() {
     if (this.rotation == null || this.rotation.get(1) == null) return 0.0F;
     return (float)Math.toRadians(((Float)this.rotation.get(1)).floatValue());
  }

  public float getzRot() {
     if (this.rotation == null || this.rotation.get(2) == null) return 0.0F;
     return (float)Math.toRadians(((Float)this.rotation.get(2)).floatValue());
  }
}


