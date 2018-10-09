/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.CompositeByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.AbstractReferenceCounted;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ final class InternalAttribute
/*     */   extends AbstractReferenceCounted
/*     */   implements InterfaceHttpData
/*     */ {
/*  31 */   private final List<ByteBuf> value = new ArrayList();
/*     */   private final Charset charset;
/*     */   private int size;
/*     */   
/*     */   InternalAttribute(Charset paramCharset) {
/*  36 */     this.charset = paramCharset;
/*     */   }
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType()
/*     */   {
/*  41 */     return InterfaceHttpData.HttpDataType.InternalAttribute;
/*     */   }
/*     */   
/*     */   public void addValue(String paramString) {
/*  45 */     if (paramString == null) {
/*  46 */       throw new NullPointerException("value");
/*     */     }
/*  48 */     ByteBuf localByteBuf = Unpooled.copiedBuffer(paramString, this.charset);
/*  49 */     this.value.add(localByteBuf);
/*  50 */     this.size += localByteBuf.readableBytes();
/*     */   }
/*     */   
/*     */   public void addValue(String paramString, int paramInt) {
/*  54 */     if (paramString == null) {
/*  55 */       throw new NullPointerException("value");
/*     */     }
/*  57 */     ByteBuf localByteBuf = Unpooled.copiedBuffer(paramString, this.charset);
/*  58 */     this.value.add(paramInt, localByteBuf);
/*  59 */     this.size += localByteBuf.readableBytes();
/*     */   }
/*     */   
/*     */   public void setValue(String paramString, int paramInt) {
/*  63 */     if (paramString == null) {
/*  64 */       throw new NullPointerException("value");
/*     */     }
/*  66 */     ByteBuf localByteBuf1 = Unpooled.copiedBuffer(paramString, this.charset);
/*  67 */     ByteBuf localByteBuf2 = (ByteBuf)this.value.set(paramInt, localByteBuf1);
/*  68 */     if (localByteBuf2 != null) {
/*  69 */       this.size -= localByteBuf2.readableBytes();
/*  70 */       localByteBuf2.release();
/*     */     }
/*  72 */     this.size += localByteBuf1.readableBytes();
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  77 */     return getName().hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  82 */     if (!(paramObject instanceof Attribute)) {
/*  83 */       return false;
/*     */     }
/*  85 */     Attribute localAttribute = (Attribute)paramObject;
/*  86 */     return getName().equalsIgnoreCase(localAttribute.getName());
/*     */   }
/*     */   
/*     */   public int compareTo(InterfaceHttpData paramInterfaceHttpData)
/*     */   {
/*  91 */     if (!(paramInterfaceHttpData instanceof InternalAttribute)) {
/*  92 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + paramInterfaceHttpData.getHttpDataType());
/*     */     }
/*     */     
/*  95 */     return compareTo((InternalAttribute)paramInterfaceHttpData);
/*     */   }
/*     */   
/*     */   public int compareTo(InternalAttribute paramInternalAttribute) {
/*  99 */     return getName().compareToIgnoreCase(paramInternalAttribute.getName());
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 104 */     StringBuilder localStringBuilder = new StringBuilder();
/* 105 */     for (ByteBuf localByteBuf : this.value) {
/* 106 */       localStringBuilder.append(localByteBuf.toString(this.charset));
/*     */     }
/* 108 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public int size() {
/* 112 */     return this.size;
/*     */   }
/*     */   
/*     */   public ByteBuf toByteBuf() {
/* 116 */     return Unpooled.compositeBuffer().addComponents(this.value).writerIndex(size()).readerIndex(0);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 121 */     return "InternalAttribute";
/*     */   }
/*     */   
/*     */   protected void deallocate() {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\InternalAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */