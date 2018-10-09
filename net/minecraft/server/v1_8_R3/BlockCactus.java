/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ 
/*     */ public class BlockCactus extends Block
/*     */ {
/*  10 */   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 15);
/*     */   
/*     */   protected BlockCactus() {
/*  13 */     super(Material.CACTUS);
/*  14 */     j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)));
/*  15 */     a(true);
/*  16 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  20 */     BlockPosition blockposition1 = blockposition.up();
/*     */     
/*  22 */     if (world.isEmpty(blockposition1))
/*     */     {
/*     */ 
/*  25 */       for (int i = 1; world.getType(blockposition.down(i)).getBlock() == this; i++) {}
/*     */       
/*     */ 
/*     */ 
/*  29 */       if (i < 3) {
/*  30 */         int j = ((Integer)iblockdata.get(AGE)).intValue();
/*     */         
/*  32 */         if (j >= (byte)(int)range(3.0F, world.growthOdds / world.spigotConfig.cactusModifier * 15.0F + 0.5F, 15.0F))
/*     */         {
/*  34 */           IBlockData iblockdata1 = iblockdata.set(AGE, Integer.valueOf(0));
/*     */           
/*  36 */           CraftEventFactory.handleBlockGrowEvent(world, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), this, 0);
/*  37 */           world.setTypeAndData(blockposition, iblockdata1, 4);
/*  38 */           doPhysics(world, blockposition1, iblockdata1, this);
/*     */         } else {
/*  40 */           world.setTypeAndData(blockposition, iblockdata.set(AGE, Integer.valueOf(j + 1)), 4);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  48 */     float f = 0.0625F;
/*     */     
/*  50 */     return new AxisAlignedBB(blockposition.getX() + f, blockposition.getY(), blockposition.getZ() + f, blockposition.getX() + 1 - f, blockposition.getY() + 1 - f, blockposition.getZ() + 1 - f);
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  62 */     return super.canPlace(world, blockposition) ? e(world, blockposition) : false;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  66 */     if (!e(world, blockposition)) {
/*  67 */       world.setAir(blockposition, true);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean e(World world, BlockPosition blockposition)
/*     */   {
/*  73 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/*  75 */     while (iterator.hasNext()) {
/*  76 */       EnumDirection enumdirection = (EnumDirection)iterator.next();
/*     */       
/*  78 */       if (world.getType(blockposition.shift(enumdirection)).getBlock().getMaterial().isBuildable()) {
/*  79 */         return false;
/*     */       }
/*     */     }
/*     */     
/*  83 */     Block block = world.getType(blockposition.down()).getBlock();
/*     */     
/*  85 */     return (block == Blocks.CACTUS) || (block == Blocks.SAND);
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
/*  89 */     CraftEventFactory.blockDamage = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*  90 */     entity.damageEntity(DamageSource.CACTUS, 1.0F);
/*  91 */     CraftEventFactory.blockDamage = null;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/*  95 */     return getBlockData().set(AGE, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/*  99 */     return ((Integer)iblockdata.get(AGE)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 103 */     return new BlockStateList(this, new IBlockState[] { AGE });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockCactus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */