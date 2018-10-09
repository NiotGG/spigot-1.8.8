/*    */ package io.netty.channel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventLoopException
/*    */   extends ChannelException
/*    */ {
/*    */   private static final long serialVersionUID = -8969100344583703616L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EventLoopException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EventLoopException(String paramString, Throwable paramThrowable)
/*    */   {
/* 30 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */   public EventLoopException(String paramString) {
/* 34 */     super(paramString);
/*    */   }
/*    */   
/*    */   public EventLoopException(Throwable paramThrowable) {
/* 38 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\EventLoopException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */