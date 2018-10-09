/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.charset.Charset;
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
/*     */ public abstract class AbstractMemoryHttpData
/*     */   extends AbstractHttpData
/*     */ {
/*     */   private ByteBuf byteBuf;
/*     */   private int chunkPosition;
/*     */   protected boolean isRenamed;
/*     */   
/*     */   protected AbstractMemoryHttpData(String paramString, Charset paramCharset, long paramLong)
/*     */   {
/*  43 */     super(paramString, paramCharset, paramLong);
/*     */   }
/*     */   
/*     */   public void setContent(ByteBuf paramByteBuf) throws IOException
/*     */   {
/*  48 */     if (paramByteBuf == null) {
/*  49 */       throw new NullPointerException("buffer");
/*     */     }
/*  51 */     long l = paramByteBuf.readableBytes();
/*  52 */     if ((this.definedSize > 0L) && (this.definedSize < l)) {
/*  53 */       throw new IOException("Out of size: " + l + " > " + this.definedSize);
/*     */     }
/*     */     
/*  56 */     if (this.byteBuf != null) {
/*  57 */       this.byteBuf.release();
/*     */     }
/*  59 */     this.byteBuf = paramByteBuf;
/*  60 */     this.size = l;
/*  61 */     this.completed = true;
/*     */   }
/*     */   
/*     */   public void setContent(InputStream paramInputStream) throws IOException
/*     */   {
/*  66 */     if (paramInputStream == null) {
/*  67 */       throw new NullPointerException("inputStream");
/*     */     }
/*  69 */     ByteBuf localByteBuf = Unpooled.buffer();
/*  70 */     byte[] arrayOfByte = new byte['䀀'];
/*  71 */     int i = paramInputStream.read(arrayOfByte);
/*  72 */     int j = 0;
/*  73 */     while (i > 0) {
/*  74 */       localByteBuf.writeBytes(arrayOfByte, 0, i);
/*  75 */       j += i;
/*  76 */       i = paramInputStream.read(arrayOfByte);
/*     */     }
/*  78 */     this.size = j;
/*  79 */     if ((this.definedSize > 0L) && (this.definedSize < this.size)) {
/*  80 */       throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */     }
/*  82 */     if (this.byteBuf != null) {
/*  83 */       this.byteBuf.release();
/*     */     }
/*  85 */     this.byteBuf = localByteBuf;
/*  86 */     this.completed = true;
/*     */   }
/*     */   
/*     */   public void addContent(ByteBuf paramByteBuf, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/*  92 */     if (paramByteBuf != null) {
/*  93 */       long l = paramByteBuf.readableBytes();
/*  94 */       if ((this.definedSize > 0L) && (this.definedSize < this.size + l)) {
/*  95 */         throw new IOException("Out of size: " + (this.size + l) + " > " + this.definedSize);
/*     */       }
/*     */       
/*  98 */       this.size += l;
/*  99 */       if (this.byteBuf == null) {
/* 100 */         this.byteBuf = paramByteBuf; } else { CompositeByteBuf localCompositeByteBuf;
/* 101 */         if ((this.byteBuf instanceof CompositeByteBuf)) {
/* 102 */           localCompositeByteBuf = (CompositeByteBuf)this.byteBuf;
/* 103 */           localCompositeByteBuf.addComponent(paramByteBuf);
/* 104 */           localCompositeByteBuf.writerIndex(localCompositeByteBuf.writerIndex() + paramByteBuf.readableBytes());
/*     */         } else {
/* 106 */           localCompositeByteBuf = Unpooled.compositeBuffer(Integer.MAX_VALUE);
/* 107 */           localCompositeByteBuf.addComponents(new ByteBuf[] { this.byteBuf, paramByteBuf });
/* 108 */           localCompositeByteBuf.writerIndex(this.byteBuf.readableBytes() + paramByteBuf.readableBytes());
/* 109 */           this.byteBuf = localCompositeByteBuf;
/*     */         }
/*     */       } }
/* 112 */     if (paramBoolean) {
/* 113 */       this.completed = true;
/*     */     }
/* 115 */     else if (paramByteBuf == null) {
/* 116 */       throw new NullPointerException("buffer");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setContent(File paramFile)
/*     */     throws IOException
/*     */   {
/* 123 */     if (paramFile == null) {
/* 124 */       throw new NullPointerException("file");
/*     */     }
/* 126 */     long l = paramFile.length();
/* 127 */     if (l > 2147483647L) {
/* 128 */       throw new IllegalArgumentException("File too big to be loaded in memory");
/*     */     }
/*     */     
/* 131 */     FileInputStream localFileInputStream = new FileInputStream(paramFile);
/* 132 */     FileChannel localFileChannel = localFileInputStream.getChannel();
/* 133 */     byte[] arrayOfByte = new byte[(int)l];
/* 134 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(arrayOfByte);
/* 135 */     int i = 0;
/* 136 */     while (i < l) {
/* 137 */       i += localFileChannel.read(localByteBuffer);
/*     */     }
/* 139 */     localFileChannel.close();
/* 140 */     localFileInputStream.close();
/* 141 */     localByteBuffer.flip();
/* 142 */     if (this.byteBuf != null) {
/* 143 */       this.byteBuf.release();
/*     */     }
/* 145 */     this.byteBuf = Unpooled.wrappedBuffer(Integer.MAX_VALUE, new ByteBuffer[] { localByteBuffer });
/* 146 */     this.size = l;
/* 147 */     this.completed = true;
/*     */   }
/*     */   
/*     */   public void delete()
/*     */   {
/* 152 */     if (this.byteBuf != null) {
/* 153 */       this.byteBuf.release();
/* 154 */       this.byteBuf = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public byte[] get()
/*     */   {
/* 160 */     if (this.byteBuf == null) {
/* 161 */       return Unpooled.EMPTY_BUFFER.array();
/*     */     }
/* 163 */     byte[] arrayOfByte = new byte[this.byteBuf.readableBytes()];
/* 164 */     this.byteBuf.getBytes(this.byteBuf.readerIndex(), arrayOfByte);
/* 165 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public String getString()
/*     */   {
/* 170 */     return getString(HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */   public String getString(Charset paramCharset)
/*     */   {
/* 175 */     if (this.byteBuf == null) {
/* 176 */       return "";
/*     */     }
/* 178 */     if (paramCharset == null) {
/* 179 */       paramCharset = HttpConstants.DEFAULT_CHARSET;
/*     */     }
/* 181 */     return this.byteBuf.toString(paramCharset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteBuf getByteBuf()
/*     */   {
/* 191 */     return this.byteBuf;
/*     */   }
/*     */   
/*     */   public ByteBuf getChunk(int paramInt) throws IOException
/*     */   {
/* 196 */     if ((this.byteBuf == null) || (paramInt == 0) || (this.byteBuf.readableBytes() == 0)) {
/* 197 */       this.chunkPosition = 0;
/* 198 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 200 */     int i = this.byteBuf.readableBytes() - this.chunkPosition;
/* 201 */     if (i == 0) {
/* 202 */       this.chunkPosition = 0;
/* 203 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 205 */     int j = paramInt;
/* 206 */     if (i < paramInt) {
/* 207 */       j = i;
/*     */     }
/* 209 */     ByteBuf localByteBuf = this.byteBuf.slice(this.chunkPosition, j).retain();
/* 210 */     this.chunkPosition += j;
/* 211 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public boolean isInMemory()
/*     */   {
/* 216 */     return true;
/*     */   }
/*     */   
/*     */   public boolean renameTo(File paramFile) throws IOException
/*     */   {
/* 221 */     if (paramFile == null) {
/* 222 */       throw new NullPointerException("dest");
/*     */     }
/* 224 */     if (this.byteBuf == null)
/*     */     {
/* 226 */       paramFile.createNewFile();
/* 227 */       this.isRenamed = true;
/* 228 */       return true;
/*     */     }
/* 230 */     int i = this.byteBuf.readableBytes();
/* 231 */     FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
/* 232 */     FileChannel localFileChannel = localFileOutputStream.getChannel();
/* 233 */     int j = 0;
/* 234 */     Object localObject; if (this.byteBuf.nioBufferCount() == 1) {
/* 235 */       localObject = this.byteBuf.nioBuffer();
/* 236 */       while (j < i) {
/* 237 */         j += localFileChannel.write((ByteBuffer)localObject);
/*     */       }
/*     */     } else {
/* 240 */       localObject = this.byteBuf.nioBuffers();
/* 241 */       while (j < i) {
/* 242 */         j = (int)(j + localFileChannel.write((ByteBuffer[])localObject));
/*     */       }
/*     */     }
/*     */     
/* 246 */     localFileChannel.force(false);
/* 247 */     localFileChannel.close();
/* 248 */     localFileOutputStream.close();
/* 249 */     this.isRenamed = true;
/* 250 */     return j == i;
/*     */   }
/*     */   
/*     */   public File getFile() throws IOException
/*     */   {
/* 255 */     throw new IOException("Not represented by a file");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\AbstractMemoryHttpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */