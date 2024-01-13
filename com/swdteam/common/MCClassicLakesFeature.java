package com.swdteam.common;

import com.mojang.serialization.Codec;
import com.swdteam.common.init.DMBlocks;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.LightType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class MCClassicLakesFeature extends Feature<BlockStateFeatureConfig> {
   private static final BlockState AIR = Blocks.field_201941_jj.getDefaultState();

  public MCClassicLakesFeature(Codec<BlockStateFeatureConfig> codec) {
     super(codec);
  }




  public boolean place(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
     while (pos.getY() > 5 && reader.func_175623_d(pos)) {
       pos = pos.func_177977_b();
    }

     if (pos.getY() <= 4) {
       return false;
    }
     pos = pos.func_177979_c(4);
     if (reader.func_241827_a(SectionPos.func_218167_a(pos), Structure.field_236381_q_).findAny().isPresent()) {
       return false;
    }
     boolean[] aboolean = new boolean[2048];
     int i = rand.nextInt(4) + 4;

     for (int j = 0; j < i; j++) {
       double d0 = rand.nextDouble() * 6.0D + 3.0D;
       double d1 = rand.nextDouble() * 4.0D + 2.0D;
       double d2 = rand.nextDouble() * 6.0D + 3.0D;
       double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
       double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
       double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

       for (int l = 1; l < 15; l++) {
         for (int i1 = 1; i1 < 15; i1++) {
           for (int j1 = 1; j1 < 7; j1++) {
             double d6 = (l - d3) / d0 / 2.0D;
             double d7 = (j1 - d4) / d1 / 2.0D;
             double d8 = (i1 - d5) / d2 / 2.0D;
             double d9 = d6 * d6 + d7 * d7 + d8 * d8;
             if (d9 < 1.0D) {
               aboolean[(l * 16 + i1) * 8 + j1] = true;
            }
          }
        }
      }
    }

     for (int k1 = 0; k1 < 16; k1++) {
       for (int l2 = 0; l2 < 16; l2++) {
         for (int k = 0; k < 8; k++) {
           boolean flag = (!aboolean[(k1 * 16 + l2) * 8 + k] && ((k1 < 15 && aboolean[((k1 + 1) * 16 + l2) * 8 + k]) || (k1 > 0 && aboolean[((k1 - 1) * 16 + l2) * 8 + k]) || (l2 < 15 && aboolean[(k1 * 16 + l2 + 1) * 8 + k]) || (l2 > 0 && aboolean[(k1 * 16 + l2 - 1) * 8 + k]) || (k < 7 && aboolean[(k1 * 16 + l2) * 8 + k + 1]) || (k > 0 && aboolean[(k1 * 16 + l2) * 8 + k - 1])));
           if (flag) {
             Material material = reader.getBlockState(pos.func_177982_a(k1, k, l2)).func_185904_a();
             if (k >= 4 && material.func_76224_d()) {
               return false;
            }

             if (k < 4 && !material.func_76220_a() && reader.getBlockState(pos.func_177982_a(k1, k, l2)) != config.field_227270_a_) {
               return false;
            }
          }
        }
      }
    }

     for (int l1 = 0; l1 < 16; l1++) {
       for (int i3 = 0; i3 < 16; i3++) {
         for (int i4 = 0; i4 < 8; i4++) {
           if (aboolean[(l1 * 16 + i3) * 8 + i4]) {
             reader.setBlockState(pos.func_177982_a(l1, i4, i3), (i4 >= 4) ? AIR : config.field_227270_a_, 2);
          }
        }
      }
    }

     for (int i2 = 0; i2 < 16; i2++) {
       for (int j3 = 0; j3 < 16; j3++) {
         for (int j4 = 4; j4 < 8; j4++) {
           if (aboolean[(i2 * 16 + j3) * 8 + j4]) {
             BlockPos blockpos = pos.func_177982_a(i2, j4 - 1, j3);
             if (func_227250_b_(reader.getBlockState(blockpos).getBlock()) && reader.func_226658_a_(LightType.SKY, pos.func_177982_a(i2, j4, j3)) > 0) {
               reader.setBlockState(blockpos, ((Block)DMBlocks.CLASSIC_SAND.get()).getDefaultState(), 2);
            }
          }
        }
      }
    }


     return true;
  }
}


