/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Door
/*     */   extends MaterialData
/*     */   implements Directional, Openable
/*     */ {
/*     */   @Deprecated
/*     */   public Door()
/*     */   {
/*  23 */     super(Material.WOODEN_DOOR);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Door(int type)
/*     */   {
/*  32 */     super(type);
/*     */   }
/*     */   
/*     */   public Door(Material type) {
/*  36 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Door(int type, byte data)
/*     */   {
/*  46 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Door(Material type, byte data)
/*     */   {
/*  56 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isOpen()
/*     */   {
/*  63 */     return (getData() & 0x4) == 4;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setOpen(boolean isOpen)
/*     */   {
/*  70 */     setData((byte)(isOpen ? getData() | 0x4 : getData() & 0xFFFFFFFB));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isTopHalf()
/*     */   {
/*  77 */     return (getData() & 0x8) == 8;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTopHalf(boolean isTopHalf)
/*     */   {
/*  86 */     setData((byte)(isTopHalf ? getData() | 0x8 : getData() & 0xFFFFFFF7));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public BlockFace getHingeCorner()
/*     */   {
/*  95 */     return BlockFace.SELF;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 100 */     return (isTopHalf() ? "TOP" : "BOTTOM") + " half of " + super.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/* 111 */     byte data = (byte)(getData() & 0x12);
/* 112 */     switch (face) {
/*     */     case EAST_SOUTH_EAST: 
/* 114 */       data = (byte)(data | 0x0);
/* 115 */       break;
/*     */     
/*     */     case DOWN: 
/* 118 */       data = (byte)(data | 0x1);
/* 119 */       break;
/*     */     
/*     */     case EAST: 
/* 122 */       data = (byte)(data | 0x2);
/* 123 */       break;
/*     */     
/*     */     case EAST_NORTH_EAST: 
/* 126 */       data = (byte)(data | 0x3);
/*     */     }
/*     */     
/* 129 */     setData(data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getFacing()
/*     */   {
/* 140 */     byte data = (byte)(getData() & 0x3);
/* 141 */     switch (data) {
/*     */     case 0: 
/* 143 */       return BlockFace.WEST;
/*     */     
/*     */     case 1: 
/* 146 */       return BlockFace.NORTH;
/*     */     
/*     */     case 2: 
/* 149 */       return BlockFace.EAST;
/*     */     
/*     */     case 3: 
/* 152 */       return BlockFace.SOUTH;
/*     */     }
/* 154 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getHinge()
/*     */   {
/* 165 */     return (getData() & 0x1) == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHinge(boolean hinge)
/*     */   {
/* 174 */     setData((byte)(hinge ? getData() | 0x1 : getData() & 0xFFFFFFFE));
/*     */   }
/*     */   
/*     */   public Door clone()
/*     */   {
/* 179 */     return (Door)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Door.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */