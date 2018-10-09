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
/*    */ public class CreateObjectException
/*    */   extends RuntimeException
/*    */ {
/*    */   static final long serialVersionUID = 7061559938704539736L;
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
/*    */   public CreateObjectException(Exception cause)
/*    */   {
/* 29 */     super(cause);
/*    */   }
/*    */   
/*    */   public CreateObjectException(String s, Exception cause) {
/* 33 */     super(s, cause);
/*    */   }
/*    */   
/*    */   public CreateObjectException(String s) {
/* 37 */     super(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\util\CreateObjectException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */