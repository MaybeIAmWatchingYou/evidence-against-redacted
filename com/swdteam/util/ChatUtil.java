package com.swdteam.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ChatUtil
{
  public static void sendMessageToPlayer(PlayerEntity player, String s, MessageType type) {
     player.func_146105_b((ITextComponent)new StringTextComponent(s), (type == MessageType.STATUS_BAR));
  }
  
  public static void sendMessageToPlayer(PlayerEntity player, ITextComponent s, MessageType type) {
     player.func_146105_b(s, (type == MessageType.STATUS_BAR));
  }
  
  public static void sendError(PlayerEntity player, IFormattableTextComponent s, MessageType type) {
     sendMessageToPlayer(player, (ITextComponent)s.func_240699_a_(TextFormatting.RED), type);
  }
  
  public static void sendError(PlayerEntity player, String s, MessageType type) {
     IFormattableTextComponent ss = (new StringTextComponent(s)).func_240699_a_(TextFormatting.RED);
     sendMessageToPlayer(player, (ITextComponent)ss, type);
  }
  
  public static void sendCompletedMsg(PlayerEntity player, String s, MessageType type) {
     IFormattableTextComponent ss = (new StringTextComponent(s)).func_240699_a_(TextFormatting.GREEN);
     sendMessageToPlayer(player, (ITextComponent)ss, type);
  }
  
  public static void sendCompletedMsg(PlayerEntity player, IFormattableTextComponent s, MessageType type) {
     s.func_240699_a_(TextFormatting.GREEN);
    
     sendMessageToPlayer(player, (ITextComponent)s, type);
  }
  
  public enum MessageType {
     CHAT,
     STATUS_BAR;
  }
}


