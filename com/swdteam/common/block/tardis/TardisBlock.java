package com.swdteam.common.block.tardis;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.block.IHaveNoItem;
import com.swdteam.common.block.TileEntityBaseBlock;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.Data;
import com.swdteam.common.tardis.Tardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tardis.TardisState;
import com.swdteam.common.tardis.actions.TardisActionList;
import com.swdteam.common.tileentity.DMTileEntityBase;
import com.swdteam.common.tileentity.TardisTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.SWDMathUtils;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StemBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;





public class TardisBlock
  extends TileEntityBaseBlock.WaterLoggable
  implements IHaveNoItem
{
   private static final VoxelShape DEFAULT_SHAPE = VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 0.9999D, 2.0D, 0.9999D);
  
  public TardisBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return getTardisShape(worldIn, pos, Boolean.valueOf(true));
  }

  
  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return getTardisShape(worldIn, pos, Boolean.valueOf(false));
  }

  
  public StemBlock getStem() {
     return (StemBlock)DMBlocks.TARDIS_PLANT.get();
  }


  
  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult p_225533_6_) {
     if (handIn == Hand.MAIN_HAND && 
       !worldIn.isRemote)
    {
      
       if (player.getHeldItem(handIn) != null && !(player.getHeldItem(handIn).getItem() instanceof com.swdteam.common.item.TardisKeyItem)) {
         TileEntity te = worldIn.getTileEntity(pos);
         if (te instanceof TardisTileEntity) {




          
           TardisTileEntity tardis = (TardisTileEntity)te;
          
           TardisData data = DMTardis.getTardis(tardis.globalID);
           if (data != null) {
            
             if (!data.getCurrentLocation().getBlockPosition().equals(pos) && tardis.state != TardisState.DEMAT) {
               if (TardisActionList.doAnimation((ServerWorld)worldIn, pos)) { ((TardisTileEntity)te).setState(TardisState.DEMAT); }
               else { worldIn.setBlockState(pos, Blocks.AIR.getDefaultState()); }
            
            }
             if (data.getOwner_uuid() == null) {
               if (!data.hasGenerated()) {
                 NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 3));
                 return ActionResultType.FAIL;
              } 
               return ActionResultType.FAIL;
            } 
            
             if (data.hasPermission(player, TardisData.PermissionType.DEFAULT) && 
               !data.hasGenerated()) {
               NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 3));
               return ActionResultType.FAIL;
            } 

            
             if (data.hasGenerated()) {
               if (!data.isLocked()) {
                 if (tardis.state == TardisState.NEUTRAL) {
                   if (player.func_225608_bj_()) {
                     if (tardis.doorOpenRight) {
                       tardis.toggleDoor(TardisDoor.LEFT, TardisTileEntity.DoorSource.TARDIS);
                    } else {
                       tardis.toggleDoor(TardisDoor.RIGHT, TardisTileEntity.DoorSource.TARDIS);
                    } 
                  } else {
                     tardis.toggleDoor(TardisDoor.RIGHT, TardisTileEntity.DoorSource.TARDIS);
                     if (tardis.doorOpenLeft) {
                       tardis.toggleDoor(TardisDoor.LEFT, TardisTileEntity.DoorSource.TARDIS);
                    }
                  } 
                }
              } else {
                 ChatUtil.sendMessageToPlayer(player, TextFormatting.YELLOW + data.getOwner_name() + "'s TARDIS is locked", ChatUtil.MessageType.STATUS_BAR);
              } 
            }
          } 
        } 
      } else {
        
         return ActionResultType.PASS;
      } 
    }

    
     return ActionResultType.CONSUME;
  }

  
  public boolean func_220074_n(BlockState state) {
     return true;
  }

  
  public int func_200011_d(BlockState state, IBlockReader worldIn, BlockPos pos) {
     return 1;
  }

  
  public void func_180633_a(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
     TileEntity te = worldIn.getTileEntity(pos);
     if (te instanceof TardisTileEntity) {
       int i = MathHelper.func_76128_c(placer.field_70126_B);
      
       if (i < 0) {
         i = 360 + i;
      }
      
       i = (int)Direction.func_176733_a(i).func_185119_l();
      
       ((TardisTileEntity)te).rotation = i;
       if (!worldIn.isRemote) ((DMTileEntityBase)te).sendUpdates(); 
    } 
     super.func_180633_a(worldIn, pos, state, placer, stack);
  }


  
  public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
     return new ItemStack((IItemProvider)DMItems.TARDIS_SPAWNER.get());
  }
  
  private VoxelShape getTardisShape(IBlockReader worldIn, BlockPos pos, Boolean visual) {
     if (!(worldIn.getTileEntity(pos) instanceof TardisTileEntity)) return DEFAULT_SHAPE; 
    try {
      TardisData data;
       TardisTileEntity tardis = (TardisTileEntity)worldIn.getTileEntity(pos);


      
       if (tardis.getWorld() == null) return DEFAULT_SHAPE;
      
       World world = tardis.getWorld();
       if (world.isRemote) {
         data = ClientTardisCache.getTardisData(tardis.globalID);
        
         if (data == null || !Minecraft.func_71410_x().func_195551_G().func_219533_b(data.getTardisExterior().getData().getModel(0))) {
           data = ClientTardisCache.DEFAULT_DATA;
        }
      } else {
         data = DMTardis.getTardis(tardis.globalID);
      } 
      
       Tardis tardisData = data.getTardisExterior();
       Data.ExteriorHitbox hitbox = tardisData.getData().getHitbox();
       if (visual.booleanValue()) hitbox = tardisData.getData().getVisualHitbox();
      
       switch (SWDMathUtils.rotationToCardinal(tardis.rotation))
      
      { default:
           return hitbox.getRotationShape(Data.ExteriorHitbox.RotationEnum.NORTH);
        case "NORTHEAST":
           return hitbox.getRotationShape(Data.ExteriorHitbox.RotationEnum.NORTH_EAST);
        case "EAST":
           return hitbox.getRotationShape(Data.ExteriorHitbox.RotationEnum.EAST);
        case "SOUTHEAST":
           return hitbox.getRotationShape(Data.ExteriorHitbox.RotationEnum.SOUTH_EAST);
        case "SOUTH":
           return hitbox.getRotationShape(Data.ExteriorHitbox.RotationEnum.SOUTH);
        case "SOUTHWEST":
           return hitbox.getRotationShape(Data.ExteriorHitbox.RotationEnum.SOUTH_WEST);
        case "WEST":
           return hitbox.getRotationShape(Data.ExteriorHitbox.RotationEnum.WEST);
        case "NORTHWEST":
           break; }  return hitbox.getRotationShape(Data.ExteriorHitbox.RotationEnum.NORTH_WEST);
    }
     catch (Exception err) {
       return DEFAULT_SHAPE;
    } 
  }
}


