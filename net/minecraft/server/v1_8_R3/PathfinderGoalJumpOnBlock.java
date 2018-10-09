/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalJumpOnBlock
/*    */   extends PathfinderGoalGotoTarget
/*    */ {
/*    */   private final EntityOcelot c;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public PathfinderGoalJumpOnBlock(EntityOcelot paramEntityOcelot, double paramDouble)
/*    */   {
/* 17 */     super(paramEntityOcelot, paramDouble, 8);
/* 18 */     this.c = paramEntityOcelot;
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 23 */     return (this.c.isTamed()) && (!this.c.isSitting()) && (super.a());
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 28 */     return super.b();
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 33 */     super.c();
/* 34 */     this.c.getGoalSit().setSitting(false);
/*    */   }
/*    */   
/*    */   public void d()
/*    */   {
/* 39 */     super.d();
/* 40 */     this.c.setSitting(false);
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 45 */     super.e();
/*    */     
/* 47 */     this.c.getGoalSit().setSitting(false);
/* 48 */     if (!f()) {
/* 49 */       this.c.setSitting(false);
/* 50 */     } else if (!this.c.isSitting()) {
/* 51 */       this.c.setSitting(true);
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean a(World paramWorld, BlockPosition paramBlockPosition)
/*    */   {
/* 57 */     if (!paramWorld.isEmpty(paramBlockPosition.up())) {
/* 58 */       return false;
/*    */     }
/*    */     
/* 61 */     IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/* 62 */     Block localBlock = localIBlockData.getBlock();
/*    */     
/* 64 */     if (localBlock == Blocks.CHEST) {
/* 65 */       TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 66 */       if (((localTileEntity instanceof TileEntityChest)) && (((TileEntityChest)localTileEntity).l < 1))
/* 67 */         return true;
/*    */     } else {
/* 69 */       if (localBlock == Blocks.LIT_FURNACE)
/* 70 */         return true;
/* 71 */       if ((localBlock == Blocks.BED) && (localIBlockData.get(BlockBed.PART) != BlockBed.EnumBedPart.HEAD)) {
/* 72 */         return true;
/*    */       }
/*    */     }
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalJumpOnBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */