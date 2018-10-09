/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public enum Instrument
/*    */ {
/*  9 */   PIANO(
/*    */   
/*    */ 
/* 12 */     0), 
/* 13 */   BASS_DRUM(
/*    */   
/*    */ 
/*    */ 
/* 17 */     1), 
/* 18 */   SNARE_DRUM(
/*    */   
/*    */ 
/*    */ 
/* 22 */     2), 
/* 23 */   STICKS(
/*    */   
/*    */ 
/*    */ 
/* 27 */     3), 
/* 28 */   BASS_GUITAR(
/*    */   
/*    */ 
/*    */ 
/* 32 */     4);
/*    */   
/*    */   private final byte type;
/*    */   private static final Map<Byte, Instrument> BY_DATA;
/*    */   
/*    */   private Instrument(int type) {
/* 38 */     this.type = ((byte)type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public byte getType()
/*    */   {
/* 47 */     return this.type;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static Instrument getByType(byte type)
/*    */   {
/* 59 */     return (Instrument)BY_DATA.get(Byte.valueOf(type));
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 35 */     BY_DATA = Maps.newHashMap();
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     Instrument[] arrayOfInstrument;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 63 */     int i = (arrayOfInstrument = values()).length; for (int j = 0; j < i; j++) { Instrument instrument = arrayOfInstrument[j];
/* 64 */       BY_DATA.put(Byte.valueOf(instrument.getType()), instrument);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Instrument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */