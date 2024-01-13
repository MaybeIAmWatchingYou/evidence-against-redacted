package com.swdteam.common.command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.math.Position;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.RotationArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class CommandInteriorDoorPos {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
     dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tardis-door")
         .executes(commandContext -> setInteriorPos((CommandSource)commandContext.getSource(), ((CommandSource)commandContext.getSource()).func_197036_d(), (((CommandSource)commandContext.getSource()).func_201004_i()).field_189983_j)))
        
         .then(((RequiredArgumentBuilder)Commands.argument("pos", (ArgumentType)Vec3Argument.vec3())
           .executes(commandContext -> setInteriorPos((CommandSource)commandContext.getSource(), Vec3Argument.func_197300_a(commandContext, "pos"), (((CommandSource)commandContext.getSource()).func_201004_i()).field_189983_j)))
          
           .then(Commands.argument("rotation", (ArgumentType)RotationArgument.func_197288_a())
             .executes(commandContext -> setInteriorPos((CommandSource)commandContext.getSource(), Vec3Argument.func_197300_a(commandContext, "pos"), (RotationArgument.func_200384_a(commandContext, "rotation").func_197282_b((CommandSource)commandContext.getSource())).field_189983_j)))));
  }

  
  public static int setInteriorPos(CommandSource source, Vector3d pos, float rotation) {
     if (source.func_197022_f() instanceof ServerPlayerEntity) {
       ServerPlayerEntity player = (ServerPlayerEntity)source.func_197022_f();
       TardisData data = DMTardis.getTardisFromInteriorPos(player.getPosition());
       if (data.hasPermission((PlayerEntity)player, TardisData.PermissionType.DEFAULT)) {
         if (DMTardis.getIDForXZ((int)pos.x, (int)pos.z) == data.getGlobalID()) {
           data.setInteriorSpawnPosition(new Position(pos.x, pos.y, pos.z));
           data.setInteriorSpawnRotation(rotation);
           data.save();
        } else {
           ChatUtil.sendError((PlayerEntity)player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_DOOR_POS_INVALID, ChatUtil.MessageType.CHAT);
        } 
      } else {
         data.noPermission((PlayerEntity)player);
      } 
    } 
     return 1;
  }
}


