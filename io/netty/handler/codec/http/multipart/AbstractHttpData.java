/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.util.AbstractReferenceCounted;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public abstract class AbstractHttpData
/*     */   extends AbstractReferenceCounted
/*     */   implements HttpData
/*     */ {
/*  32 */   private static final Pattern STRIP_PATTERN = Pattern.compile("(?:^\\s+|\\s+$|\\n)");
/*  33 */   private static final Pattern REPLACE_PATTERN = Pattern.compile("[\\r\\t]");
/*     */   
/*     */   protected final String name;
/*     */   protected long definedSize;
/*     */   protected long size;
/*  38 */   protected Charset charset = HttpConstants.DEFAULT_CHARSET;
/*     */   protected boolean completed;
/*     */   
/*     */   protected AbstractHttpData(String paramString, Charset paramCharset, long paramLong) {
/*  42 */     if (paramString == null) {
/*  43 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/*  46 */     paramString = REPLACE_PATTERN.matcher(paramString).replaceAll(" ");
/*  47 */     paramString = STRIP_PATTERN.matcher(paramString).replaceAll("");
/*     */     
/*  49 */     if (paramString.isEmpty()) {
/*  50 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/*  53 */     this.name = paramString;
/*  54 */     if (paramCharset != null) {
/*  55 */       setCharset(paramCharset);
/*     */     }
/*  57 */     this.definedSize = paramLong;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  62 */     return this.name;
/*     */   }
/*     */   
/*     */   public boolean isCompleted()
/*     */   {
/*  67 */     return this.completed;
/*     */   }
/*     */   
/*     */   public Charset getCharset()
/*     */   {
/*  72 */     return this.charset;
/*     */   }
/*     */   
/*     */   public void setCharset(Charset paramCharset)
/*     */   {
/*  77 */     if (paramCharset == null) {
/*  78 */       throw new NullPointerException("charset");
/*     */     }
/*  80 */     this.charset = paramCharset;
/*     */   }
/*     */   
/*     */   public long length()
/*     */   {
/*  85 */     return this.size;
/*     */   }
/*     */   
/*     */   public ByteBuf content()
/*     */   {
/*     */     try {
/*  91 */       return getByteBuf();
/*     */     } catch (IOException localIOException) {
/*  93 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void deallocate()
/*     */   {
/*  99 */     delete();
/*     */   }
/*     */   
/*     */   public HttpData retain()
/*     */   {
/* 104 */     super.retain();
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public HttpData retain(int paramInt)
/*     */   {
/* 110 */     super.retain(paramInt);
/* 111 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\AbstractHttpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */