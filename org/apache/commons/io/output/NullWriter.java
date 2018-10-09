/*    */ package org.apache.commons.io.output;
/*    */ 
/*    */ import java.io.Writer;
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
/*    */ public class NullWriter
/*    */   extends Writer
/*    */ {
/* 34 */   public static final NullWriter NULL_WRITER = new NullWriter();
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
/*    */   public Writer append(char paramChar)
/*    */   {
/* 51 */     return this;
/*    */   }
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
/*    */   public Writer append(CharSequence paramCharSequence, int paramInt1, int paramInt2)
/*    */   {
/* 65 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Writer append(CharSequence paramCharSequence)
/*    */   {
/* 77 */     return this;
/*    */   }
/*    */   
/*    */   public void write(int paramInt) {}
/*    */   
/*    */   public void write(char[] paramArrayOfChar) {}
/*    */   
/*    */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void write(String paramString) {}
/*    */   
/*    */   public void write(String paramString, int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void flush() {}
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\NullWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */