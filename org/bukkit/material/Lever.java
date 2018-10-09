/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Lever
/*     */   extends SimpleAttachableMaterialData implements Redstone
/*     */ {
/*     */   public Lever()
/*     */   {
/*  11 */     super(Material.LEVER);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Lever(int type)
/*     */   {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public Lever(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Lever(int type, byte data)
/*     */   {
/*  34 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Lever(Material type, byte data)
/*     */   {
/*  44 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isPowered()
/*     */   {
/*  54 */     return (getData() & 0x8) == 8;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPowered(boolean isPowered)
/*     */   {
/*  63 */     setData((byte)(isPowered ? getData() | 0x8 : getData() & 0xFFFFFFF7));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BlockFace getAttachedFace()
/*     */   {
/*  72 */     byte data = (byte)(getData() & 0x7);
/*     */     
/*  74 */     switch (data) {
/*     */     case 1: 
/*  76 */       return BlockFace.WEST;
/*     */     
/*     */     case 2: 
/*  79 */       return BlockFace.EAST;
/*     */     
/*     */     case 3: 
/*  82 */       return BlockFace.NORTH;
/*     */     
/*     */     case 4: 
/*  85 */       return BlockFace.SOUTH;
/*     */     
/*     */     case 5: 
/*     */     case 6: 
/*  89 */       return BlockFace.DOWN;
/*     */     
/*     */     case 0: 
/*     */     case 7: 
/*  93 */       return BlockFace.UP;
/*     */     }
/*     */     
/*     */     
/*  97 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/* 104 */     byte data = (byte)(getData() & 0x8);
/* 105 */     BlockFace attach = getAttachedFace();
/*     */     
/* 107 */     if (attach == BlockFace.DOWN) {
/* 108 */       switch (face) {
/*     */       case DOWN: 
/*     */       case EAST_NORTH_EAST: 
/* 111 */         data = (byte)(data | 0x5);
/* 112 */         break;
/*     */       
/*     */       case EAST: 
/*     */       case EAST_SOUTH_EAST: 
/* 116 */         data = (byte)(data | 0x6);
/*     */       }
/*     */       
/* 119 */     } else if (attach == BlockFace.UP) {
/* 120 */       switch (face) {
/*     */       case DOWN: 
/*     */       case EAST_NORTH_EAST: 
/* 123 */         data = (byte)(data | 0x7);
/* 124 */         break;
/*     */       
/*     */       case EAST: 
/*     */       case EAST_SOUTH_EAST: 
/* 128 */         data = (byte)(data | 0x0);
/*     */       }
/*     */       
/*     */     } else {
/* 132 */       switch (face) {
/*     */       case EAST: 
/* 134 */         data = (byte)(data | 0x1);
/* 135 */         break;
/*     */       
/*     */       case EAST_SOUTH_EAST: 
/* 138 */         data = (byte)(data | 0x2);
/* 139 */         break;
/*     */       
/*     */       case EAST_NORTH_EAST: 
/* 142 */         data = (byte)(data | 0x3);
/* 143 */         break;
/*     */       
/*     */       case DOWN: 
/* 146 */         data = (byte)(data | 0x4);
/*     */       }
/*     */       
/*     */     }
/* 150 */     setData(data);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 155 */     return super.toString() + " facing " + getFacing() + " " + (isPowered() ? "" : "NOT ") + "POWERED";
/*     */   }
/*     */   
/*     */   public Lever clone()
/*     */   {
/* 160 */     return (Lever)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Lever.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */