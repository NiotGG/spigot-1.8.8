/*     */ package com.avaje.ebeaninternal.server.cluster.mcast;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Logger;
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
/*     */ public class IncomingPacketsProcessed
/*     */ {
/*  46 */   private final ConcurrentHashMap<String, GotAllPoint> mapByMember = new ConcurrentHashMap();
/*     */   private final int maxResendIncoming;
/*     */   
/*     */   public IncomingPacketsProcessed(int maxResendIncoming)
/*     */   {
/*  51 */     this.maxResendIncoming = maxResendIncoming;
/*     */   }
/*     */   
/*     */   public void removeMember(String memberKey) {
/*  55 */     this.mapByMember.remove(memberKey);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isProcessPacket(String memberKey, long packetId)
/*     */   {
/*  64 */     GotAllPoint memberPackets = getMemberPackets(memberKey);
/*  65 */     return memberPackets.processPacket(packetId);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AckResendMessages getAckResendMessages(IncomingPacketsLastAck lastAck)
/*     */   {
/*  76 */     AckResendMessages response = new AckResendMessages();
/*     */     
/*  78 */     for (GotAllPoint member : this.mapByMember.values())
/*     */     {
/*  80 */       MessageAck lastAckMessage = lastAck.getLastAck(member.getMemberKey());
/*     */       
/*  82 */       member.addAckResendMessages(response, lastAckMessage);
/*     */     }
/*     */     
/*  85 */     return response;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private GotAllPoint getMemberPackets(String memberKey)
/*     */   {
/*  93 */     GotAllPoint memberGotAllPoint = (GotAllPoint)this.mapByMember.get(memberKey);
/*  94 */     if (memberGotAllPoint == null) {
/*  95 */       memberGotAllPoint = new GotAllPoint(memberKey, this.maxResendIncoming);
/*  96 */       this.mapByMember.put(memberKey, memberGotAllPoint);
/*     */     }
/*  98 */     return memberGotAllPoint;
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
/*     */   public static class GotAllPoint
/*     */   {
/* 111 */     private static final Logger logger = Logger.getLogger(GotAllPoint.class.getName());
/*     */     
/*     */ 
/*     */     private final String memberKey;
/*     */     
/*     */ 
/*     */     private final int maxResendIncoming;
/*     */     
/*     */     private long gotAllPoint;
/*     */     
/*     */     private long gotMaxPoint;
/*     */     
/* 123 */     private ArrayList<Long> outOfOrderList = new ArrayList();
/*     */     
/* 125 */     private HashMap<Long, Integer> resendCountMap = new HashMap();
/*     */     
/*     */     public GotAllPoint(String memberKey, int maxResendIncoming) {
/* 128 */       this.memberKey = memberKey;
/* 129 */       this.maxResendIncoming = maxResendIncoming;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void addAckResendMessages(AckResendMessages response, MessageAck lastAckMessage)
/*     */     {
/* 137 */       synchronized (this) {
/* 138 */         if ((lastAckMessage == null) || (lastAckMessage.getGotAllPacketId() < this.gotAllPoint))
/*     */         {
/*     */ 
/*     */ 
/* 142 */           response.add(new MessageAck(this.memberKey, this.gotAllPoint));
/*     */         }
/*     */         
/* 145 */         if (getMissingPacketCount() > 0)
/*     */         {
/* 147 */           List<Long> missingPackets = getMissingPackets();
/* 148 */           response.add(new MessageResend(this.memberKey, missingPackets));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     public String getMemberKey() {
/* 154 */       return this.memberKey;
/*     */     }
/*     */     
/*     */     /* Error */
/*     */     public long getGotAllPoint()
/*     */     {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: dup
/*     */       //   2: astore_1
/*     */       //   3: monitorenter
/*     */       //   4: aload_0
/*     */       //   5: getfield 56	com/avaje/ebeaninternal/server/cluster/mcast/IncomingPacketsProcessed$GotAllPoint:gotAllPoint	J
/*     */       //   8: aload_1
/*     */       //   9: monitorexit
/*     */       //   10: lreturn
/*     */       //   11: astore_2
/*     */       //   12: aload_1
/*     */       //   13: monitorexit
/*     */       //   14: aload_2
/*     */       //   15: athrow
/*     */       // Line number table:
/*     */       //   Java source line #158	-> byte code offset #0
/*     */       //   Java source line #159	-> byte code offset #4
/*     */       //   Java source line #160	-> byte code offset #11
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	16	0	this	GotAllPoint
/*     */       //   2	11	1	Ljava/lang/Object;	Object
/*     */       //   11	4	2	localObject1	Object
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   4	10	11	finally
/*     */       //   11	14	11	finally
/*     */     }
/*     */     
/*     */     /* Error */
/*     */     public long getGotMaxPoint()
/*     */     {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: dup
/*     */       //   2: astore_1
/*     */       //   3: monitorenter
/*     */       //   4: aload_0
/*     */       //   5: getfield 94	com/avaje/ebeaninternal/server/cluster/mcast/IncomingPacketsProcessed$GotAllPoint:gotMaxPoint	J
/*     */       //   8: aload_1
/*     */       //   9: monitorexit
/*     */       //   10: lreturn
/*     */       //   11: astore_2
/*     */       //   12: aload_1
/*     */       //   13: monitorexit
/*     */       //   14: aload_2
/*     */       //   15: athrow
/*     */       // Line number table:
/*     */       //   Java source line #164	-> byte code offset #0
/*     */       //   Java source line #165	-> byte code offset #4
/*     */       //   Java source line #166	-> byte code offset #11
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	16	0	this	GotAllPoint
/*     */       //   2	11	1	Ljava/lang/Object;	Object
/*     */       //   11	4	2	localObject1	Object
/*     */       // Exception table:
/*     */       //   from	to	target	type
/*     */       //   4	10	11	finally
/*     */       //   11	14	11	finally
/*     */     }
/*     */     
/*     */     private int getMissingPacketCount()
/*     */     {
/* 170 */       if (this.gotMaxPoint <= this.gotAllPoint) {
/* 171 */         if (!this.resendCountMap.isEmpty()) {
/* 172 */           this.resendCountMap.clear();
/*     */         }
/* 174 */         return 0;
/*     */       }
/* 176 */       return (int)(this.gotMaxPoint - this.gotAllPoint) - this.outOfOrderList.size();
/*     */     }
/*     */     
/*     */     public List<Long> getMissingPackets()
/*     */     {
/* 181 */       synchronized (this) {
/* 182 */         ArrayList<Long> missingList = new ArrayList();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 187 */         boolean lostPacket = false;
/*     */         
/* 189 */         for (long i = this.gotAllPoint + 1L; i < this.gotMaxPoint; i += 1L) {
/* 190 */           Long packetId = Long.valueOf(i);
/* 191 */           if (!this.outOfOrderList.contains(packetId)) {
/* 192 */             if (incrementResendCount(packetId))
/*     */             {
/* 194 */               missingList.add(packetId);
/*     */             } else {
/* 196 */               lostPacket = true;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 201 */         if (lostPacket) {
/* 202 */           checkOutOfOrderList();
/*     */         }
/*     */         
/* 205 */         return missingList;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private boolean incrementResendCount(Long packetId)
/*     */     {
/* 213 */       Integer resendCount = (Integer)this.resendCountMap.get(packetId);
/* 214 */       if (resendCount != null) {
/* 215 */         int i = resendCount.intValue() + 1;
/* 216 */         if (i > this.maxResendIncoming)
/*     */         {
/* 218 */           logger.warning("Exceeded maxResendIncoming[" + this.maxResendIncoming + "] for packet[" + packetId + "]. Giving up on requesting it.");
/* 219 */           this.resendCountMap.remove(packetId);
/* 220 */           this.outOfOrderList.add(packetId);
/* 221 */           return false;
/*     */         }
/* 223 */         resendCount = Integer.valueOf(i);
/* 224 */         this.resendCountMap.put(packetId, resendCount);
/*     */       } else {
/* 226 */         this.resendCountMap.put(packetId, ONE);
/*     */       }
/* 228 */       return true;
/*     */     }
/*     */     
/* 231 */     private static final Integer ONE = Integer.valueOf(1);
/*     */     
/*     */     public boolean processPacket(long packetId) {
/* 234 */       synchronized (this)
/*     */       {
/* 236 */         if (this.gotAllPoint == 0L) {
/* 237 */           this.gotAllPoint = packetId;
/* 238 */           return true;
/*     */         }
/* 240 */         if (packetId <= this.gotAllPoint)
/*     */         {
/* 242 */           return false;
/*     */         }
/*     */         
/* 245 */         if (!this.resendCountMap.isEmpty()) {
/* 246 */           this.resendCountMap.remove(Long.valueOf(packetId));
/*     */         }
/*     */         
/* 249 */         if (packetId == this.gotAllPoint + 1L) {
/* 250 */           this.gotAllPoint = packetId;
/*     */         } else {
/* 252 */           if (packetId > this.gotMaxPoint) {
/* 253 */             this.gotMaxPoint = packetId;
/*     */           }
/* 255 */           this.outOfOrderList.add(Long.valueOf(packetId));
/*     */         }
/* 257 */         checkOutOfOrderList();
/* 258 */         return true;
/*     */       }
/*     */     }
/*     */     
/*     */     private void checkOutOfOrderList()
/*     */     {
/* 264 */       if (this.outOfOrderList.size() == 0) {
/*     */         return;
/*     */       }
/*     */       boolean continueCheck;
/*     */       do
/*     */       {
/* 270 */         continueCheck = false;
/* 271 */         long nextPoint = this.gotAllPoint + 1L;
/*     */         
/* 273 */         Iterator<Long> it = this.outOfOrderList.iterator();
/* 274 */         while (it.hasNext()) {
/* 275 */           Long id = (Long)it.next();
/* 276 */           if (id.longValue() == nextPoint)
/*     */           {
/* 278 */             it.remove();
/* 279 */             this.gotAllPoint = nextPoint;
/* 280 */             continueCheck = true;
/* 281 */             break;
/*     */           }
/*     */         }
/* 284 */       } while (continueCheck);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\mcast\IncomingPacketsProcessed.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */