package com.swdteam.common.block.tardis;

import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.block.IBlockTooltip;
import com.swdteam.common.block.IDMTile;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.tileentity.tardis.DataWriterTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.Position;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class DataWriterBlock
  extends AbstractRotateableWaterLoggableBlock
  implements IBlockTooltip, IDMTile
{
  public Supplier<TileEntity> tileEntitySupplier;
   protected static final VoxelShape SHAPE_NORTH = VoxelShapes.func_197872_a(Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 8.0D), Block.func_208617_a(0.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D));
   protected static final VoxelShape SHAPE_EAST = VoxelShapes.func_197872_a(Block.func_208617_a(8.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.func_208617_a(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 16.0D));
   protected static final VoxelShape SHAPE_SOUTH = VoxelShapes.func_197872_a(Block.func_208617_a(0.0D, 0.0D, 8.0D, 16.0D, 2.0D, 16.0D), Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D));
   protected static final VoxelShape SHAPE_WEST = VoxelShapes.func_197872_a(Block.func_208617_a(0.0D, 0.0D, 0.0D, 8.0D, 2.0D, 16.0D), Block.func_208617_a(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D));
  
   public static final IntegerProperty CARTRIDGE_TYPE = IntegerProperty.func_177719_a("cartridge", 0, 2);
  private StringTextComponent LOAD_CARTRIDGE;
  private StringTextComponent EJECT_CARTRIDGE;
   private StringTextComponent VIEW_INTERFACE; public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) { if (!worldIn.isRemote && handIn == Hand.MAIN_HAND && worldIn.getDimensionKey().equals(DMDimensions.TARDIS)) { int stateID = ((Integer)state.get((Property)CARTRIDGE_TYPE)).intValue(); ItemStack itemstack = player.getHeldItem(handIn); if (itemstack.getItem() != null) { Item item = itemstack.getItem(); if ((item == DMItems.DATA_MODULE.get() || item == DMItems.DATA_MODULE_GOLD.get()) && stateID == 0) { int newState = 1; if (item == DMItems.DATA_MODULE_GOLD.get()) newState = 2;  worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)CARTRIDGE_TYPE, Integer.valueOf(newState))); worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_MODULE_INSERT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F); TileEntity te = worldIn.getTileEntity(pos); if (te != null && te instanceof DataWriterTileEntity) { DataWriterTileEntity tet = (DataWriterTileEntity)te; tet.cartridge = itemstack.func_77979_a(1); }  } else { if (stateID > 0 && player.func_225608_bj_()) { TileEntity te = worldIn.getTileEntity(pos); if (te != null && te instanceof DataWriterTileEntity) { DataWriterTileEntity tet = (DataWriterTileEntity)te; eject(state, worldIn, pos, tet); worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_MODULE_REMOVE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F); }  }  if (!player.func_225608_bj_()) NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 2));  }  }  }  return ActionResultType.CONSUME; } public void func_196243_a(BlockState state, World world, BlockPos pos, BlockState newState, boolean willHarvest) { if (!world.isRemote) { TileEntity te = world.getTileEntity(pos); if (te != null && te instanceof DataWriterTileEntity) { DataWriterTileEntity tet = (DataWriterTileEntity)te; eject(state, world, pos, tet, true); }  }  super.func_196243_a(state, world, pos, newState, willHarvest); } public static void eject(BlockState state, World worldIn, BlockPos pos, DataWriterTileEntity tet) { eject(state, worldIn, pos, tet, false); } public DataWriterBlock(AbstractBlock.Properties properties) { super(properties.func_226896_b_());





























































































































    
     this.LOAD_CARTRIDGE = new StringTextComponent("Load Data Module");
     this.EJECT_CARTRIDGE = new StringTextComponent("Eject Data Module");
     this.VIEW_INTERFACE = new StringTextComponent("View Interface"); func_180632_j((BlockState)getDefaultState().func_206870_a((Property)CARTRIDGE_TYPE, Integer.valueOf(0))); this.tileEntitySupplier = DataWriterTileEntity::new; }
  public static void eject(BlockState state, World worldIn, BlockPos pos, DataWriterTileEntity tet, boolean beingDestroyed) { if (tet.cartridge != null && tet.cartridge.getItem() instanceof com.swdteam.common.item.DataModuleItem) { Direction dir = (Direction)state.get((Property)FACING); ItemEntity itemEnt = new ItemEntity(EntityType.ITEM, worldIn); itemEnt.func_70080_a((pos.getX() + 0.5F + dir.func_82601_c()), pos.getY(), (pos.getZ() + 0.5F + dir.func_82599_e()), 0.0F, 0.0F); itemEnt.setMotion((dir.func_82601_c() / 10.0F), 0.0D, (dir.func_82599_e() / 10.0F)); itemEnt.setItem(tet.cartridge); worldIn.addEntity((Entity)itemEnt); tet.cartridge = ItemStack.field_190927_a; if (!beingDestroyed)
        worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)CARTRIDGE_TYPE, Integer.valueOf(0)));  }
     }
   public static IPosition getPosition(Direction direction, BlockPos pos) { double d0 = pos.getX() + 0.5D * direction.func_82601_c(); double d1 = pos.getY() + 0.5D * direction.func_96559_d(); double d2 = pos.getZ() + 0.5D * direction.func_82599_e(); return (IPosition)new Position(d0, d1, d2); } public ITextComponent getName(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) { boolean hasCartridge = (((Integer)state.get((Property)CARTRIDGE_TYPE)).intValue() != 0);
    
     if (hasCartridge) {
       if (player.func_225608_bj_()) {
         return (ITextComponent)this.EJECT_CARTRIDGE;
      }
       return (ITextComponent)this.VIEW_INTERFACE;
    } 
    
     return (ITextComponent)this.LOAD_CARTRIDGE; } public BlockState func_196258_a(BlockItemUseContext context) { return (BlockState)super.func_196258_a(context).func_206870_a((Property)CARTRIDGE_TYPE, Integer.valueOf(0)); }
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) { super.func_206840_a(builder); builder.func_206894_a(new Property[] { (Property)CARTRIDGE_TYPE }); }
  public BlockRenderType func_149645_b(BlockState p_149645_1_) { return BlockRenderType.MODEL; }
  public VoxelShape getShape(Direction direction) { switch (direction) { default: return SHAPE_NORTH;case EAST: return SHAPE_EAST;case SOUTH: return SHAPE_SOUTH;case WEST: break; }  return SHAPE_WEST; }
  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) { return getShape((Direction)state.get((Property)FACING)); }
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) { return getShape((Direction)state.get((Property)FACING)); }
   public Supplier<TileEntity> getTile() { return this.tileEntitySupplier; }


  
  public boolean hasTileEntity(BlockState state) {
     return true;
  }

  
  @Nullable
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
     return this.tileEntitySupplier.get();
  }
  
  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }
  
  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }
}


