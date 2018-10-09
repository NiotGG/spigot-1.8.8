/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ public class BlockAnvil
/*     */   extends BlockFalling
/*     */ {
/*   7 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*   8 */   public static final BlockStateInteger DAMAGE = BlockStateInteger.of("damage", 0, 2);
/*     */   
/*     */   protected BlockAnvil() {
/*  11 */     super(Material.HEAVY);
/*  12 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(DAMAGE, Integer.valueOf(0)));
/*  13 */     e(0);
/*  14 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  18 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  22 */     return false;
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/*  26 */     EnumDirection enumdirection1 = entityliving.getDirection().e();
/*     */     
/*  28 */     return super.getPlacedState(world, blockposition, enumdirection, f, f1, f2, i, entityliving).set(FACING, enumdirection1).set(DAMAGE, Integer.valueOf(i >> 2));
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
/*  32 */     if (!world.isClientSide) {
/*  33 */       entityhuman.openTileEntity(new TileEntityContainerAnvil(world, blockposition));
/*     */     }
/*     */     
/*  36 */     return true;
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData iblockdata) {
/*  40 */     return ((Integer)iblockdata.get(DAMAGE)).intValue();
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  44 */     EnumDirection enumdirection = (EnumDirection)iblockaccess.getType(blockposition).get(FACING);
/*     */     
/*  46 */     if (enumdirection.k() == EnumDirection.EnumAxis.X) {
/*  47 */       a(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
/*     */     } else {
/*  49 */       a(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(EntityFallingBlock entityfallingblock)
/*     */   {
/*  55 */     entityfallingblock.a(true);
/*     */   }
/*     */   
/*     */   public void a_(World world, BlockPosition blockposition) {
/*  59 */     world.triggerEffect(1022, blockposition, 0);
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/*  63 */     return getBlockData().set(FACING, EnumDirection.fromType2(i & 0x3)).set(DAMAGE, Integer.valueOf((i & 0xF) >> 2));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/*  67 */     byte b0 = 0;
/*  68 */     int i = b0 | ((EnumDirection)iblockdata.get(FACING)).b();
/*     */     
/*  70 */     i |= ((Integer)iblockdata.get(DAMAGE)).intValue() << 2;
/*  71 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/*  75 */     return new BlockStateList(this, new IBlockState[] { FACING, DAMAGE });
/*     */   }
/*     */   
/*     */   public static class TileEntityContainerAnvil implements ITileEntityContainer
/*     */   {
/*     */     private final World a;
/*     */     private final BlockPosition b;
/*     */     
/*     */     public TileEntityContainerAnvil(World world, BlockPosition blockposition) {
/*  84 */       this.a = world;
/*  85 */       this.b = blockposition;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  89 */       return "anvil";
/*     */     }
/*     */     
/*     */     public boolean hasCustomName() {
/*  93 */       return false;
/*     */     }
/*     */     
/*     */     public IChatBaseComponent getScoreboardDisplayName() {
/*  97 */       return new ChatMessage(Blocks.ANVIL.a() + ".name", new Object[0]);
/*     */     }
/*     */     
/*     */     public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
/* 101 */       return new ContainerAnvil(playerinventory, this.a, this.b, entityhuman);
/*     */     }
/*     */     
/*     */     public String getContainerName() {
/* 105 */       return "minecraft:anvil";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockAnvil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */