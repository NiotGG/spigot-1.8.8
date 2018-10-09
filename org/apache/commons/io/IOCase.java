/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public final class IOCase
/*     */   implements Serializable
/*     */ {
/*  42 */   public static final IOCase SENSITIVE = new IOCase("Sensitive", true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  47 */   public static final IOCase INSENSITIVE = new IOCase("Insensitive", false);
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
/*  61 */   public static final IOCase SYSTEM = new IOCase("System", !FilenameUtils.isSystemWindows());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = -6343169151696340687L;
/*     */   
/*     */ 
/*     */ 
/*     */   private final String name;
/*     */   
/*     */ 
/*     */ 
/*     */   private final transient boolean sensitive;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static IOCase forName(String paramString)
/*     */   {
/*  81 */     if (SENSITIVE.name.equals(paramString)) {
/*  82 */       return SENSITIVE;
/*     */     }
/*  84 */     if (INSENSITIVE.name.equals(paramString)) {
/*  85 */       return INSENSITIVE;
/*     */     }
/*  87 */     if (SYSTEM.name.equals(paramString)) {
/*  88 */       return SYSTEM;
/*     */     }
/*  90 */     throw new IllegalArgumentException("Invalid IOCase name: " + paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private IOCase(String paramString, boolean paramBoolean)
/*     */   {
/* 101 */     this.name = paramString;
/* 102 */     this.sensitive = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Object readResolve()
/*     */   {
/* 112 */     return forName(this.name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 122 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCaseSensitive()
/*     */   {
/* 131 */     return this.sensitive;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public int checkCompareTo(String paramString1, String paramString2)
/*     */   {
/* 147 */     if ((paramString1 == null) || (paramString2 == null)) {
/* 148 */       throw new NullPointerException("The strings must not be null");
/*     */     }
/* 150 */     return this.sensitive ? paramString1.compareTo(paramString2) : paramString1.compareToIgnoreCase(paramString2);
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
/*     */ 
/*     */ 
/*     */   public boolean checkEquals(String paramString1, String paramString2)
/*     */   {
/* 165 */     if ((paramString1 == null) || (paramString2 == null)) {
/* 166 */       throw new NullPointerException("The strings must not be null");
/*     */     }
/* 168 */     return this.sensitive ? paramString1.equals(paramString2) : paramString1.equalsIgnoreCase(paramString2);
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
/*     */ 
/*     */ 
/*     */   public boolean checkStartsWith(String paramString1, String paramString2)
/*     */   {
/* 183 */     return paramString1.regionMatches(!this.sensitive, 0, paramString2, 0, paramString2.length());
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
/*     */ 
/*     */ 
/*     */   public boolean checkEndsWith(String paramString1, String paramString2)
/*     */   {
/* 198 */     int i = paramString2.length();
/* 199 */     return paramString1.regionMatches(!this.sensitive, paramString1.length() - i, paramString2, 0, i);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int checkIndexOf(String paramString1, int paramInt, String paramString2)
/*     */   {
/* 218 */     int i = paramString1.length() - paramString2.length();
/* 219 */     if (i >= paramInt) {
/* 220 */       for (int j = paramInt; j <= i; j++) {
/* 221 */         if (checkRegionMatches(paramString1, j, paramString2)) {
/* 222 */           return j;
/*     */         }
/*     */       }
/*     */     }
/* 226 */     return -1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean checkRegionMatches(String paramString1, int paramInt, String paramString2)
/*     */   {
/* 242 */     return paramString1.regionMatches(!this.sensitive, paramInt, paramString2, 0, paramString2.length());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 253 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\IOCase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */