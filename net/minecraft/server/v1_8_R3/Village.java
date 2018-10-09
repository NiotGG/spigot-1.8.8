/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ public class Village
/*     */ {
/*     */   private World a;
/*  13 */   private final List<VillageDoor> b = com.google.common.collect.Lists.newArrayList();
/*     */   private BlockPosition c;
/*     */   private BlockPosition d;
/*     */   private int e;
/*     */   private int f;
/*     */   private int g;
/*     */   private int h;
/*     */   private int i;
/*     */   private TreeMap<String, Integer> j;
/*     */   private List<Aggressor> k;
/*     */   private int l;
/*     */   
/*     */   public Village() {
/*  26 */     this.c = BlockPosition.ZERO;
/*  27 */     this.d = BlockPosition.ZERO;
/*  28 */     this.j = new TreeMap();
/*  29 */     this.k = com.google.common.collect.Lists.newArrayList();
/*     */   }
/*     */   
/*     */   public Village(World world) {
/*  33 */     this.c = BlockPosition.ZERO;
/*  34 */     this.d = BlockPosition.ZERO;
/*  35 */     this.j = new TreeMap();
/*  36 */     this.k = com.google.common.collect.Lists.newArrayList();
/*  37 */     this.a = world;
/*     */   }
/*     */   
/*     */   public void a(World world) {
/*  41 */     this.a = world;
/*     */   }
/*     */   
/*     */   public void a(int i) {
/*  45 */     this.g = i;
/*  46 */     m();
/*  47 */     l();
/*  48 */     if (i % 20 == 0) {
/*  49 */       k();
/*     */     }
/*     */     
/*  52 */     if (i % 30 == 0) {
/*  53 */       j();
/*     */     }
/*     */     
/*  56 */     int j = this.h / 10;
/*     */     
/*  58 */     if ((this.l < j) && (this.b.size() > 20) && (this.a.random.nextInt(7000) == 0)) {
/*  59 */       Vec3D vec3d = a(this.d, 2, 4, 2);
/*     */       
/*  61 */       if (vec3d != null) {
/*  62 */         EntityIronGolem entityirongolem = new EntityIronGolem(this.a);
/*     */         
/*  64 */         entityirongolem.setPosition(vec3d.a, vec3d.b, vec3d.c);
/*  65 */         this.a.addEntity(entityirongolem, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.VILLAGE_DEFENSE);
/*  66 */         this.l += 1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private Vec3D a(BlockPosition blockposition, int i, int j, int k)
/*     */   {
/*  73 */     for (int l = 0; l < 10; l++) {
/*  74 */       BlockPosition blockposition1 = blockposition.a(this.a.random.nextInt(16) - 8, this.a.random.nextInt(6) - 3, this.a.random.nextInt(16) - 8);
/*     */       
/*  76 */       if ((a(blockposition1)) && (a(new BlockPosition(i, j, k), blockposition1))) {
/*  77 */         return new Vec3D(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
/*     */       }
/*     */     }
/*     */     
/*  81 */     return null;
/*     */   }
/*     */   
/*     */   private boolean a(BlockPosition blockposition, BlockPosition blockposition1) {
/*  85 */     if (!World.a(this.a, blockposition1.down())) {
/*  86 */       return false;
/*     */     }
/*  88 */     int i = blockposition1.getX() - blockposition.getX() / 2;
/*  89 */     int j = blockposition1.getZ() - blockposition.getZ() / 2;
/*     */     
/*  91 */     for (int k = i; k < i + blockposition.getX(); k++) {
/*  92 */       for (int l = blockposition1.getY(); l < blockposition1.getY() + blockposition.getY(); l++) {
/*  93 */         for (int i1 = j; i1 < j + blockposition.getZ(); i1++) {
/*  94 */           if (this.a.getType(new BlockPosition(k, l, i1)).getBlock().isOccluding()) {
/*  95 */             return false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   private void j()
/*     */   {
/* 106 */     List list = this.a.a(EntityIronGolem.class, new AxisAlignedBB(this.d.getX() - this.e, this.d.getY() - 4, this.d.getZ() - this.e, this.d.getX() + this.e, this.d.getY() + 4, this.d.getZ() + this.e));
/*     */     
/* 108 */     this.l = list.size();
/*     */   }
/*     */   
/*     */   private void k() {
/* 112 */     List list = this.a.a(EntityVillager.class, new AxisAlignedBB(this.d.getX() - this.e, this.d.getY() - 4, this.d.getZ() - this.e, this.d.getX() + this.e, this.d.getY() + 4, this.d.getZ() + this.e));
/*     */     
/* 114 */     this.h = list.size();
/* 115 */     if (this.h == 0) {
/* 116 */       this.j.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public BlockPosition a()
/*     */   {
/* 122 */     return this.d;
/*     */   }
/*     */   
/*     */   public int b() {
/* 126 */     return this.e;
/*     */   }
/*     */   
/*     */   public int c() {
/* 130 */     return this.b.size();
/*     */   }
/*     */   
/*     */   public int d() {
/* 134 */     return this.g - this.f;
/*     */   }
/*     */   
/*     */   public int e() {
/* 138 */     return this.h;
/*     */   }
/*     */   
/*     */   public boolean a(BlockPosition blockposition) {
/* 142 */     return this.d.i(blockposition) < this.e * this.e;
/*     */   }
/*     */   
/*     */   public List<VillageDoor> f() {
/* 146 */     return this.b;
/*     */   }
/*     */   
/*     */   public VillageDoor b(BlockPosition blockposition) {
/* 150 */     VillageDoor villagedoor = null;
/* 151 */     int i = Integer.MAX_VALUE;
/* 152 */     Iterator iterator = this.b.iterator();
/*     */     
/* 154 */     while (iterator.hasNext()) {
/* 155 */       VillageDoor villagedoor1 = (VillageDoor)iterator.next();
/* 156 */       int j = villagedoor1.a(blockposition);
/*     */       
/* 158 */       if (j < i) {
/* 159 */         villagedoor = villagedoor1;
/* 160 */         i = j;
/*     */       }
/*     */     }
/*     */     
/* 164 */     return villagedoor;
/*     */   }
/*     */   
/*     */   public VillageDoor c(BlockPosition blockposition) {
/* 168 */     VillageDoor villagedoor = null;
/* 169 */     int i = Integer.MAX_VALUE;
/* 170 */     Iterator iterator = this.b.iterator();
/*     */     
/* 172 */     while (iterator.hasNext()) {
/* 173 */       VillageDoor villagedoor1 = (VillageDoor)iterator.next();
/* 174 */       int j = villagedoor1.a(blockposition);
/*     */       
/* 176 */       if (j > 256) {
/* 177 */         j *= 1000;
/*     */       } else {
/* 179 */         j = villagedoor1.c();
/*     */       }
/*     */       
/* 182 */       if (j < i) {
/* 183 */         villagedoor = villagedoor1;
/* 184 */         i = j;
/*     */       }
/*     */     }
/*     */     
/* 188 */     return villagedoor;
/*     */   }
/*     */   
/*     */   public VillageDoor e(BlockPosition blockposition) {
/* 192 */     if (this.d.i(blockposition) > this.e * this.e) {
/* 193 */       return null;
/*     */     }
/* 195 */     Iterator iterator = this.b.iterator();
/*     */     
/*     */     VillageDoor villagedoor;
/*     */     do
/*     */     {
/* 200 */       if (!iterator.hasNext()) {
/* 201 */         return null;
/*     */       }
/*     */       
/* 204 */       villagedoor = (VillageDoor)iterator.next();
/* 205 */     } while ((villagedoor.d().getX() != blockposition.getX()) || (villagedoor.d().getZ() != blockposition.getZ()) || (Math.abs(villagedoor.d().getY() - blockposition.getY()) > 1));
/*     */     
/* 207 */     return villagedoor;
/*     */   }
/*     */   
/*     */   public void a(VillageDoor villagedoor)
/*     */   {
/* 212 */     this.b.add(villagedoor);
/* 213 */     this.c = this.c.a(villagedoor.d());
/* 214 */     n();
/* 215 */     this.f = villagedoor.h();
/*     */   }
/*     */   
/*     */   public boolean g() {
/* 219 */     return this.b.isEmpty();
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving) {
/* 223 */     Iterator iterator = this.k.iterator();
/*     */     
/*     */     Aggressor village_aggressor;
/*     */     do
/*     */     {
/* 228 */       if (!iterator.hasNext()) {
/* 229 */         this.k.add(new Aggressor(entityliving, this.g));
/* 230 */         return;
/*     */       }
/*     */       
/* 233 */       village_aggressor = (Aggressor)iterator.next();
/* 234 */     } while (village_aggressor.a != entityliving);
/*     */     
/* 236 */     village_aggressor.b = this.g;
/*     */   }
/*     */   
/*     */   public EntityLiving b(EntityLiving entityliving) {
/* 240 */     double d0 = Double.MAX_VALUE;
/* 241 */     Aggressor village_aggressor = null;
/*     */     
/* 243 */     for (int i = 0; i < this.k.size(); i++) {
/* 244 */       Aggressor village_aggressor1 = (Aggressor)this.k.get(i);
/* 245 */       double d1 = village_aggressor1.a.h(entityliving);
/*     */       
/* 247 */       if (d1 <= d0) {
/* 248 */         village_aggressor = village_aggressor1;
/* 249 */         d0 = d1;
/*     */       }
/*     */     }
/*     */     
/* 253 */     return village_aggressor != null ? village_aggressor.a : null;
/*     */   }
/*     */   
/*     */   public EntityHuman c(EntityLiving entityliving) {
/* 257 */     double d0 = Double.MAX_VALUE;
/* 258 */     EntityHuman entityhuman = null;
/* 259 */     Iterator iterator = this.j.keySet().iterator();
/*     */     
/* 261 */     while (iterator.hasNext()) {
/* 262 */       String s = (String)iterator.next();
/*     */       
/* 264 */       if (d(s)) {
/* 265 */         EntityHuman entityhuman1 = this.a.a(s);
/*     */         
/* 267 */         if (entityhuman1 != null) {
/* 268 */           double d1 = entityhuman1.h(entityliving);
/*     */           
/* 270 */           if (d1 <= d0) {
/* 271 */             entityhuman = entityhuman1;
/* 272 */             d0 = d1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 278 */     return entityhuman;
/*     */   }
/*     */   
/*     */   private void l() {
/* 282 */     Iterator iterator = this.k.iterator();
/*     */     
/* 284 */     while (iterator.hasNext()) {
/* 285 */       Aggressor village_aggressor = (Aggressor)iterator.next();
/*     */       
/* 287 */       if ((!village_aggressor.a.isAlive()) || (Math.abs(this.g - village_aggressor.b) > 300)) {
/* 288 */         iterator.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void m()
/*     */   {
/* 295 */     boolean flag = false;
/* 296 */     boolean flag1 = this.a.random.nextInt(50) == 0;
/* 297 */     Iterator iterator = this.b.iterator();
/*     */     
/* 299 */     while (iterator.hasNext()) {
/* 300 */       VillageDoor villagedoor = (VillageDoor)iterator.next();
/*     */       
/* 302 */       if (flag1) {
/* 303 */         villagedoor.a();
/*     */       }
/*     */       
/* 306 */       if ((!f(villagedoor.d())) || (Math.abs(this.g - villagedoor.h()) > 1200)) {
/* 307 */         this.c = this.c.b(villagedoor.d());
/* 308 */         flag = true;
/* 309 */         villagedoor.a(true);
/* 310 */         iterator.remove();
/*     */       }
/*     */     }
/*     */     
/* 314 */     if (flag) {
/* 315 */       n();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean f(BlockPosition blockposition)
/*     */   {
/* 321 */     Block block = this.a.getType(blockposition).getBlock();
/*     */     
/* 323 */     return block.getMaterial() == Material.WOOD;
/*     */   }
/*     */   
/*     */   private void n() {
/* 327 */     int i = this.b.size();
/*     */     
/* 329 */     if (i == 0) {
/* 330 */       this.d = new BlockPosition(0, 0, 0);
/* 331 */       this.e = 0;
/*     */     } else {
/* 333 */       this.d = new BlockPosition(this.c.getX() / i, this.c.getY() / i, this.c.getZ() / i);
/* 334 */       int j = 0;
/*     */       
/*     */       VillageDoor villagedoor;
/*     */       
/* 338 */       for (Iterator iterator = this.b.iterator(); iterator.hasNext(); j = Math.max(villagedoor.a(this.d), j)) {
/* 339 */         villagedoor = (VillageDoor)iterator.next();
/*     */       }
/*     */       
/* 342 */       this.e = Math.max(32, (int)Math.sqrt(j) + 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(String s) {
/* 347 */     Integer integer = (Integer)this.j.get(s);
/*     */     
/* 349 */     return integer != null ? integer.intValue() : 0;
/*     */   }
/*     */   
/*     */   public int a(String s, int i) {
/* 353 */     int j = a(s);
/* 354 */     int k = MathHelper.clamp(j + i, -30, 10);
/*     */     
/* 356 */     this.j.put(s, Integer.valueOf(k));
/* 357 */     return k;
/*     */   }
/*     */   
/*     */   public boolean d(String s) {
/* 361 */     return a(s) <= -15;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 365 */     this.h = nbttagcompound.getInt("PopSize");
/* 366 */     this.e = nbttagcompound.getInt("Radius");
/* 367 */     this.l = nbttagcompound.getInt("Golems");
/* 368 */     this.f = nbttagcompound.getInt("Stable");
/* 369 */     this.g = nbttagcompound.getInt("Tick");
/* 370 */     this.i = nbttagcompound.getInt("MTick");
/* 371 */     this.d = new BlockPosition(nbttagcompound.getInt("CX"), nbttagcompound.getInt("CY"), nbttagcompound.getInt("CZ"));
/* 372 */     this.c = new BlockPosition(nbttagcompound.getInt("ACX"), nbttagcompound.getInt("ACY"), nbttagcompound.getInt("ACZ"));
/* 373 */     NBTTagList nbttaglist = nbttagcompound.getList("Doors", 10);
/*     */     
/* 375 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 376 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 377 */       VillageDoor villagedoor = new VillageDoor(new BlockPosition(nbttagcompound1.getInt("X"), nbttagcompound1.getInt("Y"), nbttagcompound1.getInt("Z")), nbttagcompound1.getInt("IDX"), nbttagcompound1.getInt("IDZ"), nbttagcompound1.getInt("TS"));
/*     */       
/* 379 */       this.b.add(villagedoor);
/*     */     }
/*     */     
/* 382 */     NBTTagList nbttaglist1 = nbttagcompound.getList("Players", 10);
/*     */     
/* 384 */     for (int j = 0; j < nbttaglist1.size(); j++) {
/* 385 */       NBTTagCompound nbttagcompound2 = nbttaglist1.get(j);
/*     */       
/* 387 */       if (nbttagcompound2.hasKey("UUID")) {
/* 388 */         UserCache usercache = MinecraftServer.getServer().getUserCache();
/* 389 */         GameProfile gameprofile = usercache.a(java.util.UUID.fromString(nbttagcompound2.getString("UUID")));
/*     */         
/* 391 */         if (gameprofile != null) {
/* 392 */           this.j.put(gameprofile.getName(), Integer.valueOf(nbttagcompound2.getInt("S")));
/*     */         }
/*     */       } else {
/* 395 */         this.j.put(nbttagcompound2.getString("Name"), Integer.valueOf(nbttagcompound2.getInt("S")));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 402 */     nbttagcompound.setInt("PopSize", this.h);
/* 403 */     nbttagcompound.setInt("Radius", this.e);
/* 404 */     nbttagcompound.setInt("Golems", this.l);
/* 405 */     nbttagcompound.setInt("Stable", this.f);
/* 406 */     nbttagcompound.setInt("Tick", this.g);
/* 407 */     nbttagcompound.setInt("MTick", this.i);
/* 408 */     nbttagcompound.setInt("CX", this.d.getX());
/* 409 */     nbttagcompound.setInt("CY", this.d.getY());
/* 410 */     nbttagcompound.setInt("CZ", this.d.getZ());
/* 411 */     nbttagcompound.setInt("ACX", this.c.getX());
/* 412 */     nbttagcompound.setInt("ACY", this.c.getY());
/* 413 */     nbttagcompound.setInt("ACZ", this.c.getZ());
/* 414 */     NBTTagList nbttaglist = new NBTTagList();
/* 415 */     Iterator iterator = this.b.iterator();
/*     */     
/* 417 */     while (iterator.hasNext()) {
/* 418 */       VillageDoor villagedoor = (VillageDoor)iterator.next();
/* 419 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/* 421 */       nbttagcompound1.setInt("X", villagedoor.d().getX());
/* 422 */       nbttagcompound1.setInt("Y", villagedoor.d().getY());
/* 423 */       nbttagcompound1.setInt("Z", villagedoor.d().getZ());
/* 424 */       nbttagcompound1.setInt("IDX", villagedoor.f());
/* 425 */       nbttagcompound1.setInt("IDZ", villagedoor.g());
/* 426 */       nbttagcompound1.setInt("TS", villagedoor.h());
/* 427 */       nbttaglist.add(nbttagcompound1);
/*     */     }
/*     */     
/* 430 */     nbttagcompound.set("Doors", nbttaglist);
/* 431 */     NBTTagList nbttaglist1 = new NBTTagList();
/* 432 */     Iterator iterator1 = this.j.keySet().iterator();
/*     */     
/* 434 */     while (iterator1.hasNext()) {
/* 435 */       String s = (String)iterator1.next();
/* 436 */       NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 437 */       UserCache usercache = MinecraftServer.getServer().getUserCache();
/* 438 */       GameProfile gameprofile = usercache.getProfile(s);
/*     */       
/* 440 */       if (gameprofile != null) {
/* 441 */         nbttagcompound2.setString("UUID", gameprofile.getId().toString());
/* 442 */         nbttagcompound2.setInt("S", ((Integer)this.j.get(s)).intValue());
/* 443 */         nbttaglist1.add(nbttagcompound2);
/*     */       }
/*     */     }
/*     */     
/* 447 */     nbttagcompound.set("Players", nbttaglist1);
/*     */   }
/*     */   
/*     */   public void h() {
/* 451 */     this.i = this.g;
/*     */   }
/*     */   
/*     */   public boolean i() {
/* 455 */     return (this.i == 0) || (this.g - this.i >= 3600);
/*     */   }
/*     */   
/*     */   public void b(int i) {
/* 459 */     Iterator iterator = this.j.keySet().iterator();
/*     */     
/* 461 */     while (iterator.hasNext()) {
/* 462 */       String s = (String)iterator.next();
/*     */       
/* 464 */       a(s, i);
/*     */     }
/*     */   }
/*     */   
/*     */   class Aggressor
/*     */   {
/*     */     public EntityLiving a;
/*     */     public int b;
/*     */     
/*     */     Aggressor(EntityLiving entityliving, int i)
/*     */     {
/* 475 */       this.a = entityliving;
/* 476 */       this.b = i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Village.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */