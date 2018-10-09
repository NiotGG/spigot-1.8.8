/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockDaylightDetector extends BlockContainer
/*     */ {
/*   7 */   public static final BlockStateInteger POWER = BlockStateInteger.of("power", 0, 15);
/*     */   private final boolean b;
/*     */   
/*     */   public BlockDaylightDetector(boolean flag) {
/*  11 */     super(Material.WOOD);
/*  12 */     this.b = flag;
/*  13 */     j(this.blockStateList.getBlockData().set(POWER, Integer.valueOf(0)));
/*  14 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/*  15 */     a(CreativeModeTab.d);
/*  16 */     c(0.2F);
/*  17 */     a(f);
/*  18 */     c("daylightDetector");
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  22 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/*  26 */     return ((Integer)iblockdata.get(POWER)).intValue();
/*     */   }
/*     */   
/*     */   public void f(World world, BlockPosition blockposition) {
/*  30 */     if (!world.worldProvider.o()) {
/*  31 */       IBlockData iblockdata = world.getType(blockposition);
/*  32 */       int i = world.b(EnumSkyBlock.SKY, blockposition) - world.ab();
/*  33 */       float f = world.d(1.0F);
/*  34 */       float f1 = f < 3.1415927F ? 0.0F : 6.2831855F;
/*     */       
/*  36 */       f += (f1 - f) * 0.2F;
/*  37 */       i = Math.round(i * MathHelper.cos(f));
/*  38 */       i = MathHelper.clamp(i, 0, 15);
/*  39 */       if (this.b) {
/*  40 */         i = 15 - i;
/*     */       }
/*     */       
/*  43 */       if (((Integer)iblockdata.get(POWER)).intValue() != i) {
/*  44 */         i = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callRedstoneChange(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), ((Integer)iblockdata.get(POWER)).intValue(), i).getNewCurrent();
/*  45 */         world.setTypeAndData(blockposition, iblockdata.set(POWER, Integer.valueOf(i)), 3);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/*  52 */     if (entityhuman.cn()) {
/*  53 */       if (world.isClientSide) {
/*  54 */         return true;
/*     */       }
/*  56 */       if (this.b) {
/*  57 */         world.setTypeAndData(blockposition, Blocks.DAYLIGHT_DETECTOR.getBlockData().set(POWER, (Integer)iblockdata.get(POWER)), 4);
/*  58 */         Blocks.DAYLIGHT_DETECTOR.f(world, blockposition);
/*     */       } else {
/*  60 */         world.setTypeAndData(blockposition, Blocks.DAYLIGHT_DETECTOR_INVERTED.getBlockData().set(POWER, (Integer)iblockdata.get(POWER)), 4);
/*  61 */         Blocks.DAYLIGHT_DETECTOR_INVERTED.f(world, blockposition);
/*     */       }
/*     */       
/*  64 */       return true;
/*     */     }
/*     */     
/*  67 */     return super.interact(world, blockposition, iblockdata, entityhuman, enumdirection, f, f1, f2);
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i)
/*     */   {
/*  72 */     return Item.getItemOf(Blocks.DAYLIGHT_DETECTOR);
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  80 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  84 */     return 3;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/*  88 */     return true;
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*  92 */     return new TileEntityLightDetector();
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/*  96 */     return getBlockData().set(POWER, Integer.valueOf(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 100 */     return ((Integer)iblockdata.get(POWER)).intValue();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 104 */     return new BlockStateList(this, new IBlockState[] { POWER });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDaylightDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */