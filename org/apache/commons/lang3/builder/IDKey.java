/*    */ package org.apache.commons.lang3.builder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class IDKey
/*    */ {
/*    */   private final Object value;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private final int id;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IDKey(Object paramObject)
/*    */   {
/* 41 */     this.id = System.identityHashCode(paramObject);
/*    */     
/*    */ 
/*    */ 
/* 45 */     this.value = paramObject;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 54 */     return this.id;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 64 */     if (!(paramObject instanceof IDKey)) {
/* 65 */       return false;
/*    */     }
/* 67 */     IDKey localIDKey = (IDKey)paramObject;
/* 68 */     if (this.id != localIDKey.id) {
/* 69 */       return false;
/*    */     }
/*    */     
/* 72 */     return this.value == localIDKey.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\IDKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */