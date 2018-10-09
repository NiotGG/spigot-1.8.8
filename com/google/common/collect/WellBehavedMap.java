/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @GwtCompatible
/*    */ final class WellBehavedMap<K, V>
/*    */   extends ForwardingMap<K, V>
/*    */ {
/*    */   private final Map<K, V> delegate;
/*    */   private Set<Map.Entry<K, V>> entrySet;
/*    */   
/*    */   private WellBehavedMap(Map<K, V> delegate)
/*    */   {
/* 42 */     this.delegate = delegate;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   static <K, V> WellBehavedMap<K, V> wrap(Map<K, V> delegate)
/*    */   {
/* 52 */     return new WellBehavedMap(delegate);
/*    */   }
/*    */   
/*    */   protected Map<K, V> delegate() {
/* 56 */     return this.delegate;
/*    */   }
/*    */   
/*    */   public Set<Map.Entry<K, V>> entrySet() {
/* 60 */     Set<Map.Entry<K, V>> es = this.entrySet;
/* 61 */     if (es != null) {
/* 62 */       return es;
/*    */     }
/* 64 */     return this.entrySet = new EntrySet(null);
/*    */   }
/*    */   
/*    */   private final class EntrySet extends Maps.EntrySet<K, V> {
/*    */     private EntrySet() {}
/*    */     
/* 70 */     Map<K, V> map() { return WellBehavedMap.this; }
/*    */     
/*    */ 
/*    */     public Iterator<Map.Entry<K, V>> iterator()
/*    */     {
/* 75 */       new TransformedIterator(WellBehavedMap.this.keySet().iterator())
/*    */       {
/*    */         Map.Entry<K, V> transform(final K key) {
/* 78 */           new AbstractMapEntry()
/*    */           {
/*    */             public K getKey() {
/* 81 */               return (K)key;
/*    */             }
/*    */             
/*    */             public V getValue()
/*    */             {
/* 86 */               return (V)WellBehavedMap.this.get(key);
/*    */             }
/*    */             
/*    */             public V setValue(V value)
/*    */             {
/* 91 */               return (V)WellBehavedMap.this.put(key, value);
/*    */             }
/*    */           };
/*    */         }
/*    */       };
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\WellBehavedMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */