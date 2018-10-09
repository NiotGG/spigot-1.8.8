/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockingOperationException
/*    */   extends IllegalStateException
/*    */ {
/*    */   private static final long serialVersionUID = 2462223247762460301L;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BlockingOperationException() {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BlockingOperationException(String paramString)
/*    */   {
/* 31 */     super(paramString);
/*    */   }
/*    */   
/*    */   public BlockingOperationException(Throwable paramThrowable) {
/* 35 */     super(paramThrowable);
/*    */   }
/*    */   
/*    */   public BlockingOperationException(String paramString, Throwable paramThrowable) {
/* 39 */     super(paramString, paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\BlockingOperationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */