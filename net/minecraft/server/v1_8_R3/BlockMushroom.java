/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.bukkit.TreeType;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockMushroom extends BlockPlant implements IBlockFragilePlantElement
/*     */ {
/*     */   protected BlockMushroom()
/*     */   {
/*  15 */     float f = 0.2F;
/*     */     
/*  17 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
/*  18 */     a(true);
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  22 */     int sourceX = blockposition.getX();int sourceY = blockposition.getY();int sourceZ = blockposition.getZ();
/*  23 */     if (random.nextInt(Math.max(1, (int)world.growthOdds / world.spigotConfig.mushroomModifier * 25)) == 0) {
/*  24 */       int i = 5;
/*     */       
/*  26 */       Iterator iterator = BlockPosition.b(blockposition.a(-4, -1, -4), blockposition.a(4, 1, 4)).iterator();
/*     */       
/*  28 */       while (iterator.hasNext()) {
/*  29 */         BlockPosition blockposition1 = (BlockPosition)iterator.next();
/*     */         
/*  31 */         if (world.getType(blockposition1).getBlock() == this) {
/*  32 */           i--;
/*  33 */           if (i <= 0) {
/*  34 */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  39 */       BlockPosition blockposition2 = blockposition.a(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
/*     */       
/*  41 */       for (int j = 0; j < 4; j++) {
/*  42 */         if ((world.isEmpty(blockposition2)) && (f(world, blockposition2, getBlockData()))) {
/*  43 */           blockposition = blockposition2;
/*     */         }
/*     */         
/*  46 */         blockposition2 = blockposition.a(random.nextInt(3) - 1, random.nextInt(2) - random.nextInt(2), random.nextInt(3) - 1);
/*     */       }
/*     */       
/*  49 */       if ((world.isEmpty(blockposition2)) && (f(world, blockposition2, getBlockData())))
/*     */       {
/*     */ 
/*  52 */         org.bukkit.World bworld = world.getWorld();
/*  53 */         BlockState blockState = bworld.getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ()).getState();
/*  54 */         blockState.setType(org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getMaterial(this));
/*     */         
/*  56 */         BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(sourceX, sourceY, sourceZ), blockState);
/*  57 */         world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  59 */         if (!event.isCancelled()) {
/*  60 */           blockState.update(true);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canPlace(World world, BlockPosition blockposition)
/*     */   {
/*  69 */     return (super.canPlace(world, blockposition)) && (f(world, blockposition, getBlockData()));
/*     */   }
/*     */   
/*     */   protected boolean c(Block block) {
/*  73 */     return block.o();
/*     */   }
/*     */   
/*     */   public boolean f(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  77 */     if ((blockposition.getY() >= 0) && (blockposition.getY() < 256)) {
/*  78 */       IBlockData iblockdata1 = world.getType(blockposition.down());
/*     */       
/*  80 */       return iblockdata1.getBlock() == Blocks.MYCELIUM;
/*     */     }
/*  82 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/*  87 */     world.setAir(blockposition);
/*  88 */     WorldGenHugeMushroom worldgenhugemushroom = null;
/*     */     
/*  90 */     if (this == Blocks.BROWN_MUSHROOM) {
/*  91 */       BlockSapling.treeType = TreeType.BROWN_MUSHROOM;
/*  92 */       worldgenhugemushroom = new WorldGenHugeMushroom(Blocks.BROWN_MUSHROOM_BLOCK);
/*  93 */     } else if (this == Blocks.RED_MUSHROOM) {
/*  94 */       BlockSapling.treeType = TreeType.RED_MUSHROOM;
/*  95 */       worldgenhugemushroom = new WorldGenHugeMushroom(Blocks.RED_MUSHROOM_BLOCK);
/*     */     }
/*     */     
/*  98 */     if ((worldgenhugemushroom != null) && (worldgenhugemushroom.generate(world, random, blockposition))) {
/*  99 */       return true;
/*     */     }
/* 101 */     world.setTypeAndData(blockposition, iblockdata, 3);
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag)
/*     */   {
/* 107 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/* 111 */     return random.nextFloat() < 0.4D;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/* 115 */     d(world, blockposition, iblockdata, random);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockMushroom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */