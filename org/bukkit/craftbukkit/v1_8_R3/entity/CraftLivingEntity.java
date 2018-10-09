/*     */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.server.v1_8_R3.AttributeInstance;
/*     */ import net.minecraft.server.v1_8_R3.DamageSource;
/*     */ import net.minecraft.server.v1_8_R3.EntityArmorStand;
/*     */ import net.minecraft.server.v1_8_R3.EntityArrow;
/*     */ import net.minecraft.server.v1_8_R3.EntityEgg;
/*     */ import net.minecraft.server.v1_8_R3.EntityEnderPearl;
/*     */ import net.minecraft.server.v1_8_R3.EntityFireball;
/*     */ import net.minecraft.server.v1_8_R3.EntityFishingHook;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityInsentient;
/*     */ import net.minecraft.server.v1_8_R3.EntityLargeFireball;
/*     */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*     */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*     */ import net.minecraft.server.v1_8_R3.EntityPotion;
/*     */ import net.minecraft.server.v1_8_R3.EntitySmallFireball;
/*     */ import net.minecraft.server.v1_8_R3.EntitySnowball;
/*     */ import net.minecraft.server.v1_8_R3.EntityThrownExpBottle;
/*     */ import net.minecraft.server.v1_8_R3.EntityWither;
/*     */ import net.minecraft.server.v1_8_R3.EntityWitherSkull;
/*     */ import net.minecraft.server.v1_8_R3.GenericAttributes;
/*     */ import net.minecraft.server.v1_8_R3.MobEffect;
/*     */ import net.minecraft.server.v1_8_R3.World;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftEntityEquipment;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Egg;
/*     */ import org.bukkit.entity.EnderPearl;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Fireball;
/*     */ import org.bukkit.entity.Fish;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.entity.SmallFireball;
/*     */ import org.bukkit.entity.Snowball;
/*     */ import org.bukkit.entity.ThrownExpBottle;
/*     */ import org.bukkit.entity.ThrownPotion;
/*     */ import org.bukkit.entity.WitherSkull;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
/*     */ import org.bukkit.inventory.EntityEquipment;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.util.BlockIterator;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class CraftLivingEntity
/*     */   extends CraftEntity implements LivingEntity
/*     */ {
/*     */   private CraftEntityEquipment equipment;
/*     */   
/*     */   public CraftLivingEntity(CraftServer server, EntityLiving entity)
/*     */   {
/*  70 */     super(server, entity);
/*     */     
/*  72 */     if (((entity instanceof EntityInsentient)) || ((entity instanceof EntityArmorStand))) {
/*  73 */       this.equipment = new CraftEntityEquipment(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getHealth() {
/*  78 */     return Math.min(Math.max(0.0F, getHandle().getHealth()), getMaxHealth());
/*     */   }
/*     */   
/*     */   public void setHealth(double health) {
/*  82 */     if ((health < 0.0D) || (health > getMaxHealth())) {
/*  83 */       throw new IllegalArgumentException("Health must be between 0 and " + getMaxHealth());
/*     */     }
/*     */     
/*  86 */     if (((this.entity instanceof EntityPlayer)) && (health == 0.0D)) {
/*  87 */       ((EntityPlayer)this.entity).die(DamageSource.GENERIC);
/*     */     }
/*     */     
/*  90 */     getHandle().setHealth((float)health);
/*     */   }
/*     */   
/*     */   public double getMaxHealth() {
/*  94 */     return getHandle().getMaxHealth();
/*     */   }
/*     */   
/*     */   public void setMaxHealth(double amount) {
/*  98 */     Validate.isTrue(amount > 0.0D, "Max health must be greater than 0");
/*     */     
/* 100 */     getHandle().getAttributeInstance(GenericAttributes.maxHealth).setValue(amount);
/*     */     
/* 102 */     if (getHealth() > amount) {
/* 103 */       setHealth(amount);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetMaxHealth() {
/* 108 */     setMaxHealth(getHandle().getMaxHealth());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Egg throwEgg() {
/* 113 */     return (Egg)launchProjectile(Egg.class);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Snowball throwSnowball() {
/* 118 */     return (Snowball)launchProjectile(Snowball.class);
/*     */   }
/*     */   
/*     */   public double getEyeHeight() {
/* 122 */     return getHandle().getHeadHeight();
/*     */   }
/*     */   
/*     */   public double getEyeHeight(boolean ignoreSneaking) {
/* 126 */     return getEyeHeight();
/*     */   }
/*     */   
/*     */   private List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance, int maxLength) {
/* 130 */     if (maxDistance > 120) {
/* 131 */       maxDistance = 120;
/*     */     }
/* 133 */     ArrayList<Block> blocks = new ArrayList();
/* 134 */     Iterator<Block> itr = new BlockIterator(this, maxDistance);
/* 135 */     while (itr.hasNext()) {
/* 136 */       Block block = (Block)itr.next();
/* 137 */       blocks.add(block);
/* 138 */       if ((maxLength != 0) && (blocks.size() > maxLength)) {
/* 139 */         blocks.remove(0);
/*     */       }
/* 141 */       int id = block.getTypeId();
/* 142 */       if (transparent == null ? 
/* 143 */         id != 0 : 
/*     */         
/*     */ 
/*     */ 
/* 147 */         !transparent.contains(Byte.valueOf((byte)id))) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 152 */     return blocks;
/*     */   }
/*     */   
/*     */   private List<Block> getLineOfSight(Set<Material> transparent, int maxDistance, int maxLength) {
/* 156 */     if (maxDistance > 120) {
/* 157 */       maxDistance = 120;
/*     */     }
/* 159 */     ArrayList<Block> blocks = new ArrayList();
/* 160 */     Iterator<Block> itr = new BlockIterator(this, maxDistance);
/* 161 */     while (itr.hasNext()) {
/* 162 */       Block block = (Block)itr.next();
/* 163 */       blocks.add(block);
/* 164 */       if ((maxLength != 0) && (blocks.size() > maxLength)) {
/* 165 */         blocks.remove(0);
/*     */       }
/* 167 */       Material material = block.getType();
/* 168 */       if (transparent == null ? 
/* 169 */         !material.equals(Material.AIR) : 
/*     */         
/*     */ 
/*     */ 
/* 173 */         !transparent.contains(material)) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/* 178 */     return blocks;
/*     */   }
/*     */   
/*     */   public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
/* 182 */     return getLineOfSight(transparent, maxDistance, 0);
/*     */   }
/*     */   
/*     */   public List<Block> getLineOfSight(Set<Material> transparent, int maxDistance) {
/* 186 */     return getLineOfSight(transparent, maxDistance, 0);
/*     */   }
/*     */   
/*     */   public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
/* 190 */     List<Block> blocks = getLineOfSight(transparent, maxDistance, 1);
/* 191 */     return (Block)blocks.get(0);
/*     */   }
/*     */   
/*     */   public Block getTargetBlock(Set<Material> transparent, int maxDistance) {
/* 195 */     List<Block> blocks = getLineOfSight(transparent, maxDistance, 1);
/* 196 */     return (Block)blocks.get(0);
/*     */   }
/*     */   
/*     */   public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
/* 200 */     return getLineOfSight(transparent, maxDistance, 2);
/*     */   }
/*     */   
/*     */   public List<Block> getLastTwoTargetBlocks(Set<Material> transparent, int maxDistance) {
/* 204 */     return getLineOfSight(transparent, maxDistance, 2);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Arrow shootArrow() {
/* 209 */     return (Arrow)launchProjectile(Arrow.class);
/*     */   }
/*     */   
/*     */   public int getRemainingAir() {
/* 213 */     return getHandle().getAirTicks();
/*     */   }
/*     */   
/*     */   public void setRemainingAir(int ticks) {
/* 217 */     getHandle().setAirTicks(ticks);
/*     */   }
/*     */   
/*     */   public int getMaximumAir() {
/* 221 */     return getHandle().maxAirTicks;
/*     */   }
/*     */   
/*     */   public void setMaximumAir(int ticks) {
/* 225 */     getHandle().maxAirTicks = ticks;
/*     */   }
/*     */   
/*     */   public void damage(double amount) {
/* 229 */     damage(amount, null);
/*     */   }
/*     */   
/*     */   public void damage(double amount, org.bukkit.entity.Entity source) {
/* 233 */     DamageSource reason = DamageSource.GENERIC;
/*     */     
/* 235 */     if ((source instanceof HumanEntity)) {
/* 236 */       reason = DamageSource.playerAttack(((CraftHumanEntity)source).getHandle());
/* 237 */     } else if ((source instanceof LivingEntity)) {
/* 238 */       reason = DamageSource.mobAttack(((CraftLivingEntity)source).getHandle());
/*     */     }
/*     */     
/* 241 */     this.entity.damageEntity(reason, (float)amount);
/*     */   }
/*     */   
/*     */   public Location getEyeLocation() {
/* 245 */     Location loc = getLocation();
/* 246 */     loc.setY(loc.getY() + getEyeHeight());
/* 247 */     return loc;
/*     */   }
/*     */   
/*     */   public int getMaximumNoDamageTicks() {
/* 251 */     return getHandle().maxNoDamageTicks;
/*     */   }
/*     */   
/*     */   public void setMaximumNoDamageTicks(int ticks) {
/* 255 */     getHandle().maxNoDamageTicks = ticks;
/*     */   }
/*     */   
/*     */   public double getLastDamage() {
/* 259 */     return getHandle().lastDamage;
/*     */   }
/*     */   
/*     */   public void setLastDamage(double damage) {
/* 263 */     getHandle().lastDamage = ((float)damage);
/*     */   }
/*     */   
/*     */   public int getNoDamageTicks() {
/* 267 */     return getHandle().noDamageTicks;
/*     */   }
/*     */   
/*     */   public void setNoDamageTicks(int ticks) {
/* 271 */     getHandle().noDamageTicks = ticks;
/*     */   }
/*     */   
/*     */   public EntityLiving getHandle()
/*     */   {
/* 276 */     return (EntityLiving)this.entity;
/*     */   }
/*     */   
/*     */   public void setHandle(EntityLiving entity) {
/* 280 */     super.setHandle(entity);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 285 */     return "CraftLivingEntity{id=" + getEntityId() + '}';
/*     */   }
/*     */   
/*     */   public Player getKiller() {
/* 289 */     return getHandle().killer == null ? null : (Player)getHandle().killer.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public boolean addPotionEffect(PotionEffect effect) {
/* 293 */     return addPotionEffect(effect, false);
/*     */   }
/*     */   
/*     */   public boolean addPotionEffect(PotionEffect effect, boolean force) {
/* 297 */     if (hasPotionEffect(effect.getType())) {
/* 298 */       if (!force) {
/* 299 */         return false;
/*     */       }
/* 301 */       removePotionEffect(effect.getType());
/*     */     }
/* 303 */     getHandle().addEffect(new MobEffect(effect.getType().getId(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.hasParticles()));
/* 304 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addPotionEffects(Collection<PotionEffect> effects) {
/* 308 */     boolean success = true;
/* 309 */     for (PotionEffect effect : effects) {
/* 310 */       success &= addPotionEffect(effect);
/*     */     }
/* 312 */     return success;
/*     */   }
/*     */   
/*     */   public boolean hasPotionEffect(PotionEffectType type) {
/* 316 */     return getHandle().hasEffect(net.minecraft.server.v1_8_R3.MobEffectList.byId[type.getId()]);
/*     */   }
/*     */   
/*     */   public void removePotionEffect(PotionEffectType type) {
/* 320 */     getHandle().removeEffect(type.getId());
/*     */   }
/*     */   
/*     */   public Collection<PotionEffect> getActivePotionEffects() {
/* 324 */     List<PotionEffect> effects = new ArrayList();
/* 325 */     for (Object raw : getHandle().effects.values())
/* 326 */       if ((raw instanceof MobEffect))
/*     */       {
/* 328 */         MobEffect handle = (MobEffect)raw;
/* 329 */         effects.add(new PotionEffect(PotionEffectType.getById(handle.getEffectId()), handle.getDuration(), handle.getAmplifier(), handle.isAmbient(), handle.isShowParticles()));
/*     */       }
/* 331 */     return effects;
/*     */   }
/*     */   
/*     */   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
/* 335 */     return launchProjectile(projectile, null);
/*     */   }
/*     */   
/*     */   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity)
/*     */   {
/* 340 */     World world = ((CraftWorld)getWorld()).getHandle();
/* 341 */     net.minecraft.server.v1_8_R3.Entity launch = null;
/*     */     
/* 343 */     if (Snowball.class.isAssignableFrom(projectile)) {
/* 344 */       launch = new EntitySnowball(world, getHandle());
/* 345 */     } else if (Egg.class.isAssignableFrom(projectile)) {
/* 346 */       launch = new EntityEgg(world, getHandle());
/* 347 */     } else if (EnderPearl.class.isAssignableFrom(projectile)) {
/* 348 */       launch = new EntityEnderPearl(world, getHandle());
/* 349 */     } else if (Arrow.class.isAssignableFrom(projectile)) {
/* 350 */       launch = new EntityArrow(world, getHandle(), 1.0F);
/* 351 */     } else if (ThrownPotion.class.isAssignableFrom(projectile)) {
/* 352 */       launch = new EntityPotion(world, getHandle(), CraftItemStack.asNMSCopy(new ItemStack(Material.POTION, 1)));
/* 353 */     } else if (ThrownExpBottle.class.isAssignableFrom(projectile)) {
/* 354 */       launch = new EntityThrownExpBottle(world, getHandle());
/* 355 */     } else if ((Fish.class.isAssignableFrom(projectile)) && ((getHandle() instanceof EntityHuman))) {
/* 356 */       launch = new EntityFishingHook(world, (EntityHuman)getHandle());
/* 357 */     } else if (Fireball.class.isAssignableFrom(projectile)) {
/* 358 */       Location location = getEyeLocation();
/* 359 */       Vector direction = location.getDirection().multiply(10);
/*     */       
/* 361 */       if (SmallFireball.class.isAssignableFrom(projectile)) {
/* 362 */         launch = new EntitySmallFireball(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
/* 363 */       } else if (WitherSkull.class.isAssignableFrom(projectile)) {
/* 364 */         launch = new EntityWitherSkull(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
/*     */       } else {
/* 366 */         launch = new EntityLargeFireball(world, getHandle(), direction.getX(), direction.getY(), direction.getZ());
/*     */       }
/*     */       
/* 369 */       ((EntityFireball)launch).projectileSource = this;
/* 370 */       launch.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
/*     */     }
/*     */     
/* 373 */     Validate.notNull(launch, "Projectile not supported");
/*     */     
/* 375 */     if (velocity != null) {
/* 376 */       ((Projectile)launch.getBukkitEntity()).setVelocity(velocity);
/*     */     }
/*     */     
/* 379 */     world.addEntity(launch);
/* 380 */     return (Projectile)launch.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public EntityType getType() {
/* 384 */     return EntityType.UNKNOWN;
/*     */   }
/*     */   
/*     */   public boolean hasLineOfSight(org.bukkit.entity.Entity other) {
/* 388 */     return getHandle().hasLineOfSight(((CraftEntity)other).getHandle());
/*     */   }
/*     */   
/*     */   public boolean getRemoveWhenFarAway() {
/* 392 */     return ((getHandle() instanceof EntityInsentient)) && (!((EntityInsentient)getHandle()).persistent);
/*     */   }
/*     */   
/*     */   public void setRemoveWhenFarAway(boolean remove) {
/* 396 */     if ((getHandle() instanceof EntityInsentient)) {
/* 397 */       ((EntityInsentient)getHandle()).persistent = (!remove);
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityEquipment getEquipment() {
/* 402 */     return this.equipment;
/*     */   }
/*     */   
/*     */   public void setCanPickupItems(boolean pickup) {
/* 406 */     if ((getHandle() instanceof EntityInsentient)) {
/* 407 */       ((EntityInsentient)getHandle()).canPickUpLoot = pickup;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getCanPickupItems() {
/* 412 */     return ((getHandle() instanceof EntityInsentient)) && (((EntityInsentient)getHandle()).canPickUpLoot);
/*     */   }
/*     */   
/*     */   public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause)
/*     */   {
/* 417 */     if (getHealth() == 0.0D) {
/* 418 */       return false;
/*     */     }
/*     */     
/* 421 */     return super.teleport(location, cause);
/*     */   }
/*     */   
/*     */   public boolean isLeashed() {
/* 425 */     if (!(getHandle() instanceof EntityInsentient)) {
/* 426 */       return false;
/*     */     }
/* 428 */     return ((EntityInsentient)getHandle()).getLeashHolder() != null;
/*     */   }
/*     */   
/*     */   public org.bukkit.entity.Entity getLeashHolder() throws IllegalStateException {
/* 432 */     if (!isLeashed()) {
/* 433 */       throw new IllegalStateException("Entity not leashed");
/*     */     }
/* 435 */     return ((EntityInsentient)getHandle()).getLeashHolder().getBukkitEntity();
/*     */   }
/*     */   
/*     */   private boolean unleash() {
/* 439 */     if (!isLeashed()) {
/* 440 */       return false;
/*     */     }
/* 442 */     ((EntityInsentient)getHandle()).unleash(true, false);
/* 443 */     return true;
/*     */   }
/*     */   
/*     */   public boolean setLeashHolder(org.bukkit.entity.Entity holder) {
/* 447 */     if (((getHandle() instanceof EntityWither)) || (!(getHandle() instanceof EntityInsentient))) {
/* 448 */       return false;
/*     */     }
/*     */     
/* 451 */     if (holder == null) {
/* 452 */       return unleash();
/*     */     }
/*     */     
/* 455 */     if (holder.isDead()) {
/* 456 */       return false;
/*     */     }
/*     */     
/* 459 */     unleash();
/* 460 */     ((EntityInsentient)getHandle()).setLeashHolder(((CraftEntity)holder).getHandle(), true);
/* 461 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftLivingEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */