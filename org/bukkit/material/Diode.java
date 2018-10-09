/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class Diode extends MaterialData implements Directional
/*     */ {
/*     */   public Diode() {
/*   8 */     super(org.bukkit.Material.DIODE_BLOCK_ON);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Diode(int type)
/*     */   {
/*  17 */     super(type);
/*     */   }
/*     */   
/*     */   public Diode(org.bukkit.Material type) {
/*  21 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Diode(int type, byte data)
/*     */   {
/*  31 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Diode(org.bukkit.Material type, byte data)
/*     */   {
/*  41 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDelay(int delay)
/*     */   {
/*  51 */     if (delay > 4) {
/*  52 */       delay = 4;
/*     */     }
/*  54 */     if (delay < 1) {
/*  55 */       delay = 1;
/*     */     }
/*  57 */     byte newData = (byte)(getData() & 0x3);
/*     */     
/*  59 */     setData((byte)(newData | delay - 1 << 2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDelay()
/*     */   {
/*  68 */     return (getData() >> 2) + 1;
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  72 */     int delay = getDelay();
/*     */     byte data;
/*     */     byte data;
/*  75 */     byte data; byte data; switch (face) {
/*     */     case EAST: 
/*  77 */       data = 1;
/*  78 */       break;
/*     */     
/*     */     case EAST_NORTH_EAST: 
/*  81 */       data = 2;
/*  82 */       break;
/*     */     
/*     */     case EAST_SOUTH_EAST: 
/*  85 */       data = 3;
/*  86 */       break;
/*     */     
/*     */     case DOWN: 
/*     */     default: 
/*  90 */       data = 0;
/*     */     }
/*     */     
/*  93 */     setData(data);
/*  94 */     setDelay(delay);
/*     */   }
/*     */   
/*     */   public BlockFace getFacing() {
/*  98 */     byte data = (byte)(getData() & 0x3);
/*     */     
/* 100 */     switch (data) {
/*     */     case 0: 
/*     */     default: 
/* 103 */       return BlockFace.NORTH;
/*     */     
/*     */     case 1: 
/* 106 */       return BlockFace.EAST;
/*     */     
/*     */     case 2: 
/* 109 */       return BlockFace.SOUTH;
/*     */     }
/*     */     
/* 112 */     return BlockFace.WEST;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 118 */     return super.toString() + " facing " + getFacing() + " with " + getDelay() + " ticks delay";
/*     */   }
/*     */   
/*     */   public Diode clone()
/*     */   {
/* 123 */     return (Diode)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Diode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */