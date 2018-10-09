/*    */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*    */ 
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
/*    */ public final class NullCompleter
/*    */   implements Completer
/*    */ {
/* 23 */   public static final NullCompleter INSTANCE = new NullCompleter();
/*    */   
/*    */   public int complete(String buffer, int cursor, List<CharSequence> candidates) {
/* 26 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\NullCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */