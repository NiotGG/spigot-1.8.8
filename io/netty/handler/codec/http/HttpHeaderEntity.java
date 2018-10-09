/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.util.CharsetUtil;
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
/*    */ final class HttpHeaderEntity
/*    */   implements CharSequence
/*    */ {
/*    */   private final String name;
/*    */   private final int hash;
/*    */   private final byte[] bytes;
/*    */   private final int separatorLen;
/*    */   
/*    */   public HttpHeaderEntity(String paramString)
/*    */   {
/* 29 */     this(paramString, null);
/*    */   }
/*    */   
/*    */   public HttpHeaderEntity(String paramString, byte[] paramArrayOfByte) {
/* 33 */     this.name = paramString;
/* 34 */     this.hash = HttpHeaders.hash(paramString);
/* 35 */     byte[] arrayOfByte = paramString.getBytes(CharsetUtil.US_ASCII);
/* 36 */     if (paramArrayOfByte == null) {
/* 37 */       this.bytes = arrayOfByte;
/* 38 */       this.separatorLen = 0;
/*    */     } else {
/* 40 */       this.separatorLen = paramArrayOfByte.length;
/* 41 */       this.bytes = new byte[arrayOfByte.length + paramArrayOfByte.length];
/* 42 */       System.arraycopy(arrayOfByte, 0, this.bytes, 0, arrayOfByte.length);
/* 43 */       System.arraycopy(paramArrayOfByte, 0, this.bytes, arrayOfByte.length, paramArrayOfByte.length);
/*    */     }
/*    */   }
/*    */   
/*    */   int hash() {
/* 48 */     return this.hash;
/*    */   }
/*    */   
/*    */   public int length()
/*    */   {
/* 53 */     return this.bytes.length - this.separatorLen;
/*    */   }
/*    */   
/*    */   public char charAt(int paramInt)
/*    */   {
/* 58 */     if (this.bytes.length - this.separatorLen <= paramInt) {
/* 59 */       throw new IndexOutOfBoundsException();
/*    */     }
/* 61 */     return (char)this.bytes[paramInt];
/*    */   }
/*    */   
/*    */   public CharSequence subSequence(int paramInt1, int paramInt2)
/*    */   {
/* 66 */     return new HttpHeaderEntity(this.name.substring(paramInt1, paramInt2));
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 71 */     return this.name;
/*    */   }
/*    */   
/*    */   boolean encode(ByteBuf paramByteBuf) {
/* 75 */     paramByteBuf.writeBytes(this.bytes);
/* 76 */     return this.separatorLen > 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\HttpHeaderEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */