/*     */ package io.netty.handler.codec.serialization;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
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
/*     */ abstract class ReferenceMap<K, V>
/*     */   implements Map<K, V>
/*     */ {
/*     */   private final Map<K, Reference<V>> delegate;
/*     */   
/*     */   protected ReferenceMap(Map<K, Reference<V>> paramMap)
/*     */   {
/*  28 */     this.delegate = paramMap;
/*     */   }
/*     */   
/*     */   abstract Reference<V> fold(V paramV);
/*     */   
/*     */   private V unfold(Reference<V> paramReference) {
/*  34 */     if (paramReference == null) {
/*  35 */       return null;
/*     */     }
/*     */     
/*  38 */     return (V)paramReference.get();
/*     */   }
/*     */   
/*     */   public int size()
/*     */   {
/*  43 */     return this.delegate.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/*  48 */     return this.delegate.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object paramObject)
/*     */   {
/*  53 */     return this.delegate.containsKey(paramObject);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object paramObject)
/*     */   {
/*  58 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public V get(Object paramObject)
/*     */   {
/*  63 */     return (V)unfold((Reference)this.delegate.get(paramObject));
/*     */   }
/*     */   
/*     */   public V put(K paramK, V paramV)
/*     */   {
/*  68 */     return (V)unfold((Reference)this.delegate.put(paramK, fold(paramV)));
/*     */   }
/*     */   
/*     */   public V remove(Object paramObject)
/*     */   {
/*  73 */     return (V)unfold((Reference)this.delegate.remove(paramObject));
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*     */   {
/*  78 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/*  79 */       this.delegate.put(localEntry.getKey(), fold(localEntry.getValue()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/*  85 */     this.delegate.clear();
/*     */   }
/*     */   
/*     */   public Set<K> keySet()
/*     */   {
/*  90 */     return this.delegate.keySet();
/*     */   }
/*     */   
/*     */   public Collection<V> values()
/*     */   {
/*  95 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet()
/*     */   {
/* 100 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\ReferenceMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */