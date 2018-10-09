/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SourceBlock
/*    */   implements ISourceBlock
/*    */ {
/*    */   private final World a;
/*    */   
/*    */   private final BlockPosition b;
/*    */   
/*    */ 
/*    */   public SourceBlock(World paramWorld, BlockPosition paramBlockPosition)
/*    */   {
/* 15 */     this.a = paramWorld;
/* 16 */     this.b = paramBlockPosition;
/*    */   }
/*    */   
/*    */   public World getWorld()
/*    */   {
/* 21 */     return this.a;
/*    */   }
/*    */   
/*    */   public double getX()
/*    */   {
/* 26 */     return this.b.getX() + 0.5D;
/*    */   }
/*    */   
/*    */   public double getY()
/*    */   {
/* 31 */     return this.b.getY() + 0.5D;
/*    */   }
/*    */   
/*    */   public double getZ()
/*    */   {
/* 36 */     return this.b.getZ() + 0.5D;
/*    */   }
/*    */   
/*    */   public BlockPosition getBlockPosition()
/*    */   {
/* 41 */     return this.b;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int f()
/*    */   {
/* 51 */     IBlockData localIBlockData = this.a.getType(this.b);
/* 52 */     return localIBlockData.getBlock().toLegacyData(localIBlockData);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public <T extends TileEntity> T getTileEntity()
/*    */   {
/* 63 */     return this.a.getTileEntity(this.b);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\SourceBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */