/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import com.mojang.authlib.Agent;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.GameProfileRepository;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import java.util.Queue;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ public class TileEntitySkull extends TileEntity
/*     */ {
/*     */   private int a;
/*     */   private int rotation;
/*  26 */   private GameProfile g = null;
/*     */   
/*  28 */   public static final Executor executor = Executors.newFixedThreadPool(3, 
/*  29 */     new ThreadFactoryBuilder()
/*  30 */     .setNameFormat("Head Conversion Thread - %1$d")
/*  31 */     .build());
/*     */   
/*  33 */   public static final LoadingCache<String, GameProfile> skinCache = CacheBuilder.newBuilder()
/*  34 */     .maximumSize(5000L)
/*  35 */     .expireAfterAccess(60L, TimeUnit.MINUTES)
/*  36 */     .build(new CacheLoader()
/*     */   {
/*     */     public GameProfile load(String key)
/*     */       throws Exception
/*     */     {
/*  41 */       final GameProfile[] profiles = new GameProfile[1];
/*  42 */       ProfileLookupCallback gameProfileLookup = new ProfileLookupCallback()
/*     */       {
/*     */         public void onProfileLookupSucceeded(GameProfile gp)
/*     */         {
/*  46 */           profiles[0] = gp;
/*     */         }
/*     */         
/*     */         public void onProfileLookupFailed(GameProfile gp, Exception excptn)
/*     */         {
/*  51 */           profiles[0] = gp;
/*     */         }
/*     */         
/*  54 */       };
/*  55 */       MinecraftServer.getServer().getGameProfileRepository().findProfilesByNames(new String[] { key }, Agent.MINECRAFT, gameProfileLookup);
/*     */       
/*  57 */       GameProfile profile = profiles[0];
/*  58 */       if (profile == null) {
/*  59 */         UUID uuid = EntityHuman.a(new GameProfile(null, key));
/*  60 */         profile = new GameProfile(uuid, key);
/*     */         
/*  62 */         gameProfileLookup.onProfileLookupSucceeded(profile);
/*     */       }
/*     */       else
/*     */       {
/*  66 */         Property property = (Property)Iterables.getFirst(profile.getProperties().get("textures"), null);
/*     */         
/*  68 */         if (property == null)
/*     */         {
/*  70 */           profile = MinecraftServer.getServer().aD().fillProfileProperties(profile, true);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  75 */       return profile;
/*     */     }
/*  36 */   });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/*  84 */     super.b(nbttagcompound);
/*  85 */     nbttagcompound.setByte("SkullType", (byte)(this.a & 0xFF));
/*  86 */     nbttagcompound.setByte("Rot", (byte)(this.rotation & 0xFF));
/*  87 */     if (this.g != null) {
/*  88 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */       
/*  90 */       GameProfileSerializer.serialize(nbttagcompound1, this.g);
/*  91 */       nbttagcompound.set("Owner", nbttagcompound1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/*  97 */     super.a(nbttagcompound);
/*  98 */     this.a = nbttagcompound.getByte("SkullType");
/*  99 */     this.rotation = nbttagcompound.getByte("Rot");
/* 100 */     if (this.a == 3) {
/* 101 */       if (nbttagcompound.hasKeyOfType("Owner", 10)) {
/* 102 */         this.g = GameProfileSerializer.deserialize(nbttagcompound.getCompound("Owner"));
/* 103 */       } else if (nbttagcompound.hasKeyOfType("ExtraType", 8)) {
/* 104 */         String s = nbttagcompound.getString("ExtraType");
/*     */         
/* 106 */         if (!UtilColor.b(s)) {
/* 107 */           this.g = new GameProfile(null, s);
/* 108 */           e();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public GameProfile getGameProfile()
/*     */   {
/* 116 */     return this.g;
/*     */   }
/*     */   
/*     */   public Packet getUpdatePacket() {
/* 120 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 122 */     b(nbttagcompound);
/* 123 */     return new PacketPlayOutTileEntityData(this.position, 4, nbttagcompound);
/*     */   }
/*     */   
/*     */   public void setSkullType(int i) {
/* 127 */     this.a = i;
/* 128 */     this.g = null;
/*     */   }
/*     */   
/*     */   public void setGameProfile(GameProfile gameprofile) {
/* 132 */     this.a = 3;
/* 133 */     this.g = gameprofile;
/* 134 */     e();
/*     */   }
/*     */   
/*     */   private void e()
/*     */   {
/* 139 */     GameProfile profile = this.g;
/* 140 */     setSkullType(0);
/* 141 */     b(profile, new Predicate()
/*     */     {
/*     */       public boolean apply(GameProfile input)
/*     */       {
/* 145 */         TileEntitySkull.this.setSkullType(3);
/* 146 */         TileEntitySkull.this.g = input;
/* 147 */         TileEntitySkull.this.update();
/* 148 */         if (TileEntitySkull.this.world != null) {
/* 149 */           TileEntitySkull.this.world.notify(TileEntitySkull.this.position);
/*     */         }
/* 151 */         return false;
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */ 
/*     */   public static void b(GameProfile gameprofile, final Predicate<GameProfile> callback)
/*     */   {
/* 159 */     if ((gameprofile != null) && (!UtilColor.b(gameprofile.getName()))) {
/* 160 */       if ((gameprofile.isComplete()) && (gameprofile.getProperties().containsKey("textures"))) {
/* 161 */         callback.apply(gameprofile);
/* 162 */       } else if (MinecraftServer.getServer() == null) {
/* 163 */         callback.apply(gameprofile);
/*     */       } else {
/* 165 */         GameProfile profile = (GameProfile)skinCache.getIfPresent(gameprofile.getName());
/* 166 */         if ((profile != null) && (Iterables.getFirst(profile.getProperties().get("textures"), null) != null)) {
/* 167 */           callback.apply(profile);
/*     */         } else {
/* 169 */           executor.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 172 */               final GameProfile profile = (GameProfile)TileEntitySkull.skinCache.getUnchecked(TileEntitySkull.this.getName().toLowerCase());
/* 173 */               MinecraftServer.getServer().processQueue.add(new Runnable()
/*     */               {
/*     */                 public void run() {
/* 176 */                   if (profile == null) {
/* 177 */                     this.val$callback.apply(this.val$gameprofile);
/*     */                   } else {
/* 179 */                     this.val$callback.apply(profile);
/*     */                   }
/*     */                 }
/*     */               });
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/*     */     } else {
/* 188 */       callback.apply(gameprofile);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSkullType()
/*     */   {
/* 194 */     return this.a;
/*     */   }
/*     */   
/*     */   public void setRotation(int i) {
/* 198 */     this.rotation = i;
/*     */   }
/*     */   
/*     */   public int getRotation()
/*     */   {
/* 203 */     return this.rotation;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntitySkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */