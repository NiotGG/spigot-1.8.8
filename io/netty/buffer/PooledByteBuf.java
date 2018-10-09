/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.Recycler.Handle;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
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
/*     */ abstract class PooledByteBuf<T>
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final Recycler.Handle recyclerHandle;
/*     */   protected PoolChunk<T> chunk;
/*     */   protected long handle;
/*     */   protected T memory;
/*     */   protected int offset;
/*     */   protected int length;
/*     */   int maxLength;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   protected PooledByteBuf(Recycler.Handle paramHandle, int paramInt)
/*     */   {
/*  38 */     super(paramInt);
/*  39 */     this.recyclerHandle = paramHandle;
/*     */   }
/*     */   
/*     */   void init(PoolChunk<T> paramPoolChunk, long paramLong, int paramInt1, int paramInt2, int paramInt3) {
/*  43 */     assert (paramLong >= 0L);
/*  44 */     assert (paramPoolChunk != null);
/*     */     
/*  46 */     this.chunk = paramPoolChunk;
/*  47 */     this.handle = paramLong;
/*  48 */     this.memory = paramPoolChunk.memory;
/*  49 */     this.offset = paramInt1;
/*  50 */     this.length = paramInt2;
/*  51 */     this.maxLength = paramInt3;
/*  52 */     setIndex(0, 0);
/*  53 */     this.tmpNioBuf = null;
/*     */   }
/*     */   
/*     */   void initUnpooled(PoolChunk<T> paramPoolChunk, int paramInt) {
/*  57 */     assert (paramPoolChunk != null);
/*     */     
/*  59 */     this.chunk = paramPoolChunk;
/*  60 */     this.handle = 0L;
/*  61 */     this.memory = paramPoolChunk.memory;
/*  62 */     this.offset = 0;
/*  63 */     this.length = (this.maxLength = paramInt);
/*  64 */     setIndex(0, 0);
/*  65 */     this.tmpNioBuf = null;
/*     */   }
/*     */   
/*     */   public final int capacity()
/*     */   {
/*  70 */     return this.length;
/*     */   }
/*     */   
/*     */   public final ByteBuf capacity(int paramInt)
/*     */   {
/*  75 */     ensureAccessible();
/*     */     
/*     */ 
/*  78 */     if (this.chunk.unpooled) {
/*  79 */       if (paramInt == this.length) {
/*  80 */         return this;
/*     */       }
/*     */     }
/*  83 */     else if (paramInt > this.length) {
/*  84 */       if (paramInt <= this.maxLength) {
/*  85 */         this.length = paramInt;
/*  86 */         return this;
/*     */       }
/*  88 */     } else if (paramInt < this.length) {
/*  89 */       if (paramInt > this.maxLength >>> 1) {
/*  90 */         if (this.maxLength <= 512) {
/*  91 */           if (paramInt > this.maxLength - 16) {
/*  92 */             this.length = paramInt;
/*  93 */             setIndex(Math.min(readerIndex(), paramInt), Math.min(writerIndex(), paramInt));
/*  94 */             return this;
/*     */           }
/*     */         } else {
/*  97 */           this.length = paramInt;
/*  98 */           setIndex(Math.min(readerIndex(), paramInt), Math.min(writerIndex(), paramInt));
/*  99 */           return this;
/*     */         }
/*     */       }
/*     */     } else {
/* 103 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 108 */     this.chunk.arena.reallocate(this, paramInt, true);
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public final ByteBufAllocator alloc()
/*     */   {
/* 114 */     return this.chunk.arena.parent;
/*     */   }
/*     */   
/*     */   public final ByteOrder order()
/*     */   {
/* 119 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */   
/*     */   public final ByteBuf unwrap()
/*     */   {
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   protected final ByteBuffer internalNioBuffer() {
/* 128 */     ByteBuffer localByteBuffer = this.tmpNioBuf;
/* 129 */     if (localByteBuffer == null) {
/* 130 */       this.tmpNioBuf = (localByteBuffer = newInternalNioBuffer(this.memory));
/*     */     }
/* 132 */     return localByteBuffer;
/*     */   }
/*     */   
/*     */   protected abstract ByteBuffer newInternalNioBuffer(T paramT);
/*     */   
/*     */   protected final void deallocate()
/*     */   {
/* 139 */     if (this.handle >= 0L) {
/* 140 */       long l = this.handle;
/* 141 */       this.handle = -1L;
/* 142 */       this.memory = null;
/* 143 */       this.chunk.arena.free(this.chunk, l, this.maxLength);
/* 144 */       recycle();
/*     */     }
/*     */   }
/*     */   
/*     */   private void recycle() {
/* 149 */     Recycler.Handle localHandle = this.recyclerHandle;
/* 150 */     if (localHandle != null) {
/* 151 */       recycler().recycle(this, localHandle);
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract Recycler<?> recycler();
/*     */   
/*     */   protected final int idx(int paramInt) {
/* 158 */     return this.offset + paramInt;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PooledByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */