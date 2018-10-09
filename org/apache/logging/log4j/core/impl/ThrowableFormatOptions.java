/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Scanner;
/*     */ import org.apache.logging.log4j.core.helpers.Constants;
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
/*     */ public final class ThrowableFormatOptions
/*     */ {
/*     */   private static final int DEFAULT_LINES = Integer.MAX_VALUE;
/*  35 */   protected static final ThrowableFormatOptions DEFAULT = new ThrowableFormatOptions();
/*     */   
/*     */ 
/*     */ 
/*     */   private static final String FULL = "full";
/*     */   
/*     */ 
/*     */ 
/*     */   private static final String NONE = "none";
/*     */   
/*     */ 
/*     */ 
/*     */   private static final String SHORT = "short";
/*     */   
/*     */ 
/*     */ 
/*     */   private final int lines;
/*     */   
/*     */ 
/*     */   private final String separator;
/*     */   
/*     */ 
/*     */   private final List<String> packages;
/*     */   
/*     */ 
/*     */   public static final String CLASS_NAME = "short.className";
/*     */   
/*     */ 
/*     */   public static final String METHOD_NAME = "short.methodName";
/*     */   
/*     */ 
/*     */   public static final String LINE_NUMBER = "short.lineNumber";
/*     */   
/*     */ 
/*     */   public static final String FILE_NAME = "short.fileName";
/*     */   
/*     */ 
/*     */   public static final String MESSAGE = "short.message";
/*     */   
/*     */ 
/*     */   public static final String LOCALIZED_MESSAGE = "short.localizedMessage";
/*     */   
/*     */ 
/*     */ 
/*     */   protected ThrowableFormatOptions(int paramInt, String paramString, List<String> paramList)
/*     */   {
/*  81 */     this.lines = paramInt;
/*  82 */     this.separator = (paramString == null ? Constants.LINE_SEP : paramString);
/*  83 */     this.packages = paramList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ThrowableFormatOptions(List<String> paramList)
/*     */   {
/*  91 */     this(Integer.MAX_VALUE, null, paramList);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected ThrowableFormatOptions()
/*     */   {
/*  98 */     this(Integer.MAX_VALUE, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getLines()
/*     */   {
/* 106 */     return this.lines;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getSeparator()
/*     */   {
/* 114 */     return this.separator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<String> getPackages()
/*     */   {
/* 122 */     return this.packages;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean allLines()
/*     */   {
/* 130 */     return this.lines == Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean anyLines()
/*     */   {
/* 138 */     return this.lines > 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int minLines(int paramInt)
/*     */   {
/* 147 */     return this.lines > paramInt ? paramInt : this.lines;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasPackages()
/*     */   {
/* 155 */     return (this.packages != null) && (!this.packages.isEmpty());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 163 */     StringBuilder localStringBuilder = new StringBuilder();
/* 164 */     localStringBuilder.append("{").append(anyLines() ? String.valueOf(this.lines) : this.lines == 2 ? "short" : allLines() ? "full" : "none").append("}");
/* 165 */     localStringBuilder.append("{separator(").append(this.separator).append(")}");
/* 166 */     if (hasPackages()) {
/* 167 */       localStringBuilder.append("{filters(");
/* 168 */       for (String str : this.packages) {
/* 169 */         localStringBuilder.append(str).append(",");
/*     */       }
/* 171 */       localStringBuilder.deleteCharAt(localStringBuilder.length() - 1);
/* 172 */       localStringBuilder.append(")}");
/*     */     }
/* 174 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ThrowableFormatOptions newInstance(String[] paramArrayOfString)
/*     */   {
/* 182 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/* 183 */       return DEFAULT;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 191 */     if ((paramArrayOfString.length == 1) && (paramArrayOfString[0] != null) && (paramArrayOfString[0].length() > 0)) {
/* 192 */       String[] arrayOfString1 = paramArrayOfString[0].split(",", 2);
/* 193 */       str1 = arrayOfString1[0].trim();
/* 194 */       localObject = new Scanner(str1);
/* 195 */       if ((arrayOfString1.length > 1) && ((str1.equalsIgnoreCase("full")) || (str1.equalsIgnoreCase("short")) || (str1.equalsIgnoreCase("none")) || (((Scanner)localObject).hasNextInt()))) {
/* 196 */         paramArrayOfString = new String[] { str1, arrayOfString1[1].trim() };
/*     */       }
/* 198 */       ((Scanner)localObject).close();
/*     */     }
/*     */     
/* 201 */     int i = DEFAULT.lines;
/* 202 */     String str1 = DEFAULT.separator;
/* 203 */     Object localObject = DEFAULT.packages;
/* 204 */     for (String str2 : paramArrayOfString) {
/* 205 */       if (str2 != null) {
/* 206 */         String str3 = str2.trim();
/* 207 */         if (!str3.isEmpty())
/*     */         {
/* 209 */           if ((str3.startsWith("separator(")) && (str3.endsWith(")"))) {
/* 210 */             str1 = str3.substring("separator(".length(), str3.length() - 1);
/* 211 */           } else if ((str3.startsWith("filters(")) && (str3.endsWith(")"))) {
/* 212 */             String str4 = str3.substring("filters(".length(), str3.length() - 1);
/* 213 */             if (str4.length() > 0) {
/* 214 */               String[] arrayOfString3 = str4.split(",");
/* 215 */               if (arrayOfString3.length > 0) {
/* 216 */                 localObject = new ArrayList(arrayOfString3.length);
/* 217 */                 for (String str5 : arrayOfString3) {
/* 218 */                   str5 = str5.trim();
/* 219 */                   if (str5.length() > 0) {
/* 220 */                     ((List)localObject).add(str5);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/* 225 */           } else if (str3.equalsIgnoreCase("none")) {
/* 226 */             i = 0;
/* 227 */           } else if ((str3.equalsIgnoreCase("short")) || (str3.equalsIgnoreCase("short.className")) || (str3.equalsIgnoreCase("short.methodName")) || (str3.equalsIgnoreCase("short.lineNumber")) || (str3.equalsIgnoreCase("short.fileName")) || (str3.equalsIgnoreCase("short.message")) || (str3.equalsIgnoreCase("short.localizedMessage")))
/*     */           {
/*     */ 
/*     */ 
/* 231 */             i = 2;
/* 232 */           } else if (!str3.equalsIgnoreCase("full")) {
/* 233 */             i = Integer.parseInt(str3);
/*     */           } }
/*     */       }
/*     */     }
/* 237 */     return new ThrowableFormatOptions(i, str1, (List)localObject);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\ThrowableFormatOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */