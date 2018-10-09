/*    */ package org.apache.commons.io.output;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NullOutputStream
/*    */   extends OutputStream
/*    */ {
/* 35 */   public static final NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();
/*    */   
/*    */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void write(int paramInt) {}
/*    */   
/*    */   public void write(byte[] paramArrayOfByte)
/*    */     throws IOException
/*    */   {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\NullOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */