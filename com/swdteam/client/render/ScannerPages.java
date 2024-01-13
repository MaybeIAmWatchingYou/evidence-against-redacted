package com.swdteam.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.swdteam.client.tardis.data.ClientTardisFlightCache;
import com.swdteam.common.init.DMTranslationKeys;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tardis.TardisFlightData;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;

public class ScannerPages {
   public static IScannerPage[] PAGES = new IScannerPage[] { new Off(), new TardisInfo(), new Location(), new FlightInfo() };
  
  public static interface IScannerPage {
    void render(MatrixStack param1MatrixStack, FontRenderer param1FontRenderer, TardisData param1TardisData);
  }
  
  private static class Off implements IScannerPage {
    private Off() {}
    
    public void render(MatrixStack matrixStack, FontRenderer font, TardisData data) {}
  }
  
  private static class TardisInfo implements IScannerPage {
    private TardisInfo() {}
    
    public void render(MatrixStack matrixStack, FontRenderer font, TardisData data) {
       TranslationTextComponent title = DMTranslationKeys.SCANNER_TITLE_TARDIS_INFO;
       font.func_243248_b(matrixStack, (ITextComponent)title, (-font.func_238414_a_((ITextProperties)title) / 2), 0.0F, -12632066);

      
       font.func_238421_b_(matrixStack, DMTranslationKeys.SCANNER_OWNER.getString() + ": ", -60.0F, 14.0F, -4276546);
       font.func_238421_b_(matrixStack, data.getOwner_name(), -60.0F, 28.0F, -1);

      
       TranslationTextComponent doorState = data.isLocked() ? DMTranslationKeys.SCANNER_DOOR_LOCKED : DMTranslationKeys.SCANNER_DOOR_UNLOCKED;
       font.func_238421_b_(matrixStack, DMTranslationKeys.SCANNER_DOOR.getString() + ": ", -60.0F, 42.0F, -4276546);
       font.func_243248_b(matrixStack, (ITextComponent)doorState, -60.0F, 56.0F, -1);

      
       font.func_238421_b_(matrixStack, DMTranslationKeys.SCANNER_DISGUISE.getString() + ": ", -60.0F, 70.0F, -4276546);
       String name = data.getTardisExterior().getData().getExteriorName().getString();
      
       if (font.func_78256_a(name) >= 120) name = font.func_238412_a_(name, 70) + "..."; 
       font.func_238421_b_(matrixStack, name, -60.0F, 84.0F, -1);
    }
  }
  
  private static class Location implements IScannerPage { private Location() {}
    
    public void render(MatrixStack matrixStack, FontRenderer font, TardisData data) {
       if (!data.isInFlight()) {
        
         TranslationTextComponent title = DMTranslationKeys.SCANNER_TITLE_LOCATION_CURRENT;
         font.func_243248_b(matrixStack, (ITextComponent)title, (-font.func_238414_a_((ITextProperties)title) / 2), 0.0F, -114690);
      } else {
        
         TranslationTextComponent title = DMTranslationKeys.SCANNER_TITLE_LOCATION_PREVIOUS;
         font.func_243248_b(matrixStack, (ITextComponent)title, (-font.func_238414_a_((ITextProperties)title) / 2), 0.0F, -114690);
        
         font.func_238421_b_(matrixStack, DMTranslationKeys.SCANNER_IN_FLIGHT.getString(), -60.0F, 84.0F, -114690);
      } 
      
       if (data.getCurrentLocation() != null) {
        
         String dimension = DMTranslationKeys.getDimensionTranslation(data.getCurrentLocation().getDimension(), false).getString();
         font.func_238421_b_(matrixStack, dimension, -60.0F, 14.0F, -16711936);
        
         font.func_238421_b_(matrixStack, "X: " + (int)data.getCurrentLocation().getPosition().func_82615_a(), -60.0F, 28.0F, -1);
        
         font.func_238421_b_(matrixStack, "Y: " + (int)data.getCurrentLocation().getPosition().func_82617_b(), -60.0F, 42.0F, -1);
        
         font.func_238421_b_(matrixStack, "Z: " + (int)data.getCurrentLocation().getPosition().func_82616_c(), -60.0F, 56.0F, -1);
        
         String facing = DMTranslationKeys.getFacingTranslation(data.getCurrentLocation().getFacing(), false).getString();
         font.func_238421_b_(matrixStack, DMTranslationKeys.SCANNER_FACING.getString() + ": " + facing, -60.0F, 70.0F, -1);
      } else {
        
         TranslationTextComponent error = DMTranslationKeys.SCANNER_ERROR;
         font.func_243248_b(matrixStack, (ITextComponent)error, (-font.func_238414_a_((ITextProperties)error) / 2), 34.0F, -114881);
      } 
    } }
  
  private static class FlightInfo implements IScannerPage {
    private FlightInfo() {}
    
    public void render(MatrixStack matrixStack, FontRenderer font, TardisData data) {
       TardisFlightData flightData = ClientTardisFlightCache.getTardisFlightData(data.getGlobalID());

      
       TranslationTextComponent title = DMTranslationKeys.SCANNER_TITLE_FLIGHT_INFO;
       font.func_243248_b(matrixStack, (ITextComponent)title, (-font.func_238414_a_((ITextProperties)title) / 2), 0.0F, -65985);

      
       String dimension = DMTranslationKeys.getDimensionTranslation(flightData.getDimension(), false).getString();
       font.func_238421_b_(matrixStack, dimension, -60.0F, 14.0F, -16711936);
      
       font.func_238421_b_(matrixStack, "X: " + flightData.getPos().getX(), -60.0F, 28.0F, -1);
      
       font.func_238421_b_(matrixStack, "Y: " + flightData.getPos().getY(), -60.0F, 42.0F, -1);
      
       font.func_238421_b_(matrixStack, "Z: " + flightData.getPos().getZ(), -60.0F, 56.0F, -1);
      
       String facing = DMTranslationKeys.getFacingTranslation(flightData.getRotationAngle(), false).getString();
       font.func_238421_b_(matrixStack, DMTranslationKeys.SCANNER_FACING.getString() + ": " + facing, -60.0F, 70.0F, -1);
    }
  }
}


