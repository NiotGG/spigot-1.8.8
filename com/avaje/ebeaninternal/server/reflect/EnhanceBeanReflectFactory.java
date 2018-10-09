/*    */ package com.avaje.ebeaninternal.server.reflect;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EnhanceBeanReflectFactory
/*    */   implements BeanReflectFactory
/*    */ {
/*    */   public BeanReflect create(Class<?> vanillaType, Class<?> entityBeanType)
/*    */   {
/* 10 */     return new EnhanceBeanReflect(vanillaType, entityBeanType);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\reflect\EnhanceBeanReflectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */