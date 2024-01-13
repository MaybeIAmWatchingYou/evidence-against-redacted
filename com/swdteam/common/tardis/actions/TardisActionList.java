package com.swdteam.common.tardis.actions;

import com.swdteam.common.block.ForceFieldFloorBlock;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tileentity.ForceFieldFloorTileEntity;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.main.DMConfig;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.SWDMathUtils;
import com.swdteam.util.WorldUtils;
import com.swdteam.util.helpers.ArrayHelper;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;



public class TardisActionList
{
  public static boolean demat(PlayerEntity player, World world, TardisData data) {
     return demat(player, world, data, true);
  }

  public static boolean demat(PlayerEntity player, World world, TardisData data, boolean showMessage) {
     if (!world.isRemote && data != null && !data.isInFlight() && !DMFlightMode.isInFlight(data.getGlobalID())) {
       BlockPos pos = data.getCurrentLocation().getBlockPosition();
       ServerWorld serverWorld = world.getServer().getWorld(data.getCurrentLocation().dimensionWorldKey());
       if (serverWorld != null) {
         BlockState state = serverWorld.getBlockState(pos);
         TileEntity te = serverWorld.getTileEntity(pos);

         AxisAlignedBB floorAABB = new AxisAlignedBB(pos.func_177982_a(-1, -1, -1), pos.func_177982_a(1, -1, 1));

         BlockPos.func_239581_a_(floorAABB).forEach(blockpos -> {
              if (serverWorld.getBlockState(blockpos).getBlock() instanceof ForceFieldFloorBlock) {
                ((ForceFieldFloorTileEntity)serverWorld.getTileEntity(blockpos)).removeBlock(false);
              }
            });

         if (state.getBlock() == DMBlocks.TARDIS.get() && te instanceof TardisTileEntity) {
           TardisTileEntity tardis = (TardisTileEntity)te;
           tardis.closeDoor(TardisDoor.BOTH, TardisTileEntity.DoorSource.TARDIS);
           if (doAnimation(serverWorld, pos)) {
             tardis.setState(TardisState.DEMAT);
          } else {
             serverWorld.setBlockState(pos, Blocks.AIR.getDefaultState());
          }
        }
      }



       data.depleatFluidLink(player, world.rand);
       data.setPreviousLocation(data.getCurrentLocation());
       data.save();

       TardisFlightData flight = TardisFlightPool.getFlightData(data);

       boolean waypointApplied = false;
       ListNBT waypoints = null;
       if (flight != null &&
         flight.getWaypoints() != null) {
         waypointApplied = true;
         waypoints = flight.getWaypoints();
      }


       TardisFlightPool.addFlight(data);
       TardisFlightPool.getFlightData(data).setFlightStartTime();
       TardisFlightPool.getFlightData(data).setWaypoints(waypoints);

       if (showMessage) ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_HAS_DEMAT, ChatUtil.MessageType.STATUS_BAR);
       return true;
    }

     return false;
  }

  public static boolean remat(PlayerEntity player, World world, TardisData data) {
     if (!world.isRemote && data != null && data.isInFlight() && !DMFlightMode.isInFlight(data.getGlobalID())) {
       TardisFlightData flightData = TardisFlightPool.getFlightData(data);
       if (flightData != null) {
         boolean waypoint = (flightData.getWaypoints() != null);

         ServerWorld currentWorld = world.getServer().getWorld(data.getCurrentLocation().dimensionWorldKey());
         ServerWorld serverWorld = world.getServer().getWorld(flightData.dimensionWorldKey());

         double requiredFuel = data.calculateFuelForJourney((World)currentWorld, (World)serverWorld, data.getCurrentLocation().getBlockPosition(), flightData.getPos());

         float fuelModifier = 1.0F - data.getFluidLinkFuelConsumption() / 100.0F;
         if (fuelModifier <= 0.0F) {
           fuelModifier = 0.2F;
        }

         requiredFuel *= fuelModifier;

         if (data.isInFlight()) {
           if (data.timeLeft() == 0.0D) {
             if (requiredFuel <= data.getFuel()) {
               BlockPos pos = new BlockPos(flightData.getPos(Direction.Axis.X), flightData.getPos(Direction.Axis.Y), flightData.getPos(Direction.Axis.Z));

               if (data.getFluidLinkAccuracy() < 90) {
                 int rand = 4;
                 if (data.getFluidLinkAccuracy() < 10) {
                   rand = 1;
                }
                 if (serverWorld.rand.nextInt(rand) == 0) {
                   int offset = 30 - (int)(data.getFluidLinkAccuracy() / 100.0F * 30.0F);
                   pos = pos.func_177982_a(serverWorld.rand.nextInt(offset), serverWorld.rand.nextInt(offset), serverWorld.rand.nextInt(offset));
                   ChatUtil.sendError(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_DRIFTED, ChatUtil.MessageType.CHAT);
                   player.world.playSound(null, player.getPosition(), (SoundEvent)DMSoundEvents.TARDIS_FAULT_LOCATOR_ERROR.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
              }

               boolean recalculateLanding = flightData.shouldRecalculateLanding();

               if (waypoint) {
                 boolean waypointSet = false;

                 for (int i = 0; i < flightData.getWaypoints().size(); i++) {
                   CompoundNBT tag = flightData.getWaypoints().func_150305_b(i);
                   BlockPos p1 = new BlockPos(tag.getInt("pos_x"), tag.getInt("pos_y"), tag.getInt("pos_z"));
                   ServerWorld sw = world.getServer().getWorld(RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(tag.func_74779_i("location"))));

                   if (canLandOnBlock((World)sw, p1.func_177977_b())) {
                     pos = p1;
                     serverWorld = sw;
                     waypointSet = true;
                     flightData.setRotationAngle(tag.func_74760_g("facing"));

                     TardisFlightPool.updateFlight(data, tag.getInt("pos_x"), tag.getInt("pos_y"), tag.getInt("pos_z"), RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(tag.func_74779_i("location"))));
                     TardisFlightPool.getFlightData(data).setRotationAngle(tag.func_74760_g("facing"));
                     TardisFlightPool.getFlightData(data).setWaypoints(null);
                     recalculateLanding = false;

                    break;
                  }
                }
                 if (!waypointSet) {
                   ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_UNABLE_TO_LAND, ChatUtil.MessageType.STATUS_BAR);
                   return false;
                }
              }

               if (serverWorld != null) {

                 if (recalculateLanding) {
                   pos = getLandingPosition(serverWorld, pos, flightData.getRotationAngle());
                }









                 WorldBorder border = serverWorld.func_175723_af();
                 if (!border.func_177746_a(pos)) {
                   ChatUtil.sendError(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_OUTSIDE_WORLD_BOUNDS, ChatUtil.MessageType.CHAT);
                   return false;
                }

                 if (!canLandOnBlock((World)serverWorld, pos, true, flightData.getRotationAngle())) {
                   ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_UNABLE_TO_LAND, ChatUtil.MessageType.STATUS_BAR);
                   return false;
                }

                 if (!waypoint && serverWorld.getDimensionKey().equals(DMDimensions.TARDIS)) {
                   TardisData dataNew = DMTardis.getTardisFromInteriorPos(pos);
                   if (dataNew == null || (dataNew != null && !dataNew.isOwner(player))) {
                     ChatUtil.sendError(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_OUTSIDE_WORLD_BOUNDS, ChatUtil.MessageType.CHAT);
                     return false;
                  }
                }

                 serverWorld.setBlockState(pos, ((Block)DMBlocks.TARDIS.get()).getDefaultState());

                 TardisTileEntity tardis = (TardisTileEntity)serverWorld.getTileEntity(pos);
                 tardis.globalID = data.getGlobalID();
                 tardis.rotation = flightData.getRotationAngle();
                 data.setCurrentLocation(pos, flightData.dimensionWorldKey());
                 data.getCurrentLocation().setFacing(tardis.rotation);
                 data.setFuel(data.getFuel() - requiredFuel);
                 data.depleatFluidLink(player, world.rand);
                 data.save();

                 if (doAnimation(serverWorld, pos)) {
                   tardis.setState(TardisState.REMAT);
                }

                 TardisFlightPool.completeFlight(serverWorld.getServer(), data);
                 ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_HAS_REMAT, ChatUtil.MessageType.STATUS_BAR);
                 return true;
              }
            } else {

               ChatUtil.sendError(player, (IFormattableTextComponent)new TranslationTextComponent(DMTranslationKeys.TARDIS_NOT_ENOUGH_FUEL.func_150268_i(), new Object[] { new StringTextComponent("Required: " + (Math.floor(data.getFuel() * 100.0D) / 100.0D) + "/" + (Math.floor(requiredFuel * 100.0D) / 100.0D)) }), ChatUtil.MessageType.CHAT);
               return false;
            }
          } else {
             int seconds = (int)data.timeLeft();
             String s = seconds + "s";
             ChatUtil.sendError(player, (IFormattableTextComponent)new TranslationTextComponent(DMTranslationKeys.TARDIS_TRAVELING.func_150268_i(), new Object[] { new StringTextComponent(s) }), ChatUtil.MessageType.CHAT);
             return false;
          }
        } else {
           ChatUtil.sendError(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_STILL_TAKING_OFF, ChatUtil.MessageType.STATUS_BAR);
           return false;
        }
      }
    }
     return false;
  }

  public static boolean doAnimation(ServerWorld world, BlockPos pos) {
     if (world.func_217369_A().size() <= 0) return false;

     PlayerEntity ply = world.func_217366_a(pos.getX(), pos.getY(), pos.getZ(), 65.0D, false);
     return (ply != null);
  }

  public static BlockPos getLandingPosition(ServerWorld world, BlockPos pos, float rotation) {
     int y = pos.getY();

     int height = world.func_217301_I() - 1;
     if (world.getDimensionKey() == World.field_234919_h_) height = 126;

     BlockPos posUP = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
     BlockPos posDN = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

    while (true) {
       boolean pastheight = (posUP.getY() >= height);
       boolean bellowmins = (posDN.getY() <= ((Integer)DMConfig.COMMON.tardisYMin.get()).intValue());
       if (pastheight && bellowmins)
        break;
       if (!bellowmins) {
         if (canLandOnBlock((World)world, posDN, true, rotation)) return posDN;
         for (int i = 0; i < 16; i++) {
           int xRand = (world.rand.nextBoolean() ? -1 : 1) * world.rand.nextInt(16);
           int zRand = (world.rand.nextBoolean() ? -1 : 1) * world.rand.nextInt(16);
           BlockPos rnd = posDN.func_177982_a(xRand, 0, zRand);
           if (canLandOnBlock((World)world, rnd, true, rotation)) return rnd;

        }
       } else if (!pastheight) {
         if (canLandOnBlock((World)world, posUP, true, rotation)) return posUP;
         for (int i = 0; i < 16; i++) {
           int xRand = (world.rand.nextBoolean() ? -1 : 1) * world.rand.nextInt(16);
           int zRand = (world.rand.nextBoolean() ? -1 : 1) * world.rand.nextInt(16);
           BlockPos rnd = posUP.func_177982_a(xRand, 0, zRand);
           if (canLandOnBlock((World)world, rnd, true, rotation)) return rnd;

        }
      }
       posUP = posUP.func_177984_a();
       posDN = posDN.func_177977_b();
    }

     if (world.getBlockState(pos).func_203425_a(Blocks.field_150357_h)) {
       if (world.getDimensionKey() == World.field_234919_h_ && y > 64)
         return pos.func_177977_b();
       return pos.func_177984_a();
     }  return pos;
  }


























  public static boolean canLandOnBlock(World world, BlockPos pos, boolean checkExit, float rotation) {
     boolean land = (WorldUtils.canPlace(world, pos) && !(world.func_204610_c(pos).func_206886_c() instanceof net.minecraft.fluid.LavaFluid));
     boolean floor = (!WorldUtils.canPlace(world, pos.func_177977_b()) || (!world.func_204610_c(pos.func_177977_b()).func_206888_e() && world.func_204610_c(pos).func_206888_e()));

     if (checkExit) {
       float rot = rotation;
       BlockPos a = pos.func_177984_a();
       BlockState b = world.getBlockState(a.func_177971_a(SWDMathUtils.cardinalToVec(SWDMathUtils.rotationToCardinal(rot))));
       boolean exit = !b.func_200132_m();
       return (land && floor && exit);
    }

     return (land && floor);
  }

  public static boolean canLandOnBlock(World world, BlockPos pos) {
     return (Block.func_220064_c((IBlockReader)world, pos) && WorldUtils.canPlace(world, pos.func_177984_a()));
  }

  public static void addForceField(ServerWorld serverWorld, BlockPos pos) {
     BlockState under = serverWorld.getBlockState(pos.func_177982_a(0, -1, 0));

     if (under.func_204520_s() != null && !under.func_204520_s().func_206888_e()) {

       HashMap<BlockPos, BlockState> states = new HashMap<>();

       for (int i = -1; i < 2; i++) {
         for (int j = -1; j < 2; j++) {
           BlockPos newPos = pos.func_177982_a(i, -1, j);
           BlockState und = serverWorld.getBlockState(newPos);
           boolean flag1 = (und.func_204520_s() != null && !und.func_204520_s().func_206888_e() && und.getBlock() instanceof net.minecraft.block.FlowingFluidBlock);
           boolean flag2 = serverWorld.func_175623_d(newPos);

           if (flag1 || flag2) states.put(newPos, und);

        }
      }
       for (Map.Entry<BlockPos, BlockState> entry : states.entrySet()) {
         BlockPos newPos = entry.getKey();
         BlockState und = entry.getValue();

         BlockState newState = ((Block)DMBlocks.FORCE_FIELD.get()).getDefaultState();
         int fluid = ArrayHelper.findIndex(ForgeRegistries.FLUIDS.getValues().toArray(), und.func_204520_s().func_206886_c());
         if (und.func_204520_s().func_206889_d()) newState = (BlockState)newState.func_206870_a((Property)ForceFieldFloorBlock.LIQUID, Integer.valueOf(fluid));

         serverWorld.setBlockState(newPos, newState);
         if (serverWorld.getTileEntity(newPos) instanceof ForceFieldFloorTileEntity) {
           ForceFieldFloorTileEntity tile = (ForceFieldFloorTileEntity)serverWorld.getTileEntity(newPos);
           CompoundNBT nbt = tile.serializeNBT();

           if (und.func_204520_s() != null && und.func_204520_s().func_206889_d()) {
             nbt.func_74778_a(DMNBTKeys.FORCE_FIELD_LIQUID, und.getBlock().getRegistryName().toString());
          }
           tile.deserializeNBT(nbt);
        }
      }
    }
  }
}


