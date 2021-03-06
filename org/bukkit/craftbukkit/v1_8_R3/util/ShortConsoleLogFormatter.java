/*    */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*    */ 
/*    */ import java.io.StringWriter;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.logging.LogRecord;
/*    */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*    */ import org.bukkit.craftbukkit.libs.joptsimple.OptionException;
/*    */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
/*    */ 
/*    */ public class ShortConsoleLogFormatter extends java.util.logging.Formatter
/*    */ {
/*    */   private final SimpleDateFormat date;
/*    */   
/*    */   public ShortConsoleLogFormatter(MinecraftServer server)
/*    */   {
/* 16 */     OptionSet options = server.options;
/* 17 */     SimpleDateFormat date = null;
/*    */     
/* 19 */     if (options.has("date-format")) {
/*    */       try {
/* 21 */         Object object = options.valueOf("date-format");
/*    */         
/* 23 */         if ((object == null) || (!(object instanceof SimpleDateFormat))) break label81;
/* 24 */         date = (SimpleDateFormat)object;
/*    */       }
/*    */       catch (OptionException localOptionException) {
/* 27 */         System.err.println("Given date format is not valid. Falling back to default.");
/*    */       }
/* 29 */     } else if (options.has("nojline")) {
/* 30 */       date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*    */     }
/*    */     label81:
/* 33 */     if (date == null) {
/* 34 */       date = new SimpleDateFormat("HH:mm:ss");
/*    */     }
/*    */     
/* 37 */     this.date = date;
/*    */   }
/*    */   
/*    */   public String format(LogRecord record)
/*    */   {
/* 42 */     StringBuilder builder = new StringBuilder();
/* 43 */     Throwable ex = record.getThrown();
/*    */     
/* 45 */     builder.append(this.date.format(Long.valueOf(record.getMillis())));
/* 46 */     builder.append(" [");
/* 47 */     builder.append(record.getLevel().getLocalizedName().toUpperCase());
/* 48 */     builder.append("] ");
/* 49 */     builder.append(formatMessage(record));
/* 50 */     builder.append('\n');
/*    */     
/* 52 */     if (ex != null) {
/* 53 */       StringWriter writer = new StringWriter();
/* 54 */       ex.printStackTrace(new java.io.PrintWriter(writer));
/* 55 */       builder.append(writer);
/*    */     }
/*    */     
/* 58 */     return builder.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\ShortConsoleLogFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */