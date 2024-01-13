package com.swdteam.network;

import com.swdteam.network.packets.PacketApplyExterior;
import com.swdteam.network.packets.PacketApplySonicSkin;
import com.swdteam.network.packets.PacketClearKerblamBasket;
import com.swdteam.network.packets.PacketClickTardisDoors;
import com.swdteam.network.packets.PacketConfirmDesktopChange;
import com.swdteam.network.packets.PacketCreeperExplosion;
import com.swdteam.network.packets.PacketDisplayDalekDamage;
import com.swdteam.network.packets.PacketEjectWaypointCartridge;
import com.swdteam.network.packets.PacketFaultLocatorSlotUpdate;
import com.swdteam.network.packets.PacketFuelTankSlotUpdate;
import com.swdteam.network.packets.PacketHologramData;
import com.swdteam.network.packets.PacketKerblamPurchase;
import com.swdteam.network.packets.PacketLandTardisFlightMode;
import com.swdteam.network.packets.PacketOpenGui;
import com.swdteam.network.packets.PacketPickClassicItem;
import com.swdteam.network.packets.PacketRequestKerblamStore;
import com.swdteam.network.packets.PacketRequestTardis;
import com.swdteam.network.packets.PacketRequestTardisFlightData;
import com.swdteam.network.packets.PacketSendFlightData;
import com.swdteam.network.packets.PacketSendKerblamStockSync;
import com.swdteam.network.packets.PacketSendTardisData;
import com.swdteam.network.packets.PacketSendTardisInteriorRegistry;
import com.swdteam.network.packets.PacketSendTardisRecipeSync;
import com.swdteam.network.packets.PacketSendTardisRegistry;
import com.swdteam.network.packets.PacketSetColor;
import com.swdteam.network.packets.PacketSetExterior;
import com.swdteam.network.packets.PacketSonicInteraction;
import com.swdteam.network.packets.PacketStartInteriorBuild;
import com.swdteam.network.packets.PacketSyncEntityData;
import com.swdteam.network.packets.PacketTardisLightingSync;
import com.swdteam.network.packets.PacketUnlockExterior;
import com.swdteam.network.packets.PacketUnlockExteriorResponse;
import com.swdteam.network.packets.PacketUnlockSonicResponse;
import com.swdteam.network.packets.PacketUpdateFlightMode;
import com.swdteam.network.packets.PacketWeepingAngel;
import com.swdteam.network.packets.PacketXPSync;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.fml.server.ServerLifecycleHooks;







public class NetworkHandler
{
   private static final String PROTOCOL_VERSION = Integer.toString(1);
  
   private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation("dalekmod", "main_channel"))
     .clientAcceptedVersions(PROTOCOL_VERSION::equals)
     .serverAcceptedVersions(PROTOCOL_VERSION::equals)
     .networkProtocolVersion(() -> PROTOCOL_VERSION)
     .simpleChannel();

  
   public static int id = 0;
  
  public static void register() {
     INSTANCE.registerMessage(id++, PacketOpenGui.class, PacketOpenGui::encode, PacketOpenGui::decode, PacketOpenGui::handle);
    
     INSTANCE.registerMessage(id++, PacketDisplayDalekDamage.class, PacketDisplayDalekDamage::encode, PacketDisplayDalekDamage::decode, PacketDisplayDalekDamage::handle);
    
     INSTANCE.registerMessage(id++, PacketPickClassicItem.class, PacketPickClassicItem::encode, PacketPickClassicItem::decode, PacketPickClassicItem::handle);
    
     INSTANCE.registerMessage(id++, PacketCreeperExplosion.class, PacketCreeperExplosion::encode, PacketCreeperExplosion::decode, PacketCreeperExplosion::handle);
    
     INSTANCE.registerMessage(id++, PacketRequestTardis.class, PacketRequestTardis::encode, PacketRequestTardis::decode, PacketRequestTardis::handle);
    
     INSTANCE.registerMessage(id++, PacketSendTardisData.class, PacketSendTardisData::encode, PacketSendTardisData::decode, PacketSendTardisData::handle);
    
     INSTANCE.registerMessage(id++, PacketSendFlightData.class, PacketSendFlightData::encode, PacketSendFlightData::decode, PacketSendFlightData::handle);
    
     INSTANCE.registerMessage(id++, PacketRequestTardisFlightData.class, PacketRequestTardisFlightData::encode, PacketRequestTardisFlightData::decode, PacketRequestTardisFlightData::handle);
    
     INSTANCE.registerMessage(id++, PacketSendTardisRegistry.class, PacketSendTardisRegistry::encode, PacketSendTardisRegistry::decode, PacketSendTardisRegistry::handle);
    
     INSTANCE.registerMessage(id++, PacketEjectWaypointCartridge.class, PacketEjectWaypointCartridge::encode, PacketEjectWaypointCartridge::decode, PacketEjectWaypointCartridge::handle);
    
     INSTANCE.registerMessage(id++, PacketSonicInteraction.class, PacketSonicInteraction::encode, PacketSonicInteraction::decode, PacketSonicInteraction::handle);
    
     INSTANCE.registerMessage(id++, PacketSetExterior.class, PacketSetExterior::encode, PacketSetExterior::decode, PacketSetExterior::handle);
    
     INSTANCE.registerMessage(id++, PacketApplyExterior.class, PacketApplyExterior::encode, PacketApplyExterior::decode, PacketApplyExterior::handle);
    
     INSTANCE.registerMessage(id++, PacketUnlockExterior.class, PacketUnlockExterior::encode, PacketUnlockExterior::decode, PacketUnlockExterior::handle);
    
     INSTANCE.registerMessage(id++, PacketUnlockExteriorResponse.class, PacketUnlockExteriorResponse::encode, PacketUnlockExteriorResponse::decode, PacketUnlockExteriorResponse::handle);
    
     INSTANCE.registerMessage(id++, PacketSendTardisInteriorRegistry.class, PacketSendTardisInteriorRegistry::encode, PacketSendTardisInteriorRegistry::decode, PacketSendTardisInteriorRegistry::handle);
    
     INSTANCE.registerMessage(id++, PacketApplySonicSkin.class, PacketApplySonicSkin::encode, PacketApplySonicSkin::decode, PacketApplySonicSkin::handle);
    
     INSTANCE.registerMessage(id++, PacketUnlockSonicResponse.class, PacketUnlockSonicResponse::encode, PacketUnlockSonicResponse::decode, PacketUnlockSonicResponse::handle);
    
     INSTANCE.registerMessage(id++, PacketFaultLocatorSlotUpdate.class, PacketFaultLocatorSlotUpdate::encode, PacketFaultLocatorSlotUpdate::decode, PacketFaultLocatorSlotUpdate::handle);
    
     INSTANCE.registerMessage(id++, PacketStartInteriorBuild.class, PacketStartInteriorBuild::encode, PacketStartInteriorBuild::decode, PacketStartInteriorBuild::handle);
    
     INSTANCE.registerMessage(id++, PacketSendTardisRecipeSync.class, PacketSendTardisRecipeSync::encode, PacketSendTardisRecipeSync::decode, PacketSendTardisRecipeSync::handle);
    
     INSTANCE.registerMessage(id++, PacketConfirmDesktopChange.class, PacketConfirmDesktopChange::encode, PacketConfirmDesktopChange::decode, PacketConfirmDesktopChange::handle);
    
     INSTANCE.registerMessage(id++, PacketClickTardisDoors.class, PacketClickTardisDoors::encode, PacketClickTardisDoors::decode, PacketClickTardisDoors::handle);
    
     INSTANCE.registerMessage(id++, PacketFuelTankSlotUpdate.class, PacketFuelTankSlotUpdate::encode, PacketFuelTankSlotUpdate::decode, PacketFuelTankSlotUpdate::handle);
    
     INSTANCE.registerMessage(id++, PacketWeepingAngel.class, PacketWeepingAngel::encode, PacketWeepingAngel::decode, PacketWeepingAngel::handle);
    
     INSTANCE.registerMessage(id++, PacketHologramData.class, PacketHologramData::encode, PacketHologramData::decode, PacketHologramData::handle);
    
     INSTANCE.registerMessage(id++, PacketSetColor.class, PacketSetColor::encode, PacketSetColor::decode, PacketSetColor::handle);
    
     INSTANCE.registerMessage(id++, PacketXPSync.class, PacketXPSync::encode, PacketXPSync::decode, PacketXPSync::handle);
    
     INSTANCE.registerMessage(id++, PacketSendKerblamStockSync.class, PacketSendKerblamStockSync::encode, PacketSendKerblamStockSync::decode, PacketSendKerblamStockSync::handle);
    
     INSTANCE.registerMessage(id++, PacketRequestKerblamStore.class, PacketRequestKerblamStore::encode, PacketRequestKerblamStore::decode, PacketRequestKerblamStore::handle);
    
     INSTANCE.registerMessage(id++, PacketKerblamPurchase.class, PacketKerblamPurchase::encode, PacketKerblamPurchase::decode, PacketKerblamPurchase::handle);
    
     INSTANCE.registerMessage(id++, PacketSyncEntityData.class, PacketSyncEntityData::encode, PacketSyncEntityData::decode, PacketSyncEntityData::handle);
    
     INSTANCE.registerMessage(id++, PacketClearKerblamBasket.class, PacketClearKerblamBasket::encode, PacketClearKerblamBasket::decode, PacketClearKerblamBasket::handle);
    
     INSTANCE.registerMessage(id++, PacketTardisLightingSync.class, PacketTardisLightingSync::encode, PacketTardisLightingSync::decode, PacketTardisLightingSync::handle);
    
     INSTANCE.registerMessage(id++, PacketUpdateFlightMode.class, PacketUpdateFlightMode::encode, PacketUpdateFlightMode::decode, PacketUpdateFlightMode::handle);
    
     INSTANCE.registerMessage(id++, PacketLandTardisFlightMode.class, PacketLandTardisFlightMode::encode, PacketLandTardisFlightMode::decode, PacketLandTardisFlightMode::handle);
  }

  
  public static void sendToAllClients(Object packet, World world) {
     world.getServer().func_184103_al().func_181057_v().forEach(p -> sendTo(p, packet));
  }
  
  public static void sendToAllClients(Object packet) {
     ServerLifecycleHooks.getCurrentServer().func_184103_al().func_181057_v().forEach(p -> sendTo(p, packet));
  }
  
  public static void sendTo(ServerPlayerEntity playerEntity, Object packet) {
     INSTANCE.sendTo(packet, playerEntity.field_71135_a.field_147371_a, NetworkDirection.PLAY_TO_CLIENT);
  }
  
  public static void sendServerPacket(Object packet) {
     INSTANCE.sendToServer(packet);
  }
}


