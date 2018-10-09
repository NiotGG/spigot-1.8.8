/*     */ package io.netty.handler.codec.socks;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.ReplayingDecoder;
/*     */ import io.netty.util.CharsetUtil;
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
/*     */ public class SocksCmdResponseDecoder
/*     */   extends ReplayingDecoder<State>
/*     */ {
/*     */   private static final String name = "SOCKS_CMD_RESPONSE_DECODER";
/*     */   private SocksProtocolVersion version;
/*     */   private int fieldLength;
/*     */   private SocksCmdStatus cmdStatus;
/*     */   private SocksAddressType addressType;
/*     */   private byte reserved;
/*     */   private String host;
/*     */   private int port;
/*     */   
/*     */   @Deprecated
/*     */   public static String getName()
/*     */   {
/*  38 */     return "SOCKS_CMD_RESPONSE_DECODER";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  48 */   private SocksResponse msg = SocksCommonUtils.UNKNOWN_SOCKS_RESPONSE;
/*     */   
/*     */   public SocksCmdResponseDecoder() {
/*  51 */     super(State.CHECK_PROTOCOL_VERSION);
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/*  56 */     switch ((State)state()) {
/*     */     case CHECK_PROTOCOL_VERSION: 
/*  58 */       this.version = SocksProtocolVersion.valueOf(paramByteBuf.readByte());
/*  59 */       if (this.version == SocksProtocolVersion.SOCKS5)
/*     */       {
/*     */ 
/*  62 */         checkpoint(State.READ_CMD_HEADER); }
/*     */       break;
/*     */     case READ_CMD_HEADER: 
/*  65 */       this.cmdStatus = SocksCmdStatus.valueOf(paramByteBuf.readByte());
/*  66 */       this.reserved = paramByteBuf.readByte();
/*  67 */       this.addressType = SocksAddressType.valueOf(paramByteBuf.readByte());
/*  68 */       checkpoint(State.READ_CMD_ADDRESS);
/*     */     
/*     */     case READ_CMD_ADDRESS: 
/*  71 */       switch (this.addressType) {
/*     */       case IPv4: 
/*  73 */         this.host = SocksCommonUtils.intToIp(paramByteBuf.readInt());
/*  74 */         this.port = paramByteBuf.readUnsignedShort();
/*  75 */         this.msg = new SocksCmdResponse(this.cmdStatus, this.addressType, this.host, this.port);
/*  76 */         break;
/*     */       
/*     */       case DOMAIN: 
/*  79 */         this.fieldLength = paramByteBuf.readByte();
/*  80 */         this.host = paramByteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/*  81 */         this.port = paramByteBuf.readUnsignedShort();
/*  82 */         this.msg = new SocksCmdResponse(this.cmdStatus, this.addressType, this.host, this.port);
/*  83 */         break;
/*     */       
/*     */       case IPv6: 
/*  86 */         this.host = SocksCommonUtils.ipv6toStr(paramByteBuf.readBytes(16).array());
/*  87 */         this.port = paramByteBuf.readUnsignedShort();
/*  88 */         this.msg = new SocksCmdResponse(this.cmdStatus, this.addressType, this.host, this.port); }
/*  89 */       break;
/*     */     }
/*     */     
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  96 */     paramChannelHandlerContext.pipeline().remove(this);
/*  97 */     paramList.add(this.msg);
/*     */   }
/*     */   
/*     */   static enum State {
/* 101 */     CHECK_PROTOCOL_VERSION, 
/* 102 */     READ_CMD_HEADER, 
/* 103 */     READ_CMD_ADDRESS;
/*     */     
/*     */     private State() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksCmdResponseDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */