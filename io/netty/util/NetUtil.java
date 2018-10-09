/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.net.Inet4Address;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
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
/*     */ public final class NetUtil
/*     */ {
/*     */   public static final Inet4Address LOCALHOST4;
/*     */   public static final Inet6Address LOCALHOST6;
/*     */   public static final InetAddress LOCALHOST;
/*     */   public static final NetworkInterface LOOPBACK_IF;
/*     */   public static final int SOMAXCONN;
/*  74 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NetUtil.class);
/*     */   
/*     */   static {
/*  77 */     byte[] arrayOfByte1 = { Byte.MAX_VALUE, 0, 0, 1 };
/*  78 */     byte[] arrayOfByte2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 };
/*     */     
/*     */ 
/*  81 */     Inet4Address localInet4Address = null;
/*     */     try {
/*  83 */       localInet4Address = (Inet4Address)InetAddress.getByAddress(arrayOfByte1);
/*     */     }
/*     */     catch (Exception localException1) {
/*  86 */       PlatformDependent.throwException(localException1);
/*     */     }
/*  88 */     LOCALHOST4 = localInet4Address;
/*     */     
/*     */ 
/*  91 */     Inet6Address localInet6Address = null;
/*     */     try {
/*  93 */       localInet6Address = (Inet6Address)InetAddress.getByAddress(arrayOfByte2);
/*     */     }
/*     */     catch (Exception localException2) {
/*  96 */       PlatformDependent.throwException(localException2);
/*     */     }
/*  98 */     LOCALHOST6 = localInet6Address;
/*     */     
/*     */ 
/* 101 */     ArrayList localArrayList = new ArrayList();
/*     */     try {
/* 103 */       for (localEnumeration = NetworkInterface.getNetworkInterfaces(); localEnumeration.hasMoreElements();) {
/* 104 */         localObject2 = (NetworkInterface)localEnumeration.nextElement();
/*     */         
/* 106 */         if (((NetworkInterface)localObject2).getInetAddresses().hasMoreElements())
/* 107 */           localArrayList.add(localObject2);
/*     */       }
/*     */     } catch (SocketException localSocketException1) {
/*     */       Enumeration localEnumeration;
/* 111 */       logger.warn("Failed to retrieve the list of available network interfaces", localSocketException1);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 117 */     Object localObject1 = null;
/* 118 */     Object localObject2 = null;
/* 119 */     for (Iterator localIterator = localArrayList.iterator(); localIterator.hasNext();) { localObject3 = (NetworkInterface)localIterator.next();
/* 120 */       for (localObject4 = ((NetworkInterface)localObject3).getInetAddresses(); ((Enumeration)localObject4).hasMoreElements();) {
/* 121 */         InetAddress localInetAddress = (InetAddress)((Enumeration)localObject4).nextElement();
/* 122 */         if (localInetAddress.isLoopbackAddress())
/*     */         {
/* 124 */           localObject1 = localObject3;
/* 125 */           localObject2 = localInetAddress;
/*     */           break label324;
/*     */         }
/*     */       }
/*     */     }
/*     */     Object localObject4;
/*     */     label324:
/* 132 */     if (localObject1 == null) {
/*     */       try {
/* 134 */         for (localIterator = localArrayList.iterator(); localIterator.hasNext();) { localObject3 = (NetworkInterface)localIterator.next();
/* 135 */           if (((NetworkInterface)localObject3).isLoopback()) {
/* 136 */             localObject4 = ((NetworkInterface)localObject3).getInetAddresses();
/* 137 */             if (((Enumeration)localObject4).hasMoreElements())
/*     */             {
/* 139 */               localObject1 = localObject3;
/* 140 */               localObject2 = (InetAddress)((Enumeration)localObject4).nextElement();
/* 141 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 146 */         if (localObject1 == null) {
/* 147 */           logger.warn("Failed to find the loopback interface");
/*     */         }
/*     */       } catch (SocketException localSocketException2) {
/* 150 */         logger.warn("Failed to find the loopback interface", localSocketException2);
/*     */       }
/*     */     }
/*     */     
/* 154 */     if (localObject1 != null)
/*     */     {
/* 156 */       logger.debug("Loopback interface: {} ({}, {})", new Object[] { ((NetworkInterface)localObject1).getName(), ((NetworkInterface)localObject1).getDisplayName(), ((InetAddress)localObject2).getHostAddress() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/* 162 */     else if (localObject2 == null) {
/*     */       try {
/* 164 */         if (NetworkInterface.getByInetAddress(LOCALHOST6) != null) {
/* 165 */           logger.debug("Using hard-coded IPv6 localhost address: {}", localInet6Address);
/* 166 */           localObject2 = localInet6Address;
/*     */         }
/*     */       }
/*     */       catch (Exception localException3) {}finally
/*     */       {
/* 171 */         if (localObject2 == null) {
/* 172 */           logger.debug("Using hard-coded IPv4 localhost address: {}", localInet4Address);
/* 173 */           localObject2 = localInet4Address;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 179 */     LOOPBACK_IF = (NetworkInterface)localObject1;
/* 180 */     LOCALHOST = (InetAddress)localObject2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 186 */     int i = PlatformDependent.isWindows() ? 200 : 128;
/* 187 */     Object localObject3 = new File("/proc/sys/net/core/somaxconn");
/* 188 */     if (((File)localObject3).exists()) {
/* 189 */       localObject4 = null;
/*     */       try {
/* 191 */         localObject4 = new BufferedReader(new FileReader((File)localObject3));
/* 192 */         i = Integer.parseInt(((BufferedReader)localObject4).readLine());
/* 193 */         if (logger.isDebugEnabled()) {
/* 194 */           logger.debug("{}: {}", localObject3, Integer.valueOf(i));
/*     */         }
/*     */       } catch (Exception localException5) {
/* 197 */         logger.debug("Failed to get SOMAXCONN from: {}", localObject3, localException5);
/*     */       } finally {
/* 199 */         if (localObject4 != null) {
/*     */           try {
/* 201 */             ((BufferedReader)localObject4).close();
/*     */           }
/*     */           catch (Exception localException7) {}
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 208 */     if (logger.isDebugEnabled()) {
/* 209 */       logger.debug("{}: {} (non-existent)", localObject3, Integer.valueOf(i));
/*     */     }
/*     */     
/*     */ 
/* 213 */     SOMAXCONN = i;
/*     */   }
/*     */   
/*     */ 
/*     */   public static byte[] createByteArrayFromIpAddressString(String paramString)
/*     */   {
/*     */     StringTokenizer localStringTokenizer;
/*     */     Object localObject1;
/*     */     Object localObject3;
/* 222 */     if (isValidIpV4Address(paramString)) {
/* 223 */       localStringTokenizer = new StringTokenizer(paramString, ".");
/*     */       
/*     */ 
/* 226 */       localObject1 = new byte[4];
/* 227 */       for (int i = 0; i < 4; i++) {
/* 228 */         localObject3 = localStringTokenizer.nextToken();
/* 229 */         int j = Integer.parseInt((String)localObject3);
/* 230 */         localObject1[i] = ((byte)j);
/*     */       }
/*     */       
/* 233 */       return (byte[])localObject1;
/*     */     }
/*     */     
/* 236 */     if (isValidIpV6Address(paramString)) {
/* 237 */       if (paramString.charAt(0) == '[') {
/* 238 */         paramString = paramString.substring(1, paramString.length() - 1);
/*     */       }
/*     */       
/* 241 */       localStringTokenizer = new StringTokenizer(paramString, ":.", true);
/* 242 */       localObject3 = new ArrayList();
/* 243 */       ArrayList localArrayList = new ArrayList();
/* 244 */       localObject1 = "";
/* 245 */       Object localObject2 = "";
/* 246 */       int k = -1;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 254 */       while (localStringTokenizer.hasMoreTokens()) {
/* 255 */         localObject2 = localObject1;
/* 256 */         localObject1 = localStringTokenizer.nextToken();
/*     */         
/* 258 */         if (":".equals(localObject1)) {
/* 259 */           if (":".equals(localObject2)) {
/* 260 */             k = ((ArrayList)localObject3).size();
/* 261 */           } else if (!((String)localObject2).isEmpty()) {
/* 262 */             ((ArrayList)localObject3).add(localObject2);
/*     */           }
/* 264 */         } else if (".".equals(localObject1)) {
/* 265 */           localArrayList.add(localObject2);
/*     */         }
/*     */       }
/*     */       
/* 269 */       if (":".equals(localObject2)) {
/* 270 */         if (":".equals(localObject1)) {
/* 271 */           k = ((ArrayList)localObject3).size();
/*     */         } else {
/* 273 */           ((ArrayList)localObject3).add(localObject1);
/*     */         }
/* 275 */       } else if (".".equals(localObject2)) {
/* 276 */         localArrayList.add(localObject1);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 281 */       int m = 8;
/*     */       
/*     */ 
/*     */ 
/* 285 */       if (!localArrayList.isEmpty()) {
/* 286 */         m -= 2;
/*     */       }
/*     */       
/*     */ 
/* 290 */       if (k != -1) {
/* 291 */         int n = m - ((ArrayList)localObject3).size();
/* 292 */         for (i1 = 0; i1 < n; i1++) {
/* 293 */           ((ArrayList)localObject3).add(k, "0");
/*     */         }
/*     */       }
/*     */       
/* 297 */       byte[] arrayOfByte = new byte[16];
/*     */       
/*     */ 
/* 300 */       for (int i1 = 0; i1 < ((ArrayList)localObject3).size(); i1++) {
/* 301 */         convertToBytes((String)((ArrayList)localObject3).get(i1), arrayOfByte, i1 * 2);
/*     */       }
/*     */       
/*     */ 
/* 305 */       for (i1 = 0; i1 < localArrayList.size(); i1++) {
/* 306 */         arrayOfByte[(i1 + 12)] = ((byte)(Integer.parseInt((String)localArrayList.get(i1)) & 0xFF));
/*     */       }
/* 308 */       return arrayOfByte;
/*     */     }
/* 310 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void convertToBytes(String paramString, byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 318 */     int i = paramString.length();
/* 319 */     int j = 0;
/* 320 */     paramArrayOfByte[paramInt] = 0;
/* 321 */     paramArrayOfByte[(paramInt + 1)] = 0;
/*     */     
/*     */ 
/*     */ 
/* 325 */     if (i > 3) {
/* 326 */       k = getIntValue(paramString.charAt(j++)); int 
/* 327 */         tmp39_38 = paramInt; byte[] tmp39_37 = paramArrayOfByte;tmp39_37[tmp39_38] = ((byte)(tmp39_37[tmp39_38] | k << 4));
/*     */     }
/*     */     
/*     */ 
/* 331 */     if (i > 2) {
/* 332 */       k = getIntValue(paramString.charAt(j++)); int 
/* 333 */         tmp69_68 = paramInt; byte[] tmp69_67 = paramArrayOfByte;tmp69_67[tmp69_68] = ((byte)(tmp69_67[tmp69_68] | k));
/*     */     }
/*     */     
/*     */ 
/* 337 */     if (i > 1) {
/* 338 */       k = getIntValue(paramString.charAt(j++)); int 
/* 339 */         tmp99_98 = (paramInt + 1); byte[] tmp99_95 = paramArrayOfByte;tmp99_95[tmp99_98] = ((byte)(tmp99_95[tmp99_98] | k << 4));
/*     */     }
/*     */     
/*     */ 
/* 343 */     int k = getIntValue(paramString.charAt(j)); int 
/* 344 */       tmp123_122 = (paramInt + 1); byte[] tmp123_119 = paramArrayOfByte;tmp123_119[tmp123_122] = ((byte)(tmp123_119[tmp123_122] | k & 0xF));
/*     */   }
/*     */   
/*     */   static int getIntValue(char paramChar)
/*     */   {
/* 349 */     switch (paramChar) {
/*     */     case '0': 
/* 351 */       return 0;
/*     */     case '1': 
/* 353 */       return 1;
/*     */     case '2': 
/* 355 */       return 2;
/*     */     case '3': 
/* 357 */       return 3;
/*     */     case '4': 
/* 359 */       return 4;
/*     */     case '5': 
/* 361 */       return 5;
/*     */     case '6': 
/* 363 */       return 6;
/*     */     case '7': 
/* 365 */       return 7;
/*     */     case '8': 
/* 367 */       return 8;
/*     */     case '9': 
/* 369 */       return 9;
/*     */     }
/*     */     
/* 372 */     paramChar = Character.toLowerCase(paramChar);
/* 373 */     switch (paramChar) {
/*     */     case 'a': 
/* 375 */       return 10;
/*     */     case 'b': 
/* 377 */       return 11;
/*     */     case 'c': 
/* 379 */       return 12;
/*     */     case 'd': 
/* 381 */       return 13;
/*     */     case 'e': 
/* 383 */       return 14;
/*     */     case 'f': 
/* 385 */       return 15;
/*     */     }
/* 387 */     return 0;
/*     */   }
/*     */   
/*     */   public static boolean isValidIpV6Address(String paramString) {
/* 391 */     int i = paramString.length();
/* 392 */     int j = 0;
/* 393 */     int k = 0;
/* 394 */     int m = 0;
/* 395 */     int n = 0;
/* 396 */     StringBuilder localStringBuilder = new StringBuilder();
/* 397 */     char c1 = '\000';
/*     */     
/* 399 */     int i1 = 0;
/*     */     
/* 401 */     if (i < 2) {
/* 402 */       return false;
/*     */     }
/*     */     
/* 405 */     for (int i2 = 0; i2 < i; i2++) {
/* 406 */       char c2 = c1;
/* 407 */       c1 = paramString.charAt(i2);
/* 408 */       switch (c1)
/*     */       {
/*     */ 
/*     */       case '[': 
/* 412 */         if (i2 != 0) {
/* 413 */           return false;
/*     */         }
/* 415 */         if (paramString.charAt(i - 1) != ']') {
/* 416 */           return false;
/*     */         }
/* 418 */         i1 = 1;
/* 419 */         if (i < 4) {
/* 420 */           return false;
/*     */         }
/*     */         
/*     */ 
/*     */         break;
/*     */       case ']': 
/* 426 */         if (i2 != i - 1) {
/* 427 */           return false;
/*     */         }
/* 429 */         if (paramString.charAt(0) != '[') {
/* 430 */           return false;
/*     */         }
/*     */         
/*     */ 
/*     */         break;
/*     */       case '.': 
/* 436 */         m++;
/* 437 */         if (m > 3) {
/* 438 */           return false;
/*     */         }
/* 440 */         if (!isValidIp4Word(localStringBuilder.toString())) {
/* 441 */           return false;
/*     */         }
/* 443 */         if ((k != 6) && (j == 0)) {
/* 444 */           return false;
/*     */         }
/*     */         
/*     */ 
/* 448 */         if ((k == 7) && (paramString.charAt(i1) != ':') && (paramString.charAt(1 + i1) != ':'))
/*     */         {
/* 450 */           return false;
/*     */         }
/* 452 */         localStringBuilder.delete(0, localStringBuilder.length());
/* 453 */         break;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       case ':': 
/* 459 */         if ((i2 == i1) && ((paramString.length() <= i2) || (paramString.charAt(i2 + 1) != ':'))) {
/* 460 */           return false;
/*     */         }
/*     */         
/* 463 */         k++;
/* 464 */         if (k > 7) {
/* 465 */           return false;
/*     */         }
/* 467 */         if (m > 0) {
/* 468 */           return false;
/*     */         }
/* 470 */         if (c2 == ':') {
/* 471 */           if (j != 0) {
/* 472 */             return false;
/*     */           }
/* 474 */           j = 1;
/*     */         }
/* 476 */         localStringBuilder.delete(0, localStringBuilder.length());
/* 477 */         break;
/*     */       case '%': 
/* 479 */         if (k == 0) {
/* 480 */           return false;
/*     */         }
/* 482 */         n++;
/*     */         
/*     */ 
/* 485 */         if (i2 + 1 >= i)
/*     */         {
/*     */ 
/* 488 */           return false;
/*     */         }
/*     */         try {
/* 491 */           if (Integer.parseInt(paramString.substring(i2 + 1)) < 0) {
/* 492 */             return false;
/*     */           }
/*     */           
/*     */         }
/*     */         catch (NumberFormatException localNumberFormatException)
/*     */         {
/* 498 */           return false;
/*     */         }
/*     */       
/*     */ 
/*     */       default: 
/* 503 */         if (n == 0) {
/* 504 */           if ((localStringBuilder != null) && (localStringBuilder.length() > 3)) {
/* 505 */             return false;
/*     */           }
/* 507 */           if (!isValidHexChar(c1)) {
/* 508 */             return false;
/*     */           }
/*     */         }
/* 511 */         localStringBuilder.append(c1);
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 516 */     if (m > 0)
/*     */     {
/* 518 */       if ((m != 3) || (!isValidIp4Word(localStringBuilder.toString())) || (k >= 7)) {
/* 519 */         return false;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 524 */       if ((k != 7) && (j == 0)) {
/* 525 */         return false;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 531 */       if ((n == 0) && 
/* 532 */         (localStringBuilder.length() == 0) && (paramString.charAt(i - 1 - i1) == ':') && (paramString.charAt(i - 2 - i1) != ':'))
/*     */       {
/* 534 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 539 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isValidIp4Word(String paramString)
/*     */   {
/* 544 */     if ((paramString.length() < 1) || (paramString.length() > 3)) {
/* 545 */       return false;
/*     */     }
/* 547 */     for (int i = 0; i < paramString.length(); i++) {
/* 548 */       int j = paramString.charAt(i);
/* 549 */       if ((j < 48) || (j > 57)) {
/* 550 */         return false;
/*     */       }
/*     */     }
/* 553 */     return Integer.parseInt(paramString) <= 255;
/*     */   }
/*     */   
/*     */   static boolean isValidHexChar(char paramChar) {
/* 557 */     return ((paramChar >= '0') && (paramChar <= '9')) || ((paramChar >= 'A') && (paramChar <= 'F')) || ((paramChar >= 'a') && (paramChar <= 'f'));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean isValidIpV4Address(String paramString)
/*     */   {
/* 568 */     int i = 0;
/*     */     
/* 570 */     int j = paramString.length();
/*     */     
/* 572 */     if (j > 15) {
/* 573 */       return false;
/*     */     }
/*     */     
/* 576 */     StringBuilder localStringBuilder = new StringBuilder();
/* 577 */     for (int k = 0; k < j; k++) {
/* 578 */       char c = paramString.charAt(k);
/* 579 */       if (c == '.') {
/* 580 */         i++;
/* 581 */         if (i > 3) {
/* 582 */           return false;
/*     */         }
/* 584 */         if (localStringBuilder.length() == 0) {
/* 585 */           return false;
/*     */         }
/* 587 */         if (Integer.parseInt(localStringBuilder.toString()) > 255) {
/* 588 */           return false;
/*     */         }
/* 590 */         localStringBuilder.delete(0, localStringBuilder.length());
/* 591 */       } else { if (!Character.isDigit(c)) {
/* 592 */           return false;
/*     */         }
/* 594 */         if (localStringBuilder.length() > 2) {
/* 595 */           return false;
/*     */         }
/* 597 */         localStringBuilder.append(c);
/*     */       }
/*     */     }
/*     */     
/* 601 */     if ((localStringBuilder.length() == 0) || (Integer.parseInt(localStringBuilder.toString()) > 255)) {
/* 602 */       return false;
/*     */     }
/*     */     
/* 605 */     return i == 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\NetUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */