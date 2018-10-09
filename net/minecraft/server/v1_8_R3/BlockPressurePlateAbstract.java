/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public abstract class BlockPressurePlateAbstract extends Block
/*     */ {
/*     */   protected BlockPressurePlateAbstract(Material material)
/*     */   {
/*  10 */     this(material, material.r());
/*     */   }
/*     */   
/*     */   protected BlockPressurePlateAbstract(Material material, MaterialMapColor materialmapcolor) {
/*  14 */     super(material, materialmapcolor);
/*  15 */     a(CreativeModeTab.d);
/*  16 */     a(true);
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  20 */     d(iblockaccess.getType(blockposition));
/*     */   }
/*     */   
/*     */   protected void d(IBlockData iblockdata) {
/*  24 */     boolean flag = e(iblockdata) > 0;
/*     */     
/*     */ 
/*  27 */     if (flag) {
/*  28 */       a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.03125F, 0.9375F);
/*     */     } else {
/*  30 */       a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(World world)
/*     */   {
/*  36 */     return 20;
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  40 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  44 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  48 */     return false;
/*     */   }
/*     */   
/*     */   public boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  52 */     return true;
/*     */   }
/*     */   
/*     */   public boolean g() {
/*  56 */     return true;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  60 */     return m(world, blockposition.down());
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  64 */     if (!m(world, blockposition.down())) {
/*  65 */       b(world, blockposition, iblockdata, 0);
/*  66 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean m(World world, BlockPosition blockposition)
/*     */   {
/*  72 */     return (World.a(world, blockposition)) || ((world.getType(blockposition).getBlock() instanceof BlockFence));
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {}
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  78 */     if (!world.isClientSide) {
/*  79 */       int i = e(iblockdata);
/*     */       
/*  81 */       if (i > 0) {
/*  82 */         a(world, blockposition, iblockdata, i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity)
/*     */   {
/*  89 */     if (!world.isClientSide) {
/*  90 */       int i = e(iblockdata);
/*     */       
/*  92 */       if (i == 0) {
/*  93 */         a(world, blockposition, iblockdata, i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(World world, BlockPosition blockposition, IBlockData iblockdata, int i)
/*     */   {
/* 100 */     int j = f(world, blockposition);
/* 101 */     boolean flag = i > 0;
/* 102 */     boolean flag1 = j > 0;
/*     */     
/*     */ 
/* 105 */     org.bukkit.World bworld = world.getWorld();
/* 106 */     org.bukkit.plugin.PluginManager manager = world.getServer().getPluginManager();
/*     */     
/* 108 */     if (flag != flag1) {
/* 109 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), i, j);
/* 110 */       manager.callEvent(eventRedstone);
/*     */       
/* 112 */       flag1 = eventRedstone.getNewCurrent() > 0;
/* 113 */       j = eventRedstone.getNewCurrent();
/*     */     }
/*     */     
/*     */ 
/* 117 */     if (i != j) {
/* 118 */       iblockdata = a(iblockdata, j);
/* 119 */       world.setTypeAndData(blockposition, iblockdata, 2);
/* 120 */       e(world, blockposition);
/* 121 */       world.b(blockposition, blockposition);
/*     */     }
/*     */     
/* 124 */     if ((!flag1) && (flag)) {
/* 125 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.1D, blockposition.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
/* 126 */     } else if ((flag1) && (!flag)) {
/* 127 */       world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.1D, blockposition.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
/*     */     }
/*     */     
/* 130 */     if (flag1) {
/* 131 */       world.a(blockposition, this, a(world));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected AxisAlignedBB getBoundingBox(BlockPosition blockposition)
/*     */   {
/* 139 */     return new AxisAlignedBB(blockposition.getX() + 0.125F, blockposition.getY(), blockposition.getZ() + 0.125F, blockposition.getX() + 1 - 0.125F, blockposition.getY() + 0.25D, blockposition.getZ() + 1 - 0.125F);
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 143 */     if (e(iblockdata) > 0) {
/* 144 */       e(world, blockposition);
/*     */     }
/*     */     
/* 147 */     super.remove(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   protected void e(World world, BlockPosition blockposition) {
/* 151 */     world.applyPhysics(blockposition, this);
/* 152 */     world.applyPhysics(blockposition.down(), this);
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 156 */     return e(iblockdata);
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 160 */     return enumdirection == EnumDirection.UP ? e(iblockdata) : 0;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 164 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void j()
/*     */   {
/* 172 */     a(0.0F, 0.375F, 0.0F, 1.0F, 0.625F, 1.0F);
/*     */   }
/*     */   
/*     */   public int k() {
/* 176 */     return 1;
/*     */   }
/*     */   
/*     */   protected abstract int f(World paramWorld, BlockPosition paramBlockPosition);
/*     */   
/*     */   protected abstract int e(IBlockData paramIBlockData);
/*     */   
/*     */   protected abstract IBlockData a(IBlockData paramIBlockData, int paramInt);
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPressurePlateAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */