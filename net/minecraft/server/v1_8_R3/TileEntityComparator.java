/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class TileEntityComparator
/*    */   extends TileEntity
/*    */ {
/*    */   private int a;
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 10 */     super.b(paramNBTTagCompound);
/* 11 */     paramNBTTagCompound.setInt("OutputSignal", this.a);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 16 */     super.a(paramNBTTagCompound);
/* 17 */     this.a = paramNBTTagCompound.getInt("OutputSignal");
/*    */   }
/*    */   
/*    */   public int b() {
/* 21 */     return this.a;
/*    */   }
/*    */   
/*    */   public void a(int paramInt) {
/* 25 */     this.a = paramInt;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */