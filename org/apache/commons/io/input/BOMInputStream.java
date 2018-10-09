/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.ByteOrderMark;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BOMInputStream
/*     */   extends ProxyInputStream
/*     */ {
/*     */   private final boolean include;
/*     */   private final List<ByteOrderMark> boms;
/*     */   private ByteOrderMark byteOrderMark;
/*     */   private int[] firstBytes;
/*     */   private int fbLength;
/*     */   private int fbIndex;
/*     */   private int markFbIndex;
/*     */   private boolean markedAtStart;
/*     */   
/*     */   public BOMInputStream(InputStream paramInputStream)
/*     */   {
/* 107 */     this(paramInputStream, false, new ByteOrderMark[] { ByteOrderMark.UTF_8 });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BOMInputStream(InputStream paramInputStream, boolean paramBoolean)
/*     */   {
/* 119 */     this(paramInputStream, paramBoolean, new ByteOrderMark[] { ByteOrderMark.UTF_8 });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BOMInputStream(InputStream paramInputStream, ByteOrderMark... paramVarArgs)
/*     */   {
/* 131 */     this(paramInputStream, false, paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 137 */   private static final Comparator<ByteOrderMark> ByteOrderMarkLengthComparator = new Comparator()
/*     */   {
/*     */     public int compare(ByteOrderMark paramAnonymousByteOrderMark1, ByteOrderMark paramAnonymousByteOrderMark2) {
/* 140 */       int i = paramAnonymousByteOrderMark1.length();
/* 141 */       int j = paramAnonymousByteOrderMark2.length();
/* 142 */       if (i > j) {
/* 143 */         return -1;
/*     */       }
/* 145 */       if (j > i) {
/* 146 */         return 1;
/*     */       }
/* 148 */       return 0;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BOMInputStream(InputStream paramInputStream, boolean paramBoolean, ByteOrderMark... paramVarArgs)
/*     */   {
/* 163 */     super(paramInputStream);
/* 164 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/* 165 */       throw new IllegalArgumentException("No BOMs specified");
/*     */     }
/* 167 */     this.include = paramBoolean;
/*     */     
/* 169 */     Arrays.sort(paramVarArgs, ByteOrderMarkLengthComparator);
/* 170 */     this.boms = Arrays.asList(paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasBOM()
/*     */     throws IOException
/*     */   {
/* 182 */     return getBOM() != null;
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
/*     */   public boolean hasBOM(ByteOrderMark paramByteOrderMark)
/*     */     throws IOException
/*     */   {
/* 197 */     if (!this.boms.contains(paramByteOrderMark)) {
/* 198 */       throw new IllegalArgumentException("Stream not configure to detect " + paramByteOrderMark);
/*     */     }
/* 200 */     return (this.byteOrderMark != null) && (getBOM().equals(paramByteOrderMark));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteOrderMark getBOM()
/*     */     throws IOException
/*     */   {
/* 211 */     if (this.firstBytes == null) {
/* 212 */       this.fbLength = 0;
/*     */       
/* 214 */       int i = ((ByteOrderMark)this.boms.get(0)).length();
/* 215 */       this.firstBytes = new int[i];
/*     */       
/* 217 */       for (int j = 0; j < this.firstBytes.length; j++) {
/* 218 */         this.firstBytes[j] = this.in.read();
/* 219 */         this.fbLength += 1;
/* 220 */         if (this.firstBytes[j] < 0) {
/*     */           break;
/*     */         }
/*     */       }
/*     */       
/* 225 */       this.byteOrderMark = find();
/* 226 */       if ((this.byteOrderMark != null) && 
/* 227 */         (!this.include)) {
/* 228 */         if (this.byteOrderMark.length() < this.firstBytes.length) {
/* 229 */           this.fbIndex = this.byteOrderMark.length();
/*     */         } else {
/* 231 */           this.fbLength = 0;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 236 */     return this.byteOrderMark;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getBOMCharsetName()
/*     */     throws IOException
/*     */   {
/* 248 */     getBOM();
/* 249 */     return this.byteOrderMark == null ? null : this.byteOrderMark.getCharsetName();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int readFirstBytes()
/*     */     throws IOException
/*     */   {
/* 262 */     getBOM();
/* 263 */     return this.fbIndex < this.fbLength ? this.firstBytes[(this.fbIndex++)] : -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private ByteOrderMark find()
/*     */   {
/* 272 */     for (ByteOrderMark localByteOrderMark : this.boms) {
/* 273 */       if (matches(localByteOrderMark)) {
/* 274 */         return localByteOrderMark;
/*     */       }
/*     */     }
/* 277 */     return null;
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
/*     */   private boolean matches(ByteOrderMark paramByteOrderMark)
/*     */   {
/* 292 */     for (int i = 0; i < paramByteOrderMark.length(); i++) {
/* 293 */       if (paramByteOrderMark.get(i) != this.firstBytes[i]) {
/* 294 */         return false;
/*     */       }
/*     */     }
/* 297 */     return true;
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
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 313 */     int i = readFirstBytes();
/* 314 */     return i >= 0 ? i : this.in.read();
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
/*     */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 332 */     int i = 0;
/* 333 */     int j = 0;
/* 334 */     while ((paramInt2 > 0) && (j >= 0)) {
/* 335 */       j = readFirstBytes();
/* 336 */       if (j >= 0) {
/* 337 */         paramArrayOfByte[(paramInt1++)] = ((byte)(j & 0xFF));
/* 338 */         paramInt2--;
/* 339 */         i++;
/*     */       }
/*     */     }
/* 342 */     int k = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
/* 343 */     return k < 0 ? -1 : i > 0 ? i : i + k;
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
/*     */   public int read(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/* 357 */     return read(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void mark(int paramInt)
/*     */   {
/* 368 */     this.markFbIndex = this.fbIndex;
/* 369 */     this.markedAtStart = (this.firstBytes == null);
/* 370 */     this.in.mark(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void reset()
/*     */     throws IOException
/*     */   {
/* 381 */     this.fbIndex = this.markFbIndex;
/* 382 */     if (this.markedAtStart) {
/* 383 */       this.firstBytes = null;
/*     */     }
/*     */     
/* 386 */     this.in.reset();
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
/*     */   public long skip(long paramLong)
/*     */     throws IOException
/*     */   {
/* 400 */     while ((paramLong > 0L) && (readFirstBytes() >= 0)) {
/* 401 */       paramLong -= 1L;
/*     */     }
/* 403 */     return this.in.skip(paramLong);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\BOMInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */