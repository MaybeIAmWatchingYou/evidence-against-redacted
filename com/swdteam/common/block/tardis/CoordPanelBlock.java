package com.swdteam.common.block.tardis;

import com.swdteam.common.block.IBlockTooltip;
import com.swdteam.common.block.RotatableTileEntityBase;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import com.swdteam.common.tardis.data.TardisFlightPool;
import com.swdteam.common.tileentity.tardis.CoordPanelTileEntity;
import com.swdteam.util.ChatUtil;
import com.swdteam.util.SWDMathUtils;
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
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
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

public class CoordPanelBlock
  extends RotatableTileEntityBase.WaterLoggable
  implements IBlockTooltip {
   protected static final VoxelShape SHAPE_N = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.75D, 0.125D, 0.4375D, 0.9375D, 0.1875D, 0.5625D), VoxelShapes.func_197873_a(0.75D, 0.125D, 0.25D, 0.9375D, 0.1875D, 0.375D), VoxelShapes.func_197873_a(0.75D, 0.125D, 0.0625D, 0.9375D, 0.1875D, 0.1875D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.4375D, 0.25D, 0.1875D, 0.5625D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.25D, 0.25D, 0.1875D, 0.375D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.0625D, 0.25D, 0.1875D, 0.1875D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.75D, 0.1875D, 0.4375D, 0.875D), VoxelShapes.func_197873_a(0.625D, 0.125D, 0.625D, 0.9375D, 0.25D, 0.9375D), VoxelShapes.func_197873_a(0.25D, 0.125D, 0.625D, 0.5625D, 0.25D, 0.9375D) });
   protected static final VoxelShape SHAPE_E = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.4375D, 0.125D, 0.75D, 0.5625D, 0.1875D, 0.9375D), VoxelShapes.func_197873_a(0.625D, 0.125D, 0.75D, 0.75D, 0.1875D, 0.9375D), VoxelShapes.func_197873_a(0.8125D, 0.125D, 0.75D, 0.9375D, 0.1875D, 0.9375D), VoxelShapes.func_197873_a(0.4375D, 0.125D, 0.0625D, 0.5625D, 0.1875D, 0.25D), VoxelShapes.func_197873_a(0.625D, 0.125D, 0.0625D, 0.75D, 0.1875D, 0.25D), VoxelShapes.func_197873_a(0.8125D, 0.125D, 0.0625D, 0.9375D, 0.1875D, 0.25D), VoxelShapes.func_197873_a(0.125D, 0.125D, 0.0625D, 0.25D, 0.4375D, 0.1875D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.625D, 0.375D, 0.25D, 0.9375D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.25D, 0.375D, 0.25D, 0.5625D) });
   protected static final VoxelShape SHAPE_S = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.4375D, 0.25D, 0.1875D, 0.5625D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.625D, 0.25D, 0.1875D, 0.75D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.8125D, 0.25D, 0.1875D, 0.9375D), VoxelShapes.func_197873_a(0.75D, 0.125D, 0.4375D, 0.9375D, 0.1875D, 0.5625D), VoxelShapes.func_197873_a(0.75D, 0.125D, 0.625D, 0.9375D, 0.1875D, 0.75D), VoxelShapes.func_197873_a(0.75D, 0.125D, 0.8125D, 0.9375D, 0.1875D, 0.9375D), VoxelShapes.func_197873_a(0.8125D, 0.125D, 0.125D, 0.9375D, 0.4375D, 0.25D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.0625D, 0.375D, 0.25D, 0.375D), VoxelShapes.func_197873_a(0.4375D, 0.125D, 0.0625D, 0.75D, 0.25D, 0.375D) });
   protected static final VoxelShape SHAPE_W = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new VoxelShape[] { VoxelShapes.func_197873_a(0.4375D, 0.125D, 0.0625D, 0.5625D, 0.1875D, 0.25D), VoxelShapes.func_197873_a(0.25D, 0.125D, 0.0625D, 0.375D, 0.1875D, 0.25D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.0625D, 0.1875D, 0.1875D, 0.25D), VoxelShapes.func_197873_a(0.4375D, 0.125D, 0.75D, 0.5625D, 0.1875D, 0.9375D), VoxelShapes.func_197873_a(0.25D, 0.125D, 0.75D, 0.375D, 0.1875D, 0.9375D), VoxelShapes.func_197873_a(0.0625D, 0.125D, 0.75D, 0.1875D, 0.1875D, 0.9375D), VoxelShapes.func_197873_a(0.75D, 0.125D, 0.8125D, 0.875D, 0.4375D, 0.9375D), VoxelShapes.func_197873_a(0.625D, 0.125D, 0.0625D, 0.9375D, 0.25D, 0.375D), VoxelShapes.func_197873_a(0.625D, 0.125D, 0.4375D, 0.9375D, 0.25D, 0.75D) });

   public static final IntegerProperty BUTTON_PRESSED = IntegerProperty.func_177719_a("button_pressed", 0, 8);
   public static final BooleanProperty LIT = BlockStateProperties.field_208190_q;

   public static List<CoordPanelButtons> buttons = new ArrayList<>();

  public Supplier<TileEntity> tileEntitySupplier;

   private Boolean didPressButton = Boolean.valueOf(false);

  public CoordPanelBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
     func_180632_j((BlockState)((BlockState)getDefaultState().func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(0))).func_206870_a((Property)LIT, Boolean.valueOf(true)));
  }


  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }

  protected void func_206840_a(StateContainer.Builder<Block, BlockState> builder) {
     super.func_206840_a(builder);
     builder.func_206894_a(new Property[] { (Property)BUTTON_PRESSED, (Property)LIT });
  }


  public BlockState func_196258_a(BlockItemUseContext context) {
     if (!(context.func_195991_k()).isRemote && 
       context.func_195991_k().getDimensionKey().equals(DMDimensions.TARDIS)) {
       TardisData data = DMTardis.getTardisFromInteriorPos(context.func_195995_a());
       TardisFlightData flightData = TardisFlightPool.getFlightData(data);
       return (BlockState)super.func_196258_a(context).func_206870_a((Property)LIT, Boolean.valueOf(flightData.shouldRecalculateLanding()));
    }

     return super.func_196258_a(context);
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
     Direction facing = (Direction)state.get((Property)FACING);
     switch (facing)

    { default:
         return SHAPE_N;
      case ADD_Y:
         return SHAPE_E;
      case ADD_Z:
         return SHAPE_S;
      case SUB_X:
         break; }  return SHAPE_W;
  }



  public void func_225534_a_(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
     if (this.didPressButton.booleanValue())
       worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_BUTTON_RELEASE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F); 
     this.didPressButton = Boolean.valueOf(false);

     if (worldIn.func_201672_e().getDimensionKey().equals(DMDimensions.TARDIS)) {
       TardisData data = DMTardis.getTardisFromInteriorPos(pos);
       TardisFlightData flightData = TardisFlightPool.getFlightData(data);
       worldIn.setBlockState(pos, (BlockState)((BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(0))).func_206870_a((Property)LIT, Boolean.valueOf(flightData.shouldRecalculateLanding())));
    } else {
       worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(0)));
    }

     super.func_225534_a_(state, worldIn, pos, rand);
  }

  private String formatIncrementMessage(Boolean add, Direction.Axis axis, CoordPanelTileEntity tile) {
     return (add.booleanValue() ? "Added " : "Subtracted ") + tile.incrementValue + (

       add.booleanValue() ? " to " : " from ") + axis
       .toString().toUpperCase() + " (" + 
       TardisFlightPool.getFlightData(DMTardis.getTardisFromInteriorPos(tile.getPos())).getPos(axis) + ")";
  }


  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
       worldIn.func_205220_G_().func_205360_a(pos, this, 5);
       if (((Integer)state.get((Property)BUTTON_PRESSED)).intValue() == 0) {
         Vector3d hitVec = hit.func_216347_e();
         double mouseX = hitVec.func_82615_a() - pos.getX();
         double mouseZ = hitVec.func_82616_c() - pos.getZ();

         CoordPanelButtons buttonClicked = getButton(mouseX, mouseZ, (Direction)state.get((Property)FACING));
         if (worldIn.getDimensionKey().equals(DMDimensions.TARDIS)) {
           TardisData data = DMTardis.getTardisFromInteriorPos(pos);
           TardisFlightData flightData = null;

           TileEntity te = worldIn.getTileEntity(pos);
           if (te instanceof CoordPanelTileEntity) {
             CoordPanelTileEntity tet = (CoordPanelTileEntity)te;
             if (data != null) {
               flightData = TardisFlightPool.getFlightData(data);
            }
             if (flightData != null) {
               switch (buttonClicked) {
                case ADD_X:
                   flightData.incrementPos(tet.incrementValue, Direction.Axis.X);
                   ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(Boolean.valueOf(true), Direction.Axis.X, tet), ChatUtil.MessageType.STATUS_BAR);
                  break;

                case ADD_Y:
                   flightData.incrementPos(tet.incrementValue, Direction.Axis.Y);
                   ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(Boolean.valueOf(true), Direction.Axis.Y, tet), ChatUtil.MessageType.STATUS_BAR);
                  break;

                case ADD_Z:
                   flightData.incrementPos(tet.incrementValue, Direction.Axis.Z);
                   ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(Boolean.valueOf(true), Direction.Axis.Z, tet), ChatUtil.MessageType.STATUS_BAR);
                  break;

                case SUB_X:
                   flightData.incrementPos(-tet.incrementValue, Direction.Axis.X);
                   ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(Boolean.valueOf(false), Direction.Axis.X, tet), ChatUtil.MessageType.STATUS_BAR);
                  break;

                case SUB_Y:
                   flightData.incrementPos(-tet.incrementValue, Direction.Axis.Y);
                   ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(Boolean.valueOf(false), Direction.Axis.Y, tet), ChatUtil.MessageType.STATUS_BAR);
                  break;

                case SUB_Z:
                   flightData.incrementPos(-tet.incrementValue, Direction.Axis.Z);
                   ChatUtil.sendMessageToPlayer(player, formatIncrementMessage(Boolean.valueOf(false), Direction.Axis.Z, tet), ChatUtil.MessageType.STATUS_BAR);
                  break;

                case INCREMENT:
                   if (player.func_225608_bj_()) {
                     if (tet.incrementValue == 1) {
                       tet.incrementValue = 10000;
                    } else {
                       tet.incrementValue /= 10;
                    }
                   } else if (tet.incrementValue == 10000) {
                     tet.incrementValue = 1;
                  } else {
                     tet.incrementValue *= 10;
                  }

                   ChatUtil.sendMessageToPlayer(player, "Coordinate Increment: " + tet.incrementValue, ChatUtil.MessageType.STATUS_BAR);
                  break;

                case ROTATE:
                   if (player.func_225608_bj_()) {
                     flightData.setRotationAngle(flightData.getRotationAngle() - 45.0F);
                  } else {
                     flightData.setRotationAngle(flightData.getRotationAngle() + 45.0F);
                   }  ChatUtil.sendCompletedMsg(player, DMTranslationKeys.TARDIS_FLIGHT_ROTATION_SET.getString() + SWDMathUtils.rotationToCardinal(flightData.getRotationAngle()), ChatUtil.MessageType.STATUS_BAR);
                   TardisFlightPool.sendUpdates(data.getGlobalID());
                  break;

                case AUTO_CALCULATE_Y:
                   flightData.recalculateLanding(!flightData.shouldRecalculateLanding());
                   ChatUtil.sendMessageToPlayer(player, "Auto Height Calculator (" + flightData.shouldRecalculateLanding() + ")", ChatUtil.MessageType.STATUS_BAR);
                  break;
              }





               if (buttonClicked != CoordPanelButtons.EMPTY && buttonClicked != CoordPanelButtons.AUTO_CALCULATE_Y && flightData != null) {
                 flightData.setWaypoints(null);
                 TardisFlightPool.sendUpdates(data.getGlobalID());
              }
            }
          }
        }

         if (buttonClicked == CoordPanelButtons.AUTO_CALCULATE_Y) {
           worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
           worldIn.setBlockState(pos, (BlockState)state.func_235896_a_((Property)LIT));
        }

         if (buttonClicked != CoordPanelButtons.EMPTY && buttonClicked != CoordPanelButtons.AUTO_CALCULATE_Y) {
           worldIn.setBlockState(pos, (BlockState)state.func_206870_a((Property)BUTTON_PRESSED, Integer.valueOf(buttonClicked.ordinal() + 1)));
           worldIn.playSound(null, pos, (SoundEvent)DMSoundEvents.TARDIS_CONTROLS_BUTTON_CLICK.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
           this.didPressButton = Boolean.valueOf(true);
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

  public CoordPanelButtons getButton(double mouseX, double mouseZ, Direction facing) {
     for (CoordPanelButtons button : buttons) {
       if (button.values.containsKey(facing)) {
         Vector2f vec = button.values.get(facing);

         float width = button.width;
         float height = button.height;
         float x = vec.field_189982_i;
         float z = vec.field_189983_j;

         switch (facing) {






          case ADD_Z:
             if (mouseX >= x && mouseZ >= z && mouseX <= (x + width) && mouseZ <= (z + height)) {
               return button;
            }
            break;
          case ADD_Y:
             if (mouseX >= x && mouseX <= (x + height) && mouseZ <= z && mouseZ >= (z - width)) {
               return button;
            }
            break;
          case SUB_X:
             if (mouseX <= x && mouseX >= (x - height) && mouseZ >= z && mouseZ <= (z + width)) {
               return button;
            }
            break;
        }



      }
    }
     return CoordPanelButtons.EMPTY;
  }

  public enum CoordPanelButtons {
     SUB_X("Subtract X", 3.0F, 2.0F, 1.0F, 7.0F),
     SUB_Y("Subtract Y", 3.0F, 2.0F, 1.0F, 10.0F),
     SUB_Z("Subtract Z", 3.0F, 2.0F, 1.0F, 13.0F),

     ADD_X("Add X", 3.0F, 2.0F, 12.0F, 7.0F),
     ADD_Y("Add Y", 3.0F, 2.0F, 12.0F, 10.0F),
     ADD_Z("Add Z", 3.0F, 2.0F, 12.0F, 13.0F),

     INCREMENT("Change Increment", 5.0F, 5.0F, 7.0F, 1.0F),
     ROTATE("Change Rotation", 5.0F, 5.0F, 1.0F, 1.0F),

     AUTO_CALCULATE_Y("Toggle Height Calculator", 5.0F, 5.0F, 13.0F, 2.0F),

     EMPTY(null, 0.0F, 0.0F, 0.0F, 0.0F);

     Map<Direction, Vector2f> values = new HashMap<>();
    float width;
    float height;
    StringTextComponent displayName;

    CoordPanelButtons(String s, float w, float h, float x1, float z1) {
       float f = 0.0625F;
       this.width = w * f;
       this.height = h * f;
       float x2 = 16.0F - x1;
       float z2 = 16.0F - z1;
       this.values.put(Direction.NORTH, new Vector2f(x2 * f, z2 * f));
       this.values.put(Direction.EAST, new Vector2f(z1 * f, x2 * f));
       this.values.put(Direction.SOUTH, new Vector2f(x1 * f, z1 * f));
       this.values.put(Direction.WEST, new Vector2f(z2 * f, x1 * f));

       CoordPanelBlock.buttons.add(this);
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


