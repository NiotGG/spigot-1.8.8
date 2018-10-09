/*    */ package io.netty.handler.codec;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CodecException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = -1464830400709348473L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CodecException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CodecException(String paramString, Throwable paramThrowable)
/*    */   {
/* 35 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CodecException(String paramString)
/*    */   {
/* 42 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public CodecException(Throwable paramThrowable)
/*    */   {
/* 49 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\CodecException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */