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
/*    */ 
/*    */ public class PrematureChannelClosureException
/*    */   extends CodecException
/*    */ {
/*    */   private static final long serialVersionUID = 4907642202594703094L;
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
/*    */   public PrematureChannelClosureException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PrematureChannelClosureException(String paramString, Throwable paramThrowable)
/*    */   {
/* 38 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public PrematureChannelClosureException(String paramString)
/*    */   {
/* 45 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public PrematureChannelClosureException(Throwable paramThrowable)
/*    */   {
/* 52 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\PrematureChannelClosureException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */