/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum CoalType
/*    */ {
/* 11 */   COAL(0), 
/* 12 */   CHARCOAL(1);
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, CoalType> BY_DATA;
/*    */   
/*    */   private CoalType(int data) {
/* 18 */     this.data = ((byte)data);
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
/* 29 */     return this.data;
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
/*    */   public static CoalType getByData(byte data)
/*    */   {
/* 42 */     return (CoalType)BY_DATA.get(Byte.valueOf(data));
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 15 */     BY_DATA = Maps.newHashMap();
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
/*    */     CoalType[] arrayOfCoalType;
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
/* 46 */     int i = (arrayOfCoalType = values()).length; for (int j = 0; j < i; j++) { CoalType type = arrayOfCoalType[j];
/* 47 */       BY_DATA.put(Byte.valueOf(type.data), type);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\CoalType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */