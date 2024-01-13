package com.swdteam.common.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.swdteam.common.init.DMBlocks;
import java.util.Set;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class SkaroWorldCarver
  extends CaveWorldCarver {
  public SkaroWorldCarver(Codec<ProbabilityConfig> codec) {
     super(codec, 256);
     this.field_222718_j = (Set)ImmutableSet.of(DMBlocks.CINDERGRASS.get(), DMBlocks.CINDERSOIL.get(), DMBlocks.KALETITE.get());
  }
}


