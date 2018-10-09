/*    */ package com.avaje.ebeaninternal.server.querydefn;
/*    */ 
/*    */ public class NaturalKeyBindParam
/*    */ {
/*    */   private final String name;
/*    */   private final Object value;
/*    */   
/*    */   public NaturalKeyBindParam(String name, Object value)
/*    */   {
/* 10 */     this.name = name;
/* 11 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 15 */     return this.name;
/*    */   }
/*    */   
/*    */   public Object getValue() {
/* 19 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\querydefn\NaturalKeyBindParam.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */