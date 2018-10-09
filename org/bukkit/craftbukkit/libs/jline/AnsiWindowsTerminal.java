/*    */ package org.bukkit.craftbukkit.libs.jline;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
/*    */ import org.fusesource.jansi.AnsiConsole;
/*    */ import org.fusesource.jansi.AnsiOutputStream;
/*    */ import org.fusesource.jansi.WindowsAnsiOutputStream;
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
/*    */ public class AnsiWindowsTerminal
/*    */   extends WindowsTerminal
/*    */ {
/* 27 */   private final boolean ansiSupported = detectAnsiSupport();
/*    */   
/*    */   public OutputStream wrapOutIfNeeded(OutputStream out)
/*    */   {
/* 31 */     return wrapOutputStream(out);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private static OutputStream wrapOutputStream(OutputStream stream)
/*    */   {
/* 42 */     if (Configuration.isWindows())
/*    */       try
/*    */       {
/* 45 */         return new WindowsAnsiOutputStream(stream);
/*    */ 
/*    */       }
/*    */       catch (Throwable ignore)
/*    */       {
/*    */ 
/* 51 */         return new AnsiOutputStream(stream);
/*    */       }
/* 53 */     return stream;
/*    */   }
/*    */   
/*    */   private static boolean detectAnsiSupport() {
/* 57 */     OutputStream out = AnsiConsole.wrapOutputStream(new ByteArrayOutputStream());
/*    */     try {
/* 59 */       out.close();
/*    */     }
/*    */     catch (Exception e) {}
/*    */     
/*    */ 
/* 64 */     return out instanceof WindowsAnsiOutputStream;
/*    */   }
/*    */   
/*    */   public AnsiWindowsTerminal()
/*    */     throws Exception
/*    */   {}
/*    */   
/*    */   public boolean isAnsiSupported()
/*    */   {
/* 73 */     return this.ansiSupported;
/*    */   }
/*    */   
/*    */   public boolean hasWeirdWrap()
/*    */   {
/* 78 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\AnsiWindowsTerminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */