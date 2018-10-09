/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityMonster;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.Monster;
/*    */ 
/*    */ public class CraftMonster extends CraftCreature implements Monster
/*    */ {
/*    */   public CraftMonster(CraftServer server, EntityMonster entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityMonster getHandle()
/*    */   {
/* 16 */     return (EntityMonster)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 21 */     return "CraftMonster";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMonster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */