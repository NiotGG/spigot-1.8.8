/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.bukkit.event.entity.EntityTeleportEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class EntityEnderman extends EntityMonster
/*     */ {
/*  19 */   private static final UUID a = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
/*  20 */   private static final AttributeModifier b = new AttributeModifier(a, "Attacking speed boost", 0.15000000596046448D, 0).a(false);
/*  21 */   private static final Set<Block> c = com.google.common.collect.Sets.newIdentityHashSet();
/*     */   private boolean bm;
/*     */   
/*     */   public EntityEnderman(World world) {
/*  25 */     super(world);
/*  26 */     setSize(0.6F, 2.9F);
/*  27 */     this.S = 1.0F;
/*  28 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  29 */     this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, false));
/*  30 */     this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
/*  31 */     this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  32 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*  33 */     this.goalSelector.a(10, new PathfinderGoalEndermanPlaceBlock(this));
/*  34 */     this.goalSelector.a(11, new PathfinderGoalEndermanPickupBlock(this));
/*  35 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
/*  36 */     this.targetSelector.a(2, new PathfinderGoalPlayerWhoLookedAtTarget(this));
/*  37 */     this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate() {
/*     */       public boolean a(EntityEndermite entityendermite) {
/*  39 */         return entityendermite.n();
/*     */       }
/*     */       
/*     */       public boolean apply(Object object) {
/*  43 */         return a((EntityEndermite)object);
/*     */       }
/*     */     }));
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  49 */     super.initAttributes();
/*  50 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(40.0D);
/*  51 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
/*  52 */     getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(7.0D);
/*  53 */     getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(64.0D);
/*     */   }
/*     */   
/*     */   protected void h() {
/*  57 */     super.h();
/*  58 */     this.datawatcher.a(16, new Short((short)0));
/*  59 */     this.datawatcher.a(17, new Byte((byte)0));
/*  60 */     this.datawatcher.a(18, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  64 */     super.b(nbttagcompound);
/*  65 */     IBlockData iblockdata = getCarried();
/*     */     
/*  67 */     nbttagcompound.setShort("carried", (short)Block.getId(iblockdata.getBlock()));
/*  68 */     nbttagcompound.setShort("carriedData", (short)iblockdata.getBlock().toLegacyData(iblockdata));
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  72 */     super.a(nbttagcompound);
/*     */     IBlockData iblockdata;
/*     */     IBlockData iblockdata;
/*  75 */     if (nbttagcompound.hasKeyOfType("carried", 8)) {
/*  76 */       iblockdata = Block.getByName(nbttagcompound.getString("carried")).fromLegacyData(nbttagcompound.getShort("carriedData") & 0xFFFF);
/*     */     } else {
/*  78 */       iblockdata = Block.getById(nbttagcompound.getShort("carried")).fromLegacyData(nbttagcompound.getShort("carriedData") & 0xFFFF);
/*     */     }
/*     */     
/*  81 */     setCarried(iblockdata);
/*     */   }
/*     */   
/*     */   private boolean c(EntityHuman entityhuman) {
/*  85 */     ItemStack itemstack = entityhuman.inventory.armor[3];
/*     */     
/*  87 */     if ((itemstack != null) && (itemstack.getItem() == Item.getItemOf(Blocks.PUMPKIN))) {
/*  88 */       return false;
/*     */     }
/*  90 */     Vec3D vec3d = entityhuman.d(1.0F).a();
/*  91 */     Vec3D vec3d1 = new Vec3D(this.locX - entityhuman.locX, getBoundingBox().b + this.length / 2.0F - (entityhuman.locY + entityhuman.getHeadHeight()), this.locZ - entityhuman.locZ);
/*  92 */     double d0 = vec3d1.b();
/*     */     
/*  94 */     vec3d1 = vec3d1.a();
/*  95 */     double d1 = vec3d.b(vec3d1);
/*     */     
/*  97 */     return d1 > 1.0D - 0.025D / d0 ? entityhuman.hasLineOfSight(this) : false;
/*     */   }
/*     */   
/*     */   public float getHeadHeight()
/*     */   {
/* 102 */     return 2.55F;
/*     */   }
/*     */   
/*     */   public void m() {
/* 106 */     if (this.world.isClientSide) {
/* 107 */       for (int i = 0; i < 2; i++) {
/* 108 */         this.world.addParticle(EnumParticle.PORTAL, this.locX + (this.random.nextDouble() - 0.5D) * this.width, this.locY + this.random.nextDouble() * this.length - 0.25D, this.locZ + (this.random.nextDouble() - 0.5D) * this.width, (this.random.nextDouble() - 0.5D) * 2.0D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     
/* 112 */     this.aY = false;
/* 113 */     super.m();
/*     */   }
/*     */   
/*     */   protected void E() {
/* 117 */     if (U()) {
/* 118 */       damageEntity(DamageSource.DROWN, 1.0F);
/*     */     }
/*     */     
/* 121 */     if ((co()) && (!this.bm) && (this.random.nextInt(100) == 0)) {
/* 122 */       a(false);
/*     */     }
/*     */     
/* 125 */     if (this.world.w()) {
/* 126 */       float f = c(1.0F);
/*     */       
/* 128 */       if ((f > 0.5F) && (this.world.i(new BlockPosition(this))) && (this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F)) {
/* 129 */         setGoalTarget(null);
/* 130 */         a(false);
/* 131 */         this.bm = false;
/* 132 */         n();
/*     */       }
/*     */     }
/*     */     
/* 136 */     super.E();
/*     */   }
/*     */   
/*     */   protected boolean n() {
/* 140 */     double d0 = this.locX + (this.random.nextDouble() - 0.5D) * 64.0D;
/* 141 */     double d1 = this.locY + (this.random.nextInt(64) - 32);
/* 142 */     double d2 = this.locZ + (this.random.nextDouble() - 0.5D) * 64.0D;
/*     */     
/* 144 */     return k(d0, d1, d2);
/*     */   }
/*     */   
/*     */   protected boolean b(Entity entity) {
/* 148 */     Vec3D vec3d = new Vec3D(this.locX - entity.locX, getBoundingBox().b + this.length / 2.0F - entity.locY + entity.getHeadHeight(), this.locZ - entity.locZ);
/*     */     
/* 150 */     vec3d = vec3d.a();
/* 151 */     double d0 = 16.0D;
/* 152 */     double d1 = this.locX + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.a * d0;
/* 153 */     double d2 = this.locY + (this.random.nextInt(16) - 8) - vec3d.b * d0;
/* 154 */     double d3 = this.locZ + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.c * d0;
/*     */     
/* 156 */     return k(d1, d2, d3);
/*     */   }
/*     */   
/*     */   protected boolean k(double d0, double d1, double d2) {
/* 160 */     double d3 = this.locX;
/* 161 */     double d4 = this.locY;
/* 162 */     double d5 = this.locZ;
/*     */     
/* 164 */     this.locX = d0;
/* 165 */     this.locY = d1;
/* 166 */     this.locZ = d2;
/* 167 */     boolean flag = false;
/* 168 */     BlockPosition blockposition = new BlockPosition(this.locX, this.locY, this.locZ);
/*     */     
/* 170 */     if (this.world.isLoaded(blockposition)) {
/* 171 */       boolean flag1 = false;
/*     */       
/* 173 */       while ((!flag1) && (blockposition.getY() > 0)) {
/* 174 */         BlockPosition blockposition1 = blockposition.down();
/* 175 */         Block block = this.world.getType(blockposition1).getBlock();
/*     */         
/* 177 */         if (block.getMaterial().isSolid()) {
/* 178 */           flag1 = true;
/*     */         } else {
/* 180 */           this.locY -= 1.0D;
/* 181 */           blockposition = blockposition1;
/*     */         }
/*     */       }
/*     */       
/* 185 */       if (flag1)
/*     */       {
/*     */ 
/* 188 */         EntityTeleportEvent teleport = new EntityTeleportEvent(getBukkitEntity(), new Location(this.world.getWorld(), d3, d4, d5), new Location(this.world.getWorld(), this.locX, this.locY, this.locZ));
/* 189 */         this.world.getServer().getPluginManager().callEvent(teleport);
/* 190 */         if (teleport.isCancelled()) {
/* 191 */           return false;
/*     */         }
/*     */         
/* 194 */         Location to = teleport.getTo();
/* 195 */         enderTeleportTo(to.getX(), to.getY(), to.getZ());
/*     */         
/* 197 */         if ((this.world.getCubes(this, getBoundingBox()).isEmpty()) && (!this.world.containsLiquid(getBoundingBox()))) {
/* 198 */           flag = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 203 */     if (!flag) {
/* 204 */       setPosition(d3, d4, d5);
/* 205 */       return false;
/*     */     }
/* 207 */     short short0 = 128;
/*     */     
/* 209 */     for (int i = 0; i < short0; i++) {
/* 210 */       double d6 = i / (short0 - 1.0D);
/* 211 */       float f = (this.random.nextFloat() - 0.5F) * 0.2F;
/* 212 */       float f1 = (this.random.nextFloat() - 0.5F) * 0.2F;
/* 213 */       float f2 = (this.random.nextFloat() - 0.5F) * 0.2F;
/* 214 */       double d7 = d3 + (this.locX - d3) * d6 + (this.random.nextDouble() - 0.5D) * this.width * 2.0D;
/* 215 */       double d8 = d4 + (this.locY - d4) * d6 + this.random.nextDouble() * this.length;
/* 216 */       double d9 = d5 + (this.locZ - d5) * d6 + (this.random.nextDouble() - 0.5D) * this.width * 2.0D;
/*     */       
/* 218 */       this.world.addParticle(EnumParticle.PORTAL, d7, d8, d9, f, f1, f2, new int[0]);
/*     */     }
/*     */     
/* 221 */     this.world.makeSound(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
/* 222 */     makeSound("mob.endermen.portal", 1.0F, 1.0F);
/* 223 */     return true;
/*     */   }
/*     */   
/*     */   protected String z()
/*     */   {
/* 228 */     return co() ? "mob.endermen.scream" : "mob.endermen.idle";
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 232 */     return "mob.endermen.hit";
/*     */   }
/*     */   
/*     */   protected String bp() {
/* 236 */     return "mob.endermen.death";
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 240 */     return Items.ENDER_PEARL;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 244 */     Item item = getLoot();
/*     */     
/* 246 */     if (item != null) {
/* 247 */       int j = this.random.nextInt(2 + i);
/*     */       
/* 249 */       for (int k = 0; k < j; k++) {
/* 250 */         a(item, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setCarried(IBlockData iblockdata)
/*     */   {
/* 257 */     this.datawatcher.watch(16, Short.valueOf((short)(Block.getCombinedId(iblockdata) & 0xFFFF)));
/*     */   }
/*     */   
/*     */   public IBlockData getCarried() {
/* 261 */     return Block.getByCombinedId(this.datawatcher.getShort(16) & 0xFFFF);
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 265 */     if (isInvulnerable(damagesource)) {
/* 266 */       return false;
/*     */     }
/* 268 */     if ((damagesource.getEntity() == null) || (!(damagesource.getEntity() instanceof EntityEndermite))) {
/* 269 */       if (!this.world.isClientSide) {
/* 270 */         a(true);
/*     */       }
/*     */       
/* 273 */       if (((damagesource instanceof EntityDamageSource)) && ((damagesource.getEntity() instanceof EntityHuman))) {
/* 274 */         if (((damagesource.getEntity() instanceof EntityPlayer)) && (((EntityPlayer)damagesource.getEntity()).playerInteractManager.isCreative())) {
/* 275 */           a(false);
/*     */         } else {
/* 277 */           this.bm = true;
/*     */         }
/*     */       }
/*     */       
/* 281 */       if ((damagesource instanceof EntityDamageSourceIndirect)) {
/* 282 */         this.bm = false;
/*     */         
/* 284 */         for (int i = 0; i < 64; i++) {
/* 285 */           if (n()) {
/* 286 */             return true;
/*     */           }
/*     */         }
/*     */         
/* 290 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 294 */     boolean flag = super.damageEntity(damagesource, f);
/*     */     
/* 296 */     if ((damagesource.ignoresArmor()) && (this.random.nextInt(10) != 0)) {
/* 297 */       n();
/*     */     }
/*     */     
/* 300 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean co()
/*     */   {
/* 305 */     return this.datawatcher.getByte(18) > 0;
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 309 */     this.datawatcher.watch(18, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */   
/*     */   static {
/* 313 */     c.add(Blocks.GRASS);
/* 314 */     c.add(Blocks.DIRT);
/* 315 */     c.add(Blocks.SAND);
/* 316 */     c.add(Blocks.GRAVEL);
/* 317 */     c.add(Blocks.YELLOW_FLOWER);
/* 318 */     c.add(Blocks.RED_FLOWER);
/* 319 */     c.add(Blocks.BROWN_MUSHROOM);
/* 320 */     c.add(Blocks.RED_MUSHROOM);
/* 321 */     c.add(Blocks.TNT);
/* 322 */     c.add(Blocks.CACTUS);
/* 323 */     c.add(Blocks.CLAY);
/* 324 */     c.add(Blocks.PUMPKIN);
/* 325 */     c.add(Blocks.MELON_BLOCK);
/* 326 */     c.add(Blocks.MYCELIUM);
/*     */   }
/*     */   
/*     */   static class PathfinderGoalEndermanPickupBlock extends PathfinderGoal
/*     */   {
/*     */     private EntityEnderman enderman;
/*     */     
/*     */     public PathfinderGoalEndermanPickupBlock(EntityEnderman entityenderman) {
/* 334 */       this.enderman = entityenderman;
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 338 */       return this.enderman.world.getGameRules().getBoolean("mobGriefing");
/*     */     }
/*     */     
/*     */     public void e() {
/* 342 */       Random random = this.enderman.bc();
/* 343 */       World world = this.enderman.world;
/* 344 */       int i = MathHelper.floor(this.enderman.locX - 2.0D + random.nextDouble() * 4.0D);
/* 345 */       int j = MathHelper.floor(this.enderman.locY + random.nextDouble() * 3.0D);
/* 346 */       int k = MathHelper.floor(this.enderman.locZ - 2.0D + random.nextDouble() * 4.0D);
/* 347 */       BlockPosition blockposition = new BlockPosition(i, j, k);
/* 348 */       IBlockData iblockdata = world.getType(blockposition);
/* 349 */       Block block = iblockdata.getBlock();
/*     */       
/* 351 */       if (EntityEnderman.c.contains(block))
/*     */       {
/* 353 */         if (!CraftEventFactory.callEntityChangeBlockEvent(this.enderman, this.enderman.world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), org.bukkit.Material.AIR).isCancelled()) {
/* 354 */           this.enderman.setCarried(iblockdata);
/* 355 */           world.setTypeUpdate(blockposition, Blocks.AIR.getBlockData());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalEndermanPlaceBlock
/*     */     extends PathfinderGoal
/*     */   {
/*     */     private EntityEnderman a;
/*     */     
/*     */     public PathfinderGoalEndermanPlaceBlock(EntityEnderman entityenderman)
/*     */     {
/* 368 */       this.a = entityenderman;
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 372 */       return this.a.world.getGameRules().getBoolean("mobGriefing");
/*     */     }
/*     */     
/*     */     public void e() {
/* 376 */       Random random = this.a.bc();
/* 377 */       World world = this.a.world;
/* 378 */       int i = MathHelper.floor(this.a.locX - 1.0D + random.nextDouble() * 2.0D);
/* 379 */       int j = MathHelper.floor(this.a.locY + random.nextDouble() * 2.0D);
/* 380 */       int k = MathHelper.floor(this.a.locZ - 1.0D + random.nextDouble() * 2.0D);
/* 381 */       BlockPosition blockposition = new BlockPosition(i, j, k);
/* 382 */       Block block = world.getType(blockposition).getBlock();
/* 383 */       Block block1 = world.getType(blockposition.down()).getBlock();
/*     */       
/* 385 */       if (a(world, blockposition, this.a.getCarried().getBlock(), block, block1))
/*     */       {
/* 387 */         if (!CraftEventFactory.callEntityChangeBlockEvent(this.a, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this.a.getCarried().getBlock(), this.a.getCarried().getBlock().toLegacyData(this.a.getCarried())).isCancelled()) {
/* 388 */           world.setTypeAndData(blockposition, this.a.getCarried(), 3);
/* 389 */           this.a.setCarried(Blocks.AIR.getBlockData());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     private boolean a(World world, BlockPosition blockposition, Block block, Block block1, Block block2)
/*     */     {
/* 397 */       return block2.getMaterial() == Material.AIR ? false : block1.getMaterial() != Material.AIR ? false : !block.canPlace(world, blockposition) ? false : block2.d();
/*     */     }
/*     */   }
/*     */   
/*     */   static class PathfinderGoalPlayerWhoLookedAtTarget extends PathfinderGoalNearestAttackableTarget
/*     */   {
/*     */     private EntityHuman g;
/*     */     private int h;
/*     */     private int i;
/*     */     private EntityEnderman j;
/*     */     
/*     */     public PathfinderGoalPlayerWhoLookedAtTarget(EntityEnderman entityenderman) {
/* 409 */       super(EntityHuman.class, true);
/* 410 */       this.j = entityenderman;
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 414 */       double d0 = f();
/* 415 */       List list = this.e.world.a(EntityHuman.class, this.e.getBoundingBox().grow(d0, 4.0D, d0), this.c);
/*     */       
/* 417 */       Collections.sort(list, this.b);
/* 418 */       if (list.isEmpty()) {
/* 419 */         return false;
/*     */       }
/* 421 */       this.g = ((EntityHuman)list.get(0));
/* 422 */       return true;
/*     */     }
/*     */     
/*     */     public void c()
/*     */     {
/* 427 */       this.h = 5;
/* 428 */       this.i = 0;
/*     */     }
/*     */     
/*     */     public void d() {
/* 432 */       this.g = null;
/* 433 */       this.j.a(false);
/* 434 */       AttributeInstance attributeinstance = this.j.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
/*     */       
/* 436 */       attributeinstance.c(EntityEnderman.b);
/* 437 */       super.d();
/*     */     }
/*     */     
/*     */     public boolean b() {
/* 441 */       if (this.g != null) {
/* 442 */         if (!this.j.c(this.g)) {
/* 443 */           return false;
/*     */         }
/* 445 */         this.j.bm = true;
/* 446 */         this.j.a(this.g, 10.0F, 10.0F);
/* 447 */         return true;
/*     */       }
/*     */       
/* 450 */       return super.b();
/*     */     }
/*     */     
/*     */     public void e()
/*     */     {
/* 455 */       if (this.g != null) {
/* 456 */         if (--this.h <= 0) {
/* 457 */           this.d = this.g;
/* 458 */           this.g = null;
/* 459 */           super.c();
/* 460 */           this.j.makeSound("mob.endermen.stare", 1.0F, 1.0F);
/* 461 */           this.j.a(true);
/* 462 */           AttributeInstance attributeinstance = this.j.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
/*     */           
/* 464 */           attributeinstance.b(EntityEnderman.b);
/*     */         }
/*     */       } else {
/* 467 */         if (this.d != null) {
/* 468 */           if (((this.d instanceof EntityHuman)) && (this.j.c((EntityHuman)this.d))) {
/* 469 */             if (this.d.h(this.j) < 16.0D) {
/* 470 */               this.j.n();
/*     */             }
/*     */             
/* 473 */             this.i = 0;
/* 474 */           } else if ((this.d.h(this.j) > 256.0D) && (this.i++ >= 30) && (this.j.b(this.d))) {
/* 475 */             this.i = 0;
/*     */           }
/*     */         }
/*     */         
/* 479 */         super.e();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityEnderman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */