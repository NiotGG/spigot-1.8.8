/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.annotations.GwtCompatible;
/*    */ import java.util.Collection;
/*    */ import java.util.SortedMap;
/*    */ import java.util.SortedSet;
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
/*    */ abstract class AbstractSortedKeySortedSetMultimap<K, V>
/*    */   extends AbstractSortedSetMultimap<K, V>
/*    */ {
/*    */   AbstractSortedKeySortedSetMultimap(SortedMap<K, Collection<V>> map)
/*    */   {
/* 38 */     super(map);
/*    */   }
/*    */   
/*    */   public SortedMap<K, Collection<V>> asMap()
/*    */   {
/* 43 */     return (SortedMap)super.asMap();
/*    */   }
/*    */   
/*    */   SortedMap<K, Collection<V>> backingMap()
/*    */   {
/* 48 */     return (SortedMap)super.backingMap();
/*    */   }
/*    */   
/*    */   public SortedSet<K> keySet()
/*    */   {
/* 53 */     return (SortedSet)super.keySet();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractSortedKeySortedSetMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */