/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteOrderMark
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  36 */   public static final ByteOrderMark UTF_8 = new ByteOrderMark("UTF-8", new int[] { 239, 187, 191 });
/*     */   
/*     */ 
/*  39 */   public static final ByteOrderMark UTF_16BE = new ByteOrderMark("UTF-16BE", new int[] { 254, 255 });
/*     */   
/*     */ 
/*  42 */   public static final ByteOrderMark UTF_16LE = new ByteOrderMark("UTF-16LE", new int[] { 255, 254 });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  48 */   public static final ByteOrderMark UTF_32BE = new ByteOrderMark("UTF-32BE", new int[] { 0, 0, 254, 255 });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  54 */   public static final ByteOrderMark UTF_32LE = new ByteOrderMark("UTF-32LE", new int[] { 255, 254, 0, 0 });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String charsetName;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int[] bytes;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteOrderMark(String paramString, int... paramVarArgs)
/*     */   {
/*  70 */     if ((paramString == null) || (paramString.length() == 0)) {
/*  71 */       throw new IllegalArgumentException("No charsetName specified");
/*     */     }
/*  73 */     if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
/*  74 */       throw new IllegalArgumentException("No bytes specified");
/*     */     }
/*  76 */     this.charsetName = paramString;
/*  77 */     this.bytes = new int[paramVarArgs.length];
/*  78 */     System.arraycopy(paramVarArgs, 0, this.bytes, 0, paramVarArgs.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCharsetName()
/*     */   {
/*  87 */     return this.charsetName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int length()
/*     */   {
/*  96 */     return this.bytes.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int get(int paramInt)
/*     */   {
/* 106 */     return this.bytes[paramInt];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getBytes()
/*     */   {
/* 115 */     byte[] arrayOfByte = new byte[this.bytes.length];
/* 116 */     for (int i = 0; i < this.bytes.length; i++) {
/* 117 */       arrayOfByte[i] = ((byte)this.bytes[i]);
/*     */     }
/* 119 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 131 */     if (!(paramObject instanceof ByteOrderMark)) {
/* 132 */       return false;
/*     */     }
/* 134 */     ByteOrderMark localByteOrderMark = (ByteOrderMark)paramObject;
/* 135 */     if (this.bytes.length != localByteOrderMark.length()) {
/* 136 */       return false;
/*     */     }
/* 138 */     for (int i = 0; i < this.bytes.length; i++) {
/* 139 */       if (this.bytes[i] != localByteOrderMark.get(i)) {
/* 140 */         return false;
/*     */       }
/*     */     }
/* 143 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 154 */     int i = getClass().hashCode();
/* 155 */     for (int m : this.bytes) {
/* 156 */       i += m;
/*     */     }
/* 158 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 168 */     StringBuilder localStringBuilder = new StringBuilder();
/* 169 */     localStringBuilder.append(getClass().getSimpleName());
/* 170 */     localStringBuilder.append('[');
/* 171 */     localStringBuilder.append(this.charsetName);
/* 172 */     localStringBuilder.append(": ");
/* 173 */     for (int i = 0; i < this.bytes.length; i++) {
/* 174 */       if (i > 0) {
/* 175 */         localStringBuilder.append(",");
/*     */       }
/* 177 */       localStringBuilder.append("0x");
/* 178 */       localStringBuilder.append(Integer.toHexString(0xFF & this.bytes[i]).toUpperCase());
/*     */     }
/* 180 */     localStringBuilder.append(']');
/* 181 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\ByteOrderMark.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */