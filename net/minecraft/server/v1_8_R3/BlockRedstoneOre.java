/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ 
/*     */ public class BlockRedstoneOre extends Block
/*     */ {
/*     */   private final boolean a;
/*     */   
/*     */   public BlockRedstoneOre(boolean flag)
/*     */   {
/*  15 */     super(Material.STONE);
/*  16 */     if (flag) {
/*  17 */       a(true);
/*     */     }
/*     */     
/*  20 */     this.a = flag;
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  24 */     return 30;
/*     */   }
/*     */   
/*     */   public void attack(World world, BlockPosition blockposition, EntityHuman entityhuman) {
/*  28 */     e(world, blockposition, entityhuman);
/*  29 */     super.attack(world, blockposition, entityhuman);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(World world, BlockPosition blockposition, Entity entity)
/*     */   {
/*  36 */     if ((entity instanceof EntityHuman)) {
/*  37 */       PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, org.bukkit.event.block.Action.PHYSICAL, blockposition, null, null);
/*  38 */       if (!event.isCancelled()) {
/*  39 */         e(world, blockposition, entity);
/*  40 */         super.a(world, blockposition, entity);
/*     */       }
/*     */     } else {
/*  43 */       EntityInteractEvent event = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()));
/*  44 */       world.getServer().getPluginManager().callEvent(event);
/*  45 */       if (!event.isCancelled()) {
/*  46 */         e(world, blockposition, entity);
/*  47 */         super.a(world, blockposition, entity);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/*  54 */     e(world, blockposition, entityhuman);
/*  55 */     return super.interact(world, blockposition, iblockdata, entityhuman, enumdirection, f, f1, f2);
/*     */   }
/*     */   
/*     */   private void e(World world, BlockPosition blockposition, Entity entity) {
/*  59 */     f(world, blockposition);
/*  60 */     if (this == Blocks.REDSTONE_ORE)
/*     */     {
/*  62 */       if (CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.LIT_REDSTONE_ORE, 0).isCancelled()) {
/*  63 */         return;
/*     */       }
/*     */       
/*  66 */       world.setTypeUpdate(blockposition, Blocks.LIT_REDSTONE_ORE.getBlockData());
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/*  72 */     if (this == Blocks.LIT_REDSTONE_ORE)
/*     */     {
/*  74 */       if (CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), Blocks.REDSTONE_ORE).isCancelled()) {
/*  75 */         return;
/*     */       }
/*     */       
/*  78 */       world.setTypeUpdate(blockposition, Blocks.REDSTONE_ORE.getBlockData());
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*     */   {
/*  84 */     return Items.REDSTONE;
/*     */   }
/*     */   
/*     */   public int getDropCount(int i, Random random) {
/*  88 */     return a(random) + random.nextInt(i + 1);
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  92 */     return 4 + random.nextInt(2);
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
/*  96 */     super.dropNaturally(world, blockposition, iblockdata, f, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getExpDrop(World world, IBlockData data, int i)
/*     */   {
/* 108 */     if (getDropType(data, world.random, i) != Item.getItemOf(this)) {
/* 109 */       int j = 1 + world.random.nextInt(5);
/*     */       
/* 111 */       return j;
/*     */     }
/* 113 */     return 0;
/*     */   }
/*     */   
/*     */   private void f(World world, BlockPosition blockposition)
/*     */   {
/* 118 */     Random random = world.random;
/* 119 */     double d0 = 0.0625D;
/*     */     
/* 121 */     for (int i = 0; i < 6; i++) {
/* 122 */       double d1 = blockposition.getX() + random.nextFloat();
/* 123 */       double d2 = blockposition.getY() + random.nextFloat();
/* 124 */       double d3 = blockposition.getZ() + random.nextFloat();
/*     */       
/* 126 */       if ((i == 0) && (!world.getType(blockposition.up()).getBlock().c())) {
/* 127 */         d2 = blockposition.getY() + d0 + 1.0D;
/*     */       }
/*     */       
/* 130 */       if ((i == 1) && (!world.getType(blockposition.down()).getBlock().c())) {
/* 131 */         d2 = blockposition.getY() - d0;
/*     */       }
/*     */       
/* 134 */       if ((i == 2) && (!world.getType(blockposition.south()).getBlock().c())) {
/* 135 */         d3 = blockposition.getZ() + d0 + 1.0D;
/*     */       }
/*     */       
/* 138 */       if ((i == 3) && (!world.getType(blockposition.north()).getBlock().c())) {
/* 139 */         d3 = blockposition.getZ() - d0;
/*     */       }
/*     */       
/* 142 */       if ((i == 4) && (!world.getType(blockposition.east()).getBlock().c())) {
/* 143 */         d1 = blockposition.getX() + d0 + 1.0D;
/*     */       }
/*     */       
/* 146 */       if ((i == 5) && (!world.getType(blockposition.west()).getBlock().c())) {
/* 147 */         d1 = blockposition.getX() - d0;
/*     */       }
/*     */       
/* 150 */       if ((d1 < blockposition.getX()) || (d1 > blockposition.getX() + 1) || (d2 < 0.0D) || (d2 > blockposition.getY() + 1) || (d3 < blockposition.getZ()) || (d3 > blockposition.getZ() + 1)) {
/* 151 */         world.addParticle(EnumParticle.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected ItemStack i(IBlockData iblockdata)
/*     */   {
/* 158 */     return new ItemStack(Blocks.REDSTONE_ORE);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockRedstoneOre.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */