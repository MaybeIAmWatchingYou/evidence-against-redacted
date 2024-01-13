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

public class UnlockExteriorTrigger
  extends AbstractCriterionTrigger<UnlockExteriorTrigger.Instance>
{
   private static final ResourceLocation ID = new ResourceLocation("dalekmod", "exterior_unlock");
  public ResourceLocation func_192163_a() {
     return ID;
  }
  
  public Instance createInstance(JsonObject json, EntityPredicate.AndPredicate predicate, ConditionArrayParser parser) {
     ResourceLocation resourcelocation = json.has("exterior") ? new ResourceLocation(JSONUtils.func_151200_h(json, "exterior")) : null;
     return new Instance(predicate, resourcelocation);
  }
  
  public void trigger(ServerPlayerEntity player, ResourceLocation exterior) {
     func_235959_a_(player, ext -> ext.matches(exterior));
  }
  
  public static class Instance
    extends CriterionInstance
  {
    private final ResourceLocation exterior;
    
    public Instance(EntityPredicate.AndPredicate predicate, ResourceLocation exterior) {
       super(UnlockExteriorTrigger.ID, predicate);
       this.exterior = exterior;
    }
    
    public JsonObject func_230240_a_(ConditionArraySerializer serializer) {
       JsonObject json = super.func_230240_a_(serializer);
       if (this.exterior != null) json.addProperty("exterior", this.exterior.toString()); 
       return json;
    }
    
    public boolean matches(ResourceLocation exterior) {
       return (this.exterior == null || this.exterior.equals(exterior));
    }
  }
}


