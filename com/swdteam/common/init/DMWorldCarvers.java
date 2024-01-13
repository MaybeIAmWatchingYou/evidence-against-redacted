package com.swdteam.common.init;

import com.swdteam.common.RegistryHandler;
import com.swdteam.common.carver.ClassicWorldCarver;
import com.swdteam.common.carver.SkaroWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;


public class DMWorldCarvers
{
   public static final RegistryObject<ClassicWorldCarver> CARVER = RegistryHandler.WORLD_CARVERS.register("classic_carver", () -> new ClassicWorldCarver(ProbabilityConfig.field_236576_b_));
   public static final RegistryObject<SkaroWorldCarver> CARVERSKARO = RegistryHandler.WORLD_CARVERS.register("skaro_carver", () -> new SkaroWorldCarver(ProbabilityConfig.field_236576_b_));
}


