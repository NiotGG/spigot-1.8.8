/*    */ package io.netty.util.internal.logging;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.slf4j.helpers.NOPLoggerFactory;
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
/*    */ public class Slf4JLoggerFactory
/*    */   extends InternalLoggerFactory
/*    */ {
/*    */   public Slf4JLoggerFactory() {}
/*    */   
/*    */   Slf4JLoggerFactory(boolean paramBoolean)
/*    */   {
/* 36 */     assert (paramBoolean);
/*    */     
/*    */ 
/*    */ 
/* 40 */     final StringBuffer localStringBuffer = new StringBuffer();
/* 41 */     PrintStream localPrintStream = System.err;
/*    */     try {
/* 43 */       System.setErr(new PrintStream(new OutputStream()
/*    */       {
/*    */ 
/* 46 */         public void write(int paramAnonymousInt) { localStringBuffer.append((char)paramAnonymousInt); } }, true, "US-ASCII"));
/*    */     }
/*    */     catch (UnsupportedEncodingException localUnsupportedEncodingException)
/*    */     {
/* 50 */       throw new Error(localUnsupportedEncodingException);
/*    */     }
/*    */     try
/*    */     {
/* 54 */       if ((LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory)) {
/* 55 */         throw new NoClassDefFoundError(localStringBuffer.toString());
/*    */       }
/* 57 */       localPrintStream.print(localStringBuffer);
/* 58 */       localPrintStream.flush();
/*    */     }
/*    */     finally {
/* 61 */       System.setErr(localPrintStream);
/*    */     }
/*    */   }
/*    */   
/*    */   public InternalLogger newInstance(String paramString)
/*    */   {
/* 67 */     return new Slf4JLogger(LoggerFactory.getLogger(paramString));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\logging\Slf4JLoggerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */