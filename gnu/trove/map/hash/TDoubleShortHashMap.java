/*      */ package gnu.trove.map.hash;
/*      */ 
/*      */ import gnu.trove.TDoubleCollection;
/*      */ import gnu.trove.TShortCollection;
/*      */ import gnu.trove.function.TShortFunction;
/*      */ import gnu.trove.impl.HashFunctions;
/*      */ import gnu.trove.impl.hash.TDoubleShortHash;
/*      */ import gnu.trove.impl.hash.THashPrimitiveIterator;
/*      */ import gnu.trove.impl.hash.TPrimitiveHash;
/*      */ import gnu.trove.iterator.TDoubleIterator;
/*      */ import gnu.trove.iterator.TDoubleShortIterator;
/*      */ import gnu.trove.iterator.TShortIterator;
/*      */ import gnu.trove.map.TDoubleShortMap;
/*      */ import gnu.trove.procedure.TDoubleProcedure;
/*      */ import gnu.trove.procedure.TDoubleShortProcedure;
/*      */ import gnu.trove.procedure.TShortProcedure;
/*      */ import gnu.trove.set.TDoubleSet;
/*      */ import java.io.Externalizable;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectOutput;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TDoubleShortHashMap
/*      */   extends TDoubleShortHash
/*      */   implements TDoubleShortMap, Externalizable
/*      */ {
/*      */   static final long serialVersionUID = 1L;
/*      */   protected transient short[] _values;
/*      */   
/*      */   public TDoubleShortHashMap() {}
/*      */   
/*      */   public TDoubleShortHashMap(int initialCapacity)
/*      */   {
/*   73 */     super(initialCapacity);
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
/*      */   public TDoubleShortHashMap(int initialCapacity, float loadFactor)
/*      */   {
/*   86 */     super(initialCapacity, loadFactor);
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
/*      */ 
/*      */ 
/*      */   public TDoubleShortHashMap(int initialCapacity, float loadFactor, double noEntryKey, short noEntryValue)
/*      */   {
/*  104 */     super(initialCapacity, loadFactor, noEntryKey, noEntryValue);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TDoubleShortHashMap(double[] keys, short[] values)
/*      */   {
/*  116 */     super(Math.max(keys.length, values.length));
/*      */     
/*  118 */     int size = Math.min(keys.length, values.length);
/*  119 */     for (int i = 0; i < size; i++) {
/*  120 */       put(keys[i], values[i]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public TDoubleShortHashMap(TDoubleShortMap map)
/*      */   {
/*  132 */     super(map.size());
/*  133 */     if ((map instanceof TDoubleShortHashMap)) {
/*  134 */       TDoubleShortHashMap hashmap = (TDoubleShortHashMap)map;
/*  135 */       this._loadFactor = hashmap._loadFactor;
/*  136 */       this.no_entry_key = hashmap.no_entry_key;
/*  137 */       this.no_entry_value = hashmap.no_entry_value;
/*      */       
/*  139 */       if (this.no_entry_key != 0.0D) {
/*  140 */         Arrays.fill(this._set, this.no_entry_key);
/*      */       }
/*      */       
/*  143 */       if (this.no_entry_value != 0) {
/*  144 */         Arrays.fill(this._values, this.no_entry_value);
/*      */       }
/*  146 */       setUp((int)Math.ceil(10.0F / this._loadFactor));
/*      */     }
/*  148 */     putAll(map);
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
/*      */   protected int setUp(int initialCapacity)
/*      */   {
/*  162 */     int capacity = super.setUp(initialCapacity);
/*  163 */     this._values = new short[capacity];
/*  164 */     return capacity;
/*      */   }
/*      */   
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
/*  177 */     double[] oldKeys = this._set;
/*  178 */     short[] oldVals = this._values;
/*  179 */     byte[] oldStates = this._states;
/*      */     
/*  181 */     this._set = new double[newCapacity];
/*  182 */     this._values = new short[newCapacity];
/*  183 */     this._states = new byte[newCapacity];
/*      */     
/*  185 */     for (int i = oldCapacity; i-- > 0;) {
/*  186 */       if (oldStates[i] == 1) {
/*  187 */         double o = oldKeys[i];
/*  188 */         int index = insertKey(o);
/*  189 */         this._values[index] = oldVals[i];
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public short put(double key, short value)
/*      */   {
/*  197 */     int index = insertKey(key);
/*  198 */     return doPut(key, value, index);
/*      */   }
/*      */   
/*      */ 
/*      */   public short putIfAbsent(double key, short value)
/*      */   {
/*  204 */     int index = insertKey(key);
/*  205 */     if (index < 0)
/*  206 */       return this._values[(-index - 1)];
/*  207 */     return doPut(key, value, index);
/*      */   }
/*      */   
/*      */   private short doPut(double key, short value, int index)
/*      */   {
/*  212 */     short previous = this.no_entry_value;
/*  213 */     boolean isNewMapping = true;
/*  214 */     if (index < 0) {
/*  215 */       index = -index - 1;
/*  216 */       previous = this._values[index];
/*  217 */       isNewMapping = false;
/*      */     }
/*  219 */     this._values[index] = value;
/*      */     
/*  221 */     if (isNewMapping) {
/*  222 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*      */     
/*  225 */     return previous;
/*      */   }
/*      */   
/*      */ 
/*      */   public void putAll(Map<? extends Double, ? extends Short> map)
/*      */   {
/*  231 */     ensureCapacity(map.size());
/*      */     
/*  233 */     for (Map.Entry<? extends Double, ? extends Short> entry : map.entrySet()) {
/*  234 */       put(((Double)entry.getKey()).doubleValue(), ((Short)entry.getValue()).shortValue());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void putAll(TDoubleShortMap map)
/*      */   {
/*  241 */     ensureCapacity(map.size());
/*  242 */     TDoubleShortIterator iter = map.iterator();
/*  243 */     while (iter.hasNext()) {
/*  244 */       iter.advance();
/*  245 */       put(iter.key(), iter.value());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public short get(double key)
/*      */   {
/*  252 */     int index = index(key);
/*  253 */     return index < 0 ? this.no_entry_value : this._values[index];
/*      */   }
/*      */   
/*      */ 
/*      */   public void clear()
/*      */   {
/*  259 */     super.clear();
/*  260 */     Arrays.fill(this._set, 0, this._set.length, this.no_entry_key);
/*  261 */     Arrays.fill(this._values, 0, this._values.length, this.no_entry_value);
/*  262 */     Arrays.fill(this._states, 0, this._states.length, (byte)0);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  268 */     return 0 == this._size;
/*      */   }
/*      */   
/*      */ 
/*      */   public short remove(double key)
/*      */   {
/*  274 */     short prev = this.no_entry_value;
/*  275 */     int index = index(key);
/*  276 */     if (index >= 0) {
/*  277 */       prev = this._values[index];
/*  278 */       removeAt(index);
/*      */     }
/*  280 */     return prev;
/*      */   }
/*      */   
/*      */ 
/*      */   protected void removeAt(int index)
/*      */   {
/*  286 */     this._values[index] = this.no_entry_value;
/*  287 */     super.removeAt(index);
/*      */   }
/*      */   
/*      */ 
/*      */   public TDoubleSet keySet()
/*      */   {
/*  293 */     return new TKeyView();
/*      */   }
/*      */   
/*      */ 
/*      */   public double[] keys()
/*      */   {
/*  299 */     double[] keys = new double[size()];
/*  300 */     double[] k = this._set;
/*  301 */     byte[] states = this._states;
/*      */     
/*  303 */     int i = k.length; for (int j = 0; i-- > 0;) {
/*  304 */       if (states[i] == 1) {
/*  305 */         keys[(j++)] = k[i];
/*      */       }
/*      */     }
/*  308 */     return keys;
/*      */   }
/*      */   
/*      */ 
/*      */   public double[] keys(double[] array)
/*      */   {
/*  314 */     int size = size();
/*  315 */     if (array.length < size) {
/*  316 */       array = new double[size];
/*      */     }
/*      */     
/*  319 */     double[] keys = this._set;
/*  320 */     byte[] states = this._states;
/*      */     
/*  322 */     int i = keys.length; for (int j = 0; i-- > 0;) {
/*  323 */       if (states[i] == 1) {
/*  324 */         array[(j++)] = keys[i];
/*      */       }
/*      */     }
/*  327 */     return array;
/*      */   }
/*      */   
/*      */ 
/*      */   public TShortCollection valueCollection()
/*      */   {
/*  333 */     return new TValueView();
/*      */   }
/*      */   
/*      */ 
/*      */   public short[] values()
/*      */   {
/*  339 */     short[] vals = new short[size()];
/*  340 */     short[] v = this._values;
/*  341 */     byte[] states = this._states;
/*      */     
/*  343 */     int i = v.length; for (int j = 0; i-- > 0;) {
/*  344 */       if (states[i] == 1) {
/*  345 */         vals[(j++)] = v[i];
/*      */       }
/*      */     }
/*  348 */     return vals;
/*      */   }
/*      */   
/*      */ 
/*      */   public short[] values(short[] array)
/*      */   {
/*  354 */     int size = size();
/*  355 */     if (array.length < size) {
/*  356 */       array = new short[size];
/*      */     }
/*      */     
/*  359 */     short[] v = this._values;
/*  360 */     byte[] states = this._states;
/*      */     
/*  362 */     int i = v.length; for (int j = 0; i-- > 0;) {
/*  363 */       if (states[i] == 1) {
/*  364 */         array[(j++)] = v[i];
/*      */       }
/*      */     }
/*  367 */     return array;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean containsValue(short val)
/*      */   {
/*  373 */     byte[] states = this._states;
/*  374 */     short[] vals = this._values;
/*      */     
/*  376 */     for (int i = vals.length; i-- > 0;) {
/*  377 */       if ((states[i] == 1) && (val == vals[i])) {
/*  378 */         return true;
/*      */       }
/*      */     }
/*  381 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean containsKey(double key)
/*      */   {
/*  387 */     return contains(key);
/*      */   }
/*      */   
/*      */ 
/*      */   public TDoubleShortIterator iterator()
/*      */   {
/*  393 */     return new TDoubleShortHashIterator(this);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean forEachKey(TDoubleProcedure procedure)
/*      */   {
/*  399 */     return forEach(procedure);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean forEachValue(TShortProcedure procedure)
/*      */   {
/*  405 */     byte[] states = this._states;
/*  406 */     short[] values = this._values;
/*  407 */     for (int i = values.length; i-- > 0;) {
/*  408 */       if ((states[i] == 1) && (!procedure.execute(values[i]))) {
/*  409 */         return false;
/*      */       }
/*      */     }
/*  412 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean forEachEntry(TDoubleShortProcedure procedure)
/*      */   {
/*  418 */     byte[] states = this._states;
/*  419 */     double[] keys = this._set;
/*  420 */     short[] values = this._values;
/*  421 */     for (int i = keys.length; i-- > 0;) {
/*  422 */       if ((states[i] == 1) && (!procedure.execute(keys[i], values[i]))) {
/*  423 */         return false;
/*      */       }
/*      */     }
/*  426 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public void transformValues(TShortFunction function)
/*      */   {
/*  432 */     byte[] states = this._states;
/*  433 */     short[] values = this._values;
/*  434 */     for (int i = values.length; i-- > 0;) {
/*  435 */       if (states[i] == 1) {
/*  436 */         values[i] = function.execute(values[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean retainEntries(TDoubleShortProcedure procedure)
/*      */   {
/*  444 */     boolean modified = false;
/*  445 */     byte[] states = this._states;
/*  446 */     double[] keys = this._set;
/*  447 */     short[] values = this._values;
/*      */     
/*      */ 
/*      */ 
/*  451 */     tempDisableAutoCompaction();
/*      */     try {
/*  453 */       for (i = keys.length; i-- > 0;) {
/*  454 */         if ((states[i] == 1) && (!procedure.execute(keys[i], values[i]))) {
/*  455 */           removeAt(i);
/*  456 */           modified = true;
/*      */         }
/*      */       }
/*      */     } finally {
/*      */       int i;
/*  461 */       reenableAutoCompaction(true);
/*      */     }
/*      */     
/*  464 */     return modified;
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean increment(double key)
/*      */   {
/*  470 */     return adjustValue(key, (short)1);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean adjustValue(double key, short amount)
/*      */   {
/*  476 */     int index = index(key);
/*  477 */     if (index < 0) {
/*  478 */       return false;
/*      */     }
/*  480 */     int tmp20_18 = index; short[] tmp20_15 = this._values;tmp20_15[tmp20_18] = ((short)(tmp20_15[tmp20_18] + amount));
/*  481 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public short adjustOrPutValue(double key, short adjust_amount, short put_amount)
/*      */   {
/*  488 */     int index = insertKey(key);
/*      */     boolean isNewMapping;
/*      */     short newValue;
/*  491 */     boolean isNewMapping; if (index < 0) {
/*  492 */       index = -index - 1; int 
/*  493 */         tmp25_23 = index; short[] tmp25_20 = this._values;short newValue = tmp25_20[tmp25_23] = (short)(tmp25_20[tmp25_23] + adjust_amount);
/*  494 */       isNewMapping = false;
/*      */     } else {
/*  496 */       newValue = this._values[index] = put_amount;
/*  497 */       isNewMapping = true;
/*      */     }
/*      */     
/*  500 */     byte previousState = this._states[index];
/*      */     
/*  502 */     if (isNewMapping) {
/*  503 */       postInsertHook(this.consumeFreeSlot);
/*      */     }
/*      */     
/*  506 */     return newValue;
/*      */   }
/*      */   
/*      */   protected class TKeyView implements TDoubleSet
/*      */   {
/*      */     protected TKeyView() {}
/*      */     
/*      */     public TDoubleIterator iterator()
/*      */     {
/*  515 */       return new TDoubleShortHashMap.TDoubleShortKeyHashIterator(TDoubleShortHashMap.this, TDoubleShortHashMap.this);
/*      */     }
/*      */     
/*      */ 
/*      */     public double getNoEntryValue()
/*      */     {
/*  521 */       return TDoubleShortHashMap.this.no_entry_key;
/*      */     }
/*      */     
/*      */ 
/*      */     public int size()
/*      */     {
/*  527 */       return TDoubleShortHashMap.this._size;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/*  533 */       return 0 == TDoubleShortHashMap.this._size;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean contains(double entry)
/*      */     {
/*  539 */       return TDoubleShortHashMap.this.contains(entry);
/*      */     }
/*      */     
/*      */ 
/*      */     public double[] toArray()
/*      */     {
/*  545 */       return TDoubleShortHashMap.this.keys();
/*      */     }
/*      */     
/*      */ 
/*      */     public double[] toArray(double[] dest)
/*      */     {
/*  551 */       return TDoubleShortHashMap.this.keys(dest);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean add(double entry)
/*      */     {
/*  561 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean remove(double entry)
/*      */     {
/*  567 */       return TDoubleShortHashMap.this.no_entry_value != TDoubleShortHashMap.this.remove(entry);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean containsAll(Collection<?> collection)
/*      */     {
/*  573 */       for (Object element : collection) {
/*  574 */         if ((element instanceof Double)) {
/*  575 */           double ele = ((Double)element).doubleValue();
/*  576 */           if (!TDoubleShortHashMap.this.containsKey(ele)) {
/*  577 */             return false;
/*      */           }
/*      */         } else {
/*  580 */           return false;
/*      */         }
/*      */       }
/*  583 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean containsAll(TDoubleCollection collection)
/*      */     {
/*  589 */       TDoubleIterator iter = collection.iterator();
/*  590 */       while (iter.hasNext()) {
/*  591 */         if (!TDoubleShortHashMap.this.containsKey(iter.next())) {
/*  592 */           return false;
/*      */         }
/*      */       }
/*  595 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean containsAll(double[] array)
/*      */     {
/*  601 */       for (double element : array) {
/*  602 */         if (!TDoubleShortHashMap.this.contains(element)) {
/*  603 */           return false;
/*      */         }
/*      */       }
/*  606 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean addAll(Collection<? extends Double> collection)
/*      */     {
/*  616 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean addAll(TDoubleCollection collection)
/*      */     {
/*  626 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean addAll(double[] array)
/*      */     {
/*  636 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public boolean retainAll(Collection<?> collection)
/*      */     {
/*  643 */       boolean modified = false;
/*  644 */       TDoubleIterator iter = iterator();
/*  645 */       while (iter.hasNext()) {
/*  646 */         if (!collection.contains(Double.valueOf(iter.next()))) {
/*  647 */           iter.remove();
/*  648 */           modified = true;
/*      */         }
/*      */       }
/*  651 */       return modified;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean retainAll(TDoubleCollection collection)
/*      */     {
/*  657 */       if (this == collection) {
/*  658 */         return false;
/*      */       }
/*  660 */       boolean modified = false;
/*  661 */       TDoubleIterator iter = iterator();
/*  662 */       while (iter.hasNext()) {
/*  663 */         if (!collection.contains(iter.next())) {
/*  664 */           iter.remove();
/*  665 */           modified = true;
/*      */         }
/*      */       }
/*  668 */       return modified;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean retainAll(double[] array)
/*      */     {
/*  674 */       boolean changed = false;
/*  675 */       Arrays.sort(array);
/*  676 */       double[] set = TDoubleShortHashMap.this._set;
/*  677 */       byte[] states = TDoubleShortHashMap.this._states;
/*      */       
/*  679 */       for (int i = set.length; i-- > 0;) {
/*  680 */         if ((states[i] == 1) && (Arrays.binarySearch(array, set[i]) < 0)) {
/*  681 */           TDoubleShortHashMap.this.removeAt(i);
/*  682 */           changed = true;
/*      */         }
/*      */       }
/*  685 */       return changed;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean removeAll(Collection<?> collection)
/*      */     {
/*  691 */       boolean changed = false;
/*  692 */       for (Object element : collection) {
/*  693 */         if ((element instanceof Double)) {
/*  694 */           double c = ((Double)element).doubleValue();
/*  695 */           if (remove(c)) {
/*  696 */             changed = true;
/*      */           }
/*      */         }
/*      */       }
/*  700 */       return changed;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean removeAll(TDoubleCollection collection)
/*      */     {
/*  706 */       if (this == collection) {
/*  707 */         clear();
/*  708 */         return true;
/*      */       }
/*  710 */       boolean changed = false;
/*  711 */       TDoubleIterator iter = collection.iterator();
/*  712 */       while (iter.hasNext()) {
/*  713 */         double element = iter.next();
/*  714 */         if (remove(element)) {
/*  715 */           changed = true;
/*      */         }
/*      */       }
/*  718 */       return changed;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean removeAll(double[] array)
/*      */     {
/*  724 */       boolean changed = false;
/*  725 */       for (int i = array.length; i-- > 0;) {
/*  726 */         if (remove(array[i])) {
/*  727 */           changed = true;
/*      */         }
/*      */       }
/*  730 */       return changed;
/*      */     }
/*      */     
/*      */ 
/*      */     public void clear()
/*      */     {
/*  736 */       TDoubleShortHashMap.this.clear();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean forEach(TDoubleProcedure procedure)
/*      */     {
/*  742 */       return TDoubleShortHashMap.this.forEachKey(procedure);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean equals(Object other)
/*      */     {
/*  748 */       if (!(other instanceof TDoubleSet)) {
/*  749 */         return false;
/*      */       }
/*  751 */       TDoubleSet that = (TDoubleSet)other;
/*  752 */       if (that.size() != size()) {
/*  753 */         return false;
/*      */       }
/*  755 */       for (int i = TDoubleShortHashMap.this._states.length; i-- > 0;) {
/*  756 */         if ((TDoubleShortHashMap.this._states[i] == 1) && 
/*  757 */           (!that.contains(TDoubleShortHashMap.this._set[i]))) {
/*  758 */           return false;
/*      */         }
/*      */       }
/*      */       
/*  762 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */     public int hashCode()
/*      */     {
/*  768 */       int hashcode = 0;
/*  769 */       for (int i = TDoubleShortHashMap.this._states.length; i-- > 0;) {
/*  770 */         if (TDoubleShortHashMap.this._states[i] == 1) {
/*  771 */           hashcode += HashFunctions.hash(TDoubleShortHashMap.this._set[i]);
/*      */         }
/*      */       }
/*  774 */       return hashcode;
/*      */     }
/*      */     
/*      */ 
/*      */     public String toString()
/*      */     {
/*  780 */       final StringBuilder buf = new StringBuilder("{");
/*  781 */       TDoubleShortHashMap.this.forEachKey(new TDoubleProcedure() {
/*  782 */         private boolean first = true;
/*      */         
/*      */         public boolean execute(double key)
/*      */         {
/*  786 */           if (this.first) {
/*  787 */             this.first = false;
/*      */           } else {
/*  789 */             buf.append(", ");
/*      */           }
/*      */           
/*  792 */           buf.append(key);
/*  793 */           return true;
/*      */         }
/*  795 */       });
/*  796 */       buf.append("}");
/*  797 */       return buf.toString();
/*      */     }
/*      */   }
/*      */   
/*      */   protected class TValueView implements TShortCollection
/*      */   {
/*      */     protected TValueView() {}
/*      */     
/*      */     public TShortIterator iterator()
/*      */     {
/*  807 */       return new TDoubleShortHashMap.TDoubleShortValueHashIterator(TDoubleShortHashMap.this, TDoubleShortHashMap.this);
/*      */     }
/*      */     
/*      */ 
/*      */     public short getNoEntryValue()
/*      */     {
/*  813 */       return TDoubleShortHashMap.this.no_entry_value;
/*      */     }
/*      */     
/*      */ 
/*      */     public int size()
/*      */     {
/*  819 */       return TDoubleShortHashMap.this._size;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/*  825 */       return 0 == TDoubleShortHashMap.this._size;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean contains(short entry)
/*      */     {
/*  831 */       return TDoubleShortHashMap.this.containsValue(entry);
/*      */     }
/*      */     
/*      */ 
/*      */     public short[] toArray()
/*      */     {
/*  837 */       return TDoubleShortHashMap.this.values();
/*      */     }
/*      */     
/*      */ 
/*      */     public short[] toArray(short[] dest)
/*      */     {
/*  843 */       return TDoubleShortHashMap.this.values(dest);
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean add(short entry)
/*      */     {
/*  849 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean remove(short entry)
/*      */     {
/*  855 */       short[] values = TDoubleShortHashMap.this._values;
/*  856 */       double[] set = TDoubleShortHashMap.this._set;
/*      */       
/*  858 */       for (int i = values.length; i-- > 0;) {
/*  859 */         if ((set[i] != 0.0D) && (set[i] != 2.0D) && (entry == values[i])) {
/*  860 */           TDoubleShortHashMap.this.removeAt(i);
/*  861 */           return true;
/*      */         }
/*      */       }
/*  864 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean containsAll(Collection<?> collection)
/*      */     {
/*  870 */       for (Object element : collection) {
/*  871 */         if ((element instanceof Short)) {
/*  872 */           short ele = ((Short)element).shortValue();
/*  873 */           if (!TDoubleShortHashMap.this.containsValue(ele)) {
/*  874 */             return false;
/*      */           }
/*      */         } else {
/*  877 */           return false;
/*      */         }
/*      */       }
/*  880 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean containsAll(TShortCollection collection)
/*      */     {
/*  886 */       TShortIterator iter = collection.iterator();
/*  887 */       while (iter.hasNext()) {
/*  888 */         if (!TDoubleShortHashMap.this.containsValue(iter.next())) {
/*  889 */           return false;
/*      */         }
/*      */       }
/*  892 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean containsAll(short[] array)
/*      */     {
/*  898 */       for (short element : array) {
/*  899 */         if (!TDoubleShortHashMap.this.containsValue(element)) {
/*  900 */           return false;
/*      */         }
/*      */       }
/*  903 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean addAll(Collection<? extends Short> collection)
/*      */     {
/*  909 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean addAll(TShortCollection collection)
/*      */     {
/*  915 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean addAll(short[] array)
/*      */     {
/*  921 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public boolean retainAll(Collection<?> collection)
/*      */     {
/*  928 */       boolean modified = false;
/*  929 */       TShortIterator iter = iterator();
/*  930 */       while (iter.hasNext()) {
/*  931 */         if (!collection.contains(Short.valueOf(iter.next()))) {
/*  932 */           iter.remove();
/*  933 */           modified = true;
/*      */         }
/*      */       }
/*  936 */       return modified;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean retainAll(TShortCollection collection)
/*      */     {
/*  942 */       if (this == collection) {
/*  943 */         return false;
/*      */       }
/*  945 */       boolean modified = false;
/*  946 */       TShortIterator iter = iterator();
/*  947 */       while (iter.hasNext()) {
/*  948 */         if (!collection.contains(iter.next())) {
/*  949 */           iter.remove();
/*  950 */           modified = true;
/*      */         }
/*      */       }
/*  953 */       return modified;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean retainAll(short[] array)
/*      */     {
/*  959 */       boolean changed = false;
/*  960 */       Arrays.sort(array);
/*  961 */       short[] values = TDoubleShortHashMap.this._values;
/*  962 */       byte[] states = TDoubleShortHashMap.this._states;
/*      */       
/*  964 */       for (int i = values.length; i-- > 0;) {
/*  965 */         if ((states[i] == 1) && (Arrays.binarySearch(array, values[i]) < 0)) {
/*  966 */           TDoubleShortHashMap.this.removeAt(i);
/*  967 */           changed = true;
/*      */         }
/*      */       }
/*  970 */       return changed;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean removeAll(Collection<?> collection)
/*      */     {
/*  976 */       boolean changed = false;
/*  977 */       for (Object element : collection) {
/*  978 */         if ((element instanceof Short)) {
/*  979 */           short c = ((Short)element).shortValue();
/*  980 */           if (remove(c)) {
/*  981 */             changed = true;
/*      */           }
/*      */         }
/*      */       }
/*  985 */       return changed;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean removeAll(TShortCollection collection)
/*      */     {
/*  991 */       if (this == collection) {
/*  992 */         clear();
/*  993 */         return true;
/*      */       }
/*  995 */       boolean changed = false;
/*  996 */       TShortIterator iter = collection.iterator();
/*  997 */       while (iter.hasNext()) {
/*  998 */         short element = iter.next();
/*  999 */         if (remove(element)) {
/* 1000 */           changed = true;
/*      */         }
/*      */       }
/* 1003 */       return changed;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean removeAll(short[] array)
/*      */     {
/* 1009 */       boolean changed = false;
/* 1010 */       for (int i = array.length; i-- > 0;) {
/* 1011 */         if (remove(array[i])) {
/* 1012 */           changed = true;
/*      */         }
/*      */       }
/* 1015 */       return changed;
/*      */     }
/*      */     
/*      */ 
/*      */     public void clear()
/*      */     {
/* 1021 */       TDoubleShortHashMap.this.clear();
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean forEach(TShortProcedure procedure)
/*      */     {
/* 1027 */       return TDoubleShortHashMap.this.forEachValue(procedure);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1034 */       final StringBuilder buf = new StringBuilder("{");
/* 1035 */       TDoubleShortHashMap.this.forEachValue(new TShortProcedure() {
/* 1036 */         private boolean first = true;
/*      */         
/*      */         public boolean execute(short value) {
/* 1039 */           if (this.first) {
/* 1040 */             this.first = false;
/*      */           } else {
/* 1042 */             buf.append(", ");
/*      */           }
/*      */           
/* 1045 */           buf.append(value);
/* 1046 */           return true;
/*      */         }
/* 1048 */       });
/* 1049 */       buf.append("}");
/* 1050 */       return buf.toString();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   class TDoubleShortKeyHashIterator
/*      */     extends THashPrimitiveIterator
/*      */     implements TDoubleIterator
/*      */   {
/*      */     TDoubleShortKeyHashIterator(TPrimitiveHash hash)
/*      */     {
/* 1063 */       super();
/*      */     }
/*      */     
/*      */     public double next()
/*      */     {
/* 1068 */       moveToNextIndex();
/* 1069 */       return TDoubleShortHashMap.this._set[this._index];
/*      */     }
/*      */     
/*      */     public void remove()
/*      */     {
/* 1074 */       if (this._expectedSize != this._hash.size()) {
/* 1075 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 1080 */         this._hash.tempDisableAutoCompaction();
/* 1081 */         TDoubleShortHashMap.this.removeAt(this._index);
/*      */       }
/*      */       finally {
/* 1084 */         this._hash.reenableAutoCompaction(false);
/*      */       }
/*      */       
/* 1087 */       this._expectedSize -= 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   class TDoubleShortValueHashIterator
/*      */     extends THashPrimitiveIterator
/*      */     implements TShortIterator
/*      */   {
/*      */     TDoubleShortValueHashIterator(TPrimitiveHash hash)
/*      */     {
/* 1101 */       super();
/*      */     }
/*      */     
/*      */     public short next()
/*      */     {
/* 1106 */       moveToNextIndex();
/* 1107 */       return TDoubleShortHashMap.this._values[this._index];
/*      */     }
/*      */     
/*      */     public void remove()
/*      */     {
/* 1112 */       if (this._expectedSize != this._hash.size()) {
/* 1113 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       
/*      */       try
/*      */       {
/* 1118 */         this._hash.tempDisableAutoCompaction();
/* 1119 */         TDoubleShortHashMap.this.removeAt(this._index);
/*      */       }
/*      */       finally {
/* 1122 */         this._hash.reenableAutoCompaction(false);
/*      */       }
/*      */       
/* 1125 */       this._expectedSize -= 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   class TDoubleShortHashIterator
/*      */     extends THashPrimitiveIterator
/*      */     implements TDoubleShortIterator
/*      */   {
/*      */     TDoubleShortHashIterator(TDoubleShortHashMap map)
/*      */     {
/* 1138 */       super();
/*      */     }
/*      */     
/*      */     public void advance()
/*      */     {
/* 1143 */       moveToNextIndex();
/*      */     }
/*      */     
/*      */     public double key()
/*      */     {
/* 1148 */       return TDoubleShortHashMap.this._set[this._index];
/*      */     }
/*      */     
/*      */     public short value()
/*      */     {
/* 1153 */       return TDoubleShortHashMap.this._values[this._index];
/*      */     }
/*      */     
/*      */     public short setValue(short val)
/*      */     {
/* 1158 */       short old = value();
/* 1159 */       TDoubleShortHashMap.this._values[this._index] = val;
/* 1160 */       return old;
/*      */     }
/*      */     
/*      */     public void remove()
/*      */     {
/* 1165 */       if (this._expectedSize != this._hash.size()) {
/* 1166 */         throw new ConcurrentModificationException();
/*      */       }
/*      */       try
/*      */       {
/* 1170 */         this._hash.tempDisableAutoCompaction();
/* 1171 */         TDoubleShortHashMap.this.removeAt(this._index);
/*      */       }
/*      */       finally {
/* 1174 */         this._hash.reenableAutoCompaction(false);
/*      */       }
/* 1176 */       this._expectedSize -= 1;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean equals(Object other)
/*      */   {
/* 1184 */     if (!(other instanceof TDoubleShortMap)) {
/* 1185 */       return false;
/*      */     }
/* 1187 */     TDoubleShortMap that = (TDoubleShortMap)other;
/* 1188 */     if (that.size() != size()) {
/* 1189 */       return false;
/*      */     }
/* 1191 */     short[] values = this._values;
/* 1192 */     byte[] states = this._states;
/* 1193 */     short this_no_entry_value = getNoEntryValue();
/* 1194 */     short that_no_entry_value = that.getNoEntryValue();
/* 1195 */     for (int i = values.length; i-- > 0;) {
/* 1196 */       if (states[i] == 1) {
/* 1197 */         double key = this._set[i];
/* 1198 */         short that_value = that.get(key);
/* 1199 */         short this_value = values[i];
/* 1200 */         if ((this_value != that_value) && (this_value != this_no_entry_value) && (that_value != that_no_entry_value))
/*      */         {
/*      */ 
/* 1203 */           return false;
/*      */         }
/*      */       }
/*      */     }
/* 1207 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1214 */     int hashcode = 0;
/* 1215 */     byte[] states = this._states;
/* 1216 */     for (int i = this._values.length; i-- > 0;) {
/* 1217 */       if (states[i] == 1) {
/* 1218 */         hashcode += (HashFunctions.hash(this._set[i]) ^ HashFunctions.hash(this._values[i]));
/*      */       }
/*      */     }
/*      */     
/* 1222 */     return hashcode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1229 */     final StringBuilder buf = new StringBuilder("{");
/* 1230 */     forEachEntry(new TDoubleShortProcedure() {
/* 1231 */       private boolean first = true;
/*      */       
/* 1233 */       public boolean execute(double key, short value) { if (this.first) this.first = false; else {
/* 1234 */           buf.append(", ");
/*      */         }
/* 1236 */         buf.append(key);
/* 1237 */         buf.append("=");
/* 1238 */         buf.append(value);
/* 1239 */         return true;
/*      */       }
/* 1241 */     });
/* 1242 */     buf.append("}");
/* 1243 */     return buf.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void writeExternal(ObjectOutput out)
/*      */     throws IOException
/*      */   {
/* 1250 */     out.writeByte(0);
/*      */     
/*      */ 
/* 1253 */     super.writeExternal(out);
/*      */     
/*      */ 
/* 1256 */     out.writeInt(this._size);
/*      */     
/*      */ 
/* 1259 */     for (int i = this._states.length; i-- > 0;) {
/* 1260 */       if (this._states[i] == 1) {
/* 1261 */         out.writeDouble(this._set[i]);
/* 1262 */         out.writeShort(this._values[i]);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void readExternal(ObjectInput in)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1271 */     in.readByte();
/*      */     
/*      */ 
/* 1274 */     super.readExternal(in);
/*      */     
/*      */ 
/* 1277 */     int size = in.readInt();
/* 1278 */     setUp(size);
/*      */     
/*      */ 
/* 1281 */     while (size-- > 0) {
/* 1282 */       double key = in.readDouble();
/* 1283 */       short val = in.readShort();
/* 1284 */       put(key, val);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\map\hash\TDoubleShortHashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */