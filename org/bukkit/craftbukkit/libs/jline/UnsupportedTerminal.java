/*    */ package org.bukkit.craftbukkit.libs.jline;
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
/*    */ public class UnsupportedTerminal
/*    */   extends TerminalSupport
/*    */ {
/*    */   public UnsupportedTerminal()
/*    */   {
/* 22 */     super(false);
/* 23 */     setAnsiSupported(false);
/* 24 */     setEchoEnabled(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\UnsupportedTerminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */