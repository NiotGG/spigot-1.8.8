/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class AdaptiveRecvByteBufAllocator
/*     */   implements RecvByteBufAllocator
/*     */ {
/*     */   static final int DEFAULT_MINIMUM = 64;
/*     */   static final int DEFAULT_INITIAL = 1024;
/*     */   static final int DEFAULT_MAXIMUM = 65536;
/*     */   private static final int INDEX_INCREMENT = 4;
/*     */   private static final int INDEX_DECREMENT = 1;
/*     */   private static final int[] SIZE_TABLE;
/*     */   
/*     */   static
/*     */   {
/*  46 */     ArrayList localArrayList = new ArrayList();
/*  47 */     for (int i = 16; i < 512; i += 16) {
/*  48 */       localArrayList.add(Integer.valueOf(i));
/*     */     }
/*     */     
/*  51 */     for (i = 512; i > 0; i <<= 1) {
/*  52 */       localArrayList.add(Integer.valueOf(i));
/*     */     }
/*     */     
/*  55 */     SIZE_TABLE = new int[localArrayList.size()];
/*  56 */     for (i = 0; i < SIZE_TABLE.length; i++) {
/*  57 */       SIZE_TABLE[i] = ((Integer)localArrayList.get(i)).intValue();
/*     */     }
/*     */   }
/*     */   
/*  61 */   public static final AdaptiveRecvByteBufAllocator DEFAULT = new AdaptiveRecvByteBufAllocator();
/*     */   private final int minIndex;
/*     */   
/*  64 */   private static int getSizeTableIndex(int paramInt) { int i = 0;int j = SIZE_TABLE.length - 1;
/*  65 */     for (;;) { if (j < i) {
/*  66 */         return i;
/*     */       }
/*  68 */       if (j == i) {
/*  69 */         return j;
/*     */       }
/*     */       
/*  72 */       int k = i + j >>> 1;
/*  73 */       int m = SIZE_TABLE[k];
/*  74 */       int n = SIZE_TABLE[(k + 1)];
/*  75 */       if (paramInt > n) {
/*  76 */         i = k + 1;
/*  77 */       } else if (paramInt < m) {
/*  78 */         j = k - 1;
/*  79 */       } else { if (paramInt == m) {
/*  80 */           return k;
/*     */         }
/*  82 */         return k + 1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class HandleImpl implements RecvByteBufAllocator.Handle {
/*     */     private final int minIndex;
/*     */     private final int maxIndex;
/*     */     private int index;
/*     */     private int nextReceiveBufferSize;
/*     */     private boolean decreaseNow;
/*     */     
/*     */     HandleImpl(int paramInt1, int paramInt2, int paramInt3) {
/*  95 */       this.minIndex = paramInt1;
/*  96 */       this.maxIndex = paramInt2;
/*     */       
/*  98 */       this.index = AdaptiveRecvByteBufAllocator.getSizeTableIndex(paramInt3);
/*  99 */       this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
/*     */     }
/*     */     
/*     */     public ByteBuf allocate(ByteBufAllocator paramByteBufAllocator)
/*     */     {
/* 104 */       return paramByteBufAllocator.ioBuffer(this.nextReceiveBufferSize);
/*     */     }
/*     */     
/*     */     public int guess()
/*     */     {
/* 109 */       return this.nextReceiveBufferSize;
/*     */     }
/*     */     
/*     */     public void record(int paramInt)
/*     */     {
/* 114 */       if (paramInt <= AdaptiveRecvByteBufAllocator.SIZE_TABLE[Math.max(0, this.index - 1 - 1)]) {
/* 115 */         if (this.decreaseNow) {
/* 116 */           this.index = Math.max(this.index - 1, this.minIndex);
/* 117 */           this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
/* 118 */           this.decreaseNow = false;
/*     */         } else {
/* 120 */           this.decreaseNow = true;
/*     */         }
/* 122 */       } else if (paramInt >= this.nextReceiveBufferSize) {
/* 123 */         this.index = Math.min(this.index + 4, this.maxIndex);
/* 124 */         this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
/* 125 */         this.decreaseNow = false;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private final int maxIndex;
/*     */   
/*     */ 
/*     */   private final int initial;
/*     */   
/*     */ 
/*     */   private AdaptiveRecvByteBufAllocator()
/*     */   {
/* 140 */     this(64, 1024, 65536);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AdaptiveRecvByteBufAllocator(int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 151 */     if (paramInt1 <= 0) {
/* 152 */       throw new IllegalArgumentException("minimum: " + paramInt1);
/*     */     }
/* 154 */     if (paramInt2 < paramInt1) {
/* 155 */       throw new IllegalArgumentException("initial: " + paramInt2);
/*     */     }
/* 157 */     if (paramInt3 < paramInt2) {
/* 158 */       throw new IllegalArgumentException("maximum: " + paramInt3);
/*     */     }
/*     */     
/* 161 */     int i = getSizeTableIndex(paramInt1);
/* 162 */     if (SIZE_TABLE[i] < paramInt1) {
/* 163 */       this.minIndex = (i + 1);
/*     */     } else {
/* 165 */       this.minIndex = i;
/*     */     }
/*     */     
/* 168 */     int j = getSizeTableIndex(paramInt3);
/* 169 */     if (SIZE_TABLE[j] > paramInt3) {
/* 170 */       this.maxIndex = (j - 1);
/*     */     } else {
/* 172 */       this.maxIndex = j;
/*     */     }
/*     */     
/* 175 */     this.initial = paramInt2;
/*     */   }
/*     */   
/*     */   public RecvByteBufAllocator.Handle newHandle()
/*     */   {
/* 180 */     return new HandleImpl(this.minIndex, this.maxIndex, this.initial);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\AdaptiveRecvByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */