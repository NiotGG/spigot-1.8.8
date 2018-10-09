/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ public class LongHashMap<V>
/*     */ {
/*     */   private transient LongHashMapEntry<V>[] entries;
/*     */   
/*     */   private transient int count;
/*     */   
/*     */   private int c;
/*     */   
/*     */   private int d;
/*     */   
/*     */   private final float e;
/*     */   
/*     */   private volatile transient int f;
/*     */   
/*     */   public LongHashMap()
/*     */   {
/*  20 */     this.e = 0.75F;
/*  21 */     this.d = 3072;
/*  22 */     this.entries = new LongHashMapEntry['က'];
/*  23 */     this.c = (this.entries.length - 1);
/*     */   }
/*     */   
/*     */   private static int g(long paramLong) {
/*  27 */     return a((int)(paramLong ^ paramLong >>> 32));
/*     */   }
/*     */   
/*     */   private static int a(int paramInt) {
/*  31 */     paramInt ^= paramInt >>> 20 ^ paramInt >>> 12;
/*  32 */     return paramInt ^ paramInt >>> 7 ^ paramInt >>> 4;
/*     */   }
/*     */   
/*     */   private static int a(int paramInt1, int paramInt2) {
/*  36 */     return paramInt1 & paramInt2;
/*     */   }
/*     */   
/*     */   public int count() {
/*  40 */     return this.count;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public V getEntry(long paramLong)
/*     */   {
/*  48 */     int i = g(paramLong);
/*  49 */     for (LongHashMapEntry localLongHashMapEntry = this.entries[a(i, this.c)]; localLongHashMapEntry != null; localLongHashMapEntry = localLongHashMapEntry.c) {
/*  50 */       if (localLongHashMapEntry.a == paramLong) {
/*  51 */         return (V)localLongHashMapEntry.b;
/*     */       }
/*     */     }
/*  54 */     return null;
/*     */   }
/*     */   
/*     */   public boolean contains(long paramLong) {
/*  58 */     return c(paramLong) != null;
/*     */   }
/*     */   
/*     */   final LongHashMapEntry<V> c(long paramLong) {
/*  62 */     int i = g(paramLong);
/*  63 */     for (LongHashMapEntry localLongHashMapEntry = this.entries[a(i, this.c)]; localLongHashMapEntry != null; localLongHashMapEntry = localLongHashMapEntry.c) {
/*  64 */       if (localLongHashMapEntry.a == paramLong) {
/*  65 */         return localLongHashMapEntry;
/*     */       }
/*     */     }
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   public void put(long paramLong, V paramV) {
/*  72 */     int i = g(paramLong);
/*  73 */     int j = a(i, this.c);
/*  74 */     for (LongHashMapEntry localLongHashMapEntry = this.entries[j]; localLongHashMapEntry != null; localLongHashMapEntry = localLongHashMapEntry.c) {
/*  75 */       if (localLongHashMapEntry.a == paramLong) {
/*  76 */         localLongHashMapEntry.b = paramV;
/*  77 */         return;
/*     */       }
/*     */     }
/*     */     
/*  81 */     this.f += 1;
/*  82 */     a(i, paramLong, paramV, j);
/*     */   }
/*     */   
/*     */   private void b(int paramInt)
/*     */   {
/*  87 */     LongHashMapEntry[] arrayOfLongHashMapEntry1 = this.entries;
/*  88 */     int i = arrayOfLongHashMapEntry1.length;
/*  89 */     if (i == 1073741824) {
/*  90 */       this.d = Integer.MAX_VALUE;
/*  91 */       return;
/*     */     }
/*     */     
/*  94 */     LongHashMapEntry[] arrayOfLongHashMapEntry2 = new LongHashMapEntry[paramInt];
/*  95 */     a(arrayOfLongHashMapEntry2);
/*  96 */     this.entries = arrayOfLongHashMapEntry2;
/*  97 */     this.c = (this.entries.length - 1);
/*  98 */     this.d = ((int)(paramInt * this.e));
/*     */   }
/*     */   
/*     */   private void a(LongHashMapEntry<V>[] paramArrayOfLongHashMapEntry) {
/* 102 */     LongHashMapEntry[] arrayOfLongHashMapEntry = this.entries;
/* 103 */     int i = paramArrayOfLongHashMapEntry.length;
/* 104 */     for (int j = 0; j < arrayOfLongHashMapEntry.length; j++) {
/* 105 */       Object localObject = arrayOfLongHashMapEntry[j];
/* 106 */       if (localObject != null) {
/* 107 */         arrayOfLongHashMapEntry[j] = null;
/*     */         do {
/* 109 */           LongHashMapEntry localLongHashMapEntry = ((LongHashMapEntry)localObject).c;
/* 110 */           int k = a(((LongHashMapEntry)localObject).d, i - 1);
/* 111 */           ((LongHashMapEntry)localObject).c = paramArrayOfLongHashMapEntry[k];
/* 112 */           paramArrayOfLongHashMapEntry[k] = localObject;
/* 113 */           localObject = localLongHashMapEntry;
/* 114 */         } while (localObject != null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public V remove(long paramLong) {
/* 120 */     LongHashMapEntry localLongHashMapEntry = e(paramLong);
/* 121 */     return localLongHashMapEntry == null ? null : localLongHashMapEntry.b;
/*     */   }
/*     */   
/*     */   final LongHashMapEntry<V> e(long paramLong) {
/* 125 */     int i = g(paramLong);
/* 126 */     int j = a(i, this.c);
/* 127 */     Object localObject1 = this.entries[j];
/* 128 */     Object localObject2 = localObject1;
/*     */     
/* 130 */     while (localObject2 != null) {
/* 131 */       LongHashMapEntry localLongHashMapEntry = ((LongHashMapEntry)localObject2).c;
/* 132 */       if (((LongHashMapEntry)localObject2).a == paramLong) {
/* 133 */         this.f += 1;
/* 134 */         this.count -= 1;
/* 135 */         if (localObject1 == localObject2) {
/* 136 */           this.entries[j] = localLongHashMapEntry;
/*     */         } else {
/* 138 */           ((LongHashMapEntry)localObject1).c = localLongHashMapEntry;
/*     */         }
/* 140 */         return (LongHashMapEntry<V>)localObject2;
/*     */       }
/* 142 */       localObject1 = localObject2;
/* 143 */       localObject2 = localLongHashMapEntry;
/*     */     }
/*     */     
/* 146 */     return (LongHashMapEntry<V>)localObject2;
/*     */   }
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
/*     */   static class LongHashMapEntry<V>
/*     */   {
/*     */     final long a;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     V b;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     LongHashMapEntry<V> c;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     final int d;
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
/*     */     LongHashMapEntry(int paramInt, long paramLong, V paramV, LongHashMapEntry<V> paramLongHashMapEntry)
/*     */     {
/* 204 */       this.b = paramV;
/* 205 */       this.c = paramLongHashMapEntry;
/* 206 */       this.a = paramLong;
/* 207 */       this.d = paramInt;
/*     */     }
/*     */     
/*     */     public final long a() {
/* 211 */       return this.a;
/*     */     }
/*     */     
/*     */     public final V b() {
/* 215 */       return (V)this.b;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object paramObject)
/*     */     {
/* 220 */       if (!(paramObject instanceof LongHashMapEntry)) {
/* 221 */         return false;
/*     */       }
/* 223 */       LongHashMapEntry localLongHashMapEntry = (LongHashMapEntry)paramObject;
/* 224 */       Long localLong1 = Long.valueOf(a());
/* 225 */       Long localLong2 = Long.valueOf(localLongHashMapEntry.a());
/* 226 */       if ((localLong1 == localLong2) || ((localLong1 != null) && (localLong1.equals(localLong2)))) {
/* 227 */         Object localObject1 = b();
/* 228 */         Object localObject2 = localLongHashMapEntry.b();
/* 229 */         if ((localObject1 == localObject2) || ((localObject1 != null) && (localObject1.equals(localObject2)))) {
/* 230 */           return true;
/*     */         }
/*     */       }
/* 233 */       return false;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/* 237 */       return LongHashMap.f(this.a);
/*     */     }
/*     */     
/*     */     public final String toString() {
/* 241 */       return a() + "=" + b();
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(int paramInt1, long paramLong, V paramV, int paramInt2) {
/* 246 */     LongHashMapEntry localLongHashMapEntry = this.entries[paramInt2];
/* 247 */     this.entries[paramInt2] = new LongHashMapEntry(paramInt1, paramLong, paramV, localLongHashMapEntry);
/* 248 */     if (this.count++ >= this.d) {
/* 249 */       b(2 * this.entries.length);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\LongHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */