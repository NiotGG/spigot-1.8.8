/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TileEntityContainer
/*    */   extends TileEntity
/*    */   implements ITileEntityContainer, ITileInventory
/*    */ {
/* 12 */   private ChestLock a = ChestLock.a;
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 16 */     super.a(paramNBTTagCompound);
/*    */     
/* 18 */     this.a = ChestLock.b(paramNBTTagCompound);
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 23 */     super.b(paramNBTTagCompound);
/*    */     
/* 25 */     if (this.a != null) {
/* 26 */       this.a.a(paramNBTTagCompound);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean r_()
/*    */   {
/* 32 */     return (this.a != null) && (!this.a.a());
/*    */   }
/*    */   
/*    */   public ChestLock i()
/*    */   {
/* 37 */     return this.a;
/*    */   }
/*    */   
/*    */   public void a(ChestLock paramChestLock)
/*    */   {
/* 42 */     this.a = paramChestLock;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IChatBaseComponent getScoreboardDisplayName()
/*    */   {
/* 52 */     if (hasCustomName()) {
/* 53 */       return new ChatComponentText(getName());
/*    */     }
/* 55 */     return new ChatMessage(getName(), new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */