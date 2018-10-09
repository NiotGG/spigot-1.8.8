/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import org.jboss.marshalling.ByteOutput;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ChannelBufferByteOutput
/*    */   implements ByteOutput
/*    */ {
/*    */   private final ByteBuf buffer;
/*    */   
/*    */   ChannelBufferByteOutput(ByteBuf paramByteBuf)
/*    */   {
/* 36 */     this.buffer = paramByteBuf;
/*    */   }
/*    */   
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {}
/*    */   
/*    */   public void flush()
/*    */     throws IOException
/*    */   {}
/*    */   
/*    */   public void write(int paramInt)
/*    */     throws IOException
/*    */   {
/* 51 */     this.buffer.writeByte(paramInt);
/*    */   }
/*    */   
/*    */   public void write(byte[] paramArrayOfByte) throws IOException
/*    */   {
/* 56 */     this.buffer.writeBytes(paramArrayOfByte);
/*    */   }
/*    */   
/*    */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*    */   {
/* 61 */     this.buffer.writeBytes(paramArrayOfByte, paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   ByteBuf getBuffer()
/*    */   {
/* 69 */     return this.buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\ChannelBufferByteOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */