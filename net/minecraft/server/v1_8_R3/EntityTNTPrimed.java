/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*     */ 
/*     */ public class EntityTNTPrimed extends Entity
/*     */ {
/*     */   public int fuseTicks;
/*     */   private EntityLiving source;
/*   9 */   public float yield = 4.0F;
/*  10 */   public boolean isIncendiary = false;
/*     */   
/*     */   public EntityTNTPrimed(World world) {
/*  13 */     super(world);
/*  14 */     this.k = true;
/*  15 */     setSize(0.98F, 0.98F);
/*     */   }
/*     */   
/*     */   public EntityTNTPrimed(World world, double d0, double d1, double d2, EntityLiving entityliving) {
/*  19 */     this(world);
/*  20 */     setPosition(d0, d1, d2);
/*  21 */     float f = (float)(Math.random() * 3.1415927410125732D * 2.0D);
/*     */     
/*  23 */     this.motX = (-(float)Math.sin(f) * 0.02F);
/*  24 */     this.motY = 0.20000000298023224D;
/*  25 */     this.motZ = (-(float)Math.cos(f) * 0.02F);
/*  26 */     this.fuseTicks = 80;
/*  27 */     this.lastX = d0;
/*  28 */     this.lastY = d1;
/*  29 */     this.lastZ = d2;
/*  30 */     this.source = entityliving;
/*     */   }
/*     */   
/*     */   protected void h() {}
/*     */   
/*     */   protected boolean s_() {
/*  36 */     return false;
/*     */   }
/*     */   
/*     */   public boolean ad() {
/*  40 */     return !this.dead;
/*     */   }
/*     */   
/*     */   public void t_() {
/*  44 */     if (this.world.spigotConfig.currentPrimedTnt++ > this.world.spigotConfig.maxTntTicksPerTick) return;
/*  45 */     this.lastX = this.locX;
/*  46 */     this.lastY = this.locY;
/*  47 */     this.lastZ = this.locZ;
/*  48 */     this.motY -= 0.03999999910593033D;
/*  49 */     move(this.motX, this.motY, this.motZ);
/*  50 */     this.motX *= 0.9800000190734863D;
/*  51 */     this.motY *= 0.9800000190734863D;
/*  52 */     this.motZ *= 0.9800000190734863D;
/*  53 */     if (this.onGround) {
/*  54 */       this.motX *= 0.699999988079071D;
/*  55 */       this.motZ *= 0.699999988079071D;
/*  56 */       this.motY *= -0.5D;
/*     */     }
/*     */     
/*  59 */     if (this.fuseTicks-- <= 0)
/*     */     {
/*     */ 
/*  62 */       if (!this.world.isClientSide) {
/*  63 */         explode();
/*     */       }
/*  65 */       die();
/*     */     }
/*     */     else {
/*  68 */       W();
/*  69 */       this.world.addParticle(EnumParticle.SMOKE_NORMAL, this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void explode()
/*     */   {
/*  78 */     org.bukkit.craftbukkit.v1_8_R3.CraftServer server = this.world.getServer();
/*     */     
/*  80 */     ExplosionPrimeEvent event = new ExplosionPrimeEvent((org.bukkit.entity.Explosive)org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity.getEntity(server, this));
/*  81 */     server.getPluginManager().callEvent(event);
/*     */     
/*  83 */     if (!event.isCancelled()) {
/*  84 */       this.world.createExplosion(this, this.locX, this.locY + this.length / 2.0F, this.locZ, event.getRadius(), event.getFire(), true);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound)
/*     */   {
/*  90 */     nbttagcompound.setByte("Fuse", (byte)this.fuseTicks);
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {
/*  94 */     this.fuseTicks = nbttagcompound.getByte("Fuse");
/*     */   }
/*     */   
/*     */   public EntityLiving getSource() {
/*  98 */     return this.source;
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/* 102 */     return 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityTNTPrimed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */