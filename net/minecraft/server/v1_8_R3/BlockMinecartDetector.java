/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockMinecartDetector extends BlockMinecartTrackAbstract
/*     */ {
/*  11 */   public static final BlockStateEnum<BlockMinecartTrackAbstract.EnumTrackPosition> SHAPE = BlockStateEnum.a("shape", BlockMinecartTrackAbstract.EnumTrackPosition.class, new Predicate() {
/*     */     public boolean a(BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition) {
/*  13 */       return (blockminecarttrackabstract_enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST) && (blockminecarttrackabstract_enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST) && (blockminecarttrackabstract_enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST) && (blockminecarttrackabstract_enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST);
/*     */     }
/*     */     
/*     */     public boolean apply(Object object) {
/*  17 */       return a((BlockMinecartTrackAbstract.EnumTrackPosition)object);
/*     */     }
/*  11 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  20 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*     */   
/*     */   public BlockMinecartDetector() {
/*  23 */     super(true);
/*  24 */     j(this.blockStateList.getBlockData().set(POWERED, Boolean.valueOf(false)).set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH));
/*  25 */     a(true);
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  29 */     return 20;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/*  33 */     return true;
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
/*  37 */     if ((!world.isClientSide) && 
/*  38 */       (!((Boolean)iblockdata.get(POWERED)).booleanValue())) {
/*  39 */       e(world, blockposition, iblockdata);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {}
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/*  47 */     if ((!world.isClientSide) && (((Boolean)iblockdata.get(POWERED)).booleanValue())) {
/*  48 */       e(world, blockposition, iblockdata);
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/*  53 */     return ((Boolean)iblockdata.get(POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/*  57 */     return enumdirection == EnumDirection.UP ? 15 : !((Boolean)iblockdata.get(POWERED)).booleanValue() ? 0 : 0;
/*     */   }
/*     */   
/*     */   private void e(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  61 */     boolean flag = ((Boolean)iblockdata.get(POWERED)).booleanValue();
/*  62 */     boolean flag1 = false;
/*  63 */     List list = a(world, blockposition, EntityMinecartAbstract.class, new Predicate[0]);
/*     */     
/*  65 */     if (!list.isEmpty()) {
/*  66 */       flag1 = true;
/*     */     }
/*     */     
/*     */ 
/*  70 */     if (flag != flag1) {
/*  71 */       org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */       
/*  73 */       BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, flag ? 15 : 0, flag1 ? 15 : 0);
/*  74 */       world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */       
/*  76 */       flag1 = eventRedstone.getNewCurrent() > 0;
/*     */     }
/*     */     
/*     */ 
/*  80 */     if ((flag1) && (!flag)) {
/*  81 */       world.setTypeAndData(blockposition, iblockdata.set(POWERED, Boolean.valueOf(true)), 3);
/*  82 */       world.applyPhysics(blockposition, this);
/*  83 */       world.applyPhysics(blockposition.down(), this);
/*  84 */       world.b(blockposition, blockposition);
/*     */     }
/*     */     
/*  87 */     if ((!flag1) && (flag)) {
/*  88 */       world.setTypeAndData(blockposition, iblockdata.set(POWERED, Boolean.valueOf(false)), 3);
/*  89 */       world.applyPhysics(blockposition, this);
/*  90 */       world.applyPhysics(blockposition.down(), this);
/*  91 */       world.b(blockposition, blockposition);
/*     */     }
/*     */     
/*  94 */     if (flag1) {
/*  95 */       world.a(blockposition, this, a(world));
/*     */     }
/*     */     
/*  98 */     world.updateAdjacentComparators(blockposition, this);
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 102 */     super.onPlace(world, blockposition, iblockdata);
/* 103 */     e(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public IBlockState<BlockMinecartTrackAbstract.EnumTrackPosition> n() {
/* 107 */     return SHAPE;
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/* 111 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World world, BlockPosition blockposition) {
/* 115 */     if (((Boolean)world.getType(blockposition).get(POWERED)).booleanValue()) {
/* 116 */       List list = a(world, blockposition, EntityMinecartCommandBlock.class, new Predicate[0]);
/*     */       
/* 118 */       if (!list.isEmpty()) {
/* 119 */         return ((EntityMinecartCommandBlock)list.get(0)).getCommandBlock().j();
/*     */       }
/*     */       
/* 122 */       List list1 = a(world, blockposition, EntityMinecartAbstract.class, new Predicate[] { IEntitySelector.c });
/*     */       
/* 124 */       if (!list1.isEmpty()) {
/* 125 */         return Container.b((IInventory)list1.get(0));
/*     */       }
/*     */     }
/*     */     
/* 129 */     return 0;
/*     */   }
/*     */   
/*     */   protected <T extends EntityMinecartAbstract> List<T> a(World world, BlockPosition blockposition, Class<T> oclass, Predicate<Entity>... apredicate) {
/* 133 */     AxisAlignedBB axisalignedbb = a(blockposition);
/*     */     
/* 135 */     return apredicate.length != 1 ? world.a(oclass, axisalignedbb) : world.a(oclass, axisalignedbb, apredicate[0]);
/*     */   }
/*     */   
/*     */ 
/*     */   private AxisAlignedBB a(BlockPosition blockposition)
/*     */   {
/* 141 */     return new AxisAlignedBB(blockposition.getX() + 0.2F, blockposition.getY(), blockposition.getZ() + 0.2F, blockposition.getX() + 1 - 0.2F, blockposition.getY() + 1 - 0.2F, blockposition.getZ() + 1 - 0.2F);
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 145 */     return getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.a(i & 0x7)).set(POWERED, Boolean.valueOf((i & 0x8) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 149 */     byte b0 = 0;
/* 150 */     int i = b0 | ((BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(SHAPE)).a();
/*     */     
/* 152 */     if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 153 */       i |= 0x8;
/*     */     }
/*     */     
/* 156 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 160 */     return new BlockStateList(this, new IBlockState[] { SHAPE, POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockMinecartDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */