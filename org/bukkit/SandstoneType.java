/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum SandstoneType
/*    */ {
/* 11 */   CRACKED(0), 
/* 12 */   GLYPHED(1), 
/* 13 */   SMOOTH(2);
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, SandstoneType> BY_DATA;
/*    */   
/*    */   private SandstoneType(int data) {
/* 19 */     this.data = ((byte)data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public byte getData()
/*    */   {
/* 30 */     return this.data;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static SandstoneType getByData(byte data)
/*    */   {
/* 43 */     return (SandstoneType)BY_DATA.get(Byte.valueOf(data));
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 16 */     BY_DATA = Maps.newHashMap();
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
/*    */ 
/*    */ 
/*    */     SandstoneType[] arrayOfSandstoneType;
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
/*    */ 
/* 47 */     int i = (arrayOfSandstoneType = values()).length; for (int j = 0; j < i; j++) { SandstoneType type = arrayOfSandstoneType[j];
/* 48 */       BY_DATA.put(Byte.valueOf(type.data), type);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\SandstoneType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */