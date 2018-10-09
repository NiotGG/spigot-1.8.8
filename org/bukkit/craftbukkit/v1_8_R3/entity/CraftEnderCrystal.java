/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftEnderCrystal extends CraftEntity implements org.bukkit.entity.EnderCrystal
/*    */ {
/*    */   public CraftEnderCrystal(CraftServer server, EntityEnderCrystal entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityEnderCrystal getHandle()
/*    */   {
/* 15 */     return (EntityEnderCrystal)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "CraftEnderCrystal";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 24 */     return org.bukkit.entity.EntityType.ENDER_CRYSTAL;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftEnderCrystal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */