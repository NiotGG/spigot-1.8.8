/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockReed extends Block
/*     */ {
/*   8 */   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 15);
/*     */   
/*     */   protected BlockReed() {
/*  11 */     super(Material.PLANT);
/*  12 */     j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)));
/*  13 */     float f = 0.375F;
/*     */     
/*  15 */     a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/*  16 */     a(true);
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  20 */     if (((world.getType(blockposition.down()).getBlock() == Blocks.REEDS) || (e(world, blockposition, iblockdata))) && 
/*  21 */       (world.isEmpty(blockposition.up())))
/*     */     {
/*     */ 
/*  24 */       for (int i = 1; world.getType(blockposition.down(i)).getBlock() == this; i++) {}
/*     */       
/*     */ 
/*     */ 
/*  28 */       if (i < 3) {
/*  29 */         int j = ((Integer)iblockdata.get(AGE)).intValue();
/*     */         
/*  31 */         if (j >= (byte)(int)range(3.0F, world.growthOdds / world.spigotConfig.caneModifier * 15.0F + 0.5F, 15.0F))
/*     */         {
/*     */ 
/*  34 */           BlockPosition upPos = blockposition.up();
/*  35 */           org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.handleBlockGrowEvent(world, upPos.getX(), upPos.getY(), upPos.getZ(), this, 0);
/*  36 */           world.setTypeAndData(blockposition, iblockdata.set(AGE, Integer.valueOf(0)), 4);
/*     */         }
/*     */         else {
/*  39 */           world.setTypeAndData(blockposition, iblockdata.set(AGE, Integer.valueOf(j + 1)), 4);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean canPlace(World world, BlockPosition blockposition)
/*     */   {
/*  48 */     Block block = world.getType(blockposition.down()).getBlock();
/*     */     
/*  50 */     if (block == this)
/*  51 */       return true;
/*  52 */     if ((block != Blocks.GRASS) && (block != Blocks.DIRT) && (block != Blocks.SAND)) {
/*  53 */       return false;
/*     */     }
/*  55 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/*     */     EnumDirection enumdirection;
/*     */     do
/*     */     {
/*  60 */       if (!iterator.hasNext()) {
/*  61 */         return false;
/*     */       }
/*     */       
/*  64 */       enumdirection = (EnumDirection)iterator.next();
/*  65 */     } while (world.getType(blockposition.shift(enumdirection).down()).getBlock().getMaterial() != Material.WATER);
/*     */     
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/*  72 */     e(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   protected final boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  76 */     if (e(world, blockposition)) {
/*  77 */       return true;
/*     */     }
/*  79 */     b(world, blockposition, iblockdata, 0);
/*  80 */     world.setAir(blockposition);
/*  81 */     return false;
/*     */   }
/*     */   
/*     */   public boolean e(World world, BlockPosition blockposition)
/*     */   {
/*  86 */     return canPlace(world, blockposition);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  90 */     return null;
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/*  94 */     return Items.REEDS;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 106 */     return getBlockData().set(AGE, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 110 */     return ((Integer)iblockdata.get(AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 114 */     return new BlockStateList(this, new IBlockState[] { AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockReed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */