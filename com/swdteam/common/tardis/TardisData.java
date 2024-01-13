package com.swdteam.common.tardis;

import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tardis.data.TardisLocationRegistry;
import com.swdteam.main.DalekMod;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketSendTardisData;
import com.swdteam.network.packets.PacketTardisLightingSync;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.LightAlternatorToggle;
import com.swdteam.util.helpers.math.vector.Vector2f;
import com.swdteam.util.math.Position;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.server.ServerLifecycleHooks;




public class TardisData
  implements Serializable
{
  private static final long serialVersionUID = 1655931627846952582L;
  private int global_id;
  private UUID owner_uuid;
  private String owner_name;
  private Position interior_spawn_position;
  private Location current_location;
  private Location previous_location;
  private float interior_spawn_rotation;
  private String exterior;
   private int sub_exterior_id = 0;
  private boolean locked;
  private boolean has_generated = false;
   private double fuel = 100.0D;
   private List<String> unlocked_exteriors = new ArrayList<>();
  
  private boolean door_open;
  private boolean shownWelcome = false;
   private int fluidLinkFlightTime = 100;
   private int fluidLinkFuelConsumption = 100;
   private int fluidLinkAccuracy = 100;
  
  private transient Tardis tardisExterior;
  
  private String currentlyBuilding;
  
  private List<TardisInterior.BuildingRecipe.BuildingRecipePart> recipeParts;
   private Lighting lighting = new Lighting();
  
  public List<TardisInterior.BuildingRecipe.BuildingRecipePart> getRecipeParts() {
     return this.recipeParts;
  }
  
  public int getRecipeTotal(String s) {
     if (this.recipeParts != null) {
       for (int i = 0; i < getRecipeParts().size(); i++) {
         if (((TardisInterior.BuildingRecipe.BuildingRecipePart)getRecipeParts().get(i)).getItem().equals(s)) {
           return ((TardisInterior.BuildingRecipe.BuildingRecipePart)getRecipeParts().get(i)).getQuantity();
        }
      } 
    }
     return 0;
  }
  
  public void addItemToRecipe(ItemStack stack, int amount) {
     if (this.currentlyBuilding != null) {
       for (int i = 0; i < getRecipeParts().size(); i++) {
         TardisInterior.BuildingRecipe.BuildingRecipePart part = getRecipeParts().get(i);
         if (part.getItem().equals(stack.getItem().getRegistryName().toString())) {
           part.addQuantity(amount);
           save();
          
          return;
        } 
      } 
       getRecipeParts().add(new TardisInterior.BuildingRecipe.BuildingRecipePart(stack.getItem().getRegistryName().toString(), amount));
       save();
    } 
  }
  
  public boolean isRecipeComplete() {
     boolean complete = true;
     if (this.currentlyBuilding != null) {
       TardisInterior interior = (TardisInterior)DMTardisRegistry.getTardisInteriors().get(new ResourceLocation(this.currentlyBuilding));
       if (interior != null) {
         TardisInterior.BuildingRecipe recipe = interior.getRecipe();
         if (recipe != null) {
           for (int i = 0; i < (recipe.getParts()).length; i++) {
             TardisInterior.BuildingRecipe.BuildingRecipePart part = recipe.getParts()[i];
             if (part.getQuantity() != getRecipeTotal(part.getItem())) {
               complete = false;
              break;
            } 
          } 
        } else {
           complete = false;
        } 
      } else {
         complete = false;
      } 
    } else {
       complete = false;
    } 
    
     return complete;
  }
  
  public int getRecipePercentage() {
     int percent = 0;
    
     int need = 0;
     int has = 0;
    
     if (this.currentlyBuilding != null) {
       TardisInterior interior = (TardisInterior)DMTardisRegistry.getTardisInteriors().get(new ResourceLocation(this.currentlyBuilding));
       if (interior != null) {
         TardisInterior.BuildingRecipe recipe = interior.getRecipe();
         if (recipe != null) {
           for (int j = 0; j < (recipe.getParts()).length; j++) {
             need += recipe.getParts()[j].getQuantity();
          }
        }
        
         for (int i = 0; i < getRecipeParts().size(); i++) {
           TardisInterior.BuildingRecipe.BuildingRecipePart part = getRecipeParts().get(i);
           has += part.getQuantity();
        } 
      } 
    } 
    
     percent = (int)(has * 100.0F / need);
     return percent;
  }
  
  public void setCurrentlyBuilding(ResourceLocation currentlyBuilding) {
     if (currentlyBuilding == null) {
       this.currentlyBuilding = null;
       if (this.recipeParts != null) {
         this.recipeParts.clear();
      }
    } else {
       ServerPlayerEntity player = ServerLifecycleHooks.getCurrentServer().func_184103_al().func_177451_a(this.owner_uuid);
       player.func_96123_co().func_197893_a((ScoreCriteria)DalekMod.ARS_CRITERIA.get("dalekmod.ars:build." + currentlyBuilding.getPath()), player.func_195047_I_(), c -> c.func_96648_a());

      
       this.currentlyBuilding = currentlyBuilding.toString();
       this.recipeParts = new ArrayList<>();
    } 
  }
  
  public String getCurrentlyBuilding() {
     return this.currentlyBuilding;
  }
  
  public TardisData(int id) {
     this.global_id = id;
  }
  
  public int getGlobalID() {
     return this.global_id;
  }
  
  public Position getInteriorSpawnPosition() {
     return this.interior_spawn_position;
  }
  
  public String getOwner_name() {
     return this.owner_name;
  }
  
  public UUID getOwner_uuid() {
     return this.owner_uuid;
  }
  
  public void setOwnerName(String owner_name) {
     this.owner_name = owner_name;
  }
  
  public void setInteriorSpawnPosition(Position position) {
     this.interior_spawn_position = position;
  }
  
  public void setOwnerUUID(UUID owner_uuid) {
     this.owner_uuid = owner_uuid;
  }
  
  public void save() {
     NetworkHandler.sendToAllClients(new PacketSendTardisData(this));
  }
  
  public void saveToDisk() {
     TardisSaveHandler.saveTardisData(this);
  }
  
  public void setDoorOpen(boolean door_open) {
     this.door_open = door_open;
  }
  
  public boolean isDoorOpen() {
     return this.door_open;
  }
  
  public Location getCurrentLocation() {
     return this.current_location;
  }
  
  public void setCurrentLocation(BlockPos pos, RegistryKey<World> registryKey) {
     this.current_location = new Location(pos, registryKey);
  }
  
  public Location getPreviousLocation() {
     return this.previous_location;
  }
  
  public void setPreviousLocation(BlockPos pos, RegistryKey<World> registryKey) {
     this.previous_location = new Location(pos, registryKey);
  }
  
  public void setPreviousLocation(Location loc) {
     this.previous_location = new Location(loc.getBlockPosition(), loc.dimensionWorldKey());
  }
  
  public float getInteriorSpawnRotation() {
     return this.interior_spawn_rotation;
  }
  
  public void setInteriorSpawnRotation(float interiorSpawnRotation) {
     this.interior_spawn_rotation = interiorSpawnRotation;
  }
  
  public boolean isInFlight() {
     return TardisFlightPool.inFlight(this);
  }
  
  public Tardis getTardisExterior() {
     if (this.exterior == null) {
       this.exterior = DMTardisRegistry.TARDIS_CAPSULE.toString();
    }
     if (this.tardisExterior == null) {
       this.tardisExterior = DMTardisRegistry.getExterior(new ResourceLocation(this.exterior));
    }
     return this.tardisExterior;
  }
  
  public void setExterior(String exterior) {
     this.exterior = exterior;
     this.tardisExterior = null;
  }
  
  public boolean hasPermission(PlayerEntity player, PermissionType type) {
     if (type == PermissionType.CONTROLS) {
       return true;
    }
     return getOwner_uuid().equals(player.getUniqueID());
  }
  
  public boolean isOwner(PlayerEntity player) {
     return getOwner_uuid().equals(player.getUniqueID());
  }
  
  public boolean isLocked() {
     return this.locked;
  }
  
  public void setLocked(boolean locked) {
     this.locked = locked;
  }
  
  public boolean hasGenerated() {
     return this.has_generated;
  }
  
  public void setGenerated(boolean has_generated) {
     this.has_generated = has_generated;
  }
  
  public List<String> getUnlockedExteriors() {
     return this.unlocked_exteriors;
  }
  
  public void unlockExterior(ResourceLocation rl) {
     if (!this.unlocked_exteriors.contains(rl.toString())) {
       this.unlocked_exteriors.add(rl.toString());
    }
  }
  
  public boolean isUnlocked(ResourceLocation rl) {
     return this.unlocked_exteriors.contains(rl.toString());
  }
  
  public boolean isUnlocked(String rl) {
     return this.unlocked_exteriors.contains(rl);
  }
  
  public void noPermission(PlayerEntity player) {
     ChatUtil.sendError(player, "You do not have permission!", ChatUtil.MessageType.STATUS_BAR);
  }
  
  public int getSkinID() {
     return this.sub_exterior_id;
  }
  
  public void setSkinID(int i) {
     this.sub_exterior_id = i;
  }
  
  public double getFuel() {
     return this.fuel;
  }
  
  public void setFuel(double fuel) {
     if (fuel < 0.0D) {
       fuel = 0.0D;
    }
     this.fuel = fuel;
  }
  
  public void addFuel(double amount) {
     if (this.fuel + amount > 100.0D) {
       this.fuel = 100.0D;
     } else if (this.fuel + amount < 0.0D) {
       this.fuel = 0.0D;
    } else {
       this.fuel += amount;
    } 
  }
  
  public double calculateFuelForJourney(World level, World destLevel, BlockPos pos, BlockPos destPos) {
     double fuel = 0.0D;
     if (level != destLevel) {
       fuel = 10.0D;
    }
    
     Vector3d posA = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
     Vector3d posB = new Vector3d(destPos.getX(), destPos.getY(), destPos.getZ());
    
     double distance = posA.func_72438_d(posB) / 400.0D;
    
     fuel += distance;
    
     if (fuel > 100.0D) {
       fuel = 100.0D;
    }
    
     return fuel;
  }
  
  public int getFluidLinkAccuracy() {
     return this.fluidLinkAccuracy;
  }
  
  public int getFluidLinkFlightTime() {
     return this.fluidLinkFlightTime;
  }
  
  public int getFluidLinkFuelConsumption() {
     return this.fluidLinkFuelConsumption;
  }
  
  public void setFluidLinkAccuracy(int fluidLinkAccuracy) {
     this.fluidLinkAccuracy = fluidLinkAccuracy;
  }
  
  public void setFluidLinkFlightTime(int fluidLinkFlightTime) {
     this.fluidLinkFlightTime = fluidLinkFlightTime;
  }
  
  public void setFluidLinkFuelConsumption(int fluidLinkFuelConsumption) {
     this.fluidLinkFuelConsumption = fluidLinkFuelConsumption;
  }
  
  public boolean hasShownWelcome() {
     return this.shownWelcome;
  }
  
  public void setShownWelcome(boolean shownWelcome) {
     this.shownWelcome = shownWelcome;
  }
  
  public void depleatFluidLink(PlayerEntity player, Random rand) {
     if (rand.nextInt(75) == 1 &&
       getFluidLinkAccuracy() > 0) {
       setFluidLinkAccuracy(getFluidLinkAccuracy() - 1);
       if (getFluidLinkAccuracy() <= 0) {
         player.world.playSound(null, player.getPosition(), (SoundEvent)DMSoundEvents.TARDIS_FAULT_LOCATOR_BURNOUT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
    } 
    
     if (rand.nextInt(75) == 1 &&
       getFluidLinkFuelConsumption() > 0) {
       setFluidLinkFuelConsumption(getFluidLinkFuelConsumption() - 1);
       if (getFluidLinkFuelConsumption() <= 0) {
         player.world.playSound(null, player.getPosition(), (SoundEvent)DMSoundEvents.TARDIS_FAULT_LOCATOR_BURNOUT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
    } 
    
     if (rand.nextInt(75) == 1 &&
       getFluidLinkFlightTime() > 0) {
       setFluidLinkFlightTime(getFluidLinkFlightTime() - 1);
       if (getFluidLinkFlightTime() <= 0) {
         player.world.playSound(null, player.getPosition(), (SoundEvent)DMSoundEvents.TARDIS_FAULT_LOCATOR_BURNOUT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
    } 
  }

  
  public double timeLeft() {
     if (isInFlight()) {
       TardisFlightData flightData = TardisFlightPool.getFlightData(this);
       if (flightData != null) {
         long currentTime = System.currentTimeMillis() / 1000L;
        
         TardisLocationRegistry.TardisLocation location = TardisLocationRegistry.getLocationForKey(getCurrentLocation().dimensionWorldKey());
         TardisLocationRegistry.TardisLocation destination = TardisLocationRegistry.getLocationForKey(flightData.dimensionWorldKey());
        
         Vector2f locCoord = null;
         Vector2f destCoord = null;
        
         if (location != null) {
           locCoord = new Vector2f(location.getCoordinate()[0], location.getCoordinate()[1]);
        } else {
           locCoord = new Vector2f(0.0F, 0.0F);
        } 
        
         if (destination != null) {
           destCoord = new Vector2f(destination.getCoordinate()[0], destination.getCoordinate()[1]);
        } else {
           destCoord = new Vector2f(0.0F, 0.0F);
        } 
        
         double distance = Math.hypot((locCoord.x - destCoord.x), (locCoord.y - destCoord.y)) * 3.0D;
        
         if (getCurrentLocation() != null && getCurrentLocation().getPosition() != null) {
           distance += Math.hypot(getCurrentLocation().getPosition().func_82615_a() - flightData.getPos(Direction.Axis.X), getCurrentLocation().getPosition().func_82616_c() - flightData.getPos(Direction.Axis.Z)) / 150.0D;
        }
        
         float modifier = 1.0F - getFluidLinkFlightTime() / 100.0F;
         if (modifier <= 0.0F) {
           modifier = 0.2F;
        }
        
         distance *= modifier;
        
         long timeLeft = flightData.getFlightStartTime() - currentTime - (long)distance - 5L;
         if (timeLeft < 0L) timeLeft = 0L;
         return timeLeft;
      } 
    } 
     return 0.0D;
  }
  
  public void reload() {
     this.tardisExterior = null;
  }
  
  public Lighting getLighting() {
     if (this.lighting == null) this.lighting = new Lighting();
     return this.lighting;
  }
  
  public void setLighting(Lighting lighting) {
     this.lighting = lighting;
  }
  
  public void setTardisLighting(MinecraftServer server) {
     NetworkHandler.sendToAllClients(new PacketTardisLightingSync(this.global_id, ((getLighting()).selected == LightAlternatorToggle.LEFT), (getLighting()).left, (getLighting()).right, false), (World)server.getWorld(DMDimensions.TARDIS));
  }
  
  public double getLighting(LightAlternatorToggle toggle) {
     return (toggle == LightAlternatorToggle.LEFT) ? (getLighting()).left : (getLighting()).right;
  }
  
  public static class Lighting
    implements Serializable {
    private static final long serialVersionUID = 4113092150352710170L;
     public LightAlternatorToggle selected = LightAlternatorToggle.RIGHT;
     public int left = 60;
     public int right = 100;
    
    public static double toReal(int percent) {
       return percent / 100.0D * 7.0D - 5.0D;
    }
    
    public static int toPercent(double real) {
       return (int)((real + 5.0D) / 7.0D * 100.0D);
    }
    
    public void setCurrent(int light) {
       if (this.selected == LightAlternatorToggle.LEFT) { this.left = light; }
       else { this.right = light; }
    
    }
    public void setOther(int light) {
       if (this.selected == LightAlternatorToggle.RIGHT) { this.left = light; }
       else { this.right = light; }
    
    }
    public int getCurrent() {
       return (this.selected == LightAlternatorToggle.LEFT) ? this.left : this.right;
    }
    
    public Lighting copy() {
       Lighting newLighting = new Lighting();
       newLighting.left = this.left;
       newLighting.right = this.right;
       newLighting.selected = this.selected;
       return newLighting;
    }
  }
  
  public enum PermissionType
  {
     DEFAULT, CONTROLS;
  }
}


