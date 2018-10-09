/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import org.jboss.marshalling.ByteInput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ChannelBufferByteInput
/*    */   implements ByteInput
/*    */ {
/*    */   private final ByteBuf buffer;
/*    */   
/*    */   ChannelBufferByteInput(ByteBuf paramByteBuf)
/*    */   {
/* 31 */     this.buffer = paramByteBuf;
/*    */   }
/*    */   
/*    */   public void close()
/*    */     throws IOException
/*    */   {}
/*    */   
/*    */   public int available()
/*    */     throws IOException
/*    */   {
/* 41 */     return this.buffer.readableBytes();
/*    */   }
/*    */   
/*    */   public int read() throws IOException
/*    */   {
/* 46 */     if (this.buffer.isReadable()) {
/* 47 */       return this.buffer.readByte() & 0xFF;
/*    */     }
/* 49 */     return -1;
/*    */   }
/*    */   
/*    */   public int read(byte[] paramArrayOfByte) throws IOException
/*    */   {
/* 54 */     return read(paramArrayOfByte, 0, paramArrayOfByte.length);
/*    */   }
/*    */   
/*    */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*    */   {
/* 59 */     int i = available();
/* 60 */     if (i == 0) {
/* 61 */       return -1;
/*    */     }
/*    */     
/* 64 */     paramInt2 = Math.min(i, paramInt2);
/* 65 */     this.buffer.readBytes(paramArrayOfByte, paramInt1, paramInt2);
/* 66 */     return paramInt2;
/*    */   }
/*    */   
/*    */   public long skip(long paramLong) throws IOException
/*    */   {
/* 71 */     int i = this.buffer.readableBytes();
/* 72 */     if (i < paramLong) {
/* 73 */       paramLong = i;
/*    */     }
/* 75 */     this.buffer.readerIndex((int)(this.buffer.readerIndex() + paramLong));
/* 76 */     return paramLong;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\ChannelBufferByteInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */