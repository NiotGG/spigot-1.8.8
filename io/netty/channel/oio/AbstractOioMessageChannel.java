/*    */ package io.netty.channel.oio;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.Channel.Unsafe;
/*    */ import io.netty.channel.ChannelConfig;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public abstract class AbstractOioMessageChannel
/*    */   extends AbstractOioChannel
/*    */ {
/* 31 */   private final List<Object> readBuf = new ArrayList();
/*    */   
/*    */   protected AbstractOioMessageChannel(Channel paramChannel) {
/* 34 */     super(paramChannel);
/*    */   }
/*    */   
/*    */   protected void doRead()
/*    */   {
/* 39 */     ChannelConfig localChannelConfig = config();
/* 40 */     ChannelPipeline localChannelPipeline = pipeline();
/* 41 */     int i = 0;
/* 42 */     int j = localChannelConfig.getMaxMessagesPerRead();
/*    */     
/* 44 */     Object localObject = null;
/* 45 */     int k = 0;
/*    */     try {
/*    */       for (;;) {
/* 48 */         k = doReadMessages(this.readBuf);
/* 49 */         if (k != 0)
/*    */         {
/*    */ 
/* 52 */           if (k < 0) {
/* 53 */             i = 1;
/*    */ 
/*    */ 
/*    */           }
/* 57 */           else if (this.readBuf.size() < j) if (!localChannelConfig.isAutoRead())
/*    */               break;
/*    */         }
/*    */       }
/*    */     } catch (Throwable localThrowable) {
/* 62 */       localObject = localThrowable;
/*    */     }
/*    */     
/* 65 */     int m = this.readBuf.size();
/* 66 */     for (int n = 0; n < m; n++) {
/* 67 */       localChannelPipeline.fireChannelRead(this.readBuf.get(n));
/*    */     }
/* 69 */     this.readBuf.clear();
/* 70 */     localChannelPipeline.fireChannelReadComplete();
/*    */     
/* 72 */     if (localObject != null) {
/* 73 */       if ((localObject instanceof IOException)) {
/* 74 */         i = 1;
/*    */       }
/*    */       
/* 77 */       pipeline().fireExceptionCaught((Throwable)localObject);
/*    */     }
/*    */     
/* 80 */     if (i != 0) {
/* 81 */       if (isOpen()) {
/* 82 */         unsafe().close(unsafe().voidPromise());
/*    */       }
/* 84 */     } else if ((k == 0) && (isActive()))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 91 */       read();
/*    */     }
/*    */   }
/*    */   
/*    */   protected abstract int doReadMessages(List<Object> paramList)
/*    */     throws Exception;
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\oio\AbstractOioMessageChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */