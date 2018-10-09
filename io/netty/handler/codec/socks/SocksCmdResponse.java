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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SocksCmdResponse
/*     */   extends SocksResponse
/*     */ {
/*     */   private final SocksCmdStatus cmdStatus;
/*     */   private final SocksAddressType addressType;
/*     */   private final String host;
/*     */   private final int port;
/*  38 */   private static final byte[] DOMAIN_ZEROED = { 0 };
/*  39 */   private static final byte[] IPv4_HOSTNAME_ZEROED = { 0, 0, 0, 0 };
/*  40 */   private static final byte[] IPv6_HOSTNAME_ZEROED = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
/*     */   
/*     */ 
/*     */ 
/*     */   public SocksCmdResponse(SocksCmdStatus paramSocksCmdStatus, SocksAddressType paramSocksAddressType)
/*     */   {
/*  46 */     this(paramSocksCmdStatus, paramSocksAddressType, null, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SocksCmdResponse(SocksCmdStatus paramSocksCmdStatus, SocksAddressType paramSocksAddressType, String paramString, int paramInt)
/*     */   {
/*  63 */     super(SocksResponseType.CMD);
/*  64 */     if (paramSocksCmdStatus == null) {
/*  65 */       throw new NullPointerException("cmdStatus");
/*     */     }
/*  67 */     if (paramSocksAddressType == null) {
/*  68 */       throw new NullPointerException("addressType");
/*     */     }
/*  70 */     if (paramString != null) {
/*  71 */       switch (paramSocksAddressType) {
/*     */       case IPv4: 
/*  73 */         if (!NetUtil.isValidIpV4Address(paramString)) {
/*  74 */           throw new IllegalArgumentException(paramString + " is not a valid IPv4 address");
/*     */         }
/*     */         break;
/*     */       case DOMAIN: 
/*  78 */         if (IDN.toASCII(paramString).length() > 255) {
/*  79 */           throw new IllegalArgumentException(paramString + " IDN: " + IDN.toASCII(paramString) + " exceeds 255 char limit");
/*     */         }
/*     */         
/*     */         break;
/*     */       case IPv6: 
/*  84 */         if (!NetUtil.isValidIpV6Address(paramString)) {
/*  85 */           throw new IllegalArgumentException(paramString + " is not a valid IPv6 address");
/*     */         }
/*     */         
/*     */         break;
/*     */       }
/*     */       
/*  91 */       paramString = IDN.toASCII(paramString);
/*     */     }
/*  93 */     if ((paramInt < 0) || (paramInt > 65535)) {
/*  94 */       throw new IllegalArgumentException(paramInt + " is not in bounds 0 <= x <= 65535");
/*     */     }
/*  96 */     this.cmdStatus = paramSocksCmdStatus;
/*  97 */     this.addressType = paramSocksAddressType;
/*  98 */     this.host = paramString;
/*  99 */     this.port = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SocksCmdStatus cmdStatus()
/*     */   {
/* 108 */     return this.cmdStatus;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SocksAddressType addressType()
/*     */   {
/* 117 */     return this.addressType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String host()
/*     */   {
/* 129 */     if (this.host != null) {
/* 130 */       return IDN.toUnicode(this.host);
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int port()
/*     */   {
/* 143 */     return this.port;
/*     */   }
/*     */   
/*     */   public void encodeAsByteBuf(ByteBuf paramByteBuf)
/*     */   {
/* 148 */     paramByteBuf.writeByte(protocolVersion().byteValue());
/* 149 */     paramByteBuf.writeByte(this.cmdStatus.byteValue());
/* 150 */     paramByteBuf.writeByte(0);
/* 151 */     paramByteBuf.writeByte(this.addressType.byteValue());
/* 152 */     byte[] arrayOfByte; switch (this.addressType) {
/*     */     case IPv4: 
/* 154 */       arrayOfByte = this.host == null ? IPv4_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host);
/*     */       
/* 156 */       paramByteBuf.writeBytes(arrayOfByte);
/* 157 */       paramByteBuf.writeShort(this.port);
/* 158 */       break;
/*     */     
/*     */     case DOMAIN: 
/* 161 */       arrayOfByte = this.host == null ? DOMAIN_ZEROED : this.host.getBytes(CharsetUtil.US_ASCII);
/*     */       
/* 163 */       paramByteBuf.writeByte(arrayOfByte.length);
/* 164 */       paramByteBuf.writeBytes(arrayOfByte);
/* 165 */       paramByteBuf.writeShort(this.port);
/* 166 */       break;
/*     */     
/*     */     case IPv6: 
/* 169 */       arrayOfByte = this.host == null ? IPv6_HOSTNAME_ZEROED : NetUtil.createByteArrayFromIpAddressString(this.host);
/*     */       
/* 171 */       paramByteBuf.writeBytes(arrayOfByte);
/* 172 */       paramByteBuf.writeShort(this.port);
/* 173 */       break;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksCmdResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */