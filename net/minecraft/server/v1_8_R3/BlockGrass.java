/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*     */ import org.bukkit.event.block.BlockFadeEvent;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockGrass
/*     */   extends Block implements IBlockFragilePlantElement
/*     */ {
/*  15 */   public static final BlockStateBoolean SNOWY = BlockStateBoolean.of("snowy");
/*     */   
/*     */   protected BlockGrass() {
/*  18 */     super(Material.GRASS);
/*  19 */     j(this.blockStateList.getBlockData().set(SNOWY, Boolean.valueOf(false)));
/*  20 */     a(true);
/*  21 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  25 */     Block block = iblockaccess.getType(blockposition.up()).getBlock();
/*     */     
/*  27 */     return iblockdata.set(SNOWY, Boolean.valueOf((block == Blocks.SNOW) || (block == Blocks.SNOW_LAYER)));
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  31 */     if (!world.isClientSide) {
/*  32 */       if ((world.getLightLevel(blockposition.up()) < 4) && (world.getType(blockposition.up()).getBlock().p() > 2))
/*     */       {
/*     */ 
/*  35 */         org.bukkit.World bworld = world.getWorld();
/*  36 */         BlockState blockState = bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()).getState();
/*  37 */         blockState.setType(CraftMagicNumbers.getMaterial(Blocks.DIRT));
/*     */         
/*  39 */         BlockFadeEvent event = new BlockFadeEvent(blockState.getBlock(), blockState);
/*  40 */         world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  42 */         if (!event.isCancelled()) {
/*  43 */           blockState.update(true);
/*     */         }
/*     */         
/*     */       }
/*  47 */       else if (world.getLightLevel(blockposition.up()) >= 9) {
/*  48 */         for (int i = 0; i < Math.min(4, Math.max(20, (int)(400.0F / world.growthOdds))); i++) {
/*  49 */           BlockPosition blockposition1 = blockposition.a(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
/*  50 */           Block block = world.getType(blockposition1.up()).getBlock();
/*  51 */           IBlockData iblockdata1 = world.getType(blockposition1);
/*     */           
/*  53 */           if ((iblockdata1.getBlock() == Blocks.DIRT) && (iblockdata1.get(BlockDirt.VARIANT) == BlockDirt.EnumDirtVariant.DIRT) && (world.getLightLevel(blockposition1.up()) >= 4) && (block.p() <= 2))
/*     */           {
/*     */ 
/*  56 */             org.bukkit.World bworld = world.getWorld();
/*  57 */             BlockState blockState = bworld.getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()).getState();
/*  58 */             blockState.setType(CraftMagicNumbers.getMaterial(Blocks.GRASS));
/*     */             
/*  60 */             BlockSpreadEvent event = new BlockSpreadEvent(blockState.getBlock(), bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), blockState);
/*  61 */             world.getServer().getPluginManager().callEvent(event);
/*     */             
/*  63 */             if (!event.isCancelled()) {
/*  64 */               blockState.update(true);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*     */   {
/*  76 */     return Blocks.DIRT.getDropType(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT), random, i);
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
/*  80 */     return true;
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/*  88 */     BlockPosition blockposition1 = blockposition.up();
/*  89 */     int i = 0;
/*     */     label305:
/*  91 */     while (i < 128) {
/*  92 */       BlockPosition blockposition2 = blockposition1;
/*  93 */       int j = 0;
/*     */       
/*     */ 
/*  96 */       while (j < i / 16) {
/*  97 */         blockposition2 = blockposition2.a(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
/*  98 */         if ((world.getType(blockposition2.down()).getBlock() != Blocks.GRASS) || (world.getType(blockposition2).getBlock().isOccluding())) break label305;
/*  99 */         j++;
/*     */       }
/*     */       
/* 102 */       if (world.getType(blockposition2).getBlock().material == Material.AIR) {
/* 103 */         if (random.nextInt(8) == 0) {
/* 104 */           BlockFlowers.EnumFlowerVarient blockflowers_enumflowervarient = world.getBiome(blockposition2).a(random, blockposition2);
/* 105 */           BlockFlowers blockflowers = blockflowers_enumflowervarient.a().a();
/* 106 */           IBlockData iblockdata1 = blockflowers.getBlockData().set(blockflowers.n(), blockflowers_enumflowervarient);
/*     */           
/* 108 */           if (blockflowers.f(world, blockposition2, iblockdata1))
/*     */           {
/* 110 */             CraftEventFactory.handleBlockGrowEvent(world, blockposition2.getX(), blockposition2.getY(), blockposition2.getZ(), iblockdata1.getBlock(), iblockdata1.getBlock().toLegacyData(iblockdata1));
/*     */           }
/*     */         } else {
/* 113 */           IBlockData iblockdata2 = Blocks.TALLGRASS.getBlockData().set(BlockLongGrass.TYPE, BlockLongGrass.EnumTallGrassType.GRASS);
/*     */           
/* 115 */           if (Blocks.TALLGRASS.f(world, blockposition2, iblockdata2))
/*     */           {
/* 117 */             CraftEventFactory.handleBlockGrowEvent(world, blockposition2.getX(), blockposition2.getY(), blockposition2.getZ(), iblockdata2.getBlock(), iblockdata2.getBlock().toLegacyData(iblockdata2));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 122 */       i++;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData iblockdata)
/*     */   {
/* 130 */     return 0;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 134 */     return new BlockStateList(this, new IBlockState[] { SNOWY });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockGrass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */