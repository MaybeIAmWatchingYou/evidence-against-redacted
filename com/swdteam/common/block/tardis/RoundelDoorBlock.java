package com.swdteam.common.block.tardis;

import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMFlightMode;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisDoor;
import com.swdteam.common.tileentity.tardis.RoundelDoorTileEntity;
import com.swdteam.util.ChatUtil;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;




public class RoundelDoorBlock
  extends RotatableTileEntityBase.WaterLoggable
{
   public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.field_208142_aq;
   public static final IntegerProperty DOOR_PART = IntegerProperty.func_177719_a("door_part", 0, 2);
  
  public RoundelDoorBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
     func_180632_j((BlockState)((BlockState)getDefaultState().func_206870_a((Property)HINGE, (Comparable)DoorHingeSide.LEFT)).func_206870_a((Property)DOOR_PART, Integer.valueOf(0)));
  }
  
  @Nullable
  public BlockState func_196258_a(BlockItemUseContext context) {
     BlockPos blockpos = context.func_195995_a();
     if (blockpos.getY() < 255 && context.func_195991_k().getBlockState(blockpos.func_177984_a()).func_196953_a(context)) {
       return (BlockState)super.func_196258_a(context).func_206870_a((Property)HINGE, (Comparable)getHinge(context, context.func_195992_f()));
    }
     return null;
  }

  
  private DoorHingeSide getHinge(BlockItemUseContext context, Direction facing) {
     Vector3d pos = context.func_221532_j();
     double x = pos.x - context.func_195995_a().getX();
     double z = pos.z - context.func_195995_a().getZ();
     switch (facing) { case NORTH:
         return (x < 0.5D) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
       case EAST: return (z < 0.5D) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
       case SOUTH: return (x > 0.5D) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
       case WEST: return (z > 0.5D) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT; }
      return DoorHingeSide.LEFT;
  }


  
  public VoxelShape func_220071_b(BlockState state, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
     TileEntity tile = p_220071_2_.getTileEntity(p_220071_3_);
     if (tile instanceof RoundelDoorTileEntity && (
       (RoundelDoorTileEntity)tile).isOpen()) {
       return VoxelShapes.func_197880_a();
    }
    
     return super.func_220071_b(state, p_220071_2_, p_220071_3_, p_220071_4_);
  }
  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     super.func_206840_a(builder);
     builder.func_206894_a(new Property[] { (Property)HINGE, (Property)DOOR_PART });
  }

  
  public boolean func_196260_a(BlockState state, IWorldReader worldIn, BlockPos pos) {
     BlockPos blockpos = pos.func_177984_a();
     BlockState blockstate = worldIn.getBlockState(blockpos);
     BlockState blockstate2 = worldIn.getBlockState(blockpos.func_177984_a());
     return (!blockstate.func_224755_d((IBlockReader)worldIn, blockpos, Direction.DOWN) && !blockstate2.func_224755_d((IBlockReader)worldIn, blockpos.func_177984_a(), Direction.DOWN));
  }
  
  public void func_180633_a(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
     RoundelDoorTileEntity doorBase = (RoundelDoorTileEntity)worldIn.getTileEntity(pos);
    
     if (stack.func_77942_o() && 
       stack.func_77978_p().func_74764_b("BlockEntityTag")) {
       CompoundNBT tag = stack.func_77978_p().func_74775_l("BlockEntityTag");
       if (tag.func_74764_b("blockstate")) {
         CompoundNBT tag2 = tag.func_74775_l("blockstate");
         if (tag2.func_74764_b("Name")) {
           String name = tag2.func_74779_i("Name");
           Block b = (Block)Registry.field_212618_g.getOrDefault(new ResourceLocation(name.toLowerCase()));
           if (b != null) {
             doorBase.setBlock(b.getDefaultState());
          }
        } 
      } 
       if (tag.func_74764_b("blockstate_top")) {
         CompoundNBT tag2 = tag.func_74775_l("blockstate_top");
         if (tag2.func_74764_b("Name")) {
           String name = tag2.func_74779_i("Name");
           Block b = (Block)Registry.field_212618_g.getOrDefault(new ResourceLocation(name.toLowerCase()));
           if (b != null) {
             doorBase.setBlockSidesTop(b.getDefaultState());
          }
        } 
      } 
    } 

    
     FluidState fluidstate1 = worldIn.func_204610_c(pos.func_177984_a());
     FluidState fluidstate2 = worldIn.func_204610_c(pos.func_177984_a().func_177984_a());
    
     worldIn.setBlockState(pos.func_177984_a(), (BlockState)((BlockState)state.func_206870_a((Property)WATERLOGGED, Boolean.valueOf((fluidstate1.func_206886_c() == Fluids.field_204546_a)))).func_206870_a((Property)DOOR_PART, Integer.valueOf(1)), 3);
     worldIn.setBlockState(pos.func_177984_a().func_177984_a(), (BlockState)((BlockState)state.func_206870_a((Property)WATERLOGGED, Boolean.valueOf((fluidstate2.func_206886_c() == Fluids.field_204546_a)))).func_206870_a((Property)DOOR_PART, Integer.valueOf(2)), 3);
    
     ((RoundelDoorTileEntity)worldIn.getTileEntity(pos.func_177984_a())).setBlock(doorBase.getStateToRender());
     ((RoundelDoorTileEntity)worldIn.getTileEntity(pos.func_177984_a().func_177984_a())).setBlock(doorBase.getStateToRender());
    
     ((RoundelDoorTileEntity)worldIn.getTileEntity(pos.func_177984_a())).setBlockSidesTop(doorBase.getStateToRenderTop());
     ((RoundelDoorTileEntity)worldIn.getTileEntity(pos.func_177984_a().func_177984_a())).setBlockSidesTop(doorBase.getStateToRenderTop());
  }

  
  public void func_176208_a(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
     if (!worldIn.isRemote) {
       if (((Integer)state.get((Property)DOOR_PART)).intValue() == 0) {
         worldIn.func_175655_b(pos.func_177984_a(), false);
         worldIn.func_175655_b(pos.func_177984_a().func_177984_a(), false);
       } else if (((Integer)state.get((Property)DOOR_PART)).intValue() == 1) {
         worldIn.func_175655_b(pos.func_177984_a(), false);
         worldIn.func_175655_b(pos.func_177977_b(), false);
      } else {
         worldIn.func_175655_b(pos.func_177977_b(), false);
         worldIn.func_175655_b(pos.func_177977_b().func_177977_b(), false);
      } 
    }
     super.func_176208_a(worldIn, pos, state, player);
  }


  
  public void onBlockExploded(BlockState state, World worldIn, BlockPos pos, Explosion explosion) {
     if (!worldIn.isRemote) {
       if (((Integer)state.get((Property)DOOR_PART)).intValue() == 0) {
         worldIn.func_175655_b(pos.func_177984_a(), false);
         worldIn.func_175655_b(pos.func_177984_a().func_177984_a(), false);
       } else if (((Integer)state.get((Property)DOOR_PART)).intValue() == 1) {
         worldIn.func_175655_b(pos.func_177984_a(), false);
         worldIn.func_175655_b(pos.func_177977_b(), false);
      } else {
         worldIn.func_175655_b(pos.func_177977_b(), false);
         worldIn.func_175655_b(pos.func_177977_b().func_177977_b(), false);
      } 
    }
     super.onBlockExploded(state, worldIn, pos, explosion);
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
               BlockPos baseDoorPos = (((Integer)state.get((Property)DOOR_PART)).intValue() == 0) ? pos : ((((Integer)state.get((Property)DOOR_PART)).intValue() == 1) ? pos.func_177977_b() : pos.func_177977_b().func_177977_b());
              
               boolean doorCheck = ((RoundelDoorTileEntity)worldIn.getTileEntity(pos)).isMainDoor();
              
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
             TardisDoor door = (state.get((Property)HINGE) == DoorHingeSide.LEFT) ? TardisDoor.LEFT : TardisDoor.RIGHT;
             int doorPart = ((Integer)state.get((Property)DOOR_PART)).intValue();
             doDoorBit(worldIn, tilePos, door, ignoreDoorSync);
            
             if (doorPart == 0) {
               doDoorBit(worldIn, tilePos.func_177984_a(), door, ignoreDoorSync);
               doDoorBit(worldIn, tilePos.func_177984_a().func_177984_a(), door, ignoreDoorSync);
             } else if (doorPart == 1) {
               doDoorBit(worldIn, tilePos.func_177984_a(), door, ignoreDoorSync);
               doDoorBit(worldIn, tilePos.func_177977_b(), door, ignoreDoorSync);
            } else {
               doDoorBit(worldIn, tilePos.func_177977_b(), door, ignoreDoorSync);
               doDoorBit(worldIn, tilePos.func_177977_b().func_177977_b(), door, ignoreDoorSync);
            } 
          } 
        } 
      } else {
        
         return ActionResultType.PASS;
      } 
    }
     return ActionResultType.CONSUME;
  }
  
  public void doDoorBit(World world, BlockPos pos, TardisDoor door, boolean doorSync) {
     TileEntity tile = world.getTileEntity(pos);
     if (tile instanceof RoundelDoorTileEntity) {
       RoundelDoorTileEntity doors = (RoundelDoorTileEntity)tile;
      
       if (!doors.isOpen()) {
         doors.setOpen(true, doorSync);
         if (!world.isRemote) {
           world.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_ROUNDEL_DOOR_OPEN.get(), SoundCategory.BLOCKS, 0.25F, 1.0F);
        }
      } else {
         doors.setOpen(!doors.isOpen(), doorSync);
         if (!world.isRemote) {
           world.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_ROUNDEL_DOOR_CLOSE.get(), SoundCategory.BLOCKS, 0.25F, 1.0F);
        }
      } 
    } 
  }

  
  public void func_190948_a(ItemStack stack, IBlockReader reader, List<ITextComponent> list, ITooltipFlag tooltipFlag) {
     boolean flag = false;
     if (stack.func_77942_o() && 
       stack.func_77978_p().func_74764_b("BlockEntityTag")) {
       CompoundNBT tag = stack.func_77978_p().func_74775_l("BlockEntityTag");
       if (tag.func_74764_b("blockstate")) {
         CompoundNBT tag2 = tag.func_74775_l("blockstate");
         if (tag2.func_74764_b("Name")) {
           list.add((new StringTextComponent("Roundel Type:")).func_240699_a_(TextFormatting.GRAY));
           String name = tag2.func_74779_i("Name");
           Block b = (Block)Registry.field_212618_g.getOrDefault(new ResourceLocation(name.toLowerCase()));
           if (b != null) {
             name = b.func_235333_g_().getString();
          }
           list.add(new StringTextComponent(TextFormatting.GREEN + name));
           flag = true;
        } 
      } 
    } 

    
     if (!flag) {
       list.add((new StringTextComponent("Roundel Type:")).func_240699_a_(TextFormatting.GRAY));
       list.add(new StringTextComponent(TextFormatting.GREEN + ((Block)DMBlocks.BLACK_QUARTZ_ROUNDEL.get()).func_235333_g_().getString()));
    } 
     super.func_190948_a(stack, reader, list, tooltipFlag);
  }
  
  public boolean func_196266_a(BlockState state, IBlockReader reader, BlockPos pos, PathType p_196266_4_) {
     if (reader.getTileEntity(pos) instanceof RoundelDoorTileEntity) {
       RoundelDoorTileEntity door = (RoundelDoorTileEntity)reader.getTileEntity(pos);
       if (door.isOpen()) return true; 
    } 
     return false;
  }

  
  public void func_220082_b(BlockState state, World world, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
     super.func_220082_b(state, world, pos, p_220082_4_, p_220082_5_);
     ((RoundelDoorTileEntity)world.getTileEntity(pos)).forceDoorState(false);
  }
}


