/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class EntitySlice<T> extends AbstractSet<T>
/*     */ {
/*  15 */   private static final Set<Class<?>> a = ;
/*  16 */   private final Map<Class<?>, List<T>> b = Maps.newHashMap();
/*  17 */   private final Set<Class<?>> c = Sets.newIdentityHashSet();
/*     */   private final Class<T> d;
/*  19 */   private final List<T> e = Lists.newArrayList();
/*     */   
/*     */   public EntitySlice(Class<T> oclass) {
/*  22 */     this.d = oclass;
/*  23 */     this.c.add(oclass);
/*  24 */     this.b.put(oclass, this.e);
/*  25 */     Iterator iterator = a.iterator();
/*     */     
/*  27 */     while (iterator.hasNext()) {
/*  28 */       Class oclass1 = (Class)iterator.next();
/*     */       
/*  30 */       a(oclass1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(Class<?> oclass)
/*     */   {
/*  36 */     a.add(oclass);
/*  37 */     Iterator iterator = this.e.iterator();
/*     */     
/*  39 */     while (iterator.hasNext()) {
/*  40 */       Object object = iterator.next();
/*     */       
/*  42 */       if (oclass.isAssignableFrom(object.getClass())) {
/*  43 */         a(object, oclass);
/*     */       }
/*     */     }
/*     */     
/*  47 */     this.c.add(oclass);
/*     */   }
/*     */   
/*     */   protected Class<?> b(Class<?> oclass) {
/*  51 */     if (this.d.isAssignableFrom(oclass)) {
/*  52 */       if (!this.c.contains(oclass)) {
/*  53 */         a(oclass);
/*     */       }
/*     */       
/*  56 */       return oclass;
/*     */     }
/*  58 */     throw new IllegalArgumentException("Don't know how to search for " + oclass);
/*     */   }
/*     */   
/*     */   public boolean add(T t0)
/*     */   {
/*  63 */     Iterator iterator = this.c.iterator();
/*     */     
/*  65 */     while (iterator.hasNext()) {
/*  66 */       Class oclass = (Class)iterator.next();
/*     */       
/*  68 */       if (oclass.isAssignableFrom(t0.getClass())) {
/*  69 */         a(t0, oclass);
/*     */       }
/*     */     }
/*     */     
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   private void a(T t0, Class<?> oclass) {
/*  77 */     List list = (List)this.b.get(oclass);
/*     */     
/*  79 */     if (list == null) {
/*  80 */       this.b.put(oclass, Lists.newArrayList(new Object[] { t0 }));
/*     */     } else {
/*  82 */       list.add(t0);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean remove(Object object)
/*     */   {
/*  88 */     Object object1 = object;
/*  89 */     boolean flag = false;
/*  90 */     Iterator iterator = this.c.iterator();
/*     */     
/*  92 */     while (iterator.hasNext()) {
/*  93 */       Class oclass = (Class)iterator.next();
/*     */       
/*  95 */       if (oclass.isAssignableFrom(object1.getClass())) {
/*  96 */         List list = (List)this.b.get(oclass);
/*     */         
/*  98 */         if ((list != null) && (list.remove(object1))) {
/*  99 */           flag = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 104 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean contains(Object object) {
/* 108 */     return Iterators.contains(c(object.getClass()).iterator(), object);
/*     */   }
/*     */   
/*     */   public <S> Iterable<S> c(final Class<S> oclass) {
/* 112 */     new Iterable() {
/*     */       public Iterator<S> iterator() {
/* 114 */         List list = (List)EntitySlice.this.b.get(EntitySlice.this.b(oclass));
/*     */         
/* 116 */         if (list == null) {
/* 117 */           return Iterators.emptyIterator();
/*     */         }
/* 119 */         Iterator iterator = list.iterator();
/*     */         
/* 121 */         return Iterators.filter(iterator, oclass);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public Iterator<T> iterator()
/*     */   {
/* 128 */     return this.e.isEmpty() ? Iterators.emptyIterator() : Iterators.unmodifiableIterator(this.e.iterator());
/*     */   }
/*     */   
/*     */   public int size() {
/* 132 */     return this.e.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySlice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */