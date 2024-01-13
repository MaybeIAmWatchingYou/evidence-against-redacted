package com.swdteam.common.block;

import com.swdteam.common.tileentity.MozaiqueTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketSetColor;
import com.swdteam.util.ChatUtil;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;





public class MozaiqueBlock
  extends RotatableTileEntityBase.WaterLoggable
{
  public MozaiqueBlock(Supplier<TileEntity> tile) {
     super(tile, AbstractBlock.Properties.func_200945_a(Material.field_151573_f).func_200943_b(0.8F).func_226896_b_());
  }



  
  public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult result) {
     if (!world.isRemote && 
       result.func_216347_e() != null) {
       Vector3d loc = result.func_216347_e().func_178786_a(pos.getX(), pos.getY(), pos.getZ());
       TileEntity te = world.getTileEntity(pos);
      
       if (te instanceof MozaiqueTileEntity) {
         Direction dir = (Direction)state.get((Property)FACING);
         if (dir == Direction.NORTH || dir == Direction.SOUTH) {
           if (loc.x > 0.5D && loc.x <= 1.0D && loc.y > 0.5D && loc.y < 1.0D) {
            
             ((MozaiqueTileEntity)te).draw((dir == Direction.NORTH) ? MozaiqueTileEntity.Corner.TOP_LEFT : MozaiqueTileEntity.Corner.TOP_RIGHT, entity);
           } else if (loc.x <= 0.5D && loc.x >= 0.0D && loc.y > 0.5D && loc.y < 1.0D) {
            
             ((MozaiqueTileEntity)te).draw((dir == Direction.NORTH) ? MozaiqueTileEntity.Corner.TOP_RIGHT : MozaiqueTileEntity.Corner.TOP_LEFT, entity);
           } else if (loc.x > 0.5D && loc.x <= 1.0D && loc.y <= 0.5D && loc.y > 0.0D) {
            
             ((MozaiqueTileEntity)te).draw((dir == Direction.NORTH) ? MozaiqueTileEntity.Corner.BOTTOM_LEFT : MozaiqueTileEntity.Corner.BOTTOM_RIGHT, entity);
           } else if (loc.x <= 0.5D && loc.x >= 0.0D && loc.y <= 0.5D && loc.y > 0.0D) {
            
             ((MozaiqueTileEntity)te).draw((dir == Direction.NORTH) ? MozaiqueTileEntity.Corner.BOTTOM_RIGHT : MozaiqueTileEntity.Corner.BOTTOM_LEFT, entity);
          } 
         } else if (dir == Direction.EAST || dir == Direction.WEST) {
           if (loc.z > 0.5D && loc.z <= 1.0D && loc.y > 0.5D && loc.y < 1.0D) {
            
             ((MozaiqueTileEntity)te).draw((dir == Direction.WEST) ? MozaiqueTileEntity.Corner.TOP_LEFT : MozaiqueTileEntity.Corner.TOP_RIGHT, entity);
           } else if (loc.z <= 0.5D && loc.z >= 0.0D && loc.y > 0.5D && loc.y < 1.0D) {
            
             ((MozaiqueTileEntity)te).draw((dir == Direction.WEST) ? MozaiqueTileEntity.Corner.TOP_RIGHT : MozaiqueTileEntity.Corner.TOP_LEFT, entity);
           } else if (loc.z > 0.5D && loc.z <= 1.0D && loc.y <= 0.5D && loc.y > 0.0D) {
            
             ((MozaiqueTileEntity)te).draw((dir == Direction.WEST) ? MozaiqueTileEntity.Corner.BOTTOM_LEFT : MozaiqueTileEntity.Corner.BOTTOM_RIGHT, entity);
           } else if (loc.z <= 0.5D && loc.z >= 0.0D && loc.y <= 0.5D && loc.y > 0.0D) {
            
             ((MozaiqueTileEntity)te).draw((dir == Direction.WEST) ? MozaiqueTileEntity.Corner.BOTTOM_RIGHT : MozaiqueTileEntity.Corner.BOTTOM_LEFT, entity);
          } 
        } 
      } 
    } 


    
     return ActionResultType.CONSUME;
  }
  
  public void func_196270_a(BlockState p_196270_1_, World p_196270_2_, BlockPos p_196270_3_, PlayerEntity p_196270_4_) {
     if (!p_196270_2_.isRemote()) {
       MozaiqueTileEntity.Colors col = MozaiqueTileEntity.Colors.WHITE;
       if (MozaiqueTileEntity.PLAYER_COLORS.containsKey(p_196270_4_)) {
         col = (MozaiqueTileEntity.Colors)MozaiqueTileEntity.PLAYER_COLORS.get(p_196270_4_);
      }
       if (col.getId() + 1 >= (MozaiqueTileEntity.Colors.values()).length) {
         col = MozaiqueTileEntity.Colors.WHITE;
      } else {
         col = MozaiqueTileEntity.Colors.values()[col.getId() + 1];
      } 

      
       MozaiqueTileEntity.PLAYER_COLORS.put(p_196270_4_, col);
       ChatUtil.sendCompletedMsg(p_196270_4_, "Set color to: " + col.getName(), ChatUtil.MessageType.STATUS_BAR);
    } 
  }



  
  public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
     if (player.world.isRemote && 
       target.func_216347_e() != null) {
       Vector3d loc = target.func_216347_e().func_178786_a(pos.getX(), pos.getY(), pos.getZ());
       TileEntity te = world.getTileEntity(pos);
       MozaiqueTileEntity.Colors col = MozaiqueTileEntity.Colors.WHITE;
       if (te instanceof MozaiqueTileEntity) {
         Direction dir = (Direction)state.get((Property)FACING);
         if (dir == Direction.NORTH || dir == Direction.SOUTH) {
           if (loc.x > 0.5D && loc.x <= 1.0D && loc.y > 0.5D && loc.y < 1.0D) {
            
             col = ((MozaiqueTileEntity)te).getColor((dir == Direction.NORTH) ? MozaiqueTileEntity.Corner.TOP_LEFT : MozaiqueTileEntity.Corner.TOP_RIGHT);
           } else if (loc.x <= 0.5D && loc.x >= 0.0D && loc.y > 0.5D && loc.y < 1.0D) {
            
             col = ((MozaiqueTileEntity)te).getColor((dir == Direction.NORTH) ? MozaiqueTileEntity.Corner.TOP_RIGHT : MozaiqueTileEntity.Corner.TOP_LEFT);
           } else if (loc.x > 0.5D && loc.x <= 1.0D && loc.y <= 0.5D && loc.y > 0.0D) {
            
             col = ((MozaiqueTileEntity)te).getColor((dir == Direction.NORTH) ? MozaiqueTileEntity.Corner.BOTTOM_LEFT : MozaiqueTileEntity.Corner.BOTTOM_RIGHT);
           } else if (loc.x <= 0.5D && loc.x >= 0.0D && loc.y <= 0.5D && loc.y > 0.0D) {
            
             col = ((MozaiqueTileEntity)te).getColor((dir == Direction.NORTH) ? MozaiqueTileEntity.Corner.BOTTOM_RIGHT : MozaiqueTileEntity.Corner.BOTTOM_LEFT);
          } 
         } else if (dir == Direction.EAST || dir == Direction.WEST) {
           if (loc.z > 0.5D && loc.z <= 1.0D && loc.y > 0.5D && loc.y < 1.0D) {
            
             col = ((MozaiqueTileEntity)te).getColor((dir == Direction.WEST) ? MozaiqueTileEntity.Corner.TOP_LEFT : MozaiqueTileEntity.Corner.TOP_RIGHT);
           } else if (loc.z <= 0.5D && loc.z >= 0.0D && loc.y > 0.5D && loc.y < 1.0D) {
            
             col = ((MozaiqueTileEntity)te).getColor((dir == Direction.WEST) ? MozaiqueTileEntity.Corner.TOP_RIGHT : MozaiqueTileEntity.Corner.TOP_LEFT);
           } else if (loc.z > 0.5D && loc.z <= 1.0D && loc.y <= 0.5D && loc.y > 0.0D) {
            
             col = ((MozaiqueTileEntity)te).getColor((dir == Direction.WEST) ? MozaiqueTileEntity.Corner.BOTTOM_LEFT : MozaiqueTileEntity.Corner.BOTTOM_RIGHT);
           } else if (loc.z <= 0.5D && loc.z >= 0.0D && loc.y <= 0.5D && loc.y > 0.0D) {
            
             col = ((MozaiqueTileEntity)te).getColor((dir == Direction.WEST) ? MozaiqueTileEntity.Corner.BOTTOM_RIGHT : MozaiqueTileEntity.Corner.BOTTOM_LEFT);
          } 
        } 
      } 
       NetworkHandler.sendServerPacket(new PacketSetColor(col));
    } 

    
     return super.getPickBlock(state, target, world, pos, player);
  }



  
  public ItemStack func_185473_a(IBlockReader p_185473_1_, BlockPos p_185473_2_, BlockState p_185473_3_) {
     return super.func_185473_a(p_185473_1_, p_185473_2_, p_185473_3_);
  }
}


