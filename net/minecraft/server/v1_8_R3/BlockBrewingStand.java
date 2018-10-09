/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockBrewingStand extends BlockContainer
/*     */ {
/*   8 */   public static final BlockStateBoolean[] HAS_BOTTLE = { BlockStateBoolean.of("has_bottle_0"), BlockStateBoolean.of("has_bottle_1"), BlockStateBoolean.of("has_bottle_2") };
/*     */   
/*     */   public BlockBrewingStand() {
/*  11 */     super(Material.ORE);
/*  12 */     j(this.blockStateList.getBlockData().set(HAS_BOTTLE[0], Boolean.valueOf(false)).set(HAS_BOTTLE[1], Boolean.valueOf(false)).set(HAS_BOTTLE[2], Boolean.valueOf(false)));
/*     */   }
/*     */   
/*     */   public String getName() {
/*  16 */     return LocaleI18n.get("item.brewingStand.name");
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  20 */     return false;
/*     */   }
/*     */   
/*     */   public int b() {
/*  24 */     return 3;
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*  28 */     return new TileEntityBrewingStand();
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  32 */     return false;
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
/*  36 */     a(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
/*  37 */     super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
/*  38 */     j();
/*  39 */     super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
/*     */   }
/*     */   
/*     */   public void j() {
/*  43 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
/*  47 */     if (world.isClientSide) {
/*  48 */       return true;
/*     */     }
/*  50 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  52 */     if ((tileentity instanceof TileEntityBrewingStand)) {
/*  53 */       entityhuman.openContainer((TileEntityBrewingStand)tileentity);
/*  54 */       entityhuman.b(StatisticList.M);
/*     */     }
/*     */     
/*  57 */     return true;
/*     */   }
/*     */   
/*     */   public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack)
/*     */   {
/*  62 */     if (itemstack.hasName()) {
/*  63 */       TileEntity tileentity = world.getTileEntity(blockposition);
/*     */       
/*  65 */       if ((tileentity instanceof TileEntityBrewingStand)) {
/*  66 */         ((TileEntityBrewingStand)tileentity).a(itemstack.getName());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  73 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  75 */     if ((tileentity instanceof TileEntityBrewingStand)) {
/*  76 */       InventoryUtils.dropInventory(world, blockposition, (TileEntityBrewingStand)tileentity);
/*     */     }
/*     */     
/*  79 */     super.remove(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/*  83 */     return Items.BREWING_STAND;
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/*  87 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World world, BlockPosition blockposition) {
/*  91 */     return Container.a(world.getTileEntity(blockposition));
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/*  95 */     IBlockData iblockdata = getBlockData();
/*     */     
/*  97 */     for (int j = 0; j < 3; j++) {
/*  98 */       iblockdata = iblockdata.set(HAS_BOTTLE[j], Boolean.valueOf((i & 1 << j) > 0));
/*     */     }
/*     */     
/* 101 */     return iblockdata;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 105 */     int i = 0;
/*     */     
/* 107 */     for (int j = 0; j < 3; j++) {
/* 108 */       if (((Boolean)iblockdata.get(HAS_BOTTLE[j])).booleanValue()) {
/* 109 */         i |= 1 << j;
/*     */       }
/*     */     }
/*     */     
/* 113 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 117 */     return new BlockStateList(this, new IBlockState[] { HAS_BOTTLE[0], HAS_BOTTLE[1], HAS_BOTTLE[2] });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */