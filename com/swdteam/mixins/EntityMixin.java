package com.swdteam.mixins;

import com.swdteam.common.init.DMCriteriaTriggers;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.teleport.TeleportRequest;
import com.swdteam.util.TeleportUtil;
import com.swdteam.util.math.Position;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;




@Mixin({Entity.class})
public abstract class EntityMixin
{
   private int teleportCooldown = 0;

   private Entity entity = (Entity)this;

  @Inject(at = {@At("HEAD")}, method = {"Lnet/minecraft/entity/Entity;tick()V"})
  private void onTick(CallbackInfo info) {
     if (this.entity != null && !this.entity.world.isRemote && this.entity

       .getPositionVec() != null && this.entity.world
       .getDimensionKey().equals(DMDimensions.TARDIS) &&
       (this.entity.getPositionVec()).y < 0.0D) {

       int id = DMTardis.getIDForXZ((int)(this.entity.getPositionVec()).x, (int)(this.entity.getPositionVec()).z);
       TardisData data = DMTardis.getTardis(id);

       if (data != null && data.getInteriorSpawnPosition() != null) {
         Position pos = data.getInteriorSpawnPosition();
         int yPos = (int)pos.func_82617_b();
         for (int i = 0; i < 10; i++) {
           if (!this.entity.world.func_175623_d((new BlockPos(pos.func_82615_a(), yPos, pos.func_82616_c())).func_177977_b())) {
             if (this.entity instanceof LivingEntity && ((LivingEntity)this.entity).func_184603_cC()) ((LivingEntity)this.entity).func_195064_c(new EffectInstance(Effects.field_204839_B, 2, 0, false, false, false));
             TeleportUtil.TELEPORT_REQUESTS.put(this.entity, new TeleportRequest(new Location(pos.toBlockPos(), this.entity.world.getDimensionKey())));
             if (this.entity instanceof ServerPlayerEntity) DMCriteriaTriggers.VOID_TP.trigger((ServerPlayerEntity)this.entity);
            break;
          }
           yPos--;
        }
      }
    }

     if (this.entity instanceof net.minecraft.entity.player.PlayerEntity);
  }
}


