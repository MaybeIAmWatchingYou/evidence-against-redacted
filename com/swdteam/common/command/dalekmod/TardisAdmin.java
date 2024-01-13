package com.swdteam.common.command.dalekmod;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.swdteam.common.command.dalekmod.argument.ChameleonArgument;
import com.swdteam.common.command.dalekmod.argument.FacingArgument;
import com.swdteam.common.command.dalekmod.argument.LocationArgument;
import com.swdteam.common.command.dalekmod.argument.SideArgument;
import com.swdteam.common.command.dalekmod.argument.TardisArgument;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.Location;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tardis.data.UserTardises;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.util.LightAlternatorToggle;
import com.swdteam.util.SWDMathUtils;
import com.swdteam.util.math.Position;
import java.awt.Color;
import java.io.File;
import java.util.Collection;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandSource;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.FolderName;

public class TardisAdmin {
   public static final SimpleCommandExceptionType ERROR_CANT_DATA = new SimpleCommandExceptionType((Message)DMTranslationKeys.COMMAND_DM_TARDIS_CANT_FIND_DATA);
  
  public static int listTardis(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
     GameProfile profile = getPlayerArgument(ctx);
     DMTardis.userTardisesSanityCheck(profile.getId());
     List<Integer> ids = DMTardis.getUserTardises(profile.getId()).getTardises();
    
     StringTextComponent stringTextComponent = stringcomp("");
     stringTextComponent.func_240702_b_(profile.getName()).func_240702_b_(" has the TARDIS IDs: ").func_240702_b_(ids.toString()).func_240699_a_(TextFormatting.GREEN);
     sendText(ctx, (ITextComponent)stringTextComponent, false);
    
     return ids.size();
  }






  
  public static int tardisInfoFull(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
     TardisData data = TardisArgument.getTardis(ctx, "tardis_id").getData();
     IFormattableTextComponent TARDIS = DMTranslationKeys.SCANNER_TITLE_TARDIS_INFO.func_230532_e_();
     IFormattableTextComponent id = stringcomp("ID: " + data.getGlobalID()).func_240699_a_(TextFormatting.YELLOW);
     sendText(ctx, (ITextComponent)setStyle(TARDIS, TextFormatting.BLUE, hoverShowText((ITextComponent)id)), false);
     getOrSetDatabyName("owner", ctx, false);
     getOrSetDatabyName("lock", ctx, false);
     getOrSetDatabyName("chameleon", ctx, false);
     getOrSetDatabyName("light", ctx, false);
     sendText(ctx, (ITextComponent)stringcomp(""), false);
     getOrSetDatabyName("location", ctx, false);
     sendText(ctx, (ITextComponent)stringcomp(""), false);
     getOrSetDatabyName("destination", ctx, false);
     sendText(ctx, (ITextComponent)stringcomp(""), false);
     sendText(ctx, (ITextComponent)stringcomp(" - TARDIS STATS -").func_240699_a_(TextFormatting.RED), false);
     IFormattableTextComponent fuel = setStyle(bargenerator((float)(data.getFuel() / 100.0D), 60.0F, 0.1F), hoverShowText((ITextComponent)stringcomp("Fuel: " + ((int)(data.getFuel() * 100.0D) / 100.0F) + "%").func_240699_a_(TextFormatting.GOLD))).func_240702_b_("\n");
     IFormattableTextComponent flittim = setStyle(bargenerator(data.getFluidLinkFlightTime() / 100.0F, 20.0F, 0.37F), hoverShowText((ITextComponent)stringcomp("Flight Time " + data.getFluidLinkFlightTime() + "%").func_240699_a_(TextFormatting.GREEN)));
     IFormattableTextComponent fulcoms = setStyle(bargenerator(data.getFluidLinkFuelConsumption() / 100.0F, 20.0F, 0.0F), hoverShowText((ITextComponent)stringcomp("Fuel Consumption " + data.getFluidLinkFuelConsumption() + "%").func_240699_a_(TextFormatting.RED)));
     IFormattableTextComponent accurac = setStyle(bargenerator(data.getFluidLinkAccuracy() / 100.0F, 20.0F, 0.55F), hoverShowText((ITextComponent)stringcomp("Accuracy " + data.getFluidLinkAccuracy() + "%").func_240699_a_(TextFormatting.AQUA)));
     sendText(ctx, (ITextComponent)fuel.func_230529_a_((ITextComponent)flittim).func_230529_a_((ITextComponent)fulcoms).func_230529_a_((ITextComponent)accurac), false);
     return data.getGlobalID();
  }
  
  private static void sendText(CommandContext<CommandSource> ctx, ITextComponent text, boolean b) {
    try {
       if (((CommandSource)ctx.getSource()).func_197035_h() != null) {
         ((CommandSource)ctx.getSource()).func_197030_a(text, b);
        return;
      } 
     } catch (CommandSyntaxException commandSyntaxException) {}
  }
  
  public static int getOrSetData(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
     String[] data_nodes = getDataNodes(ctx);





    
     String data_node = data_nodes[5];
     boolean set = (data_nodes[4] == "set");
     return getOrSetDatabyName(data_node, ctx, set);
  }
  
  public static int getOrSetDatabyName(String data_node, CommandContext<CommandSource> ctx, boolean set) throws CommandSyntaxException {
     TardisData data = TardisArgument.getTardis(ctx, "tardis_id").getData();
     IFormattableTextComponent prefix = getTranslationbyName(data_node, data, ctx);
     StringTextComponent stringTextComponent = stringcomp("");
     int result = 0;
     if (set) {
       IFormattableTextComponent old = getDataMessagebyName(data_node, data, ctx);
       result = setDatabyName(data_node, data, ctx);
       data.save();
       IFormattableTextComponent success = (new TranslationTextComponent(DMTranslationKeys.COMMAND_DM_TARDIS_SET_VALUE + data_node)).func_240699_a_(TextFormatting.GREEN);
       IFormattableTextComponent id = stringcomp("ID: " + data.getGlobalID()).func_240699_a_(TextFormatting.YELLOW);
       setStyle(success, hoverShowText((ITextComponent)id));
       ((CommandSource)ctx.getSource()).func_197030_a((ITextComponent)success, true);
       stringTextComponent.func_230529_a_((ITextComponent)prefix).func_230529_a_((ITextComponent)old).func_230529_a_((ITextComponent)stringcomp("\nNew >\n").func_240699_a_(TextFormatting.GREEN));
    } else {
       result = getDatabyName(data_node, data, ctx);
    } 
     IFormattableTextComponent now = getDataMessagebyName(data_node, data, ctx);
     stringTextComponent.func_230529_a_((ITextComponent)prefix).func_230529_a_((ITextComponent)now);
    try {
       if (((CommandSource)ctx.getSource()).func_197035_h() != null) sendText(ctx, (ITextComponent)stringTextComponent, false); 
     } catch (CommandSyntaxException e) {
       ((CommandSource)ctx.getSource()).func_197030_a((ITextComponent)stringcomp("result " + result), false);
    } 
    
     return result;
  }
  private static int getDatabyName(String name, TardisData data, CommandContext<CommandSource> ctx) { int size;
    File folder;
     Location loc = data.isInFlight() ? data.getPreviousLocation() : data.getCurrentLocation();
     TardisFlightData dest = TardisFlightPool.getFlightData(data);
     Location destloc = new Location(dest.getPos(), dest.dimensionWorldKey());
     destloc.setFacing(dest.getRotationAngle());
    
     switch (name) {
      
      case "owner":
         size = 0;
         folder = new File(((CommandSource)ctx.getSource()).func_197028_i().func_240776_a_(FolderName.field_237253_i_) + "/tardis/ownerlookup");
         for (File datafile : folder.listFiles()) {
           if (datafile.getName().contains(data.getOwner_uuid().toString()))
             break;  size++;
        } 
         return size;
      case "lock":
         return data.isLocked() ? 1 : 0;
      case "chameleon":
         return formatInt(getRegIndex(data.getTardisExterior().getRegName()), data.getSkinID());
      case "location":
         if ((getDataNodes(ctx)).length == 7) { int i; switch (LocationArgument.getString(ctx, "location")) {
            case "x":
               return (int)loc.getPosition().func_82615_a();
            case "y":
               return (int)loc.getPosition().func_82617_b();
            case "z":
               return (int)loc.getPosition().func_82616_c();
            case "facing":
               return (int)loc.getFacing();
            case "dimension":
               i = 0;
               for (ResourceLocation id : ((CommandSource)ctx.getSource()).func_197028_i().func_244267_aX().func_230520_a_().func_148742_b()) {
                 if (id.toString().equalsIgnoreCase(loc.getDimension()))
                   break;  i++;
              } 
               return i;
          }  }
          return formatInt(loc.getPosition().toBlockPos());
      case "destination":
         if ((getDataNodes(ctx)).length == 7) { int i; switch (LocationArgument.getString(ctx, "location")) {
            case "x":
               return (int)destloc.getPosition().func_82615_a();
            case "y":
               return (int)destloc.getPosition().func_82617_b();
            case "z":
               return (int)destloc.getPosition().func_82616_c();
            case "facing":
               return (int)destloc.getFacing();
            case "dimension":
               i = 0;
               for (ResourceLocation id : ((CommandSource)ctx.getSource()).func_197028_i().func_244267_aX().func_230520_a_().func_148742_b()) {
                 if (id.toString().equalsIgnoreCase(loc.getDimension()))
                   break;  i++;
              } 
               return i;
          }  }
          return formatInt(destloc.getPosition().toBlockPos());
      case "fuel":
         return (int)data.getFuel();
      case "accuracy":
         return data.getFluidLinkAccuracy();
      case "flight-time":
         return data.getFluidLinkFlightTime();
      case "fuel-consumption":
         return data.getFluidLinkFuelConsumption();
      case "interior":
         return formatInt(data.getInteriorSpawnPosition().toBlockPos());
      case "light":
         if ((getDataNodes(ctx)).length == 7) switch (SideArgument.getString(ctx, "side")) {
            case "left":
               return (data.getLighting()).left;
            case "right":
               return (data.getLighting()).right;
            case "selected":
               return (data.getLighting()).selected.ordinal();
          }  
         return data.getLighting().getCurrent();
    } 
     return 0; } private static int setDatabyName(String name, TardisData data, CommandContext<CommandSource> ctx) throws CommandSyntaxException { GameProfile profile; int size; File folder; ServerWorld serverWorld; ChameleonArgument.IChameleonArgument skin; BlockPos currentPos; int i; TardisFlightData flight;
    TileEntity te;
    Vector3d p;
    TardisData.Lighting lighting;
    int val;
     if (data == null) return 0; 
     String[] data_nodes = getDataNodes(ctx);
     String data_nodes_string = String.join(" ", (CharSequence[])data_nodes);
     int result = 0;
     BlockPos pos = data.getCurrentLocation().getBlockPosition();
     RegistryKey<World> key = data.getCurrentLocation().dimensionWorldKey();
     float facing = data.getCurrentLocation().getFacing();
     switch (name) {
      case "owner":
         if (data.getOwner_uuid() != null) {
           UserTardises usrTar = DMTardis.getUserTardises(data.getOwner_uuid());
           usrTar.removeTardis(data.getGlobalID());
           DMTardis.getOwnerLookup().put(data.getOwner_uuid(), usrTar);
        } 
         profile = getPlayerArgument(ctx);
         data.setOwnerName(profile.getName());
         data.setOwnerUUID(profile.getId());
         if (data.getOwner_uuid() != null) {
           UserTardises usrTar = DMTardis.getUserTardises(data.getOwner_uuid());
           usrTar.addTardis(data.getGlobalID());
           DMTardis.getOwnerLookup().put(data.getOwner_uuid(), usrTar);
        } 
         size = 0;
         folder = new File(((CommandSource)ctx.getSource()).func_197028_i().func_240776_a_(FolderName.field_237253_i_) + "/tardis/ownerlookup");
         for (File datafile : folder.listFiles()) {
           if (datafile.getName().contains(data.getOwner_uuid().toString()))
             break;  size++;
        } 
         result = size;
        break;
      case "lock":
         data.setLocked(BoolArgumentType.getBool(ctx, "lock"));

        
         serverWorld = ((CommandSource)ctx.getSource()).func_197023_e();
         currentPos = data.getCurrentLocation().getBlockPosition();
         te = serverWorld.getTileEntity(currentPos);
         if (te instanceof TardisTileEntity && data.isLocked()) ((TardisTileEntity)te).closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);


        
         result = data.isLocked() ? 1 : 0;
        break;
      case "chameleon":
         skin = ChameleonArgument.getChameleon(ctx, "skin");
         if (data.getTardisExterior().getRegName().equalsIgnoreCase(skin.skinRegName())) { if (skin.specifySkinID())
           { data.setSkinID(skin.skinID()); }
          
           else if (data.getSkinID() + 1 < data.getTardisExterior().getData().getSkinCount()) { data.setSkinID(data.getSkinID() + 1); }
           else { data.setSkinID(0); }
           }
        else
         { data.setExterior(skin.skinRegName());
           data.setSkinID(0); }

        
         result = formatInt(getRegIndex(data.getTardisExterior().getRegName()), data.getSkinID());
        break;
      case "location":
         data.setPreviousLocation(data.getCurrentLocation());
         for (i = 6; i < data_nodes.length; i++) {
           switch (data_nodes[i]) {
            case "position":
              try {
                 pos = BlockPosArgument.func_197274_b(ctx, "position");
               } catch (CommandSyntaxException commandSyntaxException) {}
              break;
            case "dimension":
              try {
                 key = DimensionArgument.func_212592_a(ctx, "dimension").getDimensionKey();
               } catch (Exception exception) {}
              break;
            case "facing":
               facing = SWDMathUtils.cardinalToFloat(FacingArgument.getString(ctx, "facing"));
              break;
          } 

        
        } 
         result = formatInt(pos);
         data.setCurrentLocation(pos, key);
         data.getCurrentLocation().setFacing(facing);
        
         if (data_nodes_string.contains("summon"))
           try { boolean instant = !BoolArgumentType.getBool(ctx, "summon");
             summonTardis(((CommandSource)ctx.getSource()).func_197028_i().getWorld(data.getCurrentLocation().dimensionWorldKey()), ((CommandSource)ctx.getSource()).func_197028_i().getWorld(key), data, pos, facing, instant); }
           catch (Exception exception) {} 
        break;
      case "destination":
         flight = TardisFlightPool.getFlightData(data);
         pos = flight.getPos();
         key = flight.dimensionWorldKey();
         facing = flight.getRotationAngle();

        
         if (data_nodes_string.contains("position")) { try {
             pos = BlockPosArgument.func_197274_b(ctx, "position");
             ServerWorld serverWorld1 = ((CommandSource)ctx.getSource()).func_197023_e();
             BlockState state = serverWorld1.getBlockState(pos);
             boolean isAir = (state.func_185904_a() == Material.field_151579_a);
             boolean CmdBlk = (state.func_203425_a(Blocks.field_150483_bI) || state.func_203425_a(Blocks.field_185777_dd) || state.func_203425_a(Blocks.field_185776_dc));
             if (!isAir && !CmdBlk) {
               state = serverWorld1.getBlockState(pos.func_177984_a());
               isAir = (state.func_185904_a() == Material.field_151579_a);
              
               if (isAir) pos = pos.func_177984_a(); 
            } 
             result = 1;
           } catch (CommandSyntaxException commandSyntaxException) {} }
         else if ((((CommandSource)ctx.getSource()).func_197022_f()).world.getDimensionKey().equals(key))
         { pos = new BlockPos(((CommandSource)ctx.getSource()).func_197036_d());
           result = 1; }


        
         if (data_nodes_string.contains("dimension")) { try {
             key = DimensionArgument.func_212592_a(ctx, "dimension").getDimensionKey();
           } catch (Exception exception) {} }
         else { key = ((CommandSource)ctx.getSource()).func_197023_e().getDimensionKey(); }

        
         if (data_nodes_string.contains("facing")) { try {
             facing = SWDMathUtils.cardinalToFloat(FacingArgument.getString(ctx, "facing"));
           } catch (Exception exception) {} }
        else { try {
             facing = (((CommandSource)ctx.getSource()).func_201004_i()).field_189983_j;
           } catch (Exception e2) {
             facing = SWDMathUtils.SnapRotationToCardinal((float)(Math.random() * 360.0D));
          }  }
        
         flight.setPos(pos);
         flight.setDimensionKey(key);
         flight.setRotationAngle(facing);
         result = formatInt(pos);
        
         if (data_nodes_string.contains("summon"))
           try { boolean instant = !BoolArgumentType.getBool(ctx, "summon");
             summonTardis(((CommandSource)ctx.getSource()).func_197028_i().getWorld(data.getCurrentLocation().dimensionWorldKey()), ((CommandSource)ctx.getSource()).func_197028_i().getWorld(key), data, pos, facing, instant); }
           catch (Exception exception) {} 
        break;
      case "fuel":
         data.setFuel(FloatArgumentType.getFloat(ctx, "value"));
         result = (int)data.getFuel();
        break;
      case "flight-time":
         data.setFluidLinkFlightTime(IntegerArgumentType.getInteger(ctx, "value"));
         result = data.getFluidLinkFlightTime();
        break;
      case "fuel-consumption":
         data.setFluidLinkFuelConsumption(IntegerArgumentType.getInteger(ctx, "value"));
         result = data.getFluidLinkFuelConsumption();
        break;
      case "accuracy":
         data.setFluidLinkAccuracy(IntegerArgumentType.getInteger(ctx, "value"));
         result = data.getFluidLinkAccuracy();
        break;
      case "interior":
         p = Vec3Argument.func_197300_a(ctx, "coords");
         data.setInteriorSpawnPosition(new Position(p.x, p.y, p.z));
        try {
           float rot = FloatArgumentType.getFloat(ctx, "rotation");
           data.setInteriorSpawnRotation(rot);
           result = 1;
         } catch (Exception e) {
           data.setInteriorSpawnRotation((((CommandSource)ctx.getSource()).func_201004_i()).field_189983_j);
           result = 1;
        } 
         result = formatInt(new BlockPos(p));
        break;
      case "light":
         lighting = data.getLighting();
         val = 100;
        try {
           val = IntegerArgumentType.getInteger(ctx, "value");
         } catch (Exception exception) {}
         switch (data_nodes[6]) {
          case "selected":
             lighting.setCurrent(val);
            break;
          case "other":
             lighting.setOther(val);
            break;
          case "alternate":
             lighting.selected = lighting.selected.invert();
            break;
          case "left":
             lighting.left = val;
            break;
          case "right":
             lighting.right = val;
            break;
        } 

        
         data.setTardisLighting(((CommandSource)ctx.getSource()).func_197028_i());
         result = (lighting.selected == LightAlternatorToggle.LEFT) ? -lighting.left : lighting.right;
        break;
    } 

    
     data.save();
     return result; }

  
  private static void summonTardis(ServerWorld prevWorld, ServerWorld destWorld, TardisData data, BlockPos destPos, float facing, boolean instant) {
     BlockPos oldPosition = data.getCurrentLocation().getBlockPosition();
     destWorld.playSound(null, destPos, (SoundEvent)DMSoundEvents.ENTITY_STATTENHEIM_REMOTE_SUMMON.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);

    
     TileEntity te = prevWorld.getTileEntity(oldPosition);
     if (te instanceof TardisTileEntity) {
       if (TardisActionList.doAnimation(prevWorld, oldPosition)) { ((TardisTileEntity)te).setState(TardisState.DEMAT); }
       else { prevWorld.setBlockState(oldPosition, Blocks.AIR.getDefaultState()); }
    
    }
    
     TardisFlightPool.completeFlight(destWorld.getServer(), data);
     destWorld.setBlockState(destPos, ((Block)DMBlocks.TARDIS.get()).getDefaultState());
     data.setPreviousLocation(data.getCurrentLocation());
     data.setCurrentLocation(destPos, destWorld.getDimensionKey());
    
     te = destWorld.getTileEntity(destPos);
     if (te instanceof TardisTileEntity) {
       TardisTileEntity tardis = (TardisTileEntity)te;
       tardis.globalID = data.getGlobalID();
       tardis.closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);
       tardis.rotation = facing;
       tardis.setState(instant ? TardisState.NEUTRAL : TardisState.REMAT);
       data.getCurrentLocation().setFacing(tardis.rotation);
    } 
     data.save(); } private static IFormattableTextComponent getDataMessagebyName(String name, TardisData data, CommandContext<CommandSource> ctx) { IFormattableTextComponent uuid, ext_n, ext_s, ext_id, a, b;
    boolean isleft;
    IFormattableTextComponent left, right, l, r;
    float sel;
     Location loc = data.isInFlight() ? data.getPreviousLocation() : data.getCurrentLocation();
     TardisFlightData dest = TardisFlightPool.getFlightData(data);
     Location destloc = new Location(dest.getPos(), dest.dimensionWorldKey());
     destloc.setFacing(dest.getRotationAngle());
     switch (name) {
      case "owner":
         uuid = stringcomp("UUID: " + data.getOwner_uuid()).func_240699_a_(TextFormatting.DARK_GRAY);
         return setStyle((IFormattableTextComponent)stringcomp(data.getOwner_name()), TextFormatting.WHITE, hoverShowText((ITextComponent)uuid), clickSuggest("/msg " + data.getOwner_name()));
      case "lock":
         return data.isLocked() ? DMTranslationKeys.SCANNER_DOOR_LOCKED.func_230532_e_() : DMTranslationKeys.SCANNER_DOOR_UNLOCKED.func_230532_e_();
      case "chameleon":
         ext_n = (new StringTextComponent(data.getTardisExterior().getData().getExteriorName().getString())).func_230532_e_();
         ext_s = (new StringTextComponent(data.getTardisExterior().getData().getDescription().getString())).func_230532_e_().func_240699_a_(TextFormatting.GRAY);
         ext_id = stringcomp("\n" + data.getTardisExterior().getRegName() + "[" + data.getSkinID() + "]").func_240699_a_(TextFormatting.DARK_GRAY);
         ext_s.func_230529_a_((ITextComponent)ext_id);
         return setStyle(ext_n, TextFormatting.WHITE, hoverShowText((ITextComponent)ext_s), null);
      case "location":
         a = getfromloc(loc);
         if (data.isInFlight()) a.func_240702_b_("\n").func_230529_a_((ITextComponent)DMTranslationKeys.SCANNER_IN_FLIGHT.func_230532_e_().func_240699_a_(TextFormatting.LIGHT_PURPLE)); 
         return a;
      case "destination":
         b = getfromloc(destloc);
         return b;
      case "fuel":
         return setStyle(bargenerator((float)(data.getFuel() / 100.0D), 100.0F, 0.1F), hoverShowText((ITextComponent)stringcomp("Fuel: " + ((int)(data.getFuel() * 100.0D) / 100.0F) + "%").func_240699_a_(TextFormatting.GOLD)));
      case "flight-time":
         return setStyle(bargenerator(data.getFluidLinkFlightTime() / 100.0F, 33.0F, 0.37F), hoverShowText((ITextComponent)stringcomp("Flight Time " + data.getFluidLinkFlightTime() + "%").func_240699_a_(TextFormatting.GREEN)));
      case "fuel-consumption":
         return setStyle(bargenerator(data.getFluidLinkFuelConsumption() / 100.0F, 33.0F, 0.0F), hoverShowText((ITextComponent)stringcomp("Fuel Consumption " + data.getFluidLinkFuelConsumption() + "%").func_240699_a_(TextFormatting.RED)));
      case "accuracy":
         return setStyle(bargenerator(data.getFluidLinkAccuracy() / 100.0F, 33.0F, 0.55F), hoverShowText((ITextComponent)stringcomp("Accuracy " + data.getFluidLinkAccuracy() + "%").func_240699_a_(TextFormatting.AQUA)));
      case "interior":
         return getInteriorinfo(data);
      case "light":
         isleft = ((data.getLighting()).selected == LightAlternatorToggle.LEFT);
         left = stringcomp((data.getLighting()).left + "%").func_240699_a_(TextFormatting.DARK_GRAY);
         right = stringcomp((data.getLighting()).right + "%").func_240699_a_(TextFormatting.DARK_GRAY);
         l = stringcomp(" \\").func_240699_a_(TextFormatting.DARK_RED).func_230529_a_((ITextComponent)stringcomp("_ ").func_240699_a_(TextFormatting.GOLD));
         r = stringcomp(" _").func_240699_a_(TextFormatting.GOLD).func_230529_a_((ITextComponent)stringcomp("/ ").func_240699_a_(TextFormatting.DARK_RED));
        
         if ((getDataNodes(ctx)).length == 7) switch (getDataNodes(ctx)[6]) {
            case "left":
               isleft = true;
              break;
            case "right":
               isleft = false;
              break;
          } 
        
         sel = isleft ? (data.getLighting()).left : (data.getLighting()).right;
        
         if (sel >= 0.0F) {
           float hs = (float)(0.1D - Math.pow((sel / 100.0F - 1.0F), 4.0D) * 0.10999999940395355D);
           if (hs < 0.0F) hs = 0.0F; 
           if (hs > 1.0F) hs = 1.0F; 
           Color color = Color.getHSBColor(hs, 1.0F - sel / 100.0F, 0.7F * sel / 100.0F + 0.3F);
           (isleft ? left : right).func_230530_a_((isleft ? left : right).func_150256_b().func_240718_a_(getColor(color)));
        } 
        
         if ((getDataNodes(ctx)).length == 7) switch (getDataNodes(ctx)[6]) {
            case "left":
               return left;
            case "right":
               return right;
            case "selected":
               return (IFormattableTextComponent)stringcomp((data.getLighting()).selected.toString());
          } 
        
         return left.func_230529_a_((ITextComponent)(isleft ? l : r).func_230529_a_((ITextComponent)right));
    } 
     return (IFormattableTextComponent)stringcomp(""); }
  
  private static IFormattableTextComponent getTranslationbyName(String name, TardisData data, CommandContext<CommandSource> ctx) {
    IFormattableTextComponent title;
    IFormattableTextComponent subtitle;
     switch (name) {
      case "owner":
         return DMTranslationKeys.SCANNER_OWNER.func_230532_e_().func_240702_b_(": ").func_240699_a_(TextFormatting.GRAY);
      case "lock":
         return DMTranslationKeys.SCANNER_DOOR.func_230532_e_().func_240702_b_(": ").func_240699_a_(TextFormatting.GRAY);
      case "chameleon":
         return DMTranslationKeys.SCANNER_DISGUISE.func_230532_e_().func_240702_b_(": ").func_240699_a_(TextFormatting.GRAY);
      case "location":
         title = data.isInFlight() ? DMTranslationKeys.SCANNER_TITLE_LOCATION_PREVIOUS.func_230532_e_() : DMTranslationKeys.SCANNER_TITLE_LOCATION_CURRENT.func_230532_e_();
         subtitle = !data.isInFlight() ? DMTranslationKeys.SCANNER_TITLE_LOCATION_PREVIOUS.func_230532_e_() : DMTranslationKeys.SCANNER_TITLE_LOCATION_CURRENT.func_230532_e_();
         subtitle.func_240699_a_(TextFormatting.LIGHT_PURPLE).func_240702_b_("\n").func_230529_a_((ITextComponent)getDataMessagebyName("location", data, ctx));
         setStyle(title, TextFormatting.LIGHT_PURPLE, hoverShowText((ITextComponent)subtitle));
         return stringcomp(" ").func_230529_a_((ITextComponent)title).func_240702_b_("\n");
      case "destination":
         return stringcomp(" ").func_230529_a_((ITextComponent)DMTranslationKeys.SCANNER_TITLE_FLIGHT_INFO.func_230532_e_()).func_240702_b_("\n").func_240699_a_(TextFormatting.YELLOW);
      case "fuel":
         return stringcomp("FUEL: ").func_240699_a_(TextFormatting.GRAY);
      case "flight-time":
         return stringcomp("F-L: ").func_240699_a_(TextFormatting.GRAY);
      case "fuel-consumption":
         return stringcomp("F-L: ").func_240699_a_(TextFormatting.GRAY);
      case "accuracy":
         return stringcomp("F-L: ").func_240699_a_(TextFormatting.GRAY);
      case "interior":
         return stringcomp("Interior-Position: ").func_240699_a_(TextFormatting.GRAY);
      case "light":
         return stringcomp("Light Alternator: ").func_240699_a_(TextFormatting.GRAY);
    } 
     return (IFormattableTextComponent)stringcomp("");
  }







  
  private static IFormattableTextComponent bargenerator(float value, float count, float hueshift) {
     StringTextComponent stringTextComponent1 = stringcomp("");
     StringTextComponent stringTextComponent2 = stringcomp("");
     for (int i = 0; i < count; i++) {
       IFormattableTextComponent iFormattableTextComponent; float f = value * count - i;
       if (f > 0.0F) {
         stringTextComponent2 = stringcomp("|");
         float hs = (float)(hueshift - Math.pow((value - 1.0F), 4.0D) * 0.10000000149011612D);
         if (hs < 0.0F) hs = 0.0F; 
         if (hs > 1.0F) hs = 1.0F; 
         Color color = Color.getHSBColor(hs, 1.0F - i / count, 1.0F);
         stringTextComponent2.func_230530_a_(stringTextComponent2.func_150256_b().func_240718_a_(getColor(color)));
      } else {
         iFormattableTextComponent = stringcomp(".").func_240699_a_(TextFormatting.DARK_GRAY);
      } 
       stringTextComponent1.func_230529_a_((ITextComponent)iFormattableTextComponent);
    } 
     return (IFormattableTextComponent)stringTextComponent1;
  }
  
  private static IFormattableTextComponent getInteriorinfo(TardisData data) {
     Position pos = data.getInteriorSpawnPosition();
     String coord = "X: " + ((int)(10.0D * pos.func_82615_a()) / 10.0F) + " Y: " + ((int)(10.0D * pos.func_82617_b()) / 10.0F) + " Z: " + ((int)(10.0D * pos.func_82616_c()) / 10.0F) + " Rotation: " + (int)data.getInteriorSpawnRotation();
     return stringcomp(coord).func_240699_a_(TextFormatting.WHITE);
  }
  
  private static IFormattableTextComponent getfromloc(Location loc) {
     IFormattableTextComponent dim = DMTranslationKeys.getDimensionTranslation(loc.getDimension(), true).func_230532_e_().func_240699_a_(TextFormatting.GREEN);
     IFormattableTextComponent dimid = stringcomp(loc.getDimension()).func_240699_a_(TextFormatting.DARK_GRAY);
     String xyz = "X: " + loc.getPosition().func_82615_a() + " Y: " + loc.getPosition().func_82617_b() + " Z: " + loc.getPosition().func_82616_c();
     IFormattableTextComponent pos = stringcomp(xyz).func_240699_a_(TextFormatting.WHITE);
     StringTextComponent stringTextComponent = stringcomp("");
     IFormattableTextComponent facdir = DMTranslationKeys.getFacingTranslation(loc.getFacing(), true).func_240699_a_(TextFormatting.WHITE);
     IFormattableTextComponent facing = DMTranslationKeys.SCANNER_FACING.func_230532_e_().func_240702_b_(": ").func_240699_a_(TextFormatting.GRAY).func_230529_a_((ITextComponent)facdir);
     IFormattableTextComponent heading = stringcomp("Heading: " + loc.getFacing() + " degrees").func_240699_a_(TextFormatting.DARK_GRAY);
     setStyle(dim, hoverShowText((ITextComponent)dimid));
     setStyle(facing, hoverShowText((ITextComponent)heading));
     stringTextComponent.func_230529_a_((ITextComponent)dim).func_240702_b_(" ").func_230529_a_((ITextComponent)pos).func_240702_b_("\n");
     stringTextComponent.func_230529_a_((ITextComponent)facing);
     return (IFormattableTextComponent)stringTextComponent;
  }
  
  private static int getRegIndex(String name) {
     int i = 0;
     ResourceLocation[] reg = (ResourceLocation[])DMTardisRegistry.getRegistry().keySet().toArray((Object[])new ResourceLocation[0]);
     for (int j = 0; j < reg.length; j++) {
       if (reg[j].toString().equals(name)) {
         i = j;
        break;
      } 
    } 
     return i;
  }













  
  private static int formatInt(int a, int b) {
     return 1000000000 + 10000 * a + b;
  }
  
  private static int formatInt(BlockPos pos) {
     int x = (pos.getX() + 30000000) / 6001;
     int z = (pos.getZ() + 30000000) / 6001;
     int y = pos.getY();
     y = ((y > 128) ? 10 : -10) + (y - 128) / 13;
     return y * 100000000 + x * 10000 + z;
  }
  
  private static GameProfile getPlayerArgument(CommandContext<CommandSource> ctx) throws CommandSyntaxException {
     Collection<GameProfile> collection = GameProfileArgument.func_197109_a(ctx, "player");
     if (collection.isEmpty()) throw CommandSource.field_197039_a.create();
    
     GameProfile[] profiles = collection.<GameProfile>toArray(new GameProfile[0]);
    
     if (!profiles[0].isComplete()) throw CommandSource.field_197039_a.create();
    
     return profiles[0];
  }
  
  private static IFormattableTextComponent setStyle(IFormattableTextComponent t, TextFormatting f, HoverEvent h, ClickEvent c) {
     return t.func_230530_a_(t.func_150256_b().func_240721_b_(f).func_240716_a_(h).func_240715_a_(c));
  }
  
  private static IFormattableTextComponent setStyle(IFormattableTextComponent t, TextFormatting f, HoverEvent h) {
     return t.func_230530_a_(t.func_150256_b().func_240721_b_(f).func_240716_a_(h));
  }
  
  private static IFormattableTextComponent setStyle(IFormattableTextComponent t, HoverEvent h) {
     return t.func_230530_a_(t.func_150256_b().func_240716_a_(h));
  }
  
  private static StringTextComponent stringcomp(String n) {
     return new StringTextComponent(n);
  }
  
  private static HoverEvent hoverShowText(ITextComponent t) {
     return new HoverEvent(HoverEvent.Action.field_230550_a_, t);
  }
  
  private static ClickEvent clickSuggest(String s) {
     return new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, s);
  }
  
  private static Color getColor(Color color) {
     return Color.func_240743_a_(color.getRGB());
  }
  
  private static String[] getDataNodes(CommandContext<CommandSource> ctx) {
     String[] data_nodes = new String[ctx.getNodes().size()];
     for (int i = 0; i < data_nodes.length; ) { data_nodes[i] = ((ParsedCommandNode)ctx.getNodes().get(i)).getNode().getName(); i++; }
      return data_nodes;
  }
}


