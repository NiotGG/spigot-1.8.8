/*      */ package gnu.trove.map.hash;
/*      */ 
/*      */ import gnu.trove.TShortCollection;
/*      */ import gnu.trove.function.TShortFunction;
/*      */ import gnu.trove.impl.Constants;
/*      */ import gnu.trove.impl.HashFunctions;
/*      */ import gnu.trove.impl.hash.THash;
/*      */ import gnu.trove.impl.hash.TObjectHash;
/*      */ import gnu.trove.iterator.TObjectShortIterator;
/*      */ import gnu.trove.iterator.TShortIterator;
/*      */ import gnu.trove.iterator.hash.TObjectHashIterator;
/*      */ import gnu.trove.map.TObjectShortMap;
/*      */ import gnu.trove.procedure.TObjectProcedure;
/*      */ import gnu.trove.procedure.TObjectShortProcedure;
/*      */ import gnu.trove.procedure.TShortProcedure;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TObjectShortHashMap<K>
/*      */   extends TObjectHash<K>
/*      */   implements TObjectShortMap<K>, Externalizable
/*      */ {
/*      */   static final long serialVersionUID = 1L;
/*   61 */   private final TObjectShortProcedure<K> PUT_ALL_PROC = new TObjectShortProcedure() {
/*      */     public boolean execute(K key, short value) {
/*   63 */       TObjectShortHashMap.this.put(key, value);
/*   64 */       return true;
/*      */     }
/*      */   };
/*      */   
/*      */ 
/*      */ 
/*      */   protected transient short[] _values;
/*      */   
/*      */ 
/*      */ 
/*      */   protected short no_entry_value;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public TObjectShortHashMap()
/*      */   {
/*   81 */     this.no_entry_value = Constants.DEFAULT_SHORT_NO_ENTRY_VALUE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TObjectShortHashMap(int initialCapacity)
/*      */   {
/*   93 */     super(initialCapacity);
/*   94 */     this.no_entry_value = Constants.DEFAULT_SHORT_NO_ENTRY_VALUE;
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
/*      */   public TObjectShortHashMap(int initialCapacity, float loadFactor)
/*      */   {
/*  107 */     super(initialCapacity, loadFactor);
/*  108 */     this.no_entry_value = Constants.DEFAULT_SHORT_NO_ENTRY_VALUE;
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
/*      */   public TObjectShortHashMap(int initialCapacity, float loadFactor, short noEntryValue)
/*      */   {
/*  122 */     super(initialCapacity, loadFactor);
/*  123 */     this.no_entry_value = noEntryValue;
/*      */     
/*  125 */     if (this.no_entry_value != 0) {
/*  126 */       Arrays.fill(this._values, this.no_entry_value);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TObjectShortHashMap(TObjectShortMap<? extends K> map)
/*      */   {
/*  138 */     this(map.size(), 0.5F, map.getNoEntryValue());
/*  139 */     if ((map instanceof TObjectShortHashMap)) {
/*  140 */       TObjectShortHashMap hashmap = (TObjectShortHashMap)map;
/*  141 */       this._loadFactor = hashmap._loadFactor;
/*  142 */       this.no_entry_value = hashmap.no_entry_value;
/*      */       
/*  144 */       if (this.no_entry_value != 0) {
/*  145 */         Arrays.fill(this._values, this.no_entry_value);
/*      */       }
/*  147 */       setUp((int)Math.ceil(10.0F / this._loadFactor));
/*      */     }
/*  149 */     putAll(map);
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
/*  163 */     int capacity = super.setUp(initialCapacity);
/*  164 */     this._values = new short[capacity];
/*  165 */     return capacity;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void rehash(int newCapacity)
/*      */   {
/*  175 */     int oldCapacity = this._set.length;
/*      */     
/*      */ 
/*  178 */     K[] oldKeys = (Object[])this._set;
/*  179 */     short[] oldVals = this._values;
/*      */     
/*  181 */     this._set = new Object[newCapacity];
/*  182 */     Arrays.fill(this._set, FREE);
/*  183 */     this._values = new short[newCapacity];
/*  184 */     Arrays.fill(this._values, this.no_entry_value);
/*      */     
/*  186 */     for (int i = oldCapacity; i-- > 0;) {
/*  187 */       if ((oldKeys[i] != FREE) && (oldKeys[i] != REMOVED)) {
/*  188 */         K o = oldKeys[i];
/*  189 */         int index = insertKey(o);
/*  190 */         if (index < 0) {
/*  191 */           throwObjectContractViolation(this._set[(-index - 1)], o);
/*      */         }
/*  193 */         this._set[index] = o;
/*  194 */         this._values[index] = oldVals[i];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public short getNoEntryValue()
/*      */   {
/*  204 */     return this.no_entry_value;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean containsKey(Object key)
/*      */   {
/*  210 */     return contains(key);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean containsValue(short val)
/*      */   {
/*  216 */     Object[] keys = this._set;
/*  217 */     short[] vals = this._values;
/*      */     
/*  219 */     for (int i = vals.length; i-- > 0;) {
/*  220 */       if ((keys[i] != FREE) && (keys[i] != REMOVED) && (val == vals[i])) {
/*  221 */         return true;
/*      */       }
/*      */     }
/*  224 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public short get(Object key)
/*      */   {
/*  230 */     int index = index(key);
/*  231 */     return index < 0 ? this.no_entry_value : this._values[index];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public short put(K key, short value)
/*      */   {
/*  239 */     int index = insertKey(key);
/*  240 */     return doPut(value, index);
/*      */   }
/*      */   
/*      */ 
/*      */   public short putIfAbsent(K key, short value)
/*      */   {
/*  246 */     int index = insertKey(key);
/*  247 */     if (index < 0)
/*  248 */       return this._values[(-index - 1)];
/*  249 */     return doPut(value, index);
/*      */   }
/*      */   
/*      */   private short doPut(short value, int index)
/*      */   {
/*  254 */     short previous = this.no_entry_value;
/*  255 */     boolean isNewMapping = true;
/*  256 */     if (index < 0) {
/*  257 */       index = -index - 1;
/*  258 */       previous = this._values[index];
/*  259 */       isNewMapping = false;
/*      */     }
/*      */     
/*  262 */     this._values[index] = value;
/*      */     
/*  264 */     if (isNewMapping) {
/*  265 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*  267 */     return previous;
/*      */   }
/*      */   
/*      */ 
/*      */   public short remove(Object key)
/*      */   {
/*  273 */     short prev = this.no_entry_value;
/*  274 */     int index = index(key);
/*  275 */     if (index >= 0) {
/*  276 */       prev = this._values[index];
/*  277 */       removeAt(index);
/*      */     }
/*  279 */     return prev;
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
/*  291 */     this._values[index] = this.no_entry_value;
/*  292 */     super.removeAt(index);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putAll(Map<? extends K, ? extends Short> map)
/*      */   {
/*  300 */     Set<? extends Map.Entry<? extends K, ? extends Short>> set = map.entrySet();
/*  301 */     for (Map.Entry<? extends K, ? extends Short> entry : set) {
/*  302 */       put(entry.getKey(), ((Short)entry.getValue()).shortValue());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void putAll(TObjectShortMap<? extends K> map)
/*      */   {
/*  309 */     map.forEachEntry(this.PUT_ALL_PROC);
/*      */   }
/*      */   
/*      */ 
/*      */   public void clear()
/*      */   {
/*  315 */     super.clear();
/*  316 */     Arrays.fill(this._set, 0, this._set.length, FREE);
/*  317 */     Arrays.fill(this._values, 0, this._values.length, this.no_entry_value);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<K> keySet()
/*      */   {
/*  325 */     return new KeyView();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Object[] keys()
/*      */   {
/*  332 */     K[] keys = (Object[])new Object[size()];
/*  333 */     Object[] k = this._set;
/*      */     
/*  335 */     int i = k.length; for (int j = 0; i-- > 0;) {
/*  336 */       if ((k[i] != FREE) && (k[i] != REMOVED))
/*      */       {
/*  338 */         keys[(j++)] = k[i];
/*      */       }
/*      */     }
/*  341 */     return keys;
/*      */   }
/*      */   
/*      */ 
/*      */   public K[] keys(K[] a)
/*      */   {
/*  347 */     int size = size();
/*  348 */     if (a.length < size)
/*      */     {
/*  350 */       a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
/*      */     }
/*      */     
/*      */ 
/*  354 */     Object[] k = this._set;
/*      */     
/*  356 */     int i = k.length; for (int j = 0; i-- > 0;) {
/*  357 */       if ((k[i] != FREE) && (k[i] != REMOVED))
/*      */       {
/*  359 */         a[(j++)] = k[i];
/*      */       }
/*      */     }
/*  362 */     return a;
/*      */   }
/*      */   
/*      */ 
/*      */   public TShortCollection valueCollection()
/*      */   {
/*  368 */     return new TShortValueCollection();
/*      */   }
/*      */   
/*      */ 
/*      */   public short[] values()
/*      */   {
/*  374 */     short[] vals = new short[size()];
/*  375 */     short[] v = this._values;
/*  376 */     Object[] keys = this._set;
/*      */     
/*  378 */     int i = v.length; for (int j = 0; i-- > 0;) {
/*  379 */       if ((keys[i] != FREE) && (keys[i] != REMOVED)) {
/*  380 */         vals[(j++)] = v[i];
/*      */       }
/*      */     }
/*  383 */     return vals;
/*      */   }
/*      */   
/*      */ 
/*      */   public short[] values(short[] array)
/*      */   {
/*  389 */     int size = size();
/*  390 */     if (array.length < size) {
/*  391 */       array = new short[size];
/*      */     }
/*      */     
/*  394 */     short[] v = this._values;
/*  395 */     Object[] keys = this._set;
/*      */     
/*  397 */     int i = v.length; for (int j = 0; i-- > 0;) {
/*  398 */       if ((keys[i] != FREE) && (keys[i] != REMOVED)) {
/*  399 */         array[(j++)] = v[i];
/*      */       }
/*      */     }
/*  402 */     if (array.length > size) {
/*  403 */       array[size] = this.no_entry_value;
/*      */     }
/*  405 */     return array;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public TObjectShortIterator<K> iterator()
/*      */   {
/*  413 */     return new TObjectShortHashIterator(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean increment(K key)
/*      */   {
/*  421 */     return adjustValue(key, (short)1);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean adjustValue(K key, short amount)
/*      */   {
/*  427 */     int index = index(key);
/*  428 */     if (index < 0) {
/*  429 */       return false;
/*      */     }
/*  431 */     int tmp17_16 = index; short[] tmp17_13 = this._values;tmp17_13[tmp17_16] = ((short)(tmp17_13[tmp17_16] + amount));
/*  432 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public short adjustOrPutValue(K key, short adjust_amount, short put_amount)
/*      */   {
/*  441 */     int index = insertKey(key);
/*      */     boolean isNewMapping;
/*      */     short newValue;
/*  444 */     boolean isNewMapping; if (index < 0) {
/*  445 */       index = -index - 1; int 
/*  446 */         tmp25_23 = index; short[] tmp25_20 = this._values;short newValue = tmp25_20[tmp25_23] = (short)(tmp25_20[tmp25_23] + adjust_amount);
/*  447 */       isNewMapping = false;
/*      */     } else {
/*  449 */       newValue = this._values[index] = put_amount;
/*  450 */       isNewMapping = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  455 */     if (isNewMapping) {
/*  456 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*      */     
/*  459 */     return newValue;
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
/*  471 */     return forEach(procedure);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean forEachValue(TShortProcedure procedure)
/*      */   {
/*  483 */     Object[] keys = this._set;
/*  484 */     short[] values = this._values;
/*  485 */     for (int i = values.length; i-- > 0;) {
/*  486 */       if ((keys[i] != FREE) && (keys[i] != REMOVED) && (!procedure.execute(values[i])))
/*      */       {
/*  488 */         return false;
/*      */       }
/*      */     }
/*  491 */     return true;
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
/*      */   public boolean forEachEntry(TObjectShortProcedure<? super K> procedure)
/*      */   {
/*  505 */     Object[] keys = this._set;
/*  506 */     short[] values = this._values;
/*  507 */     for (int i = keys.length; i-- > 0;) {
/*  508 */       if ((keys[i] != FREE) && (keys[i] != REMOVED) && (!procedure.execute(keys[i], values[i])))
/*      */       {
/*      */ 
/*  511 */         return false;
/*      */       }
/*      */     }
/*  514 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean retainEntries(TObjectShortProcedure<? super K> procedure)
/*      */   {
/*  526 */     boolean modified = false;
/*      */     
/*  528 */     K[] keys = (Object[])this._set;
/*  529 */     short[] values = this._values;
/*      */     
/*      */ 
/*  532 */     tempDisableAutoCompaction();
/*      */     try {
/*  534 */       for (i = keys.length; i-- > 0;) {
/*  535 */         if ((keys[i] != FREE) && (keys[i] != REMOVED) && (!procedure.execute(keys[i], values[i])))
/*      */         {
/*      */ 
/*  538 */           removeAt(i);
/*  539 */           modified = true;
/*      */         }
/*      */       }
/*      */     } finally {
/*      */       int i;
/*  544 */       reenableAutoCompaction(true);
/*      */     }
/*      */     
/*  547 */     return modified;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void transformValues(TShortFunction function)
/*      */   {
/*  557 */     Object[] keys = this._set;
/*  558 */     short[] values = this._values;
/*  559 */     for (int i = values.length; i-- > 0;) {
/*  560 */       if ((keys[i] != null) && (keys[i] != REMOVED)) {
/*  561 */         values[i] = function.execute(values[i]);
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
/*  577 */     if (!(other instanceof TObjectShortMap)) {
/*  578 */       return false;
/*      */     }
/*  580 */     TObjectShortMap that = (TObjectShortMap)other;
/*  581 */     if (that.size() != size()) {
/*  582 */       return false;
/*      */     }
/*      */     try {
/*  585 */       TObjectShortIterator iter = iterator();
/*  586 */       while (iter.hasNext()) {
/*  587 */         iter.advance();
/*  588 */         Object key = iter.key();
/*  589 */         short value = iter.value();
/*  590 */         if (value == this.no_entry_value) {
/*  591 */           if ((that.get(key) != that.getNoEntryValue()) || (!that.containsKey(key)))
/*      */           {
/*      */ 
/*  594 */             return false;
/*      */           }
/*      */         }
/*  597 */         else if (value != that.get(key)) {
/*  598 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (ClassCastException ex) {}
/*      */     
/*      */ 
/*  605 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  611 */     int hashcode = 0;
/*  612 */     Object[] keys = this._set;
/*  613 */     short[] values = this._values;
/*  614 */     for (int i = values.length; i-- > 0;) {
/*  615 */       if ((keys[i] != FREE) && (keys[i] != REMOVED)) {
/*  616 */         hashcode += (HashFunctions.hash(values[i]) ^ (keys[i] == null ? 0 : keys[i].hashCode()));
/*      */       }
/*      */     }
/*      */     
/*  620 */     return hashcode;
/*      */   }
/*      */   
/*      */   protected class KeyView extends TObjectShortHashMap<K>.MapBackedView<K> {
/*      */     protected KeyView() {
/*  625 */       super(null);
/*      */     }
/*      */     
/*      */     public Iterator<K> iterator() {
/*  629 */       return new TObjectHashIterator(TObjectShortHashMap.this);
/*      */     }
/*      */     
/*      */     public boolean removeElement(K key) {
/*  633 */       return TObjectShortHashMap.this.no_entry_value != TObjectShortHashMap.this.remove(key);
/*      */     }
/*      */     
/*      */     public boolean containsElement(K key) {
/*  637 */       return TObjectShortHashMap.this.contains(key);
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
/*  651 */       return containsElement(key);
/*      */     }
/*      */     
/*      */     public boolean remove(Object o)
/*      */     {
/*  656 */       return removeElement(o);
/*      */     }
/*      */     
/*      */     public void clear() {
/*  660 */       TObjectShortHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public boolean add(E obj) {
/*  664 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public int size() {
/*  668 */       return TObjectShortHashMap.this.size();
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/*  672 */       Object[] result = new Object[size()];
/*  673 */       Iterator<E> e = iterator();
/*  674 */       for (int i = 0; e.hasNext(); i++) {
/*  675 */         result[i] = e.next();
/*      */       }
/*  677 */       return result;
/*      */     }
/*      */     
/*      */     public <T> T[] toArray(T[] a) {
/*  681 */       int size = size();
/*  682 */       if (a.length < size)
/*      */       {
/*  684 */         a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
/*      */       }
/*      */       
/*      */ 
/*  688 */       Iterator<E> it = iterator();
/*  689 */       Object[] result = a;
/*  690 */       for (int i = 0; i < size; i++) {
/*  691 */         result[i] = it.next();
/*      */       }
/*      */       
/*  694 */       if (a.length > size) {
/*  695 */         a[size] = null;
/*      */       }
/*      */       
/*  698 */       return a;
/*      */     }
/*      */     
/*      */     public boolean isEmpty() {
/*  702 */       return TObjectShortHashMap.this.isEmpty();
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends E> collection) {
/*  706 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean retainAll(Collection<?> collection)
/*      */     {
/*  711 */       boolean changed = false;
/*  712 */       Iterator<E> i = iterator();
/*  713 */       while (i.hasNext()) {
/*  714 */         if (!collection.contains(i.next())) {
/*  715 */           i.remove();
/*  716 */           changed = true;
/*      */         }
/*      */       }
/*  719 */       return changed;
/*      */     }
/*      */   }
/*      */   
/*      */   class TShortValueCollection implements TShortCollection
/*      */   {
/*      */     TShortValueCollection() {}
/*      */     
/*      */     public TShortIterator iterator() {
/*  728 */       return new TObjectShortValueHashIterator();
/*      */     }
/*      */     
/*      */     public short getNoEntryValue()
/*      */     {
/*  733 */       return TObjectShortHashMap.this.no_entry_value;
/*      */     }
/*      */     
/*      */     public int size()
/*      */     {
/*  738 */       return TObjectShortHashMap.this._size;
/*      */     }
/*      */     
/*      */     public boolean isEmpty()
/*      */     {
/*  743 */       return 0 == TObjectShortHashMap.this._size;
/*      */     }
/*      */     
/*      */     public boolean contains(short entry)
/*      */     {
/*  748 */       return TObjectShortHashMap.this.containsValue(entry);
/*      */     }
/*      */     
/*      */     public short[] toArray()
/*      */     {
/*  753 */       return TObjectShortHashMap.this.values();
/*      */     }
/*      */     
/*      */     public short[] toArray(short[] dest)
/*      */     {
/*  758 */       return TObjectShortHashMap.this.values(dest);
/*      */     }
/*      */     
/*      */     public boolean add(short entry) {
/*  762 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean remove(short entry)
/*      */     {
/*  767 */       short[] values = TObjectShortHashMap.this._values;
/*  768 */       Object[] set = TObjectShortHashMap.this._set;
/*      */       
/*  770 */       for (int i = values.length; i-- > 0;) {
/*  771 */         if ((set[i] != TObjectHash.FREE) && (set[i] != TObjectHash.REMOVED) && (entry == values[i])) {
/*  772 */           TObjectShortHashMap.this.removeAt(i);
/*  773 */           return true;
/*      */         }
/*      */       }
/*  776 */       return false;
/*      */     }
/*      */     
/*      */     public boolean containsAll(Collection<?> collection)
/*      */     {
/*  781 */       for (Object element : collection) {
/*  782 */         if ((element instanceof Short)) {
/*  783 */           short ele = ((Short)element).shortValue();
/*  784 */           if (!TObjectShortHashMap.this.containsValue(ele)) {
/*  785 */             return false;
/*      */           }
/*      */         } else {
/*  788 */           return false;
/*      */         }
/*      */       }
/*  791 */       return true;
/*      */     }
/*      */     
/*      */     public boolean containsAll(TShortCollection collection)
/*      */     {
/*  796 */       TShortIterator iter = collection.iterator();
/*  797 */       while (iter.hasNext()) {
/*  798 */         if (!TObjectShortHashMap.this.containsValue(iter.next())) {
/*  799 */           return false;
/*      */         }
/*      */       }
/*  802 */       return true;
/*      */     }
/*      */     
/*      */     public boolean containsAll(short[] array)
/*      */     {
/*  807 */       for (short element : array) {
/*  808 */         if (!TObjectShortHashMap.this.containsValue(element)) {
/*  809 */           return false;
/*      */         }
/*      */       }
/*  812 */       return true;
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Short> collection)
/*      */     {
/*  817 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(TShortCollection collection)
/*      */     {
/*  822 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */     public boolean addAll(short[] array)
/*      */     {
/*  827 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean retainAll(Collection<?> collection)
/*      */     {
/*  833 */       boolean modified = false;
/*  834 */       TShortIterator iter = iterator();
/*  835 */       while (iter.hasNext()) {
/*  836 */         if (!collection.contains(Short.valueOf(iter.next()))) {
/*  837 */           iter.remove();
/*  838 */           modified = true;
/*      */         }
/*      */       }
/*  841 */       return modified;
/*      */     }
/*      */     
/*      */     public boolean retainAll(TShortCollection collection)
/*      */     {
/*  846 */       if (this == collection) {
/*  847 */         return false;
/*      */       }
/*  849 */       boolean modified = false;
/*  850 */       TShortIterator iter = iterator();
/*  851 */       while (iter.hasNext()) {
/*  852 */         if (!collection.contains(iter.next())) {
/*  853 */           iter.remove();
/*  854 */           modified = true;
/*      */         }
/*      */       }
/*  857 */       return modified;
/*      */     }
/*      */     
/*      */     public boolean retainAll(short[] array)
/*      */     {
/*  862 */       boolean changed = false;
/*  863 */       Arrays.sort(array);
/*  864 */       short[] values = TObjectShortHashMap.this._values;
/*      */       
/*  866 */       Object[] set = TObjectShortHashMap.this._set;
/*  867 */       for (int i = set.length; i-- > 0;) {
/*  868 */         if ((set[i] != TObjectHash.FREE) && (set[i] != TObjectHash.REMOVED) && (Arrays.binarySearch(array, values[i]) < 0))
/*      */         {
/*      */ 
/*  871 */           TObjectShortHashMap.this.removeAt(i);
/*  872 */           changed = true;
/*      */         }
/*      */       }
/*  875 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean removeAll(Collection<?> collection)
/*      */     {
/*  880 */       boolean changed = false;
/*  881 */       for (Object element : collection) {
/*  882 */         if ((element instanceof Short)) {
/*  883 */           short c = ((Short)element).shortValue();
/*  884 */           if (remove(c)) {
/*  885 */             changed = true;
/*      */           }
/*      */         }
/*      */       }
/*  889 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean removeAll(TShortCollection collection)
/*      */     {
/*  894 */       if (this == collection) {
/*  895 */         clear();
/*  896 */         return true;
/*      */       }
/*  898 */       boolean changed = false;
/*  899 */       TShortIterator iter = collection.iterator();
/*  900 */       while (iter.hasNext()) {
/*  901 */         short element = iter.next();
/*  902 */         if (remove(element)) {
/*  903 */           changed = true;
/*      */         }
/*      */       }
/*  906 */       return changed;
/*      */     }
/*      */     
/*      */     public boolean removeAll(short[] array)
/*      */     {
/*  911 */       boolean changed = false;
/*  912 */       for (int i = array.length; i-- > 0;) {
/*  913 */         if (remove(array[i])) {
/*  914 */           changed = true;
/*      */         }
/*      */       }
/*  917 */       return changed;
/*      */     }
/*      */     
/*      */     public void clear()
/*      */     {
/*  922 */       TObjectShortHashMap.this.clear();
/*      */     }
/*      */     
/*      */     public boolean forEach(TShortProcedure procedure)
/*      */     {
/*  927 */       return TObjectShortHashMap.this.forEachValue(procedure);
/*      */     }
/*      */     
/*      */ 
/*      */     public String toString()
/*      */     {
/*  933 */       final StringBuilder buf = new StringBuilder("{");
/*  934 */       TObjectShortHashMap.this.forEachValue(new TShortProcedure() {
/*  935 */         private boolean first = true;
/*      */         
/*      */         public boolean execute(short value) {
/*  938 */           if (this.first) {
/*  939 */             this.first = false;
/*      */           } else {
/*  941 */             buf.append(", ");
/*      */           }
/*      */           
/*  944 */           buf.append(value);
/*  945 */           return true;
/*      */         }
/*  947 */       });
/*  948 */       buf.append("}");
/*  949 */       return buf.toString();
/*      */     }
/*      */     
/*      */     class TObjectShortValueHashIterator
/*      */       implements TShortIterator
/*      */     {
/*  955 */       protected THash _hash = TObjectShortHashMap.this;
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
/*      */       TObjectShortValueHashIterator()
/*      */       {
/*  968 */         this._expectedSize = this._hash.size();
/*  969 */         this._index = this._hash.capacity();
/*      */       }
/*      */       
/*      */       public boolean hasNext()
/*      */       {
/*  974 */         return nextIndex() >= 0;
/*      */       }
/*      */       
/*      */       public short next()
/*      */       {
/*  979 */         moveToNextIndex();
/*  980 */         return TObjectShortHashMap.this._values[this._index];
/*      */       }
/*      */       
/*      */       public void remove()
/*      */       {
/*  985 */         if (this._expectedSize != this._hash.size()) {
/*  986 */           throw new ConcurrentModificationException();
/*      */         }
/*      */         
/*      */ 
/*      */         try
/*      */         {
/*  992 */           this._hash.tempDisableAutoCompaction();
/*  993 */           TObjectShortHashMap.this.removeAt(this._index);
/*      */         }
/*      */         finally {
/*  996 */           this._hash.reenableAutoCompaction(false);
/*      */         }
/*      */         
/*  999 */         this._expectedSize -= 1;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       protected final void moveToNextIndex()
/*      */       {
/* 1009 */         if ((this._index = nextIndex()) < 0) {
/* 1010 */           throw new NoSuchElementException();
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
/* 1025 */         if (this._expectedSize != this._hash.size()) {
/* 1026 */           throw new ConcurrentModificationException();
/*      */         }
/*      */         
/* 1029 */         Object[] set = TObjectShortHashMap.this._set;
/* 1030 */         int i = this._index;
/* 1031 */         while ((i-- > 0) && ((set[i] == TObjectHash.FREE) || (set[i] == TObjectHash.REMOVED))) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1036 */         return i;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   class TObjectShortHashIterator<K>
/*      */     extends TObjectHashIterator<K>
/*      */     implements TObjectShortIterator<K>
/*      */   {
/*      */     private final TObjectShortHashMap<K> _map;
/*      */     
/*      */     public TObjectShortHashIterator()
/*      */     {
/* 1049 */       super();
/* 1050 */       this._map = map;
/*      */     }
/*      */     
/*      */     public void advance()
/*      */     {
/* 1055 */       moveToNextIndex();
/*      */     }
/*      */     
/*      */ 
/*      */     public K key()
/*      */     {
/* 1061 */       return (K)this._map._set[this._index];
/*      */     }
/*      */     
/*      */     public short value()
/*      */     {
/* 1066 */       return this._map._values[this._index];
/*      */     }
/*      */     
/*      */     public short setValue(short val)
/*      */     {
/* 1071 */       short old = value();
/* 1072 */       this._map._values[this._index] = val;
/* 1073 */       return old;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void writeExternal(ObjectOutput out)
/*      */     throws IOException
/*      */   {
/* 1082 */     out.writeByte(0);
/*      */     
/*      */ 
/* 1085 */     super.writeExternal(out);
/*      */     
/*      */ 
/* 1088 */     out.writeShort(this.no_entry_value);
/*      */     
/*      */ 
/* 1091 */     out.writeInt(this._size);
/*      */     
/*      */ 
/* 1094 */     for (int i = this._set.length; i-- > 0;) {
/* 1095 */       if ((this._set[i] != REMOVED) && (this._set[i] != FREE)) {
/* 1096 */         out.writeObject(this._set[i]);
/* 1097 */         out.writeShort(this._values[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void readExternal(ObjectInput in)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1107 */     in.readByte();
/*      */     
/*      */ 
/* 1110 */     super.readExternal(in);
/*      */     
/*      */ 
/* 1113 */     this.no_entry_value = in.readShort();
/*      */     
/*      */ 
/* 1116 */     int size = in.readInt();
/* 1117 */     setUp(size);
/*      */     
/*      */ 
/* 1120 */     while (size-- > 0)
/*      */     {
/* 1122 */       K key = in.readObject();
/* 1123 */       short val = in.readShort();
/* 1124 */       put(key, val);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1131 */     final StringBuilder buf = new StringBuilder("{");
/* 1132 */     forEachEntry(new TObjectShortProcedure() {
/* 1133 */       private boolean first = true;
/*      */       
/* 1135 */       public boolean execute(K key, short value) { if (this.first) this.first = false; else {
/* 1136 */           buf.append(",");
/*      */         }
/* 1138 */         buf.append(key).append("=").append(value);
/* 1139 */         return true;
/*      */       }
/* 1141 */     });
/* 1142 */     buf.append("}");
/* 1143 */     return buf.toString();
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\map\hash\TObjectShortHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */