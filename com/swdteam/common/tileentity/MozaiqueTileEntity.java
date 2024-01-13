package com.swdteam.common.tileentity;

import com.swdteam.common.init.DMBlockEntities;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class MozaiqueTileEntity
  extends DMTileEntityBase
{
   public static Map<PlayerEntity, Colors> PLAYER_COLORS = new HashMap<>();
  
   public Colors colTopLeft = Colors.WHITE, colTopRight = Colors.WHITE, colBotLeft = Colors.WHITE, colBotRight = Colors.WHITE;
  
  public MozaiqueTileEntity() {
     super((TileEntityType)DMBlockEntities.TILE_MOZAIQUE.get());
  }


  
  public CompoundNBT func_189515_b(CompoundNBT tag) {
     tag.func_74778_a("col_top_left", this.colTopLeft.name());
     tag.func_74778_a("col_top_right", this.colTopRight.name());
     tag.func_74778_a("col_bot_left", this.colBotLeft.name());
     tag.func_74778_a("col_bot_right", this.colBotRight.name());
    
     return super.func_189515_b(tag);
  }


  
  public void read(BlockState p_230337_1_, CompoundNBT tag) {
     if (tag.func_74764_b("col_top_left")) {
       this.colTopLeft = getColor("col_top_left", tag);
    }
     if (tag.func_74764_b("col_top_right")) {
       this.colTopRight = getColor("col_top_right", tag);
    }
     if (tag.func_74764_b("col_bot_left")) {
       this.colBotLeft = getColor("col_bot_left", tag);
    }
     if (tag.func_74764_b("col_bot_right")) {
       this.colBotRight = getColor("col_bot_right", tag);
    }
    
     super.read(p_230337_1_, tag);
  }
  
  public Colors getColor(String s, CompoundNBT tag) {
     String i = tag.func_74779_i(s);
     Colors c = Colors.valueOf(i.toUpperCase());
    
     if (c == null) {
       c = Colors.WHITE;
    }
    
     return c;
  }

  
  public void draw(Corner c, PlayerEntity player) {
     if (!PLAYER_COLORS.containsKey(player)) {
       PLAYER_COLORS.put(player, Colors.WHITE);
    }
    
     Colors i = PLAYER_COLORS.get(player);
    
     if (c == Corner.TOP_LEFT) {
       this.colTopLeft = i;
     } else if (c == Corner.TOP_RIGHT) {
       this.colTopRight = i;
     } else if (c == Corner.BOTTOM_LEFT) {
       this.colBotLeft = i;
     } else if (c == Corner.BOTTOM_RIGHT) {
       this.colBotRight = i;
    } 
    
     sendUpdates();
  }
  
  public Colors getColor(Corner corner) {
     if (corner == Corner.TOP_LEFT)
       return this.colTopLeft;
     if (corner == Corner.TOP_RIGHT)
       return this.colTopRight;
     if (corner == Corner.BOTTOM_LEFT)
       return this.colBotLeft;
     if (corner == Corner.BOTTOM_RIGHT) {
       return this.colBotRight;
    }
    
     return this.colTopLeft;
  }
  
  public enum Corner {
     TOP_LEFT,
     TOP_RIGHT,
     BOTTOM_LEFT,
     BOTTOM_RIGHT;
  }
  
  public enum Colors
  {
     WHITE("White", 16777215),
     MAROON("Maroon", 8981794),
    
     RED("Red", 15677753),
     ORANGE("Orange", 16744754),
     YELLOW("Yellow", 16769095),
    
     LIME("Lime", 6613075),
     GREEN("Green", 2731353),
     AQUA("Aqua", 6154999),
    
     BLUE("Blue", 3833087),
     DEEP_BLUE("Deep Blue", 3940599),
     PURPLE("Purple", 10377727),
     MAGENTA("Magenta", 14765311),
     PINK("Pink", 16749040),
    
     PEACH("Peach", 16765608),
     BLONDE("Blonde", 12885856),
     BROWN("Brown", 7162411),
    
     LIGHT_GRAY("Light Gray", 12369084),
     GRAY("Gray", 8553090),
     DARK_GRAY("Dark Gray", 5197647),
     BLACK("Black", 1447446);
    
    private int id;
    
    private int hex;
    private String display_name;
     private static int size = 0;
    
    Colors(String name, int i) {
       this.id = getCount() - 1;
       this.hex = i;
       this.display_name = name;
    } static {
    
    } public int getId() {
       return this.id;
    }
    
    public int getHex() {
       return this.hex;
    }
    
    public String getName() {
       return this.display_name;
    }
    
    public int getCount() {
       size++;
       return size;
    }
  }
}


