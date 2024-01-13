package com.swdteam.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.client.dmu.data.DMUData;
import com.swdteam.main.DalekMod;
import com.swdteam.util.Graphics;
import com.swdteam.util.IOUtil;
import java.net.URI;
import java.net.URISyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GuiDMU
  extends Screen {
  private Button updateButton;
  private Button joinServer;
   private StringTextComponent tooltip = null; private Button backBtn; public RenderSkybox panorama; public Screen previousScreen;
   public LoadState loadState = LoadState.LOADING;
  
  public DMUData serverData;
  
  private String[] messages;
  
  private boolean newUpdate = false;
   public static final ServerData SERVER = new ServerData("Dalek Mod Universe", "dmu.swdteam.com", false);
  
   public static ResourceLocation DMU_GUI = new ResourceLocation("dalekmod", "textures/gui/dmu_server.png");
  
  public GuiDMU(Screen previousScreen) {
     super((ITextComponent)new StringTextComponent("DMU Server GUI"));
     this.panorama = new RenderSkybox(MainMenuScreen.field_213098_a);
     this.previousScreen = previousScreen;
    
     (new Thread(new Runnable()
        {
          public void run() {
             String s0 = IOUtil.readFileURL("http://api.swdteam.com/dm/dm-update.json");
             if (s0 != null) {
               String[] ss = (String[])DalekMod.GSON.fromJson(s0, String[].class);
               if (ss != null && ss.length > 0 && 
                 !ss[0].equalsIgnoreCase(DalekMod.VERSION)) {
                 GuiDMU.this.newUpdate = true;
              }
            } 

            
             String s1 = IOUtil.readFileURL("http://api.swdteam.com/dm/dmu.json");
             if (s1 != null) {
               GuiDMU.this.messages = (String[])DalekMod.GSON.fromJson(s1, String[].class);
            }
            
             String s = IOUtil.readFileURL("http://dmu.swdteam.co.uk/api/scripts/getPlayersServer.php");
             if (s != null) {
              try {
                 GuiDMU.this.serverData = (DMUData)DalekMod.GSON.fromJson(s, DMUData.class);
                
                 if (GuiDMU.this.serverData != null && GuiDMU.this.serverData.getPlayersOnline() != null && GuiDMU.this.serverData.getPlayersOnline().size() > 0) {
                   GuiDMU.this.loadState = GuiDMU.LoadState.LOADED;
                } else {
                   GuiDMU.this.loadState = GuiDMU.LoadState.EMPTY;
                }
              
               } catch (Exception e) {
                 e.printStackTrace();
                 GuiDMU.this.loadState = GuiDMU.LoadState.ERROR;
              } 
            } else {
               GuiDMU.this.loadState = GuiDMU.LoadState.ERROR;
            } 
          }
         })).start();
  }

  
  public void func_231160_c_() {
     func_230480_a_((Widget)(this.joinServer = new Button(this.field_230708_k_ / 2 + 2, this.field_230709_l_ / 2 + 75, 94, 20, (ITextComponent)new StringTextComponent("Join Server"), button -> this.field_230706_i_.func_147108_a((Screen)new ConnectingScreen(this, this.field_230706_i_, SERVER)))));


    
     func_230480_a_((Widget)(this.backBtn = new Button(this.field_230708_k_ / 2 - 96, this.field_230709_l_ / 2 + 75, 94, 20, (ITextComponent)new StringTextComponent("Back"), button -> Minecraft.func_71410_x().func_147108_a(this.previousScreen))));


    
     func_230480_a_((Widget)(this.updateButton = new Button(this.field_230708_k_ / 2 - 47, this.field_230709_l_ / 2 + 20, 94, 20, (ITextComponent)new StringTextComponent("Download Update"), button -> {
            try {
              Util.func_110647_a().func_195642_a(new URI("https://www.curseforge.com/minecraft/mc-mods/the-dalek-mod/files"));
             } catch (URISyntaxException e) {
              e.printStackTrace();
            } 
          })));

    
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 - 100, this.field_230709_l_ / 2 + 40, 98, 20, (ITextComponent)new StringTextComponent("DMU Store"), button -> {
            try {
              Util.func_110647_a().func_195642_a(new URI("https://swdteam.com/DMUStore"));
             } catch (URISyntaxException e) {
              e.printStackTrace();
            } 
          }));

    
     func_230480_a_((Widget)new Button(this.field_230708_k_ / 2 + 2, this.field_230709_l_ / 2 + 40, 98, 20, (ITextComponent)new StringTextComponent("DMU Ranks"), button -> {
            try {
              Util.func_110647_a().func_195642_a(new URI("https://swdteam.com/store?q=rank"));
             } catch (URISyntaxException e) {
              e.printStackTrace();
            } 
          }));

    
     this.updateButton.field_230694_p_ = false;
     this.updateButton.field_230693_o_ = false;
    
     super.func_231160_c_();
  }

  
  public void func_231023_e_() {
     super.func_231023_e_();
     if (this.newUpdate && 
       !this.updateButton.field_230694_p_) {
       this.updateButton.field_230694_p_ = true;
       this.updateButton.field_230693_o_ = true;
       this.joinServer.field_230693_o_ = false;
       this.joinServer.field_230694_p_ = false;
       this.backBtn.field_230690_l_ = this.updateButton.field_230690_l_;
       this.updateButton.field_230691_m_ += 22;
    } 
  }



  
  public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
     this.tooltip = null;
     func_238467_a_(p_230430_1_, 0, 0, this.field_230708_k_, this.field_230709_l_, -1);
    
     this.panorama.func_217623_a(p_230430_4_, MathHelper.func_76131_a(1.0F, 0.0F, 1.0F));
    
     (Minecraft.func_71410_x()).field_71446_o.func_110577_a(DMU_GUI);
     func_238474_b_(p_230430_1_, this.field_230708_k_ / 2 - 128, this.field_230709_l_ / 2 - 99, 0, 0, 256, 199);
    
     if (!this.newUpdate) {
       int playersOnline = 0;
       int maxPlayers = 0;
      
       if (this.serverData != null && this.serverData != null) {
         playersOnline = this.serverData.getOnline();
         maxPlayers = this.serverData.getMax();
      } 
      
       if (playersOnline >= maxPlayers) {
         func_238471_a_(p_230430_1_, this.field_230712_o_, "Server is full.", this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 74, -1);
      } else {
         func_238471_a_(p_230430_1_, this.field_230712_o_, "Players Online (" + playersOnline + "/" + maxPlayers + ")", this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 74, -1);
      } 
      
       if (this.loadState == LoadState.LOADING) {
         func_238471_a_(p_230430_1_, this.field_230712_o_, "Loading...", this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 8, -664254);
       } else if (this.loadState == LoadState.ERROR) {
         func_238471_a_(p_230430_1_, this.field_230712_o_, "Please try again later", this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 8, -664254);
       } else if (this.loadState == LoadState.EMPTY) {
         func_238471_a_(p_230430_1_, this.field_230712_o_, "No players online", this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 8, -664254);
      } else {
         int size = this.serverData.getMax();
         if (playersOnline > maxPlayers) {
           size = playersOnline;
        }
         if (size > 60) {
           size = 60;
        }

        
         for (int i = 0; i < size; i++) {
          
           int x = this.field_230708_k_ / 2 - 98 + i % 10 * 20;
           int y = this.field_230709_l_ / 2 - 55 + i / 10 * 20;
           boolean valid = true;
           if (this.serverData.getPlayersOnline() == null || (this.serverData.getPlayersOnline() != null && i >= this.serverData.getPlayersOnline().size())) {
             ResourceLocation tex = Graphics.getPlayerHead("dmu_placeholder_def_skin_" + i);
             (Minecraft.func_71410_x()).field_71446_o.func_110577_a(tex);
             valid = false;
          } else {
             if (p_230430_2_ >= x && p_230430_2_ <= x + 16 && p_230430_3_ >= y && p_230430_3_ <= y + 16) {
               this.tooltip = new StringTextComponent(((DMUData.Player)this.serverData.getPlayersOnline().get(i)).getUsername());
            }
            
             ResourceLocation tex = Graphics.getPlayerHead(((DMUData.Player)this.serverData.getPlayersOnline().get(i)).getUsername());
             (Minecraft.func_71410_x()).field_71446_o.func_110577_a(tex);
          } 
          
           RenderSystem.enableBlend();
           RenderSystem.color4f(1.0F, 1.0F, 1.0F, valid ? 1.0F : 0.35F);
           this; func_238463_a_(p_230430_1_, x, y, 0.0F, 0.0F, 16, 16, 16, 16);
           RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
           RenderSystem.disableBlend();
        } 
      } 
      
       if (this.messages != null) {
         func_238467_a_(p_230430_1_, 0, this.field_230709_l_ / 2 - 60, this.field_230708_k_, this.field_230709_l_ / 2 - 48 + this.messages.length * 10, -1728053248);
         for (int i = 0; i < this.messages.length; i++) {
           func_238471_a_(p_230430_1_, this.field_230712_o_, this.messages[i], this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 52 + i * 10, -1);
        }
      } 
    } else {
       func_238471_a_(p_230430_1_, this.field_230712_o_, "A new update is available.", this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 36, -664254);
       func_238471_a_(p_230430_1_, this.field_230712_o_, "Please download it with the button below", this.field_230708_k_ / 2, this.field_230709_l_ / 2 - 24, -664254);
    } 
     super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
     if (this.tooltip != null)
       func_238652_a_(p_230430_1_, (ITextComponent)this.tooltip, p_230430_2_, p_230430_3_); 
  }
  
  public enum LoadState
  {
     LOADING,
     ERROR,
     LOADED,
     EMPTY;
  }
}


