package com.swdteam.mixins;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import com.swdteam.common.init.DMBlocks;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.init.DMNBTKeys;
import com.swdteam.common.init.DMSoundEvents;
import com.swdteam.common.init.DMTardis;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({CompassItem.class})
public abstract class CompassItemMixin {
  private static boolean isTardisCompass(ItemStack stack) {
     CompoundNBT nbt = stack.func_196082_o();
     return nbt.func_74764_b(DMNBTKeys.LINKED_ID);
  }
  
  private static Pair<BlockPos, Optional<RegistryKey<World>>> getTardisLocation(ServerWorld world, CompoundNBT nbt) {
     int id = nbt.getInt(DMNBTKeys.LINKED_ID);
     TardisData data = DMTardis.getTardis(id);
     BlockPos pos = data.getCurrentLocation().getBlockPosition();
     Optional<RegistryKey<World>> dim = data.isInFlight() ? Optional.<RegistryKey<World>>ofNullable(null) : Optional.<RegistryKey<World>>of(data.getCurrentLocation().dimensionWorldKey());
     return new Pair(pos, dim);
  }
  
  private static boolean checkIfDuplicate(CompoundNBT nbt, Pair<BlockPos, Optional<RegistryKey<World>>> pair) {
     BlockPos pairPos = (BlockPos)pair.getFirst();
     ResourceLocation pairWorld = ((Optional)pair.getSecond()).isPresent() ? ((RegistryKey)((Optional<RegistryKey>)pair.getSecond()).get()).getRegistryLocation() : new ResourceLocation("");
    
     BlockPos nbtPos = NBTUtil.func_186861_c(nbt.func_74775_l("LodestonePos"));
     ResourceLocation nbtWorld = new ResourceLocation(nbt.func_74779_i("LodestoneDimension"));
    
     return (pairPos.equals(nbtPos) && pairWorld.equals(nbtWorld));
  }
  
  private void addTardisTags(ServerWorld server, CompoundNBT nbt) {
     Pair<BlockPos, Optional<RegistryKey<World>>> pair = getTardisLocation(server, nbt);
     if (checkIfDuplicate(nbt, pair))
       return;  BlockPos pos = (BlockPos)pair.getFirst();
     Optional<RegistryKey<World>> world = (Optional<RegistryKey<World>>)pair.getSecond();
    
     nbt.func_218657_a("LodestonePos", (INBT)NBTUtil.func_186859_a(pos));
     if (world.isPresent()) {
       World.field_234917_f_.encodeStart((DynamicOps)NBTDynamicOps.field_210820_a, world.get()).resultOrPartial(LogManager.getLogger()::error).ifPresent(dim -> nbt.func_218657_a("LodestoneDimension", dim));
    } else {
      
       nbt.func_82580_o("LodestoneDimension");
     }  nbt.func_74757_a("LodestoneTracked", false);
  }
  
  private void addTardisTags(ServerWorld server, CompoundNBT nbt, int id) {
     nbt.putInt(DMNBTKeys.LINKED_ID, id);
     addTardisTags(server, nbt);
  }
  
  @Inject(at = {@At("RETURN")}, cancellable = true, method = {"Lnet/minecraft/item/CompassItem;isLodestoneCompass(Lnet/minecraft/item/ItemStack;)Z"})
  private static void isLodeStoneCompass(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
     if (isTardisCompass(stack)) info.setReturnValue(Boolean.valueOf(true)); 
  }
  
  @Inject(at = {@At("RETURN")}, cancellable = true, method = {"Lnet/minecraft/item/CompassItem;isFoil(Lnet/minecraft/item/ItemStack;)Z"})
  public void isFoil(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
     if (isTardisCompass(stack)) info.setReturnValue(Boolean.valueOf(true)); 
  }
  
  @Inject(at = {@At("HEAD")}, cancellable = true, method = {"Lnet/minecraft/item/CompassItem;inventoryTick(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;IZ)V"})
  public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean u_0, CallbackInfo info) {
     if (!world.isRemote && 
       isTardisCompass(stack)) {
       addTardisTags((ServerWorld)world, stack.func_196082_o());
      
       BlockPos pos = NBTUtil.func_186861_c(stack.func_77978_p().func_74775_l(DMNBTKeys.LODESTONE_AMPLIFIER_POS));
       ServerWorld tardisDim = world.getServer().getWorld(DMDimensions.TARDIS);
      
       if (tardisDim.getBlockState(pos).getBlock() != DMBlocks.LODESTONE_AMPLIFIER.get()) stack.func_77978_p().func_82580_o("LodestonePos");
    
    } 
  }
  
  @Inject(at = {@At("HEAD")}, cancellable = true, method = {"Lnet/minecraft/item/CompassItem;useOn(Lnet/minecraft/item/ItemUseContext;)Lnet/minecraft/util/ActionResultType;"})
  public void useOn(ItemUseContext context, CallbackInfoReturnable<ActionResultType> info) {
     BlockPos pos = context.func_195995_a();
     World world = context.func_195991_k();
     BlockState state = world.getBlockState(pos);
    
     if (state.getBlock() == DMBlocks.LODESTONE_AMPLIFIER.get() && world.getDimensionKey() == DMDimensions.TARDIS) {
       world.playSound((PlayerEntity)null, pos, (SoundEvent)DMSoundEvents.TARDIS_COMPASS_LOCK.get(), SoundCategory.PLAYERS, 1.0F, 1.0F);
      
       if (!world.isRemote) {
         ItemStack stack = context.func_195996_i();
         PlayerEntity playerentity = context.func_195999_j();
         boolean flag = (!playerentity.abilities.field_75098_d && stack.func_190916_E() == 1);
         int id = DMTardis.getIDForXZ(pos.getX(), pos.getZ());
        
         if (flag) {
           addTardisTags((ServerWorld)world, stack.func_196082_o(), id);
           stack.func_77978_p().func_218657_a(DMNBTKeys.LODESTONE_AMPLIFIER_POS, (INBT)NBTUtil.func_186859_a(pos));
        } else {
           ItemStack itemstack1 = new ItemStack((IItemProvider)Items.field_151111_aL, 1);
           CompoundNBT nbt = stack.func_77942_o() ? stack.func_77978_p().func_74737_b() : new CompoundNBT();
          
           itemstack1.func_77982_d(nbt);
           addTardisTags((ServerWorld)world, itemstack1.func_77978_p(), id);
           itemstack1.func_77978_p().func_218657_a(DMNBTKeys.LODESTONE_AMPLIFIER_POS, (INBT)NBTUtil.func_186859_a(pos));
          
           if (!playerentity.abilities.field_75098_d) stack.func_190918_g(1); 
           if (!playerentity.field_71071_by.func_70441_a(itemstack1)) playerentity.func_71019_a(itemstack1, false);
        
        } 
      } 
       info.setReturnValue(ActionResultType.func_233537_a_(world.isRemote));
    } 
  }
  
  @Inject(at = {@At("RETURN")}, cancellable = true, method = {"Lnet/minecraft/item/CompassItem;getDescriptionId(Lnet/minecraft/item/ItemStack;)Ljava/lang/String;"})
  public void getDescriptionId(ItemStack stack, CallbackInfoReturnable<String> info) {
     if (isTardisCompass(stack)) info.setReturnValue(DMTranslationKeys.TARDIS_COMPASS.func_150268_i()); 
  }
}


