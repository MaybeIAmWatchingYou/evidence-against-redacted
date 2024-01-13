package com.swdteam.common.block;

import java.util.function.Supplier;
import net.minecraft.tileentity.TileEntity;

public interface IDMTile {
  Supplier<TileEntity> getTile();
}


