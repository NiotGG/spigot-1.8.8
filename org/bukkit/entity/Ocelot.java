/*    */ package org.bukkit.entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface Ocelot
/*    */   extends Animals, Tameable
/*    */ {
/*    */   public abstract Type getCatType();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void setCatType(Type paramType);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract boolean isSitting();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void setSitting(boolean paramBoolean);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static enum Type
/*    */   {
/* 42 */     WILD_OCELOT(0), 
/* 43 */     BLACK_CAT(1), 
/* 44 */     RED_CAT(2), 
/* 45 */     SIAMESE_CAT(3);
/*    */     
/* 47 */     static { types = new Type[values().length];
/*    */       
/*    */       Type[] arrayOfType;
/*    */       
/* 51 */       int i = (arrayOfType = values()).length; for (int j = 0; j < i; j++) { Type type = arrayOfType[j];
/* 52 */         types[type.getId()] = type;
/*    */       }
/*    */     }
/*    */     
/*    */     private Type(int id) {
/* 57 */       this.id = id;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */     private static final Type[] types;
/*    */     
/*    */ 
/*    */     @Deprecated
/*    */     public int getId()
/*    */     {
/* 68 */       return this.id;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */     private final int id;
/*    */     
/*    */ 
/*    */     @Deprecated
/*    */     public static Type getType(int id)
/*    */     {
/* 80 */       return id >= types.length ? null : types[id];
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Ocelot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */