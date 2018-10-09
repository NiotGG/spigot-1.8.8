/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.BlockStateListPopulator;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockPumpkin
/*     */   extends BlockDirectional
/*     */ {
/*     */   private ShapeDetector snowGolemPart;
/*     */   private ShapeDetector snowGolem;
/*     */   private ShapeDetector ironGolemPart;
/*     */   private ShapeDetector ironGolem;
/*  17 */   private static final Predicate<IBlockData> Q = new Predicate() {
/*     */     public boolean a(IBlockData iblockdata) {
/*  19 */       return (iblockdata != null) && ((iblockdata.getBlock() == Blocks.PUMPKIN) || (iblockdata.getBlock() == Blocks.LIT_PUMPKIN));
/*     */     }
/*     */     
/*     */     public boolean apply(Object object) {
/*  23 */       return a((IBlockData)object);
/*     */     }
/*     */   };
/*     */   
/*     */   protected BlockPumpkin() {
/*  28 */     super(Material.PUMPKIN, MaterialMapColor.q);
/*  29 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
/*  30 */     a(true);
/*  31 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  35 */     super.onPlace(world, blockposition, iblockdata);
/*  36 */     f(world, blockposition);
/*     */   }
/*     */   
/*     */   public boolean e(World world, BlockPosition blockposition) {
/*  40 */     return (getDetectorSnowGolemPart().a(world, blockposition) != null) || (getDetectorIronGolemPart().a(world, blockposition) != null);
/*     */   }
/*     */   
/*     */ 
/*     */   private void f(World world, BlockPosition blockposition)
/*     */   {
/*     */     ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection;
/*     */     
/*  48 */     if ((shapedetector_shapedetectorcollection = getDetectorSnowGolem().a(world, blockposition)) != null) {
/*  49 */       BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
/*  50 */       for (int i = 0; i < getDetectorSnowGolem().b(); i++) {
/*  51 */         ShapeDetectorBlock shapedetectorblock = shapedetector_shapedetectorcollection.a(0, i, 0);
/*     */         
/*     */ 
/*     */ 
/*  55 */         BlockPosition pos = shapedetectorblock.getPosition();
/*  56 */         blockList.setTypeId(pos.getX(), pos.getY(), pos.getZ(), 0);
/*     */       }
/*     */       
/*     */ 
/*  60 */       EntitySnowman entitysnowman = new EntitySnowman(world);
/*  61 */       BlockPosition blockposition1 = shapedetector_shapedetectorcollection.a(0, 2, 0).getPosition();
/*     */       
/*  63 */       entitysnowman.setPositionRotation(blockposition1.getX() + 0.5D, blockposition1.getY() + 0.05D, blockposition1.getZ() + 0.5D, 0.0F, 0.0F);
/*     */       
/*  65 */       if (world.addEntity(entitysnowman, CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN)) {
/*  66 */         blockList.updateList();
/*     */         
/*  68 */         for (int j = 0; j < 120; j++) {
/*  69 */           world.addParticle(EnumParticle.SNOW_SHOVEL, blockposition1.getX() + world.random.nextDouble(), blockposition1.getY() + world.random.nextDouble() * 2.5D, blockposition1.getZ() + world.random.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/*  72 */         for (j = 0; j < getDetectorSnowGolem().b(); j++) {
/*  73 */           ShapeDetectorBlock shapedetectorblock1 = shapedetector_shapedetectorcollection.a(0, j, 0);
/*     */           
/*  75 */           world.update(shapedetectorblock1.getPosition(), Blocks.AIR);
/*     */         }
/*     */       }
/*  78 */     } else if ((shapedetector_shapedetectorcollection = getDetectorIronGolem().a(world, blockposition)) != null) {
/*  79 */       BlockStateListPopulator blockList = new BlockStateListPopulator(world.getWorld());
/*  80 */       for (int i = 0; i < getDetectorIronGolem().c(); i++) {
/*  81 */         for (int k = 0; k < getDetectorIronGolem().b(); k++)
/*     */         {
/*     */ 
/*  84 */           BlockPosition pos = shapedetector_shapedetectorcollection.a(i, k, 0).getPosition();
/*  85 */           blockList.setTypeId(pos.getX(), pos.getY(), pos.getZ(), 0);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  90 */       BlockPosition blockposition2 = shapedetector_shapedetectorcollection.a(1, 2, 0).getPosition();
/*  91 */       EntityIronGolem entityirongolem = new EntityIronGolem(world);
/*     */       
/*  93 */       entityirongolem.setPlayerCreated(true);
/*  94 */       entityirongolem.setPositionRotation(blockposition2.getX() + 0.5D, blockposition2.getY() + 0.05D, blockposition2.getZ() + 0.5D, 0.0F, 0.0F);
/*     */       
/*     */ 
/*  97 */       if (world.addEntity(entityirongolem, CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM)) {
/*  98 */         blockList.updateList();
/*     */         
/* 100 */         for (int j = 0; j < 120; j++) {
/* 101 */           world.addParticle(EnumParticle.SNOWBALL, blockposition2.getX() + world.random.nextDouble(), blockposition2.getY() + world.random.nextDouble() * 3.9D, blockposition2.getZ() + world.random.nextDouble(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/* 104 */         for (j = 0; j < getDetectorIronGolem().c(); j++) {
/* 105 */           for (int l = 0; l < getDetectorIronGolem().b(); l++) {
/* 106 */             ShapeDetectorBlock shapedetectorblock2 = shapedetector_shapedetectorcollection.a(j, l, 0);
/*     */             
/* 108 */             world.update(shapedetectorblock2.getPosition(), Blocks.AIR);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canPlace(World world, BlockPosition blockposition)
/*     */   {
/* 117 */     return (world.getType(blockposition).getBlock().material.isReplaceable()) && (World.a(world, blockposition.down()));
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2, int i, EntityLiving entityliving) {
/* 121 */     return getBlockData().set(FACING, entityliving.getDirection().opposite());
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/* 125 */     return getBlockData().set(FACING, EnumDirection.fromType2(i));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 129 */     return ((EnumDirection)iblockdata.get(FACING)).b();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 133 */     return new BlockStateList(this, new IBlockState[] { FACING });
/*     */   }
/*     */   
/*     */   protected ShapeDetector getDetectorSnowGolemPart() {
/* 137 */     if (this.snowGolemPart == null) {
/* 138 */       this.snowGolemPart = ShapeDetectorBuilder.a().a(new String[] { " ", "#", "#" }).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SNOW))).b();
/*     */     }
/*     */     
/* 141 */     return this.snowGolemPart;
/*     */   }
/*     */   
/*     */   protected ShapeDetector getDetectorSnowGolem() {
/* 145 */     if (this.snowGolem == null) {
/* 146 */       this.snowGolem = ShapeDetectorBuilder.a().a(new String[] { "^", "#", "#" }).a('^', ShapeDetectorBlock.a(Q)).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.SNOW))).b();
/*     */     }
/*     */     
/* 149 */     return this.snowGolem;
/*     */   }
/*     */   
/*     */   protected ShapeDetector getDetectorIronGolemPart() {
/* 153 */     if (this.ironGolemPart == null) {
/* 154 */       this.ironGolemPart = ShapeDetectorBuilder.a().a(new String[] { "~ ~", "###", "~#~" }).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.IRON_BLOCK))).a('~', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.AIR))).b();
/*     */     }
/*     */     
/* 157 */     return this.ironGolemPart;
/*     */   }
/*     */   
/*     */   protected ShapeDetector getDetectorIronGolem() {
/* 161 */     if (this.ironGolem == null) {
/* 162 */       this.ironGolem = ShapeDetectorBuilder.a().a(new String[] { "~^~", "###", "~#~" }).a('^', ShapeDetectorBlock.a(Q)).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.IRON_BLOCK))).a('~', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.AIR))).b();
/*     */     }
/*     */     
/* 165 */     return this.ironGolem;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPumpkin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */