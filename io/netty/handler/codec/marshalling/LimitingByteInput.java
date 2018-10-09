/*    */ package io.netty.handler.codec.marshalling;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class LimitingByteInput
/*    */   implements ByteInput
/*    */ {
/* 29 */   private static final TooBigObjectException EXCEPTION = new TooBigObjectException();
/*    */   private final ByteInput input;
/*    */   private final long limit;
/*    */   private long read;
/*    */   
/*    */   LimitingByteInput(ByteInput paramByteInput, long paramLong)
/*    */   {
/* 36 */     if (paramLong <= 0L) {
/* 37 */       throw new IllegalArgumentException("The limit MUST be > 0");
/*    */     }
/* 39 */     this.input = paramByteInput;
/* 40 */     this.limit = paramLong;
/*    */   }
/*    */   
/*    */   public void close()
/*    */     throws IOException
/*    */   {}
/*    */   
/*    */   public int available()
/*    */     throws IOException
/*    */   {
/* 50 */     return readable(this.input.available());
/*    */   }
/*    */   
/*    */   public int read() throws IOException
/*    */   {
/* 55 */     int i = readable(1);
/* 56 */     if (i > 0) {
/* 57 */       int j = this.input.read();
/* 58 */       this.read += 1L;
/* 59 */       return j;
/*    */     }
/* 61 */     throw EXCEPTION;
/*    */   }
/*    */   
/*    */   public int read(byte[] paramArrayOfByte)
/*    */     throws IOException
/*    */   {
/* 67 */     return read(paramArrayOfByte, 0, paramArrayOfByte.length);
/*    */   }
/*    */   
/*    */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*    */   {
/* 72 */     int i = readable(paramInt2);
/* 73 */     if (i > 0) {
/* 74 */       int j = this.input.read(paramArrayOfByte, paramInt1, i);
/* 75 */       this.read += j;
/* 76 */       return j;
/*    */     }
/* 78 */     throw EXCEPTION;
/*    */   }
/*    */   
/*    */   public long skip(long paramLong)
/*    */     throws IOException
/*    */   {
/* 84 */     int i = readable((int)paramLong);
/* 85 */     if (i > 0) {
/* 86 */       long l = this.input.skip(i);
/* 87 */       this.read += l;
/* 88 */       return l;
/*    */     }
/* 90 */     throw EXCEPTION;
/*    */   }
/*    */   
/*    */   private int readable(int paramInt)
/*    */   {
/* 95 */     return (int)Math.min(paramInt, this.limit - this.read);
/*    */   }
/*    */   
/*    */   static final class TooBigObjectException
/*    */     extends IOException
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\marshalling\LimitingByteInput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */