/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*    */ 
/*    */ public abstract class CraftComplexLivingEntity extends CraftLivingEntity implements org.bukkit.entity.ComplexLivingEntity
/*    */ {
/*    */   public CraftComplexLivingEntity(org.bukkit.craftbukkit.v1_8_R3.CraftServer server, EntityLiving entity)
/*    */   {
/*  9 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityLiving getHandle()
/*    */   {
/* 14 */     return (EntityLiving)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 19 */     return "CraftComplexLivingEntity";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftComplexLivingEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */