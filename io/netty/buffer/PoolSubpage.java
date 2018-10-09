/*     */ package io.netty.buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PoolSubpage<T>
/*     */ {
/*     */   final PoolChunk<T> chunk;
/*     */   
/*     */ 
/*     */   private final int memoryMapIdx;
/*     */   
/*     */ 
/*     */   private final int runOffset;
/*     */   
/*     */ 
/*     */   private final int pageSize;
/*     */   
/*     */ 
/*     */   private final long[] bitmap;
/*     */   
/*     */ 
/*     */   PoolSubpage<T> prev;
/*     */   
/*     */ 
/*     */   PoolSubpage<T> next;
/*     */   
/*     */   boolean doNotDestroy;
/*     */   
/*     */   int elemSize;
/*     */   
/*     */   private int maxNumElems;
/*     */   
/*     */   private int bitmapLength;
/*     */   
/*     */   private int nextAvail;
/*     */   
/*     */   private int numAvail;
/*     */   
/*     */ 
/*     */   PoolSubpage(int paramInt)
/*     */   {
/*  42 */     this.chunk = null;
/*  43 */     this.memoryMapIdx = -1;
/*  44 */     this.runOffset = -1;
/*  45 */     this.elemSize = -1;
/*  46 */     this.pageSize = paramInt;
/*  47 */     this.bitmap = null;
/*     */   }
/*     */   
/*     */   PoolSubpage(PoolChunk<T> paramPoolChunk, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  51 */     this.chunk = paramPoolChunk;
/*  52 */     this.memoryMapIdx = paramInt1;
/*  53 */     this.runOffset = paramInt2;
/*  54 */     this.pageSize = paramInt3;
/*  55 */     this.bitmap = new long[paramInt3 >>> 10];
/*  56 */     init(paramInt4);
/*     */   }
/*     */   
/*     */   void init(int paramInt) {
/*  60 */     this.doNotDestroy = true;
/*  61 */     this.elemSize = paramInt;
/*  62 */     if (paramInt != 0) {
/*  63 */       this.maxNumElems = (this.numAvail = this.pageSize / paramInt);
/*  64 */       this.nextAvail = 0;
/*  65 */       this.bitmapLength = (this.maxNumElems >>> 6);
/*  66 */       if ((this.maxNumElems & 0x3F) != 0) {
/*  67 */         this.bitmapLength += 1;
/*     */       }
/*     */       
/*  70 */       for (int i = 0; i < this.bitmapLength; i++) {
/*  71 */         this.bitmap[i] = 0L;
/*     */       }
/*     */     }
/*     */     
/*  75 */     addToPool();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   long allocate()
/*     */   {
/*  82 */     if (this.elemSize == 0) {
/*  83 */       return toHandle(0);
/*     */     }
/*     */     
/*  86 */     if ((this.numAvail == 0) || (!this.doNotDestroy)) {
/*  87 */       return -1L;
/*     */     }
/*     */     
/*  90 */     int i = getNextAvail();
/*  91 */     int j = i >>> 6;
/*  92 */     int k = i & 0x3F;
/*  93 */     assert ((this.bitmap[j] >>> k & 1L) == 0L);
/*  94 */     this.bitmap[j] |= 1L << k;
/*     */     
/*  96 */     if (--this.numAvail == 0) {
/*  97 */       removeFromPool();
/*     */     }
/*     */     
/* 100 */     return toHandle(i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean free(int paramInt)
/*     */   {
/* 109 */     if (this.elemSize == 0) {
/* 110 */       return true;
/*     */     }
/*     */     
/* 113 */     int i = paramInt >>> 6;
/* 114 */     int j = paramInt & 0x3F;
/* 115 */     assert ((this.bitmap[i] >>> j & 1L) != 0L);
/* 116 */     this.bitmap[i] ^= 1L << j;
/*     */     
/* 118 */     setNextAvail(paramInt);
/*     */     
/* 120 */     if (this.numAvail++ == 0) {
/* 121 */       addToPool();
/* 122 */       return true;
/*     */     }
/*     */     
/* 125 */     if (this.numAvail != this.maxNumElems) {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     if (this.prev == this.next)
/*     */     {
/* 131 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 135 */     this.doNotDestroy = false;
/* 136 */     removeFromPool();
/* 137 */     return false;
/*     */   }
/*     */   
/*     */   private void addToPool()
/*     */   {
/* 142 */     PoolSubpage localPoolSubpage = this.chunk.arena.findSubpagePoolHead(this.elemSize);
/* 143 */     assert ((this.prev == null) && (this.next == null));
/* 144 */     this.prev = localPoolSubpage;
/* 145 */     this.next = localPoolSubpage.next;
/* 146 */     this.next.prev = this;
/* 147 */     localPoolSubpage.next = this;
/*     */   }
/*     */   
/*     */   private void removeFromPool() {
/* 151 */     assert ((this.prev != null) && (this.next != null));
/* 152 */     this.prev.next = this.next;
/* 153 */     this.next.prev = this.prev;
/* 154 */     this.next = null;
/* 155 */     this.prev = null;
/*     */   }
/*     */   
/*     */   private void setNextAvail(int paramInt) {
/* 159 */     this.nextAvail = paramInt;
/*     */   }
/*     */   
/*     */   private int getNextAvail() {
/* 163 */     int i = this.nextAvail;
/* 164 */     if (i >= 0) {
/* 165 */       this.nextAvail = -1;
/* 166 */       return i;
/*     */     }
/* 168 */     return findNextAvail();
/*     */   }
/*     */   
/*     */   private int findNextAvail() {
/* 172 */     long[] arrayOfLong = this.bitmap;
/* 173 */     int i = this.bitmapLength;
/* 174 */     for (int j = 0; j < i; j++) {
/* 175 */       long l = arrayOfLong[j];
/* 176 */       if ((l ^ 0xFFFFFFFFFFFFFFFF) != 0L) {
/* 177 */         return findNextAvail0(j, l);
/*     */       }
/*     */     }
/* 180 */     return -1;
/*     */   }
/*     */   
/*     */   private int findNextAvail0(int paramInt, long paramLong) {
/* 184 */     int i = this.maxNumElems;
/* 185 */     int j = paramInt << 6;
/*     */     
/* 187 */     for (int k = 0; k < 64; k++) {
/* 188 */       if ((paramLong & 1L) == 0L) {
/* 189 */         int m = j | k;
/* 190 */         if (m >= i) break;
/* 191 */         return m;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 196 */       paramLong >>>= 1;
/*     */     }
/* 198 */     return -1;
/*     */   }
/*     */   
/*     */   private long toHandle(int paramInt) {
/* 202 */     return 0x4000000000000000 | paramInt << 32 | this.memoryMapIdx;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 206 */     if (!this.doNotDestroy) {
/* 207 */       return "(" + this.memoryMapIdx + ": not in use)";
/*     */     }
/*     */     
/* 210 */     return String.valueOf('(') + this.memoryMapIdx + ": " + (this.maxNumElems - this.numAvail) + '/' + this.maxNumElems + ", offset: " + this.runOffset + ", length: " + this.pageSize + ", elemSize: " + this.elemSize + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\PoolSubpage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */