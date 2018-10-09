/*     */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractList;
/*     */ import java.util.Arrays;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.RandomAccess;
/*     */ 
/*     */ 
/*     */ public class UnsafeList<E>
/*     */   extends AbstractList<E>
/*     */   implements List<E>, RandomAccess, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8683452581112892191L;
/*     */   private transient Object[] data;
/*     */   private int size;
/*     */   private int initialCapacity;
/*  24 */   private Iterator[] iterPool = new Iterator[1];
/*     */   private int maxPool;
/*     */   private int poolCounter;
/*     */   
/*     */   public UnsafeList(int capacity, int maxIterPool)
/*     */   {
/*  30 */     if (capacity < 0) capacity = 32;
/*  31 */     int rounded = Integer.highestOneBit(capacity - 1) << 1;
/*  32 */     this.data = new Object[rounded];
/*  33 */     this.initialCapacity = rounded;
/*  34 */     this.maxPool = maxIterPool;
/*  35 */     this.iterPool[0] = new Itr();
/*     */   }
/*     */   
/*     */   public UnsafeList(int capacity) {
/*  39 */     this(capacity, 5);
/*     */   }
/*     */   
/*     */   public UnsafeList() {
/*  43 */     this(32);
/*     */   }
/*     */   
/*     */   public E get(int index) {
/*  47 */     rangeCheck(index);
/*     */     
/*  49 */     return (E)this.data[index];
/*     */   }
/*     */   
/*     */   public E unsafeGet(int index) {
/*  53 */     return (E)this.data[index];
/*     */   }
/*     */   
/*     */   public E set(int index, E element) {
/*  57 */     rangeCheck(index);
/*     */     
/*  59 */     E old = this.data[index];
/*  60 */     this.data[index] = element;
/*  61 */     return old;
/*     */   }
/*     */   
/*     */   public boolean add(E element) {
/*  65 */     growIfNeeded();
/*  66 */     this.data[(this.size++)] = element;
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   public void add(int index, E element) {
/*  71 */     growIfNeeded();
/*  72 */     System.arraycopy(this.data, index, this.data, index + 1, this.size - index);
/*  73 */     this.data[index] = element;
/*  74 */     this.size += 1;
/*     */   }
/*     */   
/*     */   public E remove(int index) {
/*  78 */     rangeCheck(index);
/*     */     
/*  80 */     E old = this.data[index];
/*  81 */     int movedCount = this.size - index - 1;
/*  82 */     if (movedCount > 0) {
/*  83 */       System.arraycopy(this.data, index + 1, this.data, index, movedCount);
/*     */     }
/*  85 */     this.data[(--this.size)] = null;
/*     */     
/*  87 */     return old;
/*     */   }
/*     */   
/*     */   public boolean remove(Object o) {
/*  91 */     int index = indexOf(o);
/*  92 */     if (index >= 0) {
/*  93 */       remove(index);
/*  94 */       return true;
/*     */     }
/*     */     
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   public int indexOf(Object o) {
/* 101 */     for (int i = 0; i < this.size; i++) {
/* 102 */       if ((o == this.data[i]) || (o.equals(this.data[i]))) {
/* 103 */         return i;
/*     */       }
/*     */     }
/*     */     
/* 107 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean contains(Object o) {
/* 111 */     return indexOf(o) >= 0;
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/* 116 */     this.size = 0;
/*     */     
/*     */ 
/* 119 */     if (this.data.length > this.initialCapacity << 3) {
/* 120 */       this.data = new Object[this.initialCapacity];
/*     */     } else {
/* 122 */       for (int i = 0; i < this.data.length; i++) {
/* 123 */         this.data[i] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void trimToSize()
/*     */   {
/* 130 */     int old = this.data.length;
/* 131 */     int rounded = Integer.highestOneBit(this.size - 1) << 1;
/* 132 */     if (rounded < old) {
/* 133 */       this.data = Arrays.copyOf(this.data, rounded);
/*     */     }
/*     */   }
/*     */   
/*     */   public int size() {
/* 138 */     return this.size;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 142 */     return this.size == 0;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 146 */     UnsafeList<E> copy = (UnsafeList)super.clone();
/* 147 */     copy.data = Arrays.copyOf(this.data, this.size);
/* 148 */     copy.size = this.size;
/* 149 */     copy.initialCapacity = this.initialCapacity;
/* 150 */     copy.iterPool = new Iterator[1];
/* 151 */     copy.iterPool[0] = new Itr();
/* 152 */     copy.maxPool = this.maxPool;
/* 153 */     copy.poolCounter = 0;
/* 154 */     return copy;
/*     */   }
/*     */   
/*     */   public Iterator<E> iterator() {
/*     */     Iterator[] arrayOfIterator1;
/* 159 */     int i = (arrayOfIterator1 = this.iterPool).length; for (int j = 0; j < i; j++) { Iterator iter = arrayOfIterator1[j];
/* 160 */       if (!((Itr)iter).valid) {
/* 161 */         UnsafeList<E>.Itr iterator = (Itr)iter;
/* 162 */         iterator.reset();
/* 163 */         return iterator;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 168 */     if (this.iterPool.length < this.maxPool) {
/* 169 */       Iterator[] newPool = new Iterator[this.iterPool.length + 1];
/* 170 */       System.arraycopy(this.iterPool, 0, newPool, 0, this.iterPool.length);
/* 171 */       this.iterPool = newPool;
/*     */       
/* 173 */       this.iterPool[(this.iterPool.length - 1)] = new Itr();
/* 174 */       return this.iterPool[(this.iterPool.length - 1)];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 179 */     this.poolCounter = (++this.poolCounter % this.iterPool.length);
/* 180 */     this.iterPool[this.poolCounter] = new Itr();
/* 181 */     return this.iterPool[this.poolCounter];
/*     */   }
/*     */   
/*     */   private void rangeCheck(int index) {
/* 185 */     if ((index >= this.size) || (index < 0)) {
/* 186 */       throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
/*     */     }
/*     */   }
/*     */   
/*     */   private void growIfNeeded() {
/* 191 */     if (this.size == this.data.length) {
/* 192 */       Object[] newData = new Object[this.data.length << 1];
/* 193 */       System.arraycopy(this.data, 0, newData, 0, this.size);
/* 194 */       this.data = newData;
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream os) throws IOException {
/* 199 */     os.defaultWriteObject();
/*     */     
/* 201 */     os.writeInt(this.size);
/* 202 */     os.writeInt(this.initialCapacity);
/* 203 */     for (int i = 0; i < this.size; i++) {
/* 204 */       os.writeObject(this.data[i]);
/*     */     }
/* 206 */     os.writeInt(this.maxPool);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
/* 210 */     is.defaultReadObject();
/*     */     
/* 212 */     this.size = is.readInt();
/* 213 */     this.initialCapacity = is.readInt();
/* 214 */     this.data = new Object[Integer.highestOneBit(this.size - 1) << 1];
/* 215 */     for (int i = 0; i < this.size; i++) {
/* 216 */       this.data[i] = is.readObject();
/*     */     }
/* 218 */     this.maxPool = is.readInt();
/* 219 */     this.iterPool = new Iterator[1];
/* 220 */     this.iterPool[0] = new Itr(); }
/*     */   
/*     */   public class Itr implements Iterator<E> { int index;
/*     */     
/*     */     public Itr() {}
/* 225 */     int lastRet = -1;
/* 226 */     int expectedModCount = UnsafeList.this.modCount;
/* 227 */     public boolean valid = true;
/*     */     
/*     */     public void reset() {
/* 230 */       this.index = 0;
/* 231 */       this.lastRet = -1;
/* 232 */       this.expectedModCount = UnsafeList.this.modCount;
/* 233 */       this.valid = true;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 237 */       this.valid = (this.index != UnsafeList.this.size);
/* 238 */       return this.valid;
/*     */     }
/*     */     
/*     */     public E next() {
/* 242 */       if (UnsafeList.this.modCount != this.expectedModCount) {
/* 243 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 246 */       int i = this.index;
/* 247 */       if (i >= UnsafeList.this.size) {
/* 248 */         throw new NoSuchElementException();
/*     */       }
/*     */       
/* 251 */       if (i >= UnsafeList.this.data.length) {
/* 252 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 255 */       this.index = (i + 1);
/* 256 */       return (E)UnsafeList.this.data[(this.lastRet = i)];
/*     */     }
/*     */     
/*     */     public void remove() {
/* 260 */       if (this.lastRet < 0) {
/* 261 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 264 */       if (UnsafeList.this.modCount != this.expectedModCount) {
/* 265 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       try
/*     */       {
/* 269 */         UnsafeList.this.remove(this.lastRet);
/* 270 */         this.index = this.lastRet;
/* 271 */         this.lastRet = -1;
/* 272 */         this.expectedModCount = UnsafeList.this.modCount;
/*     */       } catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {
/* 274 */         throw new ConcurrentModificationException();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\UnsafeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */