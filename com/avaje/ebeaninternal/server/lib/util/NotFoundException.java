/*    */ package com.avaje.ebeaninternal.server.lib.util;
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
/*    */ public class NotFoundException
/*    */   extends RuntimeException
/*    */ {
/*    */   static final long serialVersionUID = 7061559938704539845L;
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
/*    */   public NotFoundException(Exception cause)
/*    */   {
/* 29 */     super(cause);
/*    */   }
/*    */   
/*    */   public NotFoundException(String s, Exception cause) {
/* 33 */     super(s, cause);
/*    */   }
/*    */   
/*    */   public NotFoundException(String s) {
/* 37 */     super(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\util\NotFoundException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */