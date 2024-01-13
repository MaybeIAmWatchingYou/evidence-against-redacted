package com.swdteam.client.tardis.data;

import com.swdteam.main.DalekMod;
import com.swdteam.model.javajson.JSONModel;
import com.swdteam.model.javajson.ModelLoader;
import com.swdteam.model.javajson.ModelRendererWrapper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;




public class ExteriorModels
{
   public static Map<ResourceLocation, DoorAnimations> ANIMATIONS = new HashMap<>();

  public static JSONModel getModel(ResourceLocation s) {
     return ModelLoader.loadModel(s);
  }

  public static DoorAnimations getDoorAnimation(ResourceLocation rl) {
     if (!ANIMATIONS.containsKey(rl)) {
       loadAnimations(rl);
    }

     return ANIMATIONS.get(rl);
  }

  public static class DoorAnimations {
     private List<TardisAnimation> door_animations = new ArrayList<>();

    public List<TardisAnimation> getDoorAnimations() {
       return this.door_animations;
    }

    public static class TardisAnimation {
      private String shape_name;
      private String mode;
      private String axis;
       private float amplifier = 1.0F;
      private String door_type;

      public float getAmplifier() {
         return this.amplifier;
      }

      public String getAxis() {
         return this.axis;
      }

      public String getMode() {
         return this.mode;
      }

      public String getShapeName() {
         return this.shape_name;
      }

      public String getDoorType() {
         return this.door_type; } } } public static class TardisAnimation { private String shape_name; private String mode; private String axis; private float amplifier; private String door_type; public TardisAnimation() { this.amplifier = 1.0F; } public float getAmplifier() { return this.amplifier; } public String getAxis() { return this.axis; } public String getDoorType() { return this.door_type; }
     public String getMode() {
      return this.mode;
    } public String getShapeName() {
      return this.shape_name;
    } }
  private static void loadAnimations(ResourceLocation res) {
     ResourceLocation rl = new ResourceLocation(res.getNamespace(), res.getPath().replaceFirst(".json", "_door.json"));

    try {
       InputStream stream = Minecraft.func_71410_x().func_195551_G().func_199002_a(rl).func_199027_b();
       BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
       StringBuilder b = new StringBuilder();
       String s = null;

       while ((s = reader.readLine()) != null) {
         b.append(s);
      }

       DoorAnimations model = (DoorAnimations)DalekMod.GSON.fromJson(b.toString(), DoorAnimations.class);

       if (model == null) {
         model = new DoorAnimations();
      }

       ANIMATIONS.put(res, model);
    }
     catch (Exception e) {
       e.printStackTrace();
       ANIMATIONS.put(res, new DoorAnimations());
    }
  }

  public static void resetDoorAnimation(ResourceLocation rl) {
     JSONModel model = getModel(rl);

     if (model != null && model.getModelData() != null) {

       DoorAnimations doors = getDoorAnimation(rl);
       if (doors != null && doors.getDoorAnimations() != null)
         for (DoorAnimations.TardisAnimation animation : doors.getDoorAnimations()) {
           Vector3f initialRotation; if (animation == null)
            continue;
           ModelRendererWrapper renderer = model.getModelData().getModel().getPart(animation.getShapeName());

           switch (animation.getMode().toLowerCase()) {
            case "rotate":
               switch (animation.getAxis().toLowerCase()) { case "x":
                   renderer.field_78795_f = 0.0F;
                 case "y": renderer.field_78796_g = 0.0F;
                 case "z": renderer.field_78808_h = 0.0F; }


            case "translate":
               initialRotation = renderer.getInitialRotation();
               switch (animation.getAxis().toLowerCase()) { case "x":
                   renderer.field_78800_c = initialRotation.func_195899_a();
                 case "y": renderer.field_78797_d = initialRotation.func_195900_b();
                 case "z": renderer.field_78798_e = initialRotation.func_195902_c(); }

          }
        }
    }
  }
}


