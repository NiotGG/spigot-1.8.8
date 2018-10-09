/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityFlowerPot
/*    */   extends TileEntity
/*    */ {
/*    */   private Item a;
/*    */   
/*    */ 
/*    */   private int f;
/*    */   
/*    */ 
/*    */   public TileEntityFlowerPot() {}
/*    */   
/*    */ 
/*    */   public TileEntityFlowerPot(Item paramItem, int paramInt)
/*    */   {
/* 19 */     this.a = paramItem;
/* 20 */     this.f = paramInt;
/*    */   }
/*    */   
/*    */   public void b(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 25 */     super.b(paramNBTTagCompound);
/* 26 */     MinecraftKey localMinecraftKey = (MinecraftKey)Item.REGISTRY.c(this.a);
/* 27 */     paramNBTTagCompound.setString("Item", localMinecraftKey == null ? "" : localMinecraftKey.toString());
/* 28 */     paramNBTTagCompound.setInt("Data", this.f);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound paramNBTTagCompound)
/*    */   {
/* 33 */     super.a(paramNBTTagCompound);
/* 34 */     if (paramNBTTagCompound.hasKeyOfType("Item", 8)) {
/* 35 */       this.a = Item.d(paramNBTTagCompound.getString("Item"));
/*    */     }
/*    */     else {
/* 38 */       this.a = Item.getById(paramNBTTagCompound.getInt("Item"));
/*    */     }
/* 40 */     this.f = paramNBTTagCompound.getInt("Data");
/*    */   }
/*    */   
/*    */   public Packet getUpdatePacket()
/*    */   {
/* 45 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 46 */     b(localNBTTagCompound);
/*    */     
/* 48 */     localNBTTagCompound.remove("Item");
/* 49 */     localNBTTagCompound.setInt("Item", Item.getId(this.a));
/* 50 */     return new PacketPlayOutTileEntityData(this.position, 5, localNBTTagCompound);
/*    */   }
/*    */   
/*    */   public void a(Item paramItem, int paramInt) {
/* 54 */     this.a = paramItem;
/* 55 */     this.f = paramInt;
/*    */   }
/*    */   
/*    */   public Item b() {
/* 59 */     return this.a;
/*    */   }
/*    */   
/*    */   public int c() {
/* 63 */     return this.f;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityFlowerPot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */