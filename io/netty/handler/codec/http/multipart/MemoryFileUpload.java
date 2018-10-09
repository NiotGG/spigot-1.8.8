/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelException;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ public class MemoryFileUpload
/*     */   extends AbstractMemoryHttpData
/*     */   implements FileUpload
/*     */ {
/*     */   private String filename;
/*     */   private String contentType;
/*     */   private String contentTransferEncoding;
/*     */   
/*     */   public MemoryFileUpload(String paramString1, String paramString2, String paramString3, String paramString4, Charset paramCharset, long paramLong)
/*     */   {
/*  40 */     super(paramString1, paramCharset, paramLong);
/*  41 */     setFilename(paramString2);
/*  42 */     setContentType(paramString3);
/*  43 */     setContentTransferEncoding(paramString4);
/*     */   }
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType()
/*     */   {
/*  48 */     return InterfaceHttpData.HttpDataType.FileUpload;
/*     */   }
/*     */   
/*     */   public String getFilename()
/*     */   {
/*  53 */     return this.filename;
/*     */   }
/*     */   
/*     */   public void setFilename(String paramString)
/*     */   {
/*  58 */     if (paramString == null) {
/*  59 */       throw new NullPointerException("filename");
/*     */     }
/*  61 */     this.filename = paramString;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  66 */     return getName().hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  71 */     if (!(paramObject instanceof Attribute)) {
/*  72 */       return false;
/*     */     }
/*  74 */     Attribute localAttribute = (Attribute)paramObject;
/*  75 */     return getName().equalsIgnoreCase(localAttribute.getName());
/*     */   }
/*     */   
/*     */   public int compareTo(InterfaceHttpData paramInterfaceHttpData)
/*     */   {
/*  80 */     if (!(paramInterfaceHttpData instanceof FileUpload)) {
/*  81 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + paramInterfaceHttpData.getHttpDataType());
/*     */     }
/*     */     
/*  84 */     return compareTo((FileUpload)paramInterfaceHttpData);
/*     */   }
/*     */   
/*     */   public int compareTo(FileUpload paramFileUpload)
/*     */   {
/*  89 */     int i = getName().compareToIgnoreCase(paramFileUpload.getName());
/*  90 */     if (i != 0) {
/*  91 */       return i;
/*     */     }
/*     */     
/*  94 */     return i;
/*     */   }
/*     */   
/*     */   public void setContentType(String paramString)
/*     */   {
/*  99 */     if (paramString == null) {
/* 100 */       throw new NullPointerException("contentType");
/*     */     }
/* 102 */     this.contentType = paramString;
/*     */   }
/*     */   
/*     */   public String getContentType()
/*     */   {
/* 107 */     return this.contentType;
/*     */   }
/*     */   
/*     */   public String getContentTransferEncoding()
/*     */   {
/* 112 */     return this.contentTransferEncoding;
/*     */   }
/*     */   
/*     */   public void setContentTransferEncoding(String paramString)
/*     */   {
/* 117 */     this.contentTransferEncoding = paramString;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 122 */     return "Content-Disposition: form-data; name=\"" + getName() + "\"; " + "filename" + "=\"" + this.filename + "\"\r\n" + "Content-Type" + ": " + this.contentType + (this.charset != null ? "; charset=" + this.charset + "\r\n" : "\r\n") + "Content-Length" + ": " + length() + "\r\n" + "Completed: " + isCompleted() + "\r\nIsInMemory: " + isInMemory();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FileUpload copy()
/*     */   {
/* 134 */     MemoryFileUpload localMemoryFileUpload = new MemoryFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
/*     */     
/* 136 */     ByteBuf localByteBuf = content();
/* 137 */     if (localByteBuf != null) {
/*     */       try {
/* 139 */         localMemoryFileUpload.setContent(localByteBuf.copy());
/* 140 */         return localMemoryFileUpload;
/*     */       } catch (IOException localIOException) {
/* 142 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     }
/* 145 */     return localMemoryFileUpload;
/*     */   }
/*     */   
/*     */   public FileUpload duplicate()
/*     */   {
/* 150 */     MemoryFileUpload localMemoryFileUpload = new MemoryFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
/*     */     
/* 152 */     ByteBuf localByteBuf = content();
/* 153 */     if (localByteBuf != null) {
/*     */       try {
/* 155 */         localMemoryFileUpload.setContent(localByteBuf.duplicate());
/* 156 */         return localMemoryFileUpload;
/*     */       } catch (IOException localIOException) {
/* 158 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     }
/* 161 */     return localMemoryFileUpload;
/*     */   }
/*     */   
/*     */   public FileUpload retain() {
/* 165 */     super.retain();
/* 166 */     return this;
/*     */   }
/*     */   
/*     */   public FileUpload retain(int paramInt)
/*     */   {
/* 171 */     super.retain(paramInt);
/* 172 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\MemoryFileUpload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */