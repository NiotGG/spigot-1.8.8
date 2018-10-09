/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.entity.EntityInteractEvent;
/*     */ 
/*     */ public class BlockSoil extends Block
/*     */ {
/*  13 */   public static final BlockStateInteger MOISTURE = BlockStateInteger.of("moisture", 0, 7);
/*     */   
/*     */   protected BlockSoil() {
/*  16 */     super(Material.EARTH);
/*  17 */     j(this.blockStateList.getBlockData().set(MOISTURE, Integer.valueOf(0)));
/*  18 */     a(true);
/*  19 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
/*  20 */     e(255);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  24 */     return new AxisAlignedBB(blockposition.getX(), blockposition.getY(), blockposition.getZ(), blockposition.getX() + 1, blockposition.getY() + 1, blockposition.getZ() + 1);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  28 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  32 */     return false;
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  36 */     int i = ((Integer)iblockdata.get(MOISTURE)).intValue();
/*     */     
/*  38 */     if ((!f(world, blockposition)) && (!world.isRainingAt(blockposition.up()))) {
/*  39 */       if (i > 0) {
/*  40 */         world.setTypeAndData(blockposition, iblockdata.set(MOISTURE, Integer.valueOf(i - 1)), 2);
/*  41 */       } else if (!e(world, blockposition))
/*     */       {
/*  43 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*  44 */         if (CraftEventFactory.callBlockFadeEvent(block, Blocks.DIRT).isCancelled()) {
/*  45 */           return;
/*     */         }
/*     */         
/*  48 */         world.setTypeUpdate(blockposition, Blocks.DIRT.getBlockData());
/*     */       }
/*  50 */     } else if (i < 7) {
/*  51 */       world.setTypeAndData(blockposition, iblockdata.set(MOISTURE, Integer.valueOf(7)), 2);
/*     */     }
/*     */   }
/*     */   
/*     */   public void fallOn(World world, BlockPosition blockposition, Entity entity, float f)
/*     */   {
/*  57 */     super.fallOn(world, blockposition, entity, f);
/*  58 */     if (((entity instanceof EntityLiving)) && 
/*  59 */       (!world.isClientSide) && (world.random.nextFloat() < f - 0.5F)) {
/*  60 */       if ((!(entity instanceof EntityHuman)) && (!world.getGameRules().getBoolean("mobGriefing"))) {
/*     */         return;
/*     */       }
/*     */       
/*     */       Cancellable cancellable;
/*     */       Cancellable cancellable;
/*  66 */       if ((entity instanceof EntityHuman)) {
/*  67 */         cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, org.bukkit.event.block.Action.PHYSICAL, blockposition, null, null);
/*     */       } else {
/*  69 */         cancellable = new EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()));
/*  70 */         world.getServer().getPluginManager().callEvent((EntityInteractEvent)cancellable);
/*     */       }
/*     */       
/*  73 */       if (cancellable.isCancelled()) {
/*  74 */         return;
/*     */       }
/*     */       
/*  77 */       if (CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.DIRT, 0).isCancelled()) {
/*  78 */         return;
/*     */       }
/*     */       
/*     */ 
/*  82 */       world.setTypeUpdate(blockposition, Blocks.DIRT.getBlockData());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean e(World world, BlockPosition blockposition)
/*     */   {
/*  90 */     Block block = world.getType(blockposition.up()).getBlock();
/*     */     
/*  92 */     return ((block instanceof BlockCrops)) || ((block instanceof BlockStem));
/*     */   }
/*     */   
/*     */   private boolean f(World world, BlockPosition blockposition) {
/*  96 */     Iterator iterator = BlockPosition.b(blockposition.a(-4, 0, -4), blockposition.a(4, 1, 4)).iterator();
/*     */     
/*     */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition;
/*     */     do
/*     */     {
/* 101 */       if (!iterator.hasNext()) {
/* 102 */         return false;
/*     */       }
/*     */       
/* 105 */       blockposition_mutableblockposition = (BlockPosition.MutableBlockPosition)iterator.next();
/* 106 */     } while (world.getType(blockposition_mutableblockposition).getBlock().getMaterial() != Material.WATER);
/*     */     
/* 108 */     return true;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/* 112 */     super.doPhysics(world, blockposition, iblockdata, block);
/* 113 */     if (world.getType(blockposition.up()).getBlock().getMaterial().isBuildable()) {
/* 114 */       world.setTypeUpdate(blockposition, Blocks.DIRT.getBlockData());
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*     */   {
/* 120 */     return Blocks.DIRT.getDropType(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT), random, i);
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 124 */     return getBlockData().set(MOISTURE, Integer.valueOf(i & 0x7));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 128 */     return ((Integer)iblockdata.get(MOISTURE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 132 */     return new BlockStateList(this, new IBlockState[] { MOISTURE });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSoil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */