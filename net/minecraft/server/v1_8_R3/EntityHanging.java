/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.entity.Hanging;
/*     */ import org.bukkit.event.hanging.HangingBreakEvent;
/*     */ import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
/*     */ import org.bukkit.event.painting.PaintingBreakEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public abstract class EntityHanging extends Entity
/*     */ {
/*     */   private int c;
/*     */   public BlockPosition blockPosition;
/*     */   public EnumDirection direction;
/*     */   
/*     */   public EntityHanging(World world)
/*     */   {
/*  21 */     super(world);
/*  22 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */   
/*     */   public EntityHanging(World world, BlockPosition blockposition) {
/*  26 */     this(world);
/*  27 */     this.blockPosition = blockposition;
/*     */   }
/*     */   
/*     */   protected void h() {}
/*     */   
/*     */   public void setDirection(EnumDirection enumdirection) {
/*  33 */     Validate.notNull(enumdirection);
/*  34 */     Validate.isTrue(enumdirection.k().c());
/*  35 */     this.direction = enumdirection;
/*  36 */     this.lastYaw = (this.yaw = this.direction.b() * 90);
/*  37 */     updateBoundingBox();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AxisAlignedBB calculateBoundingBox(BlockPosition blockPosition, EnumDirection direction, int width, int height)
/*     */   {
/*  49 */     double d0 = blockPosition.getX() + 0.5D;
/*  50 */     double d1 = blockPosition.getY() + 0.5D;
/*  51 */     double d2 = blockPosition.getZ() + 0.5D;
/*     */     
/*  53 */     double d4 = width % 32 == 0 ? 0.5D : 0.0D;
/*  54 */     double d5 = height % 32 == 0 ? 0.5D : 0.0D;
/*     */     
/*  56 */     d0 -= direction.getAdjacentX() * 0.46875D;
/*  57 */     d2 -= direction.getAdjacentZ() * 0.46875D;
/*  58 */     d1 += d5;
/*  59 */     EnumDirection enumdirection = direction.f();
/*     */     
/*  61 */     d0 += d4 * enumdirection.getAdjacentX();
/*  62 */     d2 += d4 * enumdirection.getAdjacentZ();
/*  63 */     double d6 = width;
/*  64 */     double d7 = height;
/*  65 */     double d8 = width;
/*     */     
/*  67 */     if (direction.k() == EnumDirection.EnumAxis.Z) {
/*  68 */       d8 = 1.0D;
/*     */     } else {
/*  70 */       d6 = 1.0D;
/*     */     }
/*     */     
/*  73 */     d6 /= 32.0D;
/*  74 */     d7 /= 32.0D;
/*  75 */     d8 /= 32.0D;
/*  76 */     return new AxisAlignedBB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8);
/*     */   }
/*     */   
/*     */   private void updateBoundingBox() {
/*  80 */     if (this.direction != null)
/*     */     {
/*  82 */       AxisAlignedBB bb = calculateBoundingBox(this.blockPosition, this.direction, l(), m());
/*  83 */       this.locX = ((bb.a + bb.d) / 2.0D);
/*  84 */       this.locY = ((bb.b + bb.e) / 2.0D);
/*  85 */       this.locZ = ((bb.c + bb.f) / 2.0D);
/*  86 */       a(bb);
/*     */     }
/*     */   }
/*     */   
/*     */   private double a(int i)
/*     */   {
/*  92 */     return i % 32 == 0 ? 0.5D : 0.0D;
/*     */   }
/*     */   
/*     */   public void t_() {
/*  96 */     this.lastX = this.locX;
/*  97 */     this.lastY = this.locY;
/*  98 */     this.lastZ = this.locZ;
/*  99 */     if ((this.c++ == this.world.spigotConfig.hangingTickFrequency) && (!this.world.isClientSide)) {
/* 100 */       this.c = 0;
/* 101 */       if ((!this.dead) && (!survives()))
/*     */       {
/* 103 */         Material material = this.world.getType(new BlockPosition(this)).getBlock().getMaterial();
/*     */         HangingBreakEvent.RemoveCause cause;
/*     */         HangingBreakEvent.RemoveCause cause;
/* 106 */         if (!material.equals(Material.AIR))
/*     */         {
/* 108 */           cause = HangingBreakEvent.RemoveCause.OBSTRUCTION;
/*     */         } else {
/* 110 */           cause = HangingBreakEvent.RemoveCause.PHYSICS;
/*     */         }
/*     */         
/* 113 */         HangingBreakEvent event = new HangingBreakEvent((Hanging)getBukkitEntity(), cause);
/* 114 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 116 */         PaintingBreakEvent paintingEvent = null;
/* 117 */         if ((this instanceof EntityPainting))
/*     */         {
/* 119 */           paintingEvent = new PaintingBreakEvent((org.bukkit.entity.Painting)getBukkitEntity(), org.bukkit.event.painting.PaintingBreakEvent.RemoveCause.valueOf(cause.name()));
/* 120 */           paintingEvent.setCancelled(event.isCancelled());
/* 121 */           this.world.getServer().getPluginManager().callEvent(paintingEvent);
/*     */         }
/*     */         
/* 124 */         if ((this.dead) || (event.isCancelled()) || ((paintingEvent != null) && (paintingEvent.isCancelled()))) {
/* 125 */           return;
/*     */         }
/*     */         
/* 128 */         die();
/* 129 */         b(null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean survives()
/*     */   {
/* 136 */     if (!this.world.getCubes(this, getBoundingBox()).isEmpty()) {
/* 137 */       return false;
/*     */     }
/* 139 */     int i = Math.max(1, l() / 16);
/* 140 */     int j = Math.max(1, m() / 16);
/* 141 */     BlockPosition blockposition = this.blockPosition.shift(this.direction.opposite());
/* 142 */     EnumDirection enumdirection = this.direction.f();
/*     */     
/* 144 */     for (int k = 0; k < i; k++) {
/* 145 */       for (int l = 0; l < j; l++) {
/* 146 */         BlockPosition blockposition1 = blockposition.shift(enumdirection, k).up(l);
/* 147 */         Block block = this.world.getType(blockposition1).getBlock();
/*     */         
/* 149 */         if ((!block.getMaterial().isBuildable()) && (!BlockDiodeAbstract.d(block))) {
/* 150 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 155 */     List list = this.world.getEntities(this, getBoundingBox());
/* 156 */     Iterator iterator = list.iterator();
/*     */     
/*     */     Entity entity;
/*     */     do
/*     */     {
/* 161 */       if (!iterator.hasNext()) {
/* 162 */         return true;
/*     */       }
/*     */       
/* 165 */       entity = (Entity)iterator.next();
/* 166 */     } while (!(entity instanceof EntityHanging));
/*     */     
/* 168 */     return false;
/*     */   }
/*     */   
/*     */   public boolean ad()
/*     */   {
/* 173 */     return true;
/*     */   }
/*     */   
/*     */   public boolean l(Entity entity) {
/* 177 */     return (entity instanceof EntityHuman) ? damageEntity(DamageSource.playerAttack((EntityHuman)entity), 0.0F) : false;
/*     */   }
/*     */   
/*     */   public EnumDirection getDirection() {
/* 181 */     return this.direction;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 185 */     if (isInvulnerable(damagesource)) {
/* 186 */       return false;
/*     */     }
/* 188 */     if ((!this.dead) && (!this.world.isClientSide))
/*     */     {
/* 190 */       HangingBreakEvent event = new HangingBreakEvent((Hanging)getBukkitEntity(), HangingBreakEvent.RemoveCause.DEFAULT);
/* 191 */       PaintingBreakEvent paintingEvent = null;
/* 192 */       if (damagesource.getEntity() != null) {
/* 193 */         event = new org.bukkit.event.hanging.HangingBreakByEntityEvent((Hanging)getBukkitEntity(), damagesource.getEntity() == null ? null : damagesource.getEntity().getBukkitEntity());
/*     */         
/* 195 */         if ((this instanceof EntityPainting))
/*     */         {
/* 197 */           paintingEvent = new org.bukkit.event.painting.PaintingBreakByEntityEvent((org.bukkit.entity.Painting)getBukkitEntity(), damagesource.getEntity() == null ? null : damagesource.getEntity().getBukkitEntity());
/*     */         }
/* 199 */       } else if (damagesource.isExplosion()) {
/* 200 */         event = new HangingBreakEvent((Hanging)getBukkitEntity(), HangingBreakEvent.RemoveCause.EXPLOSION);
/*     */       }
/*     */       
/* 203 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 205 */       if (paintingEvent != null) {
/* 206 */         paintingEvent.setCancelled(event.isCancelled());
/* 207 */         this.world.getServer().getPluginManager().callEvent(paintingEvent);
/*     */       }
/*     */       
/* 210 */       if ((this.dead) || (event.isCancelled()) || ((paintingEvent != null) && (paintingEvent.isCancelled()))) {
/* 211 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 215 */       die();
/* 216 */       ac();
/* 217 */       b(damagesource.getEntity());
/*     */     }
/*     */     
/* 220 */     return true;
/*     */   }
/*     */   
/*     */   public void move(double d0, double d1, double d2)
/*     */   {
/* 225 */     if ((!this.world.isClientSide) && (!this.dead) && (d0 * d0 + d1 * d1 + d2 * d2 > 0.0D)) {
/* 226 */       if (this.dead) { return;
/*     */       }
/*     */       
/*     */ 
/* 230 */       HangingBreakEvent event = new HangingBreakEvent((Hanging)getBukkitEntity(), HangingBreakEvent.RemoveCause.PHYSICS);
/* 231 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 233 */       if ((this.dead) || (event.isCancelled())) {
/* 234 */         return;
/*     */       }
/*     */       
/*     */ 
/* 238 */       die();
/* 239 */       b(null);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void g(double d0, double d1, double d2) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 253 */     nbttagcompound.setByte("Facing", (byte)this.direction.b());
/* 254 */     nbttagcompound.setInt("TileX", getBlockPosition().getX());
/* 255 */     nbttagcompound.setInt("TileY", getBlockPosition().getY());
/* 256 */     nbttagcompound.setInt("TileZ", getBlockPosition().getZ());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 260 */     this.blockPosition = new BlockPosition(nbttagcompound.getInt("TileX"), nbttagcompound.getInt("TileY"), nbttagcompound.getInt("TileZ"));
/*     */     
/*     */     EnumDirection enumdirection;
/* 263 */     if (nbttagcompound.hasKeyOfType("Direction", 99)) {
/* 264 */       EnumDirection enumdirection = EnumDirection.fromType2(nbttagcompound.getByte("Direction"));
/* 265 */       this.blockPosition = this.blockPosition.shift(enumdirection); } else { EnumDirection enumdirection;
/* 266 */       if (nbttagcompound.hasKeyOfType("Facing", 99)) {
/* 267 */         enumdirection = EnumDirection.fromType2(nbttagcompound.getByte("Facing"));
/*     */       } else {
/* 269 */         enumdirection = EnumDirection.fromType2(nbttagcompound.getByte("Dir"));
/*     */       }
/*     */     }
/* 272 */     setDirection(enumdirection);
/*     */   }
/*     */   
/*     */   public abstract int l();
/*     */   
/*     */   public abstract int m();
/*     */   
/*     */   public abstract void b(Entity paramEntity);
/*     */   
/*     */   protected boolean af() {
/* 282 */     return false;
/*     */   }
/*     */   
/*     */   public void setPosition(double d0, double d1, double d2) {
/* 286 */     this.locX = d0;
/* 287 */     this.locY = d1;
/* 288 */     this.locZ = d2;
/* 289 */     BlockPosition blockposition = this.blockPosition;
/*     */     
/* 291 */     this.blockPosition = new BlockPosition(d0, d1, d2);
/* 292 */     if (!this.blockPosition.equals(blockposition)) {
/* 293 */       updateBoundingBox();
/* 294 */       this.ai = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public BlockPosition getBlockPosition()
/*     */   {
/* 300 */     return this.blockPosition;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityHanging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */