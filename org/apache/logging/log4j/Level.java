/*     */ package org.apache.logging.log4j;
/*     */ 
/*     */ import java.util.Locale;
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
/*     */ public enum Level
/*     */ {
/*  43 */   OFF(0), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  48 */   FATAL(1), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  53 */   ERROR(2), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  58 */   WARN(3), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  63 */   INFO(4), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  68 */   DEBUG(5), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  73 */   TRACE(6), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  78 */   ALL(Integer.MAX_VALUE);
/*     */   
/*     */   private final int intLevel;
/*     */   
/*     */   private Level(int paramInt) {
/*  83 */     this.intLevel = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Level toLevel(String paramString)
/*     */   {
/*  94 */     return toLevel(paramString, DEBUG);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Level toLevel(String paramString, Level paramLevel)
/*     */   {
/* 107 */     if (paramString == null) {
/* 108 */       return paramLevel;
/*     */     }
/* 110 */     String str = paramString.toUpperCase(Locale.ENGLISH);
/* 111 */     for (Level localLevel : values()) {
/* 112 */       if (localLevel.name().equals(str)) {
/* 113 */         return localLevel;
/*     */       }
/*     */     }
/* 116 */     return paramLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAtLeastAsSpecificAs(Level paramLevel)
/*     */   {
/* 127 */     return this.intLevel <= paramLevel.intLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAtLeastAsSpecificAs(int paramInt)
/*     */   {
/* 138 */     return this.intLevel <= paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean lessOrEqual(Level paramLevel)
/*     */   {
/* 147 */     return this.intLevel <= paramLevel.intLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean lessOrEqual(int paramInt)
/*     */   {
/* 156 */     return this.intLevel <= paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int intLevel()
/*     */   {
/* 164 */     return this.intLevel;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\Level.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */