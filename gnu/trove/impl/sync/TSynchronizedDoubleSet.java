/*    */ package gnu.trove.impl.sync;
/*    */ 
/*    */ import gnu.trove.set.TDoubleSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TSynchronizedDoubleSet
/*    */   extends TSynchronizedDoubleCollection
/*    */   implements TDoubleSet
/*    */ {
/*    */   private static final long serialVersionUID = 487447009682186044L;
/*    */   
/*    */   public TSynchronizedDoubleSet(TDoubleSet s)
/*    */   {
/* 58 */     super(s);
/*    */   }
/*    */   
/* 61 */   public TSynchronizedDoubleSet(TDoubleSet s, Object mutex) { super(s, mutex); }
/*    */   
/*    */   /* Error */
/*    */   public boolean equals(Object o)
/*    */   {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: getfield 30	gnu/trove/impl/sync/TSynchronizedDoubleSet:mutex	Ljava/lang/Object;
/*    */     //   4: dup
/*    */     //   5: astore_2
/*    */     //   6: monitorenter
/*    */     //   7: aload_0
/*    */     //   8: getfield 34	gnu/trove/impl/sync/TSynchronizedDoubleSet:c	Lgnu/trove/TDoubleCollection;
/*    */     //   11: aload_1
/*    */     //   12: invokevirtual 38	java/lang/Object:equals	(Ljava/lang/Object;)Z
/*    */     //   15: aload_2
/*    */     //   16: monitorexit
/*    */     //   17: ireturn
/*    */     //   18: astore_3
/*    */     //   19: aload_2
/*    */     //   20: monitorexit
/*    */     //   21: aload_3
/*    */     //   22: athrow
/*    */     // Line number table:
/*    */     //   Java source line #65	-> byte code offset #0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	23	0	this	TSynchronizedDoubleSet
/*    */     //   0	23	1	o	Object
/*    */     //   5	15	2	Ljava/lang/Object;	Object
/*    */     //   18	4	3	localObject1	Object
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   7	17	18	finally
/*    */     //   18	21	18	finally
/*    */   }
/*    */   
/*    */   /* Error */
/*    */   public int hashCode()
/*    */   {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: getfield 30	gnu/trove/impl/sync/TSynchronizedDoubleSet:mutex	Ljava/lang/Object;
/*    */     //   4: dup
/*    */     //   5: astore_1
/*    */     //   6: monitorenter
/*    */     //   7: aload_0
/*    */     //   8: getfield 34	gnu/trove/impl/sync/TSynchronizedDoubleSet:c	Lgnu/trove/TDoubleCollection;
/*    */     //   11: invokevirtual 43	java/lang/Object:hashCode	()I
/*    */     //   14: aload_1
/*    */     //   15: monitorexit
/*    */     //   16: ireturn
/*    */     //   17: astore_2
/*    */     //   18: aload_1
/*    */     //   19: monitorexit
/*    */     //   20: aload_2
/*    */     //   21: athrow
/*    */     // Line number table:
/*    */     //   Java source line #68	-> byte code offset #0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	22	0	this	TSynchronizedDoubleSet
/*    */     //   5	14	1	Ljava/lang/Object;	Object
/*    */     //   17	4	2	localObject1	Object
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   7	16	17	finally
/*    */     //   17	20	17	finally
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\impl\sync\TSynchronizedDoubleSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */