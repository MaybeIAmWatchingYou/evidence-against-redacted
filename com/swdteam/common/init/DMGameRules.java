package com.swdteam.common.init;

import net.minecraft.world.GameRules;

public class DMGameRules
{
   public static final GameRules.RuleKey<GameRules.BooleanValue> LASER_GRIEFING = register("dmLaserGriefing", GameRules.Category.MISC, GameRules.BooleanValue.func_223568_b(true));


  public static <T extends GameRules.RuleValue<T>> GameRules.RuleKey<T> register(String name, GameRules.Category category, GameRules.RuleType<T> ruleType) {
     return GameRules.func_234903_a_(name, category, ruleType);
  }

  public static void init() {}
}


