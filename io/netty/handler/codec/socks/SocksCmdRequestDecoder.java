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
/*     */ public class SocksCmdRequestDecoder
/*     */   extends ReplayingDecoder<State>
/*     */ {
/*     */   private static final String name = "SOCKS_CMD_REQUEST_DECODER";
/*     */   private SocksProtocolVersion version;
/*     */   private int fieldLength;
/*     */   private SocksCmdType cmdType;
/*     */   private SocksAddressType addressType;
/*     */   private byte reserved;
/*     */   private String host;
/*     */   private int port;
/*     */   
/*     */   @Deprecated
/*     */   public static String getName()
/*     */   {
/*  38 */     return "SOCKS_CMD_REQUEST_DECODER";
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
/*  49 */   private SocksRequest msg = SocksCommonUtils.UNKNOWN_SOCKS_REQUEST;
/*     */   
/*     */   public SocksCmdRequestDecoder() {
/*  52 */     super(State.CHECK_PROTOCOL_VERSION);
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/*  57 */     switch ((State)state()) {
/*     */     case CHECK_PROTOCOL_VERSION: 
/*  59 */       this.version = SocksProtocolVersion.valueOf(paramByteBuf.readByte());
/*  60 */       if (this.version == SocksProtocolVersion.SOCKS5)
/*     */       {
/*     */ 
/*  63 */         checkpoint(State.READ_CMD_HEADER); }
/*     */       break;
/*     */     case READ_CMD_HEADER: 
/*  66 */       this.cmdType = SocksCmdType.valueOf(paramByteBuf.readByte());
/*  67 */       this.reserved = paramByteBuf.readByte();
/*  68 */       this.addressType = SocksAddressType.valueOf(paramByteBuf.readByte());
/*  69 */       checkpoint(State.READ_CMD_ADDRESS);
/*     */     
/*     */     case READ_CMD_ADDRESS: 
/*  72 */       switch (this.addressType) {
/*     */       case IPv4: 
/*  74 */         this.host = SocksCommonUtils.intToIp(paramByteBuf.readInt());
/*  75 */         this.port = paramByteBuf.readUnsignedShort();
/*  76 */         this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
/*  77 */         break;
/*     */       
/*     */       case DOMAIN: 
/*  80 */         this.fieldLength = paramByteBuf.readByte();
/*  81 */         this.host = paramByteBuf.readBytes(this.fieldLength).toString(CharsetUtil.US_ASCII);
/*  82 */         this.port = paramByteBuf.readUnsignedShort();
/*  83 */         this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port);
/*  84 */         break;
/*     */       
/*     */       case IPv6: 
/*  87 */         this.host = SocksCommonUtils.ipv6toStr(paramByteBuf.readBytes(16).array());
/*  88 */         this.port = paramByteBuf.readUnsignedShort();
/*  89 */         this.msg = new SocksCmdRequest(this.cmdType, this.addressType, this.host, this.port); }
/*  90 */       break;
/*     */     }
/*     */     
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  97 */     paramChannelHandlerContext.pipeline().remove(this);
/*  98 */     paramList.add(this.msg);
/*     */   }
/*     */   
/*     */   static enum State {
/* 102 */     CHECK_PROTOCOL_VERSION, 
/* 103 */     READ_CMD_HEADER, 
/* 104 */     READ_CMD_ADDRESS;
/*     */     
/*     */     private State() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksCmdRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */