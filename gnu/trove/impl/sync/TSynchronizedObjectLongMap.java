/*     */ package gnu.trove.impl.sync;
/*     */ 
/*     */ import gnu.trove.TLongCollection;
/*     */ import gnu.trove.function.TLongFunction;
/*     */ import gnu.trove.iterator.TObjectLongIterator;
/*     */ import gnu.trove.map.TObjectLongMap;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TSynchronizedObjectLongMap<K>
/*     */   implements TObjectLongMap<K>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1978198479659022715L;
/*     */   private final TObjectLongMap<K> m;
/*     */   final Object mutex;
/*     */   
/*     */   public TSynchronizedObjectLongMap(TObjectLongMap<K> m)
/*     */   {
/*  59 */     if (m == null)
/*  60 */       throw new NullPointerException();
/*  61 */     this.m = m;
/*  62 */     this.mutex = this;
/*     */   }
/*     */   
/*     */   public TSynchronizedObjectLongMap(TObjectLongMap<K> m, Object mutex) {
/*  66 */     this.m = m;
/*  67 */     this.mutex = mutex;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int size()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: invokeinterface 48 1 0
/*     */     //   16: aload_1
/*     */     //   17: monitorexit
/*     */     //   18: ireturn
/*     */     //   19: astore_2
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: aload_2
/*     */     //   23: athrow
/*     */     // Line number table:
/*     */     //   Java source line #71	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	24	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean isEmpty()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: invokeinterface 52 1 0
/*     */     //   16: aload_1
/*     */     //   17: monitorexit
/*     */     //   18: ireturn
/*     */     //   19: astore_2
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: aload_2
/*     */     //   23: athrow
/*     */     // Line number table:
/*     */     //   Java source line #74	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	24	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean containsKey(Object key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 56 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #77	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	key	Object
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean containsValue(long value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: lload_1
/*     */     //   12: invokeinterface 61 3 0
/*     */     //   17: aload_3
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore 4
/*     */     //   22: aload_3
/*     */     //   23: monitorexit
/*     */     //   24: aload 4
/*     */     //   26: athrow
/*     */     // Line number table:
/*     */     //   Java source line #80	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	27	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	27	1	value	long
/*     */     //   5	18	3	Ljava/lang/Object;	Object
/*     */     //   20	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	24	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long get(Object key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 66 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: lreturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #83	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	key	Object
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long put(K key, long value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore 4
/*     */     //   7: monitorenter
/*     */     //   8: aload_0
/*     */     //   9: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   12: aload_1
/*     */     //   13: lload_2
/*     */     //   14: invokeinterface 70 4 0
/*     */     //   19: aload 4
/*     */     //   21: monitorexit
/*     */     //   22: lreturn
/*     */     //   23: astore 5
/*     */     //   25: aload 4
/*     */     //   27: monitorexit
/*     */     //   28: aload 5
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #87	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	31	1	key	K
/*     */     //   0	31	2	value	long
/*     */     //   5	21	4	Ljava/lang/Object;	Object
/*     */     //   23	6	5	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   8	22	23	finally
/*     */     //   23	28	23	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long remove(Object key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 74 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: lreturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #90	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	key	Object
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends Long> map)
/*     */   {
/*  93 */     synchronized (this.mutex) { this.m.putAll(map);
/*     */     } }
/*     */   
/*  96 */   public void putAll(TObjectLongMap<? extends K> map) { synchronized (this.mutex) { this.m.putAll(map);
/*     */     } }
/*     */   
/*  99 */   public void clear() { synchronized (this.mutex) { this.m.clear();
/*     */     } }
/*     */   
/* 102 */   private transient Set<K> keySet = null;
/* 103 */   private transient TLongCollection values = null;
/*     */   
/*     */   public Set<K> keySet() {
/* 106 */     synchronized (this.mutex) {
/* 107 */       if (this.keySet == null) {
/* 108 */         this.keySet = new SynchronizedSet(this.m.keySet(), this.mutex);
/*     */       }
/* 110 */       return this.keySet;
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public Object[] keys()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: invokeinterface 99 1 0
/*     */     //   16: aload_1
/*     */     //   17: monitorexit
/*     */     //   18: areturn
/*     */     //   19: astore_2
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: aload_2
/*     */     //   23: athrow
/*     */     // Line number table:
/*     */     //   Java source line #114	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	24	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public K[] keys(K[] array)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 102 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: areturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #117	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	array	K[]
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public TLongCollection valueCollection()
/*     */   {
/* 121 */     synchronized (this.mutex) {
/* 122 */       if (this.values == null)
/* 123 */         this.values = new TSynchronizedLongCollection(this.m.valueCollection(), this.mutex);
/* 124 */       return this.values;
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long[] values()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: invokeinterface 117 1 0
/*     */     //   16: aload_1
/*     */     //   17: monitorexit
/*     */     //   18: areturn
/*     */     //   19: astore_2
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: aload_2
/*     */     //   23: athrow
/*     */     // Line number table:
/*     */     //   Java source line #128	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	24	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long[] values(long[] array)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 120 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: areturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #131	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	array	long[]
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public TObjectLongIterator<K> iterator()
/*     */   {
/* 135 */     return this.m.iterator();
/*     */   }
/*     */   
/*     */   public long getNoEntryValue() {
/* 139 */     return this.m.getNoEntryValue();
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long putIfAbsent(K key, long value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore 4
/*     */     //   7: monitorenter
/*     */     //   8: aload_0
/*     */     //   9: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   12: aload_1
/*     */     //   13: lload_2
/*     */     //   14: invokeinterface 132 4 0
/*     */     //   19: aload 4
/*     */     //   21: monitorexit
/*     */     //   22: lreturn
/*     */     //   23: astore 5
/*     */     //   25: aload 4
/*     */     //   27: monitorexit
/*     */     //   28: aload 5
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #142	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	31	1	key	K
/*     */     //   0	31	2	value	long
/*     */     //   5	21	4	Ljava/lang/Object;	Object
/*     */     //   23	6	5	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   8	22	23	finally
/*     */     //   23	28	23	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean forEachKey(gnu.trove.procedure.TObjectProcedure<? super K> procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 136 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #145	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TObjectProcedure<? super K>
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean forEachValue(gnu.trove.procedure.TLongProcedure procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 143 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #148	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TLongProcedure
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean forEachEntry(gnu.trove.procedure.TObjectLongProcedure<? super K> procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 148 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #151	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TObjectLongProcedure<? super K>
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public void transformValues(TLongFunction function)
/*     */   {
/* 154 */     synchronized (this.mutex) { this.m.transformValues(function);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean retainEntries(gnu.trove.procedure.TObjectLongProcedure<? super K> procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 159 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #157	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TObjectLongProcedure<? super K>
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean increment(K key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 162 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #160	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	25	1	key	K
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean adjustValue(K key, long amount)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore 4
/*     */     //   7: monitorenter
/*     */     //   8: aload_0
/*     */     //   9: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   12: aload_1
/*     */     //   13: lload_2
/*     */     //   14: invokeinterface 166 4 0
/*     */     //   19: aload 4
/*     */     //   21: monitorexit
/*     */     //   22: ireturn
/*     */     //   23: astore 5
/*     */     //   25: aload 4
/*     */     //   27: monitorexit
/*     */     //   28: aload 5
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #163	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	31	1	key	K
/*     */     //   0	31	2	amount	long
/*     */     //   5	21	4	Ljava/lang/Object;	Object
/*     */     //   23	6	5	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   8	22	23	finally
/*     */     //   23	28	23	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long adjustOrPutValue(K key, long adjust_amount, long put_amount)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore 6
/*     */     //   7: monitorenter
/*     */     //   8: aload_0
/*     */     //   9: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   12: aload_1
/*     */     //   13: lload_2
/*     */     //   14: lload 4
/*     */     //   16: invokeinterface 171 6 0
/*     */     //   21: aload 6
/*     */     //   23: monitorexit
/*     */     //   24: lreturn
/*     */     //   25: astore 7
/*     */     //   27: aload 6
/*     */     //   29: monitorexit
/*     */     //   30: aload 7
/*     */     //   32: athrow
/*     */     // Line number table:
/*     */     //   Java source line #166	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	33	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	33	1	key	K
/*     */     //   0	33	2	adjust_amount	long
/*     */     //   0	33	4	put_amount	long
/*     */     //   5	23	6	Ljava/lang/Object;	Object
/*     */     //   25	6	7	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   8	24	25	finally
/*     */     //   25	30	25	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean equals(Object o)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: aload_1
/*     */     //   12: invokevirtual 176	java/lang/Object:equals	(Ljava/lang/Object;)Z
/*     */     //   15: aload_2
/*     */     //   16: monitorexit
/*     */     //   17: ireturn
/*     */     //   18: astore_3
/*     */     //   19: aload_2
/*     */     //   20: monitorexit
/*     */     //   21: aload_3
/*     */     //   22: athrow
/*     */     // Line number table:
/*     */     //   Java source line #170	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	23	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   0	23	1	o	Object
/*     */     //   5	15	2	Ljava/lang/Object;	Object
/*     */     //   18	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	17	18	finally
/*     */     //   18	21	18	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int hashCode()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: invokevirtual 180	java/lang/Object:hashCode	()I
/*     */     //   14: aload_1
/*     */     //   15: monitorexit
/*     */     //   16: ireturn
/*     */     //   17: astore_2
/*     */     //   18: aload_1
/*     */     //   19: monitorexit
/*     */     //   20: aload_2
/*     */     //   21: athrow
/*     */     // Line number table:
/*     */     //   Java source line #173	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	22	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   5	14	1	Ljava/lang/Object;	Object
/*     */     //   17	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	16	17	finally
/*     */     //   17	20	17	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public String toString()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 40	gnu/trove/impl/sync/TSynchronizedObjectLongMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 38	gnu/trove/impl/sync/TSynchronizedObjectLongMap:m	Lgnu/trove/map/TObjectLongMap;
/*     */     //   11: invokevirtual 184	java/lang/Object:toString	()Ljava/lang/String;
/*     */     //   14: aload_1
/*     */     //   15: monitorexit
/*     */     //   16: areturn
/*     */     //   17: astore_2
/*     */     //   18: aload_1
/*     */     //   19: monitorexit
/*     */     //   20: aload_2
/*     */     //   21: athrow
/*     */     // Line number table:
/*     */     //   Java source line #176	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	22	0	this	TSynchronizedObjectLongMap<K>
/*     */     //   5	14	1	Ljava/lang/Object;	Object
/*     */     //   17	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	16	17	finally
/*     */     //   17	20	17	finally
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream s)
/*     */     throws IOException
/*     */   {
/* 179 */     synchronized (this.mutex) { s.defaultWriteObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\sync\TSynchronizedObjectLongMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */