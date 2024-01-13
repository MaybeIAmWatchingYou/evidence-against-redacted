package com.swdteam.network.packets;

import com.swdteam.common.container.ArsContainer;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTardisRegistry;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisInterior;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;


public class PacketStartInteriorBuild
{
  private BlockPos pos;
  private ResourceLocation resourceLocation;

  public PacketStartInteriorBuild(BlockPos pos, ResourceLocation resourceLocation) {
     this.resourceLocation = resourceLocation;
     this.pos = pos;
  }

  public static void encode(PacketStartInteriorBuild msg, PacketBuffer buf) {
     buf.func_192572_a(msg.resourceLocation);
     buf.func_179255_a(msg.pos);
  }

  public static PacketStartInteriorBuild decode(PacketBuffer buf) {
     ResourceLocation rl = buf.func_192575_l();
     BlockPos pos = buf.func_179259_c();

     return new PacketStartInteriorBuild(pos, rl);
  }


  public static void handle(PacketStartInteriorBuild msg, Supplier<NetworkEvent.Context> ctx) {
     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
          if (((NetworkEvent.Context)ctx.get()).getNetworkManager().getDirection() == PacketDirection.SERVERBOUND) {
            ServerPlayerEntity serverPlayerEntity = ((NetworkEvent.Context)ctx.get()).getSender();

            TardisData data = DMTardis.getTardisFromInteriorPos(msg.pos);

            if (data != null) {
              if (data.hasPermission((PlayerEntity)serverPlayerEntity, TardisData.PermissionType.DEFAULT)) {
                if (DMTardisRegistry.getTardisInteriors().containsKey(msg.resourceLocation)) {
                  TardisInterior interior = (TardisInterior)DMTardisRegistry.getTardisInteriors().get(msg.resourceLocation);

                  if (interior.getRecipe() != null && interior.getRecipe().getParts() != null && (interior.getRecipe().getParts()).length > 0) {
                    data.setCurrentlyBuilding(msg.resourceLocation);

                    data.save();

                    NetworkHooks.openGui(serverPlayerEntity, msg.getMenuProvider(((PlayerEntity)serverPlayerEntity).world.getBlockState(msg.pos), ((PlayerEntity)serverPlayerEntity).world, msg.pos), msg.pos);
                  }
                }
              } else {
                data.noPermission((PlayerEntity)serverPlayerEntity);
              }
            }
          }
        });

     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
  }

  @Nullable
  public INamedContainerProvider getMenuProvider(BlockState blockState, World world, BlockPos blockPos) {
     return (INamedContainerProvider)new SimpleNamedContainerProvider((p_220283_2_, p_220283_3_, p_220283_4_) -> new ArsContainer(p_220283_2_, p_220283_3_, world, blockPos), (ITextComponent)DMTranslationKeys.GUI_ARS_NAME);
  }
}


