/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockPoweredRail extends BlockMinecartTrackAbstract
/*     */ {
/*   9 */   public static final BlockStateEnum<BlockMinecartTrackAbstract.EnumTrackPosition> SHAPE = BlockStateEnum.a("shape", BlockMinecartTrackAbstract.EnumTrackPosition.class, new Predicate() {
/*     */     public boolean a(BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition) {
/*  11 */       return (blockminecarttrackabstract_enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST) && (blockminecarttrackabstract_enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST) && (blockminecarttrackabstract_enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST) && (blockminecarttrackabstract_enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST);
/*     */     }
/*     */     
/*     */     public boolean apply(Object object) {
/*  15 */       return a((BlockMinecartTrackAbstract.EnumTrackPosition)object);
/*     */     }
/*   9 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  18 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*     */   
/*     */   protected BlockPoweredRail() {
/*  21 */     super(true);
/*  22 */     j(this.blockStateList.getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH).set(POWERED, Boolean.valueOf(false)));
/*     */   }
/*     */   
/*     */   protected boolean a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag, int i) {
/*  26 */     if (i >= 8) {
/*  27 */       return false;
/*     */     }
/*  29 */     int j = blockposition.getX();
/*  30 */     int k = blockposition.getY();
/*  31 */     int l = blockposition.getZ();
/*  32 */     boolean flag1 = true;
/*  33 */     BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition = (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(SHAPE);
/*     */     
/*  35 */     switch (SyntheticClass_1.a[blockminecarttrackabstract_enumtrackposition.ordinal()]) {
/*     */     case 1: 
/*  37 */       if (flag) {
/*  38 */         l++;
/*     */       } else {
/*  40 */         l--;
/*     */       }
/*  42 */       break;
/*     */     
/*     */     case 2: 
/*  45 */       if (flag) {
/*  46 */         j--;
/*     */       } else {
/*  48 */         j++;
/*     */       }
/*  50 */       break;
/*     */     
/*     */     case 3: 
/*  53 */       if (flag) {
/*  54 */         j--;
/*     */       } else {
/*  56 */         j++;
/*  57 */         k++;
/*  58 */         flag1 = false;
/*     */       }
/*     */       
/*  61 */       blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
/*  62 */       break;
/*     */     
/*     */     case 4: 
/*  65 */       if (flag) {
/*  66 */         j--;
/*  67 */         k++;
/*  68 */         flag1 = false;
/*     */       } else {
/*  70 */         j++;
/*     */       }
/*     */       
/*  73 */       blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST;
/*  74 */       break;
/*     */     
/*     */     case 5: 
/*  77 */       if (flag) {
/*  78 */         l++;
/*     */       } else {
/*  80 */         l--;
/*  81 */         k++;
/*  82 */         flag1 = false;
/*     */       }
/*     */       
/*  85 */       blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/*  86 */       break;
/*     */     
/*     */     case 6: 
/*  89 */       if (flag) {
/*  90 */         l++;
/*  91 */         k++;
/*  92 */         flag1 = false;
/*     */       } else {
/*  94 */         l--;
/*     */       }
/*     */       
/*  97 */       blockminecarttrackabstract_enumtrackposition = BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/*     */     }
/*     */     
/* 100 */     return a(world, new BlockPosition(j, k, l), flag, i, blockminecarttrackabstract_enumtrackposition);
/*     */   }
/*     */   
/*     */   protected boolean a(World world, BlockPosition blockposition, boolean flag, int i, BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition)
/*     */   {
/* 105 */     IBlockData iblockdata = world.getType(blockposition);
/*     */     
/* 107 */     if (iblockdata.getBlock() != this) {
/* 108 */       return false;
/*     */     }
/* 110 */     BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition1 = (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(SHAPE);
/*     */     
/* 112 */     return (blockminecarttrackabstract_enumtrackposition != BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST) || ((blockminecarttrackabstract_enumtrackposition1 != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH) && (blockminecarttrackabstract_enumtrackposition1 != BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH) && (blockminecarttrackabstract_enumtrackposition1 != BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH));
/*     */   }
/*     */   
/*     */   protected void b(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/* 117 */     boolean flag = ((Boolean)iblockdata.get(POWERED)).booleanValue();
/* 118 */     boolean flag1 = (world.isBlockIndirectlyPowered(blockposition)) || (a(world, blockposition, iblockdata, true, 0)) || (a(world, blockposition, iblockdata, false, 0));
/*     */     
/* 120 */     if (flag1 != flag)
/*     */     {
/* 122 */       int power = ((Boolean)iblockdata.get(POWERED)).booleanValue() ? 15 : 0;
/* 123 */       int newPower = CraftEventFactory.callRedstoneChange(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), power, 15 - power).getNewCurrent();
/* 124 */       if (newPower == power) {
/* 125 */         return;
/*     */       }
/*     */       
/* 128 */       world.setTypeAndData(blockposition, iblockdata.set(POWERED, Boolean.valueOf(flag1)), 3);
/* 129 */       world.applyPhysics(blockposition.down(), this);
/* 130 */       if (((BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(SHAPE)).c()) {
/* 131 */         world.applyPhysics(blockposition.up(), this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockState<BlockMinecartTrackAbstract.EnumTrackPosition> n()
/*     */   {
/* 138 */     return SHAPE;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 142 */     return getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.a(i & 0x7)).set(POWERED, Boolean.valueOf((i & 0x8) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 146 */     byte b0 = 0;
/* 147 */     int i = b0 | ((BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(SHAPE)).a();
/*     */     
/* 149 */     if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 150 */       i |= 0x8;
/*     */     }
/*     */     
/* 153 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 157 */     return new BlockStateList(this, new IBlockState[] { SHAPE, POWERED });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 162 */     static final int[] a = new int[BlockMinecartTrackAbstract.EnumTrackPosition.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 166 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 172 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.EAST_WEST.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 178 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 184 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 190 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */       
/*     */       try
/*     */       {
/* 196 */         a[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPoweredRail.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */