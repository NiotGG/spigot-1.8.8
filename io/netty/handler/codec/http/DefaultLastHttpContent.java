/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Map.Entry;
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
/*     */ public class DefaultLastHttpContent
/*     */   extends DefaultHttpContent
/*     */   implements LastHttpContent
/*     */ {
/*     */   private final HttpHeaders trailingHeaders;
/*     */   private final boolean validateHeaders;
/*     */   
/*     */   public DefaultLastHttpContent()
/*     */   {
/*  33 */     this(Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */   public DefaultLastHttpContent(ByteBuf paramByteBuf) {
/*  37 */     this(paramByteBuf, true);
/*     */   }
/*     */   
/*     */   public DefaultLastHttpContent(ByteBuf paramByteBuf, boolean paramBoolean) {
/*  41 */     super(paramByteBuf);
/*  42 */     this.trailingHeaders = new TrailingHeaders(paramBoolean);
/*  43 */     this.validateHeaders = paramBoolean;
/*     */   }
/*     */   
/*     */   public LastHttpContent copy()
/*     */   {
/*  48 */     DefaultLastHttpContent localDefaultLastHttpContent = new DefaultLastHttpContent(content().copy(), this.validateHeaders);
/*  49 */     localDefaultLastHttpContent.trailingHeaders().set(trailingHeaders());
/*  50 */     return localDefaultLastHttpContent;
/*     */   }
/*     */   
/*     */   public LastHttpContent duplicate()
/*     */   {
/*  55 */     DefaultLastHttpContent localDefaultLastHttpContent = new DefaultLastHttpContent(content().duplicate(), this.validateHeaders);
/*  56 */     localDefaultLastHttpContent.trailingHeaders().set(trailingHeaders());
/*  57 */     return localDefaultLastHttpContent;
/*     */   }
/*     */   
/*     */   public LastHttpContent retain(int paramInt)
/*     */   {
/*  62 */     super.retain(paramInt);
/*  63 */     return this;
/*     */   }
/*     */   
/*     */   public LastHttpContent retain()
/*     */   {
/*  68 */     super.retain();
/*  69 */     return this;
/*     */   }
/*     */   
/*     */   public HttpHeaders trailingHeaders()
/*     */   {
/*  74 */     return this.trailingHeaders;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  79 */     StringBuilder localStringBuilder = new StringBuilder(super.toString());
/*  80 */     localStringBuilder.append(StringUtil.NEWLINE);
/*  81 */     appendHeaders(localStringBuilder);
/*     */     
/*     */ 
/*  84 */     localStringBuilder.setLength(localStringBuilder.length() - StringUtil.NEWLINE.length());
/*  85 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private void appendHeaders(StringBuilder paramStringBuilder) {
/*  89 */     for (Map.Entry localEntry : trailingHeaders()) {
/*  90 */       paramStringBuilder.append((String)localEntry.getKey());
/*  91 */       paramStringBuilder.append(": ");
/*  92 */       paramStringBuilder.append((String)localEntry.getValue());
/*  93 */       paramStringBuilder.append(StringUtil.NEWLINE);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class TrailingHeaders extends DefaultHttpHeaders {
/*     */     TrailingHeaders(boolean paramBoolean) {
/*  99 */       super();
/*     */     }
/*     */     
/*     */     void validateHeaderName0(CharSequence paramCharSequence)
/*     */     {
/* 104 */       super.validateHeaderName0(paramCharSequence);
/* 105 */       if ((HttpHeaders.equalsIgnoreCase("Content-Length", paramCharSequence)) || (HttpHeaders.equalsIgnoreCase("Transfer-Encoding", paramCharSequence)) || (HttpHeaders.equalsIgnoreCase("Trailer", paramCharSequence)))
/*     */       {
/*     */ 
/* 108 */         throw new IllegalArgumentException("prohibited trailing header: " + paramCharSequence);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\DefaultLastHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */