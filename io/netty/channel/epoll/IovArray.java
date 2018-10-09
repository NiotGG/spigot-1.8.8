/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelOutboundBuffer.MessageProcessor;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ final class IovArray
/*     */   implements ChannelOutboundBuffer.MessageProcessor
/*     */ {
/*  44 */   private static final int ADDRESS_SIZE = PlatformDependent.addressSize();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  50 */   private static final int IOV_SIZE = 2 * ADDRESS_SIZE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  55 */   private static final int CAPACITY = Native.IOV_MAX * IOV_SIZE;
/*     */   
/*  57 */   private static final FastThreadLocal<IovArray> ARRAY = new FastThreadLocal()
/*     */   {
/*     */     protected IovArray initialValue() throws Exception {
/*  60 */       return new IovArray(null);
/*     */     }
/*     */     
/*     */     protected void onRemoval(IovArray paramAnonymousIovArray)
/*     */       throws Exception
/*     */     {
/*  66 */       PlatformDependent.freeMemory(paramAnonymousIovArray.memoryAddress);
/*     */     }
/*     */   };
/*     */   private final long memoryAddress;
/*     */   private int count;
/*     */   private long size;
/*     */   
/*     */   private IovArray()
/*     */   {
/*  75 */     this.memoryAddress = PlatformDependent.allocateMemory(CAPACITY);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean add(ByteBuf paramByteBuf)
/*     */   {
/*  83 */     if (this.count == Native.IOV_MAX)
/*     */     {
/*  85 */       return false;
/*     */     }
/*     */     
/*  88 */     int i = paramByteBuf.readableBytes();
/*  89 */     if (i == 0)
/*     */     {
/*     */ 
/*     */ 
/*  93 */       return true;
/*     */     }
/*     */     
/*  96 */     long l1 = paramByteBuf.memoryAddress();
/*  97 */     int j = paramByteBuf.readerIndex();
/*     */     
/*  99 */     long l2 = memoryAddress(this.count++);
/* 100 */     long l3 = l2 + ADDRESS_SIZE;
/*     */     
/* 102 */     if (ADDRESS_SIZE == 8)
/*     */     {
/* 104 */       PlatformDependent.putLong(l2, l1 + j);
/* 105 */       PlatformDependent.putLong(l3, i);
/*     */     } else {
/* 107 */       assert (ADDRESS_SIZE == 4);
/* 108 */       PlatformDependent.putInt(l2, (int)l1 + j);
/* 109 */       PlatformDependent.putInt(l3, i);
/*     */     }
/*     */     
/* 112 */     this.size += i;
/* 113 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   long processWritten(int paramInt, long paramLong)
/*     */   {
/* 121 */     long l1 = memoryAddress(paramInt);
/* 122 */     long l2 = l1 + ADDRESS_SIZE;
/* 123 */     if (ADDRESS_SIZE == 8)
/*     */     {
/* 125 */       l3 = PlatformDependent.getLong(l2);
/* 126 */       if (l3 > paramLong) {
/* 127 */         long l4 = PlatformDependent.getLong(l1);
/* 128 */         PlatformDependent.putLong(l1, l4 + paramLong);
/* 129 */         PlatformDependent.putLong(l2, l3 - paramLong);
/* 130 */         return -1L;
/*     */       }
/* 132 */       return l3;
/*     */     }
/* 134 */     assert (ADDRESS_SIZE == 4);
/* 135 */     long l3 = PlatformDependent.getInt(l2);
/* 136 */     if (l3 > paramLong) {
/* 137 */       int i = PlatformDependent.getInt(l1);
/* 138 */       PlatformDependent.putInt(l1, (int)(i + paramLong));
/* 139 */       PlatformDependent.putInt(l2, (int)(l3 - paramLong));
/* 140 */       return -1L;
/*     */     }
/* 142 */     return l3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int count()
/*     */   {
/* 150 */     return this.count;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   long size()
/*     */   {
/* 157 */     return this.size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   long memoryAddress(int paramInt)
/*     */   {
/* 164 */     return this.memoryAddress + IOV_SIZE * paramInt;
/*     */   }
/*     */   
/*     */   public boolean processMessage(Object paramObject) throws Exception
/*     */   {
/* 169 */     return ((paramObject instanceof ByteBuf)) && (add((ByteBuf)paramObject));
/*     */   }
/*     */   
/*     */ 
/*     */   static IovArray get(ChannelOutboundBuffer paramChannelOutboundBuffer)
/*     */     throws Exception
/*     */   {
/* 176 */     IovArray localIovArray = (IovArray)ARRAY.get();
/* 177 */     localIovArray.size = 0L;
/* 178 */     localIovArray.count = 0;
/* 179 */     paramChannelOutboundBuffer.forEachFlushedMessage(localIovArray);
/* 180 */     return localIovArray;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\IovArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */