/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.UUID;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldNBTStorage
/*     */   implements IDataManager, IPlayerFileData
/*     */ {
/*  22 */   private static final org.apache.logging.log4j.Logger a = ;
/*     */   private final File baseDir;
/*     */   private final File playerDir;
/*     */   private final File dataDir;
/*  26 */   private final long sessionId = MinecraftServer.az();
/*     */   private final String f;
/*  28 */   private UUID uuid = null;
/*     */   
/*     */   public WorldNBTStorage(File file, String s, boolean flag) {
/*  31 */     this.baseDir = new File(file, s);
/*  32 */     this.baseDir.mkdirs();
/*  33 */     this.playerDir = new File(this.baseDir, "playerdata");
/*  34 */     this.dataDir = new File(this.baseDir, "data");
/*  35 */     this.dataDir.mkdirs();
/*  36 */     this.f = s;
/*  37 */     if (flag) {
/*  38 */       this.playerDir.mkdirs();
/*     */     }
/*     */     
/*  41 */     h();
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private void h()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: new 47	java/io/File
/*     */     //   3: dup
/*     */     //   4: aload_0
/*     */     //   5: getfield 52	net/minecraft/server/v1_8_R3/WorldNBTStorage:baseDir	Ljava/io/File;
/*     */     //   8: ldc 81
/*     */     //   10: invokespecial 50	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
/*     */     //   13: astore_1
/*     */     //   14: new 83	java/io/DataOutputStream
/*     */     //   17: dup
/*     */     //   18: new 85	java/io/FileOutputStream
/*     */     //   21: dup
/*     */     //   22: aload_1
/*     */     //   23: invokespecial 88	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
/*     */     //   26: invokespecial 91	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
/*     */     //   29: astore_2
/*     */     //   30: aload_2
/*     */     //   31: aload_0
/*     */     //   32: getfield 43	net/minecraft/server/v1_8_R3/WorldNBTStorage:sessionId	J
/*     */     //   35: invokevirtual 95	java/io/DataOutputStream:writeLong	(J)V
/*     */     //   38: goto +10 -> 48
/*     */     //   41: astore_3
/*     */     //   42: aload_2
/*     */     //   43: invokevirtual 100	java/io/DataOutputStream:close	()V
/*     */     //   46: aload_3
/*     */     //   47: athrow
/*     */     //   48: aload_2
/*     */     //   49: invokevirtual 100	java/io/DataOutputStream:close	()V
/*     */     //   52: goto +40 -> 92
/*     */     //   55: astore_1
/*     */     //   56: aload_1
/*     */     //   57: invokevirtual 103	java/io/IOException:printStackTrace	()V
/*     */     //   60: new 105	java/lang/RuntimeException
/*     */     //   63: dup
/*     */     //   64: new 107	java/lang/StringBuilder
/*     */     //   67: dup
/*     */     //   68: ldc 109
/*     */     //   70: invokespecial 112	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   73: aload_0
/*     */     //   74: getfield 52	net/minecraft/server/v1_8_R3/WorldNBTStorage:baseDir	Ljava/io/File;
/*     */     //   77: invokevirtual 116	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   80: ldc 118
/*     */     //   82: invokevirtual 121	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   85: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   88: invokespecial 126	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
/*     */     //   91: athrow
/*     */     //   92: return
/*     */     // Line number table:
/*     */     //   Java source line #46	-> byte code offset #0
/*     */     //   Java source line #47	-> byte code offset #14
/*     */     //   Java source line #50	-> byte code offset #30
/*     */     //   Java source line #51	-> byte code offset #38
/*     */     //   Java source line #52	-> byte code offset #42
/*     */     //   Java source line #53	-> byte code offset #46
/*     */     //   Java source line #52	-> byte code offset #48
/*     */     //   Java source line #55	-> byte code offset #52
/*     */     //   Java source line #56	-> byte code offset #56
/*     */     //   Java source line #57	-> byte code offset #60
/*     */     //   Java source line #59	-> byte code offset #92
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	93	0	this	WorldNBTStorage
/*     */     //   13	10	1	file	File
/*     */     //   55	2	1	ioexception	IOException
/*     */     //   29	20	2	dataoutputstream	java.io.DataOutputStream
/*     */     //   41	6	3	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   30	41	41	finally
/*     */     //   0	52	55	java/io/IOException
/*     */   }
/*     */   
/*     */   public File getDirectory()
/*     */   {
/*  62 */     return this.baseDir;
/*     */   }
/*     */   
/*     */   public void checkSession() throws ExceptionWorldConflict {
/*     */     try {
/*  67 */       File file = new File(this.baseDir, "session.lock");
/*  68 */       DataInputStream datainputstream = new DataInputStream(new FileInputStream(file));
/*     */       try
/*     */       {
/*  71 */         if (datainputstream.readLong() != this.sessionId) {
/*  72 */           throw new ExceptionWorldConflict("The save for world located at " + this.baseDir + " is being accessed from another location, aborting");
/*     */         }
/*     */       } finally {
/*  75 */         datainputstream.close(); } datainputstream.close();
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/*  79 */       throw new ExceptionWorldConflict("Failed to check session lock for world located at " + this.baseDir + ", aborting. Stop the server and delete the session.lock in this world to prevent further issues.");
/*     */     }
/*     */   }
/*     */   
/*     */   public IChunkLoader createChunkLoader(WorldProvider worldprovider) {
/*  84 */     throw new RuntimeException("Old Chunk Storage is no longer supported.");
/*     */   }
/*     */   
/*     */   public WorldData getWorldData() {
/*  88 */     File file = new File(this.baseDir, "level.dat");
/*     */     
/*     */ 
/*     */ 
/*  92 */     if (file.exists()) {
/*     */       try {
/*  94 */         NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file));
/*  95 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Data");
/*  96 */         return new WorldData(nbttagcompound1);
/*     */       } catch (Exception exception) {
/*  98 */         exception.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 102 */     file = new File(this.baseDir, "level.dat_old");
/* 103 */     if (file.exists()) {
/*     */       try {
/* 105 */         NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file));
/* 106 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Data");
/* 107 */         return new WorldData(nbttagcompound1);
/*     */       } catch (Exception exception1) {
/* 109 */         exception1.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   public void saveWorldData(WorldData worlddata, NBTTagCompound nbttagcompound) {
/* 117 */     NBTTagCompound nbttagcompound1 = worlddata.a(nbttagcompound);
/* 118 */     NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/*     */     
/* 120 */     nbttagcompound2.set("Data", nbttagcompound1);
/*     */     try
/*     */     {
/* 123 */       File file = new File(this.baseDir, "level.dat_new");
/* 124 */       File file1 = new File(this.baseDir, "level.dat_old");
/* 125 */       File file2 = new File(this.baseDir, "level.dat");
/*     */       
/* 127 */       NBTCompressedStreamTools.a(nbttagcompound2, new FileOutputStream(file));
/* 128 */       if (file1.exists()) {
/* 129 */         file1.delete();
/*     */       }
/*     */       
/* 132 */       file2.renameTo(file1);
/* 133 */       if (file2.exists()) {
/* 134 */         file2.delete();
/*     */       }
/*     */       
/* 137 */       file.renameTo(file2);
/* 138 */       if (file.exists()) {
/* 139 */         file.delete();
/*     */       }
/*     */     } catch (Exception exception) {
/* 142 */       exception.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveWorldData(WorldData worlddata)
/*     */   {
/* 148 */     NBTTagCompound nbttagcompound = worlddata.a();
/* 149 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */     
/* 151 */     nbttagcompound1.set("Data", nbttagcompound);
/*     */     try
/*     */     {
/* 154 */       File file = new File(this.baseDir, "level.dat_new");
/* 155 */       File file1 = new File(this.baseDir, "level.dat_old");
/* 156 */       File file2 = new File(this.baseDir, "level.dat");
/*     */       
/* 158 */       NBTCompressedStreamTools.a(nbttagcompound1, new FileOutputStream(file));
/* 159 */       if (file1.exists()) {
/* 160 */         file1.delete();
/*     */       }
/*     */       
/* 163 */       file2.renameTo(file1);
/* 164 */       if (file2.exists()) {
/* 165 */         file2.delete();
/*     */       }
/*     */       
/* 168 */       file.renameTo(file2);
/* 169 */       if (file.exists()) {
/* 170 */         file.delete();
/*     */       }
/*     */     } catch (Exception exception) {
/* 173 */       exception.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void save(EntityHuman entityhuman)
/*     */   {
/*     */     try {
/* 180 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */       
/* 182 */       entityhuman.e(nbttagcompound);
/* 183 */       File file = new File(this.playerDir, entityhuman.getUniqueID().toString() + ".dat.tmp");
/* 184 */       File file1 = new File(this.playerDir, entityhuman.getUniqueID().toString() + ".dat");
/*     */       
/* 186 */       NBTCompressedStreamTools.a(nbttagcompound, new FileOutputStream(file));
/* 187 */       if (file1.exists()) {
/* 188 */         file1.delete();
/*     */       }
/*     */       
/* 191 */       file.renameTo(file1);
/*     */     } catch (Exception localException) {
/* 193 */       a.warn("Failed to save player data for " + entityhuman.getName());
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound load(EntityHuman entityhuman)
/*     */   {
/* 199 */     NBTTagCompound nbttagcompound = null;
/*     */     try
/*     */     {
/* 202 */       File file = new File(this.playerDir, entityhuman.getUniqueID().toString() + ".dat");
/*     */       
/* 204 */       boolean usingWrongFile = false;
/* 205 */       if (!file.exists())
/*     */       {
/* 207 */         file = new File(this.playerDir, UUID.nameUUIDFromBytes(new StringBuilder("OfflinePlayer:").append(entityhuman.getName()).toString().getBytes("UTF-8")).toString() + ".dat");
/* 208 */         if (file.exists())
/*     */         {
/* 210 */           usingWrongFile = true;
/* 211 */           Bukkit.getServer().getLogger().warning("Using offline mode UUID file for player " + entityhuman.getName() + " as it is the only copy we can find.");
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 216 */       if ((file.exists()) && (file.isFile())) {
/* 217 */         nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file));
/*     */       }
/*     */       
/* 220 */       if (usingWrongFile)
/*     */       {
/* 222 */         file.renameTo(new File(file.getPath() + ".offline-read"));
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/* 226 */       a.warn("Failed to load player data for " + entityhuman.getName());
/*     */     }
/*     */     
/* 229 */     if (nbttagcompound != null)
/*     */     {
/* 231 */       if ((entityhuman instanceof EntityPlayer)) {
/* 232 */         CraftPlayer player = (CraftPlayer)entityhuman.getBukkitEntity();
/*     */         
/* 234 */         long modified = new File(this.playerDir, entityhuman.getUniqueID().toString() + ".dat").lastModified();
/* 235 */         if (modified < player.getFirstPlayed()) {
/* 236 */           player.setFirstPlayed(modified);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 241 */       entityhuman.f(nbttagcompound);
/*     */     }
/*     */     
/* 244 */     return nbttagcompound;
/*     */   }
/*     */   
/*     */   public NBTTagCompound getPlayerData(String s)
/*     */   {
/*     */     try {
/* 250 */       File file1 = new File(this.playerDir, s + ".dat");
/*     */       
/* 252 */       if (file1.exists()) {
/* 253 */         return NBTCompressedStreamTools.a(new FileInputStream(file1));
/*     */       }
/*     */     } catch (Exception localException) {
/* 256 */       a.warn("Failed to load player data for " + s);
/*     */     }
/*     */     
/* 259 */     return null;
/*     */   }
/*     */   
/*     */   public IPlayerFileData getPlayerFileData()
/*     */   {
/* 264 */     return this;
/*     */   }
/*     */   
/*     */   public String[] getSeenPlayers() {
/* 268 */     String[] astring = this.playerDir.list();
/*     */     
/* 270 */     if (astring == null) {
/* 271 */       astring = new String[0];
/*     */     }
/*     */     
/* 274 */     for (int i = 0; i < astring.length; i++) {
/* 275 */       if (astring[i].endsWith(".dat")) {
/* 276 */         astring[i] = astring[i].substring(0, astring[i].length() - 4);
/*     */       }
/*     */     }
/*     */     
/* 280 */     return astring;
/*     */   }
/*     */   
/*     */   public void a() {}
/*     */   
/*     */   public File getDataFile(String s) {
/* 286 */     return new File(this.dataDir, s + ".dat");
/*     */   }
/*     */   
/*     */   public String g() {
/* 290 */     return this.f;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public UUID getUUID()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 45	net/minecraft/server/v1_8_R3/WorldNBTStorage:uuid	Ljava/util/UUID;
/*     */     //   4: ifnull +8 -> 12
/*     */     //   7: aload_0
/*     */     //   8: getfield 45	net/minecraft/server/v1_8_R3/WorldNBTStorage:uuid	Ljava/util/UUID;
/*     */     //   11: areturn
/*     */     //   12: new 47	java/io/File
/*     */     //   15: dup
/*     */     //   16: aload_0
/*     */     //   17: getfield 52	net/minecraft/server/v1_8_R3/WorldNBTStorage:baseDir	Ljava/io/File;
/*     */     //   20: ldc_w 360
/*     */     //   23: invokespecial 50	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
/*     */     //   26: astore_1
/*     */     //   27: aload_1
/*     */     //   28: invokevirtual 169	java/io/File:exists	()Z
/*     */     //   31: ifeq +123 -> 154
/*     */     //   34: aconst_null
/*     */     //   35: astore_2
/*     */     //   36: new 137	java/io/DataInputStream
/*     */     //   39: dup
/*     */     //   40: new 139	java/io/FileInputStream
/*     */     //   43: dup
/*     */     //   44: aload_1
/*     */     //   45: invokespecial 140	java/io/FileInputStream:<init>	(Ljava/io/File;)V
/*     */     //   48: invokespecial 143	java/io/DataInputStream:<init>	(Ljava/io/InputStream;)V
/*     */     //   51: astore_2
/*     */     //   52: aload_0
/*     */     //   53: new 240	java/util/UUID
/*     */     //   56: dup
/*     */     //   57: aload_2
/*     */     //   58: invokevirtual 146	java/io/DataInputStream:readLong	()J
/*     */     //   61: aload_2
/*     */     //   62: invokevirtual 146	java/io/DataInputStream:readLong	()J
/*     */     //   65: invokespecial 363	java/util/UUID:<init>	(JJ)V
/*     */     //   68: dup_x1
/*     */     //   69: putfield 45	net/minecraft/server/v1_8_R3/WorldNBTStorage:uuid	Ljava/util/UUID;
/*     */     //   72: astore_3
/*     */     //   73: aload_2
/*     */     //   74: ifnull +11 -> 85
/*     */     //   77: aload_2
/*     */     //   78: invokevirtual 152	java/io/DataInputStream:close	()V
/*     */     //   81: goto +4 -> 85
/*     */     //   84: pop
/*     */     //   85: aload_3
/*     */     //   86: areturn
/*     */     //   87: astore 4
/*     */     //   89: getstatic 31	net/minecraft/server/v1_8_R3/WorldNBTStorage:a	Lorg/apache/logging/log4j/Logger;
/*     */     //   92: new 107	java/lang/StringBuilder
/*     */     //   95: dup
/*     */     //   96: ldc_w 365
/*     */     //   99: invokespecial 112	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   102: aload_1
/*     */     //   103: invokevirtual 116	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   106: ldc_w 367
/*     */     //   109: invokevirtual 121	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   112: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   115: aload 4
/*     */     //   117: invokeinterface 370 3 0
/*     */     //   122: aload_2
/*     */     //   123: ifnull +31 -> 154
/*     */     //   126: aload_2
/*     */     //   127: invokevirtual 152	java/io/DataInputStream:close	()V
/*     */     //   130: goto +24 -> 154
/*     */     //   133: pop
/*     */     //   134: goto +20 -> 154
/*     */     //   137: astore 5
/*     */     //   139: aload_2
/*     */     //   140: ifnull +11 -> 151
/*     */     //   143: aload_2
/*     */     //   144: invokevirtual 152	java/io/DataInputStream:close	()V
/*     */     //   147: goto +4 -> 151
/*     */     //   150: pop
/*     */     //   151: aload 5
/*     */     //   153: athrow
/*     */     //   154: aload_0
/*     */     //   155: invokestatic 373	java/util/UUID:randomUUID	()Ljava/util/UUID;
/*     */     //   158: putfield 45	net/minecraft/server/v1_8_R3/WorldNBTStorage:uuid	Ljava/util/UUID;
/*     */     //   161: aconst_null
/*     */     //   162: astore_2
/*     */     //   163: new 83	java/io/DataOutputStream
/*     */     //   166: dup
/*     */     //   167: new 85	java/io/FileOutputStream
/*     */     //   170: dup
/*     */     //   171: aload_1
/*     */     //   172: invokespecial 88	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
/*     */     //   175: invokespecial 91	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
/*     */     //   178: astore_2
/*     */     //   179: aload_2
/*     */     //   180: aload_0
/*     */     //   181: getfield 45	net/minecraft/server/v1_8_R3/WorldNBTStorage:uuid	Ljava/util/UUID;
/*     */     //   184: invokevirtual 376	java/util/UUID:getMostSignificantBits	()J
/*     */     //   187: invokevirtual 95	java/io/DataOutputStream:writeLong	(J)V
/*     */     //   190: aload_2
/*     */     //   191: aload_0
/*     */     //   192: getfield 45	net/minecraft/server/v1_8_R3/WorldNBTStorage:uuid	Ljava/util/UUID;
/*     */     //   195: invokevirtual 379	java/util/UUID:getLeastSignificantBits	()J
/*     */     //   198: invokevirtual 95	java/io/DataOutputStream:writeLong	(J)V
/*     */     //   201: goto +64 -> 265
/*     */     //   204: astore 4
/*     */     //   206: getstatic 31	net/minecraft/server/v1_8_R3/WorldNBTStorage:a	Lorg/apache/logging/log4j/Logger;
/*     */     //   209: new 107	java/lang/StringBuilder
/*     */     //   212: dup
/*     */     //   213: ldc_w 381
/*     */     //   216: invokespecial 112	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
/*     */     //   219: aload_1
/*     */     //   220: invokevirtual 116	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
/*     */     //   223: invokevirtual 125	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   226: aload 4
/*     */     //   228: invokeinterface 370 3 0
/*     */     //   233: aload_2
/*     */     //   234: ifnull +43 -> 277
/*     */     //   237: aload_2
/*     */     //   238: invokevirtual 100	java/io/DataOutputStream:close	()V
/*     */     //   241: goto +36 -> 277
/*     */     //   244: pop
/*     */     //   245: goto +32 -> 277
/*     */     //   248: astore 5
/*     */     //   250: aload_2
/*     */     //   251: ifnull +11 -> 262
/*     */     //   254: aload_2
/*     */     //   255: invokevirtual 100	java/io/DataOutputStream:close	()V
/*     */     //   258: goto +4 -> 262
/*     */     //   261: pop
/*     */     //   262: aload 5
/*     */     //   264: athrow
/*     */     //   265: aload_2
/*     */     //   266: ifnull +11 -> 277
/*     */     //   269: aload_2
/*     */     //   270: invokevirtual 100	java/io/DataOutputStream:close	()V
/*     */     //   273: goto +4 -> 277
/*     */     //   276: pop
/*     */     //   277: aload_0
/*     */     //   278: getfield 45	net/minecraft/server/v1_8_R3/WorldNBTStorage:uuid	Ljava/util/UUID;
/*     */     //   281: areturn
/*     */     // Line number table:
/*     */     //   Java source line #295	-> byte code offset #0
/*     */     //   Java source line #296	-> byte code offset #12
/*     */     //   Java source line #297	-> byte code offset #27
/*     */     //   Java source line #298	-> byte code offset #34
/*     */     //   Java source line #300	-> byte code offset #36
/*     */     //   Java source line #301	-> byte code offset #52
/*     */     //   Java source line #305	-> byte code offset #73
/*     */     //   Java source line #307	-> byte code offset #77
/*     */     //   Java source line #308	-> byte code offset #81
/*     */     //   Java source line #301	-> byte code offset #85
/*     */     //   Java source line #302	-> byte code offset #87
/*     */     //   Java source line #303	-> byte code offset #89
/*     */     //   Java source line #305	-> byte code offset #122
/*     */     //   Java source line #307	-> byte code offset #126
/*     */     //   Java source line #308	-> byte code offset #130
/*     */     //   Java source line #304	-> byte code offset #137
/*     */     //   Java source line #305	-> byte code offset #139
/*     */     //   Java source line #307	-> byte code offset #143
/*     */     //   Java source line #308	-> byte code offset #147
/*     */     //   Java source line #312	-> byte code offset #151
/*     */     //   Java source line #314	-> byte code offset #154
/*     */     //   Java source line #315	-> byte code offset #161
/*     */     //   Java source line #317	-> byte code offset #163
/*     */     //   Java source line #318	-> byte code offset #179
/*     */     //   Java source line #319	-> byte code offset #190
/*     */     //   Java source line #320	-> byte code offset #201
/*     */     //   Java source line #321	-> byte code offset #206
/*     */     //   Java source line #323	-> byte code offset #233
/*     */     //   Java source line #325	-> byte code offset #237
/*     */     //   Java source line #326	-> byte code offset #241
/*     */     //   Java source line #322	-> byte code offset #248
/*     */     //   Java source line #323	-> byte code offset #250
/*     */     //   Java source line #325	-> byte code offset #254
/*     */     //   Java source line #326	-> byte code offset #258
/*     */     //   Java source line #330	-> byte code offset #262
/*     */     //   Java source line #323	-> byte code offset #265
/*     */     //   Java source line #325	-> byte code offset #269
/*     */     //   Java source line #326	-> byte code offset #273
/*     */     //   Java source line #331	-> byte code offset #277
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	282	0	this	WorldNBTStorage
/*     */     //   26	194	1	file1	File
/*     */     //   35	109	2	dis	DataInputStream
/*     */     //   162	108	2	dos	java.io.DataOutputStream
/*     */     //   72	14	3	localUUID	UUID
/*     */     //   87	29	4	ex	IOException
/*     */     //   204	23	4	ex	IOException
/*     */     //   137	15	5	localObject1	Object
/*     */     //   248	15	5	localObject2	Object
/*     */     //   84	1	9	localIOException1	IOException
/*     */     //   133	1	10	localIOException2	IOException
/*     */     //   150	1	11	localIOException3	IOException
/*     */     //   244	1	12	localIOException4	IOException
/*     */     //   261	1	13	localIOException5	IOException
/*     */     //   276	1	14	localIOException6	IOException
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   77	81	84	java/io/IOException
/*     */     //   36	73	87	java/io/IOException
/*     */     //   126	130	133	java/io/IOException
/*     */     //   36	73	137	finally
/*     */     //   87	122	137	finally
/*     */     //   143	147	150	java/io/IOException
/*     */     //   163	201	204	java/io/IOException
/*     */     //   237	241	244	java/io/IOException
/*     */     //   163	233	248	finally
/*     */     //   254	258	261	java/io/IOException
/*     */     //   269	273	276	java/io/IOException
/*     */   }
/*     */   
/*     */   public File getPlayerDir()
/*     */   {
/* 335 */     return this.playerDir;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldNBTStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */