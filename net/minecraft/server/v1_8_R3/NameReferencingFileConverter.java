/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.GameProfileRepository;
/*     */ import com.mojang.authlib.ProfileLookupCallback;
/*     */ import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.text.ParseException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ 
/*     */ public class NameReferencingFileConverter
/*     */ {
/*  31 */   private static final Logger e = ;
/*  32 */   public static final File a = new File("banned-ips.txt");
/*  33 */   public static final File b = new File("banned-players.txt");
/*  34 */   public static final File c = new File("ops.txt");
/*  35 */   public static final File d = new File("white-list.txt");
/*     */   
/*     */   static List<String> a(File file, Map<String, String[]> map) throws IOException {
/*  38 */     List list = Files.readLines(file, Charsets.UTF_8);
/*  39 */     Iterator iterator = list.iterator();
/*     */     
/*  41 */     while (iterator.hasNext()) {
/*  42 */       String s = (String)iterator.next();
/*     */       
/*  44 */       s = s.trim();
/*  45 */       if ((!s.startsWith("#")) && (s.length() >= 1)) {
/*  46 */         String[] astring = s.split("\\|");
/*     */         
/*  48 */         map.put(astring[0].toLowerCase(Locale.ROOT), astring);
/*     */       }
/*     */     }
/*     */     
/*  52 */     return list;
/*     */   }
/*     */   
/*     */   private static void a(MinecraftServer minecraftserver, Collection<String> collection, ProfileLookupCallback profilelookupcallback) {
/*  56 */     String[] astring = (String[])Iterators.toArray(Iterators.filter(collection.iterator(), new com.google.common.base.Predicate() {
/*     */       public boolean a(String s) {
/*  58 */         return !UtilColor.b(s);
/*     */       }
/*     */       
/*     */       public boolean apply(Object object) {
/*  62 */         return a((String)object);
/*     */       }
/*  64 */     }), String.class);
/*     */     
/*  66 */     if ((minecraftserver.getOnlineMode()) || (org.spigotmc.SpigotConfig.bungee)) {
/*  67 */       minecraftserver.getGameProfileRepository().findProfilesByNames(astring, com.mojang.authlib.Agent.MINECRAFT, profilelookupcallback);
/*     */     } else {
/*  69 */       String[] astring1 = astring;
/*  70 */       int i = astring.length;
/*     */       
/*  72 */       for (int j = 0; j < i; j++) {
/*  73 */         String s = astring1[j];
/*  74 */         UUID uuid = EntityHuman.a(new GameProfile(null, s));
/*  75 */         GameProfile gameprofile = new GameProfile(uuid, s);
/*     */         
/*  77 */         profilelookupcallback.onProfileLookupSucceeded(gameprofile);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean a(MinecraftServer minecraftserver)
/*     */   {
/*  84 */     final GameProfileBanList gameprofilebanlist = new GameProfileBanList(PlayerList.a);
/*     */     
/*  86 */     if ((b.exists()) && (b.isFile())) {
/*  87 */       if (gameprofilebanlist.c().exists()) {
/*     */         try {
/*  89 */           gameprofilebanlist.load();
/*     */         }
/*     */         catch (IOException localIOException1) {
/*  92 */           e.warn("Could not load existing file " + gameprofilebanlist.c().getName());
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/*  97 */         final HashMap hashmap = Maps.newHashMap();
/*     */         
/*  99 */         a(b, hashmap);
/* 100 */         ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
/*     */           public void onProfileLookupSucceeded(GameProfile gameprofile) {
/* 102 */             NameReferencingFileConverter.this.getUserCache().a(gameprofile);
/* 103 */             String[] astring = (String[])hashmap.get(gameprofile.getName().toLowerCase(Locale.ROOT));
/*     */             
/* 105 */             if (astring == null) {
/* 106 */               NameReferencingFileConverter.e.warn("Could not convert user banlist entry for " + gameprofile.getName());
/* 107 */               throw new NameReferencingFileConverter.FileConversionException("Profile not in the conversionlist", null, null);
/*     */             }
/* 109 */             Date date = astring.length > 1 ? NameReferencingFileConverter.b(astring[1], null) : null;
/* 110 */             String s = astring.length > 2 ? astring[2] : null;
/* 111 */             Date date1 = astring.length > 3 ? NameReferencingFileConverter.b(astring[3], null) : null;
/* 112 */             String s1 = astring.length > 4 ? astring[4] : null;
/*     */             
/* 114 */             gameprofilebanlist.add(new GameProfileBanEntry(gameprofile, date, s, date1, s1));
/*     */           }
/*     */           
/*     */           public void onProfileLookupFailed(GameProfile gameprofile, Exception exception)
/*     */           {
/* 119 */             NameReferencingFileConverter.e.warn("Could not lookup user banlist entry for " + gameprofile.getName(), exception);
/* 120 */             if (!(exception instanceof ProfileNotFoundException)) {
/* 121 */               throw new NameReferencingFileConverter.FileConversionException("Could not request user " + gameprofile.getName() + " from backend systems", exception, null);
/*     */             }
/*     */             
/*     */           }
/* 125 */         };
/* 126 */         a(minecraftserver, hashmap.keySet(), profilelookupcallback);
/* 127 */         gameprofilebanlist.save();
/* 128 */         c(b);
/* 129 */         return true;
/*     */       } catch (IOException ioexception) {
/* 131 */         e.warn("Could not read old user banlist to convert it!", ioexception);
/* 132 */         return false;
/*     */       } catch (FileConversionException namereferencingfileconverter_fileconversionexception) {
/* 134 */         e.error("Conversion failed, please try again later", namereferencingfileconverter_fileconversionexception);
/* 135 */         return false;
/*     */       }
/*     */     }
/* 138 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean b(MinecraftServer minecraftserver)
/*     */   {
/* 143 */     IpBanList ipbanlist = new IpBanList(PlayerList.b);
/*     */     
/* 145 */     if ((a.exists()) && (a.isFile())) {
/* 146 */       if (ipbanlist.c().exists()) {
/*     */         try {
/* 148 */           ipbanlist.load();
/*     */         }
/*     */         catch (IOException localIOException1) {
/* 151 */           e.warn("Could not load existing file " + ipbanlist.c().getName());
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 156 */         HashMap hashmap = Maps.newHashMap();
/*     */         
/* 158 */         a(a, hashmap);
/* 159 */         Iterator iterator = hashmap.keySet().iterator();
/*     */         
/* 161 */         while (iterator.hasNext()) {
/* 162 */           String s = (String)iterator.next();
/* 163 */           String[] astring = (String[])hashmap.get(s);
/* 164 */           Date date = astring.length > 1 ? b(astring[1], null) : null;
/* 165 */           String s1 = astring.length > 2 ? astring[2] : null;
/* 166 */           Date date1 = astring.length > 3 ? b(astring[3], null) : null;
/* 167 */           String s2 = astring.length > 4 ? astring[4] : null;
/*     */           
/* 169 */           ipbanlist.add(new IpBanEntry(s, date, s1, date1, s2));
/*     */         }
/*     */         
/* 172 */         ipbanlist.save();
/* 173 */         c(a);
/* 174 */         return true;
/*     */       } catch (IOException ioexception) {
/* 176 */         e.warn("Could not parse old ip banlist to convert it!", ioexception);
/* 177 */         return false;
/*     */       }
/*     */     }
/* 180 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean c(MinecraftServer minecraftserver)
/*     */   {
/* 185 */     final OpList oplist = new OpList(PlayerList.c);
/*     */     
/* 187 */     if ((c.exists()) && (c.isFile())) {
/* 188 */       if (oplist.c().exists()) {
/*     */         try {
/* 190 */           oplist.load();
/*     */         }
/*     */         catch (IOException localIOException1) {
/* 193 */           e.warn("Could not load existing file " + oplist.c().getName());
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 198 */         List list = Files.readLines(c, Charsets.UTF_8);
/* 199 */         ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
/*     */           public void onProfileLookupSucceeded(GameProfile gameprofile) {
/* 201 */             NameReferencingFileConverter.this.getUserCache().a(gameprofile);
/* 202 */             oplist.add(new OpListEntry(gameprofile, NameReferencingFileConverter.this.p(), false));
/*     */           }
/*     */           
/*     */           public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
/* 206 */             NameReferencingFileConverter.e.warn("Could not lookup oplist entry for " + gameprofile.getName(), exception);
/* 207 */             if (!(exception instanceof ProfileNotFoundException)) {
/* 208 */               throw new NameReferencingFileConverter.FileConversionException("Could not request user " + gameprofile.getName() + " from backend systems", exception, null);
/*     */             }
/*     */             
/*     */           }
/* 212 */         };
/* 213 */         a(minecraftserver, list, profilelookupcallback);
/* 214 */         oplist.save();
/* 215 */         c(c);
/* 216 */         return true;
/*     */       } catch (IOException ioexception) {
/* 218 */         e.warn("Could not read old oplist to convert it!", ioexception);
/* 219 */         return false;
/*     */       } catch (FileConversionException namereferencingfileconverter_fileconversionexception) {
/* 221 */         e.error("Conversion failed, please try again later", namereferencingfileconverter_fileconversionexception);
/* 222 */         return false;
/*     */       }
/*     */     }
/* 225 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean d(MinecraftServer minecraftserver)
/*     */   {
/* 230 */     final WhiteList whitelist = new WhiteList(PlayerList.d);
/*     */     
/* 232 */     if ((d.exists()) && (d.isFile())) {
/* 233 */       if (whitelist.c().exists()) {
/*     */         try {
/* 235 */           whitelist.load();
/*     */         }
/*     */         catch (IOException localIOException1) {
/* 238 */           e.warn("Could not load existing file " + whitelist.c().getName());
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 243 */         List list = Files.readLines(d, Charsets.UTF_8);
/* 244 */         ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
/*     */           public void onProfileLookupSucceeded(GameProfile gameprofile) {
/* 246 */             NameReferencingFileConverter.this.getUserCache().a(gameprofile);
/* 247 */             whitelist.add(new WhiteListEntry(gameprofile));
/*     */           }
/*     */           
/*     */           public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
/* 251 */             NameReferencingFileConverter.e.warn("Could not lookup user whitelist entry for " + gameprofile.getName(), exception);
/* 252 */             if (!(exception instanceof ProfileNotFoundException)) {
/* 253 */               throw new NameReferencingFileConverter.FileConversionException("Could not request user " + gameprofile.getName() + " from backend systems", exception, null);
/*     */             }
/*     */             
/*     */           }
/* 257 */         };
/* 258 */         a(minecraftserver, list, profilelookupcallback);
/* 259 */         whitelist.save();
/* 260 */         c(d);
/* 261 */         return true;
/*     */       } catch (IOException ioexception) {
/* 263 */         e.warn("Could not read old whitelist to convert it!", ioexception);
/* 264 */         return false;
/*     */       } catch (FileConversionException namereferencingfileconverter_fileconversionexception) {
/* 266 */         e.error("Conversion failed, please try again later", namereferencingfileconverter_fileconversionexception);
/* 267 */         return false;
/*     */       }
/*     */     }
/* 270 */     return true;
/*     */   }
/*     */   
/*     */   public static String a(String s)
/*     */   {
/* 275 */     if ((!UtilColor.b(s)) && (s.length() <= 16)) {
/* 276 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 277 */       GameProfile gameprofile = minecraftserver.getUserCache().getProfile(s);
/*     */       
/* 279 */       if ((gameprofile != null) && (gameprofile.getId() != null))
/* 280 */         return gameprofile.getId().toString();
/* 281 */       if ((!minecraftserver.T()) && (minecraftserver.getOnlineMode())) {
/* 282 */         final ArrayList arraylist = Lists.newArrayList();
/* 283 */         ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
/*     */           public void onProfileLookupSucceeded(GameProfile gameprofile) {
/* 285 */             NameReferencingFileConverter.this.getUserCache().a(gameprofile);
/* 286 */             arraylist.add(gameprofile);
/*     */           }
/*     */           
/*     */           public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
/* 290 */             NameReferencingFileConverter.e.warn("Could not lookup user whitelist entry for " + gameprofile.getName(), exception);
/*     */           }
/*     */           
/* 293 */         };
/* 294 */         a(minecraftserver, Lists.newArrayList(new String[] { s }), profilelookupcallback);
/* 295 */         return (arraylist.size() > 0) && (((GameProfile)arraylist.get(0)).getId() != null) ? ((GameProfile)arraylist.get(0)).getId().toString() : "";
/*     */       }
/* 297 */       return EntityHuman.a(new GameProfile(null, s)).toString();
/*     */     }
/*     */     
/* 300 */     return s;
/*     */   }
/*     */   
/*     */   public static boolean a(DedicatedServer dedicatedserver, PropertyManager propertymanager)
/*     */   {
/* 305 */     final File file = d(propertymanager);
/* 306 */     new File(file.getParentFile(), "playerdata");
/* 307 */     final File file2 = new File(file.getParentFile(), "unknownplayers");
/*     */     
/* 309 */     if ((file.exists()) && (file.isDirectory())) {
/* 310 */       File[] afile = file.listFiles();
/* 311 */       ArrayList arraylist = Lists.newArrayList();
/* 312 */       File[] afile1 = afile;
/* 313 */       int i = afile.length;
/*     */       
/* 315 */       for (int j = 0; j < i; j++) {
/* 316 */         File file3 = afile1[j];
/* 317 */         String s = file3.getName();
/*     */         
/* 319 */         if (s.toLowerCase(Locale.ROOT).endsWith(".dat")) {
/* 320 */           String s1 = s.substring(0, s.length() - ".dat".length());
/*     */           
/* 322 */           if (s1.length() > 0) {
/* 323 */             arraylist.add(s1);
/*     */           }
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 329 */         final String[] astring = (String[])arraylist.toArray(new String[arraylist.size()]);
/* 330 */         ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
/*     */           public void onProfileLookupSucceeded(GameProfile gameprofile) {
/* 332 */             NameReferencingFileConverter.this.getUserCache().a(gameprofile);
/* 333 */             UUID uuid = gameprofile.getId();
/*     */             
/* 335 */             if (uuid == null) {
/* 336 */               throw new NameReferencingFileConverter.FileConversionException("Missing UUID for user profile " + gameprofile.getName(), null, null);
/*     */             }
/* 338 */             a(file, a(gameprofile), uuid.toString());
/*     */           }
/*     */           
/*     */           public void onProfileLookupFailed(GameProfile gameprofile, Exception exception)
/*     */           {
/* 343 */             NameReferencingFileConverter.e.warn("Could not lookup user uuid for " + gameprofile.getName(), exception);
/* 344 */             if ((exception instanceof ProfileNotFoundException)) {
/* 345 */               String s = a(gameprofile);
/*     */               
/* 347 */               a(file, s, s);
/*     */             } else {
/* 349 */               throw new NameReferencingFileConverter.FileConversionException("Could not request user " + gameprofile.getName() + " from backend systems", exception, null);
/*     */             }
/*     */           }
/*     */           
/*     */           private void a(File file, String s, String s1) {
/* 354 */             File file1 = new File(file2, s + ".dat");
/* 355 */             File file3 = new File(file, s1 + ".dat");
/*     */             
/*     */ 
/* 358 */             NBTTagCompound root = null;
/*     */             try
/*     */             {
/* 361 */               root = NBTCompressedStreamTools.a(new java.io.FileInputStream(file1));
/*     */             } catch (Exception exception) {
/* 363 */               exception.printStackTrace();
/*     */             }
/*     */             
/* 366 */             if (root != null) {
/* 367 */               if (!root.hasKey("bukkit")) {
/* 368 */                 root.set("bukkit", new NBTTagCompound());
/*     */               }
/* 370 */               NBTTagCompound data = root.getCompound("bukkit");
/* 371 */               data.setString("lastKnownName", s);
/*     */               try
/*     */               {
/* 374 */                 NBTCompressedStreamTools.a(root, new FileOutputStream(file2));
/*     */               } catch (Exception exception) {
/* 376 */                 exception.printStackTrace();
/*     */               }
/*     */             }
/*     */             
/*     */ 
/* 381 */             NameReferencingFileConverter.b(file);
/* 382 */             if (!file1.renameTo(file3)) {
/* 383 */               throw new NameReferencingFileConverter.FileConversionException("Could not convert file for " + s, null, null);
/*     */             }
/*     */           }
/*     */           
/*     */           private String a(GameProfile gameprofile) {
/* 388 */             String s = null;
/*     */             
/* 390 */             for (int i = 0; i < astring.length; i++) {
/* 391 */               if ((astring[i] != null) && (astring[i].equalsIgnoreCase(gameprofile.getName()))) {
/* 392 */                 s = astring[i];
/* 393 */                 break;
/*     */               }
/*     */             }
/*     */             
/* 397 */             if (s == null) {
/* 398 */               throw new NameReferencingFileConverter.FileConversionException("Could not find the filename for " + gameprofile.getName() + " anymore", null, null);
/*     */             }
/* 400 */             return s;
/*     */           }
/*     */           
/*     */ 
/* 404 */         };
/* 405 */         a(dedicatedserver, Lists.newArrayList(astring), profilelookupcallback);
/* 406 */         return true;
/*     */       } catch (FileConversionException namereferencingfileconverter_fileconversionexception) {
/* 408 */         e.error("Conversion failed, please try again later", namereferencingfileconverter_fileconversionexception);
/* 409 */         return false;
/*     */       }
/*     */     }
/* 412 */     return true;
/*     */   }
/*     */   
/*     */   private static void b(File file)
/*     */   {
/* 417 */     if (file.exists()) {
/* 418 */       if (!file.isDirectory()) {
/* 419 */         throw new FileConversionException("Can't create directory " + file.getName() + " in world save directory.", null, null);
/*     */       }
/* 421 */     } else if (!file.mkdirs()) {
/* 422 */       throw new FileConversionException("Can't create directory " + file.getName() + " in world save directory.", null, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean a(PropertyManager propertymanager) {
/* 427 */     boolean flag = b(propertymanager);
/*     */     
/* 429 */     flag = (flag) && (c(propertymanager));
/* 430 */     return flag;
/*     */   }
/*     */   
/*     */   private static boolean b(PropertyManager propertymanager) {
/* 434 */     boolean flag = false;
/*     */     
/* 436 */     if ((b.exists()) && (b.isFile())) {
/* 437 */       flag = true;
/*     */     }
/*     */     
/* 440 */     boolean flag1 = false;
/*     */     
/* 442 */     if ((a.exists()) && (a.isFile())) {
/* 443 */       flag1 = true;
/*     */     }
/*     */     
/* 446 */     boolean flag2 = false;
/*     */     
/* 448 */     if ((c.exists()) && (c.isFile())) {
/* 449 */       flag2 = true;
/*     */     }
/*     */     
/* 452 */     boolean flag3 = false;
/*     */     
/* 454 */     if ((d.exists()) && (d.isFile())) {
/* 455 */       flag3 = true;
/*     */     }
/*     */     
/* 458 */     if ((!flag) && (!flag1) && (!flag2) && (!flag3)) {
/* 459 */       return true;
/*     */     }
/* 461 */     e.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
/* 462 */     e.warn("** please remove the following files and restart the server:");
/* 463 */     if (flag) {
/* 464 */       e.warn("* " + b.getName());
/*     */     }
/*     */     
/* 467 */     if (flag1) {
/* 468 */       e.warn("* " + a.getName());
/*     */     }
/*     */     
/* 471 */     if (flag2) {
/* 472 */       e.warn("* " + c.getName());
/*     */     }
/*     */     
/* 475 */     if (flag3) {
/* 476 */       e.warn("* " + d.getName());
/*     */     }
/*     */     
/* 479 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean c(PropertyManager propertymanager)
/*     */   {
/* 484 */     File file = d(propertymanager);
/*     */     
/* 486 */     if ((file.exists()) && (file.isDirectory()) && ((file.list().length > 0) || (!file.delete()))) {
/* 487 */       e.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
/* 488 */       e.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
/* 489 */       e.warn("** please restart the server and if the problem persists, remove the directory '{}'", new Object[] { file.getPath() });
/* 490 */       return false;
/*     */     }
/* 492 */     return true;
/*     */   }
/*     */   
/*     */   private static File d(PropertyManager propertymanager)
/*     */   {
/* 497 */     String s = propertymanager.getString("level-name", "world");
/* 498 */     File file = new File(MinecraftServer.getServer().server.getWorldContainer(), s);
/*     */     
/* 500 */     return new File(file, "players");
/*     */   }
/*     */   
/*     */   private static void c(File file) {
/* 504 */     File file1 = new File(file.getName() + ".converted");
/*     */     
/* 506 */     file.renameTo(file1);
/*     */   }
/*     */   
/*     */   private static Date b(String s, Date date)
/*     */   {
/*     */     Date date1;
/*     */     try {
/* 513 */       date1 = ExpirableListEntry.a.parse(s);
/*     */     } catch (ParseException localParseException) { Date date1;
/* 515 */       date1 = date;
/*     */     }
/*     */     
/* 518 */     return date1;
/*     */   }
/*     */   
/*     */   static class FileConversionException extends RuntimeException
/*     */   {
/*     */     private FileConversionException(String s, Throwable throwable) {
/* 524 */       super(throwable);
/*     */     }
/*     */     
/*     */     private FileConversionException(String s) {
/* 528 */       super();
/*     */     }
/*     */     
/*     */     FileConversionException(String s, Object object) {
/* 532 */       this(s);
/*     */     }
/*     */     
/*     */     FileConversionException(String s, Throwable throwable, Object object) {
/* 536 */       this(s, throwable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NameReferencingFileConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */