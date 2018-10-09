/*     */ package org.apache.commons.lang3;
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
/*     */ public enum JavaVersion
/*     */ {
/*  32 */   JAVA_0_9(1.5F, "0.9"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  37 */   JAVA_1_1(1.1F, "1.1"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  42 */   JAVA_1_2(1.2F, "1.2"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  47 */   JAVA_1_3(1.3F, "1.3"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  52 */   JAVA_1_4(1.4F, "1.4"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  57 */   JAVA_1_5(1.5F, "1.5"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  62 */   JAVA_1_6(1.6F, "1.6"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  67 */   JAVA_1_7(1.7F, "1.7"), 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  72 */   JAVA_1_8(1.8F, "1.8");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final float value;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String name;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private JavaVersion(float paramFloat, String paramString)
/*     */   {
/*  90 */     this.value = paramFloat;
/*  91 */     this.name = paramString;
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
/*     */   public boolean atLeast(JavaVersion paramJavaVersion)
/*     */   {
/* 105 */     return this.value >= paramJavaVersion.value;
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
/*     */   static JavaVersion getJavaVersion(String paramString)
/*     */   {
/* 119 */     return get(paramString);
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
/*     */   static JavaVersion get(String paramString)
/*     */   {
/* 132 */     if ("0.9".equals(paramString))
/* 133 */       return JAVA_0_9;
/* 134 */     if ("1.1".equals(paramString))
/* 135 */       return JAVA_1_1;
/* 136 */     if ("1.2".equals(paramString))
/* 137 */       return JAVA_1_2;
/* 138 */     if ("1.3".equals(paramString))
/* 139 */       return JAVA_1_3;
/* 140 */     if ("1.4".equals(paramString))
/* 141 */       return JAVA_1_4;
/* 142 */     if ("1.5".equals(paramString))
/* 143 */       return JAVA_1_5;
/* 144 */     if ("1.6".equals(paramString))
/* 145 */       return JAVA_1_6;
/* 146 */     if ("1.7".equals(paramString))
/* 147 */       return JAVA_1_7;
/* 148 */     if ("1.8".equals(paramString)) {
/* 149 */       return JAVA_1_8;
/*     */     }
/* 151 */     return null;
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
/*     */   public String toString()
/*     */   {
/* 165 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\JavaVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */