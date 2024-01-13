package com.swdteam.common.tileentity;
import com.swdteam.common.init.DMBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IChestLid;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

@OnlyIn(value = Dist.CLIENT, _interface = IChestLid.class)
public class BetaChestTileEntity extends ChestTileEntity {
   private NonNullList<ItemStack> items = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
  protected float field_145989_m;
  protected float field_145986_n;
  protected int field_145987_o;
  private int tickInterval;
  private LazyOptional<IItemHandlerModifiable> chestHandler;
  
  protected BetaChestTileEntity(TileEntityType<?> p_i48287_1_) {
     super(p_i48287_1_);
  }
  
  public BetaChestTileEntity() {
     this((TileEntityType)DMBlockEntities.TILE_BETA_CHEST.get());
  }
  
  public int func_70302_i_() {
     return 27;
  }
  
  protected ITextComponent func_213907_g() {
     return (ITextComponent)new TranslationTextComponent("container.chest");
  }
  
  public void read(BlockState p_230337_1_, CompoundNBT p_230337_2_) {
     super.read(p_230337_1_, p_230337_2_);
     this.items = NonNullList.func_191197_a(func_70302_i_(), ItemStack.field_190927_a);
     if (!func_184283_b(p_230337_2_)) {
       ItemStackHelper.func_191283_b(p_230337_2_, this.items);
    }
  }

  
  public CompoundNBT func_189515_b(CompoundNBT p_189515_1_) {
     super.func_189515_b(p_189515_1_);
     if (!func_184282_c(p_189515_1_)) {
       ItemStackHelper.func_191282_a(p_189515_1_, this.items);
    }
    
     return p_189515_1_;
  }
  
  public void tick() {
     int i = this.field_174879_c.getX();
     int j = this.field_174879_c.getY();
     int k = this.field_174879_c.getZ();
     this.tickInterval++;
     this.field_145987_o = getOpenCount(this.world, (LockableTileEntity)this, this.tickInterval, i, j, k, this.field_145987_o);
     this.field_145986_n = this.field_145989_m;
    
     float f = 0.1F;
     if (this.field_145987_o > 0 && this.field_145989_m == 0.0F) {
       playSound(SoundEvents.field_187875_gN);
    }
    
     if ((this.field_145987_o == 0 && this.field_145989_m > 0.0F) || (this.field_145987_o > 0 && this.field_145989_m < 1.0F)) {
       float f1 = this.field_145989_m;
       if (this.field_145987_o > 0) {
         this.field_145989_m += 0.1F;
      } else {
         this.field_145989_m -= 0.1F;
      } 
      
       if (this.field_145989_m > 1.0F) {
         this.field_145989_m = 1.0F;
      }

      
       float f2 = 0.5F;
       if (this.field_145989_m < 0.5F && f1 >= 0.5F) {
         playSound(SoundEvents.field_187873_gM);
      }
      
       if (this.field_145989_m < 0.0F) {
         this.field_145989_m = 0.0F;
      }
    } 
  }
  
  public static int getOpenCount(World p_213977_0_, LockableTileEntity p_213977_1_, int p_213977_2_, int p_213977_3_, int p_213977_4_, int p_213977_5_, int p_213977_6_) {
     if (!p_213977_0_.isRemote && p_213977_6_ != 0 && (p_213977_2_ + p_213977_3_ + p_213977_4_ + p_213977_5_) % 200 == 0) {
       p_213977_6_ = getOpenCount(p_213977_0_, p_213977_1_, p_213977_3_, p_213977_4_, p_213977_5_);
    }
     return p_213977_6_;
  }
  
  public static int getOpenCount(World p_213976_0_, LockableTileEntity p_213976_1_, int p_213976_2_, int p_213976_3_, int p_213976_4_) {
     int i = 0;
    
     float f = 5.0F;
    
     for (PlayerEntity playerentity : p_213976_0_.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB((p_213976_2_ - 5.0F), (p_213976_3_ - 5.0F), (p_213976_4_ - 5.0F), ((p_213976_2_ + 1) + 5.0F), ((p_213976_3_ + 1) + 5.0F), ((p_213976_4_ + 1) + 5.0F)))) {
       if (playerentity.field_71070_bA instanceof ChestContainer) {
         IInventory iinventory = ((ChestContainer)playerentity.field_71070_bA).func_85151_d();
         if (iinventory == p_213976_1_ || (iinventory instanceof DoubleSidedInventory && ((DoubleSidedInventory)iinventory).func_90010_a((IInventory)p_213976_1_))) {
           i++;
        }
      } 
    } 
     return i;
  }
  
  private void playSound(SoundEvent p_195483_1_) {
     ChestType chesttype = (ChestType)func_195044_w().get((Property)ChestBlock.field_196314_b);
     if (chesttype != ChestType.LEFT) {
       double d0 = this.field_174879_c.getX() + 0.5D;
       double d1 = this.field_174879_c.getY() + 0.5D;
       double d2 = this.field_174879_c.getZ() + 0.5D;
       if (chesttype == ChestType.RIGHT) {
         Direction direction = ChestBlock.func_196311_i(func_195044_w());
         d0 += direction.func_82601_c() * 0.5D;
         d2 += direction.func_82599_e() * 0.5D;
      } 
       this.world.playSound((PlayerEntity)null, d0, d1, d2, p_195483_1_, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
    } 
  }
  
  public boolean func_145842_c(int p_145842_1_, int p_145842_2_) {
     if (p_145842_1_ == 1) {
       this.field_145987_o = p_145842_2_;
       return true;
    } 
     return super.func_145842_c(p_145842_1_, p_145842_2_);
  }

  
  public void func_174889_b(PlayerEntity p_174889_1_) {
     if (!p_174889_1_.func_175149_v()) {
       if (this.field_145987_o < 0) {
         this.field_145987_o = 0;
      }
      
       this.field_145987_o++;
       func_195482_p();
    } 
  }

  
  public void func_174886_c(PlayerEntity p_174886_1_) {
     if (!p_174886_1_.func_175149_v()) {
       this.field_145987_o--;
       func_195482_p();
    } 
  }

  
  protected void func_195482_p() {
     Block block = func_195044_w().getBlock();
     if (block instanceof ChestBlock) {
       this.world.func_175641_c(this.field_174879_c, block, 1, this.field_145987_o);
       this.world.func_195593_d(this.field_174879_c, block);
    } 
  }

  
  protected NonNullList<ItemStack> func_190576_q() {
     return this.items;
  }
  
  protected void func_199721_a(NonNullList<ItemStack> p_199721_1_) {
     this.items = p_199721_1_;
  }
  
  @OnlyIn(Dist.CLIENT)
  public float func_195480_a(float p_195480_1_) {
     return MathHelper.func_219799_g(p_195480_1_, this.field_145986_n, this.field_145989_m);
  }
  
  public static int getOpenCount(IBlockReader p_195481_0_, BlockPos p_195481_1_) {
     BlockState blockstate = p_195481_0_.getBlockState(p_195481_1_);
     if (blockstate.hasTileEntity()) {
       TileEntity tileentity = p_195481_0_.getTileEntity(p_195481_1_);
       if (tileentity instanceof BetaChestTileEntity) {
         return ((BetaChestTileEntity)tileentity).field_145987_o;
      }
    } 
    
     return 0;
  }
  
  public static void swapContents(BetaChestTileEntity p_199722_0_, BetaChestTileEntity p_199722_1_) {
     NonNullList<ItemStack> nonnulllist = p_199722_0_.func_190576_q();
     p_199722_0_.func_199721_a(p_199722_1_.func_190576_q());
     p_199722_1_.func_199721_a(nonnulllist);
  }
  
  protected Container func_213906_a(int p_213906_1_, PlayerInventory p_213906_2_) {
     return (Container)ChestContainer.func_216992_a(p_213906_1_, p_213906_2_, (IInventory)this);
  }

  
  public void func_145836_u() {
     super.func_145836_u();
     if (this.chestHandler != null) {
       this.chestHandler.invalidate();
       this.chestHandler = null;
    } 
  }

  
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
     if (!this.field_145846_f && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
       if (this.chestHandler == null)
         this.chestHandler = LazyOptional.of(this::createHandler); 
       return this.chestHandler.cast();
    } 
     return super.getCapability(cap, side);
  }
  
  private IItemHandlerModifiable createHandler() {
     BlockState state = func_195044_w();
     if (!(state.getBlock() instanceof ChestBlock)) {
       return (IItemHandlerModifiable)new InvWrapper((IInventory)this);
    }
     IInventory inv = ChestBlock.func_226916_a_((ChestBlock)state.getBlock(), state, getWorld(), getPos(), true);
     return (IItemHandlerModifiable)new InvWrapper((inv == null) ? (IInventory)this : inv);
  }

  
  protected void invalidateCaps() {
     super.invalidateCaps();
     if (this.chestHandler != null)
       this.chestHandler.invalidate(); 
  }
}


