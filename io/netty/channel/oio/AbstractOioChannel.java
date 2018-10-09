/*     */ package io.netty.channel.oio;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.AbstractChannel.AbstractUnsafe;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.channel.ThreadPerChannelEventLoop;
/*     */ import java.net.ConnectException;
/*     */ import java.net.SocketAddress;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractOioChannel
/*     */   extends AbstractChannel
/*     */ {
/*     */   protected static final int SO_TIMEOUT = 1000;
/*     */   private volatile boolean readPending;
/*  36 */   private final Runnable readTask = new Runnable()
/*     */   {
/*     */     public void run() {
/*  39 */       if ((!AbstractOioChannel.this.isReadPending()) && (!AbstractOioChannel.this.config().isAutoRead()))
/*     */       {
/*  41 */         return;
/*     */       }
/*     */       
/*  44 */       AbstractOioChannel.this.setReadPending(false);
/*  45 */       AbstractOioChannel.this.doRead();
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */   protected AbstractOioChannel(Channel paramChannel)
/*     */   {
/*  53 */     super(paramChannel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  58 */   protected AbstractChannel.AbstractUnsafe newUnsafe() { return new DefaultOioUnsafe(null); }
/*     */   
/*     */   private final class DefaultOioUnsafe extends AbstractChannel.AbstractUnsafe {
/*  61 */     private DefaultOioUnsafe() { super(); }
/*     */     
/*     */ 
/*     */     public void connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise)
/*     */     {
/*  66 */       if ((!paramChannelPromise.setUncancellable()) || (!ensureOpen(paramChannelPromise))) {
/*  67 */         return;
/*     */       }
/*     */       try
/*     */       {
/*  71 */         boolean bool = AbstractOioChannel.this.isActive();
/*  72 */         AbstractOioChannel.this.doConnect(paramSocketAddress1, paramSocketAddress2);
/*  73 */         safeSetSuccess(paramChannelPromise);
/*  74 */         if ((!bool) && (AbstractOioChannel.this.isActive()))
/*  75 */           AbstractOioChannel.this.pipeline().fireChannelActive();
/*     */       } catch (Throwable localThrowable) {
/*     */         Object localObject;
/*  78 */         if ((localThrowable instanceof ConnectException)) {
/*  79 */           ConnectException localConnectException = new ConnectException(localThrowable.getMessage() + ": " + paramSocketAddress1);
/*  80 */           localConnectException.setStackTrace(localThrowable.getStackTrace());
/*  81 */           localObject = localConnectException;
/*     */         }
/*  83 */         safeSetFailure(paramChannelPromise, (Throwable)localObject);
/*  84 */         closeIfClosed();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean isCompatible(EventLoop paramEventLoop)
/*     */   {
/*  91 */     return paramEventLoop instanceof ThreadPerChannelEventLoop;
/*     */   }
/*     */   
/*     */ 
/*     */   protected abstract void doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */   protected void doBeginRead()
/*     */     throws Exception
/*     */   {
/* 102 */     if (isReadPending()) {
/* 103 */       return;
/*     */     }
/*     */     
/* 106 */     setReadPending(true);
/* 107 */     eventLoop().execute(this.readTask);
/*     */   }
/*     */   
/*     */   protected abstract void doRead();
/*     */   
/*     */   protected boolean isReadPending() {
/* 113 */     return this.readPending;
/*     */   }
/*     */   
/*     */   protected void setReadPending(boolean paramBoolean) {
/* 117 */     this.readPending = paramBoolean;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\oio\AbstractOioChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */