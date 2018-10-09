/*    */ package io.netty.channel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChannelException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 2908618315971075004L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ChannelException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ChannelException(String paramString, Throwable paramThrowable)
/*    */   {
/* 35 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ChannelException(String paramString)
/*    */   {
/* 42 */     super(paramString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public ChannelException(Throwable paramThrowable)
/*    */   {
/* 49 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ChannelException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */