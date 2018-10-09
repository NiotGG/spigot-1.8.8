/*    */ package org.bukkit.entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface Skeleton
/*    */   extends Monster
/*    */ {
/*    */   public abstract SkeletonType getSkeletonType();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void setSkeletonType(SkeletonType paramSkeletonType);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static enum SkeletonType
/*    */   {
/* 26 */     NORMAL(0), 
/* 27 */     WITHER(1);
/*    */     
/* 29 */     static { types = new SkeletonType[values().length];
/*    */       
/*    */       SkeletonType[] arrayOfSkeletonType;
/*    */       
/* 33 */       int i = (arrayOfSkeletonType = values()).length; for (int j = 0; j < i; j++) { SkeletonType type = arrayOfSkeletonType[j];
/* 34 */         types[type.getId()] = type;
/*    */       }
/*    */     }
/*    */     
/*    */     private SkeletonType(int id) {
/* 39 */       this.id = id;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */     private static final SkeletonType[] types;
/*    */     
/*    */ 
/*    */     @Deprecated
/*    */     public int getId()
/*    */     {
/* 50 */       return this.id;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */     private final int id;
/*    */     
/*    */ 
/*    */     @Deprecated
/*    */     public static SkeletonType getType(int id)
/*    */     {
/* 62 */       return id >= types.length ? null : types[id];
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Skeleton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */