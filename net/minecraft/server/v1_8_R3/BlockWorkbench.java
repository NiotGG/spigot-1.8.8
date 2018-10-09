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
/*    */ 
/*    */ public class BlockWorkbench
/*    */   extends Block
/*    */ {
/*    */   protected BlockWorkbench()
/*    */   {
/* 20 */     super(Material.WOOD);
/* 21 */     a(CreativeModeTab.c);
/*    */   }
/*    */   
/*    */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 26 */     if (paramWorld.isClientSide) {
/* 27 */       return true;
/*    */     }
/* 29 */     paramEntityHuman.openTileEntity(new TileEntityContainerWorkbench(paramWorld, paramBlockPosition));
/* 30 */     paramEntityHuman.b(StatisticList.Z);
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public static class TileEntityContainerWorkbench implements ITileEntityContainer
/*    */   {
/*    */     private final World a;
/*    */     private final BlockPosition b;
/*    */     
/*    */     public TileEntityContainerWorkbench(World paramWorld, BlockPosition paramBlockPosition) {
/* 40 */       this.a = paramWorld;
/* 41 */       this.b = paramBlockPosition;
/*    */     }
/*    */     
/*    */     public String getName()
/*    */     {
/* 46 */       return null;
/*    */     }
/*    */     
/*    */     public boolean hasCustomName()
/*    */     {
/* 51 */       return false;
/*    */     }
/*    */     
/*    */     public IChatBaseComponent getScoreboardDisplayName()
/*    */     {
/* 56 */       return new ChatMessage(Blocks.CRAFTING_TABLE.a() + ".name", new Object[0]);
/*    */     }
/*    */     
/*    */     public Container createContainer(PlayerInventory paramPlayerInventory, EntityHuman paramEntityHuman)
/*    */     {
/* 61 */       return new ContainerWorkbench(paramPlayerInventory, this.a, this.b);
/*    */     }
/*    */     
/*    */     public String getContainerName()
/*    */     {
/* 66 */       return "minecraft:crafting_table";
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockWorkbench.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */