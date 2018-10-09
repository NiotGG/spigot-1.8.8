/*    */ package org.bukkit.craftbukkit.v1_8_R3.map;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.v1_8_R3.MapIcon;
/*    */ import net.minecraft.server.v1_8_R3.WorldMap;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.map.MapCanvas;
/*    */ import org.bukkit.map.MapCursorCollection;
/*    */ import org.bukkit.map.MapRenderer;
/*    */ import org.bukkit.map.MapView;
/*    */ 
/*    */ public class CraftMapRenderer extends MapRenderer
/*    */ {
/*    */   private final WorldMap worldMap;
/*    */   
/*    */   public CraftMapRenderer(CraftMapView mapView, WorldMap worldMap)
/*    */   {
/* 19 */     super(false);
/* 20 */     this.worldMap = worldMap;
/*    */   }
/*    */   
/*    */ 
/*    */   public void render(MapView map, MapCanvas canvas, Player player)
/*    */   {
/* 26 */     for (int x = 0; x < 128; x++) {
/* 27 */       for (int y = 0; y < 128; y++) {
/* 28 */         canvas.setPixel(x, y, this.worldMap.colors[(y * 128 + x)]);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 33 */     MapCursorCollection cursors = canvas.getCursors();
/* 34 */     while (cursors.size() > 0) {
/* 35 */       cursors.removeCursor(cursors.getCursor(0));
/*    */     }
/*    */     
/* 38 */     for (java.util.UUID key : this.worldMap.decorations.keySet())
/*    */     {
/* 40 */       Player other = Bukkit.getPlayer(key);
/* 41 */       if ((other == null) || (player.canSee(other)))
/*    */       {
/*    */ 
/*    */ 
/* 45 */         MapIcon decoration = (MapIcon)this.worldMap.decorations.get(key);
/* 46 */         cursors.addCursor(decoration.getX(), decoration.getY(), (byte)(decoration.getRotation() & 0xF), decoration.getType());
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\map\CraftMapRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */