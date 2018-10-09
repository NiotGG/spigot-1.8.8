/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityLargeFireball;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftLargeFireball extends CraftFireball implements org.bukkit.entity.LargeFireball
/*    */ {
/*    */   public CraftLargeFireball(CraftServer server, EntityLargeFireball entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public void setYield(float yield)
/*    */   {
/* 15 */     super.setYield(yield);
/* 16 */     getHandle().yield = ((int)yield);
/*    */   }
/*    */   
/*    */   public EntityLargeFireball getHandle()
/*    */   {
/* 21 */     return (EntityLargeFireball)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 26 */     return "CraftLargeFireball";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 30 */     return org.bukkit.entity.EntityType.FIREBALL;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftLargeFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */