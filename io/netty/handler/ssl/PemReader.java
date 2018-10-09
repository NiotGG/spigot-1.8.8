/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.base64.Base64;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.security.KeyException;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ final class PemReader
/*     */ {
/*  45 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PemReader.class);
/*     */   
/*  47 */   private static final Pattern CERT_PATTERN = Pattern.compile("-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*CERTIFICATE[^-]*-+", 2);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  52 */   private static final Pattern KEY_PATTERN = Pattern.compile("-+BEGIN\\s+.*PRIVATE\\s+KEY[^-]*-+(?:\\s|\\r|\\n)+([a-z0-9+/=\\r\\n]+)-+END\\s+.*PRIVATE\\s+KEY[^-]*-+", 2);
/*     */   
/*     */ 
/*     */   static ByteBuf[] readCertificates(File paramFile)
/*     */     throws CertificateException
/*     */   {
/*     */     String str;
/*     */     try
/*     */     {
/*  61 */       str = readContent(paramFile);
/*     */     } catch (IOException localIOException) {
/*  63 */       throw new CertificateException("failed to read a file: " + paramFile, localIOException);
/*     */     }
/*     */     
/*  66 */     ArrayList localArrayList = new ArrayList();
/*  67 */     Matcher localMatcher = CERT_PATTERN.matcher(str);
/*  68 */     int i = 0;
/*     */     
/*  70 */     while (localMatcher.find(i))
/*     */     {
/*     */ 
/*     */ 
/*  74 */       ByteBuf localByteBuf1 = Unpooled.copiedBuffer(localMatcher.group(1), CharsetUtil.US_ASCII);
/*  75 */       ByteBuf localByteBuf2 = Base64.decode(localByteBuf1);
/*  76 */       localByteBuf1.release();
/*  77 */       localArrayList.add(localByteBuf2);
/*     */       
/*  79 */       i = localMatcher.end();
/*     */     }
/*     */     
/*  82 */     if (localArrayList.isEmpty()) {
/*  83 */       throw new CertificateException("found no certificates: " + paramFile);
/*     */     }
/*     */     
/*  86 */     return (ByteBuf[])localArrayList.toArray(new ByteBuf[localArrayList.size()]);
/*     */   }
/*     */   
/*     */   static ByteBuf readPrivateKey(File paramFile) throws KeyException {
/*     */     String str;
/*     */     try {
/*  92 */       str = readContent(paramFile);
/*     */     } catch (IOException localIOException) {
/*  94 */       throw new KeyException("failed to read a file: " + paramFile, localIOException);
/*     */     }
/*     */     
/*  97 */     Matcher localMatcher = KEY_PATTERN.matcher(str);
/*  98 */     if (!localMatcher.find()) {
/*  99 */       throw new KeyException("found no private key: " + paramFile);
/*     */     }
/*     */     
/* 102 */     ByteBuf localByteBuf1 = Unpooled.copiedBuffer(localMatcher.group(1), CharsetUtil.US_ASCII);
/* 103 */     ByteBuf localByteBuf2 = Base64.decode(localByteBuf1);
/* 104 */     localByteBuf1.release();
/* 105 */     return localByteBuf2;
/*     */   }
/*     */   
/*     */   private static String readContent(File paramFile) throws IOException {
/* 109 */     FileInputStream localFileInputStream = new FileInputStream(paramFile);
/* 110 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*     */     try {
/* 112 */       byte[] arrayOfByte = new byte[' '];
/*     */       for (;;) {
/* 114 */         int i = localFileInputStream.read(arrayOfByte);
/* 115 */         if (i < 0) {
/*     */           break;
/*     */         }
/* 118 */         localByteArrayOutputStream.write(arrayOfByte, 0, i);
/*     */       }
/* 120 */       return localByteArrayOutputStream.toString(CharsetUtil.US_ASCII.name());
/*     */     } finally {
/* 122 */       safeClose(localFileInputStream);
/* 123 */       safeClose(localByteArrayOutputStream);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void safeClose(InputStream paramInputStream) {
/*     */     try {
/* 129 */       paramInputStream.close();
/*     */     } catch (IOException localIOException) {
/* 131 */       logger.warn("Failed to close a stream.", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void safeClose(OutputStream paramOutputStream) {
/*     */     try {
/* 137 */       paramOutputStream.close();
/*     */     } catch (IOException localIOException) {
/* 139 */       logger.warn("Failed to close a stream.", localIOException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\PemReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */