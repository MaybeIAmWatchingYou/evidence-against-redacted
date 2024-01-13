package com.swdteam.common.init;

import com.swdteam.common.sonic.SonicCategory;
import com.swdteam.common.sonic.SonicDispenserInteraction;
import com.swdteam.common.sonic.SonicDoorInteraction;
import com.swdteam.common.sonic.SonicInteractionFarmland;
import com.swdteam.common.sonic.SonicRedstoneLampInteractionOff;
import com.swdteam.common.sonic.SonicRedstoneLampInteractionOn;
import com.swdteam.common.sonic.SonicTNTInteraction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;



public class DMSonicRegistry
{
   public static List<SonicData> SONICS = new ArrayList<>();
   public static Map<Object, ISonicInteraction> SONIC_LOOKUP = new HashMap<>();

  public static void init() {
     SONIC_LOOKUP.put(Blocks.TNT, new SonicTNTInteraction());
     SONIC_LOOKUP.put(Blocks.field_150454_av, new SonicDoorInteraction());
     SONIC_LOOKUP.put(Blocks.field_180400_cw, new SonicDoorInteraction());
     SONIC_LOOKUP.put(DMBlocks.STEEL_DOOR.get(), new SonicDoorInteraction());
     SONIC_LOOKUP.put(Blocks.field_150379_bu, new SonicRedstoneLampInteractionOn());
     SONIC_LOOKUP.put(DMBlocks.POWERED_REDSTONE_LAMP.get(), new SonicRedstoneLampInteractionOff());
     SONIC_LOOKUP.put(DMBlocks.CLASSIC_TNT.get(), new SonicTNTInteraction());
     SONIC_LOOKUP.put(Blocks.field_150367_z, new SonicDispenserInteraction());
     SONIC_LOOKUP.put(Blocks.field_150409_cd, new SonicDispenserInteraction());
     SONIC_LOOKUP.put(DMBlocks.DALEK_DOOR.get(), new SonicDoorInteraction());
     SONIC_LOOKUP.put(Blocks.field_150459_bM, new SonicInteractionFarmland());
     SONIC_LOOKUP.put(Blocks.field_150469_bN, new SonicInteractionFarmland());
     SONIC_LOOKUP.put(Blocks.field_150464_aj, new SonicInteractionFarmland());
     SONIC_LOOKUP.put(Blocks.field_185773_cZ, new SonicInteractionFarmland());

     Collections.sort(SONICS, new Comparator<SonicData>() {
          public int compare(DMSonicRegistry.SonicData c1, DMSonicRegistry.SonicData c2) {
             if (c1.getXpValue() > c2.getXpValue()) return 1;
             if (c1.getXpValue() < c2.getXpValue()) return -1;
             return 0;
          }
        });
  }
  public static void registerSonic(ItemStack stack, int xp) {
     SONICS.add(new SonicData(stack, xp));
  }

  public static class SonicData {
    private int xpValue;
    private ItemStack stack;

    public SonicData(ItemStack stack, int xp) {
       this.stack = stack;
       this.xpValue = xp;
    }

    public ItemStack getStack() {
       return this.stack;
    }

    public int getXpValue() {
       return this.xpValue;
    }
  }

  public static SonicData getSonic(ItemStack stack) {
     for (int i = 0; i < SONICS.size(); i++) {
       if (stack.getItem() == ((SonicData)SONICS.get(i)).getStack().getItem()) {
         return SONICS.get(i);
      }
    }
     return SONICS.get(0);
  }

  public static SonicData getSonic(ResourceLocation res) {
     for (int i = 0; i < SONICS.size(); i++) {
       if (res.equals(((SonicData)SONICS.get(i)).getStack().getItem().getRegistryName())) {
         return SONICS.get(i);
      }
    }
     return SONICS.get(0);
  }

  public static interface ISonicInteraction {
    void interact(World param1World, PlayerEntity param1PlayerEntity, ItemStack param1ItemStack, Object param1Object);

    int scanTime();

    boolean disableDefaultInteraction(World param1World, PlayerEntity param1PlayerEntity, ItemStack param1ItemStack, Object param1Object);

    SonicCategory getCategory();
  }
}


