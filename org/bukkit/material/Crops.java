/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.CropState;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Crops
/*    */   extends MaterialData
/*    */ {
/*    */   public Crops()
/*    */   {
/* 11 */     super(Material.CROPS);
/*    */   }
/*    */   
/*    */   public Crops(CropState state) {
/* 15 */     this();
/* 16 */     setState(state);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Crops(int type)
/*    */   {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public Crops(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Crops(int type, byte data)
/*    */   {
/* 39 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Crops(Material type, byte data)
/*    */   {
/* 49 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CropState getState()
/*    */   {
/* 58 */     return CropState.getByData(getData());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setState(CropState state)
/*    */   {
/* 67 */     setData(state.getData());
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     return getState() + " " + super.toString();
/*    */   }
/*    */   
/*    */   public Crops clone()
/*    */   {
/* 77 */     return (Crops)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Crops.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */