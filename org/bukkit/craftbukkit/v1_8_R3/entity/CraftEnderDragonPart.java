/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityComplexPart;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EnderDragon;
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ public class CraftEnderDragonPart extends CraftComplexPart implements org.bukkit.entity.EnderDragonPart
/*    */ {
/*    */   public CraftEnderDragonPart(CraftServer server, EntityComplexPart entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EnderDragon getParent()
/*    */   {
/* 17 */     return (EnderDragon)super.getParent();
/*    */   }
/*    */   
/*    */   public EntityComplexPart getHandle()
/*    */   {
/* 22 */     return (EntityComplexPart)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 27 */     return "CraftEnderDragonPart";
/*    */   }
/*    */   
/*    */   public void damage(double amount) {
/* 31 */     getParent().damage(amount);
/*    */   }
/*    */   
/*    */   public void damage(double amount, Entity source) {
/* 35 */     getParent().damage(amount, source);
/*    */   }
/*    */   
/*    */   public double getHealth() {
/* 39 */     return getParent().getHealth();
/*    */   }
/*    */   
/*    */   public void setHealth(double health) {
/* 43 */     getParent().setHealth(health);
/*    */   }
/*    */   
/*    */   public double getMaxHealth() {
/* 47 */     return getParent().getMaxHealth();
/*    */   }
/*    */   
/*    */   public void setMaxHealth(double health) {
/* 51 */     getParent().setMaxHealth(health);
/*    */   }
/*    */   
/*    */   public void resetMaxHealth() {
/* 55 */     getParent().resetMaxHealth();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftEnderDragonPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */