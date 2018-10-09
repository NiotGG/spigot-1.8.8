/*    */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*    */ 
/*    */ public class TerminalConsoleWriterThread implements Runnable
/*    */ {
/*    */   private final ConsoleReader reader;
/*    */   private final OutputStream output;
/*    */   
/*    */   public TerminalConsoleWriterThread(OutputStream output, ConsoleReader reader)
/*    */   {
/* 16 */     this.output = output;
/* 17 */     this.reader = reader;
/*    */   }
/*    */   
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     for (;;)
/*    */     {
/* 25 */       String message = com.mojang.util.QueueLogAppender.getNextLogEvent("TerminalConsole");
/* 26 */       if (message != null)
/*    */       {
/*    */         try
/*    */         {
/*    */ 
/* 31 */           if (org.bukkit.craftbukkit.Main.useJline) {
/* 32 */             this.reader.print("\r");
/* 33 */             this.reader.flush();
/* 34 */             this.output.write(message.getBytes());
/* 35 */             this.output.flush();
/*    */             try
/*    */             {
/* 38 */               this.reader.drawLine();
/*    */             } catch (Throwable localThrowable) {
/* 40 */               this.reader.getCursorBuffer().clear();
/*    */             }
/* 42 */             this.reader.flush();
/*    */           } else {
/* 44 */             this.output.write(message.getBytes());
/* 45 */             this.output.flush();
/*    */           }
/*    */         } catch (IOException ex) {
/* 48 */           Logger.getLogger(TerminalConsoleWriterThread.class.getName()).log(Level.SEVERE, null, ex);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\TerminalConsoleWriterThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */