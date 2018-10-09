/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityFlying;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class CraftFlying extends CraftLivingEntity implements org.bukkit.entity.Flying
/*    */ {
/*    */   public CraftFlying(CraftServer server, EntityFlying entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityFlying getHandle()
/*    */   {
/* 15 */     return (EntityFlying)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 20 */     return "CraftFlying";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftFlying.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */