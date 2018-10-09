/*      */ package io.netty.util.internal.chmv8;
/*      */ 
/*      */ import io.netty.util.internal.IntegerHolder;
/*      */ import io.netty.util.internal.InternalThreadLocalMap;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import java.util.concurrent.locks.LockSupport;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import sun.misc.Unsafe;
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
/*      */ public class ConcurrentHashMapV8<K, V>
/*      */   implements ConcurrentMap<K, V>, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 7249069246763182397L;
/*      */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*      */   private static final int DEFAULT_CAPACITY = 16;
/*      */   static final int MAX_ARRAY_SIZE = 2147483639;
/*      */   private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
/*      */   private static final float LOAD_FACTOR = 0.75F;
/*      */   static final int TREEIFY_THRESHOLD = 8;
/*      */   static final int UNTREEIFY_THRESHOLD = 6;
/*      */   static final int MIN_TREEIFY_CAPACITY = 64;
/*      */   private static final int MIN_TRANSFER_STRIDE = 16;
/*      */   static final int MOVED = -1;
/*      */   static final int TREEBIN = -2;
/*      */   static final int RESERVED = -3;
/*      */   static final int HASH_BITS = Integer.MAX_VALUE;
/*      */   
/*      */   public static abstract interface ConcurrentHashMapSpliterator<T>
/*      */   {
/*      */     public abstract ConcurrentHashMapSpliterator<T> trySplit();
/*      */     
/*      */     public abstract long estimateSize();
/*      */     
/*      */     public abstract void forEachRemaining(ConcurrentHashMapV8.Action<? super T> paramAction);
/*      */     
/*      */     public abstract boolean tryAdvance(ConcurrentHashMapV8.Action<? super T> paramAction);
/*      */   }
/*      */   
/*      */   public static abstract interface Action<A>
/*      */   {
/*      */     public abstract void apply(A paramA);
/*      */   }
/*      */   
/*      */   public static abstract interface BiAction<A, B>
/*      */   {
/*      */     public abstract void apply(A paramA, B paramB);
/*      */   }
/*      */   
/*      */   public static abstract interface Fun<A, T>
/*      */   {
/*      */     public abstract T apply(A paramA);
/*      */   }
/*      */   
/*      */   public static abstract interface BiFun<A, B, T>
/*      */   {
/*      */     public abstract T apply(A paramA, B paramB);
/*      */   }
/*      */   
/*      */   public static abstract interface ObjectToDouble<A>
/*      */   {
/*      */     public abstract double apply(A paramA);
/*      */   }
/*      */   
/*      */   public static abstract interface ObjectToLong<A>
/*      */   {
/*      */     public abstract long apply(A paramA);
/*      */   }
/*      */   
/*  594 */   static final int NCPU = Runtime.getRuntime().availableProcessors();
/*      */   
/*      */ 
/*  597 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", Integer.TYPE), new ObjectStreamField("segmentShift", Integer.TYPE) };
/*      */   
/*      */   public static abstract interface ObjectToInt<A> { public abstract int apply(A paramA);
/*      */   }
/*      */   
/*      */   public static abstract interface ObjectByObjectToDouble<A, B> { public abstract double apply(A paramA, B paramB);
/*      */   }
/*      */   
/*      */   public static abstract interface ObjectByObjectToLong<A, B> { public abstract long apply(A paramA, B paramB);
/*      */   }
/*      */   
/*      */   public static abstract interface ObjectByObjectToInt<A, B> { public abstract int apply(A paramA, B paramB);
/*      */   }
/*      */   
/*      */   public static abstract interface DoubleByDoubleToDouble { public abstract double apply(double paramDouble1, double paramDouble2);
/*      */   }
/*      */   
/*      */   public static abstract interface LongByLongToLong { public abstract long apply(long paramLong1, long paramLong2);
/*      */   }
/*      */   
/*      */   public static abstract interface IntByIntToInt { public abstract int apply(int paramInt1, int paramInt2);
/*      */   }
/*      */   
/*  620 */   static class Node<K, V> implements Map.Entry<K, V> { Node(int paramInt, K paramK, V paramV, Node<K, V> paramNode) { this.hash = paramInt;
/*  621 */       this.key = paramK;
/*  622 */       this.val = paramV;
/*  623 */       this.next = paramNode;
/*      */     }
/*      */     
/*  626 */     public final K getKey() { return (K)this.key; }
/*  627 */     public final V getValue() { return (V)this.val; }
/*  628 */     public final int hashCode() { return this.key.hashCode() ^ this.val.hashCode(); }
/*  629 */     public final String toString() { return this.key + "=" + this.val; }
/*      */     
/*  631 */     public final V setValue(V paramV) { throw new UnsupportedOperationException(); }
/*      */     
/*      */     public final boolean equals(Object paramObject) { Map.Entry localEntry;
/*      */       Object localObject1;
/*      */       Object localObject2;
/*  636 */       Object localObject3; return ((paramObject instanceof Map.Entry)) && ((localObject1 = (localEntry = (Map.Entry)paramObject).getKey()) != null) && ((localObject2 = localEntry.getValue()) != null) && ((localObject1 == this.key) || (localObject1.equals(this.key))) && ((localObject2 == (localObject3 = this.val)) || (localObject2.equals(localObject3)));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Node<K, V> find(int paramInt, Object paramObject)
/*      */     {
/*  647 */       Node localNode = this;
/*  648 */       if (paramObject != null) {
/*      */         do {
/*      */           Object localObject;
/*  651 */           if ((localNode.hash == paramInt) && (((localObject = localNode.key) == paramObject) || ((localObject != null) && (paramObject.equals(localObject)))))
/*      */           {
/*  653 */             return localNode; }
/*  654 */         } while ((localNode = localNode.next) != null);
/*      */       }
/*  656 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final int hash;
/*      */     
/*      */ 
/*      */ 
/*      */     final K key;
/*      */     
/*      */ 
/*      */     volatile V val;
/*      */     
/*      */ 
/*      */     volatile Node<K, V> next;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final int spread(int paramInt)
/*      */   {
/*  679 */     return (paramInt ^ paramInt >>> 16) & 0x7FFFFFFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int tableSizeFor(int paramInt)
/*      */   {
/*  687 */     int i = paramInt - 1;
/*  688 */     i |= i >>> 1;
/*  689 */     i |= i >>> 2;
/*  690 */     i |= i >>> 4;
/*  691 */     i |= i >>> 8;
/*  692 */     i |= i >>> 16;
/*  693 */     return i >= 1073741824 ? 1073741824 : i < 0 ? 1 : i + 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static Class<?> comparableClassFor(Object paramObject)
/*      */   {
/*  701 */     if ((paramObject instanceof Comparable)) {
/*      */       Class localClass;
/*  703 */       if ((localClass = paramObject.getClass()) == String.class)
/*  704 */         return localClass;
/*  705 */       Type[] arrayOfType1; if ((arrayOfType1 = localClass.getGenericInterfaces()) != null) {
/*  706 */         for (int i = 0; i < arrayOfType1.length; i++) { Type localType;
/*  707 */           ParameterizedType localParameterizedType; Type[] arrayOfType2; if ((((localType = arrayOfType1[i]) instanceof ParameterizedType)) && ((localParameterizedType = (ParameterizedType)localType).getRawType() == Comparable.class) && ((arrayOfType2 = localParameterizedType.getActualTypeArguments()) != null) && (arrayOfType2.length == 1) && (arrayOfType2[0] == localClass))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  712 */             return localClass; }
/*      */         }
/*      */       }
/*      */     }
/*  716 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static int compareComparables(Class<?> paramClass, Object paramObject1, Object paramObject2)
/*      */   {
/*  725 */     return (paramObject2 == null) || (paramObject2.getClass() != paramClass) ? 0 : ((Comparable)paramObject1).compareTo(paramObject2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final <K, V> Node<K, V> tabAt(Node<K, V>[] paramArrayOfNode, int paramInt)
/*      */   {
/*  749 */     return (Node)U.getObjectVolatile(paramArrayOfNode, (paramInt << ASHIFT) + ABASE);
/*      */   }
/*      */   
/*      */   static final <K, V> boolean casTabAt(Node<K, V>[] paramArrayOfNode, int paramInt, Node<K, V> paramNode1, Node<K, V> paramNode2)
/*      */   {
/*  754 */     return U.compareAndSwapObject(paramArrayOfNode, (paramInt << ASHIFT) + ABASE, paramNode1, paramNode2);
/*      */   }
/*      */   
/*      */   static final <K, V> void setTabAt(Node<K, V>[] paramArrayOfNode, int paramInt, Node<K, V> paramNode) {
/*  758 */     U.putObjectVolatile(paramArrayOfNode, (paramInt << ASHIFT) + ABASE, paramNode);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   volatile transient Node<K, V>[] table;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile transient Node<K, V>[] nextTable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile transient long baseCount;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile transient int sizeCtl;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile transient int transferIndex;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile transient int transferOrigin;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile transient int cellsBusy;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private volatile transient CounterCell[] counterCells;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient KeySetView<K, V> keySet;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient ValuesView<K, V> values;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient EntrySetView<K, V> entrySet;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ConcurrentHashMapV8(int paramInt)
/*      */   {
/*  836 */     if (paramInt < 0)
/*  837 */       throw new IllegalArgumentException();
/*  838 */     int i = paramInt >= 536870912 ? 1073741824 : tableSizeFor(paramInt + (paramInt >>> 1) + 1);
/*      */     
/*      */ 
/*  841 */     this.sizeCtl = i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ConcurrentHashMapV8(Map<? extends K, ? extends V> paramMap)
/*      */   {
/*  850 */     this.sizeCtl = 16;
/*  851 */     putAll(paramMap);
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
/*      */ 
/*      */   public ConcurrentHashMapV8(int paramInt, float paramFloat)
/*      */   {
/*  870 */     this(paramInt, paramFloat, 1);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ConcurrentHashMapV8(int paramInt1, float paramFloat, int paramInt2)
/*      */   {
/*  893 */     if ((paramFloat <= 0.0F) || (paramInt1 < 0) || (paramInt2 <= 0))
/*  894 */       throw new IllegalArgumentException();
/*  895 */     if (paramInt1 < paramInt2)
/*  896 */       paramInt1 = paramInt2;
/*  897 */     long l = (1.0D + (float)paramInt1 / paramFloat);
/*  898 */     int i = l >= 1073741824L ? 1073741824 : tableSizeFor((int)l);
/*      */     
/*  900 */     this.sizeCtl = i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int size()
/*      */   {
/*  909 */     long l = sumCount();
/*  910 */     return l > 2147483647L ? Integer.MAX_VALUE : l < 0L ? 0 : (int)l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/*  919 */     return sumCount() <= 0L;
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
/*      */   public V get(Object paramObject)
/*      */   {
/*  935 */     int i = spread(paramObject.hashCode());
/*  936 */     Node[] arrayOfNode; int j; Node localNode1; if (((arrayOfNode = this.table) != null) && ((j = arrayOfNode.length) > 0) && ((localNode1 = tabAt(arrayOfNode, j - 1 & i)) != null)) { int k;
/*      */       Object localObject;
/*  938 */       if ((k = localNode1.hash) == i) {
/*  939 */         if (((localObject = localNode1.key) == paramObject) || ((localObject != null) && (paramObject.equals(localObject)))) {
/*  940 */           return (V)localNode1.val;
/*      */         }
/*  942 */       } else if (k < 0) { Node localNode2;
/*  943 */         return (V)((localNode2 = localNode1.find(i, paramObject)) != null ? localNode2.val : null); }
/*  944 */       while ((localNode1 = localNode1.next) != null) {
/*  945 */         if ((localNode1.hash == i) && (((localObject = localNode1.key) == paramObject) || ((localObject != null) && (paramObject.equals(localObject)))))
/*      */         {
/*  947 */           return (V)localNode1.val; }
/*      */       }
/*      */     }
/*  950 */     return null;
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
/*      */   public boolean containsKey(Object paramObject)
/*      */   {
/*  963 */     return get(paramObject) != null;
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
/*      */   public boolean containsValue(Object paramObject)
/*      */   {
/*  977 */     if (paramObject == null)
/*  978 */       throw new NullPointerException();
/*      */     Node[] arrayOfNode;
/*  980 */     if ((arrayOfNode = this.table) != null) {
/*  981 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/*  982 */       Node localNode; while ((localNode = localTraverser.advance()) != null) {
/*      */         Object localObject;
/*  984 */         if (((localObject = localNode.val) == paramObject) || ((localObject != null) && (paramObject.equals(localObject))))
/*  985 */           return true;
/*      */       }
/*      */     }
/*  988 */     return false;
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
/*      */   public V put(K paramK, V paramV)
/*      */   {
/* 1005 */     return (V)putVal(paramK, paramV, false);
/*      */   }
/*      */   
/*      */   final V putVal(K paramK, V paramV, boolean paramBoolean)
/*      */   {
/* 1010 */     if ((paramK == null) || (paramV == null)) throw new NullPointerException();
/* 1011 */     int i = spread(paramK.hashCode());
/* 1012 */     int j = 0;
/* 1013 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int k;
/* 1015 */       if ((arrayOfNode == null) || ((k = arrayOfNode.length) == 0)) {
/* 1016 */         arrayOfNode = initTable(); } else { int m;
/* 1017 */         Node localNode; if ((localNode = tabAt(arrayOfNode, m = k - 1 & i)) == null) {
/* 1018 */           if (casTabAt(arrayOfNode, m, null, new Node(i, paramK, paramV, null)))
/*      */             break;
/*      */         } else {
/*      */           int n;
/* 1022 */           if ((n = localNode.hash) == -1) {
/* 1023 */             arrayOfNode = helpTransfer(arrayOfNode, localNode);
/*      */           } else {
/* 1025 */             Object localObject1 = null;
/* 1026 */             synchronized (localNode) {
/* 1027 */               if (tabAt(arrayOfNode, m) == localNode) { Object localObject2;
/* 1028 */                 if (n >= 0) {
/* 1029 */                   j = 1;
/* 1030 */                   for (localObject2 = localNode;; j++) {
/*      */                     Object localObject3;
/* 1032 */                     if ((((Node)localObject2).hash == i) && (((localObject3 = ((Node)localObject2).key) == paramK) || ((localObject3 != null) && (paramK.equals(localObject3)))))
/*      */                     {
/*      */ 
/* 1035 */                       localObject1 = ((Node)localObject2).val;
/* 1036 */                       if (paramBoolean) break;
/* 1037 */                       ((Node)localObject2).val = paramV; break;
/*      */                     }
/*      */                     
/* 1040 */                     Object localObject4 = localObject2;
/* 1041 */                     if ((localObject2 = ((Node)localObject2).next) == null) {
/* 1042 */                       ((Node)localObject4).next = new Node(i, paramK, paramV, null);
/*      */                       
/* 1044 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/* 1048 */                 else if ((localNode instanceof TreeBin))
/*      */                 {
/* 1050 */                   j = 2;
/* 1051 */                   if ((localObject2 = ((TreeBin)localNode).putTreeVal(i, paramK, paramV)) != null)
/*      */                   {
/* 1053 */                     localObject1 = ((Node)localObject2).val;
/* 1054 */                     if (!paramBoolean)
/* 1055 */                       ((Node)localObject2).val = paramV;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 1060 */             if (j != 0) {
/* 1061 */               if (j >= 8)
/* 1062 */                 treeifyBin(arrayOfNode, m);
/* 1063 */               if (localObject1 == null) break;
/* 1064 */               return (V)localObject1;
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1069 */     addCount(1L, j);
/* 1070 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*      */   {
/* 1081 */     tryPresize(paramMap.size());
/* 1082 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 1083 */       putVal(localEntry.getKey(), localEntry.getValue(), false);
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
/*      */   public V remove(Object paramObject)
/*      */   {
/* 1096 */     return (V)replaceNode(paramObject, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final V replaceNode(Object paramObject1, V paramV, Object paramObject2)
/*      */   {
/* 1105 */     int i = spread(paramObject1.hashCode());
/* 1106 */     Node[] arrayOfNode = this.table;
/*      */     int j;
/* 1108 */     int k; Node localNode; while ((arrayOfNode != null) && ((j = arrayOfNode.length) != 0) && ((localNode = tabAt(arrayOfNode, k = j - 1 & i)) != null))
/*      */     {
/*      */       int m;
/* 1111 */       if ((m = localNode.hash) == -1) {
/* 1112 */         arrayOfNode = helpTransfer(arrayOfNode, localNode);
/*      */       } else {
/* 1114 */         Object localObject1 = null;
/* 1115 */         int n = 0;
/* 1116 */         synchronized (localNode) {
/* 1117 */           if (tabAt(arrayOfNode, k) == localNode) { Object localObject2;
/* 1118 */             Object localObject3; Object localObject4; Object localObject5; if (m >= 0) {
/* 1119 */               n = 1;
/* 1120 */               localObject2 = localNode;localObject3 = null;
/*      */               for (;;) {
/* 1122 */                 if ((((Node)localObject2).hash == i) && (((localObject4 = ((Node)localObject2).key) == paramObject1) || ((localObject4 != null) && (paramObject1.equals(localObject4)))))
/*      */                 {
/*      */ 
/* 1125 */                   localObject5 = ((Node)localObject2).val;
/* 1126 */                   if ((paramObject2 == null) || (paramObject2 == localObject5) || ((localObject5 != null) && (paramObject2.equals(localObject5))))
/*      */                   {
/* 1128 */                     localObject1 = localObject5;
/* 1129 */                     if (paramV != null) {
/* 1130 */                       ((Node)localObject2).val = paramV;
/* 1131 */                     } else if (localObject3 != null) {
/* 1132 */                       ((Node)localObject3).next = ((Node)localObject2).next;
/*      */                     } else {
/* 1134 */                       setTabAt(arrayOfNode, k, ((Node)localObject2).next);
/*      */                     }
/*      */                   }
/*      */                 } else {
/* 1138 */                   localObject3 = localObject2;
/* 1139 */                   if ((localObject2 = ((Node)localObject2).next) == null)
/*      */                     break;
/*      */                 }
/*      */               }
/* 1143 */             } else if ((localNode instanceof TreeBin)) {
/* 1144 */               n = 1;
/* 1145 */               localObject2 = (TreeBin)localNode;
/*      */               
/* 1147 */               if (((localObject3 = ((TreeBin)localObject2).root) != null) && ((localObject4 = ((TreeNode)localObject3).findTreeNode(i, paramObject1, null)) != null))
/*      */               {
/* 1149 */                 localObject5 = ((TreeNode)localObject4).val;
/* 1150 */                 if ((paramObject2 == null) || (paramObject2 == localObject5) || ((localObject5 != null) && (paramObject2.equals(localObject5))))
/*      */                 {
/* 1152 */                   localObject1 = localObject5;
/* 1153 */                   if (paramV != null) {
/* 1154 */                     ((TreeNode)localObject4).val = paramV;
/* 1155 */                   } else if (((TreeBin)localObject2).removeTreeNode((TreeNode)localObject4))
/* 1156 */                     setTabAt(arrayOfNode, k, untreeify(((TreeBin)localObject2).first));
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 1162 */         if (n != 0) {
/* 1163 */           if (localObject1 == null) break;
/* 1164 */           if (paramV == null)
/* 1165 */             addCount(-1L, -1);
/* 1166 */           return (V)localObject1;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1172 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public void clear()
/*      */   {
/* 1179 */     long l = 0L;
/* 1180 */     int i = 0;
/* 1181 */     Node[] arrayOfNode = this.table;
/* 1182 */     while ((arrayOfNode != null) && (i < arrayOfNode.length))
/*      */     {
/* 1184 */       Node localNode1 = tabAt(arrayOfNode, i);
/* 1185 */       if (localNode1 == null) {
/* 1186 */         i++; } else { int j;
/* 1187 */         if ((j = localNode1.hash) == -1) {
/* 1188 */           arrayOfNode = helpTransfer(arrayOfNode, localNode1);
/* 1189 */           i = 0;
/*      */         }
/*      */         else {
/* 1192 */           synchronized (localNode1) {
/* 1193 */             if (tabAt(arrayOfNode, i) == localNode1) {
/* 1194 */               Node localNode2 = (localNode1 instanceof TreeBin) ? ((TreeBin)localNode1).first : j >= 0 ? localNode1 : null;
/*      */               
/*      */ 
/* 1197 */               while (localNode2 != null) {
/* 1198 */                 l -= 1L;
/* 1199 */                 localNode2 = localNode2.next;
/*      */               }
/* 1201 */               setTabAt(arrayOfNode, i++, null);
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1206 */     if (l != 0L) {
/* 1207 */       addCount(l, -1);
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
/*      */   public KeySetView<K, V> keySet()
/*      */   {
/*      */     KeySetView localKeySetView;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1230 */     return (localKeySetView = this.keySet) != null ? localKeySetView : (this.keySet = new KeySetView(this, null));
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
/*      */   public Collection<V> values()
/*      */   {
/*      */     ValuesView localValuesView;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1253 */     return (localValuesView = this.values) != null ? localValuesView : (this.values = new ValuesView(this));
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
/*      */   public Set<Map.Entry<K, V>> entrySet()
/*      */   {
/*      */     EntrySetView localEntrySetView;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1275 */     return (localEntrySetView = this.entrySet) != null ? localEntrySetView : (this.entrySet = new EntrySetView(this));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1286 */     int i = 0;
/*      */     Node[] arrayOfNode;
/* 1288 */     if ((arrayOfNode = this.table) != null) {
/* 1289 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 1290 */       Node localNode; while ((localNode = localTraverser.advance()) != null)
/* 1291 */         i += (localNode.key.hashCode() ^ localNode.val.hashCode());
/*      */     }
/* 1293 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1309 */     int i = (arrayOfNode = this.table) == null ? 0 : arrayOfNode.length;
/* 1310 */     Traverser localTraverser = new Traverser(arrayOfNode, i, 0, i);
/* 1311 */     StringBuilder localStringBuilder = new StringBuilder();
/* 1312 */     localStringBuilder.append('{');
/*      */     Node localNode;
/* 1314 */     if ((localNode = localTraverser.advance()) != null) {
/*      */       for (;;) {
/* 1316 */         Object localObject1 = localNode.key;
/* 1317 */         Object localObject2 = localNode.val;
/* 1318 */         localStringBuilder.append(localObject1 == this ? "(this Map)" : localObject1);
/* 1319 */         localStringBuilder.append('=');
/* 1320 */         localStringBuilder.append(localObject2 == this ? "(this Map)" : localObject2);
/* 1321 */         if ((localNode = localTraverser.advance()) == null)
/*      */           break;
/* 1323 */         localStringBuilder.append(',').append(' ');
/*      */       }
/*      */     }
/* 1326 */     return '}';
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */     Object localObject2;
/*      */     
/*      */     Object localObject3;
/*      */     
/* 1340 */     if (paramObject != this) {
/* 1341 */       if (!(paramObject instanceof Map))
/* 1342 */         return false;
/* 1343 */       Map localMap = (Map)paramObject;
/*      */       Node[] arrayOfNode;
/* 1345 */       int i = (arrayOfNode = this.table) == null ? 0 : arrayOfNode.length;
/* 1346 */       Traverser localTraverser = new Traverser(arrayOfNode, i, 0, i);
/* 1347 */       while ((localObject1 = localTraverser.advance()) != null) {
/* 1348 */         localObject2 = ((Node)localObject1).val;
/* 1349 */         localObject3 = localMap.get(((Node)localObject1).key);
/* 1350 */         if ((localObject3 == null) || ((localObject3 != localObject2) && (!localObject3.equals(localObject2))))
/* 1351 */           return false;
/*      */       }
/* 1353 */       for (localObject1 = localMap.entrySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Map.Entry)((Iterator)localObject1).next();
/*      */         Object localObject4;
/* 1355 */         Object localObject5; if (((localObject3 = ((Map.Entry)localObject2).getKey()) == null) || ((localObject4 = ((Map.Entry)localObject2).getValue()) == null) || ((localObject5 = get(localObject3)) == null) || ((localObject4 != localObject5) && (!localObject4.equals(localObject5))))
/*      */         {
/*      */ 
/*      */ 
/* 1359 */           return false; }
/*      */       }
/*      */     }
/* 1362 */     return true;
/*      */   }
/*      */   
/*      */   static class Segment<K, V> extends ReentrantLock implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     final float loadFactor;
/*      */     
/*      */     Segment(float paramFloat)
/*      */     {
/* 1372 */       this.loadFactor = paramFloat;
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1388 */     int i = 0;
/* 1389 */     int j = 1;
/* 1390 */     while (j < 16) {
/* 1391 */       i++;
/* 1392 */       j <<= 1;
/*      */     }
/* 1394 */     int k = 32 - i;
/* 1395 */     int m = j - 1;
/* 1396 */     Segment[] arrayOfSegment = (Segment[])new Segment[16];
/*      */     
/* 1398 */     for (int n = 0; n < arrayOfSegment.length; n++)
/* 1399 */       arrayOfSegment[n] = new Segment(0.75F);
/* 1400 */     paramObjectOutputStream.putFields().put("segments", arrayOfSegment);
/* 1401 */     paramObjectOutputStream.putFields().put("segmentShift", k);
/* 1402 */     paramObjectOutputStream.putFields().put("segmentMask", m);
/* 1403 */     paramObjectOutputStream.writeFields();
/*      */     
/*      */     Node[] arrayOfNode;
/* 1406 */     if ((arrayOfNode = this.table) != null) {
/* 1407 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 1408 */       Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 1409 */         paramObjectOutputStream.writeObject(localNode.key);
/* 1410 */         paramObjectOutputStream.writeObject(localNode.val);
/*      */       }
/*      */     }
/* 1413 */     paramObjectOutputStream.writeObject(null);
/* 1414 */     paramObjectOutputStream.writeObject(null);
/* 1415 */     arrayOfSegment = null;
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1431 */     this.sizeCtl = -1;
/* 1432 */     paramObjectInputStream.defaultReadObject();
/* 1433 */     long l1 = 0L;
/* 1434 */     Object localObject1 = null;
/*      */     for (;;) {
/* 1436 */       Object localObject2 = paramObjectInputStream.readObject();
/* 1437 */       Object localObject3 = paramObjectInputStream.readObject();
/* 1438 */       if ((localObject2 == null) || (localObject3 == null)) break;
/* 1439 */       localObject1 = new Node(spread(localObject2.hashCode()), localObject2, localObject3, (Node)localObject1);
/* 1440 */       l1 += 1L;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1445 */     if (l1 == 0L) {
/* 1446 */       this.sizeCtl = 0;
/*      */     } else {
/*      */       int i;
/* 1449 */       if (l1 >= 536870912L) {
/* 1450 */         i = 1073741824;
/*      */       } else {
/* 1452 */         int j = (int)l1;
/* 1453 */         i = tableSizeFor(j + (j >>> 1) + 1);
/*      */       }
/*      */       
/* 1456 */       Node[] arrayOfNode = (Node[])new Node[i];
/* 1457 */       int k = i - 1;
/* 1458 */       long l2 = 0L;
/* 1459 */       while (localObject1 != null)
/*      */       {
/* 1461 */         Node localNode1 = ((Node)localObject1).next;
/* 1462 */         int m = ((Node)localObject1).hash;int n = m & k;
/* 1463 */         Node localNode2; int i1; if ((localNode2 = tabAt(arrayOfNode, n)) == null) {
/* 1464 */           i1 = 1;
/*      */         } else {
/* 1466 */           Object localObject4 = ((Node)localObject1).key;
/* 1467 */           if (localNode2.hash < 0) {
/* 1468 */             TreeBin localTreeBin = (TreeBin)localNode2;
/* 1469 */             if (localTreeBin.putTreeVal(m, localObject4, ((Node)localObject1).val) == null)
/* 1470 */               l2 += 1L;
/* 1471 */             i1 = 0;
/*      */           }
/*      */           else {
/* 1474 */             int i2 = 0;
/* 1475 */             i1 = 1;
/*      */             
/* 1477 */             for (Object localObject5 = localNode2; localObject5 != null; localObject5 = ((Node)localObject5).next) { Object localObject6;
/* 1478 */               if ((((Node)localObject5).hash == m) && (((localObject6 = ((Node)localObject5).key) == localObject4) || ((localObject6 != null) && (localObject4.equals(localObject6)))))
/*      */               {
/*      */ 
/* 1481 */                 i1 = 0;
/* 1482 */                 break;
/*      */               }
/* 1484 */               i2++;
/*      */             }
/* 1486 */             if ((i1 != 0) && (i2 >= 8)) {
/* 1487 */               i1 = 0;
/* 1488 */               l2 += 1L;
/* 1489 */               ((Node)localObject1).next = localNode2;
/* 1490 */               Object localObject7 = null;Object localObject8 = null;
/* 1491 */               for (localObject5 = localObject1; localObject5 != null; localObject5 = ((Node)localObject5).next) {
/* 1492 */                 TreeNode localTreeNode = new TreeNode(((Node)localObject5).hash, ((Node)localObject5).key, ((Node)localObject5).val, null, null);
/*      */                 
/* 1494 */                 if ((localTreeNode.prev = localObject8) == null) {
/* 1495 */                   localObject7 = localTreeNode;
/*      */                 } else
/* 1497 */                   ((TreeNode)localObject8).next = localTreeNode;
/* 1498 */                 localObject8 = localTreeNode;
/*      */               }
/* 1500 */               setTabAt(arrayOfNode, n, new TreeBin((TreeNode)localObject7));
/*      */             }
/*      */           }
/*      */         }
/* 1504 */         if (i1 != 0) {
/* 1505 */           l2 += 1L;
/* 1506 */           ((Node)localObject1).next = localNode2;
/* 1507 */           setTabAt(arrayOfNode, n, (Node)localObject1);
/*      */         }
/* 1509 */         localObject1 = localNode1;
/*      */       }
/* 1511 */       this.table = arrayOfNode;
/* 1512 */       this.sizeCtl = (i - (i >>> 2));
/* 1513 */       this.baseCount = l2;
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
/*      */   public V putIfAbsent(K paramK, V paramV)
/*      */   {
/* 1527 */     return (V)putVal(paramK, paramV, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean remove(Object paramObject1, Object paramObject2)
/*      */   {
/* 1536 */     if (paramObject1 == null)
/* 1537 */       throw new NullPointerException();
/* 1538 */     return (paramObject2 != null) && (replaceNode(paramObject1, null, paramObject2) != null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean replace(K paramK, V paramV1, V paramV2)
/*      */   {
/* 1547 */     if ((paramK == null) || (paramV1 == null) || (paramV2 == null))
/* 1548 */       throw new NullPointerException();
/* 1549 */     return replaceNode(paramK, paramV2, paramV1) != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V replace(K paramK, V paramV)
/*      */   {
/* 1560 */     if ((paramK == null) || (paramV == null))
/* 1561 */       throw new NullPointerException();
/* 1562 */     return (V)replaceNode(paramK, paramV, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V getOrDefault(Object paramObject, V paramV)
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1580 */     return (localObject = get(paramObject)) == null ? paramV : localObject;
/*      */   }
/*      */   
/*      */   public void forEach(BiAction<? super K, ? super V> paramBiAction) {
/* 1584 */     if (paramBiAction == null) throw new NullPointerException();
/*      */     Node[] arrayOfNode;
/* 1586 */     if ((arrayOfNode = this.table) != null) {
/* 1587 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 1588 */       Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 1589 */         paramBiAction.apply(localNode.key, localNode.val);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void replaceAll(BiFun<? super K, ? super V, ? extends V> paramBiFun) {
/* 1595 */     if (paramBiFun == null) throw new NullPointerException();
/*      */     Node[] arrayOfNode;
/* 1597 */     if ((arrayOfNode = this.table) != null) {
/* 1598 */       Traverser localTraverser = new Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 1599 */       Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 1600 */         Object localObject1 = localNode.val;
/* 1601 */         Object localObject2 = localNode.key;
/* 1602 */         for (;;) { Object localObject3 = paramBiFun.apply(localObject2, localObject1);
/* 1603 */           if (localObject3 == null)
/* 1604 */             throw new NullPointerException();
/* 1605 */           if ((replaceNode(localObject2, localObject3, localObject1) != null) || ((localObject1 = get(localObject2)) == null)) {
/*      */             break;
/*      */           }
/*      */         }
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
/*      */   public V computeIfAbsent(K paramK, Fun<? super K, ? extends V> paramFun)
/*      */   {
/* 1636 */     if ((paramK == null) || (paramFun == null))
/* 1637 */       throw new NullPointerException();
/* 1638 */     int i = spread(paramK.hashCode());
/* 1639 */     Object localObject1 = null;
/* 1640 */     int j = 0;
/* 1641 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int k;
/* 1643 */       if ((arrayOfNode == null) || ((k = arrayOfNode.length) == 0)) {
/* 1644 */         arrayOfNode = initTable(); } else { int m;
/* 1645 */         Node localNode; Object localObject2; if ((localNode = tabAt(arrayOfNode, m = k - 1 & i)) == null) {
/* 1646 */           ReservationNode localReservationNode = new ReservationNode();
/* 1647 */           synchronized (localReservationNode) {
/* 1648 */             if (casTabAt(arrayOfNode, m, null, localReservationNode)) {
/* 1649 */               j = 1;
/* 1650 */               localObject2 = null;
/*      */               try {
/* 1652 */                 if ((localObject1 = paramFun.apply(paramK)) != null)
/* 1653 */                   localObject2 = new Node(i, paramK, localObject1, null);
/*      */               } finally {
/* 1655 */                 setTabAt(arrayOfNode, m, (Node)localObject2);
/*      */               }
/*      */             }
/*      */           }
/* 1659 */           if (j != 0)
/*      */             break;
/*      */         } else { int i1;
/* 1662 */           if ((i1 = localNode.hash) == -1) {
/* 1663 */             arrayOfNode = helpTransfer(arrayOfNode, localNode);
/*      */           } else {
/* 1665 */             int n = 0;
/* 1666 */             synchronized (localNode) {
/* 1667 */               if (tabAt(arrayOfNode, m) == localNode) { Object localObject4;
/* 1668 */                 if (i1 >= 0) {
/* 1669 */                   j = 1;
/* 1670 */                   for (localObject2 = localNode;; j++)
/*      */                   {
/* 1672 */                     if ((((Node)localObject2).hash == i) && (((localObject4 = ((Node)localObject2).key) == paramK) || ((localObject4 != null) && (paramK.equals(localObject4)))))
/*      */                     {
/*      */ 
/* 1675 */                       localObject1 = ((Node)localObject2).val;
/* 1676 */                       break;
/*      */                     }
/* 1678 */                     Object localObject6 = localObject2;
/* 1679 */                     if ((localObject2 = ((Node)localObject2).next) == null) {
/* 1680 */                       if ((localObject1 = paramFun.apply(paramK)) == null) break;
/* 1681 */                       n = 1;
/* 1682 */                       ((Node)localObject6).next = new Node(i, paramK, localObject1, null); break;
/*      */                     }
/*      */                     
/*      */                   }
/*      */                   
/*      */                 }
/* 1688 */                 else if ((localNode instanceof TreeBin)) {
/* 1689 */                   j = 2;
/* 1690 */                   localObject2 = (TreeBin)localNode;
/*      */                   TreeNode localTreeNode;
/* 1692 */                   if (((localObject4 = ((TreeBin)localObject2).root) != null) && ((localTreeNode = ((TreeNode)localObject4).findTreeNode(i, paramK, null)) != null))
/*      */                   {
/* 1694 */                     localObject1 = localTreeNode.val;
/* 1695 */                   } else if ((localObject1 = paramFun.apply(paramK)) != null) {
/* 1696 */                     n = 1;
/* 1697 */                     ((TreeBin)localObject2).putTreeVal(i, paramK, localObject1);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 1702 */             if (j != 0) {
/* 1703 */               if (j >= 8)
/* 1704 */                 treeifyBin(arrayOfNode, m);
/* 1705 */               if (n != 0) break;
/* 1706 */               return (V)localObject1;
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1711 */     if (localObject1 != null)
/* 1712 */       addCount(1L, j);
/* 1713 */     return (V)localObject1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V computeIfPresent(K paramK, BiFun<? super K, ? super V, ? extends V> paramBiFun)
/*      */   {
/* 1737 */     if ((paramK == null) || (paramBiFun == null))
/* 1738 */       throw new NullPointerException();
/* 1739 */     int i = spread(paramK.hashCode());
/* 1740 */     Object localObject1 = null;
/* 1741 */     int j = 0;
/* 1742 */     int k = 0;
/* 1743 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int m;
/* 1745 */       if ((arrayOfNode == null) || ((m = arrayOfNode.length) == 0)) {
/* 1746 */         arrayOfNode = initTable(); } else { int n;
/* 1747 */         Node localNode1; if ((localNode1 = tabAt(arrayOfNode, n = m - 1 & i)) == null) break;
/*      */         int i1;
/* 1749 */         if ((i1 = localNode1.hash) == -1) {
/* 1750 */           arrayOfNode = helpTransfer(arrayOfNode, localNode1);
/*      */         } else {
/* 1752 */           synchronized (localNode1) {
/* 1753 */             if (tabAt(arrayOfNode, n) == localNode1) { Object localObject2;
/* 1754 */               Object localObject3; Object localObject4; if (i1 >= 0) {
/* 1755 */                 k = 1;
/* 1756 */                 localObject2 = localNode1; for (localObject3 = null;; k++)
/*      */                 {
/* 1758 */                   if ((((Node)localObject2).hash == i) && (((localObject4 = ((Node)localObject2).key) == paramK) || ((localObject4 != null) && (paramK.equals(localObject4)))))
/*      */                   {
/*      */ 
/* 1761 */                     localObject1 = paramBiFun.apply(paramK, ((Node)localObject2).val);
/* 1762 */                     if (localObject1 != null) {
/* 1763 */                       ((Node)localObject2).val = localObject1;
/*      */                     } else {
/* 1765 */                       j = -1;
/* 1766 */                       Node localNode2 = ((Node)localObject2).next;
/* 1767 */                       if (localObject3 != null) {
/* 1768 */                         ((Node)localObject3).next = localNode2;
/*      */                       } else {
/* 1770 */                         setTabAt(arrayOfNode, n, localNode2);
/*      */                       }
/*      */                     }
/*      */                   } else {
/* 1774 */                     localObject3 = localObject2;
/* 1775 */                     if ((localObject2 = ((Node)localObject2).next) == null)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 1779 */               } else if ((localNode1 instanceof TreeBin)) {
/* 1780 */                 k = 2;
/* 1781 */                 localObject2 = (TreeBin)localNode1;
/*      */                 
/* 1783 */                 if (((localObject3 = ((TreeBin)localObject2).root) != null) && ((localObject4 = ((TreeNode)localObject3).findTreeNode(i, paramK, null)) != null))
/*      */                 {
/* 1785 */                   localObject1 = paramBiFun.apply(paramK, ((TreeNode)localObject4).val);
/* 1786 */                   if (localObject1 != null) {
/* 1787 */                     ((TreeNode)localObject4).val = localObject1;
/*      */                   } else {
/* 1789 */                     j = -1;
/* 1790 */                     if (((TreeBin)localObject2).removeTreeNode((TreeNode)localObject4))
/* 1791 */                       setTabAt(arrayOfNode, n, untreeify(((TreeBin)localObject2).first));
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/* 1797 */           if (k != 0) break;
/*      */         }
/*      */       }
/*      */     }
/* 1801 */     if (j != 0)
/* 1802 */       addCount(j, k);
/* 1803 */     return (V)localObject1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V compute(K paramK, BiFun<? super K, ? super V, ? extends V> paramBiFun)
/*      */   {
/* 1828 */     if ((paramK == null) || (paramBiFun == null))
/* 1829 */       throw new NullPointerException();
/* 1830 */     int i = spread(paramK.hashCode());
/* 1831 */     Object localObject1 = null;
/* 1832 */     int j = 0;
/* 1833 */     int k = 0;
/* 1834 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int m;
/* 1836 */       if ((arrayOfNode == null) || ((m = arrayOfNode.length) == 0)) {
/* 1837 */         arrayOfNode = initTable(); } else { int n;
/* 1838 */         Node localNode; Object localObject2; if ((localNode = tabAt(arrayOfNode, n = m - 1 & i)) == null) {
/* 1839 */           ReservationNode localReservationNode = new ReservationNode();
/* 1840 */           synchronized (localReservationNode) {
/* 1841 */             if (casTabAt(arrayOfNode, n, null, localReservationNode)) {
/* 1842 */               k = 1;
/* 1843 */               localObject2 = null;
/*      */               try {
/* 1845 */                 if ((localObject1 = paramBiFun.apply(paramK, null)) != null) {
/* 1846 */                   j = 1;
/* 1847 */                   localObject2 = new Node(i, paramK, localObject1, null);
/*      */                 }
/*      */               } finally {
/* 1850 */                 setTabAt(arrayOfNode, n, (Node)localObject2);
/*      */               }
/*      */             }
/*      */           }
/* 1854 */           if (k != 0)
/*      */             break;
/*      */         } else { int i1;
/* 1857 */           if ((i1 = localNode.hash) == -1) {
/* 1858 */             arrayOfNode = helpTransfer(arrayOfNode, localNode);
/*      */           } else {
/* 1860 */             synchronized (localNode) {
/* 1861 */               if (tabAt(arrayOfNode, n) == localNode) { Object localObject4;
/* 1862 */                 Object localObject6; if (i1 >= 0) {
/* 1863 */                   k = 1;
/* 1864 */                   ??? = localNode; for (localObject2 = null;; k++)
/*      */                   {
/* 1866 */                     if ((((Node)???).hash == i) && (((localObject4 = ((Node)???).key) == paramK) || ((localObject4 != null) && (paramK.equals(localObject4)))))
/*      */                     {
/*      */ 
/* 1869 */                       localObject1 = paramBiFun.apply(paramK, ((Node)???).val);
/* 1870 */                       if (localObject1 != null) {
/* 1871 */                         ((Node)???).val = localObject1; break;
/*      */                       }
/* 1873 */                       j = -1;
/* 1874 */                       localObject6 = ((Node)???).next;
/* 1875 */                       if (localObject2 != null) {
/* 1876 */                         ((Node)localObject2).next = ((Node)localObject6);
/*      */                       } else {
/* 1878 */                         setTabAt(arrayOfNode, n, (Node)localObject6);
/*      */                       }
/* 1880 */                       break;
/*      */                     }
/* 1882 */                     localObject2 = ???;
/* 1883 */                     if ((??? = ((Node)???).next) == null) {
/* 1884 */                       localObject1 = paramBiFun.apply(paramK, null);
/* 1885 */                       if (localObject1 == null) break;
/* 1886 */                       j = 1;
/* 1887 */                       ((Node)localObject2).next = new Node(i, paramK, localObject1, null); break;
/*      */                     }
/*      */                     
/*      */                   }
/*      */                   
/*      */ 
/*      */                 }
/* 1894 */                 else if ((localNode instanceof TreeBin)) {
/* 1895 */                   k = 1;
/* 1896 */                   ??? = (TreeBin)localNode;
/*      */                   
/* 1898 */                   if ((localObject2 = ((TreeBin)???).root) != null) {
/* 1899 */                     localObject4 = ((TreeNode)localObject2).findTreeNode(i, paramK, null);
/*      */                   } else
/* 1901 */                     localObject4 = null;
/* 1902 */                   localObject6 = localObject4 == null ? null : ((TreeNode)localObject4).val;
/* 1903 */                   localObject1 = paramBiFun.apply(paramK, localObject6);
/* 1904 */                   if (localObject1 != null) {
/* 1905 */                     if (localObject4 != null) {
/* 1906 */                       ((TreeNode)localObject4).val = localObject1;
/*      */                     } else {
/* 1908 */                       j = 1;
/* 1909 */                       ((TreeBin)???).putTreeVal(i, paramK, localObject1);
/*      */                     }
/*      */                   }
/* 1912 */                   else if (localObject4 != null) {
/* 1913 */                     j = -1;
/* 1914 */                     if (((TreeBin)???).removeTreeNode((TreeNode)localObject4))
/* 1915 */                       setTabAt(arrayOfNode, n, untreeify(((TreeBin)???).first));
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 1920 */             if (k != 0) {
/* 1921 */               if (k < 8) break;
/* 1922 */               treeifyBin(arrayOfNode, n); break;
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1927 */     if (j != 0)
/* 1928 */       addCount(j, k);
/* 1929 */     return (V)localObject1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public V merge(K paramK, V paramV, BiFun<? super V, ? super V, ? extends V> paramBiFun)
/*      */   {
/* 1953 */     if ((paramK == null) || (paramV == null) || (paramBiFun == null))
/* 1954 */       throw new NullPointerException();
/* 1955 */     int i = spread(paramK.hashCode());
/* 1956 */     Object localObject1 = null;
/* 1957 */     int j = 0;
/* 1958 */     int k = 0;
/* 1959 */     Node[] arrayOfNode = this.table;
/*      */     for (;;) { int m;
/* 1961 */       if ((arrayOfNode == null) || ((m = arrayOfNode.length) == 0)) {
/* 1962 */         arrayOfNode = initTable(); } else { int n;
/* 1963 */         Node localNode1; if ((localNode1 = tabAt(arrayOfNode, n = m - 1 & i)) == null) {
/* 1964 */           if (casTabAt(arrayOfNode, n, null, new Node(i, paramK, paramV, null))) {
/* 1965 */             j = 1;
/* 1966 */             localObject1 = paramV;
/* 1967 */             break;
/*      */           }
/*      */         } else { int i1;
/* 1970 */           if ((i1 = localNode1.hash) == -1) {
/* 1971 */             arrayOfNode = helpTransfer(arrayOfNode, localNode1);
/*      */           } else {
/* 1973 */             synchronized (localNode1) {
/* 1974 */               if (tabAt(arrayOfNode, n) == localNode1) { Object localObject2;
/* 1975 */                 Object localObject3; Object localObject4; if (i1 >= 0) {
/* 1976 */                   k = 1;
/* 1977 */                   localObject2 = localNode1; for (localObject3 = null;; k++)
/*      */                   {
/* 1979 */                     if ((((Node)localObject2).hash == i) && (((localObject4 = ((Node)localObject2).key) == paramK) || ((localObject4 != null) && (paramK.equals(localObject4)))))
/*      */                     {
/*      */ 
/* 1982 */                       localObject1 = paramBiFun.apply(((Node)localObject2).val, paramV);
/* 1983 */                       if (localObject1 != null) {
/* 1984 */                         ((Node)localObject2).val = localObject1; break;
/*      */                       }
/* 1986 */                       j = -1;
/* 1987 */                       Node localNode2 = ((Node)localObject2).next;
/* 1988 */                       if (localObject3 != null) {
/* 1989 */                         ((Node)localObject3).next = localNode2;
/*      */                       } else {
/* 1991 */                         setTabAt(arrayOfNode, n, localNode2);
/*      */                       }
/* 1993 */                       break;
/*      */                     }
/* 1995 */                     localObject3 = localObject2;
/* 1996 */                     if ((localObject2 = ((Node)localObject2).next) == null) {
/* 1997 */                       j = 1;
/* 1998 */                       localObject1 = paramV;
/* 1999 */                       ((Node)localObject3).next = new Node(i, paramK, localObject1, null);
/*      */                       
/* 2001 */                       break;
/*      */                     }
/*      */                   }
/*      */                 }
/* 2005 */                 else if ((localNode1 instanceof TreeBin)) {
/* 2006 */                   k = 2;
/* 2007 */                   localObject2 = (TreeBin)localNode1;
/* 2008 */                   localObject3 = ((TreeBin)localObject2).root;
/* 2009 */                   localObject4 = localObject3 == null ? null : ((TreeNode)localObject3).findTreeNode(i, paramK, null);
/*      */                   
/* 2011 */                   localObject1 = localObject4 == null ? paramV : paramBiFun.apply(((TreeNode)localObject4).val, paramV);
/*      */                   
/* 2013 */                   if (localObject1 != null) {
/* 2014 */                     if (localObject4 != null) {
/* 2015 */                       ((TreeNode)localObject4).val = localObject1;
/*      */                     } else {
/* 2017 */                       j = 1;
/* 2018 */                       ((TreeBin)localObject2).putTreeVal(i, paramK, localObject1);
/*      */                     }
/*      */                   }
/* 2021 */                   else if (localObject4 != null) {
/* 2022 */                     j = -1;
/* 2023 */                     if (((TreeBin)localObject2).removeTreeNode((TreeNode)localObject4))
/* 2024 */                       setTabAt(arrayOfNode, n, untreeify(((TreeBin)localObject2).first));
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 2029 */             if (k != 0) {
/* 2030 */               if (k < 8) break;
/* 2031 */               treeifyBin(arrayOfNode, n); break;
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 2036 */     if (j != 0)
/* 2037 */       addCount(j, k);
/* 2038 */     return (V)localObject1;
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
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public boolean contains(Object paramObject)
/*      */   {
/* 2059 */     return containsValue(paramObject);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Enumeration<K> keys()
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */ 
/* 2070 */     int i = (arrayOfNode = this.table) == null ? 0 : arrayOfNode.length;
/* 2071 */     return new KeyIterator(arrayOfNode, i, 0, i, this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Enumeration<V> elements()
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/*      */ 
/* 2082 */     int i = (arrayOfNode = this.table) == null ? 0 : arrayOfNode.length;
/* 2083 */     return new ValueIterator(arrayOfNode, i, 0, i, this);
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
/*      */   public long mappingCount()
/*      */   {
/* 2099 */     long l = sumCount();
/* 2100 */     return l < 0L ? 0L : l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> KeySetView<K, Boolean> newKeySet()
/*      */   {
/* 2111 */     return new KeySetView(new ConcurrentHashMapV8(), Boolean.TRUE);
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
/*      */   public static <K> KeySetView<K, Boolean> newKeySet(int paramInt)
/*      */   {
/* 2127 */     return new KeySetView(new ConcurrentHashMapV8(paramInt), Boolean.TRUE);
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
/*      */   public KeySetView<K, V> keySet(V paramV)
/*      */   {
/* 2143 */     if (paramV == null)
/* 2144 */       throw new NullPointerException();
/* 2145 */     return new KeySetView(this, paramV);
/*      */   }
/*      */   
/*      */ 
/*      */   static final class ForwardingNode<K, V>
/*      */     extends ConcurrentHashMapV8.Node<K, V>
/*      */   {
/*      */     final ConcurrentHashMapV8.Node<K, V>[] nextTable;
/*      */     
/*      */     ForwardingNode(ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode)
/*      */     {
/* 2156 */       super(null, null, null);
/* 2157 */       this.nextTable = paramArrayOfNode;
/*      */     }
/*      */     
/*      */     ConcurrentHashMapV8.Node<K, V> find(int paramInt, Object paramObject)
/*      */     {
/* 2162 */       ConcurrentHashMapV8.Node[] arrayOfNode = this.nextTable;
/*      */       int i;
/* 2164 */       ConcurrentHashMapV8.Node localNode; if ((paramObject == null) || (arrayOfNode == null) || ((i = arrayOfNode.length) == 0) || ((localNode = ConcurrentHashMapV8.tabAt(arrayOfNode, i - 1 & paramInt)) == null))
/*      */       {
/* 2166 */         return null; }
/*      */       for (;;) { int j;
/*      */         Object localObject;
/* 2169 */         if (((j = localNode.hash) == paramInt) && (((localObject = localNode.key) == paramObject) || ((localObject != null) && (paramObject.equals(localObject)))))
/*      */         {
/* 2171 */           return localNode; }
/* 2172 */         if (j < 0) {
/* 2173 */           if ((localNode instanceof ForwardingNode)) {
/* 2174 */             arrayOfNode = ((ForwardingNode)localNode).nextTable;
/* 2175 */             break;
/*      */           }
/*      */           
/* 2178 */           return localNode.find(paramInt, paramObject);
/*      */         }
/* 2180 */         if ((localNode = localNode.next) == null) {
/* 2181 */           return null;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReservationNode<K, V>
/*      */     extends ConcurrentHashMapV8.Node<K, V>
/*      */   {
/*      */     ReservationNode()
/*      */     {
/* 2192 */       super(null, null, null);
/*      */     }
/*      */     
/*      */     ConcurrentHashMapV8.Node<K, V> find(int paramInt, Object paramObject) {
/* 2196 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final Node<K, V>[] initTable()
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/* 2207 */     while (((localObject1 = this.table) == null) || (localObject1.length == 0)) { int i;
/* 2208 */       if ((i = this.sizeCtl) < 0) {
/* 2209 */         Thread.yield();
/* 2210 */       } else if (U.compareAndSwapInt(this, SIZECTL, i, -1)) {
/*      */         try {
/* 2212 */           if (((localObject1 = this.table) == null) || (localObject1.length == 0)) {
/* 2213 */             int j = i > 0 ? i : 16;
/*      */             
/* 2215 */             Node[] arrayOfNode = (Node[])new Node[j];
/* 2216 */             this.table = (localObject1 = arrayOfNode);
/* 2217 */             i = j - (j >>> 2);
/*      */           }
/*      */         } finally {
/* 2220 */           this.sizeCtl = i;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 2225 */     return (Node<K, V>[])localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */   private final void addCount(long paramLong, int paramInt)
/*      */   {
/*      */     CounterCell[] arrayOfCounterCell;
/*      */     
/*      */     long l1;
/*      */     
/*      */     long l2;
/*      */     
/*      */     Object localObject1;
/*      */     
/*      */     Object localObject2;
/* 2240 */     if (((arrayOfCounterCell = this.counterCells) != null) || (!U.compareAndSwapLong(this, BASECOUNT, l1 = this.baseCount, l2 = l1 + paramLong)))
/*      */     {
/*      */ 
/* 2243 */       boolean bool = true;
/* 2244 */       InternalThreadLocalMap localInternalThreadLocalMap = InternalThreadLocalMap.get();
/* 2245 */       int i; long l3; if (((localObject1 = localInternalThreadLocalMap.counterHashCode()) == null) || (arrayOfCounterCell == null) || ((i = arrayOfCounterCell.length - 1) < 0) || ((localObject2 = arrayOfCounterCell[(i & localObject1.value)]) == null) || (!(bool = U.compareAndSwapLong(localObject2, CELLVALUE, l3 = ((CounterCell)localObject2).value, l3 + paramLong))))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 2250 */         fullAddCount(localInternalThreadLocalMap, paramLong, (IntegerHolder)localObject1, bool);
/* 2251 */         return;
/*      */       }
/* 2253 */       if (paramInt <= 1)
/* 2254 */         return;
/* 2255 */       l2 = sumCount();
/*      */     }
/* 2257 */     if (paramInt >= 0) {
/*      */       int j;
/* 2259 */       while ((l2 >= (j = this.sizeCtl)) && ((localObject1 = this.table) != null) && (localObject1.length < 1073741824))
/*      */       {
/* 2261 */         if (j < 0) {
/* 2262 */           if ((j == -1) || (this.transferIndex <= this.transferOrigin) || ((localObject2 = this.nextTable) == null)) {
/*      */             break;
/*      */           }
/* 2265 */           if (U.compareAndSwapInt(this, SIZECTL, j, j - 1)) {
/* 2266 */             transfer((Node[])localObject1, (Node[])localObject2);
/*      */           }
/* 2268 */         } else if (U.compareAndSwapInt(this, SIZECTL, j, -2)) {
/* 2269 */           transfer((Node[])localObject1, null); }
/* 2270 */         l2 = sumCount();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   final Node<K, V>[] helpTransfer(Node<K, V>[] paramArrayOfNode, Node<K, V> paramNode)
/*      */   {
/*      */     Node[] arrayOfNode;
/*      */     
/* 2280 */     if (((paramNode instanceof ForwardingNode)) && ((arrayOfNode = ((ForwardingNode)paramNode).nextTable) != null)) {
/*      */       int i;
/* 2282 */       if ((arrayOfNode == this.nextTable) && (paramArrayOfNode == this.table) && (this.transferIndex > this.transferOrigin) && ((i = this.sizeCtl) < -1) && (U.compareAndSwapInt(this, SIZECTL, i, i - 1)))
/*      */       {
/*      */ 
/* 2285 */         transfer(paramArrayOfNode, arrayOfNode); }
/* 2286 */       return arrayOfNode;
/*      */     }
/* 2288 */     return this.table;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void tryPresize(int paramInt)
/*      */   {
/* 2297 */     int i = paramInt >= 536870912 ? 1073741824 : tableSizeFor(paramInt + (paramInt >>> 1) + 1);
/*      */     
/*      */     int j;
/* 2300 */     while ((j = this.sizeCtl) >= 0) {
/* 2301 */       Node[] arrayOfNode1 = this.table;
/* 2302 */       int k; if ((arrayOfNode1 == null) || ((k = arrayOfNode1.length) == 0)) {
/* 2303 */         k = j > i ? j : i;
/* 2304 */         if (U.compareAndSwapInt(this, SIZECTL, j, -1)) {
/*      */           try {
/* 2306 */             if (this.table == arrayOfNode1)
/*      */             {
/* 2308 */               Node[] arrayOfNode2 = (Node[])new Node[k];
/* 2309 */               this.table = arrayOfNode2;
/* 2310 */               j = k - (k >>> 2);
/*      */             }
/*      */           } finally {
/* 2313 */             this.sizeCtl = j;
/*      */           }
/*      */         }
/*      */       } else {
/* 2317 */         if ((i <= j) || (k >= 1073741824))
/*      */           break;
/* 2319 */         if ((arrayOfNode1 == this.table) && (U.compareAndSwapInt(this, SIZECTL, j, -2)))
/*      */         {
/* 2321 */           transfer(arrayOfNode1, null);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private final void transfer(Node<K, V>[] paramArrayOfNode1, Node<K, V>[] paramArrayOfNode2)
/*      */   {
/* 2330 */     int i = paramArrayOfNode1.length;
/* 2331 */     int j; if ((j = NCPU > 1 ? (i >>> 3) / NCPU : i) < 16)
/* 2332 */       j = 16;
/* 2333 */     ForwardingNode localForwardingNode1; int m; if (paramArrayOfNode2 == null)
/*      */     {
/*      */       try {
/* 2336 */         Node[] arrayOfNode = (Node[])new Node[i << 1];
/* 2337 */         paramArrayOfNode2 = arrayOfNode;
/*      */       } catch (Throwable localThrowable) {
/* 2339 */         this.sizeCtl = Integer.MAX_VALUE;
/* 2340 */         return;
/*      */       }
/* 2342 */       this.nextTable = paramArrayOfNode2;
/* 2343 */       this.transferOrigin = i;
/* 2344 */       this.transferIndex = i;
/* 2345 */       localForwardingNode1 = new ForwardingNode(paramArrayOfNode1);
/* 2346 */       for (m = i; m > 0;) {
/* 2347 */         n = m > j ? m - j : 0;
/* 2348 */         for (i1 = n; i1 < m; i1++)
/* 2349 */           paramArrayOfNode2[i1] = localForwardingNode1;
/* 2350 */         for (i1 = i + n; i1 < i + m; i1++)
/* 2351 */           paramArrayOfNode2[i1] = localForwardingNode1;
/* 2352 */         U.putOrderedInt(this, TRANSFERORIGIN, m = n);
/*      */       }
/*      */     }
/* 2355 */     int k = paramArrayOfNode2.length;
/* 2356 */     ForwardingNode localForwardingNode2 = new ForwardingNode(paramArrayOfNode2);
/* 2357 */     int n = 1;
/* 2358 */     int i1 = 0;
/* 2359 */     int i2 = 0;int i3 = 0;
/*      */     for (;;) {
/* 2361 */       if (n != 0) {
/* 2362 */         i2--; if ((i2 >= i3) || (i1 != 0)) {
/* 2363 */           n = 0; } else { int i4;
/* 2364 */           if ((i4 = this.transferIndex) <= this.transferOrigin) {
/* 2365 */             i2 = -1;
/* 2366 */             n = 0;
/*      */           } else { int i5;
/* 2368 */             if (U.compareAndSwapInt(this, TRANSFERINDEX, i4, i5 = i4 > j ? i4 - j : 0))
/*      */             {
/*      */ 
/*      */ 
/* 2372 */               i3 = i5;
/* 2373 */               i2 = i4 - 1;
/* 2374 */               n = 0;
/*      */             }
/*      */           }
/* 2377 */         } } else if ((i2 < 0) || (i2 >= i) || (i2 + i >= k)) {
/* 2378 */         if (i1 != 0) {
/* 2379 */           this.nextTable = null;
/* 2380 */           this.table = paramArrayOfNode2;
/* 2381 */           this.sizeCtl = ((i << 1) - (i >>> 1));
/* 2382 */           return;
/*      */         }
/*      */         int i6;
/* 2385 */         while (!U.compareAndSwapInt(this, SIZECTL, i6 = this.sizeCtl, ++i6)) {}
/* 2386 */         if (i6 != -1)
/* 2387 */           return;
/* 2388 */         i1 = n = 1;
/* 2389 */         i2 = i;
/*      */       }
/*      */       else
/*      */       {
/*      */         Node localNode;
/* 2394 */         if ((localNode = tabAt(paramArrayOfNode1, i2)) == null) {
/* 2395 */           if (casTabAt(paramArrayOfNode1, i2, null, localForwardingNode2)) {
/* 2396 */             setTabAt(paramArrayOfNode2, i2, null);
/* 2397 */             setTabAt(paramArrayOfNode2, i2 + i, null);
/* 2398 */             n = 1;
/*      */           }
/*      */         } else { int i7;
/* 2401 */           if ((i7 = localNode.hash) == -1) {
/* 2402 */             n = 1;
/*      */           } else {
/* 2404 */             synchronized (localNode) {
/* 2405 */               if (tabAt(paramArrayOfNode1, i2) == localNode) { Object localObject1;
/*      */                 Object localObject2;
/* 2407 */                 Object localObject4; Object localObject5; Object localObject6; if (i7 >= 0) {
/* 2408 */                   int i8 = i7 & i;
/* 2409 */                   localObject1 = localNode;
/* 2410 */                   int i9; for (localObject2 = localNode.next; localObject2 != null; localObject2 = ((Node)localObject2).next) {
/* 2411 */                     i9 = ((Node)localObject2).hash & i;
/* 2412 */                     if (i9 != i8) {
/* 2413 */                       i8 = i9;
/* 2414 */                       localObject1 = localObject2;
/*      */                     }
/*      */                   }
/* 2417 */                   if (i8 == 0) {
/* 2418 */                     localObject4 = localObject1;
/* 2419 */                     localObject5 = null;
/*      */                   }
/*      */                   else {
/* 2422 */                     localObject5 = localObject1;
/* 2423 */                     localObject4 = null;
/*      */                   }
/* 2425 */                   for (localObject2 = localNode; localObject2 != localObject1; localObject2 = ((Node)localObject2).next) {
/* 2426 */                     i9 = ((Node)localObject2).hash;localObject6 = ((Node)localObject2).key;Object localObject7 = ((Node)localObject2).val;
/* 2427 */                     if ((i9 & i) == 0) {
/* 2428 */                       localObject4 = new Node(i9, localObject6, localObject7, (Node)localObject4);
/*      */                     } else
/* 2430 */                       localObject5 = new Node(i9, localObject6, localObject7, (Node)localObject5);
/*      */                   }
/* 2432 */                   setTabAt(paramArrayOfNode2, i2, (Node)localObject4);
/* 2433 */                   setTabAt(paramArrayOfNode2, i2 + i, (Node)localObject5);
/* 2434 */                   setTabAt(paramArrayOfNode1, i2, localForwardingNode2);
/* 2435 */                   n = 1;
/*      */                 }
/* 2437 */                 else if ((localNode instanceof TreeBin)) {
/* 2438 */                   TreeBin localTreeBin = (TreeBin)localNode;
/* 2439 */                   localObject1 = null;localObject2 = null;
/* 2440 */                   Object localObject3 = null;localObject6 = null;
/* 2441 */                   int i10 = 0;int i11 = 0;
/* 2442 */                   for (Object localObject8 = localTreeBin.first; localObject8 != null; localObject8 = ((Node)localObject8).next) {
/* 2443 */                     int i12 = ((Node)localObject8).hash;
/* 2444 */                     TreeNode localTreeNode = new TreeNode(i12, ((Node)localObject8).key, ((Node)localObject8).val, null, null);
/*      */                     
/* 2446 */                     if ((i12 & i) == 0) {
/* 2447 */                       if ((localTreeNode.prev = localObject2) == null) {
/* 2448 */                         localObject1 = localTreeNode;
/*      */                       } else
/* 2450 */                         ((TreeNode)localObject2).next = localTreeNode;
/* 2451 */                       localObject2 = localTreeNode;
/* 2452 */                       i10++;
/*      */                     }
/*      */                     else {
/* 2455 */                       if ((localTreeNode.prev = localObject6) == null) {
/* 2456 */                         localObject3 = localTreeNode;
/*      */                       } else
/* 2458 */                         ((TreeNode)localObject6).next = localTreeNode;
/* 2459 */                       localObject6 = localTreeNode;
/* 2460 */                       i11++;
/*      */                     }
/*      */                   }
/* 2463 */                   localObject4 = i11 != 0 ? new TreeBin((TreeNode)localObject1) : i10 <= 6 ? untreeify((Node)localObject1) : localTreeBin;
/*      */                   
/* 2465 */                   localObject5 = i10 != 0 ? new TreeBin((TreeNode)localObject3) : i11 <= 6 ? untreeify((Node)localObject3) : localTreeBin;
/*      */                   
/* 2467 */                   setTabAt(paramArrayOfNode2, i2, (Node)localObject4);
/* 2468 */                   setTabAt(paramArrayOfNode2, i2 + i, (Node)localObject5);
/* 2469 */                   setTabAt(paramArrayOfNode1, i2, localForwardingNode2);
/* 2470 */                   n = 1;
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final void treeifyBin(Node<K, V>[] paramArrayOfNode, int paramInt)
/*      */   {
/* 2486 */     if (paramArrayOfNode != null) { int i;
/* 2487 */       if ((i = paramArrayOfNode.length) < 64) { int j;
/* 2488 */         if ((paramArrayOfNode == this.table) && ((j = this.sizeCtl) >= 0) && (U.compareAndSwapInt(this, SIZECTL, j, -2)))
/*      */         {
/* 2490 */           transfer(paramArrayOfNode, null); }
/*      */       } else { Node localNode1;
/* 2492 */         if (((localNode1 = tabAt(paramArrayOfNode, paramInt)) != null) && (localNode1.hash >= 0)) {
/* 2493 */           synchronized (localNode1) {
/* 2494 */             if (tabAt(paramArrayOfNode, paramInt) == localNode1) {
/* 2495 */               Object localObject1 = null;Object localObject2 = null;
/* 2496 */               for (Node localNode2 = localNode1; localNode2 != null; localNode2 = localNode2.next) {
/* 2497 */                 TreeNode localTreeNode = new TreeNode(localNode2.hash, localNode2.key, localNode2.val, null, null);
/*      */                 
/*      */ 
/* 2500 */                 if ((localTreeNode.prev = localObject2) == null) {
/* 2501 */                   localObject1 = localTreeNode;
/*      */                 } else
/* 2503 */                   ((TreeNode)localObject2).next = localTreeNode;
/* 2504 */                 localObject2 = localTreeNode;
/*      */               }
/* 2506 */               setTabAt(paramArrayOfNode, paramInt, new TreeBin((TreeNode)localObject1));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static <K, V> Node<K, V> untreeify(Node<K, V> paramNode)
/*      */   {
/* 2517 */     Object localObject1 = null;Object localObject2 = null;
/* 2518 */     for (Object localObject3 = paramNode; localObject3 != null; localObject3 = ((Node)localObject3).next) {
/* 2519 */       Node localNode = new Node(((Node)localObject3).hash, ((Node)localObject3).key, ((Node)localObject3).val, null);
/* 2520 */       if (localObject2 == null) {
/* 2521 */         localObject1 = localNode;
/*      */       } else
/* 2523 */         ((Node)localObject2).next = localNode;
/* 2524 */       localObject2 = localNode;
/*      */     }
/* 2526 */     return (Node<K, V>)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */   static final class TreeNode<K, V>
/*      */     extends ConcurrentHashMapV8.Node<K, V>
/*      */   {
/*      */     TreeNode<K, V> parent;
/*      */     
/*      */     TreeNode<K, V> left;
/*      */     
/*      */     TreeNode<K, V> right;
/*      */     TreeNode<K, V> prev;
/*      */     boolean red;
/*      */     
/*      */     TreeNode(int paramInt, K paramK, V paramV, ConcurrentHashMapV8.Node<K, V> paramNode, TreeNode<K, V> paramTreeNode)
/*      */     {
/* 2543 */       super(paramK, paramV, paramNode);
/* 2544 */       this.parent = paramTreeNode;
/*      */     }
/*      */     
/*      */     ConcurrentHashMapV8.Node<K, V> find(int paramInt, Object paramObject) {
/* 2548 */       return findTreeNode(paramInt, paramObject, null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final TreeNode<K, V> findTreeNode(int paramInt, Object paramObject, Class<?> paramClass)
/*      */     {
/* 2556 */       if (paramObject != null) {
/* 2557 */         Object localObject1 = this;
/*      */         do
/*      */         {
/* 2560 */           TreeNode localTreeNode1 = ((TreeNode)localObject1).left;TreeNode localTreeNode2 = ((TreeNode)localObject1).right;
/* 2561 */           int i; if ((i = ((TreeNode)localObject1).hash) > paramInt) {
/* 2562 */             localObject1 = localTreeNode1;
/* 2563 */           } else if (i < paramInt) {
/* 2564 */             localObject1 = localTreeNode2; } else { Object localObject2;
/* 2565 */             if (((localObject2 = ((TreeNode)localObject1).key) == paramObject) || ((localObject2 != null) && (paramObject.equals(localObject2))))
/* 2566 */               return (TreeNode<K, V>)localObject1;
/* 2567 */             if ((localTreeNode1 == null) && (localTreeNode2 == null)) break;
/*      */             int j;
/* 2569 */             if (((paramClass != null) || ((paramClass = ConcurrentHashMapV8.comparableClassFor(paramObject)) != null)) && ((j = ConcurrentHashMapV8.compareComparables(paramClass, paramObject, localObject2)) != 0))
/*      */             {
/*      */ 
/* 2572 */               localObject1 = j < 0 ? localTreeNode1 : localTreeNode2;
/* 2573 */             } else if (localTreeNode1 == null) {
/* 2574 */               localObject1 = localTreeNode2; } else { TreeNode localTreeNode3;
/* 2575 */               if ((localTreeNode2 == null) || ((localTreeNode3 = localTreeNode2.findTreeNode(paramInt, paramObject, paramClass)) == null))
/*      */               {
/* 2577 */                 localObject1 = localTreeNode1;
/*      */               } else
/* 2579 */                 return localTreeNode3;
/* 2580 */             } } } while (localObject1 != null);
/*      */       }
/* 2582 */       return null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class TreeBin<K, V>
/*      */     extends ConcurrentHashMapV8.Node<K, V>
/*      */   {
/*      */     ConcurrentHashMapV8.TreeNode<K, V> root;
/*      */     
/*      */     volatile ConcurrentHashMapV8.TreeNode<K, V> first;
/*      */     
/*      */     volatile Thread waiter;
/*      */     
/*      */     volatile int lockState;
/*      */     
/*      */     static final int WRITER = 1;
/*      */     
/*      */     static final int WAITER = 2;
/*      */     
/*      */     static final int READER = 4;
/*      */     
/*      */     private static final Unsafe U;
/*      */     private static final long LOCKSTATE;
/*      */     
/*      */     TreeBin(ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode)
/*      */     {
/* 2609 */       super(null, null, null);
/* 2610 */       this.first = paramTreeNode;
/* 2611 */       Object localObject1 = null;
/* 2612 */       ConcurrentHashMapV8.TreeNode localTreeNode; for (Object localObject2 = paramTreeNode; localObject2 != null; localObject2 = localTreeNode) {
/* 2613 */         localTreeNode = (ConcurrentHashMapV8.TreeNode)((ConcurrentHashMapV8.TreeNode)localObject2).next;
/* 2614 */         ((ConcurrentHashMapV8.TreeNode)localObject2).left = (((ConcurrentHashMapV8.TreeNode)localObject2).right = null);
/* 2615 */         if (localObject1 == null) {
/* 2616 */           ((ConcurrentHashMapV8.TreeNode)localObject2).parent = null;
/* 2617 */           ((ConcurrentHashMapV8.TreeNode)localObject2).red = false;
/* 2618 */           localObject1 = localObject2;
/*      */         }
/*      */         else {
/* 2621 */           Object localObject3 = ((ConcurrentHashMapV8.TreeNode)localObject2).key;
/* 2622 */           int i = ((ConcurrentHashMapV8.TreeNode)localObject2).hash;
/* 2623 */           Class localClass = null;
/* 2624 */           Object localObject4 = localObject1;
/*      */           for (;;) { int j;
/* 2626 */             int k; if ((j = ((ConcurrentHashMapV8.TreeNode)localObject4).hash) > i) {
/* 2627 */               k = -1;
/* 2628 */             } else if (j < i) {
/* 2629 */               k = 1;
/* 2630 */             } else if ((localClass != null) || ((localClass = ConcurrentHashMapV8.comparableClassFor(localObject3)) != null))
/*      */             {
/* 2632 */               k = ConcurrentHashMapV8.compareComparables(localClass, localObject3, ((ConcurrentHashMapV8.TreeNode)localObject4).key);
/*      */             } else
/* 2634 */               k = 0;
/* 2635 */             Object localObject5 = localObject4;
/* 2636 */             if ((localObject4 = k <= 0 ? ((ConcurrentHashMapV8.TreeNode)localObject4).left : ((ConcurrentHashMapV8.TreeNode)localObject4).right) == null) {
/* 2637 */               ((ConcurrentHashMapV8.TreeNode)localObject2).parent = ((ConcurrentHashMapV8.TreeNode)localObject5);
/* 2638 */               if (k <= 0) {
/* 2639 */                 ((ConcurrentHashMapV8.TreeNode)localObject5).left = ((ConcurrentHashMapV8.TreeNode)localObject2);
/*      */               } else
/* 2641 */                 ((ConcurrentHashMapV8.TreeNode)localObject5).right = ((ConcurrentHashMapV8.TreeNode)localObject2);
/* 2642 */               localObject1 = balanceInsertion((ConcurrentHashMapV8.TreeNode)localObject1, (ConcurrentHashMapV8.TreeNode)localObject2);
/* 2643 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 2648 */       this.root = ((ConcurrentHashMapV8.TreeNode)localObject1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private final void lockRoot()
/*      */     {
/* 2655 */       if (!U.compareAndSwapInt(this, LOCKSTATE, 0, 1)) {
/* 2656 */         contendedLock();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     private final void unlockRoot()
/*      */     {
/* 2663 */       this.lockState = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private final void contendedLock()
/*      */     {
/* 2670 */       int i = 0;
/*      */       for (;;) { int j;
/* 2672 */         if (((j = this.lockState) & 0x1) == 0) {
/* 2673 */           if (U.compareAndSwapInt(this, LOCKSTATE, j, 1)) {
/* 2674 */             if (i != 0) {
/* 2675 */               this.waiter = null;
/*      */             }
/*      */           }
/*      */         }
/* 2679 */         else if ((j & 0x2) == 0) {
/* 2680 */           if (U.compareAndSwapInt(this, LOCKSTATE, j, j | 0x2)) {
/* 2681 */             i = 1;
/* 2682 */             this.waiter = Thread.currentThread();
/*      */           }
/*      */         }
/* 2685 */         else if (i != 0) {
/* 2686 */           LockSupport.park(this);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final ConcurrentHashMapV8.Node<K, V> find(int paramInt, Object paramObject)
/*      */     {
/* 2696 */       if (paramObject != null) {
/* 2697 */         for (Object localObject1 = this.first; localObject1 != null; localObject1 = ((ConcurrentHashMapV8.Node)localObject1).next) {
/*      */           int i;
/* 2699 */           if (((i = this.lockState) & 0x3) != 0) { Object localObject2;
/* 2700 */             if ((((ConcurrentHashMapV8.Node)localObject1).hash == paramInt) && (((localObject2 = ((ConcurrentHashMapV8.Node)localObject1).key) == paramObject) || ((localObject2 != null) && (paramObject.equals(localObject2)))))
/*      */             {
/* 2702 */               return (ConcurrentHashMapV8.Node<K, V>)localObject1;
/*      */             }
/* 2704 */           } else if (U.compareAndSwapInt(this, LOCKSTATE, i, i + 4)) {
/*      */             ConcurrentHashMapV8.TreeNode localTreeNode2;
/*      */             try {
/*      */               ConcurrentHashMapV8.TreeNode localTreeNode1;
/* 2708 */               localTreeNode2 = (localTreeNode1 = this.root) == null ? null : localTreeNode1.findTreeNode(paramInt, paramObject, null);
/*      */             } finally {
/*      */               int j;
/*      */               Thread localThread1;
/*      */               int k;
/* 2713 */               while (!U.compareAndSwapInt(this, LOCKSTATE, k = this.lockState, k - 4)) {}
/*      */               
/*      */               Thread localThread2;
/* 2716 */               if ((k == 6) && ((localThread2 = this.waiter) != null))
/* 2717 */                 LockSupport.unpark(localThread2);
/*      */             }
/* 2719 */             return localTreeNode2;
/*      */           }
/*      */         }
/*      */       }
/* 2723 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     final ConcurrentHashMapV8.TreeNode<K, V> putTreeVal(int paramInt, K paramK, V paramV)
/*      */     {
/* 2731 */       Class localClass = null;
/* 2732 */       ConcurrentHashMapV8.TreeNode localTreeNode1 = this.root;
/*      */       for (;;) {
/* 2734 */         if (localTreeNode1 == null) {
/* 2735 */           this.first = (this.root = new ConcurrentHashMapV8.TreeNode(paramInt, paramK, paramV, null, null));
/* 2736 */           break; }
/*      */         int i;
/* 2738 */         int j; if ((i = localTreeNode1.hash) > paramInt) {
/* 2739 */           j = -1;
/* 2740 */         } else if (i < paramInt) {
/* 2741 */           j = 1; } else { Object localObject1;
/* 2742 */           if (((localObject1 = localTreeNode1.key) == paramK) || ((localObject1 != null) && (paramK.equals(localObject1))))
/* 2743 */             return localTreeNode1;
/* 2744 */           if (((localClass == null) && ((localClass = ConcurrentHashMapV8.comparableClassFor(paramK)) == null)) || ((j = ConcurrentHashMapV8.compareComparables(localClass, paramK, localObject1)) == 0))
/*      */           {
/*      */ 
/* 2747 */             if (localTreeNode1.left == null) {
/* 2748 */               j = 1; } else { ConcurrentHashMapV8.TreeNode localTreeNode2;
/* 2749 */               ConcurrentHashMapV8.TreeNode localTreeNode3; if (((localTreeNode2 = localTreeNode1.right) == null) || ((localTreeNode3 = localTreeNode2.findTreeNode(paramInt, paramK, localClass)) == null))
/*      */               {
/* 2751 */                 j = -1;
/*      */               } else
/* 2753 */                 return localTreeNode3;
/*      */             } } }
/* 2755 */         ConcurrentHashMapV8.TreeNode localTreeNode4 = localTreeNode1;
/* 2756 */         if ((localTreeNode1 = j < 0 ? localTreeNode1.left : localTreeNode1.right) == null) {
/* 2757 */           ConcurrentHashMapV8.TreeNode localTreeNode5 = this.first;
/* 2758 */           ConcurrentHashMapV8.TreeNode localTreeNode6; this.first = (localTreeNode6 = new ConcurrentHashMapV8.TreeNode(paramInt, paramK, paramV, localTreeNode5, localTreeNode4));
/* 2759 */           if (localTreeNode5 != null)
/* 2760 */             localTreeNode5.prev = localTreeNode6;
/* 2761 */           if (j < 0) {
/* 2762 */             localTreeNode4.left = localTreeNode6;
/*      */           } else
/* 2764 */             localTreeNode4.right = localTreeNode6;
/* 2765 */           if (!localTreeNode4.red) {
/* 2766 */             localTreeNode6.red = true; break;
/*      */           }
/* 2768 */           lockRoot();
/*      */           try {
/* 2770 */             this.root = balanceInsertion(this.root, localTreeNode6);
/*      */           } finally {
/* 2772 */             unlockRoot();
/*      */           }
/*      */           
/* 2775 */           break;
/*      */         }
/*      */       }
/* 2778 */       assert (checkInvariants(this.root));
/* 2779 */       return null;
/*      */     }
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
/*      */     final boolean removeTreeNode(ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode)
/*      */     {
/* 2793 */       ConcurrentHashMapV8.TreeNode localTreeNode1 = (ConcurrentHashMapV8.TreeNode)paramTreeNode.next;
/* 2794 */       ConcurrentHashMapV8.TreeNode localTreeNode2 = paramTreeNode.prev;
/*      */       
/* 2796 */       if (localTreeNode2 == null) {
/* 2797 */         this.first = localTreeNode1;
/*      */       } else
/* 2799 */         localTreeNode2.next = localTreeNode1;
/* 2800 */       if (localTreeNode1 != null)
/* 2801 */         localTreeNode1.prev = localTreeNode2;
/* 2802 */       if (this.first == null) {
/* 2803 */         this.root = null;
/* 2804 */         return true; }
/*      */       Object localObject1;
/* 2806 */       ConcurrentHashMapV8.TreeNode localTreeNode3; if (((localObject1 = this.root) == null) || (((ConcurrentHashMapV8.TreeNode)localObject1).right == null) || ((localTreeNode3 = ((ConcurrentHashMapV8.TreeNode)localObject1).left) == null) || (localTreeNode3.left == null))
/*      */       {
/* 2808 */         return true; }
/* 2809 */       lockRoot();
/*      */       try
/*      */       {
/* 2812 */         ConcurrentHashMapV8.TreeNode localTreeNode4 = paramTreeNode.left;
/* 2813 */         ConcurrentHashMapV8.TreeNode localTreeNode5 = paramTreeNode.right;
/* 2814 */         Object localObject2; Object localObject3; if ((localTreeNode4 != null) && (localTreeNode5 != null)) {
/* 2815 */           localObject2 = localTreeNode5;
/* 2816 */           ConcurrentHashMapV8.TreeNode localTreeNode6; while ((localTreeNode6 = ((ConcurrentHashMapV8.TreeNode)localObject2).left) != null)
/* 2817 */             localObject2 = localTreeNode6;
/* 2818 */           boolean bool = ((ConcurrentHashMapV8.TreeNode)localObject2).red;((ConcurrentHashMapV8.TreeNode)localObject2).red = paramTreeNode.red;paramTreeNode.red = bool;
/* 2819 */           ConcurrentHashMapV8.TreeNode localTreeNode7 = ((ConcurrentHashMapV8.TreeNode)localObject2).right;
/* 2820 */           ConcurrentHashMapV8.TreeNode localTreeNode8 = paramTreeNode.parent;
/* 2821 */           if (localObject2 == localTreeNode5) {
/* 2822 */             paramTreeNode.parent = ((ConcurrentHashMapV8.TreeNode)localObject2);
/* 2823 */             ((ConcurrentHashMapV8.TreeNode)localObject2).right = paramTreeNode;
/*      */           }
/*      */           else {
/* 2826 */             ConcurrentHashMapV8.TreeNode localTreeNode9 = ((ConcurrentHashMapV8.TreeNode)localObject2).parent;
/* 2827 */             if ((paramTreeNode.parent = localTreeNode9) != null) {
/* 2828 */               if (localObject2 == localTreeNode9.left) {
/* 2829 */                 localTreeNode9.left = paramTreeNode;
/*      */               } else
/* 2831 */                 localTreeNode9.right = paramTreeNode;
/*      */             }
/* 2833 */             ((ConcurrentHashMapV8.TreeNode)localObject2).right = localTreeNode5;
/* 2834 */             localTreeNode5.parent = ((ConcurrentHashMapV8.TreeNode)localObject2);
/*      */           }
/* 2836 */           paramTreeNode.left = null;
/* 2837 */           ((ConcurrentHashMapV8.TreeNode)localObject2).left = localTreeNode4;
/* 2838 */           localTreeNode4.parent = ((ConcurrentHashMapV8.TreeNode)localObject2);
/* 2839 */           if ((paramTreeNode.right = localTreeNode7) != null)
/* 2840 */             localTreeNode7.parent = paramTreeNode;
/* 2841 */           if ((((ConcurrentHashMapV8.TreeNode)localObject2).parent = localTreeNode8) == null) {
/* 2842 */             localObject1 = localObject2;
/* 2843 */           } else if (paramTreeNode == localTreeNode8.left) {
/* 2844 */             localTreeNode8.left = ((ConcurrentHashMapV8.TreeNode)localObject2);
/*      */           } else
/* 2846 */             localTreeNode8.right = ((ConcurrentHashMapV8.TreeNode)localObject2);
/* 2847 */           if (localTreeNode7 != null) {
/* 2848 */             localObject3 = localTreeNode7;
/*      */           } else {
/* 2850 */             localObject3 = paramTreeNode;
/*      */           }
/* 2852 */         } else if (localTreeNode4 != null) {
/* 2853 */           localObject3 = localTreeNode4;
/* 2854 */         } else if (localTreeNode5 != null) {
/* 2855 */           localObject3 = localTreeNode5;
/*      */         } else {
/* 2857 */           localObject3 = paramTreeNode; }
/* 2858 */         if (localObject3 != paramTreeNode) {
/* 2859 */           localObject2 = ((ConcurrentHashMapV8.TreeNode)localObject3).parent = paramTreeNode.parent;
/* 2860 */           if (localObject2 == null) {
/* 2861 */             localObject1 = localObject3;
/* 2862 */           } else if (paramTreeNode == ((ConcurrentHashMapV8.TreeNode)localObject2).left) {
/* 2863 */             ((ConcurrentHashMapV8.TreeNode)localObject2).left = ((ConcurrentHashMapV8.TreeNode)localObject3);
/*      */           } else
/* 2865 */             ((ConcurrentHashMapV8.TreeNode)localObject2).right = ((ConcurrentHashMapV8.TreeNode)localObject3);
/* 2866 */           paramTreeNode.left = (paramTreeNode.right = paramTreeNode.parent = null);
/*      */         }
/*      */         
/* 2869 */         this.root = (paramTreeNode.red ? localObject1 : balanceDeletion((ConcurrentHashMapV8.TreeNode)localObject1, (ConcurrentHashMapV8.TreeNode)localObject3));
/*      */         
/* 2871 */         if (paramTreeNode == localObject3)
/*      */         {
/* 2873 */           if ((localObject2 = paramTreeNode.parent) != null) {
/* 2874 */             if (paramTreeNode == ((ConcurrentHashMapV8.TreeNode)localObject2).left) {
/* 2875 */               ((ConcurrentHashMapV8.TreeNode)localObject2).left = null;
/* 2876 */             } else if (paramTreeNode == ((ConcurrentHashMapV8.TreeNode)localObject2).right)
/* 2877 */               ((ConcurrentHashMapV8.TreeNode)localObject2).right = null;
/* 2878 */             paramTreeNode.parent = null;
/*      */           }
/*      */         }
/*      */       } finally {
/* 2882 */         unlockRoot();
/*      */       }
/* 2884 */       assert (checkInvariants(this.root));
/* 2885 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     static <K, V> ConcurrentHashMapV8.TreeNode<K, V> rotateLeft(ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode1, ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode2)
/*      */     {
/*      */       ConcurrentHashMapV8.TreeNode localTreeNode1;
/*      */       
/* 2894 */       if ((paramTreeNode2 != null) && ((localTreeNode1 = paramTreeNode2.right) != null)) { ConcurrentHashMapV8.TreeNode localTreeNode2;
/* 2895 */         if ((localTreeNode2 = paramTreeNode2.right = localTreeNode1.left) != null)
/* 2896 */           localTreeNode2.parent = paramTreeNode2;
/* 2897 */         ConcurrentHashMapV8.TreeNode localTreeNode3; if ((localTreeNode3 = localTreeNode1.parent = paramTreeNode2.parent) == null) {
/* 2898 */           (paramTreeNode1 = localTreeNode1).red = false;
/* 2899 */         } else if (localTreeNode3.left == paramTreeNode2) {
/* 2900 */           localTreeNode3.left = localTreeNode1;
/*      */         } else
/* 2902 */           localTreeNode3.right = localTreeNode1;
/* 2903 */         localTreeNode1.left = paramTreeNode2;
/* 2904 */         paramTreeNode2.parent = localTreeNode1;
/*      */       }
/* 2906 */       return paramTreeNode1;
/*      */     }
/*      */     
/*      */     static <K, V> ConcurrentHashMapV8.TreeNode<K, V> rotateRight(ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode1, ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode2)
/*      */     {
/*      */       ConcurrentHashMapV8.TreeNode localTreeNode1;
/* 2912 */       if ((paramTreeNode2 != null) && ((localTreeNode1 = paramTreeNode2.left) != null)) { ConcurrentHashMapV8.TreeNode localTreeNode2;
/* 2913 */         if ((localTreeNode2 = paramTreeNode2.left = localTreeNode1.right) != null)
/* 2914 */           localTreeNode2.parent = paramTreeNode2;
/* 2915 */         ConcurrentHashMapV8.TreeNode localTreeNode3; if ((localTreeNode3 = localTreeNode1.parent = paramTreeNode2.parent) == null) {
/* 2916 */           (paramTreeNode1 = localTreeNode1).red = false;
/* 2917 */         } else if (localTreeNode3.right == paramTreeNode2) {
/* 2918 */           localTreeNode3.right = localTreeNode1;
/*      */         } else
/* 2920 */           localTreeNode3.left = localTreeNode1;
/* 2921 */         localTreeNode1.right = paramTreeNode2;
/* 2922 */         paramTreeNode2.parent = localTreeNode1;
/*      */       }
/* 2924 */       return paramTreeNode1;
/*      */     }
/*      */     
/*      */     static <K, V> ConcurrentHashMapV8.TreeNode<K, V> balanceInsertion(ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode1, ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode2)
/*      */     {
/* 2929 */       paramTreeNode2.red = true;
/*      */       for (;;) { ConcurrentHashMapV8.TreeNode localTreeNode1;
/* 2931 */         if ((localTreeNode1 = paramTreeNode2.parent) == null) {
/* 2932 */           paramTreeNode2.red = false;
/* 2933 */           return paramTreeNode2; }
/*      */         ConcurrentHashMapV8.TreeNode localTreeNode2;
/* 2935 */         if ((!localTreeNode1.red) || ((localTreeNode2 = localTreeNode1.parent) == null))
/* 2936 */           return paramTreeNode1;
/* 2937 */         ConcurrentHashMapV8.TreeNode localTreeNode3; if (localTreeNode1 == (localTreeNode3 = localTreeNode2.left)) { ConcurrentHashMapV8.TreeNode localTreeNode4;
/* 2938 */           if (((localTreeNode4 = localTreeNode2.right) != null) && (localTreeNode4.red)) {
/* 2939 */             localTreeNode4.red = false;
/* 2940 */             localTreeNode1.red = false;
/* 2941 */             localTreeNode2.red = true;
/* 2942 */             paramTreeNode2 = localTreeNode2;
/*      */           }
/*      */           else {
/* 2945 */             if (paramTreeNode2 == localTreeNode1.right) {
/* 2946 */               paramTreeNode1 = rotateLeft(paramTreeNode1, paramTreeNode2 = localTreeNode1);
/* 2947 */               localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.parent;
/*      */             }
/* 2949 */             if (localTreeNode1 != null) {
/* 2950 */               localTreeNode1.red = false;
/* 2951 */               if (localTreeNode2 != null) {
/* 2952 */                 localTreeNode2.red = true;
/* 2953 */                 paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode2);
/*      */               }
/*      */               
/*      */             }
/*      */           }
/*      */         }
/* 2959 */         else if ((localTreeNode3 != null) && (localTreeNode3.red)) {
/* 2960 */           localTreeNode3.red = false;
/* 2961 */           localTreeNode1.red = false;
/* 2962 */           localTreeNode2.red = true;
/* 2963 */           paramTreeNode2 = localTreeNode2;
/*      */         }
/*      */         else {
/* 2966 */           if (paramTreeNode2 == localTreeNode1.left) {
/* 2967 */             paramTreeNode1 = rotateRight(paramTreeNode1, paramTreeNode2 = localTreeNode1);
/* 2968 */             localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.parent;
/*      */           }
/* 2970 */           if (localTreeNode1 != null) {
/* 2971 */             localTreeNode1.red = false;
/* 2972 */             if (localTreeNode2 != null) {
/* 2973 */               localTreeNode2.red = true;
/* 2974 */               paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode2);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     static <K, V> ConcurrentHashMapV8.TreeNode<K, V> balanceDeletion(ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode1, ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode2)
/*      */     {
/*      */       for (;;)
/*      */       {
/* 2985 */         if ((paramTreeNode2 == null) || (paramTreeNode2 == paramTreeNode1))
/* 2986 */           return paramTreeNode1;
/* 2987 */         ConcurrentHashMapV8.TreeNode localTreeNode1; if ((localTreeNode1 = paramTreeNode2.parent) == null) {
/* 2988 */           paramTreeNode2.red = false;
/* 2989 */           return paramTreeNode2;
/*      */         }
/* 2991 */         if (paramTreeNode2.red) {
/* 2992 */           paramTreeNode2.red = false;
/* 2993 */           return paramTreeNode1; }
/*      */         ConcurrentHashMapV8.TreeNode localTreeNode2;
/* 2995 */         ConcurrentHashMapV8.TreeNode localTreeNode4; ConcurrentHashMapV8.TreeNode localTreeNode5; if ((localTreeNode2 = localTreeNode1.left) == paramTreeNode2) { ConcurrentHashMapV8.TreeNode localTreeNode3;
/* 2996 */           if (((localTreeNode3 = localTreeNode1.right) != null) && (localTreeNode3.red)) {
/* 2997 */             localTreeNode3.red = false;
/* 2998 */             localTreeNode1.red = true;
/* 2999 */             paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode1);
/* 3000 */             localTreeNode3 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.right;
/*      */           }
/* 3002 */           if (localTreeNode3 == null) {
/* 3003 */             paramTreeNode2 = localTreeNode1;
/*      */           } else {
/* 3005 */             localTreeNode4 = localTreeNode3.left;localTreeNode5 = localTreeNode3.right;
/* 3006 */             if (((localTreeNode5 == null) || (!localTreeNode5.red)) && ((localTreeNode4 == null) || (!localTreeNode4.red)))
/*      */             {
/* 3008 */               localTreeNode3.red = true;
/* 3009 */               paramTreeNode2 = localTreeNode1;
/*      */             }
/*      */             else {
/* 3012 */               if ((localTreeNode5 == null) || (!localTreeNode5.red)) {
/* 3013 */                 if (localTreeNode4 != null)
/* 3014 */                   localTreeNode4.red = false;
/* 3015 */                 localTreeNode3.red = true;
/* 3016 */                 paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode3);
/* 3017 */                 localTreeNode3 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.right;
/*      */               }
/*      */               
/* 3020 */               if (localTreeNode3 != null) {
/* 3021 */                 localTreeNode3.red = (localTreeNode1 == null ? false : localTreeNode1.red);
/* 3022 */                 if ((localTreeNode5 = localTreeNode3.right) != null)
/* 3023 */                   localTreeNode5.red = false;
/*      */               }
/* 3025 */               if (localTreeNode1 != null) {
/* 3026 */                 localTreeNode1.red = false;
/* 3027 */                 paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode1);
/*      */               }
/* 3029 */               paramTreeNode2 = paramTreeNode1;
/*      */             }
/*      */           }
/*      */         }
/*      */         else {
/* 3034 */           if ((localTreeNode2 != null) && (localTreeNode2.red)) {
/* 3035 */             localTreeNode2.red = false;
/* 3036 */             localTreeNode1.red = true;
/* 3037 */             paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode1);
/* 3038 */             localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.left;
/*      */           }
/* 3040 */           if (localTreeNode2 == null) {
/* 3041 */             paramTreeNode2 = localTreeNode1;
/*      */           } else {
/* 3043 */             localTreeNode4 = localTreeNode2.left;localTreeNode5 = localTreeNode2.right;
/* 3044 */             if (((localTreeNode4 == null) || (!localTreeNode4.red)) && ((localTreeNode5 == null) || (!localTreeNode5.red)))
/*      */             {
/* 3046 */               localTreeNode2.red = true;
/* 3047 */               paramTreeNode2 = localTreeNode1;
/*      */             }
/*      */             else {
/* 3050 */               if ((localTreeNode4 == null) || (!localTreeNode4.red)) {
/* 3051 */                 if (localTreeNode5 != null)
/* 3052 */                   localTreeNode5.red = false;
/* 3053 */                 localTreeNode2.red = true;
/* 3054 */                 paramTreeNode1 = rotateLeft(paramTreeNode1, localTreeNode2);
/* 3055 */                 localTreeNode2 = (localTreeNode1 = paramTreeNode2.parent) == null ? null : localTreeNode1.left;
/*      */               }
/*      */               
/* 3058 */               if (localTreeNode2 != null) {
/* 3059 */                 localTreeNode2.red = (localTreeNode1 == null ? false : localTreeNode1.red);
/* 3060 */                 if ((localTreeNode4 = localTreeNode2.left) != null)
/* 3061 */                   localTreeNode4.red = false;
/*      */               }
/* 3063 */               if (localTreeNode1 != null) {
/* 3064 */                 localTreeNode1.red = false;
/* 3065 */                 paramTreeNode1 = rotateRight(paramTreeNode1, localTreeNode1);
/*      */               }
/* 3067 */               paramTreeNode2 = paramTreeNode1;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     static <K, V> boolean checkInvariants(ConcurrentHashMapV8.TreeNode<K, V> paramTreeNode)
/*      */     {
/* 3078 */       ConcurrentHashMapV8.TreeNode localTreeNode1 = paramTreeNode.parent;ConcurrentHashMapV8.TreeNode localTreeNode2 = paramTreeNode.left;ConcurrentHashMapV8.TreeNode localTreeNode3 = paramTreeNode.right;
/* 3079 */       ConcurrentHashMapV8.TreeNode localTreeNode4 = paramTreeNode.prev;ConcurrentHashMapV8.TreeNode localTreeNode5 = (ConcurrentHashMapV8.TreeNode)paramTreeNode.next;
/* 3080 */       if ((localTreeNode4 != null) && (localTreeNode4.next != paramTreeNode))
/* 3081 */         return false;
/* 3082 */       if ((localTreeNode5 != null) && (localTreeNode5.prev != paramTreeNode))
/* 3083 */         return false;
/* 3084 */       if ((localTreeNode1 != null) && (paramTreeNode != localTreeNode1.left) && (paramTreeNode != localTreeNode1.right))
/* 3085 */         return false;
/* 3086 */       if ((localTreeNode2 != null) && ((localTreeNode2.parent != paramTreeNode) || (localTreeNode2.hash > paramTreeNode.hash)))
/* 3087 */         return false;
/* 3088 */       if ((localTreeNode3 != null) && ((localTreeNode3.parent != paramTreeNode) || (localTreeNode3.hash < paramTreeNode.hash)))
/* 3089 */         return false;
/* 3090 */       if ((paramTreeNode.red) && (localTreeNode2 != null) && (localTreeNode2.red) && (localTreeNode3 != null) && (localTreeNode3.red))
/* 3091 */         return false;
/* 3092 */       if ((localTreeNode2 != null) && (!checkInvariants(localTreeNode2)))
/* 3093 */         return false;
/* 3094 */       if ((localTreeNode3 != null) && (!checkInvariants(localTreeNode3)))
/* 3095 */         return false;
/* 3096 */       return true;
/*      */     }
/*      */     
/*      */     static
/*      */     {
/*      */       try
/*      */       {
/* 3103 */         U = ConcurrentHashMapV8.access$000();
/* 3104 */         Class localClass = TreeBin.class;
/* 3105 */         LOCKSTATE = U.objectFieldOffset(localClass.getDeclaredField("lockState"));
/*      */       }
/*      */       catch (Exception localException) {
/* 3108 */         throw new Error(localException);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static class Traverser<K, V>
/*      */   {
/*      */     ConcurrentHashMapV8.Node<K, V>[] tab;
/*      */     
/*      */ 
/*      */ 
/*      */     ConcurrentHashMapV8.Node<K, V> next;
/*      */     
/*      */ 
/*      */ 
/*      */     int index;
/*      */     
/*      */ 
/*      */ 
/*      */     int baseIndex;
/*      */     
/*      */ 
/*      */ 
/*      */     int baseLimit;
/*      */     
/*      */ 
/*      */ 
/*      */     final int baseSize;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Traverser(ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/* 3145 */       this.tab = paramArrayOfNode;
/* 3146 */       this.baseSize = paramInt1;
/* 3147 */       this.baseIndex = (this.index = paramInt2);
/* 3148 */       this.baseLimit = paramInt3;
/* 3149 */       this.next = null;
/*      */     }
/*      */     
/*      */ 
/*      */     final ConcurrentHashMapV8.Node<K, V> advance()
/*      */     {
/*      */       Object localObject;
/*      */       
/* 3157 */       if ((localObject = this.next) != null) {
/* 3158 */         localObject = ((ConcurrentHashMapV8.Node)localObject).next;
/*      */       }
/*      */       for (;;) {
/* 3161 */         if (localObject != null)
/* 3162 */           return (ConcurrentHashMapV8.Node<K, V>)(this.next = localObject);
/* 3163 */         ConcurrentHashMapV8.Node[] arrayOfNode; int i; int j; if ((this.baseIndex >= this.baseLimit) || ((arrayOfNode = this.tab) == null) || ((i = arrayOfNode.length) <= (j = this.index)) || (j < 0))
/*      */         {
/* 3165 */           return this.next = null; }
/* 3166 */         if (((localObject = ConcurrentHashMapV8.tabAt(arrayOfNode, this.index)) != null) && (((ConcurrentHashMapV8.Node)localObject).hash < 0)) {
/* 3167 */           if ((localObject instanceof ConcurrentHashMapV8.ForwardingNode)) {
/* 3168 */             this.tab = ((ConcurrentHashMapV8.ForwardingNode)localObject).nextTable;
/* 3169 */             localObject = null;
/* 3170 */             continue;
/*      */           }
/* 3172 */           if ((localObject instanceof ConcurrentHashMapV8.TreeBin)) {
/* 3173 */             localObject = ((ConcurrentHashMapV8.TreeBin)localObject).first;
/*      */           } else
/* 3175 */             localObject = null;
/*      */         }
/* 3177 */         if (this.index += this.baseSize >= i) {
/* 3178 */           this.index = (++this.baseIndex);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static class BaseIterator<K, V>
/*      */     extends ConcurrentHashMapV8.Traverser<K, V>
/*      */   {
/*      */     final ConcurrentHashMapV8<K, V> map;
/*      */     ConcurrentHashMapV8.Node<K, V> lastReturned;
/*      */     
/*      */     BaseIterator(ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8)
/*      */     {
/* 3192 */       super(paramInt1, paramInt2, paramInt3);
/* 3193 */       this.map = paramConcurrentHashMapV8;
/* 3194 */       advance();
/*      */     }
/*      */     
/* 3197 */     public final boolean hasNext() { return this.next != null; }
/* 3198 */     public final boolean hasMoreElements() { return this.next != null; }
/*      */     
/*      */     public final void remove() {
/*      */       ConcurrentHashMapV8.Node localNode;
/* 3202 */       if ((localNode = this.lastReturned) == null)
/* 3203 */         throw new IllegalStateException();
/* 3204 */       this.lastReturned = null;
/* 3205 */       this.map.replaceNode(localNode.key, null, null);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeyIterator<K, V> extends ConcurrentHashMapV8.BaseIterator<K, V> implements Iterator<K>, Enumeration<K>
/*      */   {
/*      */     KeyIterator(ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8)
/*      */     {
/* 3213 */       super(paramInt1, paramInt2, paramInt3, paramConcurrentHashMapV8);
/*      */     }
/*      */     
/*      */     public final K next() {
/*      */       ConcurrentHashMapV8.Node localNode;
/* 3218 */       if ((localNode = this.next) == null)
/* 3219 */         throw new NoSuchElementException();
/* 3220 */       Object localObject = localNode.key;
/* 3221 */       this.lastReturned = localNode;
/* 3222 */       advance();
/* 3223 */       return (K)localObject;
/*      */     }
/*      */     
/* 3226 */     public final K nextElement() { return (K)next(); }
/*      */   }
/*      */   
/*      */   static final class ValueIterator<K, V> extends ConcurrentHashMapV8.BaseIterator<K, V> implements Iterator<V>, Enumeration<V>
/*      */   {
/*      */     ValueIterator(ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8)
/*      */     {
/* 3233 */       super(paramInt1, paramInt2, paramInt3, paramConcurrentHashMapV8);
/*      */     }
/*      */     
/*      */     public final V next() {
/*      */       ConcurrentHashMapV8.Node localNode;
/* 3238 */       if ((localNode = this.next) == null)
/* 3239 */         throw new NoSuchElementException();
/* 3240 */       Object localObject = localNode.val;
/* 3241 */       this.lastReturned = localNode;
/* 3242 */       advance();
/* 3243 */       return (V)localObject;
/*      */     }
/*      */     
/* 3246 */     public final V nextElement() { return (V)next(); }
/*      */   }
/*      */   
/*      */   static final class EntryIterator<K, V> extends ConcurrentHashMapV8.BaseIterator<K, V> implements Iterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntryIterator(ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8)
/*      */     {
/* 3253 */       super(paramInt1, paramInt2, paramInt3, paramConcurrentHashMapV8);
/*      */     }
/*      */     
/*      */     public final Map.Entry<K, V> next() {
/*      */       ConcurrentHashMapV8.Node localNode;
/* 3258 */       if ((localNode = this.next) == null)
/* 3259 */         throw new NoSuchElementException();
/* 3260 */       Object localObject1 = localNode.key;
/* 3261 */       Object localObject2 = localNode.val;
/* 3262 */       this.lastReturned = localNode;
/* 3263 */       advance();
/* 3264 */       return new ConcurrentHashMapV8.MapEntry(localObject1, localObject2, this.map);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class MapEntry<K, V> implements Map.Entry<K, V>
/*      */   {
/*      */     final K key;
/*      */     V val;
/*      */     final ConcurrentHashMapV8<K, V> map;
/*      */     
/*      */     MapEntry(K paramK, V paramV, ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8)
/*      */     {
/* 3276 */       this.key = paramK;
/* 3277 */       this.val = paramV;
/* 3278 */       this.map = paramConcurrentHashMapV8; }
/*      */     
/* 3280 */     public K getKey() { return (K)this.key; }
/* 3281 */     public V getValue() { return (V)this.val; }
/* 3282 */     public int hashCode() { return this.key.hashCode() ^ this.val.hashCode(); }
/* 3283 */     public String toString() { return this.key + "=" + this.val; }
/*      */     
/*      */     public boolean equals(Object paramObject) { Map.Entry localEntry;
/*      */       Object localObject1;
/* 3287 */       Object localObject2; return ((paramObject instanceof Map.Entry)) && ((localObject1 = (localEntry = (Map.Entry)paramObject).getKey()) != null) && ((localObject2 = localEntry.getValue()) != null) && ((localObject1 == this.key) || (localObject1.equals(this.key))) && ((localObject2 == this.val) || (localObject2.equals(this.val)));
/*      */     }
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
/*      */     public V setValue(V paramV)
/*      */     {
/* 3303 */       if (paramV == null) throw new NullPointerException();
/* 3304 */       Object localObject = this.val;
/* 3305 */       this.val = paramV;
/* 3306 */       this.map.put(this.key, paramV);
/* 3307 */       return (V)localObject;
/*      */     }
/*      */   }
/*      */   
/*      */   static final class KeySpliterator<K, V> extends ConcurrentHashMapV8.Traverser<K, V> implements ConcurrentHashMapV8.ConcurrentHashMapSpliterator<K>
/*      */   {
/*      */     long est;
/*      */     
/*      */     KeySpliterator(ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, long paramLong) {
/* 3316 */       super(paramInt1, paramInt2, paramInt3);
/* 3317 */       this.est = paramLong; }
/*      */     
/*      */     public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<K> trySplit() { int i;
/*      */       int j;
/*      */       int k;
/* 3322 */       return (k = (i = this.baseIndex) + (j = this.baseLimit) >>> 1) <= i ? null : new KeySpliterator(this.tab, this.baseSize, this.baseLimit = k, j, this.est >>>= 1);
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(ConcurrentHashMapV8.Action<? super K> paramAction)
/*      */     {
/* 3328 */       if (paramAction == null) throw new NullPointerException();
/* 3329 */       ConcurrentHashMapV8.Node localNode; while ((localNode = advance()) != null)
/* 3330 */         paramAction.apply(localNode.key);
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(ConcurrentHashMapV8.Action<? super K> paramAction) {
/* 3334 */       if (paramAction == null) throw new NullPointerException();
/*      */       ConcurrentHashMapV8.Node localNode;
/* 3336 */       if ((localNode = advance()) == null)
/* 3337 */         return false;
/* 3338 */       paramAction.apply(localNode.key);
/* 3339 */       return true;
/*      */     }
/*      */     
/* 3342 */     public long estimateSize() { return this.est; }
/*      */   }
/*      */   
/*      */   static final class ValueSpliterator<K, V> extends ConcurrentHashMapV8.Traverser<K, V> implements ConcurrentHashMapV8.ConcurrentHashMapSpliterator<V>
/*      */   {
/*      */     long est;
/*      */     
/*      */     ValueSpliterator(ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, long paramLong)
/*      */     {
/* 3351 */       super(paramInt1, paramInt2, paramInt3);
/* 3352 */       this.est = paramLong; }
/*      */     
/*      */     public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<V> trySplit() { int i;
/*      */       int j;
/*      */       int k;
/* 3357 */       return (k = (i = this.baseIndex) + (j = this.baseLimit) >>> 1) <= i ? null : new ValueSpliterator(this.tab, this.baseSize, this.baseLimit = k, j, this.est >>>= 1);
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(ConcurrentHashMapV8.Action<? super V> paramAction)
/*      */     {
/* 3363 */       if (paramAction == null) throw new NullPointerException();
/* 3364 */       ConcurrentHashMapV8.Node localNode; while ((localNode = advance()) != null)
/* 3365 */         paramAction.apply(localNode.val);
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(ConcurrentHashMapV8.Action<? super V> paramAction) {
/* 3369 */       if (paramAction == null) throw new NullPointerException();
/*      */       ConcurrentHashMapV8.Node localNode;
/* 3371 */       if ((localNode = advance()) == null)
/* 3372 */         return false;
/* 3373 */       paramAction.apply(localNode.val);
/* 3374 */       return true;
/*      */     }
/*      */     
/* 3377 */     public long estimateSize() { return this.est; }
/*      */   }
/*      */   
/*      */   static final class EntrySpliterator<K, V> extends ConcurrentHashMapV8.Traverser<K, V> implements ConcurrentHashMapV8.ConcurrentHashMapSpliterator<Map.Entry<K, V>>
/*      */   {
/*      */     final ConcurrentHashMapV8<K, V> map;
/*      */     long est;
/*      */     
/*      */     EntrySpliterator(ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, int paramInt1, int paramInt2, int paramInt3, long paramLong, ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8)
/*      */     {
/* 3387 */       super(paramInt1, paramInt2, paramInt3);
/* 3388 */       this.map = paramConcurrentHashMapV8;
/* 3389 */       this.est = paramLong; }
/*      */     
/*      */     public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<Map.Entry<K, V>> trySplit() { int i;
/*      */       int j;
/*      */       int k;
/* 3394 */       return (k = (i = this.baseIndex) + (j = this.baseLimit) >>> 1) <= i ? null : new EntrySpliterator(this.tab, this.baseSize, this.baseLimit = k, j, this.est >>>= 1, this.map);
/*      */     }
/*      */     
/*      */ 
/*      */     public void forEachRemaining(ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> paramAction)
/*      */     {
/* 3400 */       if (paramAction == null) throw new NullPointerException();
/* 3401 */       ConcurrentHashMapV8.Node localNode; while ((localNode = advance()) != null)
/* 3402 */         paramAction.apply(new ConcurrentHashMapV8.MapEntry(localNode.key, localNode.val, this.map));
/*      */     }
/*      */     
/*      */     public boolean tryAdvance(ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> paramAction) {
/* 3406 */       if (paramAction == null) throw new NullPointerException();
/*      */       ConcurrentHashMapV8.Node localNode;
/* 3408 */       if ((localNode = advance()) == null)
/* 3409 */         return false;
/* 3410 */       paramAction.apply(new ConcurrentHashMapV8.MapEntry(localNode.key, localNode.val, this.map));
/* 3411 */       return true;
/*      */     }
/*      */     
/* 3414 */     public long estimateSize() { return this.est; }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int batchFor(long paramLong)
/*      */   {
/*      */     long l;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3430 */     if ((paramLong == Long.MAX_VALUE) || ((l = sumCount()) <= 1L) || (l < paramLong))
/* 3431 */       return 0;
/* 3432 */     int i = ForkJoinPool.getCommonPoolParallelism() << 2;
/* 3433 */     return (paramLong <= 0L) || (l /= paramLong >= i) ? i : (int)l;
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
/*      */   public void forEach(long paramLong, BiAction<? super K, ? super V> paramBiAction)
/*      */   {
/* 3446 */     if (paramBiAction == null) throw new NullPointerException();
/* 3447 */     new ForEachMappingTask(null, batchFor(paramLong), 0, 0, this.table, paramBiAction).invoke();
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
/*      */ 
/*      */ 
/*      */   public <U> void forEach(long paramLong, BiFun<? super K, ? super V, ? extends U> paramBiFun, Action<? super U> paramAction)
/*      */   {
/* 3467 */     if ((paramBiFun == null) || (paramAction == null))
/* 3468 */       throw new NullPointerException();
/* 3469 */     new ForEachTransformedMappingTask(null, batchFor(paramLong), 0, 0, this.table, paramBiFun, paramAction).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U search(long paramLong, BiFun<? super K, ? super V, ? extends U> paramBiFun)
/*      */   {
/* 3491 */     if (paramBiFun == null) throw new NullPointerException();
/* 3492 */     return (U)new SearchMappingsTask(null, batchFor(paramLong), 0, 0, this.table, paramBiFun, new AtomicReference()).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U reduce(long paramLong, BiFun<? super K, ? super V, ? extends U> paramBiFun, BiFun<? super U, ? super U, ? extends U> paramBiFun1)
/*      */   {
/* 3515 */     if ((paramBiFun == null) || (paramBiFun1 == null))
/* 3516 */       throw new NullPointerException();
/* 3517 */     return (U)new MapReduceMappingsTask(null, batchFor(paramLong), 0, 0, this.table, null, paramBiFun, paramBiFun1).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double reduceToDouble(long paramLong, ObjectByObjectToDouble<? super K, ? super V> paramObjectByObjectToDouble, double paramDouble, DoubleByDoubleToDouble paramDoubleByDoubleToDouble)
/*      */   {
/* 3541 */     if ((paramObjectByObjectToDouble == null) || (paramDoubleByDoubleToDouble == null))
/* 3542 */       throw new NullPointerException();
/* 3543 */     return ((Double)new MapReduceMappingsToDoubleTask(null, batchFor(paramLong), 0, 0, this.table, null, paramObjectByObjectToDouble, paramDouble, paramDoubleByDoubleToDouble).invoke()).doubleValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long reduceToLong(long paramLong1, ObjectByObjectToLong<? super K, ? super V> paramObjectByObjectToLong, long paramLong2, LongByLongToLong paramLongByLongToLong)
/*      */   {
/* 3567 */     if ((paramObjectByObjectToLong == null) || (paramLongByLongToLong == null))
/* 3568 */       throw new NullPointerException();
/* 3569 */     return ((Long)new MapReduceMappingsToLongTask(null, batchFor(paramLong1), 0, 0, this.table, null, paramObjectByObjectToLong, paramLong2, paramLongByLongToLong).invoke()).longValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int reduceToInt(long paramLong, ObjectByObjectToInt<? super K, ? super V> paramObjectByObjectToInt, int paramInt, IntByIntToInt paramIntByIntToInt)
/*      */   {
/* 3593 */     if ((paramObjectByObjectToInt == null) || (paramIntByIntToInt == null))
/* 3594 */       throw new NullPointerException();
/* 3595 */     return ((Integer)new MapReduceMappingsToIntTask(null, batchFor(paramLong), 0, 0, this.table, null, paramObjectByObjectToInt, paramInt, paramIntByIntToInt).invoke()).intValue();
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
/*      */   public void forEachKey(long paramLong, Action<? super K> paramAction)
/*      */   {
/* 3610 */     if (paramAction == null) throw new NullPointerException();
/* 3611 */     new ForEachKeyTask(null, batchFor(paramLong), 0, 0, this.table, paramAction).invoke();
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
/*      */ 
/*      */ 
/*      */   public <U> void forEachKey(long paramLong, Fun<? super K, ? extends U> paramFun, Action<? super U> paramAction)
/*      */   {
/* 3631 */     if ((paramFun == null) || (paramAction == null))
/* 3632 */       throw new NullPointerException();
/* 3633 */     new ForEachTransformedKeyTask(null, batchFor(paramLong), 0, 0, this.table, paramFun, paramAction).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U searchKeys(long paramLong, Fun<? super K, ? extends U> paramFun)
/*      */   {
/* 3655 */     if (paramFun == null) throw new NullPointerException();
/* 3656 */     return (U)new SearchKeysTask(null, batchFor(paramLong), 0, 0, this.table, paramFun, new AtomicReference()).invoke();
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
/*      */   public K reduceKeys(long paramLong, BiFun<? super K, ? super K, ? extends K> paramBiFun)
/*      */   {
/* 3674 */     if (paramBiFun == null) throw new NullPointerException();
/* 3675 */     return (K)new ReduceKeysTask(null, batchFor(paramLong), 0, 0, this.table, null, paramBiFun).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U reduceKeys(long paramLong, Fun<? super K, ? extends U> paramFun, BiFun<? super U, ? super U, ? extends U> paramBiFun)
/*      */   {
/* 3698 */     if ((paramFun == null) || (paramBiFun == null))
/* 3699 */       throw new NullPointerException();
/* 3700 */     return (U)new MapReduceKeysTask(null, batchFor(paramLong), 0, 0, this.table, null, paramFun, paramBiFun).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double reduceKeysToDouble(long paramLong, ObjectToDouble<? super K> paramObjectToDouble, double paramDouble, DoubleByDoubleToDouble paramDoubleByDoubleToDouble)
/*      */   {
/* 3724 */     if ((paramObjectToDouble == null) || (paramDoubleByDoubleToDouble == null))
/* 3725 */       throw new NullPointerException();
/* 3726 */     return ((Double)new MapReduceKeysToDoubleTask(null, batchFor(paramLong), 0, 0, this.table, null, paramObjectToDouble, paramDouble, paramDoubleByDoubleToDouble).invoke()).doubleValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long reduceKeysToLong(long paramLong1, ObjectToLong<? super K> paramObjectToLong, long paramLong2, LongByLongToLong paramLongByLongToLong)
/*      */   {
/* 3750 */     if ((paramObjectToLong == null) || (paramLongByLongToLong == null))
/* 3751 */       throw new NullPointerException();
/* 3752 */     return ((Long)new MapReduceKeysToLongTask(null, batchFor(paramLong1), 0, 0, this.table, null, paramObjectToLong, paramLong2, paramLongByLongToLong).invoke()).longValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int reduceKeysToInt(long paramLong, ObjectToInt<? super K> paramObjectToInt, int paramInt, IntByIntToInt paramIntByIntToInt)
/*      */   {
/* 3776 */     if ((paramObjectToInt == null) || (paramIntByIntToInt == null))
/* 3777 */       throw new NullPointerException();
/* 3778 */     return ((Integer)new MapReduceKeysToIntTask(null, batchFor(paramLong), 0, 0, this.table, null, paramObjectToInt, paramInt, paramIntByIntToInt).invoke()).intValue();
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
/*      */   public void forEachValue(long paramLong, Action<? super V> paramAction)
/*      */   {
/* 3793 */     if (paramAction == null)
/* 3794 */       throw new NullPointerException();
/* 3795 */     new ForEachValueTask(null, batchFor(paramLong), 0, 0, this.table, paramAction).invoke();
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
/*      */ 
/*      */ 
/*      */   public <U> void forEachValue(long paramLong, Fun<? super V, ? extends U> paramFun, Action<? super U> paramAction)
/*      */   {
/* 3815 */     if ((paramFun == null) || (paramAction == null))
/* 3816 */       throw new NullPointerException();
/* 3817 */     new ForEachTransformedValueTask(null, batchFor(paramLong), 0, 0, this.table, paramFun, paramAction).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U searchValues(long paramLong, Fun<? super V, ? extends U> paramFun)
/*      */   {
/* 3839 */     if (paramFun == null) throw new NullPointerException();
/* 3840 */     return (U)new SearchValuesTask(null, batchFor(paramLong), 0, 0, this.table, paramFun, new AtomicReference()).invoke();
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
/*      */   public V reduceValues(long paramLong, BiFun<? super V, ? super V, ? extends V> paramBiFun)
/*      */   {
/* 3857 */     if (paramBiFun == null) throw new NullPointerException();
/* 3858 */     return (V)new ReduceValuesTask(null, batchFor(paramLong), 0, 0, this.table, null, paramBiFun).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U reduceValues(long paramLong, Fun<? super V, ? extends U> paramFun, BiFun<? super U, ? super U, ? extends U> paramBiFun)
/*      */   {
/* 3881 */     if ((paramFun == null) || (paramBiFun == null))
/* 3882 */       throw new NullPointerException();
/* 3883 */     return (U)new MapReduceValuesTask(null, batchFor(paramLong), 0, 0, this.table, null, paramFun, paramBiFun).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double reduceValuesToDouble(long paramLong, ObjectToDouble<? super V> paramObjectToDouble, double paramDouble, DoubleByDoubleToDouble paramDoubleByDoubleToDouble)
/*      */   {
/* 3907 */     if ((paramObjectToDouble == null) || (paramDoubleByDoubleToDouble == null))
/* 3908 */       throw new NullPointerException();
/* 3909 */     return ((Double)new MapReduceValuesToDoubleTask(null, batchFor(paramLong), 0, 0, this.table, null, paramObjectToDouble, paramDouble, paramDoubleByDoubleToDouble).invoke()).doubleValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long reduceValuesToLong(long paramLong1, ObjectToLong<? super V> paramObjectToLong, long paramLong2, LongByLongToLong paramLongByLongToLong)
/*      */   {
/* 3933 */     if ((paramObjectToLong == null) || (paramLongByLongToLong == null))
/* 3934 */       throw new NullPointerException();
/* 3935 */     return ((Long)new MapReduceValuesToLongTask(null, batchFor(paramLong1), 0, 0, this.table, null, paramObjectToLong, paramLong2, paramLongByLongToLong).invoke()).longValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int reduceValuesToInt(long paramLong, ObjectToInt<? super V> paramObjectToInt, int paramInt, IntByIntToInt paramIntByIntToInt)
/*      */   {
/* 3959 */     if ((paramObjectToInt == null) || (paramIntByIntToInt == null))
/* 3960 */       throw new NullPointerException();
/* 3961 */     return ((Integer)new MapReduceValuesToIntTask(null, batchFor(paramLong), 0, 0, this.table, null, paramObjectToInt, paramInt, paramIntByIntToInt).invoke()).intValue();
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
/*      */   public void forEachEntry(long paramLong, Action<? super Map.Entry<K, V>> paramAction)
/*      */   {
/* 3976 */     if (paramAction == null) throw new NullPointerException();
/* 3977 */     new ForEachEntryTask(null, batchFor(paramLong), 0, 0, this.table, paramAction).invoke();
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
/*      */ 
/*      */   public <U> void forEachEntry(long paramLong, Fun<Map.Entry<K, V>, ? extends U> paramFun, Action<? super U> paramAction)
/*      */   {
/* 3996 */     if ((paramFun == null) || (paramAction == null))
/* 3997 */       throw new NullPointerException();
/* 3998 */     new ForEachTransformedEntryTask(null, batchFor(paramLong), 0, 0, this.table, paramFun, paramAction).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U searchEntries(long paramLong, Fun<Map.Entry<K, V>, ? extends U> paramFun)
/*      */   {
/* 4020 */     if (paramFun == null) throw new NullPointerException();
/* 4021 */     return (U)new SearchEntriesTask(null, batchFor(paramLong), 0, 0, this.table, paramFun, new AtomicReference()).invoke();
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
/*      */   public Map.Entry<K, V> reduceEntries(long paramLong, BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> paramBiFun)
/*      */   {
/* 4038 */     if (paramBiFun == null) throw new NullPointerException();
/* 4039 */     return (Map.Entry)new ReduceEntriesTask(null, batchFor(paramLong), 0, 0, this.table, null, paramBiFun).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <U> U reduceEntries(long paramLong, Fun<Map.Entry<K, V>, ? extends U> paramFun, BiFun<? super U, ? super U, ? extends U> paramBiFun)
/*      */   {
/* 4062 */     if ((paramFun == null) || (paramBiFun == null))
/* 4063 */       throw new NullPointerException();
/* 4064 */     return (U)new MapReduceEntriesTask(null, batchFor(paramLong), 0, 0, this.table, null, paramFun, paramBiFun).invoke();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double reduceEntriesToDouble(long paramLong, ObjectToDouble<Map.Entry<K, V>> paramObjectToDouble, double paramDouble, DoubleByDoubleToDouble paramDoubleByDoubleToDouble)
/*      */   {
/* 4088 */     if ((paramObjectToDouble == null) || (paramDoubleByDoubleToDouble == null))
/* 4089 */       throw new NullPointerException();
/* 4090 */     return ((Double)new MapReduceEntriesToDoubleTask(null, batchFor(paramLong), 0, 0, this.table, null, paramObjectToDouble, paramDouble, paramDoubleByDoubleToDouble).invoke()).doubleValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long reduceEntriesToLong(long paramLong1, ObjectToLong<Map.Entry<K, V>> paramObjectToLong, long paramLong2, LongByLongToLong paramLongByLongToLong)
/*      */   {
/* 4114 */     if ((paramObjectToLong == null) || (paramLongByLongToLong == null))
/* 4115 */       throw new NullPointerException();
/* 4116 */     return ((Long)new MapReduceEntriesToLongTask(null, batchFor(paramLong1), 0, 0, this.table, null, paramObjectToLong, paramLong2, paramLongByLongToLong).invoke()).longValue();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int reduceEntriesToInt(long paramLong, ObjectToInt<Map.Entry<K, V>> paramObjectToInt, int paramInt, IntByIntToInt paramIntByIntToInt)
/*      */   {
/* 4140 */     if ((paramObjectToInt == null) || (paramIntByIntToInt == null))
/* 4141 */       throw new NullPointerException();
/* 4142 */     return ((Integer)new MapReduceEntriesToIntTask(null, batchFor(paramLong), 0, 0, this.table, null, paramObjectToInt, paramInt, paramIntByIntToInt).invoke()).intValue();
/*      */   }
/*      */   
/*      */ 
/*      */   static abstract class CollectionView<K, V, E>
/*      */     implements Collection<E>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7249069246763182397L;
/*      */     
/*      */     final ConcurrentHashMapV8<K, V> map;
/*      */     
/*      */     private static final String oomeMsg = "Required array size too large";
/*      */     
/*      */     CollectionView(ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8)
/*      */     {
/* 4157 */       this.map = paramConcurrentHashMapV8;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public ConcurrentHashMapV8<K, V> getMap()
/*      */     {
/* 4164 */       return this.map;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4170 */     public final void clear() { this.map.clear(); }
/* 4171 */     public final int size() { return this.map.size(); }
/* 4172 */     public final boolean isEmpty() { return this.map.isEmpty(); }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract Iterator<E> iterator();
/*      */     
/*      */ 
/*      */ 
/*      */     public abstract boolean contains(Object paramObject);
/*      */     
/*      */ 
/*      */ 
/*      */     public abstract boolean remove(Object paramObject);
/*      */     
/*      */ 
/*      */ 
/*      */     public final Object[] toArray()
/*      */     {
/* 4191 */       long l = this.map.mappingCount();
/* 4192 */       if (l > 2147483639L)
/* 4193 */         throw new OutOfMemoryError("Required array size too large");
/* 4194 */       int i = (int)l;
/* 4195 */       Object[] arrayOfObject = new Object[i];
/* 4196 */       int j = 0;
/* 4197 */       for (Object localObject : this) {
/* 4198 */         if (j == i) {
/* 4199 */           if (i >= 2147483639)
/* 4200 */             throw new OutOfMemoryError("Required array size too large");
/* 4201 */           if (i >= 1073741819) {
/* 4202 */             i = 2147483639;
/*      */           } else
/* 4204 */             i += (i >>> 1) + 1;
/* 4205 */           arrayOfObject = Arrays.copyOf(arrayOfObject, i);
/*      */         }
/* 4207 */         arrayOfObject[(j++)] = localObject;
/*      */       }
/* 4209 */       return j == i ? arrayOfObject : Arrays.copyOf(arrayOfObject, j);
/*      */     }
/*      */     
/*      */     public final <T> T[] toArray(T[] paramArrayOfT)
/*      */     {
/* 4214 */       long l = this.map.mappingCount();
/* 4215 */       if (l > 2147483639L)
/* 4216 */         throw new OutOfMemoryError("Required array size too large");
/* 4217 */       int i = (int)l;
/* 4218 */       Object[] arrayOfObject = paramArrayOfT.length >= i ? paramArrayOfT : (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), i);
/*      */       
/*      */ 
/* 4221 */       int j = arrayOfObject.length;
/* 4222 */       int k = 0;
/* 4223 */       for (Object localObject : this) {
/* 4224 */         if (k == j) {
/* 4225 */           if (j >= 2147483639)
/* 4226 */             throw new OutOfMemoryError("Required array size too large");
/* 4227 */           if (j >= 1073741819) {
/* 4228 */             j = 2147483639;
/*      */           } else
/* 4230 */             j += (j >>> 1) + 1;
/* 4231 */           arrayOfObject = Arrays.copyOf(arrayOfObject, j);
/*      */         }
/* 4233 */         arrayOfObject[(k++)] = localObject;
/*      */       }
/* 4235 */       if ((paramArrayOfT == arrayOfObject) && (k < j)) {
/* 4236 */         arrayOfObject[k] = null;
/* 4237 */         return arrayOfObject;
/*      */       }
/* 4239 */       return k == j ? arrayOfObject : Arrays.copyOf(arrayOfObject, k);
/*      */     }
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
/*      */     public final String toString()
/*      */     {
/* 4254 */       StringBuilder localStringBuilder = new StringBuilder();
/* 4255 */       localStringBuilder.append('[');
/* 4256 */       Iterator localIterator = iterator();
/* 4257 */       if (localIterator.hasNext()) {
/*      */         for (;;) {
/* 4259 */           Object localObject = localIterator.next();
/* 4260 */           localStringBuilder.append(localObject == this ? "(this Collection)" : localObject);
/* 4261 */           if (!localIterator.hasNext())
/*      */             break;
/* 4263 */           localStringBuilder.append(',').append(' ');
/*      */         }
/*      */       }
/* 4266 */       return ']';
/*      */     }
/*      */     
/*      */     public final boolean containsAll(Collection<?> paramCollection) {
/* 4270 */       if (paramCollection != this) {
/* 4271 */         for (Object localObject : paramCollection) {
/* 4272 */           if ((localObject == null) || (!contains(localObject)))
/* 4273 */             return false;
/*      */         }
/*      */       }
/* 4276 */       return true;
/*      */     }
/*      */     
/*      */     public final boolean removeAll(Collection<?> paramCollection) {
/* 4280 */       boolean bool = false;
/* 4281 */       for (Iterator localIterator = iterator(); localIterator.hasNext();) {
/* 4282 */         if (paramCollection.contains(localIterator.next())) {
/* 4283 */           localIterator.remove();
/* 4284 */           bool = true;
/*      */         }
/*      */       }
/* 4287 */       return bool;
/*      */     }
/*      */     
/*      */     public final boolean retainAll(Collection<?> paramCollection) {
/* 4291 */       boolean bool = false;
/* 4292 */       for (Iterator localIterator = iterator(); localIterator.hasNext();) {
/* 4293 */         if (!paramCollection.contains(localIterator.next())) {
/* 4294 */           localIterator.remove();
/* 4295 */           bool = true;
/*      */         }
/*      */       }
/* 4298 */       return bool;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public static class KeySetView<K, V>
/*      */     extends ConcurrentHashMapV8.CollectionView<K, V, K>
/*      */     implements Set<K>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7249069246763182397L;
/*      */     
/*      */ 
/*      */ 
/*      */     private final V value;
/*      */     
/*      */ 
/*      */ 
/*      */     KeySetView(ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8, V paramV)
/*      */     {
/* 4319 */       super();
/* 4320 */       this.value = paramV;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public V getMappedValue()
/*      */     {
/* 4330 */       return (V)this.value;
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean contains(Object paramObject)
/*      */     {
/* 4336 */       return this.map.containsKey(paramObject);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean remove(Object paramObject)
/*      */     {
/* 4347 */       return this.map.remove(paramObject) != null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public Iterator<K> iterator()
/*      */     {
/* 4354 */       ConcurrentHashMapV8 localConcurrentHashMapV8 = this.map;
/* 4355 */       ConcurrentHashMapV8.Node[] arrayOfNode; int i = (arrayOfNode = localConcurrentHashMapV8.table) == null ? 0 : arrayOfNode.length;
/* 4356 */       return new ConcurrentHashMapV8.KeyIterator(arrayOfNode, i, 0, i, localConcurrentHashMapV8);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public boolean add(K paramK)
/*      */     {
/*      */       Object localObject;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 4371 */       if ((localObject = this.value) == null)
/* 4372 */         throw new UnsupportedOperationException();
/* 4373 */       return this.map.putVal(paramK, localObject, true) == null;
/*      */     }
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
/*      */     public boolean addAll(Collection<? extends K> paramCollection)
/*      */     {
/* 4388 */       boolean bool = false;
/*      */       Object localObject1;
/* 4390 */       if ((localObject1 = this.value) == null)
/* 4391 */         throw new UnsupportedOperationException();
/* 4392 */       for (Object localObject2 : paramCollection) {
/* 4393 */         if (this.map.putVal(localObject2, localObject1, true) == null)
/* 4394 */           bool = true;
/*      */       }
/* 4396 */       return bool;
/*      */     }
/*      */     
/*      */     public int hashCode() {
/* 4400 */       int i = 0;
/* 4401 */       for (Object localObject : this)
/* 4402 */         i += localObject.hashCode();
/* 4403 */       return i;
/*      */     }
/*      */     
/*      */     public boolean equals(Object paramObject) {
/*      */       Set localSet;
/* 4408 */       return ((paramObject instanceof Set)) && (((localSet = (Set)paramObject) == this) || ((containsAll(localSet)) && (localSet.containsAll(this))));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<K> spliterator166()
/*      */     {
/* 4415 */       ConcurrentHashMapV8 localConcurrentHashMapV8 = this.map;
/* 4416 */       long l = localConcurrentHashMapV8.sumCount();
/* 4417 */       ConcurrentHashMapV8.Node[] arrayOfNode; int i = (arrayOfNode = localConcurrentHashMapV8.table) == null ? 0 : arrayOfNode.length;
/* 4418 */       return new ConcurrentHashMapV8.KeySpliterator(arrayOfNode, i, 0, i, l < 0L ? 0L : l);
/*      */     }
/*      */     
/*      */     public void forEach(ConcurrentHashMapV8.Action<? super K> paramAction) {
/* 4422 */       if (paramAction == null) throw new NullPointerException();
/*      */       ConcurrentHashMapV8.Node[] arrayOfNode;
/* 4424 */       if ((arrayOfNode = this.map.table) != null) {
/* 4425 */         ConcurrentHashMapV8.Traverser localTraverser = new ConcurrentHashMapV8.Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 4426 */         ConcurrentHashMapV8.Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 4427 */           paramAction.apply(localNode.key);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class ValuesView<K, V>
/*      */     extends ConcurrentHashMapV8.CollectionView<K, V, V>
/*      */     implements Collection<V>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     
/* 4440 */     ValuesView(ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8) { super(); }
/*      */     
/* 4442 */     public final boolean contains(Object paramObject) { return this.map.containsValue(paramObject); }
/*      */     
/*      */     public final boolean remove(Object paramObject) {
/*      */       Iterator localIterator;
/* 4446 */       if (paramObject != null) {
/* 4447 */         for (localIterator = iterator(); localIterator.hasNext();) {
/* 4448 */           if (paramObject.equals(localIterator.next())) {
/* 4449 */             localIterator.remove();
/* 4450 */             return true;
/*      */           }
/*      */         }
/*      */       }
/* 4454 */       return false;
/*      */     }
/*      */     
/*      */     public final Iterator<V> iterator() {
/* 4458 */       ConcurrentHashMapV8 localConcurrentHashMapV8 = this.map;
/*      */       ConcurrentHashMapV8.Node[] arrayOfNode;
/* 4460 */       int i = (arrayOfNode = localConcurrentHashMapV8.table) == null ? 0 : arrayOfNode.length;
/* 4461 */       return new ConcurrentHashMapV8.ValueIterator(arrayOfNode, i, 0, i, localConcurrentHashMapV8);
/*      */     }
/*      */     
/*      */     public final boolean add(V paramV) {
/* 4465 */       throw new UnsupportedOperationException();
/*      */     }
/*      */     
/* 4468 */     public final boolean addAll(Collection<? extends V> paramCollection) { throw new UnsupportedOperationException(); }
/*      */     
/*      */ 
/*      */     public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<V> spliterator166()
/*      */     {
/* 4473 */       ConcurrentHashMapV8 localConcurrentHashMapV8 = this.map;
/* 4474 */       long l = localConcurrentHashMapV8.sumCount();
/* 4475 */       ConcurrentHashMapV8.Node[] arrayOfNode; int i = (arrayOfNode = localConcurrentHashMapV8.table) == null ? 0 : arrayOfNode.length;
/* 4476 */       return new ConcurrentHashMapV8.ValueSpliterator(arrayOfNode, i, 0, i, l < 0L ? 0L : l);
/*      */     }
/*      */     
/*      */     public void forEach(ConcurrentHashMapV8.Action<? super V> paramAction) {
/* 4480 */       if (paramAction == null) throw new NullPointerException();
/*      */       ConcurrentHashMapV8.Node[] arrayOfNode;
/* 4482 */       if ((arrayOfNode = this.map.table) != null) {
/* 4483 */         ConcurrentHashMapV8.Traverser localTraverser = new ConcurrentHashMapV8.Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 4484 */         ConcurrentHashMapV8.Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 4485 */           paramAction.apply(localNode.val);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class EntrySetView<K, V>
/*      */     extends ConcurrentHashMapV8.CollectionView<K, V, Map.Entry<K, V>>
/*      */     implements Set<Map.Entry<K, V>>, Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2249069246763182397L;
/*      */     
/* 4498 */     EntrySetView(ConcurrentHashMapV8<K, V> paramConcurrentHashMapV8) { super(); }
/*      */     
/*      */     public boolean contains(Object paramObject) { Map.Entry localEntry;
/*      */       Object localObject1;
/* 4502 */       Object localObject2; Object localObject3; return ((paramObject instanceof Map.Entry)) && ((localObject1 = (localEntry = (Map.Entry)paramObject).getKey()) != null) && ((localObject2 = this.map.get(localObject1)) != null) && ((localObject3 = localEntry.getValue()) != null) && ((localObject3 == localObject2) || (localObject3.equals(localObject2)));
/*      */     }
/*      */     
/*      */ 
/*      */     public boolean remove(Object paramObject)
/*      */     {
/*      */       Map.Entry localEntry;
/*      */       Object localObject1;
/*      */       Object localObject2;
/* 4511 */       return ((paramObject instanceof Map.Entry)) && ((localObject1 = (localEntry = (Map.Entry)paramObject).getKey()) != null) && ((localObject2 = localEntry.getValue()) != null) && (this.map.remove(localObject1, localObject2));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Iterator<Map.Entry<K, V>> iterator()
/*      */     {
/* 4521 */       ConcurrentHashMapV8 localConcurrentHashMapV8 = this.map;
/*      */       ConcurrentHashMapV8.Node[] arrayOfNode;
/* 4523 */       int i = (arrayOfNode = localConcurrentHashMapV8.table) == null ? 0 : arrayOfNode.length;
/* 4524 */       return new ConcurrentHashMapV8.EntryIterator(arrayOfNode, i, 0, i, localConcurrentHashMapV8);
/*      */     }
/*      */     
/*      */     public boolean add(Map.Entry<K, V> paramEntry) {
/* 4528 */       return this.map.putVal(paramEntry.getKey(), paramEntry.getValue(), false) == null;
/*      */     }
/*      */     
/*      */     public boolean addAll(Collection<? extends Map.Entry<K, V>> paramCollection) {
/* 4532 */       boolean bool = false;
/* 4533 */       for (Map.Entry localEntry : paramCollection) {
/* 4534 */         if (add(localEntry))
/* 4535 */           bool = true;
/*      */       }
/* 4537 */       return bool;
/*      */     }
/*      */     
/*      */     public final int hashCode() {
/* 4541 */       int i = 0;
/*      */       ConcurrentHashMapV8.Node[] arrayOfNode;
/* 4543 */       if ((arrayOfNode = this.map.table) != null) {
/* 4544 */         ConcurrentHashMapV8.Traverser localTraverser = new ConcurrentHashMapV8.Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 4545 */         ConcurrentHashMapV8.Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 4546 */           i += localNode.hashCode();
/*      */         }
/*      */       }
/* 4549 */       return i;
/*      */     }
/*      */     
/*      */     public final boolean equals(Object paramObject) {
/*      */       Set localSet;
/* 4554 */       return ((paramObject instanceof Set)) && (((localSet = (Set)paramObject) == this) || ((containsAll(localSet)) && (localSet.containsAll(this))));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public ConcurrentHashMapV8.ConcurrentHashMapSpliterator<Map.Entry<K, V>> spliterator166()
/*      */     {
/* 4561 */       ConcurrentHashMapV8 localConcurrentHashMapV8 = this.map;
/* 4562 */       long l = localConcurrentHashMapV8.sumCount();
/* 4563 */       ConcurrentHashMapV8.Node[] arrayOfNode; int i = (arrayOfNode = localConcurrentHashMapV8.table) == null ? 0 : arrayOfNode.length;
/* 4564 */       return new ConcurrentHashMapV8.EntrySpliterator(arrayOfNode, i, 0, i, l < 0L ? 0L : l, localConcurrentHashMapV8);
/*      */     }
/*      */     
/*      */     public void forEach(ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> paramAction) {
/* 4568 */       if (paramAction == null) throw new NullPointerException();
/*      */       ConcurrentHashMapV8.Node[] arrayOfNode;
/* 4570 */       if ((arrayOfNode = this.map.table) != null) {
/* 4571 */         ConcurrentHashMapV8.Traverser localTraverser = new ConcurrentHashMapV8.Traverser(arrayOfNode, arrayOfNode.length, 0, arrayOfNode.length);
/* 4572 */         ConcurrentHashMapV8.Node localNode; while ((localNode = localTraverser.advance()) != null) {
/* 4573 */           paramAction.apply(new ConcurrentHashMapV8.MapEntry(localNode.key, localNode.val, this.map));
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static abstract class BulkTask<K, V, R>
/*      */     extends CountedCompleter<R>
/*      */   {
/*      */     ConcurrentHashMapV8.Node<K, V>[] tab;
/*      */     
/*      */     ConcurrentHashMapV8.Node<K, V> next;
/*      */     
/*      */     int index;
/*      */     int baseIndex;
/*      */     int baseLimit;
/*      */     final int baseSize;
/*      */     int batch;
/*      */     
/*      */     BulkTask(BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode)
/*      */     {
/* 4595 */       super();
/* 4596 */       this.batch = paramInt1;
/* 4597 */       this.index = (this.baseIndex = paramInt2);
/* 4598 */       if ((this.tab = paramArrayOfNode) == null) {
/* 4599 */         this.baseSize = (this.baseLimit = 0);
/* 4600 */       } else if (paramBulkTask == null) {
/* 4601 */         this.baseSize = (this.baseLimit = paramArrayOfNode.length);
/*      */       } else {
/* 4603 */         this.baseLimit = paramInt3;
/* 4604 */         this.baseSize = paramBulkTask.baseSize;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     final ConcurrentHashMapV8.Node<K, V> advance()
/*      */     {
/*      */       Object localObject;
/*      */       
/* 4613 */       if ((localObject = this.next) != null) {
/* 4614 */         localObject = ((ConcurrentHashMapV8.Node)localObject).next;
/*      */       }
/*      */       for (;;) {
/* 4617 */         if (localObject != null)
/* 4618 */           return (ConcurrentHashMapV8.Node<K, V>)(this.next = localObject);
/* 4619 */         ConcurrentHashMapV8.Node[] arrayOfNode; int i; int j; if ((this.baseIndex >= this.baseLimit) || ((arrayOfNode = this.tab) == null) || ((i = arrayOfNode.length) <= (j = this.index)) || (j < 0))
/*      */         {
/* 4621 */           return this.next = null; }
/* 4622 */         if (((localObject = ConcurrentHashMapV8.tabAt(arrayOfNode, this.index)) != null) && (((ConcurrentHashMapV8.Node)localObject).hash < 0)) {
/* 4623 */           if ((localObject instanceof ConcurrentHashMapV8.ForwardingNode)) {
/* 4624 */             this.tab = ((ConcurrentHashMapV8.ForwardingNode)localObject).nextTable;
/* 4625 */             localObject = null;
/* 4626 */             continue;
/*      */           }
/* 4628 */           if ((localObject instanceof ConcurrentHashMapV8.TreeBin)) {
/* 4629 */             localObject = ((ConcurrentHashMapV8.TreeBin)localObject).first;
/*      */           } else
/* 4631 */             localObject = null;
/*      */         }
/* 4633 */         if (this.index += this.baseSize >= i) {
/* 4634 */           this.index = (++this.baseIndex);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class ForEachKeyTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Action<? super K> action;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     ForEachKeyTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.Action<? super K> paramAction)
/*      */     {
/* 4653 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4654 */       this.action = paramAction;
/*      */     }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Action localAction;
/* 4658 */       if ((localAction = this.action) != null) { int j;
/* 4659 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4661 */           addToPendingCount(1);
/* 4662 */           new ForEachKeyTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localAction).fork();
/*      */         }
/*      */         
/*      */         ConcurrentHashMapV8.Node localNode;
/* 4666 */         while ((localNode = advance()) != null)
/* 4667 */           localAction.apply(localNode.key);
/* 4668 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachValueTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Action<? super V> action;
/*      */     
/*      */     ForEachValueTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.Action<? super V> paramAction)
/*      */     {
/* 4680 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4681 */       this.action = paramAction;
/*      */     }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Action localAction;
/* 4685 */       if ((localAction = this.action) != null) { int j;
/* 4686 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4688 */           addToPendingCount(1);
/* 4689 */           new ForEachValueTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localAction).fork();
/*      */         }
/*      */         
/*      */         ConcurrentHashMapV8.Node localNode;
/* 4693 */         while ((localNode = advance()) != null)
/* 4694 */           localAction.apply(localNode.val);
/* 4695 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachEntryTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> action;
/*      */     
/*      */     ForEachEntryTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.Action<? super Map.Entry<K, V>> paramAction)
/*      */     {
/* 4707 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4708 */       this.action = paramAction;
/*      */     }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Action localAction;
/* 4712 */       if ((localAction = this.action) != null) { int j;
/* 4713 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4715 */           addToPendingCount(1);
/* 4716 */           new ForEachEntryTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localAction).fork();
/*      */         }
/*      */         
/*      */         ConcurrentHashMapV8.Node localNode;
/* 4720 */         while ((localNode = advance()) != null)
/* 4721 */           localAction.apply(localNode);
/* 4722 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachMappingTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiAction<? super K, ? super V> action;
/*      */     
/*      */     ForEachMappingTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.BiAction<? super K, ? super V> paramBiAction)
/*      */     {
/* 4734 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4735 */       this.action = paramBiAction;
/*      */     }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.BiAction localBiAction;
/* 4739 */       if ((localBiAction = this.action) != null) { int j;
/* 4740 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4742 */           addToPendingCount(1);
/* 4743 */           new ForEachMappingTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localBiAction).fork();
/*      */         }
/*      */         
/*      */         ConcurrentHashMapV8.Node localNode;
/* 4747 */         while ((localNode = advance()) != null)
/* 4748 */           localBiAction.apply(localNode.key, localNode.val);
/* 4749 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachTransformedKeyTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.Action<? super U> action;
/*      */     
/*      */     ForEachTransformedKeyTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.Fun<? super K, ? extends U> paramFun, ConcurrentHashMapV8.Action<? super U> paramAction)
/*      */     {
/* 4762 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4763 */       this.transformer = paramFun;this.action = paramAction;
/*      */     }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Fun localFun;
/*      */       ConcurrentHashMapV8.Action localAction;
/* 4768 */       if (((localFun = this.transformer) != null) && ((localAction = this.action) != null)) { int j;
/*      */         int k;
/* 4770 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4772 */           addToPendingCount(1);
/* 4773 */           new ForEachTransformedKeyTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFun, localAction).fork();
/*      */         }
/*      */         
/*      */         ConcurrentHashMapV8.Node localNode;
/* 4777 */         while ((localNode = advance()) != null) {
/*      */           Object localObject;
/* 4779 */           if ((localObject = localFun.apply(localNode.key)) != null)
/* 4780 */             localAction.apply(localObject);
/*      */         }
/* 4782 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachTransformedValueTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.Action<? super U> action;
/*      */     
/*      */     ForEachTransformedValueTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.Fun<? super V, ? extends U> paramFun, ConcurrentHashMapV8.Action<? super U> paramAction)
/*      */     {
/* 4795 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4796 */       this.transformer = paramFun;this.action = paramAction;
/*      */     }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Fun localFun;
/*      */       ConcurrentHashMapV8.Action localAction;
/* 4801 */       if (((localFun = this.transformer) != null) && ((localAction = this.action) != null)) { int j;
/*      */         int k;
/* 4803 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4805 */           addToPendingCount(1);
/* 4806 */           new ForEachTransformedValueTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFun, localAction).fork();
/*      */         }
/*      */         
/*      */         ConcurrentHashMapV8.Node localNode;
/* 4810 */         while ((localNode = advance()) != null) {
/*      */           Object localObject;
/* 4812 */           if ((localObject = localFun.apply(localNode.val)) != null)
/* 4813 */             localAction.apply(localObject);
/*      */         }
/* 4815 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ForEachTransformedEntryTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.Action<? super U> action;
/*      */     
/*      */     ForEachTransformedEntryTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> paramFun, ConcurrentHashMapV8.Action<? super U> paramAction)
/*      */     {
/* 4828 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4829 */       this.transformer = paramFun;this.action = paramAction;
/*      */     }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Fun localFun;
/*      */       ConcurrentHashMapV8.Action localAction;
/* 4834 */       if (((localFun = this.transformer) != null) && ((localAction = this.action) != null)) { int j;
/*      */         int k;
/* 4836 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4838 */           addToPendingCount(1);
/* 4839 */           new ForEachTransformedEntryTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFun, localAction).fork();
/*      */         }
/*      */         
/*      */         ConcurrentHashMapV8.Node localNode;
/* 4843 */         while ((localNode = advance()) != null) {
/*      */           Object localObject;
/* 4845 */           if ((localObject = localFun.apply(localNode)) != null)
/* 4846 */             localAction.apply(localObject);
/*      */         }
/* 4848 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class ForEachTransformedMappingTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Void>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.Action<? super U> action;
/*      */     
/*      */     ForEachTransformedMappingTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> paramBiFun, ConcurrentHashMapV8.Action<? super U> paramAction)
/*      */     {
/* 4862 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4863 */       this.transformer = paramBiFun;this.action = paramAction;
/*      */     }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.BiFun localBiFun;
/*      */       ConcurrentHashMapV8.Action localAction;
/* 4868 */       if (((localBiFun = this.transformer) != null) && ((localAction = this.action) != null)) { int j;
/*      */         int k;
/* 4870 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4872 */           addToPendingCount(1);
/* 4873 */           new ForEachTransformedMappingTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localBiFun, localAction).fork();
/*      */         }
/*      */         
/*      */         ConcurrentHashMapV8.Node localNode;
/* 4877 */         while ((localNode = advance()) != null) {
/*      */           Object localObject;
/* 4879 */           if ((localObject = localBiFun.apply(localNode.key, localNode.val)) != null)
/* 4880 */             localAction.apply(localObject);
/*      */         }
/* 4882 */         propagateCompletion();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class SearchKeysTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super K, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchKeysTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.Fun<? super K, ? extends U> paramFun, AtomicReference<U> paramAtomicReference)
/*      */     {
/* 4896 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4897 */       this.searchFunction = paramFun;this.result = paramAtomicReference; }
/*      */     
/* 4899 */     public final U getRawResult() { return (U)this.result.get(); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Fun localFun;
/*      */       AtomicReference localAtomicReference;
/* 4903 */       if (((localFun = this.searchFunction) != null) && ((localAtomicReference = this.result) != null)) { int j;
/*      */         int k;
/* 4905 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4907 */           if (localAtomicReference.get() != null)
/* 4908 */             return;
/* 4909 */           addToPendingCount(1);
/* 4910 */           new SearchKeysTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFun, localAtomicReference).fork();
/*      */         }
/*      */         
/*      */ 
/* 4914 */         while (localAtomicReference.get() == null)
/*      */         {
/*      */           ConcurrentHashMapV8.Node localNode;
/* 4917 */           if ((localNode = advance()) == null) {
/* 4918 */             propagateCompletion();
/* 4919 */             break; }
/*      */           Object localObject;
/* 4921 */           if ((localObject = localFun.apply(localNode.key)) != null) {
/* 4922 */             if (!localAtomicReference.compareAndSet(null, localObject)) break;
/* 4923 */             quietlyCompleteRoot(); break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class SearchValuesTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super V, ? extends U> searchFunction;
/*      */     
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchValuesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.Fun<? super V, ? extends U> paramFun, AtomicReference<U> paramAtomicReference)
/*      */     {
/* 4940 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4941 */       this.searchFunction = paramFun;this.result = paramAtomicReference; }
/*      */     
/* 4943 */     public final U getRawResult() { return (U)this.result.get(); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Fun localFun;
/*      */       AtomicReference localAtomicReference;
/* 4947 */       if (((localFun = this.searchFunction) != null) && ((localAtomicReference = this.result) != null)) { int j;
/*      */         int k;
/* 4949 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4951 */           if (localAtomicReference.get() != null)
/* 4952 */             return;
/* 4953 */           addToPendingCount(1);
/* 4954 */           new SearchValuesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFun, localAtomicReference).fork();
/*      */         }
/*      */         
/*      */ 
/* 4958 */         while (localAtomicReference.get() == null)
/*      */         {
/*      */           ConcurrentHashMapV8.Node localNode;
/* 4961 */           if ((localNode = advance()) == null) {
/* 4962 */             propagateCompletion();
/* 4963 */             break; }
/*      */           Object localObject;
/* 4965 */           if ((localObject = localFun.apply(localNode.val)) != null) {
/* 4966 */             if (!localAtomicReference.compareAndSet(null, localObject)) break;
/* 4967 */             quietlyCompleteRoot(); break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class SearchEntriesTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> searchFunction;
/*      */     
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchEntriesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> paramFun, AtomicReference<U> paramAtomicReference)
/*      */     {
/* 4984 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 4985 */       this.searchFunction = paramFun;this.result = paramAtomicReference; }
/*      */     
/* 4987 */     public final U getRawResult() { return (U)this.result.get(); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Fun localFun;
/*      */       AtomicReference localAtomicReference;
/* 4991 */       if (((localFun = this.searchFunction) != null) && ((localAtomicReference = this.result) != null)) { int j;
/*      */         int k;
/* 4993 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 4995 */           if (localAtomicReference.get() != null)
/* 4996 */             return;
/* 4997 */           addToPendingCount(1);
/* 4998 */           new SearchEntriesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localFun, localAtomicReference).fork();
/*      */         }
/*      */         
/*      */ 
/* 5002 */         while (localAtomicReference.get() == null)
/*      */         {
/*      */           ConcurrentHashMapV8.Node localNode;
/* 5005 */           if ((localNode = advance()) == null) {
/* 5006 */             propagateCompletion();
/* 5007 */             break; }
/*      */           Object localObject;
/* 5009 */           if ((localObject = localFun.apply(localNode)) != null) {
/* 5010 */             if (localAtomicReference.compareAndSet(null, localObject))
/* 5011 */               quietlyCompleteRoot();
/* 5012 */             return;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class SearchMappingsTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> searchFunction;
/*      */     final AtomicReference<U> result;
/*      */     
/*      */     SearchMappingsTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> paramBiFun, AtomicReference<U> paramAtomicReference)
/*      */     {
/* 5028 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);
/* 5029 */       this.searchFunction = paramBiFun;this.result = paramAtomicReference; }
/*      */     
/* 5031 */     public final U getRawResult() { return (U)this.result.get(); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.BiFun localBiFun;
/*      */       AtomicReference localAtomicReference;
/* 5035 */       if (((localBiFun = this.searchFunction) != null) && ((localAtomicReference = this.result) != null)) { int j;
/*      */         int k;
/* 5037 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5039 */           if (localAtomicReference.get() != null)
/* 5040 */             return;
/* 5041 */           addToPendingCount(1);
/* 5042 */           new SearchMappingsTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, localBiFun, localAtomicReference).fork();
/*      */         }
/*      */         
/*      */ 
/* 5046 */         while (localAtomicReference.get() == null)
/*      */         {
/*      */           ConcurrentHashMapV8.Node localNode;
/* 5049 */           if ((localNode = advance()) == null) {
/* 5050 */             propagateCompletion();
/* 5051 */             break; }
/*      */           Object localObject;
/* 5053 */           if ((localObject = localBiFun.apply(localNode.key, localNode.val)) != null) {
/* 5054 */             if (!localAtomicReference.compareAndSet(null, localObject)) break;
/* 5055 */             quietlyCompleteRoot(); break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class ReduceKeysTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, K>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super K, ? super K, ? extends K> reducer;
/*      */     K result;
/*      */     ReduceKeysTask<K, V> rights;
/*      */     ReduceKeysTask<K, V> nextRight;
/*      */     
/*      */     ReduceKeysTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ReduceKeysTask<K, V> paramReduceKeysTask, ConcurrentHashMapV8.BiFun<? super K, ? super K, ? extends K> paramBiFun)
/*      */     {
/* 5073 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramReduceKeysTask;
/* 5074 */       this.reducer = paramBiFun; }
/*      */     
/* 5076 */     public final K getRawResult() { return (K)this.result; }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.BiFun localBiFun;
/* 5079 */       if ((localBiFun = this.reducer) != null) { int j;
/* 5080 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5082 */           addToPendingCount(1);
/* 5083 */           (this.rights = new ReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localBiFun)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5087 */         Object localObject1 = null;
/* 5088 */         Object localObject3; while ((localObject2 = advance()) != null) {
/* 5089 */           localObject3 = ((ConcurrentHashMapV8.Node)localObject2).key;
/* 5090 */           localObject1 = localObject3 == null ? localObject1 : localObject1 == null ? localObject3 : localBiFun.apply(localObject1, localObject3);
/*      */         }
/* 5092 */         this.result = localObject1;
/*      */         
/* 5094 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/* 5096 */           localObject3 = (ReduceKeysTask)localObject2;
/* 5097 */           ReduceKeysTask localReduceKeysTask = ((ReduceKeysTask)localObject3).rights;
/* 5098 */           while (localReduceKeysTask != null) {
/*      */             Object localObject4;
/* 5100 */             if ((localObject4 = localReduceKeysTask.result) != null) { Object localObject5;
/* 5101 */               ((ReduceKeysTask)localObject3).result = ((localObject5 = ((ReduceKeysTask)localObject3).result) == null ? localObject4 : localBiFun.apply(localObject5, localObject4));
/*      */             }
/* 5103 */             localReduceKeysTask = ((ReduceKeysTask)localObject3).rights = localReduceKeysTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReduceValuesTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, V>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> reducer;
/*      */     V result;
/*      */     ReduceValuesTask<K, V> rights;
/*      */     ReduceValuesTask<K, V> nextRight;
/*      */     
/*      */     ReduceValuesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ReduceValuesTask<K, V> paramReduceValuesTask, ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> paramBiFun)
/*      */     {
/* 5120 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramReduceValuesTask;
/* 5121 */       this.reducer = paramBiFun; }
/*      */     
/* 5123 */     public final V getRawResult() { return (V)this.result; }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.BiFun localBiFun;
/* 5126 */       if ((localBiFun = this.reducer) != null) { int j;
/* 5127 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5129 */           addToPendingCount(1);
/* 5130 */           (this.rights = new ReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localBiFun)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5134 */         Object localObject1 = null;
/* 5135 */         Object localObject3; while ((localObject2 = advance()) != null) {
/* 5136 */           localObject3 = ((ConcurrentHashMapV8.Node)localObject2).val;
/* 5137 */           localObject1 = localObject1 == null ? localObject3 : localBiFun.apply(localObject1, localObject3);
/*      */         }
/* 5139 */         this.result = localObject1;
/*      */         
/* 5141 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/* 5143 */           localObject3 = (ReduceValuesTask)localObject2;
/* 5144 */           ReduceValuesTask localReduceValuesTask = ((ReduceValuesTask)localObject3).rights;
/* 5145 */           while (localReduceValuesTask != null) {
/*      */             Object localObject4;
/* 5147 */             if ((localObject4 = localReduceValuesTask.result) != null) { Object localObject5;
/* 5148 */               ((ReduceValuesTask)localObject3).result = ((localObject5 = ((ReduceValuesTask)localObject3).result) == null ? localObject4 : localBiFun.apply(localObject5, localObject4));
/*      */             }
/* 5150 */             localReduceValuesTask = ((ReduceValuesTask)localObject3).rights = localReduceValuesTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class ReduceEntriesTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Map.Entry<K, V>>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> reducer;
/*      */     Map.Entry<K, V> result;
/*      */     ReduceEntriesTask<K, V> rights;
/*      */     ReduceEntriesTask<K, V> nextRight;
/*      */     
/*      */     ReduceEntriesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, ReduceEntriesTask<K, V> paramReduceEntriesTask, ConcurrentHashMapV8.BiFun<Map.Entry<K, V>, Map.Entry<K, V>, ? extends Map.Entry<K, V>> paramBiFun)
/*      */     {
/* 5167 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramReduceEntriesTask;
/* 5168 */       this.reducer = paramBiFun; }
/*      */     
/* 5170 */     public final Map.Entry<K, V> getRawResult() { return this.result; }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.BiFun localBiFun;
/* 5173 */       if ((localBiFun = this.reducer) != null) { int j;
/* 5174 */         int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5176 */           addToPendingCount(1);
/* 5177 */           (this.rights = new ReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localBiFun)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5181 */         Object localObject1 = null;
/* 5182 */         while ((localObject2 = advance()) != null)
/* 5183 */           localObject1 = localObject1 == null ? localObject2 : (Map.Entry)localBiFun.apply(localObject1, localObject2);
/* 5184 */         this.result = ((Map.Entry)localObject1);
/*      */         
/* 5186 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/* 5188 */           ReduceEntriesTask localReduceEntriesTask1 = (ReduceEntriesTask)localObject2;
/* 5189 */           ReduceEntriesTask localReduceEntriesTask2 = localReduceEntriesTask1.rights;
/* 5190 */           while (localReduceEntriesTask2 != null) {
/*      */             Map.Entry localEntry1;
/* 5192 */             if ((localEntry1 = localReduceEntriesTask2.result) != null) { Map.Entry localEntry2;
/* 5193 */               localReduceEntriesTask1.result = ((localEntry2 = localReduceEntriesTask1.result) == null ? localEntry1 : (Map.Entry)localBiFun.apply(localEntry2, localEntry1));
/*      */             }
/* 5195 */             localReduceEntriesTask2 = localReduceEntriesTask1.rights = localReduceEntriesTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceKeysTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super K, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceKeysTask<K, V, U> rights;
/*      */     MapReduceKeysTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceKeysTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceKeysTask<K, V, U> paramMapReduceKeysTask, ConcurrentHashMapV8.Fun<? super K, ? extends U> paramFun, ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> paramBiFun)
/*      */     {
/* 5214 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceKeysTask;
/* 5215 */       this.transformer = paramFun;
/* 5216 */       this.reducer = paramBiFun; }
/*      */     
/* 5218 */     public final U getRawResult() { return (U)this.result; }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Fun localFun;
/*      */       ConcurrentHashMapV8.BiFun localBiFun;
/* 5222 */       if (((localFun = this.transformer) != null) && ((localBiFun = this.reducer) != null)) { int j;
/*      */         int k;
/* 5224 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5226 */           addToPendingCount(1);
/* 5227 */           (this.rights = new MapReduceKeysTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localFun, localBiFun)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5231 */         Object localObject1 = null;
/* 5232 */         Object localObject3; while ((localObject2 = advance()) != null)
/*      */         {
/* 5234 */           if ((localObject3 = localFun.apply(((ConcurrentHashMapV8.Node)localObject2).key)) != null)
/* 5235 */             localObject1 = localObject1 == null ? localObject3 : localBiFun.apply(localObject1, localObject3);
/*      */         }
/* 5237 */         this.result = localObject1;
/*      */         
/* 5239 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/* 5241 */           localObject3 = (MapReduceKeysTask)localObject2;
/* 5242 */           MapReduceKeysTask localMapReduceKeysTask = ((MapReduceKeysTask)localObject3).rights;
/* 5243 */           while (localMapReduceKeysTask != null) {
/*      */             Object localObject4;
/* 5245 */             if ((localObject4 = localMapReduceKeysTask.result) != null) { Object localObject5;
/* 5246 */               ((MapReduceKeysTask)localObject3).result = ((localObject5 = ((MapReduceKeysTask)localObject3).result) == null ? localObject4 : localBiFun.apply(localObject5, localObject4));
/*      */             }
/* 5248 */             localMapReduceKeysTask = ((MapReduceKeysTask)localObject3).rights = localMapReduceKeysTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceValuesTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<? super V, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceValuesTask<K, V, U> rights;
/*      */     MapReduceValuesTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceValuesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceValuesTask<K, V, U> paramMapReduceValuesTask, ConcurrentHashMapV8.Fun<? super V, ? extends U> paramFun, ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> paramBiFun)
/*      */     {
/* 5267 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceValuesTask;
/* 5268 */       this.transformer = paramFun;
/* 5269 */       this.reducer = paramBiFun; }
/*      */     
/* 5271 */     public final U getRawResult() { return (U)this.result; }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Fun localFun;
/*      */       ConcurrentHashMapV8.BiFun localBiFun;
/* 5275 */       if (((localFun = this.transformer) != null) && ((localBiFun = this.reducer) != null)) { int j;
/*      */         int k;
/* 5277 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5279 */           addToPendingCount(1);
/* 5280 */           (this.rights = new MapReduceValuesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localFun, localBiFun)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5284 */         Object localObject1 = null;
/* 5285 */         Object localObject3; while ((localObject2 = advance()) != null)
/*      */         {
/* 5287 */           if ((localObject3 = localFun.apply(((ConcurrentHashMapV8.Node)localObject2).val)) != null)
/* 5288 */             localObject1 = localObject1 == null ? localObject3 : localBiFun.apply(localObject1, localObject3);
/*      */         }
/* 5290 */         this.result = localObject1;
/*      */         
/* 5292 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/* 5294 */           localObject3 = (MapReduceValuesTask)localObject2;
/* 5295 */           MapReduceValuesTask localMapReduceValuesTask = ((MapReduceValuesTask)localObject3).rights;
/* 5296 */           while (localMapReduceValuesTask != null) {
/*      */             Object localObject4;
/* 5298 */             if ((localObject4 = localMapReduceValuesTask.result) != null) { Object localObject5;
/* 5299 */               ((MapReduceValuesTask)localObject3).result = ((localObject5 = ((MapReduceValuesTask)localObject3).result) == null ? localObject4 : localBiFun.apply(localObject5, localObject4));
/*      */             }
/* 5301 */             localMapReduceValuesTask = ((MapReduceValuesTask)localObject3).rights = localMapReduceValuesTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceEntriesTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceEntriesTask<K, V, U> rights;
/*      */     MapReduceEntriesTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceEntriesTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceEntriesTask<K, V, U> paramMapReduceEntriesTask, ConcurrentHashMapV8.Fun<Map.Entry<K, V>, ? extends U> paramFun, ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> paramBiFun)
/*      */     {
/* 5320 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceEntriesTask;
/* 5321 */       this.transformer = paramFun;
/* 5322 */       this.reducer = paramBiFun; }
/*      */     
/* 5324 */     public final U getRawResult() { return (U)this.result; }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.Fun localFun;
/*      */       ConcurrentHashMapV8.BiFun localBiFun;
/* 5328 */       if (((localFun = this.transformer) != null) && ((localBiFun = this.reducer) != null)) { int j;
/*      */         int k;
/* 5330 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5332 */           addToPendingCount(1);
/* 5333 */           (this.rights = new MapReduceEntriesTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localFun, localBiFun)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5337 */         Object localObject1 = null;
/* 5338 */         Object localObject3; while ((localObject2 = advance()) != null)
/*      */         {
/* 5340 */           if ((localObject3 = localFun.apply(localObject2)) != null)
/* 5341 */             localObject1 = localObject1 == null ? localObject3 : localBiFun.apply(localObject1, localObject3);
/*      */         }
/* 5343 */         this.result = localObject1;
/*      */         
/* 5345 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/* 5347 */           localObject3 = (MapReduceEntriesTask)localObject2;
/* 5348 */           MapReduceEntriesTask localMapReduceEntriesTask = ((MapReduceEntriesTask)localObject3).rights;
/* 5349 */           while (localMapReduceEntriesTask != null) {
/*      */             Object localObject4;
/* 5351 */             if ((localObject4 = localMapReduceEntriesTask.result) != null) { Object localObject5;
/* 5352 */               ((MapReduceEntriesTask)localObject3).result = ((localObject5 = ((MapReduceEntriesTask)localObject3).result) == null ? localObject4 : localBiFun.apply(localObject5, localObject4));
/*      */             }
/* 5354 */             localMapReduceEntriesTask = ((MapReduceEntriesTask)localObject3).rights = localMapReduceEntriesTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceMappingsTask<K, V, U>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, U>
/*      */   {
/*      */     final ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> transformer;
/*      */     final ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> reducer;
/*      */     U result;
/*      */     MapReduceMappingsTask<K, V, U> rights;
/*      */     MapReduceMappingsTask<K, V, U> nextRight;
/*      */     
/*      */     MapReduceMappingsTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceMappingsTask<K, V, U> paramMapReduceMappingsTask, ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends U> paramBiFun, ConcurrentHashMapV8.BiFun<? super U, ? super U, ? extends U> paramBiFun1)
/*      */     {
/* 5373 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceMappingsTask;
/* 5374 */       this.transformer = paramBiFun;
/* 5375 */       this.reducer = paramBiFun1; }
/*      */     
/* 5377 */     public final U getRawResult() { return (U)this.result; }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.BiFun localBiFun1;
/*      */       ConcurrentHashMapV8.BiFun localBiFun2;
/* 5381 */       if (((localBiFun1 = this.transformer) != null) && ((localBiFun2 = this.reducer) != null)) { int j;
/*      */         int k;
/* 5383 */         for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5385 */           addToPendingCount(1);
/* 5386 */           (this.rights = new MapReduceMappingsTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localBiFun1, localBiFun2)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5390 */         Object localObject1 = null;
/* 5391 */         Object localObject3; while ((localObject2 = advance()) != null)
/*      */         {
/* 5393 */           if ((localObject3 = localBiFun1.apply(((ConcurrentHashMapV8.Node)localObject2).key, ((ConcurrentHashMapV8.Node)localObject2).val)) != null)
/* 5394 */             localObject1 = localObject1 == null ? localObject3 : localBiFun2.apply(localObject1, localObject3);
/*      */         }
/* 5396 */         this.result = localObject1;
/*      */         
/* 5398 */         for (Object localObject2 = firstComplete(); localObject2 != null; localObject2 = ((CountedCompleter)localObject2).nextComplete())
/*      */         {
/* 5400 */           localObject3 = (MapReduceMappingsTask)localObject2;
/* 5401 */           MapReduceMappingsTask localMapReduceMappingsTask = ((MapReduceMappingsTask)localObject3).rights;
/* 5402 */           while (localMapReduceMappingsTask != null) {
/*      */             Object localObject4;
/* 5404 */             if ((localObject4 = localMapReduceMappingsTask.result) != null) { Object localObject5;
/* 5405 */               ((MapReduceMappingsTask)localObject3).result = ((localObject5 = ((MapReduceMappingsTask)localObject3).result) == null ? localObject4 : localBiFun2.apply(localObject5, localObject4));
/*      */             }
/* 5407 */             localMapReduceMappingsTask = ((MapReduceMappingsTask)localObject3).rights = localMapReduceMappingsTask.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceKeysToDoubleTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Double>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToDouble<? super K> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceKeysToDoubleTask<K, V> rights;
/*      */     MapReduceKeysToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToDoubleTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceKeysToDoubleTask<K, V> paramMapReduceKeysToDoubleTask, ConcurrentHashMapV8.ObjectToDouble<? super K> paramObjectToDouble, double paramDouble, ConcurrentHashMapV8.DoubleByDoubleToDouble paramDoubleByDoubleToDouble)
/*      */     {
/* 5428 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceKeysToDoubleTask;
/* 5429 */       this.transformer = paramObjectToDouble;
/* 5430 */       this.basis = paramDouble;this.reducer = paramDoubleByDoubleToDouble; }
/*      */     
/* 5432 */     public final Double getRawResult() { return Double.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectToDouble localObjectToDouble;
/*      */       ConcurrentHashMapV8.DoubleByDoubleToDouble localDoubleByDoubleToDouble;
/* 5436 */       if (((localObjectToDouble = this.transformer) != null) && ((localDoubleByDoubleToDouble = this.reducer) != null))
/*      */       {
/* 5438 */         double d = this.basis;
/* 5439 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5441 */           addToPendingCount(1);
/* 5442 */           (this.rights = new MapReduceKeysToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localObjectToDouble, d, localDoubleByDoubleToDouble)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5446 */         while ((localObject = advance()) != null)
/* 5447 */           d = localDoubleByDoubleToDouble.apply(d, localObjectToDouble.apply(((ConcurrentHashMapV8.Node)localObject).key));
/* 5448 */         this.result = d;
/*      */         
/* 5450 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5452 */           MapReduceKeysToDoubleTask localMapReduceKeysToDoubleTask1 = (MapReduceKeysToDoubleTask)localObject;
/* 5453 */           MapReduceKeysToDoubleTask localMapReduceKeysToDoubleTask2 = localMapReduceKeysToDoubleTask1.rights;
/* 5454 */           while (localMapReduceKeysToDoubleTask2 != null) {
/* 5455 */             localMapReduceKeysToDoubleTask1.result = localDoubleByDoubleToDouble.apply(localMapReduceKeysToDoubleTask1.result, localMapReduceKeysToDoubleTask2.result);
/* 5456 */             localMapReduceKeysToDoubleTask2 = localMapReduceKeysToDoubleTask1.rights = localMapReduceKeysToDoubleTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceValuesToDoubleTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Double>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToDouble<? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceValuesToDoubleTask<K, V> rights;
/*      */     MapReduceValuesToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToDoubleTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceValuesToDoubleTask<K, V> paramMapReduceValuesToDoubleTask, ConcurrentHashMapV8.ObjectToDouble<? super V> paramObjectToDouble, double paramDouble, ConcurrentHashMapV8.DoubleByDoubleToDouble paramDoubleByDoubleToDouble)
/*      */     {
/* 5477 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceValuesToDoubleTask;
/* 5478 */       this.transformer = paramObjectToDouble;
/* 5479 */       this.basis = paramDouble;this.reducer = paramDoubleByDoubleToDouble; }
/*      */     
/* 5481 */     public final Double getRawResult() { return Double.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectToDouble localObjectToDouble;
/*      */       ConcurrentHashMapV8.DoubleByDoubleToDouble localDoubleByDoubleToDouble;
/* 5485 */       if (((localObjectToDouble = this.transformer) != null) && ((localDoubleByDoubleToDouble = this.reducer) != null))
/*      */       {
/* 5487 */         double d = this.basis;
/* 5488 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5490 */           addToPendingCount(1);
/* 5491 */           (this.rights = new MapReduceValuesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localObjectToDouble, d, localDoubleByDoubleToDouble)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5495 */         while ((localObject = advance()) != null)
/* 5496 */           d = localDoubleByDoubleToDouble.apply(d, localObjectToDouble.apply(((ConcurrentHashMapV8.Node)localObject).val));
/* 5497 */         this.result = d;
/*      */         
/* 5499 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5501 */           MapReduceValuesToDoubleTask localMapReduceValuesToDoubleTask1 = (MapReduceValuesToDoubleTask)localObject;
/* 5502 */           MapReduceValuesToDoubleTask localMapReduceValuesToDoubleTask2 = localMapReduceValuesToDoubleTask1.rights;
/* 5503 */           while (localMapReduceValuesToDoubleTask2 != null) {
/* 5504 */             localMapReduceValuesToDoubleTask1.result = localDoubleByDoubleToDouble.apply(localMapReduceValuesToDoubleTask1.result, localMapReduceValuesToDoubleTask2.result);
/* 5505 */             localMapReduceValuesToDoubleTask2 = localMapReduceValuesToDoubleTask1.rights = localMapReduceValuesToDoubleTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceEntriesToDoubleTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Double>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToDouble<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceEntriesToDoubleTask<K, V> rights;
/*      */     MapReduceEntriesToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToDoubleTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceEntriesToDoubleTask<K, V> paramMapReduceEntriesToDoubleTask, ConcurrentHashMapV8.ObjectToDouble<Map.Entry<K, V>> paramObjectToDouble, double paramDouble, ConcurrentHashMapV8.DoubleByDoubleToDouble paramDoubleByDoubleToDouble)
/*      */     {
/* 5526 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceEntriesToDoubleTask;
/* 5527 */       this.transformer = paramObjectToDouble;
/* 5528 */       this.basis = paramDouble;this.reducer = paramDoubleByDoubleToDouble; }
/*      */     
/* 5530 */     public final Double getRawResult() { return Double.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectToDouble localObjectToDouble;
/*      */       ConcurrentHashMapV8.DoubleByDoubleToDouble localDoubleByDoubleToDouble;
/* 5534 */       if (((localObjectToDouble = this.transformer) != null) && ((localDoubleByDoubleToDouble = this.reducer) != null))
/*      */       {
/* 5536 */         double d = this.basis;
/* 5537 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5539 */           addToPendingCount(1);
/* 5540 */           (this.rights = new MapReduceEntriesToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localObjectToDouble, d, localDoubleByDoubleToDouble)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5544 */         while ((localObject = advance()) != null)
/* 5545 */           d = localDoubleByDoubleToDouble.apply(d, localObjectToDouble.apply(localObject));
/* 5546 */         this.result = d;
/*      */         
/* 5548 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5550 */           MapReduceEntriesToDoubleTask localMapReduceEntriesToDoubleTask1 = (MapReduceEntriesToDoubleTask)localObject;
/* 5551 */           MapReduceEntriesToDoubleTask localMapReduceEntriesToDoubleTask2 = localMapReduceEntriesToDoubleTask1.rights;
/* 5552 */           while (localMapReduceEntriesToDoubleTask2 != null) {
/* 5553 */             localMapReduceEntriesToDoubleTask1.result = localDoubleByDoubleToDouble.apply(localMapReduceEntriesToDoubleTask1.result, localMapReduceEntriesToDoubleTask2.result);
/* 5554 */             localMapReduceEntriesToDoubleTask2 = localMapReduceEntriesToDoubleTask1.rights = localMapReduceEntriesToDoubleTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceMappingsToDoubleTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Double>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectByObjectToDouble<? super K, ? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.DoubleByDoubleToDouble reducer;
/*      */     final double basis;
/*      */     double result;
/*      */     MapReduceMappingsToDoubleTask<K, V> rights;
/*      */     MapReduceMappingsToDoubleTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToDoubleTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceMappingsToDoubleTask<K, V> paramMapReduceMappingsToDoubleTask, ConcurrentHashMapV8.ObjectByObjectToDouble<? super K, ? super V> paramObjectByObjectToDouble, double paramDouble, ConcurrentHashMapV8.DoubleByDoubleToDouble paramDoubleByDoubleToDouble)
/*      */     {
/* 5575 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceMappingsToDoubleTask;
/* 5576 */       this.transformer = paramObjectByObjectToDouble;
/* 5577 */       this.basis = paramDouble;this.reducer = paramDoubleByDoubleToDouble; }
/*      */     
/* 5579 */     public final Double getRawResult() { return Double.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectByObjectToDouble localObjectByObjectToDouble;
/*      */       ConcurrentHashMapV8.DoubleByDoubleToDouble localDoubleByDoubleToDouble;
/* 5583 */       if (((localObjectByObjectToDouble = this.transformer) != null) && ((localDoubleByDoubleToDouble = this.reducer) != null))
/*      */       {
/* 5585 */         double d = this.basis;
/* 5586 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5588 */           addToPendingCount(1);
/* 5589 */           (this.rights = new MapReduceMappingsToDoubleTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localObjectByObjectToDouble, d, localDoubleByDoubleToDouble)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5593 */         while ((localObject = advance()) != null)
/* 5594 */           d = localDoubleByDoubleToDouble.apply(d, localObjectByObjectToDouble.apply(((ConcurrentHashMapV8.Node)localObject).key, ((ConcurrentHashMapV8.Node)localObject).val));
/* 5595 */         this.result = d;
/*      */         
/* 5597 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5599 */           MapReduceMappingsToDoubleTask localMapReduceMappingsToDoubleTask1 = (MapReduceMappingsToDoubleTask)localObject;
/* 5600 */           MapReduceMappingsToDoubleTask localMapReduceMappingsToDoubleTask2 = localMapReduceMappingsToDoubleTask1.rights;
/* 5601 */           while (localMapReduceMappingsToDoubleTask2 != null) {
/* 5602 */             localMapReduceMappingsToDoubleTask1.result = localDoubleByDoubleToDouble.apply(localMapReduceMappingsToDoubleTask1.result, localMapReduceMappingsToDoubleTask2.result);
/* 5603 */             localMapReduceMappingsToDoubleTask2 = localMapReduceMappingsToDoubleTask1.rights = localMapReduceMappingsToDoubleTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceKeysToLongTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Long>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToLong<? super K> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.LongByLongToLong reducer;
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceKeysToLongTask<K, V> rights;
/*      */     MapReduceKeysToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToLongTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceKeysToLongTask<K, V> paramMapReduceKeysToLongTask, ConcurrentHashMapV8.ObjectToLong<? super K> paramObjectToLong, long paramLong, ConcurrentHashMapV8.LongByLongToLong paramLongByLongToLong)
/*      */     {
/* 5624 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceKeysToLongTask;
/* 5625 */       this.transformer = paramObjectToLong;
/* 5626 */       this.basis = paramLong;this.reducer = paramLongByLongToLong; }
/*      */     
/* 5628 */     public final Long getRawResult() { return Long.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectToLong localObjectToLong;
/*      */       ConcurrentHashMapV8.LongByLongToLong localLongByLongToLong;
/* 5632 */       if (((localObjectToLong = this.transformer) != null) && ((localLongByLongToLong = this.reducer) != null))
/*      */       {
/* 5634 */         long l = this.basis;
/* 5635 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5637 */           addToPendingCount(1);
/* 5638 */           (this.rights = new MapReduceKeysToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localObjectToLong, l, localLongByLongToLong)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5642 */         while ((localObject = advance()) != null)
/* 5643 */           l = localLongByLongToLong.apply(l, localObjectToLong.apply(((ConcurrentHashMapV8.Node)localObject).key));
/* 5644 */         this.result = l;
/*      */         
/* 5646 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5648 */           MapReduceKeysToLongTask localMapReduceKeysToLongTask1 = (MapReduceKeysToLongTask)localObject;
/* 5649 */           MapReduceKeysToLongTask localMapReduceKeysToLongTask2 = localMapReduceKeysToLongTask1.rights;
/* 5650 */           while (localMapReduceKeysToLongTask2 != null) {
/* 5651 */             localMapReduceKeysToLongTask1.result = localLongByLongToLong.apply(localMapReduceKeysToLongTask1.result, localMapReduceKeysToLongTask2.result);
/* 5652 */             localMapReduceKeysToLongTask2 = localMapReduceKeysToLongTask1.rights = localMapReduceKeysToLongTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceValuesToLongTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Long>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToLong<? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.LongByLongToLong reducer;
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceValuesToLongTask<K, V> rights;
/*      */     MapReduceValuesToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToLongTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceValuesToLongTask<K, V> paramMapReduceValuesToLongTask, ConcurrentHashMapV8.ObjectToLong<? super V> paramObjectToLong, long paramLong, ConcurrentHashMapV8.LongByLongToLong paramLongByLongToLong)
/*      */     {
/* 5673 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceValuesToLongTask;
/* 5674 */       this.transformer = paramObjectToLong;
/* 5675 */       this.basis = paramLong;this.reducer = paramLongByLongToLong; }
/*      */     
/* 5677 */     public final Long getRawResult() { return Long.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectToLong localObjectToLong;
/*      */       ConcurrentHashMapV8.LongByLongToLong localLongByLongToLong;
/* 5681 */       if (((localObjectToLong = this.transformer) != null) && ((localLongByLongToLong = this.reducer) != null))
/*      */       {
/* 5683 */         long l = this.basis;
/* 5684 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5686 */           addToPendingCount(1);
/* 5687 */           (this.rights = new MapReduceValuesToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localObjectToLong, l, localLongByLongToLong)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5691 */         while ((localObject = advance()) != null)
/* 5692 */           l = localLongByLongToLong.apply(l, localObjectToLong.apply(((ConcurrentHashMapV8.Node)localObject).val));
/* 5693 */         this.result = l;
/*      */         
/* 5695 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5697 */           MapReduceValuesToLongTask localMapReduceValuesToLongTask1 = (MapReduceValuesToLongTask)localObject;
/* 5698 */           MapReduceValuesToLongTask localMapReduceValuesToLongTask2 = localMapReduceValuesToLongTask1.rights;
/* 5699 */           while (localMapReduceValuesToLongTask2 != null) {
/* 5700 */             localMapReduceValuesToLongTask1.result = localLongByLongToLong.apply(localMapReduceValuesToLongTask1.result, localMapReduceValuesToLongTask2.result);
/* 5701 */             localMapReduceValuesToLongTask2 = localMapReduceValuesToLongTask1.rights = localMapReduceValuesToLongTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceEntriesToLongTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Long>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToLong<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.LongByLongToLong reducer;
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceEntriesToLongTask<K, V> rights;
/*      */     MapReduceEntriesToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToLongTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceEntriesToLongTask<K, V> paramMapReduceEntriesToLongTask, ConcurrentHashMapV8.ObjectToLong<Map.Entry<K, V>> paramObjectToLong, long paramLong, ConcurrentHashMapV8.LongByLongToLong paramLongByLongToLong)
/*      */     {
/* 5722 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceEntriesToLongTask;
/* 5723 */       this.transformer = paramObjectToLong;
/* 5724 */       this.basis = paramLong;this.reducer = paramLongByLongToLong; }
/*      */     
/* 5726 */     public final Long getRawResult() { return Long.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectToLong localObjectToLong;
/*      */       ConcurrentHashMapV8.LongByLongToLong localLongByLongToLong;
/* 5730 */       if (((localObjectToLong = this.transformer) != null) && ((localLongByLongToLong = this.reducer) != null))
/*      */       {
/* 5732 */         long l = this.basis;
/* 5733 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5735 */           addToPendingCount(1);
/* 5736 */           (this.rights = new MapReduceEntriesToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localObjectToLong, l, localLongByLongToLong)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5740 */         while ((localObject = advance()) != null)
/* 5741 */           l = localLongByLongToLong.apply(l, localObjectToLong.apply(localObject));
/* 5742 */         this.result = l;
/*      */         
/* 5744 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5746 */           MapReduceEntriesToLongTask localMapReduceEntriesToLongTask1 = (MapReduceEntriesToLongTask)localObject;
/* 5747 */           MapReduceEntriesToLongTask localMapReduceEntriesToLongTask2 = localMapReduceEntriesToLongTask1.rights;
/* 5748 */           while (localMapReduceEntriesToLongTask2 != null) {
/* 5749 */             localMapReduceEntriesToLongTask1.result = localLongByLongToLong.apply(localMapReduceEntriesToLongTask1.result, localMapReduceEntriesToLongTask2.result);
/* 5750 */             localMapReduceEntriesToLongTask2 = localMapReduceEntriesToLongTask1.rights = localMapReduceEntriesToLongTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceMappingsToLongTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Long>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectByObjectToLong<? super K, ? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.LongByLongToLong reducer;
/*      */     final long basis;
/*      */     long result;
/*      */     MapReduceMappingsToLongTask<K, V> rights;
/*      */     MapReduceMappingsToLongTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToLongTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceMappingsToLongTask<K, V> paramMapReduceMappingsToLongTask, ConcurrentHashMapV8.ObjectByObjectToLong<? super K, ? super V> paramObjectByObjectToLong, long paramLong, ConcurrentHashMapV8.LongByLongToLong paramLongByLongToLong)
/*      */     {
/* 5771 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceMappingsToLongTask;
/* 5772 */       this.transformer = paramObjectByObjectToLong;
/* 5773 */       this.basis = paramLong;this.reducer = paramLongByLongToLong; }
/*      */     
/* 5775 */     public final Long getRawResult() { return Long.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectByObjectToLong localObjectByObjectToLong;
/*      */       ConcurrentHashMapV8.LongByLongToLong localLongByLongToLong;
/* 5779 */       if (((localObjectByObjectToLong = this.transformer) != null) && ((localLongByLongToLong = this.reducer) != null))
/*      */       {
/* 5781 */         long l = this.basis;
/* 5782 */         int j; int k; for (int i = this.baseIndex; (this.batch > 0) && ((k = (j = this.baseLimit) + i >>> 1) > i);)
/*      */         {
/* 5784 */           addToPendingCount(1);
/* 5785 */           (this.rights = new MapReduceMappingsToLongTask(this, this.batch >>>= 1, this.baseLimit = k, j, this.tab, this.rights, localObjectByObjectToLong, l, localLongByLongToLong)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5789 */         while ((localObject = advance()) != null)
/* 5790 */           l = localLongByLongToLong.apply(l, localObjectByObjectToLong.apply(((ConcurrentHashMapV8.Node)localObject).key, ((ConcurrentHashMapV8.Node)localObject).val));
/* 5791 */         this.result = l;
/*      */         
/* 5793 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5795 */           MapReduceMappingsToLongTask localMapReduceMappingsToLongTask1 = (MapReduceMappingsToLongTask)localObject;
/* 5796 */           MapReduceMappingsToLongTask localMapReduceMappingsToLongTask2 = localMapReduceMappingsToLongTask1.rights;
/* 5797 */           while (localMapReduceMappingsToLongTask2 != null) {
/* 5798 */             localMapReduceMappingsToLongTask1.result = localLongByLongToLong.apply(localMapReduceMappingsToLongTask1.result, localMapReduceMappingsToLongTask2.result);
/* 5799 */             localMapReduceMappingsToLongTask2 = localMapReduceMappingsToLongTask1.rights = localMapReduceMappingsToLongTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceKeysToIntTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Integer>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToInt<? super K> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.IntByIntToInt reducer;
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceKeysToIntTask<K, V> rights;
/*      */     MapReduceKeysToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceKeysToIntTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceKeysToIntTask<K, V> paramMapReduceKeysToIntTask, ConcurrentHashMapV8.ObjectToInt<? super K> paramObjectToInt, int paramInt4, ConcurrentHashMapV8.IntByIntToInt paramIntByIntToInt)
/*      */     {
/* 5820 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceKeysToIntTask;
/* 5821 */       this.transformer = paramObjectToInt;
/* 5822 */       this.basis = paramInt4;this.reducer = paramIntByIntToInt; }
/*      */     
/* 5824 */     public final Integer getRawResult() { return Integer.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectToInt localObjectToInt;
/*      */       ConcurrentHashMapV8.IntByIntToInt localIntByIntToInt;
/* 5828 */       if (((localObjectToInt = this.transformer) != null) && ((localIntByIntToInt = this.reducer) != null))
/*      */       {
/* 5830 */         int i = this.basis;
/* 5831 */         int k; int m; for (int j = this.baseIndex; (this.batch > 0) && ((m = (k = this.baseLimit) + j >>> 1) > j);)
/*      */         {
/* 5833 */           addToPendingCount(1);
/* 5834 */           (this.rights = new MapReduceKeysToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, localObjectToInt, i, localIntByIntToInt)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5838 */         while ((localObject = advance()) != null)
/* 5839 */           i = localIntByIntToInt.apply(i, localObjectToInt.apply(((ConcurrentHashMapV8.Node)localObject).key));
/* 5840 */         this.result = i;
/*      */         
/* 5842 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5844 */           MapReduceKeysToIntTask localMapReduceKeysToIntTask1 = (MapReduceKeysToIntTask)localObject;
/* 5845 */           MapReduceKeysToIntTask localMapReduceKeysToIntTask2 = localMapReduceKeysToIntTask1.rights;
/* 5846 */           while (localMapReduceKeysToIntTask2 != null) {
/* 5847 */             localMapReduceKeysToIntTask1.result = localIntByIntToInt.apply(localMapReduceKeysToIntTask1.result, localMapReduceKeysToIntTask2.result);
/* 5848 */             localMapReduceKeysToIntTask2 = localMapReduceKeysToIntTask1.rights = localMapReduceKeysToIntTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceValuesToIntTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Integer>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToInt<? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.IntByIntToInt reducer;
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceValuesToIntTask<K, V> rights;
/*      */     MapReduceValuesToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceValuesToIntTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceValuesToIntTask<K, V> paramMapReduceValuesToIntTask, ConcurrentHashMapV8.ObjectToInt<? super V> paramObjectToInt, int paramInt4, ConcurrentHashMapV8.IntByIntToInt paramIntByIntToInt)
/*      */     {
/* 5869 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceValuesToIntTask;
/* 5870 */       this.transformer = paramObjectToInt;
/* 5871 */       this.basis = paramInt4;this.reducer = paramIntByIntToInt; }
/*      */     
/* 5873 */     public final Integer getRawResult() { return Integer.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectToInt localObjectToInt;
/*      */       ConcurrentHashMapV8.IntByIntToInt localIntByIntToInt;
/* 5877 */       if (((localObjectToInt = this.transformer) != null) && ((localIntByIntToInt = this.reducer) != null))
/*      */       {
/* 5879 */         int i = this.basis;
/* 5880 */         int k; int m; for (int j = this.baseIndex; (this.batch > 0) && ((m = (k = this.baseLimit) + j >>> 1) > j);)
/*      */         {
/* 5882 */           addToPendingCount(1);
/* 5883 */           (this.rights = new MapReduceValuesToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, localObjectToInt, i, localIntByIntToInt)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5887 */         while ((localObject = advance()) != null)
/* 5888 */           i = localIntByIntToInt.apply(i, localObjectToInt.apply(((ConcurrentHashMapV8.Node)localObject).val));
/* 5889 */         this.result = i;
/*      */         
/* 5891 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5893 */           MapReduceValuesToIntTask localMapReduceValuesToIntTask1 = (MapReduceValuesToIntTask)localObject;
/* 5894 */           MapReduceValuesToIntTask localMapReduceValuesToIntTask2 = localMapReduceValuesToIntTask1.rights;
/* 5895 */           while (localMapReduceValuesToIntTask2 != null) {
/* 5896 */             localMapReduceValuesToIntTask1.result = localIntByIntToInt.apply(localMapReduceValuesToIntTask1.result, localMapReduceValuesToIntTask2.result);
/* 5897 */             localMapReduceValuesToIntTask2 = localMapReduceValuesToIntTask1.rights = localMapReduceValuesToIntTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceEntriesToIntTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Integer>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectToInt<Map.Entry<K, V>> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.IntByIntToInt reducer;
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceEntriesToIntTask<K, V> rights;
/*      */     MapReduceEntriesToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceEntriesToIntTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceEntriesToIntTask<K, V> paramMapReduceEntriesToIntTask, ConcurrentHashMapV8.ObjectToInt<Map.Entry<K, V>> paramObjectToInt, int paramInt4, ConcurrentHashMapV8.IntByIntToInt paramIntByIntToInt)
/*      */     {
/* 5918 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceEntriesToIntTask;
/* 5919 */       this.transformer = paramObjectToInt;
/* 5920 */       this.basis = paramInt4;this.reducer = paramIntByIntToInt; }
/*      */     
/* 5922 */     public final Integer getRawResult() { return Integer.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectToInt localObjectToInt;
/*      */       ConcurrentHashMapV8.IntByIntToInt localIntByIntToInt;
/* 5926 */       if (((localObjectToInt = this.transformer) != null) && ((localIntByIntToInt = this.reducer) != null))
/*      */       {
/* 5928 */         int i = this.basis;
/* 5929 */         int k; int m; for (int j = this.baseIndex; (this.batch > 0) && ((m = (k = this.baseLimit) + j >>> 1) > j);)
/*      */         {
/* 5931 */           addToPendingCount(1);
/* 5932 */           (this.rights = new MapReduceEntriesToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, localObjectToInt, i, localIntByIntToInt)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5936 */         while ((localObject = advance()) != null)
/* 5937 */           i = localIntByIntToInt.apply(i, localObjectToInt.apply(localObject));
/* 5938 */         this.result = i;
/*      */         
/* 5940 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5942 */           MapReduceEntriesToIntTask localMapReduceEntriesToIntTask1 = (MapReduceEntriesToIntTask)localObject;
/* 5943 */           MapReduceEntriesToIntTask localMapReduceEntriesToIntTask2 = localMapReduceEntriesToIntTask1.rights;
/* 5944 */           while (localMapReduceEntriesToIntTask2 != null) {
/* 5945 */             localMapReduceEntriesToIntTask1.result = localIntByIntToInt.apply(localMapReduceEntriesToIntTask1.result, localMapReduceEntriesToIntTask2.result);
/* 5946 */             localMapReduceEntriesToIntTask2 = localMapReduceEntriesToIntTask1.rights = localMapReduceEntriesToIntTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   static final class MapReduceMappingsToIntTask<K, V>
/*      */     extends ConcurrentHashMapV8.BulkTask<K, V, Integer>
/*      */   {
/*      */     final ConcurrentHashMapV8.ObjectByObjectToInt<? super K, ? super V> transformer;
/*      */     
/*      */     final ConcurrentHashMapV8.IntByIntToInt reducer;
/*      */     final int basis;
/*      */     int result;
/*      */     MapReduceMappingsToIntTask<K, V> rights;
/*      */     MapReduceMappingsToIntTask<K, V> nextRight;
/*      */     
/*      */     MapReduceMappingsToIntTask(ConcurrentHashMapV8.BulkTask<K, V, ?> paramBulkTask, int paramInt1, int paramInt2, int paramInt3, ConcurrentHashMapV8.Node<K, V>[] paramArrayOfNode, MapReduceMappingsToIntTask<K, V> paramMapReduceMappingsToIntTask, ConcurrentHashMapV8.ObjectByObjectToInt<? super K, ? super V> paramObjectByObjectToInt, int paramInt4, ConcurrentHashMapV8.IntByIntToInt paramIntByIntToInt)
/*      */     {
/* 5967 */       super(paramInt1, paramInt2, paramInt3, paramArrayOfNode);this.nextRight = paramMapReduceMappingsToIntTask;
/* 5968 */       this.transformer = paramObjectByObjectToInt;
/* 5969 */       this.basis = paramInt4;this.reducer = paramIntByIntToInt; }
/*      */     
/* 5971 */     public final Integer getRawResult() { return Integer.valueOf(this.result); }
/*      */     
/*      */     public final void compute() { ConcurrentHashMapV8.ObjectByObjectToInt localObjectByObjectToInt;
/*      */       ConcurrentHashMapV8.IntByIntToInt localIntByIntToInt;
/* 5975 */       if (((localObjectByObjectToInt = this.transformer) != null) && ((localIntByIntToInt = this.reducer) != null))
/*      */       {
/* 5977 */         int i = this.basis;
/* 5978 */         int k; int m; for (int j = this.baseIndex; (this.batch > 0) && ((m = (k = this.baseLimit) + j >>> 1) > j);)
/*      */         {
/* 5980 */           addToPendingCount(1);
/* 5981 */           (this.rights = new MapReduceMappingsToIntTask(this, this.batch >>>= 1, this.baseLimit = m, k, this.tab, this.rights, localObjectByObjectToInt, i, localIntByIntToInt)).fork();
/*      */         }
/*      */         
/*      */ 
/* 5985 */         while ((localObject = advance()) != null)
/* 5986 */           i = localIntByIntToInt.apply(i, localObjectByObjectToInt.apply(((ConcurrentHashMapV8.Node)localObject).key, ((ConcurrentHashMapV8.Node)localObject).val));
/* 5987 */         this.result = i;
/*      */         
/* 5989 */         for (Object localObject = firstComplete(); localObject != null; localObject = ((CountedCompleter)localObject).nextComplete())
/*      */         {
/* 5991 */           MapReduceMappingsToIntTask localMapReduceMappingsToIntTask1 = (MapReduceMappingsToIntTask)localObject;
/* 5992 */           MapReduceMappingsToIntTask localMapReduceMappingsToIntTask2 = localMapReduceMappingsToIntTask1.rights;
/* 5993 */           while (localMapReduceMappingsToIntTask2 != null) {
/* 5994 */             localMapReduceMappingsToIntTask1.result = localIntByIntToInt.apply(localMapReduceMappingsToIntTask1.result, localMapReduceMappingsToIntTask2.result);
/* 5995 */             localMapReduceMappingsToIntTask2 = localMapReduceMappingsToIntTask1.rights = localMapReduceMappingsToIntTask2.nextRight;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   static final class CounterCell {
/*      */     volatile long p0;
/*      */     volatile long p1;
/*      */     volatile long p2;
/*      */     volatile long p3;
/*      */     volatile long p4;
/*      */     volatile long p5;
/*      */     volatile long p6;
/*      */     
/*      */     CounterCell(long paramLong) {
/* 6012 */       this.value = paramLong;
/*      */     }
/*      */     
/*      */ 
/*      */     volatile long value;
/*      */     
/*      */     volatile long q0;
/*      */     volatile long q1;
/*      */     volatile long q2;
/*      */     volatile long q3;
/*      */     volatile long q4;
/*      */     volatile long q5;
/*      */     volatile long q6;
/*      */   }
/*      */   
/* 6027 */   static final AtomicInteger counterHashCodeGenerator = new AtomicInteger();
/*      */   static final int SEED_INCREMENT = 1640531527;
/*      */   private static final Unsafe U;
/*      */   private static final long SIZECTL;
/*      */   private static final long TRANSFERINDEX;
/*      */   private static final long TRANSFERORIGIN;
/*      */   
/*      */   final long sumCount()
/*      */   {
/* 6036 */     CounterCell[] arrayOfCounterCell = this.counterCells;
/* 6037 */     long l = this.baseCount;
/* 6038 */     if (arrayOfCounterCell != null) {
/* 6039 */       for (int i = 0; i < arrayOfCounterCell.length; i++) { CounterCell localCounterCell;
/* 6040 */         if ((localCounterCell = arrayOfCounterCell[i]) != null)
/* 6041 */           l += localCounterCell.value;
/*      */       }
/*      */     }
/* 6044 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */   private final void fullAddCount(InternalThreadLocalMap paramInternalThreadLocalMap, long paramLong, IntegerHolder paramIntegerHolder, boolean paramBoolean)
/*      */   {
/*      */     int j;
/*      */     
/* 6052 */     if (paramIntegerHolder == null) {
/* 6053 */       paramIntegerHolder = new IntegerHolder();
/* 6054 */       i = counterHashCodeGenerator.addAndGet(1640531527);
/* 6055 */       j = paramIntegerHolder.value = i == 0 ? 1 : i;
/* 6056 */       paramInternalThreadLocalMap.setCounterHashCode(paramIntegerHolder);
/*      */     }
/*      */     else {
/* 6059 */       j = paramIntegerHolder.value; }
/* 6060 */     int i = 0;
/*      */     for (;;) { CounterCell[] arrayOfCounterCell1;
/*      */       int k;
/* 6063 */       long l; if (((arrayOfCounterCell1 = this.counterCells) != null) && ((k = arrayOfCounterCell1.length) > 0)) { CounterCell localCounterCell;
/* 6064 */         Object localObject1; int n; if ((localCounterCell = arrayOfCounterCell1[(k - 1 & j)]) == null) {
/* 6065 */           if (this.cellsBusy == 0) {
/* 6066 */             localObject1 = new CounterCell(paramLong);
/* 6067 */             if ((this.cellsBusy == 0) && (U.compareAndSwapInt(this, CELLSBUSY, 0, 1)))
/*      */             {
/* 6069 */               n = 0;
/*      */               try { CounterCell[] arrayOfCounterCell3;
/*      */                 int i1;
/* 6072 */                 int i2; if (((arrayOfCounterCell3 = this.counterCells) != null) && ((i1 = arrayOfCounterCell3.length) > 0) && (arrayOfCounterCell3[(i2 = i1 - 1 & j)] == null))
/*      */                 {
/*      */ 
/* 6075 */                   arrayOfCounterCell3[i2] = localObject1;
/* 6076 */                   n = 1;
/*      */                 }
/*      */               } finally {
/* 6079 */                 this.cellsBusy = 0;
/*      */               }
/* 6081 */               if (n == 0) continue;
/* 6082 */               break;
/*      */             }
/*      */           }
/*      */           
/* 6086 */           i = 0;
/*      */         }
/* 6088 */         else if (!paramBoolean) {
/* 6089 */           paramBoolean = true;
/* 6090 */         } else { if (U.compareAndSwapLong(localCounterCell, CELLVALUE, l = localCounterCell.value, l + paramLong))
/*      */             break;
/* 6092 */           if ((this.counterCells != arrayOfCounterCell1) || (k >= NCPU)) {
/* 6093 */             i = 0;
/* 6094 */           } else if (i == 0) {
/* 6095 */             i = 1;
/* 6096 */           } else if ((this.cellsBusy == 0) && (U.compareAndSwapInt(this, CELLSBUSY, 0, 1)))
/*      */           {
/*      */             try {
/* 6099 */               if (this.counterCells == arrayOfCounterCell1) {
/* 6100 */                 localObject1 = new CounterCell[k << 1];
/* 6101 */                 for (n = 0; n < k; n++)
/* 6102 */                   localObject1[n] = arrayOfCounterCell1[n];
/* 6103 */                 this.counterCells = ((CounterCell[])localObject1);
/*      */               }
/*      */             } finally {
/* 6106 */               this.cellsBusy = 0;
/*      */             }
/* 6108 */             i = 0;
/* 6109 */             continue;
/*      */           } }
/* 6111 */         j ^= j << 13;
/* 6112 */         j ^= j >>> 17;
/* 6113 */         j ^= j << 5;
/*      */       }
/* 6115 */       else if ((this.cellsBusy == 0) && (this.counterCells == arrayOfCounterCell1) && (U.compareAndSwapInt(this, CELLSBUSY, 0, 1)))
/*      */       {
/* 6117 */         int m = 0;
/*      */         try {
/* 6119 */           if (this.counterCells == arrayOfCounterCell1) {
/* 6120 */             CounterCell[] arrayOfCounterCell2 = new CounterCell[2];
/* 6121 */             arrayOfCounterCell2[(j & 0x1)] = new CounterCell(paramLong);
/* 6122 */             this.counterCells = arrayOfCounterCell2;
/* 6123 */             m = 1;
/*      */           }
/*      */         } finally {
/* 6126 */           this.cellsBusy = 0;
/*      */         }
/* 6128 */         if (m != 0)
/*      */           break;
/*      */       } else {
/* 6131 */         if (U.compareAndSwapLong(this, BASECOUNT, l = this.baseCount, l + paramLong)) break;
/*      */       }
/*      */     }
/* 6134 */     paramIntegerHolder.value = j;
/*      */   }
/*      */   
/*      */ 
/*      */   private static final long BASECOUNT;
/*      */   
/*      */   private static final long CELLSBUSY;
/*      */   
/*      */   private static final long CELLVALUE;
/*      */   
/*      */   private static final long ABASE;
/*      */   private static final int ASHIFT;
/*      */   static
/*      */   {
/*      */     try
/*      */     {
/* 6150 */       U = getUnsafe();
/* 6151 */       Class localClass1 = ConcurrentHashMapV8.class;
/* 6152 */       SIZECTL = U.objectFieldOffset(localClass1.getDeclaredField("sizeCtl"));
/*      */       
/* 6154 */       TRANSFERINDEX = U.objectFieldOffset(localClass1.getDeclaredField("transferIndex"));
/*      */       
/* 6156 */       TRANSFERORIGIN = U.objectFieldOffset(localClass1.getDeclaredField("transferOrigin"));
/*      */       
/* 6158 */       BASECOUNT = U.objectFieldOffset(localClass1.getDeclaredField("baseCount"));
/*      */       
/* 6160 */       CELLSBUSY = U.objectFieldOffset(localClass1.getDeclaredField("cellsBusy"));
/*      */       
/* 6162 */       Class localClass2 = CounterCell.class;
/* 6163 */       CELLVALUE = U.objectFieldOffset(localClass2.getDeclaredField("value"));
/*      */       
/* 6165 */       Class localClass3 = Node[].class;
/* 6166 */       ABASE = U.arrayBaseOffset(localClass3);
/* 6167 */       int i = U.arrayIndexScale(localClass3);
/* 6168 */       if ((i & i - 1) != 0)
/* 6169 */         throw new Error("data type scale not a power of two");
/* 6170 */       ASHIFT = 31 - Integer.numberOfLeadingZeros(i);
/*      */     } catch (Exception localException) {
/* 6172 */       throw new Error(localException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Unsafe getUnsafe()
/*      */   {
/*      */     try
/*      */     {
/* 6185 */       return Unsafe.getUnsafe();
/*      */     } catch (SecurityException localSecurityException) {
/*      */       try {
/* 6188 */         (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */         {
/*      */           public Unsafe run() throws Exception {
/* 6191 */             Class localClass = Unsafe.class;
/* 6192 */             for (Field localField : localClass.getDeclaredFields()) {
/* 6193 */               localField.setAccessible(true);
/* 6194 */               Object localObject = localField.get(null);
/* 6195 */               if (localClass.isInstance(localObject))
/* 6196 */                 return (Unsafe)localClass.cast(localObject);
/*      */             }
/* 6198 */             throw new NoSuchFieldError("the Unsafe");
/*      */           }
/*      */         });
/* 6201 */       } catch (PrivilegedActionException localPrivilegedActionException) { throw new RuntimeException("Could not initialize intrinsics", localPrivilegedActionException.getCause());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public ConcurrentHashMapV8() {}
/*      */   
/*      */   static final class CounterHashCode
/*      */   {
/*      */     int code;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\chmv8\ConcurrentHashMapV8.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */