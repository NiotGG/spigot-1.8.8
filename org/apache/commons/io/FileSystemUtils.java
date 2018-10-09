/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ public class FileSystemUtils
/*     */ {
/*  48 */   private static final FileSystemUtils INSTANCE = new FileSystemUtils();
/*     */   
/*     */ 
/*     */   private static final int INIT_PROBLEM = -1;
/*     */   
/*     */   private static final int OTHER = 0;
/*     */   
/*     */   private static final int WINDOWS = 1;
/*     */   
/*     */   private static final int UNIX = 2;
/*     */   
/*     */   private static final int POSIX_UNIX = 3;
/*     */   
/*     */   private static final int OS;
/*     */   
/*     */   private static final String DF;
/*     */   
/*     */ 
/*     */   static
/*     */   {
/*  68 */     int i = 0;
/*  69 */     String str1 = "df";
/*     */     try {
/*  71 */       String str2 = System.getProperty("os.name");
/*  72 */       if (str2 == null) {
/*  73 */         throw new IOException("os.name not found");
/*     */       }
/*  75 */       str2 = str2.toLowerCase(Locale.ENGLISH);
/*     */       
/*  77 */       if (str2.indexOf("windows") != -1) {
/*  78 */         i = 1;
/*  79 */       } else if ((str2.indexOf("linux") != -1) || (str2.indexOf("mpe/ix") != -1) || (str2.indexOf("freebsd") != -1) || (str2.indexOf("irix") != -1) || (str2.indexOf("digital unix") != -1) || (str2.indexOf("unix") != -1) || (str2.indexOf("mac os x") != -1))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  86 */         i = 2;
/*  87 */       } else if ((str2.indexOf("sun os") != -1) || (str2.indexOf("sunos") != -1) || (str2.indexOf("solaris") != -1))
/*     */       {
/*     */ 
/*  90 */         i = 3;
/*  91 */         str1 = "/usr/xpg4/bin/df";
/*  92 */       } else if ((str2.indexOf("hp-ux") != -1) || (str2.indexOf("aix") != -1))
/*     */       {
/*  94 */         i = 3;
/*     */       } else {
/*  96 */         i = 0;
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/* 100 */       i = -1;
/*     */     }
/* 102 */     OS = i;
/* 103 */     DF = str1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static long freeSpace(String paramString)
/*     */     throws IOException
/*     */   {
/* 142 */     return INSTANCE.freeSpaceOS(paramString, OS, false, -1L);
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
/*     */ 
/*     */ 
/*     */   public static long freeSpaceKb(String paramString)
/*     */     throws IOException
/*     */   {
/* 171 */     return freeSpaceKb(paramString, -1L);
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
/*     */ 
/*     */ 
/*     */   public static long freeSpaceKb(String paramString, long paramLong)
/*     */     throws IOException
/*     */   {
/* 200 */     return INSTANCE.freeSpaceOS(paramString, OS, true, paramLong);
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
/*     */   public static long freeSpaceKb()
/*     */     throws IOException
/*     */   {
/* 216 */     return freeSpaceKb(-1L);
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
/*     */   public static long freeSpaceKb(long paramLong)
/*     */     throws IOException
/*     */   {
/* 234 */     return freeSpaceKb(new File(".").getAbsolutePath(), paramLong);
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
/*     */   long freeSpaceOS(String paramString, int paramInt, boolean paramBoolean, long paramLong)
/*     */     throws IOException
/*     */   {
/* 259 */     if (paramString == null) {
/* 260 */       throw new IllegalArgumentException("Path must not be empty");
/*     */     }
/* 262 */     switch (paramInt) {
/*     */     case 1: 
/* 264 */       return paramBoolean ? freeSpaceWindows(paramString, paramLong) / 1024L : freeSpaceWindows(paramString, paramLong);
/*     */     case 2: 
/* 266 */       return freeSpaceUnix(paramString, paramBoolean, false, paramLong);
/*     */     case 3: 
/* 268 */       return freeSpaceUnix(paramString, paramBoolean, true, paramLong);
/*     */     case 0: 
/* 270 */       throw new IllegalStateException("Unsupported operating system");
/*     */     }
/* 272 */     throw new IllegalStateException("Exception caught when determining operating system");
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
/*     */   long freeSpaceWindows(String paramString, long paramLong)
/*     */     throws IOException
/*     */   {
/* 288 */     paramString = FilenameUtils.normalize(paramString, false);
/* 289 */     if ((paramString.length() > 0) && (paramString.charAt(0) != '"')) {
/* 290 */       paramString = "\"" + paramString + "\"";
/*     */     }
/*     */     
/*     */ 
/* 294 */     String[] arrayOfString = { "cmd.exe", "/C", "dir /a /-c " + paramString };
/*     */     
/*     */ 
/* 297 */     List localList = performCommand(arrayOfString, Integer.MAX_VALUE, paramLong);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 303 */     for (int i = localList.size() - 1; i >= 0; i--) {
/* 304 */       String str = (String)localList.get(i);
/* 305 */       if (str.length() > 0) {
/* 306 */         return parseDir(str, paramString);
/*     */       }
/*     */     }
/*     */     
/* 310 */     throw new IOException("Command line 'dir /-c' did not return any info for path '" + paramString + "'");
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
/*     */   long parseDir(String paramString1, String paramString2)
/*     */     throws IOException
/*     */   {
/* 328 */     int i = 0;
/* 329 */     int j = 0;
/* 330 */     int k = paramString1.length() - 1;
/* 331 */     char c; while (k >= 0) {
/* 332 */       c = paramString1.charAt(k);
/* 333 */       if (Character.isDigit(c))
/*     */       {
/*     */ 
/* 336 */         j = k + 1;
/* 337 */         break;
/*     */       }
/* 339 */       k--;
/*     */     }
/* 341 */     while (k >= 0) {
/* 342 */       c = paramString1.charAt(k);
/* 343 */       if ((!Character.isDigit(c)) && (c != ',') && (c != '.'))
/*     */       {
/*     */ 
/* 346 */         i = k + 1;
/* 347 */         break;
/*     */       }
/* 349 */       k--;
/*     */     }
/* 351 */     if (k < 0) {
/* 352 */       throw new IOException("Command line 'dir /-c' did not return valid info for path '" + paramString2 + "'");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 358 */     StringBuilder localStringBuilder = new StringBuilder(paramString1.substring(i, j));
/* 359 */     for (int m = 0; m < localStringBuilder.length(); m++) {
/* 360 */       if ((localStringBuilder.charAt(m) == ',') || (localStringBuilder.charAt(m) == '.')) {
/* 361 */         localStringBuilder.deleteCharAt(m--);
/*     */       }
/*     */     }
/* 364 */     return parseBytes(localStringBuilder.toString(), paramString2);
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
/*     */   long freeSpaceUnix(String paramString, boolean paramBoolean1, boolean paramBoolean2, long paramLong)
/*     */     throws IOException
/*     */   {
/* 380 */     if (paramString.length() == 0) {
/* 381 */       throw new IllegalArgumentException("Path must not be empty");
/*     */     }
/*     */     
/*     */ 
/* 385 */     String str1 = "-";
/* 386 */     if (paramBoolean1) {
/* 387 */       str1 = str1 + "k";
/*     */     }
/* 389 */     if (paramBoolean2) {
/* 390 */       str1 = str1 + "P";
/*     */     }
/* 392 */     String[] arrayOfString = { DF, str1.length() > 1 ? new String[] { DF, str1, paramString } : paramString };
/*     */     
/*     */ 
/*     */ 
/* 396 */     List localList = performCommand(arrayOfString, 3, paramLong);
/* 397 */     if (localList.size() < 2)
/*     */     {
/* 399 */       throw new IOException("Command line '" + DF + "' did not return info as expected " + "for path '" + paramString + "'- response was " + localList);
/*     */     }
/*     */     
/*     */ 
/* 403 */     String str2 = (String)localList.get(1);
/*     */     
/*     */ 
/* 406 */     StringTokenizer localStringTokenizer = new StringTokenizer(str2, " ");
/* 407 */     if (localStringTokenizer.countTokens() < 4)
/*     */     {
/* 409 */       if ((localStringTokenizer.countTokens() == 1) && (localList.size() >= 3)) {
/* 410 */         str3 = (String)localList.get(2);
/* 411 */         localStringTokenizer = new StringTokenizer(str3, " ");
/*     */       } else {
/* 413 */         throw new IOException("Command line '" + DF + "' did not return data as expected " + "for path '" + paramString + "'- check path is valid");
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 418 */       localStringTokenizer.nextToken();
/*     */     }
/* 420 */     localStringTokenizer.nextToken();
/* 421 */     localStringTokenizer.nextToken();
/* 422 */     String str3 = localStringTokenizer.nextToken();
/* 423 */     return parseBytes(str3, paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   long parseBytes(String paramString1, String paramString2)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 437 */       long l = Long.parseLong(paramString1);
/* 438 */       if (l < 0L) {
/* 439 */         throw new IOException("Command line '" + DF + "' did not find free space in response " + "for path '" + paramString2 + "'- check path is valid");
/*     */       }
/*     */       
/*     */ 
/* 443 */       return l;
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {
/* 446 */       throw new IOExceptionWithCause("Command line '" + DF + "' did not return numeric data as expected " + "for path '" + paramString2 + "'- check path is valid", localNumberFormatException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   List<String> performCommand(String[] paramArrayOfString, int paramInt, long paramLong)
/*     */     throws IOException
/*     */   {
/* 472 */     ArrayList localArrayList1 = new ArrayList(20);
/* 473 */     Process localProcess = null;
/* 474 */     InputStream localInputStream1 = null;
/* 475 */     OutputStream localOutputStream = null;
/* 476 */     InputStream localInputStream2 = null;
/* 477 */     BufferedReader localBufferedReader = null;
/*     */     try
/*     */     {
/* 480 */       Thread localThread = ThreadMonitor.start(paramLong);
/*     */       
/* 482 */       localProcess = openProcess(paramArrayOfString);
/* 483 */       localInputStream1 = localProcess.getInputStream();
/* 484 */       localOutputStream = localProcess.getOutputStream();
/* 485 */       localInputStream2 = localProcess.getErrorStream();
/* 486 */       localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream1));
/* 487 */       String str = localBufferedReader.readLine();
/* 488 */       while ((str != null) && (localArrayList1.size() < paramInt)) {
/* 489 */         str = str.toLowerCase(Locale.ENGLISH).trim();
/* 490 */         localArrayList1.add(str);
/* 491 */         str = localBufferedReader.readLine();
/*     */       }
/*     */       
/* 494 */       localProcess.waitFor();
/*     */       
/* 496 */       ThreadMonitor.stop(localThread);
/*     */       
/* 498 */       if (localProcess.exitValue() != 0)
/*     */       {
/* 500 */         throw new IOException("Command line returned OS error code '" + localProcess.exitValue() + "' for command " + Arrays.asList(paramArrayOfString));
/*     */       }
/*     */       
/*     */ 
/* 504 */       if (localArrayList1.isEmpty())
/*     */       {
/* 506 */         throw new IOException("Command line did not return any info for command " + Arrays.asList(paramArrayOfString));
/*     */       }
/*     */       
/*     */ 
/* 510 */       return localArrayList1;
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {
/* 513 */       throw new IOExceptionWithCause("Command line threw an InterruptedException for command " + Arrays.asList(paramArrayOfString) + " timeout=" + paramLong, localInterruptedException);
/*     */     }
/*     */     finally
/*     */     {
/* 517 */       IOUtils.closeQuietly(localInputStream1);
/* 518 */       IOUtils.closeQuietly(localOutputStream);
/* 519 */       IOUtils.closeQuietly(localInputStream2);
/* 520 */       IOUtils.closeQuietly(localBufferedReader);
/* 521 */       if (localProcess != null) {
/* 522 */         localProcess.destroy();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Process openProcess(String[] paramArrayOfString)
/*     */     throws IOException
/*     */   {
/* 535 */     return Runtime.getRuntime().exec(paramArrayOfString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\FileSystemUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */