package com.swdteam.client.data;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.PlayerEntity;


public class DamageMap
{
   private static List<PlayerEntity> entities = new ArrayList<>();
  
  public static void addDamagedPlayer(PlayerEntity entity) {
     entities.add(entity);
  }
  
  public static List<PlayerEntity> getEntities() {
     return entities;
  }
}


