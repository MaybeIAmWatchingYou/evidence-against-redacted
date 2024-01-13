package com.swdteam.common.command.dalekmod.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.swdteam.common.init.DMTardisRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;








public class ChameleonArgument
  implements ArgumentType<ChameleonArgument.IChameleonArgument>, Serializable
{
  private static final long serialVersionUID = 1L;

  public static ChameleonArgument skin() {
     return new ChameleonArgument();
  }

  public static IChameleonArgument getChameleon(CommandContext<?> context, String name) {
     return (IChameleonArgument)context.getArgument(name, IChameleonArgument.class);
  }


  public IChameleonArgument parse(StringReader reader) throws CommandSyntaxException {
     String text = reader.getRemaining();
     reader.setCursor(reader.getTotalLength());
     final String[] split = text.split(" ");
     return new IChameleonArgument()
      {
        public int skinID() {
           return (split.length > 1) ? Integer.parseInt(split[1]) : 0;
        }


        public String skinRegName() {
           return (split.length > 0) ? split[0] : "dalekmod:tardis_capsule";
        }


        public boolean specifySkinID() {
           return (split.length > 1);
        }
      };
  }


  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> ctx, SuggestionsBuilder builder) {
     List<String> suggestions = new ArrayList<>();
     ResourceLocation[] reg = (ResourceLocation[])DMTardisRegistry.getRegistry().keySet().toArray((Object[])new ResourceLocation[0]);

     for (ResourceLocation r : reg) {
       if (builder.getRemaining().startsWith(r.toString() + " ")) {
         for (int i = 0; i < DMTardisRegistry.getExterior(r).getData().getSkinCount(); i++) {
           suggestions.add(r.toString() + " " + i);
        }
      } else {
         suggestions.add(r.toString());
      }
    }

     return ISuggestionProvider.func_197005_b(suggestions, builder);
  }

  public static interface IChameleonArgument {
    int skinID();

    boolean specifySkinID();

    String skinRegName();
  }
}


