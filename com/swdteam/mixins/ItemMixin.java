package com.swdteam.mixins;

import com.swdteam.common.block.IRust;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;




@Mixin({Item.class})
public abstract class ItemMixin
{
   private Item item = (Item)this;

  @Inject(at = {@At("HEAD")}, cancellable = true, method = {"Lnet/minecraft/item/Item;use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"})
  private void use(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult<ItemStack>> info) {
     if (this.item == Items.field_226635_pU_) {
       ItemStack itemstack = player.getHeldItem(hand);
       BlockRayTraceResult blockRayTraceResult = getPlayerPOVHitResult(world, player);

       if (blockRayTraceResult.func_216346_c() == RayTraceResult.Type.BLOCK) {
         BlockRayTraceResult blockraytraceresult = blockRayTraceResult;
         BlockPos blockpos = blockraytraceresult.func_216350_a();
         BlockState state = world.getBlockState(blockpos);
         Direction direction = blockraytraceresult.func_216354_b();
         if (world.func_175660_a(player, blockpos) && player
           .func_175151_a(blockpos.func_177972_a(direction), direction, itemstack) && state
           .getBlock() instanceof IRust && (
           (IRust)state.getBlock()).wax(world, blockpos, player, hand)) info.setReturnValue(ActionResult.func_226249_b_(itemstack));

      }
    }
  }

  private static BlockRayTraceResult getPlayerPOVHitResult(World world, PlayerEntity player) {
     float f = player.field_70125_A;
     float f1 = player.field_70177_z;
     Vector3d vector3d = player.func_174824_e(1.0F);
     float f2 = MathHelper.func_76134_b(-f1 * 0.017453292F - 3.1415927F);
     float f3 = MathHelper.func_76126_a(-f1 * 0.017453292F - 3.1415927F);
     float f4 = -MathHelper.func_76134_b(-f * 0.017453292F);
     float f5 = MathHelper.func_76126_a(-f * 0.017453292F);
     float f6 = f3 * f4;
     float f7 = f2 * f4;
     double d0 = player.func_110148_a((Attribute)ForgeMod.REACH_DISTANCE.get()).func_111126_e();

     Vector3d vector3d1 = vector3d.func_72441_c(f6 * d0, f5 * d0, f7 * d0);
     return world.func_217299_a(new RayTraceContext(vector3d, vector3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, (Entity)player));
  }
}


