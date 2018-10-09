/*      */ package org.apache.commons.io;
/*      */ 
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileFilter;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.zip.CRC32;
/*      */ import java.util.zip.CheckedInputStream;
/*      */ import java.util.zip.Checksum;
/*      */ import org.apache.commons.io.filefilter.DirectoryFileFilter;
/*      */ import org.apache.commons.io.filefilter.FalseFileFilter;
/*      */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*      */ import org.apache.commons.io.filefilter.IOFileFilter;
/*      */ import org.apache.commons.io.filefilter.SuffixFileFilter;
/*      */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*      */ import org.apache.commons.io.output.NullOutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FileUtils
/*      */ {
/*      */   public static final long ONE_KB = 1024L;
/*   95 */   public static final BigInteger ONE_KB_BI = BigInteger.valueOf(1024L);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final long ONE_MB = 1048576L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  107 */   public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long FILE_COPY_BUFFER_SIZE = 31457280L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final long ONE_GB = 1073741824L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  124 */   public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final long ONE_TB = 1099511627776L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  136 */   public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final long ONE_PB = 1125899906842624L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  148 */   public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final long ONE_EB = 1152921504606846976L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  160 */   public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  165 */   public static final BigInteger ONE_ZB = BigInteger.valueOf(1024L).multiply(BigInteger.valueOf(1152921504606846976L));
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  170 */   public static final BigInteger ONE_YB = ONE_KB_BI.multiply(ONE_ZB);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  175 */   public static final File[] EMPTY_FILE_ARRAY = new File[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  180 */   private static final Charset UTF8 = Charset.forName("UTF-8");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static File getFile(File paramFile, String... paramVarArgs)
/*      */   {
/*  192 */     if (paramFile == null) {
/*  193 */       throw new NullPointerException("directorydirectory must not be null");
/*      */     }
/*  195 */     if (paramVarArgs == null) {
/*  196 */       throw new NullPointerException("names must not be null");
/*      */     }
/*  198 */     File localFile = paramFile;
/*  199 */     for (String str : paramVarArgs) {
/*  200 */       localFile = new File(localFile, str);
/*      */     }
/*  202 */     return localFile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static File getFile(String... paramVarArgs)
/*      */   {
/*  213 */     if (paramVarArgs == null) {
/*  214 */       throw new NullPointerException("names must not be null");
/*      */     }
/*  216 */     File localFile = null;
/*  217 */     for (String str : paramVarArgs) {
/*  218 */       if (localFile == null) {
/*  219 */         localFile = new File(str);
/*      */       } else {
/*  221 */         localFile = new File(localFile, str);
/*      */       }
/*      */     }
/*  224 */     return localFile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getTempDirectoryPath()
/*      */   {
/*  235 */     return System.getProperty("java.io.tmpdir");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static File getTempDirectory()
/*      */   {
/*  246 */     return new File(getTempDirectoryPath());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getUserDirectoryPath()
/*      */   {
/*  257 */     return System.getProperty("user.home");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static File getUserDirectory()
/*      */   {
/*  268 */     return new File(getUserDirectoryPath());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static FileInputStream openInputStream(File paramFile)
/*      */     throws IOException
/*      */   {
/*  291 */     if (paramFile.exists()) {
/*  292 */       if (paramFile.isDirectory()) {
/*  293 */         throw new IOException("File '" + paramFile + "' exists but is a directory");
/*      */       }
/*  295 */       if (!paramFile.canRead()) {
/*  296 */         throw new IOException("File '" + paramFile + "' cannot be read");
/*      */       }
/*      */     } else {
/*  299 */       throw new FileNotFoundException("File '" + paramFile + "' does not exist");
/*      */     }
/*  301 */     return new FileInputStream(paramFile);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static FileOutputStream openOutputStream(File paramFile)
/*      */     throws IOException
/*      */   {
/*  326 */     return openOutputStream(paramFile, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static FileOutputStream openOutputStream(File paramFile, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/*  352 */     if (paramFile.exists()) {
/*  353 */       if (paramFile.isDirectory()) {
/*  354 */         throw new IOException("File '" + paramFile + "' exists but is a directory");
/*      */       }
/*  356 */       if (!paramFile.canWrite()) {
/*  357 */         throw new IOException("File '" + paramFile + "' cannot be written to");
/*      */       }
/*      */     } else {
/*  360 */       File localFile = paramFile.getParentFile();
/*  361 */       if ((localFile != null) && 
/*  362 */         (!localFile.mkdirs()) && (!localFile.isDirectory())) {
/*  363 */         throw new IOException("Directory '" + localFile + "' could not be created");
/*      */       }
/*      */     }
/*      */     
/*  367 */     return new FileOutputStream(paramFile, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String byteCountToDisplaySize(BigInteger paramBigInteger)
/*      */   {
/*      */     String str;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  391 */     if (paramBigInteger.divide(ONE_EB_BI).compareTo(BigInteger.ZERO) > 0) {
/*  392 */       str = String.valueOf(paramBigInteger.divide(ONE_EB_BI)) + " EB";
/*  393 */     } else if (paramBigInteger.divide(ONE_PB_BI).compareTo(BigInteger.ZERO) > 0) {
/*  394 */       str = String.valueOf(paramBigInteger.divide(ONE_PB_BI)) + " PB";
/*  395 */     } else if (paramBigInteger.divide(ONE_TB_BI).compareTo(BigInteger.ZERO) > 0) {
/*  396 */       str = String.valueOf(paramBigInteger.divide(ONE_TB_BI)) + " TB";
/*  397 */     } else if (paramBigInteger.divide(ONE_GB_BI).compareTo(BigInteger.ZERO) > 0) {
/*  398 */       str = String.valueOf(paramBigInteger.divide(ONE_GB_BI)) + " GB";
/*  399 */     } else if (paramBigInteger.divide(ONE_MB_BI).compareTo(BigInteger.ZERO) > 0) {
/*  400 */       str = String.valueOf(paramBigInteger.divide(ONE_MB_BI)) + " MB";
/*  401 */     } else if (paramBigInteger.divide(ONE_KB_BI).compareTo(BigInteger.ZERO) > 0) {
/*  402 */       str = String.valueOf(paramBigInteger.divide(ONE_KB_BI)) + " KB";
/*      */     } else {
/*  404 */       str = String.valueOf(paramBigInteger) + " bytes";
/*      */     }
/*  406 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String byteCountToDisplaySize(long paramLong)
/*      */   {
/*  426 */     return byteCountToDisplaySize(BigInteger.valueOf(paramLong));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void touch(File paramFile)
/*      */     throws IOException
/*      */   {
/*  443 */     if (!paramFile.exists()) {
/*  444 */       FileOutputStream localFileOutputStream = openOutputStream(paramFile);
/*  445 */       IOUtils.closeQuietly(localFileOutputStream);
/*      */     }
/*  447 */     boolean bool = paramFile.setLastModified(System.currentTimeMillis());
/*  448 */     if (!bool) {
/*  449 */       throw new IOException("Unable to set the last modification time for " + paramFile);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static File[] convertFileCollectionToFileArray(Collection<File> paramCollection)
/*      */   {
/*  463 */     return (File[])paramCollection.toArray(new File[paramCollection.size()]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void innerListFiles(Collection<File> paramCollection, File paramFile, IOFileFilter paramIOFileFilter, boolean paramBoolean)
/*      */   {
/*  478 */     File[] arrayOfFile1 = paramFile.listFiles(paramIOFileFilter);
/*      */     
/*  480 */     if (arrayOfFile1 != null) {
/*  481 */       for (File localFile : arrayOfFile1) {
/*  482 */         if (localFile.isDirectory()) {
/*  483 */           if (paramBoolean) {
/*  484 */             paramCollection.add(localFile);
/*      */           }
/*  486 */           innerListFiles(paramCollection, localFile, paramIOFileFilter, paramBoolean);
/*      */         } else {
/*  488 */           paramCollection.add(localFile);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Collection<File> listFiles(File paramFile, IOFileFilter paramIOFileFilter1, IOFileFilter paramIOFileFilter2)
/*      */   {
/*  521 */     validateListFilesParameters(paramFile, paramIOFileFilter1);
/*      */     
/*  523 */     IOFileFilter localIOFileFilter1 = setUpEffectiveFileFilter(paramIOFileFilter1);
/*  524 */     IOFileFilter localIOFileFilter2 = setUpEffectiveDirFilter(paramIOFileFilter2);
/*      */     
/*      */ 
/*  527 */     LinkedList localLinkedList = new LinkedList();
/*  528 */     innerListFiles(localLinkedList, paramFile, FileFilterUtils.or(new IOFileFilter[] { localIOFileFilter1, localIOFileFilter2 }), false);
/*      */     
/*  530 */     return localLinkedList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void validateListFilesParameters(File paramFile, IOFileFilter paramIOFileFilter)
/*      */   {
/*  544 */     if (!paramFile.isDirectory()) {
/*  545 */       throw new IllegalArgumentException("Parameter 'directory' is not a directory");
/*      */     }
/*  547 */     if (paramIOFileFilter == null) {
/*  548 */       throw new NullPointerException("Parameter 'fileFilter' is null");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static IOFileFilter setUpEffectiveFileFilter(IOFileFilter paramIOFileFilter)
/*      */   {
/*  559 */     return FileFilterUtils.and(new IOFileFilter[] { paramIOFileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE) });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static IOFileFilter setUpEffectiveDirFilter(IOFileFilter paramIOFileFilter)
/*      */   {
/*  569 */     return paramIOFileFilter == null ? FalseFileFilter.INSTANCE : FileFilterUtils.and(new IOFileFilter[] { paramIOFileFilter, DirectoryFileFilter.INSTANCE });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Collection<File> listFilesAndDirs(File paramFile, IOFileFilter paramIOFileFilter1, IOFileFilter paramIOFileFilter2)
/*      */   {
/*  593 */     validateListFilesParameters(paramFile, paramIOFileFilter1);
/*      */     
/*  595 */     IOFileFilter localIOFileFilter1 = setUpEffectiveFileFilter(paramIOFileFilter1);
/*  596 */     IOFileFilter localIOFileFilter2 = setUpEffectiveDirFilter(paramIOFileFilter2);
/*      */     
/*      */ 
/*  599 */     LinkedList localLinkedList = new LinkedList();
/*  600 */     if (paramFile.isDirectory()) {
/*  601 */       localLinkedList.add(paramFile);
/*      */     }
/*  603 */     innerListFiles(localLinkedList, paramFile, FileFilterUtils.or(new IOFileFilter[] { localIOFileFilter1, localIOFileFilter2 }), true);
/*      */     
/*  605 */     return localLinkedList;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Iterator<File> iterateFiles(File paramFile, IOFileFilter paramIOFileFilter1, IOFileFilter paramIOFileFilter2)
/*      */   {
/*  628 */     return listFiles(paramFile, paramIOFileFilter1, paramIOFileFilter2).iterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Iterator<File> iterateFilesAndDirs(File paramFile, IOFileFilter paramIOFileFilter1, IOFileFilter paramIOFileFilter2)
/*      */   {
/*  652 */     return listFilesAndDirs(paramFile, paramIOFileFilter1, paramIOFileFilter2).iterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String[] toSuffixes(String[] paramArrayOfString)
/*      */   {
/*  664 */     String[] arrayOfString = new String[paramArrayOfString.length];
/*  665 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  666 */       arrayOfString[i] = ("." + paramArrayOfString[i]);
/*      */     }
/*  668 */     return arrayOfString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Collection<File> listFiles(File paramFile, String[] paramArrayOfString, boolean paramBoolean)
/*      */   {
/*      */     Object localObject;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  685 */     if (paramArrayOfString == null) {
/*  686 */       localObject = TrueFileFilter.INSTANCE;
/*      */     } else {
/*  688 */       String[] arrayOfString = toSuffixes(paramArrayOfString);
/*  689 */       localObject = new SuffixFileFilter(arrayOfString);
/*      */     }
/*  691 */     return listFiles(paramFile, (IOFileFilter)localObject, paramBoolean ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Iterator<File> iterateFiles(File paramFile, String[] paramArrayOfString, boolean paramBoolean)
/*      */   {
/*  710 */     return listFiles(paramFile, paramArrayOfString, paramBoolean).iterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean contentEquals(File paramFile1, File paramFile2)
/*      */     throws IOException
/*      */   {
/*  730 */     boolean bool1 = paramFile1.exists();
/*  731 */     if (bool1 != paramFile2.exists()) {
/*  732 */       return false;
/*      */     }
/*      */     
/*  735 */     if (!bool1)
/*      */     {
/*  737 */       return true;
/*      */     }
/*      */     
/*  740 */     if ((paramFile1.isDirectory()) || (paramFile2.isDirectory()))
/*      */     {
/*  742 */       throw new IOException("Can't compare directories, only files");
/*      */     }
/*      */     
/*  745 */     if (paramFile1.length() != paramFile2.length())
/*      */     {
/*  747 */       return false;
/*      */     }
/*      */     
/*  750 */     if (paramFile1.getCanonicalFile().equals(paramFile2.getCanonicalFile()))
/*      */     {
/*  752 */       return true;
/*      */     }
/*      */     
/*  755 */     FileInputStream localFileInputStream1 = null;
/*  756 */     FileInputStream localFileInputStream2 = null;
/*      */     try {
/*  758 */       localFileInputStream1 = new FileInputStream(paramFile1);
/*  759 */       localFileInputStream2 = new FileInputStream(paramFile2);
/*  760 */       return IOUtils.contentEquals(localFileInputStream1, localFileInputStream2);
/*      */     }
/*      */     finally {
/*  763 */       IOUtils.closeQuietly(localFileInputStream1);
/*  764 */       IOUtils.closeQuietly(localFileInputStream2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean contentEqualsIgnoreEOL(File paramFile1, File paramFile2, String paramString)
/*      */     throws IOException
/*      */   {
/*  787 */     boolean bool1 = paramFile1.exists();
/*  788 */     if (bool1 != paramFile2.exists()) {
/*  789 */       return false;
/*      */     }
/*      */     
/*  792 */     if (!bool1)
/*      */     {
/*  794 */       return true;
/*      */     }
/*      */     
/*  797 */     if ((paramFile1.isDirectory()) || (paramFile2.isDirectory()))
/*      */     {
/*  799 */       throw new IOException("Can't compare directories, only files");
/*      */     }
/*      */     
/*  802 */     if (paramFile1.getCanonicalFile().equals(paramFile2.getCanonicalFile()))
/*      */     {
/*  804 */       return true;
/*      */     }
/*      */     
/*  807 */     InputStreamReader localInputStreamReader1 = null;
/*  808 */     InputStreamReader localInputStreamReader2 = null;
/*      */     try {
/*  810 */       if (paramString == null) {
/*  811 */         localInputStreamReader1 = new InputStreamReader(new FileInputStream(paramFile1));
/*  812 */         localInputStreamReader2 = new InputStreamReader(new FileInputStream(paramFile2));
/*      */       } else {
/*  814 */         localInputStreamReader1 = new InputStreamReader(new FileInputStream(paramFile1), paramString);
/*  815 */         localInputStreamReader2 = new InputStreamReader(new FileInputStream(paramFile2), paramString);
/*      */       }
/*  817 */       return IOUtils.contentEqualsIgnoreEOL(localInputStreamReader1, localInputStreamReader2);
/*      */     }
/*      */     finally {
/*  820 */       IOUtils.closeQuietly(localInputStreamReader1);
/*  821 */       IOUtils.closeQuietly(localInputStreamReader2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static File toFile(URL paramURL)
/*      */   {
/*  841 */     if ((paramURL == null) || (!"file".equalsIgnoreCase(paramURL.getProtocol()))) {
/*  842 */       return null;
/*      */     }
/*  844 */     String str = paramURL.getFile().replace('/', File.separatorChar);
/*  845 */     str = decodeUrl(str);
/*  846 */     return new File(str);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static String decodeUrl(String paramString)
/*      */   {
/*  865 */     String str = paramString;
/*  866 */     if ((paramString != null) && (paramString.indexOf('%') >= 0)) {
/*  867 */       int i = paramString.length();
/*  868 */       StringBuffer localStringBuffer = new StringBuffer();
/*  869 */       ByteBuffer localByteBuffer = ByteBuffer.allocate(i);
/*  870 */       for (int j = 0; j < i;) {
/*  871 */         if (paramString.charAt(j) == '%') {
/*      */           try {
/*      */             do {
/*  874 */               byte b = (byte)Integer.parseInt(paramString.substring(j + 1, j + 3), 16);
/*  875 */               localByteBuffer.put(b);
/*  876 */               j += 3;
/*  877 */             } while ((j < i) && (paramString.charAt(j) == '%'));
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  883 */             if (localByteBuffer.position() <= 0) continue;
/*  884 */             localByteBuffer.flip();
/*  885 */             localStringBuffer.append(UTF8.decode(localByteBuffer).toString());
/*  886 */             localByteBuffer.clear(); continue;
/*      */           }
/*      */           catch (RuntimeException localRuntimeException) {}finally
/*      */           {
/*  883 */             if (localByteBuffer.position() > 0) {
/*  884 */               localByteBuffer.flip();
/*  885 */               localStringBuffer.append(UTF8.decode(localByteBuffer).toString());
/*  886 */               localByteBuffer.clear();
/*      */             }
/*      */           }
/*      */         } else
/*  890 */           localStringBuffer.append(paramString.charAt(j++));
/*      */       }
/*  892 */       str = localStringBuffer.toString();
/*      */     }
/*  894 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static File[] toFiles(URL[] paramArrayOfURL)
/*      */   {
/*  917 */     if ((paramArrayOfURL == null) || (paramArrayOfURL.length == 0)) {
/*  918 */       return EMPTY_FILE_ARRAY;
/*      */     }
/*  920 */     File[] arrayOfFile = new File[paramArrayOfURL.length];
/*  921 */     for (int i = 0; i < paramArrayOfURL.length; i++) {
/*  922 */       URL localURL = paramArrayOfURL[i];
/*  923 */       if (localURL != null) {
/*  924 */         if (!localURL.getProtocol().equals("file")) {
/*  925 */           throw new IllegalArgumentException("URL could not be converted to a File: " + localURL);
/*      */         }
/*      */         
/*  928 */         arrayOfFile[i] = toFile(localURL);
/*      */       }
/*      */     }
/*  931 */     return arrayOfFile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static URL[] toURLs(File[] paramArrayOfFile)
/*      */     throws IOException
/*      */   {
/*  945 */     URL[] arrayOfURL = new URL[paramArrayOfFile.length];
/*      */     
/*  947 */     for (int i = 0; i < arrayOfURL.length; i++) {
/*  948 */       arrayOfURL[i] = paramArrayOfFile[i].toURI().toURL();
/*      */     }
/*      */     
/*  951 */     return arrayOfURL;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyFileToDirectory(File paramFile1, File paramFile2)
/*      */     throws IOException
/*      */   {
/*  977 */     copyFileToDirectory(paramFile1, paramFile2, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyFileToDirectory(File paramFile1, File paramFile2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1006 */     if (paramFile2 == null) {
/* 1007 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/* 1009 */     if ((paramFile2.exists()) && (!paramFile2.isDirectory())) {
/* 1010 */       throw new IllegalArgumentException("Destination '" + paramFile2 + "' is not a directory");
/*      */     }
/* 1012 */     File localFile = new File(paramFile2, paramFile1.getName());
/* 1013 */     copyFile(paramFile1, localFile, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyFile(File paramFile1, File paramFile2)
/*      */     throws IOException
/*      */   {
/* 1038 */     copyFile(paramFile1, paramFile2, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyFile(File paramFile1, File paramFile2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1067 */     if (paramFile1 == null) {
/* 1068 */       throw new NullPointerException("Source must not be null");
/*      */     }
/* 1070 */     if (paramFile2 == null) {
/* 1071 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/* 1073 */     if (!paramFile1.exists()) {
/* 1074 */       throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
/*      */     }
/* 1076 */     if (paramFile1.isDirectory()) {
/* 1077 */       throw new IOException("Source '" + paramFile1 + "' exists but is a directory");
/*      */     }
/* 1079 */     if (paramFile1.getCanonicalPath().equals(paramFile2.getCanonicalPath())) {
/* 1080 */       throw new IOException("Source '" + paramFile1 + "' and destination '" + paramFile2 + "' are the same");
/*      */     }
/* 1082 */     File localFile = paramFile2.getParentFile();
/* 1083 */     if ((localFile != null) && 
/* 1084 */       (!localFile.mkdirs()) && (!localFile.isDirectory())) {
/* 1085 */       throw new IOException("Destination '" + localFile + "' directory cannot be created");
/*      */     }
/*      */     
/* 1088 */     if ((paramFile2.exists()) && (!paramFile2.canWrite())) {
/* 1089 */       throw new IOException("Destination '" + paramFile2 + "' exists but is read-only");
/*      */     }
/* 1091 */     doCopyFile(paramFile1, paramFile2, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long copyFile(File paramFile, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1112 */     FileInputStream localFileInputStream = new FileInputStream(paramFile);
/*      */     try {
/* 1114 */       return IOUtils.copyLarge(localFileInputStream, paramOutputStream);
/*      */     } finally {
/* 1116 */       localFileInputStream.close();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void doCopyFile(File paramFile1, File paramFile2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1129 */     if ((paramFile2.exists()) && (paramFile2.isDirectory())) {
/* 1130 */       throw new IOException("Destination '" + paramFile2 + "' exists but is a directory");
/*      */     }
/*      */     
/* 1133 */     FileInputStream localFileInputStream = null;
/* 1134 */     FileOutputStream localFileOutputStream = null;
/* 1135 */     FileChannel localFileChannel1 = null;
/* 1136 */     FileChannel localFileChannel2 = null;
/*      */     try {
/* 1138 */       localFileInputStream = new FileInputStream(paramFile1);
/* 1139 */       localFileOutputStream = new FileOutputStream(paramFile2);
/* 1140 */       localFileChannel1 = localFileInputStream.getChannel();
/* 1141 */       localFileChannel2 = localFileOutputStream.getChannel();
/* 1142 */       long l1 = localFileChannel1.size();
/* 1143 */       long l2 = 0L;
/* 1144 */       long l3 = 0L;
/* 1145 */       while (l2 < l1) {
/* 1146 */         l3 = l1 - l2 > 31457280L ? 31457280L : l1 - l2;
/* 1147 */         l2 += localFileChannel2.transferFrom(localFileChannel1, l2, l3);
/*      */       }
/*      */     } finally {
/* 1150 */       IOUtils.closeQuietly(localFileChannel2);
/* 1151 */       IOUtils.closeQuietly(localFileOutputStream);
/* 1152 */       IOUtils.closeQuietly(localFileChannel1);
/* 1153 */       IOUtils.closeQuietly(localFileInputStream);
/*      */     }
/*      */     
/* 1156 */     if (paramFile1.length() != paramFile2.length()) {
/* 1157 */       throw new IOException("Failed to copy full contents from '" + paramFile1 + "' to '" + paramFile2 + "'");
/*      */     }
/*      */     
/* 1160 */     if (paramBoolean) {
/* 1161 */       paramFile2.setLastModified(paramFile1.lastModified());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyDirectoryToDirectory(File paramFile1, File paramFile2)
/*      */     throws IOException
/*      */   {
/* 1190 */     if (paramFile1 == null) {
/* 1191 */       throw new NullPointerException("Source must not be null");
/*      */     }
/* 1193 */     if ((paramFile1.exists()) && (!paramFile1.isDirectory())) {
/* 1194 */       throw new IllegalArgumentException("Source '" + paramFile2 + "' is not a directory");
/*      */     }
/* 1196 */     if (paramFile2 == null) {
/* 1197 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/* 1199 */     if ((paramFile2.exists()) && (!paramFile2.isDirectory())) {
/* 1200 */       throw new IllegalArgumentException("Destination '" + paramFile2 + "' is not a directory");
/*      */     }
/* 1202 */     copyDirectory(paramFile1, new File(paramFile2, paramFile1.getName()), true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyDirectory(File paramFile1, File paramFile2)
/*      */     throws IOException
/*      */   {
/* 1230 */     copyDirectory(paramFile1, paramFile2, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyDirectory(File paramFile1, File paramFile2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1261 */     copyDirectory(paramFile1, paramFile2, null, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyDirectory(File paramFile1, File paramFile2, FileFilter paramFileFilter)
/*      */     throws IOException
/*      */   {
/* 1310 */     copyDirectory(paramFile1, paramFile2, paramFileFilter, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyDirectory(File paramFile1, File paramFile2, FileFilter paramFileFilter, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1361 */     if (paramFile1 == null) {
/* 1362 */       throw new NullPointerException("Source must not be null");
/*      */     }
/* 1364 */     if (paramFile2 == null) {
/* 1365 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/* 1367 */     if (!paramFile1.exists()) {
/* 1368 */       throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
/*      */     }
/* 1370 */     if (!paramFile1.isDirectory()) {
/* 1371 */       throw new IOException("Source '" + paramFile1 + "' exists but is not a directory");
/*      */     }
/* 1373 */     if (paramFile1.getCanonicalPath().equals(paramFile2.getCanonicalPath())) {
/* 1374 */       throw new IOException("Source '" + paramFile1 + "' and destination '" + paramFile2 + "' are the same");
/*      */     }
/*      */     
/*      */ 
/* 1378 */     ArrayList localArrayList = null;
/* 1379 */     if (paramFile2.getCanonicalPath().startsWith(paramFile1.getCanonicalPath())) {
/* 1380 */       File[] arrayOfFile1 = paramFileFilter == null ? paramFile1.listFiles() : paramFile1.listFiles(paramFileFilter);
/* 1381 */       if ((arrayOfFile1 != null) && (arrayOfFile1.length > 0)) {
/* 1382 */         localArrayList = new ArrayList(arrayOfFile1.length);
/* 1383 */         for (File localFile1 : arrayOfFile1) {
/* 1384 */           File localFile2 = new File(paramFile2, localFile1.getName());
/* 1385 */           localArrayList.add(localFile2.getCanonicalPath());
/*      */         }
/*      */       }
/*      */     }
/* 1389 */     doCopyDirectory(paramFile1, paramFile2, paramFileFilter, paramBoolean, localArrayList);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void doCopyDirectory(File paramFile1, File paramFile2, FileFilter paramFileFilter, boolean paramBoolean, List<String> paramList)
/*      */     throws IOException
/*      */   {
/* 1406 */     File[] arrayOfFile1 = paramFileFilter == null ? paramFile1.listFiles() : paramFile1.listFiles(paramFileFilter);
/* 1407 */     if (arrayOfFile1 == null) {
/* 1408 */       throw new IOException("Failed to list contents of " + paramFile1);
/*      */     }
/* 1410 */     if (paramFile2.exists()) {
/* 1411 */       if (!paramFile2.isDirectory()) {
/* 1412 */         throw new IOException("Destination '" + paramFile2 + "' exists but is not a directory");
/*      */       }
/*      */     }
/* 1415 */     else if ((!paramFile2.mkdirs()) && (!paramFile2.isDirectory())) {
/* 1416 */       throw new IOException("Destination '" + paramFile2 + "' directory cannot be created");
/*      */     }
/*      */     
/* 1419 */     if (!paramFile2.canWrite()) {
/* 1420 */       throw new IOException("Destination '" + paramFile2 + "' cannot be written to");
/*      */     }
/* 1422 */     for (File localFile1 : arrayOfFile1) {
/* 1423 */       File localFile2 = new File(paramFile2, localFile1.getName());
/* 1424 */       if ((paramList == null) || (!paramList.contains(localFile1.getCanonicalPath()))) {
/* 1425 */         if (localFile1.isDirectory()) {
/* 1426 */           doCopyDirectory(localFile1, localFile2, paramFileFilter, paramBoolean, paramList);
/*      */         } else {
/* 1428 */           doCopyFile(localFile1, localFile2, paramBoolean);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1434 */     if (paramBoolean) {
/* 1435 */       paramFile2.setLastModified(paramFile1.lastModified());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyURLToFile(URL paramURL, File paramFile)
/*      */     throws IOException
/*      */   {
/* 1460 */     InputStream localInputStream = paramURL.openStream();
/* 1461 */     copyInputStreamToFile(localInputStream, paramFile);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyURLToFile(URL paramURL, File paramFile, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 1486 */     URLConnection localURLConnection = paramURL.openConnection();
/* 1487 */     localURLConnection.setConnectTimeout(paramInt1);
/* 1488 */     localURLConnection.setReadTimeout(paramInt2);
/* 1489 */     InputStream localInputStream = localURLConnection.getInputStream();
/* 1490 */     copyInputStreamToFile(localInputStream, paramFile);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void copyInputStreamToFile(InputStream paramInputStream, File paramFile)
/*      */     throws IOException
/*      */   {
/*      */     try
/*      */     {
/* 1510 */       FileOutputStream localFileOutputStream = openOutputStream(paramFile);
/*      */       try {
/* 1512 */         IOUtils.copy(paramInputStream, localFileOutputStream);
/* 1513 */         localFileOutputStream.close();
/*      */       }
/*      */       finally {}
/*      */     }
/*      */     finally {
/* 1518 */       IOUtils.closeQuietly(paramInputStream);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void deleteDirectory(File paramFile)
/*      */     throws IOException
/*      */   {
/* 1530 */     if (!paramFile.exists()) {
/* 1531 */       return;
/*      */     }
/*      */     
/* 1534 */     if (!isSymlink(paramFile)) {
/* 1535 */       cleanDirectory(paramFile);
/*      */     }
/*      */     
/* 1538 */     if (!paramFile.delete()) {
/* 1539 */       String str = "Unable to delete directory " + paramFile + ".";
/*      */       
/* 1541 */       throw new IOException(str);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean deleteQuietly(File paramFile)
/*      */   {
/* 1561 */     if (paramFile == null) {
/* 1562 */       return false;
/*      */     }
/*      */     try {
/* 1565 */       if (paramFile.isDirectory()) {
/* 1566 */         cleanDirectory(paramFile);
/*      */       }
/*      */     }
/*      */     catch (Exception localException1) {}
/*      */     try
/*      */     {
/* 1572 */       return paramFile.delete();
/*      */     } catch (Exception localException2) {}
/* 1574 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean directoryContains(File paramFile1, File paramFile2)
/*      */     throws IOException
/*      */   {
/* 1605 */     if (paramFile1 == null) {
/* 1606 */       throw new IllegalArgumentException("Directory must not be null");
/*      */     }
/*      */     
/* 1609 */     if (!paramFile1.isDirectory()) {
/* 1610 */       throw new IllegalArgumentException("Not a directory: " + paramFile1);
/*      */     }
/*      */     
/* 1613 */     if (paramFile2 == null) {
/* 1614 */       return false;
/*      */     }
/*      */     
/* 1617 */     if ((!paramFile1.exists()) || (!paramFile2.exists())) {
/* 1618 */       return false;
/*      */     }
/*      */     
/*      */ 
/* 1622 */     String str1 = paramFile1.getCanonicalPath();
/* 1623 */     String str2 = paramFile2.getCanonicalPath();
/*      */     
/* 1625 */     return FilenameUtils.directoryContains(str1, str2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void cleanDirectory(File paramFile)
/*      */     throws IOException
/*      */   {
/* 1635 */     if (!paramFile.exists()) {
/* 1636 */       localObject1 = paramFile + " does not exist";
/* 1637 */       throw new IllegalArgumentException((String)localObject1);
/*      */     }
/*      */     
/* 1640 */     if (!paramFile.isDirectory()) {
/* 1641 */       localObject1 = paramFile + " is not a directory";
/* 1642 */       throw new IllegalArgumentException((String)localObject1);
/*      */     }
/*      */     
/* 1645 */     Object localObject1 = paramFile.listFiles();
/* 1646 */     if (localObject1 == null) {
/* 1647 */       throw new IOException("Failed to list contents of " + paramFile);
/*      */     }
/*      */     
/* 1650 */     Object localObject2 = null;
/* 1651 */     for (File localFile : localObject1) {
/*      */       try {
/* 1653 */         forceDelete(localFile);
/*      */       } catch (IOException localIOException) {
/* 1655 */         localObject2 = localIOException;
/*      */       }
/*      */     }
/*      */     
/* 1659 */     if (null != localObject2) {
/* 1660 */       throw ((Throwable)localObject2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean waitFor(File paramFile, int paramInt)
/*      */   {
/* 1677 */     int i = 0;
/* 1678 */     int j = 0;
/* 1679 */     for (;;) { if (!paramFile.exists()) {
/* 1680 */         if (j++ >= 10) {
/* 1681 */           j = 0;
/* 1682 */           if (i++ > paramInt) {
/* 1683 */             return false;
/*      */           }
/*      */         }
/*      */         try {
/* 1687 */           Thread.sleep(100L);
/*      */         }
/*      */         catch (InterruptedException localInterruptedException) {}catch (Exception localException) {}
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1694 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String readFileToString(File paramFile, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1709 */     FileInputStream localFileInputStream = null;
/*      */     try {
/* 1711 */       localFileInputStream = openInputStream(paramFile);
/* 1712 */       return IOUtils.toString(localFileInputStream, Charsets.toCharset(paramCharset));
/*      */     } finally {
/* 1714 */       IOUtils.closeQuietly(localFileInputStream);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String readFileToString(File paramFile, String paramString)
/*      */     throws IOException
/*      */   {
/* 1734 */     return readFileToString(paramFile, Charsets.toCharset(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String readFileToString(File paramFile)
/*      */     throws IOException
/*      */   {
/* 1748 */     return readFileToString(paramFile, Charset.defaultCharset());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] readFileToByteArray(File paramFile)
/*      */     throws IOException
/*      */   {
/* 1761 */     FileInputStream localFileInputStream = null;
/*      */     try {
/* 1763 */       localFileInputStream = openInputStream(paramFile);
/* 1764 */       return IOUtils.toByteArray(localFileInputStream, paramFile.length());
/*      */     } finally {
/* 1766 */       IOUtils.closeQuietly(localFileInputStream);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<String> readLines(File paramFile, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1781 */     FileInputStream localFileInputStream = null;
/*      */     try {
/* 1783 */       localFileInputStream = openInputStream(paramFile);
/* 1784 */       return IOUtils.readLines(localFileInputStream, Charsets.toCharset(paramCharset));
/*      */     } finally {
/* 1786 */       IOUtils.closeQuietly(localFileInputStream);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<String> readLines(File paramFile, String paramString)
/*      */     throws IOException
/*      */   {
/* 1806 */     return readLines(paramFile, Charsets.toCharset(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static List<String> readLines(File paramFile)
/*      */     throws IOException
/*      */   {
/* 1819 */     return readLines(paramFile, Charset.defaultCharset());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static LineIterator lineIterator(File paramFile, String paramString)
/*      */     throws IOException
/*      */   {
/* 1854 */     FileInputStream localFileInputStream = null;
/*      */     try {
/* 1856 */       localFileInputStream = openInputStream(paramFile);
/* 1857 */       return IOUtils.lineIterator(localFileInputStream, paramString);
/*      */     } catch (IOException localIOException) {
/* 1859 */       IOUtils.closeQuietly(localFileInputStream);
/* 1860 */       throw localIOException;
/*      */     } catch (RuntimeException localRuntimeException) {
/* 1862 */       IOUtils.closeQuietly(localFileInputStream);
/* 1863 */       throw localRuntimeException;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static LineIterator lineIterator(File paramFile)
/*      */     throws IOException
/*      */   {
/* 1877 */     return lineIterator(paramFile, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeStringToFile(File paramFile, String paramString, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1895 */     writeStringToFile(paramFile, paramString, paramCharset, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeStringToFile(File paramFile, String paramString1, String paramString2)
/*      */     throws IOException
/*      */   {
/* 1911 */     writeStringToFile(paramFile, paramString1, paramString2, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeStringToFile(File paramFile, String paramString, Charset paramCharset, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1926 */     FileOutputStream localFileOutputStream = null;
/*      */     try {
/* 1928 */       localFileOutputStream = openOutputStream(paramFile, paramBoolean);
/* 1929 */       IOUtils.write(paramString, localFileOutputStream, paramCharset);
/* 1930 */       localFileOutputStream.close();
/*      */     } finally {
/* 1932 */       IOUtils.closeQuietly(localFileOutputStream);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeStringToFile(File paramFile, String paramString1, String paramString2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1951 */     writeStringToFile(paramFile, paramString1, Charsets.toCharset(paramString2), paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeStringToFile(File paramFile, String paramString)
/*      */     throws IOException
/*      */   {
/* 1962 */     writeStringToFile(paramFile, paramString, Charset.defaultCharset(), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeStringToFile(File paramFile, String paramString, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1976 */     writeStringToFile(paramFile, paramString, Charset.defaultCharset(), paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void write(File paramFile, CharSequence paramCharSequence)
/*      */     throws IOException
/*      */   {
/* 1988 */     write(paramFile, paramCharSequence, Charset.defaultCharset(), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void write(File paramFile, CharSequence paramCharSequence, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2002 */     write(paramFile, paramCharSequence, Charset.defaultCharset(), paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void write(File paramFile, CharSequence paramCharSequence, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 2015 */     write(paramFile, paramCharSequence, paramCharset, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void write(File paramFile, CharSequence paramCharSequence, String paramString)
/*      */     throws IOException
/*      */   {
/* 2029 */     write(paramFile, paramCharSequence, paramString, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void write(File paramFile, CharSequence paramCharSequence, Charset paramCharset, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2044 */     String str = paramCharSequence == null ? null : paramCharSequence.toString();
/* 2045 */     writeStringToFile(paramFile, str, paramCharset, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void write(File paramFile, CharSequence paramCharSequence, String paramString, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2063 */     write(paramFile, paramCharSequence, Charsets.toCharset(paramString), paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeByteArrayToFile(File paramFile, byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/* 2078 */     writeByteArrayToFile(paramFile, paramArrayOfByte, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeByteArrayToFile(File paramFile, byte[] paramArrayOfByte, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2092 */     FileOutputStream localFileOutputStream = null;
/*      */     try {
/* 2094 */       localFileOutputStream = openOutputStream(paramFile, paramBoolean);
/* 2095 */       localFileOutputStream.write(paramArrayOfByte);
/* 2096 */       localFileOutputStream.close();
/*      */     } finally {
/* 2098 */       IOUtils.closeQuietly(localFileOutputStream);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeLines(File paramFile, String paramString, Collection<?> paramCollection)
/*      */     throws IOException
/*      */   {
/* 2118 */     writeLines(paramFile, paramString, paramCollection, null, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeLines(File paramFile, String paramString, Collection<?> paramCollection, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2136 */     writeLines(paramFile, paramString, paramCollection, null, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeLines(File paramFile, Collection<?> paramCollection)
/*      */     throws IOException
/*      */   {
/* 2150 */     writeLines(paramFile, null, paramCollection, null, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeLines(File paramFile, Collection<?> paramCollection, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2166 */     writeLines(paramFile, null, paramCollection, null, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeLines(File paramFile, String paramString1, Collection<?> paramCollection, String paramString2)
/*      */     throws IOException
/*      */   {
/* 2187 */     writeLines(paramFile, paramString1, paramCollection, paramString2, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeLines(File paramFile, String paramString1, Collection<?> paramCollection, String paramString2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2207 */     FileOutputStream localFileOutputStream = null;
/*      */     try {
/* 2209 */       localFileOutputStream = openOutputStream(paramFile, paramBoolean);
/* 2210 */       BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localFileOutputStream);
/* 2211 */       IOUtils.writeLines(paramCollection, paramString2, localBufferedOutputStream, paramString1);
/* 2212 */       localBufferedOutputStream.flush();
/* 2213 */       localFileOutputStream.close();
/*      */     } finally {
/* 2215 */       IOUtils.closeQuietly(localFileOutputStream);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeLines(File paramFile, Collection<?> paramCollection, String paramString)
/*      */     throws IOException
/*      */   {
/* 2231 */     writeLines(paramFile, null, paramCollection, paramString, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void writeLines(File paramFile, Collection<?> paramCollection, String paramString, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2249 */     writeLines(paramFile, null, paramCollection, paramString, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void forceDelete(File paramFile)
/*      */     throws IOException
/*      */   {
/* 2269 */     if (paramFile.isDirectory()) {
/* 2270 */       deleteDirectory(paramFile);
/*      */     } else {
/* 2272 */       boolean bool = paramFile.exists();
/* 2273 */       if (!paramFile.delete()) {
/* 2274 */         if (!bool) {
/* 2275 */           throw new FileNotFoundException("File does not exist: " + paramFile);
/*      */         }
/* 2277 */         String str = "Unable to delete file: " + paramFile;
/*      */         
/* 2279 */         throw new IOException(str);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void forceDeleteOnExit(File paramFile)
/*      */     throws IOException
/*      */   {
/* 2293 */     if (paramFile.isDirectory()) {
/* 2294 */       deleteDirectoryOnExit(paramFile);
/*      */     } else {
/* 2296 */       paramFile.deleteOnExit();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void deleteDirectoryOnExit(File paramFile)
/*      */     throws IOException
/*      */   {
/* 2308 */     if (!paramFile.exists()) {
/* 2309 */       return;
/*      */     }
/*      */     
/* 2312 */     paramFile.deleteOnExit();
/* 2313 */     if (!isSymlink(paramFile)) {
/* 2314 */       cleanDirectoryOnExit(paramFile);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void cleanDirectoryOnExit(File paramFile)
/*      */     throws IOException
/*      */   {
/* 2326 */     if (!paramFile.exists()) {
/* 2327 */       localObject1 = paramFile + " does not exist";
/* 2328 */       throw new IllegalArgumentException((String)localObject1);
/*      */     }
/*      */     
/* 2331 */     if (!paramFile.isDirectory()) {
/* 2332 */       localObject1 = paramFile + " is not a directory";
/* 2333 */       throw new IllegalArgumentException((String)localObject1);
/*      */     }
/*      */     
/* 2336 */     Object localObject1 = paramFile.listFiles();
/* 2337 */     if (localObject1 == null) {
/* 2338 */       throw new IOException("Failed to list contents of " + paramFile);
/*      */     }
/*      */     
/* 2341 */     Object localObject2 = null;
/* 2342 */     for (File localFile : localObject1) {
/*      */       try {
/* 2344 */         forceDeleteOnExit(localFile);
/*      */       } catch (IOException localIOException) {
/* 2346 */         localObject2 = localIOException;
/*      */       }
/*      */     }
/*      */     
/* 2350 */     if (null != localObject2) {
/* 2351 */       throw ((Throwable)localObject2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void forceMkdir(File paramFile)
/*      */     throws IOException
/*      */   {
/*      */     String str;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2367 */     if (paramFile.exists()) {
/* 2368 */       if (!paramFile.isDirectory()) {
/* 2369 */         str = "File " + paramFile + " exists and is " + "not a directory. Unable to create directory.";
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 2374 */         throw new IOException(str);
/*      */       }
/*      */     }
/* 2377 */     else if (!paramFile.mkdirs())
/*      */     {
/*      */ 
/* 2380 */       if (!paramFile.isDirectory())
/*      */       {
/* 2382 */         str = "Unable to create directory " + paramFile;
/*      */         
/* 2384 */         throw new IOException(str);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long sizeOf(File paramFile)
/*      */   {
/* 2411 */     if (!paramFile.exists()) {
/* 2412 */       String str = paramFile + " does not exist";
/* 2413 */       throw new IllegalArgumentException(str);
/*      */     }
/*      */     
/* 2416 */     if (paramFile.isDirectory()) {
/* 2417 */       return sizeOfDirectory(paramFile);
/*      */     }
/* 2419 */     return paramFile.length();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static BigInteger sizeOfAsBigInteger(File paramFile)
/*      */   {
/* 2444 */     if (!paramFile.exists()) {
/* 2445 */       String str = paramFile + " does not exist";
/* 2446 */       throw new IllegalArgumentException(str);
/*      */     }
/*      */     
/* 2449 */     if (paramFile.isDirectory()) {
/* 2450 */       return sizeOfDirectoryAsBigInteger(paramFile);
/*      */     }
/* 2452 */     return BigInteger.valueOf(paramFile.length());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long sizeOfDirectory(File paramFile)
/*      */   {
/* 2468 */     checkDirectory(paramFile);
/*      */     
/* 2470 */     File[] arrayOfFile1 = paramFile.listFiles();
/* 2471 */     if (arrayOfFile1 == null) {
/* 2472 */       return 0L;
/*      */     }
/* 2474 */     long l = 0L;
/*      */     
/* 2476 */     for (File localFile : arrayOfFile1) {
/*      */       try {
/* 2478 */         if (!isSymlink(localFile)) {
/* 2479 */           l += sizeOf(localFile);
/* 2480 */           if (l < 0L) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (IOException localIOException) {}
/*      */     }
/*      */     
/*      */ 
/* 2489 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static BigInteger sizeOfDirectoryAsBigInteger(File paramFile)
/*      */   {
/* 2503 */     checkDirectory(paramFile);
/*      */     
/* 2505 */     File[] arrayOfFile1 = paramFile.listFiles();
/* 2506 */     if (arrayOfFile1 == null) {
/* 2507 */       return BigInteger.ZERO;
/*      */     }
/* 2509 */     BigInteger localBigInteger = BigInteger.ZERO;
/*      */     
/* 2511 */     for (File localFile : arrayOfFile1) {
/*      */       try {
/* 2513 */         if (!isSymlink(localFile)) {
/* 2514 */           localBigInteger = localBigInteger.add(BigInteger.valueOf(sizeOf(localFile)));
/*      */         }
/*      */       }
/*      */       catch (IOException localIOException) {}
/*      */     }
/*      */     
/*      */ 
/* 2521 */     return localBigInteger;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void checkDirectory(File paramFile)
/*      */   {
/* 2531 */     if (!paramFile.exists()) {
/* 2532 */       throw new IllegalArgumentException(paramFile + " does not exist");
/*      */     }
/* 2534 */     if (!paramFile.isDirectory()) {
/* 2535 */       throw new IllegalArgumentException(paramFile + " is not a directory");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isFileNewer(File paramFile1, File paramFile2)
/*      */   {
/* 2554 */     if (paramFile2 == null) {
/* 2555 */       throw new IllegalArgumentException("No specified reference file");
/*      */     }
/* 2557 */     if (!paramFile2.exists()) {
/* 2558 */       throw new IllegalArgumentException("The reference file '" + paramFile2 + "' doesn't exist");
/*      */     }
/*      */     
/* 2561 */     return isFileNewer(paramFile1, paramFile2.lastModified());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isFileNewer(File paramFile, Date paramDate)
/*      */   {
/* 2577 */     if (paramDate == null) {
/* 2578 */       throw new IllegalArgumentException("No specified date");
/*      */     }
/* 2580 */     return isFileNewer(paramFile, paramDate.getTime());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isFileNewer(File paramFile, long paramLong)
/*      */   {
/* 2596 */     if (paramFile == null) {
/* 2597 */       throw new IllegalArgumentException("No specified file");
/*      */     }
/* 2599 */     if (!paramFile.exists()) {
/* 2600 */       return false;
/*      */     }
/* 2602 */     return paramFile.lastModified() > paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isFileOlder(File paramFile1, File paramFile2)
/*      */   {
/* 2621 */     if (paramFile2 == null) {
/* 2622 */       throw new IllegalArgumentException("No specified reference file");
/*      */     }
/* 2624 */     if (!paramFile2.exists()) {
/* 2625 */       throw new IllegalArgumentException("The reference file '" + paramFile2 + "' doesn't exist");
/*      */     }
/*      */     
/* 2628 */     return isFileOlder(paramFile1, paramFile2.lastModified());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isFileOlder(File paramFile, Date paramDate)
/*      */   {
/* 2644 */     if (paramDate == null) {
/* 2645 */       throw new IllegalArgumentException("No specified date");
/*      */     }
/* 2647 */     return isFileOlder(paramFile, paramDate.getTime());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isFileOlder(File paramFile, long paramLong)
/*      */   {
/* 2663 */     if (paramFile == null) {
/* 2664 */       throw new IllegalArgumentException("No specified file");
/*      */     }
/* 2666 */     if (!paramFile.exists()) {
/* 2667 */       return false;
/*      */     }
/* 2669 */     return paramFile.lastModified() < paramLong;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static long checksumCRC32(File paramFile)
/*      */     throws IOException
/*      */   {
/* 2685 */     CRC32 localCRC32 = new CRC32();
/* 2686 */     checksum(paramFile, localCRC32);
/* 2687 */     return localCRC32.getValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Checksum checksum(File paramFile, Checksum paramChecksum)
/*      */     throws IOException
/*      */   {
/* 2708 */     if (paramFile.isDirectory()) {
/* 2709 */       throw new IllegalArgumentException("Checksums can't be computed on directories");
/*      */     }
/* 2711 */     CheckedInputStream localCheckedInputStream = null;
/*      */     try {
/* 2713 */       localCheckedInputStream = new CheckedInputStream(new FileInputStream(paramFile), paramChecksum);
/* 2714 */       IOUtils.copy(localCheckedInputStream, new NullOutputStream());
/*      */     } finally {
/* 2716 */       IOUtils.closeQuietly(localCheckedInputStream);
/*      */     }
/* 2718 */     return paramChecksum;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void moveDirectory(File paramFile1, File paramFile2)
/*      */     throws IOException
/*      */   {
/* 2735 */     if (paramFile1 == null) {
/* 2736 */       throw new NullPointerException("Source must not be null");
/*      */     }
/* 2738 */     if (paramFile2 == null) {
/* 2739 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/* 2741 */     if (!paramFile1.exists()) {
/* 2742 */       throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
/*      */     }
/* 2744 */     if (!paramFile1.isDirectory()) {
/* 2745 */       throw new IOException("Source '" + paramFile1 + "' is not a directory");
/*      */     }
/* 2747 */     if (paramFile2.exists()) {
/* 2748 */       throw new FileExistsException("Destination '" + paramFile2 + "' already exists");
/*      */     }
/* 2750 */     boolean bool = paramFile1.renameTo(paramFile2);
/* 2751 */     if (!bool) {
/* 2752 */       if (paramFile2.getCanonicalPath().startsWith(paramFile1.getCanonicalPath())) {
/* 2753 */         throw new IOException("Cannot move directory: " + paramFile1 + " to a subdirectory of itself: " + paramFile2);
/*      */       }
/* 2755 */       copyDirectory(paramFile1, paramFile2);
/* 2756 */       deleteDirectory(paramFile1);
/* 2757 */       if (paramFile1.exists()) {
/* 2758 */         throw new IOException("Failed to delete original directory '" + paramFile1 + "' after copy to '" + paramFile2 + "'");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void moveDirectoryToDirectory(File paramFile1, File paramFile2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2778 */     if (paramFile1 == null) {
/* 2779 */       throw new NullPointerException("Source must not be null");
/*      */     }
/* 2781 */     if (paramFile2 == null) {
/* 2782 */       throw new NullPointerException("Destination directory must not be null");
/*      */     }
/* 2784 */     if ((!paramFile2.exists()) && (paramBoolean)) {
/* 2785 */       paramFile2.mkdirs();
/*      */     }
/* 2787 */     if (!paramFile2.exists()) {
/* 2788 */       throw new FileNotFoundException("Destination directory '" + paramFile2 + "' does not exist [createDestDir=" + paramBoolean + "]");
/*      */     }
/*      */     
/* 2791 */     if (!paramFile2.isDirectory()) {
/* 2792 */       throw new IOException("Destination '" + paramFile2 + "' is not a directory");
/*      */     }
/* 2794 */     moveDirectory(paramFile1, new File(paramFile2, paramFile1.getName()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void moveFile(File paramFile1, File paramFile2)
/*      */     throws IOException
/*      */   {
/* 2812 */     if (paramFile1 == null) {
/* 2813 */       throw new NullPointerException("Source must not be null");
/*      */     }
/* 2815 */     if (paramFile2 == null) {
/* 2816 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/* 2818 */     if (!paramFile1.exists()) {
/* 2819 */       throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
/*      */     }
/* 2821 */     if (paramFile1.isDirectory()) {
/* 2822 */       throw new IOException("Source '" + paramFile1 + "' is a directory");
/*      */     }
/* 2824 */     if (paramFile2.exists()) {
/* 2825 */       throw new FileExistsException("Destination '" + paramFile2 + "' already exists");
/*      */     }
/* 2827 */     if (paramFile2.isDirectory()) {
/* 2828 */       throw new IOException("Destination '" + paramFile2 + "' is a directory");
/*      */     }
/* 2830 */     boolean bool = paramFile1.renameTo(paramFile2);
/* 2831 */     if (!bool) {
/* 2832 */       copyFile(paramFile1, paramFile2);
/* 2833 */       if (!paramFile1.delete()) {
/* 2834 */         deleteQuietly(paramFile2);
/* 2835 */         throw new IOException("Failed to delete original file '" + paramFile1 + "' after copy to '" + paramFile2 + "'");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void moveFileToDirectory(File paramFile1, File paramFile2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2855 */     if (paramFile1 == null) {
/* 2856 */       throw new NullPointerException("Source must not be null");
/*      */     }
/* 2858 */     if (paramFile2 == null) {
/* 2859 */       throw new NullPointerException("Destination directory must not be null");
/*      */     }
/* 2861 */     if ((!paramFile2.exists()) && (paramBoolean)) {
/* 2862 */       paramFile2.mkdirs();
/*      */     }
/* 2864 */     if (!paramFile2.exists()) {
/* 2865 */       throw new FileNotFoundException("Destination directory '" + paramFile2 + "' does not exist [createDestDir=" + paramBoolean + "]");
/*      */     }
/*      */     
/* 2868 */     if (!paramFile2.isDirectory()) {
/* 2869 */       throw new IOException("Destination '" + paramFile2 + "' is not a directory");
/*      */     }
/* 2871 */     moveFile(paramFile1, new File(paramFile2, paramFile1.getName()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static void moveToDirectory(File paramFile1, File paramFile2, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 2890 */     if (paramFile1 == null) {
/* 2891 */       throw new NullPointerException("Source must not be null");
/*      */     }
/* 2893 */     if (paramFile2 == null) {
/* 2894 */       throw new NullPointerException("Destination must not be null");
/*      */     }
/* 2896 */     if (!paramFile1.exists()) {
/* 2897 */       throw new FileNotFoundException("Source '" + paramFile1 + "' does not exist");
/*      */     }
/* 2899 */     if (paramFile1.isDirectory()) {
/* 2900 */       moveDirectoryToDirectory(paramFile1, paramFile2, paramBoolean);
/*      */     } else {
/* 2902 */       moveFileToDirectory(paramFile1, paramFile2, paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isSymlink(File paramFile)
/*      */     throws IOException
/*      */   {
/* 2921 */     if (paramFile == null) {
/* 2922 */       throw new NullPointerException("File must not be null");
/*      */     }
/* 2924 */     if (FilenameUtils.isSystemWindows()) {
/* 2925 */       return false;
/*      */     }
/* 2927 */     File localFile1 = null;
/* 2928 */     if (paramFile.getParent() == null) {
/* 2929 */       localFile1 = paramFile;
/*      */     } else {
/* 2931 */       File localFile2 = paramFile.getParentFile().getCanonicalFile();
/* 2932 */       localFile1 = new File(localFile2, paramFile.getName());
/*      */     }
/*      */     
/* 2935 */     if (localFile1.getCanonicalFile().equals(localFile1.getAbsoluteFile())) {
/* 2936 */       return false;
/*      */     }
/* 2938 */     return true;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */