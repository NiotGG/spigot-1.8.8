/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Dye
/*    */   extends MaterialData implements Colorable
/*    */ {
/*    */   public Dye()
/*    */   {
/* 11 */     super(Material.INK_SACK);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Dye(int type)
/*    */   {
/* 20 */     super(type);
/*    */   }
/*    */   
/*    */   public Dye(Material type) {
/* 24 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Dye(int type, byte data)
/*    */   {
/* 34 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Dye(Material type, byte data)
/*    */   {
/* 44 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Dye(DyeColor color)
/*    */   {
/* 51 */     super(Material.INK_SACK, color.getDyeData());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DyeColor getColor()
/*    */   {
/* 60 */     return DyeColor.getByDyeData(getData());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setColor(DyeColor color)
/*    */   {
/* 69 */     setData(color.getDyeData());
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 74 */     return getColor() + " DYE(" + getData() + ")";
/*    */   }
/*    */   
/*    */   public Dye clone()
/*    */   {
/* 79 */     return (Dye)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Dye.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */