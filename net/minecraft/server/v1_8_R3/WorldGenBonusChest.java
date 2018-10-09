/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenBonusChest
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final List<StructurePieceTreasure> a;
/*    */   private final int b;
/*    */   
/*    */   public WorldGenBonusChest(List<StructurePieceTreasure> paramList, int paramInt)
/*    */   {
/* 20 */     this.a = paramList;
/* 21 */     this.b = paramInt;
/*    */   }
/*    */   
/*    */   public boolean generate(World paramWorld, Random paramRandom, BlockPosition paramBlockPosition)
/*    */   {
/*    */     Block localBlock;
/* 27 */     while ((((localBlock = paramWorld.getType(paramBlockPosition).getBlock()).getMaterial() == Material.AIR) || (localBlock.getMaterial() == Material.LEAVES)) && (paramBlockPosition.getY() > 1)) {
/* 28 */       paramBlockPosition = paramBlockPosition.down();
/*    */     }
/*    */     
/* 31 */     if (paramBlockPosition.getY() < 1) {
/* 32 */       return false;
/*    */     }
/* 34 */     paramBlockPosition = paramBlockPosition.up();
/*    */     
/* 36 */     for (int i = 0; i < 4; i++) {
/* 37 */       BlockPosition localBlockPosition1 = paramBlockPosition.a(paramRandom.nextInt(4) - paramRandom.nextInt(4), paramRandom.nextInt(3) - paramRandom.nextInt(3), paramRandom.nextInt(4) - paramRandom.nextInt(4));
/*    */       
/* 39 */       if ((paramWorld.isEmpty(localBlockPosition1)) && (World.a(paramWorld, localBlockPosition1.down()))) {
/* 40 */         paramWorld.setTypeAndData(localBlockPosition1, Blocks.CHEST.getBlockData(), 2);
/*    */         
/* 42 */         TileEntity localTileEntity = paramWorld.getTileEntity(localBlockPosition1);
/* 43 */         if ((localTileEntity instanceof TileEntityChest)) {
/* 44 */           StructurePieceTreasure.a(paramRandom, this.a, (TileEntityChest)localTileEntity, this.b);
/*    */         }
/*    */         
/* 47 */         BlockPosition localBlockPosition2 = localBlockPosition1.east();
/* 48 */         BlockPosition localBlockPosition3 = localBlockPosition1.west();
/* 49 */         BlockPosition localBlockPosition4 = localBlockPosition1.north();
/* 50 */         BlockPosition localBlockPosition5 = localBlockPosition1.south();
/* 51 */         if ((paramWorld.isEmpty(localBlockPosition3)) && (World.a(paramWorld, localBlockPosition3.down()))) {
/* 52 */           paramWorld.setTypeAndData(localBlockPosition3, Blocks.TORCH.getBlockData(), 2);
/*    */         }
/* 54 */         if ((paramWorld.isEmpty(localBlockPosition2)) && (World.a(paramWorld, localBlockPosition2.down()))) {
/* 55 */           paramWorld.setTypeAndData(localBlockPosition2, Blocks.TORCH.getBlockData(), 2);
/*    */         }
/* 57 */         if ((paramWorld.isEmpty(localBlockPosition4)) && (World.a(paramWorld, localBlockPosition4.down()))) {
/* 58 */           paramWorld.setTypeAndData(localBlockPosition4, Blocks.TORCH.getBlockData(), 2);
/*    */         }
/* 60 */         if ((paramWorld.isEmpty(localBlockPosition5)) && (World.a(paramWorld, localBlockPosition5.down()))) {
/* 61 */           paramWorld.setTypeAndData(localBlockPosition5, Blocks.TORCH.getBlockData(), 2);
/*    */         }
/* 63 */         return true;
/*    */       }
/*    */     }
/*    */     
/* 67 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenBonusChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */