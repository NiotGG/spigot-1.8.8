/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.entity.Fish;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerFishEvent;
/*     */ import org.bukkit.event.player.PlayerFishEvent.State;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class EntityFishingHook extends Entity
/*     */ {
/*  14 */   private static final List<PossibleFishingResult> d = java.util.Arrays.asList(new PossibleFishingResult[] { new PossibleFishingResult(new ItemStack(Items.LEATHER_BOOTS), 10).a(0.9F), new PossibleFishingResult(new ItemStack(Items.LEATHER), 10), new PossibleFishingResult(new ItemStack(Items.BONE), 10), new PossibleFishingResult(new ItemStack(Items.POTION), 10), new PossibleFishingResult(new ItemStack(Items.STRING), 5), new PossibleFishingResult(new ItemStack(Items.FISHING_ROD), 2).a(0.9F), new PossibleFishingResult(new ItemStack(Items.BOWL), 10), new PossibleFishingResult(new ItemStack(Items.STICK), 5), new PossibleFishingResult(new ItemStack(Items.DYE, 10, EnumColor.BLACK.getInvColorIndex()), 1), new PossibleFishingResult(new ItemStack(Blocks.TRIPWIRE_HOOK), 10), new PossibleFishingResult(new ItemStack(Items.ROTTEN_FLESH), 10) });
/*  15 */   private static final List<PossibleFishingResult> e = java.util.Arrays.asList(new PossibleFishingResult[] { new PossibleFishingResult(new ItemStack(Blocks.WATERLILY), 1), new PossibleFishingResult(new ItemStack(Items.NAME_TAG), 1), new PossibleFishingResult(new ItemStack(Items.SADDLE), 1), new PossibleFishingResult(new ItemStack(Items.BOW), 1).a(0.25F).a(), new PossibleFishingResult(new ItemStack(Items.FISHING_ROD), 1).a(0.25F).a(), new PossibleFishingResult(new ItemStack(Items.BOOK), 1).a() });
/*  16 */   private static final List<PossibleFishingResult> f = java.util.Arrays.asList(new PossibleFishingResult[] { new PossibleFishingResult(new ItemStack(Items.FISH, 1, ItemFish.EnumFish.COD.a()), 60), new PossibleFishingResult(new ItemStack(Items.FISH, 1, ItemFish.EnumFish.SALMON.a()), 25), new PossibleFishingResult(new ItemStack(Items.FISH, 1, ItemFish.EnumFish.CLOWNFISH.a()), 2), new PossibleFishingResult(new ItemStack(Items.FISH, 1, ItemFish.EnumFish.PUFFERFISH.a()), 13) });
/*  17 */   private int g = -1;
/*  18 */   private int h = -1;
/*  19 */   private int i = -1;
/*     */   private Block ar;
/*     */   private boolean as;
/*     */   public int a;
/*     */   public EntityHuman owner;
/*     */   private int at;
/*     */   private int au;
/*     */   private int av;
/*     */   private int aw;
/*     */   private int ax;
/*     */   private float ay;
/*     */   public Entity hooked;
/*     */   private int az;
/*     */   private double aA;
/*     */   private double aB;
/*     */   private double aC;
/*     */   private double aD;
/*     */   private double aE;
/*     */   
/*     */   public static List<PossibleFishingResult> j() {
/*  39 */     return f;
/*     */   }
/*     */   
/*     */   public EntityFishingHook(World world) {
/*  43 */     super(world);
/*  44 */     setSize(0.25F, 0.25F);
/*  45 */     this.ah = true;
/*     */   }
/*     */   
/*     */   public EntityFishingHook(World world, EntityHuman entityhuman) {
/*  49 */     super(world);
/*  50 */     this.ah = true;
/*  51 */     this.owner = entityhuman;
/*  52 */     this.owner.hookedFish = this;
/*  53 */     setSize(0.25F, 0.25F);
/*  54 */     setPositionRotation(entityhuman.locX, entityhuman.locY + entityhuman.getHeadHeight(), entityhuman.locZ, entityhuman.yaw, entityhuman.pitch);
/*  55 */     this.locX -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
/*  56 */     this.locY -= 0.10000000149011612D;
/*  57 */     this.locZ -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
/*  58 */     setPosition(this.locX, this.locY, this.locZ);
/*  59 */     float f = 0.4F;
/*     */     
/*  61 */     this.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  62 */     this.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  63 */     this.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f);
/*  64 */     c(this.motX, this.motY, this.motZ, 1.5F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void h() {}
/*     */   
/*     */   public void c(double d0, double d1, double d2, float f, float f1) {
/*  70 */     float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */     
/*  72 */     d0 /= f2;
/*  73 */     d1 /= f2;
/*  74 */     d2 /= f2;
/*  75 */     d0 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  76 */     d1 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  77 */     d2 += this.random.nextGaussian() * 0.007499999832361937D * f1;
/*  78 */     d0 *= f;
/*  79 */     d1 *= f;
/*  80 */     d2 *= f;
/*  81 */     this.motX = d0;
/*  82 */     this.motY = d1;
/*  83 */     this.motZ = d2;
/*  84 */     float f3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/*     */     
/*  86 */     this.lastYaw = (this.yaw = (float)(MathHelper.b(d0, d2) * 180.0D / 3.1415927410125732D));
/*  87 */     this.lastPitch = (this.pitch = (float)(MathHelper.b(d1, f3) * 180.0D / 3.1415927410125732D));
/*  88 */     this.at = 0;
/*     */   }
/*     */   
/*     */   public void t_() {
/*  92 */     super.t_();
/*  93 */     if (this.az > 0) {
/*  94 */       double d0 = this.locX + (this.aA - this.locX) / this.az;
/*  95 */       double d1 = this.locY + (this.aB - this.locY) / this.az;
/*  96 */       double d2 = this.locZ + (this.aC - this.locZ) / this.az;
/*  97 */       double d3 = MathHelper.g(this.aD - this.yaw);
/*     */       
/*  99 */       this.yaw = ((float)(this.yaw + d3 / this.az));
/* 100 */       this.pitch = ((float)(this.pitch + (this.aE - this.pitch) / this.az));
/* 101 */       this.az -= 1;
/* 102 */       setPosition(d0, d1, d2);
/* 103 */       setYawPitch(this.yaw, this.pitch);
/*     */     } else {
/* 105 */       if (!this.world.isClientSide) {
/* 106 */         ItemStack itemstack = this.owner.bZ();
/*     */         
/* 108 */         if ((this.owner.dead) || (!this.owner.isAlive()) || (itemstack == null) || (itemstack.getItem() != Items.FISHING_ROD) || (h(this.owner) > 1024.0D)) {
/* 109 */           die();
/* 110 */           this.owner.hookedFish = null;
/* 111 */           return;
/*     */         }
/*     */         
/* 114 */         if (this.hooked != null) {
/* 115 */           if (!this.hooked.dead) {
/* 116 */             this.locX = this.hooked.locX;
/* 117 */             double d4 = this.hooked.length;
/*     */             
/* 119 */             this.locY = (this.hooked.getBoundingBox().b + d4 * 0.8D);
/* 120 */             this.locZ = this.hooked.locZ;
/* 121 */             return;
/*     */           }
/*     */           
/* 124 */           this.hooked = null;
/*     */         }
/*     */       }
/*     */       
/* 128 */       if (this.a > 0) {
/* 129 */         this.a -= 1;
/*     */       }
/*     */       
/* 132 */       if (this.as) {
/* 133 */         if (this.world.getType(new BlockPosition(this.g, this.h, this.i)).getBlock() == this.ar) {
/* 134 */           this.at += 1;
/* 135 */           if (this.at == 1200) {
/* 136 */             die();
/*     */           }
/*     */           
/* 139 */           return;
/*     */         }
/*     */         
/* 142 */         this.as = false;
/* 143 */         this.motX *= this.random.nextFloat() * 0.2F;
/* 144 */         this.motY *= this.random.nextFloat() * 0.2F;
/* 145 */         this.motZ *= this.random.nextFloat() * 0.2F;
/* 146 */         this.at = 0;
/* 147 */         this.au = 0;
/*     */       } else {
/* 149 */         this.au += 1;
/*     */       }
/*     */       
/* 152 */       Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
/* 153 */       Vec3D vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 154 */       MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1);
/*     */       
/* 156 */       vec3d = new Vec3D(this.locX, this.locY, this.locZ);
/* 157 */       vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
/* 158 */       if (movingobjectposition != null) {
/* 159 */         vec3d1 = new Vec3D(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
/*     */       }
/*     */       
/* 162 */       Entity entity = null;
/* 163 */       List list = this.world.getEntities(this, getBoundingBox().a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
/* 164 */       double d5 = 0.0D;
/*     */       
/*     */ 
/*     */ 
/* 168 */       for (int i = 0; i < list.size(); i++) {
/* 169 */         Entity entity1 = (Entity)list.get(i);
/*     */         
/* 171 */         if ((entity1.ad()) && ((entity1 != this.owner) || (this.au >= 5))) {
/* 172 */           float f = 0.3F;
/* 173 */           AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow(f, f, f);
/* 174 */           MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);
/*     */           
/* 176 */           if (movingobjectposition1 != null) {
/* 177 */             double d6 = vec3d.distanceSquared(movingobjectposition1.pos);
/* 178 */             if ((d6 < d5) || (d5 == 0.0D)) {
/* 179 */               entity = entity1;
/* 180 */               d5 = d6;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 186 */       if (entity != null) {
/* 187 */         movingobjectposition = new MovingObjectPosition(entity);
/*     */       }
/*     */       
/* 190 */       if (movingobjectposition != null) {
/* 191 */         org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callProjectileHitEvent(this);
/* 192 */         if (movingobjectposition.entity != null) {
/* 193 */           if (movingobjectposition.entity.damageEntity(DamageSource.projectile(this, this.owner), 0.0F)) {
/* 194 */             this.hooked = movingobjectposition.entity;
/*     */           }
/*     */         } else {
/* 197 */           this.as = true;
/*     */         }
/*     */       }
/*     */       
/* 201 */       if (!this.as) {
/* 202 */         move(this.motX, this.motY, this.motZ);
/* 203 */         float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */         
/* 205 */         this.yaw = ((float)(MathHelper.b(this.motX, this.motZ) * 180.0D / 3.1415927410125732D));
/*     */         
/* 207 */         for (this.pitch = ((float)(MathHelper.b(this.motY, f1) * 180.0D / 3.1415927410125732D)); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F) {}
/*     */         
/*     */ 
/*     */ 
/* 211 */         while (this.pitch - this.lastPitch >= 180.0F) {
/* 212 */           this.lastPitch += 360.0F;
/*     */         }
/*     */         
/* 215 */         while (this.yaw - this.lastYaw < -180.0F) {
/* 216 */           this.lastYaw -= 360.0F;
/*     */         }
/*     */         
/* 219 */         while (this.yaw - this.lastYaw >= 180.0F) {
/* 220 */           this.lastYaw += 360.0F;
/*     */         }
/*     */         
/* 223 */         this.pitch = (this.lastPitch + (this.pitch - this.lastPitch) * 0.2F);
/* 224 */         this.yaw = (this.lastYaw + (this.yaw - this.lastYaw) * 0.2F);
/* 225 */         float f2 = 0.92F;
/*     */         
/* 227 */         if ((this.onGround) || (this.positionChanged)) {
/* 228 */           f2 = 0.5F;
/*     */         }
/*     */         
/* 231 */         byte b0 = 5;
/* 232 */         double d7 = 0.0D;
/*     */         
/*     */ 
/*     */ 
/* 236 */         for (int j = 0; j < b0; j++) {
/* 237 */           AxisAlignedBB axisalignedbb1 = getBoundingBox();
/* 238 */           double d9 = axisalignedbb1.e - axisalignedbb1.b;
/* 239 */           double d10 = axisalignedbb1.b + d9 * j / b0;
/*     */           
/* 241 */           double d8 = axisalignedbb1.b + d9 * (j + 1) / b0;
/* 242 */           AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(axisalignedbb1.a, d10, axisalignedbb1.c, axisalignedbb1.d, d8, axisalignedbb1.f);
/*     */           
/* 244 */           if (this.world.b(axisalignedbb2, Material.WATER)) {
/* 245 */             d7 += 1.0D / b0;
/*     */           }
/*     */         }
/*     */         
/* 249 */         if ((!this.world.isClientSide) && (d7 > 0.0D)) {
/* 250 */           WorldServer worldserver = (WorldServer)this.world;
/* 251 */           int k = 1;
/* 252 */           BlockPosition blockposition = new BlockPosition(this).up();
/*     */           
/* 254 */           if ((this.random.nextFloat() < 0.25F) && (this.world.isRainingAt(blockposition))) {
/* 255 */             k = 2;
/*     */           }
/*     */           
/* 258 */           if ((this.random.nextFloat() < 0.5F) && (!this.world.i(blockposition))) {
/* 259 */             k--;
/*     */           }
/*     */           
/* 262 */           if (this.av > 0) {
/* 263 */             this.av -= 1;
/* 264 */             if (this.av <= 0) {
/* 265 */               this.aw = 0;
/* 266 */               this.ax = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/* 276 */           else if (this.ax > 0) {
/* 277 */             this.ax -= k;
/* 278 */             if (this.ax <= 0) {
/* 279 */               this.motY -= 0.20000000298023224D;
/* 280 */               makeSound("random.splash", 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/* 281 */               float f3 = MathHelper.floor(getBoundingBox().b);
/* 282 */               worldserver.a(EnumParticle.WATER_BUBBLE, this.locX, f3 + 1.0F, this.locZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D, new int[0]);
/* 283 */               worldserver.a(EnumParticle.WATER_WAKE, this.locX, f3 + 1.0F, this.locZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D, new int[0]);
/* 284 */               this.av = MathHelper.nextInt(this.random, 10, 30);
/*     */             } else {
/* 286 */               this.ay = ((float)(this.ay + this.random.nextGaussian() * 4.0D));
/* 287 */               float f3 = this.ay * 0.017453292F;
/* 288 */               float f5 = MathHelper.sin(f3);
/* 289 */               float f4 = MathHelper.cos(f3);
/* 290 */               double d8 = this.locX + f5 * this.ax * 0.1F;
/* 291 */               double d12 = MathHelper.floor(getBoundingBox().b) + 1.0F;
/* 292 */               double d11 = this.locZ + f4 * this.ax * 0.1F;
/* 293 */               Block block = worldserver.getType(new BlockPosition((int)d8, (int)d12 - 1, (int)d11)).getBlock();
/* 294 */               if ((block == Blocks.WATER) || (block == Blocks.FLOWING_WATER)) {
/* 295 */                 if (this.random.nextFloat() < 0.15F) {
/* 296 */                   worldserver.a(EnumParticle.WATER_BUBBLE, d8, d12 - 0.10000000149011612D, d11, 1, f5, 0.1D, f4, 0.0D, new int[0]);
/*     */                 }
/*     */                 
/* 299 */                 float f6 = f5 * 0.04F;
/* 300 */                 float f7 = f4 * 0.04F;
/*     */                 
/* 302 */                 worldserver.a(EnumParticle.WATER_WAKE, d8, d12, d11, 0, f7, 0.01D, -f6, 1.0D, new int[0]);
/* 303 */                 worldserver.a(EnumParticle.WATER_WAKE, d8, d12, d11, 0, -f7, 0.01D, f6, 1.0D, new int[0]);
/*     */               }
/*     */             }
/* 306 */           } else if (this.aw > 0) {
/* 307 */             this.aw -= k;
/* 308 */             float f3 = 0.15F;
/* 309 */             if (this.aw < 20) {
/* 310 */               f3 = (float)(f3 + (20 - this.aw) * 0.05D);
/* 311 */             } else if (this.aw < 40) {
/* 312 */               f3 = (float)(f3 + (40 - this.aw) * 0.02D);
/* 313 */             } else if (this.aw < 60) {
/* 314 */               f3 = (float)(f3 + (60 - this.aw) * 0.01D);
/*     */             }
/*     */             
/* 317 */             if (this.random.nextFloat() < f3) {
/* 318 */               float f5 = MathHelper.a(this.random, 0.0F, 360.0F) * 0.017453292F;
/* 319 */               float f4 = MathHelper.a(this.random, 25.0F, 60.0F);
/* 320 */               double d8 = this.locX + MathHelper.sin(f5) * f4 * 0.1F;
/* 321 */               double d12 = MathHelper.floor(getBoundingBox().b) + 1.0F;
/* 322 */               double d11 = this.locZ + MathHelper.cos(f5) * f4 * 0.1F;
/* 323 */               Block block = worldserver.getType(new BlockPosition((int)d8, (int)d12 - 1, (int)d11)).getBlock();
/* 324 */               if ((block == Blocks.WATER) || (block == Blocks.FLOWING_WATER)) {
/* 325 */                 worldserver.a(EnumParticle.WATER_SPLASH, d8, d12, d11, 2 + this.random.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
/*     */               }
/*     */             }
/*     */             
/* 329 */             if (this.aw <= 0) {
/* 330 */               this.ay = MathHelper.a(this.random, 0.0F, 360.0F);
/* 331 */               this.ax = MathHelper.nextInt(this.random, 20, 80);
/*     */             }
/*     */           } else {
/* 334 */             this.aw = MathHelper.nextInt(this.random, 100, 900);
/* 335 */             this.aw -= EnchantmentManager.h(this.owner) * 20 * 5;
/*     */           }
/*     */           
/*     */ 
/* 339 */           if (this.av > 0) {
/* 340 */             this.motY -= this.random.nextFloat() * this.random.nextFloat() * this.random.nextFloat() * 0.2D;
/*     */           }
/*     */         }
/*     */         
/* 344 */         double d6 = d7 * 2.0D - 1.0D;
/* 345 */         this.motY += 0.03999999910593033D * d6;
/* 346 */         if (d7 > 0.0D) {
/* 347 */           f2 = (float)(f2 * 0.9D);
/* 348 */           this.motY *= 0.8D;
/*     */         }
/*     */         
/* 351 */         this.motX *= f2;
/* 352 */         this.motY *= f2;
/* 353 */         this.motZ *= f2;
/* 354 */         setPosition(this.locX, this.locY, this.locZ);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 360 */     nbttagcompound.setShort("xTile", (short)this.g);
/* 361 */     nbttagcompound.setShort("yTile", (short)this.h);
/* 362 */     nbttagcompound.setShort("zTile", (short)this.i);
/* 363 */     MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(this.ar);
/*     */     
/* 365 */     nbttagcompound.setString("inTile", minecraftkey == null ? "" : minecraftkey.toString());
/* 366 */     nbttagcompound.setByte("shake", (byte)this.a);
/* 367 */     nbttagcompound.setByte("inGround", (byte)(this.as ? 1 : 0));
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 371 */     this.g = nbttagcompound.getShort("xTile");
/* 372 */     this.h = nbttagcompound.getShort("yTile");
/* 373 */     this.i = nbttagcompound.getShort("zTile");
/* 374 */     if (nbttagcompound.hasKeyOfType("inTile", 8)) {
/* 375 */       this.ar = Block.getByName(nbttagcompound.getString("inTile"));
/*     */     } else {
/* 377 */       this.ar = Block.getById(nbttagcompound.getByte("inTile") & 0xFF);
/*     */     }
/*     */     
/* 380 */     this.a = (nbttagcompound.getByte("shake") & 0xFF);
/* 381 */     this.as = (nbttagcompound.getByte("inGround") == 1);
/*     */   }
/*     */   
/*     */   public int l() {
/* 385 */     if (this.world.isClientSide) {
/* 386 */       return 0;
/*     */     }
/* 388 */     byte b0 = 0;
/*     */     
/* 390 */     if (this.hooked != null)
/*     */     {
/* 392 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), this.hooked.getBukkitEntity(), (Fish)getBukkitEntity(), PlayerFishEvent.State.CAUGHT_ENTITY);
/* 393 */       this.world.getServer().getPluginManager().callEvent(playerFishEvent);
/*     */       
/* 395 */       if (playerFishEvent.isCancelled()) {
/* 396 */         return 0;
/*     */       }
/*     */       
/*     */ 
/* 400 */       double d0 = this.owner.locX - this.locX;
/* 401 */       double d1 = this.owner.locY - this.locY;
/* 402 */       double d2 = this.owner.locZ - this.locZ;
/* 403 */       double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 404 */       double d4 = 0.1D;
/*     */       
/* 406 */       this.hooked.motX += d0 * d4;
/* 407 */       this.hooked.motY += d1 * d4 + MathHelper.sqrt(d3) * 0.08D;
/* 408 */       this.hooked.motZ += d2 * d4;
/* 409 */       b0 = 3;
/* 410 */     } else if (this.av > 0) {
/* 411 */       EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY, this.locZ, m());
/*     */       
/* 413 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), entityitem.getBukkitEntity(), (Fish)getBukkitEntity(), PlayerFishEvent.State.CAUGHT_FISH);
/* 414 */       playerFishEvent.setExpToDrop(this.random.nextInt(6) + 1);
/* 415 */       this.world.getServer().getPluginManager().callEvent(playerFishEvent);
/*     */       
/* 417 */       if (playerFishEvent.isCancelled()) {
/* 418 */         return 0;
/*     */       }
/*     */       
/* 421 */       double d5 = this.owner.locX - this.locX;
/* 422 */       double d6 = this.owner.locY - this.locY;
/* 423 */       double d7 = this.owner.locZ - this.locZ;
/* 424 */       double d8 = MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7);
/* 425 */       double d9 = 0.1D;
/*     */       
/* 427 */       entityitem.motX = (d5 * d9);
/* 428 */       entityitem.motY = (d6 * d9 + MathHelper.sqrt(d8) * 0.08D);
/* 429 */       entityitem.motZ = (d7 * d9);
/* 430 */       this.world.addEntity(entityitem);
/*     */       
/* 432 */       if (playerFishEvent.getExpToDrop() > 0) {
/* 433 */         this.owner.world.addEntity(new EntityExperienceOrb(this.owner.world, this.owner.locX, this.owner.locY + 0.5D, this.owner.locZ + 0.5D, playerFishEvent.getExpToDrop()));
/*     */       }
/* 435 */       b0 = 1;
/*     */     }
/*     */     
/* 438 */     if (this.as)
/*     */     {
/* 440 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, (Fish)getBukkitEntity(), PlayerFishEvent.State.IN_GROUND);
/* 441 */       this.world.getServer().getPluginManager().callEvent(playerFishEvent);
/*     */       
/* 443 */       if (playerFishEvent.isCancelled()) {
/* 444 */         return 0;
/*     */       }
/*     */       
/* 447 */       b0 = 2;
/*     */     }
/*     */     
/*     */ 
/* 451 */     if (b0 == 0) {
/* 452 */       PlayerFishEvent playerFishEvent = new PlayerFishEvent((Player)this.owner.getBukkitEntity(), null, (Fish)getBukkitEntity(), PlayerFishEvent.State.FAILED_ATTEMPT);
/* 453 */       this.world.getServer().getPluginManager().callEvent(playerFishEvent);
/* 454 */       if (playerFishEvent.isCancelled()) {
/* 455 */         return 0;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 460 */     die();
/* 461 */     this.owner.hookedFish = null;
/* 462 */     return b0;
/*     */   }
/*     */   
/*     */   private ItemStack m()
/*     */   {
/* 467 */     float f = this.world.random.nextFloat();
/* 468 */     int i = EnchantmentManager.g(this.owner);
/* 469 */     int j = EnchantmentManager.h(this.owner);
/* 470 */     float f1 = 0.1F - i * 0.025F - j * 0.01F;
/* 471 */     float f2 = 0.05F + i * 0.01F - j * 0.01F;
/*     */     
/* 473 */     f1 = MathHelper.a(f1, 0.0F, 1.0F);
/* 474 */     f2 = MathHelper.a(f2, 0.0F, 1.0F);
/* 475 */     if (f < f1) {
/* 476 */       this.owner.b(StatisticList.D);
/* 477 */       return ((PossibleFishingResult)WeightedRandom.a(this.random, d)).a(this.random);
/*     */     }
/* 479 */     f -= f1;
/* 480 */     if (f < f2) {
/* 481 */       this.owner.b(StatisticList.E);
/* 482 */       return ((PossibleFishingResult)WeightedRandom.a(this.random, e)).a(this.random);
/*     */     }
/*     */     
/*     */ 
/* 486 */     this.owner.b(StatisticList.C);
/* 487 */     return ((PossibleFishingResult)WeightedRandom.a(this.random, f)).a(this.random);
/*     */   }
/*     */   
/*     */ 
/*     */   public void die()
/*     */   {
/* 493 */     super.die();
/* 494 */     if (this.owner != null) {
/* 495 */       this.owner.hookedFish = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityFishingHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */