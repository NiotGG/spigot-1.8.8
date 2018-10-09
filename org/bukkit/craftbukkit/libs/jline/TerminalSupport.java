/*     */ package org.bukkit.craftbukkit.libs.jline;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.ShutdownHooks;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.ShutdownHooks.Task;
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
/*     */ 
/*     */ public abstract class TerminalSupport
/*     */   implements Terminal
/*     */ {
/*     */   public static final int DEFAULT_WIDTH = 80;
/*     */   public static final int DEFAULT_HEIGHT = 24;
/*     */   private ShutdownHooks.Task shutdownTask;
/*     */   private boolean supported;
/*     */   private boolean echoEnabled;
/*     */   private boolean ansiSupported;
/*     */   
/*     */   protected TerminalSupport(boolean supported)
/*     */   {
/*  41 */     this.supported = supported;
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/*  45 */     if (this.shutdownTask != null) {
/*  46 */       ShutdownHooks.remove(this.shutdownTask);
/*     */     }
/*     */     
/*  49 */     this.shutdownTask = ShutdownHooks.add(new ShutdownHooks.Task()
/*     */     {
/*     */       public void run() throws Exception {
/*  52 */         TerminalSupport.this.restore();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void restore() throws Exception {
/*  58 */     TerminalFactory.resetIf(this);
/*  59 */     if (this.shutdownTask != null) {
/*  60 */       ShutdownHooks.remove(this.shutdownTask);
/*  61 */       this.shutdownTask = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() throws Exception {
/*  66 */     restore();
/*  67 */     init();
/*     */   }
/*     */   
/*     */   public final boolean isSupported() {
/*  71 */     return this.supported;
/*     */   }
/*     */   
/*     */   public synchronized boolean isAnsiSupported() {
/*  75 */     return this.ansiSupported;
/*     */   }
/*     */   
/*     */   protected synchronized void setAnsiSupported(boolean supported) {
/*  79 */     this.ansiSupported = supported;
/*  80 */     Log.debug(new Object[] { "Ansi supported: ", Boolean.valueOf(supported) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public OutputStream wrapOutIfNeeded(OutputStream out)
/*     */   {
/*  88 */     return out;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean hasWeirdWrap()
/*     */   {
/*  95 */     return true;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  99 */     return 80;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 103 */     return 24;
/*     */   }
/*     */   
/*     */   public synchronized boolean isEchoEnabled() {
/* 107 */     return this.echoEnabled;
/*     */   }
/*     */   
/*     */   public synchronized void setEchoEnabled(boolean enabled) {
/* 111 */     this.echoEnabled = enabled;
/* 112 */     Log.debug(new Object[] { "Echo enabled: ", Boolean.valueOf(enabled) });
/*     */   }
/*     */   
/*     */   public InputStream wrapInIfNeeded(InputStream in) throws IOException {
/* 116 */     return in;
/*     */   }
/*     */   
/*     */   public String getOutputEncoding()
/*     */   {
/* 121 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\TerminalSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */