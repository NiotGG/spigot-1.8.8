/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityMinecartChest
/*    */   extends EntityMinecartContainer
/*    */ {
/*    */   public EntityMinecartChest(World paramWorld)
/*    */   {
/* 19 */     super(paramWorld);
/*    */   }
/*    */   
/*    */   public EntityMinecartChest(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/* 23 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*    */   }
/*    */   
/*    */   public void a(DamageSource paramDamageSource)
/*    */   {
/* 28 */     super.a(paramDamageSource);
/*    */     
/* 30 */     if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/* 31 */       a(Item.getItemOf(Blocks.CHEST), 1, 0.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   public int getSize()
/*    */   {
/* 37 */     return 27;
/*    */   }
/*    */   
/*    */   public EntityMinecartAbstract.EnumMinecartType s()
/*    */   {
/* 42 */     return EntityMinecartAbstract.EnumMinecartType.CHEST;
/*    */   }
/*    */   
/*    */   public IBlockData u()
/*    */   {
/* 47 */     return Blocks.CHEST.getBlockData().set(BlockChest.FACING, EnumDirection.NORTH);
/*    */   }
/*    */   
/*    */   public int w()
/*    */   {
/* 52 */     return 8;
/*    */   }
/*    */   
/*    */   public String getContainerName()
/*    */   {
/* 57 */     return "minecraft:chest";
/*    */   }
/*    */   
/*    */   public Container createContainer(PlayerInventory paramPlayerInventory, EntityHuman paramEntityHuman)
/*    */   {
/* 62 */     return new ContainerChest(paramPlayerInventory, this, paramEntityHuman);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMinecartChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */