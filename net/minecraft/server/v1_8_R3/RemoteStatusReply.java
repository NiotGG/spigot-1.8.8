/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.DataOutputStream;
/*    */ 
/*    */ public class RemoteStatusReply
/*    */ {
/*    */   private java.io.ByteArrayOutputStream buffer;
/*    */   private DataOutputStream stream;
/*    */   
/*    */   public RemoteStatusReply(int paramInt)
/*    */   {
/* 12 */     this.buffer = new java.io.ByteArrayOutputStream(paramInt);
/* 13 */     this.stream = new DataOutputStream(this.buffer);
/*    */   }
/*    */   
/*    */   public void a(byte[] paramArrayOfByte) throws java.io.IOException {
/* 17 */     this.stream.write(paramArrayOfByte, 0, paramArrayOfByte.length);
/*    */   }
/*    */   
/*    */   public void a(String paramString) throws java.io.IOException {
/* 21 */     this.stream.writeBytes(paramString);
/* 22 */     this.stream.write(0);
/*    */   }
/*    */   
/*    */   public void a(int paramInt) throws java.io.IOException {
/* 26 */     this.stream.write(paramInt);
/*    */   }
/*    */   
/*    */   public void a(short paramShort) throws java.io.IOException
/*    */   {
/* 31 */     this.stream.writeShort(Short.reverseBytes(paramShort));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] a()
/*    */   {
/* 43 */     return this.buffer.toByteArray();
/*    */   }
/*    */   
/*    */   public void b() {
/* 47 */     this.buffer.reset();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RemoteStatusReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */