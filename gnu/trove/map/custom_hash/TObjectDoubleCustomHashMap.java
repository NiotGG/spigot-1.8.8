/*      */ package gnu.trove.map.custom_hash;
/*      */ 
/*      */ import gnu.trove.TDoubleCollection;
/*      */ import gnu.trove.function.TDoubleFunction;
/*      */ import gnu.trove.impl.Constants;
/*      */ import gnu.trove.impl.HashFunctions;
/*      */ import gnu.trove.impl.hash.TCustomObjectHash;
/*      */ import gnu.trove.impl.hash.THash;
/*      */ import gnu.trove.impl.hash.TObjectHash;
/*      */ import gnu.trove.iterator.TDoubleIterator;
/*      */ import gnu.trove.iterator.TObjectDoubleIterator;
/*      */ import gnu.trove.iterator.hash.TObjectHashIterator;
/*      */ import gnu.trove.map.TObjectDoubleMap;
/*      */ import gnu.trove.procedure.TDoubleProcedure;
/*      */ import gnu.trove.procedure.TObjectDoubleProcedure;
/*      */ import gnu.trove.procedure.TObjectProcedure;
/*      */ import gnu.trove.strategy.HashingStrategy;
/*      */ import java.io.Externalizable;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectOutput;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TObjectDoubleCustomHashMap<K>
/*      */   extends TCustomObjectHash<K>
/*      */   implements TObjectDoubleMap<K>, Externalizable
/*      */ {
/*      */   static final long serialVersionUID = 1L;
/*   58 */   private final TObjectDoubleProcedure<K> PUT_ALL_PROC = new TObjectDoubleProcedure() {
/*      */     public boolean execute(K key, double value) {
/*   60 */       TObjectDoubleCustomHashMap.this.put(key, value);
/*   61 */       return true;
/*      */     }
/*      */   };
/*      */   
/*      */ 
/*      */ 
/*      */   protected transient double[] _values;
/*      */   
/*      */ 
/*      */ 
/*      */   protected double no_entry_value;
/*      */   
/*      */ 
/*      */ 
/*      */   public TObjectDoubleCustomHashMap() {}
/*      */   
/*      */ 
/*      */ 
/*      */   public TObjectDoubleCustomHashMap(HashingStrategy<? super K> strategy)
/*      */   {
/*   81 */     super(strategy);
/*   82 */     this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TObjectDoubleCustomHashMap(HashingStrategy<? super K> strategy, int initialCapacity)
/*      */   {
/*   96 */     super(strategy, initialCapacity);
/*      */     
/*   98 */     this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TObjectDoubleCustomHashMap(HashingStrategy<? super K> strategy, int initialCapacity, float loadFactor)
/*      */   {
/*  113 */     super(strategy, initialCapacity, loadFactor);
/*      */     
/*  115 */     this.no_entry_value = Constants.DEFAULT_DOUBLE_NO_ENTRY_VALUE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TObjectDoubleCustomHashMap(HashingStrategy<? super K> strategy, int initialCapacity, float loadFactor, double noEntryValue)
/*      */   {
/*  131 */     super(strategy, initialCapacity, loadFactor);
/*      */     
/*  133 */     this.no_entry_value = noEntryValue;
/*      */     
/*  135 */     if (this.no_entry_value != 0.0D) {
/*  136 */       Arrays.fill(this._values, this.no_entry_value);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TObjectDoubleCustomHashMap(HashingStrategy<? super K> strategy, TObjectDoubleMap<? extends K> map)
/*      */   {
/*  150 */     this(strategy, map.size(), 0.5F, map.getNoEntryValue());
/*      */     
/*  152 */     if ((map instanceof TObjectDoubleCustomHashMap)) {
/*  153 */       TObjectDoubleCustomHashMap hashmap = (TObjectDoubleCustomHashMap)map;
/*  154 */       this._loadFactor = hashmap._loadFactor;
/*  155 */       this.no_entry_value = hashmap.no_entry_value;
/*  156 */       this.strategy = hashmap.strategy;
/*      */       
/*  158 */       if (this.no_entry_value != 0.0D) {
/*  159 */         Arrays.fill(this._values, this.no_entry_value);
/*      */       }
/*  161 */       setUp((int)Math.ceil(10.0F / this._loadFactor));
/*      */     }
/*  163 */     putAll(map);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int setUp(int initialCapacity)
/*      */   {
/*  177 */     int capacity = super.setUp(initialCapacity);
/*  178 */     this._values = new double[capacity];
/*  179 */     return capacity;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void rehash(int newCapacity)
/*      */   {
/*  189 */     int oldCapacity = this._set.length;
/*      */     
/*      */ 
/*  192 */     K[] oldKeys = (Object[])this._set;
/*  193 */     double[] oldVals = this._values;
/*      */     
/*  195 */     this._set = new Object[newCapacity];
/*  196 */     Arrays.fill(this._set, FREE);
/*  197 */     this._values = new double[newCapacity];
/*  198 */     Arrays.fill(this._values, this.no_entry_value);
/*      */     
/*  200 */     for (int i = oldCapacity; i-- > 0;) {
/*  201 */       K o = oldKeys[i];
/*  202 */       if ((o != FREE) && (o != REMOVED)) {
/*  203 */         int index = insertKey(o);
/*  204 */         if (index < 0) {
/*  205 */           throwObjectContractViolation(this._set[(-index - 1)], o);
/*      */         }
/*  207 */         this._values[index] = oldVals[i];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public double getNoEntryValue()
/*      */   {
/*  217 */     return this.no_entry_value;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean containsKey(Object key)
/*      */   {
/*  223 */     return contains(key);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean containsValue(double val)
/*      */   {
/*  229 */     Object[] keys = this._set;
/*  230 */     double[] vals = this._values;
/*      */     
/*  232 */     for (int i = vals.length; i-- > 0;) {
/*  233 */       if ((keys[i] != FREE) && (keys[i] != REMOVED) && (val == vals[i])) {
/*  234 */         return true;
/*      */       }
/*      */     }
/*  237 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public double get(Object key)
/*      */   {
/*  243 */     int index = index(key);
/*  244 */     return index < 0 ? this.no_entry_value : this._values[index];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public double put(K key, double value)
/*      */   {
/*  252 */     int index = insertKey(key);
/*  253 */     return doPut(value, index);
/*      */   }
/*      */   
/*      */ 
/*      */   public double putIfAbsent(K key, double value)
/*      */   {
/*  259 */     int index = insertKey(key);
/*  260 */     if (index < 0)
/*  261 */       return this._values[(-index - 1)];
/*  262 */     return doPut(value, index);
/*      */   }
/*      */   
/*      */   private double doPut(double value, int index)
/*      */   {
/*  267 */     double previous = this.no_entry_value;
/*  268 */     boolean isNewMapping = true;
/*  269 */     if (index < 0) {
/*  270 */       index = -index - 1;
/*  271 */       previous = this._values[index];
/*  272 */       isNewMapping = false;
/*      */     }
/*      */     
/*  275 */     this._values[index] = value;
/*      */     
/*  277 */     if (isNewMapping) {
/*  278 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*  280 */     return previous;
/*      */   }
/*      */   
/*      */ 
/*      */   public double remove(Object key)
/*      */   {
/*  286 */     double prev = this.no_entry_value;
/*  287 */     int index = index(key);
/*  288 */     if (index >= 0) {
/*  289 */       prev = this._values[index];
/*  290 */       removeAt(index);
/*      */     }
/*  292 */     return prev;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void removeAt(int index)
/*      */   {
/*  304 */     this._values[index] = this.no_entry_value;
/*  305 */     super.removeAt(index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putAll(Map<? extends K, ? extends Double> map)
/*      */   {
/*  313 */     Set<? extends Map.Entry<? extends K, ? extends Double>> set = map.entrySet();
/*  314 */     for (Map.Entry<? extends K, ? extends Double> entry : set) {
/*  315 */       put(entry.getKey(), ((Double)entry.getValue()).doubleValue());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void putAll(TObjectDoubleMap<? extends K> map)
/*      */   {
/*  322 */     map.forEachEntry(this.PUT_ALL_PROC);
/*      */   }
/*      */   
/*      */ 
/*      */   public void clear()
/*      */   {
/*  328 */     super.clear();
/*  329 */     Arrays.fill(this._set, 0, this._set.length, FREE);
/*  330 */     Arrays.fill(this._values, 0, this._values.length, this.no_entry_value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<K> keySet()
/*      */   {
/*  338 */     return new KeyView();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Object[] keys()
/*      */   {
/*  345 */     K[] keys = (Object[])new Object[size()];
/*  346 */     Object[] k = this._set;
/*      */     
/*  348 */     int i = k.length; for (int j = 0; i-- > 0;) {
/*  349 */       if ((k[i] != FREE) && (k[i] != REMOVED))
/*      */       {
/*  351 */         keys[(j++)] = k[i];
/*      */       }
/*      */     }
/*  354 */     return keys;
/*      */   }
/*      */   
/*      */ 
/*      */   public K[] keys(K[] a)
/*      */   {
/*  360 */     int size = size();
/*  361 */     if (a.length < size)
/*      */     {
/*  363 */       a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
/*      */     }
/*      */     
/*      */ 
/*  367 */     Object[] k = this._set;
/*      */     
/*  369 */     int i = k.length; for (int j = 0; i-- > 0;) {
/*  370 */       if ((k[i] != FREE) && (k[i] != REMOVED))
/*      */       {
/*  372 */         a[(j++)] = k[i];
/*      */       }
/*      */     }
/*  375 */     return a;
/*      */   }
/*      */   
/*      */ 
/*      */   public TDoubleCollection valueCollection()
/*      */   {
/*  381 */     return new TDoubleValueCollection();
/*      */   }
/*      */   
/*      */ 
/*      */   public double[] values()
/*      */   {
/*  387 */     double[] vals = new double[size()];
/*  388 */     double[] v = this._values;
/*  389 */     Object[] keys = this._set;
/*      */     
/*  391 */     int i = v.length; for (int j = 0; i-- > 0;) {
/*  392 */       if ((keys[i] != FREE) && (keys[i] != REMOVED)) {
/*  393 */         vals[(j++)] = v[i];
/*      */       }
/*      */     }
/*  396 */     return vals;
/*      */   }
/*      */   
/*      */ 
/*      */   public double[] values(double[] array)
/*      */   {
/*  402 */     int size = size();
/*  403 */     if (array.length < size) {
/*  404 */       array = new double[size];
/*      */     }
/*      */     
/*  407 */     double[] v = this._values;
/*  408 */     Object[] keys = this._set;
/*      */     
/*  410 */     int i = v.length; for (int j = 0; i-- > 0;) {
/*  411 */       if ((keys[i] != FREE) && (keys[i] != REMOVED)) {
/*  412 */         array[(j++)] = v[i];
/*      */       }
/*      */     }
/*  415 */     if (array.length > size) {
/*  416 */       array[size] = this.no_entry_value;
/*      */     }
/*  418 */     return array;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public TObjectDoubleIterator<K> iterator()
/*      */   {
/*  426 */     return new TObjectDoubleHashIterator(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean increment(K key)
/*      */   {
/*  434 */     return adjustValue(key, 1.0D);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean adjustValue(K key, double amount)
/*      */   {
/*  440 */     int index = index(key);
/*  441 */     if (index < 0) {
/*  442 */       return false;
/*      */     }
/*  444 */     this._values[index] += amount;
/*  445 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double adjustOrPutValue(K key, double adjust_amount, double put_amount)
/*      */   {
/*  454 */     int index = insertKey(key);
/*      */     boolean isNewMapping;
/*      */     double newValue;
/*  457 */     boolean isNewMapping; if (index < 0) {
/*  458 */       index = -index - 1;
/*  459 */       double newValue = this._values[index] += adjust_amount;
/*  460 */       isNewMapping = false;
/*      */     } else {
/*  462 */       newValue = this._values[index] = put_amount;
/*  463 */       isNewMapping = true;
/*      */     }
/*      */     
/*      */ 
/*  467 */     if (isNewMapping) {
/*  468 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*      */     
/*  471 */     return newValue;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean forEachKey(TObjectProcedure<? super K> procedure)
/*      */   {
/*  483 */     return forEach(procedure);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean forEachValue(TDoubleProcedure procedure)
/*      */   {
/*  495 */     Object[] keys = this._set;
/*  496 */     double[] values = this._values;
/*  497 */     for (int i = values.length; i-- > 0;) {
/*  498 */       if ((keys[i] != FREE) && (keys[i] != REMOVED) && (!procedure.execute(values[i])))
/*      */       {
/*  500 */         return false;
/*      */       }
/*      */     }
/*  503 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean forEachEntry(TObjectDoubleProcedure<? super K> procedure)
/*      */   {
/*  517 */     Object[] keys = this._set;
/*  518 */     double[] values = this._values;
/*  519 */     for (int i = keys.length; i-- > 0;) {
/*  520 */       if ((keys[i] != FREE) && (keys[i] != REMOVED) && (!procedure.execute(keys[i], values[i])))
/*      */       {
/*      */ 
/*  523 */         return false;
/*      */       }
/*      */     }
/*  526 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean retainEntries(TObjectDoubleProcedure<? super K> procedure)
/*      */   {
/*  538 */     boolean modified = false;
/*      */     
/*  540 */     K[] keys = (Object[])this._set;
/*  541 */     double[] values = this._values;
/*      */     
/*      */ 
/*  544 */     tempDisableAutoCompaction();
/*      */     try {
/*  546 */       for (i = keys.length; i-- > 0;) {
/*  547 */         if ((keys[i] != FREE) && (keys[i] != REMOVED) && (!procedure.execute(keys[i], values[i])))
/*      */         {
/*      */ 
/*  550 */           removeAt(i);
/*  551 */           modified = true;
/*      */         }
/*      */       }
/*      */     } finally {
/*      */       int i;
/*  556 */       reenableAutoCompaction(true);
/*      */     }
/*      */     
/*  559 */     return modified;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void transformValues(TDoubleFunction function)
/*      */   {
/*  569 */     Object[] keys = this._set;
/*  570 */     double[] values = this._values;
/*  571 */     for (int i = values.length; i-- > 0;) {
/*  572 */       if ((keys[i] != null) && (keys[i] != REMOVED)) {
/*  573 */         values[i] = function.execute(values[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object other)
/*      */   {
/*  589 */     if (!(other instanceof TObjectDoubleMap)) {
/*  590 */       return false;
/*      */     }
/*  592 */     TObjectDoubleMap that = (TObjectDoubleMap)other;
/*  593 */     if (that.size() != size()) {
/*  594 */       return false;
/*      */     }
/*      */     try {
/*  597 */       TObjectDoubleIterator iter = iterator();
/*  598 */       while (iter.hasNext()) {
/*  599 */         iter.advance();
/*  600 */         Object key = iter.key();
/*  601 */         double value = iter.value();
/*  602 */         if (value == this.no_entry_value) {
/*  603 */           if ((that.get(key) != that.getNoEntryValue()) || (!that.containsKey(key)))
/*      */           {
/*      */ 
/*  606 */             return false;
/*      */           }
/*      */         }
/*  609 */         else if (value != that.get(key)) {
/*  610 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (ClassCastException ex) {}
/*      */     
/*      */ 
/*  617 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  623 */     int hashcode = 0;
/*  624 */     Object[] keys = this._set;
/*  625 */     double[] values = this._values;
/*  626 */     for (int i = values.length; i-- > 0;) {
/*  627 */       if ((keys[i] != FREE) && (keys[i] != REMOVED)) {
/*  628 */         hashcode += (HashFunctions.hash(values[i]) ^ (keys[i] == null ? 0 : keys[i].hashCode()));
/*      */       }
/*      */     }
/*      */     
/*  632 */     return hashcode;
/*      */   }
/*      */   
/*      */   protected class KeyView extends TObjectDoubleCustomHashMap<K>.MapBackedView<K> {
/*      */     protected KeyView() {
/*  637 */       super(null);
/*      */     }
/*      */     
/*      */     public Iterator<K> iterator() {
/*  641 */       return new TObjectHashIterator(TObjectDoubleCustomHashMap.this);
/*      */     }
/*      */     
/*      */     public boolean removeElement(K key) {
/*  645 */       return TObjectDoubleCustomHashMap.this.no_entry_value != TObjectDoubleCustomHashMap.this.remove(key);
/*      */     }
/*      */     
/*      */     public boolean containsElement(K key) {
/*  649 */       return TObjectDoubleCustomHashMap.this.contains(key);
/*      */     }
/*      */   }
/*      */   
/*      */   private abstract class MapBackedView<E> extends AbstractSet<E> implements Set<E>, Iterable<E>
/*      */   {
/*      */     private MapBackedView() {}
/*      */     
/*      */     public abstract boolean removeElement(E paramE);
/*      */     
/*      */     public abstract boolean containsElement(E paramE);
/*      */     
/*      */     public boolean contains(Object key)
/*      */     {
/*  663 */       return containsElement(key);
/*      */     }
/*      */     
/*      */     public boolean remove(Object o)
/*      */     {
/*  668 */       return removeElement(o);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  672 */       TObjectDoubleCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public boolean add(E obj) {
/*  676 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int size() {
/*  680 */       return TObjectDoubleCustomHashMap.this.size();
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/*  684 */       Object[] result = new Object[size()];
/*  685 */       Iterator<E> e = iterator();
/*  686 */       for (int i = 0; e.hasNext(); i++) {
/*  687 */         result[i] = e.next();
/*      */       }
/*  689 */       return result;
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] a) {
/*  693 */       int size = size();
/*  694 */       if (a.length < size)
/*      */       {
/*  696 */         a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
/*      */       }
/*      */       
/*      */ 
/*  700 */       Iterator<E> it = iterator();
/*  701 */       Object[] result = a;
/*  702 */       for (int i = 0; i < size; i++) {
/*  703 */         result[i] = it.next();
/*      */       }
/*      */       
/*  706 */       if (a.length > size) {
/*  707 */         a[size] = null;
/*      */       }
/*      */       
/*  710 */       return a;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  714 */       return TObjectDoubleCustomHashMap.this.isEmpty();
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends E> collection) {
/*  718 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> collection)
/*      */     {
/*  723 */       boolean changed = false;
/*  724 */       Iterator<E> i = iterator();
/*  725 */       while (i.hasNext()) {
/*  726 */         if (!collection.contains(i.next())) {
/*  727 */           i.remove();
/*  728 */           changed = true;
/*      */         }
/*      */       }
/*  731 */       return changed;
/*      */     }
/*      */   }
/*      */   
/*      */   class TDoubleValueCollection implements TDoubleCollection
/*      */   {
/*      */     TDoubleValueCollection() {}
/*      */     
/*      */     public TDoubleIterator iterator() {
/*  740 */       return new TObjectDoubleValueHashIterator();
/*      */     }
/*      */     
/*      */     public double getNoEntryValue()
/*      */     {
/*  745 */       return TObjectDoubleCustomHashMap.this.no_entry_value;
/*      */     }
/*      */     
/*      */     public int size()
/*      */     {
/*  750 */       return TObjectDoubleCustomHashMap.this._size;
/*      */     }
/*      */     
/*      */     public boolean isEmpty()
/*      */     {
/*  755 */       return 0 == TObjectDoubleCustomHashMap.this._size;
/*      */     }
/*      */     
/*      */     public boolean contains(double entry)
/*      */     {
/*  760 */       return TObjectDoubleCustomHashMap.this.containsValue(entry);
/*      */     }
/*      */     
/*      */     public double[] toArray()
/*      */     {
/*  765 */       return TObjectDoubleCustomHashMap.this.values();
/*      */     }
/*      */     
/*      */     public double[] toArray(double[] dest)
/*      */     {
/*  770 */       return TObjectDoubleCustomHashMap.this.values(dest);
/*      */     }
/*      */     
/*      */     public boolean add(double entry) {
/*  774 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean remove(double entry)
/*      */     {
/*  779 */       double[] values = TObjectDoubleCustomHashMap.this._values;
/*  780 */       Object[] set = TObjectDoubleCustomHashMap.this._set;
/*      */       
/*  782 */       for (int i = values.length; i-- > 0;) {
/*  783 */         if ((set[i] != TObjectHash.FREE) && (set[i] != TObjectHash.REMOVED) && (entry == values[i])) {
/*  784 */           TObjectDoubleCustomHashMap.this.removeAt(i);
/*  785 */           return true;
/*      */         }
/*      */       }
/*  788 */       return false;
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> collection)
/*      */     {
/*  793 */       for (Object element : collection) {
/*  794 */         if ((element instanceof Double)) {
/*  795 */           double ele = ((Double)element).doubleValue();
/*  796 */           if (!TObjectDoubleCustomHashMap.this.containsValue(ele)) {
/*  797 */             return false;
/*      */           }
/*      */         } else {
/*  800 */           return false;
/*      */         }
/*      */       }
/*  803 */       return true;
/*      */     }
/*      */     
/*      */     public boolean containsAll(TDoubleCollection collection)
/*      */     {
/*  808 */       TDoubleIterator iter = collection.iterator();
/*  809 */       while (iter.hasNext()) {
/*  810 */         if (!TObjectDoubleCustomHashMap.this.containsValue(iter.next())) {
/*  811 */           return false;
/*      */         }
/*      */       }
/*  814 */       return true;
/*      */     }
/*      */     
/*      */     public boolean containsAll(double[] array)
/*      */     {
/*  819 */       for (double element : array) {
/*  820 */         if (!TObjectDoubleCustomHashMap.this.containsValue(element)) {
/*  821 */           return false;
/*      */         }
/*      */       }
/*  824 */       return true;
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Double> collection)
/*      */     {
/*  829 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(TDoubleCollection collection)
/*      */     {
/*  834 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(double[] array)
/*      */     {
/*  839 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean retainAll(Collection<?> collection)
/*      */     {
/*  845 */       boolean modified = false;
/*  846 */       TDoubleIterator iter = iterator();
/*  847 */       while (iter.hasNext()) {
/*  848 */         if (!collection.contains(Double.valueOf(iter.next()))) {
/*  849 */           iter.remove();
/*  850 */           modified = true;
/*      */         }
/*      */       }
/*  853 */       return modified;
/*      */     }
/*      */     
/*      */     public boolean retainAll(TDoubleCollection collection)
/*      */     {
/*  858 */       if (this == collection) {
/*  859 */         return false;
/*      */       }
/*  861 */       boolean modified = false;
/*  862 */       TDoubleIterator iter = iterator();
/*  863 */       while (iter.hasNext()) {
/*  864 */         if (!collection.contains(iter.next())) {
/*  865 */           iter.remove();
/*  866 */           modified = true;
/*      */         }
/*      */       }
/*  869 */       return modified;
/*      */     }
/*      */     
/*      */     public boolean retainAll(double[] array)
/*      */     {
/*  874 */       boolean changed = false;
/*  875 */       Arrays.sort(array);
/*  876 */       double[] values = TObjectDoubleCustomHashMap.this._values;
/*      */       
/*  878 */       Object[] set = TObjectDoubleCustomHashMap.this._set;
/*  879 */       for (int i = set.length; i-- > 0;) {
/*  880 */         if ((set[i] != TObjectHash.FREE) && (set[i] != TObjectHash.REMOVED) && (Arrays.binarySearch(array, values[i]) < 0))
/*      */         {
/*      */ 
/*  883 */           TObjectDoubleCustomHashMap.this.removeAt(i);
/*  884 */           changed = true;
/*      */         }
/*      */       }
/*  887 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> collection)
/*      */     {
/*  892 */       boolean changed = false;
/*  893 */       for (Object element : collection) {
/*  894 */         if ((element instanceof Double)) {
/*  895 */           double c = ((Double)element).doubleValue();
/*  896 */           if (remove(c)) {
/*  897 */             changed = true;
/*      */           }
/*      */         }
/*      */       }
/*  901 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean removeAll(TDoubleCollection collection)
/*      */     {
/*  906 */       if (this == collection) {
/*  907 */         clear();
/*  908 */         return true;
/*      */       }
/*  910 */       boolean changed = false;
/*  911 */       TDoubleIterator iter = collection.iterator();
/*  912 */       while (iter.hasNext()) {
/*  913 */         double element = iter.next();
/*  914 */         if (remove(element)) {
/*  915 */           changed = true;
/*      */         }
/*      */       }
/*  918 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean removeAll(double[] array)
/*      */     {
/*  923 */       boolean changed = false;
/*  924 */       for (int i = array.length; i-- > 0;) {
/*  925 */         if (remove(array[i])) {
/*  926 */           changed = true;
/*      */         }
/*      */       }
/*  929 */       return changed;
/*      */     }
/*      */     
/*      */     public void clear()
/*      */     {
/*  934 */       TObjectDoubleCustomHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public boolean forEach(TDoubleProcedure procedure)
/*      */     {
/*  939 */       return TObjectDoubleCustomHashMap.this.forEachValue(procedure);
/*      */     }
/*      */     
/*      */ 
/*      */     public String toString()
/*      */     {
/*  945 */       final StringBuilder buf = new StringBuilder("{");
/*  946 */       TObjectDoubleCustomHashMap.this.forEachValue(new TDoubleProcedure() {
/*  947 */         private boolean first = true;
/*      */         
/*      */         public boolean execute(double value) {
/*  950 */           if (this.first) {
/*  951 */             this.first = false;
/*      */           } else {
/*  953 */             buf.append(", ");
/*      */           }
/*      */           
/*  956 */           buf.append(value);
/*  957 */           return true;
/*      */         }
/*  959 */       });
/*  960 */       buf.append("}");
/*  961 */       return buf.toString();
/*      */     }
/*      */     
/*      */     class TObjectDoubleValueHashIterator
/*      */       implements TDoubleIterator
/*      */     {
/*  967 */       protected THash _hash = TObjectDoubleCustomHashMap.this;
/*      */       
/*      */ 
/*      */ 
/*      */       protected int _expectedSize;
/*      */       
/*      */ 
/*      */       protected int _index;
/*      */       
/*      */ 
/*      */ 
/*      */       TObjectDoubleValueHashIterator()
/*      */       {
/*  980 */         this._expectedSize = this._hash.size();
/*  981 */         this._index = this._hash.capacity();
/*      */       }
/*      */       
/*      */       public boolean hasNext()
/*      */       {
/*  986 */         return nextIndex() >= 0;
/*      */       }
/*      */       
/*      */       public double next()
/*      */       {
/*  991 */         moveToNextIndex();
/*  992 */         return TObjectDoubleCustomHashMap.this._values[this._index];
/*      */       }
/*      */       
/*      */       public void remove()
/*      */       {
/*  997 */         if (this._expectedSize != this._hash.size()) {
/*  998 */           throw new ConcurrentModificationException();
/*      */         }
/*      */         
/*      */ 
/*      */         try
/*      */         {
/* 1004 */           this._hash.tempDisableAutoCompaction();
/* 1005 */           TObjectDoubleCustomHashMap.this.removeAt(this._index);
/*      */         }
/*      */         finally {
/* 1008 */           this._hash.reenableAutoCompaction(false);
/*      */         }
/*      */         
/* 1011 */         this._expectedSize -= 1;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       protected final void moveToNextIndex()
/*      */       {
/* 1021 */         if ((this._index = nextIndex()) < 0) {
/* 1022 */           throw new NoSuchElementException();
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       protected final int nextIndex()
/*      */       {
/* 1037 */         if (this._expectedSize != this._hash.size()) {
/* 1038 */           throw new ConcurrentModificationException();
/*      */         }
/*      */         
/* 1041 */         Object[] set = TObjectDoubleCustomHashMap.this._set;
/* 1042 */         int i = this._index;
/* 1043 */         while ((i-- > 0) && ((set[i] == TCustomObjectHash.FREE) || (set[i] == TCustomObjectHash.REMOVED))) {}
/*      */         
/*      */ 
/*      */ 
/* 1047 */         return i;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   class TObjectDoubleHashIterator<K>
/*      */     extends TObjectHashIterator<K>
/*      */     implements TObjectDoubleIterator<K>
/*      */   {
/*      */     private final TObjectDoubleCustomHashMap<K> _map;
/*      */     
/*      */     public TObjectDoubleHashIterator()
/*      */     {
/* 1060 */       super();
/* 1061 */       this._map = map;
/*      */     }
/*      */     
/*      */     public void advance()
/*      */     {
/* 1066 */       moveToNextIndex();
/*      */     }
/*      */     
/*      */ 
/*      */     public K key()
/*      */     {
/* 1072 */       return (K)this._map._set[this._index];
/*      */     }
/*      */     
/*      */     public double value()
/*      */     {
/* 1077 */       return this._map._values[this._index];
/*      */     }
/*      */     
/*      */     public double setValue(double val)
/*      */     {
/* 1082 */       double old = value();
/* 1083 */       this._map._values[this._index] = val;
/* 1084 */       return old;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeExternal(ObjectOutput out)
/*      */     throws IOException
/*      */   {
/* 1093 */     out.writeByte(0);
/*      */     
/*      */ 
/* 1096 */     super.writeExternal(out);
/*      */     
/*      */ 
/* 1099 */     out.writeObject(this.strategy);
/*      */     
/*      */ 
/* 1102 */     out.writeDouble(this.no_entry_value);
/*      */     
/*      */ 
/* 1105 */     out.writeInt(this._size);
/*      */     
/*      */ 
/* 1108 */     for (int i = this._set.length; i-- > 0;) {
/* 1109 */       if ((this._set[i] != REMOVED) && (this._set[i] != FREE)) {
/* 1110 */         out.writeObject(this._set[i]);
/* 1111 */         out.writeDouble(this._values[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void readExternal(ObjectInput in)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1121 */     in.readByte();
/*      */     
/*      */ 
/* 1124 */     super.readExternal(in);
/*      */     
/*      */ 
/* 1127 */     this.strategy = ((HashingStrategy)in.readObject());
/*      */     
/*      */ 
/* 1130 */     this.no_entry_value = in.readDouble();
/*      */     
/*      */ 
/* 1133 */     int size = in.readInt();
/* 1134 */     setUp(size);
/*      */     
/*      */ 
/* 1137 */     while (size-- > 0)
/*      */     {
/* 1139 */       K key = in.readObject();
/* 1140 */       double val = in.readDouble();
/* 1141 */       put(key, val);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1148 */     final StringBuilder buf = new StringBuilder("{");
/* 1149 */     forEachEntry(new TObjectDoubleProcedure() {
/* 1150 */       private boolean first = true;
/*      */       
/* 1152 */       public boolean execute(K key, double value) { if (this.first) this.first = false; else {
/* 1153 */           buf.append(",");
/*      */         }
/* 1155 */         buf.append(key).append("=").append(value);
/* 1156 */         return true;
/*      */       }
/* 1158 */     });
/* 1159 */     buf.append("}");
/* 1160 */     return buf.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\map\custom_hash\TObjectDoubleCustomHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */