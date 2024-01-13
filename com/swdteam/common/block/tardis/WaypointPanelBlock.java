package com.swdteam.common.block.tardis;
import com.swdteam.common.block.IBlockTooltip;
import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMItems;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tileentity.tardis.WaypointPanelTileEntity;
import com.swdteam.util.ChatUtil;
import java.util.Random;
import java.util.function.Supplier;
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
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WaypointPanelBlock extends RotatableTileEntityBase.WaterLoggable implements IBlockTooltip {
   public static final VoxelShape PANEL_SHAPE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
   protected static final VoxelShape SHAPE = VoxelShapes.func_216384_a(PANEL_SHAPE, new VoxelShape[0]);
  
   public static final BooleanProperty BUTTON_PRESSED = BooleanProperty.func_177716_a("btn_pressed");
   public static final IntegerProperty CARTRIDGE_TYPE = IntegerProperty.func_177719_a("cartridge", 0, 2);
  private StringTextComponent LOAD_CARTRIDGE;
  private StringTextComponent EJECT_CARTRIDGE;
   private StringTextComponent APPLY_WAYPOINT; public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) { if (!worldIn.isRemote && handIn == Hand.MAIN_HAND && worldIn.getDimensionKey().equals(DMDimensions.TARDIS)) { int stateID = ((Integer)state.get((Property)CARTRIDGE_TYPE)).intValue(); ItemStack itemstack = player.getHeldItem(handIn); if (itemstack.getItem() != null) { Item item = itemstack.getItem(); if ((item == DMItems.DATA_MODULE.get() || item == DMItems.DATA_MODULE_GOLD.get()) && stateID == 0) { int newState = 1; if (item == DMItems.DATA_MODULE_GOLD.get()) newState = 2;  worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)CARTRIDGE_TYPE, Integer.valueOf(newState))); worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_MODULE_INSERT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F); TileEntity te = worldIn.getTileEntity(pos); if (te != null && te instanceof WaypointPanelTileEntity) { WaypointPanelTileEntity tet = (WaypointPanelTileEntity)te; tet.cartridge = itemstack.func_77979_a(1); }  } else if (stateID > 0) { if (player.func_225608_bj_()) { TileEntity te = worldIn.getTileEntity(pos); if (te != null && te instanceof WaypointPanelTileEntity) { WaypointPanelTileEntity tet = (WaypointPanelTileEntity)te; eject(state, worldIn, pos, tet); worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_MODULE_REMOVE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F); }  } else { worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Boolean.valueOf(true))); worldIn.func_205220_G_().func_205360_a(pos, this, 10); worldIn.playSound(null, pos, SoundEvents.field_187837_fU, SoundCategory.BLOCKS, 0.3F, 0.7F); TileEntity te = worldIn.getTileEntity(pos); if (te != null && te instanceof WaypointPanelTileEntity) { WaypointPanelTileEntity tet = (WaypointPanelTileEntity)te; if (tet.cartridge != null && tet.cartridge.getItem() instanceof com.swdteam.common.item.DataModuleItem) { ItemStack stack = tet.cartridge; if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("location")) { ListNBT list = stack.func_77978_p().func_150295_c("location", 10); CompoundNBT tag = list.func_150305_b(0); TardisData data = DMTardis.getTardisFromInteriorPos(pos); TardisFlightPool.updateFlight(data, tag.getInt("pos_x"), tag.getInt("pos_y"), tag.getInt("pos_z"), RegistryKey.func_240903_a_(Registry.field_239699_ae_, new ResourceLocation(tag.func_74779_i("location")))); TardisFlightPool.getFlightData(data).setRotationAngle(tag.func_74760_g("facing")); TardisFlightPool.getFlightData(data).setWaypoints(list); ChatUtil.sendCompletedMsg(player, (IFormattableTextComponent)DMTranslationKeys.TARDIS_WAYPOINT_APPLIED, ChatUtil.MessageType.STATUS_BAR); } else { ChatUtil.sendError(player, "This Data Module is blank...", ChatUtil.MessageType.STATUS_BAR); }  }  }  }  }  }  }  return ActionResultType.CONSUME; } public void func_196243_a(BlockState state, World world, BlockPos pos, BlockState newState, boolean willHarvest) { if (!world.isRemote) { TileEntity te = world.getTileEntity(pos); if (state.getBlock() == newState.getBlock()) return;  if (te != null && te instanceof WaypointPanelTileEntity) { WaypointPanelTileEntity tet = (WaypointPanelTileEntity)te; eject(state, world, pos, tet, true); }  }  super.func_196243_a(state, world, pos, newState, willHarvest); } public static void eject(BlockState state, World worldIn, BlockPos pos, WaypointPanelTileEntity tet) { eject(state, worldIn, pos, tet, false); } public WaypointPanelBlock(AbstractBlock.Properties properties) { super(WaypointPanelTileEntity::new, properties.func_226896_b_());















































































































































    
     this.LOAD_CARTRIDGE = new StringTextComponent("Load Data Module");
     this.EJECT_CARTRIDGE = new StringTextComponent("Eject Data Module");
     this.APPLY_WAYPOINT = new StringTextComponent("Apply Waypoint"); func_180632_j((BlockState)((BlockState)getDefaultState().func_206870_a((Property)BUTTON_PRESSED, Boolean.valueOf(false))).func_206870_a((Property)CARTRIDGE_TYPE, Integer.valueOf(0))); }
  public static void eject(BlockState state, World worldIn, BlockPos pos, WaypointPanelTileEntity tet, boolean beingDestroyed) { if (tet.cartridge != null && tet.cartridge.getItem() instanceof com.swdteam.common.item.DataModuleItem) { Direction dir = (Direction)state.get((Property)FACING); ItemEntity itemEnt = new ItemEntity(EntityType.ITEM, worldIn); itemEnt.func_70080_a((pos.getX() + 0.5F + dir.func_82601_c()), pos.getY(), (pos.getZ() + 0.5F + dir.func_82599_e()), 0.0F, 0.0F); itemEnt.setMotion((dir.func_82601_c() / 10.0F), 0.0D, (dir.func_82599_e() / 10.0F)); itemEnt.setItem(tet.cartridge); worldIn.addEntity((Entity)itemEnt); tet.cartridge = ItemStack.field_190927_a; if (!beingDestroyed)
        worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)CARTRIDGE_TYPE, Integer.valueOf(0)));  }
     }
   public static IPosition getPosition(Direction direction, BlockPos pos) { double d0 = pos.getX() + 0.5D * direction.func_82601_c(); double d1 = pos.getY() + 0.5D * direction.func_96559_d(); double d2 = pos.getZ() + 0.5D * direction.func_82599_e(); return (IPosition)new Position(d0, d1, d2); } public ITextComponent getName(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) { boolean hasCartridge = (((Integer)state.get((Property)CARTRIDGE_TYPE)).intValue() != 0);
    
     if (hasCartridge) {
       if (player.func_225608_bj_()) {
         return (ITextComponent)this.EJECT_CARTRIDGE;
      }
       return (ITextComponent)this.APPLY_WAYPOINT;
    } 
    
     return (ITextComponent)this.LOAD_CARTRIDGE; } public BlockState func_196258_a(BlockItemUseContext context) { return (BlockState)((BlockState)super.func_196258_a(context).func_206870_a((Property)BUTTON_PRESSED, Boolean.valueOf(false))).func_206870_a((Property)CARTRIDGE_TYPE, Integer.valueOf(0)); }
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) { super.func_206840_a(builder);
    builder.func_206894_a(new Property[] { (Property)BUTTON_PRESSED, (Property)CARTRIDGE_TYPE }); }
  public BlockRenderType func_149645_b(BlockState p_149645_1_) { return BlockRenderType.MODEL; }
  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) { return SHAPE; }
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) { return SHAPE; }
  public void func_225534_a_(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
     worldIn.playSound(null, pos, SoundEvents.field_187837_fU, SoundCategory.BLOCKS, 0.3F, 0.5F);
     worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Boolean.valueOf(false)));
     super.func_225534_a_(state, worldIn, pos, rand);
  }

  
  public boolean isButtonPressed(BlockPos pos, Vector3d hitVec, Direction facing) {
     float mouseX = (int)(100.0F * (float)(hitVec.func_82615_a() - pos.getX())) / 100.0F;
     float mouseY = (int)(100.0F * (float)(hitVec.func_82617_b() - pos.getY())) / 100.0F;
     float mouseZ = (int)(100.0F * (float)(hitVec.func_82616_c() - pos.getZ())) / 100.0F;
    
     if (facing == Direction.EAST) {
       if (mouseY >= 0.05F && mouseY < 0.17F && mouseZ <= 0.31F && mouseZ > 0.185F) {
         return true;
      }
     } else if (facing == Direction.NORTH) {
       if (mouseY >= 0.05F && mouseY < 0.17F && mouseX <= 0.31F && mouseX > 0.185F) {
         return true;
      }
     } else if (facing == Direction.WEST) {
       if (mouseY >= 0.05F && mouseY < 0.17F && mouseZ <= 0.79F && mouseZ > 0.69F) {
         return true;
      }
     } else if (facing == Direction.SOUTH && 
       mouseY >= 0.05F && mouseY < 0.17F && mouseX <= 0.79F && mouseX > 0.69F) {
       return true;
    } 

    
     return false;
  }
  
  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }
  
  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }
}


