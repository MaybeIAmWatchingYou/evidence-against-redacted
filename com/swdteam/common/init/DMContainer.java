package com.swdteam.common.init;

import com.swdteam.common.container.ArsContainer;
import com.swdteam.common.container.ArtronFuelTankContainer;
import com.swdteam.common.container.EngineeringTableContainer;
import com.swdteam.common.container.FaultLocatorContainer;
import com.swdteam.common.container.KerblamBoxContainer;
import com.swdteam.common.container.RoundelContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DMContainer
{
   public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS, "dalekmod");

   public static final RegistryObject<ContainerType<FaultLocatorContainer>> FAULT_LOCATOR_CONTAINER = CONTAINER_TYPE.register("fault_locator", () -> IForgeContainerType.create(FaultLocatorContainer::new));
   public static final RegistryObject<ContainerType<ArtronFuelTankContainer>> ARTRON_FUEL_CONTAINER = CONTAINER_TYPE.register("artron_fuel_tank", () -> IForgeContainerType.create(ArtronFuelTankContainer::new));
   public static final RegistryObject<ContainerType<RoundelContainer>> CRAFTING_CONTAINER = CONTAINER_TYPE.register("crafting", () -> IForgeContainerType.create(RoundelContainer::new));
   public static final RegistryObject<ContainerType<ArsContainer>> ARS_CONTAINER = CONTAINER_TYPE.register("ars", () -> IForgeContainerType.create(ArsContainer::new));
   public static final RegistryObject<ContainerType<EngineeringTableContainer>> ENGINEERING_TABLE_CONTAINER = CONTAINER_TYPE.register("engineering", () -> IForgeContainerType.create(EngineeringTableContainer::new));
   public static final RegistryObject<ContainerType<KerblamBoxContainer>> KERBLAM_BOX_CONTAINER = CONTAINER_TYPE.register("kerblam_box", () -> IForgeContainerType.create(KerblamBoxContainer::new));
}


