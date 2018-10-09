/*      */ package org.apache.commons.io;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.CharArrayWriter;
/*      */ import java.io.Closeable;
/*      */ import java.io.EOFException;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.ServerSocket;
/*      */ import java.net.Socket;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.nio.channels.Selector;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import org.apache.commons.io.output.ByteArrayOutputStream;
/*      */ import org.apache.commons.io.output.StringBuilderWriter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IOUtils
/*      */ {
/*      */   private static final int EOF = -1;
/*      */   public static final char DIR_SEPARATOR_UNIX = '/';
/*      */   public static final char DIR_SEPARATOR_WINDOWS = '\\';
/*  101 */   public static final char DIR_SEPARATOR = File.separatorChar;
/*      */   
/*      */   public static final String LINE_SEPARATOR_UNIX = "\n";
/*      */   
/*      */   public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
/*      */   
/*      */   public static final String LINE_SEPARATOR;
/*      */   
/*      */   private static final int DEFAULT_BUFFER_SIZE = 4096;
/*      */   
/*      */   private static final int SKIP_BUFFER_SIZE = 2048;
/*      */   private static char[] SKIP_CHAR_BUFFER;
/*      */   private static byte[] SKIP_BYTE_BUFFER;
/*      */   
/*      */   static
/*      */   {
/*  117 */     StringBuilderWriter localStringBuilderWriter = new StringBuilderWriter(4);
/*  118 */     PrintWriter localPrintWriter = new PrintWriter(localStringBuilderWriter);
/*  119 */     localPrintWriter.println();
/*  120 */     LINE_SEPARATOR = localStringBuilderWriter.toString();
/*  121 */     localPrintWriter.close();
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
/*      */   public static void close(URLConnection paramURLConnection)
/*      */   {
/*  164 */     if ((paramURLConnection instanceof HttpURLConnection)) {
/*  165 */       ((HttpURLConnection)paramURLConnection).disconnect();
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
/*      */   public static void closeQuietly(Reader paramReader)
/*      */   {
/*  193 */     closeQuietly(paramReader);
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
/*      */   public static void closeQuietly(Writer paramWriter)
/*      */   {
/*  219 */     closeQuietly(paramWriter);
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
/*      */   public static void closeQuietly(InputStream paramInputStream)
/*      */   {
/*  246 */     closeQuietly(paramInputStream);
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
/*      */   public static void closeQuietly(OutputStream paramOutputStream)
/*      */   {
/*  274 */     closeQuietly(paramOutputStream);
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
/*      */   public static void closeQuietly(Closeable paramCloseable)
/*      */   {
/*      */     try
/*      */     {
/*  302 */       if (paramCloseable != null) {
/*  303 */         paramCloseable.close();
/*      */       }
/*      */     }
/*      */     catch (IOException localIOException) {}
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
/*      */   public static void closeQuietly(Socket paramSocket)
/*      */   {
/*  334 */     if (paramSocket != null) {
/*      */       try {
/*  336 */         paramSocket.close();
/*      */       }
/*      */       catch (IOException localIOException) {}
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
/*      */   public static void closeQuietly(Selector paramSelector)
/*      */   {
/*  367 */     if (paramSelector != null) {
/*      */       try {
/*  369 */         paramSelector.close();
/*      */       }
/*      */       catch (IOException localIOException) {}
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
/*      */   public static void closeQuietly(ServerSocket paramServerSocket)
/*      */   {
/*  400 */     if (paramServerSocket != null) {
/*      */       try {
/*  402 */         paramServerSocket.close();
/*      */       }
/*      */       catch (IOException localIOException) {}
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
/*      */   public static InputStream toBufferedInputStream(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/*  431 */     return ByteArrayOutputStream.toBufferedInputStream(paramInputStream);
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
/*      */   public static BufferedReader toBufferedReader(Reader paramReader)
/*      */   {
/*  444 */     return (paramReader instanceof BufferedReader) ? (BufferedReader)paramReader : new BufferedReader(paramReader);
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
/*      */   public static byte[] toByteArray(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/*  461 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*  462 */     copy(paramInputStream, localByteArrayOutputStream);
/*  463 */     return localByteArrayOutputStream.toByteArray();
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
/*      */   public static byte[] toByteArray(InputStream paramInputStream, long paramLong)
/*      */     throws IOException
/*      */   {
/*  484 */     if (paramLong > 2147483647L) {
/*  485 */       throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + paramLong);
/*      */     }
/*      */     
/*  488 */     return toByteArray(paramInputStream, (int)paramLong);
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
/*      */   public static byte[] toByteArray(InputStream paramInputStream, int paramInt)
/*      */     throws IOException
/*      */   {
/*  504 */     if (paramInt < 0) {
/*  505 */       throw new IllegalArgumentException("Size must be equal or greater than zero: " + paramInt);
/*      */     }
/*      */     
/*  508 */     if (paramInt == 0) {
/*  509 */       return new byte[0];
/*      */     }
/*      */     
/*  512 */     byte[] arrayOfByte = new byte[paramInt];
/*  513 */     int i = 0;
/*      */     
/*      */     int j;
/*  516 */     while ((i < paramInt) && ((j = paramInputStream.read(arrayOfByte, i, paramInt - i)) != -1)) {
/*  517 */       i += j;
/*      */     }
/*      */     
/*  520 */     if (i != paramInt) {
/*  521 */       throw new IOException("Unexpected readed size. current: " + i + ", excepted: " + paramInt);
/*      */     }
/*      */     
/*  524 */     return arrayOfByte;
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
/*      */   public static byte[] toByteArray(Reader paramReader)
/*      */     throws IOException
/*      */   {
/*  540 */     return toByteArray(paramReader, Charset.defaultCharset());
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
/*      */   public static byte[] toByteArray(Reader paramReader, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/*  558 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*  559 */     copy(paramReader, localByteArrayOutputStream, paramCharset);
/*  560 */     return localByteArrayOutputStream.toByteArray();
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
/*      */   public static byte[] toByteArray(Reader paramReader, String paramString)
/*      */     throws IOException
/*      */   {
/*  584 */     return toByteArray(paramReader, Charsets.toCharset(paramString));
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
/*      */   @Deprecated
/*      */   public static byte[] toByteArray(String paramString)
/*      */     throws IOException
/*      */   {
/*  601 */     return paramString.getBytes();
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
/*      */   public static byte[] toByteArray(URI paramURI)
/*      */     throws IOException
/*      */   {
/*  617 */     return toByteArray(paramURI.toURL());
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
/*      */   public static byte[] toByteArray(URL paramURL)
/*      */     throws IOException
/*      */   {
/*  633 */     URLConnection localURLConnection = paramURL.openConnection();
/*      */     try {
/*  635 */       return toByteArray(localURLConnection);
/*      */     } finally {
/*  637 */       close(localURLConnection);
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
/*      */   public static byte[] toByteArray(URLConnection paramURLConnection)
/*      */     throws IOException
/*      */   {
/*  654 */     InputStream localInputStream = paramURLConnection.getInputStream();
/*      */     try {
/*  656 */       return toByteArray(localInputStream);
/*      */     } finally {
/*  658 */       localInputStream.close();
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
/*      */   public static char[] toCharArray(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/*  678 */     return toCharArray(paramInputStream, Charset.defaultCharset());
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
/*      */   public static char[] toCharArray(InputStream paramInputStream, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/*  697 */     CharArrayWriter localCharArrayWriter = new CharArrayWriter();
/*  698 */     copy(paramInputStream, localCharArrayWriter, paramCharset);
/*  699 */     return localCharArrayWriter.toCharArray();
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
/*      */   public static char[] toCharArray(InputStream paramInputStream, String paramString)
/*      */     throws IOException
/*      */   {
/*  723 */     return toCharArray(paramInputStream, Charsets.toCharset(paramString));
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
/*      */   public static char[] toCharArray(Reader paramReader)
/*      */     throws IOException
/*      */   {
/*  739 */     CharArrayWriter localCharArrayWriter = new CharArrayWriter();
/*  740 */     copy(paramReader, localCharArrayWriter);
/*  741 */     return localCharArrayWriter.toCharArray();
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
/*      */   public static String toString(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/*  759 */     return toString(paramInputStream, Charset.defaultCharset());
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
/*      */   public static String toString(InputStream paramInputStream, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/*  777 */     StringBuilderWriter localStringBuilderWriter = new StringBuilderWriter();
/*  778 */     copy(paramInputStream, localStringBuilderWriter, paramCharset);
/*  779 */     return localStringBuilderWriter.toString();
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
/*      */   public static String toString(InputStream paramInputStream, String paramString)
/*      */     throws IOException
/*      */   {
/*  803 */     return toString(paramInputStream, Charsets.toCharset(paramString));
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
/*      */   public static String toString(Reader paramReader)
/*      */     throws IOException
/*      */   {
/*  818 */     StringBuilderWriter localStringBuilderWriter = new StringBuilderWriter();
/*  819 */     copy(paramReader, localStringBuilderWriter);
/*  820 */     return localStringBuilderWriter.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toString(URI paramURI)
/*      */     throws IOException
/*      */   {
/*  833 */     return toString(paramURI, Charset.defaultCharset());
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
/*      */   public static String toString(URI paramURI, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/*  848 */     return toString(paramURI.toURL(), Charsets.toCharset(paramCharset));
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
/*      */   public static String toString(URI paramURI, String paramString)
/*      */     throws IOException
/*      */   {
/*  866 */     return toString(paramURI, Charsets.toCharset(paramString));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String toString(URL paramURL)
/*      */     throws IOException
/*      */   {
/*  879 */     return toString(paramURL, Charset.defaultCharset());
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
/*      */   public static String toString(URL paramURL, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/*  894 */     InputStream localInputStream = paramURL.openStream();
/*      */     try {
/*  896 */       return toString(localInputStream, paramCharset);
/*      */     } finally {
/*  898 */       localInputStream.close();
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
/*      */   public static String toString(URL paramURL, String paramString)
/*      */     throws IOException
/*      */   {
/*  917 */     return toString(paramURL, Charsets.toCharset(paramString));
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
/*      */   @Deprecated
/*      */   public static String toString(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/*  932 */     return new String(paramArrayOfByte);
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
/*      */   public static String toString(byte[] paramArrayOfByte, String paramString)
/*      */     throws IOException
/*      */   {
/*  949 */     return new String(paramArrayOfByte, Charsets.toCharset(paramString));
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
/*      */   public static List<String> readLines(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/*  968 */     return readLines(paramInputStream, Charset.defaultCharset());
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
/*      */   public static List<String> readLines(InputStream paramInputStream, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/*  986 */     InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream, Charsets.toCharset(paramCharset));
/*  987 */     return readLines(localInputStreamReader);
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
/*      */   public static List<String> readLines(InputStream paramInputStream, String paramString)
/*      */     throws IOException
/*      */   {
/* 1011 */     return readLines(paramInputStream, Charsets.toCharset(paramString));
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
/*      */   public static List<String> readLines(Reader paramReader)
/*      */     throws IOException
/*      */   {
/* 1028 */     BufferedReader localBufferedReader = toBufferedReader(paramReader);
/* 1029 */     ArrayList localArrayList = new ArrayList();
/* 1030 */     String str = localBufferedReader.readLine();
/* 1031 */     while (str != null) {
/* 1032 */       localArrayList.add(str);
/* 1033 */       str = localBufferedReader.readLine();
/*      */     }
/* 1035 */     return localArrayList;
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
/*      */   public static LineIterator lineIterator(Reader paramReader)
/*      */   {
/* 1068 */     return new LineIterator(paramReader);
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
/*      */   public static LineIterator lineIterator(InputStream paramInputStream, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1102 */     return new LineIterator(new InputStreamReader(paramInputStream, Charsets.toCharset(paramCharset)));
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
/*      */   public static LineIterator lineIterator(InputStream paramInputStream, String paramString)
/*      */     throws IOException
/*      */   {
/* 1139 */     return lineIterator(paramInputStream, Charsets.toCharset(paramString));
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
/*      */   public static InputStream toInputStream(CharSequence paramCharSequence)
/*      */   {
/* 1152 */     return toInputStream(paramCharSequence, Charset.defaultCharset());
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
/*      */   public static InputStream toInputStream(CharSequence paramCharSequence, Charset paramCharset)
/*      */   {
/* 1165 */     return toInputStream(paramCharSequence.toString(), paramCharset);
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
/*      */   public static InputStream toInputStream(CharSequence paramCharSequence, String paramString)
/*      */     throws IOException
/*      */   {
/* 1185 */     return toInputStream(paramCharSequence, Charsets.toCharset(paramString));
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
/*      */   public static InputStream toInputStream(String paramString)
/*      */   {
/* 1198 */     return toInputStream(paramString, Charset.defaultCharset());
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
/*      */   public static InputStream toInputStream(String paramString, Charset paramCharset)
/*      */   {
/* 1211 */     return new ByteArrayInputStream(paramString.getBytes(Charsets.toCharset(paramCharset)));
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
/*      */   public static InputStream toInputStream(String paramString1, String paramString2)
/*      */     throws IOException
/*      */   {
/* 1231 */     byte[] arrayOfByte = paramString1.getBytes(Charsets.toCharset(paramString2));
/* 1232 */     return new ByteArrayInputStream(arrayOfByte);
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
/*      */   public static void write(byte[] paramArrayOfByte, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1249 */     if (paramArrayOfByte != null) {
/* 1250 */       paramOutputStream.write(paramArrayOfByte);
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
/*      */   public static void write(byte[] paramArrayOfByte, Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1268 */     write(paramArrayOfByte, paramWriter, Charset.defaultCharset());
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
/*      */   public static void write(byte[] paramArrayOfByte, Writer paramWriter, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1286 */     if (paramArrayOfByte != null) {
/* 1287 */       paramWriter.write(new String(paramArrayOfByte, Charsets.toCharset(paramCharset)));
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
/*      */   public static void write(byte[] paramArrayOfByte, Writer paramWriter, String paramString)
/*      */     throws IOException
/*      */   {
/* 1312 */     write(paramArrayOfByte, paramWriter, Charsets.toCharset(paramString));
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
/*      */   public static void write(char[] paramArrayOfChar, Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1329 */     if (paramArrayOfChar != null) {
/* 1330 */       paramWriter.write(paramArrayOfChar);
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
/*      */   public static void write(char[] paramArrayOfChar, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1350 */     write(paramArrayOfChar, paramOutputStream, Charset.defaultCharset());
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
/*      */   public static void write(char[] paramArrayOfChar, OutputStream paramOutputStream, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1369 */     if (paramArrayOfChar != null) {
/* 1370 */       paramOutputStream.write(new String(paramArrayOfChar).getBytes(Charsets.toCharset(paramCharset)));
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
/*      */   public static void write(char[] paramArrayOfChar, OutputStream paramOutputStream, String paramString)
/*      */     throws IOException
/*      */   {
/* 1397 */     write(paramArrayOfChar, paramOutputStream, Charsets.toCharset(paramString));
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
/*      */   public static void write(CharSequence paramCharSequence, Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1412 */     if (paramCharSequence != null) {
/* 1413 */       write(paramCharSequence.toString(), paramWriter);
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
/*      */   public static void write(CharSequence paramCharSequence, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1432 */     write(paramCharSequence, paramOutputStream, Charset.defaultCharset());
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
/*      */   public static void write(CharSequence paramCharSequence, OutputStream paramOutputStream, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1449 */     if (paramCharSequence != null) {
/* 1450 */       write(paramCharSequence.toString(), paramOutputStream, paramCharset);
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
/*      */   public static void write(CharSequence paramCharSequence, OutputStream paramOutputStream, String paramString)
/*      */     throws IOException
/*      */   {
/* 1474 */     write(paramCharSequence, paramOutputStream, Charsets.toCharset(paramString));
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
/*      */   public static void write(String paramString, Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1489 */     if (paramString != null) {
/* 1490 */       paramWriter.write(paramString);
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
/*      */   public static void write(String paramString, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1509 */     write(paramString, paramOutputStream, Charset.defaultCharset());
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
/*      */   public static void write(String paramString, OutputStream paramOutputStream, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1526 */     if (paramString != null) {
/* 1527 */       paramOutputStream.write(paramString.getBytes(Charsets.toCharset(paramCharset)));
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
/*      */   public static void write(String paramString1, OutputStream paramOutputStream, String paramString2)
/*      */     throws IOException
/*      */   {
/* 1552 */     write(paramString1, paramOutputStream, Charsets.toCharset(paramString2));
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
/*      */   @Deprecated
/*      */   public static void write(StringBuffer paramStringBuffer, Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1570 */     if (paramStringBuffer != null) {
/* 1571 */       paramWriter.write(paramStringBuffer.toString());
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
/*      */   @Deprecated
/*      */   public static void write(StringBuffer paramStringBuffer, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1592 */     write(paramStringBuffer, paramOutputStream, (String)null);
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
/*      */   @Deprecated
/*      */   public static void write(StringBuffer paramStringBuffer, OutputStream paramOutputStream, String paramString)
/*      */     throws IOException
/*      */   {
/* 1617 */     if (paramStringBuffer != null) {
/* 1618 */       paramOutputStream.write(paramStringBuffer.toString().getBytes(Charsets.toCharset(paramString)));
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
/*      */   public static void writeLines(Collection<?> paramCollection, String paramString, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1638 */     writeLines(paramCollection, paramString, paramOutputStream, Charset.defaultCharset());
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
/*      */   public static void writeLines(Collection<?> paramCollection, String paramString, OutputStream paramOutputStream, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1656 */     if (paramCollection == null) {
/* 1657 */       return;
/*      */     }
/* 1659 */     if (paramString == null) {
/* 1660 */       paramString = LINE_SEPARATOR;
/*      */     }
/* 1662 */     Charset localCharset = Charsets.toCharset(paramCharset);
/* 1663 */     for (Object localObject : paramCollection) {
/* 1664 */       if (localObject != null) {
/* 1665 */         paramOutputStream.write(localObject.toString().getBytes(localCharset));
/*      */       }
/* 1667 */       paramOutputStream.write(paramString.getBytes(localCharset));
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
/*      */   public static void writeLines(Collection<?> paramCollection, String paramString1, OutputStream paramOutputStream, String paramString2)
/*      */     throws IOException
/*      */   {
/* 1692 */     writeLines(paramCollection, paramString1, paramOutputStream, Charsets.toCharset(paramString2));
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
/*      */   public static void writeLines(Collection<?> paramCollection, String paramString, Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1708 */     if (paramCollection == null) {
/* 1709 */       return;
/*      */     }
/* 1711 */     if (paramString == null) {
/* 1712 */       paramString = LINE_SEPARATOR;
/*      */     }
/* 1714 */     for (Object localObject : paramCollection) {
/* 1715 */       if (localObject != null) {
/* 1716 */         paramWriter.write(localObject.toString());
/*      */       }
/* 1718 */       paramWriter.write(paramString);
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
/*      */   public static int copy(InputStream paramInputStream, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1744 */     long l = copyLarge(paramInputStream, paramOutputStream);
/* 1745 */     if (l > 2147483647L) {
/* 1746 */       return -1;
/*      */     }
/* 1748 */     return (int)l;
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
/*      */   public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1769 */     return copyLarge(paramInputStream, paramOutputStream, new byte['က']);
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
/*      */   public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream, byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/* 1790 */     long l = 0L;
/* 1791 */     int i = 0;
/* 1792 */     while (-1 != (i = paramInputStream.read(paramArrayOfByte))) {
/* 1793 */       paramOutputStream.write(paramArrayOfByte, 0, i);
/* 1794 */       l += i;
/*      */     }
/* 1796 */     return l;
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
/*      */   public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream, long paramLong1, long paramLong2)
/*      */     throws IOException
/*      */   {
/* 1820 */     return copyLarge(paramInputStream, paramOutputStream, paramLong1, paramLong2, new byte['က']);
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
/*      */   public static long copyLarge(InputStream paramInputStream, OutputStream paramOutputStream, long paramLong1, long paramLong2, byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/* 1845 */     if (paramLong1 > 0L) {
/* 1846 */       skipFully(paramInputStream, paramLong1);
/*      */     }
/* 1848 */     if (paramLong2 == 0L) {
/* 1849 */       return 0L;
/*      */     }
/* 1851 */     int i = paramArrayOfByte.length;
/* 1852 */     int j = i;
/* 1853 */     if ((paramLong2 > 0L) && (paramLong2 < i)) {
/* 1854 */       j = (int)paramLong2;
/*      */     }
/*      */     
/* 1857 */     long l = 0L;
/* 1858 */     int k; while ((j > 0) && (-1 != (k = paramInputStream.read(paramArrayOfByte, 0, j)))) {
/* 1859 */       paramOutputStream.write(paramArrayOfByte, 0, k);
/* 1860 */       l += k;
/* 1861 */       if (paramLong2 > 0L)
/*      */       {
/* 1863 */         j = (int)Math.min(paramLong2 - l, i);
/*      */       }
/*      */     }
/* 1866 */     return l;
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
/*      */   public static void copy(InputStream paramInputStream, Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1886 */     copy(paramInputStream, paramWriter, Charset.defaultCharset());
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
/*      */   public static void copy(InputStream paramInputStream, Writer paramWriter, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 1906 */     InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream, Charsets.toCharset(paramCharset));
/* 1907 */     copy(localInputStreamReader, paramWriter);
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
/*      */   public static void copy(InputStream paramInputStream, Writer paramWriter, String paramString)
/*      */     throws IOException
/*      */   {
/* 1933 */     copy(paramInputStream, paramWriter, Charsets.toCharset(paramString));
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
/*      */   public static int copy(Reader paramReader, Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1957 */     long l = copyLarge(paramReader, paramWriter);
/* 1958 */     if (l > 2147483647L) {
/* 1959 */       return -1;
/*      */     }
/* 1961 */     return (int)l;
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
/*      */   public static long copyLarge(Reader paramReader, Writer paramWriter)
/*      */     throws IOException
/*      */   {
/* 1980 */     return copyLarge(paramReader, paramWriter, new char['က']);
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
/*      */   public static long copyLarge(Reader paramReader, Writer paramWriter, char[] paramArrayOfChar)
/*      */     throws IOException
/*      */   {
/* 1999 */     long l = 0L;
/* 2000 */     int i = 0;
/* 2001 */     while (-1 != (i = paramReader.read(paramArrayOfChar))) {
/* 2002 */       paramWriter.write(paramArrayOfChar, 0, i);
/* 2003 */       l += i;
/*      */     }
/* 2005 */     return l;
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
/*      */   public static long copyLarge(Reader paramReader, Writer paramWriter, long paramLong1, long paramLong2)
/*      */     throws IOException
/*      */   {
/* 2029 */     return copyLarge(paramReader, paramWriter, paramLong1, paramLong2, new char['က']);
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
/*      */   public static long copyLarge(Reader paramReader, Writer paramWriter, long paramLong1, long paramLong2, char[] paramArrayOfChar)
/*      */     throws IOException
/*      */   {
/* 2053 */     if (paramLong1 > 0L) {
/* 2054 */       skipFully(paramReader, paramLong1);
/*      */     }
/* 2056 */     if (paramLong2 == 0L) {
/* 2057 */       return 0L;
/*      */     }
/* 2059 */     int i = paramArrayOfChar.length;
/* 2060 */     if ((paramLong2 > 0L) && (paramLong2 < paramArrayOfChar.length)) {
/* 2061 */       i = (int)paramLong2;
/*      */     }
/*      */     
/* 2064 */     long l = 0L;
/* 2065 */     int j; while ((i > 0) && (-1 != (j = paramReader.read(paramArrayOfChar, 0, i)))) {
/* 2066 */       paramWriter.write(paramArrayOfChar, 0, j);
/* 2067 */       l += j;
/* 2068 */       if (paramLong2 > 0L)
/*      */       {
/* 2070 */         i = (int)Math.min(paramLong2 - l, paramArrayOfChar.length);
/*      */       }
/*      */     }
/* 2073 */     return l;
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
/*      */   public static void copy(Reader paramReader, OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 2097 */     copy(paramReader, paramOutputStream, Charset.defaultCharset());
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
/*      */   public static void copy(Reader paramReader, OutputStream paramOutputStream, Charset paramCharset)
/*      */     throws IOException
/*      */   {
/* 2124 */     OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(paramOutputStream, Charsets.toCharset(paramCharset));
/* 2125 */     copy(paramReader, localOutputStreamWriter);
/*      */     
/*      */ 
/* 2128 */     localOutputStreamWriter.flush();
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
/*      */   public static void copy(Reader paramReader, OutputStream paramOutputStream, String paramString)
/*      */     throws IOException
/*      */   {
/* 2158 */     copy(paramReader, paramOutputStream, Charsets.toCharset(paramString));
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
/*      */   public static boolean contentEquals(InputStream paramInputStream1, InputStream paramInputStream2)
/*      */     throws IOException
/*      */   {
/* 2179 */     if (!(paramInputStream1 instanceof BufferedInputStream)) {
/* 2180 */       paramInputStream1 = new BufferedInputStream(paramInputStream1);
/*      */     }
/* 2182 */     if (!(paramInputStream2 instanceof BufferedInputStream)) {
/* 2183 */       paramInputStream2 = new BufferedInputStream(paramInputStream2);
/*      */     }
/*      */     
/* 2186 */     int i = paramInputStream1.read();
/* 2187 */     while (-1 != i) {
/* 2188 */       j = paramInputStream2.read();
/* 2189 */       if (i != j) {
/* 2190 */         return false;
/*      */       }
/* 2192 */       i = paramInputStream1.read();
/*      */     }
/*      */     
/* 2195 */     int j = paramInputStream2.read();
/* 2196 */     return j == -1;
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
/*      */   public static boolean contentEquals(Reader paramReader1, Reader paramReader2)
/*      */     throws IOException
/*      */   {
/* 2217 */     paramReader1 = toBufferedReader(paramReader1);
/* 2218 */     paramReader2 = toBufferedReader(paramReader2);
/*      */     
/* 2220 */     int i = paramReader1.read();
/* 2221 */     while (-1 != i) {
/* 2222 */       j = paramReader2.read();
/* 2223 */       if (i != j) {
/* 2224 */         return false;
/*      */       }
/* 2226 */       i = paramReader1.read();
/*      */     }
/*      */     
/* 2229 */     int j = paramReader2.read();
/* 2230 */     return j == -1;
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
/*      */   public static boolean contentEqualsIgnoreEOL(Reader paramReader1, Reader paramReader2)
/*      */     throws IOException
/*      */   {
/* 2249 */     BufferedReader localBufferedReader1 = toBufferedReader(paramReader1);
/* 2250 */     BufferedReader localBufferedReader2 = toBufferedReader(paramReader2);
/*      */     
/* 2252 */     String str1 = localBufferedReader1.readLine();
/* 2253 */     String str2 = localBufferedReader2.readLine();
/* 2254 */     while ((str1 != null) && (str2 != null) && (str1.equals(str2))) {
/* 2255 */       str1 = localBufferedReader1.readLine();
/* 2256 */       str2 = localBufferedReader2.readLine();
/*      */     }
/* 2258 */     return str1 == null ? false : str2 == null ? true : str1.equals(str2);
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
/*      */   public static long skip(InputStream paramInputStream, long paramLong)
/*      */     throws IOException
/*      */   {
/* 2278 */     if (paramLong < 0L) {
/* 2279 */       throw new IllegalArgumentException("Skip count must be non-negative, actual: " + paramLong);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2286 */     if (SKIP_BYTE_BUFFER == null) {
/* 2287 */       SKIP_BYTE_BUFFER = new byte['ࠀ'];
/*      */     }
/* 2289 */     long l1 = paramLong;
/* 2290 */     while (l1 > 0L) {
/* 2291 */       long l2 = paramInputStream.read(SKIP_BYTE_BUFFER, 0, (int)Math.min(l1, 2048L));
/* 2292 */       if (l2 < 0L) {
/*      */         break;
/*      */       }
/* 2295 */       l1 -= l2;
/*      */     }
/* 2297 */     return paramLong - l1;
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
/*      */   public static long skip(Reader paramReader, long paramLong)
/*      */     throws IOException
/*      */   {
/* 2317 */     if (paramLong < 0L) {
/* 2318 */       throw new IllegalArgumentException("Skip count must be non-negative, actual: " + paramLong);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2325 */     if (SKIP_CHAR_BUFFER == null) {
/* 2326 */       SKIP_CHAR_BUFFER = new char['ࠀ'];
/*      */     }
/* 2328 */     long l1 = paramLong;
/* 2329 */     while (l1 > 0L) {
/* 2330 */       long l2 = paramReader.read(SKIP_CHAR_BUFFER, 0, (int)Math.min(l1, 2048L));
/* 2331 */       if (l2 < 0L) {
/*      */         break;
/*      */       }
/* 2334 */       l1 -= l2;
/*      */     }
/* 2336 */     return paramLong - l1;
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
/*      */   public static void skipFully(InputStream paramInputStream, long paramLong)
/*      */     throws IOException
/*      */   {
/* 2355 */     if (paramLong < 0L) {
/* 2356 */       throw new IllegalArgumentException("Bytes to skip must not be negative: " + paramLong);
/*      */     }
/* 2358 */     long l = skip(paramInputStream, paramLong);
/* 2359 */     if (l != paramLong) {
/* 2360 */       throw new EOFException("Bytes to skip: " + paramLong + " actual: " + l);
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
/*      */   public static void skipFully(Reader paramReader, long paramLong)
/*      */     throws IOException
/*      */   {
/* 2380 */     long l = skip(paramReader, paramLong);
/* 2381 */     if (l != paramLong) {
/* 2382 */       throw new EOFException("Chars to skip: " + paramLong + " actual: " + l);
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
/*      */   public static int read(Reader paramReader, char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 2402 */     if (paramInt2 < 0) {
/* 2403 */       throw new IllegalArgumentException("Length must not be negative: " + paramInt2);
/*      */     }
/* 2405 */     int i = paramInt2;
/* 2406 */     while (i > 0) {
/* 2407 */       int j = paramInt2 - i;
/* 2408 */       int k = paramReader.read(paramArrayOfChar, paramInt1 + j, i);
/* 2409 */       if (-1 == k) {
/*      */         break;
/*      */       }
/* 2412 */       i -= k;
/*      */     }
/* 2414 */     return paramInt2 - i;
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
/*      */   public static int read(Reader paramReader, char[] paramArrayOfChar)
/*      */     throws IOException
/*      */   {
/* 2430 */     return read(paramReader, paramArrayOfChar, 0, paramArrayOfChar.length);
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
/*      */   public static int read(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 2448 */     if (paramInt2 < 0) {
/* 2449 */       throw new IllegalArgumentException("Length must not be negative: " + paramInt2);
/*      */     }
/* 2451 */     int i = paramInt2;
/* 2452 */     while (i > 0) {
/* 2453 */       int j = paramInt2 - i;
/* 2454 */       int k = paramInputStream.read(paramArrayOfByte, paramInt1 + j, i);
/* 2455 */       if (-1 == k) {
/*      */         break;
/*      */       }
/* 2458 */       i -= k;
/*      */     }
/* 2460 */     return paramInt2 - i;
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
/*      */   public static int read(InputStream paramInputStream, byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/* 2476 */     return read(paramInputStream, paramArrayOfByte, 0, paramArrayOfByte.length);
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
/*      */   public static void readFully(Reader paramReader, char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 2496 */     int i = read(paramReader, paramArrayOfChar, paramInt1, paramInt2);
/* 2497 */     if (i != paramInt2) {
/* 2498 */       throw new EOFException("Length to read: " + paramInt2 + " actual: " + i);
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
/*      */   public static void readFully(Reader paramReader, char[] paramArrayOfChar)
/*      */     throws IOException
/*      */   {
/* 2517 */     readFully(paramReader, paramArrayOfChar, 0, paramArrayOfChar.length);
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
/*      */   public static void readFully(InputStream paramInputStream, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 2537 */     int i = read(paramInputStream, paramArrayOfByte, paramInt1, paramInt2);
/* 2538 */     if (i != paramInt2) {
/* 2539 */       throw new EOFException("Length to read: " + paramInt2 + " actual: " + i);
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
/*      */   public static void readFully(InputStream paramInputStream, byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/* 2558 */     readFully(paramInputStream, paramArrayOfByte, 0, paramArrayOfByte.length);
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\IOUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */