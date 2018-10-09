/*    */ package org.apache.commons.lang;
/*    */ 
/*    */ import org.apache.commons.lang.exception.NestableRuntimeException;
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
/*    */ public class UnhandledException
/*    */   extends NestableRuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1832101364842773720L;
/*    */   
/*    */   public UnhandledException(Throwable cause)
/*    */   {
/* 60 */     super(cause);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public UnhandledException(String message, Throwable cause)
/*    */   {
/* 70 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang\UnhandledException.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */