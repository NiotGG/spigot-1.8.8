/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.handler.codec.http.HttpConstants;
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
/*     */ public class DiskAttribute
/*     */   extends AbstractDiskHttpData
/*     */   implements Attribute
/*     */ {
/*     */   public static String baseDirectory;
/*  32 */   public static boolean deleteOnExitTemporaryFile = true;
/*     */   
/*     */ 
/*     */   public static final String prefix = "Attr_";
/*     */   
/*     */   public static final String postfix = ".att";
/*     */   
/*     */ 
/*     */   public DiskAttribute(String paramString)
/*     */   {
/*  42 */     super(paramString, HttpConstants.DEFAULT_CHARSET, 0L);
/*     */   }
/*     */   
/*     */   public DiskAttribute(String paramString1, String paramString2) throws IOException {
/*  46 */     super(paramString1, HttpConstants.DEFAULT_CHARSET, 0L);
/*  47 */     setValue(paramString2);
/*     */   }
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType()
/*     */   {
/*  52 */     return InterfaceHttpData.HttpDataType.Attribute;
/*     */   }
/*     */   
/*     */   public String getValue() throws IOException
/*     */   {
/*  57 */     byte[] arrayOfByte = get();
/*  58 */     return new String(arrayOfByte, this.charset.name());
/*     */   }
/*     */   
/*     */   public void setValue(String paramString) throws IOException
/*     */   {
/*  63 */     if (paramString == null) {
/*  64 */       throw new NullPointerException("value");
/*     */     }
/*  66 */     byte[] arrayOfByte = paramString.getBytes(this.charset.name());
/*  67 */     ByteBuf localByteBuf = Unpooled.wrappedBuffer(arrayOfByte);
/*  68 */     if (this.definedSize > 0L) {
/*  69 */       this.definedSize = localByteBuf.readableBytes();
/*     */     }
/*  71 */     setContent(localByteBuf);
/*     */   }
/*     */   
/*     */   public void addContent(ByteBuf paramByteBuf, boolean paramBoolean) throws IOException
/*     */   {
/*  76 */     int i = paramByteBuf.readableBytes();
/*  77 */     if ((this.definedSize > 0L) && (this.definedSize < this.size + i)) {
/*  78 */       this.definedSize = (this.size + i);
/*     */     }
/*  80 */     super.addContent(paramByteBuf, paramBoolean);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  84 */     return getName().hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  89 */     if (!(paramObject instanceof Attribute)) {
/*  90 */       return false;
/*     */     }
/*  92 */     Attribute localAttribute = (Attribute)paramObject;
/*  93 */     return getName().equalsIgnoreCase(localAttribute.getName());
/*     */   }
/*     */   
/*     */   public int compareTo(InterfaceHttpData paramInterfaceHttpData)
/*     */   {
/*  98 */     if (!(paramInterfaceHttpData instanceof Attribute)) {
/*  99 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + paramInterfaceHttpData.getHttpDataType());
/*     */     }
/*     */     
/* 102 */     return compareTo((Attribute)paramInterfaceHttpData);
/*     */   }
/*     */   
/*     */   public int compareTo(Attribute paramAttribute) {
/* 106 */     return getName().compareToIgnoreCase(paramAttribute.getName());
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*     */     try {
/* 112 */       return getName() + '=' + getValue();
/*     */     } catch (IOException localIOException) {}
/* 114 */     return getName() + "=IoException";
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean deleteOnExit()
/*     */   {
/* 120 */     return deleteOnExitTemporaryFile;
/*     */   }
/*     */   
/*     */   protected String getBaseDirectory()
/*     */   {
/* 125 */     return baseDirectory;
/*     */   }
/*     */   
/*     */   protected String getDiskFilename()
/*     */   {
/* 130 */     return getName() + ".att";
/*     */   }
/*     */   
/*     */   protected String getPostfix()
/*     */   {
/* 135 */     return ".att";
/*     */   }
/*     */   
/*     */   protected String getPrefix()
/*     */   {
/* 140 */     return "Attr_";
/*     */   }
/*     */   
/*     */   public Attribute copy()
/*     */   {
/* 145 */     DiskAttribute localDiskAttribute = new DiskAttribute(getName());
/* 146 */     localDiskAttribute.setCharset(getCharset());
/* 147 */     ByteBuf localByteBuf = content();
/* 148 */     if (localByteBuf != null) {
/*     */       try {
/* 150 */         localDiskAttribute.setContent(localByteBuf.copy());
/*     */       } catch (IOException localIOException) {
/* 152 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     }
/* 155 */     return localDiskAttribute;
/*     */   }
/*     */   
/*     */   public Attribute duplicate()
/*     */   {
/* 160 */     DiskAttribute localDiskAttribute = new DiskAttribute(getName());
/* 161 */     localDiskAttribute.setCharset(getCharset());
/* 162 */     ByteBuf localByteBuf = content();
/* 163 */     if (localByteBuf != null) {
/*     */       try {
/* 165 */         localDiskAttribute.setContent(localByteBuf.duplicate());
/*     */       } catch (IOException localIOException) {
/* 167 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     }
/* 170 */     return localDiskAttribute;
/*     */   }
/*     */   
/*     */   public Attribute retain(int paramInt)
/*     */   {
/* 175 */     super.retain(paramInt);
/* 176 */     return this;
/*     */   }
/*     */   
/*     */   public Attribute retain()
/*     */   {
/* 181 */     super.retain();
/* 182 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\DiskAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */