/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class BlockDispenser extends BlockContainer
/*     */ {
/*   7 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
/*   8 */   public static final BlockStateBoolean TRIGGERED = BlockStateBoolean.of("triggered");
/*   9 */   public static final RegistryDefault<Item, IDispenseBehavior> REGISTRY = new RegistryDefault(new DispenseBehaviorItem());
/*  10 */   protected Random O = new Random();
/*  11 */   public static boolean eventFired = false;
/*     */   
/*     */   protected BlockDispenser() {
/*  14 */     super(Material.STONE);
/*  15 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(TRIGGERED, Boolean.valueOf(false)));
/*  16 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  20 */     return 4;
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  24 */     super.onPlace(world, blockposition, iblockdata);
/*  25 */     e(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   private void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  29 */     if (!world.isClientSide) {
/*  30 */       EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/*  31 */       boolean flag = world.getType(blockposition.north()).getBlock().o();
/*  32 */       boolean flag1 = world.getType(blockposition.south()).getBlock().o();
/*     */       
/*  34 */       if ((enumdirection == EnumDirection.NORTH) && (flag) && (!flag1)) {
/*  35 */         enumdirection = EnumDirection.SOUTH;
/*  36 */       } else if ((enumdirection == EnumDirection.SOUTH) && (flag1) && (!flag)) {
/*  37 */         enumdirection = EnumDirection.NORTH;
/*     */       } else {
/*  39 */         boolean flag2 = world.getType(blockposition.west()).getBlock().o();
/*  40 */         boolean flag3 = world.getType(blockposition.east()).getBlock().o();
/*     */         
/*  42 */         if ((enumdirection == EnumDirection.WEST) && (flag2) && (!flag3)) {
/*  43 */           enumdirection = EnumDirection.EAST;
/*  44 */         } else if ((enumdirection == EnumDirection.EAST) && (flag3) && (!flag2)) {
/*  45 */           enumdirection = EnumDirection.WEST;
/*     */         }
/*     */       }
/*     */       
/*  49 */       world.setTypeAndData(blockposition, iblockdata.set(FACING, enumdirection).set(TRIGGERED, Boolean.valueOf(false)), 2);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
/*  54 */     if (world.isClientSide) {
/*  55 */       return true;
/*     */     }
/*  57 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  59 */     if ((tileentity instanceof TileEntityDispenser)) {
/*  60 */       entityhuman.openContainer((TileEntityDispenser)tileentity);
/*  61 */       if ((tileentity instanceof TileEntityDropper)) {
/*  62 */         entityhuman.b(StatisticList.O);
/*     */       } else {
/*  64 */         entityhuman.b(StatisticList.Q);
/*     */       }
/*     */     }
/*     */     
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public void dispense(World world, BlockPosition blockposition)
/*     */   {
/*  73 */     SourceBlock sourceblock = new SourceBlock(world, blockposition);
/*  74 */     TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();
/*     */     
/*  76 */     if (tileentitydispenser != null) {
/*  77 */       int i = tileentitydispenser.m();
/*     */       
/*  79 */       if (i < 0) {
/*  80 */         world.triggerEffect(1001, blockposition, 0);
/*     */       } else {
/*  82 */         ItemStack itemstack = tileentitydispenser.getItem(i);
/*  83 */         IDispenseBehavior idispensebehavior = a(itemstack);
/*     */         
/*  85 */         if (idispensebehavior != IDispenseBehavior.NONE) {
/*  86 */           ItemStack itemstack1 = idispensebehavior.a(sourceblock, itemstack);
/*  87 */           eventFired = false;
/*     */           
/*  89 */           tileentitydispenser.setItem(i, itemstack1.count <= 0 ? null : itemstack1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected IDispenseBehavior a(ItemStack itemstack)
/*     */   {
/*  97 */     return (IDispenseBehavior)REGISTRY.get(itemstack == null ? null : itemstack.getItem());
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/* 101 */     boolean flag = (world.isBlockIndirectlyPowered(blockposition)) || (world.isBlockIndirectlyPowered(blockposition.up()));
/* 102 */     boolean flag1 = ((Boolean)iblockdata.get(TRIGGERED)).booleanValue();
/*     */     
/* 104 */     if ((flag) && (!flag1)) {
/* 105 */       world.a(blockposition, this, a(world));
/* 106 */       world.setTypeAndData(blockposition, iblockdata.set(TRIGGERED, Boolean.valueOf(true)), 4);
/* 107 */     } else if ((!flag) && (flag1)) {
/* 108 */       world.setTypeAndData(blockposition, iblockdata.set(TRIGGERED, Boolean.valueOf(false)), 4);
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/* 114 */     if (!world.isClientSide) {
/* 115 */       dispense(world, blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i)
/*     */   {
/* 121 */     return new TileEntityDispenser();
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/* 125 */     return getBlockData().set(FACING, BlockPiston.a(world, blockposition, entityliving)).set(TRIGGERED, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
/* 129 */     world.setTypeAndData(blockposition, iblockdata.set(FACING, BlockPiston.a(world, blockposition, entityliving)), 2);
/* 130 */     if (itemstack.hasName()) {
/* 131 */       TileEntity tileentity = world.getTileEntity(blockposition);
/*     */       
/* 133 */       if ((tileentity instanceof TileEntityDispenser)) {
/* 134 */         ((TileEntityDispenser)tileentity).a(itemstack.getName());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 141 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/* 143 */     if ((tileentity instanceof TileEntityDispenser)) {
/* 144 */       InventoryUtils.dropInventory(world, blockposition, (TileEntityDispenser)tileentity);
/* 145 */       world.updateAdjacentComparators(blockposition, this);
/*     */     }
/*     */     
/* 148 */     super.remove(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public static IPosition a(ISourceBlock isourceblock) {
/* 152 */     EnumDirection enumdirection = b(isourceblock.f());
/* 153 */     double d0 = isourceblock.getX() + 0.7D * enumdirection.getAdjacentX();
/* 154 */     double d1 = isourceblock.getY() + 0.7D * enumdirection.getAdjacentY();
/* 155 */     double d2 = isourceblock.getZ() + 0.7D * enumdirection.getAdjacentZ();
/*     */     
/* 157 */     return new Position(d0, d1, d2);
/*     */   }
/*     */   
/*     */   public static EnumDirection b(int i) {
/* 161 */     return EnumDirection.fromType1(i & 0x7);
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 165 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World world, BlockPosition blockposition) {
/* 169 */     return Container.a(world.getTileEntity(blockposition));
/*     */   }
/*     */   
/*     */   public int b() {
/* 173 */     return 3;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 177 */     return getBlockData().set(FACING, b(i)).set(TRIGGERED, Boolean.valueOf((i & 0x8) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 181 */     byte b0 = 0;
/* 182 */     int i = b0 | ((EnumDirection)iblockdata.get(FACING)).a();
/*     */     
/* 184 */     if (((Boolean)iblockdata.get(TRIGGERED)).booleanValue()) {
/* 185 */       i |= 0x8;
/*     */     }
/*     */     
/* 188 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 192 */     return new BlockStateList(this, new IBlockState[] { FACING, TRIGGERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */