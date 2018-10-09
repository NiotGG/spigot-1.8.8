/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityCaveSpider;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftCaveSpider extends CraftSpider implements org.bukkit.entity.CaveSpider
/*    */ {
/*    */   public CraftCaveSpider(CraftServer server, EntityCaveSpider entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityCaveSpider getHandle()
/*    */   {
/* 16 */     return (EntityCaveSpider)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 21 */     return "CraftCaveSpider";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.CAVE_SPIDER;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftCaveSpider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */