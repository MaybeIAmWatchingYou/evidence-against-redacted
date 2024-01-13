package com.swdteam.common.block;

import com.mojang.authlib.GameProfile;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.tileentity.HologramTileEntity;
import com.swdteam.network.NetworkHandler;
import com.swdteam.network.packets.PacketOpenGui;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;


public class HologramBlock
  extends TileEntityBaseBlock.WaterLoggable
{
   public static final BooleanProperty HAS_BASE = BooleanProperty.func_177716_a("has_base");
  
   public static final VoxelShape SHAPE = VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 2.0D, 1.0D);
   public static final VoxelShape BASE_SHAPE = VoxelShapes.func_197873_a(0.0D, 0.0D, 0.0D, 1.0D, 0.13D, 1.0D);
  
  public HologramBlock(Supplier<TileEntity> tileEntitySupplier, AbstractBlock.Properties properties) {
     super(tileEntitySupplier, properties);
     func_180632_j((BlockState)getDefaultState().func_206870_a((Property)HAS_BASE, Boolean.valueOf(true)));
  }

  
  public void func_180633_a(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
     super.func_180633_a(worldIn, pos, state, placer, stack);
    
     HologramTileEntity tile = (HologramTileEntity)worldIn.getTileEntity(pos);
    
     String fullName = stack.func_151000_E().getString();
     String name = stack.func_151000_E().getString().replaceAll(":[123]", "").replaceAll("(\\W|^_)*", "");
    
     CompoundNBT compoundnbt = stack.func_77978_p();
     GameProfile profile = null;
    
     if (compoundnbt != null && compoundnbt.func_150297_b(DMNBTKeys.OWNER, 10)) {
       profile = NBTUtil.func_152459_a(compoundnbt.func_74775_l(DMNBTKeys.OWNER));
    }
     else if (compoundnbt != null && compoundnbt.func_150297_b(DMNBTKeys.OWNER, 8) && !StringUtils.isBlank(compoundnbt.func_74779_i(DMNBTKeys.OWNER))) {
       profile = SkullTileEntity.func_174884_b(new GameProfile((UUID)null, compoundnbt.func_74779_i(DMNBTKeys.OWNER)));
    }
     else if (stack.func_82837_s()) {
       if (fullName.contains(":1") || fullName.contains(":3")) tile.isSolid = true; 
       if (fullName.contains(":2") || fullName.contains(":3")) tile.smallArms = true;
      
       profile = SkullTileEntity.func_174884_b(new GameProfile((UUID)null, name));
    } 
     if (profile != null) {
       tile.setOwner(profile);
       tile.getTileData().func_218657_a(DMNBTKeys.OWNER, (INBT)NBTUtil.func_180708_a(new CompoundNBT(), profile));
    } 
    
     int i = MathHelper.func_76128_c(placer.field_70126_B);
    
     i = i % 360 - 180;
    
     tile.setRotation(i);
     tile.sendUpdates();
  }



  
  public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
     if (world.isRemote) {
       return ActionResultType.SUCCESS;
    }
     TileEntity te = world.getTileEntity(pos);
     if (te instanceof HologramTileEntity) {
       HologramTileEntity tile = (HologramTileEntity)world.getTileEntity(pos);
       if (!tile.isLocked() || player.getGameProfile().getId().equals(tile.getLockedBy())) {
         NetworkHandler.sendTo((ServerPlayerEntity)player, new PacketOpenGui(pos, 6));
      }
    } 
     return ActionResultType.CONSUME;
  }


  
  public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {}


  
  public boolean collisionExtendsVertically(BlockState state, IBlockReader world, BlockPos pos, Entity collidingEntity) {
     return true;
  }

  
  public VoxelShape func_220053_a(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
     if (((Boolean)state.get((Property)HAS_BASE)).booleanValue()) {
       return BASE_SHAPE;
    }
     return SHAPE;
  }










  
  protected void func_206840_a(StateContainer.Builder<Block, BlockState> state) {
     super.func_206840_a(state);
     state.func_206894_a(new Property[] { (Property)HAS_BASE });
  }

  
  public BlockRenderType func_149645_b(BlockState p_149645_1_) {
     return ((Boolean)p_149645_1_.get((Property)HAS_BASE)).booleanValue() ? BlockRenderType.MODEL : BlockRenderType.INVISIBLE;
  }
}


