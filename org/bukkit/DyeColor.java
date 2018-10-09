/*     */ package org.bukkit;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum DyeColor
/*     */ {
/*  12 */   WHITE(
/*     */   
/*     */ 
/*  15 */     0, 15, Color.WHITE, Color.fromRGB(15790320)), 
/*  16 */   ORANGE(
/*     */   
/*     */ 
/*  19 */     1, 14, Color.fromRGB(14188339), Color.fromRGB(15435844)), 
/*  20 */   MAGENTA(
/*     */   
/*     */ 
/*  23 */     2, 13, Color.fromRGB(11685080), Color.fromRGB(12801229)), 
/*  24 */   LIGHT_BLUE(
/*     */   
/*     */ 
/*  27 */     3, 12, Color.fromRGB(6724056), Color.fromRGB(6719955)), 
/*  28 */   YELLOW(
/*     */   
/*     */ 
/*  31 */     4, 11, Color.fromRGB(15066419), Color.fromRGB(14602026)), 
/*  32 */   LIME(
/*     */   
/*     */ 
/*  35 */     5, 10, Color.fromRGB(8375321), Color.fromRGB(4312372)), 
/*  36 */   PINK(
/*     */   
/*     */ 
/*  39 */     6, 9, Color.fromRGB(15892389), Color.fromRGB(14188952)), 
/*  40 */   GRAY(
/*     */   
/*     */ 
/*  43 */     7, 8, Color.fromRGB(5000268), Color.fromRGB(4408131)), 
/*  44 */   SILVER(
/*     */   
/*     */ 
/*  47 */     8, 7, Color.fromRGB(10066329), Color.fromRGB(11250603)), 
/*  48 */   CYAN(
/*     */   
/*     */ 
/*  51 */     9, 6, Color.fromRGB(5013401), Color.fromRGB(2651799)), 
/*  52 */   PURPLE(
/*     */   
/*     */ 
/*  55 */     10, 5, Color.fromRGB(8339378), Color.fromRGB(8073150)), 
/*  56 */   BLUE(
/*     */   
/*     */ 
/*  59 */     11, 4, Color.fromRGB(3361970), Color.fromRGB(2437522)), 
/*  60 */   BROWN(
/*     */   
/*     */ 
/*  63 */     12, 3, Color.fromRGB(6704179), Color.fromRGB(5320730)), 
/*  64 */   GREEN(
/*     */   
/*     */ 
/*  67 */     13, 2, Color.fromRGB(6717235), Color.fromRGB(3887386)), 
/*  68 */   RED(
/*     */   
/*     */ 
/*  71 */     14, 1, Color.fromRGB(10040115), Color.fromRGB(11743532)), 
/*  72 */   BLACK(
/*     */   
/*     */ 
/*  75 */     15, 0, Color.fromRGB(1644825), Color.fromRGB(1973019));
/*     */   
/*     */   private final byte woolData;
/*     */   private final byte dyeData;
/*     */   private final Color color;
/*     */   private final Color firework;
/*     */   private static final DyeColor[] BY_WOOL_DATA;
/*     */   private static final DyeColor[] BY_DYE_DATA;
/*     */   private static final Map<Color, DyeColor> BY_COLOR;
/*     */   private static final Map<Color, DyeColor> BY_FIREWORK;
/*     */   
/*     */   private DyeColor(int woolData, int dyeData, Color color, Color firework) {
/*  87 */     this.woolData = ((byte)woolData);
/*  88 */     this.dyeData = ((byte)dyeData);
/*  89 */     this.color = color;
/*  90 */     this.firework = firework;
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
/*     */   @Deprecated
/*     */   public byte getData()
/*     */   {
/* 104 */     return getWoolData();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public byte getWoolData()
/*     */   {
/* 116 */     return this.woolData;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public byte getDyeData()
/*     */   {
/* 128 */     return this.dyeData;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getColor()
/*     */   {
/* 137 */     return this.color;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Color getFireworkColor()
/*     */   {
/* 146 */     return this.firework;
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
/*     */ 
/*     */   @Deprecated
/*     */   public static DyeColor getByData(byte data)
/*     */   {
/* 162 */     return getByWoolData(data);
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
/*     */   @Deprecated
/*     */   public static DyeColor getByWoolData(byte data)
/*     */   {
/* 176 */     int i = 0xFF & data;
/* 177 */     if (i >= BY_WOOL_DATA.length) {
/* 178 */       return null;
/*     */     }
/* 180 */     return BY_WOOL_DATA[i];
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
/*     */   @Deprecated
/*     */   public static DyeColor getByDyeData(byte data)
/*     */   {
/* 194 */     int i = 0xFF & data;
/* 195 */     if (i >= BY_DYE_DATA.length) {
/* 196 */       return null;
/*     */     }
/* 198 */     return BY_DYE_DATA[i];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DyeColor getByColor(Color color)
/*     */   {
/* 209 */     return (DyeColor)BY_COLOR.get(color);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DyeColor getByFireworkColor(Color color)
/*     */   {
/* 220 */     return (DyeColor)BY_FIREWORK.get(color);
/*     */   }
/*     */   
/*     */   static {
/* 224 */     BY_WOOL_DATA = values();
/* 225 */     BY_DYE_DATA = values();
/* 226 */     ImmutableMap.Builder<Color, DyeColor> byColor = ImmutableMap.builder();
/* 227 */     ImmutableMap.Builder<Color, DyeColor> byFirework = ImmutableMap.builder();
/*     */     DyeColor[] arrayOfDyeColor;
/* 229 */     int i = (arrayOfDyeColor = values()).length; for (int j = 0; j < i; j++) { DyeColor color = arrayOfDyeColor[j];
/* 230 */       BY_WOOL_DATA[(color.woolData & 0xFF)] = color;
/* 231 */       BY_DYE_DATA[(color.dyeData & 0xFF)] = color;
/* 232 */       byColor.put(color.getColor(), color);
/* 233 */       byFirework.put(color.getFireworkColor(), color);
/*     */     }
/*     */     
/* 236 */     BY_COLOR = byColor.build();
/* 237 */     BY_FIREWORK = byFirework.build();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\DyeColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */