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
/*     */ public class MixedAttribute
/*     */   implements Attribute
/*     */ {
/*     */   private Attribute attribute;
/*     */   private final long limitSize;
/*     */   
/*     */   public MixedAttribute(String paramString, long paramLong)
/*     */   {
/*  34 */     this.limitSize = paramLong;
/*  35 */     this.attribute = new MemoryAttribute(paramString);
/*     */   }
/*     */   
/*     */   public MixedAttribute(String paramString1, String paramString2, long paramLong) {
/*  39 */     this.limitSize = paramLong;
/*  40 */     if (paramString2.length() > this.limitSize) {
/*     */       try {
/*  42 */         this.attribute = new DiskAttribute(paramString1, paramString2);
/*     */       }
/*     */       catch (IOException localIOException1) {
/*     */         try {
/*  46 */           this.attribute = new MemoryAttribute(paramString1, paramString2);
/*     */         } catch (IOException localIOException3) {
/*  48 */           throw new IllegalArgumentException(localIOException1);
/*     */         }
/*     */       }
/*     */     } else {
/*     */       try {
/*  53 */         this.attribute = new MemoryAttribute(paramString1, paramString2);
/*     */       } catch (IOException localIOException2) {
/*  55 */         throw new IllegalArgumentException(localIOException2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void addContent(ByteBuf paramByteBuf, boolean paramBoolean) throws IOException
/*     */   {
/*  62 */     if (((this.attribute instanceof MemoryAttribute)) && 
/*  63 */       (this.attribute.length() + paramByteBuf.readableBytes() > this.limitSize)) {
/*  64 */       DiskAttribute localDiskAttribute = new DiskAttribute(this.attribute.getName());
/*     */       
/*  66 */       if (((MemoryAttribute)this.attribute).getByteBuf() != null) {
/*  67 */         localDiskAttribute.addContent(((MemoryAttribute)this.attribute).getByteBuf(), false);
/*     */       }
/*     */       
/*  70 */       this.attribute = localDiskAttribute;
/*     */     }
/*     */     
/*  73 */     this.attribute.addContent(paramByteBuf, paramBoolean);
/*     */   }
/*     */   
/*     */   public void delete()
/*     */   {
/*  78 */     this.attribute.delete();
/*     */   }
/*     */   
/*     */   public byte[] get() throws IOException
/*     */   {
/*  83 */     return this.attribute.get();
/*     */   }
/*     */   
/*     */   public ByteBuf getByteBuf() throws IOException
/*     */   {
/*  88 */     return this.attribute.getByteBuf();
/*     */   }
/*     */   
/*     */   public Charset getCharset()
/*     */   {
/*  93 */     return this.attribute.getCharset();
/*     */   }
/*     */   
/*     */   public String getString() throws IOException
/*     */   {
/*  98 */     return this.attribute.getString();
/*     */   }
/*     */   
/*     */   public String getString(Charset paramCharset) throws IOException
/*     */   {
/* 103 */     return this.attribute.getString(paramCharset);
/*     */   }
/*     */   
/*     */   public boolean isCompleted()
/*     */   {
/* 108 */     return this.attribute.isCompleted();
/*     */   }
/*     */   
/*     */   public boolean isInMemory()
/*     */   {
/* 113 */     return this.attribute.isInMemory();
/*     */   }
/*     */   
/*     */   public long length()
/*     */   {
/* 118 */     return this.attribute.length();
/*     */   }
/*     */   
/*     */   public boolean renameTo(File paramFile) throws IOException
/*     */   {
/* 123 */     return this.attribute.renameTo(paramFile);
/*     */   }
/*     */   
/*     */   public void setCharset(Charset paramCharset)
/*     */   {
/* 128 */     this.attribute.setCharset(paramCharset);
/*     */   }
/*     */   
/*     */   public void setContent(ByteBuf paramByteBuf) throws IOException
/*     */   {
/* 133 */     if ((paramByteBuf.readableBytes() > this.limitSize) && 
/* 134 */       ((this.attribute instanceof MemoryAttribute)))
/*     */     {
/* 136 */       this.attribute = new DiskAttribute(this.attribute.getName());
/*     */     }
/*     */     
/* 139 */     this.attribute.setContent(paramByteBuf);
/*     */   }
/*     */   
/*     */   public void setContent(File paramFile) throws IOException
/*     */   {
/* 144 */     if ((paramFile.length() > this.limitSize) && 
/* 145 */       ((this.attribute instanceof MemoryAttribute)))
/*     */     {
/* 147 */       this.attribute = new DiskAttribute(this.attribute.getName());
/*     */     }
/*     */     
/* 150 */     this.attribute.setContent(paramFile);
/*     */   }
/*     */   
/*     */   public void setContent(InputStream paramInputStream) throws IOException
/*     */   {
/* 155 */     if ((this.attribute instanceof MemoryAttribute))
/*     */     {
/* 157 */       this.attribute = new DiskAttribute(this.attribute.getName());
/*     */     }
/* 159 */     this.attribute.setContent(paramInputStream);
/*     */   }
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType()
/*     */   {
/* 164 */     return this.attribute.getHttpDataType();
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 169 */     return this.attribute.getName();
/*     */   }
/*     */   
/*     */   public int compareTo(InterfaceHttpData paramInterfaceHttpData)
/*     */   {
/* 174 */     return this.attribute.compareTo(paramInterfaceHttpData);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 179 */     return "Mixed: " + this.attribute.toString();
/*     */   }
/*     */   
/*     */   public String getValue() throws IOException
/*     */   {
/* 184 */     return this.attribute.getValue();
/*     */   }
/*     */   
/*     */   public void setValue(String paramString) throws IOException
/*     */   {
/* 189 */     this.attribute.setValue(paramString);
/*     */   }
/*     */   
/*     */   public ByteBuf getChunk(int paramInt) throws IOException
/*     */   {
/* 194 */     return this.attribute.getChunk(paramInt);
/*     */   }
/*     */   
/*     */   public File getFile() throws IOException
/*     */   {
/* 199 */     return this.attribute.getFile();
/*     */   }
/*     */   
/*     */   public Attribute copy()
/*     */   {
/* 204 */     return this.attribute.copy();
/*     */   }
/*     */   
/*     */   public Attribute duplicate()
/*     */   {
/* 209 */     return this.attribute.duplicate();
/*     */   }
/*     */   
/*     */   public ByteBuf content()
/*     */   {
/* 214 */     return this.attribute.content();
/*     */   }
/*     */   
/*     */   public int refCnt()
/*     */   {
/* 219 */     return this.attribute.refCnt();
/*     */   }
/*     */   
/*     */   public Attribute retain()
/*     */   {
/* 224 */     this.attribute.retain();
/* 225 */     return this;
/*     */   }
/*     */   
/*     */   public Attribute retain(int paramInt)
/*     */   {
/* 230 */     this.attribute.retain(paramInt);
/* 231 */     return this;
/*     */   }
/*     */   
/*     */   public boolean release()
/*     */   {
/* 236 */     return this.attribute.release();
/*     */   }
/*     */   
/*     */   public boolean release(int paramInt)
/*     */   {
/* 241 */     return this.attribute.release(paramInt);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\MixedAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */