/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class PoweredRail
/*    */   extends ExtendedRails implements Redstone
/*    */ {
/*    */   public PoweredRail()
/*    */   {
/* 10 */     super(Material.POWERED_RAIL);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public PoweredRail(int type)
/*    */   {
/* 19 */     super(type);
/*    */   }
/*    */   
/*    */   public PoweredRail(Material type) {
/* 23 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public PoweredRail(int type, byte data)
/*    */   {
/* 33 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public PoweredRail(Material type, byte data)
/*    */   {
/* 43 */     super(type, data);
/*    */   }
/*    */   
/*    */   public boolean isPowered() {
/* 47 */     return (getData() & 0x8) == 8;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setPowered(boolean isPowered)
/*    */   {
/* 56 */     setData((byte)(isPowered ? getData() | 0x8 : getData() & 0xFFFFFFF7));
/*    */   }
/*    */   
/*    */   public PoweredRail clone()
/*    */   {
/* 61 */     return (PoweredRail)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\PoweredRail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */