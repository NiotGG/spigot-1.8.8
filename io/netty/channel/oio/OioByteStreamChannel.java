/*     */ package io.netty.channel.oio;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.FileRegion;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.NotYetConnectedException;
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
/*     */ public abstract class OioByteStreamChannel
/*     */   extends AbstractOioByteChannel
/*     */ {
/*  36 */   private static final InputStream CLOSED_IN = new InputStream()
/*     */   {
/*     */     public int read() {
/*  39 */       return -1;
/*     */     }
/*     */   };
/*     */   
/*  43 */   private static final OutputStream CLOSED_OUT = new OutputStream()
/*     */   {
/*     */     public void write(int paramAnonymousInt) throws IOException {
/*  46 */       throw new ClosedChannelException();
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */   private InputStream is;
/*     */   
/*     */ 
/*     */   private OutputStream os;
/*     */   
/*     */   private WritableByteChannel outChannel;
/*     */   
/*     */ 
/*     */   protected OioByteStreamChannel(Channel paramChannel)
/*     */   {
/*  61 */     super(paramChannel);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected final void activate(InputStream paramInputStream, OutputStream paramOutputStream)
/*     */   {
/*  68 */     if (this.is != null) {
/*  69 */       throw new IllegalStateException("input was set already");
/*     */     }
/*  71 */     if (this.os != null) {
/*  72 */       throw new IllegalStateException("output was set already");
/*     */     }
/*  74 */     if (paramInputStream == null) {
/*  75 */       throw new NullPointerException("is");
/*     */     }
/*  77 */     if (paramOutputStream == null) {
/*  78 */       throw new NullPointerException("os");
/*     */     }
/*  80 */     this.is = paramInputStream;
/*  81 */     this.os = paramOutputStream;
/*     */   }
/*     */   
/*     */   public boolean isActive()
/*     */   {
/*  86 */     InputStream localInputStream = this.is;
/*  87 */     if ((localInputStream == null) || (localInputStream == CLOSED_IN)) {
/*  88 */       return false;
/*     */     }
/*     */     
/*  91 */     OutputStream localOutputStream = this.os;
/*  92 */     return (localOutputStream != null) && (localOutputStream != CLOSED_OUT);
/*     */   }
/*     */   
/*     */   protected int available()
/*     */   {
/*     */     try {
/*  98 */       return this.is.available();
/*     */     } catch (IOException localIOException) {}
/* 100 */     return 0;
/*     */   }
/*     */   
/*     */   protected int doReadBytes(ByteBuf paramByteBuf)
/*     */     throws Exception
/*     */   {
/* 106 */     int i = Math.max(1, Math.min(available(), paramByteBuf.maxWritableBytes()));
/* 107 */     return paramByteBuf.writeBytes(this.is, i);
/*     */   }
/*     */   
/*     */   protected void doWriteBytes(ByteBuf paramByteBuf) throws Exception
/*     */   {
/* 112 */     OutputStream localOutputStream = this.os;
/* 113 */     if (localOutputStream == null) {
/* 114 */       throw new NotYetConnectedException();
/*     */     }
/* 116 */     paramByteBuf.readBytes(localOutputStream, paramByteBuf.readableBytes());
/*     */   }
/*     */   
/*     */   protected void doWriteFileRegion(FileRegion paramFileRegion) throws Exception
/*     */   {
/* 121 */     OutputStream localOutputStream = this.os;
/* 122 */     if (localOutputStream == null) {
/* 123 */       throw new NotYetConnectedException();
/*     */     }
/* 125 */     if (this.outChannel == null) {
/* 126 */       this.outChannel = Channels.newChannel(localOutputStream);
/*     */     }
/*     */     
/* 129 */     long l1 = 0L;
/*     */     for (;;) {
/* 131 */       long l2 = paramFileRegion.transferTo(this.outChannel, l1);
/* 132 */       if (l2 == -1L) {
/* 133 */         checkEOF(paramFileRegion);
/* 134 */         return;
/*     */       }
/* 136 */       l1 += l2;
/*     */       
/* 138 */       if (l1 >= paramFileRegion.count()) {
/* 139 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void checkEOF(FileRegion paramFileRegion) throws IOException {
/* 145 */     if (paramFileRegion.transfered() < paramFileRegion.count()) {
/* 146 */       throw new EOFException("Expected to be able to write " + paramFileRegion.count() + " bytes, " + "but only wrote " + paramFileRegion.transfered());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void doClose()
/*     */     throws Exception
/*     */   {
/* 153 */     InputStream localInputStream = this.is;
/* 154 */     OutputStream localOutputStream = this.os;
/* 155 */     this.is = CLOSED_IN;
/* 156 */     this.os = CLOSED_OUT;
/*     */     try
/*     */     {
/* 159 */       if (localInputStream != null) {
/* 160 */         localInputStream.close();
/*     */       }
/*     */     } finally {
/* 163 */       if (localOutputStream != null) {
/* 164 */         localOutputStream.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\oio\OioByteStreamChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */