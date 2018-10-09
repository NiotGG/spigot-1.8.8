/*    */ package org.spigotmc;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.Entity;
/*    */ import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
/*    */ import net.minecraft.server.v1_8_R3.EntityGhast;
/*    */ import net.minecraft.server.v1_8_R3.EntityItem;
/*    */ import net.minecraft.server.v1_8_R3.EntityItemFrame;
/*    */ import net.minecraft.server.v1_8_R3.EntityPainting;
/*    */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*    */ import net.minecraft.server.v1_8_R3.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TrackingRange
/*    */ {
/*    */   public static int getEntityTrackingRange(Entity entity, int defaultRange)
/*    */   {
/* 24 */     SpigotWorldConfig config = entity.world.spigotConfig;
/* 25 */     if ((entity instanceof EntityPlayer))
/*    */     {
/* 27 */       return config.playerTrackingRange; }
/* 28 */     if (entity.activationType == 1)
/*    */     {
/* 30 */       return config.monsterTrackingRange; }
/* 31 */     if ((entity instanceof EntityGhast))
/*    */     {
/* 33 */       if (config.monsterTrackingRange > config.monsterActivationRange)
/*    */       {
/* 35 */         return config.monsterTrackingRange;
/*    */       }
/*    */       
/* 38 */       return config.monsterActivationRange;
/*    */     }
/* 40 */     if (entity.activationType == 2)
/*    */     {
/* 42 */       return config.animalTrackingRange; }
/* 43 */     if (((entity instanceof EntityItemFrame)) || ((entity instanceof EntityPainting)) || ((entity instanceof EntityItem)) || ((entity instanceof EntityExperienceOrb)))
/*    */     {
/* 45 */       return config.miscTrackingRange;
/*    */     }
/*    */     
/* 48 */     return config.otherTrackingRange;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\TrackingRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */