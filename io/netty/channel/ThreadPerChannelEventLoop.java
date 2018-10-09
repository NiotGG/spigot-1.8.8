/*    */ package io.netty.channel;
/*    */ 
/*    */ import java.util.Queue;
/*    */ import java.util.Set;
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
/*    */ public class ThreadPerChannelEventLoop
/*    */   extends SingleThreadEventLoop
/*    */ {
/*    */   private final ThreadPerChannelEventLoopGroup parent;
/*    */   private Channel ch;
/*    */   
/*    */   public ThreadPerChannelEventLoop(ThreadPerChannelEventLoopGroup paramThreadPerChannelEventLoopGroup)
/*    */   {
/* 29 */     super(paramThreadPerChannelEventLoopGroup, paramThreadPerChannelEventLoopGroup.threadFactory, true);
/* 30 */     this.parent = paramThreadPerChannelEventLoopGroup;
/*    */   }
/*    */   
/*    */   public ChannelFuture register(Channel paramChannel, ChannelPromise paramChannelPromise)
/*    */   {
/* 35 */     super.register(paramChannel, paramChannelPromise).addListener(new ChannelFutureListener()
/*    */     {
/*    */       public void operationComplete(ChannelFuture paramAnonymousChannelFuture) throws Exception {
/* 38 */         if (paramAnonymousChannelFuture.isSuccess()) {
/* 39 */           ThreadPerChannelEventLoop.this.ch = paramAnonymousChannelFuture.channel();
/*    */         } else {
/* 41 */           ThreadPerChannelEventLoop.this.deregister();
/*    */         }
/*    */       }
/*    */     });
/*    */   }
/*    */   
/*    */   protected void run()
/*    */   {
/*    */     for (;;) {
/* 50 */       Runnable localRunnable = takeTask();
/* 51 */       if (localRunnable != null) {
/* 52 */         localRunnable.run();
/* 53 */         updateLastExecutionTime();
/*    */       }
/*    */       
/* 56 */       Channel localChannel = this.ch;
/* 57 */       if (isShuttingDown()) {
/* 58 */         if (localChannel != null) {
/* 59 */           localChannel.unsafe().close(localChannel.unsafe().voidPromise());
/*    */         }
/* 61 */         if (confirmShutdown()) {
/*    */           break;
/*    */         }
/*    */       }
/* 65 */       else if (localChannel != null)
/*    */       {
/* 67 */         if (!localChannel.isRegistered()) {
/* 68 */           runAllTasks();
/* 69 */           deregister();
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   protected void deregister()
/*    */   {
/* 77 */     this.ch = null;
/* 78 */     this.parent.activeChildren.remove(this);
/* 79 */     this.parent.idleChildren.add(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\ThreadPerChannelEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */