/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TCollections;
/*     */ import gnu.trove.TShortCollection;
/*     */ import gnu.trove.function.TShortFunction;
/*     */ import gnu.trove.iterator.TShortShortIterator;
/*     */ import gnu.trove.map.TShortShortMap;
/*     */ import gnu.trove.procedure.TShortProcedure;
/*     */ import gnu.trove.procedure.TShortShortProcedure;
/*     */ import gnu.trove.set.TShortSet;
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
/*     */ 
/*     */ public class TUnmodifiableShortShortMap
/*     */   implements TShortShortMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1034234728574286014L;
/*     */   private final TShortShortMap m;
/*     */   
/*     */   public TUnmodifiableShortShortMap(TShortShortMap m)
/*     */   {
/*  58 */     if (m == null)
/*  59 */       throw new NullPointerException();
/*  60 */     this.m = m;
/*     */   }
/*     */   
/*  63 */   public int size() { return this.m.size(); }
/*  64 */   public boolean isEmpty() { return this.m.isEmpty(); }
/*  65 */   public boolean containsKey(short key) { return this.m.containsKey(key); }
/*  66 */   public boolean containsValue(short val) { return this.m.containsValue(val); }
/*  67 */   public short get(short key) { return this.m.get(key); }
/*     */   
/*  69 */   public short put(short key, short value) { throw new UnsupportedOperationException(); }
/*  70 */   public short remove(short key) { throw new UnsupportedOperationException(); }
/*  71 */   public void putAll(TShortShortMap m) { throw new UnsupportedOperationException(); }
/*  72 */   public void putAll(Map<? extends Short, ? extends Short> map) { throw new UnsupportedOperationException(); }
/*  73 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */   
/*  75 */   private transient TShortSet keySet = null;
/*  76 */   private transient TShortCollection values = null;
/*     */   
/*     */   public TShortSet keySet() {
/*  79 */     if (this.keySet == null)
/*  80 */       this.keySet = TCollections.unmodifiableSet(this.m.keySet());
/*  81 */     return this.keySet; }
/*     */   
/*  83 */   public short[] keys() { return this.m.keys(); }
/*  84 */   public short[] keys(short[] array) { return this.m.keys(array); }
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
/*  97 */   public short getNoEntryKey() { return this.m.getNoEntryKey(); }
/*  98 */   public short getNoEntryValue() { return this.m.getNoEntryValue(); }
/*     */   
/*     */   public boolean forEachKey(TShortProcedure procedure) {
/* 101 */     return this.m.forEachKey(procedure);
/*     */   }
/*     */   
/* 104 */   public boolean forEachValue(TShortProcedure procedure) { return this.m.forEachValue(procedure); }
/*     */   
/*     */   public boolean forEachEntry(TShortShortProcedure procedure) {
/* 107 */     return this.m.forEachEntry(procedure);
/*     */   }
/*     */   
/*     */   public TShortShortIterator iterator() {
/* 111 */     new TShortShortIterator() {
/* 112 */       TShortShortIterator iter = TUnmodifiableShortShortMap.this.m.iterator();
/*     */       
/* 114 */       public short key() { return this.iter.key(); }
/* 115 */       public short value() { return this.iter.value(); }
/* 116 */       public void advance() { this.iter.advance(); }
/* 117 */       public boolean hasNext() { return this.iter.hasNext(); }
/* 118 */       public short setValue(short val) { throw new UnsupportedOperationException(); }
/* 119 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/* 123 */   public short putIfAbsent(short key, short value) { throw new UnsupportedOperationException(); }
/* 124 */   public void transformValues(TShortFunction function) { throw new UnsupportedOperationException(); }
/* 125 */   public boolean retainEntries(TShortShortProcedure procedure) { throw new UnsupportedOperationException(); }
/* 126 */   public boolean increment(short key) { throw new UnsupportedOperationException(); }
/* 127 */   public boolean adjustValue(short key, short amount) { throw new UnsupportedOperationException(); }
/* 128 */   public short adjustOrPutValue(short key, short adjust_amount, short put_amount) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableShortShortMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */