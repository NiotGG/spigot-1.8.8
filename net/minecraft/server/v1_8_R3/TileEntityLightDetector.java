/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityLightDetector
/*    */   extends TileEntity
/*    */   implements IUpdatePlayerListBox
/*    */ {
/*    */   public void c()
/*    */   {
/* 13 */     if ((this.world != null) && (!this.world.isClientSide) && (this.world.getTime() % 20L == 0L)) {
/* 14 */       this.e = w();
/* 15 */       if ((this.e instanceof BlockDaylightDetector)) {
/* 16 */         ((BlockDaylightDetector)this.e).f(this.world, this.position);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityLightDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */