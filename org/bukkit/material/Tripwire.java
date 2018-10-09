/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ 
/*    */ public class Tripwire
/*    */   extends MaterialData
/*    */ {
/*    */   public Tripwire()
/*    */   {
/* 11 */     super(Material.TRIPWIRE);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Tripwire(int type)
/*    */   {
/* 20 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public Tripwire(int type, byte data)
/*    */   {
/* 30 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isActivated()
/*    */   {
/* 39 */     return (getData() & 0x4) != 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setActivated(boolean act)
/*    */   {
/* 48 */     int dat = getData() & 0xB;
/* 49 */     if (act) {
/* 50 */       dat |= 0x4;
/*    */     }
/* 52 */     setData((byte)dat);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isObjectTriggering()
/*    */   {
/* 61 */     return (getData() & 0x1) != 0;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setObjectTriggering(boolean trig)
/*    */   {
/* 70 */     int dat = getData() & 0xE;
/* 71 */     if (trig) {
/* 72 */       dat |= 0x1;
/*    */     }
/* 74 */     setData((byte)dat);
/*    */   }
/*    */   
/*    */   public Tripwire clone()
/*    */   {
/* 79 */     return (Tripwire)super.clone();
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 84 */     return super.toString() + (isActivated() ? " Activated" : "") + (isObjectTriggering() ? " Triggered" : "");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\Tripwire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */