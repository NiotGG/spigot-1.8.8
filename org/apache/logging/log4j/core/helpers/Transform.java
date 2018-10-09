/*     */ package org.apache.logging.log4j.core.helpers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Transform
/*     */ {
/*     */   private static final String CDATA_START = "<![CDATA[";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String CDATA_END = "]]>";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String CDATA_PSEUDO_END = "]]&gt;";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String CDATA_EMBEDED_END = "]]>]]&gt;<![CDATA[";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  29 */   private static final int CDATA_END_LEN = "]]>".length();
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
/*     */   public static String escapeHtmlTags(String paramString)
/*     */   {
/*  47 */     if ((Strings.isEmpty(paramString)) || ((paramString.indexOf('"') == -1) && (paramString.indexOf('&') == -1) && (paramString.indexOf('<') == -1) && (paramString.indexOf('>') == -1)))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*  52 */       return paramString;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  58 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length() + 6);
/*  59 */     char c = ' ';
/*     */     
/*  61 */     int i = paramString.length();
/*  62 */     for (int j = 0; j < i; j++) {
/*  63 */       c = paramString.charAt(j);
/*  64 */       if (c > '>') {
/*  65 */         localStringBuilder.append(c);
/*  66 */       } else if (c == '<') {
/*  67 */         localStringBuilder.append("&lt;");
/*  68 */       } else if (c == '>') {
/*  69 */         localStringBuilder.append("&gt;");
/*  70 */       } else if (c == '&') {
/*  71 */         localStringBuilder.append("&amp;");
/*  72 */       } else if (c == '"') {
/*  73 */         localStringBuilder.append("&quot;");
/*     */       } else {
/*  75 */         localStringBuilder.append(c);
/*     */       }
/*     */     }
/*  78 */     return localStringBuilder.toString();
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
/*     */   public static void appendEscapingCDATA(StringBuilder paramStringBuilder, String paramString)
/*     */   {
/*  91 */     if (paramString != null) {
/*  92 */       int i = paramString.indexOf("]]>");
/*  93 */       if (i < 0) {
/*  94 */         paramStringBuilder.append(paramString);
/*     */       } else {
/*  96 */         int j = 0;
/*  97 */         while (i > -1) {
/*  98 */           paramStringBuilder.append(paramString.substring(j, i));
/*  99 */           paramStringBuilder.append("]]>]]&gt;<![CDATA[");
/* 100 */           j = i + CDATA_END_LEN;
/* 101 */           if (j < paramString.length()) {
/* 102 */             i = paramString.indexOf("]]>", j);
/*     */           } else {
/* 104 */             return;
/*     */           }
/*     */         }
/* 107 */         paramStringBuilder.append(paramString.substring(j));
/*     */       }
/*     */     }
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
/*     */   public static String escapeJsonControlCharacters(String paramString)
/*     */   {
/* 125 */     if ((Strings.isEmpty(paramString)) || ((paramString.indexOf('"') == -1) && (paramString.indexOf('\\') == -1) && (paramString.indexOf('/') == -1) && (paramString.indexOf('\b') == -1) && (paramString.indexOf('\f') == -1) && (paramString.indexOf('\n') == -1) && (paramString.indexOf('\r') == -1) && (paramString.indexOf('\t') == -1)))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 134 */       return paramString;
/*     */     }
/*     */     
/* 137 */     StringBuilder localStringBuilder = new StringBuilder(paramString.length() + 6);
/*     */     
/* 139 */     int i = paramString.length();
/* 140 */     for (int j = 0; j < i; j++) {
/* 141 */       char c = paramString.charAt(j);
/* 142 */       String str = "\\\\";
/* 143 */       switch (c) {
/*     */       case '"': 
/* 145 */         localStringBuilder.append("\\\\");
/* 146 */         localStringBuilder.append(c);
/* 147 */         break;
/*     */       case '\\': 
/* 149 */         localStringBuilder.append("\\\\");
/* 150 */         localStringBuilder.append(c);
/* 151 */         break;
/*     */       case '/': 
/* 153 */         localStringBuilder.append("\\\\");
/* 154 */         localStringBuilder.append(c);
/* 155 */         break;
/*     */       case '\b': 
/* 157 */         localStringBuilder.append("\\\\");
/* 158 */         localStringBuilder.append('b');
/* 159 */         break;
/*     */       case '\f': 
/* 161 */         localStringBuilder.append("\\\\");
/* 162 */         localStringBuilder.append('f');
/* 163 */         break;
/*     */       case '\n': 
/* 165 */         localStringBuilder.append("\\\\");
/* 166 */         localStringBuilder.append('n');
/* 167 */         break;
/*     */       case '\r': 
/* 169 */         localStringBuilder.append("\\\\");
/* 170 */         localStringBuilder.append('r');
/* 171 */         break;
/*     */       case '\t': 
/* 173 */         localStringBuilder.append("\\\\");
/* 174 */         localStringBuilder.append('t');
/* 175 */         break;
/*     */       default: 
/* 177 */         localStringBuilder.append(c);
/*     */       }
/*     */     }
/* 180 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Transform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */