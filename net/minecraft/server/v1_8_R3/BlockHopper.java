/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ 
/*     */ public class BlockHopper extends BlockContainer
/*     */ {
/*   8 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", new Predicate() {
/*     */     public boolean a(EnumDirection enumdirection) {
/*  10 */       return enumdirection != EnumDirection.UP;
/*     */     }
/*     */     
/*     */     public boolean apply(Object object) {
/*  14 */       return a((EnumDirection)object);
/*     */     }
/*   8 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  17 */   public static final BlockStateBoolean ENABLED = BlockStateBoolean.of("enabled");
/*     */   
/*     */   public BlockHopper() {
/*  20 */     super(Material.ORE, MaterialMapColor.m);
/*  21 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.DOWN).set(ENABLED, Boolean.valueOf(true)));
/*  22 */     a(CreativeModeTab.d);
/*  23 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  27 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {
/*  31 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
/*  32 */     super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
/*  33 */     float f = 0.125F;
/*     */     
/*  35 */     a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
/*  36 */     super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
/*  37 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
/*  38 */     super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
/*  39 */     a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  40 */     super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
/*  41 */     a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
/*  42 */     super.a(world, blockposition, iblockdata, axisalignedbb, list, entity);
/*  43 */     a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/*  47 */     EnumDirection enumdirection1 = enumdirection.opposite();
/*     */     
/*  49 */     if (enumdirection1 == EnumDirection.UP) {
/*  50 */       enumdirection1 = EnumDirection.DOWN;
/*     */     }
/*     */     
/*  53 */     return getBlockData().set(FACING, enumdirection1).set(ENABLED, Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*  57 */     return new TileEntityHopper();
/*     */   }
/*     */   
/*     */   public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
/*  61 */     super.postPlace(world, blockposition, iblockdata, entityliving, itemstack);
/*  62 */     if (itemstack.hasName()) {
/*  63 */       TileEntity tileentity = world.getTileEntity(blockposition);
/*     */       
/*  65 */       if ((tileentity instanceof TileEntityHopper)) {
/*  66 */         ((TileEntityHopper)tileentity).a(itemstack.getName());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  73 */     e(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
/*  77 */     if (world.isClientSide) {
/*  78 */       return true;
/*     */     }
/*  80 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  82 */     if ((tileentity instanceof TileEntityHopper)) {
/*  83 */       entityhuman.openContainer((TileEntityHopper)tileentity);
/*  84 */       entityhuman.b(StatisticList.P);
/*     */     }
/*     */     
/*  87 */     return true;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/*  92 */     e(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   private void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  96 */     boolean flag = !world.isBlockIndirectlyPowered(blockposition);
/*     */     
/*  98 */     if (flag != ((Boolean)iblockdata.get(ENABLED)).booleanValue()) {
/*  99 */       world.setTypeAndData(blockposition, iblockdata.set(ENABLED, Boolean.valueOf(flag)), 4);
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 105 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/* 107 */     if ((tileentity instanceof TileEntityHopper)) {
/* 108 */       InventoryUtils.dropInventory(world, blockposition, (TileEntityHopper)tileentity);
/* 109 */       world.updateAdjacentComparators(blockposition, this);
/*     */     }
/*     */     
/* 112 */     super.remove(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public int b() {
/* 116 */     return 3;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public static EnumDirection b(int i) {
/* 128 */     return EnumDirection.fromType1(i & 0x7);
/*     */   }
/*     */   
/*     */   public static boolean f(int i) {
/* 132 */     return (i & 0x8) != 8;
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 136 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World world, BlockPosition blockposition) {
/* 140 */     return Container.a(world.getTileEntity(blockposition));
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 144 */     return getBlockData().set(FACING, b(i)).set(ENABLED, Boolean.valueOf(f(i)));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 148 */     byte b0 = 0;
/* 149 */     int i = b0 | ((EnumDirection)iblockdata.get(FACING)).a();
/*     */     
/* 151 */     if (!((Boolean)iblockdata.get(ENABLED)).booleanValue()) {
/* 152 */       i |= 0x8;
/*     */     }
/*     */     
/* 155 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 159 */     return new BlockStateList(this, new IBlockState[] { FACING, ENABLED });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */