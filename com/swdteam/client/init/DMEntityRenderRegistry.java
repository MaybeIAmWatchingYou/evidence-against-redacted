package com.swdteam.client.init;

import com.swdteam.common.init.DMEntities;
import com.swdteam.main.DalekMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;















public class DMEntityRenderRegistry
{
  public static void registryEntityRenders() {
     DalekMod.LOGGER.info("Registering Entity Renders");
     registerRender((EntityType<Entity>)DMEntities.DALEK_ENTITY.get(), com.swdteam.client.render.entity.RenderDalek::new);
     registerRender((EntityType<Entity>)DMEntities.LASER_ENTITY.get(), com.swdteam.client.render.entity.RenderLaser::new);
     registerRender((EntityType<Entity>)DMEntities.CYBERMAN_ENTITY.get(), com.swdteam.client.render.entity.RenderCyberman::new);
     registerRender((EntityType<Entity>)DMEntities.CYBERMAT_ENTITY.get(), com.swdteam.client.render.entity.RenderCybermat::new);
     registerRender((EntityType<Entity>)DMEntities.CYBERMANVILLAGER_ENTITY.get(), com.swdteam.client.render.entity.RenderCybervillager::new);
     registerRender((EntityType<Entity>)DMEntities.OOD_ENTITY.get(), com.swdteam.client.render.entity.RenderOod::new);
     registerRender((EntityType<Entity>)DMEntities.AUTON_ENTITY.get(), com.swdteam.client.render.entity.RenderAuton::new);
     registerRender((EntityType<Entity>)DMEntities.CLASSIC_ITEM.get(), com.swdteam.client.render.entity.RenderClassicItem::new);
     registerRender((EntityType<Entity>)DMEntities.CLASSIC_SPIDER_ENTITY.get(), com.swdteam.client.render.entity.RenderClassicSpider::new);
     registerRender((EntityType<Entity>)DMEntities.CLASSIC_SKELETON_ENTITY.get(), com.swdteam.client.render.entity.RenderClassicSkeleton::new);
     registerRender((EntityType<Entity>)DMEntities.WEEPING_ANGEL_ENTITY.get(), com.swdteam.client.render.entity.RenderWeepingAngel::new);
     registerRender((EntityType<Entity>)DMEntities.KERBLAM_MAN.get(), com.swdteam.client.render.entity.RenderKerblamMan::new);
     registerRender((EntityType<Entity>)DMEntities.CYBERDRONE.get(), com.swdteam.client.render.entity.RenderCyberdrone::new);
  }
  
  public static <T extends Entity> void registerRender(EntityType<T> entityClass, IRenderFactory<? super T> renderFactory) {
     RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
  }
}


