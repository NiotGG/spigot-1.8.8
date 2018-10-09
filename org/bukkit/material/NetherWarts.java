/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.NetherWartsState;
/*    */ 
/*    */ public class NetherWarts
/*    */   extends MaterialData
/*    */ {
/*    */   public NetherWarts()
/*    */   {
/* 11 */     super(Material.NETHER_WARTS);
/*    */   }
/*    */   
/*    */   public NetherWarts(NetherWartsState state) {
/* 15 */     this();
/* 16 */     setState(state);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public NetherWarts(int type)
/*    */   {
/* 25 */     super(type);
/*    */   }
/*    */   
/*    */   public NetherWarts(Material type) {
/* 29 */     super(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public NetherWarts(int type, byte data)
/*    */   {
/* 39 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public NetherWarts(Material type, byte data)
/*    */   {
/* 49 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NetherWartsState getState()
/*    */   {
/* 58 */     switch (getData()) {
/*    */     case 0: 
/* 60 */       return NetherWartsState.SEEDED;
/*    */     case 1: 
/* 62 */       return NetherWartsState.STAGE_ONE;
/*    */     case 2: 
/* 64 */       return NetherWartsState.STAGE_TWO;
/*    */     }
/* 66 */     return NetherWartsState.RIPE;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setState(NetherWartsState state)
/*    */   {
/* 76 */     switch (state) {
/*    */     case RIPE: 
/* 78 */       setData((byte)0);
/* 79 */       return;
/*    */     case SEEDED: 
/* 81 */       setData((byte)1);
/* 82 */       return;
/*    */     case STAGE_ONE: 
/* 84 */       setData((byte)2);
/* 85 */       return;
/*    */     case STAGE_TWO: 
/* 87 */       setData((byte)3);
/* 88 */       return;
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 94 */     return getState() + " " + super.toString();
/*    */   }
/*    */   
/*    */   public NetherWarts clone()
/*    */   {
/* 99 */     return (NetherWarts)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\NetherWarts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */