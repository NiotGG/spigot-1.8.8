/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
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
/*    */ public class BlockDeadBush
/*    */   extends BlockPlant
/*    */ {
/*    */   protected BlockDeadBush()
/*    */   {
/* 20 */     super(Material.REPLACEABLE_PLANT);
/* 21 */     float f = 0.4F;
/* 22 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
/*    */   }
/*    */   
/*    */   public MaterialMapColor g(IBlockData paramIBlockData)
/*    */   {
/* 27 */     return MaterialMapColor.o;
/*    */   }
/*    */   
/*    */   protected boolean c(Block paramBlock)
/*    */   {
/* 32 */     return (paramBlock == Blocks.SAND) || (paramBlock == Blocks.HARDENED_CLAY) || (paramBlock == Blocks.STAINED_HARDENED_CLAY) || (paramBlock == Blocks.DIRT);
/*    */   }
/*    */   
/*    */   public boolean a(World paramWorld, BlockPosition paramBlockPosition)
/*    */   {
/* 37 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   public void a(World paramWorld, EntityHuman paramEntityHuman, BlockPosition paramBlockPosition, IBlockData paramIBlockData, TileEntity paramTileEntity)
/*    */   {
/* 48 */     if ((paramWorld.isClientSide) || (paramEntityHuman.bZ() == null) || (paramEntityHuman.bZ().getItem() != Items.SHEARS)) {
/* 49 */       super.a(paramWorld, paramEntityHuman, paramBlockPosition, paramIBlockData, paramTileEntity);
/*    */     } else {
/* 51 */       paramEntityHuman.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
/*    */       
/* 53 */       a(paramWorld, paramBlockPosition, new ItemStack(Blocks.DEADBUSH, 1, 0));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDeadBush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */