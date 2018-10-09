/*    */ package org.apache.logging.log4j.core.appender.rolling.helper;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ public abstract class AbstractAction
/*    */   implements Action
/*    */ {
/* 32 */   protected static final Logger LOGGER = ;
/*    */   
/*    */ 
/*    */ 
/* 36 */   private boolean complete = false;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 41 */   private boolean interrupted = false;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract boolean execute()
/*    */     throws IOException;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public synchronized void run()
/*    */   {
/* 63 */     if (!this.interrupted) {
/*    */       try {
/* 65 */         execute();
/*    */       } catch (IOException localIOException) {
/* 67 */         reportException(localIOException);
/*    */       }
/*    */       
/* 70 */       this.complete = true;
/* 71 */       this.interrupted = true;
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public synchronized void close()
/*    */   {
/* 80 */     this.interrupted = true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isComplete()
/*    */   {
/* 90 */     return this.complete;
/*    */   }
/*    */   
/*    */   protected void reportException(Exception paramException) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\helper\AbstractAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */