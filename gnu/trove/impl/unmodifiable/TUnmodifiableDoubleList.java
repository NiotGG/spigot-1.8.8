/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.function.TDoubleFunction;
/*     */ import gnu.trove.list.TDoubleList;
/*     */ import gnu.trove.procedure.TDoubleProcedure;
/*     */ import java.util.Random;
/*     */ import java.util.RandomAccess;
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
/*     */ public class TUnmodifiableDoubleList
/*     */   extends TUnmodifiableDoubleCollection
/*     */   implements TDoubleList
/*     */ {
/*     */   static final long serialVersionUID = -283967356065247728L;
/*     */   final TDoubleList list;
/*     */   
/*     */   public TUnmodifiableDoubleList(TDoubleList list)
/*     */   {
/*  58 */     super(list);
/*  59 */     this.list = list;
/*     */   }
/*     */   
/*  62 */   public boolean equals(Object o) { return (o == this) || (this.list.equals(o)); }
/*  63 */   public int hashCode() { return this.list.hashCode(); }
/*     */   
/*  65 */   public double get(int index) { return this.list.get(index); }
/*  66 */   public int indexOf(double o) { return this.list.indexOf(o); }
/*  67 */   public int lastIndexOf(double o) { return this.list.lastIndexOf(o); }
/*     */   
/*     */   public double[] toArray(int offset, int len) {
/*  70 */     return this.list.toArray(offset, len);
/*     */   }
/*     */   
/*  73 */   public double[] toArray(double[] dest, int offset, int len) { return this.list.toArray(dest, offset, len); }
/*     */   
/*     */   public double[] toArray(double[] dest, int source_pos, int dest_pos, int len) {
/*  76 */     return this.list.toArray(dest, source_pos, dest_pos, len);
/*     */   }
/*     */   
/*     */   public boolean forEachDescending(TDoubleProcedure procedure) {
/*  80 */     return this.list.forEachDescending(procedure);
/*     */   }
/*     */   
/*  83 */   public int binarySearch(double value) { return this.list.binarySearch(value); }
/*     */   
/*  85 */   public int binarySearch(double value, int fromIndex, int toIndex) { return this.list.binarySearch(value, fromIndex, toIndex); }
/*     */   
/*     */ 
/*  88 */   public int indexOf(int offset, double value) { return this.list.indexOf(offset, value); }
/*  89 */   public int lastIndexOf(int offset, double value) { return this.list.lastIndexOf(offset, value); }
/*  90 */   public TDoubleList grep(TDoubleProcedure condition) { return this.list.grep(condition); }
/*  91 */   public TDoubleList inverseGrep(TDoubleProcedure condition) { return this.list.inverseGrep(condition); }
/*     */   
/*  93 */   public double max() { return this.list.max(); }
/*  94 */   public double min() { return this.list.min(); }
/*  95 */   public double sum() { return this.list.sum(); }
/*     */   
/*     */   public TDoubleList subList(int fromIndex, int toIndex) {
/*  98 */     return new TUnmodifiableDoubleList(this.list.subList(fromIndex, toIndex));
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
/*     */   private Object readResolve()
/*     */   {
/* 140 */     return (this.list instanceof RandomAccess) ? new TUnmodifiableRandomAccessDoubleList(this.list) : this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 145 */   public void add(double[] vals) { throw new UnsupportedOperationException(); }
/* 146 */   public void add(double[] vals, int offset, int length) { throw new UnsupportedOperationException(); }
/*     */   
/* 148 */   public double removeAt(int offset) { throw new UnsupportedOperationException(); }
/* 149 */   public void remove(int offset, int length) { throw new UnsupportedOperationException(); }
/*     */   
/* 151 */   public void insert(int offset, double value) { throw new UnsupportedOperationException(); }
/* 152 */   public void insert(int offset, double[] values) { throw new UnsupportedOperationException(); }
/* 153 */   public void insert(int offset, double[] values, int valOffset, int len) { throw new UnsupportedOperationException(); }
/*     */   
/* 155 */   public double set(int offset, double val) { throw new UnsupportedOperationException(); }
/* 156 */   public void set(int offset, double[] values) { throw new UnsupportedOperationException(); }
/* 157 */   public void set(int offset, double[] values, int valOffset, int length) { throw new UnsupportedOperationException(); }
/*     */   
/* 159 */   public double replace(int offset, double val) { throw new UnsupportedOperationException(); }
/*     */   
/* 161 */   public void transformValues(TDoubleFunction function) { throw new UnsupportedOperationException(); }
/*     */   
/* 163 */   public void reverse() { throw new UnsupportedOperationException(); }
/* 164 */   public void reverse(int from, int to) { throw new UnsupportedOperationException(); }
/* 165 */   public void shuffle(Random rand) { throw new UnsupportedOperationException(); }
/*     */   
/* 167 */   public void sort() { throw new UnsupportedOperationException(); }
/* 168 */   public void sort(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
/* 169 */   public void fill(double val) { throw new UnsupportedOperationException(); }
/* 170 */   public void fill(int fromIndex, int toIndex, double val) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableDoubleList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */