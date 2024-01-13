package com.swdteam.common.block.tardis;
import com.swdteam.common.block.AbstractRotateableWaterLoggableBlock;
import com.swdteam.common.block.IBlockTooltip;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.item.sonics.SonicScrewdriverCustomizedItem;
import com.swdteam.common.tileentity.tardis.SonicInterfaceTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import com.swdteam.network.packets.PacketXPSync;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
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

public class SonicInterfaceBlock extends AbstractRotateableWaterLoggableBlock implements IBlockTooltip {
   protected static final VoxelShape SHAPE_NORTH = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.40625D, 0.0625D, 0.03125D, 0.59375D, 0.1875D, 0.21875D), new VoxelShape[] { VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), VoxelShapes.func_197873_a(0.0D, 0.125D, 0.5D, 0.5D, 0.5D, 1.0D), VoxelShapes.func_197873_a(0.625D, 0.125625D, 0.625D, 0.875D, 0.375625D, 0.875D) });
   protected static final VoxelShape SHAPE_EAST = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.78125D, 0.0625D, 0.40625D, 0.96875D, 0.1875D, 0.59375D), new VoxelShape[] { VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), VoxelShapes.func_197873_a(0.0D, 0.125D, 0.0D, 0.5D, 0.5D, 0.5D), VoxelShapes.func_197873_a(0.125D, 0.125625D, 0.625D, 0.375D, 0.375625D, 0.875D) });
   protected static final VoxelShape SHAPE_SOUTH = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.40625D, 0.0625D, 0.78125D, 0.59375D, 0.1875D, 0.96875D), new VoxelShape[] { VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), VoxelShapes.func_197873_a(0.5D, 0.125D, 0.0D, 1.0D, 0.5D, 0.5D), VoxelShapes.func_197873_a(0.125D, 0.125625D, 0.125D, 0.375D, 0.375625D, 0.375D) });
   protected static final VoxelShape SHAPE_WEST = VoxelShapes.func_216384_a(VoxelShapes.func_197873_a(0.03125D, 0.0625D, 0.40625D, 0.21875D, 0.1875D, 0.59375D), new VoxelShape[] { VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), VoxelShapes.func_197873_a(0.5D, 0.125D, 0.5D, 1.0D, 0.5D, 1.0D), VoxelShapes.func_197873_a(0.625D, 0.125625D, 0.125D, 0.875D, 0.375625D, 0.375D) });

  public SonicInterfaceBlock(AbstractBlock.Properties properties) {
     super(properties.func_226896_b_());
  }


  public ActionResultType func_225533_a_(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
     if (!worldIn.isRemote) {

       boolean insertSonic = false;

       float mouseX = (int)(100.0F * (float)(hit.func_216347_e().func_82615_a() - pos.getX())) / 100.0F;
       float mouseZ = (int)(100.0F * (float)(hit.func_216347_e().func_82616_c() - pos.getZ())) / 100.0F;

       switch ((Direction)state.get((Property)FACING)) {
        case EAST:
           if (mouseX <= 0.97F && mouseX >= 0.79F && mouseZ >= 0.4F && mouseZ <= 0.59F) {
             insertSonic = true; break;
          }
           insertSonic = false;
          break;
        case SOUTH:
           if (mouseX <= 0.59F && mouseX >= 0.4F && mouseZ >= 0.79F && mouseZ <= 0.97F) {
             insertSonic = true; break;
          }
           insertSonic = false;
          break;
        case WEST:
           if (mouseX >= 0.03F && mouseX <= 0.21F && mouseZ >= 0.4F && mouseZ <= 0.59F) {
             insertSonic = true; break;
          }
           insertSonic = false;
          break;
        default:
           if (mouseX <= 0.59F && mouseX >= 0.4F && mouseZ >= 0.03F && mouseZ <= 0.21F) {
             insertSonic = true; break;
          }
           insertSonic = false;
          break;
      }

       TileEntity te = worldIn.getTileEntity(pos);
       if (te instanceof SonicInterfaceTileEntity) {
         SonicInterfaceTileEntity sonic = (SonicInterfaceTileEntity)te;
         if (insertSonic) {
           if (sonic.getScrewdriver() == null || (sonic.getScrewdriver() != null && !(sonic.getScrewdriver().getItem() instanceof SonicScrewdriverCustomizedItem))) {
             if (player.getHeldItem(handIn).getItem() instanceof SonicScrewdriverCustomizedItem) {
               sonic.setScrewdriver(player.getHeldItem(handIn));
               ((SonicScrewdriverCustomizedItem)sonic.getScrewdriver().getItem()).checkIsSetup(sonic.getScrewdriver());
               player.func_184611_a(handIn, ItemStack.field_190927_a);
               sonic.sendUpdates();
               worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_SONIC_WORKBENCH_INSERT.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);

               return ActionResultType.CONSUME;
            }

           } else if (sonic.getScrewdriver() != null && player.getHeldItem(handIn).func_190926_b()) {
             player.func_184611_a(handIn, sonic.getScrewdriver());
             sonic.setScrewdriver(ItemStack.field_190927_a);
             sonic.sendUpdates();
             worldIn.playSound((PlayerEntity)null, pos.getX(), pos.getY(), pos.getZ(), (SoundEvent)DMSoundEvents.TARDIS_SONIC_WORKBENCH_REMOVE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
          }

        }
         else if (sonic.getScrewdriver() != null && sonic.getScrewdriver().getItem() instanceof SonicScrewdriverCustomizedItem) {
           NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketXPSync(player.experienceTotal, false));
           NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 4));
        }
      }
    }

     return ActionResultType.CONSUME;
  }


  public boolean hasTileEntity(BlockState state) {
     return true;
  }


  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
     return (TileEntity)new SonicInterfaceTileEntity();
  }

  public BlockState func_196258_a(BlockItemUseContext context) {
     return (BlockState)super.func_196258_a(context).func_206870_a((Property)FACING, (Comparable)context.func_195992_f().getOpposite());
  }


  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return BlockRenderType.MODEL;
  }


  public VoxelShape func_220071_b(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return getShape((Direction)state.get((Property)FACING));
  }


  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     return getShape((Direction)state.get((Property)FACING));
  }

  private VoxelShape getShape(Direction direction) {
     switch (direction) { default:
         return SHAPE_NORTH;
       case EAST: return SHAPE_EAST;
       case SOUTH: return SHAPE_SOUTH;
       case WEST: break; }  return SHAPE_WEST;
  }


   private static ITextComponent TXT_INSERT_SONIC = (ITextComponent)new StringTextComponent("Insert Sonic");
   private static ITextComponent TXT_REMOVE_SONIC = (ITextComponent)new StringTextComponent("Remove Sonic");
   private static ITextComponent TXT_OPEN_INTERFACE = (ITextComponent)new StringTextComponent("Open Interface");



  public ITextComponent getName(BlockState state, BlockPos pos, Vector3d hitVec, PlayerEntity player) {
     ITextComponent txt = null;

     float mouseX = (int)(100.0F * (float)(hitVec.func_82615_a() - pos.getX())) / 100.0F;
     float mouseZ = (int)(100.0F * (float)(hitVec.func_82616_c() - pos.getZ())) / 100.0F;

     switch ((Direction)state.get((Property)FACING)) {
      case EAST:
         if (mouseX <= 0.97F && mouseX >= 0.79F && mouseZ >= 0.4F && mouseZ <= 0.59F) {
           txt = TXT_INSERT_SONIC; break;
        }
         txt = TXT_OPEN_INTERFACE;
        break;
      case SOUTH:
         if (mouseX <= 0.59F && mouseX >= 0.4F && mouseZ >= 0.79F && mouseZ <= 0.97F) {
           txt = TXT_INSERT_SONIC; break;
        }
         txt = TXT_OPEN_INTERFACE;
        break;
      case WEST:
         if (mouseX >= 0.03F && mouseX <= 0.21F && mouseZ >= 0.4F && mouseZ <= 0.59F) {
           txt = TXT_INSERT_SONIC; break;
        }
         txt = TXT_OPEN_INTERFACE;
        break;
      default:
         if (mouseX <= 0.59F && mouseX >= 0.4F && mouseZ >= 0.03F && mouseZ <= 0.21F) {
           txt = TXT_INSERT_SONIC; break;
        }
         txt = TXT_OPEN_INTERFACE;
        break;
    }

     SonicInterfaceTileEntity te = (SonicInterfaceTileEntity)player.getEntityWorld().getTileEntity(pos);

     if (te.getScrewdriver() != null && te.getScrewdriver().getItem() instanceof SonicScrewdriverCustomizedItem &&
       txt == TXT_INSERT_SONIC) {
       txt = TXT_REMOVE_SONIC;
    }


     return txt;
  }


  public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, FluidState fluid) {
     if (!world.isRemote) {
       TileEntity te = world.getTileEntity(pos);
       if (te != null && te instanceof SonicInterfaceTileEntity) {
         SonicInterfaceTileEntity sonicInterface = (SonicInterfaceTileEntity)te;
         ItemStack itemstack = sonicInterface.getScrewdriver();
         if (itemstack != null) {
           ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, world);
           itemEntity.func_70080_a((pos.getX() + 0.5F), pos.getY(), (pos.getZ() + 0.5F), 0.0F, 0.0F);

           itemEntity.setItem(itemstack);
           world.addEntity((Entity)itemEntity);
        }
      }
    }
     return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
  }

  public BlockState func_196271_a(BlockState state1, Direction dir, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
     return (dir == Direction.DOWN && !func_196260_a(state2, (IWorldReader)world, pos1)) ? Blocks.AIR.getDefaultState() : super.func_196271_a(state1, dir, state2, world, pos1, pos2);
  }

  public boolean func_196260_a(BlockState state, IWorldReader reader, BlockPos pos) {
     return func_220055_a(reader, pos.func_177977_b(), Direction.UP);
  }
}


