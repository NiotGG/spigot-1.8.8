/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.AxisAlignedBB;
/*     */ import net.minecraft.server.v1_8_R3.Chunk;
/*     */ import net.minecraft.server.v1_8_R3.Entity;
/*     */ import net.minecraft.server.v1_8_R3.EntityAmbient;
/*     */ import net.minecraft.server.v1_8_R3.EntityAnimal;
/*     */ import net.minecraft.server.v1_8_R3.EntityArrow;
/*     */ import net.minecraft.server.v1_8_R3.EntityComplexPart;
/*     */ import net.minecraft.server.v1_8_R3.EntityCreature;
/*     */ import net.minecraft.server.v1_8_R3.EntityCreeper;
/*     */ import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
/*     */ import net.minecraft.server.v1_8_R3.EntityEnderDragon;
/*     */ import net.minecraft.server.v1_8_R3.EntityFireball;
/*     */ import net.minecraft.server.v1_8_R3.EntityFireworks;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*     */ import net.minecraft.server.v1_8_R3.EntityMonster;
/*     */ import net.minecraft.server.v1_8_R3.EntityProjectile;
/*     */ import net.minecraft.server.v1_8_R3.EntitySheep;
/*     */ import net.minecraft.server.v1_8_R3.EntitySlime;
/*     */ import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
/*     */ import net.minecraft.server.v1_8_R3.EntityVillager;
/*     */ import net.minecraft.server.v1_8_R3.EntityWeather;
/*     */ import net.minecraft.server.v1_8_R3.EntityWither;
/*     */ import net.minecraft.server.v1_8_R3.MathHelper;
/*     */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*     */ import net.minecraft.server.v1_8_R3.World;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActivationRange
/*     */ {
/*  39 */   static AxisAlignedBB maxBB = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  40 */   static AxisAlignedBB miscBB = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  41 */   static AxisAlignedBB animalBB = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  42 */   static AxisAlignedBB monsterBB = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte initializeEntityActivationType(Entity entity)
/*     */   {
/*  53 */     if (((entity instanceof EntityMonster)) || ((entity instanceof EntitySlime)))
/*     */     {
/*  55 */       return 1; }
/*  56 */     if (((entity instanceof EntityCreature)) || ((entity instanceof EntityAmbient)))
/*     */     {
/*  58 */       return 2;
/*     */     }
/*     */     
/*  61 */     return 3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean initializeEntityActivationState(Entity entity, SpigotWorldConfig config)
/*     */   {
/*  74 */     if (((entity.activationType == 3) && (config.miscActivationRange == 0)) || 
/*  75 */       ((entity.activationType == 2) && (config.animalActivationRange == 0)) || 
/*  76 */       ((entity.activationType == 1) && (config.monsterActivationRange == 0)) || 
/*  77 */       ((entity instanceof EntityHuman)) || 
/*  78 */       ((entity instanceof EntityProjectile)) || 
/*  79 */       ((entity instanceof EntityEnderDragon)) || 
/*  80 */       ((entity instanceof EntityComplexPart)) || 
/*  81 */       ((entity instanceof EntityWither)) || 
/*  82 */       ((entity instanceof EntityFireball)) || 
/*  83 */       ((entity instanceof EntityWeather)) || 
/*  84 */       ((entity instanceof EntityTNTPrimed)) || 
/*  85 */       ((entity instanceof EntityEnderCrystal)) || 
/*  86 */       ((entity instanceof EntityFireworks)))
/*     */     {
/*  88 */       return true;
/*     */     }
/*     */     
/*  91 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void activateEntities(World world)
/*     */   {
/* 102 */     SpigotTimings.entityActivationCheckTimer.startTiming();
/* 103 */     int miscActivationRange = world.spigotConfig.miscActivationRange;
/* 104 */     int animalActivationRange = world.spigotConfig.animalActivationRange;
/* 105 */     int monsterActivationRange = world.spigotConfig.monsterActivationRange;
/*     */     
/* 107 */     int maxRange = Math.max(monsterActivationRange, animalActivationRange);
/* 108 */     maxRange = Math.max(maxRange, miscActivationRange);
/* 109 */     maxRange = Math.min((world.spigotConfig.viewDistance << 4) - 8, maxRange);
/*     */     int j;
/* 111 */     int i1; for (Iterator localIterator = world.players.iterator(); localIterator.hasNext(); 
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
/* 125 */         i1 <= j)
/*     */     {
/* 111 */       Entity player = (Entity)localIterator.next();
/*     */       
/*     */ 
/* 114 */       player.activatedTick = MinecraftServer.currentTick;
/* 115 */       maxBB = player.getBoundingBox().grow(maxRange, 256.0D, maxRange);
/* 116 */       miscBB = player.getBoundingBox().grow(miscActivationRange, 256.0D, miscActivationRange);
/* 117 */       animalBB = player.getBoundingBox().grow(animalActivationRange, 256.0D, animalActivationRange);
/* 118 */       monsterBB = player.getBoundingBox().grow(monsterActivationRange, 256.0D, monsterActivationRange);
/*     */       
/* 120 */       int i = MathHelper.floor(maxBB.a / 16.0D);
/* 121 */       j = MathHelper.floor(maxBB.d / 16.0D);
/* 122 */       int k = MathHelper.floor(maxBB.c / 16.0D);
/* 123 */       int l = MathHelper.floor(maxBB.f / 16.0D);
/*     */       
/* 125 */       i1 = i; continue;
/*     */       
/* 127 */       for (int j1 = k; j1 <= l; j1++)
/*     */       {
/* 129 */         if (world.getWorld().isChunkLoaded(i1, j1))
/*     */         {
/* 131 */           activateChunkEntities(world.getChunkAt(i1, j1));
/*     */         }
/*     */       }
/* 125 */       i1++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 136 */     SpigotTimings.entityActivationCheckTimer.stopTiming();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static void activateChunkEntities(Chunk chunk)
/*     */   {
/*     */     List[] arrayOfList;
/*     */     
/*     */ 
/* 146 */     int i = (arrayOfList = chunk.entitySlices).length; for (int j = 0; j < i; j++) { List<Entity> slice = arrayOfList[j];
/*     */       
/* 148 */       for (Entity entity : slice)
/*     */       {
/* 150 */         if (MinecraftServer.currentTick > entity.activatedTick)
/*     */         {
/* 152 */           if (entity.defaultActivationState)
/*     */           {
/* 154 */             entity.activatedTick = MinecraftServer.currentTick;
/*     */           }
/*     */           else {
/* 157 */             switch (entity.activationType)
/*     */             {
/*     */             case 1: 
/* 160 */               if (monsterBB.b(entity.getBoundingBox()))
/*     */               {
/* 162 */                 entity.activatedTick = MinecraftServer.currentTick;
/*     */               }
/* 164 */               break;
/*     */             case 2: 
/* 166 */               if (animalBB.b(entity.getBoundingBox()))
/*     */               {
/* 168 */                 entity.activatedTick = MinecraftServer.currentTick;
/*     */               }
/* 170 */               break;
/*     */             case 3: 
/*     */             default: 
/* 173 */               if (miscBB.b(entity.getBoundingBox()))
/*     */               {
/* 175 */                 entity.activatedTick = MinecraftServer.currentTick;
/*     */               }
/*     */               
/*     */ 
/*     */ 
/*     */               break;
/*     */             }
/*     */             
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static boolean checkEntityImmunities(Entity entity)
/*     */   {
/* 193 */     if ((entity.inWater) || (entity.fireTicks > 0))
/*     */     {
/* 195 */       return true;
/*     */     }
/* 197 */     if (!(entity instanceof EntityArrow))
/*     */     {
/* 199 */       if ((!entity.onGround) || (entity.passenger != null) || 
/* 200 */         (entity.vehicle != null))
/*     */       {
/* 202 */         return true;
/*     */       }
/* 204 */     } else if (!((EntityArrow)entity).inGround)
/*     */     {
/* 206 */       return true;
/*     */     }
/*     */     
/* 209 */     if ((entity instanceof EntityLiving))
/*     */     {
/* 211 */       EntityLiving living = (EntityLiving)entity;
/* 212 */       if ((living.hurtTicks > 0) || (living.effects.size() > 0))
/*     */       {
/* 214 */         return true;
/*     */       }
/* 216 */       if (((entity instanceof EntityCreature)) && (((EntityCreature)entity).getGoalTarget() != null))
/*     */       {
/* 218 */         return true;
/*     */       }
/* 220 */       if (((entity instanceof EntityVillager)) && (((EntityVillager)entity).cm()))
/*     */       {
/* 222 */         return true;
/*     */       }
/* 224 */       if ((entity instanceof EntityAnimal))
/*     */       {
/* 226 */         EntityAnimal animal = (EntityAnimal)entity;
/* 227 */         if ((animal.isBaby()) || (animal.isInLove()))
/*     */         {
/* 229 */           return true;
/*     */         }
/* 231 */         if (((entity instanceof EntitySheep)) && (((EntitySheep)entity).isSheared()))
/*     */         {
/* 233 */           return true;
/*     */         }
/*     */       }
/* 236 */       if (((entity instanceof EntityCreeper)) && (((EntityCreeper)entity).cn())) {
/* 237 */         return true;
/*     */       }
/*     */     }
/* 240 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean checkIfActive(Entity entity)
/*     */   {
/* 251 */     SpigotTimings.checkIfActiveTimer.startTiming();
/*     */     
/* 253 */     if ((!entity.isAddedToChunk()) || ((entity instanceof EntityFireworks))) {
/* 254 */       SpigotTimings.checkIfActiveTimer.stopTiming();
/* 255 */       return true;
/*     */     }
/*     */     
/* 258 */     boolean isActive = (entity.activatedTick >= MinecraftServer.currentTick) || (entity.defaultActivationState);
/*     */     
/*     */ 
/* 261 */     if (!isActive)
/*     */     {
/* 263 */       if ((MinecraftServer.currentTick - entity.activatedTick - 1L) % 20L == 0L)
/*     */       {
/*     */ 
/* 266 */         if (checkEntityImmunities(entity))
/*     */         {
/*     */ 
/* 269 */           entity.activatedTick = (MinecraftServer.currentTick + 20);
/*     */         }
/* 271 */         isActive = true;
/*     */       }
/*     */     }
/* 274 */     else if ((!entity.defaultActivationState) && (entity.ticksLived % 4 == 0) && (!checkEntityImmunities(entity)))
/*     */     {
/* 276 */       isActive = false;
/*     */     }
/* 278 */     int x = MathHelper.floor(entity.locX);
/* 279 */     int z = MathHelper.floor(entity.locZ);
/*     */     
/* 281 */     Chunk chunk = entity.world.getChunkIfLoaded(x >> 4, z >> 4);
/* 282 */     if ((isActive) && ((chunk == null) || (!chunk.areNeighborsLoaded(1))))
/*     */     {
/* 284 */       isActive = false;
/*     */     }
/* 286 */     SpigotTimings.checkIfActiveTimer.stopTiming();
/* 287 */     return isActive;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\ActivationRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */