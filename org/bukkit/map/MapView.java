/*    */ package org.bukkit.map;
/*    */ 
/*    */ import org.bukkit.World;
/*    */ 
/*    */ public abstract interface MapView
/*    */ {
/*    */   @Deprecated
/*    */   public abstract short getId();
/*    */   
/*    */   public abstract boolean isVirtual();
/*    */   
/*    */   public abstract Scale getScale();
/*    */   
/*    */   public static enum Scale {
/* 15 */     CLOSEST(0), 
/* 16 */     CLOSE(1), 
/* 17 */     NORMAL(2), 
/* 18 */     FAR(3), 
/* 19 */     FARTHEST(4);
/*    */     
/*    */     private byte value;
/*    */     
/*    */     private Scale(int value) {
/* 24 */       this.value = ((byte)value);
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     @Deprecated
/*    */     public static Scale valueOf(byte value)
/*    */     {
/* 36 */       switch (value) {
/* 37 */       case 0:  return CLOSEST;
/* 38 */       case 1:  return CLOSE;
/* 39 */       case 2:  return NORMAL;
/* 40 */       case 3:  return FAR;
/* 41 */       case 4:  return FARTHEST; }
/* 42 */       return null;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     @Deprecated
/*    */     public byte getValue()
/*    */     {
/* 54 */       return this.value;
/*    */     }
/*    */   }
/*    */   
/*    */   public abstract void setScale(Scale paramScale);
/*    */   
/*    */   public abstract int getCenterX();
/*    */   
/*    */   public abstract int getCenterZ();
/*    */   
/*    */   public abstract void setCenterX(int paramInt);
/*    */   
/*    */   public abstract void setCenterZ(int paramInt);
/*    */   
/*    */   public abstract World getWorld();
/*    */   
/*    */   public abstract void setWorld(World paramWorld);
/*    */   
/*    */   public abstract java.util.List<MapRenderer> getRenderers();
/*    */   
/*    */   public abstract void addRenderer(MapRenderer paramMapRenderer);
/*    */   
/*    */   public abstract boolean removeRenderer(MapRenderer paramMapRenderer);
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\map\MapView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */