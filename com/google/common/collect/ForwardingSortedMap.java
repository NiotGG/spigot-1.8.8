/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Comparator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.SortedMap;
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
/*     */ public abstract class ForwardingSortedMap<K, V>
/*     */   extends ForwardingMap<K, V>
/*     */   implements SortedMap<K, V>
/*     */ {
/*     */   protected abstract SortedMap<K, V> delegate();
/*     */   
/*     */   public Comparator<? super K> comparator()
/*     */   {
/*  67 */     return delegate().comparator();
/*     */   }
/*     */   
/*     */   public K firstKey()
/*     */   {
/*  72 */     return (K)delegate().firstKey();
/*     */   }
/*     */   
/*     */   public SortedMap<K, V> headMap(K toKey)
/*     */   {
/*  77 */     return delegate().headMap(toKey);
/*     */   }
/*     */   
/*     */   public K lastKey()
/*     */   {
/*  82 */     return (K)delegate().lastKey();
/*     */   }
/*     */   
/*     */   public SortedMap<K, V> subMap(K fromKey, K toKey)
/*     */   {
/*  87 */     return delegate().subMap(fromKey, toKey);
/*     */   }
/*     */   
/*     */   public SortedMap<K, V> tailMap(K fromKey)
/*     */   {
/*  92 */     return delegate().tailMap(fromKey);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Beta
/*     */   protected class StandardKeySet
/*     */     extends Maps.SortedKeySet<K, V>
/*     */   {
/*     */     public StandardKeySet()
/*     */     {
/* 106 */       super();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private int unsafeCompare(Object k1, Object k2)
/*     */   {
/* 113 */     Comparator<? super K> comparator = comparator();
/* 114 */     if (comparator == null) {
/* 115 */       return ((Comparable)k1).compareTo(k2);
/*     */     }
/* 117 */     return comparator.compare(k1, k2);
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
/*     */   @Beta
/*     */   protected boolean standardContainsKey(@Nullable Object key)
/*     */   {
/*     */     try
/*     */     {
/* 133 */       SortedMap<Object, V> self = this;
/* 134 */       Object ceilingKey = self.tailMap(key).firstKey();
/* 135 */       return unsafeCompare(ceilingKey, key) == 0;
/*     */     } catch (ClassCastException e) {
/* 137 */       return false;
/*     */     } catch (NoSuchElementException e) {
/* 139 */       return false;
/*     */     } catch (NullPointerException e) {}
/* 141 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Beta
/*     */   protected SortedMap<K, V> standardSubMap(K fromKey, K toKey)
/*     */   {
/* 154 */     Preconditions.checkArgument(unsafeCompare(fromKey, toKey) <= 0, "fromKey must be <= toKey");
/* 155 */     return tailMap(fromKey).headMap(toKey);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\ForwardingSortedMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */