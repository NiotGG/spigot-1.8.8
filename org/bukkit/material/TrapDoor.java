/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class TrapDoor
/*     */   extends SimpleAttachableMaterialData implements Openable
/*     */ {
/*     */   public TrapDoor()
/*     */   {
/*  11 */     super(Material.TRAP_DOOR);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public TrapDoor(int type)
/*     */   {
/*  20 */     super(type);
/*     */   }
/*     */   
/*     */   public TrapDoor(Material type) {
/*  24 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public TrapDoor(int type, byte data)
/*     */   {
/*  34 */     super(type, data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public TrapDoor(Material type, byte data)
/*     */   {
/*  44 */     super(type, data);
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/*  48 */     return (getData() & 0x4) == 4;
/*     */   }
/*     */   
/*     */   public void setOpen(boolean isOpen) {
/*  52 */     byte data = getData();
/*     */     
/*  54 */     if (isOpen) {
/*  55 */       data = (byte)(data | 0x4);
/*     */     } else {
/*  57 */       data = (byte)(data & 0xFFFFFFFB);
/*     */     }
/*     */     
/*  60 */     setData(data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInverted()
/*     */   {
/*  69 */     return (getData() & 0x8) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInverted(boolean inv)
/*     */   {
/*  78 */     int dat = getData() & 0x7;
/*  79 */     if (inv) {
/*  80 */       dat |= 0x8;
/*     */     }
/*  82 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public BlockFace getAttachedFace() {
/*  86 */     byte data = (byte)(getData() & 0x3);
/*     */     
/*  88 */     switch (data) {
/*     */     case 0: 
/*  90 */       return BlockFace.SOUTH;
/*     */     
/*     */     case 1: 
/*  93 */       return BlockFace.NORTH;
/*     */     
/*     */     case 2: 
/*  96 */       return BlockFace.EAST;
/*     */     
/*     */     case 3: 
/*  99 */       return BlockFace.WEST;
/*     */     }
/*     */     
/* 102 */     return null;
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face)
/*     */   {
/* 107 */     byte data = (byte)(getData() & 0xC);
/*     */     
/* 109 */     switch (face) {
/*     */     case EAST_NORTH_EAST: 
/* 111 */       data = (byte)(data | 0x1);
/* 112 */       break;
/*     */     case EAST_SOUTH_EAST: 
/* 114 */       data = (byte)(data | 0x2);
/* 115 */       break;
/*     */     case EAST: 
/* 117 */       data = (byte)(data | 0x3);
/*     */     }
/*     */     
/*     */     
/* 121 */     setData(data);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 126 */     return (isOpen() ? "OPEN " : "CLOSED ") + super.toString() + " with hinges set " + getAttachedFace() + (isInverted() ? " inverted" : "");
/*     */   }
/*     */   
/*     */   public TrapDoor clone()
/*     */   {
/* 131 */     return (TrapDoor)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\TrapDoor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */