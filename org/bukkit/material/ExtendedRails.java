/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtendedRails
/*    */   extends Rails
/*    */ {
/*    */   @Deprecated
/*    */   public ExtendedRails(int type)
/*    */   {
/* 17 */     super(type);
/*    */   }
/*    */   
/*    */   public ExtendedRails(Material type) {
/* 21 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public ExtendedRails(int type, byte data)
/*    */   {
/* 31 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public ExtendedRails(Material type, byte data)
/*    */   {
/* 41 */     super(type, data);
/*    */   }
/*    */   
/*    */   public boolean isCurve()
/*    */   {
/* 46 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   protected byte getConvertedData()
/*    */   {
/* 56 */     return (byte)(getData() & 0x7);
/*    */   }
/*    */   
/*    */   public void setDirection(BlockFace face, boolean isOnSlope)
/*    */   {
/* 61 */     boolean extraBitSet = (getData() & 0x8) == 8;
/*    */     
/* 63 */     if ((face != BlockFace.WEST) && (face != BlockFace.EAST) && (face != BlockFace.NORTH) && (face != BlockFace.SOUTH)) {
/* 64 */       throw new IllegalArgumentException("Detector rails and powered rails cannot be set on a curve!");
/*    */     }
/*    */     
/* 67 */     super.setDirection(face, isOnSlope);
/* 68 */     setData((byte)(extraBitSet ? getData() | 0x8 : getData() & 0xFFFFFFF7));
/*    */   }
/*    */   
/*    */   public ExtendedRails clone()
/*    */   {
/* 73 */     return (ExtendedRails)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\ExtendedRails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */