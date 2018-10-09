/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World.Environment;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
/*     */ import org.bukkit.event.entity.EntityPortalExitEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class PortalTravelAgent
/*     */ {
/*     */   private final WorldServer a;
/*     */   private final Random b;
/*  18 */   private final LongHashMap<ChunkCoordinatesPortal> c = new LongHashMap();
/*  19 */   private final List<Long> d = com.google.common.collect.Lists.newArrayList();
/*     */   
/*     */   public PortalTravelAgent(WorldServer worldserver) {
/*  22 */     this.a = worldserver;
/*  23 */     this.b = new Random(worldserver.getSeed());
/*     */   }
/*     */   
/*     */   public void a(Entity entity, float f) {
/*  27 */     if (this.a.worldProvider.getDimension() != 1) {
/*  28 */       if (!b(entity, f)) {
/*  29 */         a(entity);
/*  30 */         b(entity, f);
/*     */       }
/*     */     } else {
/*  33 */       MathHelper.floor(entity.locX);
/*  34 */       MathHelper.floor(entity.locY);
/*  35 */       MathHelper.floor(entity.locZ);
/*     */       
/*  37 */       BlockPosition created = createEndPortal(entity.locX, entity.locY, entity.locZ);
/*  38 */       entity.setPositionRotation(created.getX(), created.getY(), created.getZ(), entity.yaw, 0.0F);
/*  39 */       entity.motX = (entity.motY = entity.motZ = 0.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   private BlockPosition createEndPortal(double x, double y, double z)
/*     */   {
/*  45 */     int i = MathHelper.floor(x);
/*  46 */     int j = MathHelper.floor(y) - 1;
/*  47 */     int k = MathHelper.floor(z);
/*     */     
/*  49 */     byte b0 = 1;
/*  50 */     byte b1 = 0;
/*     */     
/*  52 */     for (int l = -2; l <= 2; l++) {
/*  53 */       for (int i1 = -2; i1 <= 2; i1++) {
/*  54 */         for (int j1 = -1; j1 < 3; j1++) {
/*  55 */           int k1 = i + i1 * b0 + l * b1;
/*  56 */           int l1 = j + j1;
/*  57 */           int i2 = k + i1 * b1 - l * b0;
/*  58 */           boolean flag = j1 < 0;
/*     */           
/*  60 */           this.a.setTypeUpdate(new BlockPosition(k1, l1, i2), flag ? Blocks.OBSIDIAN.getBlockData() : Blocks.AIR.getBlockData());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  66 */     return new BlockPosition(i, k, k);
/*     */   }
/*     */   
/*     */   private BlockPosition findEndPortal(BlockPosition portal)
/*     */   {
/*  71 */     int i = portal.getX();
/*  72 */     int j = portal.getY() - 1;
/*  73 */     int k = portal.getZ();
/*  74 */     byte b0 = 1;
/*  75 */     byte b1 = 0;
/*     */     
/*  77 */     for (int l = -2; l <= 2; l++) {
/*  78 */       for (int i1 = -2; i1 <= 2; i1++) {
/*  79 */         for (int j1 = -1; j1 < 3; j1++) {
/*  80 */           int k1 = i + i1 * b0 + l * b1;
/*  81 */           int l1 = j + j1;
/*  82 */           int i2 = k + i1 * b1 - l * b0;
/*  83 */           boolean flag = j1 < 0;
/*     */           
/*  85 */           if (this.a.getType(new BlockPosition(k1, l1, i2)).getBlock() != (flag ? Blocks.OBSIDIAN : Blocks.AIR)) {
/*  86 */             return null;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  91 */     return new BlockPosition(i, j, k);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean b(Entity entity, float f)
/*     */   {
/*  97 */     BlockPosition found = findPortal(entity.locX, entity.locY, entity.locZ, 128);
/*  98 */     if (found == null) {
/*  99 */       return false;
/*     */     }
/*     */     
/* 102 */     Location exit = new Location(this.a.getWorld(), found.getX(), found.getY(), found.getZ(), f, entity.pitch);
/* 103 */     Vector velocity = entity.getBukkitEntity().getVelocity();
/* 104 */     adjustExit(entity, exit, velocity);
/* 105 */     entity.setPositionRotation(exit.getX(), exit.getY(), exit.getZ(), exit.getYaw(), exit.getPitch());
/* 106 */     if ((entity.motX != velocity.getX()) || (entity.motY != velocity.getY()) || (entity.motZ != velocity.getZ())) {
/* 107 */       entity.getBukkitEntity().setVelocity(velocity);
/*     */     }
/* 109 */     return true;
/*     */   }
/*     */   
/*     */   public BlockPosition findPortal(double x, double y, double z, int short1) {
/* 113 */     if (this.a.getWorld().getEnvironment() == World.Environment.THE_END) {
/* 114 */       return findEndPortal(this.a.worldProvider.h());
/*     */     }
/*     */     
/* 117 */     double d0 = -1.0D;
/*     */     
/* 119 */     int i = MathHelper.floor(x);
/* 120 */     int j = MathHelper.floor(z);
/*     */     
/* 122 */     boolean flag1 = true;
/* 123 */     Object object = BlockPosition.ZERO;
/* 124 */     long k = ChunkCoordIntPair.a(i, j);
/*     */     
/* 126 */     if (this.c.contains(k)) {
/* 127 */       ChunkCoordinatesPortal portaltravelagent_chunkcoordinatesportal = (ChunkCoordinatesPortal)this.c.getEntry(k);
/*     */       
/* 129 */       d0 = 0.0D;
/* 130 */       object = portaltravelagent_chunkcoordinatesportal;
/* 131 */       portaltravelagent_chunkcoordinatesportal.c = this.a.getTime();
/* 132 */       flag1 = false;
/*     */     } else {
/* 134 */       BlockPosition blockposition = new BlockPosition(x, y, z);
/*     */       
/* 136 */       for (int l = -128; l <= 128; l++)
/*     */       {
/*     */ 
/* 139 */         for (int i1 = -128; i1 <= 128; i1++) { BlockPosition blockposition1;
/* 140 */           for (BlockPosition blockposition2 = blockposition.a(l, this.a.V() - 1 - blockposition.getY(), i1); blockposition2.getY() >= 0; blockposition2 = blockposition1) {
/* 141 */             blockposition1 = blockposition2.down();
/* 142 */             if (this.a.getType(blockposition2).getBlock() == Blocks.PORTAL) {
/* 143 */               while (this.a.getType(blockposition1 = blockposition2.down()).getBlock() == Blocks.PORTAL) {
/* 144 */                 blockposition2 = blockposition1;
/*     */               }
/*     */               
/* 147 */               double d1 = blockposition2.i(blockposition);
/*     */               
/* 149 */               if ((d0 < 0.0D) || (d1 < d0)) {
/* 150 */                 d0 = d1;
/* 151 */                 object = blockposition2;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 159 */     if (d0 >= 0.0D) {
/* 160 */       if (flag1) {
/* 161 */         this.c.put(k, new ChunkCoordinatesPortal((BlockPosition)object, this.a.getTime()));
/* 162 */         this.d.add(Long.valueOf(k));
/*     */       }
/*     */       
/* 165 */       return (BlockPosition)object;
/*     */     }
/* 167 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void adjustExit(Entity entity, Location position, Vector velocity)
/*     */   {
/* 173 */     Location from = position.clone();
/* 174 */     Vector before = velocity.clone();
/* 175 */     BlockPosition object = new BlockPosition(position.getBlockX(), position.getBlockY(), position.getBlockZ());
/* 176 */     float f = position.getYaw();
/*     */     
/* 178 */     if ((this.a.getWorld().getEnvironment() == World.Environment.THE_END) || (entity.getBukkitEntity().getWorld().getEnvironment() == World.Environment.THE_END) || (entity.aG() == null))
/*     */     {
/*     */ 
/* 181 */       position.setPitch(0.0F);
/* 182 */       velocity.setX(0);
/* 183 */       velocity.setY(0);
/* 184 */       velocity.setZ(0);
/*     */     }
/*     */     else
/*     */     {
/* 188 */       double d2 = object.getX() + 0.5D;
/* 189 */       double d3 = object.getY() + 0.5D;
/* 190 */       double d4 = object.getZ() + 0.5D;
/* 191 */       ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = Blocks.PORTAL.f(this.a, object);
/* 192 */       boolean flag2 = shapedetector_shapedetectorcollection.b().e().c() == EnumDirection.EnumAxisDirection.NEGATIVE;
/* 193 */       double d5 = shapedetector_shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X ? shapedetector_shapedetectorcollection.a().getZ() : shapedetector_shapedetectorcollection.a().getX();
/*     */       
/* 195 */       d3 = shapedetector_shapedetectorcollection.a().getY() + 1 - entity.aG().b * shapedetector_shapedetectorcollection.e();
/* 196 */       if (flag2) {
/* 197 */         d5 += 1.0D;
/*     */       }
/*     */       
/* 200 */       if (shapedetector_shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X) {
/* 201 */         d4 = d5 + (1.0D - entity.aG().a) * shapedetector_shapedetectorcollection.d() * shapedetector_shapedetectorcollection.b().e().c().a();
/*     */       } else {
/* 203 */         d2 = d5 + (1.0D - entity.aG().a) * shapedetector_shapedetectorcollection.d() * shapedetector_shapedetectorcollection.b().e().c().a();
/*     */       }
/*     */       
/* 206 */       float f1 = 0.0F;
/* 207 */       float f2 = 0.0F;
/* 208 */       float f3 = 0.0F;
/* 209 */       float f4 = 0.0F;
/*     */       
/* 211 */       if (shapedetector_shapedetectorcollection.b().opposite() == entity.aH()) {
/* 212 */         f1 = 1.0F;
/* 213 */         f2 = 1.0F;
/* 214 */       } else if (shapedetector_shapedetectorcollection.b().opposite() == entity.aH().opposite()) {
/* 215 */         f1 = -1.0F;
/* 216 */         f2 = -1.0F;
/* 217 */       } else if (shapedetector_shapedetectorcollection.b().opposite() == entity.aH().e()) {
/* 218 */         f3 = 1.0F;
/* 219 */         f4 = -1.0F;
/*     */       } else {
/* 221 */         f3 = -1.0F;
/* 222 */         f4 = 1.0F;
/*     */       }
/*     */       
/*     */ 
/* 226 */       double d6 = velocity.getX();
/* 227 */       double d7 = velocity.getZ();
/*     */       
/*     */ 
/*     */ 
/* 231 */       velocity.setX(d6 * f1 + d7 * f4);
/* 232 */       velocity.setZ(d6 * f3 + d7 * f2);
/* 233 */       f = f - entity.aH().opposite().b() * 90 + shapedetector_shapedetectorcollection.b().b() * 90;
/*     */       
/* 235 */       position.setX(d2);
/* 236 */       position.setY(d3);
/* 237 */       position.setZ(d4);
/* 238 */       position.setYaw(f);
/*     */     }
/* 240 */     EntityPortalExitEvent event = new EntityPortalExitEvent(entity.getBukkitEntity(), from, position, before, velocity);
/* 241 */     this.a.getServer().getPluginManager().callEvent(event);
/* 242 */     Location to = event.getTo();
/* 243 */     if ((event.isCancelled()) || (to == null) || (!entity.isAlive())) {
/* 244 */       position.setX(from.getX());
/* 245 */       position.setY(from.getY());
/* 246 */       position.setZ(from.getZ());
/* 247 */       position.setYaw(from.getYaw());
/* 248 */       position.setPitch(from.getPitch());
/* 249 */       velocity.copy(before);
/*     */     } else {
/* 251 */       position.setX(to.getX());
/* 252 */       position.setY(to.getY());
/* 253 */       position.setZ(to.getZ());
/* 254 */       position.setYaw(to.getYaw());
/* 255 */       position.setPitch(to.getPitch());
/* 256 */       velocity.copy(event.getAfter());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean a(Entity entity)
/*     */   {
/* 263 */     return createPortal(entity.locX, entity.locY, entity.locZ, 16);
/*     */   }
/*     */   
/*     */   public boolean createPortal(double x, double y, double z, int b0) {
/* 267 */     if (this.a.getWorld().getEnvironment() == World.Environment.THE_END) {
/* 268 */       createEndPortal(x, y, z);
/* 269 */       return true;
/*     */     }
/*     */     
/* 272 */     double d0 = -1.0D;
/*     */     
/* 274 */     int i = MathHelper.floor(x);
/* 275 */     int j = MathHelper.floor(y);
/* 276 */     int k = MathHelper.floor(z);
/*     */     
/* 278 */     int l = i;
/* 279 */     int i1 = j;
/* 280 */     int j1 = k;
/* 281 */     int k1 = 0;
/* 282 */     int l1 = this.b.nextInt(4);
/* 283 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
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
/* 301 */     for (int i2 = i - b0; i2 <= i + b0; i2++) {
/* 302 */       double d1 = i2 + 0.5D - x;
/*     */       
/* 304 */       for (int j2 = k - b0; j2 <= k + b0; j2++) {
/* 305 */         double d2 = j2 + 0.5D - z;
/*     */         
/*     */ 
/* 308 */         for (int k2 = this.a.V() - 1; k2 >= 0; k2--) {
/* 309 */           if (this.a.isEmpty(blockposition_mutableblockposition.c(i2, k2, j2))) {
/* 310 */             while ((k2 > 0) && (this.a.isEmpty(blockposition_mutableblockposition.c(i2, k2 - 1, j2)))) {
/* 311 */               k2--;
/*     */             }
/*     */             
/* 314 */             for (int l2 = l1; l2 < l1 + 4; l2++) {
/* 315 */               int i3 = l2 % 2;
/* 316 */               int j3 = 1 - i3;
/* 317 */               if (l2 % 4 >= 2) {
/* 318 */                 i3 = -i3;
/* 319 */                 j3 = -j3;
/*     */               }
/*     */               
/* 322 */               for (int k3 = 0; k3 < 3; k3++) {
/* 323 */                 for (int l3 = 0; l3 < 4; l3++) {
/* 324 */                   for (int i4 = -1; i4 < 4; i4++) {
/* 325 */                     int j4 = i2 + (l3 - 1) * i3 + k3 * j3;
/* 326 */                     int k4 = k2 + i4;
/* 327 */                     int l4 = j2 + (l3 - 1) * j3 - k3 * i3;
/*     */                     
/* 329 */                     blockposition_mutableblockposition.c(j4, k4, l4);
/* 330 */                     if (((i4 < 0) && (!this.a.getType(blockposition_mutableblockposition).getBlock().getMaterial().isBuildable())) || ((i4 >= 0) && (!this.a.isEmpty(blockposition_mutableblockposition)))) {
/*     */                       break;
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */               
/* 337 */               double d3 = k2 + 0.5D - y;
/* 338 */               double d4 = d1 * d1 + d3 * d3 + d2 * d2;
/* 339 */               if ((d0 < 0.0D) || (d4 < d0)) {
/* 340 */                 d0 = d4;
/* 341 */                 l = i2;
/* 342 */                 i1 = k2;
/* 343 */                 j1 = j2;
/* 344 */                 k1 = l2 % 4;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 352 */     if (d0 < 0.0D) {
/* 353 */       for (i2 = i - b0; i2 <= i + b0; i2++) {
/* 354 */         double d1 = i2 + 0.5D - x;
/*     */         
/* 356 */         for (int j2 = k - b0; j2 <= k + b0; j2++) {
/* 357 */           double d2 = j2 + 0.5D - z;
/*     */           
/*     */ 
/* 360 */           for (int k2 = this.a.V() - 1; k2 >= 0; k2--) {
/* 361 */             if (this.a.isEmpty(blockposition_mutableblockposition.c(i2, k2, j2))) {
/* 362 */               while ((k2 > 0) && (this.a.isEmpty(blockposition_mutableblockposition.c(i2, k2 - 1, j2)))) {
/* 363 */                 k2--;
/*     */               }
/*     */               
/* 366 */               for (int l2 = l1; l2 < l1 + 2; l2++) {
/* 367 */                 int i3 = l2 % 2;
/* 368 */                 int j3 = 1 - i3;
/*     */                 
/* 370 */                 for (int k3 = 0; k3 < 4; k3++) {
/* 371 */                   for (int l3 = -1; l3 < 4; l3++) {
/* 372 */                     int i4 = i2 + (k3 - 1) * i3;
/* 373 */                     int j4 = k2 + l3;
/* 374 */                     int k4 = j2 + (k3 - 1) * j3;
/* 375 */                     blockposition_mutableblockposition.c(i4, j4, k4);
/* 376 */                     if (((l3 < 0) && (!this.a.getType(blockposition_mutableblockposition).getBlock().getMaterial().isBuildable())) || ((l3 >= 0) && (!this.a.isEmpty(blockposition_mutableblockposition)))) {
/*     */                       break;
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 
/* 382 */                 double d3 = k2 + 0.5D - y;
/* 383 */                 double d4 = d1 * d1 + d3 * d3 + d2 * d2;
/* 384 */                 if ((d0 < 0.0D) || (d4 < d0)) {
/* 385 */                   d0 = d4;
/* 386 */                   l = i2;
/* 387 */                   i1 = k2;
/* 388 */                   j1 = j2;
/* 389 */                   k1 = l2 % 2;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 398 */     int i5 = l;
/* 399 */     int j5 = i1;
/*     */     
/* 401 */     int j2 = j1;
/* 402 */     int k5 = k1 % 2;
/* 403 */     int l5 = 1 - k5;
/*     */     
/* 405 */     if (k1 % 4 >= 2) {
/* 406 */       k5 = -k5;
/* 407 */       l5 = -l5;
/*     */     }
/*     */     
/* 410 */     if (d0 < 0.0D) {
/* 411 */       i1 = MathHelper.clamp(i1, 70, this.a.V() - 10);
/* 412 */       j5 = i1;
/*     */       
/* 414 */       for (int k2 = -1; k2 <= 1; k2++) {
/* 415 */         for (int l2 = 1; l2 < 3; l2++) {
/* 416 */           for (int i3 = -1; i3 < 3; i3++) {
/* 417 */             int j3 = i5 + (l2 - 1) * k5 + k2 * l5;
/* 418 */             int k3 = j5 + i3;
/* 419 */             int l3 = j2 + (l2 - 1) * l5 - k2 * k5;
/* 420 */             boolean flag = i3 < 0;
/*     */             
/* 422 */             this.a.setTypeUpdate(new BlockPosition(j3, k3, l3), flag ? Blocks.OBSIDIAN.getBlockData() : Blocks.AIR.getBlockData());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 428 */     IBlockData iblockdata = Blocks.PORTAL.getBlockData().set(BlockPortal.AXIS, k5 != 0 ? EnumDirection.EnumAxis.X : EnumDirection.EnumAxis.Z);
/*     */     
/* 430 */     for (int l2 = 0; l2 < 4; l2++) {
/* 431 */       for (int i3 = 0; i3 < 4; i3++) {
/* 432 */         for (int j3 = -1; j3 < 4; j3++) {
/* 433 */           int k3 = i5 + (i3 - 1) * k5;
/* 434 */           int l3 = j5 + j3;
/* 435 */           int i4 = j2 + (i3 - 1) * l5;
/* 436 */           boolean flag1 = (i3 == 0) || (i3 == 3) || (j3 == -1) || (j3 == 3);
/*     */           
/* 438 */           this.a.setTypeAndData(new BlockPosition(k3, l3, i4), flag1 ? Blocks.OBSIDIAN.getBlockData() : iblockdata, 2);
/*     */         }
/*     */       }
/*     */       
/* 442 */       for (i3 = 0; i3 < 4; i3++) {
/* 443 */         for (int j3 = -1; j3 < 4; j3++) {
/* 444 */           int k3 = i5 + (i3 - 1) * k5;
/* 445 */           int l3 = j5 + j3;
/* 446 */           int i4 = j2 + (i3 - 1) * l5;
/* 447 */           BlockPosition blockposition = new BlockPosition(k3, l3, i4);
/*     */           
/* 449 */           this.a.applyPhysics(blockposition, this.a.getType(blockposition).getBlock());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 454 */     return true;
/*     */   }
/*     */   
/*     */   public void a(long i) {
/* 458 */     if (i % 100L == 0L) {
/* 459 */       Iterator iterator = this.d.iterator();
/* 460 */       long j = i - 300L;
/*     */       
/* 462 */       while (iterator.hasNext()) {
/* 463 */         Long olong = (Long)iterator.next();
/* 464 */         ChunkCoordinatesPortal portaltravelagent_chunkcoordinatesportal = (ChunkCoordinatesPortal)this.c.getEntry(olong.longValue());
/*     */         
/* 466 */         if ((portaltravelagent_chunkcoordinatesportal == null) || (portaltravelagent_chunkcoordinatesportal.c < j)) {
/* 467 */           iterator.remove();
/* 468 */           this.c.remove(olong.longValue());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public class ChunkCoordinatesPortal extends BlockPosition
/*     */   {
/*     */     public long c;
/*     */     
/*     */     public ChunkCoordinatesPortal(BlockPosition blockposition, long i)
/*     */     {
/* 480 */       super(blockposition.getY(), blockposition.getZ());
/* 481 */       this.c = i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PortalTravelAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */