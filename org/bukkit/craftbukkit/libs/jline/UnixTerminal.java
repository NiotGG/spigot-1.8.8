/*     */ package org.bukkit.craftbukkit.libs.jline;
/*     */ 
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Log;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.TerminalLineSettings;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnixTerminal
/*     */   extends TerminalSupport
/*     */ {
/*  31 */   private final TerminalLineSettings settings = new TerminalLineSettings();
/*     */   
/*     */   public UnixTerminal() throws Exception {
/*  34 */     super(true);
/*     */   }
/*     */   
/*     */   protected TerminalLineSettings getSettings() {
/*  38 */     return this.settings;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void init()
/*     */     throws Exception
/*     */   {
/*  47 */     super.init();
/*     */     
/*  49 */     setAnsiSupported(true);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  54 */     this.settings.set("-icanon min 1 -icrnl -inlcr -ixon");
/*  55 */     this.settings.set("dsusp undef");
/*     */     
/*  57 */     setEchoEnabled(false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void restore()
/*     */     throws Exception
/*     */   {
/*  67 */     this.settings.restore();
/*  68 */     super.restore();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  76 */     int w = this.settings.getProperty("columns");
/*  77 */     return w < 1 ? 80 : w;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getHeight()
/*     */   {
/*  85 */     int h = this.settings.getProperty("rows");
/*  86 */     return h < 1 ? 24 : h;
/*     */   }
/*     */   
/*     */   public synchronized void setEchoEnabled(boolean enabled)
/*     */   {
/*     */     try {
/*  92 */       if (enabled) {
/*  93 */         this.settings.set("echo");
/*     */       }
/*     */       else {
/*  96 */         this.settings.set("-echo");
/*     */       }
/*  98 */       super.setEchoEnabled(enabled);
/*     */     }
/*     */     catch (Exception e) {
/* 101 */       if ((e instanceof InterruptedException)) {
/* 102 */         Thread.currentThread().interrupt();
/*     */       }
/* 104 */       Log.error(new Object[] { "Failed to ", enabled ? "enable" : "disable", " echo", e });
/*     */     }
/*     */   }
/*     */   
/*     */   public void disableInterruptCharacter()
/*     */   {
/*     */     try {
/* 111 */       this.settings.set("intr undef");
/*     */     }
/*     */     catch (Exception e) {
/* 114 */       if ((e instanceof InterruptedException)) {
/* 115 */         Thread.currentThread().interrupt();
/*     */       }
/* 117 */       Log.error(new Object[] { "Failed to disable interrupt character", e });
/*     */     }
/*     */   }
/*     */   
/*     */   public void enableInterruptCharacter()
/*     */   {
/*     */     try {
/* 124 */       this.settings.set("intr ^C");
/*     */     }
/*     */     catch (Exception e) {
/* 127 */       if ((e instanceof InterruptedException)) {
/* 128 */         Thread.currentThread().interrupt();
/*     */       }
/* 130 */       Log.error(new Object[] { "Failed to enable interrupt character", e });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\UnixTerminal.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */