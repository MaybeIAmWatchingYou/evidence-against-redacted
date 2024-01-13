package com.swdteam.common.init;
import com.swdteam.common.entity.KerblamManEntity;
import com.swdteam.common.kerblam.KerblamItem;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketSyncEntityData;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.NBTUtil;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class DMKerblamStock {
   private static Map<ResourceLocation, KerblamItem> ITEMS = new LinkedHashMap<>();

  public static Map<ResourceLocation, KerblamItem> getItems() {
     return ITEMS;
  }

  public static void addItem(ResourceLocation rl, KerblamItem item) {
     ITEMS.put(rl, item);
  }

  public static void makeDelivery(final PlayerEntity player, Inventory... inv) {
     player.getServer().func_222817_e(new Runnable()
        {

          public void run()
          {
             BlockPos pos = player.getPosition().offset(player.getHorizontalFacing().getOpposite(), 2);
             Vector3d posToSpawnAt = new Vector3d(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
             float facing = player.getHorizontalFacing().func_185119_l();
             if (player.world.getDimensionKey().equals(DMDimensions.TARDIS)) {
               TardisData data = DMTardis.getTardisFromInteriorPos(player.getPosition());
               if (data != null) {
                 posToSpawnAt = new Vector3d(data.getInteriorSpawnPosition().func_82615_a(), data.getInteriorSpawnPosition().func_82617_b(), data.getInteriorSpawnPosition().func_82616_c());
                 facing = data.getInteriorSpawnRotation();
              }
            }

             BlockPos posToSpawn = new BlockPos(posToSpawnAt);
             VoxelShape hitbox = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.1875D, 0.0D, 0.1875D, 0.8125D, 1.875D, 0.8125D), new VoxelShape[0]);
             VoxelShape spawnBox = VoxelShapes.func_197872_a(player.world.getBlockState(posToSpawn).func_196952_d((IBlockReader)player.world, posToSpawn), player.world.getBlockState(posToSpawn.func_177984_a()).func_196952_d((IBlockReader)player.world, posToSpawn.func_177984_a()).func_197751_a(0.0D, 1.0D, 0.0D));
             boolean obstruction = VoxelShapes.func_197879_c(hitbox, spawnBox, IBooleanFunction.field_223238_i_);

             ItemStack[] stacks = new ItemStack[inv.length];

             for (int i = 0; i < inv.length; i++) {
               Inventory inventory = inv[i];

               CompoundNBT items = new CompoundNBT();

               items = NBTUtil.saveAllItems(items, inventory);

               ItemStack stack = new ItemStack((IItemProvider)DMBlocks.KERBLAM_BOX.get());
               if (!stack.func_77942_o()) {
                 stack.func_77982_d(new CompoundNBT());
              }

               stack.func_77978_p().func_218657_a("BlockEntityTag", (INBT)items);
               stacks[i] = stack;
            }

             if (!obstruction) {
               KerblamManEntity ent = new KerblamManEntity((EntityType)DMEntities.KERBLAM_MAN.get(), player.world);
               ent.func_70080_a(posToSpawnAt.x, posToSpawnAt.y, posToSpawnAt.z, facing, 0.0F);

               player.world.addEntity((Entity)ent);
               CompoundNBT tag = new CompoundNBT();
               ent.func_189511_e(tag);
               NetworkHandler.sendToAllClients(new PacketSyncEntityData(ent.func_145782_y(), tag), player.world);
               ent.setDeliveryFor(player);
               ent.addPackages(stacks);

               for (int j = 0; j < 5; j++) {
                 ((ServerWorld)player.world).addParticle((IParticleData)ParticleTypes.SMOKE, (ent.getPositionVec()).x, (ent.getPositionVec()).y, (ent.getPositionVec()).z, 0.0D, 0.1D, 0.0D);
              }

               ChatUtil.sendCompletedMsg(player, "You order is being delivered at: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ(), ChatUtil.MessageType.CHAT);
            } else {
               for (ItemStack stack : stacks)
                 player.addItemStackToInventory(stack);
            }
          }
        });
  }
}


