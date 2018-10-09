/*     */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
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
/*     */ public class LongHashSet
/*     */ {
/*     */   private static final int INITIAL_SIZE = 3;
/*     */   private static final double LOAD_FACTOR = 0.75D;
/*     */   private static final long FREE = 0L;
/*     */   private static final long REMOVED = Long.MIN_VALUE;
/*     */   private int freeEntries;
/*     */   private int elements;
/*     */   private long[] values;
/*     */   private int modCount;
/*     */   
/*     */   public LongHashSet()
/*     */   {
/*  37 */     this(3);
/*     */   }
/*     */   
/*     */   public LongHashSet(int size) {
/*  41 */     this.values = new long[size == 0 ? 1 : size];
/*  42 */     this.elements = 0;
/*  43 */     this.freeEntries = this.values.length;
/*  44 */     this.modCount = 0;
/*     */   }
/*     */   
/*     */   public Iterator iterator() {
/*  48 */     return new Itr();
/*     */   }
/*     */   
/*     */   public int size() {
/*  52 */     return this.elements;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  56 */     return this.elements == 0;
/*     */   }
/*     */   
/*     */   public boolean contains(int msw, int lsw) {
/*  60 */     return contains(LongHash.toLong(msw, lsw));
/*     */   }
/*     */   
/*     */   public boolean contains(long value) {
/*  64 */     int hash = hash(value);
/*  65 */     int index = (hash & 0x7FFFFFFF) % this.values.length;
/*  66 */     int offset = 1;
/*     */     
/*     */ 
/*  69 */     while ((this.values[index] != 0L) && ((hash(this.values[index]) != hash) || (this.values[index] != value))) {
/*  70 */       index = (index + offset & 0x7FFFFFFF) % this.values.length;
/*  71 */       offset = offset * 2 + 1;
/*     */       
/*  73 */       if (offset == -1) {
/*  74 */         offset = 2;
/*     */       }
/*     */     }
/*     */     
/*  78 */     return this.values[index] != 0L;
/*     */   }
/*     */   
/*     */   public boolean add(int msw, int lsw) {
/*  82 */     return add(LongHash.toLong(msw, lsw));
/*     */   }
/*     */   
/*     */   public boolean add(long value) {
/*  86 */     int hash = hash(value);
/*  87 */     int index = (hash & 0x7FFFFFFF) % this.values.length;
/*  88 */     int offset = 1;
/*  89 */     int deletedix = -1;
/*     */     
/*     */ 
/*  92 */     while ((this.values[index] != 0L) && ((hash(this.values[index]) != hash) || (this.values[index] != value)))
/*     */     {
/*     */ 
/*  95 */       if (this.values[index] == Long.MIN_VALUE) {
/*  96 */         deletedix = index;
/*     */       }
/*     */       
/*  99 */       index = (index + offset & 0x7FFFFFFF) % this.values.length;
/* 100 */       offset = offset * 2 + 1;
/*     */       
/* 102 */       if (offset == -1) {
/* 103 */         offset = 2;
/*     */       }
/*     */     }
/*     */     
/* 107 */     if (this.values[index] == 0L) {
/* 108 */       if (deletedix != -1) {
/* 109 */         index = deletedix;
/*     */       } else {
/* 111 */         this.freeEntries -= 1;
/*     */       }
/*     */       
/* 114 */       this.modCount += 1;
/* 115 */       this.elements += 1;
/* 116 */       this.values[index] = value;
/*     */       
/* 118 */       if (1.0D - this.freeEntries / this.values.length > 0.75D) {
/* 119 */         rehash();
/*     */       }
/*     */       
/* 122 */       return true;
/*     */     }
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public void remove(int msw, int lsw)
/*     */   {
/* 129 */     remove(LongHash.toLong(msw, lsw));
/*     */   }
/*     */   
/*     */   public boolean remove(long value) {
/* 133 */     int hash = hash(value);
/* 134 */     int index = (hash & 0x7FFFFFFF) % this.values.length;
/* 135 */     int offset = 1;
/*     */     
/*     */ 
/* 138 */     while ((this.values[index] != 0L) && ((hash(this.values[index]) != hash) || (this.values[index] != value))) {
/* 139 */       index = (index + offset & 0x7FFFFFFF) % this.values.length;
/* 140 */       offset = offset * 2 + 1;
/*     */       
/* 142 */       if (offset == -1) {
/* 143 */         offset = 2;
/*     */       }
/*     */     }
/*     */     
/* 147 */     if (this.values[index] != 0L) {
/* 148 */       this.values[index] = Long.MIN_VALUE;
/* 149 */       this.modCount += 1;
/* 150 */       this.elements -= 1;
/* 151 */       return true;
/*     */     }
/* 153 */     return false;
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/* 158 */     this.elements = 0;
/* 159 */     for (int ix = 0; ix < this.values.length; ix++) {
/* 160 */       this.values[ix] = 0L;
/*     */     }
/*     */     
/* 163 */     this.freeEntries = this.values.length;
/* 164 */     this.modCount += 1;
/*     */   }
/*     */   
/*     */   public long[] toArray() {
/* 168 */     long[] result = new long[this.elements];
/* 169 */     long[] values = Arrays.copyOf(this.values, this.values.length);
/* 170 */     int pos = 0;
/*     */     long[] arrayOfLong1;
/* 172 */     int i = (arrayOfLong1 = values).length; for (int j = 0; j < i; j++) { long value = arrayOfLong1[j];
/* 173 */       if ((value != 0L) && (value != Long.MIN_VALUE)) {
/* 174 */         result[(pos++)] = value;
/*     */       }
/*     */     }
/*     */     
/* 178 */     return result;
/*     */   }
/*     */   
/*     */   public long popFirst() { long[] arrayOfLong;
/* 182 */     int i = (arrayOfLong = this.values).length; for (int j = 0; j < i; j++) { long value = arrayOfLong[j];
/* 183 */       if ((value != 0L) && (value != Long.MIN_VALUE)) {
/* 184 */         remove(value);
/* 185 */         return value;
/*     */       }
/*     */     }
/*     */     
/* 189 */     return 0L;
/*     */   }
/*     */   
/*     */   public long[] popAll() {
/* 193 */     long[] ret = toArray();
/* 194 */     clear();
/* 195 */     return ret;
/*     */   }
/*     */   
/*     */   private int hash(long value)
/*     */   {
/* 200 */     value ^= value >>> 33;
/* 201 */     value *= -49064778989728563L;
/* 202 */     value ^= value >>> 33;
/* 203 */     value *= -4265267296055464877L;
/* 204 */     value ^= value >>> 33;
/* 205 */     return (int)value;
/*     */   }
/*     */   
/*     */   private void rehash() {
/* 209 */     int gargagecells = this.values.length - (this.elements + this.freeEntries);
/* 210 */     if (gargagecells / this.values.length > 0.05D) {
/* 211 */       rehash(this.values.length);
/*     */     } else {
/* 213 */       rehash(this.values.length * 2 + 1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void rehash(int newCapacity) {
/* 218 */     long[] newValues = new long[newCapacity];
/*     */     long[] arrayOfLong1;
/* 220 */     int i = (arrayOfLong1 = this.values).length; for (int j = 0; j < i; j++) { long value = arrayOfLong1[j];
/* 221 */       if ((value != 0L) && (value != Long.MIN_VALUE))
/*     */       {
/*     */ 
/*     */ 
/* 225 */         int hash = hash(value);
/* 226 */         int index = (hash & 0x7FFFFFFF) % newCapacity;
/* 227 */         int offset = 1;
/*     */         
/*     */ 
/* 230 */         while (newValues[index] != 0L) {
/* 231 */           index = (index + offset & 0x7FFFFFFF) % newCapacity;
/* 232 */           offset = offset * 2 + 1;
/*     */           
/* 234 */           if (offset == -1) {
/* 235 */             offset = 2;
/*     */           }
/*     */         }
/*     */         
/* 239 */         newValues[index] = value;
/*     */       }
/*     */     }
/* 242 */     this.values = newValues;
/* 243 */     this.freeEntries = (this.values.length - this.elements);
/*     */   }
/*     */   
/*     */   private class Itr implements Iterator {
/*     */     private int index;
/* 248 */     private int lastReturned = -1;
/*     */     private int expectedModCount;
/*     */     
/*     */     public Itr() {
/* 252 */       for (this.index = 0; (this.index < LongHashSet.this.values.length) && ((LongHashSet.this.values[this.index] == 0L) || (LongHashSet.this.values[this.index] == Long.MIN_VALUE)); this.index += 1) {}
/*     */       
/*     */ 
/* 255 */       this.expectedModCount = LongHashSet.this.modCount;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 259 */       return this.index != LongHashSet.this.values.length;
/*     */     }
/*     */     
/*     */     public Long next() {
/* 263 */       if (LongHashSet.this.modCount != this.expectedModCount) {
/* 264 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 267 */       int length = LongHashSet.this.values.length;
/* 268 */       if (this.index >= length) {
/* 269 */         this.lastReturned = -2;
/* 270 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 273 */       this.lastReturned = this.index;
/* 274 */       for (this.index += 1; (this.index < length) && ((LongHashSet.this.values[this.index] == 0L) || (LongHashSet.this.values[this.index] == Long.MIN_VALUE)); this.index += 1) {}
/*     */       
/*     */ 
/*     */ 
/* 278 */       if (LongHashSet.this.values[this.lastReturned] == 0L) {
/* 279 */         return Long.valueOf(0L);
/*     */       }
/* 281 */       return Long.valueOf(LongHashSet.this.values[this.lastReturned]);
/*     */     }
/*     */     
/*     */     public void remove()
/*     */     {
/* 286 */       if (LongHashSet.this.modCount != this.expectedModCount) {
/* 287 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 290 */       if ((this.lastReturned == -1) || (this.lastReturned == -2)) {
/* 291 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 294 */       if ((LongHashSet.this.values[this.lastReturned] != 0L) && (LongHashSet.this.values[this.lastReturned] != Long.MIN_VALUE)) {
/* 295 */         LongHashSet.this.values[this.lastReturned] = Long.MIN_VALUE;
/* 296 */         LongHashSet.this.elements -= 1;
/* 297 */         LongHashSet.this.modCount += 1;
/* 298 */         this.expectedModCount = LongHashSet.this.modCount;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\LongHashSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */