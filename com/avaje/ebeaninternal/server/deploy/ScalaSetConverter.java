/*    */ package com.avaje.ebeaninternal.server.deploy;
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
/*    */ public class ScalaSetConverter
/*    */   implements CollectionTypeConverter
/*    */ {
/*    */   public Object toUnderlying(Object wrapped)
/*    */   {
/* 34 */     throw new IllegalArgumentException("Scala types not supported in this build");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Object toWrapped(Object wrapped)
/*    */   {
/* 42 */     throw new IllegalArgumentException("Scala types not supported in this build");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\ScalaSetConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */