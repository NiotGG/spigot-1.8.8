/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.TravelAgent;
/*      */ import org.bukkit.block.BlockFace;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*      */ import org.bukkit.entity.Hanging;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Painting;
/*      */ import org.bukkit.entity.Vehicle;
/*      */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*      */ import org.bukkit.event.entity.EntityCombustEvent;
/*      */ import org.bukkit.event.entity.EntityPortalEvent;
/*      */ import org.bukkit.event.hanging.HangingBreakByEntityEvent;
/*      */ import org.bukkit.event.painting.PaintingBreakByEntityEvent;
/*      */ import org.bukkit.event.vehicle.VehicleEnterEvent;
/*      */ import org.bukkit.event.vehicle.VehicleExitEvent;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.spigotmc.ActivationRange;
/*      */ import org.spigotmc.CustomTimingsHandler;
/*      */ import org.spigotmc.event.entity.EntityMountEvent;
/*      */ 
/*      */ public abstract class Entity implements ICommandListener
/*      */ {
/*      */   private static final int CURRENT_LEVEL = 2;
/*      */   
/*      */   static boolean isLevelAtLeast(NBTTagCompound tag, int level)
/*      */   {
/*   40 */     return (tag.hasKey("Bukkit.updateLevel")) && (tag.getInt("Bukkit.updateLevel") >= level);
/*      */   }
/*      */   
/*      */ 
/*   44 */   private static final AxisAlignedBB a = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*      */   private static int entityCount;
/*      */   private int id;
/*      */   public double j;
/*      */   public boolean k;
/*      */   public Entity passenger;
/*      */   public Entity vehicle;
/*      */   public boolean attachedToPlayer;
/*      */   public World world;
/*      */   public double lastX;
/*      */   public double lastY;
/*      */   public double lastZ;
/*      */   public double locX;
/*      */   public double locY;
/*      */   public double locZ;
/*      */   public double motX;
/*      */   public double motY;
/*      */   public double motZ;
/*      */   public float yaw;
/*      */   public float pitch;
/*      */   public float lastYaw;
/*      */   public float lastPitch;
/*      */   private AxisAlignedBB boundingBox;
/*      */   public boolean onGround;
/*      */   public boolean positionChanged;
/*      */   public boolean E;
/*      */   public boolean F;
/*      */   public boolean velocityChanged;
/*      */   protected boolean H;
/*      */   private boolean g;
/*      */   public boolean dead;
/*      */   public float width;
/*      */   public float length;
/*      */   public float L;
/*      */   public float M;
/*      */   public float N;
/*      */   public float fallDistance;
/*      */   private int h;
/*      */   public double P;
/*      */   public double Q;
/*      */   public double R;
/*      */   public float S;
/*      */   public boolean noclip;
/*      */   public float U;
/*      */   protected Random random;
/*      */   public int ticksLived;
/*      */   public int maxFireTicks;
/*      */   public int fireTicks;
/*      */   public boolean inWater;
/*      */   public int noDamageTicks;
/*      */   protected boolean justCreated;
/*      */   protected boolean fireProof;
/*      */   protected DataWatcher datawatcher;
/*      */   private double ar;
/*      */   private double as; public boolean ad;
/*   99 */   public boolean isAddedToChunk() { return this.ad; }
/*      */   
/*      */ 
/*      */   public int ae;
/*      */   public int af;
/*      */   public int ag;
/*      */   public boolean ah;
/*      */   public boolean ai;
/*      */   public int portalCooldown;
/*      */   protected boolean ak;
/*      */   protected int al;
/*      */   public int dimension;
/*      */   protected BlockPosition an;
/*      */   protected Vec3D ao;
/*      */   protected EnumDirection ap;
/*      */   private boolean invulnerable;
/*      */   protected UUID uniqueID;
/*      */   private final CommandObjectiveExecutor au;
/*      */   public boolean valid;
/*      */   public org.bukkit.projectiles.ProjectileSource projectileSource;
/*      */   public boolean forceExplosionKnockback;
/*  120 */   public CustomTimingsHandler tickTimer = SpigotTimings.getEntityTimings(this);
/*  121 */   public final byte activationType = ActivationRange.initializeEntityActivationType(this);
/*      */   public final boolean defaultActivationState;
/*  123 */   public long activatedTick = -2147483648L;
/*      */   public boolean fromMobSpawner;
/*      */   
/*      */   public void inactiveTick() {}
/*      */   
/*      */   public int getId() {
/*  129 */     return this.id;
/*      */   }
/*      */   
/*      */   public void d(int i) {
/*  133 */     this.id = i;
/*      */   }
/*      */   
/*      */   public void G() {
/*  137 */     die();
/*      */   }
/*      */   
/*      */   public Entity(World world) {
/*  141 */     this.id = (entityCount++);
/*  142 */     this.j = 1.0D;
/*  143 */     this.boundingBox = a;
/*  144 */     this.width = 0.6F;
/*  145 */     this.length = 1.8F;
/*  146 */     this.h = 1;
/*  147 */     this.random = new Random();
/*  148 */     this.maxFireTicks = 1;
/*  149 */     this.justCreated = true;
/*  150 */     this.uniqueID = MathHelper.a(this.random);
/*  151 */     this.au = new CommandObjectiveExecutor();
/*  152 */     this.world = world;
/*  153 */     setPosition(0.0D, 0.0D, 0.0D);
/*  154 */     if (world != null) {
/*  155 */       this.dimension = world.worldProvider.getDimension();
/*      */       
/*  157 */       this.defaultActivationState = ActivationRange.initializeEntityActivationState(this, world.spigotConfig);
/*      */     } else {
/*  159 */       this.defaultActivationState = false;
/*      */     }
/*      */     
/*      */ 
/*  163 */     this.datawatcher = new DataWatcher(this);
/*  164 */     this.datawatcher.a(0, Byte.valueOf((byte)0));
/*  165 */     this.datawatcher.a(1, Short.valueOf((short)300));
/*  166 */     this.datawatcher.a(3, Byte.valueOf((byte)0));
/*  167 */     this.datawatcher.a(2, "");
/*  168 */     this.datawatcher.a(4, Byte.valueOf((byte)0));
/*  169 */     h();
/*      */   }
/*      */   
/*      */   protected abstract void h();
/*      */   
/*      */   public DataWatcher getDataWatcher() {
/*  175 */     return this.datawatcher;
/*      */   }
/*      */   
/*      */   public boolean equals(Object object) {
/*  179 */     return ((Entity)object).id == this.id;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/*  183 */     return this.id;
/*      */   }
/*      */   
/*      */   public void die() {
/*  187 */     this.dead = true;
/*      */   }
/*      */   
/*      */   public void setSize(float f, float f1) {
/*  191 */     if ((f != this.width) || (f1 != this.length)) {
/*  192 */       float f2 = this.width;
/*      */       
/*  194 */       this.width = f;
/*  195 */       this.length = f1;
/*  196 */       a(new AxisAlignedBB(getBoundingBox().a, getBoundingBox().b, getBoundingBox().c, getBoundingBox().a + this.width, getBoundingBox().b + this.length, getBoundingBox().c + this.width));
/*  197 */       if ((this.width > f2) && (!this.justCreated) && (!this.world.isClientSide)) {
/*  198 */         move(f2 - this.width, 0.0D, f2 - this.width);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected void setYawPitch(float f, float f1)
/*      */   {
/*  206 */     if (Float.isNaN(f)) {
/*  207 */       f = 0.0F;
/*      */     }
/*      */     
/*  210 */     if ((f == Float.POSITIVE_INFINITY) || (f == Float.NEGATIVE_INFINITY)) {
/*  211 */       if ((this instanceof EntityPlayer)) {
/*  212 */         this.world.getServer().getLogger().warning(getName() + " was caught trying to crash the server with an invalid yaw");
/*  213 */         ((CraftPlayer)getBukkitEntity()).kickPlayer("Infinite yaw (Hacking?)");
/*      */       }
/*  215 */       f = 0.0F;
/*      */     }
/*      */     
/*      */ 
/*  219 */     if (Float.isNaN(f1)) {
/*  220 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  223 */     if ((f1 == Float.POSITIVE_INFINITY) || (f1 == Float.NEGATIVE_INFINITY)) {
/*  224 */       if ((this instanceof EntityPlayer)) {
/*  225 */         this.world.getServer().getLogger().warning(getName() + " was caught trying to crash the server with an invalid pitch");
/*  226 */         ((CraftPlayer)getBukkitEntity()).kickPlayer("Infinite pitch (Hacking?)");
/*      */       }
/*  228 */       f1 = 0.0F;
/*      */     }
/*      */     
/*      */ 
/*  232 */     this.yaw = (f % 360.0F);
/*  233 */     this.pitch = (f1 % 360.0F);
/*      */   }
/*      */   
/*      */   public void setPosition(double d0, double d1, double d2) {
/*  237 */     this.locX = d0;
/*  238 */     this.locY = d1;
/*  239 */     this.locZ = d2;
/*  240 */     float f = this.width / 2.0F;
/*  241 */     float f1 = this.length;
/*      */     
/*  243 */     a(new AxisAlignedBB(d0 - f, d1, d2 - f, d0 + f, d1 + f1, d2 + f));
/*      */   }
/*      */   
/*      */   public void t_() {
/*  247 */     K();
/*      */   }
/*      */   
/*      */   public void K() {
/*  251 */     this.world.methodProfiler.a("entityBaseTick");
/*  252 */     if ((this.vehicle != null) && (this.vehicle.dead)) {
/*  253 */       this.vehicle = null;
/*      */     }
/*      */     
/*  256 */     this.L = this.M;
/*  257 */     this.lastX = this.locX;
/*  258 */     this.lastY = this.locY;
/*  259 */     this.lastZ = this.locZ;
/*  260 */     this.lastPitch = this.pitch;
/*  261 */     this.lastYaw = this.yaw;
/*  262 */     if ((!this.world.isClientSide) && ((this.world instanceof WorldServer))) {
/*  263 */       this.world.methodProfiler.a("portal");
/*  264 */       MinecraftServer minecraftserver = ((WorldServer)this.world).getMinecraftServer();
/*  265 */       int i = L();
/*      */       
/*  267 */       if (this.ak)
/*      */       {
/*  269 */         if ((this.vehicle == null) && (this.al++ >= i)) {
/*  270 */           this.al = i;
/*  271 */           this.portalCooldown = aq();
/*      */           byte b0;
/*      */           byte b0;
/*  274 */           if (this.world.worldProvider.getDimension() == -1) {
/*  275 */             b0 = 0;
/*      */           } else {
/*  277 */             b0 = -1;
/*      */           }
/*      */           
/*  280 */           c(b0);
/*      */         }
/*      */         
/*  283 */         this.ak = false;
/*      */       }
/*      */       else {
/*  286 */         if (this.al > 0) {
/*  287 */           this.al -= 4;
/*      */         }
/*      */         
/*  290 */         if (this.al < 0) {
/*  291 */           this.al = 0;
/*      */         }
/*      */       }
/*      */       
/*  295 */       if (this.portalCooldown > 0) {
/*  296 */         this.portalCooldown -= 1;
/*      */       }
/*      */       
/*  299 */       this.world.methodProfiler.b();
/*      */     }
/*      */     
/*  302 */     Y();
/*  303 */     W();
/*  304 */     if (this.world.isClientSide) {
/*  305 */       this.fireTicks = 0;
/*  306 */     } else if (this.fireTicks > 0) {
/*  307 */       if (this.fireProof) {
/*  308 */         this.fireTicks -= 4;
/*  309 */         if (this.fireTicks < 0) {
/*  310 */           this.fireTicks = 0;
/*      */         }
/*      */       } else {
/*  313 */         if (this.fireTicks % 20 == 0) {
/*  314 */           damageEntity(DamageSource.BURN, 1.0F);
/*      */         }
/*      */         
/*  317 */         this.fireTicks -= 1;
/*      */       }
/*      */     }
/*      */     
/*  321 */     if (ab()) {
/*  322 */       burnFromLava();
/*  323 */       this.fallDistance *= 0.5F;
/*      */     }
/*      */     
/*  326 */     if (this.locY < -64.0D) {
/*  327 */       O();
/*      */     }
/*      */     
/*  330 */     if (!this.world.isClientSide) {
/*  331 */       b(0, this.fireTicks > 0);
/*      */     }
/*      */     
/*  334 */     this.justCreated = false;
/*  335 */     this.world.methodProfiler.b();
/*      */   }
/*      */   
/*      */   public int L() {
/*  339 */     return 0;
/*      */   }
/*      */   
/*      */   protected void burnFromLava() {
/*  343 */     if (!this.fireProof) {
/*  344 */       damageEntity(DamageSource.LAVA, 4.0F);
/*      */       
/*      */ 
/*  347 */       if ((this instanceof EntityLiving)) {
/*  348 */         if (this.fireTicks <= 0)
/*      */         {
/*      */ 
/*  351 */           org.bukkit.block.Block damager = null;
/*  352 */           org.bukkit.entity.Entity damagee = getBukkitEntity();
/*  353 */           EntityCombustEvent combustEvent = new org.bukkit.event.entity.EntityCombustByBlockEvent(damager, damagee, 15);
/*  354 */           this.world.getServer().getPluginManager().callEvent(combustEvent);
/*      */           
/*  356 */           if (!combustEvent.isCancelled()) {
/*  357 */             setOnFire(combustEvent.getDuration());
/*      */           }
/*      */         }
/*      */         else {
/*  361 */           setOnFire(15);
/*      */         }
/*  363 */         return;
/*      */       }
/*      */       
/*  366 */       setOnFire(15);
/*      */     }
/*      */   }
/*      */   
/*      */   public void setOnFire(int i) {
/*  371 */     int j = i * 20;
/*      */     
/*  373 */     j = EnchantmentProtection.a(this, j);
/*  374 */     if (this.fireTicks < j) {
/*  375 */       this.fireTicks = j;
/*      */     }
/*      */   }
/*      */   
/*      */   public void extinguish()
/*      */   {
/*  381 */     this.fireTicks = 0;
/*      */   }
/*      */   
/*      */   protected void O() {
/*  385 */     die();
/*      */   }
/*      */   
/*      */   public boolean c(double d0, double d1, double d2) {
/*  389 */     AxisAlignedBB axisalignedbb = getBoundingBox().c(d0, d1, d2);
/*      */     
/*  391 */     return b(axisalignedbb);
/*      */   }
/*      */   
/*      */   private boolean b(AxisAlignedBB axisalignedbb) {
/*  395 */     return (this.world.getCubes(this, axisalignedbb).isEmpty()) && (!this.world.containsLiquid(axisalignedbb));
/*      */   }
/*      */   
/*      */   public void move(double d0, double d1, double d2) {
/*  399 */     SpigotTimings.entityMoveTimer.startTiming();
/*  400 */     if (this.noclip) {
/*  401 */       a(getBoundingBox().c(d0, d1, d2));
/*  402 */       recalcPosition();
/*      */     }
/*      */     else
/*      */     {
/*      */       try {
/*  407 */         checkBlockCollisions();
/*      */       } catch (Throwable throwable) {
/*  409 */         CrashReport crashreport = CrashReport.a(throwable, "Checking entity block collision");
/*  410 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being checked for collision");
/*      */         
/*  412 */         appendEntityCrashDetails(crashreportsystemdetails);
/*  413 */         throw new ReportedException(crashreport);
/*      */       }
/*      */       
/*  416 */       if ((d0 == 0.0D) && (d1 == 0.0D) && (d2 == 0.0D) && (this.vehicle == null) && (this.passenger == null)) {
/*  417 */         return;
/*      */       }
/*      */       
/*  420 */       this.world.methodProfiler.a("move");
/*  421 */       double d3 = this.locX;
/*  422 */       double d4 = this.locY;
/*  423 */       double d5 = this.locZ;
/*      */       
/*  425 */       if (this.H) {
/*  426 */         this.H = false;
/*  427 */         d0 *= 0.25D;
/*  428 */         d1 *= 0.05000000074505806D;
/*  429 */         d2 *= 0.25D;
/*  430 */         this.motX = 0.0D;
/*  431 */         this.motY = 0.0D;
/*  432 */         this.motZ = 0.0D;
/*      */       }
/*      */       
/*  435 */       double d6 = d0;
/*  436 */       double d7 = d1;
/*  437 */       double d8 = d2;
/*  438 */       boolean flag = (this.onGround) && (isSneaking()) && ((this instanceof EntityHuman));
/*      */       
/*  440 */       if (flag)
/*      */       {
/*      */ 
/*  443 */         double d9 = 0.05D;
/*  444 */         for (;;) { if ((d0 < d9) && (d0 >= -d9)) {
/*  445 */             d0 = 0.0D;
/*  446 */           } else if (d0 > 0.0D) {
/*  447 */             d0 -= d9;
/*      */           } else {
/*  449 */             d0 += d9;
/*      */           }
/*  443 */           d6 = d0; if (d0 != 0.0D) { if (!this.world.getCubes(this, getBoundingBox().c(d0, -1.0D, 0.0D)).isEmpty()) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */         do
/*      */         {
/*  454 */           if ((d2 < d9) && (d2 >= -d9)) {
/*  455 */             d2 = 0.0D;
/*  456 */           } else if (d2 > 0.0D) {
/*  457 */             d2 -= d9;
/*      */           } else {
/*  459 */             d2 += d9;
/*      */           }
/*  453 */           d8 = d2; if (d2 == 0.0D) break; } while (this.world.getCubes(this, getBoundingBox().c(0.0D, -1.0D, d2)).isEmpty());
/*  463 */         for (; 
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  463 */             (d0 != 0.0D) && (d2 != 0.0D) && (this.world.getCubes(this, getBoundingBox().c(d0, -1.0D, d2)).isEmpty()); d8 = d2) {
/*  464 */           if ((d0 < d9) && (d0 >= -d9)) {
/*  465 */             d0 = 0.0D;
/*  466 */           } else if (d0 > 0.0D) {
/*  467 */             d0 -= d9;
/*      */           } else {
/*  469 */             d0 += d9;
/*      */           }
/*      */           
/*  472 */           d6 = d0;
/*  473 */           if ((d2 < d9) && (d2 >= -d9)) {
/*  474 */             d2 = 0.0D;
/*  475 */           } else if (d2 > 0.0D) {
/*  476 */             d2 -= d9;
/*      */           } else {
/*  478 */             d2 += d9;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  483 */       List list = this.world.getCubes(this, getBoundingBox().a(d0, d1, d2));
/*  484 */       AxisAlignedBB axisalignedbb = getBoundingBox();
/*      */       
/*      */       AxisAlignedBB axisalignedbb1;
/*      */       
/*  488 */       for (Iterator iterator = list.iterator(); iterator.hasNext(); d1 = axisalignedbb1.b(getBoundingBox(), d1)) {
/*  489 */         axisalignedbb1 = (AxisAlignedBB)iterator.next();
/*      */       }
/*      */       
/*  492 */       a(getBoundingBox().c(0.0D, d1, 0.0D));
/*  493 */       boolean flag1 = (this.onGround) || ((d7 != d1) && (d7 < 0.0D));
/*      */       
/*      */ 
/*      */       AxisAlignedBB axisalignedbb2;
/*      */       
/*  498 */       for (Iterator iterator1 = list.iterator(); iterator1.hasNext(); d0 = axisalignedbb2.a(getBoundingBox(), d0)) {
/*  499 */         axisalignedbb2 = (AxisAlignedBB)iterator1.next();
/*      */       }
/*      */       
/*  502 */       a(getBoundingBox().c(d0, 0.0D, 0.0D));
/*      */       AxisAlignedBB axisalignedbb2;
/*  504 */       for (iterator1 = list.iterator(); iterator1.hasNext(); d2 = axisalignedbb2.c(getBoundingBox(), d2)) {
/*  505 */         axisalignedbb2 = (AxisAlignedBB)iterator1.next();
/*      */       }
/*      */       
/*  508 */       a(getBoundingBox().c(0.0D, 0.0D, d2));
/*  509 */       if ((this.S > 0.0F) && (flag1) && ((d6 != d0) || (d8 != d2))) {
/*  510 */         double d10 = d0;
/*  511 */         double d11 = d1;
/*  512 */         double d12 = d2;
/*  513 */         AxisAlignedBB axisalignedbb3 = getBoundingBox();
/*      */         
/*  515 */         a(axisalignedbb);
/*  516 */         d1 = this.S;
/*  517 */         List list1 = this.world.getCubes(this, getBoundingBox().a(d6, d1, d8));
/*  518 */         AxisAlignedBB axisalignedbb4 = getBoundingBox();
/*  519 */         AxisAlignedBB axisalignedbb5 = axisalignedbb4.a(d6, 0.0D, d8);
/*  520 */         double d13 = d1;
/*      */         
/*      */         AxisAlignedBB axisalignedbb6;
/*      */         
/*  524 */         for (Iterator iterator2 = list1.iterator(); iterator2.hasNext(); d13 = axisalignedbb6.b(axisalignedbb5, d13)) {
/*  525 */           axisalignedbb6 = (AxisAlignedBB)iterator2.next();
/*      */         }
/*      */         
/*  528 */         axisalignedbb4 = axisalignedbb4.c(0.0D, d13, 0.0D);
/*  529 */         double d14 = d6;
/*      */         
/*      */         AxisAlignedBB axisalignedbb7;
/*      */         
/*  533 */         for (Iterator iterator3 = list1.iterator(); iterator3.hasNext(); d14 = axisalignedbb7.a(axisalignedbb4, d14)) {
/*  534 */           axisalignedbb7 = (AxisAlignedBB)iterator3.next();
/*      */         }
/*      */         
/*  537 */         axisalignedbb4 = axisalignedbb4.c(d14, 0.0D, 0.0D);
/*  538 */         double d15 = d8;
/*      */         
/*      */         AxisAlignedBB axisalignedbb8;
/*      */         
/*  542 */         for (Iterator iterator4 = list1.iterator(); iterator4.hasNext(); d15 = axisalignedbb8.c(axisalignedbb4, d15)) {
/*  543 */           axisalignedbb8 = (AxisAlignedBB)iterator4.next();
/*      */         }
/*      */         
/*  546 */         axisalignedbb4 = axisalignedbb4.c(0.0D, 0.0D, d15);
/*  547 */         AxisAlignedBB axisalignedbb9 = getBoundingBox();
/*  548 */         double d16 = d1;
/*      */         
/*      */         AxisAlignedBB axisalignedbb10;
/*      */         
/*  552 */         for (Iterator iterator5 = list1.iterator(); iterator5.hasNext(); d16 = axisalignedbb10.b(axisalignedbb9, d16)) {
/*  553 */           axisalignedbb10 = (AxisAlignedBB)iterator5.next();
/*      */         }
/*      */         
/*  556 */         axisalignedbb9 = axisalignedbb9.c(0.0D, d16, 0.0D);
/*  557 */         double d17 = d6;
/*      */         
/*      */         AxisAlignedBB axisalignedbb11;
/*      */         
/*  561 */         for (Iterator iterator6 = list1.iterator(); iterator6.hasNext(); d17 = axisalignedbb11.a(axisalignedbb9, d17)) {
/*  562 */           axisalignedbb11 = (AxisAlignedBB)iterator6.next();
/*      */         }
/*      */         
/*  565 */         axisalignedbb9 = axisalignedbb9.c(d17, 0.0D, 0.0D);
/*  566 */         double d18 = d8;
/*      */         
/*      */         AxisAlignedBB axisalignedbb12;
/*      */         
/*  570 */         for (Iterator iterator7 = list1.iterator(); iterator7.hasNext(); d18 = axisalignedbb12.c(axisalignedbb9, d18)) {
/*  571 */           axisalignedbb12 = (AxisAlignedBB)iterator7.next();
/*      */         }
/*      */         
/*  574 */         axisalignedbb9 = axisalignedbb9.c(0.0D, 0.0D, d18);
/*  575 */         double d19 = d14 * d14 + d15 * d15;
/*  576 */         double d20 = d17 * d17 + d18 * d18;
/*      */         
/*  578 */         if (d19 > d20) {
/*  579 */           d0 = d14;
/*  580 */           d2 = d15;
/*  581 */           d1 = -d13;
/*  582 */           a(axisalignedbb4);
/*      */         } else {
/*  584 */           d0 = d17;
/*  585 */           d2 = d18;
/*  586 */           d1 = -d16;
/*  587 */           a(axisalignedbb9);
/*      */         }
/*      */         
/*      */         AxisAlignedBB axisalignedbb13;
/*      */         
/*  592 */         for (Iterator iterator8 = list1.iterator(); iterator8.hasNext(); d1 = axisalignedbb13.b(getBoundingBox(), d1)) {
/*  593 */           axisalignedbb13 = (AxisAlignedBB)iterator8.next();
/*      */         }
/*      */         
/*  596 */         a(getBoundingBox().c(0.0D, d1, 0.0D));
/*  597 */         if (d10 * d10 + d12 * d12 >= d0 * d0 + d2 * d2) {
/*  598 */           d0 = d10;
/*  599 */           d1 = d11;
/*  600 */           d2 = d12;
/*  601 */           a(axisalignedbb3);
/*      */         }
/*      */       }
/*      */       
/*  605 */       this.world.methodProfiler.b();
/*  606 */       this.world.methodProfiler.a("rest");
/*  607 */       recalcPosition();
/*  608 */       this.positionChanged = ((d6 != d0) || (d8 != d2));
/*  609 */       this.E = (d7 != d1);
/*  610 */       this.onGround = ((this.E) && (d7 < 0.0D));
/*  611 */       this.F = ((this.positionChanged) || (this.E));
/*  612 */       int i = MathHelper.floor(this.locX);
/*  613 */       int j = MathHelper.floor(this.locY - 0.20000000298023224D);
/*  614 */       int k = MathHelper.floor(this.locZ);
/*  615 */       BlockPosition blockposition = new BlockPosition(i, j, k);
/*  616 */       Block block = this.world.getType(blockposition).getBlock();
/*      */       
/*  618 */       if (block.getMaterial() == Material.AIR) {
/*  619 */         Block block1 = this.world.getType(blockposition.down()).getBlock();
/*      */         
/*  621 */         if (((block1 instanceof BlockFence)) || ((block1 instanceof BlockCobbleWall)) || ((block1 instanceof BlockFenceGate))) {
/*  622 */           block = block1;
/*  623 */           blockposition = blockposition.down();
/*      */         }
/*      */       }
/*      */       
/*  627 */       a(d1, this.onGround, block, blockposition);
/*  628 */       if (d6 != d0) {
/*  629 */         this.motX = 0.0D;
/*      */       }
/*      */       
/*  632 */       if (d8 != d2) {
/*  633 */         this.motZ = 0.0D;
/*      */       }
/*      */       
/*  636 */       if (d7 != d1) {
/*  637 */         block.a(this.world, this);
/*      */       }
/*      */       
/*      */ 
/*  641 */       if ((this.positionChanged) && ((getBukkitEntity() instanceof Vehicle))) {
/*  642 */         Vehicle vehicle = (Vehicle)getBukkitEntity();
/*  643 */         org.bukkit.block.Block bl = this.world.getWorld().getBlockAt(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
/*      */         
/*  645 */         if (d6 > d0) {
/*  646 */           bl = bl.getRelative(BlockFace.EAST);
/*  647 */         } else if (d6 < d0) {
/*  648 */           bl = bl.getRelative(BlockFace.WEST);
/*  649 */         } else if (d8 > d2) {
/*  650 */           bl = bl.getRelative(BlockFace.SOUTH);
/*  651 */         } else if (d8 < d2) {
/*  652 */           bl = bl.getRelative(BlockFace.NORTH);
/*      */         }
/*      */         
/*  655 */         org.bukkit.event.vehicle.VehicleBlockCollisionEvent event = new org.bukkit.event.vehicle.VehicleBlockCollisionEvent(vehicle, bl);
/*  656 */         this.world.getServer().getPluginManager().callEvent(event);
/*      */       }
/*      */       
/*      */ 
/*  660 */       if ((s_()) && (!flag) && (this.vehicle == null)) {
/*  661 */         double d21 = this.locX - d3;
/*  662 */         double d22 = this.locY - d4;
/*  663 */         double d23 = this.locZ - d5;
/*      */         
/*  665 */         if (block != Blocks.LADDER) {
/*  666 */           d22 = 0.0D;
/*      */         }
/*      */         
/*  669 */         if (block != null) {}
/*      */         
/*      */ 
/*      */ 
/*  673 */         this.M = ((float)(this.M + MathHelper.sqrt(d21 * d21 + d23 * d23) * 0.6D));
/*  674 */         this.N = ((float)(this.N + MathHelper.sqrt(d21 * d21 + d22 * d22 + d23 * d23) * 0.6D));
/*  675 */         if ((this.N > this.h) && (block.getMaterial() != Material.AIR)) {
/*  676 */           this.h = ((int)this.N + 1);
/*  677 */           if (V()) {
/*  678 */             float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.35F;
/*      */             
/*  680 */             if (f > 1.0F) {
/*  681 */               f = 1.0F;
/*      */             }
/*      */             
/*  684 */             makeSound(P(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*      */           }
/*      */           
/*  687 */           a(blockposition, block);
/*  688 */           block.a(this.world, blockposition, this);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  706 */       boolean flag2 = U();
/*      */       
/*  708 */       if (this.world.e(getBoundingBox().shrink(0.001D, 0.001D, 0.001D))) {
/*  709 */         burn(1.0F);
/*  710 */         if (!flag2) {
/*  711 */           this.fireTicks += 1;
/*      */           
/*  713 */           if (this.fireTicks <= 0) {
/*  714 */             EntityCombustEvent event = new EntityCombustEvent(getBukkitEntity(), 8);
/*  715 */             this.world.getServer().getPluginManager().callEvent(event);
/*      */             
/*  717 */             if (!event.isCancelled()) {
/*  718 */               setOnFire(event.getDuration());
/*      */             }
/*      */           }
/*      */           else {
/*  722 */             setOnFire(8);
/*      */           }
/*      */         }
/*  725 */       } else if (this.fireTicks <= 0) {
/*  726 */         this.fireTicks = (-this.maxFireTicks);
/*      */       }
/*      */       
/*  729 */       if ((flag2) && (this.fireTicks > 0)) {
/*  730 */         makeSound("random.fizz", 0.7F, 1.6F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*  731 */         this.fireTicks = (-this.maxFireTicks);
/*      */       }
/*      */       
/*  734 */       this.world.methodProfiler.b();
/*      */     }
/*  736 */     SpigotTimings.entityMoveTimer.stopTiming();
/*      */   }
/*      */   
/*      */   private void recalcPosition() {
/*  740 */     this.locX = ((getBoundingBox().a + getBoundingBox().d) / 2.0D);
/*  741 */     this.locY = getBoundingBox().b;
/*  742 */     this.locZ = ((getBoundingBox().c + getBoundingBox().f) / 2.0D);
/*      */   }
/*      */   
/*      */   protected String P() {
/*  746 */     return "game.neutral.swim";
/*      */   }
/*      */   
/*      */   protected void checkBlockCollisions() {
/*  750 */     BlockPosition blockposition = new BlockPosition(getBoundingBox().a + 0.001D, getBoundingBox().b + 0.001D, getBoundingBox().c + 0.001D);
/*  751 */     BlockPosition blockposition1 = new BlockPosition(getBoundingBox().d - 0.001D, getBoundingBox().e - 0.001D, getBoundingBox().f - 0.001D);
/*      */     
/*  753 */     if (this.world.areChunksLoadedBetween(blockposition, blockposition1)) {
/*  754 */       for (int i = blockposition.getX(); i <= blockposition1.getX(); i++) {
/*  755 */         for (int j = blockposition.getY(); j <= blockposition1.getY(); j++) {
/*  756 */           for (int k = blockposition.getZ(); k <= blockposition1.getZ(); k++) {
/*  757 */             BlockPosition blockposition2 = new BlockPosition(i, j, k);
/*  758 */             IBlockData iblockdata = this.world.getType(blockposition2);
/*      */             try
/*      */             {
/*  761 */               iblockdata.getBlock().a(this.world, blockposition2, iblockdata, this);
/*      */             } catch (Throwable throwable) {
/*  763 */               CrashReport crashreport = CrashReport.a(throwable, "Colliding entity with block");
/*  764 */               CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being collided with");
/*      */               
/*  766 */               CrashReportSystemDetails.a(crashreportsystemdetails, blockposition2, iblockdata);
/*  767 */               throw new ReportedException(crashreport);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a(BlockPosition blockposition, Block block)
/*      */   {
/*  777 */     Block.StepSound block_stepsound = block.stepSound;
/*      */     
/*  779 */     if (this.world.getType(blockposition.up()).getBlock() == Blocks.SNOW_LAYER) {
/*  780 */       block_stepsound = Blocks.SNOW_LAYER.stepSound;
/*  781 */       makeSound(block_stepsound.getStepSound(), block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
/*  782 */     } else if (!block.getMaterial().isLiquid()) {
/*  783 */       makeSound(block_stepsound.getStepSound(), block_stepsound.getVolume1() * 0.15F, block_stepsound.getVolume2());
/*      */     }
/*      */   }
/*      */   
/*      */   public void makeSound(String s, float f, float f1)
/*      */   {
/*  789 */     if (!R()) {
/*  790 */       this.world.makeSound(this, s, f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean R()
/*      */   {
/*  796 */     return this.datawatcher.getByte(4) == 1;
/*      */   }
/*      */   
/*      */   public void b(boolean flag) {
/*  800 */     this.datawatcher.watch(4, Byte.valueOf((byte)(flag ? 1 : 0)));
/*      */   }
/*      */   
/*      */   protected boolean s_() {
/*  804 */     return true;
/*      */   }
/*      */   
/*      */   protected void a(double d0, boolean flag, Block block, BlockPosition blockposition) {
/*  808 */     if (flag) {
/*  809 */       if (this.fallDistance > 0.0F) {
/*  810 */         if (block != null) {
/*  811 */           block.fallOn(this.world, blockposition, this, this.fallDistance);
/*      */         } else {
/*  813 */           e(this.fallDistance, 1.0F);
/*      */         }
/*      */         
/*  816 */         this.fallDistance = 0.0F;
/*      */       }
/*  818 */     } else if (d0 < 0.0D) {
/*  819 */       this.fallDistance = ((float)(this.fallDistance - d0));
/*      */     }
/*      */   }
/*      */   
/*      */   public AxisAlignedBB S()
/*      */   {
/*  825 */     return null;
/*      */   }
/*      */   
/*      */   protected void burn(float i) {
/*  829 */     if (!this.fireProof) {
/*  830 */       damageEntity(DamageSource.FIRE, i);
/*      */     }
/*      */   }
/*      */   
/*      */   public final boolean isFireProof()
/*      */   {
/*  836 */     return this.fireProof;
/*      */   }
/*      */   
/*      */   public void e(float f, float f1) {
/*  840 */     if (this.passenger != null) {
/*  841 */       this.passenger.e(f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean U()
/*      */   {
/*  847 */     return (this.inWater) || (this.world.isRainingAt(new BlockPosition(this.locX, this.locY, this.locZ))) || (this.world.isRainingAt(new BlockPosition(this.locX, this.locY + this.length, this.locZ)));
/*      */   }
/*      */   
/*      */   public boolean V() {
/*  851 */     return this.inWater;
/*      */   }
/*      */   
/*      */   public boolean W() {
/*  855 */     if (this.world.a(getBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D, 0.001D, 0.001D), Material.WATER, this)) {
/*  856 */       if ((!this.inWater) && (!this.justCreated)) {
/*  857 */         X();
/*      */       }
/*      */       
/*  860 */       this.fallDistance = 0.0F;
/*  861 */       this.inWater = true;
/*  862 */       this.fireTicks = 0;
/*      */     } else {
/*  864 */       this.inWater = false;
/*      */     }
/*      */     
/*  867 */     return this.inWater;
/*      */   }
/*      */   
/*      */   protected void X() {
/*  871 */     float f = MathHelper.sqrt(this.motX * this.motX * 0.20000000298023224D + this.motY * this.motY + this.motZ * this.motZ * 0.20000000298023224D) * 0.2F;
/*      */     
/*  873 */     if (f > 1.0F) {
/*  874 */       f = 1.0F;
/*      */     }
/*      */     
/*  877 */     makeSound(aa(), f, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/*  878 */     float f1 = MathHelper.floor(getBoundingBox().b);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  884 */     for (int i = 0; i < 1.0F + this.width * 20.0F; i++) {
/*  885 */       float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
/*  886 */       float f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
/*  887 */       this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX + f2, f1 + 1.0F, this.locZ + f3, this.motX, this.motY - this.random.nextFloat() * 0.2F, this.motZ, new int[0]);
/*      */     }
/*      */     
/*  890 */     for (i = 0; i < 1.0F + this.width * 20.0F; i++) {
/*  891 */       float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
/*  892 */       float f3 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width;
/*  893 */       this.world.addParticle(EnumParticle.WATER_SPLASH, this.locX + f2, f1 + 1.0F, this.locZ + f3, this.motX, this.motY, this.motZ, new int[0]);
/*      */     }
/*      */   }
/*      */   
/*      */   public void Y()
/*      */   {
/*  899 */     if ((isSprinting()) && (!V())) {
/*  900 */       Z();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void Z()
/*      */   {
/*  906 */     int i = MathHelper.floor(this.locX);
/*  907 */     int j = MathHelper.floor(this.locY - 0.20000000298023224D);
/*  908 */     int k = MathHelper.floor(this.locZ);
/*  909 */     BlockPosition blockposition = new BlockPosition(i, j, k);
/*  910 */     IBlockData iblockdata = this.world.getType(blockposition);
/*  911 */     Block block = iblockdata.getBlock();
/*      */     
/*  913 */     if (block.b() != -1) {
/*  914 */       this.world.addParticle(EnumParticle.BLOCK_CRACK, this.locX + (this.random.nextFloat() - 0.5D) * this.width, getBoundingBox().b + 0.1D, this.locZ + (this.random.nextFloat() - 0.5D) * this.width, -this.motX * 4.0D, 1.5D, -this.motZ * 4.0D, new int[] { Block.getCombinedId(iblockdata) });
/*      */     }
/*      */   }
/*      */   
/*      */   protected String aa()
/*      */   {
/*  920 */     return "game.neutral.swim.splash";
/*      */   }
/*      */   
/*      */   public boolean a(Material material) {
/*  924 */     double d0 = this.locY + getHeadHeight();
/*  925 */     BlockPosition blockposition = new BlockPosition(this.locX, d0, this.locZ);
/*  926 */     IBlockData iblockdata = this.world.getType(blockposition);
/*  927 */     Block block = iblockdata.getBlock();
/*      */     
/*  929 */     if (block.getMaterial() == material) {
/*  930 */       float f = BlockFluids.b(iblockdata.getBlock().toLegacyData(iblockdata)) - 0.11111111F;
/*  931 */       float f1 = blockposition.getY() + 1 - f;
/*  932 */       boolean flag = d0 < f1;
/*      */       
/*  934 */       return (!flag) && ((this instanceof EntityHuman)) ? false : flag;
/*      */     }
/*  936 */     return false;
/*      */   }
/*      */   
/*      */   public boolean ab()
/*      */   {
/*  941 */     return this.world.a(getBoundingBox().grow(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.LAVA);
/*      */   }
/*      */   
/*      */   public void a(float f, float f1, float f2) {
/*  945 */     float f3 = f * f + f1 * f1;
/*      */     
/*  947 */     if (f3 >= 1.0E-4F) {
/*  948 */       f3 = MathHelper.c(f3);
/*  949 */       if (f3 < 1.0F) {
/*  950 */         f3 = 1.0F;
/*      */       }
/*      */       
/*  953 */       f3 = f2 / f3;
/*  954 */       f *= f3;
/*  955 */       f1 *= f3;
/*  956 */       float f4 = MathHelper.sin(this.yaw * 3.1415927F / 180.0F);
/*  957 */       float f5 = MathHelper.cos(this.yaw * 3.1415927F / 180.0F);
/*      */       
/*  959 */       this.motX += f * f5 - f1 * f4;
/*  960 */       this.motZ += f1 * f5 + f * f4;
/*      */     }
/*      */   }
/*      */   
/*      */   public float c(float f) {
/*  965 */     BlockPosition blockposition = new BlockPosition(this.locX, this.locY + getHeadHeight(), this.locZ);
/*      */     
/*  967 */     return this.world.isLoaded(blockposition) ? this.world.o(blockposition) : 0.0F;
/*      */   }
/*      */   
/*      */   public void spawnIn(World world)
/*      */   {
/*  972 */     if (world == null) {
/*  973 */       die();
/*  974 */       this.world = ((CraftWorld)Bukkit.getServer().getWorlds().get(0)).getHandle();
/*  975 */       return;
/*      */     }
/*      */     
/*  978 */     this.world = world;
/*      */   }
/*      */   
/*      */   public void setLocation(double d0, double d1, double d2, float f, float f1) {
/*  982 */     this.lastX = (this.locX = d0);
/*  983 */     this.lastY = (this.locY = d1);
/*  984 */     this.lastZ = (this.locZ = d2);
/*  985 */     this.lastYaw = (this.yaw = f);
/*  986 */     this.lastPitch = (this.pitch = f1);
/*  987 */     double d3 = this.lastYaw - f;
/*      */     
/*  989 */     if (d3 < -180.0D) {
/*  990 */       this.lastYaw += 360.0F;
/*      */     }
/*      */     
/*  993 */     if (d3 >= 180.0D) {
/*  994 */       this.lastYaw -= 360.0F;
/*      */     }
/*      */     
/*  997 */     setPosition(this.locX, this.locY, this.locZ);
/*  998 */     setYawPitch(f, f1);
/*      */   }
/*      */   
/*      */   public void setPositionRotation(BlockPosition blockposition, float f, float f1) {
/* 1002 */     setPositionRotation(blockposition.getX() + 0.5D, blockposition.getY(), blockposition.getZ() + 0.5D, f, f1);
/*      */   }
/*      */   
/*      */   public void setPositionRotation(double d0, double d1, double d2, float f, float f1) {
/* 1006 */     this.P = (this.lastX = this.locX = d0);
/* 1007 */     this.Q = (this.lastY = this.locY = d1);
/* 1008 */     this.R = (this.lastZ = this.locZ = d2);
/* 1009 */     this.yaw = f;
/* 1010 */     this.pitch = f1;
/* 1011 */     setPosition(this.locX, this.locY, this.locZ);
/*      */   }
/*      */   
/*      */   public float g(Entity entity) {
/* 1015 */     float f = (float)(this.locX - entity.locX);
/* 1016 */     float f1 = (float)(this.locY - entity.locY);
/* 1017 */     float f2 = (float)(this.locZ - entity.locZ);
/*      */     
/* 1019 */     return MathHelper.c(f * f + f1 * f1 + f2 * f2);
/*      */   }
/*      */   
/*      */   public double e(double d0, double d1, double d2) {
/* 1023 */     double d3 = this.locX - d0;
/* 1024 */     double d4 = this.locY - d1;
/* 1025 */     double d5 = this.locZ - d2;
/*      */     
/* 1027 */     return d3 * d3 + d4 * d4 + d5 * d5;
/*      */   }
/*      */   
/*      */   public double b(BlockPosition blockposition) {
/* 1031 */     return blockposition.c(this.locX, this.locY, this.locZ);
/*      */   }
/*      */   
/*      */   public double c(BlockPosition blockposition) {
/* 1035 */     return blockposition.d(this.locX, this.locY, this.locZ);
/*      */   }
/*      */   
/*      */   public double f(double d0, double d1, double d2) {
/* 1039 */     double d3 = this.locX - d0;
/* 1040 */     double d4 = this.locY - d1;
/* 1041 */     double d5 = this.locZ - d2;
/*      */     
/* 1043 */     return MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*      */   }
/*      */   
/*      */   public double h(Entity entity) {
/* 1047 */     double d0 = this.locX - entity.locX;
/* 1048 */     double d1 = this.locY - entity.locY;
/* 1049 */     double d2 = this.locZ - entity.locZ;
/*      */     
/* 1051 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */   
/*      */   public void d(EntityHuman entityhuman) {}
/*      */   
/* 1056 */   int numCollisions = 0;
/*      */   
/* 1058 */   public void collide(Entity entity) { if ((entity.passenger != this) && (entity.vehicle != this) && 
/* 1059 */       (!entity.noclip) && (!this.noclip)) {
/* 1060 */       double d0 = entity.locX - this.locX;
/* 1061 */       double d1 = entity.locZ - this.locZ;
/* 1062 */       double d2 = MathHelper.a(d0, d1);
/*      */       
/* 1064 */       if (d2 >= 0.009999999776482582D) {
/* 1065 */         d2 = MathHelper.sqrt(d2);
/* 1066 */         d0 /= d2;
/* 1067 */         d1 /= d2;
/* 1068 */         double d3 = 1.0D / d2;
/*      */         
/* 1070 */         if (d3 > 1.0D) {
/* 1071 */           d3 = 1.0D;
/*      */         }
/*      */         
/* 1074 */         d0 *= d3;
/* 1075 */         d1 *= d3;
/* 1076 */         d0 *= 0.05000000074505806D;
/* 1077 */         d1 *= 0.05000000074505806D;
/* 1078 */         d0 *= (1.0F - this.U);
/* 1079 */         d1 *= (1.0F - this.U);
/* 1080 */         if (this.passenger == null) {
/* 1081 */           g(-d0, 0.0D, -d1);
/*      */         }
/*      */         
/* 1084 */         if (entity.passenger == null) {
/* 1085 */           entity.g(d0, 0.0D, d1);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void g(double d0, double d1, double d2)
/*      */   {
/* 1094 */     this.motX += d0;
/* 1095 */     this.motY += d1;
/* 1096 */     this.motZ += d2;
/* 1097 */     this.ai = true;
/*      */   }
/*      */   
/*      */   protected void ac() {
/* 1101 */     this.velocityChanged = true;
/*      */   }
/*      */   
/*      */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 1105 */     if (isInvulnerable(damagesource)) {
/* 1106 */       return false;
/*      */     }
/* 1108 */     ac();
/* 1109 */     return false;
/*      */   }
/*      */   
/*      */   public Vec3D d(float f)
/*      */   {
/* 1114 */     if (f == 1.0F) {
/* 1115 */       return f(this.pitch, this.yaw);
/*      */     }
/* 1117 */     float f1 = this.lastPitch + (this.pitch - this.lastPitch) * f;
/* 1118 */     float f2 = this.lastYaw + (this.yaw - this.lastYaw) * f;
/*      */     
/* 1120 */     return f(f1, f2);
/*      */   }
/*      */   
/*      */   protected final Vec3D f(float f, float f1)
/*      */   {
/* 1125 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/* 1126 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/* 1127 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/* 1128 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/*      */     
/* 1130 */     return new Vec3D(f3 * f4, f5, f2 * f4);
/*      */   }
/*      */   
/*      */   public boolean ad() {
/* 1134 */     return false;
/*      */   }
/*      */   
/*      */   public boolean ae() {
/* 1138 */     return false;
/*      */   }
/*      */   
/*      */   public void b(Entity entity, int i) {}
/*      */   
/*      */   public boolean c(NBTTagCompound nbttagcompound) {
/* 1144 */     String s = ag();
/*      */     
/* 1146 */     if ((!this.dead) && (s != null)) {
/* 1147 */       nbttagcompound.setString("id", s);
/* 1148 */       e(nbttagcompound);
/* 1149 */       return true;
/*      */     }
/* 1151 */     return false;
/*      */   }
/*      */   
/*      */   public boolean d(NBTTagCompound nbttagcompound)
/*      */   {
/* 1156 */     String s = ag();
/*      */     
/* 1158 */     if ((!this.dead) && (s != null) && (this.passenger == null)) {
/* 1159 */       nbttagcompound.setString("id", s);
/* 1160 */       e(nbttagcompound);
/* 1161 */       return true;
/*      */     }
/* 1163 */     return false;
/*      */   }
/*      */   
/*      */   public void e(NBTTagCompound nbttagcompound)
/*      */   {
/*      */     try {
/* 1169 */       nbttagcompound.set("Pos", a(new double[] { this.locX, this.locY, this.locZ }));
/* 1170 */       nbttagcompound.set("Motion", a(new double[] { this.motX, this.motY, this.motZ }));
/*      */       
/*      */ 
/*      */ 
/* 1174 */       if (Float.isNaN(this.yaw)) {
/* 1175 */         this.yaw = 0.0F;
/*      */       }
/*      */       
/* 1178 */       if (Float.isNaN(this.pitch)) {
/* 1179 */         this.pitch = 0.0F;
/*      */       }
/*      */       
/*      */ 
/* 1183 */       nbttagcompound.set("Rotation", a(new float[] { this.yaw, this.pitch }));
/* 1184 */       nbttagcompound.setFloat("FallDistance", this.fallDistance);
/* 1185 */       nbttagcompound.setShort("Fire", (short)this.fireTicks);
/* 1186 */       nbttagcompound.setShort("Air", (short)getAirTicks());
/* 1187 */       nbttagcompound.setBoolean("OnGround", this.onGround);
/* 1188 */       nbttagcompound.setInt("Dimension", this.dimension);
/* 1189 */       nbttagcompound.setBoolean("Invulnerable", this.invulnerable);
/* 1190 */       nbttagcompound.setInt("PortalCooldown", this.portalCooldown);
/* 1191 */       nbttagcompound.setLong("UUIDMost", getUniqueID().getMostSignificantBits());
/* 1192 */       nbttagcompound.setLong("UUIDLeast", getUniqueID().getLeastSignificantBits());
/*      */       
/* 1194 */       nbttagcompound.setLong("WorldUUIDLeast", this.world.getDataManager().getUUID().getLeastSignificantBits());
/* 1195 */       nbttagcompound.setLong("WorldUUIDMost", this.world.getDataManager().getUUID().getMostSignificantBits());
/* 1196 */       nbttagcompound.setInt("Bukkit.updateLevel", 2);
/* 1197 */       nbttagcompound.setInt("Spigot.ticksLived", this.ticksLived);
/*      */       
/* 1199 */       if ((getCustomName() != null) && (getCustomName().length() > 0)) {
/* 1200 */         nbttagcompound.setString("CustomName", getCustomName());
/* 1201 */         nbttagcompound.setBoolean("CustomNameVisible", getCustomNameVisible());
/*      */       }
/*      */       
/* 1204 */       this.au.b(nbttagcompound);
/* 1205 */       if (R()) {
/* 1206 */         nbttagcompound.setBoolean("Silent", R());
/*      */       }
/*      */       
/* 1209 */       b(nbttagcompound);
/* 1210 */       if (this.vehicle != null) {
/* 1211 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */         
/* 1213 */         if (this.vehicle.c(nbttagcompound1)) {
/* 1214 */           nbttagcompound.set("Riding", nbttagcompound1);
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Throwable throwable) {
/* 1219 */       CrashReport crashreport = CrashReport.a(throwable, "Saving entity NBT");
/* 1220 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being saved");
/*      */       
/* 1222 */       appendEntityCrashDetails(crashreportsystemdetails);
/* 1223 */       throw new ReportedException(crashreport);
/*      */     }
/*      */   }
/*      */   
/*      */   public void f(NBTTagCompound nbttagcompound) {
/*      */     try {
/* 1229 */       NBTTagList nbttaglist = nbttagcompound.getList("Pos", 6);
/* 1230 */       NBTTagList nbttaglist1 = nbttagcompound.getList("Motion", 6);
/* 1231 */       NBTTagList nbttaglist2 = nbttagcompound.getList("Rotation", 5);
/*      */       
/* 1233 */       this.motX = nbttaglist1.d(0);
/* 1234 */       this.motY = nbttaglist1.d(1);
/* 1235 */       this.motZ = nbttaglist1.d(2);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1251 */       this.lastX = (this.P = this.locX = nbttaglist.d(0));
/* 1252 */       this.lastY = (this.Q = this.locY = nbttaglist.d(1));
/* 1253 */       this.lastZ = (this.R = this.locZ = nbttaglist.d(2));
/* 1254 */       this.lastYaw = (this.yaw = nbttaglist2.e(0));
/* 1255 */       this.lastPitch = (this.pitch = nbttaglist2.e(1));
/* 1256 */       f(this.yaw);
/* 1257 */       g(this.yaw);
/* 1258 */       this.fallDistance = nbttagcompound.getFloat("FallDistance");
/* 1259 */       this.fireTicks = nbttagcompound.getShort("Fire");
/* 1260 */       setAirTicks(nbttagcompound.getShort("Air"));
/* 1261 */       this.onGround = nbttagcompound.getBoolean("OnGround");
/* 1262 */       this.dimension = nbttagcompound.getInt("Dimension");
/* 1263 */       this.invulnerable = nbttagcompound.getBoolean("Invulnerable");
/* 1264 */       this.portalCooldown = nbttagcompound.getInt("PortalCooldown");
/* 1265 */       if ((nbttagcompound.hasKeyOfType("UUIDMost", 4)) && (nbttagcompound.hasKeyOfType("UUIDLeast", 4))) {
/* 1266 */         this.uniqueID = new UUID(nbttagcompound.getLong("UUIDMost"), nbttagcompound.getLong("UUIDLeast"));
/* 1267 */       } else if (nbttagcompound.hasKeyOfType("UUID", 8)) {
/* 1268 */         this.uniqueID = UUID.fromString(nbttagcompound.getString("UUID"));
/*      */       }
/*      */       
/* 1271 */       setPosition(this.locX, this.locY, this.locZ);
/* 1272 */       setYawPitch(this.yaw, this.pitch);
/* 1273 */       if ((nbttagcompound.hasKeyOfType("CustomName", 8)) && (nbttagcompound.getString("CustomName").length() > 0)) {
/* 1274 */         setCustomName(nbttagcompound.getString("CustomName"));
/*      */       }
/*      */       
/* 1277 */       setCustomNameVisible(nbttagcompound.getBoolean("CustomNameVisible"));
/* 1278 */       this.au.a(nbttagcompound);
/* 1279 */       b(nbttagcompound.getBoolean("Silent"));
/* 1280 */       a(nbttagcompound);
/* 1281 */       if (af()) {
/* 1282 */         setPosition(this.locX, this.locY, this.locZ);
/*      */       }
/*      */       
/*      */ 
/* 1286 */       if ((this instanceof EntityLiving)) {
/* 1287 */         EntityLiving entity = (EntityLiving)this;
/*      */         
/* 1289 */         this.ticksLived = nbttagcompound.getInt("Spigot.ticksLived");
/*      */         
/*      */ 
/* 1292 */         if (((entity instanceof EntityTameableAnimal)) && (!isLevelAtLeast(nbttagcompound, 2)) && (!nbttagcompound.getBoolean("PersistenceRequired"))) {
/* 1293 */           EntityInsentient entityinsentient = (EntityInsentient)entity;
/* 1294 */           entityinsentient.persistent = (!entityinsentient.isTypeNotPersistent());
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1300 */       if (!(getBukkitEntity() instanceof Vehicle)) {
/* 1301 */         if (Math.abs(this.motX) > 10.0D) {
/* 1302 */           this.motX = 0.0D;
/*      */         }
/*      */         
/* 1305 */         if (Math.abs(this.motY) > 10.0D) {
/* 1306 */           this.motY = 0.0D;
/*      */         }
/*      */         
/* 1309 */         if (Math.abs(this.motZ) > 10.0D) {
/* 1310 */           this.motZ = 0.0D;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1316 */       if ((this instanceof EntityPlayer)) {
/* 1317 */         Server server = Bukkit.getServer();
/* 1318 */         org.bukkit.World bworld = null;
/*      */         
/*      */ 
/* 1321 */         String worldName = nbttagcompound.getString("world");
/*      */         
/* 1323 */         if ((nbttagcompound.hasKey("WorldUUIDMost")) && (nbttagcompound.hasKey("WorldUUIDLeast"))) {
/* 1324 */           UUID uid = new UUID(nbttagcompound.getLong("WorldUUIDMost"), nbttagcompound.getLong("WorldUUIDLeast"));
/* 1325 */           bworld = server.getWorld(uid);
/*      */         } else {
/* 1327 */           bworld = server.getWorld(worldName);
/*      */         }
/*      */         
/* 1330 */         if (bworld == null) {
/* 1331 */           EntityPlayer entityPlayer = (EntityPlayer)this;
/* 1332 */           bworld = ((CraftServer)server).getServer().getWorldServer(entityPlayer.dimension).getWorld();
/*      */         }
/*      */         
/* 1335 */         spawnIn(bworld == null ? null : ((CraftWorld)bworld).getHandle());
/*      */       }
/*      */     }
/*      */     catch (Throwable throwable)
/*      */     {
/* 1340 */       CrashReport crashreport = CrashReport.a(throwable, "Loading entity NBT");
/* 1341 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being loaded");
/*      */       
/* 1343 */       appendEntityCrashDetails(crashreportsystemdetails);
/* 1344 */       throw new ReportedException(crashreport);
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean af() {
/* 1349 */     return true;
/*      */   }
/*      */   
/*      */   protected final String ag() {
/* 1353 */     return EntityTypes.b(this);
/*      */   }
/*      */   
/*      */   protected abstract void a(NBTTagCompound paramNBTTagCompound);
/*      */   
/*      */   protected abstract void b(NBTTagCompound paramNBTTagCompound);
/*      */   
/*      */   public void ah() {}
/*      */   
/*      */   protected NBTTagList a(double... adouble) {
/* 1363 */     NBTTagList nbttaglist = new NBTTagList();
/* 1364 */     double[] adouble1 = adouble;
/* 1365 */     int i = adouble.length;
/*      */     
/* 1367 */     for (int j = 0; j < i; j++) {
/* 1368 */       double d0 = adouble1[j];
/*      */       
/* 1370 */       nbttaglist.add(new NBTTagDouble(d0));
/*      */     }
/*      */     
/* 1373 */     return nbttaglist;
/*      */   }
/*      */   
/*      */   protected NBTTagList a(float... afloat) {
/* 1377 */     NBTTagList nbttaglist = new NBTTagList();
/* 1378 */     float[] afloat1 = afloat;
/* 1379 */     int i = afloat.length;
/*      */     
/* 1381 */     for (int j = 0; j < i; j++) {
/* 1382 */       float f = afloat1[j];
/*      */       
/* 1384 */       nbttaglist.add(new NBTTagFloat(f));
/*      */     }
/*      */     
/* 1387 */     return nbttaglist;
/*      */   }
/*      */   
/*      */   public EntityItem a(Item item, int i) {
/* 1391 */     return a(item, i, 0.0F);
/*      */   }
/*      */   
/*      */   public EntityItem a(Item item, int i, float f) {
/* 1395 */     return a(new ItemStack(item, i, 0), f);
/*      */   }
/*      */   
/*      */   public EntityItem a(ItemStack itemstack, float f) {
/* 1399 */     if ((itemstack.count != 0) && (itemstack.getItem() != null))
/*      */     {
/* 1401 */       if (((this instanceof EntityLiving)) && (((EntityLiving)this).drops != null)) {
/* 1402 */         ((EntityLiving)this).drops.add(org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asBukkitCopy(itemstack));
/* 1403 */         return null;
/*      */       }
/*      */       
/* 1406 */       EntityItem entityitem = new EntityItem(this.world, this.locX, this.locY + f, this.locZ, itemstack);
/*      */       
/* 1408 */       entityitem.p();
/* 1409 */       this.world.addEntity(entityitem);
/* 1410 */       return entityitem;
/*      */     }
/* 1412 */     return null;
/*      */   }
/*      */   
/*      */   public boolean isAlive()
/*      */   {
/* 1417 */     return !this.dead;
/*      */   }
/*      */   
/*      */   public boolean inBlock() {
/* 1421 */     if (this.noclip) {
/* 1422 */       return false;
/*      */     }
/* 1424 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
/*      */     
/* 1426 */     for (int i = 0; i < 8; i++) {
/* 1427 */       int j = MathHelper.floor(this.locY + ((i >> 0) % 2 - 0.5F) * 0.1F + getHeadHeight());
/* 1428 */       int k = MathHelper.floor(this.locX + ((i >> 1) % 2 - 0.5F) * this.width * 0.8F);
/* 1429 */       int l = MathHelper.floor(this.locZ + ((i >> 2) % 2 - 0.5F) * this.width * 0.8F);
/*      */       
/* 1431 */       if ((blockposition_mutableblockposition.getX() != k) || (blockposition_mutableblockposition.getY() != j) || (blockposition_mutableblockposition.getZ() != l)) {
/* 1432 */         blockposition_mutableblockposition.c(k, j, l);
/* 1433 */         if (this.world.getType(blockposition_mutableblockposition).getBlock().w()) {
/* 1434 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1439 */     return false;
/*      */   }
/*      */   
/*      */   public boolean e(EntityHuman entityhuman)
/*      */   {
/* 1444 */     return false;
/*      */   }
/*      */   
/*      */   public AxisAlignedBB j(Entity entity) {
/* 1448 */     return null;
/*      */   }
/*      */   
/*      */   public void ak() {
/* 1452 */     if (this.vehicle.dead) {
/* 1453 */       this.vehicle = null;
/*      */     } else {
/* 1455 */       this.motX = 0.0D;
/* 1456 */       this.motY = 0.0D;
/* 1457 */       this.motZ = 0.0D;
/* 1458 */       t_();
/* 1459 */       if (this.vehicle != null) {
/* 1460 */         this.vehicle.al();
/* 1461 */         this.as += this.vehicle.yaw - this.vehicle.lastYaw;
/*      */         
/* 1463 */         for (this.ar += this.vehicle.pitch - this.vehicle.lastPitch; this.as >= 180.0D; this.as -= 360.0D) {}
/*      */         
/*      */ 
/*      */ 
/* 1467 */         while (this.as < -180.0D) {
/* 1468 */           this.as += 360.0D;
/*      */         }
/*      */         
/* 1471 */         while (this.ar >= 180.0D) {
/* 1472 */           this.ar -= 360.0D;
/*      */         }
/*      */         
/* 1475 */         while (this.ar < -180.0D) {
/* 1476 */           this.ar += 360.0D;
/*      */         }
/*      */         
/* 1479 */         double d0 = this.as * 0.5D;
/* 1480 */         double d1 = this.ar * 0.5D;
/* 1481 */         float f = 10.0F;
/*      */         
/* 1483 */         if (d0 > f) {
/* 1484 */           d0 = f;
/*      */         }
/*      */         
/* 1487 */         if (d0 < -f) {
/* 1488 */           d0 = -f;
/*      */         }
/*      */         
/* 1491 */         if (d1 > f) {
/* 1492 */           d1 = f;
/*      */         }
/*      */         
/* 1495 */         if (d1 < -f) {
/* 1496 */           d1 = -f;
/*      */         }
/*      */         
/* 1499 */         this.as -= d0;
/* 1500 */         this.ar -= d1;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void al() {
/* 1506 */     if (this.passenger != null) {
/* 1507 */       this.passenger.setPosition(this.locX, this.locY + an() + this.passenger.am(), this.locZ);
/*      */     }
/*      */   }
/*      */   
/*      */   public double am() {
/* 1512 */     return 0.0D;
/*      */   }
/*      */   
/*      */   public double an() {
/* 1516 */     return this.length * 0.75D;
/*      */   }
/*      */   
/*      */ 
/*      */   protected CraftEntity bukkitEntity;
/*      */   public CraftEntity getBukkitEntity()
/*      */   {
/* 1523 */     if (this.bukkitEntity == null) {
/* 1524 */       this.bukkitEntity = CraftEntity.getEntity(this.world.getServer(), this);
/*      */     }
/* 1526 */     return this.bukkitEntity;
/*      */   }
/*      */   
/*      */   public void mount(Entity entity) {
/* 1530 */     Entity originalVehicle = this.vehicle;
/* 1531 */     Entity originalPassenger = this.vehicle == null ? null : this.vehicle.passenger;
/* 1532 */     PluginManager pluginManager = Bukkit.getPluginManager();
/* 1533 */     getBukkitEntity();
/*      */     
/* 1535 */     this.ar = 0.0D;
/* 1536 */     this.as = 0.0D;
/* 1537 */     if (entity == null) {
/* 1538 */       if (this.vehicle != null)
/*      */       {
/* 1540 */         if (((this.bukkitEntity instanceof LivingEntity)) && ((this.vehicle.getBukkitEntity() instanceof Vehicle))) {
/* 1541 */           VehicleExitEvent event = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)this.bukkitEntity);
/* 1542 */           pluginManager.callEvent(event);
/*      */           
/* 1544 */           if ((event.isCancelled()) || (this.vehicle != originalVehicle)) {
/* 1545 */             return;
/*      */           }
/*      */         }
/*      */         
/* 1549 */         pluginManager.callEvent(new org.spigotmc.event.entity.EntityDismountEvent(getBukkitEntity(), this.vehicle.getBukkitEntity()));
/* 1550 */         setPositionRotation(this.vehicle.locX, this.vehicle.getBoundingBox().b + this.vehicle.length, this.vehicle.locZ, this.yaw, this.pitch);
/* 1551 */         this.vehicle.passenger = null;
/*      */       }
/*      */       
/* 1554 */       this.vehicle = null;
/*      */     }
/*      */     else {
/* 1557 */       if (((this.bukkitEntity instanceof LivingEntity)) && ((entity.getBukkitEntity() instanceof Vehicle)) && (entity.world.isChunkLoaded((int)entity.locX >> 4, (int)entity.locZ >> 4, true)))
/*      */       {
/* 1559 */         VehicleExitEvent exitEvent = null;
/* 1560 */         if ((this.vehicle != null) && ((this.vehicle.getBukkitEntity() instanceof Vehicle))) {
/* 1561 */           exitEvent = new VehicleExitEvent((Vehicle)this.vehicle.getBukkitEntity(), (LivingEntity)this.bukkitEntity);
/* 1562 */           pluginManager.callEvent(exitEvent);
/*      */           
/* 1564 */           if ((exitEvent.isCancelled()) || (this.vehicle != originalVehicle) || ((this.vehicle != null) && (this.vehicle.passenger != originalPassenger))) {
/* 1565 */             return;
/*      */           }
/*      */         }
/*      */         
/* 1569 */         VehicleEnterEvent event = new VehicleEnterEvent((Vehicle)entity.getBukkitEntity(), this.bukkitEntity);
/* 1570 */         pluginManager.callEvent(event);
/*      */         
/*      */ 
/* 1573 */         if ((event.isCancelled()) || (this.vehicle != originalVehicle) || ((this.vehicle != null) && (this.vehicle.passenger != originalPassenger)))
/*      */         {
/* 1575 */           if ((exitEvent != null) && (this.vehicle == originalVehicle) && (this.vehicle != null) && (this.vehicle.passenger == originalPassenger)) {
/* 1576 */             setPositionRotation(this.vehicle.locX, this.vehicle.getBoundingBox().b + this.vehicle.length, this.vehicle.locZ, this.yaw, this.pitch);
/* 1577 */             this.vehicle.passenger = null;
/* 1578 */             this.vehicle = null;
/*      */           }
/* 1580 */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1585 */       if (entity.world.isChunkLoaded((int)entity.locX >> 4, (int)entity.locZ >> 4, true))
/*      */       {
/* 1587 */         EntityMountEvent event = new EntityMountEvent(getBukkitEntity(), entity.getBukkitEntity());
/* 1588 */         pluginManager.callEvent(event);
/* 1589 */         if (event.isCancelled())
/*      */         {
/* 1591 */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1596 */       if (this.vehicle != null) {
/* 1597 */         this.vehicle.passenger = null;
/*      */       }
/*      */       
/* 1600 */       if (entity != null) {
/* 1601 */         for (Entity entity1 = entity.vehicle; entity1 != null; entity1 = entity1.vehicle) {
/* 1602 */           if (entity1 == this) {
/* 1603 */             return;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1608 */       this.vehicle = entity;
/* 1609 */       entity.passenger = this;
/*      */     }
/*      */   }
/*      */   
/*      */   public float ao() {
/* 1614 */     return 0.1F;
/*      */   }
/*      */   
/*      */   public Vec3D ap() {
/* 1618 */     return null;
/*      */   }
/*      */   
/*      */   public void d(BlockPosition blockposition) {
/* 1622 */     if (this.portalCooldown > 0) {
/* 1623 */       this.portalCooldown = aq();
/*      */     } else {
/* 1625 */       if ((!this.world.isClientSide) && (!blockposition.equals(this.an))) {
/* 1626 */         this.an = blockposition;
/* 1627 */         ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = Blocks.PORTAL.f(this.world, blockposition);
/* 1628 */         double d0 = shapedetector_shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X ? shapedetector_shapedetectorcollection.a().getZ() : shapedetector_shapedetectorcollection.a().getX();
/* 1629 */         double d1 = shapedetector_shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X ? this.locZ : this.locX;
/*      */         
/* 1631 */         d1 = Math.abs(MathHelper.c(d1 - (shapedetector_shapedetectorcollection.b().e().c() == EnumDirection.EnumAxisDirection.NEGATIVE ? 1 : 0), d0, d0 - shapedetector_shapedetectorcollection.d()));
/* 1632 */         double d2 = MathHelper.c(this.locY - 1.0D, shapedetector_shapedetectorcollection.a().getY(), shapedetector_shapedetectorcollection.a().getY() - shapedetector_shapedetectorcollection.e());
/*      */         
/* 1634 */         this.ao = new Vec3D(d1, d2, 0.0D);
/* 1635 */         this.ap = shapedetector_shapedetectorcollection.b();
/*      */       }
/*      */       
/* 1638 */       this.ak = true;
/*      */     }
/*      */   }
/*      */   
/*      */   public int aq() {
/* 1643 */     return 300;
/*      */   }
/*      */   
/*      */   public ItemStack[] getEquipment() {
/* 1647 */     return null;
/*      */   }
/*      */   
/*      */   public void setEquipment(int i, ItemStack itemstack) {}
/*      */   
/*      */   public boolean isBurning() {
/* 1653 */     boolean flag = (this.world != null) && (this.world.isClientSide);
/*      */     
/* 1655 */     return (!this.fireProof) && ((this.fireTicks > 0) || ((flag) && (g(0))));
/*      */   }
/*      */   
/*      */   public boolean au() {
/* 1659 */     return this.vehicle != null;
/*      */   }
/*      */   
/*      */   public boolean isSneaking() {
/* 1663 */     return g(1);
/*      */   }
/*      */   
/*      */   public void setSneaking(boolean flag) {
/* 1667 */     b(1, flag);
/*      */   }
/*      */   
/*      */   public boolean isSprinting() {
/* 1671 */     return g(3);
/*      */   }
/*      */   
/*      */   public void setSprinting(boolean flag) {
/* 1675 */     b(3, flag);
/*      */   }
/*      */   
/*      */   public boolean isInvisible() {
/* 1679 */     return g(5);
/*      */   }
/*      */   
/*      */   public void setInvisible(boolean flag) {
/* 1683 */     b(5, flag);
/*      */   }
/*      */   
/*      */   public void f(boolean flag) {
/* 1687 */     b(4, flag);
/*      */   }
/*      */   
/*      */   protected boolean g(int i) {
/* 1691 */     return (this.datawatcher.getByte(0) & 1 << i) != 0;
/*      */   }
/*      */   
/*      */   protected void b(int i, boolean flag) {
/* 1695 */     byte b0 = this.datawatcher.getByte(0);
/*      */     
/* 1697 */     if (flag) {
/* 1698 */       this.datawatcher.watch(0, Byte.valueOf((byte)(b0 | 1 << i)));
/*      */     } else {
/* 1700 */       this.datawatcher.watch(0, Byte.valueOf((byte)(b0 & (1 << i ^ 0xFFFFFFFF))));
/*      */     }
/*      */   }
/*      */   
/*      */   public int getAirTicks()
/*      */   {
/* 1706 */     return this.datawatcher.getShort(1);
/*      */   }
/*      */   
/*      */   public void setAirTicks(int i) {
/* 1710 */     this.datawatcher.watch(1, Short.valueOf((short)i));
/*      */   }
/*      */   
/*      */   public void onLightningStrike(EntityLightning entitylightning)
/*      */   {
/* 1715 */     org.bukkit.entity.Entity thisBukkitEntity = getBukkitEntity();
/* 1716 */     org.bukkit.entity.Entity stormBukkitEntity = entitylightning.getBukkitEntity();
/* 1717 */     PluginManager pluginManager = Bukkit.getPluginManager();
/*      */     
/* 1719 */     if ((thisBukkitEntity instanceof Hanging)) {
/* 1720 */       HangingBreakByEntityEvent hangingEvent = new HangingBreakByEntityEvent((Hanging)thisBukkitEntity, stormBukkitEntity);
/* 1721 */       PaintingBreakByEntityEvent paintingEvent = null;
/*      */       
/* 1723 */       if ((thisBukkitEntity instanceof Painting)) {
/* 1724 */         paintingEvent = new PaintingBreakByEntityEvent((Painting)thisBukkitEntity, stormBukkitEntity);
/*      */       }
/*      */       
/* 1727 */       pluginManager.callEvent(hangingEvent);
/*      */       
/* 1729 */       if (paintingEvent != null) {
/* 1730 */         paintingEvent.setCancelled(hangingEvent.isCancelled());
/* 1731 */         pluginManager.callEvent(paintingEvent);
/*      */       }
/*      */       
/* 1734 */       if ((hangingEvent.isCancelled()) || ((paintingEvent != null) && (paintingEvent.isCancelled()))) {
/* 1735 */         return;
/*      */       }
/*      */     }
/*      */     
/* 1739 */     if (this.fireProof) {
/* 1740 */       return;
/*      */     }
/* 1742 */     org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.entityDamage = entitylightning;
/* 1743 */     if (!damageEntity(DamageSource.LIGHTNING, 5.0F)) {
/* 1744 */       org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.entityDamage = null;
/* 1745 */       return;
/*      */     }
/*      */     
/* 1748 */     this.fireTicks += 1;
/* 1749 */     if (this.fireTicks == 0)
/*      */     {
/* 1751 */       EntityCombustByEntityEvent entityCombustEvent = new EntityCombustByEntityEvent(stormBukkitEntity, thisBukkitEntity, 8);
/* 1752 */       pluginManager.callEvent(entityCombustEvent);
/* 1753 */       if (!entityCombustEvent.isCancelled()) {
/* 1754 */         setOnFire(entityCombustEvent.getDuration());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void a(EntityLiving entityliving) {}
/*      */   
/*      */   protected boolean j(double d0, double d1, double d2)
/*      */   {
/* 1764 */     BlockPosition blockposition = new BlockPosition(d0, d1, d2);
/* 1765 */     double d3 = d0 - blockposition.getX();
/* 1766 */     double d4 = d1 - blockposition.getY();
/* 1767 */     double d5 = d2 - blockposition.getZ();
/* 1768 */     List list = this.world.a(getBoundingBox());
/*      */     
/* 1770 */     if ((list.isEmpty()) && (!this.world.u(blockposition))) {
/* 1771 */       return false;
/*      */     }
/* 1773 */     byte b0 = 3;
/* 1774 */     double d6 = 9999.0D;
/*      */     
/* 1776 */     if ((!this.world.u(blockposition.west())) && (d3 < d6)) {
/* 1777 */       d6 = d3;
/* 1778 */       b0 = 0;
/*      */     }
/*      */     
/* 1781 */     if ((!this.world.u(blockposition.east())) && (1.0D - d3 < d6)) {
/* 1782 */       d6 = 1.0D - d3;
/* 1783 */       b0 = 1;
/*      */     }
/*      */     
/* 1786 */     if ((!this.world.u(blockposition.up())) && (1.0D - d4 < d6)) {
/* 1787 */       d6 = 1.0D - d4;
/* 1788 */       b0 = 3;
/*      */     }
/*      */     
/* 1791 */     if ((!this.world.u(blockposition.north())) && (d5 < d6)) {
/* 1792 */       d6 = d5;
/* 1793 */       b0 = 4;
/*      */     }
/*      */     
/* 1796 */     if ((!this.world.u(blockposition.south())) && (1.0D - d5 < d6)) {
/* 1797 */       d6 = 1.0D - d5;
/* 1798 */       b0 = 5;
/*      */     }
/*      */     
/* 1801 */     float f = this.random.nextFloat() * 0.2F + 0.1F;
/*      */     
/* 1803 */     if (b0 == 0) {
/* 1804 */       this.motX = (-f);
/*      */     }
/*      */     
/* 1807 */     if (b0 == 1) {
/* 1808 */       this.motX = f;
/*      */     }
/*      */     
/* 1811 */     if (b0 == 3) {
/* 1812 */       this.motY = f;
/*      */     }
/*      */     
/* 1815 */     if (b0 == 4) {
/* 1816 */       this.motZ = (-f);
/*      */     }
/*      */     
/* 1819 */     if (b0 == 5) {
/* 1820 */       this.motZ = f;
/*      */     }
/*      */     
/* 1823 */     return true;
/*      */   }
/*      */   
/*      */   public void aA()
/*      */   {
/* 1828 */     this.H = true;
/* 1829 */     this.fallDistance = 0.0F;
/*      */   }
/*      */   
/*      */   public String getName() {
/* 1833 */     if (hasCustomName()) {
/* 1834 */       return getCustomName();
/*      */     }
/* 1836 */     String s = EntityTypes.b(this);
/*      */     
/* 1838 */     if (s == null) {
/* 1839 */       s = "generic";
/*      */     }
/*      */     
/* 1842 */     return LocaleI18n.get("entity." + s + ".name");
/*      */   }
/*      */   
/*      */   public Entity[] aB()
/*      */   {
/* 1847 */     return null;
/*      */   }
/*      */   
/*      */   public boolean k(Entity entity) {
/* 1851 */     return this == entity;
/*      */   }
/*      */   
/*      */   public float getHeadRotation() {
/* 1855 */     return 0.0F;
/*      */   }
/*      */   
/*      */   public void f(float f) {}
/*      */   
/*      */   public void g(float f) {}
/*      */   
/*      */   public boolean aD() {
/* 1863 */     return true;
/*      */   }
/*      */   
/*      */   public boolean l(Entity entity) {
/* 1867 */     return false;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 1871 */     return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getName(), Integer.valueOf(this.id), this.world == null ? "~NULL~" : this.world.getWorldData().getName(), Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ) });
/*      */   }
/*      */   
/*      */   public boolean isInvulnerable(DamageSource damagesource) {
/* 1875 */     return (this.invulnerable) && (damagesource != DamageSource.OUT_OF_WORLD) && (!damagesource.u());
/*      */   }
/*      */   
/*      */   public void m(Entity entity) {
/* 1879 */     setPositionRotation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
/*      */   }
/*      */   
/*      */   public void n(Entity entity) {
/* 1883 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */     
/* 1885 */     entity.e(nbttagcompound);
/* 1886 */     f(nbttagcompound);
/* 1887 */     this.portalCooldown = entity.portalCooldown;
/* 1888 */     this.an = entity.an;
/* 1889 */     this.ao = entity.ao;
/* 1890 */     this.ap = entity.ap;
/*      */   }
/*      */   
/*      */   public void c(int i) {
/* 1894 */     if ((!this.world.isClientSide) && (!this.dead)) {
/* 1895 */       this.world.methodProfiler.a("changeDimension");
/* 1896 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1901 */       WorldServer exitWorld = null;
/* 1902 */       if (this.dimension < 10)
/*      */       {
/* 1904 */         for (WorldServer world : minecraftserver.worlds) {
/* 1905 */           if (world.dimension == i) {
/* 1906 */             exitWorld = world;
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1911 */       Location enter = getBukkitEntity().getLocation();
/* 1912 */       Location exit = exitWorld != null ? minecraftserver.getPlayerList().calculateTarget(enter, minecraftserver.getWorldServer(i)) : null;
/* 1913 */       boolean useTravelAgent = (exitWorld != null) && ((this.dimension != 1) || (exitWorld.dimension != 1));
/*      */       
/* 1915 */       TravelAgent agent = exit != null ? (TravelAgent)((CraftWorld)exit.getWorld()).getHandle().getTravelAgent() : org.bukkit.craftbukkit.v1_8_R3.CraftTravelAgent.DEFAULT;
/* 1916 */       EntityPortalEvent event = new EntityPortalEvent(getBukkitEntity(), enter, exit, agent);
/* 1917 */       event.useTravelAgent(useTravelAgent);
/* 1918 */       event.getEntity().getServer().getPluginManager().callEvent(event);
/* 1919 */       if ((event.isCancelled()) || (event.getTo() == null) || (event.getTo().getWorld() == null) || (!isAlive())) {
/* 1920 */         return;
/*      */       }
/* 1922 */       exit = event.useTravelAgent() ? event.getPortalTravelAgent().findOrCreate(event.getTo()) : event.getTo();
/* 1923 */       teleportTo(exit, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void teleportTo(Location exit, boolean portal)
/*      */   {
/* 1929 */     WorldServer worldserver = ((CraftWorld)getBukkitEntity().getLocation().getWorld()).getHandle();
/* 1930 */     WorldServer worldserver1 = ((CraftWorld)exit.getWorld()).getHandle();
/* 1931 */     int i = worldserver1.dimension;
/*      */     
/*      */ 
/* 1934 */     this.dimension = i;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1942 */     this.world.kill(this);
/* 1943 */     this.dead = false;
/* 1944 */     this.world.methodProfiler.a("reposition");
/*      */     
/*      */ 
/* 1947 */     boolean before = worldserver1.chunkProviderServer.forceChunkLoad;
/* 1948 */     worldserver1.chunkProviderServer.forceChunkLoad = true;
/* 1949 */     worldserver1.getMinecraftServer().getPlayerList().repositionEntity(this, exit, portal);
/* 1950 */     worldserver1.chunkProviderServer.forceChunkLoad = before;
/*      */     
/* 1952 */     this.world.methodProfiler.c("reloading");
/* 1953 */     Entity entity = EntityTypes.createEntityByName(EntityTypes.b(this), worldserver1);
/*      */     
/* 1955 */     if (entity != null) {
/* 1956 */       entity.n(this);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1965 */       worldserver1.addEntity(entity);
/*      */       
/* 1967 */       getBukkitEntity().setHandle(entity);
/* 1968 */       entity.bukkitEntity = getBukkitEntity();
/*      */       
/* 1970 */       if ((this instanceof EntityInsentient)) {
/* 1971 */         ((EntityInsentient)this).unleash(true, false);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1976 */     this.dead = true;
/* 1977 */     this.world.methodProfiler.b();
/* 1978 */     worldserver.j();
/* 1979 */     worldserver1.j();
/* 1980 */     this.world.methodProfiler.b();
/*      */   }
/*      */   
/*      */   public float a(Explosion explosion, World world, BlockPosition blockposition, IBlockData iblockdata)
/*      */   {
/* 1985 */     return iblockdata.getBlock().a(this);
/*      */   }
/*      */   
/*      */   public boolean a(Explosion explosion, World world, BlockPosition blockposition, IBlockData iblockdata, float f) {
/* 1989 */     return true;
/*      */   }
/*      */   
/*      */   public int aE() {
/* 1993 */     return 3;
/*      */   }
/*      */   
/*      */   public Vec3D aG() {
/* 1997 */     return this.ao;
/*      */   }
/*      */   
/*      */   public EnumDirection aH() {
/* 2001 */     return this.ap;
/*      */   }
/*      */   
/*      */   public boolean aI() {
/* 2005 */     return false;
/*      */   }
/*      */   
/*      */   public void appendEntityCrashDetails(CrashReportSystemDetails crashreportsystemdetails) {
/* 2009 */     crashreportsystemdetails.a("Entity Type", new Callable() {
/*      */       public String a() throws Exception {
/* 2011 */         return EntityTypes.b(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
/*      */       }
/*      */       
/*      */       public Object call() throws Exception {
/* 2015 */         return a();
/*      */       }
/* 2017 */     });
/* 2018 */     crashreportsystemdetails.a("Entity ID", Integer.valueOf(this.id));
/* 2019 */     crashreportsystemdetails.a("Entity Name", new Callable() {
/*      */       public String a() throws Exception {
/* 2021 */         return Entity.this.getName();
/*      */       }
/*      */       
/*      */       public Object call() throws Exception {
/* 2025 */         return a();
/*      */       }
/* 2027 */     });
/* 2028 */     crashreportsystemdetails.a("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.locX), Double.valueOf(this.locY), Double.valueOf(this.locZ) }));
/* 2029 */     crashreportsystemdetails.a("Entity's Block location", CrashReportSystemDetails.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)));
/* 2030 */     crashreportsystemdetails.a("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motX), Double.valueOf(this.motY), Double.valueOf(this.motZ) }));
/* 2031 */     crashreportsystemdetails.a("Entity's Rider", new Callable() {
/*      */       public String a() throws Exception {
/* 2033 */         return Entity.this.passenger.toString();
/*      */       }
/*      */       
/*      */       public Object call() throws Exception {
/* 2037 */         return a();
/*      */       }
/* 2039 */     });
/* 2040 */     crashreportsystemdetails.a("Entity's Vehicle", new Callable() {
/*      */       public String a() throws Exception {
/* 2042 */         return Entity.this.vehicle.toString();
/*      */       }
/*      */       
/*      */       public Object call() throws Exception {
/* 2046 */         return a();
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   public UUID getUniqueID() {
/* 2052 */     return this.uniqueID;
/*      */   }
/*      */   
/*      */   public boolean aL() {
/* 2056 */     return true;
/*      */   }
/*      */   
/*      */   public IChatBaseComponent getScoreboardDisplayName() {
/* 2060 */     ChatComponentText chatcomponenttext = new ChatComponentText(getName());
/*      */     
/* 2062 */     chatcomponenttext.getChatModifier().setChatHoverable(aQ());
/* 2063 */     chatcomponenttext.getChatModifier().setInsertion(getUniqueID().toString());
/* 2064 */     return chatcomponenttext;
/*      */   }
/*      */   
/*      */   public void setCustomName(String s)
/*      */   {
/* 2069 */     if (s.length() > 256) {
/* 2070 */       s = s.substring(0, 256);
/*      */     }
/*      */     
/* 2073 */     this.datawatcher.watch(2, s);
/*      */   }
/*      */   
/*      */   public String getCustomName() {
/* 2077 */     return this.datawatcher.getString(2);
/*      */   }
/*      */   
/*      */   public boolean hasCustomName() {
/* 2081 */     return this.datawatcher.getString(2).length() > 0;
/*      */   }
/*      */   
/*      */   public void setCustomNameVisible(boolean flag) {
/* 2085 */     this.datawatcher.watch(3, Byte.valueOf((byte)(flag ? 1 : 0)));
/*      */   }
/*      */   
/*      */   public boolean getCustomNameVisible() {
/* 2089 */     return this.datawatcher.getByte(3) == 1;
/*      */   }
/*      */   
/*      */   public void enderTeleportTo(double d0, double d1, double d2) {
/* 2093 */     setPositionRotation(d0, d1, d2, this.yaw, this.pitch);
/*      */   }
/*      */   
/*      */   public void i(int i) {}
/*      */   
/*      */   public EnumDirection getDirection() {
/* 2099 */     return EnumDirection.fromType2(MathHelper.floor(this.yaw * 4.0F / 360.0F + 0.5D) & 0x3);
/*      */   }
/*      */   
/*      */   protected ChatHoverable aQ() {
/* 2103 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2104 */     String s = EntityTypes.b(this);
/*      */     
/* 2106 */     nbttagcompound.setString("id", getUniqueID().toString());
/* 2107 */     if (s != null) {
/* 2108 */       nbttagcompound.setString("type", s);
/*      */     }
/*      */     
/* 2111 */     nbttagcompound.setString("name", getName());
/* 2112 */     return new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ENTITY, new ChatComponentText(nbttagcompound.toString()));
/*      */   }
/*      */   
/*      */   public boolean a(EntityPlayer entityplayer) {
/* 2116 */     return true;
/*      */   }
/*      */   
/*      */   public AxisAlignedBB getBoundingBox() {
/* 2120 */     return this.boundingBox;
/*      */   }
/*      */   
/*      */   public void a(AxisAlignedBB axisalignedbb)
/*      */   {
/* 2125 */     double a = axisalignedbb.a;
/* 2126 */     double b = axisalignedbb.b;
/* 2127 */     double c = axisalignedbb.c;
/* 2128 */     double d = axisalignedbb.d;
/* 2129 */     double e = axisalignedbb.e;
/* 2130 */     double f = axisalignedbb.f;
/* 2131 */     double len = axisalignedbb.d - axisalignedbb.a;
/* 2132 */     if (len < 0.0D) d = a;
/* 2133 */     if (len > 64.0D) { d = a + 64.0D;
/*      */     }
/* 2135 */     len = axisalignedbb.e - axisalignedbb.b;
/* 2136 */     if (len < 0.0D) e = b;
/* 2137 */     if (len > 64.0D) { e = b + 64.0D;
/*      */     }
/* 2139 */     len = axisalignedbb.f - axisalignedbb.c;
/* 2140 */     if (len < 0.0D) f = c;
/* 2141 */     if (len > 64.0D) f = c + 64.0D;
/* 2142 */     this.boundingBox = new AxisAlignedBB(a, b, c, d, e, f);
/*      */   }
/*      */   
/*      */   public float getHeadHeight()
/*      */   {
/* 2147 */     return this.length * 0.85F;
/*      */   }
/*      */   
/*      */   public boolean aT() {
/* 2151 */     return this.g;
/*      */   }
/*      */   
/*      */   public void h(boolean flag) {
/* 2155 */     this.g = flag;
/*      */   }
/*      */   
/*      */   public boolean d(int i, ItemStack itemstack) {
/* 2159 */     return false;
/*      */   }
/*      */   
/*      */   public void sendMessage(IChatBaseComponent ichatbasecomponent) {}
/*      */   
/*      */   public boolean a(int i, String s) {
/* 2165 */     return true;
/*      */   }
/*      */   
/*      */   public BlockPosition getChunkCoordinates() {
/* 2169 */     return new BlockPosition(this.locX, this.locY + 0.5D, this.locZ);
/*      */   }
/*      */   
/*      */   public Vec3D d() {
/* 2173 */     return new Vec3D(this.locX, this.locY, this.locZ);
/*      */   }
/*      */   
/*      */   public World getWorld() {
/* 2177 */     return this.world;
/*      */   }
/*      */   
/*      */   public Entity f() {
/* 2181 */     return this;
/*      */   }
/*      */   
/*      */   public boolean getSendCommandFeedback() {
/* 2185 */     return false;
/*      */   }
/*      */   
/*      */   public void a(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor_enumcommandresult, int i) {
/* 2189 */     this.au.a(this, commandobjectiveexecutor_enumcommandresult, i);
/*      */   }
/*      */   
/*      */   public CommandObjectiveExecutor aU() {
/* 2193 */     return this.au;
/*      */   }
/*      */   
/*      */   public void o(Entity entity) {
/* 2197 */     this.au.a(entity.aU());
/*      */   }
/*      */   
/*      */   public NBTTagCompound getNBTTag() {
/* 2201 */     return null;
/*      */   }
/*      */   
/*      */   public boolean a(EntityHuman entityhuman, Vec3D vec3d) {
/* 2205 */     return false;
/*      */   }
/*      */   
/*      */   public boolean aW() {
/* 2209 */     return false;
/*      */   }
/*      */   
/*      */   protected void a(EntityLiving entityliving, Entity entity) {
/* 2213 */     if ((entity instanceof EntityLiving)) {
/* 2214 */       EnchantmentManager.a((EntityLiving)entity, entityliving);
/*      */     }
/*      */     
/* 2217 */     EnchantmentManager.b(entityliving, entity);
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Entity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */