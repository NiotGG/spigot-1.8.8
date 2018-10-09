/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityWither;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftWither extends CraftMonster implements org.bukkit.entity.Wither
/*    */ {
/*    */   public CraftWither(CraftServer server, EntityWither entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityWither getHandle()
/*    */   {
/* 15 */     return (EntityWither)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "CraftWither";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 24 */     return org.bukkit.entity.EntityType.WITHER;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftWither.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */