package com.swdteam.common.command.dalekmod.argument;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisSaveHandler;
import com.swdteam.common.tileentity.TardisTileEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.block.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;








public class TardisArgument
  implements ArgumentType<TardisArgument.ITardisArgument>, Serializable
{
  private static final long serialVersionUID = 1L;
   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0", "~ ~ ~", "0 0 0", "^1 ^ ^-5", "~0.5 ~1 ~-5" });
   public static final SimpleCommandExceptionType ERROR_NO_TARDIS = new SimpleCommandExceptionType((Message)DMTranslationKeys.COMMAND_DM_TARDIS_NO_TARDIS);
   public static final SimpleCommandExceptionType ERROR_CANT_FIND_TILE = new SimpleCommandExceptionType((Message)DMTranslationKeys.COMMAND_DM_TARDIS_CANT_FIND_TILE);

  public static TardisArgument tardis() {
     return new TardisArgument();
  }

  public static ITardisArgument getTardis(CommandContext<CommandSource> ctx, String var_name) {
     return (ITardisArgument)ctx.getArgument(var_name, ITardisArgument.class);
  }

  public ITardisArgument parse(StringReader reader) throws CommandSyntaxException {
     final int id = reader.readInt();
     return new ITardisArgument()
      {
        public int getGlobalID() {
           return id;
        }
      };
  }

  public static TardisData getTardisDataOrException(int id) throws CommandSyntaxException {
     TardisSaveHandler.loadTardisData(id);
     if (DMTardis.getLoadedTardises().containsKey(Integer.valueOf(id))) {
       return (TardisData)DMTardis.getLoadedTardises().get(Integer.valueOf(id));
    }
     throw ERROR_NO_TARDIS.create();
  }

  public static TardisTileEntity getTardisTileOrException(TardisData data) throws CommandSyntaxException {
     ServerWorld world = ServerLifecycleHooks.getCurrentServer().getWorld(data.getCurrentLocation().dimensionWorldKey());
     BlockPos pos = data.getCurrentLocation().getBlockPosition();
     return getTardisTileOrException((World)world, pos);
  }

  public static TardisTileEntity getTardisTileOrException(World world, BlockPos pos) throws CommandSyntaxException {
     if (world.getBlockState(pos) == ((Block)DMBlocks.TARDIS.get()).getDefaultState()) {
       return (TardisTileEntity)world.getTileEntity(pos);
    }
     throw ERROR_CANT_FIND_TILE.create();
  }





















  public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> ctx, SuggestionsBuilder builder) {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_3
    //   8: invokestatic func_71410_x : ()Lnet/minecraft/client/Minecraft;
    //   11: astore #4
    //   13: aload #4
    //   15: getfield field_71439_g : Lnet/minecraft/client/entity/player/ClientPlayerEntity;
    //   18: getfield world : Lnet/minecraft/world/World;
    //   21: invokevirtual getDimensionKey : ()Lnet/minecraft/util/RegistryKey;
    //   24: getstatic com/swdteam/common/init/DMDimensions.TARDIS : Lnet/minecraft/util/RegistryKey;
    //   27: if_acmpne -> 79
    //   30: aload_3
    //   31: new java/lang/StringBuilder
    //   34: dup
    //   35: invokespecial <init> : ()V
    //   38: ldc ''
    //   40: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: aload #4
    //   45: getfield field_71439_g : Lnet/minecraft/client/entity/player/ClientPlayerEntity;
    //   48: invokevirtual getPosX : ()D
    //   51: d2i
    //   52: aload #4
    //   54: getfield field_71439_g : Lnet/minecraft/client/entity/player/ClientPlayerEntity;
    //   57: invokevirtual getPosZ : ()D
    //   60: d2i
    //   61: invokestatic getIDForXZ : (II)I
    //   64: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   67: invokevirtual toString : ()Ljava/lang/String;
    //   70: invokeinterface add : (Ljava/lang/Object;)Z
    //   75: pop
    //   76: goto -> 397
    //   79: aload #4
    //   81: getfield field_71439_g : Lnet/minecraft/client/entity/player/ClientPlayerEntity;
    //   84: invokevirtual getPositionVec : ()Lnet/minecraft/util/math/vector/Vector3d;
    //   87: dconst_0
    //   88: aload #4
    //   90: getfield field_71439_g : Lnet/minecraft/client/entity/player/ClientPlayerEntity;
    //   93: invokevirtual func_70047_e : ()F
    //   96: f2d
    //   97: dconst_0
    //   98: invokevirtual func_72441_c : (DDD)Lnet/minecraft/util/math/vector/Vector3d;
    //   101: astore #5
    //   103: aload #4
    //   105: getfield field_71439_g : Lnet/minecraft/client/entity/player/ClientPlayerEntity;
    //   108: invokevirtual func_70040_Z : ()Lnet/minecraft/util/math/vector/Vector3d;
    //   111: invokevirtual func_72432_b : ()Lnet/minecraft/util/math/vector/Vector3d;
    //   114: astore #6
    //   116: aload #4
    //   118: getfield field_71439_g : Lnet/minecraft/client/entity/player/ClientPlayerEntity;
    //   121: getfield world : Lnet/minecraft/world/World;
    //   124: astore #7
    //   126: aload #5
    //   128: aload #6
    //   130: invokestatic trace : (Lnet/minecraft/util/math/vector/Vector3d;Lnet/minecraft/util/math/vector/Vector3d;)Ljava/util/List;
    //   133: invokeinterface iterator : ()Ljava/util/Iterator;
    //   138: astore #8
    //   140: aload #8
    //   142: invokeinterface hasNext : ()Z
    //   147: ifeq -> 397
    //   150: aload #8
    //   152: invokeinterface next : ()Ljava/lang/Object;
    //   157: checkcast net/minecraft/util/math/BlockPos
    //   160: astore #9
    //   162: aload #7
    //   164: aload #9
    //   166: invokevirtual getBlockState : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;
    //   169: getstatic net/minecraft/block/Blocks.AIR : Lnet/minecraft/block/Block;
    //   172: invokevirtual getDefaultState : ()Lnet/minecraft/block/BlockState;
    //   175: if_acmpeq -> 394
    //   178: aload #7
    //   180: aload #9
    //   182: invokevirtual getBlockState : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;
    //   185: getstatic com/swdteam/common/init/DMBlocks.TARDIS : Lnet/minecraftforge/fml/RegistryObject;
    //   188: invokevirtual get : ()Lnet/minecraftforge/registries/IForgeRegistryEntry;
    //   191: checkcast net/minecraft/block/Block
    //   194: invokevirtual getDefaultState : ()Lnet/minecraft/block/BlockState;
    //   197: if_acmpne -> 248
    //   200: aload_3
    //   201: new java/lang/StringBuilder
    //   204: dup
    //   205: invokespecial <init> : ()V
    //   208: aload #7
    //   210: aload #9
    //   212: invokestatic getTardisTileOrException : (Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lcom/swdteam/common/tileentity/TardisTileEntity;
    //   215: getfield globalID : I
    //   218: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   221: ldc ''
    //   223: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   226: invokevirtual toString : ()Ljava/lang/String;
    //   229: invokeinterface add : (Ljava/lang/Object;)Z
    //   234: pop
    //   235: goto -> 397
    //   238: astore #10
    //   240: aload #10
    //   242: invokevirtual printStackTrace : ()V
    //   245: goto -> 397
    //   248: new net/minecraft/util/math/vector/Vector3d
    //   251: dup
    //   252: aload #9
    //   254: invokevirtual getX : ()I
    //   257: i2d
    //   258: aload #9
    //   260: invokevirtual getY : ()I
    //   263: i2d
    //   264: aload #9
    //   266: invokevirtual getZ : ()I
    //   269: i2d
    //   270: invokespecial <init> : (DDD)V
    //   273: invokestatic grid : (Lnet/minecraft/util/math/vector/Vector3d;)Ljava/util/List;
    //   276: invokeinterface iterator : ()Ljava/util/Iterator;
    //   281: astore #10
    //   283: aload #10
    //   285: invokeinterface hasNext : ()Z
    //   290: ifeq -> 391
    //   293: aload #10
    //   295: invokeinterface next : ()Ljava/lang/Object;
    //   300: checkcast net/minecraft/util/math/BlockPos
    //   303: astore #11
    //   305: aload #7
    //   307: aload #11
    //   309: invokevirtual getBlockState : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;
    //   312: getstatic net/minecraft/block/Blocks.AIR : Lnet/minecraft/block/Block;
    //   315: invokevirtual getDefaultState : ()Lnet/minecraft/block/BlockState;
    //   318: if_acmpeq -> 388
    //   321: aload #7
    //   323: aload #11
    //   325: invokevirtual getBlockState : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;
    //   328: getstatic com/swdteam/common/init/DMBlocks.TARDIS : Lnet/minecraftforge/fml/RegistryObject;
    //   331: invokevirtual get : ()Lnet/minecraftforge/registries/IForgeRegistryEntry;
    //   334: checkcast net/minecraft/block/Block
    //   337: invokevirtual getDefaultState : ()Lnet/minecraft/block/BlockState;
    //   340: if_acmpne -> 388
    //   343: aload_3
    //   344: new java/lang/StringBuilder
    //   347: dup
    //   348: invokespecial <init> : ()V
    //   351: aload #7
    //   353: aload #11
    //   355: invokestatic getTardisTileOrException : (Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Lcom/swdteam/common/tileentity/TardisTileEntity;
    //   358: getfield globalID : I
    //   361: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   364: ldc ''
    //   366: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   369: invokevirtual toString : ()Ljava/lang/String;
    //   372: invokeinterface add : (Ljava/lang/Object;)Z
    //   377: pop
    //   378: goto -> 388
    //   381: astore #12
    //   383: aload #12
    //   385: invokevirtual printStackTrace : ()V
    //   388: goto -> 283
    //   391: goto -> 397
    //   394: goto -> 140
    //   397: aload_3
    //   398: invokeinterface isEmpty : ()Z
    //   403: ifeq -> 436
    //   406: invokestatic getCache : ()Ljava/util/Map;
    //   409: invokeinterface isEmpty : ()Z
    //   414: ifne -> 436
    //   417: invokestatic getCache : ()Ljava/util/Map;
    //   420: invokeinterface keySet : ()Ljava/util/Set;
    //   425: aload_3
    //   426: <illegal opcode> accept : (Ljava/util/List;)Ljava/util/function/Consumer;
    //   431: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   436: aload_3
    //   437: aload_2
    //   438: invokestatic func_197005_b : (Ljava/lang/Iterable;Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;)Ljava/util/concurrent/CompletableFuture;
    //   441: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #88	-> 0
    //   #90	-> 8
    //   #91	-> 13
    //   #92	-> 30
    //   #94	-> 79
    //   #95	-> 103
    //   #96	-> 116
    //   #97	-> 126
    //   #98	-> 162
    //   #99	-> 178
    //   #101	-> 200
    //   #104	-> 235
    //   #102	-> 238
    //   #103	-> 240
    //   #105	-> 245
    //   #107	-> 248
    //   #108	-> 305
    //   #109	-> 321
    //   #111	-> 343
    //   #114	-> 378
    //   #112	-> 381
    //   #113	-> 383
    //   #116	-> 388
    //   #117	-> 391
    //   #119	-> 394
    //   #122	-> 397
    //   #123	-> 406
    //   #124	-> 417
    //   #126	-> 436
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   240	5	10	e	Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
    //   383	5	12	e	Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
    //   305	83	11	pos1	Lnet/minecraft/util/math/BlockPos;
    //   162	232	9	pos	Lnet/minecraft/util/math/BlockPos;
    //   103	294	5	start	Lnet/minecraft/util/math/vector/Vector3d;
    //   116	281	6	vec	Lnet/minecraft/util/math/vector/Vector3d;
    //   126	271	7	world	Lnet/minecraft/world/World;
    //   0	442	0	this	Lcom/swdteam/common/command/dalekmod/argument/TardisArgument;
    //   0	442	1	ctx	Lcom/mojang/brigadier/context/CommandContext;
    //   0	442	2	builder	Lcom/mojang/brigadier/suggestion/SuggestionsBuilder;
    //   8	434	3	suggestions	Ljava/util/List;
    //   13	429	4	minecraft	Lnet/minecraft/client/Minecraft;
    // Local variable type table:
    //   start	length	slot	name	signature
    //   0	442	1	ctx	Lcom/mojang/brigadier/context/CommandContext<TS;>;
    //   8	434	3	suggestions	Ljava/util/List<Ljava/lang/String;>;
    // Exception table:
    //   from	to	target	type
    //   200	235	238	com/mojang/brigadier/exceptions/CommandSyntaxException
    //   343	378	381	com/mojang/brigadier/exceptions/CommandSyntaxException
  }




















  private static List<BlockPos> trace(Vector3d start, Vector3d vec) {
     List<BlockPos> blocks = new ArrayList<>();
     for (int i = 0; i < 10; i++) {
       start = start.func_178787_e(vec);
       blocks.add(new BlockPos(start.func_178787_e(vec)));
    }
     return blocks;
  }

  private static List<BlockPos> grid(Vector3d center) {
     List<BlockPos> blocks = new ArrayList<>();
     for (int x = -8; x <= 8; x++) {
       for (int z = -8; z <= 8; z++) {
         for (int y = -2; y <= 2; y++)
           blocks.add((new BlockPos(center)).func_177982_a(x, y, z)); 
      }
     }  return blocks;
  }

  public Collection<String> getExamples() {
     return EXAMPLES;
  }

  public static interface ITardisArgument {
    int getGlobalID();

    default TardisTileEntity getTardisTile() throws CommandSyntaxException {
       return TardisArgument.getTardisTileOrException(getData());
    }

    default TardisData getData() throws CommandSyntaxException {
       return TardisArgument.getTardisDataOrException(getGlobalID());
    }

    default String getOwnerName() throws CommandSyntaxException {
       return getData().getOwner_name();
    }

    default UUID getOwnerUUID() throws CommandSyntaxException {
       return getData().getOwner_uuid();
    }
  }
}


