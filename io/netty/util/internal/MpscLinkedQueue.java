/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Queue;
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
/*     */ final class MpscLinkedQueue<E>
/*     */   extends MpscLinkedQueueTailRef<E>
/*     */   implements Queue<E>
/*     */ {
/*     */   private static final long serialVersionUID = -1878402552271506449L;
/*     */   long p00;
/*     */   long p01;
/*     */   long p02;
/*     */   long p03;
/*     */   long p04;
/*     */   long p05;
/*     */   long p06;
/*     */   long p07;
/*     */   long p30;
/*     */   long p31;
/*     */   long p32;
/*     */   long p33;
/*     */   long p34;
/*     */   long p35;
/*     */   long p36;
/*     */   long p37;
/*     */   
/*     */   MpscLinkedQueue()
/*     */   {
/*  91 */     DefaultNode localDefaultNode = new DefaultNode(null);
/*  92 */     setHeadRef(localDefaultNode);
/*  93 */     setTailRef(localDefaultNode);
/*     */   }
/*     */   
/*     */ 
/*     */   private MpscLinkedQueueNode<E> peekNode()
/*     */   {
/*     */     for (;;)
/*     */     {
/* 101 */       MpscLinkedQueueNode localMpscLinkedQueueNode1 = headRef();
/* 102 */       MpscLinkedQueueNode localMpscLinkedQueueNode2 = localMpscLinkedQueueNode1.next();
/* 103 */       if (localMpscLinkedQueueNode2 != null) {
/* 104 */         return localMpscLinkedQueueNode2;
/*     */       }
/* 106 */       if (localMpscLinkedQueueNode1 == tailRef()) {
/* 107 */         return null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean offer(E paramE)
/*     */   {
/* 120 */     if (paramE == null) {
/* 121 */       throw new NullPointerException("value");
/*     */     }
/*     */     
/*     */     Object localObject;
/* 125 */     if ((paramE instanceof MpscLinkedQueueNode)) {
/* 126 */       localObject = (MpscLinkedQueueNode)paramE;
/* 127 */       ((MpscLinkedQueueNode)localObject).setNext(null);
/*     */     } else {
/* 129 */       localObject = new DefaultNode(paramE);
/*     */     }
/*     */     
/* 132 */     MpscLinkedQueueNode localMpscLinkedQueueNode = getAndSetTailRef((MpscLinkedQueueNode)localObject);
/* 133 */     localMpscLinkedQueueNode.setNext((MpscLinkedQueueNode)localObject);
/* 134 */     return true;
/*     */   }
/*     */   
/*     */   public E poll()
/*     */   {
/* 139 */     MpscLinkedQueueNode localMpscLinkedQueueNode1 = peekNode();
/* 140 */     if (localMpscLinkedQueueNode1 == null) {
/* 141 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 145 */     MpscLinkedQueueNode localMpscLinkedQueueNode2 = headRef();
/*     */     
/*     */ 
/*     */ 
/* 149 */     lazySetHeadRef(localMpscLinkedQueueNode1);
/*     */     
/*     */ 
/* 152 */     localMpscLinkedQueueNode2.unlink();
/*     */     
/* 154 */     return (E)localMpscLinkedQueueNode1.clearMaybe();
/*     */   }
/*     */   
/*     */   public E peek()
/*     */   {
/* 159 */     MpscLinkedQueueNode localMpscLinkedQueueNode = peekNode();
/* 160 */     if (localMpscLinkedQueueNode == null) {
/* 161 */       return null;
/*     */     }
/* 163 */     return (E)localMpscLinkedQueueNode.value();
/*     */   }
/*     */   
/*     */   public int size()
/*     */   {
/* 168 */     int i = 0;
/* 169 */     MpscLinkedQueueNode localMpscLinkedQueueNode = peekNode();
/*     */     
/* 171 */     while (localMpscLinkedQueueNode != null)
/*     */     {
/*     */ 
/* 174 */       i++;
/* 175 */       localMpscLinkedQueueNode = localMpscLinkedQueueNode.next();
/*     */     }
/* 177 */     return i;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 182 */     return peekNode() == null;
/*     */   }
/*     */   
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 187 */     MpscLinkedQueueNode localMpscLinkedQueueNode = peekNode();
/*     */     
/* 189 */     while (localMpscLinkedQueueNode != null)
/*     */     {
/*     */ 
/* 192 */       if (localMpscLinkedQueueNode.value() == paramObject) {
/* 193 */         return true;
/*     */       }
/* 195 */       localMpscLinkedQueueNode = localMpscLinkedQueueNode.next();
/*     */     }
/* 197 */     return false;
/*     */   }
/*     */   
/*     */   public Iterator<E> iterator()
/*     */   {
/* 202 */     new Iterator() {
/* 203 */       private MpscLinkedQueueNode<E> node = MpscLinkedQueue.this.peekNode();
/*     */       
/*     */       public boolean hasNext()
/*     */       {
/* 207 */         return this.node != null;
/*     */       }
/*     */       
/*     */       public E next()
/*     */       {
/* 212 */         MpscLinkedQueueNode localMpscLinkedQueueNode = this.node;
/* 213 */         if (localMpscLinkedQueueNode == null) {
/* 214 */           throw new NoSuchElementException();
/*     */         }
/* 216 */         Object localObject = localMpscLinkedQueueNode.value();
/* 217 */         this.node = localMpscLinkedQueueNode.next();
/* 218 */         return (E)localObject;
/*     */       }
/*     */       
/*     */       public void remove()
/*     */       {
/* 223 */         throw new UnsupportedOperationException();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public boolean add(E paramE)
/*     */   {
/* 230 */     if (offer(paramE)) {
/* 231 */       return true;
/*     */     }
/* 233 */     throw new IllegalStateException("queue full");
/*     */   }
/*     */   
/*     */   public E remove()
/*     */   {
/* 238 */     Object localObject = poll();
/* 239 */     if (localObject != null) {
/* 240 */       return (E)localObject;
/*     */     }
/* 242 */     throw new NoSuchElementException();
/*     */   }
/*     */   
/*     */   public E element()
/*     */   {
/* 247 */     Object localObject = peek();
/* 248 */     if (localObject != null) {
/* 249 */       return (E)localObject;
/*     */     }
/* 251 */     throw new NoSuchElementException();
/*     */   }
/*     */   
/*     */   public Object[] toArray()
/*     */   {
/* 256 */     Object[] arrayOfObject = new Object[size()];
/* 257 */     Iterator localIterator = iterator();
/* 258 */     for (int i = 0; i < arrayOfObject.length; i++) {
/* 259 */       if (localIterator.hasNext()) {
/* 260 */         arrayOfObject[i] = localIterator.next();
/*     */       } else {
/* 262 */         return Arrays.copyOf(arrayOfObject, i);
/*     */       }
/*     */     }
/* 265 */     return arrayOfObject;
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 271 */     int i = size();
/*     */     Object localObject;
/* 273 */     if (paramArrayOfT.length >= i) {
/* 274 */       localObject = paramArrayOfT;
/*     */     } else {
/* 276 */       localObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
/*     */     }
/*     */     
/* 279 */     Iterator localIterator = iterator();
/* 280 */     for (int j = 0; j < localObject.length; j++) {
/* 281 */       if (localIterator.hasNext()) {
/* 282 */         localObject[j] = localIterator.next();
/*     */       } else {
/* 284 */         if (paramArrayOfT == localObject) {
/* 285 */           localObject[j] = null;
/* 286 */           return (T[])localObject;
/*     */         }
/*     */         
/* 289 */         if (paramArrayOfT.length < j) {
/* 290 */           return Arrays.copyOf((Object[])localObject, j);
/*     */         }
/*     */         
/* 293 */         System.arraycopy(localObject, 0, paramArrayOfT, 0, j);
/* 294 */         if (paramArrayOfT.length > j) {
/* 295 */           paramArrayOfT[j] = null;
/*     */         }
/* 297 */         return paramArrayOfT;
/*     */       }
/*     */     }
/* 300 */     return (T[])localObject;
/*     */   }
/*     */   
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 305 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 310 */     for (Object localObject : paramCollection) {
/* 311 */       if (!contains(localObject)) {
/* 312 */         return false;
/*     */       }
/*     */     }
/* 315 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<? extends E> paramCollection)
/*     */   {
/* 320 */     if (paramCollection == null) {
/* 321 */       throw new NullPointerException("c");
/*     */     }
/* 323 */     if (paramCollection == this) {
/* 324 */       throw new IllegalArgumentException("c == this");
/*     */     }
/*     */     
/* 327 */     boolean bool = false;
/* 328 */     for (Object localObject : paramCollection) {
/* 329 */       add(localObject);
/* 330 */       bool = true;
/*     */     }
/* 332 */     return bool;
/*     */   }
/*     */   
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 337 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 342 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/* 347 */     while (poll() != null) {}
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 353 */     paramObjectOutputStream.defaultWriteObject();
/* 354 */     for (Object localObject : this) {
/* 355 */       paramObjectOutputStream.writeObject(localObject);
/*     */     }
/* 357 */     paramObjectOutputStream.writeObject(null);
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 361 */     paramObjectInputStream.defaultReadObject();
/*     */     
/* 363 */     DefaultNode localDefaultNode = new DefaultNode(null);
/* 364 */     setHeadRef(localDefaultNode);
/* 365 */     setTailRef(localDefaultNode);
/*     */     
/*     */     for (;;)
/*     */     {
/* 369 */       Object localObject = paramObjectInputStream.readObject();
/* 370 */       if (localObject == null) {
/*     */         break;
/*     */       }
/* 373 */       add(localObject);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DefaultNode<T> extends MpscLinkedQueueNode<T>
/*     */   {
/*     */     private T value;
/*     */     
/*     */     DefaultNode(T paramT) {
/* 382 */       this.value = paramT;
/*     */     }
/*     */     
/*     */     public T value()
/*     */     {
/* 387 */       return (T)this.value;
/*     */     }
/*     */     
/*     */     protected T clearMaybe()
/*     */     {
/* 392 */       Object localObject = this.value;
/* 393 */       this.value = null;
/* 394 */       return (T)localObject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\MpscLinkedQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */