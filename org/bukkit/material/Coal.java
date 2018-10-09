/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.CoalType;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class Coal
/*    */   extends MaterialData
/*    */ {
/*    */   public Coal()
/*    */   {
/* 11 */     super(Material.COAL);
/*    */   }
/*    */   
/*    */   public Coal(CoalType type) {
/* 15 */     this();
/* 16 */     setType(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Coal(int type)
/*    */   {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public Coal(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Coal(int type, byte data)
/*    */   {
/* 39 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Coal(Material type, byte data)
/*    */   {
/* 49 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public CoalType getType()
/*    */   {
/* 58 */     return CoalType.getByData(getData());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setType(CoalType type)
/*    */   {
/* 67 */     setData(type.getData());
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     return getType() + " " + super.toString();
/*    */   }
/*    */   
/*    */   public Coal clone()
/*    */   {
/* 77 */     return (Coal)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Coal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */