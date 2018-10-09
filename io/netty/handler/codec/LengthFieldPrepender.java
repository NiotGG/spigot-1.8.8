/*     */ package io.netty.handler.codec;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
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
/*     */ 
/*     */ 
/*     */ @ChannelHandler.Sharable
/*     */ public class LengthFieldPrepender
/*     */   extends MessageToByteEncoder<ByteBuf>
/*     */ {
/*     */   private final int lengthFieldLength;
/*     */   private final boolean lengthIncludesLengthFieldLength;
/*     */   private final int lengthAdjustment;
/*     */   
/*     */   public LengthFieldPrepender(int paramInt)
/*     */   {
/*  66 */     this(paramInt, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public LengthFieldPrepender(int paramInt, boolean paramBoolean)
/*     */   {
/*  83 */     this(paramInt, 0, paramBoolean);
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
/*     */ 
/*     */   public LengthFieldPrepender(int paramInt1, int paramInt2)
/*     */   {
/*  98 */     this(paramInt1, paramInt2, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LengthFieldPrepender(int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 117 */     if ((paramInt1 != 1) && (paramInt1 != 2) && (paramInt1 != 3) && (paramInt1 != 4) && (paramInt1 != 8))
/*     */     {
/*     */ 
/* 120 */       throw new IllegalArgumentException("lengthFieldLength must be either 1, 2, 3, 4, or 8: " + paramInt1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 125 */     this.lengthFieldLength = paramInt1;
/* 126 */     this.lengthIncludesLengthFieldLength = paramBoolean;
/* 127 */     this.lengthAdjustment = paramInt2;
/*     */   }
/*     */   
/*     */   protected void encode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf1, ByteBuf paramByteBuf2) throws Exception
/*     */   {
/* 132 */     int i = paramByteBuf1.readableBytes() + this.lengthAdjustment;
/* 133 */     if (this.lengthIncludesLengthFieldLength) {
/* 134 */       i += this.lengthFieldLength;
/*     */     }
/*     */     
/* 137 */     if (i < 0) {
/* 138 */       throw new IllegalArgumentException("Adjusted frame length (" + i + ") is less than zero");
/*     */     }
/*     */     
/*     */ 
/* 142 */     switch (this.lengthFieldLength) {
/*     */     case 1: 
/* 144 */       if (i >= 256) {
/* 145 */         throw new IllegalArgumentException("length does not fit into a byte: " + i);
/*     */       }
/*     */       
/* 148 */       paramByteBuf2.writeByte((byte)i);
/* 149 */       break;
/*     */     case 2: 
/* 151 */       if (i >= 65536) {
/* 152 */         throw new IllegalArgumentException("length does not fit into a short integer: " + i);
/*     */       }
/*     */       
/* 155 */       paramByteBuf2.writeShort((short)i);
/* 156 */       break;
/*     */     case 3: 
/* 158 */       if (i >= 16777216) {
/* 159 */         throw new IllegalArgumentException("length does not fit into a medium integer: " + i);
/*     */       }
/*     */       
/* 162 */       paramByteBuf2.writeMedium(i);
/* 163 */       break;
/*     */     case 4: 
/* 165 */       paramByteBuf2.writeInt(i);
/* 166 */       break;
/*     */     case 8: 
/* 168 */       paramByteBuf2.writeLong(i);
/* 169 */       break;
/*     */     case 5: case 6: case 7: default: 
/* 171 */       throw new Error("should not reach here");
/*     */     }
/*     */     
/* 174 */     paramByteBuf2.writeBytes(paramByteBuf1, paramByteBuf1.readerIndex(), paramByteBuf1.readableBytes());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\LengthFieldPrepender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */