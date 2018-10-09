/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ResourceLeak;
/*     */ import io.netty.util.ResourceLeakDetector;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ public abstract class AbstractByteBufAllocator
/*     */   implements ByteBufAllocator
/*     */ {
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 256;
/*     */   private static final int DEFAULT_MAX_COMPONENTS = 16;
/*     */   private final boolean directByDefault;
/*     */   private final ByteBuf emptyBuf;
/*     */   
/*     */   protected static ByteBuf toLeakAwareBuffer(ByteBuf paramByteBuf)
/*     */   {
/*     */     ResourceLeak localResourceLeak;
/*  33 */     switch (ResourceLeakDetector.getLevel()) {
/*     */     case SIMPLE: 
/*  35 */       localResourceLeak = AbstractByteBuf.leakDetector.open(paramByteBuf);
/*  36 */       if (localResourceLeak != null) {
/*  37 */         paramByteBuf = new SimpleLeakAwareByteBuf(paramByteBuf, localResourceLeak);
/*     */       }
/*     */       break;
/*     */     case ADVANCED: 
/*     */     case PARANOID: 
/*  42 */       localResourceLeak = AbstractByteBuf.leakDetector.open(paramByteBuf);
/*  43 */       if (localResourceLeak != null) {
/*  44 */         paramByteBuf = new AdvancedLeakAwareByteBuf(paramByteBuf, localResourceLeak);
/*     */       }
/*     */       break;
/*     */     }
/*  48 */     return paramByteBuf;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractByteBufAllocator()
/*     */   {
/*  58 */     this(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractByteBufAllocator(boolean paramBoolean)
/*     */   {
/*  68 */     this.directByDefault = ((paramBoolean) && (PlatformDependent.hasUnsafe()));
/*  69 */     this.emptyBuf = new EmptyByteBuf(this);
/*     */   }
/*     */   
/*     */   public ByteBuf buffer()
/*     */   {
/*  74 */     if (this.directByDefault) {
/*  75 */       return directBuffer();
/*     */     }
/*  77 */     return heapBuffer();
/*     */   }
/*     */   
/*     */   public ByteBuf buffer(int paramInt)
/*     */   {
/*  82 */     if (this.directByDefault) {
/*  83 */       return directBuffer(paramInt);
/*     */     }
/*  85 */     return heapBuffer(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf buffer(int paramInt1, int paramInt2)
/*     */   {
/*  90 */     if (this.directByDefault) {
/*  91 */       return directBuffer(paramInt1, paramInt2);
/*     */     }
/*  93 */     return heapBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf ioBuffer()
/*     */   {
/*  98 */     if (PlatformDependent.hasUnsafe()) {
/*  99 */       return directBuffer(256);
/*     */     }
/* 101 */     return heapBuffer(256);
/*     */   }
/*     */   
/*     */   public ByteBuf ioBuffer(int paramInt)
/*     */   {
/* 106 */     if (PlatformDependent.hasUnsafe()) {
/* 107 */       return directBuffer(paramInt);
/*     */     }
/* 109 */     return heapBuffer(paramInt);
/*     */   }
/*     */   
/*     */   public ByteBuf ioBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 114 */     if (PlatformDependent.hasUnsafe()) {
/* 115 */       return directBuffer(paramInt1, paramInt2);
/*     */     }
/* 117 */     return heapBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf heapBuffer()
/*     */   {
/* 122 */     return heapBuffer(256, Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public ByteBuf heapBuffer(int paramInt)
/*     */   {
/* 127 */     return heapBuffer(paramInt, Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public ByteBuf heapBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 132 */     if ((paramInt1 == 0) && (paramInt2 == 0)) {
/* 133 */       return this.emptyBuf;
/*     */     }
/* 135 */     validate(paramInt1, paramInt2);
/* 136 */     return newHeapBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public ByteBuf directBuffer()
/*     */   {
/* 141 */     return directBuffer(256, Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public ByteBuf directBuffer(int paramInt)
/*     */   {
/* 146 */     return directBuffer(paramInt, Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public ByteBuf directBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 151 */     if ((paramInt1 == 0) && (paramInt2 == 0)) {
/* 152 */       return this.emptyBuf;
/*     */     }
/* 154 */     validate(paramInt1, paramInt2);
/* 155 */     return newDirectBuffer(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public CompositeByteBuf compositeBuffer()
/*     */   {
/* 160 */     if (this.directByDefault) {
/* 161 */       return compositeDirectBuffer();
/*     */     }
/* 163 */     return compositeHeapBuffer();
/*     */   }
/*     */   
/*     */   public CompositeByteBuf compositeBuffer(int paramInt)
/*     */   {
/* 168 */     if (this.directByDefault) {
/* 169 */       return compositeDirectBuffer(paramInt);
/*     */     }
/* 171 */     return compositeHeapBuffer(paramInt);
/*     */   }
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer()
/*     */   {
/* 176 */     return compositeHeapBuffer(16);
/*     */   }
/*     */   
/*     */   public CompositeByteBuf compositeHeapBuffer(int paramInt)
/*     */   {
/* 181 */     return new CompositeByteBuf(this, false, paramInt);
/*     */   }
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer()
/*     */   {
/* 186 */     return compositeDirectBuffer(16);
/*     */   }
/*     */   
/*     */   public CompositeByteBuf compositeDirectBuffer(int paramInt)
/*     */   {
/* 191 */     return new CompositeByteBuf(this, true, paramInt);
/*     */   }
/*     */   
/*     */   private static void validate(int paramInt1, int paramInt2) {
/* 195 */     if (paramInt1 < 0) {
/* 196 */       throw new IllegalArgumentException("initialCapacity: " + paramInt1 + " (expectd: 0+)");
/*     */     }
/* 198 */     if (paramInt1 > paramInt2) {
/* 199 */       throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) }));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract ByteBuf newHeapBuffer(int paramInt1, int paramInt2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract ByteBuf newDirectBuffer(int paramInt1, int paramInt2);
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 217 */     return StringUtil.simpleClassName(this) + "(directByDefault: " + this.directByDefault + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\AbstractByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */