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
/*    */ 
/*    */ public class BlockSign
/*    */   extends BlockContainer
/*    */ {
/*    */   protected BlockSign()
/*    */   {
/* 21 */     super(Material.WOOD);
/* 22 */     float f1 = 0.25F;
/* 23 */     float f2 = 1.0F;
/* 24 */     a(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, f2, 0.5F + f1);
/*    */   }
/*    */   
/*    */ 
/*    */   public AxisAlignedBB a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 30 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean d()
/*    */   {
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   public boolean b(IBlockAccess paramIBlockAccess, BlockPosition paramBlockPosition)
/*    */   {
/* 46 */     return true;
/*    */   }
/*    */   
/*    */   public boolean c()
/*    */   {
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   public boolean g()
/*    */   {
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   public TileEntity a(World paramWorld, int paramInt)
/*    */   {
/* 61 */     return new TileEntitySign();
/*    */   }
/*    */   
/*    */ 
/*    */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*    */   {
/* 67 */     return Items.SIGN;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 77 */     if (paramWorld.isClientSide) {
/* 78 */       return true;
/*    */     }
/*    */     
/* 81 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 82 */     if ((localTileEntity instanceof TileEntitySign)) {
/* 83 */       return ((TileEntitySign)localTileEntity).b(paramEntityHuman);
/*    */     }
/*    */     
/* 86 */     return false;
/*    */   }
/*    */   
/*    */   public boolean canPlace(World paramWorld, BlockPosition paramBlockPosition)
/*    */   {
/* 91 */     return (!e(paramWorld, paramBlockPosition)) && (super.canPlace(paramWorld, paramBlockPosition));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */