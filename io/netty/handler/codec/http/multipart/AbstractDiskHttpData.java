/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.Buffer;
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
/*     */ public abstract class AbstractDiskHttpData
/*     */   extends AbstractHttpData
/*     */ {
/*  40 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(AbstractDiskHttpData.class);
/*     */   protected File file;
/*     */   private boolean isRenamed;
/*     */   private FileChannel fileChannel;
/*     */   
/*     */   protected AbstractDiskHttpData(String paramString, Charset paramCharset, long paramLong)
/*     */   {
/*  47 */     super(paramString, paramCharset, paramLong);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract String getDiskFilename();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract String getPrefix();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract String getBaseDirectory();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract String getPostfix();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract boolean deleteOnExit();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private File tempFile()
/*     */     throws IOException
/*     */   {
/*  81 */     String str1 = getDiskFilename();
/*  82 */     String str2; if (str1 != null) {
/*  83 */       str2 = '_' + str1;
/*     */     } else {
/*  85 */       str2 = getPostfix();
/*     */     }
/*     */     File localFile;
/*  88 */     if (getBaseDirectory() == null)
/*     */     {
/*  90 */       localFile = File.createTempFile(getPrefix(), str2);
/*     */     } else {
/*  92 */       localFile = File.createTempFile(getPrefix(), str2, new File(getBaseDirectory()));
/*     */     }
/*     */     
/*  95 */     if (deleteOnExit()) {
/*  96 */       localFile.deleteOnExit();
/*     */     }
/*  98 */     return localFile;
/*     */   }
/*     */   
/*     */   public void setContent(ByteBuf paramByteBuf) throws IOException
/*     */   {
/* 103 */     if (paramByteBuf == null) {
/* 104 */       throw new NullPointerException("buffer");
/*     */     }
/*     */     try {
/* 107 */       this.size = paramByteBuf.readableBytes();
/* 108 */       if ((this.definedSize > 0L) && (this.definedSize < this.size)) {
/* 109 */         throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */       }
/* 111 */       if (this.file == null) {
/* 112 */         this.file = tempFile();
/*     */       }
/* 114 */       if (paramByteBuf.readableBytes() == 0)
/*     */       {
/* 116 */         this.file.createNewFile();
/*     */       }
/*     */       else {
/* 119 */         FileOutputStream localFileOutputStream = new FileOutputStream(this.file);
/* 120 */         FileChannel localFileChannel = localFileOutputStream.getChannel();
/* 121 */         ByteBuffer localByteBuffer = paramByteBuf.nioBuffer();
/* 122 */         int i = 0;
/* 123 */         while (i < this.size) {
/* 124 */           i += localFileChannel.write(localByteBuffer);
/*     */         }
/* 126 */         paramByteBuf.readerIndex(paramByteBuf.readerIndex() + i);
/* 127 */         localFileChannel.force(false);
/* 128 */         localFileChannel.close();
/* 129 */         localFileOutputStream.close();
/* 130 */         this.completed = true;
/*     */       }
/*     */     }
/*     */     finally {
/* 134 */       paramByteBuf.release();
/*     */     }
/*     */   }
/*     */   
/*     */   public void addContent(ByteBuf paramByteBuf, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 141 */     if (paramByteBuf != null) {
/*     */       try {
/* 143 */         int i = paramByteBuf.readableBytes();
/* 144 */         if ((this.definedSize > 0L) && (this.definedSize < this.size + i)) {
/* 145 */           throw new IOException("Out of size: " + (this.size + i) + " > " + this.definedSize);
/*     */         }
/*     */         
/* 148 */         ByteBuffer localByteBuffer = paramByteBuf.nioBufferCount() == 1 ? paramByteBuf.nioBuffer() : paramByteBuf.copy().nioBuffer();
/* 149 */         int j = 0;
/* 150 */         if (this.file == null) {
/* 151 */           this.file = tempFile();
/*     */         }
/* 153 */         if (this.fileChannel == null) {
/* 154 */           FileOutputStream localFileOutputStream2 = new FileOutputStream(this.file);
/* 155 */           this.fileChannel = localFileOutputStream2.getChannel();
/*     */         }
/* 157 */         while (j < i) {
/* 158 */           j += this.fileChannel.write(localByteBuffer);
/*     */         }
/* 160 */         this.size += i;
/* 161 */         paramByteBuf.readerIndex(paramByteBuf.readerIndex() + j);
/*     */       }
/*     */       finally
/*     */       {
/* 165 */         paramByteBuf.release();
/*     */       }
/*     */     }
/* 168 */     if (paramBoolean) {
/* 169 */       if (this.file == null) {
/* 170 */         this.file = tempFile();
/*     */       }
/* 172 */       if (this.fileChannel == null) {
/* 173 */         FileOutputStream localFileOutputStream1 = new FileOutputStream(this.file);
/* 174 */         this.fileChannel = localFileOutputStream1.getChannel();
/*     */       }
/* 176 */       this.fileChannel.force(false);
/* 177 */       this.fileChannel.close();
/* 178 */       this.fileChannel = null;
/* 179 */       this.completed = true;
/*     */     }
/* 181 */     else if (paramByteBuf == null) {
/* 182 */       throw new NullPointerException("buffer");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setContent(File paramFile)
/*     */     throws IOException
/*     */   {
/* 189 */     if (this.file != null) {
/* 190 */       delete();
/*     */     }
/* 192 */     this.file = paramFile;
/* 193 */     this.size = paramFile.length();
/* 194 */     this.isRenamed = true;
/* 195 */     this.completed = true;
/*     */   }
/*     */   
/*     */   public void setContent(InputStream paramInputStream) throws IOException
/*     */   {
/* 200 */     if (paramInputStream == null) {
/* 201 */       throw new NullPointerException("inputStream");
/*     */     }
/* 203 */     if (this.file != null) {
/* 204 */       delete();
/*     */     }
/* 206 */     this.file = tempFile();
/* 207 */     FileOutputStream localFileOutputStream = new FileOutputStream(this.file);
/* 208 */     FileChannel localFileChannel = localFileOutputStream.getChannel();
/* 209 */     byte[] arrayOfByte = new byte['䀀'];
/* 210 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(arrayOfByte);
/* 211 */     int i = paramInputStream.read(arrayOfByte);
/* 212 */     int j = 0;
/* 213 */     while (i > 0) {
/* 214 */       localByteBuffer.position(i).flip();
/* 215 */       j += localFileChannel.write(localByteBuffer);
/* 216 */       i = paramInputStream.read(arrayOfByte);
/*     */     }
/* 218 */     localFileChannel.force(false);
/* 219 */     localFileChannel.close();
/* 220 */     this.size = j;
/* 221 */     if ((this.definedSize > 0L) && (this.definedSize < this.size)) {
/* 222 */       this.file.delete();
/* 223 */       this.file = null;
/* 224 */       throw new IOException("Out of size: " + this.size + " > " + this.definedSize);
/*     */     }
/* 226 */     this.isRenamed = true;
/* 227 */     this.completed = true;
/*     */   }
/*     */   
/*     */   public void delete()
/*     */   {
/* 232 */     if (this.fileChannel != null) {
/*     */       try {
/* 234 */         this.fileChannel.force(false);
/* 235 */         this.fileChannel.close();
/*     */       } catch (IOException localIOException) {
/* 237 */         logger.warn("Failed to close a file.", localIOException);
/*     */       }
/* 239 */       this.fileChannel = null;
/*     */     }
/* 241 */     if (!this.isRenamed) {
/* 242 */       if ((this.file != null) && (this.file.exists())) {
/* 243 */         this.file.delete();
/*     */       }
/* 245 */       this.file = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public byte[] get() throws IOException
/*     */   {
/* 251 */     if (this.file == null) {
/* 252 */       return EmptyArrays.EMPTY_BYTES;
/*     */     }
/* 254 */     return readFrom(this.file);
/*     */   }
/*     */   
/*     */   public ByteBuf getByteBuf() throws IOException
/*     */   {
/* 259 */     if (this.file == null) {
/* 260 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 262 */     byte[] arrayOfByte = readFrom(this.file);
/* 263 */     return Unpooled.wrappedBuffer(arrayOfByte);
/*     */   }
/*     */   
/*     */   public ByteBuf getChunk(int paramInt) throws IOException
/*     */   {
/* 268 */     if ((this.file == null) || (paramInt == 0)) {
/* 269 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 271 */     if (this.fileChannel == null) {
/* 272 */       FileInputStream localFileInputStream = new FileInputStream(this.file);
/* 273 */       this.fileChannel = localFileInputStream.getChannel();
/*     */     }
/* 275 */     int i = 0;
/* 276 */     ByteBuffer localByteBuffer = ByteBuffer.allocate(paramInt);
/* 277 */     while (i < paramInt) {
/* 278 */       int j = this.fileChannel.read(localByteBuffer);
/* 279 */       if (j == -1) {
/* 280 */         this.fileChannel.close();
/* 281 */         this.fileChannel = null;
/* 282 */         break;
/*     */       }
/* 284 */       i += j;
/*     */     }
/*     */     
/* 287 */     if (i == 0) {
/* 288 */       return Unpooled.EMPTY_BUFFER;
/*     */     }
/* 290 */     localByteBuffer.flip();
/* 291 */     ByteBuf localByteBuf = Unpooled.wrappedBuffer(localByteBuffer);
/* 292 */     localByteBuf.readerIndex(0);
/* 293 */     localByteBuf.writerIndex(i);
/* 294 */     return localByteBuf;
/*     */   }
/*     */   
/*     */   public String getString() throws IOException
/*     */   {
/* 299 */     return getString(HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */   public String getString(Charset paramCharset) throws IOException
/*     */   {
/* 304 */     if (this.file == null) {
/* 305 */       return "";
/*     */     }
/* 307 */     if (paramCharset == null) {
/* 308 */       arrayOfByte = readFrom(this.file);
/* 309 */       return new String(arrayOfByte, HttpConstants.DEFAULT_CHARSET.name());
/*     */     }
/* 311 */     byte[] arrayOfByte = readFrom(this.file);
/* 312 */     return new String(arrayOfByte, paramCharset.name());
/*     */   }
/*     */   
/*     */   public boolean isInMemory()
/*     */   {
/* 317 */     return false;
/*     */   }
/*     */   
/*     */   public boolean renameTo(File paramFile) throws IOException
/*     */   {
/* 322 */     if (paramFile == null) {
/* 323 */       throw new NullPointerException("dest");
/*     */     }
/* 325 */     if (this.file == null) {
/* 326 */       throw new IOException("No file defined so cannot be renamed");
/*     */     }
/* 328 */     if (!this.file.renameTo(paramFile))
/*     */     {
/* 330 */       FileInputStream localFileInputStream = new FileInputStream(this.file);
/* 331 */       FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
/* 332 */       FileChannel localFileChannel1 = localFileInputStream.getChannel();
/* 333 */       FileChannel localFileChannel2 = localFileOutputStream.getChannel();
/* 334 */       int i = 8196;
/* 335 */       long l = 0L;
/* 336 */       while (l < this.size) {
/* 337 */         if (i < this.size - l) {
/* 338 */           i = (int)(this.size - l);
/*     */         }
/* 340 */         l += localFileChannel1.transferTo(l, i, localFileChannel2);
/*     */       }
/* 342 */       localFileChannel1.close();
/* 343 */       localFileChannel2.close();
/* 344 */       if (l == this.size) {
/* 345 */         this.file.delete();
/* 346 */         this.file = paramFile;
/* 347 */         this.isRenamed = true;
/* 348 */         return true;
/*     */       }
/* 350 */       paramFile.delete();
/* 351 */       return false;
/*     */     }
/*     */     
/* 354 */     this.file = paramFile;
/* 355 */     this.isRenamed = true;
/* 356 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static byte[] readFrom(File paramFile)
/*     */     throws IOException
/*     */   {
/* 364 */     long l = paramFile.length();
/* 365 */     if (l > 2147483647L) {
/* 366 */       throw new IllegalArgumentException("File too big to be loaded in memory");
/*     */     }
/*     */     
/* 369 */     FileInputStream localFileInputStream = new FileInputStream(paramFile);
/* 370 */     FileChannel localFileChannel = localFileInputStream.getChannel();
/* 371 */     byte[] arrayOfByte = new byte[(int)l];
/* 372 */     ByteBuffer localByteBuffer = ByteBuffer.wrap(arrayOfByte);
/* 373 */     int i = 0;
/* 374 */     while (i < l) {
/* 375 */       i += localFileChannel.read(localByteBuffer);
/*     */     }
/* 377 */     localFileChannel.close();
/* 378 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public File getFile() throws IOException
/*     */   {
/* 383 */     return this.file;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\AbstractDiskHttpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */