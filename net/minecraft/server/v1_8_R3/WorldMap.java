/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.map.CraftMapView;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.map.RenderData;
/*     */ import org.bukkit.map.MapCursor;
/*     */ 
/*     */ public class WorldMap extends PersistentBase
/*     */ {
/*     */   public int centerX;
/*     */   public int centerZ;
/*     */   public byte map;
/*     */   public byte scale;
/*  24 */   public byte[] colors = new byte['䀀'];
/*  25 */   public List<WorldMapHumanTracker> g = com.google.common.collect.Lists.newArrayList();
/*  26 */   public Map<EntityHuman, WorldMapHumanTracker> i = Maps.newHashMap();
/*  27 */   public Map<UUID, MapIcon> decorations = Maps.newLinkedHashMap();
/*     */   
/*     */   public final CraftMapView mapView;
/*     */   
/*     */   private CraftServer server;
/*  32 */   private UUID uniqueId = null;
/*     */   
/*     */   public WorldMap(String s)
/*     */   {
/*  36 */     super(s);
/*     */     
/*  38 */     this.mapView = new CraftMapView(this);
/*  39 */     this.server = ((CraftServer)org.bukkit.Bukkit.getServer());
/*     */   }
/*     */   
/*     */   public void a(double d0, double d1, int i)
/*     */   {
/*  44 */     int j = 128 * (1 << i);
/*  45 */     int k = MathHelper.floor((d0 + 64.0D) / j);
/*  46 */     int l = MathHelper.floor((d1 + 64.0D) / j);
/*     */     
/*  48 */     this.centerX = (k * j + j / 2 - 64);
/*  49 */     this.centerZ = (l * j + j / 2 - 64);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/*  54 */     byte dimension = nbttagcompound.getByte("dimension");
/*     */     
/*  56 */     if (dimension >= 10) {
/*  57 */       long least = nbttagcompound.getLong("UUIDLeast");
/*  58 */       long most = nbttagcompound.getLong("UUIDMost");
/*     */       
/*  60 */       if ((least != 0L) && (most != 0L)) {
/*  61 */         this.uniqueId = new UUID(most, least);
/*     */         
/*  63 */         CraftWorld world = (CraftWorld)this.server.getWorld(this.uniqueId);
/*     */         
/*  65 */         if (world == null)
/*     */         {
/*     */ 
/*  68 */           dimension = Byte.MAX_VALUE;
/*     */         } else {
/*  70 */           dimension = (byte)world.getHandle().dimension;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  75 */     this.map = dimension;
/*     */     
/*  77 */     this.centerX = nbttagcompound.getInt("xCenter");
/*  78 */     this.centerZ = nbttagcompound.getInt("zCenter");
/*  79 */     this.scale = nbttagcompound.getByte("scale");
/*  80 */     this.scale = ((byte)MathHelper.clamp(this.scale, 0, 4));
/*  81 */     short short0 = nbttagcompound.getShort("width");
/*  82 */     short short1 = nbttagcompound.getShort("height");
/*     */     
/*  84 */     if ((short0 == 128) && (short1 == 128)) {
/*  85 */       this.colors = nbttagcompound.getByteArray("colors");
/*     */     } else {
/*  87 */       byte[] abyte = nbttagcompound.getByteArray("colors");
/*     */       
/*  89 */       this.colors = new byte['䀀'];
/*  90 */       int i = (128 - short0) / 2;
/*  91 */       int j = (128 - short1) / 2;
/*     */       
/*  93 */       for (int k = 0; k < short1; k++) {
/*  94 */         int l = k + j;
/*     */         
/*  96 */         if ((l >= 0) || (l < 128)) {
/*  97 */           for (int i1 = 0; i1 < short0; i1++) {
/*  98 */             int j1 = i1 + i;
/*     */             
/* 100 */             if ((j1 >= 0) || (j1 < 128)) {
/* 101 */               this.colors[(j1 + l * 128)] = abyte[(i1 + k * short0)];
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 112 */     if (this.map >= 10) {
/* 113 */       if (this.uniqueId == null) {
/* 114 */         for (org.bukkit.World world : this.server.getWorlds()) {
/* 115 */           CraftWorld cWorld = (CraftWorld)world;
/* 116 */           if (cWorld.getHandle().dimension == this.map) {
/* 117 */             this.uniqueId = cWorld.getUID();
/* 118 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 124 */       if (this.uniqueId != null) {
/* 125 */         nbttagcompound.setLong("UUIDLeast", this.uniqueId.getLeastSignificantBits());
/* 126 */         nbttagcompound.setLong("UUIDMost", this.uniqueId.getMostSignificantBits());
/*     */       }
/*     */     }
/*     */     
/* 130 */     nbttagcompound.setByte("dimension", this.map);
/* 131 */     nbttagcompound.setInt("xCenter", this.centerX);
/* 132 */     nbttagcompound.setInt("zCenter", this.centerZ);
/* 133 */     nbttagcompound.setByte("scale", this.scale);
/* 134 */     nbttagcompound.setShort("width", (short)128);
/* 135 */     nbttagcompound.setShort("height", (short)128);
/* 136 */     nbttagcompound.setByteArray("colors", this.colors);
/*     */   }
/*     */   
/*     */   public void a(EntityHuman entityhuman, ItemStack itemstack) {
/* 140 */     if (!this.i.containsKey(entityhuman)) {
/* 141 */       WorldMapHumanTracker worldmap_worldmaphumantracker = new WorldMapHumanTracker(entityhuman);
/*     */       
/* 143 */       this.i.put(entityhuman, worldmap_worldmaphumantracker);
/* 144 */       this.g.add(worldmap_worldmaphumantracker);
/*     */     }
/*     */     
/* 147 */     if (!entityhuman.inventory.c(itemstack)) {
/* 148 */       this.decorations.remove(entityhuman.getUniqueID());
/*     */     }
/*     */     
/* 151 */     for (int i = 0; i < this.g.size(); i++) {
/* 152 */       WorldMapHumanTracker worldmap_worldmaphumantracker1 = (WorldMapHumanTracker)this.g.get(i);
/*     */       
/* 154 */       if ((!worldmap_worldmaphumantracker1.trackee.dead) && ((worldmap_worldmaphumantracker1.trackee.inventory.c(itemstack)) || (itemstack.y()))) {
/* 155 */         if ((!itemstack.y()) && (worldmap_worldmaphumantracker1.trackee.dimension == this.map)) {
/* 156 */           a(0, worldmap_worldmaphumantracker1.trackee.world, worldmap_worldmaphumantracker1.trackee.getUniqueID(), worldmap_worldmaphumantracker1.trackee.locX, worldmap_worldmaphumantracker1.trackee.locZ, worldmap_worldmaphumantracker1.trackee.yaw);
/*     */         }
/*     */       } else {
/* 159 */         this.i.remove(worldmap_worldmaphumantracker1.trackee);
/* 160 */         this.g.remove(worldmap_worldmaphumantracker1);
/*     */       }
/*     */     }
/*     */     
/* 164 */     if (itemstack.y()) {
/* 165 */       EntityItemFrame entityitemframe = itemstack.z();
/* 166 */       BlockPosition blockposition = entityitemframe.getBlockPosition();
/*     */       
/* 168 */       a(1, entityhuman.world, UUID.nameUUIDFromBytes(("frame-" + entityitemframe.getId()).getBytes(Charsets.US_ASCII)), blockposition.getX(), blockposition.getZ(), entityitemframe.direction.b() * 90);
/*     */     }
/*     */     
/* 171 */     if ((itemstack.hasTag()) && (itemstack.getTag().hasKeyOfType("Decorations", 9))) {
/* 172 */       NBTTagList nbttaglist = itemstack.getTag().getList("Decorations", 10);
/*     */       
/* 174 */       for (int j = 0; j < nbttaglist.size(); j++) {
/* 175 */         NBTTagCompound nbttagcompound = nbttaglist.get(j);
/*     */         
/*     */ 
/* 178 */         UUID uuid = UUID.nameUUIDFromBytes(nbttagcompound.getString("id").getBytes(Charsets.US_ASCII));
/* 179 */         if (!this.decorations.containsKey(uuid)) {
/* 180 */           a(nbttagcompound.getByte("type"), entityhuman.world, uuid, nbttagcompound.getDouble("x"), nbttagcompound.getDouble("z"), nbttagcompound.getDouble("rot"));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void a(int i, World world, UUID s, double d0, double d1, double d2)
/*     */   {
/* 189 */     int j = 1 << this.scale;
/* 190 */     float f = (float)(d0 - this.centerX) / j;
/* 191 */     float f1 = (float)(d1 - this.centerZ) / j;
/* 192 */     byte b0 = (byte)(int)(f * 2.0F + 0.5D);
/* 193 */     byte b1 = (byte)(int)(f1 * 2.0F + 0.5D);
/* 194 */     byte b2 = 63;
/*     */     
/*     */     byte b3;
/* 197 */     if ((f >= -b2) && (f1 >= -b2) && (f <= b2) && (f1 <= b2)) {
/* 198 */       d2 += (d2 < 0.0D ? -8.0D : 8.0D);
/* 199 */       byte b3 = (byte)(int)(d2 * 16.0D / 360.0D);
/* 200 */       if (this.map < 0) {
/* 201 */         int k = (int)(world.getWorldData().getDayTime() / 10L);
/*     */         
/* 203 */         b3 = (byte)(k * k * 34187121 + k * 121 >> 15 & 0xF);
/*     */       }
/*     */     } else {
/* 206 */       if ((Math.abs(f) >= 320.0F) || (Math.abs(f1) >= 320.0F)) {
/* 207 */         this.decorations.remove(s);
/* 208 */         return;
/*     */       }
/*     */       
/* 211 */       i = 6;
/* 212 */       b3 = 0;
/* 213 */       if (f <= -b2) {
/* 214 */         b0 = (byte)(int)(b2 * 2 + 2.5D);
/*     */       }
/*     */       
/* 217 */       if (f1 <= -b2) {
/* 218 */         b1 = (byte)(int)(b2 * 2 + 2.5D);
/*     */       }
/*     */       
/* 221 */       if (f >= b2) {
/* 222 */         b0 = (byte)(b2 * 2 + 1);
/*     */       }
/*     */       
/* 225 */       if (f1 >= b2) {
/* 226 */         b1 = (byte)(b2 * 2 + 1);
/*     */       }
/*     */     }
/*     */     
/* 230 */     this.decorations.put(s, new MapIcon((byte)i, b0, b1, b3));
/*     */   }
/*     */   
/*     */   public Packet a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 234 */     WorldMapHumanTracker worldmap_worldmaphumantracker = (WorldMapHumanTracker)this.i.get(entityhuman);
/*     */     
/* 236 */     return worldmap_worldmaphumantracker == null ? null : worldmap_worldmaphumantracker.a(itemstack);
/*     */   }
/*     */   
/*     */   public void flagDirty(int i, int j) {
/* 240 */     super.c();
/* 241 */     Iterator iterator = this.g.iterator();
/*     */     
/* 243 */     while (iterator.hasNext()) {
/* 244 */       WorldMapHumanTracker worldmap_worldmaphumantracker = (WorldMapHumanTracker)iterator.next();
/*     */       
/* 246 */       worldmap_worldmaphumantracker.a(i, j);
/*     */     }
/*     */   }
/*     */   
/*     */   public WorldMapHumanTracker a(EntityHuman entityhuman)
/*     */   {
/* 252 */     WorldMapHumanTracker worldmap_worldmaphumantracker = (WorldMapHumanTracker)this.i.get(entityhuman);
/*     */     
/* 254 */     if (worldmap_worldmaphumantracker == null) {
/* 255 */       worldmap_worldmaphumantracker = new WorldMapHumanTracker(entityhuman);
/* 256 */       this.i.put(entityhuman, worldmap_worldmaphumantracker);
/* 257 */       this.g.add(worldmap_worldmaphumantracker);
/*     */     }
/*     */     
/* 260 */     return worldmap_worldmaphumantracker;
/*     */   }
/*     */   
/*     */   public class WorldMapHumanTracker
/*     */   {
/*     */     public final EntityHuman trackee;
/* 266 */     private boolean d = true;
/* 267 */     private int e = 0;
/* 268 */     private int f = 0;
/* 269 */     private int g = 127;
/* 270 */     private int h = 127;
/*     */     private int i;
/*     */     public int b;
/*     */     
/*     */     public WorldMapHumanTracker(EntityHuman entityhuman) {
/* 275 */       this.trackee = entityhuman;
/*     */     }
/*     */     
/*     */     public Packet a(ItemStack itemstack)
/*     */     {
/* 280 */       RenderData render = WorldMap.this.mapView.render((CraftPlayer)this.trackee.getBukkitEntity());
/*     */       
/* 282 */       Collection<MapIcon> icons = new ArrayList();
/*     */       
/* 284 */       for (MapCursor cursor : render.cursors)
/*     */       {
/* 286 */         if (cursor.isVisible()) {
/* 287 */           icons.add(new MapIcon(cursor.getRawType(), cursor.getX(), cursor.getY(), cursor.getDirection()));
/*     */         }
/*     */       }
/*     */       
/* 291 */       if (this.d) {
/* 292 */         this.d = false;
/* 293 */         return new PacketPlayOutMap(itemstack.getData(), WorldMap.this.scale, icons, render.buffer, this.e, this.f, this.g + 1 - this.e, this.h + 1 - this.f);
/*     */       }
/* 295 */       return this.i++ % 5 == 0 ? new PacketPlayOutMap(itemstack.getData(), WorldMap.this.scale, icons, render.buffer, 0, 0, 0, 0) : null;
/*     */     }
/*     */     
/*     */ 
/*     */     public void a(int i, int j)
/*     */     {
/* 301 */       if (this.d) {
/* 302 */         this.e = Math.min(this.e, i);
/* 303 */         this.f = Math.min(this.f, j);
/* 304 */         this.g = Math.max(this.g, i);
/* 305 */         this.h = Math.max(this.h, j);
/*     */       } else {
/* 307 */         this.d = true;
/* 308 */         this.e = i;
/* 309 */         this.f = j;
/* 310 */         this.g = i;
/* 311 */         this.h = j;
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */