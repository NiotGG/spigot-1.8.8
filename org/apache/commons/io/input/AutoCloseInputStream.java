/*    */ package org.apache.commons.io.input;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoCloseInputStream
/*    */   extends ProxyInputStream
/*    */ {
/*    */   public AutoCloseInputStream(InputStream paramInputStream)
/*    */   {
/* 45 */     super(paramInputStream);
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
/*    */ 
/*    */ 
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/* 63 */     this.in.close();
/* 64 */     this.in = new ClosedInputStream();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void afterRead(int paramInt)
/*    */     throws IOException
/*    */   {
/* 76 */     if (paramInt == -1) {
/* 77 */       close();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected void finalize()
/*    */     throws Throwable
/*    */   {
/* 89 */     close();
/* 90 */     super.finalize();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\AutoCloseInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */