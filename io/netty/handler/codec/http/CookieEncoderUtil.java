/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.util.internal.InternalThreadLocalMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class CookieEncoderUtil
/*    */ {
/*    */   static StringBuilder stringBuilder()
/*    */   {
/* 24 */     return InternalThreadLocalMap.get().stringBuilder();
/*    */   }
/*    */   
/*    */   static String stripTrailingSeparator(StringBuilder paramStringBuilder) {
/* 28 */     if (paramStringBuilder.length() > 0) {
/* 29 */       paramStringBuilder.setLength(paramStringBuilder.length() - 2);
/*    */     }
/* 31 */     return paramStringBuilder.toString();
/*    */   }
/*    */   
/*    */   static void add(StringBuilder paramStringBuilder, String paramString1, String paramString2) {
/* 35 */     if (paramString2 == null) {
/* 36 */       addQuoted(paramStringBuilder, paramString1, "");
/* 37 */       return;
/*    */     }
/*    */     
/* 40 */     for (int i = 0; i < paramString2.length(); i++) {
/* 41 */       int j = paramString2.charAt(i);
/* 42 */       switch (j) {
/*    */       case 9: case 32: case 34: case 40: case 41: 
/*    */       case 44: case 47: case 58: case 59: case 60: 
/*    */       case 61: case 62: case 63: case 64: case 91: 
/*    */       case 92: case 93: case 123: case 125: 
/* 47 */         addQuoted(paramStringBuilder, paramString1, paramString2);
/* 48 */         return;
/*    */       }
/*    */       
/*    */     }
/* 52 */     addUnquoted(paramStringBuilder, paramString1, paramString2);
/*    */   }
/*    */   
/*    */   static void addUnquoted(StringBuilder paramStringBuilder, String paramString1, String paramString2) {
/* 56 */     paramStringBuilder.append(paramString1);
/* 57 */     paramStringBuilder.append('=');
/* 58 */     paramStringBuilder.append(paramString2);
/* 59 */     paramStringBuilder.append(';');
/* 60 */     paramStringBuilder.append(' ');
/*    */   }
/*    */   
/*    */   static void addQuoted(StringBuilder paramStringBuilder, String paramString1, String paramString2) {
/* 64 */     if (paramString2 == null) {
/* 65 */       paramString2 = "";
/*    */     }
/*    */     
/* 68 */     paramStringBuilder.append(paramString1);
/* 69 */     paramStringBuilder.append('=');
/* 70 */     paramStringBuilder.append('"');
/* 71 */     paramStringBuilder.append(paramString2.replace("\\", "\\\\").replace("\"", "\\\""));
/* 72 */     paramStringBuilder.append('"');
/* 73 */     paramStringBuilder.append(';');
/* 74 */     paramStringBuilder.append(' ');
/*    */   }
/*    */   
/*    */   static void add(StringBuilder paramStringBuilder, String paramString, long paramLong) {
/* 78 */     paramStringBuilder.append(paramString);
/* 79 */     paramStringBuilder.append('=');
/* 80 */     paramStringBuilder.append(paramLong);
/* 81 */     paramStringBuilder.append(';');
/* 82 */     paramStringBuilder.append(' ');
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\CookieEncoderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */