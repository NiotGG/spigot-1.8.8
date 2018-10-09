/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TCollections;
/*     */ import gnu.trove.TLongCollection;
/*     */ import gnu.trove.function.TLongFunction;
/*     */ import gnu.trove.iterator.TFloatLongIterator;
/*     */ import gnu.trove.map.TFloatLongMap;
/*     */ import gnu.trove.procedure.TFloatLongProcedure;
/*     */ import gnu.trove.procedure.TFloatProcedure;
/*     */ import gnu.trove.procedure.TLongProcedure;
/*     */ import gnu.trove.set.TFloatSet;
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
/*     */ public class TUnmodifiableFloatLongMap
/*     */   implements TFloatLongMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1034234728574286014L;
/*     */   private final TFloatLongMap m;
/*     */   
/*     */   public TUnmodifiableFloatLongMap(TFloatLongMap m)
/*     */   {
/*  58 */     if (m == null)
/*  59 */       throw new NullPointerException();
/*  60 */     this.m = m;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.m.size(); }
/*  64 */   public boolean isEmpty() { return this.m.isEmpty(); }
/*  65 */   public boolean containsKey(float key) { return this.m.containsKey(key); }
/*  66 */   public boolean containsValue(long val) { return this.m.containsValue(val); }
/*  67 */   public long get(float key) { return this.m.get(key); }
/*     */   
/*  69 */   public long put(float key, long value) { throw new UnsupportedOperationException(); }
/*  70 */   public long remove(float key) { throw new UnsupportedOperationException(); }
/*  71 */   public void putAll(TFloatLongMap m) { throw new UnsupportedOperationException(); }
/*  72 */   public void putAll(Map<? extends Float, ? extends Long> map) { throw new UnsupportedOperationException(); }
/*  73 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */   
/*  75 */   private transient TFloatSet keySet = null;
/*  76 */   private transient TLongCollection values = null;
/*     */   
/*     */   public TFloatSet keySet() {
/*  79 */     if (this.keySet == null)
/*  80 */       this.keySet = TCollections.unmodifiableSet(this.m.keySet());
/*  81 */     return this.keySet; }
/*     */   
/*  83 */   public float[] keys() { return this.m.keys(); }
/*  84 */   public float[] keys(float[] array) { return this.m.keys(array); }
/*     */   
/*     */   public TLongCollection valueCollection() {
/*  87 */     if (this.values == null)
/*  88 */       this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
/*  89 */     return this.values; }
/*     */   
/*  91 */   public long[] values() { return this.m.values(); }
/*  92 */   public long[] values(long[] array) { return this.m.values(array); }
/*     */   
/*  94 */   public boolean equals(Object o) { return (o == this) || (this.m.equals(o)); }
/*  95 */   public int hashCode() { return this.m.hashCode(); }
/*  96 */   public String toString() { return this.m.toString(); }
/*  97 */   public float getNoEntryKey() { return this.m.getNoEntryKey(); }
/*  98 */   public long getNoEntryValue() { return this.m.getNoEntryValue(); }
/*     */   
/*     */   public boolean forEachKey(TFloatProcedure procedure) {
/* 101 */     return this.m.forEachKey(procedure);
/*     */   }
/*     */   
/* 104 */   public boolean forEachValue(TLongProcedure procedure) { return this.m.forEachValue(procedure); }
/*     */   
/*     */   public boolean forEachEntry(TFloatLongProcedure procedure) {
/* 107 */     return this.m.forEachEntry(procedure);
/*     */   }
/*     */   
/*     */   public TFloatLongIterator iterator() {
/* 111 */     new TFloatLongIterator() {
/* 112 */       TFloatLongIterator iter = TUnmodifiableFloatLongMap.this.m.iterator();
/*     */       
/* 114 */       public float key() { return this.iter.key(); }
/* 115 */       public long value() { return this.iter.value(); }
/* 116 */       public void advance() { this.iter.advance(); }
/* 117 */       public boolean hasNext() { return this.iter.hasNext(); }
/* 118 */       public long setValue(long val) { throw new UnsupportedOperationException(); }
/* 119 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/* 123 */   public long putIfAbsent(float key, long value) { throw new UnsupportedOperationException(); }
/* 124 */   public void transformValues(TLongFunction function) { throw new UnsupportedOperationException(); }
/* 125 */   public boolean retainEntries(TFloatLongProcedure procedure) { throw new UnsupportedOperationException(); }
/* 126 */   public boolean increment(float key) { throw new UnsupportedOperationException(); }
/* 127 */   public boolean adjustValue(float key, long amount) { throw new UnsupportedOperationException(); }
/* 128 */   public long adjustOrPutValue(float key, long adjust_amount, long put_amount) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableFloatLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */