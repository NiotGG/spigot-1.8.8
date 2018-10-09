/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelException;
/*     */ import java.io.File;
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
/*     */ public class DiskFileUpload
/*     */   extends AbstractDiskHttpData
/*     */   implements FileUpload
/*     */ {
/*     */   public static String baseDirectory;
/*  32 */   public static boolean deleteOnExitTemporaryFile = true;
/*     */   
/*     */   public static final String prefix = "FUp_";
/*     */   
/*     */   public static final String postfix = ".tmp";
/*     */   
/*     */   private String filename;
/*     */   
/*     */   private String contentType;
/*     */   
/*     */   private String contentTransferEncoding;
/*     */   
/*     */   public DiskFileUpload(String paramString1, String paramString2, String paramString3, String paramString4, Charset paramCharset, long paramLong)
/*     */   {
/*  46 */     super(paramString1, paramCharset, paramLong);
/*  47 */     setFilename(paramString2);
/*  48 */     setContentType(paramString3);
/*  49 */     setContentTransferEncoding(paramString4);
/*     */   }
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType()
/*     */   {
/*  54 */     return InterfaceHttpData.HttpDataType.FileUpload;
/*     */   }
/*     */   
/*     */   public String getFilename()
/*     */   {
/*  59 */     return this.filename;
/*     */   }
/*     */   
/*     */   public void setFilename(String paramString)
/*     */   {
/*  64 */     if (paramString == null) {
/*  65 */       throw new NullPointerException("filename");
/*     */     }
/*  67 */     this.filename = paramString;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  72 */     return getName().hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  77 */     if (!(paramObject instanceof Attribute)) {
/*  78 */       return false;
/*     */     }
/*  80 */     Attribute localAttribute = (Attribute)paramObject;
/*  81 */     return getName().equalsIgnoreCase(localAttribute.getName());
/*     */   }
/*     */   
/*     */   public int compareTo(InterfaceHttpData paramInterfaceHttpData)
/*     */   {
/*  86 */     if (!(paramInterfaceHttpData instanceof FileUpload)) {
/*  87 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + paramInterfaceHttpData.getHttpDataType());
/*     */     }
/*     */     
/*  90 */     return compareTo((FileUpload)paramInterfaceHttpData);
/*     */   }
/*     */   
/*     */   public int compareTo(FileUpload paramFileUpload)
/*     */   {
/*  95 */     int i = getName().compareToIgnoreCase(paramFileUpload.getName());
/*  96 */     if (i != 0) {
/*  97 */       return i;
/*     */     }
/*     */     
/* 100 */     return i;
/*     */   }
/*     */   
/*     */   public void setContentType(String paramString)
/*     */   {
/* 105 */     if (paramString == null) {
/* 106 */       throw new NullPointerException("contentType");
/*     */     }
/* 108 */     this.contentType = paramString;
/*     */   }
/*     */   
/*     */   public String getContentType()
/*     */   {
/* 113 */     return this.contentType;
/*     */   }
/*     */   
/*     */   public String getContentTransferEncoding()
/*     */   {
/* 118 */     return this.contentTransferEncoding;
/*     */   }
/*     */   
/*     */   public void setContentTransferEncoding(String paramString)
/*     */   {
/* 123 */     this.contentTransferEncoding = paramString;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 128 */     return "Content-Disposition: form-data; name=\"" + getName() + "\"; " + "filename" + "=\"" + this.filename + "\"\r\n" + "Content-Type" + ": " + this.contentType + (this.charset != null ? "; charset=" + this.charset + "\r\n" : "\r\n") + "Content-Length" + ": " + length() + "\r\n" + "Completed: " + isCompleted() + "\r\nIsInMemory: " + isInMemory() + "\r\nRealFile: " + (this.file != null ? this.file.getAbsolutePath() : "null") + " DefaultDeleteAfter: " + deleteOnExitTemporaryFile;
/*     */   }
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
/*     */   protected boolean deleteOnExit()
/*     */   {
/* 142 */     return deleteOnExitTemporaryFile;
/*     */   }
/*     */   
/*     */   protected String getBaseDirectory()
/*     */   {
/* 147 */     return baseDirectory;
/*     */   }
/*     */   
/*     */   protected String getDiskFilename()
/*     */   {
/* 152 */     File localFile = new File(this.filename);
/* 153 */     return localFile.getName();
/*     */   }
/*     */   
/*     */   protected String getPostfix()
/*     */   {
/* 158 */     return ".tmp";
/*     */   }
/*     */   
/*     */   protected String getPrefix()
/*     */   {
/* 163 */     return "FUp_";
/*     */   }
/*     */   
/*     */   public FileUpload copy()
/*     */   {
/* 168 */     DiskFileUpload localDiskFileUpload = new DiskFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
/*     */     
/* 170 */     ByteBuf localByteBuf = content();
/* 171 */     if (localByteBuf != null) {
/*     */       try {
/* 173 */         localDiskFileUpload.setContent(localByteBuf.copy());
/*     */       } catch (IOException localIOException) {
/* 175 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     }
/* 178 */     return localDiskFileUpload;
/*     */   }
/*     */   
/*     */   public FileUpload duplicate()
/*     */   {
/* 183 */     DiskFileUpload localDiskFileUpload = new DiskFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
/*     */     
/* 185 */     ByteBuf localByteBuf = content();
/* 186 */     if (localByteBuf != null) {
/*     */       try {
/* 188 */         localDiskFileUpload.setContent(localByteBuf.duplicate());
/*     */       } catch (IOException localIOException) {
/* 190 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     }
/* 193 */     return localDiskFileUpload;
/*     */   }
/*     */   
/*     */   public FileUpload retain(int paramInt)
/*     */   {
/* 198 */     super.retain(paramInt);
/* 199 */     return this;
/*     */   }
/*     */   
/*     */   public FileUpload retain()
/*     */   {
/* 204 */     super.retain();
/* 205 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\DiskFileUpload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */