/*    */ package org.bukkit.craftbukkit.libs.jline;
/*    */ 
/*    */ import org.bukkit.craftbukkit.libs.jline.internal.TerminalLineSettings;
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
/*    */ public class NoInterruptUnixTerminal
/*    */   extends UnixTerminal
/*    */ {
/*    */   public NoInterruptUnixTerminal()
/*    */     throws Exception
/*    */   {}
/*    */   
/*    */   public void init()
/*    */     throws Exception
/*    */   {
/* 27 */     super.init();
/* 28 */     getSettings().set("intr undef");
/*    */   }
/*    */   
/*    */   public void restore() throws Exception
/*    */   {
/* 33 */     getSettings().set("intr ^C");
/* 34 */     super.restore();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\NoInterruptUnixTerminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */