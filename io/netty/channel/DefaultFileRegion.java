/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.AbstractReferenceCounted;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.WritableByteChannel;
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
/*     */ public class DefaultFileRegion
/*     */   extends AbstractReferenceCounted
/*     */   implements FileRegion
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultFileRegion.class);
/*     */   
/*     */ 
/*     */   private final FileChannel file;
/*     */   
/*     */ 
/*     */   private final long position;
/*     */   
/*     */   private final long count;
/*     */   
/*     */   private long transfered;
/*     */   
/*     */ 
/*     */   public DefaultFileRegion(FileChannel paramFileChannel, long paramLong1, long paramLong2)
/*     */   {
/*  49 */     if (paramFileChannel == null) {
/*  50 */       throw new NullPointerException("file");
/*     */     }
/*  52 */     if (paramLong1 < 0L) {
/*  53 */       throw new IllegalArgumentException("position must be >= 0 but was " + paramLong1);
/*     */     }
/*  55 */     if (paramLong2 < 0L) {
/*  56 */       throw new IllegalArgumentException("count must be >= 0 but was " + paramLong2);
/*     */     }
/*  58 */     this.file = paramFileChannel;
/*  59 */     this.position = paramLong1;
/*  60 */     this.count = paramLong2;
/*     */   }
/*     */   
/*     */   public long position()
/*     */   {
/*  65 */     return this.position;
/*     */   }
/*     */   
/*     */   public long count()
/*     */   {
/*  70 */     return this.count;
/*     */   }
/*     */   
/*     */   public long transfered()
/*     */   {
/*  75 */     return this.transfered;
/*     */   }
/*     */   
/*     */   public long transferTo(WritableByteChannel paramWritableByteChannel, long paramLong) throws IOException
/*     */   {
/*  80 */     long l1 = this.count - paramLong;
/*  81 */     if ((l1 < 0L) || (paramLong < 0L)) {
/*  82 */       throw new IllegalArgumentException("position out of range: " + paramLong + " (expected: 0 - " + (this.count - 1L) + ')');
/*     */     }
/*     */     
/*     */ 
/*  86 */     if (l1 == 0L) {
/*  87 */       return 0L;
/*     */     }
/*     */     
/*  90 */     long l2 = this.file.transferTo(this.position + paramLong, l1, paramWritableByteChannel);
/*  91 */     if (l2 > 0L) {
/*  92 */       this.transfered += l2;
/*     */     }
/*  94 */     return l2;
/*     */   }
/*     */   
/*     */   protected void deallocate()
/*     */   {
/*     */     try {
/* 100 */       this.file.close();
/*     */     } catch (IOException localIOException) {
/* 102 */       if (logger.isWarnEnabled()) {
/* 103 */         logger.warn("Failed to close a file.", localIOException);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\DefaultFileRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */