package com.swdteam.client.render.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.swdteam.common.block.tardis.RoundelDoorBlock;
import com.swdteam.common.tileentity.tardis.RoundelDoorTileEntity;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class RenderRoundelDoor
  extends TileEntityRenderer<RoundelDoorTileEntity> {
  public RenderRoundelDoor(TileEntityRendererDispatcher dispatcher) {
     super(dispatcher);
  }



  
  public void render(RoundelDoorTileEntity tile, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
     Block block = tile.getStateToRender().getBlock();
    
     Block blockSides = tile.getStateToRenderTop().getBlock();
     if (tile.getWorld().getBlockState(tile.getPos()).getBlock() instanceof RoundelDoorBlock) {
       matrix.func_227860_a_();
       float dir = -180.0F + ((Direction)tile.getWorld().getBlockState(tile.getPos()).get((Property)RoundelDoorBlock.FACING)).func_185119_l();
      
       DoorHingeSide hinge = (DoorHingeSide)tile.func_195044_w().get((Property)RoundelDoorBlock.HINGE);
      
       float maxRot = 105.0F;
      
       matrix.func_227861_a_(0.5D, 0.0D, 0.5D);
       matrix.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_(dir));
       matrix.func_227861_a_(-0.5D, 0.0D, -0.5D);
       matrix.func_227861_a_(0.0D, 0.0D, 0.125D);
      
       if (hinge == DoorHingeSide.LEFT) {
         matrix.func_227861_a_(1.0D, 0.0D, 0.0D);
         matrix.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_(tile.getDoorRotation() * maxRot));
         matrix.func_227861_a_(-1.0D, 0.0D, 0.0D);
      } else {
         matrix.func_227863_a_(Vector3f.field_229180_c_.func_229187_a_(-(tile.getDoorRotation() * maxRot)));
      } 
      
       IBakedModel ta = Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178125_b(block.getDefaultState());
       IBakedModel ta1 = Minecraft.func_71410_x().func_175602_ab().func_175023_a().func_178125_b(blockSides.getDefaultState());

      
       IVertexBuilder builder = buffer.getBuffer(RenderType.func_228638_b_(AtlasTexture.field_110575_b));
       MatrixStack.Entry entry = matrix.func_227866_c_();
      
       TextureAtlasSprite sprite = getTexture(ta, block.getDefaultState(), Direction.NORTH, (tile.getWorld()).rand);
      
       float U0 = sprite.func_94209_e();
       float V0 = sprite.func_94206_g();
       float U1 = sprite.func_94212_f();
       float V1 = sprite.func_94210_h();
      
       drawQuad(entry, builder, 1.0F, 1.0F, U0, V0, U1, V1, tile.getWorld(), tile.getPos(), dir);
      
       sprite = getTexture(ta1, blockSides.getDefaultState(), Direction.NORTH, (tile.getWorld()).rand);
      
       U0 = sprite.func_94209_e();
       V0 = sprite.func_94206_g() + 0.0098125F;
       U1 = sprite.func_94212_f();
       V1 = sprite.func_94210_h() + 0.0098125F;


      
       matrix.func_227861_a_(0.0D, 1.0D, 0.0D);
       matrix.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(90.0F));
      
       drawQuad(entry, builder, 1.0F, 0.375F, U0, V0, U1, V0 + (V1 - V0) * 0.375F, tile.getWorld(), tile.getPos(), dir);
      
       matrix.func_227863_a_(Vector3f.field_229178_a_.func_229187_a_(90.0F));
       matrix.func_227861_a_(0.0D, -1.0D, 0.0D);
      
       U0 = sprite.func_94209_e();
       V0 = sprite.func_94206_g();
       U1 = sprite.func_94212_f();
       V1 = sprite.func_94210_h();
      
       matrix.func_227861_a_(0.5D, 0.0D, 0.375D);
       matrix.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(90.0F));
       matrix.func_227861_a_(-0.0D, 0.0D, -0.5D);
      
       drawQuad(entry, builder, 0.375F, 1.0F, U0, V0, U0 + (U1 - U0) * 0.375F, V1, tile.getWorld(), tile.getPos(), dir + 270.0F);
      
       matrix.func_227861_a_(0.5D, 0.0D, 0.5D);
       matrix.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(90.0F));
       matrix.func_227861_a_(-0.5D, 0.0D, -0.5D);
      
       sprite = getTexture(ta, block.getDefaultState(), Direction.NORTH, (tile.getWorld()).rand);
      
       U0 = sprite.func_94209_e();
       V0 = sprite.func_94206_g();
       U1 = sprite.func_94212_f();
       V1 = sprite.func_94210_h();
      
       drawQuad(entry, builder, 1.0F, 1.0F, U0, V0, U1, V1, tile.getWorld(), tile.getPos(), dir + 180.0F);
      
       sprite = getTexture(ta1, blockSides.getDefaultState(), Direction.NORTH, (tile.getWorld()).rand);
      
       U0 = sprite.func_94209_e();
       V0 = sprite.func_94206_g();
       U1 = sprite.func_94212_f();
       V1 = sprite.func_94210_h();
      
       matrix.func_227861_a_(0.5D, 0.0D, 0.375D);
       matrix.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(90.0F));
       matrix.func_227861_a_(0.0D, 0.0D, -0.5D);
      
       drawQuad(entry, builder, 0.375F, 1.0F, U0 + (U1 - U0) * 0.375F, V0, U0, V1, tile.getWorld(), tile.getPos(), dir + 90.0F);






















































      
       matrix.func_227865_b_();
    } 
  }


  
  public final int getPackedLight(World world, BlockPos pos) {
     int blockLight = world.func_226658_a_(LightType.BLOCK, pos);
     int skyLight = world.func_226658_a_(LightType.SKY, pos);
     return LightTexture.func_228451_a_(blockLight, skyLight);
  }
  
  public void drawQuad(MatrixStack.Entry entry, IVertexBuilder builder, float w, float h, float U0, float V0, float U1, float V1, World world, BlockPos pos, float dir) {
     float r = dir + 100.0F;
     float xn = (float)Math.cos(Math.toRadians(r));
     float yn = 0.0F;
     float zn = (float)Math.sin(Math.toRadians(r));
    
     int lit0 = getPackedLight(world, pos);
    
     int lit1 = world.getBlockState(pos.func_177984_a()).func_200132_m() ? lit0 : getPackedLight(world, pos.func_177984_a());
    
     drawVertex(entry, builder, 0.0F, 0.0F, 0.0F, xn, yn, zn, lit0, U1, V1);
     drawVertex(entry, builder, 0.0F, h, 0.0F, xn, yn, zn, lit1, U1, V0);
     drawVertex(entry, builder, w, h, 0.0F, xn, yn, zn, lit1, U0, V0);
     drawVertex(entry, builder, w, 0.0F, 0.0F, xn, yn, zn, lit0, U0, V1);
  }
  
  public void drawVertex(MatrixStack.Entry entry, IVertexBuilder builder, float x, float y, float z, float nx, float ny, float nz, int combinedLightIn, float u, float v) {
     Matrix4f matrix = entry.func_227870_a_();
     Matrix3f normals = entry.func_227872_b_();
     builder.func_227888_a_(matrix, x, y, z).func_225586_a_(250, 255, 255, 255).func_225583_a_(u, v).func_227891_b_(OverlayTexture.field_229196_a_).func_227886_a_(combinedLightIn).func_227887_a_(normals, nx, nz, ny).func_181675_d();
  }

  
  private TextureAtlasSprite getTexture(IBakedModel ibakedmodel, BlockState state, Direction facing, Random rand) {
     List<BakedQuad> quadList = ibakedmodel.func_200117_a(state, facing, rand);
     TextureAtlasSprite sprite = quadList.isEmpty() ? ibakedmodel.func_177554_e() : ((BakedQuad)quadList.get(0)).func_187508_a();
     return sprite;
  }
}


