package com.swdteam.common.init;

import com.swdteam.common.trigger.ARSTrigger;
import com.swdteam.common.trigger.UnlockExteriorTrigger;
import com.swdteam.common.trigger.VoidTeleportTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;


public class DMCriteriaTriggers
{
   public static final ARSTrigger ARS_TRIGGER = register(new ARSTrigger());
   public static final UnlockExteriorTrigger UNLOCK_EXT = register(new UnlockExteriorTrigger());
   public static final VoidTeleportTrigger VOID_TP = register(new VoidTeleportTrigger());

  
  private static <T extends ICriterionTrigger<?>> T register(T trigger) {
     return (T)CriteriaTriggers.func_192118_a((ICriterionTrigger)trigger);
  }
  
  public static void init() {}
}


