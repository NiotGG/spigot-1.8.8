/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixedFileUpload
/*     */   implements FileUpload
/*     */ {
/*     */   private FileUpload fileUpload;
/*     */   private final long limitSize;
/*     */   private final long definedSize;
/*     */   
/*     */   public MixedFileUpload(String paramString1, String paramString2, String paramString3, String paramString4, Charset paramCharset, long paramLong1, long paramLong2)
/*     */   {
/*  39 */     this.limitSize = paramLong2;
/*  40 */     if (paramLong1 > this.limitSize) {
/*  41 */       this.fileUpload = new DiskFileUpload(paramString1, paramString2, paramString3, paramString4, paramCharset, paramLong1);
/*     */     }
/*     */     else {
/*  44 */       this.fileUpload = new MemoryFileUpload(paramString1, paramString2, paramString3, paramString4, paramCharset, paramLong1);
/*     */     }
/*     */     
/*  47 */     this.definedSize = paramLong1;
/*     */   }
/*     */   
/*     */   public void addContent(ByteBuf paramByteBuf, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/*  53 */     if (((this.fileUpload instanceof MemoryFileUpload)) && 
/*  54 */       (this.fileUpload.length() + paramByteBuf.readableBytes() > this.limitSize)) {
/*  55 */       DiskFileUpload localDiskFileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  61 */       ByteBuf localByteBuf = this.fileUpload.getByteBuf();
/*  62 */       if ((localByteBuf != null) && (localByteBuf.isReadable())) {
/*  63 */         localDiskFileUpload.addContent(localByteBuf.retain(), false);
/*     */       }
/*     */       
/*  66 */       this.fileUpload.release();
/*     */       
/*  68 */       this.fileUpload = localDiskFileUpload;
/*     */     }
/*     */     
/*  71 */     this.fileUpload.addContent(paramByteBuf, paramBoolean);
/*     */   }
/*     */   
/*     */   public void delete()
/*     */   {
/*  76 */     this.fileUpload.delete();
/*     */   }
/*     */   
/*     */   public byte[] get() throws IOException
/*     */   {
/*  81 */     return this.fileUpload.get();
/*     */   }
/*     */   
/*     */   public ByteBuf getByteBuf() throws IOException
/*     */   {
/*  86 */     return this.fileUpload.getByteBuf();
/*     */   }
/*     */   
/*     */   public Charset getCharset()
/*     */   {
/*  91 */     return this.fileUpload.getCharset();
/*     */   }
/*     */   
/*     */   public String getContentType()
/*     */   {
/*  96 */     return this.fileUpload.getContentType();
/*     */   }
/*     */   
/*     */   public String getContentTransferEncoding()
/*     */   {
/* 101 */     return this.fileUpload.getContentTransferEncoding();
/*     */   }
/*     */   
/*     */   public String getFilename()
/*     */   {
/* 106 */     return this.fileUpload.getFilename();
/*     */   }
/*     */   
/*     */   public String getString() throws IOException
/*     */   {
/* 111 */     return this.fileUpload.getString();
/*     */   }
/*     */   
/*     */   public String getString(Charset paramCharset) throws IOException
/*     */   {
/* 116 */     return this.fileUpload.getString(paramCharset);
/*     */   }
/*     */   
/*     */   public boolean isCompleted()
/*     */   {
/* 121 */     return this.fileUpload.isCompleted();
/*     */   }
/*     */   
/*     */   public boolean isInMemory()
/*     */   {
/* 126 */     return this.fileUpload.isInMemory();
/*     */   }
/*     */   
/*     */   public long length()
/*     */   {
/* 131 */     return this.fileUpload.length();
/*     */   }
/*     */   
/*     */   public boolean renameTo(File paramFile) throws IOException
/*     */   {
/* 136 */     return this.fileUpload.renameTo(paramFile);
/*     */   }
/*     */   
/*     */   public void setCharset(Charset paramCharset)
/*     */   {
/* 141 */     this.fileUpload.setCharset(paramCharset);
/*     */   }
/*     */   
/*     */   public void setContent(ByteBuf paramByteBuf) throws IOException
/*     */   {
/* 146 */     if ((paramByteBuf.readableBytes() > this.limitSize) && 
/* 147 */       ((this.fileUpload instanceof MemoryFileUpload))) {
/* 148 */       FileUpload localFileUpload = this.fileUpload;
/*     */       
/* 150 */       this.fileUpload = new DiskFileUpload(localFileUpload.getName(), localFileUpload.getFilename(), localFileUpload.getContentType(), localFileUpload.getContentTransferEncoding(), localFileUpload.getCharset(), this.definedSize);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 157 */       localFileUpload.release();
/*     */     }
/*     */     
/* 160 */     this.fileUpload.setContent(paramByteBuf);
/*     */   }
/*     */   
/*     */   public void setContent(File paramFile) throws IOException
/*     */   {
/* 165 */     if ((paramFile.length() > this.limitSize) && 
/* 166 */       ((this.fileUpload instanceof MemoryFileUpload))) {
/* 167 */       FileUpload localFileUpload = this.fileUpload;
/*     */       
/*     */ 
/* 170 */       this.fileUpload = new DiskFileUpload(localFileUpload.getName(), localFileUpload.getFilename(), localFileUpload.getContentType(), localFileUpload.getContentTransferEncoding(), localFileUpload.getCharset(), this.definedSize);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 177 */       localFileUpload.release();
/*     */     }
/*     */     
/* 180 */     this.fileUpload.setContent(paramFile);
/*     */   }
/*     */   
/*     */   public void setContent(InputStream paramInputStream) throws IOException
/*     */   {
/* 185 */     if ((this.fileUpload instanceof MemoryFileUpload)) {
/* 186 */       FileUpload localFileUpload = this.fileUpload;
/*     */       
/*     */ 
/* 189 */       this.fileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 196 */       localFileUpload.release();
/*     */     }
/* 198 */     this.fileUpload.setContent(paramInputStream);
/*     */   }
/*     */   
/*     */   public void setContentType(String paramString)
/*     */   {
/* 203 */     this.fileUpload.setContentType(paramString);
/*     */   }
/*     */   
/*     */   public void setContentTransferEncoding(String paramString)
/*     */   {
/* 208 */     this.fileUpload.setContentTransferEncoding(paramString);
/*     */   }
/*     */   
/*     */   public void setFilename(String paramString)
/*     */   {
/* 213 */     this.fileUpload.setFilename(paramString);
/*     */   }
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType()
/*     */   {
/* 218 */     return this.fileUpload.getHttpDataType();
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 223 */     return this.fileUpload.getName();
/*     */   }
/*     */   
/*     */   public int compareTo(InterfaceHttpData paramInterfaceHttpData)
/*     */   {
/* 228 */     return this.fileUpload.compareTo(paramInterfaceHttpData);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 233 */     return "Mixed: " + this.fileUpload.toString();
/*     */   }
/*     */   
/*     */   public ByteBuf getChunk(int paramInt) throws IOException
/*     */   {
/* 238 */     return this.fileUpload.getChunk(paramInt);
/*     */   }
/*     */   
/*     */   public File getFile() throws IOException
/*     */   {
/* 243 */     return this.fileUpload.getFile();
/*     */   }
/*     */   
/*     */   public FileUpload copy()
/*     */   {
/* 248 */     return this.fileUpload.copy();
/*     */   }
/*     */   
/*     */   public FileUpload duplicate()
/*     */   {
/* 253 */     return this.fileUpload.duplicate();
/*     */   }
/*     */   
/*     */   public ByteBuf content()
/*     */   {
/* 258 */     return this.fileUpload.content();
/*     */   }
/*     */   
/*     */   public int refCnt()
/*     */   {
/* 263 */     return this.fileUpload.refCnt();
/*     */   }
/*     */   
/*     */   public FileUpload retain()
/*     */   {
/* 268 */     this.fileUpload.retain();
/* 269 */     return this;
/*     */   }
/*     */   
/*     */   public FileUpload retain(int paramInt)
/*     */   {
/* 274 */     this.fileUpload.retain(paramInt);
/* 275 */     return this;
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/* 280 */     return this.fileUpload.release();
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/* 285 */     return this.fileUpload.release(paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\MixedFileUpload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */