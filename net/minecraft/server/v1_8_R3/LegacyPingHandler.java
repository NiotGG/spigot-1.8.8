/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import java.net.InetSocketAddress;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class LegacyPingHandler extends ChannelInboundHandlerAdapter
/*     */ {
/*  16 */   private static final Logger a = ;
/*     */   
/*     */   private ServerConnection b;
/*     */   
/*     */   public LegacyPingHandler(ServerConnection paramServerConnection)
/*     */   {
/*  22 */     this.b = paramServerConnection;
/*     */   }
/*     */   
/*     */   public void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception
/*     */   {
/*  27 */     ByteBuf localByteBuf1 = (ByteBuf)paramObject;
/*     */     
/*  29 */     localByteBuf1.markReaderIndex();
/*     */     
/*  31 */     int i = 1;
/*     */     try {
/*  33 */       if (localByteBuf1.readUnsignedByte() != 254) {
/*     */         return;
/*     */       }
/*     */       
/*  37 */       InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramChannelHandlerContext.channel().remoteAddress();
/*  38 */       MinecraftServer localMinecraftServer = this.b.d();
/*     */       
/*  40 */       int j = localByteBuf1.readableBytes();
/*  41 */       String str1; switch (j) {
/*     */       case 0: 
/*  43 */         a.debug("Ping: (<1.3.x) from {}:{}", new Object[] { localInetSocketAddress.getAddress(), Integer.valueOf(localInetSocketAddress.getPort()) });
/*     */         
/*  45 */         str1 = String.format("%s§%d§%d", new Object[] { localMinecraftServer.getMotd(), Integer.valueOf(localMinecraftServer.I()), Integer.valueOf(localMinecraftServer.J()) });
/*  46 */         a(paramChannelHandlerContext, a(str1));
/*     */         
/*  48 */         break;
/*     */       
/*     */       case 1: 
/*  51 */         if (localByteBuf1.readUnsignedByte() != 1) {
/*     */           return;
/*     */         }
/*     */         
/*  55 */         a.debug("Ping: (1.4-1.5.x) from {}:{}", new Object[] { localInetSocketAddress.getAddress(), Integer.valueOf(localInetSocketAddress.getPort()) });
/*     */         
/*  57 */         str1 = String.format("§1\000%d\000%s\000%s\000%d\000%d", new Object[] { Integer.valueOf(127), localMinecraftServer.getVersion(), localMinecraftServer.getMotd(), Integer.valueOf(localMinecraftServer.I()), Integer.valueOf(localMinecraftServer.J()) });
/*  58 */         a(paramChannelHandlerContext, a(str1));
/*     */         
/*  60 */         break;
/*     */       
/*     */       default: 
/*  63 */         boolean bool = localByteBuf1.readUnsignedByte() == 1;
/*  64 */         bool &= localByteBuf1.readUnsignedByte() == 250;
/*  65 */         bool &= "MC|PingHost".equals(new String(localByteBuf1.readBytes(localByteBuf1.readShort() * 2).array(), com.google.common.base.Charsets.UTF_16BE));
/*  66 */         int k = localByteBuf1.readUnsignedShort();
/*  67 */         bool &= localByteBuf1.readUnsignedByte() >= 73;
/*  68 */         bool &= 3 + localByteBuf1.readBytes(localByteBuf1.readShort() * 2).array().length + 4 == k;
/*  69 */         bool &= localByteBuf1.readInt() <= 65535;
/*  70 */         bool &= localByteBuf1.readableBytes() == 0;
/*     */         
/*  72 */         if (!bool) {
/*     */           return;
/*     */         }
/*     */         
/*  76 */         a.debug("Ping: (1.6) from {}:{}", new Object[] { localInetSocketAddress.getAddress(), Integer.valueOf(localInetSocketAddress.getPort()) });
/*     */         
/*  78 */         String str2 = String.format("§1\000%d\000%s\000%s\000%d\000%d", new Object[] { Integer.valueOf(127), localMinecraftServer.getVersion(), localMinecraftServer.getMotd(), Integer.valueOf(localMinecraftServer.I()), Integer.valueOf(localMinecraftServer.J()) });
/*  79 */         ByteBuf localByteBuf2 = a(str2);
/*     */         try {
/*  81 */           a(paramChannelHandlerContext, localByteBuf2);
/*     */         } finally {
/*  83 */           localByteBuf2.release();
/*     */         }
/*     */       }
/*     */       
/*  87 */       localByteBuf1.release();
/*  88 */       i = 0;
/*     */     }
/*     */     catch (RuntimeException localRuntimeException) {}finally {
/*  91 */       if (i != 0) {
/*  92 */         localByteBuf1.resetReaderIndex();
/*  93 */         paramChannelHandlerContext.channel().pipeline().remove("legacy_query");
/*  94 */         paramChannelHandlerContext.fireChannelRead(paramObject);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf) {
/* 100 */     paramChannelHandlerContext.pipeline().firstContext().writeAndFlush(paramByteBuf).addListener(ChannelFutureListener.CLOSE);
/*     */   }
/*     */   
/*     */   private ByteBuf a(String paramString) {
/* 104 */     ByteBuf localByteBuf = Unpooled.buffer();
/* 105 */     localByteBuf.writeByte(255);
/*     */     
/* 107 */     char[] arrayOfChar1 = paramString.toCharArray();
/* 108 */     localByteBuf.writeShort(arrayOfChar1.length);
/* 109 */     for (int k : arrayOfChar1) {
/* 110 */       localByteBuf.writeChar(k);
/*     */     }
/*     */     
/* 113 */     return localByteBuf;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\LegacyPingHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */