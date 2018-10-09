/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
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
/*     */ public class DefaultAttributeMap
/*     */   implements AttributeMap
/*     */ {
/*     */   private static final AtomicReferenceFieldUpdater<DefaultAttributeMap, AtomicReferenceArray> updater;
/*     */   private static final int BUCKET_SIZE = 4;
/*     */   private static final int MASK = 3;
/*     */   private volatile AtomicReferenceArray<DefaultAttribute<?>> attributes;
/*     */   
/*     */   static
/*     */   {
/*  35 */     AtomicReferenceFieldUpdater localAtomicReferenceFieldUpdater = PlatformDependent.newAtomicReferenceFieldUpdater(DefaultAttributeMap.class, "attributes");
/*     */     
/*  37 */     if (localAtomicReferenceFieldUpdater == null) {
/*  38 */       localAtomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(DefaultAttributeMap.class, AtomicReferenceArray.class, "attributes");
/*     */     }
/*     */     
/*  41 */     updater = localAtomicReferenceFieldUpdater;
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
/*     */   public <T> Attribute<T> attr(AttributeKey<T> paramAttributeKey)
/*     */   {
/*  54 */     if (paramAttributeKey == null) {
/*  55 */       throw new NullPointerException("key");
/*     */     }
/*  57 */     AtomicReferenceArray localAtomicReferenceArray = this.attributes;
/*  58 */     if (localAtomicReferenceArray == null)
/*     */     {
/*  60 */       localAtomicReferenceArray = new AtomicReferenceArray(4);
/*     */       
/*  62 */       if (!updater.compareAndSet(this, null, localAtomicReferenceArray)) {
/*  63 */         localAtomicReferenceArray = this.attributes;
/*     */       }
/*     */     }
/*     */     
/*  67 */     int i = index(paramAttributeKey);
/*  68 */     DefaultAttribute localDefaultAttribute1 = (DefaultAttribute)localAtomicReferenceArray.get(i);
/*  69 */     if (localDefaultAttribute1 == null)
/*     */     {
/*     */ 
/*  72 */       localDefaultAttribute1 = new DefaultAttribute(paramAttributeKey);
/*  73 */       if (localAtomicReferenceArray.compareAndSet(i, null, localDefaultAttribute1))
/*     */       {
/*  75 */         return localDefaultAttribute1;
/*     */       }
/*  77 */       localDefaultAttribute1 = (DefaultAttribute)localAtomicReferenceArray.get(i);
/*     */     }
/*     */     
/*     */ 
/*  81 */     synchronized (localDefaultAttribute1) {
/*  82 */       Object localObject1 = localDefaultAttribute1;
/*     */       
/*  84 */       if ((!((DefaultAttribute)localObject1).removed) && (((DefaultAttribute)localObject1).key == paramAttributeKey)) {
/*  85 */         return (Attribute<T>)localObject1;
/*     */       }
/*     */       
/*  88 */       DefaultAttribute localDefaultAttribute2 = ((DefaultAttribute)localObject1).next;
/*  89 */       if (localDefaultAttribute2 == null) {
/*  90 */         DefaultAttribute localDefaultAttribute3 = new DefaultAttribute(localDefaultAttribute1, paramAttributeKey);
/*  91 */         ((DefaultAttribute)localObject1).next = localDefaultAttribute3;
/*  92 */         localDefaultAttribute3.prev = ((DefaultAttribute)localObject1);
/*  93 */         return localDefaultAttribute3;
/*     */       }
/*  95 */       localObject1 = localDefaultAttribute2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static int index(AttributeKey<?> paramAttributeKey)
/*     */   {
/* 102 */     return paramAttributeKey.id() & 0x3;
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class DefaultAttribute<T>
/*     */     extends AtomicReference<T>
/*     */     implements Attribute<T>
/*     */   {
/*     */     private static final long serialVersionUID = -2661411462200283011L;
/*     */     
/*     */     private final DefaultAttribute<?> head;
/*     */     
/*     */     private final AttributeKey<T> key;
/*     */     
/*     */     private DefaultAttribute<?> prev;
/*     */     private DefaultAttribute<?> next;
/*     */     private volatile boolean removed;
/*     */     
/*     */     DefaultAttribute(DefaultAttribute<?> paramDefaultAttribute, AttributeKey<T> paramAttributeKey)
/*     */     {
/* 122 */       this.head = paramDefaultAttribute;
/* 123 */       this.key = paramAttributeKey;
/*     */     }
/*     */     
/*     */     DefaultAttribute(AttributeKey<T> paramAttributeKey) {
/* 127 */       this.head = this;
/* 128 */       this.key = paramAttributeKey;
/*     */     }
/*     */     
/*     */     public AttributeKey<T> key()
/*     */     {
/* 133 */       return this.key;
/*     */     }
/*     */     
/*     */     public T setIfAbsent(T paramT)
/*     */     {
/* 138 */       while (!compareAndSet(null, paramT)) {
/* 139 */         Object localObject = get();
/* 140 */         if (localObject != null) {
/* 141 */           return (T)localObject;
/*     */         }
/*     */       }
/* 144 */       return null;
/*     */     }
/*     */     
/*     */     public T getAndRemove()
/*     */     {
/* 149 */       this.removed = true;
/* 150 */       Object localObject = getAndSet(null);
/* 151 */       remove0();
/* 152 */       return (T)localObject;
/*     */     }
/*     */     
/*     */     public void remove()
/*     */     {
/* 157 */       this.removed = true;
/* 158 */       set(null);
/* 159 */       remove0();
/*     */     }
/*     */     
/*     */     private void remove0() {
/* 163 */       synchronized (this.head)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 169 */         if (this.prev != null) {
/* 170 */           this.prev.next = this.next;
/*     */           
/* 172 */           if (this.next != null) {
/* 173 */             this.next.prev = this.prev;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\DefaultAttributeMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */