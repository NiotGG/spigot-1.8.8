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
/*    */ public class InvalidDataException
/*    */   extends RuntimeException
/*    */ {
/*    */   static final long serialVersionUID = 7061559938704539846L;
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
/*    */   public InvalidDataException(Exception cause)
/*    */   {
/* 29 */     super(cause);
/*    */   }
/*    */   
/*    */   public InvalidDataException(String s, Exception cause) {
/* 33 */     super(s, cause);
/*    */   }
/*    */   
/*    */   public InvalidDataException(String s) {
/* 37 */     super(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\util\InvalidDataException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */