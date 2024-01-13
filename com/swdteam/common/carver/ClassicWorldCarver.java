package com.swdteam.common.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.swdteam.common.init.DMBlocks;
import java.util.Set;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class ClassicWorldCarver
  extends CaveWorldCarver {
  public ClassicWorldCarver(Codec<ProbabilityConfig> codec) {
     super(codec, 256);
     this.field_222718_j = (Set)ImmutableSet.of(DMBlocks.CLASSIC_STONE.get(), DMBlocks.CLASSIC_GRASS.get(), DMBlocks.CLASSIC_DIRT.get(), DMBlocks.CLASSIC_GRAVEL.get(), DMBlocks.CLASSIC_SAND.get());
  }
}


