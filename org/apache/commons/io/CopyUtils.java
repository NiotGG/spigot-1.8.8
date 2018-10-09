/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ public class CopyUtils
/*     */ {
/*     */   private static final int DEFAULT_BUFFER_SIZE = 4096;
/*     */   
/*     */   public static void copy(byte[] paramArrayOfByte, OutputStream paramOutputStream)
/*     */     throws IOException
/*     */   {
/* 136 */     paramOutputStream.write(paramArrayOfByte);
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
/*     */   public static void copy(byte[] paramArrayOfByte, Writer paramWriter)
/*     */     throws IOException
/*     */   {
/* 153 */     ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
/* 154 */     copy(localByteArrayInputStream, paramWriter);
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
/*     */   public static void copy(byte[] paramArrayOfByte, Writer paramWriter, String paramString)
/*     */     throws IOException
/*     */   {
/* 173 */     ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
/* 174 */     copy(localByteArrayInputStream, paramWriter, paramString);
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
/*     */   public static int copy(InputStream paramInputStream, OutputStream paramOutputStream)
/*     */     throws IOException
/*     */   {
/* 194 */     byte[] arrayOfByte = new byte['က'];
/* 195 */     int i = 0;
/* 196 */     int j = 0;
/* 197 */     while (-1 != (j = paramInputStream.read(arrayOfByte))) {
/* 198 */       paramOutputStream.write(arrayOfByte, 0, j);
/* 199 */       i += j;
/*     */     }
/* 201 */     return i;
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
/*     */   public static int copy(Reader paramReader, Writer paramWriter)
/*     */     throws IOException
/*     */   {
/* 219 */     char[] arrayOfChar = new char['က'];
/* 220 */     int i = 0;
/* 221 */     int j = 0;
/* 222 */     while (-1 != (j = paramReader.read(arrayOfChar))) {
/* 223 */       paramWriter.write(arrayOfChar, 0, j);
/* 224 */       i += j;
/*     */     }
/* 226 */     return i;
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
/*     */   public static void copy(InputStream paramInputStream, Writer paramWriter)
/*     */     throws IOException
/*     */   {
/* 245 */     InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream);
/* 246 */     copy(localInputStreamReader, paramWriter);
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
/*     */   public static void copy(InputStream paramInputStream, Writer paramWriter, String paramString)
/*     */     throws IOException
/*     */   {
/* 264 */     InputStreamReader localInputStreamReader = new InputStreamReader(paramInputStream, paramString);
/* 265 */     copy(localInputStreamReader, paramWriter);
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
/*     */   public static void copy(Reader paramReader, OutputStream paramOutputStream)
/*     */     throws IOException
/*     */   {
/* 284 */     OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(paramOutputStream);
/* 285 */     copy(paramReader, localOutputStreamWriter);
/*     */     
/*     */ 
/* 288 */     localOutputStreamWriter.flush();
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
/*     */   public static void copy(String paramString, OutputStream paramOutputStream)
/*     */     throws IOException
/*     */   {
/* 307 */     StringReader localStringReader = new StringReader(paramString);
/* 308 */     OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(paramOutputStream);
/* 309 */     copy(localStringReader, localOutputStreamWriter);
/*     */     
/*     */ 
/* 312 */     localOutputStreamWriter.flush();
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
/*     */   public static void copy(String paramString, Writer paramWriter)
/*     */     throws IOException
/*     */   {
/* 327 */     paramWriter.write(paramString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\CopyUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */