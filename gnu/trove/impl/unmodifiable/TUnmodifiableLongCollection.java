/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TLongCollection;
/*     */ import gnu.trove.iterator.TLongIterator;
/*     */ import gnu.trove.procedure.TLongProcedure;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
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
/*     */ public class TUnmodifiableLongCollection
/*     */   implements TLongCollection, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1820017752578914078L;
/*     */   final TLongCollection c;
/*     */   
/*     */   public TUnmodifiableLongCollection(TLongCollection c)
/*     */   {
/*  58 */     if (c == null)
/*  59 */       throw new NullPointerException();
/*  60 */     this.c = c;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.c.size(); }
/*  64 */   public boolean isEmpty() { return this.c.isEmpty(); }
/*  65 */   public boolean contains(long o) { return this.c.contains(o); }
/*  66 */   public long[] toArray() { return this.c.toArray(); }
/*  67 */   public long[] toArray(long[] a) { return this.c.toArray(a); }
/*  68 */   public String toString() { return this.c.toString(); }
/*  69 */   public long getNoEntryValue() { return this.c.getNoEntryValue(); }
/*  70 */   public boolean forEach(TLongProcedure procedure) { return this.c.forEach(procedure); }
/*     */   
/*     */   public TLongIterator iterator() {
/*  73 */     new TLongIterator() {
/*  74 */       TLongIterator i = TUnmodifiableLongCollection.this.c.iterator();
/*     */       
/*  76 */       public boolean hasNext() { return this.i.hasNext(); }
/*  77 */       public long next() { return this.i.next(); }
/*  78 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/*  82 */   public boolean add(long e) { throw new UnsupportedOperationException(); }
/*  83 */   public boolean remove(long o) { throw new UnsupportedOperationException(); }
/*     */   
/*  85 */   public boolean containsAll(Collection<?> coll) { return this.c.containsAll(coll); }
/*  86 */   public boolean containsAll(TLongCollection coll) { return this.c.containsAll(coll); }
/*  87 */   public boolean containsAll(long[] array) { return this.c.containsAll(array); }
/*     */   
/*  89 */   public boolean addAll(TLongCollection coll) { throw new UnsupportedOperationException(); }
/*  90 */   public boolean addAll(Collection<? extends Long> coll) { throw new UnsupportedOperationException(); }
/*  91 */   public boolean addAll(long[] array) { throw new UnsupportedOperationException(); }
/*     */   
/*  93 */   public boolean removeAll(Collection<?> coll) { throw new UnsupportedOperationException(); }
/*  94 */   public boolean removeAll(TLongCollection coll) { throw new UnsupportedOperationException(); }
/*  95 */   public boolean removeAll(long[] array) { throw new UnsupportedOperationException(); }
/*     */   
/*  97 */   public boolean retainAll(Collection<?> coll) { throw new UnsupportedOperationException(); }
/*  98 */   public boolean retainAll(TLongCollection coll) { throw new UnsupportedOperationException(); }
/*  99 */   public boolean retainAll(long[] array) { throw new UnsupportedOperationException(); }
/*     */   
/* 101 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableLongCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */