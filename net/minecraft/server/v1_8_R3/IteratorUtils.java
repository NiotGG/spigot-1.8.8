/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class IteratorUtils
/*     */ {
/*     */   public static <T> Iterable<T[]> a(Class<T> paramClass, Iterable<? extends Iterable<? extends T>> paramIterable)
/*     */   {
/*  13 */     return new ClassIterable(paramClass, (Iterable[])b(Iterable.class, paramIterable), null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> Iterable<List<T>> a(Iterable<? extends Iterable<? extends T>> paramIterable)
/*     */   {
/*  21 */     return b(a(Object.class, paramIterable));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static <T> Iterable<List<T>> b(Iterable<Object[]> paramIterable)
/*     */   {
/*  29 */     return com.google.common.collect.Iterables.transform(paramIterable, new ArrayToList(null));
/*     */   }
/*     */   
/*     */   private static <T> T[] b(Class<? super T> paramClass, Iterable<? extends T> paramIterable) {
/*  33 */     ArrayList localArrayList = com.google.common.collect.Lists.newArrayList();
/*  34 */     for (Object localObject : paramIterable) {
/*  35 */       localArrayList.add(localObject);
/*     */     }
/*     */     
/*  38 */     return (Object[])localArrayList.toArray(b(paramClass, localArrayList.size()));
/*     */   }
/*     */   
/*     */   private static <T> T[] b(Class<? super T> paramClass, int paramInt)
/*     */   {
/*  43 */     return (Object[])Array.newInstance(paramClass, paramInt);
/*     */   }
/*     */   
/*     */   static class ArrayToList<T> implements com.google.common.base.Function<Object[], List<T>>
/*     */   {
/*     */     public List<T> a(Object[] paramArrayOfObject)
/*     */     {
/*  50 */       return Arrays.asList((Object[])paramArrayOfObject);
/*     */     }
/*     */   }
/*     */   
/*     */   static class ClassIterable<T> implements Iterable<T[]> {
/*     */     private final Class<T> a;
/*     */     private final Iterable<? extends T>[] b;
/*     */     
/*     */     private ClassIterable(Class<T> paramClass, Iterable<? extends T>[] paramArrayOfIterable) {
/*  59 */       this.a = paramClass;
/*  60 */       this.b = paramArrayOfIterable;
/*     */     }
/*     */     
/*     */     public Iterator<T[]> iterator()
/*     */     {
/*  65 */       if (this.b.length <= 0)
/*     */       {
/*  67 */         return java.util.Collections.singletonList((Object[])IteratorUtils.a(this.a, 0)).iterator();
/*     */       }
/*  69 */       return new ClassIterator(this.a, this.b, null);
/*     */     }
/*     */     
/*     */ 
/*     */     static class ClassIterator<T>
/*     */       extends com.google.common.collect.UnmodifiableIterator<T[]>
/*     */     {
/*  76 */       private int a = -2;
/*     */       private final Iterable<? extends T>[] b;
/*     */       private final Iterator<? extends T>[] c;
/*     */       private final T[] d;
/*     */       
/*     */       private ClassIterator(Class<T> paramClass, Iterable<? extends T>[] paramArrayOfIterable)
/*     */       {
/*  83 */         this.b = paramArrayOfIterable;
/*  84 */         this.c = ((Iterator[])IteratorUtils.a(Iterator.class, this.b.length));
/*  85 */         for (int i = 0; i < this.b.length; i++) {
/*  86 */           this.c[i] = paramArrayOfIterable[i].iterator();
/*     */         }
/*  88 */         this.d = IteratorUtils.a(paramClass, this.c.length);
/*     */       }
/*     */       
/*     */       private void b() {
/*  92 */         this.a = -1;
/*     */         
/*  94 */         Arrays.fill(this.c, null);
/*  95 */         Arrays.fill(this.d, null);
/*     */       }
/*     */       
/*     */       public boolean hasNext()
/*     */       {
/* 100 */         if (this.a == -2) {
/* 101 */           this.a = 0;
/* 102 */           for (Object localObject2 : this.c) {
/* 103 */             if (!((Iterator)localObject2).hasNext()) {
/* 104 */               b();
/* 105 */               break;
/*     */             }
/*     */           }
/* 108 */           return true;
/*     */         }
/*     */         
/* 111 */         if (this.a >= this.c.length) {
/* 112 */           for (this.a = (this.c.length - 1); this.a >= 0; this.a -= 1)
/*     */           {
/* 114 */             ??? = this.c[this.a];
/* 115 */             if (((Iterator)???).hasNext()) {
/*     */               break;
/*     */             }
/*     */             
/*     */ 
/* 120 */             if (this.a == 0) {
/* 121 */               b();
/* 122 */               break;
/*     */             }
/*     */             
/*     */ 
/* 126 */             ??? = this.b[this.a].iterator();
/* 127 */             this.c[this.a] = ???;
/*     */             
/*     */ 
/* 130 */             if (!((Iterator)???).hasNext()) {
/* 131 */               b();
/* 132 */               break;
/*     */             }
/*     */           }
/*     */         }
/* 136 */         return this.a >= 0;
/*     */       }
/*     */       
/*     */       public T[] a()
/*     */       {
/* 141 */         if (!hasNext()) {
/* 142 */           throw new java.util.NoSuchElementException();
/*     */         }
/* 145 */         for (; 
/* 145 */             this.a < this.c.length; this.a += 1) {
/* 146 */           this.d[this.a] = this.c[this.a].next();
/*     */         }
/* 148 */         return (Object[])this.d.clone();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\IteratorUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */