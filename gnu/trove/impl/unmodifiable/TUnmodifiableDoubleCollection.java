/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TDoubleCollection;
/*     */ import gnu.trove.iterator.TDoubleIterator;
/*     */ import gnu.trove.procedure.TDoubleProcedure;
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
/*     */ public class TUnmodifiableDoubleCollection
/*     */   implements TDoubleCollection, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1820017752578914078L;
/*     */   final TDoubleCollection c;
/*     */   
/*     */   public TUnmodifiableDoubleCollection(TDoubleCollection c)
/*     */   {
/*  58 */     if (c == null)
/*  59 */       throw new NullPointerException();
/*  60 */     this.c = c;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.c.size(); }
/*  64 */   public boolean isEmpty() { return this.c.isEmpty(); }
/*  65 */   public boolean contains(double o) { return this.c.contains(o); }
/*  66 */   public double[] toArray() { return this.c.toArray(); }
/*  67 */   public double[] toArray(double[] a) { return this.c.toArray(a); }
/*  68 */   public String toString() { return this.c.toString(); }
/*  69 */   public double getNoEntryValue() { return this.c.getNoEntryValue(); }
/*  70 */   public boolean forEach(TDoubleProcedure procedure) { return this.c.forEach(procedure); }
/*     */   
/*     */   public TDoubleIterator iterator() {
/*  73 */     new TDoubleIterator() {
/*  74 */       TDoubleIterator i = TUnmodifiableDoubleCollection.this.c.iterator();
/*     */       
/*  76 */       public boolean hasNext() { return this.i.hasNext(); }
/*  77 */       public double next() { return this.i.next(); }
/*  78 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/*  82 */   public boolean add(double e) { throw new UnsupportedOperationException(); }
/*  83 */   public boolean remove(double o) { throw new UnsupportedOperationException(); }
/*     */   
/*  85 */   public boolean containsAll(Collection<?> coll) { return this.c.containsAll(coll); }
/*  86 */   public boolean containsAll(TDoubleCollection coll) { return this.c.containsAll(coll); }
/*  87 */   public boolean containsAll(double[] array) { return this.c.containsAll(array); }
/*     */   
/*  89 */   public boolean addAll(TDoubleCollection coll) { throw new UnsupportedOperationException(); }
/*  90 */   public boolean addAll(Collection<? extends Double> coll) { throw new UnsupportedOperationException(); }
/*  91 */   public boolean addAll(double[] array) { throw new UnsupportedOperationException(); }
/*     */   
/*  93 */   public boolean removeAll(Collection<?> coll) { throw new UnsupportedOperationException(); }
/*  94 */   public boolean removeAll(TDoubleCollection coll) { throw new UnsupportedOperationException(); }
/*  95 */   public boolean removeAll(double[] array) { throw new UnsupportedOperationException(); }
/*     */   
/*  97 */   public boolean retainAll(Collection<?> coll) { throw new UnsupportedOperationException(); }
/*  98 */   public boolean retainAll(TDoubleCollection coll) { throw new UnsupportedOperationException(); }
/*  99 */   public boolean retainAll(double[] array) { throw new UnsupportedOperationException(); }
/*     */   
/* 101 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableDoubleCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */