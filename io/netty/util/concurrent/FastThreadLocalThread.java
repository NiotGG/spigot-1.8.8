/*    */ package io.netty.util.concurrent;
/*    */ 
/*    */ import io.netty.util.internal.InternalThreadLocalMap;
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
/*    */ public class FastThreadLocalThread
/*    */   extends Thread
/*    */ {
/*    */   private InternalThreadLocalMap threadLocalMap;
/*    */   
/*    */   public FastThreadLocalThread() {}
/*    */   
/*    */   public FastThreadLocalThread(Runnable paramRunnable)
/*    */   {
/* 30 */     super(paramRunnable);
/*    */   }
/*    */   
/*    */   public FastThreadLocalThread(ThreadGroup paramThreadGroup, Runnable paramRunnable) {
/* 34 */     super(paramThreadGroup, paramRunnable);
/*    */   }
/*    */   
/*    */   public FastThreadLocalThread(String paramString) {
/* 38 */     super(paramString);
/*    */   }
/*    */   
/*    */   public FastThreadLocalThread(ThreadGroup paramThreadGroup, String paramString) {
/* 42 */     super(paramThreadGroup, paramString);
/*    */   }
/*    */   
/*    */   public FastThreadLocalThread(Runnable paramRunnable, String paramString) {
/* 46 */     super(paramRunnable, paramString);
/*    */   }
/*    */   
/*    */   public FastThreadLocalThread(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString) {
/* 50 */     super(paramThreadGroup, paramRunnable, paramString);
/*    */   }
/*    */   
/*    */   public FastThreadLocalThread(ThreadGroup paramThreadGroup, Runnable paramRunnable, String paramString, long paramLong) {
/* 54 */     super(paramThreadGroup, paramRunnable, paramString, paramLong);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public final InternalThreadLocalMap threadLocalMap()
/*    */   {
/* 62 */     return this.threadLocalMap;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public final void setThreadLocalMap(InternalThreadLocalMap paramInternalThreadLocalMap)
/*    */   {
/* 70 */     this.threadLocalMap = paramInternalThreadLocalMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\FastThreadLocalThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */