/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntitySkeleton;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Skeleton.SkeletonType;
/*    */ 
/*    */ public class CraftSkeleton extends CraftMonster implements org.bukkit.entity.Skeleton
/*    */ {
/*    */   public CraftSkeleton(CraftServer server, EntitySkeleton entity)
/*    */   {
/* 13 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntitySkeleton getHandle()
/*    */   {
/* 18 */     return (EntitySkeleton)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 23 */     return "CraftSkeleton";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 27 */     return EntityType.SKELETON;
/*    */   }
/*    */   
/*    */   public Skeleton.SkeletonType getSkeletonType() {
/* 31 */     return Skeleton.SkeletonType.getType(getHandle().getSkeletonType());
/*    */   }
/*    */   
/*    */   public void setSkeletonType(Skeleton.SkeletonType type) {
/* 35 */     Validate.notNull(type);
/* 36 */     getHandle().setSkeletonType(type.getId());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftSkeleton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */