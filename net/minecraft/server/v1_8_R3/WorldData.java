/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.concurrent.Callable;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.event.weather.ThunderChangeEvent;
/*     */ import org.bukkit.event.weather.WeatherChangeEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class WorldData
/*     */ {
/*  12 */   public static final EnumDifficulty a = EnumDifficulty.NORMAL;
/*     */   private long b;
/*     */   private WorldType c;
/*     */   private String d;
/*     */   private int e;
/*     */   private int f;
/*     */   private int g;
/*     */   private long h;
/*     */   private long i;
/*     */   private long j;
/*     */   private long k;
/*     */   private NBTTagCompound l;
/*     */   private int m;
/*     */   private String n;
/*     */   private int o;
/*     */   private int p;
/*     */   private boolean q;
/*     */   private int r;
/*     */   private boolean s;
/*     */   private int t;
/*     */   private WorldSettings.EnumGamemode u;
/*     */   private boolean v;
/*     */   private boolean w;
/*     */   private boolean x;
/*     */   private boolean y;
/*     */   private EnumDifficulty z;
/*     */   private boolean A;
/*     */   private double B;
/*     */   private double C;
/*     */   private double D;
/*     */   private long E;
/*     */   private double F;
/*     */   private double G;
/*     */   private double H;
/*     */   private int I;
/*     */   private int J;
/*     */   private GameRules K;
/*     */   public WorldServer world;
/*     */   
/*     */   protected WorldData() {
/*  52 */     this.c = WorldType.NORMAL;
/*  53 */     this.d = "";
/*  54 */     this.B = 0.0D;
/*  55 */     this.C = 0.0D;
/*  56 */     this.D = 6.0E7D;
/*  57 */     this.E = 0L;
/*  58 */     this.F = 0.0D;
/*  59 */     this.G = 5.0D;
/*  60 */     this.H = 0.2D;
/*  61 */     this.I = 5;
/*  62 */     this.J = 15;
/*  63 */     this.K = new GameRules();
/*     */   }
/*     */   
/*     */   public WorldData(NBTTagCompound nbttagcompound) {
/*  67 */     this.c = WorldType.NORMAL;
/*  68 */     this.d = "";
/*  69 */     this.B = 0.0D;
/*  70 */     this.C = 0.0D;
/*  71 */     this.D = 6.0E7D;
/*  72 */     this.E = 0L;
/*  73 */     this.F = 0.0D;
/*  74 */     this.G = 5.0D;
/*  75 */     this.H = 0.2D;
/*  76 */     this.I = 5;
/*  77 */     this.J = 15;
/*  78 */     this.K = new GameRules();
/*  79 */     this.b = nbttagcompound.getLong("RandomSeed");
/*  80 */     if (nbttagcompound.hasKeyOfType("generatorName", 8)) {
/*  81 */       String s = nbttagcompound.getString("generatorName");
/*     */       
/*  83 */       this.c = WorldType.getType(s);
/*  84 */       if (this.c == null) {
/*  85 */         this.c = WorldType.NORMAL;
/*  86 */       } else if (this.c.f()) {
/*  87 */         int i = 0;
/*     */         
/*  89 */         if (nbttagcompound.hasKeyOfType("generatorVersion", 99)) {
/*  90 */           i = nbttagcompound.getInt("generatorVersion");
/*     */         }
/*     */         
/*  93 */         this.c = this.c.a(i);
/*     */       }
/*     */       
/*  96 */       if (nbttagcompound.hasKeyOfType("generatorOptions", 8)) {
/*  97 */         this.d = nbttagcompound.getString("generatorOptions");
/*     */       }
/*     */     }
/*     */     
/* 101 */     this.u = WorldSettings.EnumGamemode.getById(nbttagcompound.getInt("GameType"));
/* 102 */     if (nbttagcompound.hasKeyOfType("MapFeatures", 99)) {
/* 103 */       this.v = nbttagcompound.getBoolean("MapFeatures");
/*     */     } else {
/* 105 */       this.v = true;
/*     */     }
/*     */     
/* 108 */     this.e = nbttagcompound.getInt("SpawnX");
/* 109 */     this.f = nbttagcompound.getInt("SpawnY");
/* 110 */     this.g = nbttagcompound.getInt("SpawnZ");
/* 111 */     this.h = nbttagcompound.getLong("Time");
/* 112 */     if (nbttagcompound.hasKeyOfType("DayTime", 99)) {
/* 113 */       this.i = nbttagcompound.getLong("DayTime");
/*     */     } else {
/* 115 */       this.i = this.h;
/*     */     }
/*     */     
/* 118 */     this.j = nbttagcompound.getLong("LastPlayed");
/* 119 */     this.k = nbttagcompound.getLong("SizeOnDisk");
/* 120 */     this.n = nbttagcompound.getString("LevelName");
/* 121 */     this.o = nbttagcompound.getInt("version");
/* 122 */     this.p = nbttagcompound.getInt("clearWeatherTime");
/* 123 */     this.r = nbttagcompound.getInt("rainTime");
/* 124 */     this.q = nbttagcompound.getBoolean("raining");
/* 125 */     this.t = nbttagcompound.getInt("thunderTime");
/* 126 */     this.s = nbttagcompound.getBoolean("thundering");
/* 127 */     this.w = nbttagcompound.getBoolean("hardcore");
/* 128 */     if (nbttagcompound.hasKeyOfType("initialized", 99)) {
/* 129 */       this.y = nbttagcompound.getBoolean("initialized");
/*     */     } else {
/* 131 */       this.y = true;
/*     */     }
/*     */     
/* 134 */     if (nbttagcompound.hasKeyOfType("allowCommands", 99)) {
/* 135 */       this.x = nbttagcompound.getBoolean("allowCommands");
/*     */     } else {
/* 137 */       this.x = (this.u == WorldSettings.EnumGamemode.CREATIVE);
/*     */     }
/*     */     
/* 140 */     if (nbttagcompound.hasKeyOfType("Player", 10)) {
/* 141 */       this.l = nbttagcompound.getCompound("Player");
/* 142 */       this.m = this.l.getInt("Dimension");
/*     */     }
/*     */     
/* 145 */     if (nbttagcompound.hasKeyOfType("GameRules", 10)) {
/* 146 */       this.K.a(nbttagcompound.getCompound("GameRules"));
/*     */     }
/*     */     
/* 149 */     if (nbttagcompound.hasKeyOfType("Difficulty", 99)) {
/* 150 */       this.z = EnumDifficulty.getById(nbttagcompound.getByte("Difficulty"));
/*     */     }
/*     */     
/* 153 */     if (nbttagcompound.hasKeyOfType("DifficultyLocked", 1)) {
/* 154 */       this.A = nbttagcompound.getBoolean("DifficultyLocked");
/*     */     }
/*     */     
/* 157 */     if (nbttagcompound.hasKeyOfType("BorderCenterX", 99)) {
/* 158 */       this.B = nbttagcompound.getDouble("BorderCenterX");
/*     */     }
/*     */     
/* 161 */     if (nbttagcompound.hasKeyOfType("BorderCenterZ", 99)) {
/* 162 */       this.C = nbttagcompound.getDouble("BorderCenterZ");
/*     */     }
/*     */     
/* 165 */     if (nbttagcompound.hasKeyOfType("BorderSize", 99)) {
/* 166 */       this.D = nbttagcompound.getDouble("BorderSize");
/*     */     }
/*     */     
/* 169 */     if (nbttagcompound.hasKeyOfType("BorderSizeLerpTime", 99)) {
/* 170 */       this.E = nbttagcompound.getLong("BorderSizeLerpTime");
/*     */     }
/*     */     
/* 173 */     if (nbttagcompound.hasKeyOfType("BorderSizeLerpTarget", 99)) {
/* 174 */       this.F = nbttagcompound.getDouble("BorderSizeLerpTarget");
/*     */     }
/*     */     
/* 177 */     if (nbttagcompound.hasKeyOfType("BorderSafeZone", 99)) {
/* 178 */       this.G = nbttagcompound.getDouble("BorderSafeZone");
/*     */     }
/*     */     
/* 181 */     if (nbttagcompound.hasKeyOfType("BorderDamagePerBlock", 99)) {
/* 182 */       this.H = nbttagcompound.getDouble("BorderDamagePerBlock");
/*     */     }
/*     */     
/* 185 */     if (nbttagcompound.hasKeyOfType("BorderWarningBlocks", 99)) {
/* 186 */       this.I = nbttagcompound.getInt("BorderWarningBlocks");
/*     */     }
/*     */     
/* 189 */     if (nbttagcompound.hasKeyOfType("BorderWarningTime", 99)) {
/* 190 */       this.J = nbttagcompound.getInt("BorderWarningTime");
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldData(WorldSettings worldsettings, String s)
/*     */   {
/* 196 */     this.c = WorldType.NORMAL;
/* 197 */     this.d = "";
/* 198 */     this.B = 0.0D;
/* 199 */     this.C = 0.0D;
/* 200 */     this.D = 6.0E7D;
/* 201 */     this.E = 0L;
/* 202 */     this.F = 0.0D;
/* 203 */     this.G = 5.0D;
/* 204 */     this.H = 0.2D;
/* 205 */     this.I = 5;
/* 206 */     this.J = 15;
/* 207 */     this.K = new GameRules();
/* 208 */     a(worldsettings);
/* 209 */     this.n = s;
/* 210 */     this.z = a;
/* 211 */     this.y = false;
/*     */   }
/*     */   
/*     */   public void a(WorldSettings worldsettings) {
/* 215 */     this.b = worldsettings.d();
/* 216 */     this.u = worldsettings.e();
/* 217 */     this.v = worldsettings.g();
/* 218 */     this.w = worldsettings.f();
/* 219 */     this.c = worldsettings.h();
/* 220 */     this.d = worldsettings.j();
/* 221 */     this.x = worldsettings.i();
/*     */   }
/*     */   
/*     */   public WorldData(WorldData worlddata) {
/* 225 */     this.c = WorldType.NORMAL;
/* 226 */     this.d = "";
/* 227 */     this.B = 0.0D;
/* 228 */     this.C = 0.0D;
/* 229 */     this.D = 6.0E7D;
/* 230 */     this.E = 0L;
/* 231 */     this.F = 0.0D;
/* 232 */     this.G = 5.0D;
/* 233 */     this.H = 0.2D;
/* 234 */     this.I = 5;
/* 235 */     this.J = 15;
/* 236 */     this.K = new GameRules();
/* 237 */     this.b = worlddata.b;
/* 238 */     this.c = worlddata.c;
/* 239 */     this.d = worlddata.d;
/* 240 */     this.u = worlddata.u;
/* 241 */     this.v = worlddata.v;
/* 242 */     this.e = worlddata.e;
/* 243 */     this.f = worlddata.f;
/* 244 */     this.g = worlddata.g;
/* 245 */     this.h = worlddata.h;
/* 246 */     this.i = worlddata.i;
/* 247 */     this.j = worlddata.j;
/* 248 */     this.k = worlddata.k;
/* 249 */     this.l = worlddata.l;
/* 250 */     this.m = worlddata.m;
/* 251 */     this.n = worlddata.n;
/* 252 */     this.o = worlddata.o;
/* 253 */     this.r = worlddata.r;
/* 254 */     this.q = worlddata.q;
/* 255 */     this.t = worlddata.t;
/* 256 */     this.s = worlddata.s;
/* 257 */     this.w = worlddata.w;
/* 258 */     this.x = worlddata.x;
/* 259 */     this.y = worlddata.y;
/* 260 */     this.K = worlddata.K;
/* 261 */     this.z = worlddata.z;
/* 262 */     this.A = worlddata.A;
/* 263 */     this.B = worlddata.B;
/* 264 */     this.C = worlddata.C;
/* 265 */     this.D = worlddata.D;
/* 266 */     this.E = worlddata.E;
/* 267 */     this.F = worlddata.F;
/* 268 */     this.G = worlddata.G;
/* 269 */     this.H = worlddata.H;
/* 270 */     this.J = worlddata.J;
/* 271 */     this.I = worlddata.I;
/*     */   }
/*     */   
/*     */   public NBTTagCompound a() {
/* 275 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 277 */     a(nbttagcompound, this.l);
/* 278 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public NBTTagCompound a(NBTTagCompound nbttagcompound) {
/* 282 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */     
/* 284 */     a(nbttagcompound1, nbttagcompound);
/* 285 */     return nbttagcompound1;
/*     */   }
/*     */   
/*     */   private void a(NBTTagCompound nbttagcompound, NBTTagCompound nbttagcompound1) {
/* 289 */     nbttagcompound.setLong("RandomSeed", this.b);
/* 290 */     nbttagcompound.setString("generatorName", this.c.name());
/* 291 */     nbttagcompound.setInt("generatorVersion", this.c.getVersion());
/* 292 */     nbttagcompound.setString("generatorOptions", this.d);
/* 293 */     nbttagcompound.setInt("GameType", this.u.getId());
/* 294 */     nbttagcompound.setBoolean("MapFeatures", this.v);
/* 295 */     nbttagcompound.setInt("SpawnX", this.e);
/* 296 */     nbttagcompound.setInt("SpawnY", this.f);
/* 297 */     nbttagcompound.setInt("SpawnZ", this.g);
/* 298 */     nbttagcompound.setLong("Time", this.h);
/* 299 */     nbttagcompound.setLong("DayTime", this.i);
/* 300 */     nbttagcompound.setLong("SizeOnDisk", this.k);
/* 301 */     nbttagcompound.setLong("LastPlayed", MinecraftServer.az());
/* 302 */     nbttagcompound.setString("LevelName", this.n);
/* 303 */     nbttagcompound.setInt("version", this.o);
/* 304 */     nbttagcompound.setInt("clearWeatherTime", this.p);
/* 305 */     nbttagcompound.setInt("rainTime", this.r);
/* 306 */     nbttagcompound.setBoolean("raining", this.q);
/* 307 */     nbttagcompound.setInt("thunderTime", this.t);
/* 308 */     nbttagcompound.setBoolean("thundering", this.s);
/* 309 */     nbttagcompound.setBoolean("hardcore", this.w);
/* 310 */     nbttagcompound.setBoolean("allowCommands", this.x);
/* 311 */     nbttagcompound.setBoolean("initialized", this.y);
/* 312 */     nbttagcompound.setDouble("BorderCenterX", this.B);
/* 313 */     nbttagcompound.setDouble("BorderCenterZ", this.C);
/* 314 */     nbttagcompound.setDouble("BorderSize", this.D);
/* 315 */     nbttagcompound.setLong("BorderSizeLerpTime", this.E);
/* 316 */     nbttagcompound.setDouble("BorderSafeZone", this.G);
/* 317 */     nbttagcompound.setDouble("BorderDamagePerBlock", this.H);
/* 318 */     nbttagcompound.setDouble("BorderSizeLerpTarget", this.F);
/* 319 */     nbttagcompound.setDouble("BorderWarningBlocks", this.I);
/* 320 */     nbttagcompound.setDouble("BorderWarningTime", this.J);
/* 321 */     if (this.z != null) {
/* 322 */       nbttagcompound.setByte("Difficulty", (byte)this.z.a());
/*     */     }
/*     */     
/* 325 */     nbttagcompound.setBoolean("DifficultyLocked", this.A);
/* 326 */     nbttagcompound.set("GameRules", this.K.a());
/* 327 */     if (nbttagcompound1 != null) {
/* 328 */       nbttagcompound.set("Player", nbttagcompound1);
/*     */     }
/*     */   }
/*     */   
/*     */   public long getSeed()
/*     */   {
/* 334 */     return this.b;
/*     */   }
/*     */   
/*     */   public int c() {
/* 338 */     return this.e;
/*     */   }
/*     */   
/*     */   public int d() {
/* 342 */     return this.f;
/*     */   }
/*     */   
/*     */   public int e() {
/* 346 */     return this.g;
/*     */   }
/*     */   
/*     */   public long getTime() {
/* 350 */     return this.h;
/*     */   }
/*     */   
/*     */   public long getDayTime() {
/* 354 */     return this.i;
/*     */   }
/*     */   
/*     */   public NBTTagCompound i() {
/* 358 */     return this.l;
/*     */   }
/*     */   
/*     */   public void setTime(long i) {
/* 362 */     this.h = i;
/*     */   }
/*     */   
/*     */   public void setDayTime(long i) {
/* 366 */     this.i = i;
/*     */   }
/*     */   
/*     */   public void setSpawn(BlockPosition blockposition) {
/* 370 */     this.e = blockposition.getX();
/* 371 */     this.f = blockposition.getY();
/* 372 */     this.g = blockposition.getZ();
/*     */   }
/*     */   
/*     */   public String getName() {
/* 376 */     return this.n;
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 380 */     this.n = s;
/*     */   }
/*     */   
/*     */   public int l() {
/* 384 */     return this.o;
/*     */   }
/*     */   
/*     */   public void e(int i) {
/* 388 */     this.o = i;
/*     */   }
/*     */   
/*     */   public int A() {
/* 392 */     return this.p;
/*     */   }
/*     */   
/*     */   public void i(int i) {
/* 396 */     this.p = i;
/*     */   }
/*     */   
/*     */   public boolean isThundering() {
/* 400 */     return this.s;
/*     */   }
/*     */   
/*     */   public void setThundering(boolean flag)
/*     */   {
/* 405 */     org.bukkit.World world = Bukkit.getWorld(getName());
/* 406 */     if (world != null) {
/* 407 */       ThunderChangeEvent thunder = new ThunderChangeEvent(world, flag);
/* 408 */       Bukkit.getServer().getPluginManager().callEvent(thunder);
/* 409 */       if (thunder.isCancelled()) {
/* 410 */         return;
/*     */       }
/*     */       
/* 413 */       setThunderDuration(0);
/*     */     }
/*     */     
/* 416 */     this.s = flag;
/*     */   }
/*     */   
/*     */   public int getThunderDuration() {
/* 420 */     return this.t;
/*     */   }
/*     */   
/*     */   public void setThunderDuration(int i) {
/* 424 */     this.t = i;
/*     */   }
/*     */   
/*     */   public boolean hasStorm() {
/* 428 */     return this.q;
/*     */   }
/*     */   
/*     */   public void setStorm(boolean flag)
/*     */   {
/* 433 */     org.bukkit.World world = Bukkit.getWorld(getName());
/* 434 */     if (world != null) {
/* 435 */       WeatherChangeEvent weather = new WeatherChangeEvent(world, flag);
/* 436 */       Bukkit.getServer().getPluginManager().callEvent(weather);
/* 437 */       if (weather.isCancelled()) {
/* 438 */         return;
/*     */       }
/*     */       
/* 441 */       setWeatherDuration(0);
/*     */     }
/*     */     
/* 444 */     this.q = flag;
/*     */   }
/*     */   
/*     */   public int getWeatherDuration() {
/* 448 */     return this.r;
/*     */   }
/*     */   
/*     */   public void setWeatherDuration(int i) {
/* 452 */     this.r = i;
/*     */   }
/*     */   
/*     */   public WorldSettings.EnumGamemode getGameType() {
/* 456 */     return this.u;
/*     */   }
/*     */   
/*     */   public boolean shouldGenerateMapFeatures() {
/* 460 */     return this.v;
/*     */   }
/*     */   
/*     */   public void f(boolean flag) {
/* 464 */     this.v = flag;
/*     */   }
/*     */   
/*     */   public void setGameType(WorldSettings.EnumGamemode worldsettings_enumgamemode) {
/* 468 */     this.u = worldsettings_enumgamemode;
/*     */   }
/*     */   
/*     */   public boolean isHardcore() {
/* 472 */     return this.w;
/*     */   }
/*     */   
/*     */   public void g(boolean flag) {
/* 476 */     this.w = flag;
/*     */   }
/*     */   
/*     */   public WorldType getType() {
/* 480 */     return this.c;
/*     */   }
/*     */   
/*     */   public void a(WorldType worldtype) {
/* 484 */     this.c = worldtype;
/*     */   }
/*     */   
/*     */   public String getGeneratorOptions() {
/* 488 */     return this.d;
/*     */   }
/*     */   
/*     */   public boolean v() {
/* 492 */     return this.x;
/*     */   }
/*     */   
/*     */   public void c(boolean flag) {
/* 496 */     this.x = flag;
/*     */   }
/*     */   
/*     */   public boolean w() {
/* 500 */     return this.y;
/*     */   }
/*     */   
/*     */   public void d(boolean flag) {
/* 504 */     this.y = flag;
/*     */   }
/*     */   
/*     */   public GameRules x() {
/* 508 */     return this.K;
/*     */   }
/*     */   
/*     */   public double C() {
/* 512 */     return this.B;
/*     */   }
/*     */   
/*     */   public double D() {
/* 516 */     return this.C;
/*     */   }
/*     */   
/*     */   public double E() {
/* 520 */     return this.D;
/*     */   }
/*     */   
/*     */   public void a(double d0) {
/* 524 */     this.D = d0;
/*     */   }
/*     */   
/*     */   public long F() {
/* 528 */     return this.E;
/*     */   }
/*     */   
/*     */   public void e(long i) {
/* 532 */     this.E = i;
/*     */   }
/*     */   
/*     */   public double G() {
/* 536 */     return this.F;
/*     */   }
/*     */   
/*     */   public void b(double d0) {
/* 540 */     this.F = d0;
/*     */   }
/*     */   
/*     */   public void c(double d0) {
/* 544 */     this.C = d0;
/*     */   }
/*     */   
/*     */   public void d(double d0) {
/* 548 */     this.B = d0;
/*     */   }
/*     */   
/*     */   public double H() {
/* 552 */     return this.G;
/*     */   }
/*     */   
/*     */   public void e(double d0) {
/* 556 */     this.G = d0;
/*     */   }
/*     */   
/*     */   public double I() {
/* 560 */     return this.H;
/*     */   }
/*     */   
/*     */   public void f(double d0) {
/* 564 */     this.H = d0;
/*     */   }
/*     */   
/*     */   public int J() {
/* 568 */     return this.I;
/*     */   }
/*     */   
/*     */   public int K() {
/* 572 */     return this.J;
/*     */   }
/*     */   
/*     */   public void j(int i) {
/* 576 */     this.I = i;
/*     */   }
/*     */   
/*     */   public void k(int i) {
/* 580 */     this.J = i;
/*     */   }
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 584 */     return this.z;
/*     */   }
/*     */   
/*     */   public void setDifficulty(EnumDifficulty enumdifficulty) {
/* 588 */     this.z = enumdifficulty;
/*     */     
/* 590 */     PacketPlayOutServerDifficulty packet = new PacketPlayOutServerDifficulty(getDifficulty(), isDifficultyLocked());
/* 591 */     for (EntityPlayer player : this.world.players) {
/* 592 */       player.playerConnection.sendPacket(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isDifficultyLocked()
/*     */   {
/* 598 */     return this.A;
/*     */   }
/*     */   
/*     */   public void e(boolean flag) {
/* 602 */     this.A = flag;
/*     */   }
/*     */   
/*     */   public void a(CrashReportSystemDetails crashreportsystemdetails) {
/* 606 */     crashreportsystemdetails.a("Level seed", new Callable() {
/*     */       public String a() throws Exception {
/* 608 */         return String.valueOf(WorldData.this.getSeed());
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 612 */         return a();
/*     */       }
/* 614 */     });
/* 615 */     crashreportsystemdetails.a("Level generator", new Callable() {
/*     */       public String a() throws Exception {
/* 617 */         return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[] { Integer.valueOf(WorldData.this.c.g()), WorldData.this.c.name(), Integer.valueOf(WorldData.this.c.getVersion()), Boolean.valueOf(WorldData.this.v) });
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 621 */         return a();
/*     */       }
/* 623 */     });
/* 624 */     crashreportsystemdetails.a("Level generator options", new Callable() {
/*     */       public String a() throws Exception {
/* 626 */         return WorldData.this.d;
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 630 */         return a();
/*     */       }
/* 632 */     });
/* 633 */     crashreportsystemdetails.a("Level spawn location", new Callable() {
/*     */       public String a() throws Exception {
/* 635 */         return CrashReportSystemDetails.a(WorldData.this.e, WorldData.this.f, WorldData.this.g);
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 639 */         return a();
/*     */       }
/* 641 */     });
/* 642 */     crashreportsystemdetails.a("Level time", new Callable() {
/*     */       public String a() throws Exception {
/* 644 */         return String.format("%d game time, %d day time", new Object[] { Long.valueOf(WorldData.this.h), Long.valueOf(WorldData.this.i) });
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 648 */         return a();
/*     */       }
/* 650 */     });
/* 651 */     crashreportsystemdetails.a("Level dimension", new Callable() {
/*     */       public String a() throws Exception {
/* 653 */         return String.valueOf(WorldData.this.m);
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 657 */         return a();
/*     */       }
/* 659 */     });
/* 660 */     crashreportsystemdetails.a("Level storage version", new Callable() {
/*     */       public String a() throws Exception {
/* 662 */         String s = "Unknown?";
/*     */         try
/*     */         {
/* 665 */           switch (WorldData.this.o) {
/*     */           case 19132: 
/* 667 */             s = "McRegion";
/* 668 */             break;
/*     */           
/*     */           case 19133: 
/* 671 */             s = "Anvil";
/*     */           }
/*     */           
/*     */         }
/*     */         catch (Throwable localThrowable) {}
/*     */         
/* 677 */         return String.format("0x%05X - %s", new Object[] { Integer.valueOf(WorldData.this.o), s });
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 681 */         return a();
/*     */       }
/* 683 */     });
/* 684 */     crashreportsystemdetails.a("Level weather", new Callable() {
/*     */       public String a() throws Exception {
/* 686 */         return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[] { Integer.valueOf(WorldData.this.r), Boolean.valueOf(WorldData.this.q), Integer.valueOf(WorldData.this.t), Boolean.valueOf(WorldData.this.s) });
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 690 */         return a();
/*     */       }
/* 692 */     });
/* 693 */     crashreportsystemdetails.a("Level game mode", new Callable() {
/*     */       public String a() throws Exception {
/* 695 */         return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { WorldData.this.u.b(), Integer.valueOf(WorldData.this.u.getId()), Boolean.valueOf(WorldData.this.w), Boolean.valueOf(WorldData.this.x) });
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 699 */         return a();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public void checkName(String name)
/*     */   {
/* 706 */     if (!this.n.equals(name)) {
/* 707 */       this.n = name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */