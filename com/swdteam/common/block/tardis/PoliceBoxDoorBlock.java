package com.swdteam.common.block.tardis;

import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tileentity.tardis.DoubleDoorsTileEntity;
import com.swdteam.common.tileentity.tardis.PoliceBoxDoorsTileEntity;
import com.swdteam.util.ChatUtil;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;



public class PoliceBoxDoorBlock
  extends RotatableTileEntityBase.WaterLoggable
{
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.field_208163_P;
   public static final IntegerProperty OFFSET = IntegerProperty.func_177719_a("offset", 0, 2);
  
   public static final VoxelShape OFFSET_0_N = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new VoxelShape[0]);
   public static final VoxelShape OFFSET_0_E = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D), new VoxelShape[0]);
   public static final VoxelShape OFFSET_0_S = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D), new VoxelShape[0]);
   public static final VoxelShape OFFSET_0_W = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D), new VoxelShape[0]);
  
   public static final VoxelShape OFFSET_1 = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D), new VoxelShape[0]);
   public static final VoxelShape OFFSET_1_ROT = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D), new VoxelShape[0]);
  
   public static final VoxelShape OFFSET_2_N = OFFSET_0_S;
   public static final VoxelShape OFFSET_2_E = OFFSET_0_W;
   public static final VoxelShape OFFSET_2_S = OFFSET_0_N;
   public static final VoxelShape OFFSET_2_W = OFFSET_0_E;
  
  public PoliceBoxDoorBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
     this.tileEntitySupplier = tileEntitySupplier;
     func_180632_j((BlockState)((BlockState)getDefaultState().func_206870_a((Property)HALF, (Comparable)DoubleBlockHalf.LOWER)).func_206870_a((Property)OFFSET, Integer.valueOf(0)));
  }
  
  public BlockState func_196258_a(BlockItemUseContext context) {
     Direction d = context.func_195992_f().func_176746_e();
    
     BlockPos pos = context.func_195995_a();
     Vector3d hitVec = context.func_221532_j();
     float mouseX = (int)(100.0F * (float)(hitVec.func_82615_a() - pos.getX())) / 100.0F;
     float mouseZ = (int)(100.0F * (float)(hitVec.func_82616_c() - pos.getZ())) / 100.0F;
     int offset = 0;
    
     if (d == Direction.SOUTH) {
       if (mouseX < 0.33F) {
         offset = 0;
       } else if (mouseX < 0.66F) {
         offset = 1;
      } else {
         offset = 2;
      } 
     } else if (d == Direction.NORTH) {
       if (mouseX < 0.33F) {
         offset = 2;
       } else if (mouseX < 0.66F) {
         offset = 1;
      } else {
         offset = 0;
      } 
     } else if (d == Direction.WEST) {
       if (mouseZ < 0.33F) {
         offset = 0;
       } else if (mouseZ < 0.66F) {
         offset = 1;
      } else {
         offset = 2;
      } 
     } else if (d == Direction.EAST) {
       if (mouseZ < 0.33F) {
         offset = 2;
       } else if (mouseZ < 0.66F) {
         offset = 1;
      } else {
         offset = 0;
      } 
    } 
    
     return (BlockState)((BlockState)((BlockState)super.func_196258_a(context).func_206870_a((Property)FACING, (Comparable)d)).func_206870_a((Property)HALF, (Comparable)DoubleBlockHalf.LOWER)).func_206870_a((Property)OFFSET, Integer.valueOf(offset));
  }
  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     super.func_206840_a(builder);
     builder.func_206894_a(new Property[] { (Property)HALF, (Property)OFFSET });
  }

  
  public boolean hasTileEntity(BlockState state) {
     return (state.get((Property)HALF) == DoubleBlockHalf.LOWER);
  }


  
  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (handIn == Hand.MAIN_HAND) {
       if (player.getHeldItem(handIn) != null && !(player.getHeldItem(handIn).getItem() instanceof com.swdteam.common.item.TardisKeyItem)) {
         if (!worldIn.isRemote) {
          
           boolean shouldOpen = true;
           boolean ignoreDoorSync = true;
           if (worldIn.getDimensionKey().equals(DMDimensions.TARDIS)) {
             TardisData data = DMTardis.getTardisFromInteriorPos(pos);
             if (data != null && !DMFlightMode.isInFlight(data.getGlobalID())) {
               BlockPos baseDoorPos = (state.get((Property)HALF) == DoubleBlockHalf.LOWER) ? pos : pos.func_177977_b();
              
               boolean doorCheck = ((PoliceBoxDoorsTileEntity)worldIn.getTileEntity(baseDoorPos)).isMainDoor();
               if (doorCheck) {
                 ignoreDoorSync = false;
                 if (data.isInFlight()) {
                   TranslationTextComponent inFlight = DMTranslationKeys.TARDIS_IN_FLIGHT;
                   ChatUtil.sendMessageToPlayer(player, TextFormatting.YELLOW + inFlight.getString(), ChatUtil.MessageType.STATUS_BAR);
                   shouldOpen = false;
                } 
                
                 if (data.isLocked()) {
                   TranslationTextComponent isLocked = DMTranslationKeys.TARDIS_IS_LOCKED;
                   ChatUtil.sendMessageToPlayer(player, TextFormatting.YELLOW + isLocked.getString(), ChatUtil.MessageType.STATUS_BAR);
                   shouldOpen = false;
                } 
              } 
            } 
          } 
          
           if (shouldOpen) {
             BlockPos tilePos = pos;
             if (state.get((Property)HALF) == DoubleBlockHalf.UPPER) {
               tilePos = pos.func_177977_b();
            }
            
             TardisDoor door = TardisDoor.LEFT;
             Direction d = (Direction)state.get((Property)FACING);
            
             if (hit != null) {
               float mouseX = (int)(100.0F * (float)(hit.func_216347_e().func_82615_a() - pos.getX())) / 100.0F;
               float mouseZ = (int)(100.0F * (float)(hit.func_216347_e().func_82616_c() - pos.getZ())) / 100.0F;
               if (d == Direction.SOUTH) {
                 door = (mouseZ <= 0.5D) ? TardisDoor.LEFT : TardisDoor.RIGHT;
               } else if (d == Direction.NORTH) {
                 door = (mouseZ <= 0.5D) ? TardisDoor.RIGHT : TardisDoor.LEFT;
               } else if (d == Direction.EAST) {
                 door = (mouseX <= 0.5D) ? TardisDoor.LEFT : TardisDoor.RIGHT;
               } else if (d == Direction.WEST) {
                 door = (mouseX <= 0.5D) ? TardisDoor.RIGHT : TardisDoor.LEFT;
              } 
            } 
            
             TileEntity tile = worldIn.getTileEntity(tilePos);
             if (tile instanceof DoubleDoorsTileEntity) {
               DoubleDoorsTileEntity doors = (DoubleDoorsTileEntity)tile;
              
               if (!doors.isOpen(TardisDoor.LEFT)) {
                 doors.setOpen(TardisDoor.LEFT, true, ignoreDoorSync);
              }
               else if (door == TardisDoor.LEFT) {
                 doors.setOpen(TardisDoor.LEFT, !doors.isOpen(TardisDoor.LEFT), ignoreDoorSync);
                 if (doors.isOpen(TardisDoor.RIGHT)) {
                   doors.setOpen(TardisDoor.RIGHT, false, ignoreDoorSync);
                }
              } else {
                 doors.setOpen(TardisDoor.RIGHT, !doors.isOpen(TardisDoor.RIGHT), ignoreDoorSync);
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

  
  public boolean func_196260_a(BlockState state, IWorldReader worldIn, BlockPos pos) {
     BlockPos blockpos = pos.func_177984_a();
     BlockState blockstate = worldIn.getBlockState(blockpos);
     return !blockstate.func_224755_d((IBlockReader)worldIn, blockpos, Direction.DOWN);
  }
  
  public void func_180633_a(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
     FluidState fluidstate = worldIn.func_204610_c(pos.func_177984_a());
     worldIn.setBlockState(pos.func_177984_a(), (BlockState)((BlockState)state.func_206870_a((Property)WATERLOGGED, Boolean.valueOf((fluidstate.func_206886_c() == Fluids.field_204546_a)))).func_206870_a((Property)HALF, (Comparable)DoubleBlockHalf.UPPER), 3);
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     switch (((Integer)state.get((Property)OFFSET)).intValue())
    { default:
         switch ((Direction)state.get((Property)FACING)) { default:
             return OFFSET_0_N;
           case EAST: return OFFSET_0_E;
           case SOUTH: return OFFSET_0_S;
           case WEST: break; }  return OFFSET_0_W;
      
      case 1:
         if (state.get((Property)FACING) == Direction.NORTH || state.get((Property)FACING) == Direction.SOUTH)
           return OFFSET_1; 
         return OFFSET_1_ROT;
       case 2: break; }  switch ((Direction)state.get((Property)FACING)) { default:
         return OFFSET_2_N;
       case EAST: return OFFSET_2_E;
       case SOUTH: return OFFSET_2_S;
       case WEST: break; }  return OFFSET_2_W;
  }



  
  public VoxelShape func_220071_b(BlockState state, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
     BlockPos tilePos = p_220071_3_;
     if (state.get((Property)HALF) == DoubleBlockHalf.UPPER) {
       tilePos = p_220071_3_.func_177977_b();
    }
     TileEntity tile = p_220071_2_.getTileEntity(tilePos);
     if (tile instanceof DoubleDoorsTileEntity && (
       (DoubleDoorsTileEntity)tile).isOpen(TardisDoor.BOTH)) {
       return VoxelShapes.func_197880_a();
    }

    
     return super.func_220071_b(state, p_220071_2_, p_220071_3_, p_220071_4_);
  }

  
  public void func_176208_a(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
     if (!worldIn.isRemote) {
       if (state.get((Property)HALF) == DoubleBlockHalf.LOWER) {
         worldIn.func_175655_b(pos.func_177984_a(), false);
      } else {
         worldIn.func_175655_b(pos.func_177977_b(), false);
      } 
    }
    
     super.func_176208_a(worldIn, pos, state, player);
  }


  
  public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
     if (!world.isRemote) {
       if (state.get((Property)HALF) == DoubleBlockHalf.LOWER) {
         world.func_175655_b(pos.func_177984_a(), false);
      } else {
         world.func_175655_b(pos.func_177977_b(), false);
      } 
    }
    
     super.onBlockExploded(state, world, pos, explosion);
  }
  
  public boolean func_196266_a(BlockState state, IBlockReader reader, BlockPos pos, PathType p_196266_4_) {
     if (reader.getTileEntity(pos) instanceof PoliceBoxDoorsTileEntity) {
       PoliceBoxDoorsTileEntity door = (PoliceBoxDoorsTileEntity)reader.getTileEntity(pos);
       if (door.isOpen(TardisDoor.BOTH)) return true; 
    } 
     return false;
  }
}


