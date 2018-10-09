/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HexDump
/*     */ {
/*     */   public static void dump(byte[] paramArrayOfByte, long paramLong, OutputStream paramOutputStream, int paramInt)
/*     */     throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException
/*     */   {
/*  76 */     if ((paramInt < 0) || (paramInt >= paramArrayOfByte.length)) {
/*  77 */       throw new ArrayIndexOutOfBoundsException("illegal index: " + paramInt + " into array of length " + paramArrayOfByte.length);
/*     */     }
/*     */     
/*     */ 
/*  81 */     if (paramOutputStream == null) {
/*  82 */       throw new IllegalArgumentException("cannot write to nullstream");
/*     */     }
/*  84 */     long l = paramLong + paramInt;
/*  85 */     StringBuilder localStringBuilder = new StringBuilder(74);
/*     */     
/*  87 */     for (int i = paramInt; i < paramArrayOfByte.length; i += 16) {
/*  88 */       int j = paramArrayOfByte.length - i;
/*     */       
/*  90 */       if (j > 16) {
/*  91 */         j = 16;
/*     */       }
/*  93 */       dump(localStringBuilder, l).append(' ');
/*  94 */       for (int k = 0; k < 16; k++) {
/*  95 */         if (k < j) {
/*  96 */           dump(localStringBuilder, paramArrayOfByte[(k + i)]);
/*     */         } else {
/*  98 */           localStringBuilder.append("  ");
/*     */         }
/* 100 */         localStringBuilder.append(' ');
/*     */       }
/* 102 */       for (k = 0; k < j; k++) {
/* 103 */         if ((paramArrayOfByte[(k + i)] >= 32) && (paramArrayOfByte[(k + i)] < Byte.MAX_VALUE)) {
/* 104 */           localStringBuilder.append((char)paramArrayOfByte[(k + i)]);
/*     */         } else {
/* 106 */           localStringBuilder.append('.');
/*     */         }
/*     */       }
/* 109 */       localStringBuilder.append(EOL);
/* 110 */       paramOutputStream.write(localStringBuilder.toString().getBytes());
/* 111 */       paramOutputStream.flush();
/* 112 */       localStringBuilder.setLength(0);
/* 113 */       l += j;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 120 */   public static final String EOL = System.getProperty("line.separator");
/*     */   
/* 122 */   private static final char[] _hexcodes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 127 */   private static final int[] _shifts = { 28, 24, 20, 16, 12, 8, 4, 0 };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static StringBuilder dump(StringBuilder paramStringBuilder, long paramLong)
/*     */   {
/* 140 */     for (int i = 0; i < 8; i++) {
/* 141 */       paramStringBuilder.append(_hexcodes[((int)(paramLong >> _shifts[i]) & 0xF)]);
/*     */     }
/*     */     
/* 144 */     return paramStringBuilder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static StringBuilder dump(StringBuilder paramStringBuilder, byte paramByte)
/*     */   {
/* 155 */     for (int i = 0; i < 2; i++) {
/* 156 */       paramStringBuilder.append(_hexcodes[(paramByte >> _shifts[(i + 6)] & 0xF)]);
/*     */     }
/* 158 */     return paramStringBuilder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\HexDump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */