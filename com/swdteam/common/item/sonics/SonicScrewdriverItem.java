package com.swdteam.common.item.sonics;

import com.swdteam.common.init.DMSoundEvents;
import net.minecraft.item.ItemGroup;

public class SonicScrewdriverItem
  extends SonicScrewdriverCustomizedItem
{
  public SonicScrewdriverItem(ItemGroup itemGroup, int xpValue) {
     super(itemGroup, xpValue);
     setSonicSound(DMSoundEvents.ENTITY_SONIC_SECOND);
  }
}


