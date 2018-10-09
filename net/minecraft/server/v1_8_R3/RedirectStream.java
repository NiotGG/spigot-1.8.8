/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class RedirectStream extends PrintStream
/*    */ {
/* 10 */   private static final Logger a = ;
/*    */   private final String b;
/*    */   
/*    */   public RedirectStream(String paramString, OutputStream paramOutputStream) {
/* 14 */     super(paramOutputStream);
/* 15 */     this.b = paramString;
/*    */   }
/*    */   
/*    */   public void println(String paramString)
/*    */   {
/* 20 */     a(paramString);
/*    */   }
/*    */   
/*    */   public void println(Object paramObject)
/*    */   {
/* 25 */     a(String.valueOf(paramObject));
/*    */   }
/*    */   
/*    */   private void a(String paramString) {
/* 29 */     StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
/* 30 */     StackTraceElement localStackTraceElement = arrayOfStackTraceElement[Math.min(3, arrayOfStackTraceElement.length)];
/* 31 */     a.info("[{}]@.({}:{}): {}", new Object[] { this.b, localStackTraceElement.getFileName(), Integer.valueOf(localStackTraceElement.getLineNumber()), paramString });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RedirectStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */