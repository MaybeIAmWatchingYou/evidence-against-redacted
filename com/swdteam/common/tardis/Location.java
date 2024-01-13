package com.swdteam.common.tardis;

import com.swdteam.util.math.Position;
import java.io.Serializable;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Location
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Position position;
  private String dimension;
  private String biome;
  private float facing;

  public Location(BlockPos pos, RegistryKey<World> registryKey) {
     this.position = new Position(pos.getX(), pos.getY(), pos.getZ());
     this.dimension = registryKey.getRegistryLocation().toString();
     this.biome = registryKey.getRegistryLocation().toString();
  }

  public Location(Vector3d pos, RegistryKey<World> registryKey) {
     this.position = new Position(pos.func_82615_a(), pos.func_82617_b(), pos.func_82616_c());
     this.dimension = registryKey.getRegistryLocation().toString();
     this.biome = registryKey.getRegistryLocation().toString();
  }

  public String getDimension() {
     return this.dimension;
  }

  public String getBiome() {
     return this.biome;
  }

  public RegistryKey<World> dimensionWorldKey() {
     return RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(this.dimension));
  }

  public RegistryKey<Biome> biome() {
     return RegistryKey.func_240903_a_(Registry.BIOME_KEY, new ResourceLocation(this.biome));
  }

  public BlockPos getBlockPosition() {
     return new BlockPos((IPosition)this.position);
  }

  public Position getPosition() {
     return this.position;
  }

  public float getFacing() {
     return this.facing;
  }

  public void setFacing(float facing) {
     this.facing = facing;
  }
}


