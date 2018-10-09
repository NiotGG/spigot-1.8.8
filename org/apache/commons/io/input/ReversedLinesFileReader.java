/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import org.apache.commons.io.Charsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReversedLinesFileReader
/*     */   implements Closeable
/*     */ {
/*     */   private final int blockSize;
/*     */   private final Charset encoding;
/*     */   private final RandomAccessFile randomAccessFile;
/*     */   private final long totalByteLength;
/*     */   private final long totalBlockCount;
/*     */   private final byte[][] newLineSequences;
/*     */   private final int avoidNewlineSplitBufferSize;
/*     */   private final int byteDecrement;
/*     */   private FilePart currentFilePart;
/*  52 */   private boolean trailingNewlineOfFileSkipped = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReversedLinesFileReader(File paramFile)
/*     */     throws IOException
/*     */   {
/*  63 */     this(paramFile, 4096, Charset.defaultCharset().toString());
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
/*     */   public ReversedLinesFileReader(File paramFile, int paramInt, Charset paramCharset)
/*     */     throws IOException
/*     */   {
/*  80 */     this.blockSize = paramInt;
/*  81 */     this.encoding = paramCharset;
/*     */     
/*  83 */     this.randomAccessFile = new RandomAccessFile(paramFile, "r");
/*  84 */     this.totalByteLength = this.randomAccessFile.length();
/*  85 */     int i = (int)(this.totalByteLength % paramInt);
/*  86 */     if (i > 0) {
/*  87 */       this.totalBlockCount = (this.totalByteLength / paramInt + 1L);
/*     */     } else {
/*  89 */       this.totalBlockCount = (this.totalByteLength / paramInt);
/*  90 */       if (this.totalByteLength > 0L) {
/*  91 */         i = paramInt;
/*     */       }
/*     */     }
/*  94 */     this.currentFilePart = new FilePart(this.totalBlockCount, i, null, null);
/*     */     
/*     */ 
/*  97 */     Charset localCharset = Charsets.toCharset(paramCharset);
/*  98 */     CharsetEncoder localCharsetEncoder = localCharset.newEncoder();
/*  99 */     float f = localCharsetEncoder.maxBytesPerChar();
/* 100 */     if (f == 1.0F)
/*     */     {
/* 102 */       this.byteDecrement = 1;
/* 103 */     } else if (localCharset == Charset.forName("UTF-8"))
/*     */     {
/*     */ 
/* 106 */       this.byteDecrement = 1;
/* 107 */     } else if (localCharset == Charset.forName("Shift_JIS"))
/*     */     {
/*     */ 
/* 110 */       this.byteDecrement = 1;
/* 111 */     } else if ((localCharset == Charset.forName("UTF-16BE")) || (localCharset == Charset.forName("UTF-16LE")))
/*     */     {
/*     */ 
/* 114 */       this.byteDecrement = 2;
/* 115 */     } else { if (localCharset == Charset.forName("UTF-16")) {
/* 116 */         throw new UnsupportedEncodingException("For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)");
/*     */       }
/*     */       
/* 119 */       throw new UnsupportedEncodingException("Encoding " + paramCharset + " is not supported yet (feel free to submit a patch)");
/*     */     }
/*     */     
/*     */ 
/* 123 */     this.newLineSequences = new byte[][] { "\r\n".getBytes(paramCharset), "\n".getBytes(paramCharset), "\r".getBytes(paramCharset) };
/*     */     
/* 125 */     this.avoidNewlineSplitBufferSize = this.newLineSequences[0].length;
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
/*     */   public ReversedLinesFileReader(File paramFile, int paramInt, String paramString)
/*     */     throws IOException
/*     */   {
/* 144 */     this(paramFile, paramInt, Charsets.toCharset(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String readLine()
/*     */     throws IOException
/*     */   {
/* 155 */     String str = this.currentFilePart.readLine();
/* 156 */     while (str == null) {
/* 157 */       this.currentFilePart = this.currentFilePart.rollOver();
/* 158 */       if (this.currentFilePart == null) break;
/* 159 */       str = this.currentFilePart.readLine();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 167 */     if (("".equals(str)) && (!this.trailingNewlineOfFileSkipped)) {
/* 168 */       this.trailingNewlineOfFileSkipped = true;
/* 169 */       str = readLine();
/*     */     }
/*     */     
/* 172 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 181 */     this.randomAccessFile.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class FilePart
/*     */   {
/*     */     private final long no;
/*     */     
/*     */ 
/*     */     private final byte[] data;
/*     */     
/*     */     private byte[] leftOver;
/*     */     
/*     */     private int currentLastBytePos;
/*     */     
/*     */ 
/*     */     private FilePart(long paramLong, int paramInt, byte[] paramArrayOfByte)
/*     */       throws IOException
/*     */     {
/* 201 */       this.no = paramLong;
/* 202 */       int i = paramInt + (paramArrayOfByte != null ? paramArrayOfByte.length : 0);
/* 203 */       this.data = new byte[i];
/* 204 */       long l = (paramLong - 1L) * ReversedLinesFileReader.this.blockSize;
/*     */       
/*     */ 
/* 207 */       if (paramLong > 0L) {
/* 208 */         ReversedLinesFileReader.this.randomAccessFile.seek(l);
/* 209 */         int j = ReversedLinesFileReader.this.randomAccessFile.read(this.data, 0, paramInt);
/* 210 */         if (j != paramInt) {
/* 211 */           throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
/*     */         }
/*     */       }
/*     */       
/* 215 */       if (paramArrayOfByte != null) {
/* 216 */         System.arraycopy(paramArrayOfByte, 0, this.data, paramInt, paramArrayOfByte.length);
/*     */       }
/* 218 */       this.currentLastBytePos = (this.data.length - 1);
/* 219 */       this.leftOver = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private FilePart rollOver()
/*     */       throws IOException
/*     */     {
/* 230 */       if (this.currentLastBytePos > -1) {
/* 231 */         throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
/*     */       }
/*     */       
/*     */ 
/* 235 */       if (this.no > 1L) {
/* 236 */         return new FilePart(ReversedLinesFileReader.this, this.no - 1L, ReversedLinesFileReader.this.blockSize, this.leftOver);
/*     */       }
/*     */       
/* 239 */       if (this.leftOver != null) {
/* 240 */         throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=" + new String(this.leftOver, ReversedLinesFileReader.this.encoding));
/*     */       }
/*     */       
/* 243 */       return null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private String readLine()
/*     */       throws IOException
/*     */     {
/* 255 */       String str = null;
/*     */       
/*     */ 
/* 258 */       int i = this.no == 1L ? 1 : 0;
/*     */       
/* 260 */       int j = this.currentLastBytePos;
/* 261 */       while (j > -1)
/*     */       {
/* 263 */         if ((i == 0) && (j < ReversedLinesFileReader.this.avoidNewlineSplitBufferSize))
/*     */         {
/*     */ 
/* 266 */           createLeftOver();
/*     */         }
/*     */         else
/*     */         {
/*     */           int k;
/* 271 */           if ((k = getNewLineMatchByteCount(this.data, j)) > 0) {
/* 272 */             int m = j + 1;
/* 273 */             int n = this.currentLastBytePos - m + 1;
/*     */             
/* 275 */             if (n < 0) {
/* 276 */               throw new IllegalStateException("Unexpected negative line length=" + n);
/*     */             }
/* 278 */             byte[] arrayOfByte = new byte[n];
/* 279 */             System.arraycopy(this.data, m, arrayOfByte, 0, n);
/*     */             
/* 281 */             str = new String(arrayOfByte, ReversedLinesFileReader.this.encoding);
/*     */             
/* 283 */             this.currentLastBytePos = (j - k);
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 288 */             j -= ReversedLinesFileReader.this.byteDecrement;
/*     */             
/*     */ 
/* 291 */             if (j < 0) {
/* 292 */               createLeftOver();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 298 */       if ((i != 0) && (this.leftOver != null))
/*     */       {
/* 300 */         str = new String(this.leftOver, ReversedLinesFileReader.this.encoding);
/* 301 */         this.leftOver = null;
/*     */       }
/*     */       
/* 304 */       return str;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private void createLeftOver()
/*     */     {
/* 311 */       int i = this.currentLastBytePos + 1;
/* 312 */       if (i > 0)
/*     */       {
/* 314 */         this.leftOver = new byte[i];
/* 315 */         System.arraycopy(this.data, 0, this.leftOver, 0, i);
/*     */       } else {
/* 317 */         this.leftOver = null;
/*     */       }
/* 319 */       this.currentLastBytePos = -1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private int getNewLineMatchByteCount(byte[] paramArrayOfByte, int paramInt)
/*     */     {
/* 330 */       for (byte[] arrayOfByte1 : ReversedLinesFileReader.this.newLineSequences) {
/* 331 */         int k = 1;
/* 332 */         for (int m = arrayOfByte1.length - 1; m >= 0; m--) {
/* 333 */           int n = paramInt + m - (arrayOfByte1.length - 1);
/* 334 */           k &= ((n >= 0) && (paramArrayOfByte[n] == arrayOfByte1[m]) ? 1 : 0);
/*     */         }
/* 336 */         if (k != 0) {
/* 337 */           return arrayOfByte1.length;
/*     */         }
/*     */       }
/* 340 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\ReversedLinesFileReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */