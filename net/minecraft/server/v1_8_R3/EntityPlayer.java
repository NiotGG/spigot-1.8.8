/*      */ package net.minecraft.server.v1_8_R3;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.EnumSet;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.WeatherType;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*      */ import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboardManager;
/*      */ import org.bukkit.event.entity.PlayerDeathEvent;
/*      */ import org.bukkit.event.inventory.InventoryType;
/*      */ import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
/*      */ 
/*      */ public class EntityPlayer extends EntityHuman implements ICrafting
/*      */ {
/*   29 */   private static final org.apache.logging.log4j.Logger bH = ;
/*   30 */   public String locale = "en_US";
/*      */   public PlayerConnection playerConnection;
/*      */   public final MinecraftServer server;
/*      */   public final PlayerInteractManager playerInteractManager;
/*      */   public double d;
/*      */   public double e;
/*   36 */   public final List<ChunkCoordIntPair> chunkCoordIntPairQueue = Lists.newLinkedList();
/*   37 */   public final List<Integer> removeQueue = Lists.newLinkedList();
/*      */   private final ServerStatisticManager bK;
/*   39 */   private float bL = Float.MIN_VALUE;
/*   40 */   private float bM = -1.0E8F;
/*   41 */   private int bN = -99999999;
/*   42 */   private boolean bO = true;
/*   43 */   public int lastSentExp = -99999999;
/*   44 */   public int invulnerableTicks = 60;
/*      */   private EntityHuman.EnumChatVisibility bR;
/*   46 */   private boolean bS = true;
/*   47 */   private long bT = System.currentTimeMillis();
/*   48 */   private Entity bU = null;
/*      */   
/*      */   private int containerCounter;
/*      */   
/*      */   public boolean g;
/*      */   public int ping;
/*      */   public boolean viewingCredits;
/*      */   public String displayName;
/*      */   public IChatBaseComponent listName;
/*      */   public org.bukkit.Location compassTarget;
/*   58 */   public int newExp = 0;
/*   59 */   public int newLevel = 0;
/*   60 */   public int newTotalExp = 0;
/*   61 */   public boolean keepLevel = false;
/*      */   public double maxHealthCache;
/*   63 */   public boolean joining = true;
/*      */   
/*      */ 
/*   66 */   public boolean collidesWithEntities = true;
/*      */   
/*      */ 
/*      */   public boolean ad()
/*      */   {
/*   71 */     return (this.collidesWithEntities) && (super.ad());
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean ae()
/*      */   {
/*   77 */     return (this.collidesWithEntities) && (super.ae());
/*      */   }
/*      */   
/*      */   public EntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager)
/*      */   {
/*   82 */     super(worldserver, gameprofile);
/*   83 */     playerinteractmanager.player = this;
/*   84 */     this.playerInteractManager = playerinteractmanager;
/*   85 */     BlockPosition blockposition = worldserver.getSpawn();
/*      */     
/*   87 */     if ((!worldserver.worldProvider.o()) && (worldserver.getWorldData().getGameType() != WorldSettings.EnumGamemode.ADVENTURE)) {
/*   88 */       int i = Math.max(5, minecraftserver.getSpawnProtection() - 6);
/*   89 */       int j = MathHelper.floor(worldserver.getWorldBorder().b(blockposition.getX(), blockposition.getZ()));
/*      */       
/*   91 */       if (j < i) {
/*   92 */         i = j;
/*      */       }
/*      */       
/*   95 */       if (j <= 1) {
/*   96 */         i = 1;
/*      */       }
/*      */       
/*   99 */       blockposition = worldserver.r(blockposition.a(this.random.nextInt(i * 2) - i, 0, this.random.nextInt(i * 2) - i));
/*      */     }
/*      */     
/*  102 */     this.server = minecraftserver;
/*  103 */     this.bK = minecraftserver.getPlayerList().a(this);
/*  104 */     this.S = 0.0F;
/*  105 */     setPositionRotation(blockposition, 0.0F, 0.0F);
/*      */     
/*  107 */     while ((!worldserver.getCubes(this, getBoundingBox()).isEmpty()) && (this.locY < 255.0D)) {
/*  108 */       setPosition(this.locX, this.locY + 1.0D, this.locZ);
/*      */     }
/*      */     
/*      */ 
/*  112 */     this.displayName = getName();
/*      */     
/*  114 */     this.maxHealthCache = getMaxHealth();
/*      */   }
/*      */   
/*      */   public void a(NBTTagCompound nbttagcompound)
/*      */   {
/*  119 */     super.a(nbttagcompound);
/*  120 */     if (nbttagcompound.hasKeyOfType("playerGameType", 99)) {
/*  121 */       if (MinecraftServer.getServer().getForceGamemode()) {
/*  122 */         this.playerInteractManager.setGameMode(MinecraftServer.getServer().getGamemode());
/*      */       } else {
/*  124 */         this.playerInteractManager.setGameMode(WorldSettings.EnumGamemode.getById(nbttagcompound.getInt("playerGameType")));
/*      */       }
/*      */     }
/*      */     
/*  128 */     getBukkitEntity().readExtraData(nbttagcompound);
/*      */   }
/*      */   
/*      */   public void b(NBTTagCompound nbttagcompound) {
/*  132 */     super.b(nbttagcompound);
/*  133 */     nbttagcompound.setInt("playerGameType", this.playerInteractManager.getGameMode().getId());
/*      */     
/*  135 */     getBukkitEntity().setExtraData(nbttagcompound);
/*      */   }
/*      */   
/*      */   public void spawnIn(World world)
/*      */   {
/*  140 */     super.spawnIn(world);
/*  141 */     if (world == null) {
/*  142 */       this.dead = false;
/*  143 */       BlockPosition position = null;
/*  144 */       if ((this.spawnWorld != null) && (!this.spawnWorld.equals(""))) {
/*  145 */         CraftWorld cworld = (CraftWorld)Bukkit.getServer().getWorld(this.spawnWorld);
/*  146 */         if ((cworld != null) && (getBed() != null)) {
/*  147 */           world = cworld.getHandle();
/*  148 */           position = EntityHuman.getBed(cworld.getHandle(), getBed(), false);
/*      */         }
/*      */       }
/*  151 */       if ((world == null) || (position == null)) {
/*  152 */         world = ((CraftWorld)Bukkit.getServer().getWorlds().get(0)).getHandle();
/*  153 */         position = world.getSpawn();
/*      */       }
/*  155 */       this.world = world;
/*  156 */       setPosition(position.getX() + 0.5D, position.getY(), position.getZ() + 0.5D);
/*      */     }
/*  158 */     this.dimension = ((WorldServer)this.world).dimension;
/*  159 */     this.playerInteractManager.a((WorldServer)world);
/*      */   }
/*      */   
/*      */   public void levelDown(int i)
/*      */   {
/*  164 */     super.levelDown(i);
/*  165 */     this.lastSentExp = -1;
/*      */   }
/*      */   
/*      */   public void enchantDone(int i) {
/*  169 */     super.enchantDone(i);
/*  170 */     this.lastSentExp = -1;
/*      */   }
/*      */   
/*      */   public void syncInventory() {
/*  174 */     this.activeContainer.addSlotListener(this);
/*      */   }
/*      */   
/*      */   public void enterCombat() {
/*  178 */     super.enterCombat();
/*  179 */     this.playerConnection.sendPacket(new PacketPlayOutCombatEvent(bs(), PacketPlayOutCombatEvent.EnumCombatEventType.ENTER_COMBAT));
/*      */   }
/*      */   
/*      */   public void exitCombat() {
/*  183 */     super.exitCombat();
/*  184 */     this.playerConnection.sendPacket(new PacketPlayOutCombatEvent(bs(), PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT));
/*      */   }
/*      */   
/*      */   public void t_()
/*      */   {
/*  189 */     if (this.joining) {
/*  190 */       this.joining = false;
/*      */     }
/*      */     
/*  193 */     this.playerInteractManager.a();
/*  194 */     this.invulnerableTicks -= 1;
/*  195 */     if (this.noDamageTicks > 0) {
/*  196 */       this.noDamageTicks -= 1;
/*      */     }
/*      */     
/*  199 */     this.activeContainer.b();
/*  200 */     if ((!this.world.isClientSide) && (!this.activeContainer.a(this))) {
/*  201 */       closeInventory();
/*  202 */       this.activeContainer = this.defaultContainer;
/*      */     }
/*      */     
/*  205 */     while (!this.removeQueue.isEmpty()) {
/*  206 */       int i = Math.min(this.removeQueue.size(), Integer.MAX_VALUE);
/*  207 */       int[] aint = new int[i];
/*  208 */       Iterator iterator = this.removeQueue.iterator();
/*  209 */       int j = 0;
/*      */       
/*  211 */       while ((iterator.hasNext()) && (j < i)) {
/*  212 */         aint[(j++)] = ((Integer)iterator.next()).intValue();
/*  213 */         iterator.remove();
/*      */       }
/*      */       
/*  216 */       this.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(aint));
/*      */     }
/*      */     
/*  219 */     if (!this.chunkCoordIntPairQueue.isEmpty()) {
/*  220 */       ArrayList arraylist = Lists.newArrayList();
/*  221 */       Iterator iterator1 = this.chunkCoordIntPairQueue.iterator();
/*  222 */       ArrayList arraylist1 = Lists.newArrayList();
/*      */       
/*      */ 
/*      */ 
/*  226 */       while ((iterator1.hasNext()) && (arraylist.size() < this.world.spigotConfig.maxBulkChunk)) {
/*  227 */         ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)iterator1.next();
/*      */         
/*  229 */         if (chunkcoordintpair != null) {
/*  230 */           if (this.world.isLoaded(new BlockPosition(chunkcoordintpair.x << 4, 0, chunkcoordintpair.z << 4))) {
/*  231 */             Chunk chunk = this.world.getChunkAt(chunkcoordintpair.x, chunkcoordintpair.z);
/*  232 */             if (chunk.isReady()) {
/*  233 */               arraylist.add(chunk);
/*  234 */               arraylist1.addAll(chunk.tileEntities.values());
/*  235 */               iterator1.remove();
/*      */             }
/*      */           }
/*      */         } else {
/*  239 */           iterator1.remove();
/*      */         }
/*      */       }
/*      */       
/*  243 */       if (!arraylist.isEmpty()) {
/*  244 */         if (arraylist.size() == 1) {
/*  245 */           this.playerConnection.sendPacket(new PacketPlayOutMapChunk((Chunk)arraylist.get(0), true, 65535));
/*      */         } else {
/*  247 */           this.playerConnection.sendPacket(new PacketPlayOutMapChunkBulk(arraylist));
/*      */         }
/*      */         
/*  250 */         Iterator iterator2 = arraylist1.iterator();
/*      */         
/*  252 */         while (iterator2.hasNext()) {
/*  253 */           TileEntity tileentity = (TileEntity)iterator2.next();
/*      */           
/*  255 */           a(tileentity);
/*      */         }
/*      */         
/*  258 */         iterator2 = arraylist.iterator();
/*      */         
/*  260 */         while (iterator2.hasNext()) {
/*  261 */           Chunk chunk = (Chunk)iterator2.next();
/*  262 */           u().getTracker().a(this, chunk);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  267 */     Entity entity = C();
/*      */     
/*  269 */     if (entity != this) {
/*  270 */       if (!entity.isAlive()) {
/*  271 */         setSpectatorTarget(this);
/*      */       } else {
/*  273 */         setLocation(entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
/*  274 */         this.server.getPlayerList().d(this);
/*  275 */         if (isSneaking()) {
/*  276 */           setSpectatorTarget(this);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void l()
/*      */   {
/*      */     try {
/*  285 */       super.t_();
/*      */       
/*  287 */       for (int i = 0; i < this.inventory.getSize(); i++) {
/*  288 */         ItemStack itemstack = this.inventory.getItem(i);
/*      */         
/*  290 */         if ((itemstack != null) && (itemstack.getItem().f())) {
/*  291 */           Packet packet = ((ItemWorldMapBase)itemstack.getItem()).c(itemstack, this.world, this);
/*      */           
/*  293 */           if (packet != null) {
/*  294 */             this.playerConnection.sendPacket(packet);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  300 */       if ((getHealth() == this.bM) && (this.bN == this.foodData.getFoodLevel())) { if ((this.foodData.getSaturationLevel() == 0.0F) == this.bO) {}
/*  301 */       } else { this.playerConnection.sendPacket(new PacketPlayOutUpdateHealth(getBukkitEntity().getScaledHealth(), this.foodData.getFoodLevel(), this.foodData.getSaturationLevel()));
/*  302 */         this.bM = getHealth();
/*  303 */         this.bN = this.foodData.getFoodLevel();
/*  304 */         this.bO = (this.foodData.getSaturationLevel() == 0.0F);
/*      */       }
/*      */       
/*  307 */       if (getHealth() + getAbsorptionHearts() != this.bL) {
/*  308 */         this.bL = (getHealth() + getAbsorptionHearts());
/*  309 */         Collection collection = getScoreboard().getObjectivesForCriteria(IScoreboardCriteria.g);
/*  310 */         Iterator iterator = collection.iterator();
/*      */         
/*  312 */         while (iterator.hasNext()) {
/*  313 */           ScoreboardObjective scoreboardobjective = (ScoreboardObjective)iterator.next();
/*      */           
/*  315 */           getScoreboard().getPlayerScoreForObjective(getName(), scoreboardobjective).updateForList(java.util.Arrays.asList(new EntityHuman[] { this }));
/*      */         }
/*      */         
/*  318 */         this.world.getServer().getScoreboardManager().updateAllScoresForList(IScoreboardCriteria.g, getName(), com.google.common.collect.ImmutableList.of(this));
/*      */       }
/*      */       
/*  321 */       if (this.maxHealthCache != getMaxHealth()) {
/*  322 */         getBukkitEntity().updateScaledHealth();
/*      */       }
/*      */       
/*      */ 
/*  326 */       if (this.expTotal != this.lastSentExp) {
/*  327 */         this.lastSentExp = this.expTotal;
/*  328 */         this.playerConnection.sendPacket(new PacketPlayOutExperience(this.exp, this.expTotal, this.expLevel));
/*      */       }
/*      */       
/*  331 */       if ((this.ticksLived % 20 * 5 == 0) && (!getStatisticManager().hasAchievement(AchievementList.L))) {
/*  332 */         i_();
/*      */       }
/*      */       
/*      */ 
/*  336 */       if (this.oldLevel == -1) {
/*  337 */         this.oldLevel = this.expLevel;
/*      */       }
/*      */       
/*  340 */       if (this.oldLevel != this.expLevel) {
/*  341 */         CraftEventFactory.callPlayerLevelChangeEvent(this.world.getServer().getPlayer(this), this.oldLevel, this.expLevel);
/*  342 */         this.oldLevel = this.expLevel;
/*      */       }
/*      */     }
/*      */     catch (Throwable throwable) {
/*  346 */       CrashReport crashreport = CrashReport.a(throwable, "Ticking player");
/*  347 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Player being ticked");
/*      */       
/*  349 */       appendEntityCrashDetails(crashreportsystemdetails);
/*  350 */       throw new ReportedException(crashreport);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void i_() {
/*  355 */     BiomeBase biomebase = this.world.getBiome(new BlockPosition(MathHelper.floor(this.locX), 0, MathHelper.floor(this.locZ)));
/*  356 */     String s = biomebase.ah;
/*  357 */     AchievementSet achievementset = (AchievementSet)getStatisticManager().b(AchievementList.L);
/*      */     
/*  359 */     if (achievementset == null) {
/*  360 */       achievementset = (AchievementSet)getStatisticManager().a(AchievementList.L, new AchievementSet());
/*      */     }
/*      */     
/*  363 */     achievementset.add(s);
/*  364 */     if ((getStatisticManager().b(AchievementList.L)) && (achievementset.size() >= BiomeBase.n.size())) {
/*  365 */       HashSet hashset = com.google.common.collect.Sets.newHashSet(BiomeBase.n);
/*  366 */       Iterator iterator = achievementset.iterator();
/*      */       
/*  368 */       while (iterator.hasNext()) {
/*  369 */         String s1 = (String)iterator.next();
/*  370 */         Iterator iterator1 = hashset.iterator();
/*      */         
/*  372 */         while (iterator1.hasNext()) {
/*  373 */           BiomeBase biomebase1 = (BiomeBase)iterator1.next();
/*      */           
/*  375 */           if (biomebase1.ah.equals(s1)) {
/*  376 */             iterator1.remove();
/*      */           }
/*      */         }
/*      */         
/*  380 */         if (hashset.isEmpty()) {
/*      */           break;
/*      */         }
/*      */       }
/*      */       
/*  385 */       if (hashset.isEmpty()) {
/*  386 */         b(AchievementList.L);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void die(DamageSource damagesource)
/*      */   {
/*  394 */     if (this.dead) {
/*  395 */       return;
/*      */     }
/*  397 */     List<org.bukkit.inventory.ItemStack> loot = new ArrayList();
/*  398 */     boolean keepInventory = this.world.getGameRules().getBoolean("keepInventory");
/*      */     
/*  400 */     if (!keepInventory) {
/*  401 */       for (int i = 0; i < this.inventory.items.length; i++) {
/*  402 */         if (this.inventory.items[i] != null) {
/*  403 */           loot.add(CraftItemStack.asCraftMirror(this.inventory.items[i]));
/*      */         }
/*      */       }
/*      */       
/*  407 */       for (int i = 0; i < this.inventory.armor.length; i++) {
/*  408 */         if (this.inventory.armor[i] != null) {
/*  409 */           loot.add(CraftItemStack.asCraftMirror(this.inventory.armor[i]));
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  414 */     IChatBaseComponent chatmessage = bs().b();
/*      */     
/*  416 */     String deathmessage = chatmessage.c();
/*  417 */     PlayerDeathEvent event = CraftEventFactory.callPlayerDeathEvent(this, loot, deathmessage, keepInventory);
/*      */     
/*  419 */     String deathMessage = event.getDeathMessage();
/*      */     
/*  421 */     if ((deathMessage != null) && (deathMessage.length() > 0) && (this.world.getGameRules().getBoolean("showDeathMessages"))) {
/*  422 */       if (deathMessage.equals(deathmessage)) {
/*  423 */         this.server.getPlayerList().sendMessage(chatmessage);
/*      */       } else {
/*  425 */         this.server.getPlayerList().sendMessage(org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage.fromString(deathMessage));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  430 */     if (!event.getKeepInventory()) {
/*  431 */       for (int i = 0; i < this.inventory.items.length; i++) {
/*  432 */         this.inventory.items[i] = null;
/*      */       }
/*      */       
/*  435 */       for (int i = 0; i < this.inventory.armor.length; i++) {
/*  436 */         this.inventory.armor[i] = null;
/*      */       }
/*      */     }
/*      */     
/*  440 */     closeInventory();
/*  441 */     setSpectatorTarget(this);
/*      */     
/*      */ 
/*      */ 
/*  445 */     Collection collection = this.world.getServer().getScoreboardManager().getScoreboardScores(IScoreboardCriteria.d, getName(), new ArrayList());
/*  446 */     Iterator iterator = collection.iterator();
/*      */     
/*  448 */     while (iterator.hasNext()) {
/*  449 */       ScoreboardScore scoreboardscore = (ScoreboardScore)iterator.next();
/*      */       
/*  451 */       scoreboardscore.incrementScore();
/*      */     }
/*      */     
/*  454 */     EntityLiving entityliving = bt();
/*      */     
/*  456 */     if (entityliving != null) {
/*  457 */       EntityTypes.MonsterEggInfo entitytypes_monsteregginfo = (EntityTypes.MonsterEggInfo)EntityTypes.eggInfo.get(Integer.valueOf(EntityTypes.a(entityliving)));
/*      */       
/*  459 */       if (entitytypes_monsteregginfo != null) {
/*  460 */         b(entitytypes_monsteregginfo.e);
/*      */       }
/*      */       
/*  463 */       entityliving.b(this, this.aW);
/*      */     }
/*      */     
/*  466 */     b(StatisticList.y);
/*  467 */     a(StatisticList.h);
/*  468 */     bs().g();
/*      */   }
/*      */   
/*      */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  472 */     if (isInvulnerable(damagesource)) {
/*  473 */       return false;
/*      */     }
/*  475 */     boolean flag = (this.server.ae()) && (cr()) && ("fall".equals(damagesource.translationIndex));
/*      */     
/*  477 */     if ((!flag) && (this.invulnerableTicks > 0) && (damagesource != DamageSource.OUT_OF_WORLD)) {
/*  478 */       return false;
/*      */     }
/*  480 */     if ((damagesource instanceof EntityDamageSource)) {
/*  481 */       Entity entity = damagesource.getEntity();
/*      */       
/*  483 */       if (((entity instanceof EntityHuman)) && (!a((EntityHuman)entity))) {
/*  484 */         return false;
/*      */       }
/*      */       
/*  487 */       if ((entity instanceof EntityArrow)) {
/*  488 */         EntityArrow entityarrow = (EntityArrow)entity;
/*      */         
/*  490 */         if (((entityarrow.shooter instanceof EntityHuman)) && (!a((EntityHuman)entityarrow.shooter))) {
/*  491 */           return false;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  496 */     return super.damageEntity(damagesource, f);
/*      */   }
/*      */   
/*      */ 
/*      */   public boolean a(EntityHuman entityhuman)
/*      */   {
/*  502 */     return !cr() ? false : super.a(entityhuman);
/*      */   }
/*      */   
/*      */   private boolean cr()
/*      */   {
/*  507 */     return this.world.pvpMode;
/*      */   }
/*      */   
/*      */   public void c(int i) {
/*  511 */     if ((this.dimension == 1) && (i == 1)) {
/*  512 */       b(AchievementList.D);
/*  513 */       this.world.kill(this);
/*  514 */       this.viewingCredits = true;
/*  515 */       this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(4, 0.0F));
/*      */     } else {
/*  517 */       if ((this.dimension == 0) && (i == 1)) {
/*  518 */         b(AchievementList.C);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  531 */         b(AchievementList.y);
/*      */       }
/*      */       
/*      */ 
/*  535 */       PlayerTeleportEvent.TeleportCause cause = (this.dimension == 1) || (i == 1) ? PlayerTeleportEvent.TeleportCause.END_PORTAL : PlayerTeleportEvent.TeleportCause.NETHER_PORTAL;
/*  536 */       this.server.getPlayerList().changeDimension(this, i, cause);
/*      */       
/*  538 */       this.lastSentExp = -1;
/*  539 */       this.bM = -1.0F;
/*  540 */       this.bN = -1;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean a(EntityPlayer entityplayer)
/*      */   {
/*  546 */     return isSpectator() ? false : entityplayer.isSpectator() ? false : C() == this ? true : super.a(entityplayer);
/*      */   }
/*      */   
/*      */   private void a(TileEntity tileentity) {
/*  550 */     if (tileentity != null) {
/*  551 */       Packet packet = tileentity.getUpdatePacket();
/*      */       
/*  553 */       if (packet != null) {
/*  554 */         this.playerConnection.sendPacket(packet);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void receive(Entity entity, int i)
/*      */   {
/*  561 */     super.receive(entity, i);
/*  562 */     this.activeContainer.b();
/*      */   }
/*      */   
/*      */   public EntityHuman.EnumBedResult a(BlockPosition blockposition) {
/*  566 */     EntityHuman.EnumBedResult entityhuman_enumbedresult = super.a(blockposition);
/*      */     
/*  568 */     if (entityhuman_enumbedresult == EntityHuman.EnumBedResult.OK) {
/*  569 */       PacketPlayOutBed packetplayoutbed = new PacketPlayOutBed(this, blockposition);
/*      */       
/*  571 */       u().getTracker().a(this, packetplayoutbed);
/*  572 */       this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/*  573 */       this.playerConnection.sendPacket(packetplayoutbed);
/*      */     }
/*      */     
/*  576 */     return entityhuman_enumbedresult;
/*      */   }
/*      */   
/*      */   public void a(boolean flag, boolean flag1, boolean flag2) {
/*  580 */     if (!this.sleeping) return;
/*  581 */     if (isSleeping()) {
/*  582 */       u().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(this, 2));
/*      */     }
/*      */     
/*  585 */     super.a(flag, flag1, flag2);
/*  586 */     if (this.playerConnection != null) {
/*  587 */       this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/*      */     }
/*      */   }
/*      */   
/*      */   public void mount(Entity entity)
/*      */   {
/*  593 */     Entity entity1 = this.vehicle;
/*      */     
/*  595 */     super.mount(entity);
/*  596 */     if (this.vehicle != entity1) {
/*  597 */       this.playerConnection.sendPacket(new PacketPlayOutAttachEntity(0, this, this.vehicle));
/*  598 */       this.playerConnection.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void a(double d0, boolean flag, Block block, BlockPosition blockposition) {}
/*      */   
/*      */   public void a(double d0, boolean flag)
/*      */   {
/*  606 */     int i = MathHelper.floor(this.locX);
/*  607 */     int j = MathHelper.floor(this.locY - 0.20000000298023224D);
/*  608 */     int k = MathHelper.floor(this.locZ);
/*  609 */     BlockPosition blockposition = new BlockPosition(i, j, k);
/*  610 */     Block block = this.world.getType(blockposition).getBlock();
/*      */     
/*  612 */     if (block.getMaterial() == Material.AIR) {
/*  613 */       Block block1 = this.world.getType(blockposition.down()).getBlock();
/*      */       
/*  615 */       if (((block1 instanceof BlockFence)) || ((block1 instanceof BlockCobbleWall)) || ((block1 instanceof BlockFenceGate))) {
/*  616 */         blockposition = blockposition.down();
/*  617 */         block = this.world.getType(blockposition).getBlock();
/*      */       }
/*      */     }
/*      */     
/*  621 */     super.a(d0, flag, block, blockposition);
/*      */   }
/*      */   
/*      */   public void openSign(TileEntitySign tileentitysign) {
/*  625 */     tileentitysign.a(this);
/*  626 */     this.playerConnection.sendPacket(new PacketPlayOutOpenSignEditor(tileentitysign.getPosition()));
/*      */   }
/*      */   
/*      */   public int nextContainerCounter() {
/*  630 */     this.containerCounter = (this.containerCounter % 100 + 1);
/*  631 */     return this.containerCounter;
/*      */   }
/*      */   
/*      */   public void openTileEntity(ITileEntityContainer itileentitycontainer)
/*      */   {
/*  636 */     Container container = CraftEventFactory.callInventoryOpenEvent(this, itileentitycontainer.createContainer(this.inventory, this));
/*  637 */     if (container == null) {
/*  638 */       return;
/*      */     }
/*      */     
/*  641 */     nextContainerCounter();
/*  642 */     this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, itileentitycontainer.getContainerName(), itileentitycontainer.getScoreboardDisplayName()));
/*  643 */     this.activeContainer = container;
/*  644 */     this.activeContainer.windowId = this.containerCounter;
/*  645 */     this.activeContainer.addSlotListener(this);
/*      */   }
/*      */   
/*      */ 
/*      */   public void openContainer(IInventory iinventory)
/*      */   {
/*  651 */     boolean cancelled = false;
/*  652 */     if ((iinventory instanceof ITileInventory)) {
/*  653 */       ITileInventory itileinventory = (ITileInventory)iinventory;
/*  654 */       cancelled = (itileinventory.r_()) && (!a(itileinventory.i())) && (!isSpectator());
/*      */     }
/*      */     
/*      */     Container container;
/*  658 */     if ((iinventory instanceof ITileEntityContainer)) {
/*  659 */       container = ((ITileEntityContainer)iinventory).createContainer(this.inventory, this);
/*      */     } else {
/*  661 */       container = new ContainerChest(this.inventory, iinventory, this);
/*      */     }
/*  663 */     Container container = CraftEventFactory.callInventoryOpenEvent(this, container, cancelled);
/*  664 */     if ((container == null) && (!cancelled)) {
/*  665 */       iinventory.closeContainer(this);
/*  666 */       return;
/*      */     }
/*      */     
/*  669 */     if (this.activeContainer != this.defaultContainer) {
/*  670 */       closeInventory();
/*      */     }
/*      */     
/*  673 */     if ((iinventory instanceof ITileInventory)) {
/*  674 */       ITileInventory itileinventory = (ITileInventory)iinventory;
/*      */       
/*  676 */       if ((itileinventory.r_()) && (!a(itileinventory.i())) && (!isSpectator()) && (container == null)) {
/*  677 */         this.playerConnection.sendPacket(new PacketPlayOutChat(new ChatMessage("container.isLocked", new Object[] { iinventory.getScoreboardDisplayName() }), (byte)2));
/*  678 */         this.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect("random.door_close", this.locX, this.locY, this.locZ, 1.0F, 1.0F));
/*      */         
/*  680 */         iinventory.closeContainer(this);
/*  681 */         return;
/*      */       }
/*      */     }
/*      */     
/*  685 */     nextContainerCounter();
/*  686 */     if ((iinventory instanceof ITileEntityContainer)) {
/*  687 */       this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, ((ITileEntityContainer)iinventory).getContainerName(), iinventory.getScoreboardDisplayName(), iinventory.getSize()));
/*  688 */       this.activeContainer = container;
/*      */     } else {
/*  690 */       this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, "minecraft:container", iinventory.getScoreboardDisplayName(), iinventory.getSize()));
/*  691 */       this.activeContainer = container;
/*      */     }
/*      */     
/*  694 */     this.activeContainer.windowId = this.containerCounter;
/*  695 */     this.activeContainer.addSlotListener(this);
/*      */   }
/*      */   
/*      */   public void openTrade(IMerchant imerchant)
/*      */   {
/*  700 */     Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerMerchant(this.inventory, imerchant, this.world));
/*  701 */     if (container == null) {
/*  702 */       return;
/*      */     }
/*      */     
/*  705 */     nextContainerCounter();
/*  706 */     this.activeContainer = container;
/*  707 */     this.activeContainer.windowId = this.containerCounter;
/*  708 */     this.activeContainer.addSlotListener(this);
/*  709 */     InventoryMerchant inventorymerchant = ((ContainerMerchant)this.activeContainer).e();
/*  710 */     IChatBaseComponent ichatbasecomponent = imerchant.getScoreboardDisplayName();
/*      */     
/*  712 */     this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, "minecraft:villager", ichatbasecomponent, inventorymerchant.getSize()));
/*  713 */     MerchantRecipeList merchantrecipelist = imerchant.getOffers(this);
/*      */     
/*  715 */     if (merchantrecipelist != null) {
/*  716 */       PacketDataSerializer packetdataserializer = new PacketDataSerializer(Unpooled.buffer());
/*      */       
/*  718 */       packetdataserializer.writeInt(this.containerCounter);
/*  719 */       merchantrecipelist.a(packetdataserializer);
/*  720 */       this.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|TrList", packetdataserializer));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void openHorseInventory(EntityHorse entityhorse, IInventory iinventory)
/*      */   {
/*  727 */     Container container = CraftEventFactory.callInventoryOpenEvent(this, new ContainerHorse(this.inventory, iinventory, entityhorse, this));
/*  728 */     if (container == null) {
/*  729 */       iinventory.closeContainer(this);
/*  730 */       return;
/*      */     }
/*      */     
/*  733 */     if (this.activeContainer != this.defaultContainer) {
/*  734 */       closeInventory();
/*      */     }
/*      */     
/*  737 */     nextContainerCounter();
/*  738 */     this.playerConnection.sendPacket(new PacketPlayOutOpenWindow(this.containerCounter, "EntityHorse", iinventory.getScoreboardDisplayName(), iinventory.getSize(), entityhorse.getId()));
/*  739 */     this.activeContainer = container;
/*  740 */     this.activeContainer.windowId = this.containerCounter;
/*  741 */     this.activeContainer.addSlotListener(this);
/*      */   }
/*      */   
/*      */   public void openBook(ItemStack itemstack) {
/*  745 */     Item item = itemstack.getItem();
/*      */     
/*  747 */     if (item == Items.WRITTEN_BOOK) {
/*  748 */       this.playerConnection.sendPacket(new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(Unpooled.buffer())));
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(Container container, int i, ItemStack itemstack)
/*      */   {
/*  754 */     if ((!(container.getSlot(i) instanceof SlotResult)) && 
/*  755 */       (!this.g)) {
/*  756 */       this.playerConnection.sendPacket(new PacketPlayOutSetSlot(container.windowId, i, itemstack));
/*      */     }
/*      */   }
/*      */   
/*      */   public void updateInventory(Container container)
/*      */   {
/*  762 */     a(container, container.a());
/*      */   }
/*      */   
/*      */   public void a(Container container, List<ItemStack> list) {
/*  766 */     this.playerConnection.sendPacket(new PacketPlayOutWindowItems(container.windowId, list));
/*  767 */     this.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.inventory.getCarried()));
/*      */     
/*  769 */     if (EnumSet.of(InventoryType.CRAFTING, InventoryType.WORKBENCH).contains(container.getBukkitView().getType())) {
/*  770 */       this.playerConnection.sendPacket(new PacketPlayOutSetSlot(container.windowId, 0, container.getSlot(0).getItem()));
/*      */     }
/*      */   }
/*      */   
/*      */   public void setContainerData(Container container, int i, int j)
/*      */   {
/*  776 */     this.playerConnection.sendPacket(new PacketPlayOutWindowData(container.windowId, i, j));
/*      */   }
/*      */   
/*      */   public void setContainerData(Container container, IInventory iinventory) {
/*  780 */     for (int i = 0; i < iinventory.g(); i++) {
/*  781 */       this.playerConnection.sendPacket(new PacketPlayOutWindowData(container.windowId, i, iinventory.getProperty(i)));
/*      */     }
/*      */   }
/*      */   
/*      */   public void closeInventory()
/*      */   {
/*  787 */     CraftEventFactory.handleInventoryCloseEvent(this);
/*  788 */     this.playerConnection.sendPacket(new PacketPlayOutCloseWindow(this.activeContainer.windowId));
/*  789 */     p();
/*      */   }
/*      */   
/*      */   public void broadcastCarriedItem() {
/*  793 */     if (!this.g) {
/*  794 */       this.playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, this.inventory.getCarried()));
/*      */     }
/*      */   }
/*      */   
/*      */   public void p() {
/*  799 */     this.activeContainer.b(this);
/*  800 */     this.activeContainer = this.defaultContainer;
/*      */   }
/*      */   
/*      */   public void a(float f, float f1, boolean flag, boolean flag1) {
/*  804 */     if (this.vehicle != null) {
/*  805 */       if ((f >= -1.0F) && (f <= 1.0F)) {
/*  806 */         this.aZ = f;
/*      */       }
/*      */       
/*  809 */       if ((f1 >= -1.0F) && (f1 <= 1.0F)) {
/*  810 */         this.ba = f1;
/*      */       }
/*      */       
/*  813 */       this.aY = flag;
/*  814 */       setSneaking(flag1);
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(Statistic statistic, int i)
/*      */   {
/*  820 */     if (statistic != null) {
/*  821 */       this.bK.b(this, statistic, i);
/*  822 */       Iterator iterator = getScoreboard().getObjectivesForCriteria(statistic.k()).iterator();
/*      */       
/*  824 */       while (iterator.hasNext()) {
/*  825 */         ScoreboardObjective scoreboardobjective = (ScoreboardObjective)iterator.next();
/*      */         
/*  827 */         getScoreboard().getPlayerScoreForObjective(getName(), scoreboardobjective).addScore(i);
/*      */       }
/*      */       
/*  830 */       if (this.bK.e()) {
/*  831 */         this.bK.a(this);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void a(Statistic statistic)
/*      */   {
/*  838 */     if (statistic != null) {
/*  839 */       this.bK.setStatistic(this, statistic, 0);
/*  840 */       Iterator iterator = getScoreboard().getObjectivesForCriteria(statistic.k()).iterator();
/*      */       
/*  842 */       while (iterator.hasNext()) {
/*  843 */         ScoreboardObjective scoreboardobjective = (ScoreboardObjective)iterator.next();
/*      */         
/*  845 */         getScoreboard().getPlayerScoreForObjective(getName(), scoreboardobjective).setScore(0);
/*      */       }
/*      */       
/*  848 */       if (this.bK.e()) {
/*  849 */         this.bK.a(this);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void q()
/*      */   {
/*  856 */     if (this.passenger != null) {
/*  857 */       this.passenger.mount(this);
/*      */     }
/*      */     
/*  860 */     if (this.sleeping) {
/*  861 */       a(true, false, false);
/*      */     }
/*      */   }
/*      */   
/*      */   public void triggerHealthUpdate()
/*      */   {
/*  867 */     this.bM = -1.0E8F;
/*  868 */     this.lastSentExp = -1;
/*      */   }
/*      */   
/*      */   public void sendMessage(IChatBaseComponent[] ichatbasecomponent) {
/*      */     IChatBaseComponent[] arrayOfIChatBaseComponent;
/*  873 */     int i = (arrayOfIChatBaseComponent = ichatbasecomponent).length; for (int j = 0; j < i; j++) { IChatBaseComponent component = arrayOfIChatBaseComponent[j];
/*  874 */       sendMessage(component);
/*      */     }
/*      */   }
/*      */   
/*      */   public void b(IChatBaseComponent ichatbasecomponent)
/*      */   {
/*  880 */     this.playerConnection.sendPacket(new PacketPlayOutChat(ichatbasecomponent));
/*      */   }
/*      */   
/*      */   protected void s() {
/*  884 */     this.playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte)9));
/*  885 */     super.s();
/*      */   }
/*      */   
/*      */   public void a(ItemStack itemstack, int i) {
/*  889 */     super.a(itemstack, i);
/*  890 */     if ((itemstack != null) && (itemstack.getItem() != null) && (itemstack.getItem().e(itemstack) == EnumAnimation.EAT)) {
/*  891 */       u().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(this, 3));
/*      */     }
/*      */   }
/*      */   
/*      */   public void copyTo(EntityHuman entityhuman, boolean flag)
/*      */   {
/*  897 */     super.copyTo(entityhuman, flag);
/*  898 */     this.lastSentExp = -1;
/*  899 */     this.bM = -1.0F;
/*  900 */     this.bN = -1;
/*  901 */     this.removeQueue.addAll(((EntityPlayer)entityhuman).removeQueue);
/*      */   }
/*      */   
/*      */   protected void a(MobEffect mobeffect) {
/*  905 */     super.a(mobeffect);
/*  906 */     this.playerConnection.sendPacket(new PacketPlayOutEntityEffect(getId(), mobeffect));
/*      */   }
/*      */   
/*      */   protected void a(MobEffect mobeffect, boolean flag) {
/*  910 */     super.a(mobeffect, flag);
/*  911 */     this.playerConnection.sendPacket(new PacketPlayOutEntityEffect(getId(), mobeffect));
/*      */   }
/*      */   
/*      */   protected void b(MobEffect mobeffect) {
/*  915 */     super.b(mobeffect);
/*  916 */     this.playerConnection.sendPacket(new PacketPlayOutRemoveEntityEffect(getId(), mobeffect));
/*      */   }
/*      */   
/*      */   public void enderTeleportTo(double d0, double d1, double d2) {
/*  920 */     this.playerConnection.a(d0, d1, d2, this.yaw, this.pitch);
/*      */   }
/*      */   
/*      */   public void b(Entity entity) {
/*  924 */     u().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(entity, 4));
/*      */   }
/*      */   
/*      */   public void c(Entity entity) {
/*  928 */     u().getTracker().sendPacketToEntity(this, new PacketPlayOutAnimation(entity, 5));
/*      */   }
/*      */   
/*      */   public void updateAbilities() {
/*  932 */     if (this.playerConnection != null) {
/*  933 */       this.playerConnection.sendPacket(new PacketPlayOutAbilities(this.abilities));
/*  934 */       B();
/*      */     }
/*      */   }
/*      */   
/*      */   public WorldServer u() {
/*  939 */     return (WorldServer)this.world;
/*      */   }
/*      */   
/*      */   public void a(WorldSettings.EnumGamemode worldsettings_enumgamemode) {
/*  943 */     getBukkitEntity().setGameMode(org.bukkit.GameMode.getByValue(worldsettings_enumgamemode.getId()));
/*      */   }
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
/*      */   public boolean isSpectator()
/*      */   {
/*  959 */     return this.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.SPECTATOR;
/*      */   }
/*      */   
/*      */   public void sendMessage(IChatBaseComponent ichatbasecomponent) {
/*  963 */     this.playerConnection.sendPacket(new PacketPlayOutChat(ichatbasecomponent));
/*      */   }
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
/*      */   public boolean a(int i, String s)
/*      */   {
/*  982 */     if ("@".equals(s)) {
/*  983 */       return getBukkitEntity().hasPermission("minecraft.command.selector");
/*      */     }
/*  985 */     return true;
/*      */   }
/*      */   
/*      */   public String w()
/*      */   {
/*  990 */     String s = this.playerConnection.networkManager.getSocketAddress().toString();
/*      */     
/*  992 */     s = s.substring(s.indexOf("/") + 1);
/*  993 */     s = s.substring(0, s.indexOf(":"));
/*  994 */     return s;
/*      */   }
/*      */   
/*      */   public void a(PacketPlayInSettings packetplayinsettings) {
/*  998 */     this.locale = packetplayinsettings.a();
/*  999 */     this.bR = packetplayinsettings.c();
/* 1000 */     this.bS = packetplayinsettings.d();
/* 1001 */     getDataWatcher().watch(10, Byte.valueOf((byte)packetplayinsettings.e()));
/*      */   }
/*      */   
/*      */   public EntityHuman.EnumChatVisibility getChatFlags() {
/* 1005 */     return this.bR;
/*      */   }
/*      */   
/*      */   public void setResourcePack(String s, String s1) {
/* 1009 */     this.playerConnection.sendPacket(new PacketPlayOutResourcePackSend(s, s1));
/*      */   }
/*      */   
/*      */   public BlockPosition getChunkCoordinates() {
/* 1013 */     return new BlockPosition(this.locX, this.locY + 0.5D, this.locZ);
/*      */   }
/*      */   
/*      */   public void resetIdleTimer() {
/* 1017 */     this.bT = MinecraftServer.az();
/*      */   }
/*      */   
/*      */   public ServerStatisticManager getStatisticManager() {
/* 1021 */     return this.bK;
/*      */   }
/*      */   
/*      */   public void d(Entity entity) {
/* 1025 */     if ((entity instanceof EntityHuman)) {
/* 1026 */       this.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(new int[] { entity.getId() }));
/*      */     } else {
/* 1028 */       this.removeQueue.add(Integer.valueOf(entity.getId()));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void B()
/*      */   {
/* 1034 */     if (isSpectator()) {
/* 1035 */       bj();
/* 1036 */       setInvisible(true);
/*      */     } else {
/* 1038 */       super.B();
/*      */     }
/*      */     
/* 1041 */     u().getTracker().a(this);
/*      */   }
/*      */   
/*      */   public Entity C() {
/* 1045 */     return this.bU == null ? this : this.bU;
/*      */   }
/*      */   
/*      */   public void setSpectatorTarget(Entity entity) {
/* 1049 */     Entity entity1 = C();
/*      */     
/* 1051 */     this.bU = (entity == null ? this : entity);
/* 1052 */     if (entity1 != this.bU) {
/* 1053 */       this.playerConnection.sendPacket(new PacketPlayOutCamera(this.bU));
/* 1054 */       enderTeleportTo(this.bU.locX, this.bU.locY, this.bU.locZ);
/*      */     }
/*      */   }
/*      */   
/*      */   public void attack(Entity entity)
/*      */   {
/* 1060 */     if (this.playerInteractManager.getGameMode() == WorldSettings.EnumGamemode.SPECTATOR) {
/* 1061 */       setSpectatorTarget(entity);
/*      */     } else {
/* 1063 */       super.attack(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   public long D()
/*      */   {
/* 1069 */     return this.bT;
/*      */   }
/*      */   
/*      */   public IChatBaseComponent getPlayerListName() {
/* 1073 */     return this.listName;
/*      */   }
/*      */   
/*      */ 
/* 1077 */   public long timeOffset = 0L;
/* 1078 */   public boolean relativeTime = true;
/*      */   
/*      */   public long getPlayerTime() {
/* 1081 */     if (this.relativeTime)
/*      */     {
/* 1083 */       return this.world.getDayTime() + this.timeOffset;
/*      */     }
/*      */     
/* 1086 */     return this.world.getDayTime() - this.world.getDayTime() % 24000L + this.timeOffset;
/*      */   }
/*      */   
/*      */ 
/* 1090 */   public WeatherType weather = null;
/*      */   private float pluginRainPosition;
/*      */   
/* 1093 */   public WeatherType getPlayerWeather() { return this.weather; }
/*      */   
/*      */   public void setPlayerWeather(WeatherType type, boolean plugin)
/*      */   {
/* 1097 */     if ((!plugin) && (this.weather != null)) {
/* 1098 */       return;
/*      */     }
/*      */     
/* 1101 */     if (plugin) {
/* 1102 */       this.weather = type;
/*      */     }
/*      */     
/* 1105 */     if (type == WeatherType.DOWNFALL) {
/* 1106 */       this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(2, 0.0F));
/*      */     } else {
/* 1108 */       this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(1, 0.0F));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private float pluginRainPositionPrevious;
/*      */   public void updateWeather(float oldRain, float newRain, float oldThunder, float newThunder)
/*      */   {
/* 1116 */     if (this.weather == null)
/*      */     {
/* 1118 */       if (oldRain != newRain) {
/* 1119 */         this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, newRain));
/*      */       }
/*      */       
/*      */     }
/* 1123 */     else if (this.pluginRainPositionPrevious != this.pluginRainPosition) {
/* 1124 */       this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(7, this.pluginRainPosition));
/*      */     }
/*      */     
/*      */ 
/* 1128 */     if (oldThunder != newThunder) {
/* 1129 */       if ((this.weather == WeatherType.DOWNFALL) || (this.weather == null)) {
/* 1130 */         this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, newThunder));
/*      */       } else {
/* 1132 */         this.playerConnection.sendPacket(new PacketPlayOutGameStateChange(8, 0.0F));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public void tickWeather() {
/* 1138 */     if (this.weather == null) { return;
/*      */     }
/* 1140 */     this.pluginRainPositionPrevious = this.pluginRainPosition;
/* 1141 */     if (this.weather == WeatherType.DOWNFALL) {
/* 1142 */       this.pluginRainPosition = ((float)(this.pluginRainPosition + 0.01D));
/*      */     } else {
/* 1144 */       this.pluginRainPosition = ((float)(this.pluginRainPosition - 0.01D));
/*      */     }
/*      */     
/* 1147 */     this.pluginRainPosition = MathHelper.a(this.pluginRainPosition, 0.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public void resetPlayerWeather() {
/* 1151 */     this.weather = null;
/* 1152 */     setPlayerWeather(this.world.getWorldData().hasStorm() ? WeatherType.DOWNFALL : WeatherType.CLEAR, false);
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/* 1157 */     return super.toString() + "(" + getName() + " at " + this.locX + "," + this.locY + "," + this.locZ + ")";
/*      */   }
/*      */   
/*      */   public void reset() {
/* 1161 */     float exp = 0.0F;
/* 1162 */     boolean keepInventory = this.world.getGameRules().getBoolean("keepInventory");
/*      */     
/* 1164 */     if ((this.keepLevel) || (keepInventory)) {
/* 1165 */       exp = this.exp;
/* 1166 */       this.newTotalExp = this.expTotal;
/* 1167 */       this.newLevel = this.expLevel;
/*      */     }
/*      */     
/* 1170 */     setHealth(getMaxHealth());
/* 1171 */     this.fireTicks = 0;
/* 1172 */     this.fallDistance = 0.0F;
/* 1173 */     this.foodData = new FoodMetaData(this);
/* 1174 */     this.expLevel = this.newLevel;
/* 1175 */     this.expTotal = this.newTotalExp;
/* 1176 */     this.exp = 0.0F;
/* 1177 */     this.deathTicks = 0;
/* 1178 */     removeAllEffects();
/* 1179 */     this.updateEffects = true;
/* 1180 */     this.activeContainer = this.defaultContainer;
/* 1181 */     this.killer = null;
/* 1182 */     this.lastDamager = null;
/* 1183 */     this.combatTracker = new CombatTracker(this);
/* 1184 */     this.lastSentExp = -1;
/* 1185 */     if ((this.keepLevel) || (keepInventory)) {
/* 1186 */       this.exp = exp;
/*      */     } else {
/* 1188 */       giveExp(this.newExp);
/*      */     }
/* 1190 */     this.keepLevel = false;
/*      */   }
/*      */   
/*      */   public CraftPlayer getBukkitEntity()
/*      */   {
/* 1195 */     return (CraftPlayer)super.getBukkitEntity();
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */