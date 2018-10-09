/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
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
/*     */ final class WebSocketUtil
/*     */ {
/*     */   static byte[] md5(byte[] paramArrayOfByte)
/*     */   {
/*     */     try
/*     */     {
/*  39 */       MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
/*     */       
/*  41 */       return localMessageDigest.digest(paramArrayOfByte);
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  44 */       throw new InternalError("MD5 not supported on this platform - Outdated?");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static byte[] sha1(byte[] paramArrayOfByte)
/*     */   {
/*     */     try
/*     */     {
/*  57 */       MessageDigest localMessageDigest = MessageDigest.getInstance("SHA1");
/*     */       
/*  59 */       return localMessageDigest.digest(paramArrayOfByte);
/*     */     }
/*     */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*  62 */       throw new InternalError("SHA-1 is not supported on this platform - Outdated?");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static String base64(byte[] paramArrayOfByte)
/*     */   {
/*  73 */     ByteBuf localByteBuf1 = Unpooled.wrappedBuffer(paramArrayOfByte);
/*  74 */     ByteBuf localByteBuf2 = Base64.encode(localByteBuf1);
/*  75 */     String str = localByteBuf2.toString(CharsetUtil.UTF_8);
/*  76 */     localByteBuf2.release();
/*  77 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static byte[] randomBytes(int paramInt)
/*     */   {
/*  87 */     byte[] arrayOfByte = new byte[paramInt];
/*     */     
/*  89 */     for (int i = 0; i < paramInt; i++) {
/*  90 */       arrayOfByte[i] = ((byte)randomNumber(0, 255));
/*     */     }
/*     */     
/*  93 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int randomNumber(int paramInt1, int paramInt2)
/*     */   {
/* 104 */     return (int)(Math.random() * paramInt2 + paramInt1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\websocketx\WebSocketUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */