package com.swdteam.common.command.dalekmod.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.ISuggestionProvider;







public class FacingArgument
  implements ArgumentType<String>, Serializable
{
  private static final long serialVersionUID = 1L;

  public static FacingArgument facing() {
     return new FacingArgument();
  }

  public static String getString(CommandContext<?> context, String name) {
     return (String)context.getArgument(name, String.class);
  }


  public String parse(StringReader reader) throws CommandSyntaxException {
     return reader.readUnquotedString();
  }

   private final List<String> suggestions = Arrays.asList(new String[] { "North", "NorthEast", "NorthWest", "South", "SouthEast", "SouthWest", "East", "West" });


  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> ctx, SuggestionsBuilder builder) {
     return ISuggestionProvider.func_197005_b(this.suggestions, builder);
  }
}


