/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import java.util.Set;
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
/*    */ public class SpdyHeaderBlockRawEncoder
/*    */   extends SpdyHeaderBlockEncoder
/*    */ {
/*    */   private final int version;
/*    */   
/*    */   public SpdyHeaderBlockRawEncoder(SpdyVersion paramSpdyVersion)
/*    */   {
/* 30 */     if (paramSpdyVersion == null) {
/* 31 */       throw new NullPointerException("version");
/*    */     }
/* 33 */     this.version = paramSpdyVersion.getVersion();
/*    */   }
/*    */   
/*    */   private static void setLengthField(ByteBuf paramByteBuf, int paramInt1, int paramInt2) {
/* 37 */     paramByteBuf.setInt(paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   private static void writeLengthField(ByteBuf paramByteBuf, int paramInt) {
/* 41 */     paramByteBuf.writeInt(paramInt);
/*    */   }
/*    */   
/*    */   public ByteBuf encode(SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception
/*    */   {
/* 46 */     Set localSet = paramSpdyHeadersFrame.headers().names();
/* 47 */     int i = localSet.size();
/* 48 */     if (i == 0) {
/* 49 */       return Unpooled.EMPTY_BUFFER;
/*    */     }
/* 51 */     if (i > 65535) {
/* 52 */       throw new IllegalArgumentException("header block contains too many headers");
/*    */     }
/*    */     
/* 55 */     ByteBuf localByteBuf = Unpooled.buffer();
/* 56 */     writeLengthField(localByteBuf, i);
/* 57 */     for (String str1 : localSet) {
/* 58 */       byte[] arrayOfByte1 = str1.getBytes("UTF-8");
/* 59 */       writeLengthField(localByteBuf, arrayOfByte1.length);
/* 60 */       localByteBuf.writeBytes(arrayOfByte1);
/* 61 */       int j = localByteBuf.writerIndex();
/* 62 */       int k = 0;
/* 63 */       writeLengthField(localByteBuf, k);
/* 64 */       for (String str2 : paramSpdyHeadersFrame.headers().getAll(str1)) {
/* 65 */         byte[] arrayOfByte2 = str2.getBytes("UTF-8");
/* 66 */         if (arrayOfByte2.length > 0) {
/* 67 */           localByteBuf.writeBytes(arrayOfByte2);
/* 68 */           localByteBuf.writeByte(0);
/* 69 */           k += arrayOfByte2.length + 1;
/*    */         }
/*    */       }
/* 72 */       if (k != 0) {
/* 73 */         k--;
/*    */       }
/* 75 */       if (k > 65535) {
/* 76 */         throw new IllegalArgumentException("header exceeds allowable length: " + str1);
/*    */       }
/*    */       
/* 79 */       if (k > 0) {
/* 80 */         setLengthField(localByteBuf, j, k);
/* 81 */         localByteBuf.writerIndex(localByteBuf.writerIndex() - 1);
/*    */       }
/*    */     }
/* 84 */     return localByteBuf;
/*    */   }
/*    */   
/*    */   void end() {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockRawEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */