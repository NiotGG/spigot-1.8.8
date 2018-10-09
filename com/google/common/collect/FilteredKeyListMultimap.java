/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import com.google.common.base.Predicate;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
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
/*    */ final class FilteredKeyListMultimap<K, V>
/*    */   extends FilteredKeyMultimap<K, V>
/*    */   implements ListMultimap<K, V>
/*    */ {
/*    */   FilteredKeyListMultimap(ListMultimap<K, V> unfiltered, Predicate<? super K> keyPredicate)
/*    */   {
/* 35 */     super(unfiltered, keyPredicate);
/*    */   }
/*    */   
/*    */   public ListMultimap<K, V> unfiltered()
/*    */   {
/* 40 */     return (ListMultimap)super.unfiltered();
/*    */   }
/*    */   
/*    */   public List<V> get(K key)
/*    */   {
/* 45 */     return (List)super.get(key);
/*    */   }
/*    */   
/*    */   public List<V> removeAll(@Nullable Object key)
/*    */   {
/* 50 */     return (List)super.removeAll(key);
/*    */   }
/*    */   
/*    */   public List<V> replaceValues(K key, Iterable<? extends V> values)
/*    */   {
/* 55 */     return (List)super.replaceValues(key, values);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\FilteredKeyListMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */