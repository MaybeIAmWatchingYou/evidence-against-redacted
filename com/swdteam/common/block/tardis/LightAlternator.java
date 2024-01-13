package com.swdteam.common.block.tardis;

import com.swdteam.common.block.IBlockTooltip;
import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.LightAlternatorToggle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
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

public class LightAlternator
  extends RotatableTileEntityBase.WaterLoggable implements IBlockTooltip {
   protected static final VoxelShape SHAPE_NS = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.75D, 0.125D, 0.1875D, 0.9375D, 0.1875D, 0.375D), VoxelShapes.func_197873_a(0.75D, 0.125D, 0.625D, 0.9375D, 0.1875D, 0.8125D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.1875D, 0.25D, 0.1875D, 0.375D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.625D, 0.25D, 0.1875D, 0.8125D) });
   protected static final VoxelShape SHAPE_EW = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.1875D, 0.125D, 0.0625D, 0.375D, 0.1875D, 0.25D), VoxelShapes.func_197873_a(0.625D, 0.125D, 0.0625D, 0.8125D, 0.1875D, 0.25D), VoxelShapes.func_197873_a(0.1875D, 0.125D, 0.75D, 0.375D, 0.1875D, 0.9375D), VoxelShapes.func_197873_a(0.625D, 0.125D, 0.75D, 0.8125D, 0.1875D, 0.9375D) });
   public static final IntegerProperty BUTTON_PRESSED = IntegerProperty.func_177719_a("button_pressed", 0, 4);
   public static final EnumProperty<LightAlternatorToggle> SIDE_ACTIVATED = EnumProperty.func_177709_a("side_activated", LightAlternatorToggle.class);
  
   public static List<LightAlternatorButtons> buttons = new ArrayList<>();
  
  public Supplier<TileEntity> tileEntitySupplier;
  
   private Boolean didPressButton = Boolean.valueOf(false);
  
  public LightAlternator(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
     func_180632_j((BlockState)((BlockState)getDefaultState().func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(0))).func_206870_a((Property)SIDE_ACTIVATED, (Comparable)LightAlternatorToggle.RIGHT));
  }

  
  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }
  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     super.func_206840_a(builder);
     builder.func_206894_a(new Property[] { (Property)BUTTON_PRESSED, (Property)SIDE_ACTIVATED });
  }

  
  public BlockState func_196258_a(BlockItemUseContext context) {
     if (!(context.func_195991_k()).isRemote && 
       context.func_195991_k().getDimensionKey().equals(DMDimensions.TARDIS)) {
       TardisData data = DMTardis.getTardisFromInteriorPos(context.func_195995_a());
       return (BlockState)super.func_196258_a(context).func_206870_a((Property)SIDE_ACTIVATED, (Comparable)(data.getLighting()).selected);
    } 
    
     return super.func_196258_a(context);
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
     Direction facing = (Direction)state.get((Property)FACING);
     switch (facing) { default:
         return SHAPE_NS;
       case SUB_L: case SUB_R: break; }  return SHAPE_EW;
  }


  
  public void func_225534_a_(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
     if (this.didPressButton.booleanValue()) worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_BUTTON_RELEASE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F); 
     this.didPressButton = Boolean.valueOf(false);
    
     if (worldIn.func_201672_e().getDimensionKey().equals(DMDimensions.TARDIS)) {
       TardisData data = DMTardis.getTardisFromInteriorPos(pos);
       worldIn.setBlockState(pos, (BlockState)((BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(0))).func_206870_a((Property)SIDE_ACTIVATED, (Comparable)(data.getLighting()).selected));
    } else {
       worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(0)));
    } 
    
     super.func_225534_a_(state, worldIn, pos, rand);
  }
  
  private String formatIncrementMessage(boolean positive, LightAlternatorToggle toggle, TileEntity tile) {
     TardisData data = DMTardis.getTardisFromInteriorPos(tile.getPos());
     String adding = positive ? "Added to " : "Subtracted from ";
     return adding + toggle.toString() + " (" + (int)data.getLighting(toggle) + "%)";
  }

  
  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
       worldIn.func_205220_G_().func_205360_a(pos, this, 5);
       if (((Integer)state.get((Property)BUTTON_PRESSED)).intValue() == 0) {
         Vector3d hitVec = hit.func_216347_e();
         double mouseX = hitVec.func_82615_a() - pos.getX();
         double mouseZ = hitVec.func_82616_c() - pos.getZ();
        
         LightAlternatorButtons buttonClicked = getButton(mouseX, mouseZ, (Direction)state.get((Property)FACING));
         if (worldIn.getDimensionKey().equals(DMDimensions.TARDIS)) {
           TardisData data = DMTardis.getTardisFromInteriorPos(pos);
          
           TileEntity tet = worldIn.getTileEntity(pos);
           if (data != null) {
             switch (buttonClicked) {
              case ADD_L:
                 (data.getLighting()).left += 10;
                 if ((data.getLighting()).left > 100) (data.getLighting()).left = 100; 
                 ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(true, LightAlternatorToggle.LEFT, tet), ChatUtil.MessageType.STATUS_BAR);
                break;
              
              case ADD_R:
                 (data.getLighting()).right += 10;
                 if ((data.getLighting()).right > 100) (data.getLighting()).right = 100; 
                 ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(true, LightAlternatorToggle.RIGHT, tet), ChatUtil.MessageType.STATUS_BAR);
                break;
              
              case SUB_L:
                 (data.getLighting()).left -= 10;
                 if ((data.getLighting()).left < 0) (data.getLighting()).left = 0; 
                 ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(true, LightAlternatorToggle.LEFT, tet), ChatUtil.MessageType.STATUS_BAR);
                break;
              
              case SUB_R:
                 (data.getLighting()).right -= 10;
                 if ((data.getLighting()).right < 0) (data.getLighting()).right = 0; 
                 ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(true, LightAlternatorToggle.RIGHT, tet), ChatUtil.MessageType.STATUS_BAR);
                break;
              
              case SWAP:
                 (data.getLighting()).selected = (data.getLighting()).selected.invert();
                 ChatUtil.sendMessageToPlayer(player, "Alternated Lighting", ChatUtil.MessageType.STATUS_BAR);
                break;
            } 


            
             if (buttonClicked != LightAlternatorButtons.EMPTY) {
               data.setTardisLighting(tet.getWorld().getServer());
               TardisFlightPool.sendUpdates(data.getGlobalID());
            } 
          } 
        } 
        
         if (buttonClicked != LightAlternatorButtons.EMPTY) {
           if (buttonClicked != LightAlternatorButtons.SWAP) {
             worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(buttonClicked.ordinal() + 1)));
             this.didPressButton = Boolean.valueOf(true);
          } else {
            
             worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)SIDE_ACTIVATED, (Comparable)((LightAlternatorToggle)state.get((Property)SIDE_ACTIVATED)).invert()));
          } 
           worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
        } 
      } 
    } 
    
     return ActionResultType.CONSUME;
  }


  
  public ITextComponent getName(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) {
     double mouseX = hitVec.func_82615_a() - pos.getX();
     double mouseZ = hitVec.func_82616_c() - pos.getZ();
    
     StringTextComponent text = (getButton(mouseX, mouseZ, (Direction)state.get((Property)FACING))).displayName;
    
     return (ITextComponent)text;
  }
  
  public LightAlternatorButtons getButton(double mouseX, double mouseZ, Direction facing) {
     for (LightAlternatorButtons button : buttons) {
       if (button.values.containsKey(facing)) {
         Vector2f vec = button.values.get(facing);
        
         float width = button.width;
         float height = button.height;
         float x = vec.field_189982_i;
         float z = vec.field_189983_j;
        
         switch (facing) {





          
          case ADD_R:
             if (mouseX >= x && mouseZ >= z && mouseX <= (x + width) && mouseZ <= (z + height)) {
               return button;
            }
            break;
          case SUB_L:
             if (mouseX >= x && mouseX <= (x + height) && mouseZ <= z && mouseZ >= (z - width)) {
               return button;
            }
            break;
          case SUB_R:
             if (mouseX <= x && mouseX >= (x - height) && mouseZ >= z && mouseZ <= (z + width)) {
               return button;
            }
            break;
        } 


      
      } 
    } 
     return LightAlternatorButtons.EMPTY;
  }
  
  public enum LightAlternatorButtons {
     SUB_L("Subtract Left", 3.0F, 3.0F, 1.0F, 10.0F),
     SUB_R("Subtract Right", 3.0F, 3.0F, 12.0F, 10.0F),
    
     ADD_L("Add Left", 3.0F, 3.0F, 1.0F, 3.0F),
     ADD_R("Add Right", 3.0F, 3.0F, 12.0F, 3.0F),
    
     SWAP("Swap", 6.0F, 6.0F, 5.0F, 5.0F),
    
     EMPTY(null, 0.0F, 0.0F, 0.0F, 0.0F);
    
     Map<Direction, Vector2f> values = new HashMap<>();
    float width;
    float height;
    StringTextComponent displayName;
    
    LightAlternatorButtons(String s, float w, float h, float x1, float z1) {
       float f = 0.0625F;
       this.width = w * f;
       this.height = h * f;
       float x2 = 16.0F - x1;
       float z2 = 16.0F - z1;
       this.values.put(Direction.NORTH, new Vector2f(x2 * f, z2 * f));
       this.values.put(Direction.EAST, new Vector2f(z1 * f, x2 * f));
       this.values.put(Direction.SOUTH, new Vector2f(x1 * f, z1 * f));
       this.values.put(Direction.WEST, new Vector2f(z2 * f, x1 * f));
      
       LightAlternator.buttons.add(this);
       if (s != null) {
         this.displayName = new StringTextComponent(s);
      }
    }
  }
  
  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }
  
  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }
}


