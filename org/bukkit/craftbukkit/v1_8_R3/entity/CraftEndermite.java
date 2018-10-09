/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityEndermite;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftEndermite extends CraftMonster implements org.bukkit.entity.Endermite
/*    */ {
/*    */   public CraftEndermite(CraftServer server, EntityEndermite entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 16 */     return "CraftEndermite";
/*    */   }
/*    */   
/*    */   public EntityType getType()
/*    */   {
/* 21 */     return EntityType.ENDERMITE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftEndermite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */