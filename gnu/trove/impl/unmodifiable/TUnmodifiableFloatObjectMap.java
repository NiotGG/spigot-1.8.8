/*     */ package gnu.trove.impl.unmodifiable;
/*     */ 
/*     */ import gnu.trove.TCollections;
/*     */ import gnu.trove.function.TObjectFunction;
/*     */ import gnu.trove.iterator.TFloatObjectIterator;
/*     */ import gnu.trove.map.TFloatObjectMap;
/*     */ import gnu.trove.procedure.TFloatObjectProcedure;
/*     */ import gnu.trove.procedure.TFloatProcedure;
/*     */ import gnu.trove.procedure.TObjectProcedure;
/*     */ import gnu.trove.set.TFloatSet;
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
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
/*     */ public class TUnmodifiableFloatObjectMap<V>
/*     */   implements TFloatObjectMap<V>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1034234728574286014L;
/*     */   private final TFloatObjectMap<V> m;
/*     */   
/*     */   public TUnmodifiableFloatObjectMap(TFloatObjectMap<V> m)
/*     */   {
/*  53 */     if (m == null)
/*  54 */       throw new NullPointerException();
/*  55 */     this.m = m;
/*     */   }
/*     */   
/*  58 */   public int size() { return this.m.size(); }
/*  59 */   public boolean isEmpty() { return this.m.isEmpty(); }
/*  60 */   public boolean containsKey(float key) { return this.m.containsKey(key); }
/*  61 */   public boolean containsValue(Object val) { return this.m.containsValue(val); }
/*  62 */   public V get(float key) { return (V)this.m.get(key); }
/*     */   
/*  64 */   public V put(float key, V value) { throw new UnsupportedOperationException(); }
/*  65 */   public V remove(float key) { throw new UnsupportedOperationException(); }
/*  66 */   public void putAll(TFloatObjectMap<? extends V> m) { throw new UnsupportedOperationException(); }
/*  67 */   public void putAll(Map<? extends Float, ? extends V> map) { throw new UnsupportedOperationException(); }
/*  68 */   public void clear() { throw new UnsupportedOperationException(); }
/*     */   
/*  70 */   private transient TFloatSet keySet = null;
/*  71 */   private transient Collection<V> values = null;
/*     */   
/*     */   public TFloatSet keySet() {
/*  74 */     if (this.keySet == null)
/*  75 */       this.keySet = TCollections.unmodifiableSet(this.m.keySet());
/*  76 */     return this.keySet; }
/*     */   
/*  78 */   public float[] keys() { return this.m.keys(); }
/*  79 */   public float[] keys(float[] array) { return this.m.keys(array); }
/*     */   
/*     */   public Collection<V> valueCollection() {
/*  82 */     if (this.values == null)
/*  83 */       this.values = Collections.unmodifiableCollection(this.m.valueCollection());
/*  84 */     return this.values; }
/*     */   
/*  86 */   public Object[] values() { return this.m.values(); }
/*  87 */   public V[] values(V[] array) { return this.m.values(array); }
/*     */   
/*  89 */   public boolean equals(Object o) { return (o == this) || (this.m.equals(o)); }
/*  90 */   public int hashCode() { return this.m.hashCode(); }
/*  91 */   public String toString() { return this.m.toString(); }
/*  92 */   public float getNoEntryKey() { return this.m.getNoEntryKey(); }
/*     */   
/*     */   public boolean forEachKey(TFloatProcedure procedure) {
/*  95 */     return this.m.forEachKey(procedure);
/*     */   }
/*     */   
/*  98 */   public boolean forEachValue(TObjectProcedure<? super V> procedure) { return this.m.forEachValue(procedure); }
/*     */   
/*     */   public boolean forEachEntry(TFloatObjectProcedure<? super V> procedure) {
/* 101 */     return this.m.forEachEntry(procedure);
/*     */   }
/*     */   
/*     */   public TFloatObjectIterator<V> iterator() {
/* 105 */     new TFloatObjectIterator() {
/* 106 */       TFloatObjectIterator<V> iter = TUnmodifiableFloatObjectMap.this.m.iterator();
/*     */       
/* 108 */       public float key() { return this.iter.key(); }
/* 109 */       public V value() { return (V)this.iter.value(); }
/* 110 */       public void advance() { this.iter.advance(); }
/* 111 */       public boolean hasNext() { return this.iter.hasNext(); }
/* 112 */       public V setValue(V val) { throw new UnsupportedOperationException(); }
/* 113 */       public void remove() { throw new UnsupportedOperationException(); }
/*     */     };
/*     */   }
/*     */   
/* 117 */   public V putIfAbsent(float key, V value) { throw new UnsupportedOperationException(); }
/* 118 */   public void transformValues(TObjectFunction<V, V> function) { throw new UnsupportedOperationException(); }
/* 119 */   public boolean retainEntries(TFloatObjectProcedure<? super V> procedure) { throw new UnsupportedOperationException(); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\unmodifiable\TUnmodifiableFloatObjectMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */