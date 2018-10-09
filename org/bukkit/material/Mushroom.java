/*     */ package org.bukkit.material;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockFace;
/*     */ 
/*     */ 
/*     */ public class Mushroom
/*     */   extends MaterialData
/*     */ {
/*     */   private static final byte SHROOM_NONE = 0;
/*     */   private static final byte SHROOM_STEM = 10;
/*     */   private static final byte NORTH_LIMIT = 4;
/*     */   private static final byte SOUTH_LIMIT = 6;
/*     */   private static final byte EAST_WEST_LIMIT = 3;
/*     */   private static final byte EAST_REMAINDER = 0;
/*     */   private static final byte WEST_REMAINDER = 1;
/*     */   private static final byte NORTH_SOUTH_MOD = 3;
/*     */   private static final byte EAST_WEST_MOD = 1;
/*     */   
/*     */   public Mushroom(Material shroom)
/*     */   {
/*  25 */     super(shroom);
/*  26 */     Validate.isTrue((shroom == Material.HUGE_MUSHROOM_1) || (shroom == Material.HUGE_MUSHROOM_2), "Not a mushroom!");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Mushroom(Material shroom, byte data)
/*     */   {
/*  36 */     super(shroom, data);
/*  37 */     Validate.isTrue((shroom == Material.HUGE_MUSHROOM_1) || (shroom == Material.HUGE_MUSHROOM_2), "Not a mushroom!");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public Mushroom(int type, byte data)
/*     */   {
/*  47 */     super(type, data);
/*  48 */     Validate.isTrue((type == Material.HUGE_MUSHROOM_1.getId()) || (type == Material.HUGE_MUSHROOM_2.getId()), "Not a mushroom!");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isStem()
/*     */   {
/*  55 */     return getData() == 10;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setStem()
/*     */   {
/*  62 */     setData((byte)10);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFacePainted(BlockFace face)
/*     */   {
/*  72 */     byte data = getData();
/*     */     
/*  74 */     if ((data == 0) || (data == 10)) {
/*  75 */       return false;
/*     */     }
/*     */     
/*  78 */     switch (face) {
/*     */     case EAST_SOUTH_EAST: 
/*  80 */       return data < 4;
/*     */     case EAST: 
/*  82 */       return data > 6;
/*     */     case DOWN: 
/*  84 */       return data % 3 == 0;
/*     */     case EAST_NORTH_EAST: 
/*  86 */       return data % 3 == 1;
/*     */     case NORTH: 
/*  88 */       return true;
/*     */     }
/*  90 */     return false;
/*     */   }
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
/*     */   public void setFacePainted(BlockFace face, boolean painted)
/*     */   {
/* 104 */     if (painted == isFacePainted(face)) {
/* 105 */       return;
/*     */     }
/*     */     
/* 108 */     byte data = getData();
/*     */     
/* 110 */     if (data == 10) {
/* 111 */       data = 5;
/*     */     }
/*     */     
/* 114 */     switch (face) {
/*     */     case EAST_SOUTH_EAST: 
/* 116 */       if (painted) {
/* 117 */         data = (byte)(data - 3);
/*     */       } else {
/* 119 */         data = (byte)(data + 3);
/*     */       }
/*     */       
/* 122 */       break;
/*     */     case EAST: 
/* 124 */       if (painted) {
/* 125 */         data = (byte)(data + 3);
/*     */       } else {
/* 127 */         data = (byte)(data - 3);
/*     */       }
/*     */       
/* 130 */       break;
/*     */     case DOWN: 
/* 132 */       if (painted) {
/* 133 */         data = (byte)(data + 1);
/*     */       } else {
/* 135 */         data = (byte)(data - 1);
/*     */       }
/*     */       
/* 138 */       break;
/*     */     case EAST_NORTH_EAST: 
/* 140 */       if (painted) {
/* 141 */         data = (byte)(data - 1);
/*     */       } else {
/* 143 */         data = (byte)(data + 1);
/*     */       }
/*     */       
/* 146 */       break;
/*     */     case NORTH: 
/* 148 */       if (!painted) {
/* 149 */         data = 0;
/*     */       }
/*     */       
/* 152 */       break;
/*     */     default: 
/* 154 */       throw new IllegalArgumentException("Can't paint that face of a mushroom!");
/*     */     }
/*     */     
/* 157 */     setData(data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<BlockFace> getPaintedFaces()
/*     */   {
/* 165 */     EnumSet<BlockFace> faces = EnumSet.noneOf(BlockFace.class);
/*     */     
/* 167 */     if (isFacePainted(BlockFace.WEST)) {
/* 168 */       faces.add(BlockFace.WEST);
/*     */     }
/*     */     
/* 171 */     if (isFacePainted(BlockFace.NORTH)) {
/* 172 */       faces.add(BlockFace.NORTH);
/*     */     }
/*     */     
/* 175 */     if (isFacePainted(BlockFace.SOUTH)) {
/* 176 */       faces.add(BlockFace.SOUTH);
/*     */     }
/*     */     
/* 179 */     if (isFacePainted(BlockFace.EAST)) {
/* 180 */       faces.add(BlockFace.EAST);
/*     */     }
/*     */     
/* 183 */     if (isFacePainted(BlockFace.UP)) {
/* 184 */       faces.add(BlockFace.UP);
/*     */     }
/*     */     
/* 187 */     return faces;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 192 */     return Material.getMaterial(getItemTypeId()).toString() + (isStem() ? "{STEM}" : getPaintedFaces());
/*     */   }
/*     */   
/*     */   public Mushroom clone()
/*     */   {
/* 197 */     return (Mushroom)super.clone();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Mushroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */