package com.swdteam.common.entity.dalek;

import com.swdteam.client.model.ModelDalekBase;
import com.swdteam.common.entity.LaserEntity;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.common.init.DMProjectiles;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.model.javajson.JSONModel;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Difficulty;



public abstract class DalekBase
  implements IDalek, IMob, Cloneable
{
   private List<ITextComponent> tooltips = new ArrayList<>();
  private String dalekKey;
  public String dalekName;
  private ModelDalekBase dalekModel;
  private boolean isDead = false;
  private DalekType type;
   private List<IDalek> children = new ArrayList<>();
   private List<String> leftArmAttachments = new ArrayList<>();
   private List<String> rightArmAttachments = new ArrayList<>();
  
  public DalekBase(String dalekName) {
     this.dalekName = dalekName;
    
     this.leftArmAttachments.add("GunArm");
     this.rightArmAttachments.add("SuctionArm");
  }

  
  public String getName() {
     return this.dalekName;
  }

  
  public String getID() {
     return this.dalekKey;
  }
  
  public boolean canFly() {
     return false;
  }

  
  public void setRegistryName(DalekType type, String name) {
     if (this.dalekKey == null) {
       this.dalekKey = name;
       this.type = type;
    } 
  }


  
  public void mobInteract(PlayerEntity player, Hand hand, Entity dalek) {}


  
  public void getTooltips(List<ITextComponent> tooltip) {
     tooltip.addAll(this.tooltips);
  }


  
  public void setupDalek(Entity e) {
     if (e.world.rand.nextInt(5) == 3);

    
     ((DalekEntity)e).func_110148_a(Attributes.field_233818_a_).func_111128_a(getMaxHealth());
  }


  
  public void onUpdate(Entity e) {}


  
  public void onDeath(Entity e) {}

  
  public void onAttacked(Entity dalek, Entity attacker, DamageSource damage) {
     if (dalek.world.rand.nextInt(5) == 3) {
       playSound((DalekEntity)dalek, getAttackedSound(dalek));
    }
  }

  
  public double getMoveSpeed() {
     return 0.2D;
  }

  
  public float getMaxHealth() {
     return 50.0F;
  }

  
  public void setDead(boolean dead) {
     this.isDead = dead;
  }

  
  public boolean isDead() {
     return this.isDead;
  }
  
  public void remove() {
     remove();
  }


  
  public ModelDalekBase getModel() {
     return this.dalekModel;
  }

  
  public void setModel(JSONModel model) {
     this.dalekModel = new ModelDalekBase(model);
  }

  
  public DalekBase addTooltip(ITextComponent comp) {
     this.tooltips.add(comp);
     return this;
  }


  
  public DMProjectiles.Laser getLaser(DalekEntity dalek) {
     return DMProjectiles.BLUE_LASER;
  }

  
  public LaserEntity onPreLaserAttack(DalekEntity dalek, LivingEntity living, float distanceFactor) {
     if (dalek.getFuse() == -1) {
       dalek.func_184212_Q().func_187227_b(DalekEntity.DALEK_FUSE, Integer.valueOf(0));
       SoundEvent s = getAttackSound((Entity)dalek);
       dalek.func_184185_a(s, 1.0F, 1.0F);
    } 
    
     return null;
  }

  
  public LaserEntity spawnLaserAttack(DalekEntity dalek, LivingEntity target) {
     double d0 = 0.0D;
     double d1 = 0.0D;
     double d2 = 0.0D;
    
     if (!target.func_70089_S()) {
       return null;
    }
     d0 = target.getPosX() - dalek.getPosX();
     d1 = target.func_226283_e_(0.3333333333333333D) - dalek.getPosY() - 0.5D;
     d2 = target.getPosZ() - dalek.getPosZ();
    
     LaserEntity laser = new LaserEntity(dalek.world, (LivingEntity)dalek, 0.2F, 2.0F);
     laser.setDamage(attackDamage(dalek));
     laser.setLaserType(getLaser(dalek));
     laser.func_70186_c(d0, d1, d2, 2.5F, 0.0F);
     dalek.func_184185_a(getShootSound((Entity)dalek), 1.0F, 1.0F);
     dalek.world.addEntity((Entity)laser);
    
     return laser;
  }

  
  public static SoundEvent getRandomSound(Entity e, SoundEvent[] s) {
     if (e != null && s != null && s.length != 0) {
       return s[e.world.rand.nextInt(s.length)];
    }
     return null;
  }

  
  public SoundEvent getHurtSound(Entity e) {
     return null;
  }

  
  public SoundEvent getAmbientSound(Entity e) {
     return null;
  }

  
  public SoundEvent getDeathSound(Entity e) {
     return null;
  }

  
  public SoundEvent getSpawnSound(Entity e) {
     return null;
  }

  
  public SoundEvent getAttackSound(Entity e) {
     return null;
  }

  
  public SoundEvent getAttackedSound(Entity e) {
     return null;
  }

  
  public SoundEvent getShootSound(Entity e) {
     return null;
  }
  
  public Object clone() throws CloneNotSupportedException {
     return super.clone();
  }
  
  public void playSound(DalekEntity w, SoundEvent s) {
     if (s != null)
       w.func_184185_a(s, 1.0F, 1.0F);
  }
  
  public DalekType getType() {
     return this.type;
  }
  
  public void setDalekName(String dalekName) {
     this.dalekName = dalekName;
  }
  
  public List<IDalek> getChildren() {
     return this.children;
  }

  
  public IDalek addChild(String regKey) {
    try {
       Class<?> clazz = getClass();
       Constructor<?> ctor = clazz.getConstructor(new Class[] { String.class });
       Object object = ctor.newInstance(new Object[] { this.dalekName });
      
       if (object instanceof IDalek) {
         IDalek dalek = (IDalek)object;
         dalek.setRegistryName(getType(), regKey);
         this.children.add(dalek);
         DMDalekRegistry.addDalek(dalek);
         return dalek;
      } 
     } catch (Exception e) {
       e.printStackTrace();
    } 
     return null;
  }

  
  public List<String> getLeftArmAttatchments() {
     return this.leftArmAttachments;
  }

  
  public List<String> getRightArmAttatchments() {
     return this.rightArmAttachments;
  }

  
  public void addLeftArmAttatchment(String s) {
     this.leftArmAttachments.add(s);
  }

  
  public void addRightArmAttatchment(String s) {
     this.rightArmAttachments.add(s);
  }

  
  public String getRandomLeftArm(DalekEntity dalek) {
     return this.leftArmAttachments.get(dalek.func_70681_au().nextInt(this.leftArmAttachments.size()));
  }

  
  public String getRandomRightArm(DalekEntity dalek) {
     return this.rightArmAttachments.get(dalek.func_70681_au().nextInt(this.rightArmAttachments.size()));
  }

  
  public float attackDamage(DalekEntity dalek) {
     return (dalek.world.func_175659_aa() == Difficulty.EASY) ? 4.0F : ((dalek.world.func_175659_aa() == Difficulty.NORMAL) ? 6 : 8);
  }

  
  public SoundEvent getMovementSound(Entity e) {
     return (SoundEvent)DMSoundEvents.ENTITY_DALEK_GLIDE.get();
  }
  
  protected abstract void aiStep();
}


