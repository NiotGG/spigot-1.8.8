/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Cake extends MaterialData {
/*    */   public Cake() {
/*  7 */     super(Material.CAKE_BLOCK);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Cake(int type)
/*    */   {
/* 16 */     super(type);
/*    */   }
/*    */   
/*    */   public Cake(Material type) {
/* 20 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Cake(int type, byte data)
/*    */   {
/* 30 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Cake(Material type, byte data)
/*    */   {
/* 40 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getSlicesEaten()
/*    */   {
/* 49 */     return getData();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getSlicesRemaining()
/*    */   {
/* 58 */     return 6 - getData();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setSlicesEaten(int n)
/*    */   {
/* 67 */     if (n < 6) {
/* 68 */       setData((byte)n);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setSlicesRemaining(int n)
/*    */   {
/* 78 */     if (n > 6) {
/* 79 */       n = 6;
/*    */     }
/* 81 */     setData((byte)(6 - n));
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 86 */     return super.toString() + " " + getSlicesEaten() + "/" + getSlicesRemaining() + " slices eaten/remaining";
/*    */   }
/*    */   
/*    */   public Cake clone()
/*    */   {
/* 91 */     return (Cake)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Cake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */