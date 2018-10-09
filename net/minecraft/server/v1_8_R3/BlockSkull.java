/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.BlockStateListPopulator;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*     */ 
/*     */ 
/*     */ public class BlockSkull
/*     */   extends BlockContainer
/*     */ {
/*  14 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
/*  15 */   public static final BlockStateBoolean NODROP = BlockStateBoolean.of("nodrop");
/*  16 */   private static final Predicate<ShapeDetectorBlock> N = new Predicate() {
/*     */     public boolean a(ShapeDetectorBlock shapedetectorblock) {
/*  18 */       return (shapedetectorblock.a() != null) && (shapedetectorblock.a().getBlock() == Blocks.SKULL) && ((shapedetectorblock.b() instanceof TileEntitySkull)) && (((TileEntitySkull)shapedetectorblock.b()).getSkullType() == 1);
/*     */     }
/*     */     
/*     */     public boolean apply(Object object) {
/*  22 */       return a((ShapeDetectorBlock)object);
/*     */     }
/*     */   };
/*     */   private ShapeDetector O;
/*     */   private ShapeDetector P;
/*     */   
/*     */   protected BlockSkull() {
/*  29 */     super(Material.ORIENTABLE);
/*  30 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(NODROP, Boolean.valueOf(false)));
/*  31 */     a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  35 */     return LocaleI18n.get("tile.skull.skeleton.name");
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  39 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  43 */     return false;
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  47 */     switch (SyntheticClass_1.a[((EnumDirection)iblockaccess.getType(blockposition).get(FACING)).ordinal()]) {
/*     */     case 1: 
/*     */     default: 
/*  50 */       a(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
/*  51 */       break;
/*     */     
/*     */     case 2: 
/*  54 */       a(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
/*  55 */       break;
/*     */     
/*     */     case 3: 
/*  58 */       a(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
/*  59 */       break;
/*     */     
/*     */     case 4: 
/*  62 */       a(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
/*  63 */       break;
/*     */     
/*     */     case 5: 
/*  66 */       a(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
/*     */     }
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  72 */     updateShape(world, blockposition);
/*  73 */     return super.a(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/*  77 */     return getBlockData().set(FACING, entityliving.getDirection()).set(NODROP, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*  81 */     return new TileEntitySkull();
/*     */   }
/*     */   
/*     */   public int getDropData(World world, BlockPosition blockposition) {
/*  85 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  87 */     return (tileentity instanceof TileEntitySkull) ? ((TileEntitySkull)tileentity).getSkullType() : super.getDropData(world, blockposition);
/*     */   }
/*     */   
/*     */ 
/*     */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i)
/*     */   {
/*  93 */     if (world.random.nextFloat() < f) {
/*  94 */       ItemStack itemstack = new ItemStack(Items.SKULL, 1, getDropData(world, blockposition));
/*  95 */       TileEntitySkull tileentityskull = (TileEntitySkull)world.getTileEntity(blockposition);
/*     */       
/*  97 */       if ((tileentityskull.getSkullType() == 3) && (tileentityskull.getGameProfile() != null)) {
/*  98 */         itemstack.setTag(new NBTTagCompound());
/*  99 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */         
/* 101 */         GameProfileSerializer.serialize(nbttagcompound, tileentityskull.getGameProfile());
/* 102 */         itemstack.getTag().set("SkullOwner", nbttagcompound);
/*     */       }
/*     */       
/* 105 */       a(world, blockposition, itemstack);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman)
/*     */   {
/* 111 */     if (entityhuman.abilities.canInstantlyBuild) {
/* 112 */       iblockdata = iblockdata.set(NODROP, Boolean.valueOf(true));
/* 113 */       world.setTypeAndData(blockposition, iblockdata, 4);
/*     */     }
/*     */     
/* 116 */     super.a(world, blockposition, iblockdata, entityhuman);
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 120 */     if (!world.isClientSide)
/*     */     {
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
/* 143 */       super.remove(world, blockposition, iblockdata);
/*     */     }
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/* 148 */     return Items.SKULL;
/*     */   }
/*     */   
/*     */   public boolean b(World world, BlockPosition blockposition, ItemStack itemstack) {
/* 152 */     return l().a(world, blockposition) != null;
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, TileEntitySkull tileentityskull) {
/* 156 */     if (world.captureBlockStates) return;
/* 157 */     if ((tileentityskull.getSkullType() == 1) && (blockposition.getY() >= 2) && (world.getDifficulty() != EnumDifficulty.PEACEFUL) && (!world.isClientSide)) {
/* 158 */       ShapeDetector shapedetector = n();
/* 159 */       ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = shapedetector.a(world, blockposition);
/*     */       
/* 161 */       if (shapedetector_shapedetectorcollection != null)
/*     */       {
/* 163 */         BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
/*     */         
/*     */ 
/* 166 */         for (int i = 0; i < 3; i++) {
/* 167 */           ShapeDetectorBlock shapedetectorblock = shapedetector_shapedetectorcollection.a(i, 0, 0);
/*     */           
/*     */ 
/*     */ 
/* 171 */           BlockPosition pos = shapedetectorblock.getPosition();
/* 172 */           IBlockData data = shapedetectorblock.a().set(NODROP, Boolean.valueOf(true));
/* 173 */           blockList.setTypeAndData(pos.getX(), pos.getY(), pos.getZ(), data.getBlock(), data.getBlock().toLegacyData(data), 2);
/*     */         }
/*     */         
/*     */ 
/* 177 */         for (i = 0; i < shapedetector.c(); i++) {
/* 178 */           for (int j = 0; j < shapedetector.b(); j++) {
/* 179 */             ShapeDetectorBlock shapedetectorblock1 = shapedetector_shapedetectorcollection.a(i, j, 0);
/*     */             
/*     */ 
/*     */ 
/* 183 */             BlockPosition pos = shapedetectorblock1.getPosition();
/* 184 */             blockList.setTypeAndData(pos.getX(), pos.getY(), pos.getZ(), Blocks.AIR, 0, 2);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 189 */         BlockPosition blockposition1 = shapedetector_shapedetectorcollection.a(1, 0, 0).getPosition();
/* 190 */         EntityWither entitywither = new EntityWither(world);
/* 191 */         BlockPosition blockposition2 = shapedetector_shapedetectorcollection.a(1, 2, 0).getPosition();
/*     */         
/* 193 */         entitywither.setPositionRotation(blockposition2.getX() + 0.5D, blockposition2.getY() + 0.55D, blockposition2.getZ() + 0.5D, shapedetector_shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X ? 0.0F : 90.0F, 0.0F);
/* 194 */         entitywither.aI = (shapedetector_shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X ? 0.0F : 90.0F);
/* 195 */         entitywither.n();
/* 196 */         Iterator iterator = world.a(EntityHuman.class, entitywither.getBoundingBox().grow(50.0D, 50.0D, 50.0D)).iterator();
/*     */         
/*     */ 
/* 199 */         if (world.addEntity(entitywither, CreatureSpawnEvent.SpawnReason.BUILD_WITHER)) {
/* 200 */           blockList.updateList();
/*     */           
/* 202 */           while (iterator.hasNext()) {
/* 203 */             EntityHuman entityhuman = (EntityHuman)iterator.next();
/*     */             
/* 205 */             entityhuman.b(AchievementList.I);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 210 */           for (int k = 0; k < 120; k++) {
/* 211 */             world.addParticle(EnumParticle.SNOWBALL, blockposition1.getX() + world.random.nextDouble(), blockposition1.getY() - 2 + world.random.nextDouble() * 3.9D, blockposition1.getZ() + world.random.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           }
/*     */           
/* 214 */           for (k = 0; k < shapedetector.c(); k++) {
/* 215 */             for (int l = 0; l < shapedetector.b(); l++) {
/* 216 */               ShapeDetectorBlock shapedetectorblock2 = shapedetector_shapedetectorcollection.a(k, l, 0);
/*     */               
/* 218 */               world.update(shapedetectorblock2.getPosition(), Blocks.AIR);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i)
/*     */   {
/* 228 */     return getBlockData().set(FACING, EnumDirection.fromType1(i & 0x7)).set(NODROP, Boolean.valueOf((i & 0x8) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 232 */     byte b0 = 0;
/* 233 */     int i = b0 | ((EnumDirection)iblockdata.get(FACING)).a();
/*     */     
/* 235 */     if (((Boolean)iblockdata.get(NODROP)).booleanValue()) {
/* 236 */       i |= 0x8;
/*     */     }
/*     */     
/* 239 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 243 */     return new BlockStateList(this, new IBlockState[] { FACING, NODROP });
/*     */   }
/*     */   
/*     */   protected ShapeDetector l() {
/* 247 */     if (this.O == null) {
/* 248 */       this.O = ShapeDetectorBuilder.a().a(new String[] { "   ", "###", "~#~" }).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SOUL_SAND))).a('~', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.AIR))).b();
/*     */     }
/*     */     
/* 251 */     return this.O;
/*     */   }
/*     */   
/*     */   protected ShapeDetector n() {
/* 255 */     if (this.P == null) {
/* 256 */       this.P = ShapeDetectorBuilder.a().a(new String[] { "^^^", "###", "~#~" }).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SOUL_SAND))).a('^', N).a('~', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.AIR))).b();
/*     */     }
/*     */     
/* 259 */     return this.P;
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 264 */     static final int[] a = new int[EnumDirection.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 268 */         a[EnumDirection.UP.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 274 */         a[EnumDirection.NORTH.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 280 */         a[EnumDirection.SOUTH.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 286 */         a[EnumDirection.WEST.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */       
/*     */       try
/*     */       {
/* 292 */         a[EnumDirection.EAST.ordinal()] = 5;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError5) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */