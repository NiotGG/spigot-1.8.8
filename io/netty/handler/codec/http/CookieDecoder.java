/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ public final class CookieDecoder
/*     */ {
/*     */   private static final char COMMA = ',';
/*     */   
/*     */   public static Set<Cookie> decode(String paramString)
/*     */   {
/*  50 */     ArrayList localArrayList1 = new ArrayList(8);
/*  51 */     ArrayList localArrayList2 = new ArrayList(8);
/*  52 */     extractKeyValuePairs(paramString, localArrayList1, localArrayList2);
/*     */     
/*  54 */     if (localArrayList1.isEmpty()) {
/*  55 */       return Collections.emptySet();
/*     */     }
/*     */     
/*     */ 
/*  59 */     int i = 0;
/*     */     
/*     */     int j;
/*     */     
/*  63 */     if (((String)localArrayList1.get(0)).equalsIgnoreCase("Version")) {
/*     */       try {
/*  65 */         i = Integer.parseInt((String)localArrayList2.get(0));
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException1) {}
/*     */       
/*  69 */       j = 1;
/*     */     } else {
/*  71 */       j = 0;
/*     */     }
/*     */     
/*  74 */     if (localArrayList1.size() <= j)
/*     */     {
/*  76 */       return Collections.emptySet();
/*     */     }
/*     */     
/*  79 */     TreeSet localTreeSet = new TreeSet();
/*  80 */     for (; j < localArrayList1.size(); j++) {
/*  81 */       String str1 = (String)localArrayList1.get(j);
/*  82 */       String str2 = (String)localArrayList2.get(j);
/*  83 */       if (str2 == null) {
/*  84 */         str2 = "";
/*     */       }
/*     */       
/*  87 */       DefaultCookie localDefaultCookie = new DefaultCookie(str1, str2);
/*     */       
/*  89 */       boolean bool1 = false;
/*  90 */       boolean bool2 = false;
/*  91 */       boolean bool3 = false;
/*  92 */       String str3 = null;
/*  93 */       String str4 = null;
/*  94 */       String str5 = null;
/*  95 */       String str6 = null;
/*  96 */       long l1 = Long.MIN_VALUE;
/*  97 */       ArrayList localArrayList3 = new ArrayList(2);
/*     */       
/*  99 */       for (int k = j + 1; k < localArrayList1.size(); j++) {
/* 100 */         str1 = (String)localArrayList1.get(k);
/* 101 */         str2 = (String)localArrayList2.get(k);
/*     */         
/* 103 */         if ("Discard".equalsIgnoreCase(str1)) {
/* 104 */           bool1 = true;
/* 105 */         } else if ("Secure".equalsIgnoreCase(str1)) {
/* 106 */           bool2 = true;
/* 107 */         } else if ("HTTPOnly".equalsIgnoreCase(str1)) {
/* 108 */           bool3 = true;
/* 109 */         } else if ("Comment".equalsIgnoreCase(str1)) {
/* 110 */           str3 = str2;
/* 111 */         } else if ("CommentURL".equalsIgnoreCase(str1)) {
/* 112 */           str4 = str2;
/* 113 */         } else if ("Domain".equalsIgnoreCase(str1)) {
/* 114 */           str5 = str2;
/* 115 */         } else if ("Path".equalsIgnoreCase(str1)) {
/* 116 */           str6 = str2;
/* 117 */         } else if ("Expires".equalsIgnoreCase(str1)) {
/*     */           try {
/* 119 */             long l2 = HttpHeaderDateFormat.get().parse(str2).getTime() - System.currentTimeMillis();
/*     */             
/*     */ 
/*     */ 
/* 123 */             l1 = l2 / 1000L + (l2 % 1000L != 0L ? 1 : 0);
/*     */           }
/*     */           catch (ParseException localParseException) {}
/*     */         }
/* 127 */         else if ("Max-Age".equalsIgnoreCase(str1)) {
/* 128 */           l1 = Integer.parseInt(str2);
/* 129 */         } else if ("Version".equalsIgnoreCase(str1)) {
/* 130 */           i = Integer.parseInt(str2);
/* 131 */         } else { if (!"Port".equalsIgnoreCase(str1)) break;
/* 132 */           String[] arrayOfString1 = StringUtil.split(str2, ',');
/* 133 */           for (String str7 : arrayOfString1) {
/*     */             try {
/* 135 */               localArrayList3.add(Integer.valueOf(str7));
/*     */             }
/*     */             catch (NumberFormatException localNumberFormatException2) {}
/*     */           }
/*     */         }
/*  99 */         k++;
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 145 */       localDefaultCookie.setVersion(i);
/* 146 */       localDefaultCookie.setMaxAge(l1);
/* 147 */       localDefaultCookie.setPath(str6);
/* 148 */       localDefaultCookie.setDomain(str5);
/* 149 */       localDefaultCookie.setSecure(bool2);
/* 150 */       localDefaultCookie.setHttpOnly(bool3);
/* 151 */       if (i > 0) {
/* 152 */         localDefaultCookie.setComment(str3);
/*     */       }
/* 154 */       if (i > 1) {
/* 155 */         localDefaultCookie.setCommentUrl(str4);
/* 156 */         localDefaultCookie.setPorts(localArrayList3);
/* 157 */         localDefaultCookie.setDiscard(bool1);
/*     */       }
/*     */       
/* 160 */       localTreeSet.add(localDefaultCookie);
/*     */     }
/*     */     
/* 163 */     return localTreeSet;
/*     */   }
/*     */   
/*     */ 
/*     */   private static void extractKeyValuePairs(String paramString, List<String> paramList1, List<String> paramList2)
/*     */   {
/* 169 */     int i = paramString.length();
/* 170 */     int j = 0;
/*     */     
/*     */ 
/*     */ 
/* 174 */     while (j != i)
/*     */     {
/*     */ 
/* 177 */       switch (paramString.charAt(j)) {
/*     */       case '\t': case '\n': case '\013': case '\f': 
/*     */       case '\r': case ' ': case ',': case ';': 
/* 180 */         j++;
/* 181 */         break;
/*     */       
/*     */ 
/*     */ 
/*     */       default: 
/*     */         for (;;)
/*     */         {
/* 188 */           if (j == i) {
/*     */             return;
/*     */           }
/* 191 */           if (paramString.charAt(j) != '$') break;
/* 192 */           j++;
/*     */         }
/*     */         
/*     */ 
/*     */         String str1;
/*     */         
/*     */ 
/*     */         String str2;
/*     */         
/* 201 */         if (j == i) {
/* 202 */           str1 = null;
/* 203 */           str2 = null;
/*     */         } else {
/* 205 */           int k = j;
/*     */           do {
/* 207 */             switch (paramString.charAt(j))
/*     */             {
/*     */             case ';': 
/* 210 */               str1 = paramString.substring(k, j);
/* 211 */               str2 = null;
/* 212 */               break;
/*     */             
/*     */             case '=': 
/* 215 */               str1 = paramString.substring(k, j);
/* 216 */               j++;
/* 217 */               if (j == i)
/*     */               {
/* 219 */                 str2 = "";
/*     */               }
/*     */               else
/*     */               {
/* 223 */                 int m = j;
/* 224 */                 char c1 = paramString.charAt(j);
/* 225 */                 if ((c1 == '"') || (c1 == '\''))
/*     */                 {
/* 227 */                   StringBuilder localStringBuilder = new StringBuilder(paramString.length() - j);
/* 228 */                   char c2 = c1;
/* 229 */                   int i1 = 0;
/* 230 */                   j++;
/*     */                   for (;;) {
/* 232 */                     if (j == i) {
/* 233 */                       str2 = localStringBuilder.toString();
/* 234 */                       break;
/*     */                     }
/* 236 */                     if (i1 != 0) {
/* 237 */                       i1 = 0;
/* 238 */                       c1 = paramString.charAt(j++);
/* 239 */                       switch (c1) {
/*     */                       case '"': case '\'': 
/*     */                       case '\\': 
/* 242 */                         localStringBuilder.setCharAt(localStringBuilder.length() - 1, c1);
/* 243 */                         break;
/*     */                       
/*     */                       default: 
/* 246 */                         localStringBuilder.append(c1);break;
/*     */                       }
/*     */                     } else {
/* 249 */                       c1 = paramString.charAt(j++);
/* 250 */                       if (c1 == c2) {
/* 251 */                         str2 = localStringBuilder.toString();
/* 252 */                         break;
/*     */                       }
/* 254 */                       localStringBuilder.append(c1);
/* 255 */                       if (c1 == '\\') {
/* 256 */                         i1 = 1;
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 
/* 262 */                 int n = paramString.indexOf(';', j);
/* 263 */                 if (n > 0) {
/* 264 */                   str2 = paramString.substring(m, n);
/* 265 */                   j = n;
/*     */                 } else {
/* 267 */                   str2 = paramString.substring(m);
/* 268 */                   j = i;
/*     */                 }
/*     */               }
/* 271 */               break;
/*     */             default: 
/* 273 */               j++;
/*     */             }
/*     */             
/* 276 */           } while (j != i);
/*     */           
/* 278 */           str1 = paramString.substring(k);
/* 279 */           str2 = null;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 285 */         paramList1.add(str1);
/* 286 */         paramList2.add(str2);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\CookieDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */