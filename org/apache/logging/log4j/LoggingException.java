/*    */ package org.apache.logging.log4j;
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
/*    */ public class LoggingException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 6366395965071580537L;
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
/*    */   public LoggingException(String paramString)
/*    */   {
/* 35 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LoggingException(String paramString, Throwable paramThrowable)
/*    */   {
/* 45 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public LoggingException(Throwable paramThrowable)
/*    */   {
/* 54 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\LoggingException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */