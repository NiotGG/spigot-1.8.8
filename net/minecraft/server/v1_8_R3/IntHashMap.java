/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ public class IntHashMap<V>
/*     */ {
/*     */   private transient IntHashMapEntry<V>[] a;
/*     */   
/*     */   private transient int b;
/*     */   
/*     */   private int c;
/*     */   
/*     */   private final float d;
/*     */   
/*     */   public IntHashMap()
/*     */   {
/*  16 */     this.d = 0.75F;
/*  17 */     this.c = 12;
/*  18 */     this.a = new IntHashMapEntry[16];
/*     */   }
/*     */   
/*     */   private static int g(int paramInt) {
/*  22 */     paramInt ^= paramInt >>> 20 ^ paramInt >>> 12;
/*  23 */     return paramInt ^ paramInt >>> 7 ^ paramInt >>> 4;
/*     */   }
/*     */   
/*     */   private static int a(int paramInt1, int paramInt2) {
/*  27 */     return paramInt1 & paramInt2 - 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public V get(int paramInt)
/*     */   {
/*  39 */     int i = g(paramInt);
/*  40 */     for (IntHashMapEntry localIntHashMapEntry = this.a[a(i, this.a.length)]; localIntHashMapEntry != null; localIntHashMapEntry = localIntHashMapEntry.c) {
/*  41 */       if (localIntHashMapEntry.a == paramInt) {
/*  42 */         return (V)localIntHashMapEntry.b;
/*     */       }
/*     */     }
/*  45 */     return null;
/*     */   }
/*     */   
/*     */   public boolean b(int paramInt) {
/*  49 */     return c(paramInt) != null;
/*     */   }
/*     */   
/*     */   final IntHashMapEntry<V> c(int paramInt) {
/*  53 */     int i = g(paramInt);
/*  54 */     for (IntHashMapEntry localIntHashMapEntry = this.a[a(i, this.a.length)]; localIntHashMapEntry != null; localIntHashMapEntry = localIntHashMapEntry.c) {
/*  55 */       if (localIntHashMapEntry.a == paramInt) {
/*  56 */         return localIntHashMapEntry;
/*     */       }
/*     */     }
/*  59 */     return null;
/*     */   }
/*     */   
/*     */   public void a(int paramInt, V paramV) {
/*  63 */     int i = g(paramInt);
/*  64 */     int j = a(i, this.a.length);
/*  65 */     for (IntHashMapEntry localIntHashMapEntry = this.a[j]; localIntHashMapEntry != null; localIntHashMapEntry = localIntHashMapEntry.c) {
/*  66 */       if (localIntHashMapEntry.a == paramInt) {
/*  67 */         localIntHashMapEntry.b = paramV;
/*  68 */         return;
/*     */       }
/*     */     }
/*     */     
/*  72 */     a(i, paramInt, paramV, j);
/*     */   }
/*     */   
/*     */   private void h(int paramInt)
/*     */   {
/*  77 */     IntHashMapEntry[] arrayOfIntHashMapEntry1 = this.a;
/*  78 */     int i = arrayOfIntHashMapEntry1.length;
/*  79 */     if (i == 1073741824) {
/*  80 */       this.c = Integer.MAX_VALUE;
/*  81 */       return;
/*     */     }
/*     */     
/*  84 */     IntHashMapEntry[] arrayOfIntHashMapEntry2 = new IntHashMapEntry[paramInt];
/*  85 */     a(arrayOfIntHashMapEntry2);
/*  86 */     this.a = arrayOfIntHashMapEntry2;
/*  87 */     this.c = ((int)(paramInt * this.d));
/*     */   }
/*     */   
/*     */   private void a(IntHashMapEntry<V>[] paramArrayOfIntHashMapEntry) {
/*  91 */     IntHashMapEntry[] arrayOfIntHashMapEntry = this.a;
/*  92 */     int i = paramArrayOfIntHashMapEntry.length;
/*  93 */     for (int j = 0; j < arrayOfIntHashMapEntry.length; j++) {
/*  94 */       Object localObject = arrayOfIntHashMapEntry[j];
/*  95 */       if (localObject != null) {
/*  96 */         arrayOfIntHashMapEntry[j] = null;
/*     */         do {
/*  98 */           IntHashMapEntry localIntHashMapEntry = ((IntHashMapEntry)localObject).c;
/*  99 */           int k = a(((IntHashMapEntry)localObject).d, i);
/* 100 */           ((IntHashMapEntry)localObject).c = paramArrayOfIntHashMapEntry[k];
/* 101 */           paramArrayOfIntHashMapEntry[k] = localObject;
/* 102 */           localObject = localIntHashMapEntry;
/* 103 */         } while (localObject != null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public V d(int paramInt) {
/* 109 */     IntHashMapEntry localIntHashMapEntry = e(paramInt);
/* 110 */     return localIntHashMapEntry == null ? null : localIntHashMapEntry.b;
/*     */   }
/*     */   
/*     */   final IntHashMapEntry<V> e(int paramInt) {
/* 114 */     int i = g(paramInt);
/* 115 */     int j = a(i, this.a.length);
/* 116 */     Object localObject1 = this.a[j];
/* 117 */     Object localObject2 = localObject1;
/*     */     
/* 119 */     while (localObject2 != null) {
/* 120 */       IntHashMapEntry localIntHashMapEntry = ((IntHashMapEntry)localObject2).c;
/* 121 */       if (((IntHashMapEntry)localObject2).a == paramInt) {
/* 122 */         this.b -= 1;
/* 123 */         if (localObject1 == localObject2) {
/* 124 */           this.a[j] = localIntHashMapEntry;
/*     */         } else {
/* 126 */           ((IntHashMapEntry)localObject1).c = localIntHashMapEntry;
/*     */         }
/* 128 */         return (IntHashMapEntry<V>)localObject2;
/*     */       }
/* 130 */       localObject1 = localObject2;
/* 131 */       localObject2 = localIntHashMapEntry;
/*     */     }
/*     */     
/* 134 */     return (IntHashMapEntry<V>)localObject2;
/*     */   }
/*     */   
/*     */   public void c() {
/* 138 */     IntHashMapEntry[] arrayOfIntHashMapEntry = this.a;
/* 139 */     for (int i = 0; i < arrayOfIntHashMapEntry.length; i++) {
/* 140 */       arrayOfIntHashMapEntry[i] = null;
/*     */     }
/* 142 */     this.b = 0;
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
/*     */   static class IntHashMapEntry<V>
/*     */   {
/*     */     final int a;
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
/*     */     IntHashMapEntry<V> c;
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
/*     */     IntHashMapEntry(int paramInt1, int paramInt2, V paramV, IntHashMapEntry<V> paramIntHashMapEntry)
/*     */     {
/* 191 */       this.b = paramV;
/* 192 */       this.c = paramIntHashMapEntry;
/* 193 */       this.a = paramInt2;
/* 194 */       this.d = paramInt1;
/*     */     }
/*     */     
/*     */     public final int a() {
/* 198 */       return this.a;
/*     */     }
/*     */     
/*     */     public final V b() {
/* 202 */       return (V)this.b;
/*     */     }
/*     */     
/*     */     public final boolean equals(Object paramObject)
/*     */     {
/* 207 */       if (!(paramObject instanceof IntHashMapEntry)) {
/* 208 */         return false;
/*     */       }
/* 210 */       IntHashMapEntry localIntHashMapEntry = (IntHashMapEntry)paramObject;
/* 211 */       Integer localInteger1 = Integer.valueOf(a());
/* 212 */       Integer localInteger2 = Integer.valueOf(localIntHashMapEntry.a());
/* 213 */       if ((localInteger1 == localInteger2) || ((localInteger1 != null) && (localInteger1.equals(localInteger2)))) {
/* 214 */         Object localObject1 = b();
/* 215 */         Object localObject2 = localIntHashMapEntry.b();
/* 216 */         if ((localObject1 == localObject2) || ((localObject1 != null) && (localObject1.equals(localObject2)))) {
/* 217 */           return true;
/*     */         }
/*     */       }
/* 220 */       return false;
/*     */     }
/*     */     
/*     */     public final int hashCode() {
/* 224 */       return IntHashMap.f(this.a);
/*     */     }
/*     */     
/*     */     public final String toString() {
/* 228 */       return a() + "=" + b();
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(int paramInt1, int paramInt2, V paramV, int paramInt3) {
/* 233 */     IntHashMapEntry localIntHashMapEntry = this.a[paramInt3];
/* 234 */     this.a[paramInt3] = new IntHashMapEntry(paramInt1, paramInt2, paramV, localIntHashMapEntry);
/* 235 */     if (this.b++ >= this.c) {
/* 236 */       h(2 * this.a.length);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IntHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */