/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntitySilverfish;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftSilverfish extends CraftMonster implements org.bukkit.entity.Silverfish
/*    */ {
/*    */   public CraftSilverfish(CraftServer server, EntitySilverfish entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntitySilverfish getHandle()
/*    */   {
/* 16 */     return (EntitySilverfish)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 21 */     return "CraftSilverfish";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.SILVERFISH;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftSilverfish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */