/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum CropState
/*    */ {
/* 12 */   SEEDED(
/*    */   
/*    */ 
/* 15 */     0), 
/* 16 */   GERMINATED(
/*    */   
/*    */ 
/* 19 */     1), 
/* 20 */   VERY_SMALL(
/*    */   
/*    */ 
/* 23 */     2), 
/* 24 */   SMALL(
/*    */   
/*    */ 
/* 27 */     3), 
/* 28 */   MEDIUM(
/*    */   
/*    */ 
/* 31 */     4), 
/* 32 */   TALL(
/*    */   
/*    */ 
/* 35 */     5), 
/* 36 */   VERY_TALL(
/*    */   
/*    */ 
/* 39 */     6), 
/* 40 */   RIPE(
/*    */   
/*    */ 
/* 43 */     7);
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, CropState> BY_DATA;
/*    */   
/*    */   private CropState(int data) {
/* 49 */     this.data = ((byte)data);
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
/* 60 */     return this.data;
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
/*    */   public static CropState getByData(byte data)
/*    */   {
/* 73 */     return (CropState)BY_DATA.get(Byte.valueOf(data));
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 46 */     BY_DATA = Maps.newHashMap();
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
/*    */     CropState[] arrayOfCropState;
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
/* 77 */     int i = (arrayOfCropState = values()).length; for (int j = 0; j < i; j++) { CropState cropState = arrayOfCropState[j];
/* 78 */       BY_DATA.put(Byte.valueOf(cropState.getData()), cropState);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\CropState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */