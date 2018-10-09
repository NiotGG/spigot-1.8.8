/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockFalling
/*    */   extends Block
/*    */ {
/*    */   public static boolean instaFall;
/*    */   
/*    */   public BlockFalling()
/*    */   {
/* 16 */     super(Material.SAND);
/* 17 */     a(CreativeModeTab.b);
/*    */   }
/*    */   
/*    */   public BlockFalling(Material paramMaterial) {
/* 21 */     super(paramMaterial);
/*    */   }
/*    */   
/*    */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*    */   {
/* 26 */     paramWorld.a(paramBlockPosition, this, a(paramWorld));
/*    */   }
/*    */   
/*    */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*    */   {
/* 31 */     paramWorld.a(paramBlockPosition, this, a(paramWorld));
/*    */   }
/*    */   
/*    */   public void b(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Random paramRandom)
/*    */   {
/* 36 */     if (!paramWorld.isClientSide) {
/* 37 */       f(paramWorld, paramBlockPosition);
/*    */     }
/*    */   }
/*    */   
/*    */   private void f(World paramWorld, BlockPosition paramBlockPosition) {
/* 42 */     if ((!canFall(paramWorld, paramBlockPosition.down())) || (paramBlockPosition.getY() < 0)) {
/* 43 */       return;
/*    */     }
/*    */     
/* 46 */     int i = 32;
/* 47 */     Object localObject; if ((instaFall) || (!paramWorld.areChunksLoadedBetween(paramBlockPosition.a(-i, -i, -i), paramBlockPosition.a(i, i, i)))) {
/* 48 */       paramWorld.setAir(paramBlockPosition);
/*    */       
/* 50 */       localObject = paramBlockPosition.down();
/* 51 */       while ((canFall(paramWorld, (BlockPosition)localObject)) && (((BlockPosition)localObject).getY() > 0)) {
/* 52 */         localObject = ((BlockPosition)localObject).down();
/*    */       }
/* 54 */       if (((BlockPosition)localObject).getY() > 0)
/*    */       {
/* 56 */         paramWorld.setTypeUpdate(((BlockPosition)localObject).up(), getBlockData());
/*    */       }
/* 58 */     } else if (!paramWorld.isClientSide) {
/* 59 */       localObject = new EntityFallingBlock(paramWorld, paramBlockPosition.getX() + 0.5D, paramBlockPosition.getY(), paramBlockPosition.getZ() + 0.5D, paramWorld.getType(paramBlockPosition));
/* 60 */       a((EntityFallingBlock)localObject);
/* 61 */       paramWorld.addEntity((Entity)localObject);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   protected void a(EntityFallingBlock paramEntityFallingBlock) {}
/*    */   
/*    */   public int a(World paramWorld)
/*    */   {
/* 70 */     return 2;
/*    */   }
/*    */   
/*    */   public static boolean canFall(World paramWorld, BlockPosition paramBlockPosition)
/*    */   {
/* 75 */     Block localBlock = paramWorld.getType(paramBlockPosition).getBlock();
/* 76 */     Material localMaterial = localBlock.material;
/* 77 */     return (localBlock == Blocks.FIRE) || (localMaterial == Material.AIR) || (localMaterial == Material.WATER) || (localMaterial == Material.LAVA);
/*    */   }
/*    */   
/*    */   public void a_(World paramWorld, BlockPosition paramBlockPosition) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFalling.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */