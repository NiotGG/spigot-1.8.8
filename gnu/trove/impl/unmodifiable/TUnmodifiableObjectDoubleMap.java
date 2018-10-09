/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TCollections;
/*     */ import gnu.trove.TDoubleCollection;
/*     */ import gnu.trove.function.TDoubleFunction;
/*     */ import gnu.trove.iterator.TObjectDoubleIterator;
/*     */ import gnu.trove.map.TObjectDoubleMap;
/*     */ import gnu.trove.procedure.TDoubleProcedure;
/*     */ import gnu.trove.procedure.TObjectDoubleProcedure;
/*     */ import gnu.trove.procedure.TObjectProcedure;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TUnmodifiableObjectDoubleMap<K>
/*     */   implements TObjectDoubleMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1034234728574286014L;
/*     */   private final TObjectDoubleMap<K> m;
/*     */   
/*     */   public TUnmodifiableObjectDoubleMap(TObjectDoubleMap<K> m)
/*     */   {
/*  53 */     if (m == null)
/*  54 */       throw new NullPointerException();
/*  55 */     this.m = m;
/*     */   }
/*     */   
/*  58 */   public int size() { return this.m.size(); }
/*  59 */   public boolean isEmpty() { return this.m.isEmpty(); }
/*  60 */   public boolean containsKey(Object key) { return this.m.containsKey(key); }
/*  61 */   public boolean containsValue(double val) { return this.m.containsValue(val); }
/*  62 */   public double get(Object key) { return this.m.get(key); }
/*     */   
/*  64 */   public double put(K key, double value) { throw new UnsupportedOperationException(); }
/*  65 */   public double remove(Object key) { throw new UnsupportedOperationException(); }
/*  66 */   public void putAll(TObjectDoubleMap<? extends K> m) { throw new UnsupportedOperationException(); }
/*  67 */   public void putAll(Map<? extends K, ? extends Double> map) { throw new UnsupportedOperationException(); }
/*  68 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */   
/*  70 */   private transient Set<K> keySet = null;
/*  71 */   private transient TDoubleCollection values = null;
/*     */   
/*     */   public Set<K> keySet() {
/*  74 */     if (this.keySet == null)
/*  75 */       this.keySet = Collections.unmodifiableSet(this.m.keySet());
/*  76 */     return this.keySet; }
/*     */   
/*  78 */   public Object[] keys() { return this.m.keys(); }
/*  79 */   public K[] keys(K[] array) { return this.m.keys(array); }
/*     */   
/*     */   public TDoubleCollection valueCollection() {
/*  82 */     if (this.values == null)
/*  83 */       this.values = TCollections.unmodifiableCollection(this.m.valueCollection());
/*  84 */     return this.values; }
/*     */   
/*  86 */   public double[] values() { return this.m.values(); }
/*  87 */   public double[] values(double[] array) { return this.m.values(array); }
/*     */   
/*  89 */   public boolean equals(Object o) { return (o == this) || (this.m.equals(o)); }
/*  90 */   public int hashCode() { return this.m.hashCode(); }
/*  91 */   public String toString() { return this.m.toString(); }
/*  92 */   public double getNoEntryValue() { return this.m.getNoEntryValue(); }
/*     */   
/*     */   public boolean forEachKey(TObjectProcedure<? super K> procedure) {
/*  95 */     return this.m.forEachKey(procedure);
/*     */   }
/*     */   
/*  98 */   public boolean forEachValue(TDoubleProcedure procedure) { return this.m.forEachValue(procedure); }
/*     */   
/*     */   public boolean forEachEntry(TObjectDoubleProcedure<? super K> procedure) {
/* 101 */     return this.m.forEachEntry(procedure);
/*     */   }
/*     */   
/*     */   public TObjectDoubleIterator<K> iterator() {
/* 105 */     new TObjectDoubleIterator() {
/* 106 */       TObjectDoubleIterator<K> iter = TUnmodifiableObjectDoubleMap.this.m.iterator();
/*     */       
/* 108 */       public K key() { return (K)this.iter.key(); }
/* 109 */       public double value() { return this.iter.value(); }
/* 110 */       public void advance() { this.iter.advance(); }
/* 111 */       public boolean hasNext() { return this.iter.hasNext(); }
/* 112 */       public double setValue(double val) { throw new UnsupportedOperationException(); }
/* 113 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/* 117 */   public double putIfAbsent(K key, double value) { throw new UnsupportedOperationException(); }
/* 118 */   public void transformValues(TDoubleFunction function) { throw new UnsupportedOperationException(); }
/* 119 */   public boolean retainEntries(TObjectDoubleProcedure<? super K> procedure) { throw new UnsupportedOperationException(); }
/* 120 */   public boolean increment(K key) { throw new UnsupportedOperationException(); }
/* 121 */   public boolean adjustValue(K key, double amount) { throw new UnsupportedOperationException(); }
/* 122 */   public double adjustOrPutValue(K key, double adjust_amount, double put_amount) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableObjectDoubleMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */