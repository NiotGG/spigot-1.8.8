/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.SandstoneType;
/*    */ 
/*    */ public class Sandstone
/*    */   extends MaterialData
/*    */ {
/*    */   public Sandstone()
/*    */   {
/* 11 */     super(Material.SANDSTONE);
/*    */   }
/*    */   
/*    */   public Sandstone(SandstoneType type) {
/* 15 */     this();
/* 16 */     setType(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Sandstone(int type)
/*    */   {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public Sandstone(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Sandstone(int type, byte data)
/*    */   {
/* 39 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Sandstone(Material type, byte data)
/*    */   {
/* 49 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public SandstoneType getType()
/*    */   {
/* 58 */     return SandstoneType.getByData(getData());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setType(SandstoneType type)
/*    */   {
/* 67 */     setData(type.getData());
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     return getType() + " " + super.toString();
/*    */   }
/*    */   
/*    */   public Sandstone clone()
/*    */   {
/* 77 */     return (Sandstone)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Sandstone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */