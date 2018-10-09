/*    */ package com.avaje.ebeaninternal.server.cluster;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*    */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
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
/*    */ public abstract class SerialiseTransactionHelper
/*    */ {
/*    */   private final PacketWriter packetWriter;
/*    */   
/*    */   public SerialiseTransactionHelper()
/*    */   {
/* 38 */     this.packetWriter = new PacketWriter(Integer.MAX_VALUE);
/*    */   }
/*    */   
/*    */ 
/*    */   public abstract SpiEbeanServer getEbeanServer(String paramString);
/*    */   
/*    */ 
/*    */   public DataHolder createDataHolder(RemoteTransactionEvent transEvent)
/*    */     throws IOException
/*    */   {
/* 48 */     List<Packet> packetList = this.packetWriter.write(transEvent);
/* 49 */     if (packetList.size() != 1) {
/* 50 */       throw new RuntimeException("Always expecting 1 Packet but got " + packetList.size());
/*    */     }
/* 52 */     byte[] data = ((Packet)packetList.get(0)).getBytes();
/* 53 */     return new DataHolder(data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public RemoteTransactionEvent read(DataHolder dataHolder)
/*    */     throws IOException
/*    */   {
/* 61 */     ByteArrayInputStream bi = new ByteArrayInputStream(dataHolder.getData());
/* 62 */     DataInputStream dataInput = new DataInputStream(bi);
/*    */     
/* 64 */     Packet header = Packet.readHeader(dataInput);
/*    */     
/* 66 */     SpiEbeanServer server = getEbeanServer(header.getServerName());
/*    */     
/* 68 */     PacketTransactionEvent tranEventPacket = PacketTransactionEvent.forRead(header, server);
/* 69 */     tranEventPacket.read(dataInput);
/*    */     
/* 71 */     return tranEventPacket.getEvent();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\SerialiseTransactionHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */