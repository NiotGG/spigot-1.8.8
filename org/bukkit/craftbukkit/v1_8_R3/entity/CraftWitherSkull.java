/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityWitherSkull;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftWitherSkull extends CraftFireball implements org.bukkit.entity.WitherSkull
/*    */ {
/*    */   public CraftWitherSkull(CraftServer server, EntityWitherSkull entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public void setCharged(boolean charged)
/*    */   {
/* 15 */     getHandle().setCharged(charged);
/*    */   }
/*    */   
/*    */   public boolean isCharged()
/*    */   {
/* 20 */     return getHandle().isCharged();
/*    */   }
/*    */   
/*    */   public EntityWitherSkull getHandle()
/*    */   {
/* 25 */     return (EntityWitherSkull)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 30 */     return "CraftWitherSkull";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 34 */     return org.bukkit.entity.EntityType.WITHER_SKULL;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftWitherSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */