/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.annotations.GwtCompatible;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NavigableSet;
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
/*     */ @GwtCompatible(emulated=true)
/*     */ abstract class AbstractSortedMultiset<E>
/*     */   extends AbstractMultiset<E>
/*     */   implements SortedMultiset<E>
/*     */ {
/*     */   @GwtTransient
/*     */   final Comparator<? super E> comparator;
/*     */   private transient SortedMultiset<E> descendingMultiset;
/*     */   
/*     */   AbstractSortedMultiset()
/*     */   {
/*  43 */     this(Ordering.natural());
/*     */   }
/*     */   
/*     */   AbstractSortedMultiset(Comparator<? super E> comparator) {
/*  47 */     this.comparator = ((Comparator)Preconditions.checkNotNull(comparator));
/*     */   }
/*     */   
/*     */   public NavigableSet<E> elementSet()
/*     */   {
/*  52 */     return (NavigableSet)super.elementSet();
/*     */   }
/*     */   
/*     */   NavigableSet<E> createElementSet()
/*     */   {
/*  57 */     return new SortedMultisets.NavigableElementSet(this);
/*     */   }
/*     */   
/*     */   public Comparator<? super E> comparator()
/*     */   {
/*  62 */     return this.comparator;
/*     */   }
/*     */   
/*     */   public Multiset.Entry<E> firstEntry()
/*     */   {
/*  67 */     Iterator<Multiset.Entry<E>> entryIterator = entryIterator();
/*  68 */     return entryIterator.hasNext() ? (Multiset.Entry)entryIterator.next() : null;
/*     */   }
/*     */   
/*     */   public Multiset.Entry<E> lastEntry()
/*     */   {
/*  73 */     Iterator<Multiset.Entry<E>> entryIterator = descendingEntryIterator();
/*  74 */     return entryIterator.hasNext() ? (Multiset.Entry)entryIterator.next() : null;
/*     */   }
/*     */   
/*     */   public Multiset.Entry<E> pollFirstEntry()
/*     */   {
/*  79 */     Iterator<Multiset.Entry<E>> entryIterator = entryIterator();
/*  80 */     if (entryIterator.hasNext()) {
/*  81 */       Multiset.Entry<E> result = (Multiset.Entry)entryIterator.next();
/*  82 */       result = Multisets.immutableEntry(result.getElement(), result.getCount());
/*  83 */       entryIterator.remove();
/*  84 */       return result;
/*     */     }
/*  86 */     return null;
/*     */   }
/*     */   
/*     */   public Multiset.Entry<E> pollLastEntry()
/*     */   {
/*  91 */     Iterator<Multiset.Entry<E>> entryIterator = descendingEntryIterator();
/*  92 */     if (entryIterator.hasNext()) {
/*  93 */       Multiset.Entry<E> result = (Multiset.Entry)entryIterator.next();
/*  94 */       result = Multisets.immutableEntry(result.getElement(), result.getCount());
/*  95 */       entryIterator.remove();
/*  96 */       return result;
/*     */     }
/*  98 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public SortedMultiset<E> subMultiset(@Nullable E fromElement, BoundType fromBoundType, @Nullable E toElement, BoundType toBoundType)
/*     */   {
/* 105 */     Preconditions.checkNotNull(fromBoundType);
/* 106 */     Preconditions.checkNotNull(toBoundType);
/* 107 */     return tailMultiset(fromElement, fromBoundType).headMultiset(toElement, toBoundType);
/*     */   }
/*     */   
/*     */   abstract Iterator<Multiset.Entry<E>> descendingEntryIterator();
/*     */   
/*     */   Iterator<E> descendingIterator() {
/* 113 */     return Multisets.iteratorImpl(descendingMultiset());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public SortedMultiset<E> descendingMultiset()
/*     */   {
/* 120 */     SortedMultiset<E> result = this.descendingMultiset;
/* 121 */     return result == null ? (this.descendingMultiset = createDescendingMultiset()) : result;
/*     */   }
/*     */   
/*     */   SortedMultiset<E> createDescendingMultiset() {
/* 125 */     new DescendingMultiset()
/*     */     {
/*     */       SortedMultiset<E> forwardMultiset() {
/* 128 */         return AbstractSortedMultiset.this;
/*     */       }
/*     */       
/*     */       Iterator<Multiset.Entry<E>> entryIterator()
/*     */       {
/* 133 */         return AbstractSortedMultiset.this.descendingEntryIterator();
/*     */       }
/*     */       
/*     */       public Iterator<E> iterator()
/*     */       {
/* 138 */         return AbstractSortedMultiset.this.descendingIterator();
/*     */       }
/*     */     };
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\collect\AbstractSortedMultiset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */