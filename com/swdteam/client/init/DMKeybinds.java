package com.swdteam.client.init;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class DMKeybinds {
  public static KeyBinding CLASSIC_INVENTORY;
  public static KeyBinding FINGER_CLICK;

  public static void init() {
     CLASSIC_INVENTORY = new KeyBinding("dalekmod.keybinds.classic_inventory", 66, "Dalek Mod");
     FINGER_CLICK = new KeyBinding("dalekmod.keybinds.finger_snap", 90, "Dalek Mod");
     ClientRegistry.registerKeyBinding(CLASSIC_INVENTORY);
     ClientRegistry.registerKeyBinding(FINGER_CLICK);
  }
}


