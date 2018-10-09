/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Predicate;
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
/*    */ @GwtCompatible
/*    */ final class FilteredEntrySetMultimap<K, V>
/*    */   extends FilteredEntryMultimap<K, V>
/*    */   implements FilteredSetMultimap<K, V>
/*    */ {
/*    */   FilteredEntrySetMultimap(SetMultimap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> predicate)
/*    */   {
/* 35 */     super(unfiltered, predicate);
/*    */   }
/*    */   
/*    */   public SetMultimap<K, V> unfiltered()
/*    */   {
/* 40 */     return (SetMultimap)this.unfiltered;
/*    */   }
/*    */   
/*    */   public Set<V> get(K key)
/*    */   {
/* 45 */     return (Set)super.get(key);
/*    */   }
/*    */   
/*    */   public Set<V> removeAll(Object key)
/*    */   {
/* 50 */     return (Set)super.removeAll(key);
/*    */   }
/*    */   
/*    */   public Set<V> replaceValues(K key, Iterable<? extends V> values)
/*    */   {
/* 55 */     return (Set)super.replaceValues(key, values);
/*    */   }
/*    */   
/*    */   Set<Map.Entry<K, V>> createEntries()
/*    */   {
/* 60 */     return Sets.filter(unfiltered().entries(), entryPredicate());
/*    */   }
/*    */   
/*    */   public Set<Map.Entry<K, V>> entries()
/*    */   {
/* 65 */     return (Set)super.entries();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\FilteredEntrySetMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */