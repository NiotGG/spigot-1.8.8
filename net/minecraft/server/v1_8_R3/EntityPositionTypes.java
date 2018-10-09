/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityPositionTypes
/*    */ {
/* 14 */   private static final HashMap<Class, EntityInsentient.EnumEntityPositionType> a = ;
/*    */   
/*    */   public static EntityInsentient.EnumEntityPositionType a(Class paramClass) {
/* 17 */     return (EntityInsentient.EnumEntityPositionType)a.get(paramClass);
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 22 */     a.put(EntityBat.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 23 */     a.put(EntityChicken.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 24 */     a.put(EntityCow.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 25 */     a.put(EntityHorse.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 26 */     a.put(EntityMushroomCow.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 27 */     a.put(EntityOcelot.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 28 */     a.put(EntityPig.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 29 */     a.put(EntityRabbit.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 30 */     a.put(EntitySheep.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 31 */     a.put(EntitySnowman.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 32 */     a.put(EntitySquid.class, EntityInsentient.EnumEntityPositionType.IN_WATER);
/* 33 */     a.put(EntityIronGolem.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 34 */     a.put(EntityWolf.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/*    */     
/* 36 */     a.put(EntityVillager.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/*    */     
/* 38 */     a.put(EntityEnderDragon.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 39 */     a.put(EntityWither.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/*    */     
/* 41 */     a.put(EntityBlaze.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 42 */     a.put(EntityCaveSpider.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 43 */     a.put(EntityCreeper.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 44 */     a.put(EntityEnderman.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 45 */     a.put(EntityEndermite.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 46 */     a.put(EntityGhast.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 47 */     a.put(EntityGiantZombie.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 48 */     a.put(EntityGuardian.class, EntityInsentient.EnumEntityPositionType.IN_WATER);
/* 49 */     a.put(EntityMagmaCube.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 50 */     a.put(EntityPigZombie.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 51 */     a.put(EntitySilverfish.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 52 */     a.put(EntitySkeleton.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 53 */     a.put(EntitySlime.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 54 */     a.put(EntitySpider.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 55 */     a.put(EntityWitch.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/* 56 */     a.put(EntityZombie.class, EntityInsentient.EnumEntityPositionType.ON_GROUND);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityPositionTypes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */