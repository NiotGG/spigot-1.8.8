/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntitySpider;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Spider;
/*    */ 
/*    */ public class CraftSpider extends CraftMonster implements Spider
/*    */ {
/*    */   public CraftSpider(CraftServer server, EntitySpider entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntitySpider getHandle()
/*    */   {
/* 17 */     return (EntitySpider)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 22 */     return "CraftSpider";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.SPIDER;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftSpider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */