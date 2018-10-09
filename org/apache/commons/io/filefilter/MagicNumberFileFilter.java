/*     */ package org.apache.commons.io.filefilter;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MagicNumberFileFilter
/*     */   extends AbstractFileFilter
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -547733176983104172L;
/*     */   private final byte[] magicNumbers;
/*     */   private final long byteOffset;
/*     */   
/*     */   public MagicNumberFileFilter(byte[] paramArrayOfByte)
/*     */   {
/* 112 */     this(paramArrayOfByte, 0L);
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
/*     */   public MagicNumberFileFilter(String paramString)
/*     */   {
/* 137 */     this(paramString, 0L);
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
/*     */   public MagicNumberFileFilter(String paramString, long paramLong)
/*     */   {
/* 161 */     if (paramString == null) {
/* 162 */       throw new IllegalArgumentException("The magic number cannot be null");
/*     */     }
/* 164 */     if (paramString.length() == 0) {
/* 165 */       throw new IllegalArgumentException("The magic number must contain at least one byte");
/*     */     }
/* 167 */     if (paramLong < 0L) {
/* 168 */       throw new IllegalArgumentException("The offset cannot be negative");
/*     */     }
/*     */     
/* 171 */     this.magicNumbers = paramString.getBytes();
/* 172 */     this.byteOffset = paramLong;
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
/*     */   public MagicNumberFileFilter(byte[] paramArrayOfByte, long paramLong)
/*     */   {
/* 206 */     if (paramArrayOfByte == null) {
/* 207 */       throw new IllegalArgumentException("The magic number cannot be null");
/*     */     }
/* 209 */     if (paramArrayOfByte.length == 0) {
/* 210 */       throw new IllegalArgumentException("The magic number must contain at least one byte");
/*     */     }
/* 212 */     if (paramLong < 0L) {
/* 213 */       throw new IllegalArgumentException("The offset cannot be negative");
/*     */     }
/*     */     
/* 216 */     this.magicNumbers = new byte[paramArrayOfByte.length];
/* 217 */     System.arraycopy(paramArrayOfByte, 0, this.magicNumbers, 0, paramArrayOfByte.length);
/* 218 */     this.byteOffset = paramLong;
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
/*     */   public boolean accept(File paramFile)
/*     */   {
/* 239 */     if ((paramFile != null) && (paramFile.isFile()) && (paramFile.canRead())) {
/* 240 */       RandomAccessFile localRandomAccessFile = null;
/*     */       try {
/* 242 */         byte[] arrayOfByte = new byte[this.magicNumbers.length];
/* 243 */         localRandomAccessFile = new RandomAccessFile(paramFile, "r");
/* 244 */         localRandomAccessFile.seek(this.byteOffset);
/* 245 */         int i = localRandomAccessFile.read(arrayOfByte);
/* 246 */         boolean bool; if (i != this.magicNumbers.length) {
/* 247 */           return false;
/*     */         }
/* 249 */         return Arrays.equals(this.magicNumbers, arrayOfByte);
/*     */       }
/*     */       catch (IOException localIOException) {}finally
/*     */       {
/* 253 */         IOUtils.closeQuietly(localRandomAccessFile);
/*     */       }
/*     */     }
/*     */     
/* 257 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 268 */     StringBuilder localStringBuilder = new StringBuilder(super.toString());
/* 269 */     localStringBuilder.append("(");
/* 270 */     localStringBuilder.append(new String(this.magicNumbers));
/* 271 */     localStringBuilder.append(",");
/* 272 */     localStringBuilder.append(this.byteOffset);
/* 273 */     localStringBuilder.append(")");
/* 274 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\filefilter\MagicNumberFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */