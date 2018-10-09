/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.util.concurrent.ListeningExecutorService;
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
/*    */ 
/*    */ public class BlockBeacon
/*    */   extends BlockContainer
/*    */ {
/*    */   public BlockBeacon()
/*    */   {
/* 23 */     super(Material.SHATTERABLE, MaterialMapColor.G);
/* 24 */     c(3.0F);
/* 25 */     a(CreativeModeTab.f);
/*    */   }
/*    */   
/*    */   public TileEntity a(World paramWorld, int paramInt)
/*    */   {
/* 30 */     return new TileEntityBeacon();
/*    */   }
/*    */   
/*    */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 35 */     if (paramWorld.isClientSide) {
/* 36 */       return true;
/*    */     }
/*    */     
/* 39 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 40 */     if ((localTileEntity instanceof TileEntityBeacon)) {
/* 41 */       paramEntityHuman.openContainer((TileEntityBeacon)localTileEntity);
/* 42 */       paramEntityHuman.b(StatisticList.N);
/*    */     }
/*    */     
/* 45 */     return true;
/*    */   }
/*    */   
/*    */   public boolean c()
/*    */   {
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public boolean d()
/*    */   {
/* 55 */     return false;
/*    */   }
/*    */   
/*    */   public int b()
/*    */   {
/* 60 */     return 3;
/*    */   }
/*    */   
/*    */   public void postPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityLiving paramEntityLiving, ItemStack paramItemStack)
/*    */   {
/* 65 */     super.postPlace(paramWorld, paramBlockPosition, paramIBlockData, paramEntityLiving, paramItemStack);
/*    */     
/* 67 */     if (paramItemStack.hasName()) {
/* 68 */       TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 69 */       if ((localTileEntity instanceof TileEntityBeacon)) {
/* 70 */         ((TileEntityBeacon)localTileEntity).a(paramItemStack.getName());
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*    */   {
/* 77 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 78 */     if ((localTileEntity instanceof TileEntityBeacon)) {
/* 79 */       ((TileEntityBeacon)localTileEntity).m();
/* 80 */       paramWorld.playBlockAction(paramBlockPosition, this, 1, 0);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void f(World paramWorld, final BlockPosition paramBlockPosition)
/*    */   {
/* 90 */     HttpUtilities.a.submit(new Runnable()
/*    */     {
/*    */       public void run() {
/* 93 */         Chunk localChunk = this.a.getChunkAtWorldCoords(paramBlockPosition);
/* 94 */         for (int i = paramBlockPosition.getY() - 1; i >= 0; i--) {
/* 95 */           final BlockPosition localBlockPosition = new BlockPosition(paramBlockPosition.getX(), i, paramBlockPosition.getZ());
/* 96 */           if (!localChunk.d(localBlockPosition)) break;
/* 97 */           IBlockData localIBlockData = this.a.getType(localBlockPosition);
/* 98 */           if (localIBlockData.getBlock() == Blocks.BEACON) {
/* 99 */             ((WorldServer)this.a).postToMainThread(new Runnable()
/*    */             {
/*    */               public void run() {
/* :2 */                 TileEntity localTileEntity = BlockBeacon.1.this.a.getTileEntity(localBlockPosition);
/* :3 */                 if ((localTileEntity instanceof TileEntityBeacon)) {
/* :4 */                   ((TileEntityBeacon)localTileEntity).m();
/* :5 */                   BlockBeacon.1.this.a.playBlockAction(localBlockPosition, Blocks.BEACON, 1, 0);
/*    */                 }
/*    */               }
/*    */             });
/*    */           }
/*    */         }
/*    */       }
/*    */     });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */