/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityWaterAnimal;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.WaterMob;
/*    */ 
/*    */ public class CraftWaterMob
/*    */   extends CraftLivingEntity implements WaterMob
/*    */ {
/*    */   public CraftWaterMob(CraftServer server, EntityWaterAnimal entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityWaterAnimal getHandle()
/*    */   {
/* 17 */     return (EntityWaterAnimal)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 22 */     return "CraftWaterMob";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftWaterMob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */