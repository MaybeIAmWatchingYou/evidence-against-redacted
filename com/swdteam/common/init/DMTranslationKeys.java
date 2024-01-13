package com.swdteam.common.init;

import com.swdteam.util.SWDMathUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;




public class DMTranslationKeys
{
   public static TranslationTextComponent TARDIS_HAS_DEMAT = register("notice.dalekmod.tardis.has_demat");
   public static TranslationTextComponent TARDIS_HAS_REMAT = register("notice.dalekmod.tardis.has_remat");
   public static TranslationTextComponent TARDIS_TRAVELING = register("notice.dalekmod.tardis.traveling");
   public static TranslationTextComponent TARDIS_STILL_TAKING_OFF = register("notice.dalekmod.tardis.still_taking_off");
   public static TranslationTextComponent TARDIS_NOT_ENOUGH_FUEL = register("notice.dalekmod.tardis.not_enough_fuel");
   public static TranslationTextComponent TARDIS_UNABLE_TO_LAND = register("notice.dalekmod.tardis.landing.no");
   public static TranslationTextComponent TARDIS_FAST_RETURN_SET = register("notice.dalekmod.tardis.fast_return_set");
   public static TranslationTextComponent TARDIS_FLIGHT_ROTATION_SET = register("notice.dalekmod.tardis.flight_rotation_set");
   public static TranslationTextComponent TARDIS_DRIFTED = register("notice.dalekmod.tardis.drifted");
   public static TranslationTextComponent TARDIS_IN_FLIGHT = register("notice.dalekmod.tardis.in_flight");
   public static TranslationTextComponent TARDIS_IS_LOCKED = register("notice.dalekmod.tardis.is_locked");
   public static TranslationTextComponent TARDIS_WAYPOINT_APPLIED = register("notice.dalekmod.tardis.waypoint_applied");
   public static TranslationTextComponent TARDIS_DOOR_POS_INVALID = register("notice.dalekmod.tardis.invalid_door_pos");
   public static TranslationTextComponent TARDIS_OWNERSHIP_TRANSFER = register("notice.dalekmod.tardis.transfer");
   public static TranslationTextComponent TARDIS_OWNERSHIP_GIVEN = register("notice.dalekmod.tardis.given");
   public static TranslationTextComponent TARDIS_CHAMELEON_UNKNOWN = register("msg.dalekmod.tardis.chameleon_unknown");
   public static TranslationTextComponent TARDIS_OUTSIDE_WORLD_BOUNDS = register("msg.dalekmod.tardis.outside_bounds");
   public static TranslationTextComponent TARDIS_OUTSIDE_INTERIOR_BOUNDS = register("msg.dalekmod.tardis.outside_interior_bounds");

  
   public static TranslationTextComponent SCANNER_TITLE_TARDIS_INFO = register("screen.dalekmod.scanner.screen_1");
   public static TranslationTextComponent SCANNER_TITLE_LOCATION_CURRENT = register("screen.dalekmod.scanner.screen_2_cur");
   public static TranslationTextComponent SCANNER_TITLE_LOCATION_PREVIOUS = register("screen.dalekmod.scanner.screen_2_prev");
   public static TranslationTextComponent SCANNER_TITLE_FLIGHT_INFO = register("screen.dalekmod.scanner.screen_3");
   public static TranslationTextComponent SCANNER_OWNER = register("screen.dalekmod.scanner.owner");
   public static TranslationTextComponent SCANNER_DOOR = register("screen.dalekmod.scanner.door");
   public static TranslationTextComponent SCANNER_DOOR_LOCKED = register("screen.dalekmod.scanner.door_locked");
   public static TranslationTextComponent SCANNER_DOOR_UNLOCKED = register("screen.dalekmod.scanner.door_unlocked");
   public static TranslationTextComponent SCANNER_DISGUISE = register("screen.dalekmod.scanner.disguise");
   public static TranslationTextComponent SCANNER_X_COORD = register("screen.dalekmod.scanner.X");
   public static TranslationTextComponent SCANNER_Y_COORD = register("screen.dalekmod.scanner.Y");
   public static TranslationTextComponent SCANNER_Z_COORD = register("screen.dalekmod.scanner.Z");
   public static TranslationTextComponent SCANNER_FACING = register("screen.dalekmod.scanner.facing");
   public static TranslationTextComponent SCANNER_LANDED = register("screen.dalekmod.scanner.landed");
   public static TranslationTextComponent SCANNER_NOT_FLYING = register("screen.dalekmod.scanner.not_flying");
   public static TranslationTextComponent SCANNER_ERROR = register("screen.dalekmod.scanner.error");
   public static TranslationTextComponent SCANNER_IN_FLIGHT = register("screen.dalekmod.scanner.in_flight");
   public static TranslationTextComponent SCANNER_TIME_REMAINING = register("screen.dalekmod.scanner.time_remaining");
   public static TranslationTextComponent SCANNER_SECONDS = register("screen.dalekmod.scanner.seconds");

  
   public static TranslationTextComponent GUI_ROUNDEL_BUILDER_NAME = register("container.roundel_builder.name");
   public static TranslationTextComponent GUI_ENGINEERING_TABLE_NAME = register("container.engineering_table.name");
   public static TranslationTextComponent GUI_ARS_NAME = register("container.ars.name");
   public static TranslationTextComponent GUI_FAULT_LOCATOR_NAME = register("container.fault_locator.name");
  
   public static TranslationTextComponent GUI_TARDIS_SELECTION_COMPLETE = register("container.tardis_selection.complete");
   public static TranslationTextComponent GUI_TARDIS_SELECTION_SKIN = register("container.tardis_selection.skin");
   public static TranslationTextComponent GUI_TARDIS_SELECTION_INTERIOR = register("container.tardis_selection.interior");
   public static TranslationTextComponent GUI_TARDIS_SELECTION_EXTERIOR = register("container.tardis_selection.exterior");
  
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_NAME = register("container.waypoint_writer.name");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_LOCATION = register("container.waypoint_writer.location");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_POSITION = register("container.waypoint_writer.position");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_FACING = register("container.waypoint_writer.facing");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_EMPTY = register("container.waypoint_writer.empty");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_CONFIRM_LINE_1 = register("container.waypoint_writer.confirm_line_1");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_CONFIRM_LINE_2 = register("container.waypoint_writer.confirm_line_2");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_ERROR = register("container.waypoint_writer.error");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_TRY_LATER = register("container.waypoint_writer.try_later");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_MODULE_USED = register("container.waypoint_writer.module_used");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_USE_GOLD_LINE_1 = register("container.waypoint_writer.use_gold_line_1");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_USE_GOLD_LINE_2 = register("container.waypoint_writer.use_gold_line_2");
   public static TranslationTextComponent GUI_WAYPOINT_WRITER_NOT_REUSABLE = register("container.waypoint_writer.not_reusable");
  
   public static TranslationTextComponent GUI_HOLO_USERNAME = register("container.hologram.username");
   public static TranslationTextComponent GUI_HOLO_SOLID = register("container.hologram.solid");
   public static TranslationTextComponent GUI_HOLO_SLIM = register("container.hologram.slim");
   public static TranslationTextComponent GUI_HOLO_BASE = register("container.hologram.base");
   public static TranslationTextComponent GUI_HOLO_LOCKED = register("container.hologram.locked");
   public static TranslationTextComponent GUI_HOLO_ANIMATED = register("container.hologram.animated");

  
   public static TranslationTextComponent COMMAND_DM_TARDIS_NO_TARDIS = register("command.dalekmod.error.tardis.no_tardis");
   public static TranslationTextComponent COMMAND_DM_TARDIS_CANT_FIND_TILE = register("command.dalekmod.error.tardis.cant_find_tile");
   public static TranslationTextComponent COMMAND_DM_TARDIS_CANT_FIND_DATA = register("command.dalekmod.error.tardis.cant_find_data");

  
   public static TranslationTextComponent BLOCK_WAX_ALREADY_WAXED = register("block.dalekmod.wax.already_waxed");
   public static TranslationTextComponent BLOCK_WAX_COMPLETED = register("block.dalekmod.wax.completed");

  
   public static TranslationTextComponent ITEM_DIMENSIONAL_CIRCUIT_DESC = register("item.dalekmod.dimensional_circuit_desc");
   public static TranslationTextComponent ITEM_CHAMELEON_CIRCUIT_DESC = register("item.dalekmod.chameleon_circuit_desc");
   public static TranslationTextComponent ITEM_FLIGHT_FLUID_LINK_DESC = register("item.dalekmod.flight_fluid_link_desc");
   public static TranslationTextComponent ITEM_FUEL_FLUID_LINK_DESC = register("item.dalekmod.fuel_fluid_link_desc");
   public static TranslationTextComponent ITEM_ACCURACY_FLUID_LINK_DESC = register("item.dalekmod.accuracy_fluid_link_desc");
   public static TranslationTextComponent TARDIS_COMPASS = register("item.dalekmod.tardis_compass");

  
   public static TranslationTextComponent INVALID_ITEM = register("notice.dalekmod.kerblam.invalid_item");
   public static TranslationTextComponent KERBLAM_PURCHASE = register("notice.dalekmod.kerblam.purchase");
   public static TranslationTextComponent KERBLAM_NOT_ENOUGH_XP = register("notice.dalekmod.kerblam.xp_invalid");
   public static TranslationTextComponent KERBLAM_DELIVERY_FULFILLED = register("notice.dalekmod.kerblam.delivery_fulfilled");
   public static TranslationTextComponent KERBLAM_IT = register("notice.dalekmod.kerblam.kerblam_it");
  
  private static TranslationTextComponent register(String s) {
     return new TranslationTextComponent(s);
  }

  
   private static Map<String, TranslationTextComponent> DIMENSION_NAMES_CACHE = new HashMap<>();
   private static Map<String, TranslationTextComponent> SCANNER_FACING_ROT_CACHE = new HashMap<>();
   private static String SCANNER_FACING_ROTATION = "screen.dalekmod.scanner.facing.";
   public static String COMMAND_DM_TARDIS_SET_VALUE = "command.dalekmod.success.tardis.data.set.";

  
  public static IFormattableTextComponent getDimensionTranslation(String name, boolean reload) {
     if (name == null) return (IFormattableTextComponent)SCANNER_ERROR; 
     String reformatted = ("" + name).replace(":", ".");
    
     if (!DIMENSION_NAMES_CACHE.containsKey("dimension." + reformatted + ".name")) DIMENSION_NAMES_CACHE.put("dimension." + reformatted + ".name", new TranslationTextComponent("dimension." + reformatted + ".name"));
    
     if (((TranslationTextComponent)DIMENSION_NAMES_CACHE.get("dimension." + reformatted + ".name")).getString().startsWith("dimension.")) {
       reformatted = name.substring(name.indexOf(':') + 1).replaceAll("_", " ");
      
       Pattern pattern = Pattern.compile("(^[a-z]| [a-z])");
       Matcher matcher = pattern.matcher(reformatted);
      
       StringBuffer noKeyName = new StringBuffer();
       for (; matcher.find(); matcher.appendReplacement(noKeyName, matcher.group().toUpperCase()));
       matcher.appendTail(noKeyName);
      
       return (IFormattableTextComponent)new StringTextComponent(noKeyName.toString());
    } 
    
     if (reload) DIMENSION_NAMES_CACHE.replace("dimension." + reformatted + ".name", new TranslationTextComponent("dimension." + reformatted + ".name"));
    
     return (IFormattableTextComponent)DIMENSION_NAMES_CACHE.get("dimension." + reformatted + ".name");
  }
  
  public static IFormattableTextComponent getFacingTranslation(float rotation, boolean reload) {
     String name1 = SWDMathUtils.rotationToCardinal(rotation).toLowerCase();
    
     if (SCANNER_FACING_ROT_CACHE.containsKey(SCANNER_FACING_ROTATION + name1)) {
       if (reload && ((TranslationTextComponent)SCANNER_FACING_ROT_CACHE.get(SCANNER_FACING_ROTATION + name1)).getString().startsWith(SCANNER_FACING_ROTATION)) SCANNER_FACING_ROT_CACHE.replace(SCANNER_FACING_ROTATION + name1, new TranslationTextComponent(SCANNER_FACING_ROTATION + name1)); 
       return (IFormattableTextComponent)SCANNER_FACING_ROT_CACHE.get(SCANNER_FACING_ROTATION + name1);
     }  SCANNER_FACING_ROT_CACHE.put(SCANNER_FACING_ROTATION + name1, new TranslationTextComponent(SCANNER_FACING_ROTATION + name1));
    
     return (IFormattableTextComponent)SCANNER_FACING_ROT_CACHE.get(SCANNER_FACING_ROTATION + name1);
  }
}


