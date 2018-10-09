/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class DetectorRail
/*    */   extends ExtendedRails implements PressureSensor
/*    */ {
/*    */   public DetectorRail()
/*    */   {
/* 10 */     super(Material.DETECTOR_RAIL);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public DetectorRail(int type)
/*    */   {
/* 19 */     super(type);
/*    */   }
/*    */   
/*    */   public DetectorRail(Material type) {
/* 23 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public DetectorRail(int type, byte data)
/*    */   {
/* 33 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public DetectorRail(Material type, byte data)
/*    */   {
/* 43 */     super(type, data);
/*    */   }
/*    */   
/*    */   public boolean isPressed() {
/* 47 */     return (getData() & 0x8) == 8;
/*    */   }
/*    */   
/*    */   public void setPressed(boolean isPressed) {
/* 51 */     setData((byte)(isPressed ? getData() | 0x8 : getData() & 0xFFFFFFF7));
/*    */   }
/*    */   
/*    */   public DetectorRail clone()
/*    */   {
/* 56 */     return (DetectorRail)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\DetectorRail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */