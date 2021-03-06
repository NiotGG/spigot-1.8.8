/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TCollections;
/*     */ import gnu.trove.TShortCollection;
/*     */ import gnu.trove.function.TShortFunction;
/*     */ import gnu.trove.iterator.TDoubleShortIterator;
/*     */ import gnu.trove.map.TDoubleShortMap;
/*     */ import gnu.trove.procedure.TDoubleProcedure;
/*     */ import gnu.trove.procedure.TDoubleShortProcedure;
/*     */ import gnu.trove.procedure.TShortProcedure;
/*     */ import gnu.trove.set.TDoubleSet;
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
/*     */ public class TUnmodifiableDoubleShortMap
/*     */   implements TDoubleShortMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1034234728574286014L;
/*     */   private final TDoubleShortMap m;
/*     */   
/*     */   public TUnmodifiableDoubleShortMap(TDoubleShortMap m)
/*     */   {
/*  58 */     if (m == null)
/*  59 */       throw new NullPointerException();
/*  60 */     this.m = m;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.m.size(); }
/*  64 */   public boolean isEmpty() { return this.m.isEmpty(); }
/*  65 */   public boolean containsKey(double key) { return this.m.containsKey(key); }
/*  66 */   public boolean containsValue(short val) { return this.m.containsValue(val); }
/*  67 */   public short get(double key) { return this.m.get(key); }
/*     */   
/*  69 */   public short put(double key, short value) { throw new UnsupportedOperationException(); }
/*  70 */   public short remove(double key) { throw new UnsupportedOperationException(); }
/*  71 */   public void putAll(TDoubleShortMap m) { throw new UnsupportedOperationException(); }
/*  72 */   public void putAll(Map<? extends Double, ? extends Short> map) { throw new UnsupportedOperationException(); }
/*  73 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */   
/*  75 */   private transient TDoubleSet keySet = null;
/*  76 */   private transient TShortCollection values = null;
/*     */   
/*     */   public TDoubleSet keySet() {
/*  79 */     if (this.keySet == null)
/*  80 */       this.keySet = TCollections.unmodifiableSet(this.m.keySet());
/*  81 */     return this.keySet; }
/*     */   
/*  83 */   public double[] keys() { return this.m.keys(); }
/*  84 */   public double[] keys(double[] array) { return this.m.keys(array); }
/*     */   
/*     */   public TShortCollection valueCollection() {
/*  87 */     if (this.values == null)
/*  88 */       this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
/*  89 */     return this.values; }
/*     */   
/*  91 */   public short[] values() { return this.m.values(); }
/*  92 */   public short[] values(short[] array) { return this.m.values(array); }
/*     */   
/*  94 */   public boolean equals(Object o) { return (o == this) || (this.m.equals(o)); }
/*  95 */   public int hashCode() { return this.m.hashCode(); }
/*  96 */   public String toString() { return this.m.toString(); }
/*  97 */   public double getNoEntryKey() { return this.m.getNoEntryKey(); }
/*  98 */   public short getNoEntryValue() { return this.m.getNoEntryValue(); }
/*     */   
/*     */   public boolean forEachKey(TDoubleProcedure procedure) {
/* 101 */     return this.m.forEachKey(procedure);
/*     */   }
/*     */   
/* 104 */   public boolean forEachValue(TShortProcedure procedure) { return this.m.forEachValue(procedure); }
/*     */   
/*     */   public boolean forEachEntry(TDoubleShortProcedure procedure) {
/* 107 */     return this.m.forEachEntry(procedure);
/*     */   }
/*     */   
/*     */   public TDoubleShortIterator iterator() {
/* 111 */     new TDoubleShortIterator() {
/* 112 */       TDoubleShortIterator iter = TUnmodifiableDoubleShortMap.this.m.iterator();
/*     */       
/* 114 */       public double key() { return this.iter.key(); }
/* 115 */       public short value() { return this.iter.value(); }
/* 116 */       public void advance() { this.iter.advance(); }
/* 117 */       public boolean hasNext() { return this.iter.hasNext(); }
/* 118 */       public short setValue(short val) { throw new UnsupportedOperationException(); }
/* 119 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/* 123 */   public short putIfAbsent(double key, short value) { throw new UnsupportedOperationException(); }
/* 124 */   public void transformValues(TShortFunction function) { throw new UnsupportedOperationException(); }
/* 125 */   public boolean retainEntries(TDoubleShortProcedure procedure) { throw new UnsupportedOperationException(); }
/* 126 */   public boolean increment(double key) { throw new UnsupportedOperationException(); }
/* 127 */   public boolean adjustValue(double key, short amount) { throw new UnsupportedOperationException(); }
/* 128 */   public short adjustOrPutValue(double key, short adjust_amount, short put_amount) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableDoubleShortMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */