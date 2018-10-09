/*     */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class LongObjectHashMap<V> implements Cloneable, Serializable
/*     */ {
/*     */   static final long serialVersionUID = 2841537710170573815L;
/*     */   private static final long EMPTY_KEY = Long.MIN_VALUE;
/*     */   private static final int BUCKET_SIZE = 4096;
/*     */   private transient long[][] keys;
/*     */   private transient V[][] values;
/*     */   private transient int modCount;
/*     */   private transient int size;
/*     */   
/*     */   public LongObjectHashMap()
/*     */   {
/*  31 */     initialize();
/*     */   }
/*     */   
/*     */   public LongObjectHashMap(Map<? extends Long, ? extends V> map) {
/*  35 */     this();
/*  36 */     putAll(map);
/*     */   }
/*     */   
/*     */   public int size() {
/*  40 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  44 */     return this.size == 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(long key) {
/*  48 */     return get(key) != null;
/*     */   }
/*     */   
/*     */   public boolean containsValue(V value) {
/*  52 */     for (V val : values()) {
/*  53 */       if ((val == value) || (val.equals(value))) {
/*  54 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   public V get(long key) {
/*  62 */     int index = (int)(keyIndex(key) & 0xFFF);
/*  63 */     long[] inner = this.keys[index];
/*  64 */     if (inner == null) { return null;
/*     */     }
/*  66 */     for (int i = 0; i < inner.length; i++) {
/*  67 */       long innerKey = inner[i];
/*  68 */       if (innerKey == Long.MIN_VALUE)
/*  69 */         return null;
/*  70 */       if (innerKey == key) {
/*  71 */         return (V)this.values[index][i];
/*     */       }
/*     */     }
/*     */     
/*  75 */     return null;
/*     */   }
/*     */   
/*     */   public V put(long key, V value) {
/*  79 */     int index = (int)(keyIndex(key) & 0xFFF);
/*  80 */     long[] innerKeys = this.keys[index];
/*  81 */     Object[] innerValues = this.values[index];
/*  82 */     this.modCount += 1;
/*     */     
/*  84 */     if (innerKeys == null)
/*     */     {
/*  86 */       this.keys[index] = (innerKeys = new long[8]);
/*  87 */       Arrays.fill(innerKeys, Long.MIN_VALUE);
/*  88 */       this.values[index] = (innerValues = new Object[8]);
/*  89 */       innerKeys[0] = key;
/*  90 */       innerValues[0] = value;
/*  91 */       this.size += 1;
/*     */     }
/*     */     else {
/*  94 */       for (int i = 0; i < innerKeys.length; i++)
/*     */       {
/*  96 */         if (innerKeys[i] == Long.MIN_VALUE) {
/*  97 */           this.size += 1;
/*  98 */           innerKeys[i] = key;
/*  99 */           innerValues[i] = value;
/* 100 */           return null;
/*     */         }
/*     */         
/*     */ 
/* 104 */         if (innerKeys[i] == key) {
/* 105 */           V oldValue = innerValues[i];
/* 106 */           innerKeys[i] = key;
/* 107 */           innerValues[i] = value;
/* 108 */           return oldValue;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 113 */       this.keys[index] = (innerKeys = Arrays.copyOf(innerKeys, i << 1));
/* 114 */       Arrays.fill(innerKeys, i, innerKeys.length, Long.MIN_VALUE);
/* 115 */       this.values[index] = (innerValues = Arrays.copyOf(innerValues, i << 1));
/* 116 */       innerKeys[i] = key;
/* 117 */       innerValues[i] = value;
/* 118 */       this.size += 1;
/*     */     }
/*     */     
/* 121 */     return null;
/*     */   }
/*     */   
/*     */   public V remove(long key) {
/* 125 */     int index = (int)(keyIndex(key) & 0xFFF);
/* 126 */     long[] inner = this.keys[index];
/* 127 */     if (inner == null) {
/* 128 */       return null;
/*     */     }
/*     */     
/* 131 */     for (int i = 0; i < inner.length; i++)
/*     */     {
/* 133 */       if (inner[i] == Long.MIN_VALUE) {
/*     */         break;
/*     */       }
/*     */       
/* 137 */       if (inner[i] == key) {
/* 138 */         V value = this.values[index][i];
/*     */         
/* 140 */         for (i++; i < inner.length; i++) {
/* 141 */           if (inner[i] == Long.MIN_VALUE) {
/*     */             break;
/*     */           }
/*     */           
/* 145 */           inner[(i - 1)] = inner[i];
/* 146 */           this.values[index][(i - 1)] = this.values[index][i];
/*     */         }
/*     */         
/* 149 */         inner[(i - 1)] = Long.MIN_VALUE;
/* 150 */         this.values[index][(i - 1)] = null;
/* 151 */         this.size -= 1;
/* 152 */         this.modCount += 1;
/* 153 */         return value;
/*     */       }
/*     */     }
/*     */     
/* 157 */     return null;
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends V> map) {
/* 161 */     for (Map.Entry entry : map.entrySet()) {
/* 162 */       put(((Long)entry.getKey()).longValue(), entry.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear() {
/* 167 */     if (this.size == 0) {
/* 168 */       return;
/*     */     }
/*     */     
/* 171 */     this.modCount += 1;
/* 172 */     this.size = 0;
/* 173 */     Arrays.fill(this.keys, null);
/* 174 */     Arrays.fill(this.values, null);
/*     */   }
/*     */   
/*     */   public Set<Long> keySet() {
/* 178 */     return new KeySet(null);
/*     */   }
/*     */   
/*     */   public Collection<V> values() {
/* 182 */     return new ValueCollection(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Set<Map.Entry<Long, V>> entrySet()
/*     */   {
/* 195 */     HashSet<Map.Entry<Long, V>> set = new HashSet();
/* 196 */     for (Iterator localIterator = keySet().iterator(); localIterator.hasNext();) { long key = ((Long)localIterator.next()).longValue();
/* 197 */       set.add(new Entry(key, get(key)));
/*     */     }
/*     */     
/* 200 */     return set;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 204 */     LongObjectHashMap clone = (LongObjectHashMap)super.clone();
/*     */     
/* 206 */     clone.clear();
/*     */     
/* 208 */     clone.initialize();
/*     */     
/*     */ 
/* 211 */     for (Iterator localIterator = keySet().iterator(); localIterator.hasNext();) { long key = ((Long)localIterator.next()).longValue();
/* 212 */       V value = get(key);
/* 213 */       clone.put(key, value);
/*     */     }
/*     */     
/* 216 */     return clone;
/*     */   }
/*     */   
/*     */   private void initialize() {
/* 220 */     this.keys = new long['က'][];
/* 221 */     this.values = new Object['က'][];
/*     */   }
/*     */   
/*     */   private long keyIndex(long key) {
/* 225 */     key ^= key >>> 33;
/* 226 */     key *= -49064778989728563L;
/* 227 */     key ^= key >>> 33;
/* 228 */     key *= -4265267296055464877L;
/* 229 */     key ^= key >>> 33;
/* 230 */     return key;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream outputStream) throws IOException {
/* 234 */     outputStream.defaultWriteObject();
/*     */     
/* 236 */     for (Iterator localIterator = keySet().iterator(); localIterator.hasNext();) { long key = ((Long)localIterator.next()).longValue();
/* 237 */       V value = get(key);
/* 238 */       outputStream.writeLong(key);
/* 239 */       outputStream.writeObject(value);
/*     */     }
/*     */     
/* 242 */     outputStream.writeLong(Long.MIN_VALUE);
/* 243 */     outputStream.writeObject(null);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
/* 247 */     inputStream.defaultReadObject();
/* 248 */     initialize();
/*     */     for (;;)
/*     */     {
/* 251 */       long key = inputStream.readLong();
/* 252 */       V value = inputStream.readObject();
/* 253 */       if ((key == Long.MIN_VALUE) && (value == null)) {
/*     */         break;
/*     */       }
/*     */       
/* 257 */       put(key, value);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ValueIterator implements Iterator<V>
/*     */   {
/*     */     private int count;
/*     */     private int index;
/*     */     private int innerIndex;
/*     */     private int expectedModCount;
/* 267 */     private long lastReturned = Long.MIN_VALUE;
/*     */     
/* 269 */     long prevKey = Long.MIN_VALUE;
/*     */     V prevValue;
/*     */     
/*     */     ValueIterator() {
/* 273 */       this.expectedModCount = LongObjectHashMap.this.modCount;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 277 */       return this.count < LongObjectHashMap.this.size;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 281 */       if (LongObjectHashMap.this.modCount != this.expectedModCount) {
/* 282 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 285 */       if (this.lastReturned == Long.MIN_VALUE) {
/* 286 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 289 */       this.count -= 1;
/* 290 */       LongObjectHashMap.this.remove(this.lastReturned);
/* 291 */       this.lastReturned = Long.MIN_VALUE;
/* 292 */       this.expectedModCount = LongObjectHashMap.this.modCount;
/*     */     }
/*     */     
/*     */     public V next() {
/* 296 */       if (LongObjectHashMap.this.modCount != this.expectedModCount) {
/* 297 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 300 */       if (!hasNext()) {
/* 301 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 304 */       long[][] keys = LongObjectHashMap.this.keys;
/* 305 */       this.count += 1;
/*     */       
/* 307 */       if (this.prevKey != Long.MIN_VALUE) {
/* 308 */         this.innerIndex += 1;
/*     */       }
/* 311 */       for (; 
/* 311 */           this.index < keys.length; this.index += 1) {
/* 312 */         if (keys[this.index] != null) {
/* 313 */           if (this.innerIndex < keys[this.index].length) {
/* 314 */             long key = keys[this.index][this.innerIndex];
/* 315 */             V value = LongObjectHashMap.this.values[this.index][this.innerIndex];
/* 316 */             if (key != Long.MIN_VALUE)
/*     */             {
/*     */ 
/*     */ 
/* 320 */               this.lastReturned = key;
/* 321 */               this.prevKey = key;
/* 322 */               this.prevValue = value;
/* 323 */               return (V)this.prevValue;
/*     */             } }
/* 325 */           this.innerIndex = 0;
/*     */         }
/*     */       }
/*     */       
/* 329 */       throw new NoSuchElementException();
/*     */     }
/*     */   }
/*     */   
/*     */   private class KeyIterator implements Iterator<Long> {
/*     */     final LongObjectHashMap<V>.ValueIterator iterator;
/*     */     
/*     */     public KeyIterator() {
/* 337 */       this.iterator = new LongObjectHashMap.ValueIterator(LongObjectHashMap.this);
/*     */     }
/*     */     
/*     */     public void remove() {
/* 341 */       this.iterator.remove();
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 345 */       return this.iterator.hasNext();
/*     */     }
/*     */     
/*     */     public Long next() {
/* 349 */       this.iterator.next();
/* 350 */       return Long.valueOf(this.iterator.prevKey);
/*     */     }
/*     */   }
/*     */   
/*     */   private class KeySet extends AbstractSet<Long> {
/*     */     private KeySet() {}
/*     */     
/* 357 */     public void clear() { LongObjectHashMap.this.clear(); }
/*     */     
/*     */     public int size()
/*     */     {
/* 361 */       return LongObjectHashMap.this.size();
/*     */     }
/*     */     
/*     */     public boolean contains(Object key) {
/* 365 */       return ((key instanceof Long)) && (LongObjectHashMap.this.containsKey(((Long)key).longValue()));
/*     */     }
/*     */     
/*     */     public boolean remove(Object key)
/*     */     {
/* 370 */       return LongObjectHashMap.this.remove(((Long)key).longValue()) != null;
/*     */     }
/*     */     
/*     */     public Iterator<Long> iterator() {
/* 374 */       return new LongObjectHashMap.KeyIterator(LongObjectHashMap.this);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ValueCollection extends AbstractCollection<V> {
/*     */     private ValueCollection() {}
/*     */     
/* 381 */     public void clear() { LongObjectHashMap.this.clear(); }
/*     */     
/*     */     public int size()
/*     */     {
/* 385 */       return LongObjectHashMap.this.size();
/*     */     }
/*     */     
/*     */     public boolean contains(Object value) {
/* 389 */       return LongObjectHashMap.this.containsValue(value);
/*     */     }
/*     */     
/*     */     public Iterator<V> iterator() {
/* 393 */       return new LongObjectHashMap.ValueIterator(LongObjectHashMap.this);
/*     */     }
/*     */   }
/*     */   
/*     */   private class Entry implements Map.Entry<Long, V>
/*     */   {
/*     */     private final Long key;
/*     */     private V value;
/*     */     
/*     */     Entry(V arg3) {
/* 403 */       this.key = Long.valueOf(k);
/* 404 */       this.value = v;
/*     */     }
/*     */     
/*     */     public Long getKey() {
/* 408 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 412 */       return (V)this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V v) {
/* 416 */       V old = this.value;
/* 417 */       this.value = v;
/* 418 */       LongObjectHashMap.this.put(this.key.longValue(), v);
/* 419 */       return old;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\LongObjectHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */