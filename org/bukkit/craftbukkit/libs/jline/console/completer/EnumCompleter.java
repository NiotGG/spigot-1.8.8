/*    */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.bukkit.craftbukkit.libs.jline.internal.Preconditions;
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
/*    */ public class EnumCompleter
/*    */   extends StringsCompleter
/*    */ {
/*    */   public EnumCompleter(Class<? extends Enum> source)
/*    */   {
/* 23 */     Preconditions.checkNotNull(source);
/*    */     
/* 25 */     for (Enum<?> n : (Enum[])source.getEnumConstants()) {
/* 26 */       getStrings().add(n.name().toLowerCase());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\EnumCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */