/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BitField
/*     */ {
/*     */   private final int _mask;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int _shift_count;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BitField(int paramInt)
/*     */   {
/*  38 */     this._mask = paramInt;
/*  39 */     int i = 0;
/*  40 */     int j = paramInt;
/*     */     
/*  42 */     if (j != 0) {
/*  43 */       while ((j & 0x1) == 0) {
/*  44 */         i++;
/*  45 */         j >>= 1;
/*     */       }
/*     */     }
/*  48 */     this._shift_count = i;
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
/*     */   public int getValue(int paramInt)
/*     */   {
/*  66 */     return getRawValue(paramInt) >> this._shift_count;
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
/*     */   public short getShortValue(short paramShort)
/*     */   {
/*  84 */     return (short)getValue(paramShort);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRawValue(int paramInt)
/*     */   {
/*  95 */     return paramInt & this._mask;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short getShortRawValue(short paramShort)
/*     */   {
/* 106 */     return (short)getRawValue(paramShort);
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
/*     */   public boolean isSet(int paramInt)
/*     */   {
/* 123 */     return (paramInt & this._mask) != 0;
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
/*     */   public boolean isAllSet(int paramInt)
/*     */   {
/* 139 */     return (paramInt & this._mask) == this._mask;
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
/*     */   public int setValue(int paramInt1, int paramInt2)
/*     */   {
/* 153 */     return paramInt1 & (this._mask ^ 0xFFFFFFFF) | paramInt2 << this._shift_count & this._mask;
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
/*     */   public short setShortValue(short paramShort1, short paramShort2)
/*     */   {
/* 167 */     return (short)setValue(paramShort1, paramShort2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int clear(int paramInt)
/*     */   {
/* 179 */     return paramInt & (this._mask ^ 0xFFFFFFFF);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short clearShort(short paramShort)
/*     */   {
/* 191 */     return (short)clear(paramShort);
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
/*     */   public byte clearByte(byte paramByte)
/*     */   {
/* 204 */     return (byte)clear(paramByte);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int set(int paramInt)
/*     */   {
/* 216 */     return paramInt | this._mask;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public short setShort(short paramShort)
/*     */   {
/* 228 */     return (short)set(paramShort);
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
/*     */   public byte setByte(byte paramByte)
/*     */   {
/* 241 */     return (byte)set(paramByte);
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
/*     */   public int setBoolean(int paramInt, boolean paramBoolean)
/*     */   {
/* 254 */     return paramBoolean ? set(paramInt) : clear(paramInt);
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
/*     */   public short setShortBoolean(short paramShort, boolean paramBoolean)
/*     */   {
/* 267 */     return paramBoolean ? setShort(paramShort) : clearShort(paramShort);
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
/*     */   public byte setByteBoolean(byte paramByte, boolean paramBoolean)
/*     */   {
/* 280 */     return paramBoolean ? setByte(paramByte) : clearByte(paramByte);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\BitField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */