package com.swdteam.mixins;

import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin({Minecraft.class})
public abstract class MinecraftMixin
{
   private Minecraft mc = (Minecraft)this;
  
  @Inject(at = {@At("RETURN")}, cancellable = true, method = {"Lnet/minecraft/client/Minecraft;getSituationalMusic()Lnet/minecraft/client/audio/BackgroundMusicSelector;"})
  private void getSituationalMusic(CallbackInfoReturnable<BackgroundMusicSelector> info) {
     if ((info.getReturnValue() == BackgroundMusicTracks.field_232671_b_ || info.getReturnValue() == BackgroundMusicTracks.field_232676_g_) && this.mc.field_71439_g != null) {
       RegistryKey<World> dim = this.mc.field_71439_g.world.getDimensionKey();

      
       if (dim == DMDimensions.CLASSIC) info.setReturnValue(BackgroundMusicTracks.func_232677_a_((SoundEvent)DMSoundEvents.MUSIC_CLASSIC_AMBIENCE.get()));
       if (dim == DMDimensions.SKARO) info.setReturnValue(BackgroundMusicTracks.func_232677_a_((SoundEvent)DMSoundEvents.MUSIC_SKARO_AMBIENCE.get()));
       if (dim == DMDimensions.TARDIS) info.setReturnValue(BackgroundMusicTracks.func_232677_a_((SoundEvent)DMSoundEvents.MUSIC_TARDIS_AMBIENCE.get()));
    } 
  }
}


