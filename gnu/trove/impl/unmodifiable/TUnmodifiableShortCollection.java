/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TShortCollection;
/*     */ import gnu.trove.iterator.TShortIterator;
/*     */ import gnu.trove.procedure.TShortProcedure;
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
/*     */ public class TUnmodifiableShortCollection
/*     */   implements TShortCollection, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1820017752578914078L;
/*     */   final TShortCollection c;
/*     */   
/*     */   public TUnmodifiableShortCollection(TShortCollection c)
/*     */   {
/*  58 */     if (c == null)
/*  59 */       throw new NullPointerException();
/*  60 */     this.c = c;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.c.size(); }
/*  64 */   public boolean isEmpty() { return this.c.isEmpty(); }
/*  65 */   public boolean contains(short o) { return this.c.contains(o); }
/*  66 */   public short[] toArray() { return this.c.toArray(); }
/*  67 */   public short[] toArray(short[] a) { return this.c.toArray(a); }
/*  68 */   public String toString() { return this.c.toString(); }
/*  69 */   public short getNoEntryValue() { return this.c.getNoEntryValue(); }
/*  70 */   public boolean forEach(TShortProcedure procedure) { return this.c.forEach(procedure); }
/*     */   
/*     */   public TShortIterator iterator() {
/*  73 */     new TShortIterator() {
/*  74 */       TShortIterator i = TUnmodifiableShortCollection.this.c.iterator();
/*     */       
/*  76 */       public boolean hasNext() { return this.i.hasNext(); }
/*  77 */       public short next() { return this.i.next(); }
/*  78 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/*  82 */   public boolean add(short e) { throw new UnsupportedOperationException(); }
/*  83 */   public boolean remove(short o) { throw new UnsupportedOperationException(); }
/*     */   
/*  85 */   public boolean containsAll(Collection<?> coll) { return this.c.containsAll(coll); }
/*  86 */   public boolean containsAll(TShortCollection coll) { return this.c.containsAll(coll); }
/*  87 */   public boolean containsAll(short[] array) { return this.c.containsAll(array); }
/*     */   
/*  89 */   public boolean addAll(TShortCollection coll) { throw new UnsupportedOperationException(); }
/*  90 */   public boolean addAll(Collection<? extends Short> coll) { throw new UnsupportedOperationException(); }
/*  91 */   public boolean addAll(short[] array) { throw new UnsupportedOperationException(); }
/*     */   
/*  93 */   public boolean removeAll(Collection<?> coll) { throw new UnsupportedOperationException(); }
/*  94 */   public boolean removeAll(TShortCollection coll) { throw new UnsupportedOperationException(); }
/*  95 */   public boolean removeAll(short[] array) { throw new UnsupportedOperationException(); }
/*     */   
/*  97 */   public boolean retainAll(Collection<?> coll) { throw new UnsupportedOperationException(); }
/*  98 */   public boolean retainAll(TShortCollection coll) { throw new UnsupportedOperationException(); }
/*  99 */   public boolean retainAll(short[] array) { throw new UnsupportedOperationException(); }
/*     */   
/* 101 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableShortCollection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */