/*     */ package gnu.trove.impl.sync;
/*     */ 
/*     */ import gnu.trove.TIntCollection;
/*     */ import gnu.trove.function.TIntFunction;
/*     */ import gnu.trove.iterator.TLongIntIterator;
/*     */ import gnu.trove.map.TLongIntMap;
/*     */ import gnu.trove.set.TLongSet;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ 
/*     */ public class TSynchronizedLongIntMap
/*     */   implements TLongIntMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1978198479659022715L;
/*     */   private final TLongIntMap m;
/*     */   final Object mutex;
/*     */   
/*     */   public TSynchronizedLongIntMap(TLongIntMap m)
/*     */   {
/*  59 */     if (m == null)
/*  60 */       throw new NullPointerException();
/*  61 */     this.m = m;
/*  62 */     this.mutex = this;
/*     */   }
/*     */   
/*     */   public TSynchronizedLongIntMap(TLongIntMap m, Object mutex) {
/*  66 */     this.m = m;
/*  67 */     this.mutex = mutex;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int size()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: invokeinterface 44 1 0
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
/*     */     //   0	24	0	this	TSynchronizedLongIntMap
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
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
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
/*     */     //   Java source line #74	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	24	0	this	TSynchronizedLongIntMap
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean containsKey(long key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: lload_1
/*     */     //   12: invokeinterface 52 3 0
/*     */     //   17: aload_3
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore 4
/*     */     //   22: aload_3
/*     */     //   23: monitorexit
/*     */     //   24: aload 4
/*     */     //   26: athrow
/*     */     // Line number table:
/*     */     //   Java source line #77	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	27	0	this	TSynchronizedLongIntMap
/*     */     //   0	27	1	key	long
/*     */     //   5	18	3	Ljava/lang/Object;	Object
/*     */     //   20	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	24	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean containsValue(int value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: iload_1
/*     */     //   12: invokeinterface 57 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #80	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedLongIntMap
/*     */     //   0	25	1	value	int
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int get(long key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: lload_1
/*     */     //   12: invokeinterface 63 3 0
/*     */     //   17: aload_3
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore 4
/*     */     //   22: aload_3
/*     */     //   23: monitorexit
/*     */     //   24: aload 4
/*     */     //   26: athrow
/*     */     // Line number table:
/*     */     //   Java source line #83	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	27	0	this	TSynchronizedLongIntMap
/*     */     //   0	27	1	key	long
/*     */     //   5	18	3	Ljava/lang/Object;	Object
/*     */     //   20	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	24	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int put(long key, int value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore 4
/*     */     //   7: monitorenter
/*     */     //   8: aload_0
/*     */     //   9: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   12: lload_1
/*     */     //   13: iload_3
/*     */     //   14: invokeinterface 67 4 0
/*     */     //   19: aload 4
/*     */     //   21: monitorexit
/*     */     //   22: ireturn
/*     */     //   23: astore 5
/*     */     //   25: aload 4
/*     */     //   27: monitorexit
/*     */     //   28: aload 5
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #87	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	TSynchronizedLongIntMap
/*     */     //   0	31	1	key	long
/*     */     //   0	31	3	value	int
/*     */     //   5	21	4	Ljava/lang/Object;	Object
/*     */     //   23	6	5	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   8	22	23	finally
/*     */     //   23	28	23	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int remove(long key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: lload_1
/*     */     //   12: invokeinterface 70 3 0
/*     */     //   17: aload_3
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore 4
/*     */     //   22: aload_3
/*     */     //   23: monitorexit
/*     */     //   24: aload 4
/*     */     //   26: athrow
/*     */     // Line number table:
/*     */     //   Java source line #90	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	27	0	this	TSynchronizedLongIntMap
/*     */     //   0	27	1	key	long
/*     */     //   5	18	3	Ljava/lang/Object;	Object
/*     */     //   20	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	24	20	finally
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends Long, ? extends Integer> map)
/*     */   {
/*  93 */     synchronized (this.mutex) { this.m.putAll(map);
/*     */     } }
/*     */   
/*  96 */   public void putAll(TLongIntMap map) { synchronized (this.mutex) { this.m.putAll(map);
/*     */     } }
/*     */   
/*  99 */   public void clear() { synchronized (this.mutex) { this.m.clear();
/*     */     } }
/*     */   
/* 102 */   private transient TLongSet keySet = null;
/* 103 */   private transient TIntCollection values = null;
/*     */   
/*     */   public TLongSet keySet() {
/* 106 */     synchronized (this.mutex) {
/* 107 */       if (this.keySet == null)
/* 108 */         this.keySet = new TSynchronizedLongSet(this.m.keySet(), this.mutex);
/* 109 */       return this.keySet;
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long[] keys()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: invokeinterface 94 1 0
/*     */     //   16: aload_1
/*     */     //   17: monitorexit
/*     */     //   18: areturn
/*     */     //   19: astore_2
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: aload_2
/*     */     //   23: athrow
/*     */     // Line number table:
/*     */     //   Java source line #113	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	24	0	this	TSynchronizedLongIntMap
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long[] keys(long[] array)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 97 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: areturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #116	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedLongIntMap
/*     */     //   0	25	1	array	long[]
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public TIntCollection valueCollection()
/*     */   {
/* 120 */     synchronized (this.mutex) {
/* 121 */       if (this.values == null)
/* 122 */         this.values = new TSynchronizedIntCollection(this.m.valueCollection(), this.mutex);
/* 123 */       return this.values;
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int[] values()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: invokeinterface 111 1 0
/*     */     //   16: aload_1
/*     */     //   17: monitorexit
/*     */     //   18: areturn
/*     */     //   19: astore_2
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: aload_2
/*     */     //   23: athrow
/*     */     // Line number table:
/*     */     //   Java source line #127	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	24	0	this	TSynchronizedLongIntMap
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int[] values(int[] array)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 114 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: areturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #130	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedLongIntMap
/*     */     //   0	25	1	array	int[]
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public TLongIntIterator iterator()
/*     */   {
/* 134 */     return this.m.iterator();
/*     */   }
/*     */   
/*     */ 
/* 138 */   public long getNoEntryKey() { return this.m.getNoEntryKey(); }
/* 139 */   public int getNoEntryValue() { return this.m.getNoEntryValue(); }
/*     */   
/*     */   /* Error */
/*     */   public int putIfAbsent(long key, int value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore 4
/*     */     //   7: monitorenter
/*     */     //   8: aload_0
/*     */     //   9: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   12: lload_1
/*     */     //   13: iload_3
/*     */     //   14: invokeinterface 129 4 0
/*     */     //   19: aload 4
/*     */     //   21: monitorexit
/*     */     //   22: ireturn
/*     */     //   23: astore 5
/*     */     //   25: aload 4
/*     */     //   27: monitorexit
/*     */     //   28: aload 5
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #142	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	TSynchronizedLongIntMap
/*     */     //   0	31	1	key	long
/*     */     //   0	31	3	value	int
/*     */     //   5	21	4	Ljava/lang/Object;	Object
/*     */     //   23	6	5	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   8	22	23	finally
/*     */     //   23	28	23	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean forEachKey(gnu.trove.procedure.TLongProcedure procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 133 2 0
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
/*     */     //   0	25	0	this	TSynchronizedLongIntMap
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
/*     */   public boolean forEachValue(gnu.trove.procedure.TIntProcedure procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 139 2 0
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
/*     */     //   0	25	0	this	TSynchronizedLongIntMap
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TIntProcedure
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean forEachEntry(gnu.trove.procedure.TLongIntProcedure procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 144 2 0
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
/*     */     //   0	25	0	this	TSynchronizedLongIntMap
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TLongIntProcedure
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public void transformValues(TIntFunction function)
/*     */   {
/* 154 */     synchronized (this.mutex) { this.m.transformValues(function);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean retainEntries(gnu.trove.procedure.TLongIntProcedure procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 154 2 0
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
/*     */     //   0	25	0	this	TSynchronizedLongIntMap
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TLongIntProcedure
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean increment(long key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: lload_1
/*     */     //   12: invokeinterface 157 3 0
/*     */     //   17: aload_3
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: astore 4
/*     */     //   22: aload_3
/*     */     //   23: monitorexit
/*     */     //   24: aload 4
/*     */     //   26: athrow
/*     */     // Line number table:
/*     */     //   Java source line #160	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	27	0	this	TSynchronizedLongIntMap
/*     */     //   0	27	1	key	long
/*     */     //   5	18	3	Ljava/lang/Object;	Object
/*     */     //   20	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	24	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean adjustValue(long key, int amount)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore 4
/*     */     //   7: monitorenter
/*     */     //   8: aload_0
/*     */     //   9: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   12: lload_1
/*     */     //   13: iload_3
/*     */     //   14: invokeinterface 161 4 0
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
/*     */     //   0	31	0	this	TSynchronizedLongIntMap
/*     */     //   0	31	1	key	long
/*     */     //   0	31	3	amount	int
/*     */     //   5	21	4	Ljava/lang/Object;	Object
/*     */     //   23	6	5	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   8	22	23	finally
/*     */     //   23	28	23	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int adjustOrPutValue(long key, int adjust_amount, int put_amount)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore 5
/*     */     //   7: monitorenter
/*     */     //   8: aload_0
/*     */     //   9: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   12: lload_1
/*     */     //   13: iload_3
/*     */     //   14: iload 4
/*     */     //   16: invokeinterface 166 5 0
/*     */     //   21: aload 5
/*     */     //   23: monitorexit
/*     */     //   24: ireturn
/*     */     //   25: astore 6
/*     */     //   27: aload 5
/*     */     //   29: monitorexit
/*     */     //   30: aload 6
/*     */     //   32: athrow
/*     */     // Line number table:
/*     */     //   Java source line #166	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	33	0	this	TSynchronizedLongIntMap
/*     */     //   0	33	1	key	long
/*     */     //   0	33	3	adjust_amount	int
/*     */     //   0	33	4	put_amount	int
/*     */     //   5	23	5	Ljava/lang/Object;	Object
/*     */     //   25	6	6	localObject1	Object
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
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: aload_1
/*     */     //   12: invokevirtual 172	java/lang/Object:equals	(Ljava/lang/Object;)Z
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
/*     */     //   0	23	0	this	TSynchronizedLongIntMap
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
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: invokevirtual 176	java/lang/Object:hashCode	()I
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
/*     */     //   0	22	0	this	TSynchronizedLongIntMap
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
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedLongIntMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedLongIntMap:m	Lgnu/trove/map/TLongIntMap;
/*     */     //   11: invokevirtual 180	java/lang/Object:toString	()Ljava/lang/String;
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
/*     */     //   0	22	0	this	TSynchronizedLongIntMap
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


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\sync\TSynchronizedLongIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */