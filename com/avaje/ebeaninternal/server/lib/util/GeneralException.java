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
/*    */ public class GeneralException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 5783084420007103280L;
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
/*    */   public GeneralException(Exception cause)
/*    */   {
/* 28 */     super(cause);
/*    */   }
/*    */   
/*    */   public GeneralException(String s, Exception cause) {
/* 32 */     super(s, cause);
/*    */   }
/*    */   
/*    */   public GeneralException(String s) {
/* 36 */     super(s);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\util\GeneralException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */