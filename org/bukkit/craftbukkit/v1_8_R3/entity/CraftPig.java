/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityPig;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftPig extends CraftAnimals implements org.bukkit.entity.Pig
/*    */ {
/*    */   public CraftPig(CraftServer server, EntityPig entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public boolean hasSaddle() {
/* 15 */     return getHandle().hasSaddle();
/*    */   }
/*    */   
/*    */   public void setSaddle(boolean saddled) {
/* 19 */     getHandle().setSaddle(saddled);
/*    */   }
/*    */   
/*    */   public EntityPig getHandle() {
/* 23 */     return (EntityPig)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 28 */     return "CraftPig";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 32 */     return EntityType.PIG;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftPig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */