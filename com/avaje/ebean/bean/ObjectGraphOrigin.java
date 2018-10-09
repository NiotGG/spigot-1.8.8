/*    */ package com.avaje.ebean.bean;
/*    */ 
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
/*    */ public final class ObjectGraphOrigin
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 410937765287968707L;
/*    */   private final CallStack callStack;
/*    */   private final String key;
/*    */   private final String beanType;
/*    */   
/*    */   public ObjectGraphOrigin(int queryHash, CallStack callStack, String beanType)
/*    */   {
/* 45 */     this.callStack = callStack;
/* 46 */     this.beanType = beanType;
/* 47 */     this.key = callStack.getOriginKey(queryHash);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getKey()
/*    */   {
/* 55 */     return this.key;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getBeanType()
/*    */   {
/* 62 */     return this.beanType;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CallStack getCallStack()
/*    */   {
/* 69 */     return this.callStack;
/*    */   }
/*    */   
/*    */   public String getFirstStackElement() {
/* 73 */     return this.callStack.getFirstStackTraceElement().toString();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 77 */     return this.key + " " + this.beanType + " " + this.callStack.getFirstStackTraceElement();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\ObjectGraphOrigin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */