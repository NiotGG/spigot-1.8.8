/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TCollections;
/*     */ import gnu.trove.TFloatCollection;
/*     */ import gnu.trove.function.TFloatFunction;
/*     */ import gnu.trove.iterator.TIntFloatIterator;
/*     */ import gnu.trove.map.TIntFloatMap;
/*     */ import gnu.trove.procedure.TFloatProcedure;
/*     */ import gnu.trove.procedure.TIntFloatProcedure;
/*     */ import gnu.trove.procedure.TIntProcedure;
/*     */ import gnu.trove.set.TIntSet;
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
/*     */ public class TUnmodifiableIntFloatMap
/*     */   implements TIntFloatMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1034234728574286014L;
/*     */   private final TIntFloatMap m;
/*     */   
/*     */   public TUnmodifiableIntFloatMap(TIntFloatMap m)
/*     */   {
/*  58 */     if (m == null)
/*  59 */       throw new NullPointerException();
/*  60 */     this.m = m;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.m.size(); }
/*  64 */   public boolean isEmpty() { return this.m.isEmpty(); }
/*  65 */   public boolean containsKey(int key) { return this.m.containsKey(key); }
/*  66 */   public boolean containsValue(float val) { return this.m.containsValue(val); }
/*  67 */   public float get(int key) { return this.m.get(key); }
/*     */   
/*  69 */   public float put(int key, float value) { throw new UnsupportedOperationException(); }
/*  70 */   public float remove(int key) { throw new UnsupportedOperationException(); }
/*  71 */   public void putAll(TIntFloatMap m) { throw new UnsupportedOperationException(); }
/*  72 */   public void putAll(Map<? extends Integer, ? extends Float> map) { throw new UnsupportedOperationException(); }
/*  73 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */   
/*  75 */   private transient TIntSet keySet = null;
/*  76 */   private transient TFloatCollection values = null;
/*     */   
/*     */   public TIntSet keySet() {
/*  79 */     if (this.keySet == null)
/*  80 */       this.keySet = TCollections.unmodifiableSet(this.m.keySet());
/*  81 */     return this.keySet; }
/*     */   
/*  83 */   public int[] keys() { return this.m.keys(); }
/*  84 */   public int[] keys(int[] array) { return this.m.keys(array); }
/*     */   
/*     */   public TFloatCollection valueCollection() {
/*  87 */     if (this.values == null)
/*  88 */       this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
/*  89 */     return this.values; }
/*     */   
/*  91 */   public float[] values() { return this.m.values(); }
/*  92 */   public float[] values(float[] array) { return this.m.values(array); }
/*     */   
/*  94 */   public boolean equals(Object o) { return (o == this) || (this.m.equals(o)); }
/*  95 */   public int hashCode() { return this.m.hashCode(); }
/*  96 */   public String toString() { return this.m.toString(); }
/*  97 */   public int getNoEntryKey() { return this.m.getNoEntryKey(); }
/*  98 */   public float getNoEntryValue() { return this.m.getNoEntryValue(); }
/*     */   
/*     */   public boolean forEachKey(TIntProcedure procedure) {
/* 101 */     return this.m.forEachKey(procedure);
/*     */   }
/*     */   
/* 104 */   public boolean forEachValue(TFloatProcedure procedure) { return this.m.forEachValue(procedure); }
/*     */   
/*     */   public boolean forEachEntry(TIntFloatProcedure procedure) {
/* 107 */     return this.m.forEachEntry(procedure);
/*     */   }
/*     */   
/*     */   public TIntFloatIterator iterator() {
/* 111 */     new TIntFloatIterator() {
/* 112 */       TIntFloatIterator iter = TUnmodifiableIntFloatMap.this.m.iterator();
/*     */       
/* 114 */       public int key() { return this.iter.key(); }
/* 115 */       public float value() { return this.iter.value(); }
/* 116 */       public void advance() { this.iter.advance(); }
/* 117 */       public boolean hasNext() { return this.iter.hasNext(); }
/* 118 */       public float setValue(float val) { throw new UnsupportedOperationException(); }
/* 119 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/* 123 */   public float putIfAbsent(int key, float value) { throw new UnsupportedOperationException(); }
/* 124 */   public void transformValues(TFloatFunction function) { throw new UnsupportedOperationException(); }
/* 125 */   public boolean retainEntries(TIntFloatProcedure procedure) { throw new UnsupportedOperationException(); }
/* 126 */   public boolean increment(int key) { throw new UnsupportedOperationException(); }
/* 127 */   public boolean adjustValue(int key, float amount) { throw new UnsupportedOperationException(); }
/* 128 */   public float adjustOrPutValue(int key, float adjust_amount, float put_amount) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableIntFloatMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */