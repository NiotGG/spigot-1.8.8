/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ 
/*     */ public class BlockCocoa
/*     */   extends BlockDirectional implements IBlockFragilePlantElement
/*     */ {
/*   9 */   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 2);
/*     */   
/*     */   public BlockCocoa() {
/*  12 */     super(Material.PLANT);
/*  13 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(AGE, Integer.valueOf(0)));
/*  14 */     a(true);
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  18 */     if (!e(world, blockposition, iblockdata)) {
/*  19 */       f(world, blockposition, iblockdata);
/*  20 */     } else if (world.random.nextInt(5) == 0) {
/*  21 */       int i = ((Integer)iblockdata.get(AGE)).intValue();
/*     */       
/*  23 */       if (i < 2)
/*     */       {
/*  25 */         IBlockData data = iblockdata.set(AGE, Integer.valueOf(i + 1));
/*  26 */         CraftEventFactory.handleBlockGrowEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this, toLegacyData(data));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean e(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  34 */     blockposition = blockposition.shift((EnumDirection)iblockdata.get(FACING));
/*  35 */     IBlockData iblockdata1 = world.getType(blockposition);
/*     */     
/*  37 */     return (iblockdata1.getBlock() == Blocks.LOG) && (iblockdata1.get(BlockWood.VARIANT) == BlockWood.EnumLogVariant.JUNGLE);
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  41 */     return false;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  49 */     updateShape(world, blockposition);
/*  50 */     return super.a(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  54 */     IBlockData iblockdata = iblockaccess.getType(blockposition);
/*  55 */     EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/*  56 */     int i = ((Integer)iblockdata.get(AGE)).intValue();
/*  57 */     int j = 4 + i * 2;
/*  58 */     int k = 5 + i * 2;
/*  59 */     float f = j / 2.0F;
/*     */     
/*  61 */     switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*     */     case 1: 
/*  63 */       a((8.0F - f) / 16.0F, (12.0F - k) / 16.0F, (15.0F - j) / 16.0F, (8.0F + f) / 16.0F, 0.75F, 0.9375F);
/*  64 */       break;
/*     */     
/*     */     case 2: 
/*  67 */       a((8.0F - f) / 16.0F, (12.0F - k) / 16.0F, 0.0625F, (8.0F + f) / 16.0F, 0.75F, (1.0F + j) / 16.0F);
/*  68 */       break;
/*     */     
/*     */     case 3: 
/*  71 */       a(0.0625F, (12.0F - k) / 16.0F, (8.0F - f) / 16.0F, (1.0F + j) / 16.0F, 0.75F, (8.0F + f) / 16.0F);
/*  72 */       break;
/*     */     
/*     */     case 4: 
/*  75 */       a((15.0F - j) / 16.0F, (12.0F - k) / 16.0F, (8.0F - f) / 16.0F, 0.9375F, 0.75F, (8.0F + f) / 16.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack)
/*     */   {
/*  81 */     EnumDirection enumdirection = EnumDirection.fromAngle(entityliving.yaw);
/*     */     
/*  83 */     world.setTypeAndData(blockposition, iblockdata.set(FACING, enumdirection), 2);
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/*  87 */     if (!enumdirection.k().c()) {
/*  88 */       enumdirection = EnumDirection.NORTH;
/*     */     }
/*     */     
/*  91 */     return getBlockData().set(FACING, enumdirection.opposite()).set(AGE, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  95 */     if (!e(world, blockposition, iblockdata)) {
/*  96 */       f(world, blockposition, iblockdata);
/*     */     }
/*     */   }
/*     */   
/*     */   private void f(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 102 */     world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
/* 103 */     b(world, blockposition, iblockdata, 0);
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
/* 107 */     int j = ((Integer)iblockdata.get(AGE)).intValue();
/* 108 */     byte b0 = 1;
/*     */     
/* 110 */     if (j >= 2) {
/* 111 */       b0 = 3;
/*     */     }
/*     */     
/* 114 */     for (int k = 0; k < b0; k++) {
/* 115 */       a(world, blockposition, new ItemStack(Items.DYE, 1, EnumColor.BROWN.getInvColorIndex()));
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDropData(World world, BlockPosition blockposition)
/*     */   {
/* 121 */     return EnumColor.BROWN.getInvColorIndex();
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
/* 125 */     return ((Integer)iblockdata.get(AGE)).intValue() < 2;
/*     */   }
/*     */   
/*     */   public boolean a(World world, Random random, BlockPosition blockposition, IBlockData iblockdata) {
/* 129 */     return true;
/*     */   }
/*     */   
/*     */   public void b(World world, Random random, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 134 */     IBlockData data = iblockdata.set(AGE, Integer.valueOf(((Integer)iblockdata.get(AGE)).intValue() + 1));
/* 135 */     CraftEventFactory.handleBlockGrowEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this, toLegacyData(data));
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i)
/*     */   {
/* 140 */     return getBlockData().set(FACING, EnumDirection.fromType2(i)).set(AGE, Integer.valueOf((i & 0xF) >> 2));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 144 */     byte b0 = 0;
/* 145 */     int i = b0 | ((EnumDirection)iblockdata.get(FACING)).b();
/*     */     
/* 147 */     i |= ((Integer)iblockdata.get(AGE)).intValue() << 2;
/* 148 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 152 */     return new BlockStateList(this, new IBlockState[] { FACING, AGE });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 157 */     static final int[] a = new int[EnumDirection.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 161 */         a[EnumDirection.SOUTH.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 167 */         a[EnumDirection.NORTH.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 173 */         a[EnumDirection.WEST.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 179 */         a[EnumDirection.EAST.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockCocoa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */