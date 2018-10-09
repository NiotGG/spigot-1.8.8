/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Cauldron
/*    */   extends MaterialData
/*    */ {
/*    */   private static final int CAULDRON_FULL = 3;
/*    */   private static final int CAULDRON_EMPTY = 0;
/*    */   
/*    */   public Cauldron()
/*    */   {
/* 13 */     super(Material.CAULDRON);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Cauldron(int type, byte data)
/*    */   {
/* 24 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Cauldron(byte data)
/*    */   {
/* 34 */     super(Material.CAULDRON, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isFull()
/*    */   {
/* 43 */     return getData() >= 3;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 52 */     return getData() <= 0;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 57 */     return (isFull() ? "FULL" : isEmpty() ? "EMPTY" : new StringBuilder(String.valueOf(getData())).append("/3 FULL").toString()) + " CAULDRON";
/*    */   }
/*    */   
/*    */   public Cauldron clone()
/*    */   {
/* 62 */     return (Cauldron)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Cauldron.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */