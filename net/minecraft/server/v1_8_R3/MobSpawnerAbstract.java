/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*     */ import org.bukkit.event.entity.SpawnerSpawnEvent;
/*     */ import org.spigotmc.SpigotWorldConfig;
/*     */ 
/*     */ public abstract class MobSpawnerAbstract
/*     */ {
/*  15 */   public int spawnDelay = 20;
/*  16 */   private String mobName = "Pig";
/*  17 */   private final List<a> mobs = Lists.newArrayList();
/*     */   private a spawnData;
/*     */   private double e;
/*     */   private double f;
/*  21 */   private int minSpawnDelay = 200;
/*  22 */   private int maxSpawnDelay = 800;
/*  23 */   private int spawnCount = 4;
/*     */   private Entity j;
/*  25 */   private int maxNearbyEntities = 6;
/*  26 */   private int requiredPlayerRange = 16;
/*  27 */   private int spawnRange = 4;
/*     */   
/*     */ 
/*     */   public String getMobName()
/*     */   {
/*  32 */     if (i() == null)
/*     */     {
/*  34 */       if (this.mobName == null) {
/*  35 */         this.mobName = "Pig";
/*     */       }
/*     */       
/*  38 */       if ((this.mobName != null) && (this.mobName.equals("Minecart"))) {
/*  39 */         this.mobName = "MinecartRideable";
/*     */       }
/*     */       
/*  42 */       return this.mobName;
/*     */     }
/*  44 */     return i().d;
/*     */   }
/*     */   
/*     */   public void setMobName(String s)
/*     */   {
/*  49 */     this.mobName = s;
/*     */   }
/*     */   
/*     */   private boolean g() {
/*  53 */     BlockPosition blockposition = b();
/*     */     
/*  55 */     return a().isPlayerNearby(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D, this.requiredPlayerRange);
/*     */   }
/*     */   
/*     */   public void c() {
/*  59 */     if (g()) {
/*  60 */       BlockPosition blockposition = b();
/*     */       
/*     */ 
/*  63 */       if (a().isClientSide) {
/*  64 */         double d1 = blockposition.getX() + a().random.nextFloat();
/*  65 */         double d2 = blockposition.getY() + a().random.nextFloat();
/*     */         
/*  67 */         double d0 = blockposition.getZ() + a().random.nextFloat();
/*  68 */         a().addParticle(EnumParticle.SMOKE_NORMAL, d1, d2, d0, 0.0D, 0.0D, 0.0D, new int[0]);
/*  69 */         a().addParticle(EnumParticle.FLAME, d1, d2, d0, 0.0D, 0.0D, 0.0D, new int[0]);
/*  70 */         if (this.spawnDelay > 0) {
/*  71 */           this.spawnDelay -= 1;
/*     */         }
/*     */         
/*  74 */         this.f = this.e;
/*  75 */         this.e = ((this.e + 1000.0F / (this.spawnDelay + 200.0F)) % 360.0D);
/*     */       } else {
/*  77 */         if (this.spawnDelay == -1) {
/*  78 */           h();
/*     */         }
/*     */         
/*  81 */         if (this.spawnDelay > 0) {
/*  82 */           this.spawnDelay -= 1;
/*  83 */           return;
/*     */         }
/*     */         
/*  86 */         boolean flag = false;
/*     */         
/*  88 */         for (int i = 0; i < this.spawnCount; i++) {
/*  89 */           Entity entity = EntityTypes.createEntityByName(getMobName(), a());
/*     */           
/*  91 */           if (entity == null) {
/*  92 */             return;
/*     */           }
/*     */           
/*  95 */           int j = a().a(entity.getClass(), new AxisAlignedBB(blockposition.getX(), blockposition.getY(), blockposition.getZ(), blockposition.getX() + 1, blockposition.getY() + 1, blockposition.getZ() + 1).grow(this.spawnRange, this.spawnRange, this.spawnRange)).size();
/*     */           
/*  97 */           if (j >= this.maxNearbyEntities) {
/*  98 */             h();
/*  99 */             return;
/*     */           }
/*     */           
/* 102 */           double d0 = blockposition.getX() + (a().random.nextDouble() - a().random.nextDouble()) * this.spawnRange + 0.5D;
/* 103 */           double d3 = blockposition.getY() + a().random.nextInt(3) - 1;
/* 104 */           double d4 = blockposition.getZ() + (a().random.nextDouble() - a().random.nextDouble()) * this.spawnRange + 0.5D;
/* 105 */           EntityInsentient entityinsentient = (entity instanceof EntityInsentient) ? (EntityInsentient)entity : null;
/*     */           
/* 107 */           entity.setPositionRotation(d0, d3, d4, a().random.nextFloat() * 360.0F, 0.0F);
/* 108 */           if ((entityinsentient == null) || ((entityinsentient.bR()) && (entityinsentient.canSpawn()))) {
/* 109 */             a(entity, true);
/* 110 */             a().triggerEffect(2004, blockposition, 0);
/* 111 */             if (entityinsentient != null) {
/* 112 */               entityinsentient.y();
/*     */             }
/*     */             
/* 115 */             flag = true;
/*     */           }
/*     */         }
/*     */         
/* 119 */         if (flag) {
/* 120 */           h();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private Entity a(Entity entity, boolean flag)
/*     */   {
/* 128 */     if (i() != null) {
/* 129 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 131 */       entity.d(nbttagcompound);
/* 132 */       Iterator iterator = i().c.c().iterator();
/*     */       
/* 134 */       while (iterator.hasNext()) {
/* 135 */         String s = (String)iterator.next();
/* 136 */         NBTBase nbtbase = i().c.get(s);
/*     */         
/* 138 */         nbttagcompound.set(s, nbtbase.clone());
/*     */       }
/*     */       
/* 141 */       entity.f(nbttagcompound);
/* 142 */       if ((entity.world != null) && (flag))
/*     */       {
/* 144 */         SpawnerSpawnEvent event = CraftEventFactory.callSpawnerSpawnEvent(entity, b().getX(), b().getY(), b().getZ());
/* 145 */         if (!event.isCancelled()) {
/* 146 */           entity.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
/*     */           
/* 148 */           if (entity.world.spigotConfig.nerfSpawnerMobs)
/*     */           {
/* 150 */             entity.fromMobSpawner = true;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */       NBTTagCompound nbttagcompound1;
/*     */       
/*     */ 
/* 159 */       for (Entity entity1 = entity; nbttagcompound.hasKeyOfType("Riding", 10); nbttagcompound = nbttagcompound1) {
/* 160 */         nbttagcompound1 = nbttagcompound.getCompound("Riding");
/* 161 */         Entity entity2 = EntityTypes.createEntityByName(nbttagcompound1.getString("id"), entity.world);
/*     */         
/* 163 */         if (entity2 != null) {
/* 164 */           NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/*     */           
/* 166 */           entity2.d(nbttagcompound2);
/* 167 */           Iterator iterator1 = nbttagcompound1.c().iterator();
/*     */           
/* 169 */           while (iterator1.hasNext()) {
/* 170 */             String s1 = (String)iterator1.next();
/* 171 */             NBTBase nbtbase1 = nbttagcompound1.get(s1);
/*     */             
/* 173 */             nbttagcompound2.set(s1, nbtbase1.clone());
/*     */           }
/*     */           
/* 176 */           entity2.f(nbttagcompound2);
/* 177 */           entity2.setPositionRotation(entity1.locX, entity1.locY, entity1.locZ, entity1.yaw, entity1.pitch);
/*     */           
/* 179 */           SpawnerSpawnEvent event = CraftEventFactory.callSpawnerSpawnEvent(entity2, b().getX(), b().getY(), b().getZ());
/* 180 */           if (!event.isCancelled())
/*     */           {
/*     */ 
/* 183 */             if ((entity.world != null) && (flag)) {
/* 184 */               entity.world.addEntity(entity2, CreatureSpawnEvent.SpawnReason.SPAWNER);
/*     */             }
/*     */             
/* 187 */             entity1.mount(entity2);
/*     */           }
/*     */         } else {
/* 190 */           entity1 = entity2;
/*     */         }
/* 192 */       } } else if (((entity instanceof EntityLiving)) && (entity.world != null) && (flag)) {
/* 193 */       if ((entity instanceof EntityInsentient)) {
/* 194 */         ((EntityInsentient)entity).prepare(entity.world.E(new BlockPosition(entity)), null);
/*     */       }
/*     */       
/* 197 */       SpawnerSpawnEvent event = CraftEventFactory.callSpawnerSpawnEvent(entity, b().getX(), b().getY(), b().getZ());
/* 198 */       if (!event.isCancelled()) {
/* 199 */         entity.world.addEntity(entity, CreatureSpawnEvent.SpawnReason.SPAWNER);
/*     */         
/* 201 */         if (entity.world.spigotConfig.nerfSpawnerMobs)
/*     */         {
/* 203 */           entity.fromMobSpawner = true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 210 */     return entity;
/*     */   }
/*     */   
/*     */   private void h() {
/* 214 */     if (this.maxSpawnDelay <= this.minSpawnDelay) {
/* 215 */       this.spawnDelay = this.minSpawnDelay;
/*     */     } else {
/* 217 */       int i = this.maxSpawnDelay - this.minSpawnDelay;
/*     */       
/* 219 */       this.spawnDelay = (this.minSpawnDelay + a().random.nextInt(i));
/*     */     }
/*     */     
/* 222 */     if (this.mobs.size() > 0) {
/* 223 */       a((a)WeightedRandom.a(a().random, this.mobs));
/*     */     }
/*     */     
/* 226 */     a(1);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 230 */     this.mobName = nbttagcompound.getString("EntityId");
/* 231 */     this.spawnDelay = nbttagcompound.getShort("Delay");
/* 232 */     this.mobs.clear();
/* 233 */     if (nbttagcompound.hasKeyOfType("SpawnPotentials", 9)) {
/* 234 */       NBTTagList nbttaglist = nbttagcompound.getList("SpawnPotentials", 10);
/*     */       
/* 236 */       for (int i = 0; i < nbttaglist.size(); i++) {
/* 237 */         this.mobs.add(new a(nbttaglist.get(i)));
/*     */       }
/*     */     }
/*     */     
/* 241 */     if (nbttagcompound.hasKeyOfType("SpawnData", 10)) {
/* 242 */       a(new a(nbttagcompound.getCompound("SpawnData"), this.mobName));
/*     */     } else {
/* 244 */       a(null);
/*     */     }
/*     */     
/* 247 */     if (nbttagcompound.hasKeyOfType("MinSpawnDelay", 99)) {
/* 248 */       this.minSpawnDelay = nbttagcompound.getShort("MinSpawnDelay");
/* 249 */       this.maxSpawnDelay = nbttagcompound.getShort("MaxSpawnDelay");
/* 250 */       this.spawnCount = nbttagcompound.getShort("SpawnCount");
/*     */     }
/*     */     
/* 253 */     if (nbttagcompound.hasKeyOfType("MaxNearbyEntities", 99)) {
/* 254 */       this.maxNearbyEntities = nbttagcompound.getShort("MaxNearbyEntities");
/* 255 */       this.requiredPlayerRange = nbttagcompound.getShort("RequiredPlayerRange");
/*     */     }
/*     */     
/* 258 */     if (nbttagcompound.hasKeyOfType("SpawnRange", 99)) {
/* 259 */       this.spawnRange = nbttagcompound.getShort("SpawnRange");
/*     */     }
/*     */     
/* 262 */     if (a() != null) {
/* 263 */       this.j = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 269 */     String s = getMobName();
/*     */     
/* 271 */     if (!UtilColor.b(s)) {
/* 272 */       nbttagcompound.setString("EntityId", s);
/* 273 */       nbttagcompound.setShort("Delay", (short)this.spawnDelay);
/* 274 */       nbttagcompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
/* 275 */       nbttagcompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
/* 276 */       nbttagcompound.setShort("SpawnCount", (short)this.spawnCount);
/* 277 */       nbttagcompound.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
/* 278 */       nbttagcompound.setShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
/* 279 */       nbttagcompound.setShort("SpawnRange", (short)this.spawnRange);
/* 280 */       if (i() != null) {
/* 281 */         nbttagcompound.set("SpawnData", i().c.clone());
/*     */       }
/*     */       
/* 284 */       if ((i() != null) || (this.mobs.size() > 0)) {
/* 285 */         NBTTagList nbttaglist = new NBTTagList();
/*     */         
/* 287 */         if (this.mobs.size() > 0) {
/* 288 */           Iterator iterator = this.mobs.iterator();
/*     */           
/* 290 */           while (iterator.hasNext()) {
/* 291 */             a mobspawnerabstract_a = (a)iterator.next();
/*     */             
/* 293 */             nbttaglist.add(mobspawnerabstract_a.a());
/*     */           }
/*     */         } else {
/* 296 */           nbttaglist.add(i().a());
/*     */         }
/*     */         
/* 299 */         nbttagcompound.set("SpawnPotentials", nbttaglist);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean b(int i)
/*     */   {
/* 306 */     if ((i == 1) && (a().isClientSide)) {
/* 307 */       this.spawnDelay = this.minSpawnDelay;
/* 308 */       return true;
/*     */     }
/* 310 */     return false;
/*     */   }
/*     */   
/*     */   private a i()
/*     */   {
/* 315 */     return this.spawnData;
/*     */   }
/*     */   
/*     */   public void a(a mobspawnerabstract_a) {
/* 319 */     this.spawnData = mobspawnerabstract_a;
/*     */   }
/*     */   
/*     */   public abstract void a(int paramInt);
/*     */   
/*     */   public abstract World a();
/*     */   
/*     */   public abstract BlockPosition b();
/*     */   
/*     */   public class a extends WeightedRandom.WeightedRandomChoice
/*     */   {
/*     */     private final NBTTagCompound c;
/*     */     private final String d;
/*     */     
/*     */     public a(NBTTagCompound nbttagcompound) {
/* 334 */       this(nbttagcompound.getCompound("Properties"), nbttagcompound.getString("Type"), nbttagcompound.getInt("Weight"));
/*     */     }
/*     */     
/*     */     public a(NBTTagCompound nbttagcompound, String s) {
/* 338 */       this(nbttagcompound, s, 1);
/*     */     }
/*     */     
/*     */     private a(NBTTagCompound nbttagcompound, String s, int i) {
/* 342 */       super();
/* 343 */       if (s.equals("Minecart")) {
/* 344 */         if (nbttagcompound != null) {
/* 345 */           s = EntityMinecartAbstract.EnumMinecartType.a(nbttagcompound.getInt("Type")).b();
/*     */         } else {
/* 347 */           s = "MinecartRideable";
/*     */         }
/*     */       }
/*     */       
/* 351 */       this.c = nbttagcompound;
/* 352 */       this.d = s;
/*     */     }
/*     */     
/*     */     public NBTTagCompound a() {
/* 356 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 358 */       nbttagcompound.set("Properties", this.c);
/* 359 */       nbttagcompound.setString("Type", this.d);
/* 360 */       nbttagcompound.setInt("Weight", this.a);
/* 361 */       return nbttagcompound;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MobSpawnerAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */