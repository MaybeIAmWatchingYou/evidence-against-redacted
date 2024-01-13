package com.swdteam.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.swdteam.common.command.dalekmod.SchemSave;
import com.swdteam.common.command.dalekmod.TardisAdmin;
import com.swdteam.common.command.dalekmod.argument.ChameleonArgument;
import com.swdteam.common.command.dalekmod.argument.FacingArgument;
import com.swdteam.common.command.dalekmod.argument.LocationArgument;
import com.swdteam.common.command.dalekmod.argument.SideArgument;
import com.swdteam.common.command.dalekmod.argument.TardisArgument;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.command.arguments.ILocationArgument;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.command.arguments.Vec3Argument;
import net.minecraft.util.ResourceLocation;

public class CommandDalekModBase
{
   private static RequiredArgumentBuilder<CommandSource, TardisArgument.ITardisArgument> tardis_argument = Commands.argument("tardis_id", (ArgumentType)TardisArgument.tardis());
   private static RequiredArgumentBuilder<CommandSource, GameProfileArgument.IProfileProvider> player_argument = Commands.argument("player", (ArgumentType)GameProfileArgument.gameProfile());
   private static RequiredArgumentBuilder<CommandSource, ResourceLocation> dimension_argument = Commands.argument("dimension", (ArgumentType)DimensionArgument.getDimension());
   private static RequiredArgumentBuilder<CommandSource, ILocationArgument> position_argument = Commands.argument("position", (ArgumentType)BlockPosArgument.blockPos());
   private static RequiredArgumentBuilder<CommandSource, String> facing_argument = Commands.argument("facing", (ArgumentType)FacingArgument.facing());
   private static RequiredArgumentBuilder<CommandSource, ChameleonArgument.IChameleonArgument> skin_argument = Commands.argument("skin", (ArgumentType)ChameleonArgument.skin());
   private static RequiredArgumentBuilder<CommandSource, Boolean> summon_argument = Commands.argument("summon", (ArgumentType)BoolArgumentType.bool());
   private static RequiredArgumentBuilder<CommandSource, Boolean> lock_argument = Commands.argument("lock", (ArgumentType)BoolArgumentType.bool());
   private static RequiredArgumentBuilder<CommandSource, Integer> int_argument = Commands.argument("value", (ArgumentType)IntegerArgumentType.integer());
   private static RequiredArgumentBuilder<CommandSource, ILocationArgument> coords_argument = Commands.argument("coords", (ArgumentType)Vec3Argument.vec3());
   private static RequiredArgumentBuilder<CommandSource, Float> rotation_argument = Commands.argument("rotation", (ArgumentType)FloatArgumentType.floatArg());
   private static RequiredArgumentBuilder<CommandSource, String> side_argument = Commands.argument("side", (ArgumentType)SideArgument.side());
   private static RequiredArgumentBuilder<CommandSource, String> location_argument = Commands.argument("location", (ArgumentType)LocationArgument.loc());

  
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
     dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("dalekmod")
         .requires(c -> c.hasPermissionLevel(2)))

        
         .then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("schem")
           .then(Commands.literal("save")
             .then(Commands.argument("save-name", (ArgumentType)MessageArgument.message())
               .executes(c -> SchemSave.saveSchem(c)))))

          
           .then(Commands.literal("load")
             .then(Commands.argument("load-name", (ArgumentType)MessageArgument.message())
               .executes(c -> SchemSave.loadSchem(c)))))

          
           .then(Commands.literal("pos1")
             .executes(c -> SchemSave.pos1(c))))
          
           .then(Commands.literal("pos2")
             .executes(c -> SchemSave.pos2(c))))
          
           .then(Commands.literal("paste")
             .executes(c -> SchemSave.paste(c)))))


        
         .then(((LiteralArgumentBuilder)Commands.literal("tardis")
           .then(tardis_argument
             .then(((LiteralArgumentBuilder)Commands.literal("data")
              
               .then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("get")
                 .executes(c -> TardisAdmin.tardisInfoFull(c)))
                 .then(Commands.literal("owner")
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(Commands.literal("lock")
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(Commands.literal("chameleon")
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(((LiteralArgumentBuilder)Commands.literal("location")
                   .then(location_argument.executes(c -> TardisAdmin.getOrSetData(c))))
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(((LiteralArgumentBuilder)Commands.literal("destination")
                   .then(location_argument.executes(c -> TardisAdmin.getOrSetData(c))))
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(Commands.literal("fuel")
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(Commands.literal("accuracy")
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(Commands.literal("flight-time")
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(Commands.literal("fuel-consumption")
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(Commands.literal("interior")
                   .executes(c -> TardisAdmin.getOrSetData(c))))
                
                 .then(((LiteralArgumentBuilder)Commands.literal("light")
                   .then(side_argument.executes(c -> TardisAdmin.getOrSetData(c))))
                   .executes(c -> TardisAdmin.getOrSetData(c)))))

              
               .then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("set")
                
                 .then(Commands.literal("owner")
                   .then(player_argument
                     .executes(c -> TardisAdmin.getOrSetData(c)))))

                
                 .then(Commands.literal("lock")
                   .then(lock_argument
                     .executes(c -> TardisAdmin.getOrSetData(c)))))

                
                 .then(Commands.literal("chameleon")
                   .then(skin_argument
                     .executes(c -> TardisAdmin.getOrSetData(c)))))

                
                 .then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("location")
                   .then(Commands.literal("position").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)position_argument
                       .executes(c -> TardisAdmin.getOrSetData(c)))
                       .then(Commands.literal("dimension").then(((RequiredArgumentBuilder)dimension_argument
                           .executes(c -> TardisAdmin.getOrSetData(c)))
                           .then(Commands.literal("facing").then(facing_argument
                               .executes(c -> TardisAdmin.getOrSetData(c)))))))

                      
                       .then(Commands.literal("facing").then(facing_argument
                           .executes(c -> TardisAdmin.getOrSetData(c)))))))

                  
                   .then(Commands.literal("dimension").then(((RequiredArgumentBuilder)dimension_argument
                       .executes(c -> TardisAdmin.getOrSetData(c)))
                       .then(Commands.literal("facing").then(facing_argument
                           .executes(c -> TardisAdmin.getOrSetData(c)))))))

                  
                   .then(Commands.literal("facing").then(facing_argument
                       .executes(c -> TardisAdmin.getOrSetData(c))))))

                
                 .then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("destination")
                   .then(Commands.literal("position").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)position_argument
                       .executes(c -> TardisAdmin.getOrSetData(c)))
                       .then(summon_argument
                         .executes(c -> TardisAdmin.getOrSetData(c))))
                      
                       .then(Commands.literal("dimension").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)dimension_argument
                           .executes(c -> TardisAdmin.getOrSetData(c)))
                           .then(summon_argument
                             .executes(c -> TardisAdmin.getOrSetData(c))))
                          
                           .then(Commands.literal("facing").then(((RequiredArgumentBuilder)facing_argument
                               .executes(c -> TardisAdmin.getOrSetData(c)))
                               .then(summon_argument
                                 .executes(c -> TardisAdmin.getOrSetData(c))))))))


                      
                       .then(Commands.literal("facing").then(((RequiredArgumentBuilder)facing_argument
                           .executes(c -> TardisAdmin.getOrSetData(c)))
                           .then(summon_argument
                             .executes(c -> TardisAdmin.getOrSetData(c))))))))


                  
                   .then(Commands.literal("dimension").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)dimension_argument
                       .executes(c -> TardisAdmin.getOrSetData(c)))
                       .then(summon_argument
                         .executes(c -> TardisAdmin.getOrSetData(c))))
                      
                       .then(Commands.literal("facing").then(((RequiredArgumentBuilder)facing_argument
                           .executes(c -> TardisAdmin.getOrSetData(c)))
                           .then(summon_argument
                             .executes(c -> TardisAdmin.getOrSetData(c))))))))


                  
                   .then(Commands.literal("facing").then(((RequiredArgumentBuilder)facing_argument
                       .executes(c -> TardisAdmin.getOrSetData(c)))
                       .then(summon_argument
                         .executes(c -> TardisAdmin.getOrSetData(c)))))))


                
                 .then(Commands.literal("fuel").then(int_argument
                     .executes(c -> TardisAdmin.getOrSetData(c)))))
                
                 .then(Commands.literal("accuracy").then(int_argument
                     .executes(c -> TardisAdmin.getOrSetData(c)))))
                
                 .then(Commands.literal("flight-time").then(int_argument
                     .executes(c -> TardisAdmin.getOrSetData(c)))))
                
                 .then(Commands.literal("fuel-consumption").then(int_argument
                     .executes(c -> TardisAdmin.getOrSetData(c)))))
                
                 .then(Commands.literal("interior").then(((RequiredArgumentBuilder)coords_argument
                     .executes(c -> TardisAdmin.getOrSetData(c)))
                     .then(rotation_argument
                       .executes(c -> TardisAdmin.getOrSetData(c))))))

                
                 .then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("light")
                   .then(Commands.literal("selected")
                     .then(int_argument
                       .executes(c -> TardisAdmin.getOrSetData(c)))))

                  
                   .then(Commands.literal("other")
                     .then(int_argument
                       .executes(c -> TardisAdmin.getOrSetData(c)))))

                  
                   .then(Commands.literal("alternate")
                     .executes(c -> TardisAdmin.getOrSetData(c))))
                  
                   .then(Commands.literal("left")
                     .then(int_argument
                       .executes(c -> TardisAdmin.getOrSetData(c)))))

                  
                   .then(Commands.literal("right")
                     .then(int_argument
                       .executes(c -> TardisAdmin.getOrSetData(c)))))))))








          
           .then(Commands.literal("list")
             .then(player_argument
               .executes(c -> TardisAdmin.listTardis(c))))));
  }
}


