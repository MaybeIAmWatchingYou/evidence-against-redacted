package com.swdteam.util;

import com.swdteam.common.teleport.TeleportRequest;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;




public class TeleportUtil
{
   public static Map<Entity, TeleportRequest> TELEPORT_REQUESTS = new HashMap<>();

  public static void teleportPlayer(Entity entity, RegistryKey<World> destinationType, BlockPos destinationPos) {
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     System.out.println("jkahdlkajhsdlkjahsdkljhasj");
     teleportPlayer(entity, destinationType, (IPosition)destinationPos, entity.field_70177_z);
  }

  public static void teleportPlayer(Entity entity, RegistryKey<World> destinationType, IPosition destinationPos, float yRot) {
     if (entity != null) {
       if (entity instanceof ContainerMinecartEntity) {
         ((ContainerMinecartEntity)entity).dropContentsWhenDead(false);
      }

       if (destinationPos != null) {
         ServerWorld nextWorld = entity.getServer().getWorld(destinationType);
         nextWorld.getChunk(new BlockPos(destinationPos));

         if (entity instanceof ServerPlayerEntity)
         { ((ServerPlayerEntity)entity).func_200619_a(nextWorld, destinationPos.func_82615_a(), destinationPos.func_82617_b(), destinationPos.func_82616_c(), yRot, entity.field_70125_A); }

         else if (nextWorld == entity.world)
         { entity.setPosition(destinationPos.func_82615_a(), destinationPos.func_82617_b(), destinationPos.func_82616_c());
           entity.func_184188_bt().forEach(e -> { if (e instanceof net.minecraft.entity.player.PlayerEntity)
                  e.func_213319_R();  }); }
         else { CompoundNBT compoundtag = new CompoundNBT();
           entity.func_70039_c(compoundtag);
           compoundtag.func_74778_a("id", entity.getType().getRegistryName().toString());

           Entity newEntity = EntityType.func_220335_a(compoundtag, (World)nextWorld, e -> {
                e.func_70012_b(destinationPos.func_82615_a(), destinationPos.func_82617_b(), destinationPos.func_82616_c(), yRot, (e.func_189653_aC()).field_189982_i);

                return e;
              });
           entity.remove();
           if (newEntity != null) {
             entity.func_184188_bt().forEach(e -> { if (!(e instanceof net.minecraft.entity.player.PlayerEntity))
                     e.remove();  }); nextWorld.addEntity(newEntity);
             if (newEntity instanceof ContainerMinecartEntity)
               ((ContainerMinecartEntity)newEntity).dropContentsWhenDead(true);
          }  }

      }
    }
  }
}


