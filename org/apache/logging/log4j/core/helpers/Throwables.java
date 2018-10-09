/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InterruptedIOException;
/*    */ import java.io.LineNumberReader;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringReader;
/*    */ import java.io.StringWriter;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class Throwables
/*    */ {
/*    */   public static List<String> toStringList(Throwable paramThrowable)
/*    */   {
/* 41 */     StringWriter localStringWriter = new StringWriter();
/* 42 */     PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
/*    */     try {
/* 44 */       paramThrowable.printStackTrace(localPrintWriter);
/*    */     }
/*    */     catch (RuntimeException localRuntimeException) {}
/*    */     
/* 48 */     localPrintWriter.flush();
/* 49 */     LineNumberReader localLineNumberReader = new LineNumberReader(new StringReader(localStringWriter.toString()));
/* 50 */     ArrayList localArrayList = new ArrayList();
/*    */     try {
/* 52 */       String str = localLineNumberReader.readLine();
/* 53 */       while (str != null) {
/* 54 */         localArrayList.add(str);
/* 55 */         str = localLineNumberReader.readLine();
/*    */       }
/*    */     } catch (IOException localIOException) {
/* 58 */       if ((localIOException instanceof InterruptedIOException)) {
/* 59 */         Thread.currentThread().interrupt();
/*    */       }
/* 61 */       localArrayList.add(localIOException.toString());
/*    */     }
/* 63 */     return localArrayList;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Throwables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */