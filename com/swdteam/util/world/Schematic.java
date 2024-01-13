package com.swdteam.util.world;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;





public class Schematic
  implements Serializable
{
  public static final long serialVersionUID = -1408529579543411242L;
  public int schem_dim_x;
  public int schem_dim_y;
  public int schem_dim_z;
   public Map<Integer, String> blockTable = new HashMap<>();
  
  public int[] blockMap;
  
   public List<TileData> tileData = new ArrayList<>();
  
  public Schematic(int x, int y, int z) {
     this.schem_dim_x = x;
     this.schem_dim_y = y;
     this.schem_dim_z = z;
  }

  
  public int getBlockID(BlockState tag) {
     String block = Registry.field_212618_g.func_177774_c(tag.getBlock()).toString();
    
     ImmutableMap<Property<?>, Comparable<?>> immutablemap = tag.func_206871_b();
     if (!immutablemap.isEmpty())
    {
       for (UnmodifiableIterator<Map.Entry<Property<?>, Comparable<?>>> unmodifiableIterator = immutablemap.entrySet().iterator(); unmodifiableIterator.hasNext(); ) { Map.Entry<Property<?>, Comparable<?>> entry = unmodifiableIterator.next();
         Property<?> Property = entry.getKey();
         block = block + "," + Property.func_177701_a() + "=" + getName(Property, entry.getValue()); }
    
    }
    
     if (!this.blockTable.containsValue(block)) {
       int i = this.blockTable.size();
       this.blockTable.put(Integer.valueOf(i), block);
    } 


    
     int id = ((Integer)getKeyByValue(this.blockTable, block)).intValue();
    
     return id;
  }
  
  public <T, E> T getKeyByValue(Map<T, E> map, E value) {
     for (Map.Entry<T, E> entry : map.entrySet()) {
       if (Objects.equals(value, entry.getValue())) {
         return entry.getKey();
      }
    } 
     return null;
  }
  
  public BlockState getStateFromID(int id) {
     String s = this.blockTable.get(Integer.valueOf(id));
     if (s == null) {
       return Blocks.AIR.getDefaultState();
    }
     BlockState state = readBlockState(s);
     if (state == null) {
       return Blocks.AIR.getDefaultState();
    }
     return state;
  }





  
  private BlockState readBlockState(String string) {
     String[] data = string.split(",");

    
     Block block = (Block)Registry.field_212618_g.getOrDefault(new ResourceLocation(data[0]));
     BlockState blockstate = block.getDefaultState();
    
     StateContainer<Block, BlockState> stateDefinition = block.func_176194_O();
    
     for (int i = 1; i < data.length; i++) {
       String[] s = data[i].split("=");
       String key = s[0];
       String value = s[1];
      
       Property<?> Property = stateDefinition.func_185920_a(key);
       if (Property != null) {
         blockstate = setValueHelper(blockstate, Property, value);
      }
    } 
     return blockstate;
  }
  
  private static <S extends net.minecraft.state.StateHolder<?, S>, T extends Comparable<T>> S setValueHelper(S p_193590_0_, Property<T> p_193590_1_, String blockState) {
     Optional<T> optional = p_193590_1_.func_185929_b(blockState);
     if (optional.isPresent()) {
       return (S)p_193590_0_.func_206870_a(p_193590_1_, (Comparable)optional.get());
    }
     return p_193590_0_;
  }


  
  private <T extends Comparable<T>> String getName(Property<T> p_190010_0_, Comparable<?> p_190010_1_) {
     return p_190010_0_.func_177702_a(p_190010_1_);
  }
  
  public int getSchemDimX() {
     return this.schem_dim_x;
  }
  
  public int getSchemDimY() {
     return this.schem_dim_y;
  }
  
  public int getSchemDimZ() {
     return this.schem_dim_z;
  }
  
  public List<TileData> getTileData() {
     return this.tileData;
  }
  
  public void setTileData(List<TileData> tileData) {
     this.tileData = tileData;
  }
  
  public void setBlockMap(int[] blockMap) {
     this.blockMap = blockMap;
  }
}


