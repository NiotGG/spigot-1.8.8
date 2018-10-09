/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Formatter;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class StringUtil
/*     */ {
/*     */   public static final String NEWLINE;
/*     */   private static final String[] BYTE2HEX_PAD;
/*     */   private static final String[] BYTE2HEX_NOPAD;
/*     */   private static final String EMPTY_STRING = "";
/*     */   
/*     */   static
/*     */   {
/*  30 */     BYTE2HEX_PAD = new String['Ā'];
/*  31 */     BYTE2HEX_NOPAD = new String['Ā'];
/*     */     
/*     */ 
/*     */     String str1;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  39 */       str1 = new Formatter().format("%n", new Object[0]).toString();
/*     */     }
/*     */     catch (Exception localException) {
/*  42 */       str1 = "\n";
/*     */     }
/*     */     
/*  45 */     NEWLINE = str1;
/*     */     
/*     */     StringBuilder localStringBuilder;
/*     */     
/*  49 */     for (int i = 0; i < 10; i++) {
/*  50 */       localStringBuilder = new StringBuilder(2);
/*  51 */       localStringBuilder.append('0');
/*  52 */       localStringBuilder.append(i);
/*  53 */       BYTE2HEX_PAD[i] = localStringBuilder.toString();
/*  54 */       BYTE2HEX_NOPAD[i] = String.valueOf(i);
/*     */     }
/*  56 */     for (; i < 16; i++) {
/*  57 */       localStringBuilder = new StringBuilder(2);
/*  58 */       char c = (char)(97 + i - 10);
/*  59 */       localStringBuilder.append('0');
/*  60 */       localStringBuilder.append(c);
/*  61 */       BYTE2HEX_PAD[i] = localStringBuilder.toString();
/*  62 */       BYTE2HEX_NOPAD[i] = String.valueOf(c);
/*     */     }
/*  64 */     for (; i < BYTE2HEX_PAD.length; i++) {
/*  65 */       localStringBuilder = new StringBuilder(2);
/*  66 */       localStringBuilder.append(Integer.toHexString(i));
/*  67 */       String str2 = localStringBuilder.toString();
/*  68 */       BYTE2HEX_PAD[i] = str2;
/*  69 */       BYTE2HEX_NOPAD[i] = str2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String[] split(String paramString, char paramChar)
/*     */   {
/*  78 */     int i = paramString.length();
/*  79 */     ArrayList localArrayList = new ArrayList();
/*     */     
/*  81 */     int j = 0;
/*  82 */     for (int k = 0; k < i; k++) {
/*  83 */       if (paramString.charAt(k) == paramChar) {
/*  84 */         if (j == k) {
/*  85 */           localArrayList.add("");
/*     */         } else {
/*  87 */           localArrayList.add(paramString.substring(j, k));
/*     */         }
/*  89 */         j = k + 1;
/*     */       }
/*     */     }
/*     */     
/*  93 */     if (j == 0) {
/*  94 */       localArrayList.add(paramString);
/*     */     }
/*  96 */     else if (j != i)
/*     */     {
/*  98 */       localArrayList.add(paramString.substring(j, i));
/*     */     }
/*     */     else {
/* 101 */       for (k = localArrayList.size() - 1; k >= 0; k--) {
/* 102 */         if (!((String)localArrayList.get(k)).isEmpty()) break;
/* 103 */         localArrayList.remove(k);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 111 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String byteToHexStringPadded(int paramInt)
/*     */   {
/* 118 */     return BYTE2HEX_PAD[(paramInt & 0xFF)];
/*     */   }
/*     */   
/*     */ 
/*     */   public static <T extends Appendable> T byteToHexStringPadded(T paramT, int paramInt)
/*     */   {
/*     */     try
/*     */     {
/* 126 */       paramT.append(byteToHexStringPadded(paramInt));
/*     */     } catch (IOException localIOException) {
/* 128 */       PlatformDependent.throwException(localIOException);
/*     */     }
/* 130 */     return paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String toHexStringPadded(byte[] paramArrayOfByte)
/*     */   {
/* 137 */     return toHexStringPadded(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String toHexStringPadded(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 144 */     return ((StringBuilder)toHexStringPadded(new StringBuilder(paramInt2 << 1), paramArrayOfByte, paramInt1, paramInt2)).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static <T extends Appendable> T toHexStringPadded(T paramT, byte[] paramArrayOfByte)
/*     */   {
/* 151 */     return toHexStringPadded(paramT, paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static <T extends Appendable> T toHexStringPadded(T paramT, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 158 */     int i = paramInt1 + paramInt2;
/* 159 */     for (int j = paramInt1; j < i; j++) {
/* 160 */       byteToHexStringPadded(paramT, paramArrayOfByte[j]);
/*     */     }
/* 162 */     return paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String byteToHexString(int paramInt)
/*     */   {
/* 169 */     return BYTE2HEX_NOPAD[(paramInt & 0xFF)];
/*     */   }
/*     */   
/*     */ 
/*     */   public static <T extends Appendable> T byteToHexString(T paramT, int paramInt)
/*     */   {
/*     */     try
/*     */     {
/* 177 */       paramT.append(byteToHexString(paramInt));
/*     */     } catch (IOException localIOException) {
/* 179 */       PlatformDependent.throwException(localIOException);
/*     */     }
/* 181 */     return paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String toHexString(byte[] paramArrayOfByte)
/*     */   {
/* 188 */     return toHexString(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String toHexString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 195 */     return ((StringBuilder)toHexString(new StringBuilder(paramInt2 << 1), paramArrayOfByte, paramInt1, paramInt2)).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static <T extends Appendable> T toHexString(T paramT, byte[] paramArrayOfByte)
/*     */   {
/* 202 */     return toHexString(paramT, paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static <T extends Appendable> T toHexString(T paramT, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 209 */     assert (paramInt2 >= 0);
/* 210 */     if (paramInt2 == 0) {
/* 211 */       return paramT;
/*     */     }
/*     */     
/* 214 */     int i = paramInt1 + paramInt2;
/* 215 */     int j = i - 1;
/*     */     
/*     */ 
/*     */ 
/* 219 */     for (int k = paramInt1; k < j; k++) {
/* 220 */       if (paramArrayOfByte[k] != 0) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 225 */     byteToHexString(paramT, paramArrayOfByte[(k++)]);
/* 226 */     int m = i - k;
/* 227 */     toHexStringPadded(paramT, paramArrayOfByte, k, m);
/*     */     
/* 229 */     return paramT;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String simpleClassName(Object paramObject)
/*     */   {
/* 236 */     if (paramObject == null) {
/* 237 */       return "null_object";
/*     */     }
/* 239 */     return simpleClassName(paramObject.getClass());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String simpleClassName(Class<?> paramClass)
/*     */   {
/* 248 */     if (paramClass == null) {
/* 249 */       return "null_class";
/*     */     }
/*     */     
/* 252 */     Package localPackage = paramClass.getPackage();
/* 253 */     if (localPackage != null) {
/* 254 */       return paramClass.getName().substring(localPackage.getName().length() + 1);
/*     */     }
/* 256 */     return paramClass.getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\StringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */