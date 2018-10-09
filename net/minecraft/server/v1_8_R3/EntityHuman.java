/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboardManager;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*      */ import org.bukkit.event.player.PlayerBedEnterEvent;
/*      */ import org.bukkit.event.player.PlayerDropItemEvent;
/*      */ import org.bukkit.event.player.PlayerItemConsumeEvent;
/*      */ import org.bukkit.event.player.PlayerVelocityEvent;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.scoreboard.Team;
/*      */ import org.bukkit.util.Vector;
/*      */ import org.spigotmc.SpigotWorldConfig;
/*      */ 
/*      */ public abstract class EntityHuman extends EntityLiving
/*      */ {
/*   27 */   public PlayerInventory inventory = new PlayerInventory(this);
/*   28 */   private InventoryEnderChest enderChest = new InventoryEnderChest();
/*      */   public Container defaultContainer;
/*      */   public Container activeContainer;
/*   31 */   protected FoodMetaData foodData = new FoodMetaData(this);
/*      */   protected int bm;
/*      */   public float bn;
/*      */   public float bo;
/*      */   public int bp;
/*      */   public double bq;
/*      */   public double br;
/*      */   public double bs;
/*      */   public double bt;
/*      */   public double bu;
/*      */   public double bv;
/*      */   public boolean sleeping;
/*      */   public BlockPosition bx;
/*      */   public int sleepTicks;
/*      */   public float by;
/*      */   public float bz;
/*      */   private BlockPosition c;
/*      */   private boolean d;
/*      */   private BlockPosition e;
/*   50 */   public PlayerAbilities abilities = new PlayerAbilities();
/*      */   public int expLevel;
/*      */   public int expTotal;
/*      */   public float exp;
/*      */   private int f;
/*      */   private ItemStack g;
/*      */   private int h;
/*   57 */   protected float bE = 0.1F;
/*   58 */   protected float bF = 0.02F;
/*      */   private int i;
/*      */   private final GameProfile bH;
/*   61 */   private boolean bI = false;
/*      */   
/*      */   public EntityFishingHook hookedFish;
/*      */   
/*      */   public boolean fauxSleeping;
/*   66 */   public String spawnWorld = "";
/*   67 */   public int oldLevel = -1;
/*      */   
/*      */   public org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity getBukkitEntity()
/*      */   {
/*   71 */     return (org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity)super.getBukkitEntity();
/*      */   }
/*      */   
/*      */   public EntityHuman(World world, GameProfile gameprofile)
/*      */   {
/*   76 */     super(world);
/*   77 */     this.uniqueID = a(gameprofile);
/*   78 */     this.bH = gameprofile;
/*   79 */     this.defaultContainer = new ContainerPlayer(this.inventory, !world.isClientSide, this);
/*   80 */     this.activeContainer = this.defaultContainer;
/*   81 */     BlockPosition blockposition = world.getSpawn();
/*      */     
/*   83 */     setPositionRotation(blockposition.getX() + 0.5D, blockposition.getY() + 1, blockposition.getZ() + 0.5D, 0.0F, 0.0F);
/*   84 */     this.aV = 180.0F;
/*   85 */     this.maxFireTicks = 20;
/*      */   }
/*      */   
/*      */   protected void initAttributes() {
/*   89 */     super.initAttributes();
/*   90 */     getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE).setValue(1.0D);
/*   91 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.10000000149011612D);
/*      */   }
/*      */   
/*      */   protected void h() {
/*   95 */     super.h();
/*   96 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*   97 */     this.datawatcher.a(17, Float.valueOf(0.0F));
/*   98 */     this.datawatcher.a(18, Integer.valueOf(0));
/*   99 */     this.datawatcher.a(10, Byte.valueOf((byte)0));
/*      */   }
/*      */   
/*      */   public boolean bS() {
/*  103 */     return this.g != null;
/*      */   }
/*      */   
/*      */   public void bU() {
/*  107 */     if (this.g != null) {
/*  108 */       this.g.b(this.world, this, this.h);
/*      */     }
/*      */     
/*  111 */     bV();
/*      */   }
/*      */   
/*      */   public void bV() {
/*  115 */     this.g = null;
/*  116 */     this.h = 0;
/*  117 */     if (!this.world.isClientSide) {
/*  118 */       f(false);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isBlocking()
/*      */   {
/*  124 */     return (bS()) && (this.g.getItem().e(this.g) == EnumAnimation.BLOCK);
/*      */   }
/*      */   
/*      */   public void t_() {
/*  128 */     this.noclip = isSpectator();
/*  129 */     if (isSpectator()) {
/*  130 */       this.onGround = false;
/*      */     }
/*      */     
/*  133 */     if (this.g != null) {
/*  134 */       ItemStack itemstack = this.inventory.getItemInHand();
/*      */       
/*  136 */       if (itemstack == this.g) {
/*  137 */         if ((this.h <= 25) && (this.h % 4 == 0)) {
/*  138 */           b(itemstack, 5);
/*      */         }
/*      */         
/*  141 */         if ((--this.h == 0) && (!this.world.isClientSide)) {
/*  142 */           s();
/*      */         }
/*      */       } else {
/*  145 */         bV();
/*      */       }
/*      */     }
/*      */     
/*  149 */     if (this.bp > 0) {
/*  150 */       this.bp -= 1;
/*      */     }
/*      */     
/*  153 */     if (isSleeping()) {
/*  154 */       this.sleepTicks += 1;
/*  155 */       if (this.sleepTicks > 100) {
/*  156 */         this.sleepTicks = 100;
/*      */       }
/*      */       
/*  159 */       if (!this.world.isClientSide) {
/*  160 */         if (!p()) {
/*  161 */           a(true, true, false);
/*  162 */         } else if (this.world.w()) {
/*  163 */           a(false, true, true);
/*      */         }
/*      */       }
/*  166 */     } else if (this.sleepTicks > 0) {
/*  167 */       this.sleepTicks += 1;
/*  168 */       if (this.sleepTicks >= 110) {
/*  169 */         this.sleepTicks = 0;
/*      */       }
/*      */     }
/*      */     
/*  173 */     super.t_();
/*  174 */     if ((!this.world.isClientSide) && (this.activeContainer != null) && (!this.activeContainer.a(this))) {
/*  175 */       closeInventory();
/*  176 */       this.activeContainer = this.defaultContainer;
/*      */     }
/*      */     
/*  179 */     if ((isBurning()) && (this.abilities.isInvulnerable)) {
/*  180 */       extinguish();
/*      */     }
/*      */     
/*  183 */     this.bq = this.bt;
/*  184 */     this.br = this.bu;
/*  185 */     this.bs = this.bv;
/*  186 */     double d0 = this.locX - this.bt;
/*  187 */     double d1 = this.locY - this.bu;
/*  188 */     double d2 = this.locZ - this.bv;
/*  189 */     double d3 = 10.0D;
/*      */     
/*  191 */     if (d0 > d3) {
/*  192 */       this.bq = (this.bt = this.locX);
/*      */     }
/*      */     
/*  195 */     if (d2 > d3) {
/*  196 */       this.bs = (this.bv = this.locZ);
/*      */     }
/*      */     
/*  199 */     if (d1 > d3) {
/*  200 */       this.br = (this.bu = this.locY);
/*      */     }
/*      */     
/*  203 */     if (d0 < -d3) {
/*  204 */       this.bq = (this.bt = this.locX);
/*      */     }
/*      */     
/*  207 */     if (d2 < -d3) {
/*  208 */       this.bs = (this.bv = this.locZ);
/*      */     }
/*      */     
/*  211 */     if (d1 < -d3) {
/*  212 */       this.br = (this.bu = this.locY);
/*      */     }
/*      */     
/*  215 */     this.bt += d0 * 0.25D;
/*  216 */     this.bv += d2 * 0.25D;
/*  217 */     this.bu += d1 * 0.25D;
/*  218 */     if (this.vehicle == null) {
/*  219 */       this.e = null;
/*      */     }
/*      */     
/*  222 */     if (!this.world.isClientSide) {
/*  223 */       this.foodData.a(this);
/*  224 */       b(StatisticList.g);
/*  225 */       if (isAlive()) {
/*  226 */         b(StatisticList.h);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  231 */     double d4 = MathHelper.a(this.locX, -2.9999999E7D, 2.9999999E7D);
/*  232 */     double d5 = MathHelper.a(this.locZ, -2.9999999E7D, 2.9999999E7D);
/*      */     
/*  234 */     if ((d4 != this.locX) || (d5 != this.locZ)) {
/*  235 */       setPosition(d4, this.locY, d5);
/*      */     }
/*      */   }
/*      */   
/*      */   public int L()
/*      */   {
/*  241 */     return this.abilities.isInvulnerable ? 0 : 80;
/*      */   }
/*      */   
/*      */   protected String P() {
/*  245 */     return "game.player.swim";
/*      */   }
/*      */   
/*      */   protected String aa() {
/*  249 */     return "game.player.swim.splash";
/*      */   }
/*      */   
/*      */   public int aq() {
/*  253 */     return 10;
/*      */   }
/*      */   
/*      */   public void makeSound(String s, float f, float f1) {
/*  257 */     this.world.a(this, s, f, f1);
/*      */   }
/*      */   
/*      */   protected void b(ItemStack itemstack, int i) {
/*  261 */     if (itemstack.m() == EnumAnimation.DRINK) {
/*  262 */       makeSound("random.drink", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*      */     }
/*      */     
/*  265 */     if (itemstack.m() == EnumAnimation.EAT) {
/*  266 */       for (int j = 0; j < i; j++) {
/*  267 */         Vec3D vec3d = new Vec3D((this.random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/*      */         
/*  269 */         vec3d = vec3d.a(-this.pitch * 3.1415927F / 180.0F);
/*  270 */         vec3d = vec3d.b(-this.yaw * 3.1415927F / 180.0F);
/*  271 */         double d0 = -this.random.nextFloat() * 0.6D - 0.3D;
/*  272 */         Vec3D vec3d1 = new Vec3D((this.random.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/*      */         
/*  274 */         vec3d1 = vec3d1.a(-this.pitch * 3.1415927F / 180.0F);
/*  275 */         vec3d1 = vec3d1.b(-this.yaw * 3.1415927F / 180.0F);
/*  276 */         vec3d1 = vec3d1.add(this.locX, this.locY + getHeadHeight(), this.locZ);
/*  277 */         if (itemstack.usesData()) {
/*  278 */           this.world.addParticle(EnumParticle.ITEM_CRACK, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c, new int[] { Item.getId(itemstack.getItem()), itemstack.getData() });
/*      */         } else {
/*  280 */           this.world.addParticle(EnumParticle.ITEM_CRACK, vec3d1.a, vec3d1.b, vec3d1.c, vec3d.a, vec3d.b + 0.05D, vec3d.c, new int[] { Item.getId(itemstack.getItem()) });
/*      */         }
/*      */       }
/*      */       
/*  284 */       makeSound("random.eat", 0.5F + 0.5F * this.random.nextInt(2), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void s()
/*      */   {
/*  290 */     if (this.g != null) {
/*  291 */       b(this.g, 16);
/*  292 */       int i = this.g.count;
/*      */       
/*      */ 
/*  295 */       org.bukkit.inventory.ItemStack craftItem = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asBukkitCopy(this.g);
/*  296 */       PlayerItemConsumeEvent event = new PlayerItemConsumeEvent((Player)getBukkitEntity(), craftItem);
/*  297 */       this.world.getServer().getPluginManager().callEvent(event);
/*      */       
/*  299 */       if (event.isCancelled())
/*      */       {
/*  301 */         if ((this instanceof EntityPlayer)) {
/*  302 */           ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, this.activeContainer.getSlot(this.inventory, this.inventory.itemInHandIndex).index, this.g));
/*      */           
/*  304 */           ((EntityPlayer)this).getBukkitEntity().updateInventory();
/*  305 */           ((EntityPlayer)this).getBukkitEntity().updateScaledHealth();
/*      */         }
/*      */         
/*  308 */         return;
/*      */       }
/*      */       
/*      */ 
/*  312 */       if (!craftItem.equals(event.getItem())) {
/*  313 */         org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(event.getItem()).b(this.world, this);
/*      */         
/*      */ 
/*  316 */         if ((this instanceof EntityPlayer)) {
/*  317 */           ((EntityPlayer)this).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, this.activeContainer.getSlot(this.inventory, this.inventory.itemInHandIndex).index, this.g));
/*      */         }
/*  319 */         return;
/*      */       }
/*      */       
/*      */ 
/*  323 */       ItemStack itemstack = this.g.b(this.world, this);
/*      */       
/*  325 */       if ((itemstack != this.g) || ((itemstack != null) && (itemstack.count != i))) {
/*  326 */         this.inventory.items[this.inventory.itemInHandIndex] = itemstack;
/*  327 */         if (itemstack.count == 0) {
/*  328 */           this.inventory.items[this.inventory.itemInHandIndex] = null;
/*      */         }
/*      */       }
/*      */       
/*  332 */       bV();
/*      */     }
/*      */   }
/*      */   
/*      */   protected boolean bD()
/*      */   {
/*  338 */     return (getHealth() <= 0.0F) || (isSleeping());
/*      */   }
/*      */   
/*      */   public void closeInventory() {
/*  342 */     this.activeContainer = this.defaultContainer;
/*      */   }
/*      */   
/*      */   public void ak() {
/*  346 */     if ((!this.world.isClientSide) && (isSneaking())) {
/*  347 */       mount(null);
/*  348 */       setSneaking(false);
/*      */     } else {
/*  350 */       double d0 = this.locX;
/*  351 */       double d1 = this.locY;
/*  352 */       double d2 = this.locZ;
/*  353 */       float f = this.yaw;
/*  354 */       float f1 = this.pitch;
/*      */       
/*  356 */       super.ak();
/*  357 */       this.bn = this.bo;
/*  358 */       this.bo = 0.0F;
/*  359 */       l(this.locX - d0, this.locY - d1, this.locZ - d2);
/*  360 */       if ((this.vehicle instanceof EntityPig)) {
/*  361 */         this.pitch = f1;
/*  362 */         this.yaw = f;
/*  363 */         this.aI = ((EntityPig)this.vehicle).aI;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void doTick()
/*      */   {
/*  370 */     super.doTick();
/*  371 */     bx();
/*  372 */     this.aK = this.yaw;
/*      */   }
/*      */   
/*      */   public void m() {
/*  376 */     if (this.bm > 0) {
/*  377 */       this.bm -= 1;
/*      */     }
/*      */     
/*  380 */     if ((this.world.getDifficulty() == EnumDifficulty.PEACEFUL) && (this.world.getGameRules().getBoolean("naturalRegeneration"))) {
/*  381 */       if ((getHealth() < getMaxHealth()) && (this.ticksLived % 20 == 0))
/*      */       {
/*  383 */         heal(1.0F, org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason.REGEN);
/*      */       }
/*      */       
/*  386 */       if ((this.foodData.c()) && (this.ticksLived % 10 == 0)) {
/*  387 */         this.foodData.a(this.foodData.getFoodLevel() + 1);
/*      */       }
/*      */     }
/*      */     
/*  391 */     this.inventory.k();
/*  392 */     this.bn = this.bo;
/*  393 */     super.m();
/*  394 */     AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
/*      */     
/*  396 */     if (!this.world.isClientSide) {
/*  397 */       attributeinstance.setValue(this.abilities.b());
/*      */     }
/*      */     
/*  400 */     this.aM = this.bF;
/*  401 */     if (isSprinting()) {
/*  402 */       this.aM = ((float)(this.aM + this.bF * 0.3D));
/*      */     }
/*      */     
/*  405 */     k((float)attributeinstance.getValue());
/*  406 */     float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*  407 */     float f1 = (float)(org.bukkit.craftbukkit.v1_8_R3.TrigMath.atan(-this.motY * 0.20000000298023224D) * 15.0D);
/*      */     
/*  409 */     if (f > 0.1F) {
/*  410 */       f = 0.1F;
/*      */     }
/*      */     
/*  413 */     if ((!this.onGround) || (getHealth() <= 0.0F)) {
/*  414 */       f = 0.0F;
/*      */     }
/*      */     
/*  417 */     if ((this.onGround) || (getHealth() <= 0.0F)) {
/*  418 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  421 */     this.bo += (f - this.bo) * 0.4F;
/*  422 */     this.aF += (f1 - this.aF) * 0.8F;
/*  423 */     if ((getHealth() > 0.0F) && (!isSpectator())) {
/*  424 */       AxisAlignedBB axisalignedbb = null;
/*      */       
/*  426 */       if ((this.vehicle != null) && (!this.vehicle.dead)) {
/*  427 */         axisalignedbb = getBoundingBox().a(this.vehicle.getBoundingBox()).grow(1.0D, 0.0D, 1.0D);
/*      */       } else {
/*  429 */         axisalignedbb = getBoundingBox().grow(1.0D, 0.5D, 1.0D);
/*      */       }
/*      */       
/*  432 */       List list = this.world.getEntities(this, axisalignedbb);
/*      */       
/*  434 */       if (ae()) {
/*  435 */         for (int i = 0; i < list.size(); i++) {
/*  436 */           Entity entity = (Entity)list.get(i);
/*      */           
/*  438 */           if (!entity.dead) {
/*  439 */             d(entity);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void d(Entity entity)
/*      */   {
/*  448 */     entity.d(this);
/*      */   }
/*      */   
/*      */   public int getScore() {
/*  452 */     return this.datawatcher.getInt(18);
/*      */   }
/*      */   
/*      */   public void setScore(int i) {
/*  456 */     this.datawatcher.watch(18, Integer.valueOf(i));
/*      */   }
/*      */   
/*      */   public void addScore(int i) {
/*  460 */     int j = getScore();
/*      */     
/*  462 */     this.datawatcher.watch(18, Integer.valueOf(j + i));
/*      */   }
/*      */   
/*      */   public void die(DamageSource damagesource) {
/*  466 */     super.die(damagesource);
/*  467 */     setSize(0.2F, 0.2F);
/*  468 */     setPosition(this.locX, this.locY, this.locZ);
/*  469 */     this.motY = 0.10000000149011612D;
/*  470 */     if (getName().equals("Notch")) {
/*  471 */       a(new ItemStack(Items.APPLE, 1), true, false);
/*      */     }
/*      */     
/*  474 */     if (!this.world.getGameRules().getBoolean("keepInventory")) {
/*  475 */       this.inventory.n();
/*      */     }
/*      */     
/*  478 */     if (damagesource != null) {
/*  479 */       this.motX = (-MathHelper.cos((this.aw + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
/*  480 */       this.motZ = (-MathHelper.sin((this.aw + this.yaw) * 3.1415927F / 180.0F) * 0.1F);
/*      */     } else {
/*  482 */       this.motX = (this.motZ = 0.0D);
/*      */     }
/*      */     
/*  485 */     b(StatisticList.y);
/*  486 */     a(StatisticList.h);
/*      */   }
/*      */   
/*      */   protected String bo() {
/*  490 */     return "game.player.hurt";
/*      */   }
/*      */   
/*      */   protected String bp() {
/*  494 */     return "game.player.die";
/*      */   }
/*      */   
/*      */   public void b(Entity entity, int i) {
/*  498 */     addScore(i);
/*      */     
/*  500 */     Collection<ScoreboardScore> collection = this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.f, getName(), new java.util.ArrayList());
/*      */     
/*  502 */     if ((entity instanceof EntityHuman)) {
/*  503 */       b(StatisticList.B);
/*      */       
/*  505 */       this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.e, getName(), collection);
/*  506 */       collection.addAll(e(entity));
/*      */     } else {
/*  508 */       b(StatisticList.z);
/*      */     }
/*      */     
/*  511 */     Iterator iterator = collection.iterator();
/*      */     
/*  513 */     while (iterator.hasNext()) {
/*  514 */       ScoreboardScore scoreboardscore = (ScoreboardScore)iterator.next();
/*      */       
/*  516 */       scoreboardscore.incrementScore();
/*      */     }
/*      */   }
/*      */   
/*      */   private Collection e(Entity entity)
/*      */   {
/*  522 */     ScoreboardTeam scoreboardteam = getScoreboard().getPlayerTeam(getName());
/*      */     
/*  524 */     if (scoreboardteam != null) {
/*  525 */       int i = scoreboardteam.l().b();
/*      */       
/*  527 */       if ((i >= 0) && (i < IScoreboardCriteria.i.length)) {
/*  528 */         Iterator iterator = getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.i[i]).iterator();
/*      */         
/*  530 */         while (iterator.hasNext()) {
/*  531 */           ScoreboardObjective scoreboardobjective = (ScoreboardObjective)iterator.next();
/*  532 */           ScoreboardScore scoreboardscore = getScoreboard().getPlayerScoreForObjective(entity.getName(), scoreboardobjective);
/*      */           
/*  534 */           scoreboardscore.incrementScore();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  539 */     ScoreboardTeam scoreboardteam1 = getScoreboard().getPlayerTeam(entity.getName());
/*      */     
/*  541 */     if (scoreboardteam1 != null) {
/*  542 */       int j = scoreboardteam1.l().b();
/*      */       
/*  544 */       if ((j >= 0) && (j < IScoreboardCriteria.h.length)) {
/*  545 */         return getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.h[j]);
/*      */       }
/*      */     }
/*      */     
/*  549 */     return com.google.common.collect.Lists.newArrayList();
/*      */   }
/*      */   
/*      */   public EntityItem a(boolean flag)
/*      */   {
/*  554 */     return a(this.inventory.splitStack(this.inventory.itemInHandIndex, (flag) && (this.inventory.getItemInHand() != null) ? this.inventory.getItemInHand().count : 1), false, true);
/*      */   }
/*      */   
/*      */   public EntityItem drop(ItemStack itemstack, boolean flag) {
/*  558 */     return a(itemstack, false, false);
/*      */   }
/*      */   
/*      */   public EntityItem a(ItemStack itemstack, boolean flag, boolean flag1) {
/*  562 */     if (itemstack == null)
/*  563 */       return null;
/*  564 */     if (itemstack.count == 0) {
/*  565 */       return null;
/*      */     }
/*  567 */     double d0 = this.locY - 0.30000001192092896D + getHeadHeight();
/*  568 */     EntityItem entityitem = new EntityItem(this.world, this.locX, d0, this.locZ, itemstack);
/*      */     
/*  570 */     entityitem.a(40);
/*  571 */     if (flag1) {
/*  572 */       entityitem.c(getName());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  578 */     if (flag) {
/*  579 */       float f = this.random.nextFloat() * 0.5F;
/*  580 */       float f1 = this.random.nextFloat() * 3.1415927F * 2.0F;
/*  581 */       entityitem.motX = (-MathHelper.sin(f1) * f);
/*  582 */       entityitem.motZ = (MathHelper.cos(f1) * f);
/*  583 */       entityitem.motY = 0.20000000298023224D;
/*      */     } else {
/*  585 */       float f = 0.3F;
/*  586 */       entityitem.motX = (-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  587 */       entityitem.motZ = (MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F) * f);
/*  588 */       entityitem.motY = (-MathHelper.sin(this.pitch / 180.0F * 3.1415927F) * f + 0.1F);
/*  589 */       float f1 = this.random.nextFloat() * 3.1415927F * 2.0F;
/*  590 */       f = 0.02F * this.random.nextFloat();
/*  591 */       entityitem.motX += Math.cos(f1) * f;
/*  592 */       entityitem.motY += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
/*  593 */       entityitem.motZ += Math.sin(f1) * f;
/*      */     }
/*      */     
/*      */ 
/*  597 */     Player player = (Player)getBukkitEntity();
/*  598 */     CraftItem drop = new CraftItem(this.world.getServer(), entityitem);
/*      */     
/*  600 */     PlayerDropItemEvent event = new PlayerDropItemEvent(player, drop);
/*  601 */     this.world.getServer().getPluginManager().callEvent(event);
/*      */     
/*  603 */     if (event.isCancelled()) {
/*  604 */       org.bukkit.inventory.ItemStack cur = player.getInventory().getItemInHand();
/*  605 */       if ((flag1) && ((cur == null) || (cur.getAmount() == 0)))
/*      */       {
/*  607 */         player.getInventory().setItemInHand(drop.getItemStack());
/*  608 */       } else if ((flag1) && (cur.isSimilar(drop.getItemStack())) && (drop.getItemStack().getAmount() == 1))
/*      */       {
/*  610 */         cur.setAmount(cur.getAmount() + 1);
/*  611 */         player.getInventory().setItemInHand(cur);
/*      */       }
/*      */       else {
/*  614 */         player.getInventory().addItem(new org.bukkit.inventory.ItemStack[] { drop.getItemStack() });
/*      */       }
/*  616 */       return null;
/*      */     }
/*      */     
/*      */ 
/*  620 */     a(entityitem);
/*  621 */     if (flag1) {
/*  622 */       b(StatisticList.v);
/*      */     }
/*      */     
/*  625 */     return entityitem;
/*      */   }
/*      */   
/*      */   protected void a(EntityItem entityitem)
/*      */   {
/*  630 */     this.world.addEntity(entityitem);
/*      */   }
/*      */   
/*      */   public float a(Block block) {
/*  634 */     float f = this.inventory.a(block);
/*      */     
/*  636 */     if (f > 1.0F) {
/*  637 */       int i = EnchantmentManager.getDigSpeedEnchantmentLevel(this);
/*  638 */       ItemStack itemstack = this.inventory.getItemInHand();
/*      */       
/*  640 */       if ((i > 0) && (itemstack != null)) {
/*  641 */         f += i * i + 1;
/*      */       }
/*      */     }
/*      */     
/*  645 */     if (hasEffect(MobEffectList.FASTER_DIG)) {
/*  646 */       f *= (1.0F + (getEffect(MobEffectList.FASTER_DIG).getAmplifier() + 1) * 0.2F);
/*      */     }
/*      */     
/*  649 */     if (hasEffect(MobEffectList.SLOWER_DIG)) {
/*  650 */       float f1 = 1.0F;
/*      */       
/*  652 */       switch (getEffect(MobEffectList.SLOWER_DIG).getAmplifier()) {
/*      */       case 0: 
/*  654 */         f1 = 0.3F;
/*  655 */         break;
/*      */       
/*      */       case 1: 
/*  658 */         f1 = 0.09F;
/*  659 */         break;
/*      */       
/*      */       case 2: 
/*  662 */         f1 = 0.0027F;
/*  663 */         break;
/*      */       
/*      */       case 3: 
/*      */       default: 
/*  667 */         f1 = 8.1E-4F;
/*      */       }
/*      */       
/*  670 */       f *= f1;
/*      */     }
/*      */     
/*  673 */     if ((a(Material.WATER)) && (!EnchantmentManager.j(this))) {
/*  674 */       f /= 5.0F;
/*      */     }
/*      */     
/*  677 */     if (!this.onGround) {
/*  678 */       f /= 5.0F;
/*      */     }
/*      */     
/*  681 */     return f;
/*      */   }
/*      */   
/*      */   public boolean b(Block block) {
/*  685 */     return this.inventory.b(block);
/*      */   }
/*      */   
/*      */   public void a(NBTTagCompound nbttagcompound) {
/*  689 */     super.a(nbttagcompound);
/*  690 */     this.uniqueID = a(this.bH);
/*  691 */     NBTTagList nbttaglist = nbttagcompound.getList("Inventory", 10);
/*      */     
/*  693 */     this.inventory.b(nbttaglist);
/*  694 */     this.inventory.itemInHandIndex = nbttagcompound.getInt("SelectedItemSlot");
/*  695 */     this.sleeping = nbttagcompound.getBoolean("Sleeping");
/*  696 */     this.sleepTicks = nbttagcompound.getShort("SleepTimer");
/*  697 */     this.exp = nbttagcompound.getFloat("XpP");
/*  698 */     this.expLevel = nbttagcompound.getInt("XpLevel");
/*  699 */     this.expTotal = nbttagcompound.getInt("XpTotal");
/*  700 */     this.f = nbttagcompound.getInt("XpSeed");
/*  701 */     if (this.f == 0) {
/*  702 */       this.f = this.random.nextInt();
/*      */     }
/*      */     
/*  705 */     setScore(nbttagcompound.getInt("Score"));
/*  706 */     if (this.sleeping) {
/*  707 */       this.bx = new BlockPosition(this);
/*  708 */       a(true, true, false);
/*      */     }
/*      */     
/*      */ 
/*  712 */     this.spawnWorld = nbttagcompound.getString("SpawnWorld");
/*  713 */     if ("".equals(this.spawnWorld)) {
/*  714 */       this.spawnWorld = ((org.bukkit.World)this.world.getServer().getWorlds().get(0)).getName();
/*      */     }
/*      */     
/*      */ 
/*  718 */     if ((nbttagcompound.hasKeyOfType("SpawnX", 99)) && (nbttagcompound.hasKeyOfType("SpawnY", 99)) && (nbttagcompound.hasKeyOfType("SpawnZ", 99))) {
/*  719 */       this.c = new BlockPosition(nbttagcompound.getInt("SpawnX"), nbttagcompound.getInt("SpawnY"), nbttagcompound.getInt("SpawnZ"));
/*  720 */       this.d = nbttagcompound.getBoolean("SpawnForced");
/*      */     }
/*      */     
/*  723 */     this.foodData.a(nbttagcompound);
/*  724 */     this.abilities.b(nbttagcompound);
/*  725 */     if (nbttagcompound.hasKeyOfType("EnderItems", 9)) {
/*  726 */       NBTTagList nbttaglist1 = nbttagcompound.getList("EnderItems", 10);
/*      */       
/*  728 */       this.enderChest.a(nbttaglist1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void b(NBTTagCompound nbttagcompound)
/*      */   {
/*  734 */     super.b(nbttagcompound);
/*  735 */     nbttagcompound.set("Inventory", this.inventory.a(new NBTTagList()));
/*  736 */     nbttagcompound.setInt("SelectedItemSlot", this.inventory.itemInHandIndex);
/*  737 */     nbttagcompound.setBoolean("Sleeping", this.sleeping);
/*  738 */     nbttagcompound.setShort("SleepTimer", (short)this.sleepTicks);
/*  739 */     nbttagcompound.setFloat("XpP", this.exp);
/*  740 */     nbttagcompound.setInt("XpLevel", this.expLevel);
/*  741 */     nbttagcompound.setInt("XpTotal", this.expTotal);
/*  742 */     nbttagcompound.setInt("XpSeed", this.f);
/*  743 */     nbttagcompound.setInt("Score", getScore());
/*  744 */     if (this.c != null) {
/*  745 */       nbttagcompound.setInt("SpawnX", this.c.getX());
/*  746 */       nbttagcompound.setInt("SpawnY", this.c.getY());
/*  747 */       nbttagcompound.setInt("SpawnZ", this.c.getZ());
/*  748 */       nbttagcompound.setBoolean("SpawnForced", this.d);
/*      */     }
/*      */     
/*  751 */     this.foodData.b(nbttagcompound);
/*  752 */     this.abilities.a(nbttagcompound);
/*  753 */     nbttagcompound.set("EnderItems", this.enderChest.h());
/*  754 */     ItemStack itemstack = this.inventory.getItemInHand();
/*      */     
/*  756 */     if ((itemstack != null) && (itemstack.getItem() != null)) {
/*  757 */       nbttagcompound.set("SelectedItem", itemstack.save(new NBTTagCompound()));
/*      */     }
/*  759 */     nbttagcompound.setString("SpawnWorld", this.spawnWorld);
/*      */   }
/*      */   
/*      */   public boolean damageEntity(DamageSource damagesource, float f)
/*      */   {
/*  764 */     if (isInvulnerable(damagesource))
/*  765 */       return false;
/*  766 */     if ((this.abilities.isInvulnerable) && (!damagesource.ignoresInvulnerability())) {
/*  767 */       return false;
/*      */     }
/*  769 */     this.ticksFarFromPlayer = 0;
/*  770 */     if (getHealth() <= 0.0F) {
/*  771 */       return false;
/*      */     }
/*  773 */     if ((isSleeping()) && (!this.world.isClientSide)) {
/*  774 */       a(true, true, false);
/*      */     }
/*      */     
/*  777 */     if (damagesource.r()) {
/*  778 */       if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
/*  779 */         return false;
/*      */       }
/*      */       
/*  782 */       if (this.world.getDifficulty() == EnumDifficulty.EASY) {
/*  783 */         f = f / 2.0F + 1.0F;
/*      */       }
/*      */       
/*  786 */       if (this.world.getDifficulty() == EnumDifficulty.HARD) {
/*  787 */         f = f * 3.0F / 2.0F;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  794 */     Entity entity = damagesource.getEntity();
/*      */     
/*  796 */     if (((entity instanceof EntityArrow)) && (((EntityArrow)entity).shooter != null)) {
/*  797 */       entity = ((EntityArrow)entity).shooter;
/*      */     }
/*      */     
/*  800 */     return super.damageEntity(damagesource, f);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public boolean a(EntityHuman entityhuman)
/*      */   {
/*      */     Team team;
/*      */     
/*      */ 
/*  810 */     if ((entityhuman instanceof EntityPlayer)) {
/*  811 */       EntityPlayer thatPlayer = (EntityPlayer)entityhuman;
/*  812 */       Team team = thatPlayer.getBukkitEntity().getScoreboard().getPlayerTeam(thatPlayer.getBukkitEntity());
/*  813 */       if ((team == null) || (team.allowFriendlyFire())) {
/*  814 */         return true;
/*      */       }
/*      */     }
/*      */     else {
/*  818 */       org.bukkit.OfflinePlayer thisPlayer = entityhuman.world.getServer().getOfflinePlayer(entityhuman.getName());
/*  819 */       team = entityhuman.world.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(thisPlayer);
/*  820 */       if ((team == null) || (team.allowFriendlyFire())) {
/*  821 */         return true;
/*      */       }
/*      */     }
/*      */     
/*  825 */     if ((this instanceof EntityPlayer)) {
/*  826 */       return !team.hasPlayer(((EntityPlayer)this).getBukkitEntity());
/*      */     }
/*  828 */     return !team.hasPlayer(this.world.getServer().getOfflinePlayer(getName()));
/*      */   }
/*      */   
/*      */   protected void damageArmor(float f)
/*      */   {
/*  833 */     this.inventory.a(f);
/*      */   }
/*      */   
/*      */   public int br() {
/*  837 */     return this.inventory.m();
/*      */   }
/*      */   
/*      */   public float bY() {
/*  841 */     int i = 0;
/*  842 */     ItemStack[] aitemstack = this.inventory.armor;
/*  843 */     int j = aitemstack.length;
/*      */     
/*  845 */     for (int k = 0; k < j; k++) {
/*  846 */       ItemStack itemstack = aitemstack[k];
/*      */       
/*  848 */       if (itemstack != null) {
/*  849 */         i++;
/*      */       }
/*      */     }
/*      */     
/*  853 */     return i / this.inventory.armor.length;
/*      */   }
/*      */   
/*      */ 
/*      */   protected boolean d(DamageSource damagesource, float f)
/*      */   {
/*  859 */     return super.d(damagesource, f);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void openSign(TileEntitySign tileentitysign) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void a(CommandBlockListenerAbstract commandblocklistenerabstract) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void openTrade(IMerchant imerchant) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void openContainer(IInventory iinventory) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void openHorseInventory(EntityHorse entityhorse, IInventory iinventory) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void openTileEntity(ITileEntityContainer itileentitycontainer) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void openBook(ItemStack itemstack) {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean u(Entity entity)
/*      */   {
/*  903 */     if (isSpectator()) {
/*  904 */       if ((entity instanceof IInventory)) {
/*  905 */         openContainer((IInventory)entity);
/*      */       }
/*      */       
/*  908 */       return false;
/*      */     }
/*  910 */     ItemStack itemstack = bZ();
/*  911 */     ItemStack itemstack1 = itemstack != null ? itemstack.cloneItemStack() : null;
/*      */     
/*  913 */     if (!entity.e(this)) {
/*  914 */       if ((itemstack != null) && ((entity instanceof EntityLiving))) {
/*  915 */         if (this.abilities.canInstantlyBuild) {
/*  916 */           itemstack = itemstack1;
/*      */         }
/*      */         
/*  919 */         if (itemstack.a(this, (EntityLiving)entity))
/*      */         {
/*  921 */           if ((itemstack.count == 0) && (!this.abilities.canInstantlyBuild)) {
/*  922 */             ca();
/*      */           }
/*      */           
/*  925 */           return true;
/*      */         }
/*      */       }
/*      */       
/*  929 */       return false;
/*      */     }
/*  931 */     if ((itemstack != null) && (itemstack == bZ())) {
/*  932 */       if ((itemstack.count <= 0) && (!this.abilities.canInstantlyBuild)) {
/*  933 */         ca();
/*  934 */       } else if ((itemstack.count < itemstack1.count) && (this.abilities.canInstantlyBuild)) {
/*  935 */         itemstack.count = itemstack1.count;
/*      */       }
/*      */     }
/*      */     
/*  939 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   public ItemStack bZ()
/*      */   {
/*  945 */     return this.inventory.getItemInHand();
/*      */   }
/*      */   
/*      */   public void ca() {
/*  949 */     this.inventory.setItem(this.inventory.itemInHandIndex, null);
/*      */   }
/*      */   
/*      */   public double am() {
/*  953 */     return -0.35D;
/*      */   }
/*      */   
/*      */   public void attack(Entity entity) {
/*  957 */     if ((entity.aD()) && 
/*  958 */       (!entity.l(this))) {
/*  959 */       float f = (float)getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
/*  960 */       byte b0 = 0;
/*  961 */       float f1 = 0.0F;
/*      */       
/*  963 */       if ((entity instanceof EntityLiving)) {
/*  964 */         f1 = EnchantmentManager.a(bA(), ((EntityLiving)entity).getMonsterType());
/*      */       } else {
/*  966 */         f1 = EnchantmentManager.a(bA(), EnumMonsterType.UNDEFINED);
/*      */       }
/*      */       
/*  969 */       int i = b0 + EnchantmentManager.a(this);
/*      */       
/*  971 */       if (isSprinting()) {
/*  972 */         i++;
/*      */       }
/*      */       
/*  975 */       if ((f > 0.0F) || (f1 > 0.0F)) {
/*  976 */         boolean flag = (this.fallDistance > 0.0F) && (!this.onGround) && (!k_()) && (!V()) && (!hasEffect(MobEffectList.BLINDNESS)) && (this.vehicle == null) && ((entity instanceof EntityLiving));
/*      */         
/*  978 */         if ((flag) && (f > 0.0F)) {
/*  979 */           f *= 1.5F;
/*      */         }
/*      */         
/*  982 */         f += f1;
/*  983 */         boolean flag1 = false;
/*  984 */         int j = EnchantmentManager.getFireAspectEnchantmentLevel(this);
/*      */         
/*  986 */         if (((entity instanceof EntityLiving)) && (j > 0) && (!entity.isBurning()))
/*      */         {
/*  988 */           EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), 1);
/*  989 */           org.bukkit.Bukkit.getPluginManager().callEvent(combustEvent);
/*      */           
/*  991 */           if (!combustEvent.isCancelled()) {
/*  992 */             flag1 = true;
/*  993 */             entity.setOnFire(combustEvent.getDuration());
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  998 */         double d0 = entity.motX;
/*  999 */         double d1 = entity.motY;
/* 1000 */         double d2 = entity.motZ;
/* 1001 */         boolean flag2 = entity.damageEntity(DamageSource.playerAttack(this), f);
/*      */         
/* 1003 */         if (flag2) {
/* 1004 */           if (i > 0) {
/* 1005 */             entity.g(-MathHelper.sin(this.yaw * 3.1415927F / 180.0F) * i * 0.5F, 0.1D, MathHelper.cos(this.yaw * 3.1415927F / 180.0F) * i * 0.5F);
/* 1006 */             this.motX *= 0.6D;
/* 1007 */             this.motZ *= 0.6D;
/* 1008 */             setSprinting(false);
/*      */           }
/*      */           
/* 1011 */           if (((entity instanceof EntityPlayer)) && (entity.velocityChanged))
/*      */           {
/* 1013 */             boolean cancelled = false;
/* 1014 */             Player player = (Player)entity.getBukkitEntity();
/* 1015 */             Vector velocity = new Vector(d0, d1, d2);
/*      */             
/* 1017 */             PlayerVelocityEvent event = new PlayerVelocityEvent(player, velocity.clone());
/* 1018 */             this.world.getServer().getPluginManager().callEvent(event);
/*      */             
/* 1020 */             if (event.isCancelled()) {
/* 1021 */               cancelled = true;
/* 1022 */             } else if (!velocity.equals(event.getVelocity())) {
/* 1023 */               player.setVelocity(event.getVelocity());
/*      */             }
/*      */             
/* 1026 */             if (!cancelled) {
/* 1027 */               ((EntityPlayer)entity).playerConnection.sendPacket(new PacketPlayOutEntityVelocity(entity));
/* 1028 */               entity.velocityChanged = false;
/* 1029 */               entity.motX = d0;
/* 1030 */               entity.motY = d1;
/* 1031 */               entity.motZ = d2;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1036 */           if (flag) {
/* 1037 */             b(entity);
/*      */           }
/*      */           
/* 1040 */           if (f1 > 0.0F) {
/* 1041 */             c(entity);
/*      */           }
/*      */           
/* 1044 */           if (f >= 18.0F) {
/* 1045 */             b(AchievementList.F);
/*      */           }
/*      */           
/* 1048 */           p(entity);
/* 1049 */           if ((entity instanceof EntityLiving)) {
/* 1050 */             EnchantmentManager.a((EntityLiving)entity, this);
/*      */           }
/*      */           
/* 1053 */           EnchantmentManager.b(this, entity);
/* 1054 */           ItemStack itemstack = bZ();
/* 1055 */           Object object = entity;
/*      */           
/* 1057 */           if ((entity instanceof EntityComplexPart)) {
/* 1058 */             IComplex icomplex = ((EntityComplexPart)entity).owner;
/*      */             
/* 1060 */             if ((icomplex instanceof EntityLiving)) {
/* 1061 */               object = (EntityLiving)icomplex;
/*      */             }
/*      */           }
/*      */           
/* 1065 */           if ((itemstack != null) && ((object instanceof EntityLiving))) {
/* 1066 */             itemstack.a((EntityLiving)object, this);
/*      */             
/* 1068 */             if (itemstack.count == 0) {
/* 1069 */               ca();
/*      */             }
/*      */           }
/*      */           
/* 1073 */           if ((entity instanceof EntityLiving)) {
/* 1074 */             a(StatisticList.w, Math.round(f * 10.0F));
/* 1075 */             if (j > 0)
/*      */             {
/* 1077 */               EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(getBukkitEntity(), entity.getBukkitEntity(), j * 4);
/* 1078 */               org.bukkit.Bukkit.getPluginManager().callEvent(combustEvent);
/*      */               
/* 1080 */               if (!combustEvent.isCancelled()) {
/* 1081 */                 entity.setOnFire(combustEvent.getDuration());
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/* 1087 */           applyExhaustion(this.world.spigotConfig.combatExhaustion);
/* 1088 */         } else if (flag1) {
/* 1089 */           entity.extinguish();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void b(Entity entity) {}
/*      */   
/*      */   public void c(Entity entity) {}
/*      */   
/*      */   public void die()
/*      */   {
/* 1102 */     super.die();
/* 1103 */     this.defaultContainer.b(this);
/* 1104 */     if (this.activeContainer != null) {
/* 1105 */       this.activeContainer.b(this);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean inBlock()
/*      */   {
/* 1111 */     return (!this.sleeping) && (super.inBlock());
/*      */   }
/*      */   
/*      */   public GameProfile getProfile() {
/* 1115 */     return this.bH;
/*      */   }
/*      */   
/*      */   public EnumBedResult a(BlockPosition blockposition) {
/* 1119 */     if (!this.world.isClientSide) {
/* 1120 */       if ((isSleeping()) || (!isAlive())) {
/* 1121 */         return EnumBedResult.OTHER_PROBLEM;
/*      */       }
/*      */       
/* 1124 */       if (!this.world.worldProvider.d()) {
/* 1125 */         return EnumBedResult.NOT_POSSIBLE_HERE;
/*      */       }
/*      */       
/* 1128 */       if (this.world.w()) {
/* 1129 */         return EnumBedResult.NOT_POSSIBLE_NOW;
/*      */       }
/*      */       
/* 1132 */       if ((Math.abs(this.locX - blockposition.getX()) > 3.0D) || (Math.abs(this.locY - blockposition.getY()) > 2.0D) || (Math.abs(this.locZ - blockposition.getZ()) > 3.0D)) {
/* 1133 */         return EnumBedResult.TOO_FAR_AWAY;
/*      */       }
/*      */       
/* 1136 */       double d0 = 8.0D;
/* 1137 */       double d1 = 5.0D;
/* 1138 */       List list = this.world.a(EntityMonster.class, new AxisAlignedBB(blockposition.getX() - d0, blockposition.getY() - d1, blockposition.getZ() - d0, blockposition.getX() + d0, blockposition.getY() + d1, blockposition.getZ() + d0));
/*      */       
/* 1140 */       if (!list.isEmpty()) {
/* 1141 */         return EnumBedResult.NOT_SAFE;
/*      */       }
/*      */     }
/*      */     
/* 1145 */     if (au()) {
/* 1146 */       mount(null);
/*      */     }
/*      */     
/*      */ 
/* 1150 */     if ((getBukkitEntity() instanceof Player)) {
/* 1151 */       Player player = (Player)getBukkitEntity();
/* 1152 */       org.bukkit.block.Block bed = this.world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*      */       
/* 1154 */       PlayerBedEnterEvent event = new PlayerBedEnterEvent(player, bed);
/* 1155 */       this.world.getServer().getPluginManager().callEvent(event);
/*      */       
/* 1157 */       if (event.isCancelled()) {
/* 1158 */         return EnumBedResult.OTHER_PROBLEM;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1163 */     setSize(0.2F, 0.2F);
/* 1164 */     if (this.world.isLoaded(blockposition)) {
/* 1165 */       EnumDirection enumdirection = (EnumDirection)this.world.getType(blockposition).get(BlockDirectional.FACING);
/* 1166 */       float f = 0.5F;
/* 1167 */       float f1 = 0.5F;
/*      */       
/* 1169 */       switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*      */       case 1: 
/* 1171 */         f1 = 0.9F;
/* 1172 */         break;
/*      */       
/*      */       case 2: 
/* 1175 */         f1 = 0.1F;
/* 1176 */         break;
/*      */       
/*      */       case 3: 
/* 1179 */         f = 0.1F;
/* 1180 */         break;
/*      */       
/*      */       case 4: 
/* 1183 */         f = 0.9F;
/*      */       }
/*      */       
/* 1186 */       a(enumdirection);
/* 1187 */       setPosition(blockposition.getX() + f, blockposition.getY() + 0.6875F, blockposition.getZ() + f1);
/*      */     } else {
/* 1189 */       setPosition(blockposition.getX() + 0.5F, blockposition.getY() + 0.6875F, blockposition.getZ() + 0.5F);
/*      */     }
/*      */     
/* 1192 */     this.sleeping = true;
/* 1193 */     this.sleepTicks = 0;
/* 1194 */     this.bx = blockposition;
/* 1195 */     this.motX = (this.motZ = this.motY = 0.0D);
/* 1196 */     if (!this.world.isClientSide) {
/* 1197 */       this.world.everyoneSleeping();
/*      */     }
/*      */     
/* 1200 */     return EnumBedResult.OK;
/*      */   }
/*      */   
/*      */   private void a(EnumDirection enumdirection) {
/* 1204 */     this.by = 0.0F;
/* 1205 */     this.bz = 0.0F;
/* 1206 */     switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*      */     case 1: 
/* 1208 */       this.bz = -1.8F;
/* 1209 */       break;
/*      */     
/*      */     case 2: 
/* 1212 */       this.bz = 1.8F;
/* 1213 */       break;
/*      */     
/*      */     case 3: 
/* 1216 */       this.by = 1.8F;
/* 1217 */       break;
/*      */     
/*      */     case 4: 
/* 1220 */       this.by = -1.8F;
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(boolean flag, boolean flag1, boolean flag2)
/*      */   {
/* 1226 */     setSize(0.6F, 1.8F);
/* 1227 */     IBlockData iblockdata = this.world.getType(this.bx);
/*      */     
/* 1229 */     if ((this.bx != null) && (iblockdata.getBlock() == Blocks.BED)) {
/* 1230 */       this.world.setTypeAndData(this.bx, iblockdata.set(BlockBed.OCCUPIED, Boolean.valueOf(false)), 4);
/* 1231 */       BlockPosition blockposition = BlockBed.a(this.world, this.bx, 0);
/*      */       
/* 1233 */       if (blockposition == null) {
/* 1234 */         blockposition = this.bx.up();
/*      */       }
/*      */       
/* 1237 */       setPosition(blockposition.getX() + 0.5F, blockposition.getY() + 0.1F, blockposition.getZ() + 0.5F);
/*      */     }
/*      */     
/* 1240 */     this.sleeping = false;
/* 1241 */     if ((!this.world.isClientSide) && (flag1)) {
/* 1242 */       this.world.everyoneSleeping();
/*      */     }
/*      */     
/*      */ 
/* 1246 */     if ((getBukkitEntity() instanceof Player)) {
/* 1247 */       Player player = (Player)getBukkitEntity();
/*      */       
/*      */ 
/* 1250 */       BlockPosition blockposition = this.bx;
/* 1251 */       org.bukkit.block.Block bed; org.bukkit.block.Block bed; if (blockposition != null) {
/* 1252 */         bed = this.world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*      */       } else {
/* 1254 */         bed = this.world.getWorld().getBlockAt(player.getLocation());
/*      */       }
/*      */       
/* 1257 */       org.bukkit.event.player.PlayerBedLeaveEvent event = new org.bukkit.event.player.PlayerBedLeaveEvent(player, bed);
/* 1258 */       this.world.getServer().getPluginManager().callEvent(event);
/*      */     }
/*      */     
/*      */ 
/* 1262 */     this.sleepTicks = (flag ? 0 : 100);
/* 1263 */     if (flag2) {
/* 1264 */       setRespawnPosition(this.bx, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean p()
/*      */   {
/* 1270 */     return this.world.getType(this.bx).getBlock() == Blocks.BED;
/*      */   }
/*      */   
/*      */   public static BlockPosition getBed(World world, BlockPosition blockposition, boolean flag) {
/* 1274 */     ((ChunkProviderServer)world.chunkProvider).getChunkAt(blockposition.getX() >> 4, blockposition.getZ() >> 4);
/* 1275 */     Block block = world.getType(blockposition).getBlock();
/*      */     
/* 1277 */     if (block != Blocks.BED) {
/* 1278 */       if (!flag) {
/* 1279 */         return null;
/*      */       }
/* 1281 */       boolean flag1 = block.g();
/* 1282 */       boolean flag2 = world.getType(blockposition.up()).getBlock().g();
/*      */       
/* 1284 */       return (flag1) && (flag2) ? blockposition : null;
/*      */     }
/*      */     
/* 1287 */     return BlockBed.a(world, blockposition, 0);
/*      */   }
/*      */   
/*      */   public boolean isSleeping()
/*      */   {
/* 1292 */     return this.sleeping;
/*      */   }
/*      */   
/*      */   public boolean isDeeplySleeping() {
/* 1296 */     return (this.sleeping) && (this.sleepTicks >= 100);
/*      */   }
/*      */   
/*      */   public void b(IChatBaseComponent ichatbasecomponent) {}
/*      */   
/*      */   public BlockPosition getBed() {
/* 1302 */     return this.c;
/*      */   }
/*      */   
/*      */   public boolean isRespawnForced() {
/* 1306 */     return this.d;
/*      */   }
/*      */   
/*      */   public void setRespawnPosition(BlockPosition blockposition, boolean flag) {
/* 1310 */     if (blockposition != null) {
/* 1311 */       this.c = blockposition;
/* 1312 */       this.d = flag;
/* 1313 */       this.spawnWorld = this.world.worldData.getName();
/*      */     } else {
/* 1315 */       this.c = null;
/* 1316 */       this.d = false;
/* 1317 */       this.spawnWorld = "";
/*      */     }
/*      */   }
/*      */   
/*      */   public void b(Statistic statistic)
/*      */   {
/* 1323 */     a(statistic, 1);
/*      */   }
/*      */   
/*      */   public void a(Statistic statistic, int i) {}
/*      */   
/*      */   public void a(Statistic statistic) {}
/*      */   
/*      */   public void bF() {
/* 1331 */     super.bF();
/* 1332 */     b(StatisticList.u);
/* 1333 */     if (isSprinting()) {
/* 1334 */       applyExhaustion(this.world.spigotConfig.sprintExhaustion);
/*      */     } else {
/* 1336 */       applyExhaustion(this.world.spigotConfig.walkExhaustion);
/*      */     }
/*      */   }
/*      */   
/*      */   public void g(float f, float f1)
/*      */   {
/* 1342 */     double d0 = this.locX;
/* 1343 */     double d1 = this.locY;
/* 1344 */     double d2 = this.locZ;
/*      */     
/* 1346 */     if ((this.abilities.isFlying) && (this.vehicle == null)) {
/* 1347 */       double d3 = this.motY;
/* 1348 */       float f2 = this.aM;
/*      */       
/* 1350 */       this.aM = (this.abilities.a() * (isSprinting() ? 2 : 1));
/* 1351 */       super.g(f, f1);
/* 1352 */       this.motY = (d3 * 0.6D);
/* 1353 */       this.aM = f2;
/*      */     } else {
/* 1355 */       super.g(f, f1);
/*      */     }
/*      */     
/* 1358 */     checkMovement(this.locX - d0, this.locY - d1, this.locZ - d2);
/*      */   }
/*      */   
/*      */   public float bI() {
/* 1362 */     return (float)getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue();
/*      */   }
/*      */   
/*      */   public void checkMovement(double d0, double d1, double d2) {
/* 1366 */     if (this.vehicle == null)
/*      */     {
/*      */ 
/* 1369 */       if (a(Material.WATER)) {
/* 1370 */         int i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
/* 1371 */         if (i > 0) {
/* 1372 */           a(StatisticList.p, i);
/* 1373 */           applyExhaustion(0.015F * i * 0.01F);
/*      */         }
/* 1375 */       } else if (V()) {
/* 1376 */         int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
/* 1377 */         if (i > 0) {
/* 1378 */           a(StatisticList.l, i);
/* 1379 */           applyExhaustion(0.015F * i * 0.01F);
/*      */         }
/* 1381 */       } else if (k_()) {
/* 1382 */         if (d1 > 0.0D) {
/* 1383 */           a(StatisticList.n, (int)Math.round(d1 * 100.0D));
/*      */         }
/* 1385 */       } else if (this.onGround) {
/* 1386 */         int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
/* 1387 */         if (i > 0) {
/* 1388 */           a(StatisticList.i, i);
/* 1389 */           if (isSprinting()) {
/* 1390 */             a(StatisticList.k, i);
/* 1391 */             applyExhaustion(0.099999994F * i * 0.01F);
/*      */           } else {
/* 1393 */             if (isSneaking()) {
/* 1394 */               a(StatisticList.j, i);
/*      */             }
/*      */             
/* 1397 */             applyExhaustion(0.01F * i * 0.01F);
/*      */           }
/*      */         }
/*      */       } else {
/* 1401 */         int i = Math.round(MathHelper.sqrt(d0 * d0 + d2 * d2) * 100.0F);
/* 1402 */         if (i > 25) {
/* 1403 */           a(StatisticList.o, i);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void l(double d0, double d1, double d2)
/*      */   {
/* 1411 */     if (this.vehicle != null) {
/* 1412 */       int i = Math.round(MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 100.0F);
/*      */       
/* 1414 */       if (i > 0) {
/* 1415 */         if ((this.vehicle instanceof EntityMinecartAbstract)) {
/* 1416 */           a(StatisticList.q, i);
/* 1417 */           if (this.e == null) {
/* 1418 */             this.e = new BlockPosition(this);
/* 1419 */           } else if (this.e.c(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ)) >= 1000000.0D) {
/* 1420 */             b(AchievementList.q);
/*      */           }
/* 1422 */         } else if ((this.vehicle instanceof EntityBoat)) {
/* 1423 */           a(StatisticList.r, i);
/* 1424 */         } else if ((this.vehicle instanceof EntityPig)) {
/* 1425 */           a(StatisticList.s, i);
/* 1426 */         } else if ((this.vehicle instanceof EntityHorse)) {
/* 1427 */           a(StatisticList.t, i);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void e(float f, float f1)
/*      */   {
/* 1435 */     if (!this.abilities.canFly) {
/* 1436 */       if (f >= 2.0F) {
/* 1437 */         a(StatisticList.m, (int)Math.round(f * 100.0D));
/*      */       }
/*      */       
/* 1440 */       super.e(f, f1);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void X() {
/* 1445 */     if (!isSpectator()) {
/* 1446 */       super.X();
/*      */     }
/*      */   }
/*      */   
/*      */   protected String n(int i)
/*      */   {
/* 1452 */     return i > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
/*      */   }
/*      */   
/*      */   public void a(EntityLiving entityliving) {
/* 1456 */     if ((entityliving instanceof IMonster)) {
/* 1457 */       b(AchievementList.s);
/*      */     }
/*      */     
/* 1460 */     EntityTypes.MonsterEggInfo entitytypes_monsteregginfo = (EntityTypes.MonsterEggInfo)EntityTypes.eggInfo.get(Integer.valueOf(EntityTypes.a(entityliving)));
/*      */     
/* 1462 */     if (entitytypes_monsteregginfo != null) {
/* 1463 */       b(entitytypes_monsteregginfo.killEntityStatistic);
/*      */     }
/*      */   }
/*      */   
/*      */   public void aA()
/*      */   {
/* 1469 */     if (!this.abilities.isFlying) {
/* 1470 */       super.aA();
/*      */     }
/*      */   }
/*      */   
/*      */   public ItemStack q(int i)
/*      */   {
/* 1476 */     return this.inventory.e(i);
/*      */   }
/*      */   
/*      */   public void giveExp(int i) {
/* 1480 */     addScore(i);
/* 1481 */     int j = Integer.MAX_VALUE - this.expTotal;
/*      */     
/* 1483 */     if (i > j) {
/* 1484 */       i = j;
/*      */     }
/*      */     
/* 1487 */     this.exp += i / getExpToLevel();
/*      */     
/* 1489 */     for (this.expTotal += i; this.exp >= 1.0F; this.exp /= getExpToLevel()) {
/* 1490 */       this.exp = ((this.exp - 1.0F) * getExpToLevel());
/* 1491 */       levelDown(1);
/*      */     }
/*      */   }
/*      */   
/*      */   public int cj()
/*      */   {
/* 1497 */     return this.f;
/*      */   }
/*      */   
/*      */   public void enchantDone(int i) {
/* 1501 */     this.expLevel -= i;
/* 1502 */     if (this.expLevel < 0) {
/* 1503 */       this.expLevel = 0;
/* 1504 */       this.exp = 0.0F;
/* 1505 */       this.expTotal = 0;
/*      */     }
/*      */     
/* 1508 */     this.f = this.random.nextInt();
/*      */   }
/*      */   
/*      */   public void levelDown(int i) {
/* 1512 */     this.expLevel += i;
/* 1513 */     if (this.expLevel < 0) {
/* 1514 */       this.expLevel = 0;
/* 1515 */       this.exp = 0.0F;
/* 1516 */       this.expTotal = 0;
/*      */     }
/*      */     
/* 1519 */     if ((i > 0) && (this.expLevel % 5 == 0) && (this.i < this.ticksLived - 100.0F)) {
/* 1520 */       float f = this.expLevel > 30 ? 1.0F : this.expLevel / 30.0F;
/*      */       
/* 1522 */       this.world.makeSound(this, "random.levelup", f * 0.75F, 1.0F);
/* 1523 */       this.i = this.ticksLived;
/*      */     }
/*      */   }
/*      */   
/*      */   public int getExpToLevel()
/*      */   {
/* 1529 */     return this.expLevel >= 15 ? 37 + (this.expLevel - 15) * 5 : this.expLevel >= 30 ? 112 + (this.expLevel - 30) * 9 : 7 + this.expLevel * 2;
/*      */   }
/*      */   
/*      */   public void applyExhaustion(float f) {
/* 1533 */     if ((!this.abilities.isInvulnerable) && 
/* 1534 */       (!this.world.isClientSide)) {
/* 1535 */       this.foodData.a(f);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public FoodMetaData getFoodData()
/*      */   {
/* 1542 */     return this.foodData;
/*      */   }
/*      */   
/*      */   public boolean j(boolean flag) {
/* 1546 */     return ((flag) || (this.foodData.c())) && (!this.abilities.isInvulnerable);
/*      */   }
/*      */   
/*      */   public boolean cm() {
/* 1550 */     return (getHealth() > 0.0F) && (getHealth() < getMaxHealth());
/*      */   }
/*      */   
/*      */   public void a(ItemStack itemstack, int i) {
/* 1554 */     if (itemstack != this.g) {
/* 1555 */       this.g = itemstack;
/* 1556 */       this.h = i;
/* 1557 */       if (!this.world.isClientSide) {
/* 1558 */         f(true);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean cn()
/*      */   {
/* 1565 */     return this.abilities.mayBuild;
/*      */   }
/*      */   
/*      */   public boolean a(BlockPosition blockposition, EnumDirection enumdirection, ItemStack itemstack) {
/* 1569 */     if (this.abilities.mayBuild)
/* 1570 */       return true;
/* 1571 */     if (itemstack == null) {
/* 1572 */       return false;
/*      */     }
/* 1574 */     BlockPosition blockposition1 = blockposition.shift(enumdirection.opposite());
/* 1575 */     Block block = this.world.getType(blockposition1).getBlock();
/*      */     
/* 1577 */     return (itemstack.d(block)) || (itemstack.x());
/*      */   }
/*      */   
/*      */   protected int getExpValue(EntityHuman entityhuman)
/*      */   {
/* 1582 */     if (this.world.getGameRules().getBoolean("keepInventory")) {
/* 1583 */       return 0;
/*      */     }
/* 1585 */     int i = this.expLevel * 7;
/*      */     
/* 1587 */     return i > 100 ? 100 : i;
/*      */   }
/*      */   
/*      */   protected boolean alwaysGivesExp()
/*      */   {
/* 1592 */     return true;
/*      */   }
/*      */   
/*      */   public void copyTo(EntityHuman entityhuman, boolean flag) {
/* 1596 */     if (flag) {
/* 1597 */       this.inventory.b(entityhuman.inventory);
/* 1598 */       setHealth(entityhuman.getHealth());
/* 1599 */       this.foodData = entityhuman.foodData;
/* 1600 */       this.expLevel = entityhuman.expLevel;
/* 1601 */       this.expTotal = entityhuman.expTotal;
/* 1602 */       this.exp = entityhuman.exp;
/* 1603 */       setScore(entityhuman.getScore());
/* 1604 */       this.an = entityhuman.an;
/* 1605 */       this.ao = entityhuman.ao;
/* 1606 */       this.ap = entityhuman.ap;
/* 1607 */     } else if (this.world.getGameRules().getBoolean("keepInventory")) {
/* 1608 */       this.inventory.b(entityhuman.inventory);
/* 1609 */       this.expLevel = entityhuman.expLevel;
/* 1610 */       this.expTotal = entityhuman.expTotal;
/* 1611 */       this.exp = entityhuman.exp;
/* 1612 */       setScore(entityhuman.getScore());
/*      */     }
/*      */     
/* 1615 */     this.f = entityhuman.f;
/* 1616 */     this.enderChest = entityhuman.enderChest;
/* 1617 */     getDataWatcher().watch(10, Byte.valueOf(entityhuman.getDataWatcher().getByte(10)));
/*      */   }
/*      */   
/*      */   protected boolean s_() {
/* 1621 */     return !this.abilities.isFlying;
/*      */   }
/*      */   
/*      */   public void updateAbilities() {}
/*      */   
/*      */   public void a(WorldSettings.EnumGamemode worldsettings_enumgamemode) {}
/*      */   
/*      */   public String getName() {
/* 1629 */     return this.bH.getName();
/*      */   }
/*      */   
/*      */   public InventoryEnderChest getEnderChest() {
/* 1633 */     return this.enderChest;
/*      */   }
/*      */   
/*      */   public ItemStack getEquipment(int i) {
/* 1637 */     return i == 0 ? this.inventory.getItemInHand() : this.inventory.armor[(i - 1)];
/*      */   }
/*      */   
/*      */   public ItemStack bA() {
/* 1641 */     return this.inventory.getItemInHand();
/*      */   }
/*      */   
/*      */   public void setEquipment(int i, ItemStack itemstack) {
/* 1645 */     this.inventory.armor[i] = itemstack;
/*      */   }
/*      */   
/*      */   public abstract boolean isSpectator();
/*      */   
/*      */   public ItemStack[] getEquipment() {
/* 1651 */     return this.inventory.armor;
/*      */   }
/*      */   
/*      */   public boolean aL() {
/* 1655 */     return !this.abilities.isFlying;
/*      */   }
/*      */   
/*      */   public Scoreboard getScoreboard() {
/* 1659 */     return this.world.getScoreboard();
/*      */   }
/*      */   
/*      */   public ScoreboardTeamBase getScoreboardTeam() {
/* 1663 */     return getScoreboard().getPlayerTeam(getName());
/*      */   }
/*      */   
/*      */   public IChatBaseComponent getScoreboardDisplayName()
/*      */   {
/* 1668 */     ChatComponentText chatcomponenttext = new ChatComponentText(ScoreboardTeam.getPlayerDisplayName(getScoreboardTeam(), getName()));
/*      */     
/* 1670 */     chatcomponenttext.getChatModifier().setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.SUGGEST_COMMAND, "/msg " + getName() + " "));
/* 1671 */     chatcomponenttext.getChatModifier().setChatHoverable(aQ());
/* 1672 */     chatcomponenttext.getChatModifier().setInsertion(getName());
/* 1673 */     return chatcomponenttext;
/*      */   }
/*      */   
/*      */   public float getHeadHeight() {
/* 1677 */     float f = 1.62F;
/*      */     
/* 1679 */     if (isSleeping()) {
/* 1680 */       f = 0.2F;
/*      */     }
/*      */     
/* 1683 */     if (isSneaking()) {
/* 1684 */       f -= 0.08F;
/*      */     }
/*      */     
/* 1687 */     return f;
/*      */   }
/*      */   
/*      */   public void setAbsorptionHearts(float f) {
/* 1691 */     if (f < 0.0F) {
/* 1692 */       f = 0.0F;
/*      */     }
/*      */     
/* 1695 */     getDataWatcher().watch(17, Float.valueOf(f));
/*      */   }
/*      */   
/*      */   public float getAbsorptionHearts() {
/* 1699 */     return getDataWatcher().getFloat(17);
/*      */   }
/*      */   
/*      */   public static UUID a(GameProfile gameprofile) {
/* 1703 */     UUID uuid = gameprofile.getId();
/*      */     
/* 1705 */     if (uuid == null) {
/* 1706 */       uuid = b(gameprofile.getName());
/*      */     }
/*      */     
/* 1709 */     return uuid;
/*      */   }
/*      */   
/*      */   public static UUID b(String s) {
/* 1713 */     return UUID.nameUUIDFromBytes(("OfflinePlayer:" + s).getBytes(com.google.common.base.Charsets.UTF_8));
/*      */   }
/*      */   
/*      */   public boolean a(ChestLock chestlock) {
/* 1717 */     if (chestlock.a()) {
/* 1718 */       return true;
/*      */     }
/* 1720 */     ItemStack itemstack = bZ();
/*      */     
/* 1722 */     return (itemstack != null) && (itemstack.hasName()) ? itemstack.getName().equals(chestlock.b()) : false;
/*      */   }
/*      */   
/*      */   public boolean getSendCommandFeedback()
/*      */   {
/* 1727 */     return MinecraftServer.getServer().worldServer[0].getGameRules().getBoolean("sendCommandFeedback");
/*      */   }
/*      */   
/*      */   public boolean d(int i, ItemStack itemstack) {
/* 1731 */     if ((i >= 0) && (i < this.inventory.items.length)) {
/* 1732 */       this.inventory.setItem(i, itemstack);
/* 1733 */       return true;
/*      */     }
/* 1735 */     int j = i - 100;
/*      */     
/*      */ 
/* 1738 */     if ((j >= 0) && (j < this.inventory.armor.length)) {
/* 1739 */       int k = j + 1;
/* 1740 */       if ((itemstack != null) && (itemstack.getItem() != null)) {
/* 1741 */         if ((itemstack.getItem() instanceof ItemArmor)) {
/* 1742 */           if (EntityInsentient.c(itemstack) != k) {
/* 1743 */             return false;
/*      */           }
/* 1745 */         } else if ((k != 4) || ((itemstack.getItem() != Items.SKULL) && (!(itemstack.getItem() instanceof ItemBlock)))) {
/* 1746 */           return false;
/*      */         }
/*      */       }
/*      */       
/* 1750 */       this.inventory.setItem(j + this.inventory.items.length, itemstack);
/* 1751 */       return true;
/*      */     }
/* 1753 */     int k = i - 200;
/* 1754 */     if ((k >= 0) && (k < this.enderChest.getSize())) {
/* 1755 */       this.enderChest.setItem(k, itemstack);
/* 1756 */       return true;
/*      */     }
/* 1758 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static class SyntheticClass_1
/*      */   {
/* 1766 */     static final int[] a = new int[EnumDirection.values().length];
/*      */     
/*      */     static {
/*      */       try {
/* 1770 */         a[EnumDirection.SOUTH.ordinal()] = 1;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*      */       
/*      */       try
/*      */       {
/* 1776 */         a[EnumDirection.NORTH.ordinal()] = 2;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*      */       
/*      */       try
/*      */       {
/* 1782 */         a[EnumDirection.WEST.ordinal()] = 3;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*      */       
/*      */       try
/*      */       {
/* 1788 */         a[EnumDirection.EAST.ordinal()] = 4;
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static enum EnumBedResult
/*      */   {
/* 1798 */     OK,  NOT_POSSIBLE_HERE,  NOT_POSSIBLE_NOW,  TOO_FAR_AWAY,  OTHER_PROBLEM,  NOT_SAFE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static enum EnumChatVisibility
/*      */   {
/* 1805 */     FULL(0, "options.chat.visibility.full"),  SYSTEM(1, "options.chat.visibility.system"),  HIDDEN(2, "options.chat.visibility.hidden");
/*      */     
/*      */     private static final EnumChatVisibility[] d;
/*      */     private final int e;
/*      */     private final String f;
/*      */     
/*      */     private EnumChatVisibility(int i, String s) {
/* 1812 */       this.e = i;
/* 1813 */       this.f = s;
/*      */     }
/*      */     
/*      */     public int a() {
/* 1817 */       return this.e;
/*      */     }
/*      */     
/*      */     public static EnumChatVisibility a(int i) {
/* 1821 */       return d[(i % d.length)];
/*      */     }
/*      */     
/*      */     static
/*      */     {
/* 1807 */       d = new EnumChatVisibility[values().length];
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
/*      */ 
/*      */ 
/* 1825 */       EnumChatVisibility[] aentityhuman_enumchatvisibility = values();
/* 1826 */       int i = aentityhuman_enumchatvisibility.length;
/*      */       
/* 1828 */       for (int j = 0; j < i; j++) {
/* 1829 */         EnumChatVisibility entityhuman_enumchatvisibility = aentityhuman_enumchatvisibility[j];
/*      */         
/* 1831 */         d[entityhuman_enumchatvisibility.e] = entityhuman_enumchatvisibility;
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityHuman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */