package com.swdteam.common.block;

import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.util.DoorPhase;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DeadlockDoorBlock extends Block {
   protected static final VoxelShape OPEN_NORTH = Block.func_208617_a(13.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape OPEN_NORTH_HINGE = Block.func_208617_a(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
   protected static final VoxelShape OPEN_SOUTH = Block.func_208617_a(3.0D, 0.0D, 0.0D, 0.0D, 16.0D, 1.0D);
   protected static final VoxelShape OPEN_SOUTH_HINGE = Block.func_208617_a(3.0D, 0.0D, 15.0D, 0.0D, 16.0D, 16.0D);
   protected static final VoxelShape OPEN_EAST = Block.func_208617_a(0.0D, 0.0D, 13.0D, 1.0D, 16.0D, 16.0D);
   protected static final VoxelShape OPEN_EAST_HINGE = Block.func_208617_a(15.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape OPEN_WEST = Block.func_208617_a(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 0.0D);
   protected static final VoxelShape OPEN_WEST_HINGE = Block.func_208617_a(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 0.0D);
  
   protected static final VoxelShape CLOSED_NORTH = Block.func_208617_a(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape CLOSED_SOUTH = Block.func_208617_a(3.0D, 0.0D, 0.0D, 0.0D, 16.0D, 16.0D);
   protected static final VoxelShape CLOSED_EAST = Block.func_208617_a(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape CLOSED_WEST = Block.func_208617_a(0.0D, 0.0D, 3.0D, 16.0D, 16.0D, 0.0D);
  
   public static final EnumProperty<DoorPhase> DOOR_STATE = EnumProperty.func_177709_a("door_state", DoorPhase.class);
   public static final DirectionProperty FACING = HorizontalBlock.field_185512_D;
   public static final BooleanProperty POWERED = BlockStateProperties.field_208194_u;
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.field_208163_P;
   public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.field_208142_aq;
  
  public DeadlockDoorBlock(AbstractBlock.Properties properties) {
     super(properties);
     func_180632_j((BlockState)((BlockState)((BlockState)((BlockState)func_176194_O().func_177621_b())
         .func_206870_a((Property)FACING, (Comparable)Direction.NORTH))
         .func_206870_a((Property)HALF, (Comparable)DoubleBlockHalf.LOWER))
         .func_206870_a((Property)HINGE, (Comparable)DoorHingeSide.LEFT));
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     boolean hinge = (state.get((Property)HINGE) == DoorHingeSide.RIGHT);
     if (state.get((Property)DOOR_STATE) == DoorPhase.CLOSED) {
       switch ((Direction)state.get((Property)FACING)) { default:
           return CLOSED_NORTH;
         case WATER: return CLOSED_SOUTH;
         case AIR: return CLOSED_WEST;
         case null: break; }  return CLOSED_EAST;
    } 

    
     switch ((Direction)state.get((Property)FACING)) { default:
         return hinge ? OPEN_NORTH_HINGE : OPEN_NORTH;
       case WATER: return hinge ? OPEN_SOUTH_HINGE : OPEN_SOUTH;
       case AIR: return hinge ? OPEN_WEST_HINGE : OPEN_WEST;
       case null: break; }  return hinge ? OPEN_EAST_HINGE : OPEN_EAST;
  }


  
  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     if (state.get((Property)DOOR_STATE) != DoorPhase.CLOSED) return VoxelShapes.func_197880_a(); 
     return func_220053_a(state, worldIn, pos, context);
  }

  
  public boolean func_196266_a(BlockState state, IBlockReader block, BlockPos pos, PathType pathType) {
     switch (pathType) {
      case LAND:
         return (state.get((Property)DOOR_STATE) == DoorPhase.OPEN);
      case WATER:
         return false;
      case AIR:
         return (state.get((Property)DOOR_STATE) == DoorPhase.OPEN);
    } 
     return false;
  }

  
  public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
     return false;
  }
  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     builder.func_206894_a(new Property[] { (Property)FACING, (Property)DOOR_STATE, (Property)HALF, (Property)POWERED, (Property)HINGE });
  }

  
  public BlockState func_196271_a(BlockState statePlaced, Direction direction, BlockState stateOn, IWorld world, BlockPos posOn, BlockPos posPlaced) {
     DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)statePlaced.get((Property)HALF);
     if (direction.func_176740_k() == Direction.Axis.Y) if (((doubleblockhalf == DoubleBlockHalf.LOWER) ? true : false) == ((direction == Direction.UP) ? true : false)) {
         return (stateOn.func_203425_a(this) && stateOn.get((Property)HALF) != doubleblockhalf) ? (BlockState)((BlockState)((BlockState)statePlaced.func_206870_a((Property)FACING, stateOn.get((Property)FACING))).func_206870_a((Property)DOOR_STATE, stateOn.get((Property)DOOR_STATE))).func_206870_a((Property)POWERED, stateOn.get((Property)POWERED)) : Blocks.AIR.getDefaultState();
      } 
     return (doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !statePlaced.func_196955_c((IWorldReader)world, posOn)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(statePlaced, direction, stateOn, world, posOn, posPlaced);
  }

  
  public void updateDoor(World world, BlockState state, BlockPos pos) {
     BlockPos otherHalfPos = (state.get((Property)HALF) == DoubleBlockHalf.LOWER) ? pos.func_177984_a() : pos.func_177977_b();
     boolean powered = (((Boolean)state.get((Property)POWERED)).booleanValue() || ((Boolean)world.getBlockState(otherHalfPos).get((Property)POWERED)).booleanValue());
     SoundEvent sound = powered ? (SoundEvent)DMSoundEvents.BLOCK_DEADLOCK_DOOR_OPEN.get() : (SoundEvent)DMSoundEvents.BLOCK_DEADLOCK_DOOR_CLOSE.get();
     DoorPhase nextPhase = DoorPhase.MID;
     if (state.get((Property)DOOR_STATE) == DoorPhase.MID) nextPhase = powered ? DoorPhase.OPEN : DoorPhase.CLOSED;
    
     if (!world.isRemote && nextPhase == DoorPhase.MID) world.playSound(null, pos, sound, SoundCategory.BLOCKS, 0.5F, 1.0F);
    
     world.setBlockState(pos, (BlockState)state.func_206870_a((Property)DOOR_STATE, (Comparable)nextPhase));
     world.setBlockState(otherHalfPos, (BlockState)world.getBlockState(otherHalfPos).func_206870_a((Property)DOOR_STATE, (Comparable)nextPhase));
    
     world.func_205220_G_().func_205360_a(pos, this, 4);
     world.func_205220_G_().func_205360_a(otherHalfPos, this, 4);
  }

  
  public void func_220069_a(BlockState doorState, World world, BlockPos doorPos, Block neighborBlock, BlockPos neighborPos, boolean isMoving) {
     BlockPos otherHalfPos = (doorState.get((Property)HALF) == DoubleBlockHalf.LOWER) ? doorPos.func_177984_a() : doorPos.func_177977_b();
     boolean powered = (world.func_175640_z(doorPos) || world.func_175640_z(otherHalfPos));
     if (neighborBlock != this && powered != ((Boolean)doorState.get((Property)POWERED)).booleanValue()) {
       updateDoor(world, (BlockState)doorState.func_206870_a((Property)POWERED, Boolean.valueOf(powered)), doorPos);
    }
  }

  
  public void func_225534_a_(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
     BlockPos otherHalfPos = (state.get((Property)HALF) == DoubleBlockHalf.LOWER) ? pos.func_177984_a() : pos.func_177977_b();
     boolean powered = (((Boolean)state.get((Property)POWERED)).booleanValue() || ((Boolean)worldIn.getBlockState(otherHalfPos).get((Property)POWERED)).booleanValue());
     DoorPhase phase = (DoorPhase)state.get((Property)DOOR_STATE);
     if (phase == DoorPhase.MID || (phase != DoorPhase.MID && ((phase == DoorPhase.CLOSED && powered) || (phase == DoorPhase.OPEN && !powered))))
    {




      
       updateDoor((World)worldIn, state, pos); } 
  }
  
  public DoorHingeSide getHinge(BlockItemUseContext context) {
     World world = context.func_195991_k();
     BlockPos blockpos = context.func_195995_a();
     Direction direction = context.func_195992_f();
     BlockPos blockpos1 = blockpos.func_177984_a();
     Direction direction1 = direction.func_176735_f();
     BlockPos blockpos2 = blockpos.func_177972_a(direction1);
     BlockState blockstate = world.getBlockState(blockpos2);
     BlockPos blockpos3 = blockpos1.func_177972_a(direction1);
     BlockState blockstate1 = world.getBlockState(blockpos3);
     Direction direction2 = direction.func_176746_e();
     BlockPos blockpos4 = blockpos.func_177972_a(direction2);
     BlockState blockstate2 = world.getBlockState(blockpos4);
     BlockPos blockpos5 = blockpos1.func_177972_a(direction2);
     BlockState blockstate3 = world.getBlockState(blockpos5);
     int i = (blockstate.func_235785_r_((IBlockReader)world, blockpos2) ? -1 : 0) + (blockstate1.func_235785_r_((IBlockReader)world, blockpos3) ? -1 : 0) + (blockstate2.func_235785_r_((IBlockReader)world, blockpos4) ? 1 : 0) + (blockstate3.func_235785_r_((IBlockReader)world, blockpos5) ? 1 : 0);
     boolean flag = (blockstate.func_203425_a(this) && blockstate.get((Property)HALF) == DoubleBlockHalf.LOWER);
     boolean flag1 = (blockstate2.func_203425_a(this) && blockstate2.get((Property)HALF) == DoubleBlockHalf.LOWER);
     if ((!flag || flag1) && i <= 0) {
       if ((!flag1 || flag) && i >= 0) {
         int j = direction.func_82601_c();
         int k = direction.func_82599_e();
         Vector3d vector3d = context.func_221532_j();
         double d0 = vector3d.x - blockpos.getX();
         double d1 = vector3d.z - blockpos.getZ();
         return ((j >= 0 || d1 >= 0.5D) && (j <= 0 || d1 <= 0.5D) && (k >= 0 || d0 <= 0.5D) && (k <= 0 || d0 >= 0.5D)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
      } 
       return DoorHingeSide.LEFT;
    } 
    
     return DoorHingeSide.RIGHT;
  }

  
  public BlockState func_196258_a(BlockItemUseContext context) {
     Direction d = context.func_195992_f().func_176746_e();
     BlockPos blockpos = context.func_195995_a();
     if (blockpos.getY() < 255 && context.func_195991_k().getBlockState(blockpos.func_177984_a()).func_196953_a(context)) {
       World world = context.func_195991_k();
       boolean isPowered = (world.func_175640_z(blockpos) || world.func_175640_z(blockpos.func_177984_a()));
       return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)getDefaultState().func_206870_a((Property)FACING, (Comparable)d)).func_206870_a((Property)HALF, (Comparable)DoubleBlockHalf.LOWER)).func_206870_a((Property)HINGE, (Comparable)getHinge(context))).func_206870_a((Property)DOOR_STATE, isPowered ? (Comparable)DoorPhase.OPEN : (Comparable)DoorPhase.CLOSED)).func_206870_a((Property)POWERED, Boolean.valueOf(isPowered));
    } 
     return null;
  }


  
  public BlockState func_185499_a(BlockState state, Rotation rot) {
     return (BlockState)state.func_206870_a((Property)FACING, (Comparable)rot.func_185831_a((Direction)state.get((Property)FACING)));
  }

  
  public BlockRenderType func_149645_b(BlockState blockState) {
     return BlockRenderType.MODEL;
  }
  
  public boolean func_196260_a(BlockState state, IWorldReader worldIn, BlockPos pos) {
     BlockPos blockpos = pos.func_177977_b();
     BlockState blockstate = worldIn.getBlockState(blockpos);
    
     if (state.get((Property)HALF) == DoubleBlockHalf.LOWER) {
       return blockstate.func_224755_d((IBlockReader)worldIn, blockpos, Direction.UP);
    }
     return blockstate.func_203425_a(this);
  }


  
  public void func_180633_a(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
     worldIn.setBlockState(pos.func_177984_a(), (BlockState)state.func_206870_a((Property)HALF, (Comparable)DoubleBlockHalf.UPPER), 3);
  }

  
  public void func_176208_a(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
     if (!worldIn.isRemote && player.isCreative()) {
       DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)state.get((Property)HALF);
       if (doubleblockhalf == DoubleBlockHalf.UPPER) {
         BlockPos blockpos = pos.func_177977_b();
         BlockState blockstate = worldIn.getBlockState(blockpos);
         if (blockstate.getBlock() == state.getBlock() && blockstate.get((Property)HALF) == DoubleBlockHalf.LOWER) {
           worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 35);
           worldIn.func_217378_a(player, 2001, blockpos, Block.func_196246_j(blockstate));
        } 
      } 
    } 
    
     super.func_176208_a(worldIn, pos, state, player);
  }
  
  public PushReaction func_149656_h(BlockState p_149656_1_) {
     return PushReaction.DESTROY;
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
}


