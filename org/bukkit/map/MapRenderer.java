/*    */ package org.bukkit.map;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MapRenderer
/*    */ {
/*    */   private boolean contextual;
/*    */   
/*    */   public MapRenderer()
/*    */   {
/* 17 */     this(false);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public MapRenderer(boolean contextual)
/*    */   {
/* 27 */     this.contextual = contextual;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final boolean isContextual()
/*    */   {
/* 37 */     return this.contextual;
/*    */   }
/*    */   
/*    */   public void initialize(MapView map) {}
/*    */   
/*    */   public abstract void render(MapView paramMapView, MapCanvas paramMapCanvas, Player paramPlayer);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\map\MapRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */