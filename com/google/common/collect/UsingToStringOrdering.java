/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible(serializable=true)
/*    */ final class UsingToStringOrdering
/*    */   extends Ordering<Object>
/*    */   implements Serializable
/*    */ {
/* 30 */   static final UsingToStringOrdering INSTANCE = new UsingToStringOrdering();
/*    */   private static final long serialVersionUID = 0L;
/*    */   
/* 33 */   public int compare(Object left, Object right) { return left.toString().compareTo(right.toString()); }
/*    */   
/*    */ 
/*    */   private Object readResolve()
/*    */   {
/* 38 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 42 */     return "Ordering.usingToString()";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\UsingToStringOrdering.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */