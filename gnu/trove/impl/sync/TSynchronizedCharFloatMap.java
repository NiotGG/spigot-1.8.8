/*     */ package gnu.trove.impl.sync;
/*     */ 
/*     */ import gnu.trove.TFloatCollection;
/*     */ import gnu.trove.function.TFloatFunction;
/*     */ import gnu.trove.iterator.TCharFloatIterator;
/*     */ import gnu.trove.map.TCharFloatMap;
/*     */ import gnu.trove.set.TCharSet;
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
/*     */ public class TSynchronizedCharFloatMap
/*     */   implements TCharFloatMap, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1978198479659022715L;
/*     */   private final TCharFloatMap m;
/*     */   final Object mutex;
/*     */   
/*     */   public TSynchronizedCharFloatMap(TCharFloatMap m)
/*     */   {
/*  59 */     if (m == null)
/*  60 */       throw new NullPointerException();
/*  61 */     this.m = m;
/*  62 */     this.mutex = this;
/*     */   }
/*     */   
/*     */   public TSynchronizedCharFloatMap(TCharFloatMap m, Object mutex) {
/*  66 */     this.m = m;
/*  67 */     this.mutex = mutex;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int size()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
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
/*     */     //   0	24	0	this	TSynchronizedCharFloatMap
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
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
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
/*     */     //   0	24	0	this	TSynchronizedCharFloatMap
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean containsKey(char key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: iload_1
/*     */     //   12: invokeinterface 52 2 0
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
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	key	char
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean containsValue(float value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: fload_1
/*     */     //   12: invokeinterface 58 2 0
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
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	value	float
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public float get(char key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: iload_1
/*     */     //   12: invokeinterface 64 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: freturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #83	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	key	char
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public float put(char key, float value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: iload_1
/*     */     //   12: fload_2
/*     */     //   13: invokeinterface 68 3 0
/*     */     //   18: aload_3
/*     */     //   19: monitorexit
/*     */     //   20: freturn
/*     */     //   21: astore 4
/*     */     //   23: aload_3
/*     */     //   24: monitorexit
/*     */     //   25: aload 4
/*     */     //   27: athrow
/*     */     // Line number table:
/*     */     //   Java source line #87	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	28	0	this	TSynchronizedCharFloatMap
/*     */     //   0	28	1	key	char
/*     */     //   0	28	2	value	float
/*     */     //   5	19	3	Ljava/lang/Object;	Object
/*     */     //   21	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	20	21	finally
/*     */     //   21	25	21	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public float remove(char key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: iload_1
/*     */     //   12: invokeinterface 71 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: freturn
/*     */     //   20: astore_3
/*     */     //   21: aload_2
/*     */     //   22: monitorexit
/*     */     //   23: aload_3
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #90	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	key	char
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends Character, ? extends Float> map)
/*     */   {
/*  93 */     synchronized (this.mutex) { this.m.putAll(map);
/*     */     } }
/*     */   
/*  96 */   public void putAll(TCharFloatMap map) { synchronized (this.mutex) { this.m.putAll(map);
/*     */     } }
/*     */   
/*  99 */   public void clear() { synchronized (this.mutex) { this.m.clear();
/*     */     } }
/*     */   
/* 102 */   private transient TCharSet keySet = null;
/* 103 */   private transient TFloatCollection values = null;
/*     */   
/*     */   public TCharSet keySet() {
/* 106 */     synchronized (this.mutex) {
/* 107 */       if (this.keySet == null)
/* 108 */         this.keySet = new TSynchronizedCharSet(this.m.keySet(), this.mutex);
/* 109 */       return this.keySet;
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public char[] keys()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: invokeinterface 95 1 0
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
/*     */     //   0	24	0	this	TSynchronizedCharFloatMap
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public char[] keys(char[] array)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 98 2 0
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
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	array	char[]
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public TFloatCollection valueCollection()
/*     */   {
/* 120 */     synchronized (this.mutex) {
/* 121 */       if (this.values == null)
/* 122 */         this.values = new TSynchronizedFloatCollection(this.m.valueCollection(), this.mutex);
/* 123 */       return this.values;
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public float[] values()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: invokeinterface 112 1 0
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
/*     */     //   0	24	0	this	TSynchronizedCharFloatMap
/*     */     //   5	16	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public float[] values(float[] array)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 115 2 0
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
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	array	float[]
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public TCharFloatIterator iterator()
/*     */   {
/* 134 */     return this.m.iterator();
/*     */   }
/*     */   
/*     */ 
/* 138 */   public char getNoEntryKey() { return this.m.getNoEntryKey(); }
/* 139 */   public float getNoEntryValue() { return this.m.getNoEntryValue(); }
/*     */   
/*     */   /* Error */
/*     */   public float putIfAbsent(char key, float value)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: iload_1
/*     */     //   12: fload_2
/*     */     //   13: invokeinterface 131 3 0
/*     */     //   18: aload_3
/*     */     //   19: monitorexit
/*     */     //   20: freturn
/*     */     //   21: astore 4
/*     */     //   23: aload_3
/*     */     //   24: monitorexit
/*     */     //   25: aload 4
/*     */     //   27: athrow
/*     */     // Line number table:
/*     */     //   Java source line #142	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	28	0	this	TSynchronizedCharFloatMap
/*     */     //   0	28	1	key	char
/*     */     //   0	28	2	value	float
/*     */     //   5	19	3	Ljava/lang/Object;	Object
/*     */     //   21	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	20	21	finally
/*     */     //   21	25	21	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean forEachKey(gnu.trove.procedure.TCharProcedure procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 135 2 0
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
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TCharProcedure
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean forEachValue(gnu.trove.procedure.TFloatProcedure procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 141 2 0
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
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TFloatProcedure
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean forEachEntry(gnu.trove.procedure.TCharFloatProcedure procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 146 2 0
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
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TCharFloatProcedure
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   public void transformValues(TFloatFunction function)
/*     */   {
/* 154 */     synchronized (this.mutex) { this.m.transformValues(function);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean retainEntries(gnu.trove.procedure.TCharFloatProcedure procedure)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 156 2 0
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
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	procedure	gnu.trove.procedure.TCharFloatProcedure
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean increment(char key)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: iload_1
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
/*     */     //   Java source line #160	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	TSynchronizedCharFloatMap
/*     */     //   0	25	1	key	char
/*     */     //   5	17	2	Ljava/lang/Object;	Object
/*     */     //   20	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	23	20	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean adjustValue(char key, float amount)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_3
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: iload_1
/*     */     //   12: fload_2
/*     */     //   13: invokeinterface 163 3 0
/*     */     //   18: aload_3
/*     */     //   19: monitorexit
/*     */     //   20: ireturn
/*     */     //   21: astore 4
/*     */     //   23: aload_3
/*     */     //   24: monitorexit
/*     */     //   25: aload 4
/*     */     //   27: athrow
/*     */     // Line number table:
/*     */     //   Java source line #163	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	28	0	this	TSynchronizedCharFloatMap
/*     */     //   0	28	1	key	char
/*     */     //   0	28	2	amount	float
/*     */     //   5	19	3	Ljava/lang/Object;	Object
/*     */     //   21	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	20	21	finally
/*     */     //   21	25	21	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public float adjustOrPutValue(char key, float adjust_amount, float put_amount)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore 4
/*     */     //   7: monitorenter
/*     */     //   8: aload_0
/*     */     //   9: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   12: iload_1
/*     */     //   13: fload_2
/*     */     //   14: fload_3
/*     */     //   15: invokeinterface 168 4 0
/*     */     //   20: aload 4
/*     */     //   22: monitorexit
/*     */     //   23: freturn
/*     */     //   24: astore 5
/*     */     //   26: aload 4
/*     */     //   28: monitorexit
/*     */     //   29: aload 5
/*     */     //   31: athrow
/*     */     // Line number table:
/*     */     //   Java source line #166	-> byte code offset #0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	32	0	this	TSynchronizedCharFloatMap
/*     */     //   0	32	1	key	char
/*     */     //   0	32	2	adjust_amount	float
/*     */     //   0	32	3	put_amount	float
/*     */     //   5	22	4	Ljava/lang/Object;	Object
/*     */     //   24	6	5	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   8	23	24	finally
/*     */     //   24	29	24	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean equals(Object o)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: aload_1
/*     */     //   12: invokevirtual 174	java/lang/Object:equals	(Ljava/lang/Object;)Z
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
/*     */     //   0	23	0	this	TSynchronizedCharFloatMap
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
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: invokevirtual 178	java/lang/Object:hashCode	()I
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
/*     */     //   0	22	0	this	TSynchronizedCharFloatMap
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
/*     */     //   1: getfield 37	gnu/trove/impl/sync/TSynchronizedCharFloatMap:mutex	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 35	gnu/trove/impl/sync/TSynchronizedCharFloatMap:m	Lgnu/trove/map/TCharFloatMap;
/*     */     //   11: invokevirtual 182	java/lang/Object:toString	()Ljava/lang/String;
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
/*     */     //   0	22	0	this	TSynchronizedCharFloatMap
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


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\sync\TSynchronizedCharFloatMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */