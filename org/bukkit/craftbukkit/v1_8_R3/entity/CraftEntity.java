/*     */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_8_R3.EntityArrow;
/*     */ import net.minecraft.server.v1_8_R3.EntityComplexPart;
/*     */ import net.minecraft.server.v1_8_R3.EntityEnderDragon;
/*     */ import net.minecraft.server.v1_8_R3.EntityGiantZombie;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityRabbit;
/*     */ import net.minecraft.server.v1_8_R3.EntitySilverfish;
/*     */ import net.minecraft.server.v1_8_R3.EntitySlime;
/*     */ import net.minecraft.server.v1_8_R3.EntityWaterAnimal;
/*     */ import net.minecraft.server.v1_8_R3.EntityWeather;
/*     */ import org.bukkit.EntityEffect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.metadata.EntityMetadataStore;
/*     */ import org.bukkit.entity.Entity.Spigot;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.permissions.PermissibleBase;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionAttachment;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public abstract class CraftEntity implements org.bukkit.entity.Entity
/*     */ {
/*  30 */   private static final PermissibleBase perm = new PermissibleBase(new org.bukkit.permissions.ServerOperator()
/*     */   {
/*     */     public boolean isOp()
/*     */     {
/*  34 */       return false;
/*     */     }
/*     */     
/*     */     public void setOp(boolean value) {}
/*  30 */   });
/*     */   
/*     */ 
/*     */ 
/*     */   protected final CraftServer server;
/*     */   
/*     */ 
/*     */ 
/*     */   protected net.minecraft.server.v1_8_R3.Entity entity;
/*     */   
/*     */ 
/*     */ 
/*     */   private EntityDamageEvent lastDamageEvent;
/*     */   
/*     */ 
/*     */ 
/*     */   public CraftEntity(CraftServer server, net.minecraft.server.v1_8_R3.Entity entity)
/*     */   {
/*  48 */     this.server = server;
/*  49 */     this.entity = entity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static CraftEntity getEntity(CraftServer server, net.minecraft.server.v1_8_R3.Entity entity)
/*     */   {
/*  56 */     if ((entity instanceof net.minecraft.server.v1_8_R3.EntityLiving))
/*     */     {
/*  58 */       if ((entity instanceof EntityHuman)) {
/*  59 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityPlayer)) return new CraftPlayer(server, (net.minecraft.server.v1_8_R3.EntityPlayer)entity);
/*  60 */         return new CraftHumanEntity(server, (EntityHuman)entity);
/*     */       }
/*     */       
/*  63 */       if ((entity instanceof EntityWaterAnimal)) {
/*  64 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntitySquid)) return new CraftSquid(server, (net.minecraft.server.v1_8_R3.EntitySquid)entity);
/*  65 */         return new CraftWaterMob(server, (EntityWaterAnimal)entity);
/*     */       }
/*  67 */       if ((entity instanceof net.minecraft.server.v1_8_R3.EntityCreature))
/*     */       {
/*  69 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityAnimal)) {
/*  70 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityChicken)) return new CraftChicken(server, (net.minecraft.server.v1_8_R3.EntityChicken)entity);
/*  71 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityCow)) {
/*  72 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMushroomCow)) return new CraftMushroomCow(server, (net.minecraft.server.v1_8_R3.EntityMushroomCow)entity);
/*  73 */             return new CraftCow(server, (net.minecraft.server.v1_8_R3.EntityCow)entity);
/*     */           }
/*  75 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityPig)) return new CraftPig(server, (net.minecraft.server.v1_8_R3.EntityPig)entity);
/*  76 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityTameableAnimal)) {
/*  77 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityWolf)) return new CraftWolf(server, (net.minecraft.server.v1_8_R3.EntityWolf)entity);
/*  78 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityOcelot)) return new CraftOcelot(server, (net.minecraft.server.v1_8_R3.EntityOcelot)entity);
/*     */           } else {
/*  80 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntitySheep)) return new CraftSheep(server, (net.minecraft.server.v1_8_R3.EntitySheep)entity);
/*  81 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityHorse)) return new CraftHorse(server, (net.minecraft.server.v1_8_R3.EntityHorse)entity);
/*  82 */             if ((entity instanceof EntityRabbit)) return new CraftRabbit(server, (EntityRabbit)entity);
/*  83 */             return new CraftAnimals(server, (net.minecraft.server.v1_8_R3.EntityAnimal)entity);
/*     */           }
/*     */         } else {
/*  86 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMonster)) {
/*  87 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityZombie)) {
/*  88 */               if ((entity instanceof net.minecraft.server.v1_8_R3.EntityPigZombie)) return new CraftPigZombie(server, (net.minecraft.server.v1_8_R3.EntityPigZombie)entity);
/*  89 */               return new CraftZombie(server, (net.minecraft.server.v1_8_R3.EntityZombie)entity);
/*     */             }
/*  91 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityCreeper)) return new CraftCreeper(server, (net.minecraft.server.v1_8_R3.EntityCreeper)entity);
/*  92 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityEnderman)) return new CraftEnderman(server, (net.minecraft.server.v1_8_R3.EntityEnderman)entity);
/*  93 */             if ((entity instanceof EntitySilverfish)) return new CraftSilverfish(server, (EntitySilverfish)entity);
/*  94 */             if ((entity instanceof EntityGiantZombie)) return new CraftGiant(server, (EntityGiantZombie)entity);
/*  95 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntitySkeleton)) return new CraftSkeleton(server, (net.minecraft.server.v1_8_R3.EntitySkeleton)entity);
/*  96 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityBlaze)) return new CraftBlaze(server, (net.minecraft.server.v1_8_R3.EntityBlaze)entity);
/*  97 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityWitch)) return new CraftWitch(server, (net.minecraft.server.v1_8_R3.EntityWitch)entity);
/*  98 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityWither)) return new CraftWither(server, (net.minecraft.server.v1_8_R3.EntityWither)entity);
/*  99 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntitySpider)) {
/* 100 */               if ((entity instanceof net.minecraft.server.v1_8_R3.EntityCaveSpider)) return new CraftCaveSpider(server, (net.minecraft.server.v1_8_R3.EntityCaveSpider)entity);
/* 101 */               return new CraftSpider(server, (net.minecraft.server.v1_8_R3.EntitySpider)entity);
/*     */             }
/* 103 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityEndermite)) return new CraftEndermite(server, (net.minecraft.server.v1_8_R3.EntityEndermite)entity);
/* 104 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityGuardian)) { return new CraftGuardian(server, (net.minecraft.server.v1_8_R3.EntityGuardian)entity);
/*     */             }
/* 106 */             return new CraftMonster(server, (net.minecraft.server.v1_8_R3.EntityMonster)entity);
/*     */           }
/* 108 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityGolem)) {
/* 109 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntitySnowman)) return new CraftSnowman(server, (net.minecraft.server.v1_8_R3.EntitySnowman)entity);
/* 110 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityIronGolem)) return new CraftIronGolem(server, (net.minecraft.server.v1_8_R3.EntityIronGolem)entity);
/*     */           } else {
/* 112 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityVillager)) return new CraftVillager(server, (net.minecraft.server.v1_8_R3.EntityVillager)entity);
/* 113 */             return new CraftCreature(server, (net.minecraft.server.v1_8_R3.EntityCreature)entity);
/*     */           }
/*     */         }
/* 116 */       } else { if ((entity instanceof EntitySlime)) {
/* 117 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMagmaCube)) return new CraftMagmaCube(server, (net.minecraft.server.v1_8_R3.EntityMagmaCube)entity);
/* 118 */           return new CraftSlime(server, (EntitySlime)entity);
/*     */         }
/*     */         
/* 121 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityFlying)) {
/* 122 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityGhast)) return new CraftGhast(server, (net.minecraft.server.v1_8_R3.EntityGhast)entity);
/* 123 */           return new CraftFlying(server, (net.minecraft.server.v1_8_R3.EntityFlying)entity);
/*     */         }
/* 125 */         if ((entity instanceof EntityEnderDragon)) {
/* 126 */           return new CraftEnderDragon(server, (EntityEnderDragon)entity);
/*     */         }
/*     */         
/* 129 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityAmbient)) {
/* 130 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityBat)) return new CraftBat(server, (net.minecraft.server.v1_8_R3.EntityBat)entity);
/* 131 */           return new CraftAmbient(server, (net.minecraft.server.v1_8_R3.EntityAmbient)entity);
/*     */         }
/* 133 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityArmorStand)) return new CraftArmorStand(server, (net.minecraft.server.v1_8_R3.EntityArmorStand)entity);
/* 134 */         return new CraftLivingEntity(server, (net.minecraft.server.v1_8_R3.EntityLiving)entity);
/*     */       }
/* 136 */     } else { if ((entity instanceof EntityComplexPart)) {
/* 137 */         EntityComplexPart part = (EntityComplexPart)entity;
/* 138 */         if ((part.owner instanceof EntityEnderDragon)) return new CraftEnderDragonPart(server, (EntityComplexPart)entity);
/* 139 */         return new CraftComplexPart(server, (EntityComplexPart)entity);
/*     */       }
/* 141 */       if ((entity instanceof net.minecraft.server.v1_8_R3.EntityExperienceOrb)) return new CraftExperienceOrb(server, (net.minecraft.server.v1_8_R3.EntityExperienceOrb)entity);
/* 142 */       if ((entity instanceof EntityArrow)) return new CraftArrow(server, (EntityArrow)entity);
/* 143 */       if ((entity instanceof net.minecraft.server.v1_8_R3.EntityBoat)) return new CraftBoat(server, (net.minecraft.server.v1_8_R3.EntityBoat)entity);
/* 144 */       if ((entity instanceof net.minecraft.server.v1_8_R3.EntityProjectile)) {
/* 145 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityEgg)) return new CraftEgg(server, (net.minecraft.server.v1_8_R3.EntityEgg)entity);
/* 146 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntitySnowball)) return new CraftSnowball(server, (net.minecraft.server.v1_8_R3.EntitySnowball)entity);
/* 147 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityPotion)) return new CraftThrownPotion(server, (net.minecraft.server.v1_8_R3.EntityPotion)entity);
/* 148 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityEnderPearl)) return new CraftEnderPearl(server, (net.minecraft.server.v1_8_R3.EntityEnderPearl)entity);
/* 149 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityThrownExpBottle)) return new CraftThrownExpBottle(server, (net.minecraft.server.v1_8_R3.EntityThrownExpBottle)entity);
/*     */       } else {
/* 151 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityFallingBlock)) return new CraftFallingSand(server, (net.minecraft.server.v1_8_R3.EntityFallingBlock)entity);
/* 152 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityFireball)) {
/* 153 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntitySmallFireball)) return new CraftSmallFireball(server, (net.minecraft.server.v1_8_R3.EntitySmallFireball)entity);
/* 154 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityLargeFireball)) return new CraftLargeFireball(server, (net.minecraft.server.v1_8_R3.EntityLargeFireball)entity);
/* 155 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityWitherSkull)) return new CraftWitherSkull(server, (net.minecraft.server.v1_8_R3.EntityWitherSkull)entity);
/* 156 */           return new CraftFireball(server, (net.minecraft.server.v1_8_R3.EntityFireball)entity);
/*     */         }
/* 158 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityEnderSignal)) return new CraftEnderSignal(server, (net.minecraft.server.v1_8_R3.EntityEnderSignal)entity);
/* 159 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityEnderCrystal)) return new CraftEnderCrystal(server, (net.minecraft.server.v1_8_R3.EntityEnderCrystal)entity);
/* 160 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityFishingHook)) return new CraftFish(server, (net.minecraft.server.v1_8_R3.EntityFishingHook)entity);
/* 161 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityItem)) return new CraftItem(server, (net.minecraft.server.v1_8_R3.EntityItem)entity);
/* 162 */         if ((entity instanceof EntityWeather)) {
/* 163 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityLightning)) return new CraftLightningStrike(server, (net.minecraft.server.v1_8_R3.EntityLightning)entity);
/* 164 */           return new CraftWeather(server, (EntityWeather)entity);
/*     */         }
/* 166 */         if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMinecartAbstract)) {
/* 167 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMinecartFurnace)) return new CraftMinecartFurnace(server, (net.minecraft.server.v1_8_R3.EntityMinecartFurnace)entity);
/* 168 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMinecartChest)) return new CraftMinecartChest(server, (net.minecraft.server.v1_8_R3.EntityMinecartChest)entity);
/* 169 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMinecartTNT)) return new CraftMinecartTNT(server, (net.minecraft.server.v1_8_R3.EntityMinecartTNT)entity);
/* 170 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMinecartHopper)) return new CraftMinecartHopper(server, (net.minecraft.server.v1_8_R3.EntityMinecartHopper)entity);
/* 171 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMinecartMobSpawner)) return new CraftMinecartMobSpawner(server, (net.minecraft.server.v1_8_R3.EntityMinecartMobSpawner)entity);
/* 172 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMinecartRideable)) return new CraftMinecartRideable(server, (net.minecraft.server.v1_8_R3.EntityMinecartRideable)entity);
/* 173 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityMinecartCommandBlock)) return new CraftMinecartCommand(server, (net.minecraft.server.v1_8_R3.EntityMinecartCommandBlock)entity);
/* 174 */         } else { if ((entity instanceof net.minecraft.server.v1_8_R3.EntityHanging)) {
/* 175 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityPainting)) return new CraftPainting(server, (net.minecraft.server.v1_8_R3.EntityPainting)entity);
/* 176 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityItemFrame)) return new CraftItemFrame(server, (net.minecraft.server.v1_8_R3.EntityItemFrame)entity);
/* 177 */             if ((entity instanceof net.minecraft.server.v1_8_R3.EntityLeash)) return new CraftLeash(server, (net.minecraft.server.v1_8_R3.EntityLeash)entity);
/* 178 */             return new CraftHanging(server, (net.minecraft.server.v1_8_R3.EntityHanging)entity);
/*     */           }
/* 180 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityTNTPrimed)) return new CraftTNTPrimed(server, (net.minecraft.server.v1_8_R3.EntityTNTPrimed)entity);
/* 181 */           if ((entity instanceof net.minecraft.server.v1_8_R3.EntityFireworks)) return new CraftFirework(server, (net.minecraft.server.v1_8_R3.EntityFireworks)entity);
/*     */         } } }
/* 183 */     throw new AssertionError("Unknown entity " + (entity == null ? null : entity.getClass()));
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 187 */     return new Location(getWorld(), this.entity.locX, this.entity.locY, this.entity.locZ, this.entity.yaw, this.entity.pitch);
/*     */   }
/*     */   
/*     */   public Location getLocation(Location loc) {
/* 191 */     if (loc != null) {
/* 192 */       loc.setWorld(getWorld());
/* 193 */       loc.setX(this.entity.locX);
/* 194 */       loc.setY(this.entity.locY);
/* 195 */       loc.setZ(this.entity.locZ);
/* 196 */       loc.setYaw(this.entity.yaw);
/* 197 */       loc.setPitch(this.entity.pitch);
/*     */     }
/*     */     
/* 200 */     return loc;
/*     */   }
/*     */   
/*     */   public Vector getVelocity() {
/* 204 */     return new Vector(this.entity.motX, this.entity.motY, this.entity.motZ);
/*     */   }
/*     */   
/*     */   public void setVelocity(Vector vel) {
/* 208 */     this.entity.motX = vel.getX();
/* 209 */     this.entity.motY = vel.getY();
/* 210 */     this.entity.motZ = vel.getZ();
/* 211 */     this.entity.velocityChanged = true;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/* 215 */     if ((this.entity instanceof EntityArrow)) {
/* 216 */       return ((EntityArrow)this.entity).isInGround();
/*     */     }
/* 218 */     return this.entity.onGround;
/*     */   }
/*     */   
/*     */   public org.bukkit.World getWorld() {
/* 222 */     return this.entity.world.getWorld();
/*     */   }
/*     */   
/*     */   public boolean teleport(Location location) {
/* 226 */     return teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
/*     */   }
/*     */   
/*     */   public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
/* 230 */     if ((this.entity.passenger != null) || (this.entity.dead)) {
/* 231 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 235 */     this.entity.mount(null);
/*     */     
/*     */ 
/* 238 */     if (!location.getWorld().equals(getWorld())) {
/* 239 */       this.entity.teleportTo(location, cause.equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL));
/* 240 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 245 */     this.entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
/*     */     
/* 247 */     return true;
/*     */   }
/*     */   
/*     */   public boolean teleport(org.bukkit.entity.Entity destination) {
/* 251 */     return teleport(destination.getLocation());
/*     */   }
/*     */   
/*     */   public boolean teleport(org.bukkit.entity.Entity destination, PlayerTeleportEvent.TeleportCause cause) {
/* 255 */     return teleport(destination.getLocation(), cause);
/*     */   }
/*     */   
/*     */   public List<org.bukkit.entity.Entity> getNearbyEntities(double x, double y, double z) {
/* 259 */     List<net.minecraft.server.v1_8_R3.Entity> notchEntityList = this.entity.world.a(this.entity, this.entity.getBoundingBox().grow(x, y, z), null);
/* 260 */     List<org.bukkit.entity.Entity> bukkitEntityList = new java.util.ArrayList(notchEntityList.size());
/*     */     
/* 262 */     for (net.minecraft.server.v1_8_R3.Entity e : notchEntityList) {
/* 263 */       bukkitEntityList.add(e.getBukkitEntity());
/*     */     }
/* 265 */     return bukkitEntityList;
/*     */   }
/*     */   
/*     */   public int getEntityId() {
/* 269 */     return this.entity.getId();
/*     */   }
/*     */   
/*     */   public int getFireTicks() {
/* 273 */     return this.entity.fireTicks;
/*     */   }
/*     */   
/*     */   public int getMaxFireTicks() {
/* 277 */     return this.entity.maxFireTicks;
/*     */   }
/*     */   
/*     */   public void setFireTicks(int ticks) {
/* 281 */     this.entity.fireTicks = ticks;
/*     */   }
/*     */   
/*     */   public void remove() {
/* 285 */     this.entity.dead = true;
/*     */   }
/*     */   
/*     */   public boolean isDead() {
/* 289 */     return !this.entity.isAlive();
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/* 293 */     return (this.entity.isAlive()) && (this.entity.valid);
/*     */   }
/*     */   
/*     */   public org.bukkit.Server getServer() {
/* 297 */     return this.server;
/*     */   }
/*     */   
/*     */   public Vector getMomentum() {
/* 301 */     return getVelocity();
/*     */   }
/*     */   
/*     */   public void setMomentum(Vector value) {
/* 305 */     setVelocity(value);
/*     */   }
/*     */   
/*     */   public org.bukkit.entity.Entity getPassenger() {
/* 309 */     return isEmpty() ? null : getHandle().passenger.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public boolean setPassenger(org.bukkit.entity.Entity passenger) {
/* 313 */     com.google.common.base.Preconditions.checkArgument(!equals(passenger), "Entity cannot ride itself.");
/* 314 */     if ((passenger instanceof CraftEntity)) {
/* 315 */       ((CraftEntity)passenger).getHandle().mount(getHandle());
/* 316 */       return true;
/*     */     }
/* 318 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 323 */     return getHandle().passenger == null;
/*     */   }
/*     */   
/*     */   public boolean eject() {
/* 327 */     if (getHandle().passenger == null) {
/* 328 */       return false;
/*     */     }
/*     */     
/* 331 */     getHandle().passenger.mount(null);
/* 332 */     return true;
/*     */   }
/*     */   
/*     */   public float getFallDistance() {
/* 336 */     return getHandle().fallDistance;
/*     */   }
/*     */   
/*     */   public void setFallDistance(float distance) {
/* 340 */     getHandle().fallDistance = distance;
/*     */   }
/*     */   
/*     */   public void setLastDamageCause(EntityDamageEvent event) {
/* 344 */     this.lastDamageEvent = event;
/*     */   }
/*     */   
/*     */   public EntityDamageEvent getLastDamageCause() {
/* 348 */     return this.lastDamageEvent;
/*     */   }
/*     */   
/*     */   public java.util.UUID getUniqueId() {
/* 352 */     return getHandle().getUniqueID();
/*     */   }
/*     */   
/*     */   public int getTicksLived() {
/* 356 */     return getHandle().ticksLived;
/*     */   }
/*     */   
/*     */   public void setTicksLived(int value) {
/* 360 */     if (value <= 0) {
/* 361 */       throw new IllegalArgumentException("Age must be at least 1 tick");
/*     */     }
/* 363 */     getHandle().ticksLived = value;
/*     */   }
/*     */   
/*     */   public net.minecraft.server.v1_8_R3.Entity getHandle() {
/* 367 */     return this.entity;
/*     */   }
/*     */   
/*     */   public void playEffect(EntityEffect type) {
/* 371 */     getHandle().world.broadcastEntityEffect(getHandle(), type.getData());
/*     */   }
/*     */   
/*     */   public void setHandle(net.minecraft.server.v1_8_R3.Entity entity) {
/* 375 */     this.entity = entity;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 380 */     return "CraftEntity{id=" + getEntityId() + '}';
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 385 */     if (obj == null) {
/* 386 */       return false;
/*     */     }
/* 388 */     if (getClass() != obj.getClass()) {
/* 389 */       return false;
/*     */     }
/* 391 */     CraftEntity other = (CraftEntity)obj;
/* 392 */     return getEntityId() == other.getEntityId();
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 397 */     int hash = 7;
/* 398 */     hash = 29 * hash + getEntityId();
/* 399 */     return hash;
/*     */   }
/*     */   
/*     */   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
/* 403 */     this.server.getEntityMetadata().setMetadata(this, metadataKey, newMetadataValue);
/*     */   }
/*     */   
/*     */   public List<MetadataValue> getMetadata(String metadataKey) {
/* 407 */     return this.server.getEntityMetadata().getMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public boolean hasMetadata(String metadataKey) {
/* 411 */     return this.server.getEntityMetadata().hasMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
/* 415 */     this.server.getEntityMetadata().removeMetadata(this, metadataKey, owningPlugin);
/*     */   }
/*     */   
/*     */   public boolean isInsideVehicle() {
/* 419 */     return getHandle().vehicle != null;
/*     */   }
/*     */   
/*     */   public boolean leaveVehicle() {
/* 423 */     if (getHandle().vehicle == null) {
/* 424 */       return false;
/*     */     }
/*     */     
/* 427 */     getHandle().mount(null);
/* 428 */     return true;
/*     */   }
/*     */   
/*     */   public org.bukkit.entity.Entity getVehicle() {
/* 432 */     if (getHandle().vehicle == null) {
/* 433 */       return null;
/*     */     }
/*     */     
/* 436 */     return getHandle().vehicle.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public void setCustomName(String name)
/*     */   {
/* 441 */     if (name == null) {
/* 442 */       name = "";
/*     */     }
/*     */     
/* 445 */     getHandle().setCustomName(name);
/*     */   }
/*     */   
/*     */   public String getCustomName()
/*     */   {
/* 450 */     String name = getHandle().getCustomName();
/*     */     
/* 452 */     if ((name == null) || (name.length() == 0)) {
/* 453 */       return null;
/*     */     }
/*     */     
/* 456 */     return name;
/*     */   }
/*     */   
/*     */   public void setCustomNameVisible(boolean flag)
/*     */   {
/* 461 */     getHandle().setCustomNameVisible(flag);
/*     */   }
/*     */   
/*     */   public boolean isCustomNameVisible()
/*     */   {
/* 466 */     return getHandle().getCustomNameVisible();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void sendMessage(String message) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void sendMessage(String[] messages) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 481 */     return getHandle().getName();
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(String name)
/*     */   {
/* 486 */     return perm.isPermissionSet(name);
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(Permission perm)
/*     */   {
/* 491 */     return perm.isPermissionSet(perm);
/*     */   }
/*     */   
/*     */   public boolean hasPermission(String name)
/*     */   {
/* 496 */     return perm.hasPermission(name);
/*     */   }
/*     */   
/*     */   public boolean hasPermission(Permission perm)
/*     */   {
/* 501 */     return perm.hasPermission(perm);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value)
/*     */   {
/* 506 */     return perm.addAttachment(plugin, name, value);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin)
/*     */   {
/* 511 */     return perm.addAttachment(plugin);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks)
/*     */   {
/* 516 */     return perm.addAttachment(plugin, name, value, ticks);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, int ticks)
/*     */   {
/* 521 */     return perm.addAttachment(plugin, ticks);
/*     */   }
/*     */   
/*     */   public void removeAttachment(PermissionAttachment attachment)
/*     */   {
/* 526 */     perm.removeAttachment(attachment);
/*     */   }
/*     */   
/*     */   public void recalculatePermissions()
/*     */   {
/* 531 */     perm.recalculatePermissions();
/*     */   }
/*     */   
/*     */   public java.util.Set<org.bukkit.permissions.PermissionAttachmentInfo> getEffectivePermissions()
/*     */   {
/* 536 */     return perm.getEffectivePermissions();
/*     */   }
/*     */   
/*     */   public boolean isOp()
/*     */   {
/* 541 */     return perm.isOp();
/*     */   }
/*     */   
/*     */   public void setOp(boolean value)
/*     */   {
/* 546 */     perm.setOp(value);
/*     */   }
/*     */   
/*     */ 
/* 550 */   private final Entity.Spigot spigot = new Entity.Spigot()
/*     */   {
/*     */ 
/*     */     public boolean isInvulnerable()
/*     */     {
/* 555 */       return CraftEntity.this.getHandle().isInvulnerable(net.minecraft.server.v1_8_R3.DamageSource.GENERIC);
/*     */     }
/*     */   };
/*     */   
/*     */   public Entity.Spigot spigot()
/*     */   {
/* 561 */     return this.spigot;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */