/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityGolem;
/*    */ 
/*    */ public class CraftGolem extends CraftCreature implements org.bukkit.entity.Golem
/*    */ {
/*    */   public CraftGolem(org.bukkit.craftbukkit.v1_8_R3.CraftServer server, EntityGolem entity)
/*    */   {
/*  9 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityGolem getHandle()
/*    */   {
/* 14 */     return (EntityGolem)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 19 */     return "CraftGolem";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */