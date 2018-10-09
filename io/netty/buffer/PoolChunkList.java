/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ final class PoolChunkList<T>
/*     */ {
/*     */   private final PoolArena<T> arena;
/*     */   private final PoolChunkList<T> nextList;
/*     */   PoolChunkList<T> prevList;
/*     */   private final int minUsage;
/*     */   private final int maxUsage;
/*     */   private PoolChunk<T> head;
/*     */   
/*     */   PoolChunkList(PoolArena<T> paramPoolArena, PoolChunkList<T> paramPoolChunkList, int paramInt1, int paramInt2)
/*     */   {
/*  35 */     this.arena = paramPoolArena;
/*  36 */     this.nextList = paramPoolChunkList;
/*  37 */     this.minUsage = paramInt1;
/*  38 */     this.maxUsage = paramInt2;
/*     */   }
/*     */   
/*     */   boolean allocate(PooledByteBuf<T> paramPooledByteBuf, int paramInt1, int paramInt2) {
/*  42 */     if (this.head == null) {
/*  43 */       return false;
/*     */     }
/*     */     
/*  46 */     PoolChunk localPoolChunk = this.head;
/*  47 */     for (;;) { long l = localPoolChunk.allocate(paramInt2);
/*  48 */       if (l < 0L) {
/*  49 */         localPoolChunk = localPoolChunk.next;
/*  50 */         if (localPoolChunk == null) {
/*  51 */           return false;
/*     */         }
/*     */       } else {
/*  54 */         localPoolChunk.initBuf(paramPooledByteBuf, l, paramInt1);
/*  55 */         if (localPoolChunk.usage() >= this.maxUsage) {
/*  56 */           remove(localPoolChunk);
/*  57 */           this.nextList.add(localPoolChunk);
/*     */         }
/*  59 */         return true;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void free(PoolChunk<T> paramPoolChunk, long paramLong) {
/*  65 */     paramPoolChunk.free(paramLong);
/*  66 */     if (paramPoolChunk.usage() < this.minUsage) {
/*  67 */       remove(paramPoolChunk);
/*  68 */       if (this.prevList == null) {
/*  69 */         assert (paramPoolChunk.usage() == 0);
/*  70 */         this.arena.destroyChunk(paramPoolChunk);
/*     */       } else {
/*  72 */         this.prevList.add(paramPoolChunk);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   void add(PoolChunk<T> paramPoolChunk) {
/*  78 */     if (paramPoolChunk.usage() >= this.maxUsage) {
/*  79 */       this.nextList.add(paramPoolChunk);
/*  80 */       return;
/*     */     }
/*     */     
/*  83 */     paramPoolChunk.parent = this;
/*  84 */     if (this.head == null) {
/*  85 */       this.head = paramPoolChunk;
/*  86 */       paramPoolChunk.prev = null;
/*  87 */       paramPoolChunk.next = null;
/*     */     } else {
/*  89 */       paramPoolChunk.prev = null;
/*  90 */       paramPoolChunk.next = this.head;
/*  91 */       this.head.prev = paramPoolChunk;
/*  92 */       this.head = paramPoolChunk;
/*     */     }
/*     */   }
/*     */   
/*     */   private void remove(PoolChunk<T> paramPoolChunk) {
/*  97 */     if (paramPoolChunk == this.head) {
/*  98 */       this.head = paramPoolChunk.next;
/*  99 */       if (this.head != null) {
/* 100 */         this.head.prev = null;
/*     */       }
/*     */     } else {
/* 103 */       PoolChunk localPoolChunk = paramPoolChunk.next;
/* 104 */       paramPoolChunk.prev.next = localPoolChunk;
/* 105 */       if (localPoolChunk != null) {
/* 106 */         localPoolChunk.prev = paramPoolChunk.prev;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 113 */     if (this.head == null) {
/* 114 */       return "none";
/*     */     }
/*     */     
/* 117 */     StringBuilder localStringBuilder = new StringBuilder();
/* 118 */     PoolChunk localPoolChunk = this.head;
/* 119 */     for (;;) { localStringBuilder.append(localPoolChunk);
/* 120 */       localPoolChunk = localPoolChunk.next;
/* 121 */       if (localPoolChunk == null) {
/*     */         break;
/*     */       }
/* 124 */       localStringBuilder.append(StringUtil.NEWLINE);
/*     */     }
/*     */     
/* 127 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PoolChunkList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */