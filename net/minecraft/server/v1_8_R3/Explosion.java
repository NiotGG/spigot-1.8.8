/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.block.BlockExplodeEvent;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.spigotmc.AntiXray;
/*     */ 
/*     */ public class Explosion
/*     */ {
/*     */   private final boolean a;
/*     */   private final boolean b;
/*  23 */   private final Random c = new Random();
/*     */   private final World world;
/*     */   private final double posX;
/*     */   private final double posY;
/*     */   private final double posZ;
/*     */   public final Entity source;
/*     */   private final float size;
/*  30 */   private final List<BlockPosition> blocks = Lists.newArrayList();
/*  31 */   private final Map<EntityHuman, Vec3D> k = Maps.newHashMap();
/*  32 */   public boolean wasCanceled = false;
/*     */   
/*     */   public Explosion(World world, Entity entity, double d0, double d1, double d2, float f, boolean flag, boolean flag1) {
/*  35 */     this.world = world;
/*  36 */     this.source = entity;
/*  37 */     this.size = ((float)Math.max(f, 0.0D));
/*  38 */     this.posX = d0;
/*  39 */     this.posY = d1;
/*  40 */     this.posZ = d2;
/*  41 */     this.a = flag;
/*  42 */     this.b = flag1;
/*     */   }
/*     */   
/*     */   public void a()
/*     */   {
/*  47 */     if (this.size < 0.1F) {
/*  48 */       return;
/*     */     }
/*     */     
/*  51 */     HashSet hashset = com.google.common.collect.Sets.newHashSet();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  57 */     for (int k = 0; k < 16; k++) {
/*  58 */       for (int i = 0; i < 16; i++) {
/*  59 */         for (int j = 0; j < 16; j++) {
/*  60 */           if ((k == 0) || (k == 15) || (i == 0) || (i == 15) || (j == 0) || (j == 15)) {
/*  61 */             double d0 = k / 15.0F * 2.0F - 1.0F;
/*  62 */             double d1 = i / 15.0F * 2.0F - 1.0F;
/*  63 */             double d2 = j / 15.0F * 2.0F - 1.0F;
/*  64 */             double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */             
/*  66 */             d0 /= d3;
/*  67 */             d1 /= d3;
/*  68 */             d2 /= d3;
/*  69 */             float f = this.size * (0.7F + this.world.random.nextFloat() * 0.6F);
/*  70 */             double d4 = this.posX;
/*  71 */             double d5 = this.posY;
/*  72 */             double d6 = this.posZ;
/*  74 */             for (; 
/*  74 */                 f > 0.0F; f -= 0.22500001F) {
/*  75 */               BlockPosition blockposition = new BlockPosition(d4, d5, d6);
/*  76 */               IBlockData iblockdata = this.world.getType(blockposition);
/*     */               
/*  78 */               if (iblockdata.getBlock().getMaterial() != Material.AIR) {
/*  79 */                 float f2 = this.source != null ? this.source.a(this, this.world, blockposition, iblockdata) : iblockdata.getBlock().a(null);
/*     */                 
/*  81 */                 f -= (f2 + 0.3F) * 0.3F;
/*     */               }
/*     */               
/*  84 */               if ((f > 0.0F) && ((this.source == null) || (this.source.a(this, this.world, blockposition, iblockdata, f))) && (blockposition.getY() < 256) && (blockposition.getY() >= 0)) {
/*  85 */                 hashset.add(blockposition);
/*     */               }
/*     */               
/*  88 */               d4 += d0 * 0.30000001192092896D;
/*  89 */               d5 += d1 * 0.30000001192092896D;
/*  90 */               d6 += d2 * 0.30000001192092896D;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  97 */     this.blocks.addAll(hashset);
/*  98 */     float f3 = this.size * 2.0F;
/*     */     
/* 100 */     int i = MathHelper.floor(this.posX - f3 - 1.0D);
/* 101 */     int j = MathHelper.floor(this.posX + f3 + 1.0D);
/* 102 */     int l = MathHelper.floor(this.posY - f3 - 1.0D);
/* 103 */     int i1 = MathHelper.floor(this.posY + f3 + 1.0D);
/* 104 */     int j1 = MathHelper.floor(this.posZ - f3 - 1.0D);
/* 105 */     int k1 = MathHelper.floor(this.posZ + f3 + 1.0D);
/* 106 */     List list = this.world.getEntities(this.source, new AxisAlignedBB(i, l, j1, j, i1, k1));
/* 107 */     Vec3D vec3d = new Vec3D(this.posX, this.posY, this.posZ);
/*     */     
/* 109 */     for (int l1 = 0; l1 < list.size(); l1++) {
/* 110 */       Entity entity = (Entity)list.get(l1);
/*     */       
/* 112 */       if (!entity.aW()) {
/* 113 */         double d7 = entity.f(this.posX, this.posY, this.posZ) / f3;
/*     */         
/* 115 */         if (d7 <= 1.0D) {
/* 116 */           double d8 = entity.locX - this.posX;
/* 117 */           double d9 = entity.locY + entity.getHeadHeight() - this.posY;
/* 118 */           double d10 = entity.locZ - this.posZ;
/* 119 */           double d11 = MathHelper.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
/*     */           
/* 121 */           if (d11 != 0.0D) {
/* 122 */             d8 /= d11;
/* 123 */             d9 /= d11;
/* 124 */             d10 /= d11;
/* 125 */             double d12 = this.world.a(vec3d, entity.getBoundingBox());
/* 126 */             double d13 = (1.0D - d7) * d12;
/*     */             
/*     */ 
/* 129 */             CraftEventFactory.entityDamage = this.source;
/* 130 */             entity.forceExplosionKnockback = false;
/* 131 */             boolean wasDamaged = entity.damageEntity(DamageSource.explosion(this), (int)((d13 * d13 + d13) / 2.0D * 8.0D * f3 + 1.0D));
/* 132 */             CraftEventFactory.entityDamage = null;
/* 133 */             if ((wasDamaged) || ((entity instanceof EntityTNTPrimed)) || ((entity instanceof EntityFallingBlock)) || (entity.forceExplosionKnockback))
/*     */             {
/*     */ 
/*     */ 
/* 137 */               double d14 = EnchantmentProtection.a(entity, d13);
/*     */               
/* 139 */               entity.motX += d8 * d14;
/* 140 */               entity.motY += d9 * d14;
/* 141 */               entity.motZ += d10 * d14;
/* 142 */               if (((entity instanceof EntityHuman)) && (!((EntityHuman)entity).abilities.isInvulnerable)) {
/* 143 */                 this.k.put((EntityHuman)entity, new Vec3D(d8 * d13, d9 * d13, d10 * d13));
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 153 */     this.world.makeSound(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F);
/* 154 */     if ((this.size >= 2.0F) && (this.b)) {
/* 155 */       this.world.addParticle(EnumParticle.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     } else {
/* 157 */       this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 163 */     if (this.b)
/*     */     {
/* 165 */       org.bukkit.World bworld = this.world.getWorld();
/* 166 */       org.bukkit.entity.Entity explode = this.source == null ? null : this.source.getBukkitEntity();
/* 167 */       Location location = new Location(bworld, this.posX, this.posY, this.posZ);
/*     */       
/* 169 */       List<org.bukkit.block.Block> blockList = Lists.newArrayList();
/* 170 */       for (int i1 = this.blocks.size() - 1; i1 >= 0; i1--) {
/* 171 */         BlockPosition cpos = (BlockPosition)this.blocks.get(i1);
/* 172 */         org.bukkit.block.Block bblock = bworld.getBlockAt(cpos.getX(), cpos.getY(), cpos.getZ());
/* 173 */         if (bblock.getType() != org.bukkit.Material.AIR) {
/* 174 */           blockList.add(bblock);
/*     */         }
/*     */       }
/*     */       
/*     */       float yield;
/*     */       boolean cancelled;
/*     */       List<org.bukkit.block.Block> bukkitBlocks;
/*     */       float yield;
/* 182 */       if (explode != null) {
/* 183 */         EntityExplodeEvent event = new EntityExplodeEvent(explode, location, blockList, 0.3F);
/* 184 */         this.world.getServer().getPluginManager().callEvent(event);
/* 185 */         boolean cancelled = event.isCancelled();
/* 186 */         List<org.bukkit.block.Block> bukkitBlocks = event.blockList();
/* 187 */         yield = event.getYield();
/*     */       } else {
/* 189 */         BlockExplodeEvent event = new BlockExplodeEvent(location.getBlock(), blockList, 0.3F);
/* 190 */         this.world.getServer().getPluginManager().callEvent(event);
/* 191 */         cancelled = event.isCancelled();
/* 192 */         bukkitBlocks = event.blockList();
/* 193 */         yield = event.getYield();
/*     */       }
/*     */       
/* 196 */       this.blocks.clear();
/*     */       
/* 198 */       for (org.bukkit.block.Block bblock : bukkitBlocks) {
/* 199 */         BlockPosition coords = new BlockPosition(bblock.getX(), bblock.getY(), bblock.getZ());
/* 200 */         this.blocks.add(coords);
/*     */       }
/*     */       
/* 203 */       if (cancelled) {
/* 204 */         this.wasCanceled = true;
/* 205 */         return;
/*     */       }
/*     */       
/* 208 */       Iterator iterator = this.blocks.iterator();
/*     */       
/* 210 */       while (iterator.hasNext()) {
/* 211 */         BlockPosition blockposition = (BlockPosition)iterator.next();
/* 212 */         Block block = this.world.getType(blockposition).getBlock();
/*     */         
/* 214 */         this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition);
/* 215 */         if (flag) {
/* 216 */           double d0 = blockposition.getX() + this.world.random.nextFloat();
/* 217 */           double d1 = blockposition.getY() + this.world.random.nextFloat();
/* 218 */           double d2 = blockposition.getZ() + this.world.random.nextFloat();
/* 219 */           double d3 = d0 - this.posX;
/* 220 */           double d4 = d1 - this.posY;
/* 221 */           double d5 = d2 - this.posZ;
/* 222 */           double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*     */           
/* 224 */           d3 /= d6;
/* 225 */           d4 /= d6;
/* 226 */           d5 /= d6;
/* 227 */           double d7 = 0.5D / (d6 / this.size + 0.1D);
/*     */           
/* 229 */           d7 *= (this.world.random.nextFloat() * this.world.random.nextFloat() + 0.3F);
/* 230 */           d3 *= d7;
/* 231 */           d4 *= d7;
/* 232 */           d5 *= d7;
/* 233 */           this.world.addParticle(EnumParticle.EXPLOSION_NORMAL, (d0 + this.posX * 1.0D) / 2.0D, (d1 + this.posY * 1.0D) / 2.0D, (d2 + this.posZ * 1.0D) / 2.0D, d3, d4, d5, new int[0]);
/* 234 */           this.world.addParticle(EnumParticle.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */         }
/*     */         
/* 237 */         if (block.getMaterial() != Material.AIR) {
/* 238 */           if (block.a(this))
/*     */           {
/* 240 */             block.dropNaturally(this.world, blockposition, this.world.getType(blockposition), yield, 0);
/*     */           }
/*     */           
/* 243 */           this.world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 3);
/* 244 */           block.wasExploded(this.world, blockposition, this);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 249 */     if (this.a) {
/* 250 */       Iterator iterator = this.blocks.iterator();
/*     */       
/* 252 */       while (iterator.hasNext()) {
/* 253 */         BlockPosition blockposition = (BlockPosition)iterator.next();
/* 254 */         if ((this.world.getType(blockposition).getBlock().getMaterial() == Material.AIR) && (this.world.getType(blockposition.down()).getBlock().o()) && (this.c.nextInt(3) == 0))
/*     */         {
/* 256 */           if (!CraftEventFactory.callBlockIgniteEvent(this.world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this).isCancelled()) {
/* 257 */             this.world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public Map<EntityHuman, Vec3D> b()
/*     */   {
/* 267 */     return this.k;
/*     */   }
/*     */   
/*     */   public EntityLiving getSource()
/*     */   {
/* 272 */     return (this.source instanceof EntityFireball) ? ((EntityFireball)this.source).shooter : (this.source instanceof EntityLiving) ? (EntityLiving)this.source : (this.source instanceof EntityTNTPrimed) ? ((EntityTNTPrimed)this.source).getSource() : this.source == null ? null : null;
/*     */   }
/*     */   
/*     */   public void clearBlocks()
/*     */   {
/* 277 */     this.blocks.clear();
/*     */   }
/*     */   
/*     */   public List<BlockPosition> getBlocks() {
/* 281 */     return this.blocks;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Explosion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */