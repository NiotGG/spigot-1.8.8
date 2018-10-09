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
/*     */ public class MemoryAttribute
/*     */   extends AbstractMemoryHttpData
/*     */   implements Attribute
/*     */ {
/*     */   public MemoryAttribute(String paramString)
/*     */   {
/*  32 */     super(paramString, HttpConstants.DEFAULT_CHARSET, 0L);
/*     */   }
/*     */   
/*     */   public MemoryAttribute(String paramString1, String paramString2) throws IOException {
/*  36 */     super(paramString1, HttpConstants.DEFAULT_CHARSET, 0L);
/*  37 */     setValue(paramString2);
/*     */   }
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType()
/*     */   {
/*  42 */     return InterfaceHttpData.HttpDataType.Attribute;
/*     */   }
/*     */   
/*     */   public String getValue()
/*     */   {
/*  47 */     return getByteBuf().toString(this.charset);
/*     */   }
/*     */   
/*     */   public void setValue(String paramString) throws IOException
/*     */   {
/*  52 */     if (paramString == null) {
/*  53 */       throw new NullPointerException("value");
/*     */     }
/*  55 */     byte[] arrayOfByte = paramString.getBytes(this.charset.name());
/*  56 */     ByteBuf localByteBuf = Unpooled.wrappedBuffer(arrayOfByte);
/*  57 */     if (this.definedSize > 0L) {
/*  58 */       this.definedSize = localByteBuf.readableBytes();
/*     */     }
/*  60 */     setContent(localByteBuf);
/*     */   }
/*     */   
/*     */   public void addContent(ByteBuf paramByteBuf, boolean paramBoolean) throws IOException
/*     */   {
/*  65 */     int i = paramByteBuf.readableBytes();
/*  66 */     if ((this.definedSize > 0L) && (this.definedSize < this.size + i)) {
/*  67 */       this.definedSize = (this.size + i);
/*     */     }
/*  69 */     super.addContent(paramByteBuf, paramBoolean);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  74 */     return getName().hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  79 */     if (!(paramObject instanceof Attribute)) {
/*  80 */       return false;
/*     */     }
/*  82 */     Attribute localAttribute = (Attribute)paramObject;
/*  83 */     return getName().equalsIgnoreCase(localAttribute.getName());
/*     */   }
/*     */   
/*     */   public int compareTo(InterfaceHttpData paramInterfaceHttpData)
/*     */   {
/*  88 */     if (!(paramInterfaceHttpData instanceof Attribute)) {
/*  89 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + paramInterfaceHttpData.getHttpDataType());
/*     */     }
/*     */     
/*  92 */     return compareTo((Attribute)paramInterfaceHttpData);
/*     */   }
/*     */   
/*     */   public int compareTo(Attribute paramAttribute) {
/*  96 */     return getName().compareToIgnoreCase(paramAttribute.getName());
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 101 */     return getName() + '=' + getValue();
/*     */   }
/*     */   
/*     */   public Attribute copy()
/*     */   {
/* 106 */     MemoryAttribute localMemoryAttribute = new MemoryAttribute(getName());
/* 107 */     localMemoryAttribute.setCharset(getCharset());
/* 108 */     ByteBuf localByteBuf = content();
/* 109 */     if (localByteBuf != null) {
/*     */       try {
/* 111 */         localMemoryAttribute.setContent(localByteBuf.copy());
/*     */       } catch (IOException localIOException) {
/* 113 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     }
/* 116 */     return localMemoryAttribute;
/*     */   }
/*     */   
/*     */   public Attribute duplicate()
/*     */   {
/* 121 */     MemoryAttribute localMemoryAttribute = new MemoryAttribute(getName());
/* 122 */     localMemoryAttribute.setCharset(getCharset());
/* 123 */     ByteBuf localByteBuf = content();
/* 124 */     if (localByteBuf != null) {
/*     */       try {
/* 126 */         localMemoryAttribute.setContent(localByteBuf.duplicate());
/*     */       } catch (IOException localIOException) {
/* 128 */         throw new ChannelException(localIOException);
/*     */       }
/*     */     }
/* 131 */     return localMemoryAttribute;
/*     */   }
/*     */   
/*     */   public Attribute retain()
/*     */   {
/* 136 */     super.retain();
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public Attribute retain(int paramInt)
/*     */   {
/* 142 */     super.retain(paramInt);
/* 143 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\MemoryAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */