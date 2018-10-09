/*    */ package org.bukkit.craftbukkit.libs.joptsimple.internal;
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
/*    */ class ColumnWidthCalculator
/*    */ {
/*    */   int calculate(int totalWidth, int numberOfColumns)
/*    */   {
/* 34 */     if (numberOfColumns == 1) {
/* 35 */       return totalWidth;
/*    */     }
/* 37 */     int remainder = totalWidth % numberOfColumns;
/* 38 */     if (remainder == numberOfColumns - 1)
/* 39 */       return totalWidth / numberOfColumns;
/* 40 */     return totalWidth / numberOfColumns - 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\internal\ColumnWidthCalculator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */