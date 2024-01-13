package com.swdteam.common.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.criterion.AbstractCriterionTrigger;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.loot.ConditionArraySerializer;
import net.minecraft.util.ResourceLocation;

public class VoidTeleportTrigger
  extends AbstractCriterionTrigger<VoidTeleportTrigger.Instance>
{
   private static final ResourceLocation ID = new ResourceLocation("dalekmod", "void_teleport");
  public ResourceLocation func_192163_a() {
     return ID;
  }

  public Instance createInstance(JsonObject json, EntityPredicate.AndPredicate predicate, ConditionArrayParser parser) {
     return new Instance(predicate);
  }

  public void trigger(ServerPlayerEntity player) {
     func_235959_a_(player, u_0 -> true);
  }

  public static class Instance
    extends CriterionInstance {
    public Instance(EntityPredicate.AndPredicate predicate) {
       super(VoidTeleportTrigger.ID, predicate);
    }

    public JsonObject func_230240_a_(ConditionArraySerializer serializer) {
       return super.func_230240_a_(serializer);
    }
  }
}


