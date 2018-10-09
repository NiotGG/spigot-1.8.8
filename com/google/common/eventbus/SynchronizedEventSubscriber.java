/*    */ package com.google.common.eventbus;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
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
/*    */ final class SynchronizedEventSubscriber
/*    */   extends EventSubscriber
/*    */ {
/*    */   public SynchronizedEventSubscriber(Object target, Method method)
/*    */   {
/* 40 */     super(target, method);
/*    */   }
/*    */   
/*    */   public void handleEvent(Object event)
/*    */     throws InvocationTargetException
/*    */   {
/* 46 */     synchronized (this) {
/* 47 */       super.handleEvent(event);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\eventbus\SynchronizedEventSubscriber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */