/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ReadOnlyBufferException;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLEngineResult.HandshakeStatus;
/*     */ import javax.net.ssl.SSLEngineResult.Status;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.security.cert.X509Certificate;
/*     */ import org.apache.tomcat.jni.Buffer;
/*     */ import org.apache.tomcat.jni.SSL;
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
/*     */ public final class OpenSslEngine
/*     */   extends SSLEngine
/*     */ {
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSslEngine.class);
/*     */   
/*  49 */   private static final Certificate[] EMPTY_CERTIFICATES = new Certificate[0];
/*  50 */   private static final X509Certificate[] EMPTY_X509_CERTIFICATES = new X509Certificate[0];
/*     */   
/*  52 */   private static final SSLException ENGINE_CLOSED = new SSLException("engine closed");
/*  53 */   private static final SSLException RENEGOTIATION_UNSUPPORTED = new SSLException("renegotiation unsupported");
/*  54 */   private static final SSLException ENCRYPTED_PACKET_OVERSIZED = new SSLException("encrypted packet oversized");
/*     */   private static final int MAX_PLAINTEXT_LENGTH = 16384;
/*     */   
/*  57 */   static { ENGINE_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  58 */     RENEGOTIATION_UNSUPPORTED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  59 */     ENCRYPTED_PACKET_OVERSIZED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*     */   }
/*     */   
/*     */ 
/*     */   private static final int MAX_COMPRESSED_LENGTH = 17408;
/*     */   
/*     */   private static final int MAX_CIPHERTEXT_LENGTH = 18432;
/*     */   
/*     */   static final int MAX_ENCRYPTED_PACKET_LENGTH = 18713;
/*     */   
/*     */   static final int MAX_ENCRYPTION_OVERHEAD_LENGTH = 2329;
/*     */   
/*  71 */   private static final AtomicIntegerFieldUpdater<OpenSslEngine> DESTROYED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(OpenSslEngine.class, "destroyed");
/*     */   
/*     */ 
/*     */   private long ssl;
/*     */   
/*     */ 
/*     */   private long networkBIO;
/*     */   
/*     */   private int accepted;
/*     */   
/*     */   private boolean handshakeFinished;
/*     */   
/*     */   private boolean receivedShutdown;
/*     */   
/*     */   private volatile int destroyed;
/*     */   
/*     */   private String cipher;
/*     */   
/*     */   private volatile String applicationProtocol;
/*     */   
/*     */   private boolean isInboundDone;
/*     */   
/*     */   private boolean isOutboundDone;
/*     */   
/*     */   private boolean engineClosed;
/*     */   
/*     */   private int lastPrimingReadResult;
/*     */   
/*     */   private final ByteBufAllocator alloc;
/*     */   
/*     */   private final String fallbackApplicationProtocol;
/*     */   
/*     */   private SSLSession session;
/*     */   
/*     */ 
/*     */   public OpenSslEngine(long paramLong, ByteBufAllocator paramByteBufAllocator, String paramString)
/*     */   {
/* 108 */     OpenSsl.ensureAvailability();
/* 109 */     if (paramLong == 0L) {
/* 110 */       throw new NullPointerException("sslContext");
/*     */     }
/* 112 */     if (paramByteBufAllocator == null) {
/* 113 */       throw new NullPointerException("alloc");
/*     */     }
/*     */     
/* 116 */     this.alloc = paramByteBufAllocator;
/* 117 */     this.ssl = SSL.newSSL(paramLong, true);
/* 118 */     this.networkBIO = SSL.makeNetworkBIO(this.ssl);
/* 119 */     this.fallbackApplicationProtocol = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void shutdown()
/*     */   {
/* 126 */     if (DESTROYED_UPDATER.compareAndSet(this, 0, 1)) {
/* 127 */       SSL.freeSSL(this.ssl);
/* 128 */       SSL.freeBIO(this.networkBIO);
/* 129 */       this.ssl = (this.networkBIO = 0L);
/*     */       
/*     */ 
/* 132 */       this.isInboundDone = (this.isOutboundDone = this.engineClosed = 1);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int writePlaintextData(ByteBuffer paramByteBuffer)
/*     */   {
/* 142 */     int i = paramByteBuffer.position();
/* 143 */     int j = paramByteBuffer.limit();
/* 144 */     int k = Math.min(j - i, 16384);
/*     */     
/*     */     int m;
/* 147 */     if (paramByteBuffer.isDirect()) {
/* 148 */       long l1 = Buffer.address(paramByteBuffer) + i;
/* 149 */       m = SSL.writeToSSL(this.ssl, l1, k);
/* 150 */       if (m > 0) {
/* 151 */         paramByteBuffer.position(i + m);
/* 152 */         return m;
/*     */       }
/*     */     } else {
/* 155 */       ByteBuf localByteBuf = this.alloc.directBuffer(k);
/*     */       try {
/*     */         long l2;
/* 158 */         if (localByteBuf.hasMemoryAddress()) {
/* 159 */           l2 = localByteBuf.memoryAddress();
/*     */         } else {
/* 161 */           l2 = Buffer.address(localByteBuf.nioBuffer());
/*     */         }
/*     */         
/* 164 */         paramByteBuffer.limit(i + k);
/*     */         
/* 166 */         localByteBuf.setBytes(0, paramByteBuffer);
/* 167 */         paramByteBuffer.limit(j);
/*     */         
/* 169 */         m = SSL.writeToSSL(this.ssl, l2, k);
/* 170 */         if (m > 0) {
/* 171 */           paramByteBuffer.position(i + m);
/* 172 */           return m;
/*     */         }
/* 174 */         paramByteBuffer.position(i);
/*     */       }
/*     */       finally {
/* 177 */         localByteBuf.release();
/*     */       }
/*     */     }
/*     */     
/* 181 */     throw new IllegalStateException("SSL.writeToSSL() returned a non-positive value: " + m);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int writeEncryptedData(ByteBuffer paramByteBuffer)
/*     */   {
/* 188 */     int i = paramByteBuffer.position();
/* 189 */     int j = paramByteBuffer.remaining();
/* 190 */     if (paramByteBuffer.isDirect()) {
/* 191 */       long l1 = Buffer.address(paramByteBuffer) + i;
/* 192 */       int k = SSL.writeToBIO(this.networkBIO, l1, j);
/* 193 */       if (k >= 0) {
/* 194 */         paramByteBuffer.position(i + k);
/* 195 */         this.lastPrimingReadResult = SSL.readFromSSL(this.ssl, l1, 0);
/* 196 */         return k;
/*     */       }
/*     */     } else {
/* 199 */       ByteBuf localByteBuf = this.alloc.directBuffer(j);
/*     */       try {
/*     */         long l2;
/* 202 */         if (localByteBuf.hasMemoryAddress()) {
/* 203 */           l2 = localByteBuf.memoryAddress();
/*     */         } else {
/* 205 */           l2 = Buffer.address(localByteBuf.nioBuffer());
/*     */         }
/*     */         
/* 208 */         localByteBuf.setBytes(0, paramByteBuffer);
/*     */         
/* 210 */         int m = SSL.writeToBIO(this.networkBIO, l2, j);
/* 211 */         if (m >= 0) {
/* 212 */           paramByteBuffer.position(i + m);
/* 213 */           this.lastPrimingReadResult = SSL.readFromSSL(this.ssl, l2, 0);
/* 214 */           return m;
/*     */         }
/* 216 */         paramByteBuffer.position(i);
/*     */       }
/*     */       finally {
/* 219 */         localByteBuf.release();
/*     */       }
/*     */     }
/*     */     
/* 223 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   private int readPlaintextData(ByteBuffer paramByteBuffer)
/*     */   {
/*     */     int i;
/* 230 */     if (paramByteBuffer.isDirect()) {
/* 231 */       i = paramByteBuffer.position();
/* 232 */       long l1 = Buffer.address(paramByteBuffer) + i;
/* 233 */       int j = paramByteBuffer.limit() - i;
/* 234 */       int k = SSL.readFromSSL(this.ssl, l1, j);
/* 235 */       if (k > 0) {
/* 236 */         paramByteBuffer.position(i + k);
/* 237 */         return k;
/*     */       }
/*     */     } else {
/* 240 */       i = paramByteBuffer.position();
/* 241 */       int m = paramByteBuffer.limit();
/* 242 */       int n = Math.min(18713, m - i);
/* 243 */       ByteBuf localByteBuf = this.alloc.directBuffer(n);
/*     */       try {
/*     */         long l2;
/* 246 */         if (localByteBuf.hasMemoryAddress()) {
/* 247 */           l2 = localByteBuf.memoryAddress();
/*     */         } else {
/* 249 */           l2 = Buffer.address(localByteBuf.nioBuffer());
/*     */         }
/*     */         
/* 252 */         int i1 = SSL.readFromSSL(this.ssl, l2, n);
/* 253 */         if (i1 > 0) {
/* 254 */           paramByteBuffer.limit(i + i1);
/* 255 */           localByteBuf.getBytes(0, paramByteBuffer);
/* 256 */           paramByteBuffer.limit(m);
/* 257 */           return i1;
/*     */         }
/*     */       } finally {
/* 260 */         localByteBuf.release();
/*     */       }
/*     */     }
/*     */     
/* 264 */     return 0;
/*     */   }
/*     */   
/*     */   private int readEncryptedData(ByteBuffer paramByteBuffer, int paramInt)
/*     */   {
/*     */     long l;
/*     */     int j;
/* 271 */     if ((paramByteBuffer.isDirect()) && (paramByteBuffer.remaining() >= paramInt)) {
/* 272 */       int i = paramByteBuffer.position();
/* 273 */       l = Buffer.address(paramByteBuffer) + i;
/* 274 */       j = SSL.readFromBIO(this.networkBIO, l, paramInt);
/* 275 */       if (j > 0) {
/* 276 */         paramByteBuffer.position(i + j);
/* 277 */         return j;
/*     */       }
/*     */     } else {
/* 280 */       ByteBuf localByteBuf = this.alloc.directBuffer(paramInt);
/*     */       try
/*     */       {
/* 283 */         if (localByteBuf.hasMemoryAddress()) {
/* 284 */           l = localByteBuf.memoryAddress();
/*     */         } else {
/* 286 */           l = Buffer.address(localByteBuf.nioBuffer());
/*     */         }
/*     */         
/* 289 */         j = SSL.readFromBIO(this.networkBIO, l, paramInt);
/* 290 */         if (j > 0) {
/* 291 */           int k = paramByteBuffer.limit();
/* 292 */           paramByteBuffer.limit(paramByteBuffer.position() + j);
/* 293 */           localByteBuf.getBytes(0, paramByteBuffer);
/* 294 */           paramByteBuffer.limit(k);
/* 295 */           return j;
/*     */         }
/*     */       } finally {
/* 298 */         localByteBuf.release();
/*     */       }
/*     */     }
/*     */     
/* 302 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized SSLEngineResult wrap(ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2, ByteBuffer paramByteBuffer)
/*     */     throws SSLException
/*     */   {
/* 310 */     if (this.destroyed != 0) {
/* 311 */       return new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
/*     */     }
/*     */     
/*     */ 
/* 315 */     if (paramArrayOfByteBuffer == null) {
/* 316 */       throw new NullPointerException("srcs");
/*     */     }
/* 318 */     if (paramByteBuffer == null) {
/* 319 */       throw new NullPointerException("dst");
/*     */     }
/*     */     
/* 322 */     if ((paramInt1 >= paramArrayOfByteBuffer.length) || (paramInt1 + paramInt2 > paramArrayOfByteBuffer.length)) {
/* 323 */       throw new IndexOutOfBoundsException("offset: " + paramInt1 + ", length: " + paramInt2 + " (expected: offset <= offset + length <= srcs.length (" + paramArrayOfByteBuffer.length + "))");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 328 */     if (paramByteBuffer.isReadOnly()) {
/* 329 */       throw new ReadOnlyBufferException();
/*     */     }
/*     */     
/*     */ 
/* 333 */     if (this.accepted == 0) {
/* 334 */       beginHandshakeImplicitly();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 339 */     SSLEngineResult.HandshakeStatus localHandshakeStatus = getHandshakeStatus();
/* 340 */     if (((!this.handshakeFinished) || (this.engineClosed)) && (localHandshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP)) {
/* 341 */       return new SSLEngineResult(getEngineStatus(), SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
/*     */     }
/*     */     
/* 344 */     int i = 0;
/*     */     
/*     */ 
/*     */ 
/* 348 */     int j = SSL.pendingWrittenBytesInBIO(this.networkBIO);
/* 349 */     if (j > 0)
/*     */     {
/* 351 */       k = paramByteBuffer.remaining();
/* 352 */       if (k < j) {
/* 353 */         return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, localHandshakeStatus, 0, i);
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 358 */         i += readEncryptedData(paramByteBuffer, j);
/*     */       } catch (Exception localException1) {
/* 360 */         throw new SSLException(localException1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 366 */       if (this.isOutboundDone) {
/* 367 */         shutdown();
/*     */       }
/*     */       
/* 370 */       return new SSLEngineResult(getEngineStatus(), getHandshakeStatus(), 0, i);
/*     */     }
/*     */     
/*     */ 
/* 374 */     int k = 0;
/* 375 */     for (int m = paramInt1; m < paramInt2; m++) {
/* 376 */       ByteBuffer localByteBuffer = paramArrayOfByteBuffer[m];
/* 377 */       while (localByteBuffer.hasRemaining())
/*     */       {
/*     */         try
/*     */         {
/* 381 */           k += writePlaintextData(localByteBuffer);
/*     */         } catch (Exception localException2) {
/* 383 */           throw new SSLException(localException2);
/*     */         }
/*     */         
/*     */ 
/* 387 */         j = SSL.pendingWrittenBytesInBIO(this.networkBIO);
/* 388 */         if (j > 0)
/*     */         {
/* 390 */           int n = paramByteBuffer.remaining();
/* 391 */           if (n < j) {
/* 392 */             return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), k, i);
/*     */           }
/*     */           
/*     */           try
/*     */           {
/* 397 */             i += readEncryptedData(paramByteBuffer, j);
/*     */           } catch (Exception localException3) {
/* 399 */             throw new SSLException(localException3);
/*     */           }
/*     */           
/* 402 */           return new SSLEngineResult(getEngineStatus(), getHandshakeStatus(), k, i);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 407 */     return new SSLEngineResult(getEngineStatus(), getHandshakeStatus(), k, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized SSLEngineResult unwrap(ByteBuffer paramByteBuffer, ByteBuffer[] paramArrayOfByteBuffer, int paramInt1, int paramInt2)
/*     */     throws SSLException
/*     */   {
/* 415 */     if (this.destroyed != 0) {
/* 416 */       return new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
/*     */     }
/*     */     
/*     */ 
/* 420 */     if (paramByteBuffer == null) {
/* 421 */       throw new NullPointerException("src");
/*     */     }
/* 423 */     if (paramArrayOfByteBuffer == null) {
/* 424 */       throw new NullPointerException("dsts");
/*     */     }
/* 426 */     if ((paramInt1 >= paramArrayOfByteBuffer.length) || (paramInt1 + paramInt2 > paramArrayOfByteBuffer.length)) {
/* 427 */       throw new IndexOutOfBoundsException("offset: " + paramInt1 + ", length: " + paramInt2 + " (expected: offset <= offset + length <= dsts.length (" + paramArrayOfByteBuffer.length + "))");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 432 */     int i = 0;
/* 433 */     int j = paramInt1 + paramInt2;
/* 434 */     for (int k = paramInt1; k < j; k++) {
/* 435 */       ByteBuffer localByteBuffer1 = paramArrayOfByteBuffer[k];
/* 436 */       if (localByteBuffer1 == null) {
/* 437 */         throw new IllegalArgumentException();
/*     */       }
/* 439 */       if (localByteBuffer1.isReadOnly()) {
/* 440 */         throw new ReadOnlyBufferException();
/*     */       }
/* 442 */       i += localByteBuffer1.remaining();
/*     */     }
/*     */     
/*     */ 
/* 446 */     if (this.accepted == 0) {
/* 447 */       beginHandshakeImplicitly();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 452 */     SSLEngineResult.HandshakeStatus localHandshakeStatus = getHandshakeStatus();
/* 453 */     if (((!this.handshakeFinished) || (this.engineClosed)) && (localHandshakeStatus == SSLEngineResult.HandshakeStatus.NEED_WRAP)) {
/* 454 */       return new SSLEngineResult(getEngineStatus(), SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
/*     */     }
/*     */     
/*     */ 
/* 458 */     if (paramByteBuffer.remaining() > 18713) {
/* 459 */       this.isInboundDone = true;
/* 460 */       this.isOutboundDone = true;
/* 461 */       this.engineClosed = true;
/* 462 */       shutdown();
/* 463 */       throw ENCRYPTED_PACKET_OVERSIZED;
/*     */     }
/*     */     
/*     */ 
/* 467 */     int m = 0;
/* 468 */     this.lastPrimingReadResult = 0;
/*     */     try {
/* 470 */       m += writeEncryptedData(paramByteBuffer);
/*     */     } catch (Exception localException1) {
/* 472 */       throw new SSLException(localException1);
/*     */     }
/*     */     
/*     */ 
/* 476 */     String str = SSL.getLastError();
/* 477 */     if ((str != null) && (!str.startsWith("error:00000000:"))) {
/* 478 */       if (logger.isInfoEnabled()) {
/* 479 */         logger.info("SSL_read failed: primingReadResult: " + this.lastPrimingReadResult + "; OpenSSL error: '" + str + '\'');
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 485 */       shutdown();
/* 486 */       throw new SSLException(str);
/*     */     }
/*     */     
/*     */ 
/* 490 */     int n = SSL.isInInit(this.ssl) == 0 ? SSL.pendingReadableBytesInSSL(this.ssl) : 0;
/*     */     
/*     */ 
/* 493 */     if (i < n) {
/* 494 */       return new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, getHandshakeStatus(), m, 0);
/*     */     }
/*     */     
/*     */ 
/* 498 */     int i1 = 0;
/* 499 */     int i2 = paramInt1;
/* 500 */     while (i2 < j) {
/* 501 */       ByteBuffer localByteBuffer2 = paramArrayOfByteBuffer[i2];
/* 502 */       if (!localByteBuffer2.hasRemaining()) {
/* 503 */         i2++;
/*     */       }
/*     */       else
/*     */       {
/* 507 */         if (n <= 0) {
/*     */           break;
/*     */         }
/*     */         int i3;
/*     */         try
/*     */         {
/* 513 */           i3 = readPlaintextData(localByteBuffer2);
/*     */         } catch (Exception localException2) {
/* 515 */           throw new SSLException(localException2);
/*     */         }
/*     */         
/* 518 */         if (i3 == 0) {
/*     */           break;
/*     */         }
/*     */         
/* 522 */         i1 += i3;
/* 523 */         n -= i3;
/*     */         
/* 525 */         if (!localByteBuffer2.hasRemaining()) {
/* 526 */           i2++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 531 */     if ((!this.receivedShutdown) && ((SSL.getShutdown(this.ssl) & 0x2) == 2)) {
/* 532 */       this.receivedShutdown = true;
/* 533 */       closeOutbound();
/* 534 */       closeInbound();
/*     */     }
/*     */     
/* 537 */     return new SSLEngineResult(getEngineStatus(), getHandshakeStatus(), m, i1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Runnable getDelegatedTask()
/*     */   {
/* 545 */     return null;
/*     */   }
/*     */   
/*     */   public synchronized void closeInbound() throws SSLException
/*     */   {
/* 550 */     if (this.isInboundDone) {
/* 551 */       return;
/*     */     }
/*     */     
/* 554 */     this.isInboundDone = true;
/* 555 */     this.engineClosed = true;
/*     */     
/* 557 */     if (this.accepted != 0) {
/* 558 */       if (!this.receivedShutdown) {
/* 559 */         shutdown();
/* 560 */         throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 565 */       shutdown();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized boolean isInboundDone()
/*     */   {
/* 571 */     return (this.isInboundDone) || (this.engineClosed);
/*     */   }
/*     */   
/*     */   public synchronized void closeOutbound()
/*     */   {
/* 576 */     if (this.isOutboundDone) {
/* 577 */       return;
/*     */     }
/*     */     
/* 580 */     this.isOutboundDone = true;
/* 581 */     this.engineClosed = true;
/*     */     
/* 583 */     if ((this.accepted != 0) && (this.destroyed == 0)) {
/* 584 */       int i = SSL.getShutdown(this.ssl);
/* 585 */       if ((i & 0x1) != 1) {
/* 586 */         SSL.shutdownSSL(this.ssl);
/*     */       }
/*     */     }
/*     */     else {
/* 590 */       shutdown();
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized boolean isOutboundDone()
/*     */   {
/* 596 */     return this.isOutboundDone;
/*     */   }
/*     */   
/*     */   public String[] getSupportedCipherSuites()
/*     */   {
/* 601 */     return EmptyArrays.EMPTY_STRINGS;
/*     */   }
/*     */   
/*     */   public String[] getEnabledCipherSuites()
/*     */   {
/* 606 */     return EmptyArrays.EMPTY_STRINGS;
/*     */   }
/*     */   
/*     */   public void setEnabledCipherSuites(String[] paramArrayOfString)
/*     */   {
/* 611 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String[] getSupportedProtocols()
/*     */   {
/* 616 */     return EmptyArrays.EMPTY_STRINGS;
/*     */   }
/*     */   
/*     */   public String[] getEnabledProtocols()
/*     */   {
/* 621 */     return EmptyArrays.EMPTY_STRINGS;
/*     */   }
/*     */   
/*     */   public void setEnabledProtocols(String[] paramArrayOfString)
/*     */   {
/* 626 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SSLSession getSession()
/*     */   {
/* 631 */     Object localObject = this.session;
/* 632 */     if (localObject == null) {
/* 633 */       this.session = ( = new SSLSession()
/*     */       {
/*     */         public byte[] getId() {
/* 636 */           return String.valueOf(OpenSslEngine.this.ssl).getBytes();
/*     */         }
/*     */         
/*     */         public SSLSessionContext getSessionContext()
/*     */         {
/* 641 */           return null;
/*     */         }
/*     */         
/*     */         public long getCreationTime()
/*     */         {
/* 646 */           return 0L;
/*     */         }
/*     */         
/*     */         public long getLastAccessedTime()
/*     */         {
/* 651 */           return 0L;
/*     */         }
/*     */         
/*     */ 
/*     */         public void invalidate() {}
/*     */         
/*     */ 
/*     */         public boolean isValid()
/*     */         {
/* 660 */           return false;
/*     */         }
/*     */         
/*     */ 
/*     */         public void putValue(String paramAnonymousString, Object paramAnonymousObject) {}
/*     */         
/*     */ 
/*     */         public Object getValue(String paramAnonymousString)
/*     */         {
/* 669 */           return null;
/*     */         }
/*     */         
/*     */ 
/*     */         public void removeValue(String paramAnonymousString) {}
/*     */         
/*     */ 
/*     */         public String[] getValueNames()
/*     */         {
/* 678 */           return EmptyArrays.EMPTY_STRINGS;
/*     */         }
/*     */         
/*     */         public Certificate[] getPeerCertificates()
/*     */         {
/* 683 */           return OpenSslEngine.EMPTY_CERTIFICATES;
/*     */         }
/*     */         
/*     */         public Certificate[] getLocalCertificates()
/*     */         {
/* 688 */           return OpenSslEngine.EMPTY_CERTIFICATES;
/*     */         }
/*     */         
/*     */         public X509Certificate[] getPeerCertificateChain()
/*     */         {
/* 693 */           return OpenSslEngine.EMPTY_X509_CERTIFICATES;
/*     */         }
/*     */         
/*     */         public Principal getPeerPrincipal()
/*     */         {
/* 698 */           return null;
/*     */         }
/*     */         
/*     */         public Principal getLocalPrincipal()
/*     */         {
/* 703 */           return null;
/*     */         }
/*     */         
/*     */         public String getCipherSuite()
/*     */         {
/* 708 */           return OpenSslEngine.this.cipher;
/*     */         }
/*     */         
/*     */ 
/*     */         public String getProtocol()
/*     */         {
/* 714 */           String str = OpenSslEngine.this.applicationProtocol;
/* 715 */           if (str == null) {
/* 716 */             return "unknown";
/*     */           }
/* 718 */           return "unknown:" + str;
/*     */         }
/*     */         
/*     */ 
/*     */         public String getPeerHost()
/*     */         {
/* 724 */           return null;
/*     */         }
/*     */         
/*     */         public int getPeerPort()
/*     */         {
/* 729 */           return 0;
/*     */         }
/*     */         
/*     */         public int getPacketBufferSize()
/*     */         {
/* 734 */           return 18713;
/*     */         }
/*     */         
/*     */         public int getApplicationBufferSize()
/*     */         {
/* 739 */           return 16384;
/*     */         }
/*     */       });
/*     */     }
/*     */     
/* 744 */     return (SSLSession)localObject;
/*     */   }
/*     */   
/*     */   public synchronized void beginHandshake() throws SSLException
/*     */   {
/* 749 */     if (this.engineClosed) {
/* 750 */       throw ENGINE_CLOSED;
/*     */     }
/*     */     
/* 753 */     switch (this.accepted) {
/*     */     case 0: 
/* 755 */       SSL.doHandshake(this.ssl);
/* 756 */       this.accepted = 2;
/* 757 */       break;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     case 1: 
/* 765 */       this.accepted = 2;
/* 766 */       break;
/*     */     case 2: 
/* 768 */       throw RENEGOTIATION_UNSUPPORTED;
/*     */     default: 
/* 770 */       throw new Error();
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void beginHandshakeImplicitly() throws SSLException {
/* 775 */     if (this.engineClosed) {
/* 776 */       throw ENGINE_CLOSED;
/*     */     }
/*     */     
/* 779 */     if (this.accepted == 0) {
/* 780 */       SSL.doHandshake(this.ssl);
/* 781 */       this.accepted = 1;
/*     */     }
/*     */   }
/*     */   
/*     */   private SSLEngineResult.Status getEngineStatus() {
/* 786 */     return this.engineClosed ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK;
/*     */   }
/*     */   
/*     */   public synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus()
/*     */   {
/* 791 */     if ((this.accepted == 0) || (this.destroyed != 0)) {
/* 792 */       return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/*     */     }
/*     */     
/*     */ 
/* 796 */     if (!this.handshakeFinished)
/*     */     {
/* 798 */       if (SSL.pendingWrittenBytesInBIO(this.networkBIO) != 0) {
/* 799 */         return SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 804 */       if (SSL.isInInit(this.ssl) == 0) {
/* 805 */         this.handshakeFinished = true;
/* 806 */         this.cipher = SSL.getCipherForSSL(this.ssl);
/* 807 */         String str = SSL.getNextProtoNegotiated(this.ssl);
/* 808 */         if (str == null) {
/* 809 */           str = this.fallbackApplicationProtocol;
/*     */         }
/* 811 */         if (str != null) {
/* 812 */           this.applicationProtocol = str.replace(':', '_');
/*     */         } else {
/* 814 */           this.applicationProtocol = null;
/*     */         }
/* 816 */         return SSLEngineResult.HandshakeStatus.FINISHED;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 821 */       return SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
/*     */     }
/*     */     
/*     */ 
/* 825 */     if (this.engineClosed)
/*     */     {
/* 827 */       if (SSL.pendingWrittenBytesInBIO(this.networkBIO) != 0) {
/* 828 */         return SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*     */       }
/*     */       
/*     */ 
/* 832 */       return SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
/*     */     }
/*     */     
/* 835 */     return SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
/*     */   }
/*     */   
/*     */   public void setUseClientMode(boolean paramBoolean)
/*     */   {
/* 840 */     if (paramBoolean) {
/* 841 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getUseClientMode()
/*     */   {
/* 847 */     return false;
/*     */   }
/*     */   
/*     */   public void setNeedClientAuth(boolean paramBoolean)
/*     */   {
/* 852 */     if (paramBoolean) {
/* 853 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getNeedClientAuth()
/*     */   {
/* 859 */     return false;
/*     */   }
/*     */   
/*     */   public void setWantClientAuth(boolean paramBoolean)
/*     */   {
/* 864 */     if (paramBoolean) {
/* 865 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getWantClientAuth()
/*     */   {
/* 871 */     return false;
/*     */   }
/*     */   
/*     */   public void setEnableSessionCreation(boolean paramBoolean)
/*     */   {
/* 876 */     if (paramBoolean) {
/* 877 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getEnableSessionCreation()
/*     */   {
/* 883 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\ssl\OpenSslEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */