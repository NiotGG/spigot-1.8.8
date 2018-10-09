/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ 
/*     */ public class BlockLever extends Block
/*     */ {
/*   9 */   public static final BlockStateEnum<EnumLeverPosition> FACING = BlockStateEnum.of("facing", EnumLeverPosition.class);
/*  10 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*     */   
/*     */   protected BlockLever() {
/*  13 */     super(Material.ORIENTABLE);
/*  14 */     j(this.blockStateList.getBlockData().set(FACING, EnumLeverPosition.NORTH).set(POWERED, Boolean.valueOf(false)));
/*  15 */     a(CreativeModeTab.d);
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  19 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  23 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  27 */     return false;
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/*  31 */     return a(world, blockposition, enumdirection.opposite());
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition) {
/*  35 */     EnumDirection[] aenumdirection = EnumDirection.values();
/*  36 */     int i = aenumdirection.length;
/*     */     
/*  38 */     for (int j = 0; j < i; j++) {
/*  39 */       EnumDirection enumdirection = aenumdirection[j];
/*     */       
/*  41 */       if (a(world, blockposition, enumdirection)) {
/*  42 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  46 */     return false;
/*     */   }
/*     */   
/*     */   protected static boolean a(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/*  50 */     return BlockButtonAbstract.a(world, blockposition, enumdirection);
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/*  54 */     IBlockData iblockdata = getBlockData().set(POWERED, Boolean.valueOf(false));
/*     */     
/*  56 */     if (a(world, blockposition, enumdirection.opposite())) {
/*  57 */       return iblockdata.set(FACING, EnumLeverPosition.a(enumdirection, entityliving.getDirection()));
/*     */     }
/*  59 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/*     */     EnumDirection enumdirection1;
/*     */     do
/*     */     {
/*  64 */       if (!iterator.hasNext()) {
/*  65 */         if (World.a(world, blockposition.down())) {
/*  66 */           return iblockdata.set(FACING, EnumLeverPosition.a(EnumDirection.UP, entityliving.getDirection()));
/*     */         }
/*     */         
/*  69 */         return iblockdata;
/*     */       }
/*     */       
/*  72 */       enumdirection1 = (EnumDirection)iterator.next();
/*  73 */     } while ((enumdirection1 == enumdirection) || (!a(world, blockposition, enumdirection1.opposite())));
/*     */     
/*  75 */     return iblockdata.set(FACING, EnumLeverPosition.a(enumdirection1, entityliving.getDirection()));
/*     */   }
/*     */   
/*     */   public static int a(EnumDirection enumdirection)
/*     */   {
/*  80 */     switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*     */     case 1: 
/*  82 */       return 0;
/*     */     
/*     */     case 2: 
/*  85 */       return 5;
/*     */     
/*     */     case 3: 
/*  88 */       return 4;
/*     */     
/*     */     case 4: 
/*  91 */       return 3;
/*     */     
/*     */     case 5: 
/*  94 */       return 2;
/*     */     
/*     */     case 6: 
/*  97 */       return 1;
/*     */     }
/*     */     
/* 100 */     return -1;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/* 105 */     if ((e(world, blockposition, iblockdata)) && (!a(world, blockposition, ((EnumLeverPosition)iblockdata.get(FACING)).c().opposite()))) {
/* 106 */       b(world, blockposition, iblockdata, 0);
/* 107 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean e(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 113 */     if (canPlace(world, blockposition)) {
/* 114 */       return true;
/*     */     }
/* 116 */     b(world, blockposition, iblockdata, 0);
/* 117 */     world.setAir(blockposition);
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition)
/*     */   {
/* 123 */     float f = 0.1875F;
/*     */     
/* 125 */     switch (SyntheticClass_1.b[((EnumLeverPosition)iblockaccess.getType(blockposition).get(FACING)).ordinal()]) {
/*     */     case 1: 
/* 127 */       a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
/* 128 */       break;
/*     */     
/*     */     case 2: 
/* 131 */       a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
/* 132 */       break;
/*     */     
/*     */     case 3: 
/* 135 */       a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
/* 136 */       break;
/*     */     
/*     */     case 4: 
/* 139 */       a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
/* 140 */       break;
/*     */     
/*     */     case 5: 
/*     */     case 6: 
/* 144 */       f = 0.25F;
/* 145 */       a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
/* 146 */       break;
/*     */     
/*     */     case 7: 
/*     */     case 8: 
/* 150 */       f = 0.25F;
/* 151 */       a(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/* 157 */     if (world.isClientSide) {
/* 158 */       return true;
/*     */     }
/*     */     
/* 161 */     boolean powered = ((Boolean)iblockdata.get(POWERED)).booleanValue();
/* 162 */     org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 163 */     int old = powered ? 15 : 0;
/* 164 */     int current = !powered ? 15 : 0;
/*     */     
/* 166 */     BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
/* 167 */     world.getServer().getPluginManager().callEvent(eventRedstone);
/*     */     
/* 169 */     if ((eventRedstone.getNewCurrent() > 0 ? 1 : 0) != (powered ? 0 : 1)) {
/* 170 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 174 */     iblockdata = iblockdata.a(POWERED);
/* 175 */     world.setTypeAndData(blockposition, iblockdata, 3);
/* 176 */     world.makeSound(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, "random.click", 0.3F, ((Boolean)iblockdata.get(POWERED)).booleanValue() ? 0.6F : 0.5F);
/* 177 */     world.applyPhysics(blockposition, this);
/* 178 */     EnumDirection enumdirection1 = ((EnumLeverPosition)iblockdata.get(FACING)).c();
/*     */     
/* 180 */     world.applyPhysics(blockposition.shift(enumdirection1.opposite()), this);
/* 181 */     return true;
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 186 */     if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 187 */       world.applyPhysics(blockposition, this);
/* 188 */       EnumDirection enumdirection = ((EnumLeverPosition)iblockdata.get(FACING)).c();
/*     */       
/* 190 */       world.applyPhysics(blockposition.shift(enumdirection.opposite()), this);
/*     */     }
/*     */     
/* 193 */     super.remove(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 197 */     return ((Boolean)iblockdata.get(POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */   
/*     */   public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection) {
/* 201 */     return ((EnumLeverPosition)iblockdata.get(FACING)).c() == enumdirection ? 15 : !((Boolean)iblockdata.get(POWERED)).booleanValue() ? 0 : 0;
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 205 */     return true;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 209 */     return getBlockData().set(FACING, EnumLeverPosition.a(i & 0x7)).set(POWERED, Boolean.valueOf((i & 0x8) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 213 */     byte b0 = 0;
/* 214 */     int i = b0 | ((EnumLeverPosition)iblockdata.get(FACING)).a();
/*     */     
/* 216 */     if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 217 */       i |= 0x8;
/*     */     }
/*     */     
/* 220 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 224 */     return new BlockStateList(this, new IBlockState[] { FACING, POWERED });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/*     */     static final int[] a;
/*     */     static final int[] b;
/* 231 */     static final int[] c = new int[EnumDirection.EnumAxis.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 235 */         c[EnumDirection.EnumAxis.X.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 241 */         c[EnumDirection.EnumAxis.Z.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */ 
/* 246 */       b = new int[BlockLever.EnumLeverPosition.values().length];
/*     */       try
/*     */       {
/* 249 */         b[BlockLever.EnumLeverPosition.EAST.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 255 */         b[BlockLever.EnumLeverPosition.WEST.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 261 */         b[BlockLever.EnumLeverPosition.SOUTH.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */       
/*     */       try
/*     */       {
/* 267 */         b[BlockLever.EnumLeverPosition.NORTH.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError6) {}
/*     */       
/*     */       try
/*     */       {
/* 273 */         b[BlockLever.EnumLeverPosition.UP_Z.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError7) {}
/*     */       
/*     */       try
/*     */       {
/* 279 */         b[BlockLever.EnumLeverPosition.UP_X.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError8) {}
/*     */       
/*     */       try
/*     */       {
/* 285 */         b[BlockLever.EnumLeverPosition.DOWN_X.ordinal()] = 7;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError9) {}
/*     */       
/*     */       try
/*     */       {
/* 291 */         b[BlockLever.EnumLeverPosition.DOWN_Z.ordinal()] = 8;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError10) {}
/*     */       
/*     */ 
/* 296 */       a = new int[EnumDirection.values().length];
/*     */       try
/*     */       {
/* 299 */         a[EnumDirection.DOWN.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError11) {}
/*     */       
/*     */       try
/*     */       {
/* 305 */         a[EnumDirection.UP.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError12) {}
/*     */       
/*     */       try
/*     */       {
/* 311 */         a[EnumDirection.NORTH.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError13) {}
/*     */       
/*     */       try
/*     */       {
/* 317 */         a[EnumDirection.SOUTH.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError14) {}
/*     */       
/*     */       try
/*     */       {
/* 323 */         a[EnumDirection.WEST.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError15) {}
/*     */       
/*     */       try
/*     */       {
/* 329 */         a[EnumDirection.EAST.ordinal()] = 6;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError16) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumLeverPosition
/*     */     implements INamable
/*     */   {
/* 339 */     DOWN_X(0, "down_x", EnumDirection.DOWN),  EAST(1, "east", EnumDirection.EAST),  WEST(2, "west", EnumDirection.WEST),  SOUTH(3, "south", EnumDirection.SOUTH),  NORTH(4, "north", EnumDirection.NORTH),  UP_Z(5, "up_z", EnumDirection.UP),  UP_X(6, "up_x", EnumDirection.UP),  DOWN_Z(7, "down_z", EnumDirection.DOWN);
/*     */     
/*     */     private static final EnumLeverPosition[] i;
/*     */     private final int j;
/*     */     private final String k;
/*     */     private final EnumDirection l;
/*     */     
/*     */     private EnumLeverPosition(int i, String s, EnumDirection enumdirection) {
/* 347 */       this.j = i;
/* 348 */       this.k = s;
/* 349 */       this.l = enumdirection;
/*     */     }
/*     */     
/*     */     public int a() {
/* 353 */       return this.j;
/*     */     }
/*     */     
/*     */     public EnumDirection c() {
/* 357 */       return this.l;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 361 */       return this.k;
/*     */     }
/*     */     
/*     */     public static EnumLeverPosition a(int i) {
/* 365 */       if ((i < 0) || (i >= i.length)) {
/* 366 */         i = 0;
/*     */       }
/*     */       
/* 369 */       return i[i];
/*     */     }
/*     */     
/*     */     public static EnumLeverPosition a(EnumDirection enumdirection, EnumDirection enumdirection1) {
/* 373 */       switch (BlockLever.SyntheticClass_1.a[enumdirection.ordinal()]) {
/*     */       case 1: 
/* 375 */         switch (BlockLever.SyntheticClass_1.c[enumdirection1.k().ordinal()]) {
/*     */         case 1: 
/* 377 */           return DOWN_X;
/*     */         
/*     */         case 2: 
/* 380 */           return DOWN_Z;
/*     */         }
/*     */         
/* 383 */         throw new IllegalArgumentException("Invalid entityFacing " + enumdirection1 + " for facing " + enumdirection);
/*     */       
/*     */ 
/*     */       case 2: 
/* 387 */         switch (BlockLever.SyntheticClass_1.c[enumdirection1.k().ordinal()]) {
/*     */         case 1: 
/* 389 */           return UP_X;
/*     */         
/*     */         case 2: 
/* 392 */           return UP_Z;
/*     */         }
/*     */         
/* 395 */         throw new IllegalArgumentException("Invalid entityFacing " + enumdirection1 + " for facing " + enumdirection);
/*     */       
/*     */ 
/*     */       case 3: 
/* 399 */         return NORTH;
/*     */       
/*     */       case 4: 
/* 402 */         return SOUTH;
/*     */       
/*     */       case 5: 
/* 405 */         return WEST;
/*     */       
/*     */       case 6: 
/* 408 */         return EAST;
/*     */       }
/*     */       
/* 411 */       throw new IllegalArgumentException("Invalid facing: " + enumdirection);
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 416 */       return this.k;
/*     */     }
/*     */     
/*     */     static
/*     */     {
/* 341 */       i = new EnumLeverPosition[values().length];
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 420 */       EnumLeverPosition[] ablocklever_enumleverposition = values();
/* 421 */       int i = ablocklever_enumleverposition.length;
/*     */       
/* 423 */       for (int j = 0; j < i; j++) {
/* 424 */         EnumLeverPosition blocklever_enumleverposition = ablocklever_enumleverposition[j];
/*     */         
/* 426 */         i[blocklever_enumleverposition.a()] = blocklever_enumleverposition;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockLever.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */