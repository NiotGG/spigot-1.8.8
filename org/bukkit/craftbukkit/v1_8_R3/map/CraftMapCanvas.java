/*     */ package org.bukkit.craftbukkit.v1_8_R3.map;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import net.minecraft.server.v1_8_R3.WorldMap;
/*     */ import org.bukkit.map.MapCanvas;
/*     */ import org.bukkit.map.MapCursorCollection;
/*     */ import org.bukkit.map.MapFont;
/*     */ import org.bukkit.map.MapFont.CharacterSprite;
/*     */ import org.bukkit.map.MapPalette;
/*     */ 
/*     */ public class CraftMapCanvas implements MapCanvas
/*     */ {
/*  13 */   private final byte[] buffer = new byte['䀀'];
/*     */   private final CraftMapView mapView;
/*     */   private byte[] base;
/*  16 */   private MapCursorCollection cursors = new MapCursorCollection();
/*     */   
/*     */   protected CraftMapCanvas(CraftMapView mapView) {
/*  19 */     this.mapView = mapView;
/*  20 */     java.util.Arrays.fill(this.buffer, (byte)-1);
/*     */   }
/*     */   
/*     */   public CraftMapView getMapView() {
/*  24 */     return this.mapView;
/*     */   }
/*     */   
/*     */   public MapCursorCollection getCursors() {
/*  28 */     return this.cursors;
/*     */   }
/*     */   
/*     */   public void setCursors(MapCursorCollection cursors) {
/*  32 */     this.cursors = cursors;
/*     */   }
/*     */   
/*     */   public void setPixel(int x, int y, byte color) {
/*  36 */     if ((x < 0) || (y < 0) || (x >= 128) || (y >= 128))
/*  37 */       return;
/*  38 */     if (this.buffer[(y * 128 + x)] != color) {
/*  39 */       this.buffer[(y * 128 + x)] = color;
/*  40 */       this.mapView.worldMap.flagDirty(x, y);
/*     */     }
/*     */   }
/*     */   
/*     */   public byte getPixel(int x, int y) {
/*  45 */     if ((x < 0) || (y < 0) || (x >= 128) || (y >= 128))
/*  46 */       return 0;
/*  47 */     return this.buffer[(y * 128 + x)];
/*     */   }
/*     */   
/*     */   public byte getBasePixel(int x, int y) {
/*  51 */     if ((x < 0) || (y < 0) || (x >= 128) || (y >= 128))
/*  52 */       return 0;
/*  53 */     return this.base[(y * 128 + x)];
/*     */   }
/*     */   
/*     */   protected void setBase(byte[] base) {
/*  57 */     this.base = base;
/*     */   }
/*     */   
/*     */   protected byte[] getBuffer() {
/*  61 */     return this.buffer;
/*     */   }
/*     */   
/*     */   public void drawImage(int x, int y, Image image) {
/*  65 */     byte[] bytes = MapPalette.imageToBytes(image);
/*  66 */     for (int x2 = 0; x2 < image.getWidth(null); x2++) {
/*  67 */       for (int y2 = 0; y2 < image.getHeight(null); y2++) {
/*  68 */         setPixel(x + x2, y + y2, bytes[(y2 * image.getWidth(null) + x2)]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawText(int x, int y, MapFont font, String text) {
/*  74 */     int xStart = x;
/*  75 */     byte color = 44;
/*  76 */     if (!font.isValid(text)) {
/*  77 */       throw new IllegalArgumentException("text contains invalid characters");
/*     */     }
/*     */     
/*  80 */     for (int i = 0; i < text.length(); i++) {
/*  81 */       char ch = text.charAt(i);
/*  82 */       if (ch == '\n') {
/*  83 */         x = xStart;
/*  84 */         y += font.getHeight() + 1;
/*     */       } else {
/*  86 */         if (ch == '§') {
/*  87 */           int j = text.indexOf(';', i);
/*  88 */           if (j >= 0) {
/*     */             try {
/*  90 */               color = Byte.parseByte(text.substring(i + 1, j));
/*  91 */               i = j;
/*     */             }
/*     */             catch (NumberFormatException localNumberFormatException) {}
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*  98 */         MapFont.CharacterSprite sprite = font.getChar(text.charAt(i));
/*  99 */         for (int r = 0; r < font.getHeight(); r++) {
/* 100 */           for (int c = 0; c < sprite.getWidth(); c++) {
/* 101 */             if (sprite.get(r, c)) {
/* 102 */               setPixel(x + c, y + r, color);
/*     */             }
/*     */           }
/*     */         }
/* 106 */         x += sprite.getWidth() + 1;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\map\CraftMapCanvas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */