/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.SortedSet;
/*     */ import javax.annotation.Nullable;
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
/*     */ @GwtCompatible
/*     */ abstract class AbstractSortedSetMultimap<K, V>
/*     */   extends AbstractSetMultimap<K, V>
/*     */   implements SortedSetMultimap<K, V>
/*     */ {
/*     */   private static final long serialVersionUID = 430848587173315748L;
/*     */   
/*     */   protected AbstractSortedSetMultimap(Map<K, Collection<V>> map)
/*     */   {
/*  47 */     super(map);
/*     */   }
/*     */   
/*     */ 
/*     */   abstract SortedSet<V> createCollection();
/*     */   
/*     */   SortedSet<V> createUnmodifiableEmptyCollection()
/*     */   {
/*  55 */     Comparator<? super V> comparator = valueComparator();
/*  56 */     if (comparator == null) {
/*  57 */       return Collections.unmodifiableSortedSet(createCollection());
/*     */     }
/*  59 */     return ImmutableSortedSet.emptySet(valueComparator());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SortedSet<V> get(@Nullable K key)
/*     */   {
/*  78 */     return (SortedSet)super.get(key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SortedSet<V> removeAll(@Nullable Object key)
/*     */   {
/*  90 */     return (SortedSet)super.removeAll(key);
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
/*     */ 
/*     */ 
/*     */   public SortedSet<V> replaceValues(@Nullable K key, Iterable<? extends V> values)
/*     */   {
/* 105 */     return (SortedSet)super.replaceValues(key, values);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<K, Collection<V>> asMap()
/*     */   {
/* 123 */     return super.asMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection<V> values()
/*     */   {
/* 133 */     return super.values();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractSortedSetMultimap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */