/*     */ package org.bukkit.map;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Image;
/*     */ import java.awt.image.BufferedImage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MapPalette
/*     */ {
/*     */   private static Color c(int r, int g, int b)
/*     */   {
/*  19 */     return new Color(r, g, b);
/*     */   }
/*     */   
/*     */   private static double getDistance(Color c1, Color c2) {
/*  23 */     double rmean = (c1.getRed() + c2.getRed()) / 2.0D;
/*  24 */     double r = c1.getRed() - c2.getRed();
/*  25 */     double g = c1.getGreen() - c2.getGreen();
/*  26 */     int b = c1.getBlue() - c2.getBlue();
/*  27 */     double weightR = 2.0D + rmean / 256.0D;
/*  28 */     double weightG = 4.0D;
/*  29 */     double weightB = 2.0D + (255.0D - rmean) / 256.0D;
/*  30 */     return weightR * r * r + weightG * g * g + weightB * b * b;
/*     */   }
/*     */   
/*  33 */   static final Color[] colors = {
/*  34 */     c(0, 0, 0), c(0, 0, 0), c(0, 0, 0), c(0, 0, 0), 
/*  35 */     c(89, 125, 39), c(109, 153, 48), c(127, 178, 56), c(67, 94, 29), 
/*  36 */     c(174, 164, 115), c(213, 201, 140), c(247, 233, 163), c(130, 123, 86), 
/*  37 */     c(140, 140, 140), c(171, 171, 171), c(199, 199, 199), c(105, 105, 105), 
/*  38 */     c(180, 0, 0), c(220, 0, 0), c(255, 0, 0), c(135, 0, 0), 
/*  39 */     c(112, 112, 180), c(138, 138, 220), c(160, 160, 255), c(84, 84, 135), 
/*  40 */     c(117, 117, 117), c(144, 144, 144), c(167, 167, 167), c(88, 88, 88), 
/*  41 */     c(0, 87, 0), c(0, 106, 0), c(0, 124, 0), c(0, 65, 0), 
/*  42 */     c(180, 180, 180), c(220, 220, 220), c(255, 255, 255), c(135, 135, 135), 
/*  43 */     c(115, 118, 129), c(141, 144, 158), c(164, 168, 184), c(86, 88, 97), 
/*  44 */     c(106, 76, 54), c(130, 94, 66), c(151, 109, 77), c(79, 57, 40), 
/*  45 */     c(79, 79, 79), c(96, 96, 96), c(112, 112, 112), c(59, 59, 59), 
/*  46 */     c(45, 45, 180), c(55, 55, 220), c(64, 64, 255), c(33, 33, 135), 
/*  47 */     c(100, 84, 50), c(123, 102, 62), c(143, 119, 72), c(75, 63, 38), 
/*  48 */     c(180, 177, 172), c(220, 217, 211), c(255, 252, 245), c(135, 133, 129), 
/*  49 */     c(152, 89, 36), c(186, 109, 44), c(216, 127, 51), c(114, 67, 27), 
/*  50 */     c(125, 53, 152), c(153, 65, 186), c(178, 76, 216), c(94, 40, 114), 
/*  51 */     c(72, 108, 152), c(88, 132, 186), c(102, 153, 216), c(54, 81, 114), 
/*  52 */     c(161, 161, 36), c(197, 197, 44), c(229, 229, 51), c(121, 121, 27), 
/*  53 */     c(89, 144, 17), c(109, 176, 21), c(127, 204, 25), c(67, 108, 13), 
/*  54 */     c(170, 89, 116), c(208, 109, 142), c(242, 127, 165), c(128, 67, 87), 
/*  55 */     c(53, 53, 53), c(65, 65, 65), c(76, 76, 76), c(40, 40, 40), 
/*  56 */     c(108, 108, 108), c(132, 132, 132), c(153, 153, 153), c(81, 81, 81), 
/*  57 */     c(53, 89, 108), c(65, 109, 132), c(76, 127, 153), c(40, 67, 81), 
/*  58 */     c(89, 44, 125), c(109, 54, 153), c(127, 63, 178), c(67, 33, 94), 
/*  59 */     c(36, 53, 125), c(44, 65, 153), c(51, 76, 178), c(27, 40, 94), 
/*  60 */     c(72, 53, 36), c(88, 65, 44), c(102, 76, 51), c(54, 40, 27), 
/*  61 */     c(72, 89, 36), c(88, 109, 44), c(102, 127, 51), c(54, 67, 27), 
/*  62 */     c(108, 36, 36), c(132, 44, 44), c(153, 51, 51), c(81, 27, 27), 
/*  63 */     c(17, 17, 17), c(21, 21, 21), c(25, 25, 25), c(13, 13, 13), 
/*  64 */     c(176, 168, 54), c(215, 205, 66), c(250, 238, 77), c(132, 126, 40), 
/*  65 */     c(64, 154, 150), c(79, 188, 183), c(92, 219, 213), c(48, 115, 112), 
/*  66 */     c(52, 90, 180), c(63, 110, 220), c(74, 128, 255), c(39, 67, 135), 
/*  67 */     c(0, 153, 40), c(0, 187, 50), c(0, 217, 58), c(0, 114, 30), 
/*  68 */     c(91, 60, 34), c(111, 74, 42), c(129, 86, 49), c(68, 45, 25), 
/*  69 */     c(79, 1, 0), c(96, 1, 0), c(112, 2, 0), c(59, 1, 0) };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte TRANSPARENT = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte LIGHT_GREEN = 4;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte LIGHT_BROWN = 8;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte GRAY_1 = 12;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte RED = 16;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte PALE_BLUE = 20;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte GRAY_2 = 24;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte DARK_GREEN = 28;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte WHITE = 32;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte LIGHT_GRAY = 36;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte BROWN = 40;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte DARK_GRAY = 44;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte BLUE = 48;
/*     */   
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static final byte DARK_BROWN = 52;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static BufferedImage resizeImage(Image image)
/*     */   {
/* 151 */     BufferedImage result = new BufferedImage(128, 128, 2);
/* 152 */     Graphics2D graphics = result.createGraphics();
/* 153 */     graphics.drawImage(image, 0, 0, 128, 128, null);
/* 154 */     graphics.dispose();
/* 155 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static byte[] imageToBytes(Image image)
/*     */   {
/* 167 */     BufferedImage temp = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
/* 168 */     Graphics2D graphics = temp.createGraphics();
/* 169 */     graphics.drawImage(image, 0, 0, null);
/* 170 */     graphics.dispose();
/*     */     
/* 172 */     int[] pixels = new int[temp.getWidth() * temp.getHeight()];
/* 173 */     temp.getRGB(0, 0, temp.getWidth(), temp.getHeight(), pixels, 0, temp.getWidth());
/*     */     
/* 175 */     byte[] result = new byte[temp.getWidth() * temp.getHeight()];
/* 176 */     for (int i = 0; i < pixels.length; i++) {
/* 177 */       result[i] = matchColor(new Color(pixels[i], true));
/*     */     }
/* 179 */     return result;
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
/*     */   @Deprecated
/*     */   public static byte matchColor(int r, int g, int b)
/*     */   {
/* 194 */     return matchColor(new Color(r, g, b));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static byte matchColor(Color color)
/*     */   {
/* 207 */     if (color.getAlpha() < 128) { return 0;
/*     */     }
/* 209 */     int index = 0;
/* 210 */     double best = -1.0D;
/*     */     
/* 212 */     for (int i = 4; i < colors.length; i++) {
/* 213 */       double distance = getDistance(color, colors[i]);
/* 214 */       if ((distance < best) || (best == -1.0D)) {
/* 215 */         best = distance;
/* 216 */         index = i;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 221 */     return (byte)(index < 128 ? index : 65407 + (index - 127));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static Color getColor(byte index)
/*     */   {
/* 233 */     if (((index > -113) && (index < 0)) || (index > Byte.MAX_VALUE)) {
/* 234 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */     
/* 237 */     return colors[(index + 256)];
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\map\MapPalette.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */