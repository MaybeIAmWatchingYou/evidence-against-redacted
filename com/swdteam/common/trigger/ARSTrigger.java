package com.swdteam.common.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ARSTrigger
  extends AbstractCriterionTrigger<ARSTrigger.Instance>
{
   private static final ResourceLocation ID = new ResourceLocation("dalekmod", "ars_build");
  public ResourceLocation func_192163_a() {
     return ID;
  }
  
  public Instance createInstance(JsonObject json, EntityPredicate.AndPredicate predicate, ConditionArrayParser parser) {
     ResourceLocation resourcelocation = json.has("interior") ? new ResourceLocation(JSONUtils.func_151200_h(json, "interior")) : null;
     return new Instance(predicate, resourcelocation);
  }
  
  public void trigger(ServerPlayerEntity player, ResourceLocation interiors) {
     func_235959_a_(player, ints -> ints.matches(interiors));
  }
  
  public static class Instance
    extends CriterionInstance
  {
    private final ResourceLocation interior;
    
    public Instance(EntityPredicate.AndPredicate predicate, ResourceLocation interior) {
       super(ARSTrigger.ID, predicate);
       this.interior = interior;
    }
    
    public JsonObject func_230240_a_(ConditionArraySerializer serializer) {
       JsonObject json = super.func_230240_a_(serializer);
       if (this.interior != null) json.addProperty("interior", this.interior.toString());
       return json;
    }
    
    public boolean matches(ResourceLocation interior) {
       return (this.interior == null || this.interior.equals(interior));
    }
  }
}


