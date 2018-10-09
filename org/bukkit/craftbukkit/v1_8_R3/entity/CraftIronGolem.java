/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityIronGolem;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftIronGolem extends CraftGolem implements org.bukkit.entity.IronGolem
/*    */ {
/*    */   public CraftIronGolem(CraftServer server, EntityIronGolem entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityIronGolem getHandle()
/*    */   {
/* 15 */     return (EntityIronGolem)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "CraftIronGolem";
/*    */   }
/*    */   
/*    */   public boolean isPlayerCreated() {
/* 24 */     return getHandle().isPlayerCreated();
/*    */   }
/*    */   
/*    */   public void setPlayerCreated(boolean playerCreated) {
/* 28 */     getHandle().setPlayerCreated(playerCreated);
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType()
/*    */   {
/* 33 */     return org.bukkit.entity.EntityType.IRON_GOLEM;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftIronGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */