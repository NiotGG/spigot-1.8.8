/*     */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ public class OutgoingPacketsAcked
/*     */ {
/*     */   private long minimumGotAllPacketId;
/*     */   private Map<String, GroupMemberAck> recievedByMap;
/*     */   
/*     */   public OutgoingPacketsAcked()
/*     */   {
/*  29 */     this.recievedByMap = new HashMap();
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public int getGroupSize()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_1
/*     */     //   3: monitorenter
/*     */     //   4: aload_0
/*     */     //   5: getfield 24	com/avaje/ebeaninternal/server/cluster/mcast/OutgoingPacketsAcked:recievedByMap	Ljava/util/Map;
/*     */     //   8: invokeinterface 33 1 0
/*     */     //   13: aload_1
/*     */     //   14: monitorexit
/*     */     //   15: ireturn
/*     */     //   16: astore_2
/*     */     //   17: aload_1
/*     */     //   18: monitorexit
/*     */     //   19: aload_2
/*     */     //   20: athrow
/*     */     // Line number table:
/*     */     //   Java source line #32	-> byte code offset #0
/*     */     //   Java source line #33	-> byte code offset #4
/*     */     //   Java source line #34	-> byte code offset #16
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	21	0	this	OutgoingPacketsAcked
/*     */     //   2	16	1	Ljava/lang/Object;	Object
/*     */     //   16	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	15	16	finally
/*     */     //   16	19	16	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public long getMinimumGotAllPacketId()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_1
/*     */     //   3: monitorenter
/*     */     //   4: aload_0
/*     */     //   5: getfield 37	com/avaje/ebeaninternal/server/cluster/mcast/OutgoingPacketsAcked:minimumGotAllPacketId	J
/*     */     //   8: aload_1
/*     */     //   9: monitorexit
/*     */     //   10: lreturn
/*     */     //   11: astore_2
/*     */     //   12: aload_1
/*     */     //   13: monitorexit
/*     */     //   14: aload_2
/*     */     //   15: athrow
/*     */     // Line number table:
/*     */     //   Java source line #38	-> byte code offset #0
/*     */     //   Java source line #39	-> byte code offset #4
/*     */     //   Java source line #40	-> byte code offset #11
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	16	0	this	OutgoingPacketsAcked
/*     */     //   2	11	1	Ljava/lang/Object;	Object
/*     */     //   11	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	10	11	finally
/*     */     //   11	14	11	finally
/*     */   }
/*     */   
/*     */   public void removeMember(String groupMember)
/*     */   {
/*  44 */     synchronized (this) {
/*  45 */       this.recievedByMap.remove(groupMember);
/*  46 */       resetGotAllMin();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean resetGotAllMin() {
/*     */     long tempMin;
/*     */     long tempMin;
/*  53 */     if (this.recievedByMap.isEmpty())
/*     */     {
/*  55 */       tempMin = Long.MAX_VALUE;
/*     */     } else {
/*  57 */       tempMin = Long.MAX_VALUE;
/*     */     }
/*     */     
/*  60 */     for (GroupMemberAck groupMemAck : this.recievedByMap.values()) {
/*  61 */       long memberMin = groupMemAck.getGotAllPacketId();
/*  62 */       if (memberMin < tempMin)
/*     */       {
/*  64 */         tempMin = memberMin;
/*     */       }
/*     */     }
/*     */     
/*  68 */     if (tempMin != this.minimumGotAllPacketId) {
/*  69 */       this.minimumGotAllPacketId = tempMin;
/*  70 */       return true;
/*     */     }
/*  72 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public long receivedAck(String groupMember, MessageAck ack)
/*     */   {
/*  78 */     synchronized (this)
/*     */     {
/*  80 */       boolean checkMin = false;
/*     */       
/*  82 */       GroupMemberAck groupMemberAck = (GroupMemberAck)this.recievedByMap.get(groupMember);
/*  83 */       if (groupMemberAck == null)
/*     */       {
/*  85 */         groupMemberAck = new GroupMemberAck(null);
/*  86 */         groupMemberAck.setIfBigger(ack.getGotAllPacketId());
/*  87 */         this.recievedByMap.put(groupMember, groupMemberAck);
/*  88 */         checkMin = true;
/*     */       } else {
/*  90 */         checkMin = groupMemberAck.getGotAllPacketId() == this.minimumGotAllPacketId;
/*     */         
/*  92 */         groupMemberAck.setIfBigger(ack.getGotAllPacketId());
/*     */       }
/*     */       
/*  95 */       boolean minChanged = false;
/*     */       
/*     */ 
/*  98 */       if ((checkMin) || (this.minimumGotAllPacketId == 0L))
/*     */       {
/* 100 */         minChanged = resetGotAllMin();
/*     */       }
/*     */       
/*     */ 
/* 104 */       return minChanged ? this.minimumGotAllPacketId : 0L;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class GroupMemberAck
/*     */   {
/*     */     private long gotAllPacketId;
/*     */     
/*     */ 
/*     */     private long getGotAllPacketId()
/*     */     {
/* 116 */       return this.gotAllPacketId;
/*     */     }
/*     */     
/*     */     private void setIfBigger(long newGotAll) {
/* 120 */       if (newGotAll > this.gotAllPacketId) {
/* 121 */         this.gotAllPacketId = newGotAll;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\mcast\OutgoingPacketsAcked.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */