package com.swdteam.main.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ServerProxy {
  public void doServerStuff(FMLCommonSetupEvent event) {}
  
  public void doClientStuff(FMLClientSetupEvent event) {}
  
  public void postLoad() {}
}


