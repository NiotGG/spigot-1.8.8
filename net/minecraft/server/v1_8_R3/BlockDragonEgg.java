/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.block.BlockFromToEvent;
/*     */ 
/*     */ public class BlockDragonEgg extends Block
/*     */ {
/*     */   public BlockDragonEgg()
/*     */   {
/*  10 */     super(Material.DRAGON_EGG, MaterialMapColor.E);
/*  11 */     a(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  15 */     world.a(blockposition, this, a(world));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  19 */     world.a(blockposition, this, a(world));
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  23 */     e(world, blockposition);
/*     */   }
/*     */   
/*     */   private void e(World world, BlockPosition blockposition) {
/*  27 */     if ((BlockFalling.canFall(world, blockposition.down())) && (blockposition.getY() >= 0)) {
/*  28 */       byte b0 = 32;
/*     */       
/*  30 */       if ((!BlockFalling.instaFall) && (world.areChunksLoadedBetween(blockposition.a(-b0, -b0, -b0), blockposition.a(b0, b0, b0)))) {
/*  31 */         world.addEntity(new EntityFallingBlock(world, blockposition.getX() + 0.5F, blockposition.getY(), blockposition.getZ() + 0.5F, getBlockData()));
/*     */       } else {
/*  33 */         world.setAir(blockposition);
/*     */         
/*     */ 
/*     */ 
/*  37 */         for (BlockPosition blockposition1 = blockposition; (BlockFalling.canFall(world, blockposition1)) && (blockposition1.getY() > 0); blockposition1 = blockposition1.down()) {}
/*     */         
/*     */ 
/*     */ 
/*  41 */         if (blockposition1.getY() > 0) {
/*  42 */           world.setTypeAndData(blockposition1, getBlockData(), 2);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/*  50 */     f(world, blockposition);
/*  51 */     return true;
/*     */   }
/*     */   
/*     */   public void attack(World world, BlockPosition blockposition, EntityHuman entityhuman) {
/*  55 */     f(world, blockposition);
/*     */   }
/*     */   
/*     */   private void f(World world, BlockPosition blockposition) {
/*  59 */     IBlockData iblockdata = world.getType(blockposition);
/*     */     
/*  61 */     if (iblockdata.getBlock() == this) {
/*  62 */       for (int i = 0; i < 1000; i++) {
/*  63 */         BlockPosition blockposition1 = blockposition.a(world.random.nextInt(16) - world.random.nextInt(16), world.random.nextInt(8) - world.random.nextInt(8), world.random.nextInt(16) - world.random.nextInt(16));
/*     */         
/*  65 */         if (world.getType(blockposition1).getBlock().material == Material.AIR)
/*     */         {
/*  67 */           org.bukkit.block.Block from = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*  68 */           org.bukkit.block.Block to = world.getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
/*  69 */           BlockFromToEvent event = new BlockFromToEvent(from, to);
/*  70 */           org.bukkit.Bukkit.getPluginManager().callEvent(event);
/*     */           
/*  72 */           if (event.isCancelled()) {
/*  73 */             return;
/*     */           }
/*     */           
/*  76 */           blockposition1 = new BlockPosition(event.getToBlock().getX(), event.getToBlock().getY(), event.getToBlock().getZ());
/*     */           
/*  78 */           if (world.isClientSide) {
/*  79 */             for (int j = 0; j < 128; j++) {
/*  80 */               double d0 = world.random.nextDouble();
/*  81 */               float f = (world.random.nextFloat() - 0.5F) * 0.2F;
/*  82 */               float f1 = (world.random.nextFloat() - 0.5F) * 0.2F;
/*  83 */               float f2 = (world.random.nextFloat() - 0.5F) * 0.2F;
/*  84 */               double d1 = blockposition1.getX() + (blockposition.getX() - blockposition1.getX()) * d0 + (world.random.nextDouble() - 0.5D) * 1.0D + 0.5D;
/*  85 */               double d2 = blockposition1.getY() + (blockposition.getY() - blockposition1.getY()) * d0 + world.random.nextDouble() * 1.0D - 0.5D;
/*  86 */               double d3 = blockposition1.getZ() + (blockposition.getZ() - blockposition1.getZ()) * d0 + (world.random.nextDouble() - 0.5D) * 1.0D + 0.5D;
/*     */               
/*  88 */               world.addParticle(EnumParticle.PORTAL, d1, d2, d3, f, f1, f2, new int[0]);
/*     */             }
/*     */           } else {
/*  91 */             world.setTypeAndData(blockposition1, iblockdata, 2);
/*  92 */             world.setAir(blockposition);
/*     */           }
/*     */           
/*  95 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(World world)
/*     */   {
/* 103 */     return 5;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDragonEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */