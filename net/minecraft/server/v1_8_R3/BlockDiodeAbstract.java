/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public abstract class BlockDiodeAbstract extends BlockDirectional
/*     */ {
/*     */   protected final boolean N;
/*     */   
/*     */   protected BlockDiodeAbstract(boolean flag)
/*     */   {
/*  12 */     super(Material.ORIENTABLE);
/*  13 */     this.N = flag;
/*  14 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  18 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  22 */     return World.a(world, blockposition.down()) ? super.canPlace(world, blockposition) : false;
/*     */   }
/*     */   
/*     */   public boolean e(World world, BlockPosition blockposition) {
/*  26 */     return World.a(world, blockposition.down());
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {}
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  32 */     if (!b(world, blockposition, iblockdata)) {
/*  33 */       boolean flag = e(world, blockposition, iblockdata);
/*     */       
/*  35 */       if ((this.N) && (!flag))
/*     */       {
/*  37 */         if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callRedstoneChange(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), 15, 0).getNewCurrent() != 0) {
/*  38 */           return;
/*     */         }
/*     */         
/*  41 */         world.setTypeAndData(blockposition, k(iblockdata), 2);
/*  42 */       } else if (!this.N)
/*     */       {
/*  44 */         if (org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callRedstoneChange(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), 0, 15).getNewCurrent() != 15) {
/*  45 */           return;
/*     */         }
/*     */         
/*  48 */         world.setTypeAndData(blockposition, e(iblockdata), 2);
/*  49 */         if (!flag) {
/*  50 */           world.a(blockposition, e(iblockdata).getBlock(), m(iblockdata), -1);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean l(IBlockData iblockdata)
/*     */   {
/*  58 */     return this.N;
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/*  62 */     return a(iblockaccess, blockposition, iblockdata, enumdirection);
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/*  66 */     return iblockdata.get(FACING) == enumdirection ? a(iblockaccess, blockposition, iblockdata) : !l(iblockdata) ? 0 : 0;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  70 */     if (e(world, blockposition)) {
/*  71 */       g(world, blockposition, iblockdata);
/*     */     } else {
/*  73 */       b(world, blockposition, iblockdata, 0);
/*  74 */       world.setAir(blockposition);
/*  75 */       EnumDirection[] aenumdirection = EnumDirection.values();
/*  76 */       int i = aenumdirection.length;
/*     */       
/*  78 */       for (int j = 0; j < i; j++) {
/*  79 */         EnumDirection enumdirection = aenumdirection[j];
/*     */         
/*  81 */         world.applyPhysics(blockposition.shift(enumdirection), this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void g(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  88 */     if (!b(world, blockposition, iblockdata)) {
/*  89 */       boolean flag = e(world, blockposition, iblockdata);
/*     */       
/*  91 */       if (((this.N) && (!flag)) || ((!this.N) && (flag) && (!world.a(blockposition, this)))) {
/*  92 */         byte b0 = -1;
/*     */         
/*  94 */         if (i(world, blockposition, iblockdata)) {
/*  95 */           b0 = -3;
/*  96 */         } else if (this.N) {
/*  97 */           b0 = -2;
/*     */         }
/*     */         
/* 100 */         world.a(blockposition, this, d(iblockdata), b0);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 107 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 111 */     return f(world, blockposition, iblockdata) > 0;
/*     */   }
/*     */   
/*     */   protected int f(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 115 */     EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/* 116 */     BlockPosition blockposition1 = blockposition.shift(enumdirection);
/* 117 */     int i = world.getBlockFacePower(blockposition1, enumdirection);
/*     */     
/* 119 */     if (i >= 15) {
/* 120 */       return i;
/*     */     }
/* 122 */     IBlockData iblockdata1 = world.getType(blockposition1);
/*     */     
/* 124 */     return Math.max(i, iblockdata1.getBlock() == Blocks.REDSTONE_WIRE ? ((Integer)iblockdata1.get(BlockRedstoneWire.POWER)).intValue() : 0);
/*     */   }
/*     */   
/*     */   protected int c(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 129 */     EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/* 130 */     EnumDirection enumdirection1 = enumdirection.e();
/* 131 */     EnumDirection enumdirection2 = enumdirection.f();
/*     */     
/* 133 */     return Math.max(c(iblockaccess, blockposition.shift(enumdirection1), enumdirection1), c(iblockaccess, blockposition.shift(enumdirection2), enumdirection2));
/*     */   }
/*     */   
/*     */   protected int c(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
/* 137 */     IBlockData iblockdata = iblockaccess.getType(blockposition);
/* 138 */     Block block = iblockdata.getBlock();
/*     */     
/* 140 */     return c(block) ? iblockaccess.getBlockPower(blockposition, enumdirection) : block == Blocks.REDSTONE_WIRE ? ((Integer)iblockdata.get(BlockRedstoneWire.POWER)).intValue() : 0;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/* 148 */     return getBlockData().set(FACING, entityliving.getDirection().opposite());
/*     */   }
/*     */   
/*     */   public void postPlace(World world, BlockPosition blockposition, IBlockData iblockdata, EntityLiving entityliving, ItemStack itemstack) {
/* 152 */     if (e(world, blockposition, iblockdata)) {
/* 153 */       world.a(blockposition, this, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 159 */     h(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   protected void h(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 163 */     EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
/* 164 */     BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
/*     */     
/* 166 */     world.d(blockposition1, this);
/* 167 */     world.a(blockposition1, this, enumdirection);
/*     */   }
/*     */   
/*     */   public void postBreak(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 171 */     if (this.N) {
/* 172 */       EnumDirection[] aenumdirection = EnumDirection.values();
/* 173 */       int i = aenumdirection.length;
/*     */       
/* 175 */       for (int j = 0; j < i; j++) {
/* 176 */         EnumDirection enumdirection = aenumdirection[j];
/*     */         
/* 178 */         world.applyPhysics(blockposition.shift(enumdirection), this);
/*     */       }
/*     */     }
/*     */     
/* 182 */     super.postBreak(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 186 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean c(Block block) {
/* 190 */     return block.isPowerSource();
/*     */   }
/*     */   
/*     */   protected int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
/* 194 */     return 15;
/*     */   }
/*     */   
/*     */   public static boolean d(Block block) {
/* 198 */     return (Blocks.UNPOWERED_REPEATER.e(block)) || (Blocks.UNPOWERED_COMPARATOR.e(block));
/*     */   }
/*     */   
/*     */   public boolean e(Block block) {
/* 202 */     return (block == e(getBlockData()).getBlock()) || (block == k(getBlockData()).getBlock());
/*     */   }
/*     */   
/*     */   public boolean i(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 206 */     EnumDirection enumdirection = ((EnumDirection)iblockdata.get(FACING)).opposite();
/* 207 */     BlockPosition blockposition1 = blockposition.shift(enumdirection);
/*     */     
/* 209 */     return world.getType(blockposition1).get(FACING) != enumdirection;
/*     */   }
/*     */   
/*     */   protected int m(IBlockData iblockdata) {
/* 213 */     return d(iblockdata);
/*     */   }
/*     */   
/*     */   protected abstract int d(IBlockData paramIBlockData);
/*     */   
/*     */   protected abstract IBlockData e(IBlockData paramIBlockData);
/*     */   
/*     */   protected abstract IBlockData k(IBlockData paramIBlockData);
/*     */   
/*     */   public boolean b(Block block) {
/* 223 */     return e(block);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockDiodeAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */