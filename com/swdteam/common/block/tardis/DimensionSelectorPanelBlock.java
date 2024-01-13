package com.swdteam.common.block.tardis;

import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.block.IBlockTooltip;
import com.swdteam.common.block.IDMTile;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tardis.data.TardisLocationRegistry;
import com.swdteam.common.tileentity.tardis.DimensionSelectorTileEntity;
import com.swdteam.util.ChatUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
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
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DimensionSelectorPanelBlock
  extends AbstractRotateableWaterLoggableBlock
  implements IBlockTooltip, IDMTile {
   public static final IntegerProperty BUTTON_PRESSED = IntegerProperty.func_177719_a("button_pressed", 0, 3);
  
   public static final VoxelShape PANEL_SHAPE_BTN_RIGHT_N = Block.func_208617_a(11.0D, 2.0D, 12.0D, 15.0D, 3.0D, 15.0D);
   public static final VoxelShape PANEL_SHAPE_BTN_MIDDLE_N = Block.func_208617_a(6.0D, 2.0D, 12.0D, 10.0D, 3.0D, 15.0D);
   public static final VoxelShape PANEL_SHAPE_BTN_LEFT_N = Block.func_208617_a(1.0D, 2.0D, 12.0D, 5.0D, 3.0D, 15.0D);
   public static final VoxelShape PANEL_SHAPE_BASE = Block.func_208617_a(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
  
   public static final VoxelShape PANEL_SHAPE_BTN_RIGHT_S = Block.func_208617_a(1.0D, 2.0D, 1.0D, 5.0D, 3.0D, 4.0D);
   public static final VoxelShape PANEL_SHAPE_BTN_MIDDLE_S = Block.func_208617_a(6.0D, 2.0D, 1.0D, 10.0D, 3.0D, 4.0D);
   public static final VoxelShape PANEL_SHAPE_BTN_LEFT_S = Block.func_208617_a(11.0D, 2.0D, 1.0D, 15.0D, 3.0D, 4.0D);
  
   public static final VoxelShape PANEL_SHAPE_BTN_RIGHT_W = Block.func_208617_a(12.0D, 2.0D, 1.0D, 15.0D, 3.0D, 5.0D);
   public static final VoxelShape PANEL_SHAPE_BTN_MIDDLE_W = Block.func_208617_a(12.0D, 2.0D, 6.0D, 15.0D, 3.0D, 10.0D);
   public static final VoxelShape PANEL_SHAPE_BTN_LEFT_W = Block.func_208617_a(12.0D, 2.0D, 11.0D, 15.0D, 3.0D, 15.0D);
  
   public static final VoxelShape PANEL_SHAPE_BTN_RIGHT_E = Block.func_208617_a(1.0D, 2.0D, 11.0D, 4.0D, 3.0D, 15.0D);
   public static final VoxelShape PANEL_SHAPE_BTN_MIDDLE_E = Block.func_208617_a(1.0D, 2.0D, 6.0D, 4.0D, 3.0D, 10.0D);
   public static final VoxelShape PANEL_SHAPE_BTN_LEFT_E = Block.func_208617_a(1.0D, 2.0D, 1.0D, 4.0D, 3.0D, 5.0D);
  
   protected static final VoxelShape TEST = VoxelShapes.func_216384_a(PANEL_SHAPE_BASE, new VoxelShape[0]);
  
   protected static final VoxelShape SHAPE_SOUTH = VoxelShapes.func_216384_a(PANEL_SHAPE_BASE, new VoxelShape[] { PANEL_SHAPE_BTN_RIGHT_S, PANEL_SHAPE_BTN_MIDDLE_S, PANEL_SHAPE_BTN_LEFT_S });
   protected static final VoxelShape SHAPE_NORTH = VoxelShapes.func_216384_a(PANEL_SHAPE_BASE, new VoxelShape[] { PANEL_SHAPE_BTN_RIGHT_N, PANEL_SHAPE_BTN_MIDDLE_N, PANEL_SHAPE_BTN_LEFT_N });
   protected static final VoxelShape SHAPE_EAST = VoxelShapes.func_216384_a(PANEL_SHAPE_BASE, new VoxelShape[] { PANEL_SHAPE_BTN_RIGHT_E, PANEL_SHAPE_BTN_MIDDLE_E, PANEL_SHAPE_BTN_LEFT_E });
   protected static final VoxelShape SHAPE_WEST = VoxelShapes.func_216384_a(PANEL_SHAPE_BASE, new VoxelShape[] { PANEL_SHAPE_BTN_RIGHT_W, PANEL_SHAPE_BTN_MIDDLE_W, PANEL_SHAPE_BTN_LEFT_W });
  
  public Supplier<TileEntity> tileEntitySupplier;
   public static List<DimensionPanelButtons> buttons = new ArrayList<>();
  
  public DimensionSelectorPanelBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(properties);
     this.tileEntitySupplier = tileEntitySupplier;
     func_180632_j((BlockState)getDefaultState().func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(0)));
  }
  
  public BlockState func_196258_a(BlockItemUseContext context) {
     return (BlockState)super.func_196258_a(context).func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(0));
  }
  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     super.func_206840_a(builder);
     builder.func_206894_a(new Property[] { (Property)BUTTON_PRESSED });
  }

  
  public boolean hasTileEntity(BlockState state) {
     return true;
  }

  
  @Nullable
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
     return this.tileEntitySupplier.get();
  }

  
  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }
  
  public VoxelShape getShape(Direction direction) {
     switch (direction)
    { default:
         return SHAPE_NORTH;
       case BTN_SELECT: return SHAPE_EAST;
       case BTN_RIGHT: return SHAPE_SOUTH;
       case null: break; }  return SHAPE_WEST;
  }


  
  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return getShape((Direction)state.get((Property)FACING));
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return getShape((Direction)state.get((Property)FACING));
  }


  
  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (!worldIn.isRemote && 
       handIn == Hand.MAIN_HAND && worldIn.getTileEntity(pos) instanceof DimensionSelectorTileEntity && (
       (Integer)state.get((Property)BUTTON_PRESSED)).intValue() == 0) {
       Vector3d hitVec = hit.func_216347_e();
       float mouseX = (int)(100.0F * (float)(hitVec.func_82615_a() - pos.getX())) / 100.0F;
       float mouseZ = (int)(100.0F * (float)(hitVec.func_82616_c() - pos.getZ())) / 100.0F;
       DimensionSelectorTileEntity tet = (DimensionSelectorTileEntity)worldIn.getTileEntity(pos);
       DimensionPanelButtons buttonClicked = getButton(mouseX, mouseZ, (Direction)state.get((Property)FACING));
      
       switch (buttonClicked) {
        case BTN_LEFT:
           worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(1))); break;
         case BTN_SELECT: worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(2))); break;
         case BTN_RIGHT: worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(3)));
          break;
      } 
       if (buttonClicked != DimensionPanelButtons.EMPTY) {
         worldIn.func_205220_G_().func_205360_a(pos, this, 10);
         worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
      } 
      
       if (worldIn.getDimensionKey().equals(DMDimensions.TARDIS)) {
         TardisData data = DMTardis.getTardisFromInteriorPos(pos);
         if (data != null) {
           TardisFlightData flightData; switch (buttonClicked) {
            
            case BTN_LEFT:
               if (tet.getIndex() - 1 < 0) { tet.setIndex(TardisLocationRegistry.getLocationRegistry().size() - 1); break; }
                tet.setIndex(tet.getIndex() - 1); break;
            case BTN_RIGHT:
               tet.setIndex((tet.getIndex() + 1) % TardisLocationRegistry.getLocationRegistry().size()); break;
            case BTN_SELECT:
               flightData = TardisFlightPool.getFlightData(data);
               if (flightData != null) {
                 TardisLocationRegistry.TardisLocation location = TardisLocationRegistry.getLocationRegistryAsList().get(tet.getIndex());
                 if (location == null) { location = TardisLocationRegistry.getLocationRegistryAsList().get(0); break; }
                
                 flightData.setDimensionKey(location.getDimension());
                 flightData.setWaypoints(null);
                 TardisFlightPool.sendUpdates(data.getGlobalID());
                 ChatUtil.sendCompletedMsg(player, "TARDIS set for: " + location.getDimensionName().getString(), ChatUtil.MessageType.STATUS_BAR);
                 worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_DING.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
              } 
              break;
          } 

          
           if (buttonClicked == DimensionPanelButtons.BTN_LEFT || buttonClicked == DimensionPanelButtons.BTN_RIGHT) {
             TardisLocationRegistry.TardisLocation loc = TardisLocationRegistry.getLocationRegistryAsList().get(tet.getIndex());
             if (loc != null) {
               tet.setTexturePath(loc.getDimensionImage());
               ChatUtil.sendCompletedMsg(player, loc.getDimensionName().getString(), ChatUtil.MessageType.STATUS_BAR);
               tet.sendUpdates();
            } 
          } 
        } 
      } 
    } 


    
     return ActionResultType.CONSUME;
  }


  
  public ITextComponent getName(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) {
     String ss = "";
    
     double mouseX = hitVec.func_82615_a() - pos.getX();
     double mouseZ = hitVec.func_82616_c() - pos.getZ();
    
     StringTextComponent text = (getButton(mouseX, mouseZ, (Direction)state.get((Property)FACING))).displayName;
    
     return (ITextComponent)text;
  }
  
  public DimensionPanelButtons getButton(double mouseX, double mouseZ, Direction facing) {
     for (DimensionPanelButtons button : buttons) {
       if (button.values.containsKey(facing)) {
         Vector2f vec = button.values.get(facing);
        
         float width = button.width;
         float height = button.height;
         float x = vec.field_189982_i;
         float z = vec.field_189983_j;
        
         switch (facing) {





          
          case BTN_RIGHT:
             if (mouseX >= x && mouseZ >= z && mouseX <= (x + width) && mouseZ <= (z + height)) {
               return button;
            }
            break;
          case BTN_SELECT:
             if (mouseX >= x && mouseX <= (x + height) && mouseZ <= z && mouseZ >= (z - width)) {
               return button;
            }
            break;
          case null:
             if (mouseX <= x && mouseX >= (x - height) && mouseZ >= z && mouseZ <= (z + width)) {
               return button;
            }
            break;
        } 

      
      } 
    } 
     return DimensionPanelButtons.EMPTY;
  }
  
  public enum DimensionPanelButtons {
     BTN_LEFT("Previous Dimension", 4.0F, 3.0F, 1.0F, 1.0F),
     BTN_SELECT("Select Dimension", 4.0F, 3.0F, 6.0F, 1.0F),
     BTN_RIGHT("Next Dimension", 4.0F, 3.0F, 11.0F, 1.0F),
    
     EMPTY(null, 0.0F, 0.0F, 0.0F, 0.0F);
    
     Map<Direction, Vector2f> values = new HashMap<>();
    float width;
    float height;
    StringTextComponent displayName;
    
    DimensionPanelButtons(String s, float w, float h, float x1, float z1) {
       float f = 0.0625F;
       this.width = w * f;
       this.height = h * f;
       float x2 = 16.0F - x1;
       float z2 = 16.0F - z1;
       this.values.put(Direction.NORTH, new Vector2f(x2 * f, z2 * f));
       this.values.put(Direction.EAST, new Vector2f(z1 * f, x2 * f));
       this.values.put(Direction.SOUTH, new Vector2f(x1 * f, z1 * f));
       this.values.put(Direction.WEST, new Vector2f(z2 * f, x1 * f));
      
       DimensionSelectorPanelBlock.buttons.add(this);
       if (s != null) {
         this.displayName = new StringTextComponent(s);
      }
    }
  }

  
  public Supplier<TileEntity> getTile() {
     return this.tileEntitySupplier;
  }


  
  public void func_225534_a_(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
     worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_BUTTON_RELEASE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
     worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(0)));
     super.func_225534_a_(state, worldIn, pos, rand);
  }
  
  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }
  
  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }
}


