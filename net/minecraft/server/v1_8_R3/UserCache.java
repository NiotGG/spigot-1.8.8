/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.GameProfileRepository;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.LinkedBlockingDeque;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class UserCache
/*     */ {
/*  42 */   public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
/*  43 */   private final Map<String, UserCacheEntry> c = Maps.newHashMap();
/*  44 */   private final Map<UUID, UserCacheEntry> d = Maps.newHashMap();
/*  45 */   private final Deque<GameProfile> e = new LinkedBlockingDeque();
/*     */   private final MinecraftServer f;
/*     */   protected final Gson b;
/*     */   private final File g;
/*  49 */   private static final ParameterizedType h = new ParameterizedType() {
/*     */     public Type[] getActualTypeArguments() {
/*  51 */       return new Type[] { UserCache.UserCacheEntry.class };
/*     */     }
/*     */     
/*     */     public Type getRawType() {
/*  55 */       return List.class;
/*     */     }
/*     */     
/*     */     public Type getOwnerType() {
/*  59 */       return null;
/*     */     }
/*     */   };
/*     */   
/*     */   public UserCache(MinecraftServer minecraftserver, File file) {
/*  64 */     this.f = minecraftserver;
/*  65 */     this.g = file;
/*  66 */     GsonBuilder gsonbuilder = new GsonBuilder();
/*     */     
/*  68 */     gsonbuilder.registerTypeHierarchyAdapter(UserCacheEntry.class, new BanEntrySerializer(null));
/*  69 */     this.b = gsonbuilder.create();
/*  70 */     b();
/*     */   }
/*     */   
/*     */   private static GameProfile a(MinecraftServer minecraftserver, String s) {
/*  74 */     GameProfile[] agameprofile = new GameProfile[1];
/*  75 */     ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
/*     */       public void onProfileLookupSucceeded(GameProfile gameprofile) {
/*  77 */         UserCache.this[0] = gameprofile;
/*     */       }
/*     */       
/*     */       public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
/*  81 */         UserCache.this[0] = null;
/*     */       }
/*     */       
/*  84 */     };
/*  85 */     minecraftserver.getGameProfileRepository().findProfilesByNames(new String[] { s }, com.mojang.authlib.Agent.MINECRAFT, profilelookupcallback);
/*  86 */     if ((!minecraftserver.getOnlineMode()) && (agameprofile[0] == null)) {
/*  87 */       UUID uuid = EntityHuman.a(new GameProfile(null, s));
/*  88 */       GameProfile gameprofile = new GameProfile(uuid, s);
/*     */       
/*  90 */       profilelookupcallback.onProfileLookupSucceeded(gameprofile);
/*     */     }
/*     */     
/*  93 */     return agameprofile[0];
/*     */   }
/*     */   
/*     */   public void a(GameProfile gameprofile) {
/*  97 */     a(gameprofile, null);
/*     */   }
/*     */   
/*     */   private void a(GameProfile gameprofile, Date date) {
/* 101 */     UUID uuid = gameprofile.getId();
/*     */     
/* 103 */     if (date == null) {
/* 104 */       Calendar calendar = Calendar.getInstance();
/*     */       
/* 106 */       calendar.setTime(new Date());
/* 107 */       calendar.add(2, 1);
/* 108 */       date = calendar.getTime();
/*     */     }
/*     */     
/* 111 */     gameprofile.getName().toLowerCase(Locale.ROOT);
/* 112 */     UserCacheEntry usercache_usercacheentry = new UserCacheEntry(gameprofile, date, null);
/*     */     
/* 114 */     if (this.d.containsKey(uuid)) {
/* 115 */       UserCacheEntry usercache_usercacheentry1 = (UserCacheEntry)this.d.get(uuid);
/*     */       
/* 117 */       this.c.remove(usercache_usercacheentry1.a().getName().toLowerCase(Locale.ROOT));
/* 118 */       this.e.remove(gameprofile);
/*     */     }
/*     */     
/* 121 */     this.c.put(gameprofile.getName().toLowerCase(Locale.ROOT), usercache_usercacheentry);
/* 122 */     this.d.put(uuid, usercache_usercacheentry);
/* 123 */     this.e.addFirst(gameprofile);
/* 124 */     if (!SpigotConfig.saveUserCacheOnStopOnly) c();
/*     */   }
/*     */   
/*     */   public GameProfile getProfile(String s) {
/* 128 */     String s1 = s.toLowerCase(Locale.ROOT);
/* 129 */     UserCacheEntry usercache_usercacheentry = (UserCacheEntry)this.c.get(s1);
/*     */     
/* 131 */     if ((usercache_usercacheentry != null) && (new Date().getTime() >= usercache_usercacheentry.c.getTime())) {
/* 132 */       this.d.remove(usercache_usercacheentry.a().getId());
/* 133 */       this.c.remove(usercache_usercacheentry.a().getName().toLowerCase(Locale.ROOT));
/* 134 */       this.e.remove(usercache_usercacheentry.a());
/* 135 */       usercache_usercacheentry = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 140 */     if (usercache_usercacheentry != null) {
/* 141 */       GameProfile gameprofile = usercache_usercacheentry.a();
/* 142 */       this.e.remove(gameprofile);
/* 143 */       this.e.addFirst(gameprofile);
/*     */     } else {
/* 145 */       GameProfile gameprofile = a(this.f, s);
/* 146 */       if (gameprofile != null) {
/* 147 */         a(gameprofile);
/* 148 */         usercache_usercacheentry = (UserCacheEntry)this.c.get(s1);
/*     */       }
/*     */     }
/*     */     
/* 152 */     if (!SpigotConfig.saveUserCacheOnStopOnly) c();
/* 153 */     return usercache_usercacheentry == null ? null : usercache_usercacheentry.a();
/*     */   }
/*     */   
/*     */   public String[] a() {
/* 157 */     ArrayList arraylist = Lists.newArrayList(this.c.keySet());
/*     */     
/* 159 */     return (String[])arraylist.toArray(new String[arraylist.size()]);
/*     */   }
/*     */   
/*     */   public GameProfile a(UUID uuid) {
/* 163 */     UserCacheEntry usercache_usercacheentry = (UserCacheEntry)this.d.get(uuid);
/*     */     
/* 165 */     return usercache_usercacheentry == null ? null : usercache_usercacheentry.a();
/*     */   }
/*     */   
/*     */   private UserCacheEntry b(UUID uuid) {
/* 169 */     UserCacheEntry usercache_usercacheentry = (UserCacheEntry)this.d.get(uuid);
/*     */     
/* 171 */     if (usercache_usercacheentry != null) {
/* 172 */       GameProfile gameprofile = usercache_usercacheentry.a();
/*     */       
/* 174 */       this.e.remove(gameprofile);
/* 175 */       this.e.addFirst(gameprofile);
/*     */     }
/*     */     
/* 178 */     return usercache_usercacheentry;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void b()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_1
/*     */     //   2: aload_0
/*     */     //   3: getfield 70	net/minecraft/server/v1_8_R3/UserCache:g	Ljava/io/File;
/*     */     //   6: getstatic 292	com/google/common/base/Charsets:UTF_8	Ljava/nio/charset/Charset;
/*     */     //   9: invokestatic 298	com/google/common/io/Files:newReader	(Ljava/io/File;Ljava/nio/charset/Charset;)Ljava/io/BufferedReader;
/*     */     //   12: astore_1
/*     */     //   13: aload_0
/*     */     //   14: getfield 86	net/minecraft/server/v1_8_R3/UserCache:b	Lcom/google/gson/Gson;
/*     */     //   17: aload_1
/*     */     //   18: getstatic 49	net/minecraft/server/v1_8_R3/UserCache:h	Ljava/lang/reflect/ParameterizedType;
/*     */     //   21: invokevirtual 304	com/google/gson/Gson:fromJson	(Ljava/io/Reader;Ljava/lang/reflect/Type;)Ljava/lang/Object;
/*     */     //   24: checkcast 306	java/util/List
/*     */     //   27: astore_2
/*     */     //   28: aload_0
/*     */     //   29: getfield 59	net/minecraft/server/v1_8_R3/UserCache:c	Ljava/util/Map;
/*     */     //   32: invokeinterface 309 1 0
/*     */     //   37: aload_0
/*     */     //   38: getfield 61	net/minecraft/server/v1_8_R3/UserCache:d	Ljava/util/Map;
/*     */     //   41: invokeinterface 309 1 0
/*     */     //   46: aload_0
/*     */     //   47: getfield 66	net/minecraft/server/v1_8_R3/UserCache:e	Ljava/util/Deque;
/*     */     //   50: invokeinterface 310 1 0
/*     */     //   55: aload_2
/*     */     //   56: invokestatic 314	com/google/common/collect/Lists:reverse	(Ljava/util/List;)Ljava/util/List;
/*     */     //   59: invokeinterface 318 1 0
/*     */     //   64: astore_3
/*     */     //   65: goto +33 -> 98
/*     */     //   68: aload_3
/*     */     //   69: invokeinterface 326 1 0
/*     */     //   74: checkcast 14	net/minecraft/server/v1_8_R3/UserCache$UserCacheEntry
/*     */     //   77: astore 4
/*     */     //   79: aload 4
/*     */     //   81: ifnull +17 -> 98
/*     */     //   84: aload_0
/*     */     //   85: aload 4
/*     */     //   87: invokevirtual 209	net/minecraft/server/v1_8_R3/UserCache$UserCacheEntry:a	()Lcom/mojang/authlib/GameProfile;
/*     */     //   90: aload 4
/*     */     //   92: invokevirtual 328	net/minecraft/server/v1_8_R3/UserCache$UserCacheEntry:b	()Ljava/util/Date;
/*     */     //   95: invokespecial 152	net/minecraft/server/v1_8_R3/UserCache:a	(Lcom/mojang/authlib/GameProfile;Ljava/util/Date;)V
/*     */     //   98: aload_3
/*     */     //   99: invokeinterface 331 1 0
/*     */     //   104: ifne -36 -> 68
/*     */     //   107: goto +55 -> 162
/*     */     //   110: pop
/*     */     //   111: aload_1
/*     */     //   112: invokestatic 337	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Reader;)V
/*     */     //   115: goto +51 -> 166
/*     */     //   118: pop
/*     */     //   119: getstatic 342	net/minecraft/server/v1_8_R3/JsonList:a	Lorg/apache/logging/log4j/Logger;
/*     */     //   122: ldc_w 344
/*     */     //   125: invokeinterface 349 2 0
/*     */     //   130: aload_0
/*     */     //   131: getfield 70	net/minecraft/server/v1_8_R3/UserCache:g	Ljava/io/File;
/*     */     //   134: invokevirtual 354	java/io/File:delete	()Z
/*     */     //   137: pop
/*     */     //   138: aload_1
/*     */     //   139: invokestatic 337	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Reader;)V
/*     */     //   142: goto +24 -> 166
/*     */     //   145: pop
/*     */     //   146: aload_1
/*     */     //   147: invokestatic 337	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Reader;)V
/*     */     //   150: goto +16 -> 166
/*     */     //   153: astore 5
/*     */     //   155: aload_1
/*     */     //   156: invokestatic 337	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Reader;)V
/*     */     //   159: aload 5
/*     */     //   161: athrow
/*     */     //   162: aload_1
/*     */     //   163: invokestatic 337	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Reader;)V
/*     */     //   166: return
/*     */     // Line number table:
/*     */     //   Java source line #182	-> byte code offset #0
/*     */     //   Java source line #185	-> byte code offset #2
/*     */     //   Java source line #186	-> byte code offset #13
/*     */     //   Java source line #188	-> byte code offset #28
/*     */     //   Java source line #189	-> byte code offset #37
/*     */     //   Java source line #190	-> byte code offset #46
/*     */     //   Java source line #191	-> byte code offset #55
/*     */     //   Java source line #193	-> byte code offset #65
/*     */     //   Java source line #194	-> byte code offset #68
/*     */     //   Java source line #196	-> byte code offset #79
/*     */     //   Java source line #197	-> byte code offset #84
/*     */     //   Java source line #193	-> byte code offset #98
/*     */     //   Java source line #200	-> byte code offset #107
/*     */     //   Java source line #210	-> byte code offset #111
/*     */     //   Java source line #203	-> byte code offset #118
/*     */     //   Java source line #204	-> byte code offset #119
/*     */     //   Java source line #205	-> byte code offset #130
/*     */     //   Java source line #210	-> byte code offset #138
/*     */     //   Java source line #207	-> byte code offset #145
/*     */     //   Java source line #210	-> byte code offset #146
/*     */     //   Java source line #209	-> byte code offset #153
/*     */     //   Java source line #210	-> byte code offset #155
/*     */     //   Java source line #211	-> byte code offset #159
/*     */     //   Java source line #210	-> byte code offset #162
/*     */     //   Java source line #213	-> byte code offset #166
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	167	0	this	UserCache
/*     */     //   1	162	1	bufferedreader	java.io.BufferedReader
/*     */     //   27	29	2	list	List
/*     */     //   64	35	3	iterator	Iterator
/*     */     //   77	14	4	usercache_usercacheentry	UserCacheEntry
/*     */     //   153	7	5	localObject	Object
/*     */     //   110	1	6	localFileNotFoundException	FileNotFoundException
/*     */     //   118	1	7	localJsonSyntaxException	com.google.gson.JsonSyntaxException
/*     */     //   145	1	8	localJsonParseException	JsonParseException
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   2	107	110	java/io/FileNotFoundException
/*     */     //   2	107	118	com/google/gson/JsonSyntaxException
/*     */     //   2	107	145	com/google/gson/JsonParseException
/*     */     //   2	111	153	finally
/*     */     //   118	138	153	finally
/*     */     //   145	146	153	finally
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/* 216 */     String s = this.b.toJson(a(SpigotConfig.userCacheCap));
/* 217 */     BufferedWriter bufferedwriter = null;
/*     */     try
/*     */     {
/* 220 */       bufferedwriter = Files.newWriter(this.g, com.google.common.base.Charsets.UTF_8);
/* 221 */       bufferedwriter.write(s);
/* 222 */       return;
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException localFileNotFoundException) {}catch (IOException localIOException) {}finally
/*     */     {
/*     */ 
/* 228 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     }
/*     */   }
/*     */   
/*     */   private List<UserCacheEntry> a(int i)
/*     */   {
/* 234 */     ArrayList arraylist = Lists.newArrayList();
/* 235 */     ArrayList arraylist1 = Lists.newArrayList(Iterators.limit(this.e.iterator(), i));
/* 236 */     Iterator iterator = arraylist1.iterator();
/*     */     
/* 238 */     while (iterator.hasNext()) {
/* 239 */       GameProfile gameprofile = (GameProfile)iterator.next();
/* 240 */       UserCacheEntry usercache_usercacheentry = b(gameprofile.getId());
/*     */       
/* 242 */       if (usercache_usercacheentry != null) {
/* 243 */         arraylist.add(usercache_usercacheentry);
/*     */       }
/*     */     }
/*     */     
/* 247 */     return arraylist;
/*     */   }
/*     */   
/*     */   class UserCacheEntry
/*     */   {
/*     */     private final GameProfile b;
/*     */     private final Date c;
/*     */     
/*     */     private UserCacheEntry(GameProfile gameprofile, Date date) {
/* 256 */       this.b = gameprofile;
/* 257 */       this.c = date;
/*     */     }
/*     */     
/*     */     public GameProfile a() {
/* 261 */       return this.b;
/*     */     }
/*     */     
/*     */     public Date b() {
/* 265 */       return this.c;
/*     */     }
/*     */     
/*     */     UserCacheEntry(GameProfile gameprofile, Date date, Object object) {
/* 269 */       this(gameprofile, date);
/*     */     }
/*     */   }
/*     */   
/*     */   class BanEntrySerializer implements JsonDeserializer<UserCache.UserCacheEntry>, JsonSerializer<UserCache.UserCacheEntry>
/*     */   {
/*     */     private BanEntrySerializer() {}
/*     */     
/*     */     public JsonElement a(UserCache.UserCacheEntry usercache_usercacheentry, Type type, JsonSerializationContext jsonserializationcontext) {
/* 278 */       JsonObject jsonobject = new JsonObject();
/*     */       
/* 280 */       jsonobject.addProperty("name", usercache_usercacheentry.a().getName());
/* 281 */       UUID uuid = usercache_usercacheentry.a().getId();
/*     */       
/* 283 */       jsonobject.addProperty("uuid", uuid == null ? "" : uuid.toString());
/* 284 */       jsonobject.addProperty("expiresOn", UserCache.a.format(usercache_usercacheentry.b()));
/* 285 */       return jsonobject;
/*     */     }
/*     */     
/*     */     public UserCache.UserCacheEntry a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
/* 289 */       if (jsonelement.isJsonObject()) {
/* 290 */         JsonObject jsonobject = jsonelement.getAsJsonObject();
/* 291 */         JsonElement jsonelement1 = jsonobject.get("name");
/* 292 */         JsonElement jsonelement2 = jsonobject.get("uuid");
/* 293 */         JsonElement jsonelement3 = jsonobject.get("expiresOn");
/*     */         
/* 295 */         if ((jsonelement1 != null) && (jsonelement2 != null)) {
/* 296 */           String s = jsonelement2.getAsString();
/* 297 */           String s1 = jsonelement1.getAsString();
/* 298 */           Date date = null;
/*     */           
/* 300 */           if (jsonelement3 != null) {
/*     */             try {
/* 302 */               date = UserCache.a.parse(jsonelement3.getAsString());
/*     */             } catch (ParseException localParseException) {
/* 304 */               date = null;
/*     */             }
/*     */           }
/*     */           
/* 308 */           if ((s1 != null) && (s != null))
/*     */           {
/*     */             try
/*     */             {
/* 312 */               uuid = UUID.fromString(s);
/*     */             } catch (Throwable localThrowable) { UUID uuid;
/* 314 */               return null;
/*     */             }
/*     */             UUID uuid;
/* 317 */             UserCache tmp123_120 = UserCache.this;tmp123_120.getClass();UserCache.UserCacheEntry usercache_usercacheentry = new UserCache.UserCacheEntry(tmp123_120, new GameProfile(uuid, s1), date, null);
/*     */             
/* 319 */             return usercache_usercacheentry;
/*     */           }
/* 321 */           return null;
/*     */         }
/*     */         
/* 324 */         return null;
/*     */       }
/*     */       
/* 327 */       return null;
/*     */     }
/*     */     
/*     */     public JsonElement serialize(UserCache.UserCacheEntry object, Type type, JsonSerializationContext jsonserializationcontext)
/*     */     {
/* 332 */       return a(object, type, jsonserializationcontext);
/*     */     }
/*     */     
/*     */     public UserCache.UserCacheEntry deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
/* 336 */       return a(jsonelement, type, jsondeserializationcontext);
/*     */     }
/*     */     
/*     */     BanEntrySerializer(Object object) {
/* 340 */       this();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\UserCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */