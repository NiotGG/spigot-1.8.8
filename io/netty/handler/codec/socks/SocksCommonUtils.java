/*     */ package io.netty.handler.codec.socks;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
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
/*     */ final class SocksCommonUtils
/*     */ {
/*  21 */   public static final SocksRequest UNKNOWN_SOCKS_REQUEST = new UnknownSocksRequest();
/*  22 */   public static final SocksResponse UNKNOWN_SOCKS_RESPONSE = new UnknownSocksResponse();
/*     */   
/*     */ 
/*     */   private static final int SECOND_ADDRESS_OCTET_SHIFT = 16;
/*     */   
/*     */ 
/*     */   private static final int FIRST_ADDRESS_OCTET_SHIFT = 24;
/*     */   
/*     */   private static final int THIRD_ADDRESS_OCTET_SHIFT = 8;
/*     */   
/*     */   private static final int XOR_DEFAULT_VALUE = 255;
/*     */   
/*     */ 
/*     */   public static String intToIp(int paramInt)
/*     */   {
/*  37 */     return String.valueOf(paramInt >> 24 & 0xFF) + '.' + (paramInt >> 16 & 0xFF) + '.' + (paramInt >> 8 & 0xFF) + '.' + (paramInt & 0xFF);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  43 */   private static final char[] ipv6conseqZeroFiller = { ':', ':' };
/*     */   
/*     */ 
/*     */   private static final char ipv6hextetSeparator = ':';
/*     */   
/*     */ 
/*     */   public static String ipv6toCompressedForm(byte[] paramArrayOfByte)
/*     */   {
/*  51 */     assert (paramArrayOfByte.length == 16);
/*     */     
/*     */ 
/*  54 */     int i = -1;
/*     */     
/*  56 */     int j = 0;
/*  57 */     for (int k = 0; k < 8;) {
/*  58 */       int m = k * 2;
/*  59 */       int n = 0;
/*     */       
/*  61 */       while ((m < paramArrayOfByte.length) && (paramArrayOfByte[m] == 0) && (paramArrayOfByte[(m + 1)] == 0)) {
/*  62 */         m += 2;
/*  63 */         n++;
/*     */       }
/*  65 */       if (n > j) {
/*  66 */         i = k;
/*  67 */         j = n;
/*     */       }
/*  69 */       k = m / 2 + 1;
/*     */     }
/*  71 */     if ((i == -1) || (j < 2))
/*     */     {
/*  73 */       return ipv6toStr(paramArrayOfByte);
/*     */     }
/*  75 */     StringBuilder localStringBuilder = new StringBuilder(39);
/*  76 */     ipv6toStr(localStringBuilder, paramArrayOfByte, 0, i);
/*  77 */     localStringBuilder.append(ipv6conseqZeroFiller);
/*  78 */     ipv6toStr(localStringBuilder, paramArrayOfByte, i + j, 8);
/*  79 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static String ipv6toStr(byte[] paramArrayOfByte)
/*     */   {
/*  86 */     assert (paramArrayOfByte.length == 16);
/*  87 */     StringBuilder localStringBuilder = new StringBuilder(39);
/*  88 */     ipv6toStr(localStringBuilder, paramArrayOfByte, 0, 8);
/*  89 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static void ipv6toStr(StringBuilder paramStringBuilder, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */     
/*  95 */     for (int i = paramInt1; i < paramInt2; i++) {
/*  96 */       appendHextet(paramStringBuilder, paramArrayOfByte, i);
/*  97 */       paramStringBuilder.append(':');
/*     */     }
/*     */     
/* 100 */     appendHextet(paramStringBuilder, paramArrayOfByte, i);
/*     */   }
/*     */   
/*     */   private static void appendHextet(StringBuilder paramStringBuilder, byte[] paramArrayOfByte, int paramInt) {
/* 104 */     StringUtil.toHexString(paramStringBuilder, paramArrayOfByte, paramInt << 1, 2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\socks\SocksCommonUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */