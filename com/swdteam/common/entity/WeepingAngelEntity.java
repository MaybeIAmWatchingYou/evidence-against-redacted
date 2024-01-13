package com.swdteam.common.entity;
import com.swdteam.common.init.DMItems;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketWeepingAngel;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeEntity;

public class WeepingAngelEntity extends MonsterEntity implements IForgeEntity {
   public float quantum = 0.0F;
   public float prev_quantum = 0.0F;
  public boolean seen = true;
  public boolean changeDetect = true;
   private static float defaultSpeed = 0.6F;
  public long lastHit;
   private SoundEvent breakSound = SoundEvents.field_187835_fT;
  
   public List<String> names_seeing = new ArrayList<>();
  
  public WeepingAngelEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
     super(type, worldIn);
  }
  
  public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
     return MobEntity.func_233666_p_()
       .func_233815_a_(Attributes.field_233821_d_, defaultSpeed)
       .func_233815_a_(Attributes.field_233818_a_, 40.0D)
       .func_233815_a_(Attributes.field_233823_f_, 6.0D);
  }

  
  protected void func_184651_r() {
     super.func_184651_r();
     this.field_70714_bg.func_75776_a(2, (Goal)new MeleeAttackGoal((CreatureEntity)this, 1.0D, false));
     this.field_70715_bh.func_75776_a(2, (Goal)new NearestAttackableTargetGoal((MobEntity)this, PlayerEntity.class, true));
  }

  
  protected void func_180429_a(BlockPos pos, BlockState blockIn) {
     super.func_180429_a(pos, blockIn);
  }


  
  public void func_70071_h_() {
     if (func_110143_aJ() <= 0.0F) {
       this.world.playSound((PlayerEntity)null, getPosX(), getPosY(), getPosZ(), this.breakSound, SoundCategory.BLOCKS, 1.0F, 0.8F);
       func_174812_G();
      
      return;
    } 
     if (this.world.isRemote) {
       boolean occluded = checkOcclusion();
       if (this.quantum == this.prev_quantum || occluded) {
         if (this.seen) {
           this.seen = false;
           NetworkHandler.sendServerPacket(new PacketWeepingAngel(this.seen, this.field_96093_i.toString()));
        }
      
       } else if (!this.seen) {
         this.seen = true;
         NetworkHandler.sendServerPacket(new PacketWeepingAngel(this.seen, this.field_96093_i.toString()));
      } 
      
       this.prev_quantum = this.quantum;
    } 
    
     if (!this.seen) {
       super.func_70071_h_();
       func_110148_a(Attributes.field_233821_d_).func_111128_a(defaultSpeed);
       PlayerEntity player = this.world.func_217362_a((Entity)this, 40.0D);
       if (player != null) {
         func_70625_a((Entity)player, 1.0F, 1.0F);
      }
    } else {
       func_110148_a(Attributes.field_233821_d_).func_111128_a(0.0D);
       this.field_70760_ar = this.field_70761_aq;
       BlockPos blockpos = func_226270_aj_();
       float f3 = this.world.getBlockState(func_226270_aj_()).getSlipperiness((IWorldReader)this.world, func_226270_aj_(), (Entity)this);
       Vector3d vector3d5 = func_233633_a_(new Vector3d(0.0D, -300.0D, 0.0D), f3);
       double d2 = vector3d5.y;
       if (this.world.isRemote && !this.world.func_175667_e(blockpos)) {
         if (getPosY() > 0.0D) {
           d2 = -0.1D;
        } else {
           d2 = 0.0D;
        } 
      }
       setMotion(0.0D, d2 * 0.9800000190734863D, 0.0D);
    } 
  }






















  
  public void handleSeenUpdate(String name, boolean seen) {
     if (seen) {
       boolean has = false;
       if (this.names_seeing.size() > 0) {
         for (String s : this.names_seeing) {
           if (s.equals(name)) {
             has = true;
            break;
          } 
        } 
      }
       if (!has)
         this.names_seeing.add(name); 
    } else {
       int ee = this.names_seeing.size();
       this.names_seeing.remove(name);
       if (this.names_seeing.size() == ee && 
         this.names_seeing.size() > 0) {
         for (int i = this.names_seeing.size() - 1; i >= 0; i++) {
           if (this.names_seeing.size() > i) {
             ServerPlayerEntity player = getServer().func_184103_al().func_152612_a(this.names_seeing.get(i));
             if (player == null || (!seen && ((String)this.names_seeing.get(i)).equals(name))) {
               this.names_seeing.remove(i);
            }
          } 
        } 
      }
    } 
     if (this.names_seeing.size() > 0) {
       this.seen = true;
    } else {
       this.seen = false;
    } 
  }


  
  public void func_70057_ab() {}

  
  public boolean func_70104_M() {
     return false;
  }

  
  public PushReaction func_184192_z() {
     return PushReaction.IGNORE;
  }









  
  public boolean func_241849_j(Entity entity) {
     return true;
  }

  
  public void func_174812_G() {
     remove();
  }


  
  public void func_233627_a_(float p_233627_1_, double p_233627_2_, double p_233627_4_) {}

  
  public boolean func_70097_a(DamageSource source, float p_70097_2_) {
     if (source.func_76346_g() instanceof PlayerEntity) {
       Item item = ((PlayerEntity)source.func_76346_g()).func_184614_ca().getItem();
       if (item instanceof net.minecraft.item.PickaxeItem) {
         long i = this.world.getGameTime();
         if (i - this.lastHit > 3L) {
           this.world.func_72960_a((Entity)this, (byte)32);
           this.lastHit = i;
        } 
         setHealth(func_110143_aJ() - 1.0F);
         if (this.world instanceof ServerWorld) {
           ((ServerWorld)this.world).func_195598_a((IParticleData)new BlockParticleData(ParticleTypes.field_197611_d, Blocks.field_150348_b.getDefaultState()), getPosX(), func_226283_e_(0.16666666666666666D), getPosZ(), 10, (func_213311_cf() / 4.0F), (func_213302_cg() / 4.0F), (func_213311_cf() / 4.0F), 0.05D);
        }
      } 
    } 
     return true;
  }
  
  @OnlyIn(Dist.CLIENT)
  public void func_70103_a(byte p_70103_1_) {
     if (p_70103_1_ == 32) {
       if (this.world.isRemote) {
         this.world.func_184134_a(getPosX(), getPosY(), getPosZ(), SoundEvents.field_187843_fX, SoundCategory.BLOCKS, 0.3F, 1.0F, false);
         this.lastHit = this.world.getGameTime();
      } 
    } else {
       super.func_70103_a(p_70103_1_);
    } 
  }

  
  public boolean func_184603_cC() {
     return false;
  }


  
  protected void func_70679_bo() {}


  
  public boolean func_190530_aW() {
     return true;
  }
  
  protected boolean func_225502_at_() {
     return false;
  }

  
  private boolean checkOcclusion() {
     boolean occludes = false;
     ClientPlayerEntity player = (Minecraft.func_71410_x()).field_71439_g;
     Vector3d start_pos = player.getPositionVec().func_72441_c(0.0D, 1.0D, 0.0D);
     Vector3d vector = getPositionVec().func_178788_d(player.getPositionVec()).func_72432_b();
     int distance = (int)player.func_70032_d((Entity)this);
     occludes = wallOcclusionCheck(start_pos, vector, distance);
     return occludes;
  }

  
  private List<PlayerEntity> getAllPlayersInRadius(int radius) {
     AxisAlignedBB bounds = getBoundingBox().grow(radius);
     List<PlayerEntity> entities = this.world.getEntitiesWithinAABB(PlayerEntity.class, bounds);
     return entities;
  }
  
  private boolean wallOcclusionCheck(Vector3d start_pos, Vector3d vector, int distance) {
     boolean occludes = false;
     Vector3d start = start_pos;
     for (int i = 0; i < distance - 1; i++) {
       start = start.func_178787_e(vector);
       BlockPos pos = new BlockPos(start);
       boolean all_solid = true;
       for (int x = 0; x < 3; x++) {
         for (int y = 0; y < 3; y++) {
           BlockPos p = pos;
           if (Math.abs(vector.x) > Math.abs(vector.y) && Math.abs(vector.x) > Math.abs(vector.z)) {
             p = pos.func_177982_a(0, x - 1, y - 1);
          }
           if (Math.abs(vector.y) > Math.abs(vector.x) && Math.abs(vector.y) > Math.abs(vector.z)) {
             p = pos.func_177982_a(x - 1, 0, y - 1);
          }
           if (Math.abs(vector.z) > Math.abs(vector.x) && Math.abs(vector.z) > Math.abs(vector.y)) {
             p = pos.func_177982_a(x - 1, y - 1, 0);
          }
           BlockState block = this.world.getBlockState(p);
           if (!block.func_200132_m()) {
             all_solid = false;
          }
           if (!all_solid)
            break; 
        } 
         if (!all_solid)
          break; 
      } 
       if (all_solid) {
         occludes = true;
        break;
      } 
    } 
     return occludes;
  }



























  
  public ItemStack getPickedResult(RayTraceResult target) {
     return new ItemStack((IItemProvider)DMItems.WEEPING_ANGEL_SPAWNER.get());
  }
}


