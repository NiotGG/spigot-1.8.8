/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ public class TripwireHook
/*     */   extends SimpleAttachableMaterialData
/*     */   implements Redstone
/*     */ {
/*     */   public TripwireHook()
/*     */   {
/*  12 */     super(Material.TRIPWIRE_HOOK);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public TripwireHook(int type)
/*     */   {
/*  21 */     super(type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public TripwireHook(int type, byte data)
/*     */   {
/*  31 */     super(type, data);
/*     */   }
/*     */   
/*     */   public TripwireHook(BlockFace dir) {
/*  35 */     this();
/*  36 */     setFacingDirection(dir);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConnected()
/*     */   {
/*  45 */     return (getData() & 0x4) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConnected(boolean connected)
/*     */   {
/*  54 */     int dat = getData() & 0xB;
/*  55 */     if (connected) {
/*  56 */       dat |= 0x4;
/*     */     }
/*  58 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isActivated()
/*     */   {
/*  67 */     return (getData() & 0x8) != 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setActivated(boolean act)
/*     */   {
/*  76 */     int dat = getData() & 0x7;
/*  77 */     if (act) {
/*  78 */       dat |= 0x8;
/*     */     }
/*  80 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public void setFacingDirection(BlockFace face) {
/*  84 */     int dat = getData() & 0xC;
/*  85 */     switch (face) {
/*     */     case EAST_SOUTH_EAST: 
/*  87 */       dat |= 0x1;
/*  88 */       break;
/*     */     case DOWN: 
/*  90 */       dat |= 0x2;
/*  91 */       break;
/*     */     case EAST: 
/*  93 */       dat |= 0x3;
/*  94 */       break;
/*     */     }
/*     */     
/*     */     
/*     */ 
/*  99 */     setData((byte)dat);
/*     */   }
/*     */   
/*     */   public BlockFace getAttachedFace() {
/* 103 */     switch (getData() & 0x3) {
/*     */     case 0: 
/* 105 */       return BlockFace.NORTH;
/*     */     case 1: 
/* 107 */       return BlockFace.EAST;
/*     */     case 2: 
/* 109 */       return BlockFace.SOUTH;
/*     */     case 3: 
/* 111 */       return BlockFace.WEST;
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isPowered() {
/* 117 */     return isActivated();
/*     */   }
/*     */   
/*     */   public TripwireHook clone()
/*     */   {
/* 122 */     return (TripwireHook)super.clone();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 127 */     return super.toString() + " facing " + getFacing() + (isActivated() ? " Activated" : "") + (isConnected() ? " Connected" : "");
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\TripwireHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */