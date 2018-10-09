/*     */ package io.netty.handler.codec.spdy;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ public class DefaultSpdyHeadersFrame
/*     */   extends DefaultSpdyStreamFrame
/*     */   implements SpdyHeadersFrame
/*     */ {
/*     */   private boolean invalid;
/*     */   private boolean truncated;
/*  30 */   private final SpdyHeaders headers = new DefaultSpdyHeaders();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultSpdyHeadersFrame(int paramInt)
/*     */   {
/*  38 */     super(paramInt);
/*     */   }
/*     */   
/*     */   public SpdyHeadersFrame setStreamId(int paramInt)
/*     */   {
/*  43 */     super.setStreamId(paramInt);
/*  44 */     return this;
/*     */   }
/*     */   
/*     */   public SpdyHeadersFrame setLast(boolean paramBoolean)
/*     */   {
/*  49 */     super.setLast(paramBoolean);
/*  50 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isInvalid()
/*     */   {
/*  55 */     return this.invalid;
/*     */   }
/*     */   
/*     */   public SpdyHeadersFrame setInvalid()
/*     */   {
/*  60 */     this.invalid = true;
/*  61 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isTruncated()
/*     */   {
/*  66 */     return this.truncated;
/*     */   }
/*     */   
/*     */   public SpdyHeadersFrame setTruncated()
/*     */   {
/*  71 */     this.truncated = true;
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public SpdyHeaders headers()
/*     */   {
/*  77 */     return this.headers;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  82 */     StringBuilder localStringBuilder = new StringBuilder();
/*  83 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/*  84 */     localStringBuilder.append("(last: ");
/*  85 */     localStringBuilder.append(isLast());
/*  86 */     localStringBuilder.append(')');
/*  87 */     localStringBuilder.append(StringUtil.NEWLINE);
/*  88 */     localStringBuilder.append("--> Stream-ID = ");
/*  89 */     localStringBuilder.append(streamId());
/*  90 */     localStringBuilder.append(StringUtil.NEWLINE);
/*  91 */     localStringBuilder.append("--> Headers:");
/*  92 */     localStringBuilder.append(StringUtil.NEWLINE);
/*  93 */     appendHeaders(localStringBuilder);
/*     */     
/*     */ 
/*  96 */     localStringBuilder.setLength(localStringBuilder.length() - StringUtil.NEWLINE.length());
/*  97 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   protected void appendHeaders(StringBuilder paramStringBuilder) {
/* 101 */     for (Map.Entry localEntry : headers()) {
/* 102 */       paramStringBuilder.append("    ");
/* 103 */       paramStringBuilder.append((String)localEntry.getKey());
/* 104 */       paramStringBuilder.append(": ");
/* 105 */       paramStringBuilder.append((String)localEntry.getValue());
/* 106 */       paramStringBuilder.append(StringUtil.NEWLINE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdyHeadersFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */