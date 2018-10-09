/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.function.TCharFunction;
/*     */ import gnu.trove.list.TCharList;
/*     */ import gnu.trove.procedure.TCharProcedure;
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
/*     */ public class TUnmodifiableCharList
/*     */   extends TUnmodifiableCharCollection
/*     */   implements TCharList
/*     */ {
/*     */   static final long serialVersionUID = -283967356065247728L;
/*     */   final TCharList list;
/*     */   
/*     */   public TUnmodifiableCharList(TCharList list)
/*     */   {
/*  58 */     super(list);
/*  59 */     this.list = list;
/*     */   }
/*     */   
/*  62 */   public boolean equals(Object o) { return (o == this) || (this.list.equals(o)); }
/*  63 */   public int hashCode() { return this.list.hashCode(); }
/*     */   
/*  65 */   public char get(int index) { return this.list.get(index); }
/*  66 */   public int indexOf(char o) { return this.list.indexOf(o); }
/*  67 */   public int lastIndexOf(char o) { return this.list.lastIndexOf(o); }
/*     */   
/*     */   public char[] toArray(int offset, int len) {
/*  70 */     return this.list.toArray(offset, len);
/*     */   }
/*     */   
/*  73 */   public char[] toArray(char[] dest, int offset, int len) { return this.list.toArray(dest, offset, len); }
/*     */   
/*     */   public char[] toArray(char[] dest, int source_pos, int dest_pos, int len) {
/*  76 */     return this.list.toArray(dest, source_pos, dest_pos, len);
/*     */   }
/*     */   
/*     */   public boolean forEachDescending(TCharProcedure procedure) {
/*  80 */     return this.list.forEachDescending(procedure);
/*     */   }
/*     */   
/*  83 */   public int binarySearch(char value) { return this.list.binarySearch(value); }
/*     */   
/*  85 */   public int binarySearch(char value, int fromIndex, int toIndex) { return this.list.binarySearch(value, fromIndex, toIndex); }
/*     */   
/*     */ 
/*  88 */   public int indexOf(int offset, char value) { return this.list.indexOf(offset, value); }
/*  89 */   public int lastIndexOf(int offset, char value) { return this.list.lastIndexOf(offset, value); }
/*  90 */   public TCharList grep(TCharProcedure condition) { return this.list.grep(condition); }
/*  91 */   public TCharList inverseGrep(TCharProcedure condition) { return this.list.inverseGrep(condition); }
/*     */   
/*  93 */   public char max() { return this.list.max(); }
/*  94 */   public char min() { return this.list.min(); }
/*  95 */   public char sum() { return this.list.sum(); }
/*     */   
/*     */   public TCharList subList(int fromIndex, int toIndex) {
/*  98 */     return new TUnmodifiableCharList(this.list.subList(fromIndex, toIndex));
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
/* 140 */     return (this.list instanceof RandomAccess) ? new TUnmodifiableRandomAccessCharList(this.list) : this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 145 */   public void add(char[] vals) { throw new UnsupportedOperationException(); }
/* 146 */   public void add(char[] vals, int offset, int length) { throw new UnsupportedOperationException(); }
/*     */   
/* 148 */   public char removeAt(int offset) { throw new UnsupportedOperationException(); }
/* 149 */   public void remove(int offset, int length) { throw new UnsupportedOperationException(); }
/*     */   
/* 151 */   public void insert(int offset, char value) { throw new UnsupportedOperationException(); }
/* 152 */   public void insert(int offset, char[] values) { throw new UnsupportedOperationException(); }
/* 153 */   public void insert(int offset, char[] values, int valOffset, int len) { throw new UnsupportedOperationException(); }
/*     */   
/* 155 */   public char set(int offset, char val) { throw new UnsupportedOperationException(); }
/* 156 */   public void set(int offset, char[] values) { throw new UnsupportedOperationException(); }
/* 157 */   public void set(int offset, char[] values, int valOffset, int length) { throw new UnsupportedOperationException(); }
/*     */   
/* 159 */   public char replace(int offset, char val) { throw new UnsupportedOperationException(); }
/*     */   
/* 161 */   public void transformValues(TCharFunction function) { throw new UnsupportedOperationException(); }
/*     */   
/* 163 */   public void reverse() { throw new UnsupportedOperationException(); }
/* 164 */   public void reverse(int from, int to) { throw new UnsupportedOperationException(); }
/* 165 */   public void shuffle(Random rand) { throw new UnsupportedOperationException(); }
/*     */   
/* 167 */   public void sort() { throw new UnsupportedOperationException(); }
/* 168 */   public void sort(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
/* 169 */   public void fill(char val) { throw new UnsupportedOperationException(); }
/* 170 */   public void fill(int fromIndex, int toIndex, char val) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableCharList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */