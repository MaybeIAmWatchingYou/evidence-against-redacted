package com.swdteam.util;

import java.util.List;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemUtils {
  public static List<ITextComponent> addText(List<ITextComponent> tooltip, String text, TextFormatting colour) {
     tooltip.add(new StringTextComponent(colour + text));
     return tooltip;
  }
  
  public static List<ITextComponent> addEffectText(List<ITextComponent> tooltip, String text, TextFormatting colour) {
     tooltip.add(new StringTextComponent("EFFECT:" + colour + text));
     return tooltip;
  }
  
  public static ITextComponent addText(String text, TextFormatting colour) {
     return (ITextComponent)new StringTextComponent(colour + text);
  }
  
  public static ITextComponent addLangText(String text, TextFormatting colour) {
     return (ITextComponent)(new TranslationTextComponent(text)).func_240699_a_(colour);
  }
  
  public static ITextComponent addEffectText(String text, TextFormatting colour) {
     return (ITextComponent)new StringTextComponent("EFFECT:" + colour + text);
  }
}


