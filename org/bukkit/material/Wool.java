/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Wool
/*    */   extends MaterialData implements Colorable
/*    */ {
/*    */   public Wool()
/*    */   {
/* 11 */     super(Material.WOOL);
/*    */   }
/*    */   
/*    */   public Wool(DyeColor color) {
/* 15 */     this();
/* 16 */     setColor(color);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Wool(int type)
/*    */   {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public Wool(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Wool(int type, byte data)
/*    */   {
/* 39 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Wool(Material type, byte data)
/*    */   {
/* 49 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DyeColor getColor()
/*    */   {
/* 58 */     return DyeColor.getByWoolData(getData());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setColor(DyeColor color)
/*    */   {
/* 67 */     setData(color.getWoolData());
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     return getColor() + " " + super.toString();
/*    */   }
/*    */   
/*    */   public Wool clone()
/*    */   {
/* 77 */     return (Wool)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Wool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */