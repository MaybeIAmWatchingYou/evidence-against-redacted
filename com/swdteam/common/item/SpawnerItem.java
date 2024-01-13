package com.swdteam.common.item;

import com.swdteam.common.entity.IEntityVariant;
import com.swdteam.common.entity.dalek.DalekEntity;
import com.swdteam.common.entity.dalek.IDalek;
import com.swdteam.common.init.DMDalekRegistry;
import com.swdteam.common.init.DMEntities;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.AbstractSpawner;

public class SpawnerItem<T extends Entity>
  extends Item {
  private String entityType;
  private List<String> variants;

  public SpawnerItem(final String entityType, final List<String> keys) {
     super((new Item.Properties()).func_200916_a(ItemGroup.field_78026_f));
     this.entityType = entityType;
     this.variants = keys;
     DispenserBlock.func_199774_a((IItemProvider)this, (IDispenseItemBehavior)new DefaultDispenseItemBehavior() {
          public ItemStack func_82487_b(IBlockSource dispenser, ItemStack spawnerStack) {
             Direction direction = (Direction)dispenser.func_189992_e().get((Property)DispenserBlock.field_176441_a);
             EntityType<?> entitytype = DMEntities.getEntityTypeFromString(entityType);
             Entity e = entitytype.func_220331_a(dispenser.func_197524_h(), spawnerStack, (PlayerEntity)null, dispenser.func_180699_d().func_177972_a(direction), SpawnReason.DISPENSER, (direction != Direction.UP), false);

             if (e instanceof DalekEntity) {
               ((DalekEntity)e).setID(keys.get(e.world.rand.nextInt(keys.size())));
               System.out.println("askdhalkjsdhaljshds");
            }

             spawnerStack.func_190918_g(1);
             return spawnerStack;
          }
        });
  }

  public SpawnerItem(String entityType, String key) {
     this(entityType, Arrays.asList(new String[] { key }));
  }

  public SpawnerItem(String entityType) {
     this(entityType, Arrays.asList(new String[0]));
  }

  public ActionResultType func_195939_a(ItemUseContext context) {
    BlockPos blockpos1;
     World world = context.func_195991_k();
     if (world.isRemote) {
       return ActionResultType.SUCCESS;
    }
     ItemStack itemstack = context.func_195996_i();
     BlockPos blockpos = context.func_195995_a();
     Direction direction = context.func_196000_l();
     BlockState blockstate = world.getBlockState(blockpos);
     Block block = blockstate.getBlock();
     if (block == Blocks.field_150474_ac) {
       TileEntity tileentity = world.getTileEntity(blockpos);
       if (tileentity instanceof MobSpawnerTileEntity) {
         AbstractSpawner abstractspawner = ((MobSpawnerTileEntity)tileentity).func_145881_a();
         EntityType<?> entitytype1 = DMEntities.getEntityTypeFromString(this.entityType);
         abstractspawner.func_200876_a(entitytype1);
         tileentity.markDirty();
         world.func_184138_a(blockpos, blockstate, blockstate, 3);
         itemstack.func_190918_g(1);
         return ActionResultType.SUCCESS;
      }
    }


     if (blockstate.func_196952_d((IBlockReader)world, blockpos).func_197766_b()) {
       blockpos1 = blockpos;
    } else {
       blockpos1 = blockpos.func_177972_a(direction);
    }

     EntityType<?> entitytype = DMEntities.getEntityTypeFromString(this.entityType);


     Entity entity = entitytype.func_220331_a((ServerWorld)world, itemstack, context.func_195999_j(), blockpos1, SpawnReason.SPAWN_EGG, true, (!Objects.equals(blockpos, blockpos1) && direction == Direction.UP));
     itemstack.func_190918_g(1);

     if (entity != null && this.variants != null)
    {

       if (entity instanceof IEntityVariant) {
         String variant = this.variants.get((new Random()).nextInt(this.variants.size()));
         ((IEntityVariant)entity).setID(variant);
      }
    }

     return ActionResultType.SUCCESS;
  }







  public ActionResult<ItemStack> func_77659_a(World worldIn, PlayerEntity playerIn, Hand handIn) {
     ItemStack itemstack = playerIn.getHeldItem(handIn);
     BlockRayTraceResult blockRayTraceResult1 = func_219968_a(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
     if (blockRayTraceResult1.func_216346_c() != RayTraceResult.Type.BLOCK)
       return ActionResult.func_226250_c_(itemstack); 
     if (worldIn.isRemote) {
       return ActionResult.func_226248_a_(itemstack);
    }
     BlockRayTraceResult blockraytraceresult = blockRayTraceResult1;
     BlockPos blockpos = blockraytraceresult.func_216350_a();
     if (!(worldIn.getBlockState(blockpos).getBlock() instanceof net.minecraft.block.FlowingFluidBlock))
       return ActionResult.func_226250_c_(itemstack); 
     if (worldIn.func_175660_a(playerIn, blockpos) && playerIn.func_175151_a(blockpos, blockraytraceresult.func_216354_b(), itemstack)) {
       EntityType<?> entitytype = DMEntities.getEntityTypeFromString(this.entityType);
      Entity entity;
       if ((entity = entitytype.func_220331_a((ServerWorld)worldIn, itemstack, playerIn, blockpos, SpawnReason.SPAWN_EGG, false, false)) == null) {
         return ActionResult.func_226250_c_(itemstack);
      }
       if (!playerIn.abilities.field_75098_d) {
         itemstack.func_190918_g(1);
      }

       itemstack.func_190918_g(1);
       if (entity != null && this.variants != null)
      {


         if (entity instanceof IEntityVariant) {
           String variant = this.variants.get((new Random()).nextInt(this.variants.size()));
           ((IEntityVariant)entity).setID(variant);
        }
      }

       playerIn.func_71029_a(Stats.field_75929_E.func_199076_b(this));
       return ActionResult.func_226248_a_(itemstack);
    }

     return ActionResult.func_226251_d_(itemstack);
  }




  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
     super.func_77624_a(stack, worldIn, tooltip, flagIn);

     if (this.entityType != null && worldIn != null && 
       this.entityType == "dalek")
       ((IDalek)DMDalekRegistry.getDaleks().get(this.variants.get(0))).getTooltips(tooltip); 
  }
}


