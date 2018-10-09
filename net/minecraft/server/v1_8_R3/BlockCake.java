/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockCake extends Block
/*     */ {
/*   7 */   public static final BlockStateInteger BITES = BlockStateInteger.of("bites", 0, 6);
/*     */   
/*     */   protected BlockCake() {
/*  10 */     super(Material.CAKE);
/*  11 */     j(this.blockStateList.getBlockData().set(BITES, Integer.valueOf(0)));
/*  12 */     a(true);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  16 */     float f = 0.0625F;
/*  17 */     float f1 = (1 + ((Integer)iblockaccess.getType(blockposition).get(BITES)).intValue() * 2) / 16.0F;
/*  18 */     float f2 = 0.5F;
/*     */     
/*  20 */     a(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
/*     */   }
/*     */   
/*     */   public void j() {
/*  24 */     float f = 0.0625F;
/*  25 */     float f1 = 0.5F;
/*     */     
/*  27 */     a(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  31 */     float f = 0.0625F;
/*  32 */     float f1 = (1 + ((Integer)iblockdata.get(BITES)).intValue() * 2) / 16.0F;
/*  33 */     float f2 = 0.5F;
/*     */     
/*  35 */     return new AxisAlignedBB(blockposition.getX() + f1, blockposition.getY(), blockposition.getZ() + f, blockposition.getX() + 1 - f, blockposition.getY() + f2, blockposition.getZ() + 1 - f);
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  39 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  43 */     return false;
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
/*  47 */     b(world, blockposition, iblockdata, entityhuman);
/*  48 */     return true;
/*     */   }
/*     */   
/*     */   public void attack(World world, BlockPosition blockposition, EntityHuman entityhuman) {
/*  52 */     b(world, blockposition, world.getType(blockposition), entityhuman);
/*     */   }
/*     */   
/*     */   private void b(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
/*  56 */     if (entityhuman.j(false)) {
/*  57 */       entityhuman.b(StatisticList.H);
/*     */       
/*     */ 
/*  60 */       int oldFoodLevel = entityhuman.getFoodData().foodLevel;
/*     */       
/*  62 */       org.bukkit.event.entity.FoodLevelChangeEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callFoodLevelChangeEvent(entityhuman, 2 + oldFoodLevel);
/*     */       
/*  64 */       if (!event.isCancelled()) {
/*  65 */         entityhuman.getFoodData().eat(event.getFoodLevel() - oldFoodLevel, 0.1F);
/*     */       }
/*     */       
/*  68 */       ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)entityhuman).getBukkitEntity().getScaledHealth(), entityhuman.getFoodData().foodLevel, entityhuman.getFoodData().saturationLevel));
/*     */       
/*  70 */       int i = ((Integer)iblockdata.get(BITES)).intValue();
/*     */       
/*  72 */       if (i < 6) {
/*  73 */         world.setTypeAndData(blockposition, iblockdata.set(BITES, Integer.valueOf(i + 1)), 3);
/*     */       } else {
/*  75 */         world.setAir(blockposition);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition)
/*     */   {
/*  82 */     return super.canPlace(world, blockposition) ? e(world, blockposition) : false;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  86 */     if (!e(world, blockposition)) {
/*  87 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean e(World world, BlockPosition blockposition)
/*     */   {
/*  93 */     return world.getType(blockposition.down()).getBlock().getMaterial().isBuildable();
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  97 */     return 0;
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 105 */     return getBlockData().set(BITES, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 109 */     return ((Integer)iblockdata.get(BITES)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 113 */     return new BlockStateList(this, new IBlockState[] { BITES });
/*     */   }
/*     */   
/*     */   public int l(World world, BlockPosition blockposition) {
/* 117 */     return (7 - ((Integer)world.getType(blockposition).get(BITES)).intValue()) * 2;
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 121 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockCake.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */