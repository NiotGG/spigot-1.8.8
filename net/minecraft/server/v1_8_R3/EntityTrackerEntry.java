/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerVelocityEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.util.Vector;
/*     */ import org.spigotmc.AsyncCatcher;
/*     */ 
/*     */ public class EntityTrackerEntry
/*     */ {
/*  18 */   private static final org.apache.logging.log4j.Logger p = ;
/*     */   public Entity tracker;
/*     */   public int b;
/*     */   public int c;
/*     */   public int xLoc;
/*     */   public int yLoc;
/*     */   public int zLoc;
/*     */   public int yRot;
/*     */   public int xRot;
/*     */   public int i;
/*     */   public double j;
/*     */   public double k;
/*     */   public double l;
/*     */   public int m;
/*     */   private double q;
/*     */   private double r;
/*     */   private double s;
/*     */   private boolean isMoving;
/*     */   private boolean u;
/*     */   private int v;
/*     */   private Entity w;
/*     */   private boolean x;
/*     */   private boolean y;
/*     */   public boolean n;
/*  42 */   public Set<EntityPlayer> trackedPlayers = com.google.common.collect.Sets.newHashSet();
/*     */   
/*     */   public EntityTrackerEntry(Entity entity, int i, int j, boolean flag) {
/*  45 */     this.tracker = entity;
/*  46 */     this.b = i;
/*  47 */     this.c = j;
/*  48 */     this.u = flag;
/*  49 */     this.xLoc = MathHelper.floor(entity.locX * 32.0D);
/*  50 */     this.yLoc = MathHelper.floor(entity.locY * 32.0D);
/*  51 */     this.zLoc = MathHelper.floor(entity.locZ * 32.0D);
/*  52 */     this.yRot = MathHelper.d(entity.yaw * 256.0F / 360.0F);
/*  53 */     this.xRot = MathHelper.d(entity.pitch * 256.0F / 360.0F);
/*  54 */     this.i = MathHelper.d(entity.getHeadRotation() * 256.0F / 360.0F);
/*  55 */     this.y = entity.onGround;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/*  59 */     return ((EntityTrackerEntry)object).tracker.getId() == this.tracker.getId();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  63 */     return this.tracker.getId();
/*     */   }
/*     */   
/*     */   public void track(List<EntityHuman> list) {
/*  67 */     this.n = false;
/*  68 */     if ((!this.isMoving) || (this.tracker.e(this.q, this.r, this.s) > 16.0D)) {
/*  69 */       this.q = this.tracker.locX;
/*  70 */       this.r = this.tracker.locY;
/*  71 */       this.s = this.tracker.locZ;
/*  72 */       this.isMoving = true;
/*  73 */       this.n = true;
/*  74 */       scanPlayers(list);
/*     */     }
/*     */     
/*  77 */     if ((this.w != this.tracker.vehicle) || ((this.tracker.vehicle != null) && (this.m % 60 == 0))) {
/*  78 */       this.w = this.tracker.vehicle;
/*  79 */       broadcast(new PacketPlayOutAttachEntity(0, this.tracker, this.tracker.vehicle));
/*     */     }
/*     */     
/*  82 */     if ((this.tracker instanceof EntityItemFrame)) {
/*  83 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.tracker;
/*  84 */       ItemStack itemstack = entityitemframe.getItem();
/*     */       
/*  86 */       if ((this.m % 10 == 0) && (itemstack != null) && ((itemstack.getItem() instanceof ItemWorldMap))) {
/*  87 */         WorldMap worldmap = Items.FILLED_MAP.getSavedMap(itemstack, this.tracker.world);
/*  88 */         Iterator iterator = this.trackedPlayers.iterator();
/*     */         
/*  90 */         while (iterator.hasNext()) {
/*  91 */           EntityHuman entityhuman = (EntityHuman)iterator.next();
/*  92 */           EntityPlayer entityplayer = (EntityPlayer)entityhuman;
/*     */           
/*  94 */           worldmap.a(entityplayer, itemstack);
/*  95 */           Packet packet = Items.FILLED_MAP.c(itemstack, this.tracker.world, entityplayer);
/*     */           
/*  97 */           if (packet != null) {
/*  98 */             entityplayer.playerConnection.sendPacket(packet);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 103 */       b();
/*     */     }
/*     */     
/* 106 */     if ((this.m % this.c == 0) || (this.tracker.ai) || (this.tracker.getDataWatcher().a()))
/*     */     {
/*     */ 
/*     */ 
/* 110 */       if (this.tracker.vehicle == null) {
/* 111 */         this.v += 1;
/* 112 */         int i = MathHelper.floor(this.tracker.locX * 32.0D);
/* 113 */         int j = MathHelper.floor(this.tracker.locY * 32.0D);
/* 114 */         int k = MathHelper.floor(this.tracker.locZ * 32.0D);
/* 115 */         int l = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
/* 116 */         int i1 = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
/* 117 */         int j1 = i - this.xLoc;
/* 118 */         int k1 = j - this.yLoc;
/* 119 */         int l1 = k - this.zLoc;
/* 120 */         Object object = null;
/* 121 */         boolean flag = (Math.abs(j1) >= 4) || (Math.abs(k1) >= 4) || (Math.abs(l1) >= 4) || (this.m % 60 == 0);
/* 122 */         boolean flag1 = (Math.abs(l - this.yRot) >= 4) || (Math.abs(i1 - this.xRot) >= 4);
/*     */         
/*     */ 
/* 125 */         if (flag) {
/* 126 */           this.xLoc = i;
/* 127 */           this.yLoc = j;
/* 128 */           this.zLoc = k;
/*     */         }
/*     */         
/* 131 */         if (flag1) {
/* 132 */           this.yRot = l;
/* 133 */           this.xRot = i1;
/*     */         }
/*     */         
/*     */ 
/* 137 */         if ((this.m > 0) || ((this.tracker instanceof EntityArrow))) {
/* 138 */           if ((j1 >= -128) && (j1 < 128) && (k1 >= -128) && (k1 < 128) && (l1 >= -128) && (l1 < 128) && (this.v <= 400) && (!this.x) && (this.y == this.tracker.onGround)) {
/* 139 */             if (((!flag) || (!flag1)) && (!(this.tracker instanceof EntityArrow))) {
/* 140 */               if (flag) {
/* 141 */                 object = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(this.tracker.getId(), (byte)j1, (byte)k1, (byte)l1, this.tracker.onGround);
/* 142 */               } else if (flag1) {
/* 143 */                 object = new PacketPlayOutEntity.PacketPlayOutEntityLook(this.tracker.getId(), (byte)l, (byte)i1, this.tracker.onGround);
/*     */               }
/*     */             } else {
/* 146 */               object = new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(this.tracker.getId(), (byte)j1, (byte)k1, (byte)l1, (byte)l, (byte)i1, this.tracker.onGround);
/*     */             }
/*     */           } else {
/* 149 */             this.y = this.tracker.onGround;
/* 150 */             this.v = 0;
/*     */             
/* 152 */             if ((this.tracker instanceof EntityPlayer)) {
/* 153 */               scanPlayers(new ArrayList(this.trackedPlayers));
/*     */             }
/*     */             
/* 156 */             object = new PacketPlayOutEntityTeleport(this.tracker.getId(), i, j, k, (byte)l, (byte)i1, this.tracker.onGround);
/*     */           }
/*     */         }
/*     */         
/* 160 */         if (this.u) {
/* 161 */           double d0 = this.tracker.motX - this.j;
/* 162 */           double d1 = this.tracker.motY - this.k;
/* 163 */           double d2 = this.tracker.motZ - this.l;
/* 164 */           double d3 = 0.02D;
/* 165 */           double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */           
/* 167 */           if ((d4 > d3 * d3) || ((d4 > 0.0D) && (this.tracker.motX == 0.0D) && (this.tracker.motY == 0.0D) && (this.tracker.motZ == 0.0D))) {
/* 168 */             this.j = this.tracker.motX;
/* 169 */             this.k = this.tracker.motY;
/* 170 */             this.l = this.tracker.motZ;
/* 171 */             broadcast(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.j, this.k, this.l));
/*     */           }
/*     */         }
/*     */         
/* 175 */         if (object != null) {
/* 176 */           broadcast((Packet)object);
/*     */         }
/*     */         
/* 179 */         b();
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
/* 193 */         this.x = false;
/*     */       } else {
/* 195 */         i = MathHelper.d(this.tracker.yaw * 256.0F / 360.0F);
/* 196 */         int j = MathHelper.d(this.tracker.pitch * 256.0F / 360.0F);
/* 197 */         boolean flag2 = (Math.abs(i - this.yRot) >= 4) || (Math.abs(j - this.xRot) >= 4);
/*     */         
/* 199 */         if (flag2) {
/* 200 */           broadcast(new PacketPlayOutEntity.PacketPlayOutEntityLook(this.tracker.getId(), (byte)i, (byte)j, this.tracker.onGround));
/* 201 */           this.yRot = i;
/* 202 */           this.xRot = j;
/*     */         }
/*     */         
/* 205 */         this.xLoc = MathHelper.floor(this.tracker.locX * 32.0D);
/* 206 */         this.yLoc = MathHelper.floor(this.tracker.locY * 32.0D);
/* 207 */         this.zLoc = MathHelper.floor(this.tracker.locZ * 32.0D);
/* 208 */         b();
/* 209 */         this.x = true;
/*     */       }
/*     */       
/* 212 */       int i = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
/* 213 */       if (Math.abs(i - this.i) >= 4) {
/* 214 */         broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte)i));
/* 215 */         this.i = i;
/*     */       }
/*     */       
/* 218 */       this.tracker.ai = false;
/*     */     }
/*     */     
/* 221 */     this.m += 1;
/* 222 */     if (this.tracker.velocityChanged)
/*     */     {
/* 224 */       boolean cancelled = false;
/*     */       
/* 226 */       if ((this.tracker instanceof EntityPlayer)) {
/* 227 */         Player player = (Player)this.tracker.getBukkitEntity();
/* 228 */         Vector velocity = player.getVelocity();
/*     */         
/* 230 */         PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity.clone());
/* 231 */         this.tracker.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 233 */         if (event.isCancelled()) {
/* 234 */           cancelled = true;
/* 235 */         } else if (!velocity.equals(event.getVelocity())) {
/* 236 */           player.setVelocity(event.getVelocity());
/*     */         }
/*     */       }
/*     */       
/* 240 */       if (!cancelled) {
/* 241 */         broadcastIncludingSelf(new PacketPlayOutEntityVelocity(this.tracker));
/*     */       }
/*     */       
/* 244 */       this.tracker.velocityChanged = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private void b()
/*     */   {
/* 250 */     DataWatcher datawatcher = this.tracker.getDataWatcher();
/*     */     
/* 252 */     if (datawatcher.a()) {
/* 253 */       broadcastIncludingSelf(new PacketPlayOutEntityMetadata(this.tracker.getId(), datawatcher, false));
/*     */     }
/*     */     
/* 256 */     if ((this.tracker instanceof EntityLiving)) {
/* 257 */       AttributeMapServer attributemapserver = (AttributeMapServer)((EntityLiving)this.tracker).getAttributeMap();
/* 258 */       Set set = attributemapserver.getAttributes();
/*     */       
/* 260 */       if (!set.isEmpty())
/*     */       {
/* 262 */         if ((this.tracker instanceof EntityPlayer)) {
/* 263 */           ((EntityPlayer)this.tracker).getBukkitEntity().injectScaledMaxHealth(set, false);
/*     */         }
/*     */         
/* 266 */         broadcastIncludingSelf(new PacketPlayOutUpdateAttributes(this.tracker.getId(), set));
/*     */       }
/*     */       
/* 269 */       set.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public void broadcast(Packet packet)
/*     */   {
/* 275 */     Iterator iterator = this.trackedPlayers.iterator();
/*     */     
/* 277 */     while (iterator.hasNext()) {
/* 278 */       EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/*     */       
/* 280 */       entityplayer.playerConnection.sendPacket(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void broadcastIncludingSelf(Packet packet)
/*     */   {
/* 286 */     broadcast(packet);
/* 287 */     if ((this.tracker instanceof EntityPlayer)) {
/* 288 */       ((EntityPlayer)this.tracker).playerConnection.sendPacket(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a()
/*     */   {
/* 294 */     Iterator iterator = this.trackedPlayers.iterator();
/*     */     
/* 296 */     while (iterator.hasNext()) {
/* 297 */       EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/*     */       
/* 299 */       entityplayer.d(this.tracker);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(EntityPlayer entityplayer)
/*     */   {
/* 305 */     if (this.trackedPlayers.contains(entityplayer)) {
/* 306 */       entityplayer.d(this.tracker);
/* 307 */       this.trackedPlayers.remove(entityplayer);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updatePlayer(EntityPlayer entityplayer)
/*     */   {
/* 313 */     AsyncCatcher.catchOp("player tracker update");
/* 314 */     if (entityplayer != this.tracker) {
/* 315 */       if (c(entityplayer)) {
/* 316 */         if ((!this.trackedPlayers.contains(entityplayer)) && ((e(entityplayer)) || (this.tracker.attachedToPlayer)))
/*     */         {
/* 318 */           if ((this.tracker instanceof EntityPlayer)) {
/* 319 */             Player player = ((EntityPlayer)this.tracker).getBukkitEntity();
/* 320 */             if (!entityplayer.getBukkitEntity().canSee(player)) {
/* 321 */               return;
/*     */             }
/*     */           }
/*     */           
/* 325 */           entityplayer.removeQueue.remove(Integer.valueOf(this.tracker.getId()));
/*     */           
/* 327 */           this.trackedPlayers.add(entityplayer);
/* 328 */           Packet packet = c();
/*     */           
/* 330 */           entityplayer.playerConnection.sendPacket(packet);
/* 331 */           if (!this.tracker.getDataWatcher().d()) {
/* 332 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityMetadata(this.tracker.getId(), this.tracker.getDataWatcher(), true));
/*     */           }
/*     */           
/* 335 */           NBTTagCompound nbttagcompound = this.tracker.getNBTTag();
/*     */           
/* 337 */           if (nbttagcompound != null) {
/* 338 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateEntityNBT(this.tracker.getId(), nbttagcompound));
/*     */           }
/*     */           
/* 341 */           if ((this.tracker instanceof EntityLiving)) {
/* 342 */             AttributeMapServer attributemapserver = (AttributeMapServer)((EntityLiving)this.tracker).getAttributeMap();
/* 343 */             Collection collection = attributemapserver.c();
/*     */             
/*     */ 
/* 346 */             if (this.tracker.getId() == entityplayer.getId()) {
/* 347 */               ((EntityPlayer)this.tracker).getBukkitEntity().injectScaledMaxHealth(collection, false);
/*     */             }
/*     */             
/*     */ 
/* 351 */             if (!collection.isEmpty()) {
/* 352 */               entityplayer.playerConnection.sendPacket(new PacketPlayOutUpdateAttributes(this.tracker.getId(), collection));
/*     */             }
/*     */           }
/*     */           
/* 356 */           this.j = this.tracker.motX;
/* 357 */           this.k = this.tracker.motY;
/* 358 */           this.l = this.tracker.motZ;
/* 359 */           if ((this.u) && (!(packet instanceof PacketPlayOutSpawnEntityLiving))) {
/* 360 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityVelocity(this.tracker.getId(), this.tracker.motX, this.tracker.motY, this.tracker.motZ));
/*     */           }
/*     */           
/* 363 */           if (this.tracker.vehicle != null) {
/* 364 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, this.tracker, this.tracker.vehicle));
/*     */           }
/*     */           
/* 367 */           if (((this.tracker instanceof EntityInsentient)) && (((EntityInsentient)this.tracker).getLeashHolder() != null)) {
/* 368 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, this.tracker, ((EntityInsentient)this.tracker).getLeashHolder()));
/*     */           }
/*     */           
/* 371 */           if ((this.tracker instanceof EntityLiving)) {
/* 372 */             for (int i = 0; i < 5; i++) {
/* 373 */               ItemStack itemstack = ((EntityLiving)this.tracker).getEquipment(i);
/*     */               
/* 375 */               if (itemstack != null) {
/* 376 */                 entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEquipment(this.tracker.getId(), i, itemstack));
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 381 */           if ((this.tracker instanceof EntityHuman)) {
/* 382 */             EntityHuman entityhuman = (EntityHuman)this.tracker;
/*     */             
/* 384 */             if (entityhuman.isSleeping()) {
/* 385 */               entityplayer.playerConnection.sendPacket(new PacketPlayOutBed(entityhuman, new BlockPosition(this.tracker)));
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 390 */           this.i = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
/* 391 */           broadcast(new PacketPlayOutEntityHeadRotation(this.tracker, (byte)this.i));
/*     */           
/*     */ 
/* 394 */           if ((this.tracker instanceof EntityLiving)) {
/* 395 */             EntityLiving entityliving = (EntityLiving)this.tracker;
/* 396 */             Iterator iterator = entityliving.getEffects().iterator();
/*     */             
/* 398 */             while (iterator.hasNext()) {
/* 399 */               MobEffect mobeffect = (MobEffect)iterator.next();
/*     */               
/* 401 */               entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityEffect(this.tracker.getId(), mobeffect));
/*     */             }
/*     */           }
/*     */         }
/* 405 */       } else if (this.trackedPlayers.contains(entityplayer)) {
/* 406 */         this.trackedPlayers.remove(entityplayer);
/* 407 */         entityplayer.d(this.tracker);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean c(EntityPlayer entityplayer)
/*     */   {
/* 415 */     double d0 = entityplayer.locX - this.tracker.locX;
/* 416 */     double d1 = entityplayer.locZ - this.tracker.locZ;
/*     */     
/*     */ 
/* 419 */     return (d0 >= -this.b) && (d0 <= this.b) && (d1 >= -this.b) && (d1 <= this.b) && (this.tracker.a(entityplayer));
/*     */   }
/*     */   
/*     */   private boolean e(EntityPlayer entityplayer) {
/* 423 */     return entityplayer.u().getPlayerChunkMap().a(entityplayer, this.tracker.ae, this.tracker.ag);
/*     */   }
/*     */   
/*     */   public void scanPlayers(List<EntityHuman> list) {
/* 427 */     for (int i = 0; i < list.size(); i++) {
/* 428 */       updatePlayer((EntityPlayer)list.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */   private Packet c()
/*     */   {
/* 434 */     if (this.tracker.dead)
/*     */     {
/*     */ 
/* 437 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 441 */     if ((this.tracker instanceof EntityItem))
/* 442 */       return new PacketPlayOutSpawnEntity(this.tracker, 2, 1);
/* 443 */     if ((this.tracker instanceof EntityPlayer))
/* 444 */       return new PacketPlayOutNamedEntitySpawn((EntityHuman)this.tracker);
/* 445 */     if ((this.tracker instanceof EntityMinecartAbstract)) {
/* 446 */       EntityMinecartAbstract entityminecartabstract = (EntityMinecartAbstract)this.tracker;
/*     */       
/* 448 */       return new PacketPlayOutSpawnEntity(this.tracker, 10, entityminecartabstract.s().a()); }
/* 449 */     if ((this.tracker instanceof EntityBoat))
/* 450 */       return new PacketPlayOutSpawnEntity(this.tracker, 1);
/* 451 */     if ((this.tracker instanceof IAnimal)) {
/* 452 */       this.i = MathHelper.d(this.tracker.getHeadRotation() * 256.0F / 360.0F);
/* 453 */       return new PacketPlayOutSpawnEntityLiving((EntityLiving)this.tracker); }
/* 454 */     if ((this.tracker instanceof EntityFishingHook)) {
/* 455 */       EntityHuman entityhuman = ((EntityFishingHook)this.tracker).owner;
/*     */       
/* 457 */       return new PacketPlayOutSpawnEntity(this.tracker, 90, entityhuman != null ? entityhuman.getId() : this.tracker.getId()); }
/* 458 */     if ((this.tracker instanceof EntityArrow)) {
/* 459 */       Entity entity = ((EntityArrow)this.tracker).shooter;
/*     */       
/* 461 */       return new PacketPlayOutSpawnEntity(this.tracker, 60, entity != null ? entity.getId() : this.tracker.getId()); }
/* 462 */     if ((this.tracker instanceof EntitySnowball))
/* 463 */       return new PacketPlayOutSpawnEntity(this.tracker, 61);
/* 464 */     if ((this.tracker instanceof EntityPotion))
/* 465 */       return new PacketPlayOutSpawnEntity(this.tracker, 73, ((EntityPotion)this.tracker).getPotionValue());
/* 466 */     if ((this.tracker instanceof EntityThrownExpBottle))
/* 467 */       return new PacketPlayOutSpawnEntity(this.tracker, 75);
/* 468 */     if ((this.tracker instanceof EntityEnderPearl))
/* 469 */       return new PacketPlayOutSpawnEntity(this.tracker, 65);
/* 470 */     if ((this.tracker instanceof EntityEnderSignal))
/* 471 */       return new PacketPlayOutSpawnEntity(this.tracker, 72);
/* 472 */     if ((this.tracker instanceof EntityFireworks)) {
/* 473 */       return new PacketPlayOutSpawnEntity(this.tracker, 76);
/*     */     }
/*     */     
/*     */ 
/* 477 */     if ((this.tracker instanceof EntityFireball)) {
/* 478 */       EntityFireball entityfireball = (EntityFireball)this.tracker;
/*     */       
/* 480 */       PacketPlayOutSpawnEntity packetplayoutspawnentity = null;
/* 481 */       byte b0 = 63;
/*     */       
/* 483 */       if ((this.tracker instanceof EntitySmallFireball)) {
/* 484 */         b0 = 64;
/* 485 */       } else if ((this.tracker instanceof EntityWitherSkull)) {
/* 486 */         b0 = 66;
/*     */       }
/*     */       
/* 489 */       if (entityfireball.shooter != null) {
/* 490 */         packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, b0, ((EntityFireball)this.tracker).shooter.getId());
/*     */       } else {
/* 492 */         packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, b0, 0);
/*     */       }
/*     */       
/* 495 */       packetplayoutspawnentity.d((int)(entityfireball.dirX * 8000.0D));
/* 496 */       packetplayoutspawnentity.e((int)(entityfireball.dirY * 8000.0D));
/* 497 */       packetplayoutspawnentity.f((int)(entityfireball.dirZ * 8000.0D));
/* 498 */       return packetplayoutspawnentity; }
/* 499 */     if ((this.tracker instanceof EntityEgg))
/* 500 */       return new PacketPlayOutSpawnEntity(this.tracker, 62);
/* 501 */     if ((this.tracker instanceof EntityTNTPrimed))
/* 502 */       return new PacketPlayOutSpawnEntity(this.tracker, 50);
/* 503 */     if ((this.tracker instanceof EntityEnderCrystal))
/* 504 */       return new PacketPlayOutSpawnEntity(this.tracker, 51);
/* 505 */     if ((this.tracker instanceof EntityFallingBlock)) {
/* 506 */       EntityFallingBlock entityfallingblock = (EntityFallingBlock)this.tracker;
/*     */       
/* 508 */       return new PacketPlayOutSpawnEntity(this.tracker, 70, Block.getCombinedId(entityfallingblock.getBlock())); }
/* 509 */     if ((this.tracker instanceof EntityArmorStand))
/* 510 */       return new PacketPlayOutSpawnEntity(this.tracker, 78);
/* 511 */     if ((this.tracker instanceof EntityPainting)) {
/* 512 */       return new PacketPlayOutSpawnEntityPainting((EntityPainting)this.tracker);
/*     */     }
/*     */     
/*     */ 
/* 516 */     if ((this.tracker instanceof EntityItemFrame)) {
/* 517 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.tracker;
/*     */       
/* 519 */       PacketPlayOutSpawnEntity packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, 71, entityitemframe.direction.b());
/* 520 */       BlockPosition blockposition = entityitemframe.getBlockPosition();
/* 521 */       packetplayoutspawnentity.a(MathHelper.d(blockposition.getX() * 32));
/* 522 */       packetplayoutspawnentity.b(MathHelper.d(blockposition.getY() * 32));
/* 523 */       packetplayoutspawnentity.c(MathHelper.d(blockposition.getZ() * 32));
/* 524 */       return packetplayoutspawnentity; }
/* 525 */     if ((this.tracker instanceof EntityLeash)) {
/* 526 */       EntityLeash entityleash = (EntityLeash)this.tracker;
/*     */       
/* 528 */       PacketPlayOutSpawnEntity packetplayoutspawnentity = new PacketPlayOutSpawnEntity(this.tracker, 77);
/* 529 */       BlockPosition blockposition = entityleash.getBlockPosition();
/* 530 */       packetplayoutspawnentity.a(MathHelper.d(blockposition.getX() * 32));
/* 531 */       packetplayoutspawnentity.b(MathHelper.d(blockposition.getY() * 32));
/* 532 */       packetplayoutspawnentity.c(MathHelper.d(blockposition.getZ() * 32));
/* 533 */       return packetplayoutspawnentity; }
/* 534 */     if ((this.tracker instanceof EntityExperienceOrb)) {
/* 535 */       return new PacketPlayOutSpawnEntityExperienceOrb((EntityExperienceOrb)this.tracker);
/*     */     }
/* 537 */     throw new IllegalArgumentException("Don't know how to add " + this.tracker.getClass() + "!");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void clear(EntityPlayer entityplayer)
/*     */   {
/* 544 */     AsyncCatcher.catchOp("player tracker clear");
/* 545 */     if (this.trackedPlayers.contains(entityplayer)) {
/* 546 */       this.trackedPlayers.remove(entityplayer);
/* 547 */       entityplayer.d(this.tracker);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityTrackerEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */