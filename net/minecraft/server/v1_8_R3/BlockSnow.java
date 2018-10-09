/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockSnow extends Block
/*     */ {
/*   7 */   public static final BlockStateInteger LAYERS = BlockStateInteger.of("layers", 1, 8);
/*     */   
/*     */   protected BlockSnow() {
/*  10 */     super(Material.PACKED_ICE);
/*  11 */     j(this.blockStateList.getBlockData().set(LAYERS, Integer.valueOf(1)));
/*  12 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*  13 */     a(true);
/*  14 */     a(CreativeModeTab.c);
/*  15 */     j();
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  19 */     return ((Integer)iblockaccess.getType(blockposition).get(LAYERS)).intValue() < 5;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  23 */     int i = ((Integer)iblockdata.get(LAYERS)).intValue() - 1;
/*  24 */     float f = 0.125F;
/*     */     
/*  26 */     return new AxisAlignedBB(blockposition.getX() + this.minX, blockposition.getY() + this.minY, blockposition.getZ() + this.minZ, blockposition.getX() + this.maxX, blockposition.getY() + i * f, blockposition.getZ() + this.maxZ);
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  30 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  34 */     return false;
/*     */   }
/*     */   
/*     */   public void j() {
/*  38 */     b(0);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  42 */     IBlockData iblockdata = iblockaccess.getType(blockposition);
/*     */     
/*  44 */     b(((Integer)iblockdata.get(LAYERS)).intValue());
/*     */   }
/*     */   
/*     */   protected void b(int i) {
/*  48 */     a(0.0F, 0.0F, 0.0F, 1.0F, i / 8.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  52 */     IBlockData iblockdata = world.getType(blockposition.down());
/*  53 */     Block block = iblockdata.getBlock();
/*     */     
/*  55 */     return block.getMaterial() == Material.LEAVES;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  59 */     e(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  63 */     if (!canPlace(world, blockposition)) {
/*  64 */       b(world, blockposition, iblockdata, 0);
/*  65 */       world.setAir(blockposition);
/*  66 */       return false;
/*     */     }
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, TileEntity tileentity)
/*     */   {
/*  73 */     a(world, blockposition, new ItemStack(Items.SNOWBALL, ((Integer)iblockdata.get(LAYERS)).intValue() + 1, 0));
/*  74 */     world.setAir(blockposition);
/*  75 */     entityhuman.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/*  79 */     return Items.SNOWBALL;
/*     */   }
/*     */   
/*     */   public int a(Random random) {
/*  83 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  87 */     if (world.b(EnumSkyBlock.BLOCK, blockposition) > 11)
/*     */     {
/*  89 */       if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callBlockFadeEvent(world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), Blocks.AIR).isCancelled()) {
/*  90 */         return;
/*     */       }
/*     */       
/*  93 */       b(world, blockposition, world.getType(blockposition), 0);
/*  94 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i)
/*     */   {
/* 100 */     return getBlockData().set(LAYERS, Integer.valueOf((i & 0x7) + 1));
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition) {
/* 104 */     return ((Integer)world.getType(blockposition).get(LAYERS)).intValue() == 1;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 108 */     return ((Integer)iblockdata.get(LAYERS)).intValue() - 1;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 112 */     return new BlockStateList(this, new IBlockState[] { LAYERS });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSnow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */