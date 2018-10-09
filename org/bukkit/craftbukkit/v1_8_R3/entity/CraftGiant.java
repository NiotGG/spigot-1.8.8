/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityGiantZombie;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Giant;
/*    */ 
/*    */ public class CraftGiant extends CraftMonster implements Giant
/*    */ {
/*    */   public CraftGiant(CraftServer server, EntityGiantZombie entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityGiantZombie getHandle()
/*    */   {
/* 17 */     return (EntityGiantZombie)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 22 */     return "CraftGiant";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.GIANT;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftGiant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */