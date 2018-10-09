/*     */ package com.avaje.ebeaninternal.server.cluster;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.cluster.mcast.Message;
/*     */ import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class PacketWriter
/*     */ {
/*     */   private final PacketIdGenerator idGenerator;
/*     */   private final PacketBuilder messagesPacketBuilder;
/*     */   private final PacketBuilder transEventPacketBuilder;
/*     */   
/*     */   public PacketWriter(int maxPacketSize)
/*     */   {
/*  50 */     this.idGenerator = new PacketIdGenerator(null);
/*  51 */     this.messagesPacketBuilder = new PacketBuilder(maxPacketSize, this.idGenerator, new MessagesPacketFactory(null), null);
/*  52 */     this.transEventPacketBuilder = new PacketBuilder(maxPacketSize, this.idGenerator, new TransPacketFactory(null), null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public long currentPacketId()
/*     */   {
/*  59 */     return this.idGenerator.currentPacketId();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Packet> write(boolean requiresAck, List<? extends Message> messages)
/*     */     throws IOException
/*     */   {
/*  71 */     BinaryMessageList binaryMsgList = new BinaryMessageList();
/*  72 */     for (int i = 0; i < messages.size(); i++) {
/*  73 */       Message message = (Message)messages.get(i);
/*  74 */       message.writeBinaryMessage(binaryMsgList);
/*     */     }
/*  76 */     return this.messagesPacketBuilder.write(requiresAck, binaryMsgList, "");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<Packet> write(RemoteTransactionEvent transEvent)
/*     */     throws IOException
/*     */   {
/*  88 */     BinaryMessageList messageList = new BinaryMessageList();
/*     */     
/*     */ 
/*  91 */     transEvent.writeBinaryMessage(messageList);
/*     */     
/*  93 */     return this.transEventPacketBuilder.write(true, messageList, transEvent.getServerName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class PacketIdGenerator
/*     */   {
/*     */     long packetIdCounter;
/*     */     
/*     */ 
/*     */     public long nextPacketId()
/*     */     {
/* 105 */       return ++this.packetIdCounter;
/*     */     }
/*     */     
/*     */     public long currentPacketId() {
/* 109 */       return this.packetIdCounter;
/*     */     }
/*     */   }
/*     */   
/*     */   static abstract interface PacketFactory
/*     */   {
/*     */     public abstract Packet createPacket(long paramLong1, long paramLong2, String paramString) throws IOException;
/*     */   }
/*     */   
/*     */   private static class TransPacketFactory implements PacketWriter.PacketFactory
/*     */   {
/*     */     public Packet createPacket(long packetId, long timestamp, String serverName) throws IOException
/*     */     {
/* 122 */       return PacketTransactionEvent.forWrite(packetId, timestamp, serverName);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class MessagesPacketFactory implements PacketWriter.PacketFactory
/*     */   {
/*     */     public Packet createPacket(long packetId, long timestamp, String serverName) throws IOException {
/* 129 */       return PacketMessages.forWrite(packetId, timestamp, serverName);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class PacketBuilder
/*     */   {
/*     */     private final PacketWriter.PacketIdGenerator idGenerator;
/*     */     
/*     */     private final PacketWriter.PacketFactory packetFactory;
/*     */     
/*     */     private final int maxPacketSize;
/*     */     
/*     */     private PacketBuilder(int maxPacketSize, PacketWriter.PacketIdGenerator idGenerator, PacketWriter.PacketFactory packetFactory)
/*     */     {
/* 144 */       this.maxPacketSize = maxPacketSize;
/* 145 */       this.idGenerator = idGenerator;
/* 146 */       this.packetFactory = packetFactory;
/*     */     }
/*     */     
/*     */     private List<Packet> write(boolean requiresAck, BinaryMessageList messageList, String serverName)
/*     */       throws IOException
/*     */     {
/* 152 */       List<BinaryMessage> list = messageList.getList();
/*     */       
/* 154 */       ArrayList<Packet> packets = new ArrayList(1);
/*     */       
/* 156 */       long timestamp = System.currentTimeMillis();
/*     */       
/* 158 */       long packetId = requiresAck ? this.idGenerator.nextPacketId() : 0L;
/* 159 */       Packet p = this.packetFactory.createPacket(packetId, timestamp, serverName);
/*     */       
/* 161 */       packets.add(p);
/*     */       
/* 163 */       for (int i = 0; i < list.size(); i++) {
/* 164 */         BinaryMessage binMsg = (BinaryMessage)list.get(i);
/* 165 */         if (!p.writeBinaryMessage(binMsg, this.maxPacketSize))
/*     */         {
/* 167 */           packetId = requiresAck ? this.idGenerator.nextPacketId() : 0L;
/* 168 */           p = this.packetFactory.createPacket(packetId, timestamp, serverName);
/* 169 */           packets.add(p);
/* 170 */           p.writeBinaryMessage(binMsg, this.maxPacketSize);
/*     */         }
/*     */       }
/* 173 */       p.writeEof();
/*     */       
/* 175 */       return packets;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\cluster\PacketWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */