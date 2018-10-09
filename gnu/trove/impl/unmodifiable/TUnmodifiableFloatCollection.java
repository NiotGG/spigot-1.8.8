/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TFloatCollection;
/*     */ import gnu.trove.iterator.TFloatIterator;
/*     */ import gnu.trove.procedure.TFloatProcedure;
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
/*     */ public class TUnmodifiableFloatCollection
/*     */   implements TFloatCollection, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1820017752578914078L;
/*     */   final TFloatCollection c;
/*     */   
/*     */   public TUnmodifiableFloatCollection(TFloatCollection c)
/*     */   {
/*  58 */     if (c == null)
/*  59 */       throw new NullPointerException();
/*  60 */     this.c = c;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.c.size(); }
/*  64 */   public boolean isEmpty() { return this.c.isEmpty(); }
/*  65 */   public boolean contains(float o) { return this.c.contains(o); }
/*  66 */   public float[] toArray() { return this.c.toArray(); }
/*  67 */   public float[] toArray(float[] a) { return this.c.toArray(a); }
/*  68 */   public String toString() { return this.c.toString(); }
/*  69 */   public float getNoEntryValue() { return this.c.getNoEntryValue(); }
/*  70 */   public boolean forEach(TFloatProcedure procedure) { return this.c.forEach(procedure); }
/*     */   
/*     */   public TFloatIterator iterator() {
/*  73 */     new TFloatIterator() {
/*  74 */       TFloatIterator i = TUnmodifiableFloatCollection.this.c.iterator();
/*     */       
/*  76 */       public boolean hasNext() { return this.i.hasNext(); }
/*  77 */       public float next() { return this.i.next(); }
/*  78 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/*  82 */   public boolean add(float e) { throw new UnsupportedOperationException(); }
/*  83 */   public boolean remove(float o) { throw new UnsupportedOperationException(); }
/*     */   
/*  85 */   public boolean containsAll(Collection<?> coll) { return this.c.containsAll(coll); }
/*  86 */   public boolean containsAll(TFloatCollection coll) { return this.c.containsAll(coll); }
/*  87 */   public boolean containsAll(float[] array) { return this.c.containsAll(array); }
/*     */   
/*  89 */   public boolean addAll(TFloatCollection coll) { throw new UnsupportedOperationException(); }
/*  90 */   public boolean addAll(Collection<? extends Float> coll) { throw new UnsupportedOperationException(); }
/*  91 */   public boolean addAll(float[] array) { throw new UnsupportedOperationException(); }
/*     */   
/*  93 */   public boolean removeAll(Collection<?> coll) { throw new UnsupportedOperationException(); }
/*  94 */   public boolean removeAll(TFloatCollection coll) { throw new UnsupportedOperationException(); }
/*  95 */   public boolean removeAll(float[] array) { throw new UnsupportedOperationException(); }
/*     */   
/*  97 */   public boolean retainAll(Collection<?> coll) { throw new UnsupportedOperationException(); }
/*  98 */   public boolean retainAll(TFloatCollection coll) { throw new UnsupportedOperationException(); }
/*  99 */   public boolean retainAll(float[] array) { throw new UnsupportedOperationException(); }
/*     */   
/* 101 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableFloatCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */