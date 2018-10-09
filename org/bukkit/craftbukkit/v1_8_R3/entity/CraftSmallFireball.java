/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntitySmallFireball;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftSmallFireball extends CraftFireball implements org.bukkit.entity.SmallFireball
/*    */ {
/*    */   public CraftSmallFireball(CraftServer server, EntitySmallFireball entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntitySmallFireball getHandle()
/*    */   {
/* 15 */     return (EntitySmallFireball)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "CraftSmallFireball";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 24 */     return org.bukkit.entity.EntityType.SMALL_FIREBALL;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftSmallFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */