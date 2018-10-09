/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.CreatureType;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreatureSpawnEvent
/*     */   extends EntitySpawnEvent
/*     */ {
/*     */   private final SpawnReason spawnReason;
/*     */   
/*     */   public CreatureSpawnEvent(LivingEntity spawnee, SpawnReason spawnReason)
/*     */   {
/*  17 */     super(spawnee);
/*  18 */     this.spawnReason = spawnReason;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public CreatureSpawnEvent(Entity spawnee, CreatureType type, Location loc, SpawnReason reason) {
/*  23 */     super(spawnee);
/*  24 */     this.spawnReason = reason;
/*     */   }
/*     */   
/*     */   public LivingEntity getEntity()
/*     */   {
/*  29 */     return (LivingEntity)this.entity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public CreatureType getCreatureType()
/*     */   {
/*  41 */     return CreatureType.fromEntityType(getEntityType());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SpawnReason getSpawnReason()
/*     */   {
/*  51 */     return this.spawnReason;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum SpawnReason
/*     */   {
/*  59 */     NATURAL, 
/*     */     
/*     */ 
/*     */ 
/*  63 */     JOCKEY, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  68 */     CHUNK_GEN, 
/*     */     
/*     */ 
/*     */ 
/*  72 */     SPAWNER, 
/*     */     
/*     */ 
/*     */ 
/*  76 */     EGG, 
/*     */     
/*     */ 
/*     */ 
/*  80 */     SPAWNER_EGG, 
/*     */     
/*     */ 
/*     */ 
/*  84 */     LIGHTNING, 
/*     */     
/*     */ 
/*     */ 
/*  88 */     BED, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  95 */     BUILD_SNOWMAN, 
/*     */     
/*     */ 
/*     */ 
/*  99 */     BUILD_IRONGOLEM, 
/*     */     
/*     */ 
/*     */ 
/* 103 */     BUILD_WITHER, 
/*     */     
/*     */ 
/*     */ 
/* 107 */     VILLAGE_DEFENSE, 
/*     */     
/*     */ 
/*     */ 
/* 111 */     VILLAGE_INVASION, 
/*     */     
/*     */ 
/*     */ 
/* 115 */     BREEDING, 
/*     */     
/*     */ 
/*     */ 
/* 119 */     SLIME_SPLIT, 
/*     */     
/*     */ 
/*     */ 
/* 123 */     REINFORCEMENTS, 
/*     */     
/*     */ 
/*     */ 
/* 127 */     NETHER_PORTAL, 
/*     */     
/*     */ 
/*     */ 
/* 131 */     DISPENSE_EGG, 
/*     */     
/*     */ 
/*     */ 
/* 135 */     INFECTION, 
/*     */     
/*     */ 
/*     */ 
/* 139 */     CURED, 
/*     */     
/*     */ 
/*     */ 
/* 143 */     OCELOT_BABY, 
/*     */     
/*     */ 
/*     */ 
/* 147 */     SILVERFISH_BLOCK, 
/*     */     
/*     */ 
/*     */ 
/* 151 */     MOUNT, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 156 */     CUSTOM, 
/*     */     
/*     */ 
/*     */ 
/* 160 */     DEFAULT;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\CreatureSpawnEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */