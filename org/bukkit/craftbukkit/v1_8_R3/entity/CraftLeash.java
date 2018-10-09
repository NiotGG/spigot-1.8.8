/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityLeash;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftLeash extends CraftHanging implements org.bukkit.entity.LeashHitch
/*    */ {
/*    */   public CraftLeash(CraftServer server, EntityLeash entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityLeash getHandle()
/*    */   {
/* 16 */     return (EntityLeash)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 21 */     return "CraftLeash";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.LEASH_HITCH;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftLeash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */