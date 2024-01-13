package com.swdteam.common.entity.dalek;

import com.swdteam.client.model.ModelDalekBase;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.model.javajson.JSONModel;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;

public interface IDalek {
  ModelDalekBase getModel();
  
  boolean canFly();
  
  void setModel(JSONModel paramJSONModel);
  
  void setupDalek(Entity paramEntity);
  
  DalekBase addTooltip(ITextComponent paramITextComponent);
  
  String getName();
  
  SoundEvent getHurtSound(Entity paramEntity);
  
  SoundEvent getAmbientSound(Entity paramEntity);
  
  SoundEvent getDeathSound(Entity paramEntity);
  
  SoundEvent getSpawnSound(Entity paramEntity);
  
  SoundEvent getAttackSound(Entity paramEntity);
  
  SoundEvent getAttackedSound(Entity paramEntity);
  
  SoundEvent getShootSound(Entity paramEntity);
  
  SoundEvent getMovementSound(Entity paramEntity);
  
  void onUpdate(Entity paramEntity);
  
  void mobInteract(PlayerEntity paramPlayerEntity, Hand paramHand, Entity paramEntity);
  
  void onDeath(Entity paramEntity);
  
  void setDead(boolean paramBoolean);
  
  void onAttacked(Entity paramEntity1, Entity paramEntity2, DamageSource paramDamageSource);
  
  boolean isDead();
  
  LaserEntity onPreLaserAttack(DalekEntity paramDalekEntity, LivingEntity paramLivingEntity, float paramFloat);
  
  LaserEntity spawnLaserAttack(DalekEntity paramDalekEntity, LivingEntity paramLivingEntity);
  
  double getMoveSpeed();
  
  float getMaxHealth();
  
  float attackDamage(DalekEntity paramDalekEntity);
  
  void setRegistryName(DalekType paramDalekType, String paramString);
  
  String getID();
  
  DMProjectiles.Laser getLaser(DalekEntity paramDalekEntity);
  
  void getTooltips(List<ITextComponent> paramList);
  
  IDalek addChild(String paramString);
  
  List<IDalek> getChildren();
  
  void setDalekName(String paramString);
  
  DalekType getType();
  
  List<String> getLeftArmAttatchments();
  
  List<String> getRightArmAttatchments();
  
  void addLeftArmAttatchment(String paramString);
  
  void addRightArmAttatchment(String paramString);
  
  String getRandomLeftArm(DalekEntity paramDalekEntity);
  
  String getRandomRightArm(DalekEntity paramDalekEntity);
}


