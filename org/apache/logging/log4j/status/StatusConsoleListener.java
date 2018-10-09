/*     */ package org.apache.logging.log4j.status;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public class StatusConsoleListener
/*     */   implements StatusListener
/*     */ {
/*     */   private static final String STATUS_LEVEL = "org.apache.logging.log4j.StatusLevel";
/*  31 */   private Level level = Level.FATAL;
/*     */   
/*  33 */   private String[] filters = null;
/*     */   
/*     */ 
/*     */ 
/*     */   private final PrintStream stream;
/*     */   
/*     */ 
/*     */ 
/*     */   public StatusConsoleListener()
/*     */   {
/*  43 */     String str = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.StatusLevel");
/*  44 */     if (str != null) {
/*  45 */       this.level = Level.toLevel(str, Level.FATAL);
/*     */     }
/*  47 */     this.stream = System.out;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public StatusConsoleListener(Level paramLevel)
/*     */   {
/*  55 */     this.level = paramLevel;
/*  56 */     this.stream = System.out;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public StatusConsoleListener(Level paramLevel, PrintStream paramPrintStream)
/*     */   {
/*  65 */     this.level = paramLevel;
/*  66 */     this.stream = paramPrintStream;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLevel(Level paramLevel)
/*     */   {
/*  74 */     this.level = paramLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Level getStatusLevel()
/*     */   {
/*  83 */     return this.level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void log(StatusData paramStatusData)
/*     */   {
/*  92 */     if (!filtered(paramStatusData)) {
/*  93 */       this.stream.println(paramStatusData.getFormattedStatus());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFilters(String... paramVarArgs)
/*     */   {
/* 102 */     this.filters = paramVarArgs;
/*     */   }
/*     */   
/*     */   private boolean filtered(StatusData paramStatusData) {
/* 106 */     if (this.filters == null) {
/* 107 */       return false;
/*     */     }
/* 109 */     String str1 = paramStatusData.getStackTraceElement().getClassName();
/* 110 */     for (String str2 : this.filters) {
/* 111 */       if (str1.startsWith(str2)) {
/* 112 */         return true;
/*     */       }
/*     */     }
/* 115 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\status\StatusConsoleListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */