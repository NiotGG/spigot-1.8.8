/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ 
/*     */ public class DefaultSpdySynStreamFrame
/*     */   extends DefaultSpdyHeadersFrame
/*     */   implements SpdySynStreamFrame
/*     */ {
/*     */   private int associatedStreamId;
/*     */   private byte priority;
/*     */   private boolean unidirectional;
/*     */   
/*     */   public DefaultSpdySynStreamFrame(int paramInt1, int paramInt2, byte paramByte)
/*     */   {
/*  38 */     super(paramInt1);
/*  39 */     setAssociatedStreamId(paramInt2);
/*  40 */     setPriority(paramByte);
/*     */   }
/*     */   
/*     */   public SpdySynStreamFrame setStreamId(int paramInt)
/*     */   {
/*  45 */     super.setStreamId(paramInt);
/*  46 */     return this;
/*     */   }
/*     */   
/*     */   public SpdySynStreamFrame setLast(boolean paramBoolean)
/*     */   {
/*  51 */     super.setLast(paramBoolean);
/*  52 */     return this;
/*     */   }
/*     */   
/*     */   public SpdySynStreamFrame setInvalid()
/*     */   {
/*  57 */     super.setInvalid();
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   public int associatedStreamId()
/*     */   {
/*  63 */     return this.associatedStreamId;
/*     */   }
/*     */   
/*     */   public SpdySynStreamFrame setAssociatedStreamId(int paramInt)
/*     */   {
/*  68 */     if (paramInt < 0) {
/*  69 */       throw new IllegalArgumentException("Associated-To-Stream-ID cannot be negative: " + paramInt);
/*     */     }
/*     */     
/*     */ 
/*  73 */     this.associatedStreamId = paramInt;
/*  74 */     return this;
/*     */   }
/*     */   
/*     */   public byte priority()
/*     */   {
/*  79 */     return this.priority;
/*     */   }
/*     */   
/*     */   public SpdySynStreamFrame setPriority(byte paramByte)
/*     */   {
/*  84 */     if ((paramByte < 0) || (paramByte > 7)) {
/*  85 */       throw new IllegalArgumentException("Priority must be between 0 and 7 inclusive: " + paramByte);
/*     */     }
/*     */     
/*  88 */     this.priority = paramByte;
/*  89 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isUnidirectional()
/*     */   {
/*  94 */     return this.unidirectional;
/*     */   }
/*     */   
/*     */   public SpdySynStreamFrame setUnidirectional(boolean paramBoolean)
/*     */   {
/*  99 */     this.unidirectional = paramBoolean;
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 105 */     StringBuilder localStringBuilder = new StringBuilder();
/* 106 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 107 */     localStringBuilder.append("(last: ");
/* 108 */     localStringBuilder.append(isLast());
/* 109 */     localStringBuilder.append("; unidirectional: ");
/* 110 */     localStringBuilder.append(isUnidirectional());
/* 111 */     localStringBuilder.append(')');
/* 112 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 113 */     localStringBuilder.append("--> Stream-ID = ");
/* 114 */     localStringBuilder.append(streamId());
/* 115 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 116 */     if (this.associatedStreamId != 0) {
/* 117 */       localStringBuilder.append("--> Associated-To-Stream-ID = ");
/* 118 */       localStringBuilder.append(associatedStreamId());
/* 119 */       localStringBuilder.append(StringUtil.NEWLINE);
/*     */     }
/* 121 */     localStringBuilder.append("--> Priority = ");
/* 122 */     localStringBuilder.append(priority());
/* 123 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 124 */     localStringBuilder.append("--> Headers:");
/* 125 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 126 */     appendHeaders(localStringBuilder);
/*     */     
/*     */ 
/* 129 */     localStringBuilder.setLength(localStringBuilder.length() - StringUtil.NEWLINE.length());
/* 130 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdySynStreamFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */