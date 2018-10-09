/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TCollections;
/*     */ import gnu.trove.TIntCollection;
/*     */ import gnu.trove.function.TIntFunction;
/*     */ import gnu.trove.iterator.TCharIntIterator;
/*     */ import gnu.trove.map.TCharIntMap;
/*     */ import gnu.trove.procedure.TCharIntProcedure;
/*     */ import gnu.trove.procedure.TCharProcedure;
/*     */ import gnu.trove.procedure.TIntProcedure;
/*     */ import gnu.trove.set.TCharSet;
/*     */ import java.io.Serializable;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TUnmodifiableCharIntMap
/*     */   implements TCharIntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1034234728574286014L;
/*     */   private final TCharIntMap m;
/*     */   
/*     */   public TUnmodifiableCharIntMap(TCharIntMap m)
/*     */   {
/*  58 */     if (m == null)
/*  59 */       throw new NullPointerException();
/*  60 */     this.m = m;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.m.size(); }
/*  64 */   public boolean isEmpty() { return this.m.isEmpty(); }
/*  65 */   public boolean containsKey(char key) { return this.m.containsKey(key); }
/*  66 */   public boolean containsValue(int val) { return this.m.containsValue(val); }
/*  67 */   public int get(char key) { return this.m.get(key); }
/*     */   
/*  69 */   public int put(char key, int value) { throw new UnsupportedOperationException(); }
/*  70 */   public int remove(char key) { throw new UnsupportedOperationException(); }
/*  71 */   public void putAll(TCharIntMap m) { throw new UnsupportedOperationException(); }
/*  72 */   public void putAll(Map<? extends Character, ? extends Integer> map) { throw new UnsupportedOperationException(); }
/*  73 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */   
/*  75 */   private transient TCharSet keySet = null;
/*  76 */   private transient TIntCollection values = null;
/*     */   
/*     */   public TCharSet keySet() {
/*  79 */     if (this.keySet == null)
/*  80 */       this.keySet = TCollections.unmodifiableSet(this.m.keySet());
/*  81 */     return this.keySet; }
/*     */   
/*  83 */   public char[] keys() { return this.m.keys(); }
/*  84 */   public char[] keys(char[] array) { return this.m.keys(array); }
/*     */   
/*     */   public TIntCollection valueCollection() {
/*  87 */     if (this.values == null)
/*  88 */       this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
/*  89 */     return this.values; }
/*     */   
/*  91 */   public int[] values() { return this.m.values(); }
/*  92 */   public int[] values(int[] array) { return this.m.values(array); }
/*     */   
/*  94 */   public boolean equals(Object o) { return (o == this) || (this.m.equals(o)); }
/*  95 */   public int hashCode() { return this.m.hashCode(); }
/*  96 */   public String toString() { return this.m.toString(); }
/*  97 */   public char getNoEntryKey() { return this.m.getNoEntryKey(); }
/*  98 */   public int getNoEntryValue() { return this.m.getNoEntryValue(); }
/*     */   
/*     */   public boolean forEachKey(TCharProcedure procedure) {
/* 101 */     return this.m.forEachKey(procedure);
/*     */   }
/*     */   
/* 104 */   public boolean forEachValue(TIntProcedure procedure) { return this.m.forEachValue(procedure); }
/*     */   
/*     */   public boolean forEachEntry(TCharIntProcedure procedure) {
/* 107 */     return this.m.forEachEntry(procedure);
/*     */   }
/*     */   
/*     */   public TCharIntIterator iterator() {
/* 111 */     new TCharIntIterator() {
/* 112 */       TCharIntIterator iter = TUnmodifiableCharIntMap.this.m.iterator();
/*     */       
/* 114 */       public char key() { return this.iter.key(); }
/* 115 */       public int value() { return this.iter.value(); }
/* 116 */       public void advance() { this.iter.advance(); }
/* 117 */       public boolean hasNext() { return this.iter.hasNext(); }
/* 118 */       public int setValue(int val) { throw new UnsupportedOperationException(); }
/* 119 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/* 123 */   public int putIfAbsent(char key, int value) { throw new UnsupportedOperationException(); }
/* 124 */   public void transformValues(TIntFunction function) { throw new UnsupportedOperationException(); }
/* 125 */   public boolean retainEntries(TCharIntProcedure procedure) { throw new UnsupportedOperationException(); }
/* 126 */   public boolean increment(char key) { throw new UnsupportedOperationException(); }
/* 127 */   public boolean adjustValue(char key, int amount) { throw new UnsupportedOperationException(); }
/* 128 */   public int adjustOrPutValue(char key, int adjust_amount, int put_amount) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableCharIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */