package com.swdteam.common.command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.data.UserTardises;
import com.swdteam.util.ChatUtil;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class CommandTardisOwnership {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
     dispatcher.register((LiteralArgumentBuilder)Commands.literal("tardis-transfer-owner").then(Commands.argument("player", (ArgumentType)EntityArgument.func_197096_c()).executes(commandContext -> {
              if (((CommandSource)commandContext.getSource()).func_197022_f() instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity)((CommandSource)commandContext.getSource()).func_197022_f();
                ServerPlayerEntity player2 = EntityArgument.func_197089_d(commandContext, "player");
                TardisData data = DMTardis.getTardisFromInteriorPos(player.getPosition());
                if (data.hasPermission((PlayerEntity)player, TardisData.PermissionType.DEFAULT)) {
                  if (data.getOwner_uuid() != null) {
                    UserTardises usrTar = DMTardis.getUserTardises(data.getOwner_uuid());
                    usrTar.removeTardis(data.getGlobalID());
                    DMTardis.getOwnerLookup().put(data.getOwner_uuid(), usrTar);
                  }
                  data.setOwnerName(player2.getGameProfile().getName());
                  data.setOwnerUUID(player2.getGameProfile().getId());
                  if (data.getOwner_uuid() != null) {
                    UserTardises usrTar = DMTardis.getUserTardises(data.getOwner_uuid());
                    usrTar.addTardis(data.getGlobalID());
                    DMTardis.getOwnerLookup().put(data.getOwner_uuid(), usrTar);
                  }
                  data.save();
                  ChatUtil.sendCompletedMsg((PlayerEntity)player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_OWNERSHIP_TRANSFER, ChatUtil.MessageType.CHAT);
                  ChatUtil.sendCompletedMsg((PlayerEntity)player2, (IFormattableTextComponent)new TranslationTextComponent(DMTranslationKeys.TARDIS_OWNERSHIP_GIVEN.func_150268_i(), new Object[] { new StringTextComponent(player.getGameProfile().getName()) }), ChatUtil.MessageType.CHAT);
                } else {
                  data.noPermission((PlayerEntity)player);
                }
              }
              return 1;
            })));
  }
}


