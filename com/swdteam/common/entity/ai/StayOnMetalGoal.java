package com.swdteam.common.entity.ai;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;

public class StayOnMetalGoal extends RandomWalkingGoal {
  protected final float probability;
  
  public StayOnMetalGoal(CreatureEntity entity, double d) {
     this(entity, d, 0.001F);
  }
  
  public StayOnMetalGoal(CreatureEntity entity, double d, float f) {
     super(entity, d);
     this.probability = f;
  }
}


