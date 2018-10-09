/*     */ package io.netty.util.collection;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
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
/*     */ public class IntObjectHashMap<V>
/*     */   implements IntObjectMap<V>, Iterable<IntObjectMap.Entry<V>>
/*     */ {
/*     */   private static final int DEFAULT_CAPACITY = 11;
/*     */   private static final float DEFAULT_LOAD_FACTOR = 0.5F;
/*  43 */   private static final Object NULL_VALUE = new Object();
/*     */   
/*     */   private int maxSize;
/*     */   
/*     */   private final float loadFactor;
/*     */   
/*     */   private int[] keys;
/*     */   
/*     */   private V[] values;
/*     */   private int size;
/*     */   
/*     */   public IntObjectHashMap()
/*     */   {
/*  56 */     this(11, 0.5F);
/*     */   }
/*     */   
/*     */   public IntObjectHashMap(int paramInt) {
/*  60 */     this(paramInt, 0.5F);
/*     */   }
/*     */   
/*     */   public IntObjectHashMap(int paramInt, float paramFloat) {
/*  64 */     if (paramInt < 1) {
/*  65 */       throw new IllegalArgumentException("initialCapacity must be >= 1");
/*     */     }
/*  67 */     if ((paramFloat <= 0.0F) || (paramFloat > 1.0F))
/*     */     {
/*     */ 
/*  70 */       throw new IllegalArgumentException("loadFactor must be > 0 and <= 1");
/*     */     }
/*     */     
/*  73 */     this.loadFactor = paramFloat;
/*     */     
/*     */ 
/*  76 */     int i = adjustCapacity(paramInt);
/*     */     
/*     */ 
/*  79 */     this.keys = new int[i];
/*     */     
/*  81 */     Object[] arrayOfObject = (Object[])new Object[i];
/*  82 */     this.values = arrayOfObject;
/*     */     
/*     */ 
/*  85 */     this.maxSize = calcMaxSize(i);
/*     */   }
/*     */   
/*     */   private static <T> T toExternal(T paramT) {
/*  89 */     return paramT == NULL_VALUE ? null : paramT;
/*     */   }
/*     */   
/*     */   private static <T> T toInternal(T paramT)
/*     */   {
/*  94 */     return (T)(paramT == null ? NULL_VALUE : paramT);
/*     */   }
/*     */   
/*     */   public V get(int paramInt)
/*     */   {
/*  99 */     int i = indexOf(paramInt);
/* 100 */     return i == -1 ? null : toExternal(this.values[i]);
/*     */   }
/*     */   
/*     */   public V put(int paramInt, V paramV)
/*     */   {
/* 105 */     int i = hashIndex(paramInt);
/* 106 */     int j = i;
/*     */     do
/*     */     {
/* 109 */       if (this.values[j] == null)
/*     */       {
/* 111 */         this.keys[j] = paramInt;
/* 112 */         this.values[j] = toInternal(paramV);
/* 113 */         growSize();
/* 114 */         return null; }
/* 115 */       if (this.keys[j] == paramInt)
/*     */       {
/* 117 */         Object localObject = this.values[j];
/* 118 */         this.values[j] = toInternal(paramV);
/* 119 */         return (V)toExternal(localObject);
/*     */       }
/*     */       
/*     */     }
/* 123 */     while ((j = probeNext(j)) != i);
/*     */     
/* 125 */     throw new IllegalStateException("Unable to insert");
/*     */   }
/*     */   
/*     */ 
/*     */   private int probeNext(int paramInt)
/*     */   {
/* 131 */     return paramInt == this.values.length - 1 ? 0 : paramInt + 1;
/*     */   }
/*     */   
/*     */   public void putAll(IntObjectMap<V> paramIntObjectMap)
/*     */   {
/* 136 */     if ((paramIntObjectMap instanceof IntObjectHashMap))
/*     */     {
/* 138 */       localObject1 = (IntObjectHashMap)paramIntObjectMap;
/* 139 */       for (int i = 0; i < ((IntObjectHashMap)localObject1).values.length; i++) {
/* 140 */         Object localObject2 = localObject1.values[i];
/* 141 */         if (localObject2 != null) {
/* 142 */           put(localObject1.keys[i], localObject2);
/*     */         }
/*     */       }
/* 145 */       return;
/*     */     }
/*     */     
/*     */ 
/* 149 */     for (Object localObject1 = paramIntObjectMap.entries().iterator(); ((Iterator)localObject1).hasNext();) { IntObjectMap.Entry localEntry = (IntObjectMap.Entry)((Iterator)localObject1).next();
/* 150 */       put(localEntry.key(), localEntry.value());
/*     */     }
/*     */   }
/*     */   
/*     */   public V remove(int paramInt)
/*     */   {
/* 156 */     int i = indexOf(paramInt);
/* 157 */     if (i == -1) {
/* 158 */       return null;
/*     */     }
/*     */     
/* 161 */     Object localObject = this.values[i];
/* 162 */     removeAt(i);
/* 163 */     return (V)toExternal(localObject);
/*     */   }
/*     */   
/*     */   public int size()
/*     */   {
/* 168 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 173 */     return this.size == 0;
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/* 178 */     Arrays.fill(this.keys, 0);
/* 179 */     Arrays.fill(this.values, null);
/* 180 */     this.size = 0;
/*     */   }
/*     */   
/*     */   public boolean containsKey(int paramInt)
/*     */   {
/* 185 */     return indexOf(paramInt) >= 0;
/*     */   }
/*     */   
/*     */   public boolean containsValue(V paramV)
/*     */   {
/* 190 */     Object localObject = toInternal(paramV);
/* 191 */     for (int i = 0; i < this.values.length; i++)
/*     */     {
/* 193 */       if ((this.values[i] != null) && (this.values[i].equals(localObject))) {
/* 194 */         return true;
/*     */       }
/*     */     }
/* 197 */     return false;
/*     */   }
/*     */   
/*     */   public Iterable<IntObjectMap.Entry<V>> entries()
/*     */   {
/* 202 */     return this;
/*     */   }
/*     */   
/*     */   public Iterator<IntObjectMap.Entry<V>> iterator()
/*     */   {
/* 207 */     return new IteratorImpl(null);
/*     */   }
/*     */   
/*     */   public int[] keys()
/*     */   {
/* 212 */     int[] arrayOfInt = new int[size()];
/* 213 */     int i = 0;
/* 214 */     for (int j = 0; j < this.values.length; j++) {
/* 215 */       if (this.values[j] != null) {
/* 216 */         arrayOfInt[(i++)] = this.keys[j];
/*     */       }
/*     */     }
/* 219 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */   public V[] values(Class<V> paramClass)
/*     */   {
/* 225 */     Object[] arrayOfObject = (Object[])Array.newInstance(paramClass, size());
/* 226 */     int i = 0;
/* 227 */     for (int j = 0; j < this.values.length; j++) {
/* 228 */       if (this.values[j] != null) {
/* 229 */         arrayOfObject[(i++)] = this.values[j];
/*     */       }
/*     */     }
/* 232 */     return arrayOfObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 240 */     int i = this.size;
/* 241 */     for (int j = 0; j < this.keys.length; j++)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 249 */       i ^= this.keys[j];
/*     */     }
/* 251 */     return i;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 256 */     if (this == paramObject)
/* 257 */       return true;
/* 258 */     if (!(paramObject instanceof IntObjectMap)) {
/* 259 */       return false;
/*     */     }
/*     */     
/* 262 */     IntObjectMap localIntObjectMap = (IntObjectMap)paramObject;
/* 263 */     if (this.size != localIntObjectMap.size()) {
/* 264 */       return false;
/*     */     }
/* 266 */     for (int i = 0; i < this.values.length; i++) {
/* 267 */       Object localObject1 = this.values[i];
/* 268 */       if (localObject1 != null) {
/* 269 */         int j = this.keys[i];
/* 270 */         Object localObject2 = localIntObjectMap.get(j);
/* 271 */         if (localObject1 == NULL_VALUE) {
/* 272 */           if (localObject2 != null) {
/* 273 */             return false;
/*     */           }
/* 275 */         } else if (!localObject1.equals(localObject2)) {
/* 276 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 280 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int indexOf(int paramInt)
/*     */   {
/* 290 */     int i = hashIndex(paramInt);
/* 291 */     int j = i;
/*     */     do
/*     */     {
/* 294 */       if (this.values[j] == null)
/*     */       {
/* 296 */         return -1; }
/* 297 */       if (paramInt == this.keys[j]) {
/* 298 */         return j;
/*     */       }
/*     */       
/*     */     }
/* 302 */     while ((j = probeNext(j)) != i);
/* 303 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int hashIndex(int paramInt)
/*     */   {
/* 312 */     return paramInt % this.keys.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void growSize()
/*     */   {
/* 319 */     this.size += 1;
/*     */     
/* 321 */     if (this.size > this.maxSize)
/*     */     {
/*     */ 
/* 324 */       rehash(adjustCapacity((int)Math.min(this.keys.length * 2.0D, 2.147483639E9D)));
/* 325 */     } else if (this.size == this.keys.length)
/*     */     {
/*     */ 
/* 328 */       rehash(this.keys.length);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static int adjustCapacity(int paramInt)
/*     */   {
/* 336 */     return paramInt | 0x1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void removeAt(int paramInt)
/*     */   {
/* 346 */     this.size -= 1;
/*     */     
/*     */ 
/* 349 */     this.keys[paramInt] = 0;
/* 350 */     this.values[paramInt] = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 357 */     int i = paramInt;
/* 358 */     for (int j = probeNext(paramInt); this.values[j] != null; j = probeNext(j)) {
/* 359 */       int k = hashIndex(this.keys[j]);
/* 360 */       if (((j < k) && ((k <= i) || (i <= j))) || ((k <= i) && (i <= j)))
/*     */       {
/*     */ 
/* 363 */         this.keys[i] = this.keys[j];
/* 364 */         this.values[i] = this.values[j];
/*     */         
/* 366 */         this.keys[j] = 0;
/* 367 */         this.values[j] = null;
/* 368 */         i = j;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private int calcMaxSize(int paramInt)
/*     */   {
/* 378 */     int i = paramInt - 1;
/* 379 */     return Math.min(i, (int)(paramInt * this.loadFactor));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void rehash(int paramInt)
/*     */   {
/* 388 */     int[] arrayOfInt = this.keys;
/* 389 */     Object[] arrayOfObject1 = this.values;
/*     */     
/* 391 */     this.keys = new int[paramInt];
/*     */     
/* 393 */     Object[] arrayOfObject2 = (Object[])new Object[paramInt];
/* 394 */     this.values = arrayOfObject2;
/*     */     
/* 396 */     this.maxSize = calcMaxSize(paramInt);
/*     */     
/*     */ 
/* 399 */     for (int i = 0; i < arrayOfObject1.length; i++) {
/* 400 */       Object localObject = arrayOfObject1[i];
/* 401 */       if (localObject != null)
/*     */       {
/*     */ 
/* 404 */         int j = arrayOfInt[i];
/* 405 */         int k = hashIndex(j);
/* 406 */         int m = k;
/*     */         for (;;)
/*     */         {
/* 409 */           if (this.values[m] == null) {
/* 410 */             this.keys[m] = j;
/* 411 */             this.values[m] = toInternal(localObject);
/* 412 */             break;
/*     */           }
/*     */           
/*     */ 
/* 416 */           m = probeNext(m);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private final class IteratorImpl
/*     */     implements Iterator<IntObjectMap.Entry<V>>, IntObjectMap.Entry<V>
/*     */   {
/* 426 */     private int prevIndex = -1;
/* 427 */     private int nextIndex = -1;
/* 428 */     private int entryIndex = -1;
/*     */     
/*     */     private IteratorImpl() {}
/*     */     
/* 432 */     private void scanNext() { while (++this.nextIndex != IntObjectHashMap.this.values.length) { if (IntObjectHashMap.this.values[this.nextIndex] != null) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean hasNext()
/*     */     {
/* 440 */       if (this.nextIndex == -1) {
/* 441 */         scanNext();
/*     */       }
/* 443 */       return this.nextIndex < IntObjectHashMap.this.keys.length;
/*     */     }
/*     */     
/*     */     public IntObjectMap.Entry<V> next()
/*     */     {
/* 448 */       if (!hasNext()) {
/* 449 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 452 */       this.prevIndex = this.nextIndex;
/* 453 */       scanNext();
/*     */       
/*     */ 
/* 456 */       this.entryIndex = this.prevIndex;
/* 457 */       return this;
/*     */     }
/*     */     
/*     */     public void remove()
/*     */     {
/* 462 */       if (this.prevIndex < 0) {
/* 463 */         throw new IllegalStateException("next must be called before each remove.");
/*     */       }
/* 465 */       IntObjectHashMap.this.removeAt(this.prevIndex);
/* 466 */       this.prevIndex = -1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public int key()
/*     */     {
/* 474 */       return IntObjectHashMap.this.keys[this.entryIndex];
/*     */     }
/*     */     
/*     */     public V value()
/*     */     {
/* 479 */       return (V)IntObjectHashMap.toExternal(IntObjectHashMap.this.values[this.entryIndex]);
/*     */     }
/*     */     
/*     */     public void setValue(V paramV)
/*     */     {
/* 484 */       IntObjectHashMap.this.values[this.entryIndex] = IntObjectHashMap.toInternal(paramV);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 490 */     if (this.size == 0) {
/* 491 */       return "{}";
/*     */     }
/* 493 */     StringBuilder localStringBuilder = new StringBuilder(4 * this.size);
/* 494 */     for (int i = 0; i < this.values.length; i++) {
/* 495 */       Object localObject = this.values[i];
/* 496 */       if (localObject != null) {
/* 497 */         localStringBuilder.append(localStringBuilder.length() == 0 ? "{" : ", ");
/* 498 */         localStringBuilder.append(this.keys[i]).append('=').append(localObject == this ? "(this Map)" : localObject);
/*     */       }
/*     */     }
/* 501 */     return '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\collection\IntObjectHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */