/*     */ package io.netty.channel.sctp;
/*     */ 
/*     */ import com.sun.nio.sctp.MessageInfo;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.DefaultByteBufHolder;
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
/*     */ public final class SctpMessage
/*     */   extends DefaultByteBufHolder
/*     */ {
/*     */   private final int streamIdentifier;
/*     */   private final int protocolIdentifier;
/*     */   private final MessageInfo msgInfo;
/*     */   
/*     */   public SctpMessage(int paramInt1, int paramInt2, ByteBuf paramByteBuf)
/*     */   {
/*  39 */     super(paramByteBuf);
/*  40 */     this.protocolIdentifier = paramInt1;
/*  41 */     this.streamIdentifier = paramInt2;
/*  42 */     this.msgInfo = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SctpMessage(MessageInfo paramMessageInfo, ByteBuf paramByteBuf)
/*     */   {
/*  51 */     super(paramByteBuf);
/*  52 */     if (paramMessageInfo == null) {
/*  53 */       throw new NullPointerException("msgInfo");
/*     */     }
/*  55 */     this.msgInfo = paramMessageInfo;
/*  56 */     this.streamIdentifier = paramMessageInfo.streamNumber();
/*  57 */     this.protocolIdentifier = paramMessageInfo.payloadProtocolID();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int streamIdentifier()
/*     */   {
/*  64 */     return this.streamIdentifier;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int protocolIdentifier()
/*     */   {
/*  71 */     return this.protocolIdentifier;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public MessageInfo messageInfo()
/*     */   {
/*  79 */     return this.msgInfo;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isComplete()
/*     */   {
/*  86 */     if (this.msgInfo != null) {
/*  87 */       return this.msgInfo.isComplete();
/*     */     }
/*     */     
/*  90 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  96 */     if (this == paramObject) {
/*  97 */       return true;
/*     */     }
/*     */     
/* 100 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 101 */       return false;
/*     */     }
/*     */     
/* 104 */     SctpMessage localSctpMessage = (SctpMessage)paramObject;
/*     */     
/* 106 */     if (this.protocolIdentifier != localSctpMessage.protocolIdentifier) {
/* 107 */       return false;
/*     */     }
/*     */     
/* 110 */     if (this.streamIdentifier != localSctpMessage.streamIdentifier) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     if (!content().equals(localSctpMessage.content())) {
/* 115 */       return false;
/*     */     }
/*     */     
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 123 */     int i = this.streamIdentifier;
/* 124 */     i = 31 * i + this.protocolIdentifier;
/* 125 */     i = 31 * i + content().hashCode();
/* 126 */     return i;
/*     */   }
/*     */   
/*     */   public SctpMessage copy()
/*     */   {
/* 131 */     if (this.msgInfo == null) {
/* 132 */       return new SctpMessage(this.protocolIdentifier, this.streamIdentifier, content().copy());
/*     */     }
/* 134 */     return new SctpMessage(this.msgInfo, content().copy());
/*     */   }
/*     */   
/*     */ 
/*     */   public SctpMessage duplicate()
/*     */   {
/* 140 */     if (this.msgInfo == null) {
/* 141 */       return new SctpMessage(this.protocolIdentifier, this.streamIdentifier, content().duplicate());
/*     */     }
/* 143 */     return new SctpMessage(this.msgInfo, content().copy());
/*     */   }
/*     */   
/*     */ 
/*     */   public SctpMessage retain()
/*     */   {
/* 149 */     super.retain();
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   public SctpMessage retain(int paramInt)
/*     */   {
/* 155 */     super.retain(paramInt);
/* 156 */     return this;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 161 */     if (refCnt() == 0) {
/* 162 */       return "SctpFrame{streamIdentifier=" + this.streamIdentifier + ", protocolIdentifier=" + this.protocolIdentifier + ", data=(FREED)}";
/*     */     }
/*     */     
/*     */ 
/* 166 */     return "SctpFrame{streamIdentifier=" + this.streamIdentifier + ", protocolIdentifier=" + this.protocolIdentifier + ", data=" + ByteBufUtil.hexDump(content()) + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\sctp\SctpMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */