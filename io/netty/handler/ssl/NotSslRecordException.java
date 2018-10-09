/*    */ package io.netty.handler.ssl;
/*    */ 
/*    */ import javax.net.ssl.SSLException;
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
/*    */ public class NotSslRecordException
/*    */   extends SSLException
/*    */ {
/*    */   private static final long serialVersionUID = -4316784434770656841L;
/*    */   
/*    */   public NotSslRecordException()
/*    */   {
/* 33 */     super("");
/*    */   }
/*    */   
/*    */   public NotSslRecordException(String paramString) {
/* 37 */     super(paramString);
/*    */   }
/*    */   
/*    */   public NotSslRecordException(Throwable paramThrowable) {
/* 41 */     super(paramThrowable);
/*    */   }
/*    */   
/*    */   public NotSslRecordException(String paramString, Throwable paramThrowable) {
/* 45 */     super(paramString, paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\NotSslRecordException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */