/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*    */ 
/*    */ public class BlockStationary extends BlockFluids
/*    */ {
/*    */   protected BlockStationary(Material material)
/*    */   {
/* 10 */     super(material);
/* 11 */     a(false);
/* 12 */     if (material == Material.LAVA) {
/* 13 */       a(true);
/*    */     }
/*    */   }
/*    */   
/*    */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*    */   {
/* 19 */     if (!e(world, blockposition, iblockdata)) {
/* 20 */       f(world, blockposition, iblockdata);
/*    */     }
/*    */   }
/*    */   
/*    */   private void f(World world, BlockPosition blockposition, IBlockData iblockdata)
/*    */   {
/* 26 */     BlockFlowing blockflowing = a(this.material);
/*    */     
/* 28 */     world.setTypeAndData(blockposition, blockflowing.getBlockData().set(LEVEL, (Integer)iblockdata.get(LEVEL)), 2);
/* 29 */     world.a(blockposition, blockflowing, a(world));
/*    */   }
/*    */   
/*    */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/* 33 */     if ((this.material == Material.LAVA) && 
/* 34 */       (world.getGameRules().getBoolean("doFireTick"))) {
/* 35 */       int i = random.nextInt(3);
/*    */       
/* 37 */       if (i > 0) {
/* 38 */         BlockPosition blockposition1 = blockposition;
/*    */         
/* 40 */         for (int j = 0; j < i; j++) {
/* 41 */           blockposition1 = blockposition1.a(random.nextInt(3) - 1, 1, random.nextInt(3) - 1);
/* 42 */           Block block = world.getType(blockposition1).getBlock();
/*    */           
/* 44 */           if (block.material == Material.AIR) {
/* 45 */             if (f(world, blockposition1))
/*    */             {
/* 47 */               if ((world.getType(blockposition1) == Blocks.FIRE) || 
/* 48 */                 (!CraftEventFactory.callBlockIgniteEvent(world, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), blockposition.getX(), blockposition.getY(), blockposition.getZ()).isCancelled()))
/*    */               {
/*    */ 
/*    */ 
/*    */ 
/* 53 */                 world.setTypeUpdate(blockposition1, Blocks.FIRE.getBlockData());
/*    */               }
/*    */             }
/* 56 */           } else if (block.material.isSolid()) {
/* 57 */             return;
/*    */           }
/*    */         }
/*    */       } else {
/* 61 */         for (int k = 0; k < 3; k++) {
/* 62 */           BlockPosition blockposition2 = blockposition.a(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
/*    */           
/* 64 */           if ((world.isEmpty(blockposition2.up())) && (m(world, blockposition2)))
/*    */           {
/* 66 */             BlockPosition up = blockposition2.up();
/* 67 */             if ((world.getType(up) == Blocks.FIRE) || 
/* 68 */               (!CraftEventFactory.callBlockIgniteEvent(world, up.getX(), up.getY(), up.getZ(), blockposition.getX(), blockposition.getY(), blockposition.getZ()).isCancelled()))
/*    */             {
/*    */ 
/*    */ 
/*    */ 
/* 73 */               world.setTypeUpdate(blockposition2.up(), Blocks.FIRE.getBlockData());
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean f(World world, BlockPosition blockposition)
/*    */   {
/* 83 */     EnumDirection[] aenumdirection = EnumDirection.values();
/* 84 */     int i = aenumdirection.length;
/*    */     
/* 86 */     for (int j = 0; j < i; j++) {
/* 87 */       EnumDirection enumdirection = aenumdirection[j];
/*    */       
/* 89 */       if (m(world, blockposition.shift(enumdirection))) {
/* 90 */         return true;
/*    */       }
/*    */     }
/*    */     
/* 94 */     return false;
/*    */   }
/*    */   
/*    */   private boolean m(World world, BlockPosition blockposition) {
/* 98 */     return world.getType(blockposition).getBlock().getMaterial().isBurnable();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStationary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */