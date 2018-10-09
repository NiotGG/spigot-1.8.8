/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.spigotmc.SpigotWorldConfig;
/*     */ 
/*     */ public class BlockStem extends BlockPlant implements IBlockFragilePlantElement
/*     */ {
/*  11 */   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 7);
/*  12 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", new Predicate() {
/*     */     public boolean a(EnumDirection enumdirection) {
/*  14 */       return enumdirection != EnumDirection.DOWN;
/*     */     }
/*     */     
/*     */     public boolean apply(Object object) {
/*  18 */       return a((EnumDirection)object);
/*     */     }
/*  12 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Block blockFruit;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BlockStem(Block block)
/*     */   {
/*  24 */     j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)).set(FACING, EnumDirection.UP));
/*  25 */     this.blockFruit = block;
/*  26 */     a(true);
/*  27 */     float f = 0.125F;
/*     */     
/*  29 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*  30 */     a(null);
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  34 */     iblockdata = iblockdata.set(FACING, EnumDirection.UP);
/*  35 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/*  37 */     while (iterator.hasNext()) {
/*  38 */       EnumDirection enumdirection = (EnumDirection)iterator.next();
/*     */       
/*  40 */       if (iblockaccess.getType(blockposition.shift(enumdirection)).getBlock() == this.blockFruit) {
/*  41 */         iblockdata = iblockdata.set(FACING, enumdirection);
/*  42 */         break;
/*     */       }
/*     */     }
/*     */     
/*  46 */     return iblockdata;
/*     */   }
/*     */   
/*     */   protected boolean c(Block block) {
/*  50 */     return block == Blocks.FARMLAND;
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  54 */     super.b(world, blockposition, iblockdata, random);
/*  55 */     if (world.getLightLevel(blockposition.up()) >= 9) {
/*  56 */       float f = BlockCrops.a(this, world, blockposition);
/*     */       
/*  58 */       if (random.nextInt((int)(world.growthOdds / (this == Blocks.PUMPKIN_STEM ? world.spigotConfig.pumpkinModifier : world.spigotConfig.melonModifier) * (25.0F / f)) + 1) == 0) {
/*  59 */         int i = ((Integer)iblockdata.get(AGE)).intValue();
/*     */         
/*  61 */         if (i < 7) {
/*  62 */           iblockdata = iblockdata.set(AGE, Integer.valueOf(i + 1));
/*     */           
/*  64 */           CraftEventFactory.handleBlockGrowEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this, toLegacyData(iblockdata));
/*     */         } else {
/*  66 */           Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */           
/*  68 */           while (iterator.hasNext()) {
/*  69 */             EnumDirection enumdirection = (EnumDirection)iterator.next();
/*     */             
/*  71 */             if (world.getType(blockposition.shift(enumdirection)).getBlock() == this.blockFruit) {
/*  72 */               return;
/*     */             }
/*     */           }
/*     */           
/*  76 */           blockposition = blockposition.shift(EnumDirection.EnumDirectionLimit.HORIZONTAL.a(random));
/*  77 */           Block block = world.getType(blockposition.down()).getBlock();
/*     */           
/*  79 */           if ((world.getType(blockposition).getBlock().material == Material.AIR) && ((block == Blocks.FARMLAND) || (block == Blocks.DIRT) || (block == Blocks.GRASS)))
/*     */           {
/*  81 */             CraftEventFactory.handleBlockGrowEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this.blockFruit, 0);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void g(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  90 */     int i = ((Integer)iblockdata.get(AGE)).intValue() + MathHelper.nextInt(world.random, 2, 5);
/*     */     
/*     */ 
/*  93 */     CraftEventFactory.handleBlockGrowEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this, Math.min(7, i));
/*     */   }
/*     */   
/*     */   public void j() {
/*  97 */     float f = 0.125F;
/*     */     
/*  99 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 103 */     this.maxY = ((((Integer)iblockaccess.getType(blockposition).get(AGE)).intValue() * 2 + 2) / 16.0F);
/* 104 */     float f = 0.125F;
/*     */     
/* 106 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float)this.maxY, 0.5F + f);
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
/* 110 */     super.dropNaturally(world, blockposition, iblockdata, f, i);
/* 111 */     if (!world.isClientSide) {
/* 112 */       Item item = l();
/*     */       
/* 114 */       if (item != null) {
/* 115 */         int j = ((Integer)iblockdata.get(AGE)).intValue();
/*     */         
/* 117 */         for (int k = 0; k < 3; k++) {
/* 118 */           if (world.random.nextInt(15) <= j) {
/* 119 */             a(world, blockposition, new ItemStack(item));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected Item l()
/*     */   {
/* 128 */     return this.blockFruit == Blocks.MELON_BLOCK ? Items.MELON_SEEDS : this.blockFruit == Blocks.PUMPKIN ? Items.PUMPKIN_SEEDS : null;
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/* 132 */     return null;
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
/* 136 */     return ((Integer)iblockdata.get(AGE)).intValue() != 7;
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/* 140 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/* 144 */     g(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 148 */     return getBlockData().set(AGE, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 152 */     return ((Integer)iblockdata.get(AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 156 */     return new BlockStateList(this, new IBlockState[] { AGE, FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */