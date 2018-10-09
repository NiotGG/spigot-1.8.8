/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CyclicBuffer<T>
/*    */ {
/*    */   private final T[] ring;
/* 28 */   private int first = 0;
/* 29 */   private int last = 0;
/* 30 */   private int numElems = 0;
/*    */   
/*    */ 
/*    */   private final Class<T> clazz;
/*    */   
/*    */ 
/*    */ 
/*    */   public CyclicBuffer(Class<T> paramClass, int paramInt)
/*    */     throws IllegalArgumentException
/*    */   {
/* 40 */     if (paramInt < 1) {
/* 41 */       throw new IllegalArgumentException("The maxSize argument (" + paramInt + ") is not a positive integer.");
/*    */     }
/* 43 */     this.ring = makeArray(paramClass, paramInt);
/* 44 */     this.clazz = paramClass;
/*    */   }
/*    */   
/*    */   private T[] makeArray(Class<T> paramClass, int paramInt)
/*    */   {
/* 49 */     return (Object[])Array.newInstance(paramClass, paramInt);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public synchronized void add(T paramT)
/*    */   {
/* 57 */     this.ring[this.last] = paramT;
/* 58 */     if (++this.last == this.ring.length) {
/* 59 */       this.last = 0;
/*    */     }
/*    */     
/* 62 */     if (this.numElems < this.ring.length) {
/* 63 */       this.numElems += 1;
/* 64 */     } else if (++this.first == this.ring.length) {
/* 65 */       this.first = 0;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public synchronized T[] removeAll()
/*    */   {
/* 74 */     Object[] arrayOfObject = makeArray(this.clazz, this.numElems);
/* 75 */     int i = 0;
/* 76 */     while (this.numElems > 0) {
/* 77 */       this.numElems -= 1;
/* 78 */       arrayOfObject[(i++)] = this.ring[this.first];
/* 79 */       this.ring[this.first] = null;
/* 80 */       if (++this.first == this.ring.length) {
/* 81 */         this.first = 0;
/*    */       }
/*    */     }
/* 84 */     return arrayOfObject;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 92 */     return 0 == this.numElems;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\CyclicBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */