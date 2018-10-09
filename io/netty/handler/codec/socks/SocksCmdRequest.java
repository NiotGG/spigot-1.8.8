/*     */ package io.netty.handler.codec.socks;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.NetUtil;
/*     */ import java.net.IDN;
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
/*     */ public final class SocksCmdRequest
/*     */   extends SocksRequest
/*     */ {
/*     */   private final SocksCmdType cmdType;
/*     */   private final SocksAddressType addressType;
/*     */   private final String host;
/*     */   private final int port;
/*     */   
/*     */   public SocksCmdRequest(SocksCmdType paramSocksCmdType, SocksAddressType paramSocksAddressType, String paramString, int paramInt)
/*     */   {
/*  37 */     super(SocksRequestType.CMD);
/*  38 */     if (paramSocksCmdType == null) {
/*  39 */       throw new NullPointerException("cmdType");
/*     */     }
/*  41 */     if (paramSocksAddressType == null) {
/*  42 */       throw new NullPointerException("addressType");
/*     */     }
/*  44 */     if (paramString == null) {
/*  45 */       throw new NullPointerException("host");
/*     */     }
/*  47 */     switch (paramSocksAddressType) {
/*     */     case IPv4: 
/*  49 */       if (!NetUtil.isValidIpV4Address(paramString)) {
/*  50 */         throw new IllegalArgumentException(paramString + " is not a valid IPv4 address");
/*     */       }
/*     */       break;
/*     */     case DOMAIN: 
/*  54 */       if (IDN.toASCII(paramString).length() > 255) {
/*  55 */         throw new IllegalArgumentException(paramString + " IDN: " + IDN.toASCII(paramString) + " exceeds 255 char limit");
/*     */       }
/*     */       break;
/*     */     case IPv6: 
/*  59 */       if (!NetUtil.isValidIpV6Address(paramString)) {
/*  60 */         throw new IllegalArgumentException(paramString + " is not a valid IPv6 address");
/*     */       }
/*     */       
/*     */       break;
/*     */     }
/*     */     
/*  66 */     if ((paramInt <= 0) || (paramInt >= 65536)) {
/*  67 */       throw new IllegalArgumentException(paramInt + " is not in bounds 0 < x < 65536");
/*     */     }
/*  69 */     this.cmdType = paramSocksCmdType;
/*  70 */     this.addressType = paramSocksAddressType;
/*  71 */     this.host = IDN.toASCII(paramString);
/*  72 */     this.port = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SocksCmdType cmdType()
/*     */   {
/*  81 */     return this.cmdType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SocksAddressType addressType()
/*     */   {
/*  90 */     return this.addressType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String host()
/*     */   {
/*  99 */     return IDN.toUnicode(this.host);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int port()
/*     */   {
/* 108 */     return this.port;
/*     */   }
/*     */   
/*     */   public void encodeAsByteBuf(ByteBuf paramByteBuf)
/*     */   {
/* 113 */     paramByteBuf.writeByte(protocolVersion().byteValue());
/* 114 */     paramByteBuf.writeByte(this.cmdType.byteValue());
/* 115 */     paramByteBuf.writeByte(0);
/* 116 */     paramByteBuf.writeByte(this.addressType.byteValue());
/* 117 */     switch (this.addressType) {
/*     */     case IPv4: 
/* 119 */       paramByteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
/* 120 */       paramByteBuf.writeShort(this.port);
/* 121 */       break;
/*     */     
/*     */ 
/*     */     case DOMAIN: 
/* 125 */       paramByteBuf.writeByte(this.host.length());
/* 126 */       paramByteBuf.writeBytes(this.host.getBytes(CharsetUtil.US_ASCII));
/* 127 */       paramByteBuf.writeShort(this.port);
/* 128 */       break;
/*     */     
/*     */ 
/*     */     case IPv6: 
/* 132 */       paramByteBuf.writeBytes(NetUtil.createByteArrayFromIpAddressString(this.host));
/* 133 */       paramByteBuf.writeShort(this.port);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksCmdRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */