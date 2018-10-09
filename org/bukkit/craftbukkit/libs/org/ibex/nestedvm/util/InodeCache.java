/*     */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InodeCache
/*     */ {
/*  10 */   private static final Object PLACEHOLDER = new Object();
/*     */   
/*     */   private static final short SHORT_PLACEHOLDER = -2;
/*     */   private static final short SHORT_NULL = -1;
/*     */   private static final int LOAD_FACTOR = 2;
/*     */   private final int maxSize;
/*     */   private final int totalSlots;
/*     */   private final int maxUsedSlots;
/*     */   private final Object[] keys;
/*     */   private final short[] next;
/*     */   private final short[] prev;
/*     */   private final short[] inodes;
/*     */   private final short[] reverse;
/*     */   private int size;
/*     */   private int usedSlots;
/*     */   private short mru;
/*     */   private short lru;
/*     */   
/*  28 */   public InodeCache() { this(1024); }
/*     */   
/*  30 */   public InodeCache(int paramInt) { this.maxSize = paramInt;
/*  31 */     this.totalSlots = (paramInt * 2 * 2 + 3);
/*  32 */     this.maxUsedSlots = (this.totalSlots / 2);
/*  33 */     if (this.totalSlots > 32767) throw new IllegalArgumentException("cache size too large");
/*  34 */     this.keys = new Object[this.totalSlots];
/*  35 */     this.next = new short[this.totalSlots];
/*  36 */     this.prev = new short[this.totalSlots];
/*  37 */     this.inodes = new short[this.totalSlots];
/*  38 */     this.reverse = new short[this.totalSlots];
/*  39 */     clear();
/*     */   }
/*     */   
/*  42 */   private static void fill(Object[] paramArrayOfObject, Object paramObject) { for (int i = 0; i < paramArrayOfObject.length; i++) paramArrayOfObject[i] = paramObject; }
/*  43 */   private static void fill(short[] paramArrayOfShort, short paramShort) { for (int i = 0; i < paramArrayOfShort.length; i++) paramArrayOfShort[i] = paramShort; }
/*     */   
/*  45 */   public final void clear() { this.size = (this.usedSlots = 0);
/*  46 */     this.mru = (this.lru = -1);
/*  47 */     fill(this.keys, null);
/*  48 */     fill(this.inodes, (short)-1);
/*  49 */     fill(this.reverse, (short)-1);
/*     */   }
/*     */   
/*     */   public final short get(Object paramObject) {
/*  53 */     int i = paramObject.hashCode() & 0x7FFFFFFF;
/*  54 */     int j = i % this.totalSlots;
/*  55 */     int k = j;
/*  56 */     int m = 1;
/*  57 */     int n = 1;
/*     */     
/*  59 */     int i1 = -1;
/*     */     Object localObject;
/*  61 */     short s; int i3; while ((localObject = this.keys[j]) != null) {
/*  62 */       if (localObject == PLACEHOLDER) {
/*  63 */         if (i1 == -1) i1 = j;
/*  64 */       } else if (localObject.equals(paramObject)) {
/*  65 */         s = this.inodes[j];
/*  66 */         if (j == this.mru) return s;
/*  67 */         if (this.lru == j) {
/*  68 */           this.lru = this.next[this.lru];
/*     */         } else {
/*  70 */           i2 = this.prev[j];
/*  71 */           i3 = this.next[j];
/*  72 */           this.next[i2] = i3;
/*  73 */           this.prev[i3] = i2;
/*     */         }
/*  75 */         this.prev[j] = this.mru;
/*  76 */         this.next[this.mru] = ((short)j);
/*  77 */         this.mru = ((short)j);
/*  78 */         return s;
/*     */       }
/*  80 */       j = Math.abs((k + (n != 0 ? 1 : -1) * m * m) % this.totalSlots);
/*  81 */       if (n == 0) m++;
/*  82 */       n = n == 0 ? 1 : 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  87 */     if (i1 == -1)
/*     */     {
/*  89 */       s = j;
/*  90 */       if (this.usedSlots == this.maxUsedSlots) {
/*  91 */         clear();
/*  92 */         return get(paramObject);
/*     */       }
/*  94 */       this.usedSlots += 1;
/*     */     }
/*     */     else {
/*  97 */       s = i1;
/*     */     }
/*     */     
/* 100 */     if (this.size == this.maxSize)
/*     */     {
/* 102 */       this.keys[this.lru] = PLACEHOLDER;
/* 103 */       this.inodes[this.lru] = -2;
/* 104 */       this.lru = this.next[this.lru];
/*     */     } else {
/* 106 */       if (this.size == 0) this.lru = ((short)s);
/* 107 */       this.size += 1;
/*     */     }
/*     */     
/*     */     label488:
/* 111 */     for (int i2 = i & 0x7FFF;; i2++) {
/* 112 */       j = i2 % this.totalSlots;
/* 113 */       k = j;
/* 114 */       m = 1;
/* 115 */       n = 1;
/* 116 */       i1 = -1;
/*     */       
/* 118 */       while ((i3 = this.reverse[j]) != -1) {
/* 119 */         int i4 = this.inodes[i3];
/* 120 */         if (i4 == -2) {
/* 121 */           if (i1 == -1) i1 = j;
/* 122 */         } else if (i4 == i2) {
/*     */             break label488;
/*     */           }
/* 125 */         j = Math.abs((k + (n != 0 ? 1 : -1) * m * m) % this.totalSlots);
/* 126 */         if (n == 0) m++;
/* 127 */         n = n == 0 ? 1 : 0;
/*     */       }
/*     */       
/* 130 */       if (i1 == -1) break; j = i1; break;
/*     */     }
/*     */     
/* 133 */     this.keys[s] = paramObject;
/* 134 */     this.reverse[j] = ((short)s);
/* 135 */     this.inodes[s] = ((short)i2);
/* 136 */     if (this.mru != -1) {
/* 137 */       this.prev[s] = this.mru;
/* 138 */       this.next[this.mru] = ((short)s);
/*     */     }
/* 140 */     this.mru = ((short)s);
/* 141 */     return (short)i2;
/*     */   }
/*     */   
/*     */   public Object reverse(short paramShort) {
/* 145 */     int i = paramShort % this.totalSlots;
/* 146 */     int j = i;
/* 147 */     int k = 1;
/* 148 */     int m = 1;
/*     */     int n;
/* 150 */     while ((n = this.reverse[i]) != -1) {
/* 151 */       if (this.inodes[n] == paramShort) return this.keys[n];
/* 152 */       i = Math.abs((j + (m != 0 ? 1 : -1) * k * k) % this.totalSlots);
/* 153 */       if (m == 0) k++;
/* 154 */       m = m == 0 ? 1 : 0;
/*     */     }
/* 156 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\util\InodeCache.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */