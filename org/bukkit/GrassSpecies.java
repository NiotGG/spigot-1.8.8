/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum GrassSpecies
/*    */ {
/* 12 */   DEAD(
/*    */   
/*    */ 
/* 15 */     0), 
/* 16 */   NORMAL(
/*    */   
/*    */ 
/* 19 */     1), 
/* 20 */   FERN_LIKE(
/*    */   
/*    */ 
/* 23 */     2);
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, GrassSpecies> BY_DATA;
/*    */   
/*    */   private GrassSpecies(int data) {
/* 29 */     this.data = ((byte)data);
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
/* 40 */     return this.data;
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
/*    */   public static GrassSpecies getByData(byte data)
/*    */   {
/* 53 */     return (GrassSpecies)BY_DATA.get(Byte.valueOf(data));
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 26 */     BY_DATA = Maps.newHashMap();
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
/*    */     GrassSpecies[] arrayOfGrassSpecies;
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
/* 57 */     int i = (arrayOfGrassSpecies = values()).length; for (int j = 0; j < i; j++) { GrassSpecies grassSpecies = arrayOfGrassSpecies[j];
/* 58 */       BY_DATA.put(Byte.valueOf(grassSpecies.getData()), grassSpecies);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\GrassSpecies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */