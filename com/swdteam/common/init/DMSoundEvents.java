package com.swdteam.common.init;

import com.swdteam.common.RegistryHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;




public class DMSoundEvents
{
   public static final RegistryObject<SoundEvent> BLOCK_DALEK_DOOR_OPEN = buildSound(RegistryHandler.SOUNDS, "block.dalek_door_open");
   public static final RegistryObject<SoundEvent> BLOCK_DALEK_DOOR_CLOSE = buildSound(RegistryHandler.SOUNDS, "block.dalek_door_close");
   public static final RegistryObject<SoundEvent> BLOCK_DEADLOCK_DOOR_OPEN = buildSound(RegistryHandler.SOUNDS, "block.deadlock_door_open");
   public static final RegistryObject<SoundEvent> BLOCK_DEADLOCK_DOOR_CLOSE = buildSound(RegistryHandler.SOUNDS, "block.deadlock_door_close");
   public static final RegistryObject<SoundEvent> BLOCK_BETA_CHEST_OPEN = buildSound(RegistryHandler.SOUNDS, "block.beta_chest_open");
   public static final RegistryObject<SoundEvent> BLOCK_BETA_CHEST_CLOSE = buildSound(RegistryHandler.SOUNDS, "block.beta_chest_close");
   public static final RegistryObject<SoundEvent> BLOCK_CLASSIC_GRASS = buildSound(RegistryHandler.SOUNDS, "block.classic_grass_step");
   public static final RegistryObject<SoundEvent> BLOCK_CLASSIC_GRAVEL = buildSound(RegistryHandler.SOUNDS, "block.classic_gravel_step");
   public static final RegistryObject<SoundEvent> BLOCK_CLASSIC_WOOD = buildSound(RegistryHandler.SOUNDS, "block.classic_wood_step");
   public static final RegistryObject<SoundEvent> BLOCK_CLASSIC_STONE = buildSound(RegistryHandler.SOUNDS, "block.classic_stone_step");
   public static final RegistryObject<SoundEvent> BLOCK_TELEPORT_PAD = buildSound(RegistryHandler.SOUNDS, "block.teleport_pad");
   public static final RegistryObject<SoundEvent> BLOCK_BUBBLE_WRAP_BREAK = buildSound(RegistryHandler.SOUNDS, "block.bubble_wrap_break");
   public static final RegistryObject<SoundEvent> BLOCK_BUBBLE_WRAP_STEP = buildSound(RegistryHandler.SOUNDS, "block.bubble_wrap_step");
   public static final RegistryObject<SoundEvent> BLOCK_CARDBOARD_BREAK = buildSound(RegistryHandler.SOUNDS, "block.cardboard_break");
   public static final RegistryObject<SoundEvent> BLOCK_CARDBOARD_STEP = buildSound(RegistryHandler.SOUNDS, "block.cardboard_step");
   public static final RegistryObject<SoundEvent> BLOCK_CARDBOARD_PLACE = buildSound(RegistryHandler.SOUNDS, "block.cardboard_place");
   public static final RegistryObject<SoundEvent> BLOCK_CARDBOARD_HIT = buildSound(RegistryHandler.SOUNDS, "block.cardboard_hit");
   public static final RegistryObject<SoundEvent> BLOCK_CARDBOARD_FALL = buildSound(RegistryHandler.SOUNDS, "block.cardboard_fall");
   public static final RegistryObject<SoundEvent> BLOCK_CARDBOARD_OPEN = buildSound(RegistryHandler.SOUNDS, "block.cardboard_open");
   public static final RegistryObject<SoundEvent> BLOCK_CARDBOARD_CLOSE = buildSound(RegistryHandler.SOUNDS, "block.cardboard_close");
   public static final RegistryObject<SoundEvent> BLOCK_GRATE_STEP = buildSound(RegistryHandler.SOUNDS, "block.grate_step");

  
   public static final RegistryObject<SoundEvent> TARDIS_LOCK = buildSound(RegistryHandler.SOUNDS, "tardis.lock");
   public static final RegistryObject<SoundEvent> TARDIS_DEMAT = buildSound(RegistryHandler.SOUNDS, "tardis.flight_demat");
   public static final RegistryObject<SoundEvent> TARDIS_REMAT = buildSound(RegistryHandler.SOUNDS, "tardis.flight_remat");
   public static final RegistryObject<SoundEvent> TARDIS_THUD = buildSound(RegistryHandler.SOUNDS, "tardis.flight_thud");
   public static final RegistryObject<SoundEvent> TARDIS_FLIGHT_LOOP = buildSound(RegistryHandler.SOUNDS, "tardis.flight_loop");
   public static final RegistryObject<SoundEvent> TARDIS_CONTROLS_BEEP = buildSound(RegistryHandler.SOUNDS, "tardis.controls_beep");
   public static final RegistryObject<SoundEvent> TARDIS_CONTROLS_DING = buildSound(RegistryHandler.SOUNDS, "tardis.controls_ding");
   public static final RegistryObject<SoundEvent> TARDIS_CONTROLS_BUTTON_CLICK = buildSound(RegistryHandler.SOUNDS, "tardis.controls_button_click");
   public static final RegistryObject<SoundEvent> TARDIS_CONTROLS_BUTTON_RELEASE = buildSound(RegistryHandler.SOUNDS, "tardis.controls_button_release");
   public static final RegistryObject<SoundEvent> TARDIS_CONTROLS_SCANNER = buildSound(RegistryHandler.SOUNDS, "tardis.controls_scanner");
   public static final RegistryObject<SoundEvent> TARDIS_CONTROLS_LEVER = buildSound(RegistryHandler.SOUNDS, "tardis.controls_lever");
   public static final RegistryObject<SoundEvent> TARDIS_MODULE_INSERT = buildSound(RegistryHandler.SOUNDS, "tardis.module_insert");
   public static final RegistryObject<SoundEvent> TARDIS_MODULE_REMOVE = buildSound(RegistryHandler.SOUNDS, "tardis.module_remove");
   public static final RegistryObject<SoundEvent> TARDIS_MODULE_WRITE = buildSound(RegistryHandler.SOUNDS, "tardis.module_write");
   public static final RegistryObject<SoundEvent> TARDIS_MODULE_LOAD = buildSound(RegistryHandler.SOUNDS, "tardis.module_load");
   public static final RegistryObject<SoundEvent> TARDIS_SONIC_WORKBENCH_INSERT = buildSound(RegistryHandler.SOUNDS, "tardis.sonic_workbench_insert");
   public static final RegistryObject<SoundEvent> TARDIS_SONIC_WORKBENCH_REMOVE = buildSound(RegistryHandler.SOUNDS, "tardis.sonic_workbench_remove");
   public static final RegistryObject<SoundEvent> TARDIS_SONIC_WORKBENCH_UNLOCK = buildSound(RegistryHandler.SOUNDS, "tardis.sonic_workbench_unlock");
   public static final RegistryObject<SoundEvent> TARDIS_CHAMELEON_PANEL_UNLOCK = buildSound(RegistryHandler.SOUNDS, "tardis.chameleon_panel_unlock");
   public static final RegistryObject<SoundEvent> TARDIS_FAULT_LOCATOR_INSERT = buildSound(RegistryHandler.SOUNDS, "tardis.fault_locator_insert");
   public static final RegistryObject<SoundEvent> TARDIS_FAULT_LOCATOR_REMOVE = buildSound(RegistryHandler.SOUNDS, "tardis.fault_locator_remove");
   public static final RegistryObject<SoundEvent> TARDIS_FAULT_LOCATOR_ERROR = buildSound(RegistryHandler.SOUNDS, "tardis.fault_locator_error");
   public static final RegistryObject<SoundEvent> TARDIS_FAULT_LOCATOR_BURNOUT = buildSound(RegistryHandler.SOUNDS, "tardis.fault_locator_burnout");
   public static final RegistryObject<SoundEvent> TARDIS_FUEL_REFILL = buildSound(RegistryHandler.SOUNDS, "tardis.fuel_refill");
   public static final RegistryObject<SoundEvent> TARDIS_POLICE_BOX_DOOR_OPEN = buildSound(RegistryHandler.SOUNDS, "tardis.police_box_door_open");
   public static final RegistryObject<SoundEvent> TARDIS_POLICE_BOX_DOOR_CLOSE = buildSound(RegistryHandler.SOUNDS, "tardis.police_box_door_close");
   public static final RegistryObject<SoundEvent> TARDIS_ROUNDEL_DOOR_OPEN = buildSound(RegistryHandler.SOUNDS, "tardis.roundel_door_open");
   public static final RegistryObject<SoundEvent> TARDIS_ROUNDEL_DOOR_CLOSE = buildSound(RegistryHandler.SOUNDS, "tardis.roundel_door_close");
   public static final RegistryObject<SoundEvent> TARDIS_ENGINE_HUM = buildSound(RegistryHandler.SOUNDS, "tardis.engine_hum");

  
   public static final RegistryObject<SoundEvent> MISC_CLASSIC_EXPLOSION = buildSound(RegistryHandler.SOUNDS, "misc.classic_explosion");
   public static final RegistryObject<SoundEvent> SECRET_1 = buildSound(RegistryHandler.SOUNDS, "misc.secret_sound_1");
   public static final RegistryObject<SoundEvent> SECRET_2 = buildSound(RegistryHandler.SOUNDS, "misc.secret_sound_2");

  
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_SECOND = buildSound(RegistryHandler.SOUNDS, "entity.sonic_second");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_THIRD = buildSound(RegistryHandler.SOUNDS, "entity.sonic_third");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_FOURTH = buildSound(RegistryHandler.SOUNDS, "entity.sonic_fourth");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_FIFTH = buildSound(RegistryHandler.SOUNDS, "entity.sonic_fifth");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_SIXTH = buildSound(RegistryHandler.SOUNDS, "entity.sonic_sixth");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_NINTH = buildSound(RegistryHandler.SOUNDS, "entity.sonic_ninth");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_NINTH_EXTEND = buildSound(RegistryHandler.SOUNDS, "entity.sonic_ninth_extend");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_ELEVENTH = buildSound(RegistryHandler.SOUNDS, "entity.sonic_eleventh");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_ELEVENTH_EXTEND = buildSound(RegistryHandler.SOUNDS, "entity.sonic_eleventh_extend");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_PY_GREEN = buildSound(RegistryHandler.SOUNDS, "entity.sonic_py_green");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_PY_BLUE = buildSound(RegistryHandler.SOUNDS, "entity.sonic_py_blue");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_TEMPORAL = buildSound(RegistryHandler.SOUNDS, "entity.sonic_temporal");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_EPSILON = buildSound(RegistryHandler.SOUNDS, "entity.sonic_epsilon");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_LIPSTICK = buildSound(RegistryHandler.SOUNDS, "entity.sonic_lipstick");
   public static final RegistryObject<SoundEvent> ENTITY_SONIC_LAKE = buildSound(RegistryHandler.SOUNDS, "entity.sonic_lake");
  
   public static final RegistryObject<SoundEvent> ENTITY_STATTENHEIM_REMOTE_SYNC = buildSound(RegistryHandler.SOUNDS, "entity.stattenheim_remote_sync");
   public static final RegistryObject<SoundEvent> ENTITY_STATTENHEIM_REMOTE_SUMMON = buildSound(RegistryHandler.SOUNDS, "entity.stattenheim_remote_summon");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_GUNSTICK_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_gunstick_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_GUNSTICK_CHARGE = buildSound(RegistryHandler.SOUNDS, "entity.dalek_gunstick_charge");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_CANNON_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_cannon_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_CANNON_CHARGE = buildSound(RegistryHandler.SOUNDS, "entity.dalek_cannon_charge");
  
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_CLASSIC_ATTACK = buildSound(RegistryHandler.SOUNDS, "entity.dalek_classic_attack");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_SKARO_ATTACK = buildSound(RegistryHandler.SOUNDS, "entity.dalek_skaro_attack");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_SKARO_AMBIENT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_skaro_ambient");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_SUICIDE_BOMBER_ATTACK = buildSound(RegistryHandler.SOUNDS, "entity.dalek_suicide_bomber_attack");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_TIME_WAR_ATTACK = buildSound(RegistryHandler.SOUNDS, "entity.dalek_time_war_attack");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_NETHER_ATTACK = buildSound(RegistryHandler.SOUNDS, "entity.dalek_nether_attack");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_MOLTEN_ATTACK = buildSound(RegistryHandler.SOUNDS, "entity.dalek_molten_attack");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_IMPERIAL_ATTACK = buildSound(RegistryHandler.SOUNDS, "entity.dalek_imperial_attack");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_IMPERIAL_STAY = buildSound(RegistryHandler.SOUNDS, "entity.dalek_imperial_stay_where_you_are");
  
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_GLIDE_START = buildSound(RegistryHandler.SOUNDS, "entity.dalek_glide_start");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_GLIDE = buildSound(RegistryHandler.SOUNDS, "entity.dalek_glide");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_HOVER_START = buildSound(RegistryHandler.SOUNDS, "entity.dalek_hover_start");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_HOVER = buildSound(RegistryHandler.SOUNDS, "entity.dalek_hover");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_ROTATE = buildSound(RegistryHandler.SOUNDS, "entity.dalek_rotate");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_EYESTALK = buildSound(RegistryHandler.SOUNDS, "entity.dalek_eyestalk");
  
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_SPARK_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_spark_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_SMOKE_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_smoke_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_BULLET_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_bullet_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_LASER_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_laser_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_BEAM_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_beam_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_FLAME_THROWER_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_flame_thrower_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_SWD_CHARGE = buildSound(RegistryHandler.SOUNDS, "entity.dalek_special_weapons_charge");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_SWD_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_special_weapons_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_DALEK_HURT = buildSound(RegistryHandler.SOUNDS, "entity.dalek_hurt");
  
   public static final RegistryObject<SoundEvent> ENTITY_CYBERMAN_STEP = buildSound(RegistryHandler.SOUNDS, "entity.cyberman_step");
   public static final RegistryObject<SoundEvent> ENTITY_CYBERMAN_LIVING = buildSound(RegistryHandler.SOUNDS, "entity.cyberman_living");
   public static final RegistryObject<SoundEvent> ENTITY_CYBERMAN_HURT = buildSound(RegistryHandler.SOUNDS, "entity.cyberman_hurt");
   public static final RegistryObject<SoundEvent> ENTITY_CYBERMAN_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.cyberman_shoot");
   public static final RegistryObject<SoundEvent> ENTITY_AUTON_SHOOT = buildSound(RegistryHandler.SOUNDS, "entity.auton_shoot");




  
   public static final RegistryObject<SoundEvent> ENTITY_CYBERVILLAGER_AMBIENT = buildSound(RegistryHandler.SOUNDS, "entity.cybervillager.ambient");

  
   public static final RegistryObject<SoundEvent> MUSIC_TITLE_SCREEN = buildSound(RegistryHandler.SOUNDS, "music.title_screen");
   public static final RegistryObject<SoundEvent> MUSIC_DISC_CYBERWAVE = buildSound(RegistryHandler.SOUNDS, "music.disc_cyberwave");
   public static final RegistryObject<SoundEvent> MUSIC_DISC_SONG = buildSound(RegistryHandler.SOUNDS, "music.disc_song");
   public static final RegistryObject<SoundEvent> MUSIC_BOSS_DALEK = buildSound(RegistryHandler.SOUNDS, "music.boss_dalek");
   public static final RegistryObject<SoundEvent> MUSIC_BOSS_TIME_LORD = buildSound(RegistryHandler.SOUNDS, "music.boss_time_lord");
   public static final RegistryObject<SoundEvent> MUSIC_CLASSIC_AMBIENCE = buildSound(RegistryHandler.SOUNDS, "music.classic_ambience");
   public static final RegistryObject<SoundEvent> MUSIC_TARDIS_AMBIENCE = buildSound(RegistryHandler.SOUNDS, "music.tardis_ambience");
   public static final RegistryObject<SoundEvent> MUSIC_SKARO_AMBIENCE = buildSound(RegistryHandler.SOUNDS, "music.skaro_ambience");

  
   public static final RegistryObject<SoundEvent> TARDIS_COMPASS_LOCK = buildSound(RegistryHandler.SOUNDS, "item.tardis_compass.lock");
   public static final RegistryObject<SoundEvent> ITEM_GUN_SHOOT = buildSound(RegistryHandler.SOUNDS, "item.gun.shoot");
   public static final RegistryObject<SoundEvent> ITEM_GUN_CLICK = buildSound(RegistryHandler.SOUNDS, "item.gun.click");

  
   public static final RegistryObject<SoundEvent> PLAYER_SNAP = buildSound(RegistryHandler.SOUNDS, "player.snap");
   public static final RegistryObject<SoundEvent> PLAYER_CLASSIC_HURT = buildSound(RegistryHandler.SOUNDS, "player.classic_hurt");
   public static final RegistryObject<SoundEvent> PLAYER_PHONE = buildSound(RegistryHandler.SOUNDS, "player.phone");
  
  public static RegistryObject<SoundEvent> buildSound(DeferredRegister<SoundEvent> register, String registryName) {
     RegistryObject<SoundEvent> SOUND = register.register(registryName, () -> new SoundEvent(new ResourceLocation("dalekmod", registryName)));
     return SOUND;
  }
}


