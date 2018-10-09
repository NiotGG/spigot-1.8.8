/*     */ package org.bukkit.craftbukkit.libs.jline.internal;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Log
/*     */ {
/*     */   public static enum Level
/*     */   {
/*  27 */     TRACE, 
/*  28 */     DEBUG, 
/*  29 */     INFO, 
/*  30 */     WARN, 
/*  31 */     ERROR;
/*     */     
/*     */     private Level() {} }
/*     */   
/*  35 */   public static final boolean TRACE = Boolean.getBoolean(Log.class.getName() + ".trace");
/*     */   
/*     */ 
/*  38 */   public static final boolean DEBUG = (TRACE) || (Boolean.getBoolean(Log.class.getName() + ".debug"));
/*     */   
/*  40 */   private static PrintStream output = System.err;
/*     */   
/*     */   public static PrintStream getOutput() {
/*  43 */     return output;
/*     */   }
/*     */   
/*     */   public static void setOutput(PrintStream out) {
/*  47 */     output = (PrintStream)Preconditions.checkNotNull(out);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @TestAccessible
/*     */   static void render(PrintStream out, Object message)
/*     */   {
/*  55 */     if (message.getClass().isArray()) {
/*  56 */       Object[] array = (Object[])message;
/*     */       
/*  58 */       out.print("[");
/*  59 */       for (int i = 0; i < array.length; i++) {
/*  60 */         out.print(array[i]);
/*  61 */         if (i + 1 < array.length) {
/*  62 */           out.print(",");
/*     */         }
/*     */       }
/*  65 */       out.print("]");
/*     */     }
/*     */     else {
/*  68 */       out.print(message);
/*     */     }
/*     */   }
/*     */   
/*     */   @TestAccessible
/*     */   static void log(Level level, Object... messages)
/*     */   {
/*  75 */     synchronized (output) {
/*  76 */       output.format("[%s] ", new Object[] { level });
/*     */       
/*  78 */       for (int i = 0; i < messages.length; i++)
/*     */       {
/*  80 */         if ((i + 1 == messages.length) && ((messages[i] instanceof Throwable))) {
/*  81 */           output.println();
/*  82 */           ((Throwable)messages[i]).printStackTrace(output);
/*     */         }
/*     */         else {
/*  85 */           render(output, messages[i]);
/*     */         }
/*     */       }
/*     */       
/*  89 */       output.println();
/*  90 */       output.flush();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void trace(Object... messages) {
/*  95 */     if (TRACE) {
/*  96 */       log(Level.TRACE, messages);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void debug(Object... messages) {
/* 101 */     if ((TRACE) || (DEBUG)) {
/* 102 */       log(Level.DEBUG, messages);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void info(Object... messages)
/*     */   {
/* 110 */     log(Level.INFO, messages);
/*     */   }
/*     */   
/*     */   public static void warn(Object... messages) {
/* 114 */     log(Level.WARN, messages);
/*     */   }
/*     */   
/*     */   public static void error(Object... messages) {
/* 118 */     log(Level.ERROR, messages);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\Log.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */