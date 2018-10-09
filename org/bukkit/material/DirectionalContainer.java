/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.BlockFace;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DirectionalContainer
/*    */   extends MaterialData
/*    */   implements Directional
/*    */ {
/*    */   @Deprecated
/*    */   public DirectionalContainer(int type)
/*    */   {
/* 16 */     super(type);
/*    */   }
/*    */   
/*    */   public DirectionalContainer(Material type) {
/* 20 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public DirectionalContainer(int type, byte data)
/*    */   {
/* 30 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/* 40 */   public DirectionalContainer(Material type, byte data) { super(type, data); }
/*    */   
/*    */   public void setFacingDirection(BlockFace face) { byte data;
/*    */     byte data;
/*    */     byte data;
/*    */     byte data;
/* 46 */     switch (face) {
/*    */     case DOWN: 
/* 48 */       data = 2;
/* 49 */       break;
/*    */     
/*    */     case EAST_NORTH_EAST: 
/* 52 */       data = 3;
/* 53 */       break;
/*    */     
/*    */     case EAST_SOUTH_EAST: 
/* 56 */       data = 4;
/* 57 */       break;
/*    */     
/*    */     case EAST: 
/*    */     default: 
/* 61 */       data = 5;
/*    */     }
/*    */     
/* 64 */     setData(data);
/*    */   }
/*    */   
/*    */   public BlockFace getFacing() {
/* 68 */     byte data = getData();
/*    */     
/* 70 */     switch (data) {
/*    */     case 2: 
/* 72 */       return BlockFace.NORTH;
/*    */     
/*    */     case 3: 
/* 75 */       return BlockFace.SOUTH;
/*    */     
/*    */     case 4: 
/* 78 */       return BlockFace.WEST;
/*    */     }
/*    */     
/*    */     
/* 82 */     return BlockFace.EAST;
/*    */   }
/*    */   
/*    */ 
/*    */   public String toString()
/*    */   {
/* 88 */     return super.toString() + " facing " + getFacing();
/*    */   }
/*    */   
/*    */   public DirectionalContainer clone()
/*    */   {
/* 93 */     return (DirectionalContainer)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\DirectionalContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */