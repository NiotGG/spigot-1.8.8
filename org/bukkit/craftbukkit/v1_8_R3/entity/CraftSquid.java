/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntitySquid;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Squid;
/*    */ 
/*    */ public class CraftSquid extends CraftWaterMob implements Squid
/*    */ {
/*    */   public CraftSquid(CraftServer server, EntitySquid entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntitySquid getHandle()
/*    */   {
/* 17 */     return (EntitySquid)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 22 */     return "CraftSquid";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.SQUID;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftSquid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */