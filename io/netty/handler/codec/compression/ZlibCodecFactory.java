/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public final class ZlibCodecFactory
/*     */ {
/*  27 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ZlibCodecFactory.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  32 */   private static final boolean noJdkZlibDecoder = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibDecoder", true);
/*  33 */   static { logger.debug("-Dio.netty.noJdkZlibDecoder: {}", Boolean.valueOf(noJdkZlibDecoder)); }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int paramInt)
/*     */   {
/*  37 */     if (PlatformDependent.javaVersion() < 7) {
/*  38 */       return new JZlibEncoder(paramInt);
/*     */     }
/*  40 */     return new JdkZlibEncoder(paramInt);
/*     */   }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper paramZlibWrapper)
/*     */   {
/*  45 */     if (PlatformDependent.javaVersion() < 7) {
/*  46 */       return new JZlibEncoder(paramZlibWrapper);
/*     */     }
/*  48 */     return new JdkZlibEncoder(paramZlibWrapper);
/*     */   }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper paramZlibWrapper, int paramInt)
/*     */   {
/*  53 */     if (PlatformDependent.javaVersion() < 7) {
/*  54 */       return new JZlibEncoder(paramZlibWrapper, paramInt);
/*     */     }
/*  56 */     return new JdkZlibEncoder(paramZlibWrapper, paramInt);
/*     */   }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper paramZlibWrapper, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  61 */     if (PlatformDependent.javaVersion() < 7) {
/*  62 */       return new JZlibEncoder(paramZlibWrapper, paramInt1, paramInt2, paramInt3);
/*     */     }
/*  64 */     return new JdkZlibEncoder(paramZlibWrapper, paramInt1);
/*     */   }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(byte[] paramArrayOfByte)
/*     */   {
/*  69 */     if (PlatformDependent.javaVersion() < 7) {
/*  70 */       return new JZlibEncoder(paramArrayOfByte);
/*     */     }
/*  72 */     return new JdkZlibEncoder(paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int paramInt, byte[] paramArrayOfByte)
/*     */   {
/*  77 */     if (PlatformDependent.javaVersion() < 7) {
/*  78 */       return new JZlibEncoder(paramInt, paramArrayOfByte);
/*     */     }
/*  80 */     return new JdkZlibEncoder(paramInt, paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte)
/*     */   {
/*  85 */     if (PlatformDependent.javaVersion() < 7) {
/*  86 */       return new JZlibEncoder(paramInt1, paramInt2, paramInt3, paramArrayOfByte);
/*     */     }
/*  88 */     return new JdkZlibEncoder(paramInt1, paramArrayOfByte);
/*     */   }
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder()
/*     */   {
/*  93 */     if ((PlatformDependent.javaVersion() < 7) || (noJdkZlibDecoder)) {
/*  94 */       return new JZlibDecoder();
/*     */     }
/*  96 */     return new JdkZlibDecoder();
/*     */   }
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder(ZlibWrapper paramZlibWrapper)
/*     */   {
/* 101 */     if ((PlatformDependent.javaVersion() < 7) || (noJdkZlibDecoder)) {
/* 102 */       return new JZlibDecoder(paramZlibWrapper);
/*     */     }
/* 104 */     return new JdkZlibDecoder(paramZlibWrapper);
/*     */   }
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder(byte[] paramArrayOfByte)
/*     */   {
/* 109 */     if ((PlatformDependent.javaVersion() < 7) || (noJdkZlibDecoder)) {
/* 110 */       return new JZlibDecoder(paramArrayOfByte);
/*     */     }
/* 112 */     return new JdkZlibDecoder(paramArrayOfByte);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\ZlibCodecFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */