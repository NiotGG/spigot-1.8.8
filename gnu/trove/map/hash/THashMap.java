/*     */ package gnu.trove.map.hash;
/*     */ 
/*     */ import gnu.trove.function.TObjectFunction;
/*     */ import gnu.trove.impl.HashFunctions;
/*     */ import gnu.trove.impl.hash.TObjectHash;
/*     */ import gnu.trove.iterator.hash.TObjectHashIterator;
/*     */ import gnu.trove.map.TMap;
/*     */ import gnu.trove.procedure.TObjectObjectProcedure;
/*     */ import gnu.trove.procedure.TObjectProcedure;
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */ public class THashMap<K, V>
/*     */   extends TObjectHash<K>
/*     */   implements TMap<K, V>, Externalizable
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*     */   protected transient V[] _values;
/*     */   
/*     */   public THashMap() {}
/*     */   
/*     */   public THashMap(int initialCapacity)
/*     */   {
/*  77 */     super(initialCapacity);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public THashMap(int initialCapacity, float loadFactor)
/*     */   {
/*  90 */     super(initialCapacity, loadFactor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public THashMap(Map<? extends K, ? extends V> map)
/*     */   {
/* 101 */     this(map.size());
/* 102 */     putAll(map);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public THashMap(THashMap<? extends K, ? extends V> map)
/*     */   {
/* 113 */     this(map.size());
/* 114 */     putAll(map);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int setUp(int initialCapacity)
/*     */   {
/* 127 */     int capacity = super.setUp(initialCapacity);
/*     */     
/* 129 */     this._values = ((Object[])new Object[capacity]);
/* 130 */     return capacity;
/*     */   }
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
/*     */   public V put(K key, V value)
/*     */   {
/* 144 */     int index = insertKey(key);
/* 145 */     return (V)doPut(value, index);
/*     */   }
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
/*     */   public V putIfAbsent(K key, V value)
/*     */   {
/* 160 */     int index = insertKey(key);
/* 161 */     if (index < 0) {
/* 162 */       return (V)this._values[(-index - 1)];
/*     */     }
/* 164 */     return (V)doPut(value, index);
/*     */   }
/*     */   
/*     */   private V doPut(V value, int index)
/*     */   {
/* 169 */     V previous = null;
/* 170 */     boolean isNewMapping = true;
/* 171 */     if (index < 0) {
/* 172 */       index = -index - 1;
/* 173 */       previous = this._values[index];
/* 174 */       isNewMapping = false;
/*     */     }
/* 176 */     this._values[index] = value;
/* 177 */     if (isNewMapping) {
/* 178 */       postInsertHook(this.consumeFreeSlot);
/*     */     }
/*     */     
/* 181 */     return previous;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object other)
/*     */   {
/* 194 */     if (!(other instanceof Map)) {
/* 195 */       return false;
/*     */     }
/* 197 */     Map<K, V> that = (Map)other;
/* 198 */     if (that.size() != size()) {
/* 199 */       return false;
/*     */     }
/* 201 */     return forEachEntry(new EqProcedure(that));
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 206 */     THashMap<K, V>.HashProcedure p = new HashProcedure(null);
/* 207 */     forEachEntry(p);
/* 208 */     return p.getHashCode();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 213 */     final StringBuilder buf = new StringBuilder("{");
/* 214 */     forEachEntry(new TObjectObjectProcedure() {
/* 215 */       private boolean first = true;
/*     */       
/*     */       public boolean execute(K key, V value)
/*     */       {
/* 219 */         if (this.first) {
/* 220 */           this.first = false;
/*     */         } else {
/* 222 */           buf.append(", ");
/*     */         }
/*     */         
/* 225 */         buf.append(key);
/* 226 */         buf.append("=");
/* 227 */         buf.append(value);
/* 228 */         return true;
/*     */       }
/* 230 */     });
/* 231 */     buf.append("}");
/* 232 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private final class HashProcedure implements TObjectObjectProcedure<K, V> { private HashProcedure() {}
/*     */     
/* 237 */     private int h = 0;
/*     */     
/*     */     public int getHashCode() {
/* 240 */       return this.h;
/*     */     }
/*     */     
/*     */     public final boolean execute(K key, V value) {
/* 244 */       this.h += (HashFunctions.hash(key) ^ (value == null ? 0 : value.hashCode()));
/* 245 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private final class EqProcedure<K, V> implements TObjectObjectProcedure<K, V>
/*     */   {
/*     */     private final Map<K, V> _otherMap;
/*     */     
/*     */     EqProcedure() {
/* 254 */       this._otherMap = otherMap;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public final boolean execute(K key, V value)
/*     */     {
/* 262 */       if ((value == null) && (!this._otherMap.containsKey(key))) {
/* 263 */         return false;
/*     */       }
/*     */       
/* 266 */       V oValue = this._otherMap.get(key);
/* 267 */       return (oValue == value) || ((oValue != null) && (THashMap.this.equals(oValue, value)));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean forEachKey(TObjectProcedure<? super K> procedure)
/*     */   {
/* 281 */     return forEach(procedure);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean forEachValue(TObjectProcedure<? super V> procedure)
/*     */   {
/* 293 */     V[] values = this._values;
/* 294 */     Object[] set = this._set;
/* 295 */     for (int i = values.length; i-- > 0;) {
/* 296 */       if ((set[i] != FREE) && (set[i] != REMOVED) && (!procedure.execute(values[i])))
/*     */       {
/*     */ 
/* 299 */         return false;
/*     */       }
/*     */     }
/* 302 */     return true;
/*     */   }
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
/*     */   public boolean forEachEntry(TObjectObjectProcedure<? super K, ? super V> procedure)
/*     */   {
/* 316 */     Object[] keys = this._set;
/* 317 */     V[] values = this._values;
/* 318 */     for (int i = keys.length; i-- > 0;) {
/* 319 */       if ((keys[i] != FREE) && (keys[i] != REMOVED) && (!procedure.execute(keys[i], values[i])))
/*     */       {
/*     */ 
/* 322 */         return false;
/*     */       }
/*     */     }
/* 325 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean retainEntries(TObjectObjectProcedure<? super K, ? super V> procedure)
/*     */   {
/* 338 */     boolean modified = false;
/* 339 */     Object[] keys = this._set;
/* 340 */     V[] values = this._values;
/*     */     
/*     */ 
/* 343 */     tempDisableAutoCompaction();
/*     */     try {
/* 345 */       for (i = keys.length; i-- > 0;)
/* 346 */         if ((keys[i] != FREE) && (keys[i] != REMOVED) && (!procedure.execute(keys[i], values[i])))
/*     */         {
/*     */ 
/* 349 */           removeAt(i);
/* 350 */           modified = true;
/*     */         }
/*     */     } finally {
/*     */       int i;
/* 354 */       reenableAutoCompaction(true);
/*     */     }
/*     */     
/* 357 */     return modified;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void transformValues(TObjectFunction<V, V> function)
/*     */   {
/* 367 */     V[] values = this._values;
/* 368 */     Object[] set = this._set;
/* 369 */     for (int i = values.length; i-- > 0;) {
/* 370 */       if ((set[i] != FREE) && (set[i] != REMOVED)) {
/* 371 */         values[i] = function.execute(values[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void rehash(int newCapacity)
/*     */   {
/* 384 */     int oldCapacity = this._set.length;
/* 385 */     int oldSize = size();
/* 386 */     Object[] oldKeys = this._set;
/* 387 */     V[] oldVals = this._values;
/*     */     
/* 389 */     this._set = new Object[newCapacity];
/* 390 */     Arrays.fill(this._set, FREE);
/* 391 */     this._values = ((Object[])new Object[newCapacity]);
/*     */     
/*     */ 
/*     */ 
/* 395 */     int count = 0;
/* 396 */     for (int i = oldCapacity; i-- > 0;) {
/* 397 */       Object o = oldKeys[i];
/*     */       
/* 399 */       if ((o != FREE) && (o != REMOVED))
/*     */       {
/* 401 */         int index = insertKey(o);
/* 402 */         if (index < 0) {
/* 403 */           throwObjectContractViolation(this._set[(-index - 1)], o, size(), oldSize, oldKeys);
/*     */         }
/* 405 */         this._values[index] = oldVals[i];
/*     */         
/* 407 */         count++;
/*     */       }
/*     */     }
/*     */     
/* 411 */     reportPotentialConcurrentMod(size(), oldSize);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public V get(Object key)
/*     */   {
/* 423 */     int index = index(key);
/* 424 */     return index < 0 ? null : this._values[index];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void clear()
/*     */   {
/* 432 */     if (size() == 0) {
/* 433 */       return;
/*     */     }
/*     */     
/* 436 */     super.clear();
/*     */     
/* 438 */     Arrays.fill(this._set, 0, this._set.length, FREE);
/* 439 */     Arrays.fill(this._values, 0, this._values.length, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public V remove(Object key)
/*     */   {
/* 451 */     V prev = null;
/* 452 */     int index = index(key);
/* 453 */     if (index >= 0) {
/* 454 */       prev = this._values[index];
/* 455 */       removeAt(index);
/*     */     }
/* 457 */     return prev;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeAt(int index)
/*     */   {
/* 467 */     this._values[index] = null;
/* 468 */     super.removeAt(index);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection<V> values()
/*     */   {
/* 478 */     return new ValueView();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<K> keySet()
/*     */   {
/* 488 */     return new KeyView();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<Map.Entry<K, V>> entrySet()
/*     */   {
/* 498 */     return new EntryView();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean containsValue(Object val)
/*     */   {
/* 509 */     Object[] set = this._set;
/* 510 */     V[] vals = this._values;
/*     */     
/*     */     int i;
/*     */     int i;
/* 514 */     if (null == val) {
/* 515 */       for (i = vals.length; i-- > 0;) {
/* 516 */         if ((set[i] != FREE) && (set[i] != REMOVED) && (val == vals[i]))
/*     */         {
/* 518 */           return true;
/*     */         }
/*     */       }
/*     */     } else {
/* 522 */       for (i = vals.length; i-- > 0;) {
/* 523 */         if ((set[i] != FREE) && (set[i] != REMOVED) && ((val == vals[i]) || (equals(val, vals[i]))))
/*     */         {
/* 525 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 529 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean containsKey(Object key)
/*     */   {
/* 541 */     return contains(key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void putAll(Map<? extends K, ? extends V> map)
/*     */   {
/* 551 */     ensureCapacity(map.size());
/*     */     
/* 553 */     for (Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
/* 554 */       put(e.getKey(), e.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   protected class ValueView extends THashMap<K, V>.MapBackedView<V>
/*     */   {
/*     */     protected ValueView()
/*     */     {
/* 562 */       super(null);
/*     */     }
/*     */     
/*     */     public Iterator<V> iterator() {
/* 566 */       new TObjectHashIterator(THashMap.this) {
/*     */         protected V objectAtIndex(int index) {
/* 568 */           return (V)THashMap.this._values[index];
/*     */         }
/*     */       };
/*     */     }
/*     */     
/*     */     public boolean containsElement(V value)
/*     */     {
/* 575 */       return THashMap.this.containsValue(value);
/*     */     }
/*     */     
/*     */     public boolean removeElement(V value)
/*     */     {
/* 580 */       Object[] values = THashMap.this._values;
/* 581 */       Object[] set = THashMap.this._set;
/*     */       
/* 583 */       for (int i = values.length; i-- > 0;) {
/* 584 */         if (((set[i] != TObjectHash.FREE) && (set[i] != TObjectHash.REMOVED) && (value == values[i])) || ((null != values[i]) && (THashMap.this.equals(values[i], value))))
/*     */         {
/*     */ 
/*     */ 
/* 588 */           THashMap.this.removeAt(i);
/* 589 */           return true;
/*     */         }
/*     */       }
/*     */       
/* 593 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class EntryView extends THashMap<K, V>.MapBackedView<Map.Entry<K, V>>
/*     */   {
/*     */     protected EntryView() {
/* 600 */       super(null);
/*     */     }
/*     */     
/*     */     private final class EntryIterator extends TObjectHashIterator {
/*     */       EntryIterator() {
/* 605 */         super();
/*     */       }
/*     */       
/*     */ 
/*     */       public THashMap<K, V>.Entry objectAtIndex(int index)
/*     */       {
/* 611 */         return new THashMap.Entry(THashMap.this, THashMap.this._set[index], THashMap.this._values[index], index);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public Iterator<Map.Entry<K, V>> iterator()
/*     */     {
/* 618 */       return new EntryIterator(THashMap.this);
/*     */     }
/*     */     
/*     */     public boolean removeElement(Map.Entry<K, V> entry)
/*     */     {
/* 623 */       if (entry == null) { return false;
/*     */       }
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
/* 637 */       K key = keyForEntry(entry);
/* 638 */       int index = THashMap.this.index(key);
/* 639 */       if (index >= 0) {
/* 640 */         V val = valueForEntry(entry);
/* 641 */         if ((val == THashMap.this._values[index]) || ((null != val) && (THashMap.this.equals(val, THashMap.this._values[index]))))
/*     */         {
/* 643 */           THashMap.this.removeAt(index);
/* 644 */           return true;
/*     */         }
/*     */       }
/* 647 */       return false;
/*     */     }
/*     */     
/*     */     public boolean containsElement(Map.Entry<K, V> entry)
/*     */     {
/* 652 */       V val = THashMap.this.get(keyForEntry(entry));
/* 653 */       V entryValue = entry.getValue();
/* 654 */       return (entryValue == val) || ((null != val) && (THashMap.this.equals(val, entryValue)));
/*     */     }
/*     */     
/*     */ 
/*     */     protected V valueForEntry(Map.Entry<K, V> entry)
/*     */     {
/* 660 */       return (V)entry.getValue();
/*     */     }
/*     */     
/*     */     protected K keyForEntry(Map.Entry<K, V> entry)
/*     */     {
/* 665 */       return (K)entry.getKey();
/*     */     }
/*     */   }
/*     */   
/*     */   private abstract class MapBackedView<E>
/*     */     extends AbstractSet<E>
/*     */     implements Set<E>, Iterable<E>
/*     */   {
/*     */     private MapBackedView() {}
/*     */     
/*     */     public abstract Iterator<E> iterator();
/*     */     
/*     */     public abstract boolean removeElement(E paramE);
/*     */     
/*     */     public abstract boolean containsElement(E paramE);
/*     */     
/*     */     public boolean contains(Object key)
/*     */     {
/* 683 */       return containsElement(key);
/*     */     }
/*     */     
/*     */     public boolean remove(Object o)
/*     */     {
/*     */       try
/*     */       {
/* 690 */         return removeElement(o);
/*     */       } catch (ClassCastException ex) {}
/* 692 */       return false;
/*     */     }
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
/*     */     public void clear()
/*     */     {
/* 708 */       THashMap.this.clear();
/*     */     }
/*     */     
/*     */     public boolean add(E obj)
/*     */     {
/* 713 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public int size()
/*     */     {
/* 718 */       return THashMap.this.size();
/*     */     }
/*     */     
/*     */     public Object[] toArray()
/*     */     {
/* 723 */       Object[] result = new Object[size()];
/* 724 */       Iterator<E> e = iterator();
/* 725 */       for (int i = 0; e.hasNext(); i++) {
/* 726 */         result[i] = e.next();
/*     */       }
/* 728 */       return result;
/*     */     }
/*     */     
/*     */ 
/*     */     public <T> T[] toArray(T[] a)
/*     */     {
/* 734 */       int size = size();
/* 735 */       if (a.length < size) {
/* 736 */         a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
/*     */       }
/*     */       
/* 739 */       Iterator<E> it = iterator();
/* 740 */       Object[] result = a;
/* 741 */       for (int i = 0; i < size; i++) {
/* 742 */         result[i] = it.next();
/*     */       }
/*     */       
/* 745 */       if (a.length > size) {
/* 746 */         a[size] = null;
/*     */       }
/*     */       
/* 749 */       return a;
/*     */     }
/*     */     
/*     */     public boolean isEmpty()
/*     */     {
/* 754 */       return THashMap.this.isEmpty();
/*     */     }
/*     */     
/*     */     public boolean addAll(Collection<? extends E> collection)
/*     */     {
/* 759 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean retainAll(Collection<?> collection)
/*     */     {
/* 765 */       boolean changed = false;
/* 766 */       Iterator<E> i = iterator();
/* 767 */       while (i.hasNext()) {
/* 768 */         if (!collection.contains(i.next())) {
/* 769 */           i.remove();
/* 770 */           changed = true;
/*     */         }
/*     */       }
/* 773 */       return changed;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 777 */       Iterator<E> i = iterator();
/* 778 */       if (!i.hasNext()) { return "{}";
/*     */       }
/* 780 */       StringBuilder sb = new StringBuilder();
/* 781 */       sb.append('{');
/*     */       for (;;) {
/* 783 */         E e = i.next();
/* 784 */         sb.append(e == this ? "(this Collection)" : e);
/* 785 */         if (!i.hasNext()) return '}';
/* 786 */         sb.append(", ");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected class KeyView extends THashMap<K, V>.MapBackedView<K>
/*     */   {
/*     */     protected KeyView() {
/* 794 */       super(null);
/*     */     }
/*     */     
/*     */     public Iterator<K> iterator() {
/* 798 */       return new TObjectHashIterator(THashMap.this);
/*     */     }
/*     */     
/*     */     public boolean removeElement(K key)
/*     */     {
/* 803 */       return null != THashMap.this.remove(key);
/*     */     }
/*     */     
/*     */     public boolean containsElement(K key)
/*     */     {
/* 808 */       return THashMap.this.contains(key);
/*     */     }
/*     */   }
/*     */   
/*     */   final class Entry implements Map.Entry<K, V>
/*     */   {
/*     */     private K key;
/*     */     private V val;
/*     */     private final int index;
/*     */     
/*     */     Entry(V key, int value)
/*     */     {
/* 820 */       this.key = key;
/* 821 */       this.val = value;
/* 822 */       this.index = index;
/*     */     }
/*     */     
/*     */     public K getKey()
/*     */     {
/* 827 */       return (K)this.key;
/*     */     }
/*     */     
/*     */     public V getValue()
/*     */     {
/* 832 */       return (V)this.val;
/*     */     }
/*     */     
/*     */     public V setValue(V o)
/*     */     {
/* 837 */       if (THashMap.this._values[this.index] != this.val) {
/* 838 */         throw new ConcurrentModificationException();
/*     */       }
/*     */       
/* 841 */       V retval = this.val;
/*     */       
/* 843 */       THashMap.this._values[this.index] = o;
/* 844 */       this.val = o;
/* 845 */       return retval;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o)
/*     */     {
/* 850 */       if ((o instanceof Map.Entry)) {
/* 851 */         Map.Entry<K, V> e1 = this;
/* 852 */         Map.Entry e2 = (Map.Entry)o;
/* 853 */         return (THashMap.this.equals(e1.getKey(), e2.getKey())) && (THashMap.this.equals(e1.getValue(), e1.getValue()));
/*     */       }
/*     */       
/* 856 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 861 */       return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/*     */     }
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/* 867 */       return this.key + "=" + this.val;
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeExternal(ObjectOutput out)
/*     */     throws IOException
/*     */   {
/* 874 */     out.writeByte(1);
/*     */     
/*     */ 
/* 877 */     super.writeExternal(out);
/*     */     
/*     */ 
/* 880 */     out.writeInt(this._size);
/*     */     
/*     */ 
/* 883 */     for (int i = this._set.length; i-- > 0;) {
/* 884 */       if ((this._set[i] != REMOVED) && (this._set[i] != FREE)) {
/* 885 */         out.writeObject(this._set[i]);
/* 886 */         out.writeObject(this._values[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void readExternal(ObjectInput in)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 896 */     byte version = in.readByte();
/*     */     
/*     */ 
/* 899 */     if (version != 0) {
/* 900 */       super.readExternal(in);
/*     */     }
/*     */     
/*     */ 
/* 904 */     int size = in.readInt();
/* 905 */     setUp(size);
/*     */     
/*     */ 
/* 908 */     while (size-- > 0)
/*     */     {
/* 910 */       K key = in.readObject();
/*     */       
/* 912 */       V val = in.readObject();
/* 913 */       put(key, val);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\map\hash\THashMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */