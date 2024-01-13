package com.swdteam.mixins;

import com.swdteam.common.init.DMFlightMode;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MovementInputFromOptions.class})
public class MovementInputMixin
  extends MovementInput
{
  @Shadow
  @Final
  private GameSettings field_78903_e;

  @Inject(at = {@At("HEAD")}, method = {"tick"}, cancellable = true)
  public void tick(boolean p_225607_1_, CallbackInfo info) {
     if (DMFlightMode.isInFlight((Minecraft.func_71410_x()).field_71439_g.getGameProfile().getId()) &&
       (Minecraft.func_71410_x()).field_71439_g.isOnGround()) {
       info.cancel();
       this.field_192832_b = 0.0F;
       this.field_78902_a = 0.0F;
       this.field_78901_c = this.field_78903_e.field_74314_A.func_151470_d();
       this.field_228350_h_ = this.field_78903_e.field_228046_af_.func_151470_d();
    }
  }
}


