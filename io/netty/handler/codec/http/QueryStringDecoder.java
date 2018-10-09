/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import java.net.URI;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QueryStringDecoder
/*     */ {
/*     */   private static final int DEFAULT_MAX_PARAMS = 1024;
/*     */   private final Charset charset;
/*     */   private final String uri;
/*     */   private final boolean hasPath;
/*     */   private final int maxParams;
/*     */   private String path;
/*     */   private Map<String, List<String>> params;
/*     */   private int nParams;
/*     */   
/*     */   public QueryStringDecoder(String paramString)
/*     */   {
/*  73 */     this(paramString, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QueryStringDecoder(String paramString, boolean paramBoolean)
/*     */   {
/*  81 */     this(paramString, HttpConstants.DEFAULT_CHARSET, paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QueryStringDecoder(String paramString, Charset paramCharset)
/*     */   {
/*  89 */     this(paramString, paramCharset, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QueryStringDecoder(String paramString, Charset paramCharset, boolean paramBoolean)
/*     */   {
/*  97 */     this(paramString, paramCharset, paramBoolean, 1024);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QueryStringDecoder(String paramString, Charset paramCharset, boolean paramBoolean, int paramInt)
/*     */   {
/* 105 */     if (paramString == null) {
/* 106 */       throw new NullPointerException("getUri");
/*     */     }
/* 108 */     if (paramCharset == null) {
/* 109 */       throw new NullPointerException("charset");
/*     */     }
/* 111 */     if (paramInt <= 0) {
/* 112 */       throw new IllegalArgumentException("maxParams: " + paramInt + " (expected: a positive integer)");
/*     */     }
/*     */     
/*     */ 
/* 116 */     this.uri = paramString;
/* 117 */     this.charset = paramCharset;
/* 118 */     this.maxParams = paramInt;
/* 119 */     this.hasPath = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QueryStringDecoder(URI paramURI)
/*     */   {
/* 127 */     this(paramURI, HttpConstants.DEFAULT_CHARSET);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QueryStringDecoder(URI paramURI, Charset paramCharset)
/*     */   {
/* 135 */     this(paramURI, paramCharset, 1024);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public QueryStringDecoder(URI paramURI, Charset paramCharset, int paramInt)
/*     */   {
/* 143 */     if (paramURI == null) {
/* 144 */       throw new NullPointerException("getUri");
/*     */     }
/* 146 */     if (paramCharset == null) {
/* 147 */       throw new NullPointerException("charset");
/*     */     }
/* 149 */     if (paramInt <= 0) {
/* 150 */       throw new IllegalArgumentException("maxParams: " + paramInt + " (expected: a positive integer)");
/*     */     }
/*     */     
/*     */ 
/* 154 */     String str = paramURI.getRawPath();
/* 155 */     if (str != null) {
/* 156 */       this.hasPath = true;
/*     */     } else {
/* 158 */       str = "";
/* 159 */       this.hasPath = false;
/*     */     }
/*     */     
/* 162 */     this.uri = (str + '?' + paramURI.getRawQuery());
/*     */     
/* 164 */     this.charset = paramCharset;
/* 165 */     this.maxParams = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String path()
/*     */   {
/* 172 */     if (this.path == null) {
/* 173 */       if (!this.hasPath) {
/* 174 */         return this.path = "";
/*     */       }
/*     */       
/* 177 */       int i = this.uri.indexOf('?');
/* 178 */       if (i < 0) {
/* 179 */         this.path = this.uri;
/*     */       } else {
/* 181 */         return this.path = this.uri.substring(0, i);
/*     */       }
/*     */     }
/* 184 */     return this.path;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Map<String, List<String>> parameters()
/*     */   {
/* 191 */     if (this.params == null) {
/* 192 */       if (this.hasPath) {
/* 193 */         int i = path().length();
/* 194 */         if (this.uri.length() == i) {
/* 195 */           return Collections.emptyMap();
/*     */         }
/* 197 */         decodeParams(this.uri.substring(i + 1));
/*     */       } else {
/* 199 */         if (this.uri.isEmpty()) {
/* 200 */           return Collections.emptyMap();
/*     */         }
/* 202 */         decodeParams(this.uri);
/*     */       }
/*     */     }
/* 205 */     return this.params;
/*     */   }
/*     */   
/*     */   private void decodeParams(String paramString) {
/* 209 */     LinkedHashMap localLinkedHashMap = this.params = new LinkedHashMap();
/* 210 */     this.nParams = 0;
/* 211 */     String str = null;
/* 212 */     int i = 0;
/*     */     
/*     */ 
/* 215 */     for (int j = 0; j < paramString.length(); j++) {
/* 216 */       int k = paramString.charAt(j);
/* 217 */       if ((k == 61) && (str == null)) {
/* 218 */         if (i != j) {
/* 219 */           str = decodeComponent(paramString.substring(i, j), this.charset);
/*     */         }
/* 221 */         i = j + 1;
/*     */       }
/* 223 */       else if ((k == 38) || (k == 59)) {
/* 224 */         if ((str == null) && (i != j))
/*     */         {
/*     */ 
/*     */ 
/* 228 */           if (addParam(localLinkedHashMap, decodeComponent(paramString.substring(i, j), this.charset), "")) {}
/*     */ 
/*     */         }
/* 231 */         else if (str != null) {
/* 232 */           if (!addParam(localLinkedHashMap, str, decodeComponent(paramString.substring(i, j), this.charset))) {
/* 233 */             return;
/*     */           }
/* 235 */           str = null;
/*     */         }
/* 237 */         i = j + 1;
/*     */       }
/*     */     }
/*     */     
/* 241 */     if (i != j) {
/* 242 */       if (str == null) {
/* 243 */         addParam(localLinkedHashMap, decodeComponent(paramString.substring(i, j), this.charset), "");
/*     */       } else {
/* 245 */         addParam(localLinkedHashMap, str, decodeComponent(paramString.substring(i, j), this.charset));
/*     */       }
/* 247 */     } else if (str != null) {
/* 248 */       addParam(localLinkedHashMap, str, "");
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean addParam(Map<String, List<String>> paramMap, String paramString1, String paramString2) {
/* 253 */     if (this.nParams >= this.maxParams) {
/* 254 */       return false;
/*     */     }
/*     */     
/* 257 */     Object localObject = (List)paramMap.get(paramString1);
/* 258 */     if (localObject == null) {
/* 259 */       localObject = new ArrayList(1);
/* 260 */       paramMap.put(paramString1, localObject);
/*     */     }
/* 262 */     ((List)localObject).add(paramString2);
/* 263 */     this.nParams += 1;
/* 264 */     return true;
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
/*     */   public static String decodeComponent(String paramString)
/*     */   {
/* 279 */     return decodeComponent(paramString, HttpConstants.DEFAULT_CHARSET);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String decodeComponent(String paramString, Charset paramCharset)
/*     */   {
/* 305 */     if (paramString == null) {
/* 306 */       return "";
/*     */     }
/* 308 */     int i = paramString.length();
/* 309 */     int j = 0;
/* 310 */     for (int k = 0; k < i; k++) {
/* 311 */       m = paramString.charAt(k);
/* 312 */       if ((m == 37) || (m == 43)) {
/* 313 */         j = 1;
/* 314 */         break;
/*     */       }
/*     */     }
/* 317 */     if (j == 0) {
/* 318 */       return paramString;
/*     */     }
/* 320 */     byte[] arrayOfByte = new byte[i];
/* 321 */     int m = 0;
/* 322 */     for (int n = 0; n < i; n++) {
/* 323 */       int i1 = paramString.charAt(n);
/* 324 */       int i2; switch (i1) {
/*     */       case 43: 
/* 326 */         arrayOfByte[(m++)] = 32;
/* 327 */         break;
/*     */       case 37: 
/* 329 */         if (n == i - 1) {
/* 330 */           throw new IllegalArgumentException("unterminated escape sequence at end of string: " + paramString);
/*     */         }
/*     */         
/* 333 */         i1 = paramString.charAt(++n);
/* 334 */         if (i1 == 37) {
/* 335 */           arrayOfByte[(m++)] = 37;
/*     */         }
/*     */         else {
/* 338 */           if (n == i - 1) {
/* 339 */             throw new IllegalArgumentException("partial escape sequence at end of string: " + paramString);
/*     */           }
/*     */           
/* 342 */           i1 = decodeHexNibble(i1);
/* 343 */           int i3 = decodeHexNibble(paramString.charAt(++n));
/* 344 */           if ((i1 == 65535) || (i3 == 65535)) {
/* 345 */             throw new IllegalArgumentException("invalid escape sequence `%" + paramString.charAt(n - 1) + paramString.charAt(n) + "' at index " + (n - 2) + " of: " + paramString);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 350 */           i2 = (char)(i1 * 16 + i3);
/*     */         }
/*     */         break;
/* 353 */       default:  arrayOfByte[(m++)] = ((byte)i2);
/*     */       }
/*     */       
/*     */     }
/* 357 */     return new String(arrayOfByte, 0, m, paramCharset);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static char decodeHexNibble(char paramChar)
/*     */   {
/* 368 */     if (('0' <= paramChar) && (paramChar <= '9'))
/* 369 */       return (char)(paramChar - '0');
/* 370 */     if (('a' <= paramChar) && (paramChar <= 'f'))
/* 371 */       return (char)(paramChar - 'a' + 10);
/* 372 */     if (('A' <= paramChar) && (paramChar <= 'F')) {
/* 373 */       return (char)(paramChar - 'A' + 10);
/*     */     }
/* 375 */     return 65535;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\QueryStringDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */