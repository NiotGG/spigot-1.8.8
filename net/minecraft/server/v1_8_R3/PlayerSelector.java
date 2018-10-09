/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.ComparisonChain;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class PlayerSelector
/*     */ {
/*  37 */   private static final Pattern a = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  46 */   private static final Pattern b = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
/*  47 */   private static final Pattern c = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
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
/*  70 */   private static final Set<String> d = Sets.newHashSet(new String[] { "x", "y", "z", "dx", "dy", "dz", "rm", "r" });
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
/*     */   public static EntityPlayer getPlayer(ICommandListener paramICommandListener, String paramString)
/*     */   {
/*  83 */     return (EntityPlayer)getEntity(paramICommandListener, paramString, EntityPlayer.class);
/*     */   }
/*     */   
/*     */   public static <T extends Entity> T getEntity(ICommandListener paramICommandListener, String paramString, Class<? extends T> paramClass)
/*     */   {
/*  88 */     List localList = getPlayers(paramICommandListener, paramString, paramClass);
/*  89 */     return localList.size() == 1 ? (Entity)localList.get(0) : null;
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent getPlayerNames(ICommandListener paramICommandListener, String paramString)
/*     */   {
/*  94 */     List localList = getPlayers(paramICommandListener, paramString, Entity.class);
/*  95 */     if (localList.isEmpty()) {
/*  96 */       return null;
/*     */     }
/*     */     
/*  99 */     ArrayList localArrayList = Lists.newArrayList();
/* 100 */     for (Entity localEntity : localList) {
/* 101 */       localArrayList.add(localEntity.getScoreboardDisplayName());
/*     */     }
/*     */     
/* 104 */     return CommandAbstract.a(localArrayList);
/*     */   }
/*     */   
/*     */   public static <T extends Entity> List<T> getPlayers(ICommandListener paramICommandListener, String paramString, Class<? extends T> paramClass) {
/* 108 */     Matcher localMatcher = a.matcher(paramString);
/*     */     
/* 110 */     if ((localMatcher.matches()) && (paramICommandListener.a(1, "@"))) {
/* 111 */       Map localMap = c(localMatcher.group(2));
/* 112 */       if (!b(paramICommandListener, localMap)) {
/* 113 */         return Collections.emptyList();
/*     */       }
/*     */       
/* 116 */       String str = localMatcher.group(1);
/* 117 */       BlockPosition localBlockPosition = b(localMap, paramICommandListener.getChunkCoordinates());
/* 118 */       List localList = a(paramICommandListener, localMap);
/* 119 */       ArrayList localArrayList1 = Lists.newArrayList();
/*     */       
/* 121 */       for (World localWorld : localList)
/* 122 */         if (localWorld != null)
/*     */         {
/*     */ 
/*     */ 
/* 126 */           ArrayList localArrayList2 = Lists.newArrayList();
/* 127 */           localArrayList2.addAll(a(localMap, str));
/* 128 */           localArrayList2.addAll(b(localMap));
/* 129 */           localArrayList2.addAll(c(localMap));
/* 130 */           localArrayList2.addAll(d(localMap));
/* 131 */           localArrayList2.addAll(e(localMap));
/* 132 */           localArrayList2.addAll(f(localMap));
/* 133 */           localArrayList2.addAll(a(localMap, localBlockPosition));
/* 134 */           localArrayList2.addAll(g(localMap));
/*     */           
/* 136 */           localArrayList1.addAll(a(localMap, paramClass, localArrayList2, str, localWorld, localBlockPosition));
/*     */         }
/* 138 */       return a(localArrayList1, localMap, paramICommandListener, paramClass, str, localBlockPosition);
/*     */     }
/*     */     
/* 141 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   private static List<World> a(ICommandListener paramICommandListener, Map<String, String> paramMap) {
/* 145 */     ArrayList localArrayList = Lists.newArrayList();
/* 146 */     if (h(paramMap)) {
/* 147 */       localArrayList.add(paramICommandListener.getWorld());
/*     */     } else {
/* 149 */       Collections.addAll(localArrayList, MinecraftServer.getServer().worldServer);
/*     */     }
/* 151 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static <T extends Entity> boolean b(ICommandListener paramICommandListener, Map<String, String> paramMap) {
/* 155 */     String str = b(paramMap, "type");
/* 156 */     str = (str != null) && (str.startsWith("!")) ? str.substring(1) : str;
/* 157 */     if ((str != null) && (!EntityTypes.b(str))) {
/* 158 */       ChatMessage localChatMessage = new ChatMessage("commands.generic.entity.invalidType", new Object[] { str });
/* 159 */       localChatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/* 160 */       paramICommandListener.sendMessage(localChatMessage);
/* 161 */       return false;
/*     */     }
/* 163 */     return true;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> a(Map<String, String> paramMap, String paramString) {
/* 167 */     ArrayList localArrayList = Lists.newArrayList();
/* 168 */     String str1 = b(paramMap, "type");
/* 169 */     final boolean bool = (str1 != null) && (str1.startsWith("!"));
/* 170 */     if (bool) {
/* 171 */       str1 = str1.substring(1);
/*     */     }
/* 173 */     String str2 = str1;
/*     */     
/* 175 */     int i = !paramString.equals("e") ? 1 : 0;
/* 176 */     int j = (paramString.equals("r")) && (str1 != null) ? 1 : 0;
/* 177 */     if (((str1 != null) && (paramString.equals("e"))) || (j != 0)) {
/* 178 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 181 */           return EntityTypes.a(paramAnonymousEntity, this.a) != bool;
/*     */         }
/*     */       });
/* 184 */     } else if (i != 0) {
/* 185 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 188 */           return paramAnonymousEntity instanceof EntityHuman;
/*     */         }
/*     */       });
/*     */     }
/* 192 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> b(Map<String, String> paramMap) {
/* 196 */     ArrayList localArrayList = Lists.newArrayList();
/* 197 */     int i = a(paramMap, "lm", -1);
/* 198 */     final int j = a(paramMap, "l", -1);
/*     */     
/* 200 */     if ((i > -1) || (j > -1)) {
/* 201 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 204 */           if (!(paramAnonymousEntity instanceof EntityPlayer)) {
/* 205 */             return false;
/*     */           }
/*     */           
/* 208 */           EntityPlayer localEntityPlayer = (EntityPlayer)paramAnonymousEntity;
/* 209 */           return ((this.a <= -1) || (localEntityPlayer.expLevel >= this.a)) && ((j <= -1) || (localEntityPlayer.expLevel <= j));
/*     */         }
/*     */       });
/*     */     }
/* 213 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> c(Map<String, String> paramMap) {
/* 217 */     ArrayList localArrayList = Lists.newArrayList();
/* 218 */     int i = a(paramMap, "m", WorldSettings.EnumGamemode.NOT_SET.getId());
/*     */     
/* 220 */     if (i != WorldSettings.EnumGamemode.NOT_SET.getId()) {
/* 221 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 224 */           if (!(paramAnonymousEntity instanceof EntityPlayer)) {
/* 225 */             return false;
/*     */           }
/*     */           
/* 228 */           EntityPlayer localEntityPlayer = (EntityPlayer)paramAnonymousEntity;
/* 229 */           return localEntityPlayer.playerInteractManager.getGameMode().getId() == this.a;
/*     */         }
/*     */       });
/*     */     }
/* 233 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> d(Map<String, String> paramMap) {
/* 237 */     ArrayList localArrayList = Lists.newArrayList();
/* 238 */     String str1 = b(paramMap, "team");
/* 239 */     final boolean bool = (str1 != null) && (str1.startsWith("!"));
/* 240 */     if (bool) {
/* 241 */       str1 = str1.substring(1);
/*     */     }
/* 243 */     String str2 = str1;
/*     */     
/* 245 */     if (str1 != null) {
/* 246 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 249 */           if (!(paramAnonymousEntity instanceof EntityLiving)) {
/* 250 */             return false;
/*     */           }
/*     */           
/* 253 */           EntityLiving localEntityLiving = (EntityLiving)paramAnonymousEntity;
/* 254 */           ScoreboardTeamBase localScoreboardTeamBase = localEntityLiving.getScoreboardTeam();
/* 255 */           String str = localScoreboardTeamBase == null ? "" : localScoreboardTeamBase.getName();
/* 256 */           return str.equals(this.a) != bool;
/*     */         }
/*     */       });
/*     */     }
/* 260 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> e(Map<String, String> paramMap) {
/* 264 */     ArrayList localArrayList = Lists.newArrayList();
/* 265 */     Map localMap = a(paramMap);
/*     */     
/* 267 */     if ((localMap != null) && (localMap.size() > 0)) {
/* 268 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 271 */           Scoreboard localScoreboard = MinecraftServer.getServer().getWorldServer(0).getScoreboard();
/* 272 */           for (Map.Entry localEntry : this.a.entrySet()) {
/* 273 */             String str1 = (String)localEntry.getKey();
/* 274 */             int i = 0;
/*     */             
/* 276 */             if ((str1.endsWith("_min")) && (str1.length() > 4)) {
/* 277 */               i = 1;
/* 278 */               str1 = str1.substring(0, str1.length() - 4);
/*     */             }
/*     */             
/* 281 */             ScoreboardObjective localScoreboardObjective = localScoreboard.getObjective(str1);
/* 282 */             if (localScoreboardObjective == null) {
/* 283 */               return false;
/*     */             }
/* 285 */             String str2 = (paramAnonymousEntity instanceof EntityPlayer) ? paramAnonymousEntity.getName() : paramAnonymousEntity.getUniqueID().toString();
/* 286 */             if (!localScoreboard.b(str2, localScoreboardObjective)) {
/* 287 */               return false;
/*     */             }
/* 289 */             ScoreboardScore localScoreboardScore = localScoreboard.getPlayerScoreForObjective(str2, localScoreboardObjective);
/* 290 */             int j = localScoreboardScore.getScore();
/*     */             
/* 292 */             if ((j < ((Integer)localEntry.getValue()).intValue()) && (i != 0))
/* 293 */               return false;
/* 294 */             if ((j > ((Integer)localEntry.getValue()).intValue()) && (i == 0)) {
/* 295 */               return false;
/*     */             }
/*     */           }
/*     */           
/* 299 */           return true;
/*     */         }
/*     */       });
/*     */     }
/* 303 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> f(Map<String, String> paramMap) {
/* 307 */     ArrayList localArrayList = Lists.newArrayList();
/* 308 */     String str1 = b(paramMap, "name");
/* 309 */     final boolean bool = (str1 != null) && (str1.startsWith("!"));
/* 310 */     if (bool) {
/* 311 */       str1 = str1.substring(1);
/*     */     }
/* 313 */     String str2 = str1;
/*     */     
/* 315 */     if (str1 != null) {
/* 316 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 319 */           return paramAnonymousEntity.getName().equals(this.a) != bool;
/*     */         }
/*     */       });
/*     */     }
/* 323 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> a(Map<String, String> paramMap, BlockPosition paramBlockPosition) {
/* 327 */     ArrayList localArrayList = Lists.newArrayList();
/* 328 */     final int i = a(paramMap, "rm", -1);
/* 329 */     final int j = a(paramMap, "r", -1);
/*     */     
/* 331 */     if ((paramBlockPosition != null) && ((i >= 0) || (j >= 0))) {
/* 332 */       final int k = i * i;
/* 333 */       final int m = j * j;
/*     */       
/* 335 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 338 */           int i = (int)paramAnonymousEntity.c(this.a);
/* 339 */           return ((i < 0) || (i >= k)) && ((j < 0) || (i <= m));
/*     */         }
/*     */       });
/*     */     }
/* 343 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static List<Predicate<Entity>> g(Map<String, String> paramMap) {
/* 347 */     ArrayList localArrayList = Lists.newArrayList();
/* 348 */     int i; final int j; if ((paramMap.containsKey("rym")) || (paramMap.containsKey("ry"))) {
/* 349 */       i = a(a(paramMap, "rym", 0));
/* 350 */       j = a(a(paramMap, "ry", 359));
/*     */       
/* 352 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 355 */           int i = PlayerSelector.a((int)Math.floor(paramAnonymousEntity.yaw));
/* 356 */           if (this.a > j) {
/* 357 */             return (i >= this.a) || (i <= j);
/*     */           }
/* 359 */           return (i >= this.a) && (i <= j);
/*     */         }
/*     */       });
/*     */     }
/* 363 */     if ((paramMap.containsKey("rxm")) || (paramMap.containsKey("rx"))) {
/* 364 */       i = a(a(paramMap, "rxm", 0));
/* 365 */       j = a(a(paramMap, "rx", 359));
/*     */       
/* 367 */       localArrayList.add(new Predicate()
/*     */       {
/*     */         public boolean a(Entity paramAnonymousEntity) {
/* 370 */           int i = PlayerSelector.a((int)Math.floor(paramAnonymousEntity.pitch));
/* 371 */           if (this.a > j) {
/* 372 */             return (i >= this.a) || (i <= j);
/*     */           }
/* 374 */           return (i >= this.a) && (i <= j);
/*     */         }
/*     */       });
/*     */     }
/* 378 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static <T extends Entity> List<T> a(Map<String, String> paramMap, Class<? extends T> paramClass, List<Predicate<Entity>> paramList, String paramString, World paramWorld, BlockPosition paramBlockPosition) {
/* 382 */     ArrayList localArrayList = Lists.newArrayList();
/* 383 */     String str = b(paramMap, "type");
/* 384 */     str = (str != null) && (str.startsWith("!")) ? str.substring(1) : str;
/*     */     
/* 386 */     int i = !paramString.equals("e") ? 1 : 0;
/* 387 */     int j = (paramString.equals("r")) && (str != null) ? 1 : 0;
/*     */     
/* 389 */     int k = a(paramMap, "dx", 0);
/* 390 */     int m = a(paramMap, "dy", 0);
/* 391 */     int n = a(paramMap, "dz", 0);
/*     */     
/* 393 */     int i1 = a(paramMap, "r", -1);
/*     */     
/* 395 */     Predicate localPredicate1 = Predicates.and(paramList);
/* 396 */     Predicate localPredicate2 = Predicates.and(IEntitySelector.a, localPredicate1);
/* 397 */     if (paramBlockPosition != null) {
/* 398 */       int i2 = paramWorld.players.size();
/* 399 */       int i3 = paramWorld.entityList.size();
/* 400 */       int i4 = i2 < i3 / 16 ? 1 : 0;
/* 401 */       AxisAlignedBB localAxisAlignedBB; if ((paramMap.containsKey("dx")) || (paramMap.containsKey("dy")) || (paramMap.containsKey("dz"))) {
/* 402 */         localAxisAlignedBB = a(paramBlockPosition, k, m, n);
/* 403 */         if ((i != 0) && (i4 != 0) && (j == 0)) {
/* 404 */           Predicate local11 = new Predicate()
/*     */           {
/*     */             public boolean a(Entity paramAnonymousEntity) {
/* 407 */               if ((paramAnonymousEntity.locX < this.a.a) || (paramAnonymousEntity.locY < this.a.b) || (paramAnonymousEntity.locZ < this.a.c)) {
/* 408 */                 return false;
/*     */               }
/* 410 */               if ((paramAnonymousEntity.locX >= this.a.d) || (paramAnonymousEntity.locY >= this.a.e) || (paramAnonymousEntity.locZ >= this.a.f)) {
/* 411 */                 return false;
/*     */               }
/* 413 */               return true;
/*     */             }
/* 415 */           };
/* 416 */           localArrayList.addAll(paramWorld.b(paramClass, Predicates.and(localPredicate2, local11)));
/*     */         } else {
/* 418 */           localArrayList.addAll(paramWorld.a(paramClass, localAxisAlignedBB, localPredicate2));
/*     */         }
/* 420 */       } else if (i1 >= 0) {
/* 421 */         localAxisAlignedBB = new AxisAlignedBB(paramBlockPosition.getX() - i1, paramBlockPosition.getY() - i1, paramBlockPosition.getZ() - i1, paramBlockPosition.getX() + i1 + 1, paramBlockPosition.getY() + i1 + 1, paramBlockPosition.getZ() + i1 + 1);
/* 422 */         if ((i != 0) && (i4 != 0) && (j == 0)) {
/* 423 */           localArrayList.addAll(paramWorld.b(paramClass, localPredicate2));
/*     */         } else {
/* 425 */           localArrayList.addAll(paramWorld.a(paramClass, localAxisAlignedBB, localPredicate2));
/*     */         }
/*     */       }
/* 428 */       else if (paramString.equals("a")) {
/* 429 */         localArrayList.addAll(paramWorld.b(paramClass, localPredicate1));
/* 430 */       } else if ((paramString.equals("p")) || ((paramString.equals("r")) && (j == 0))) {
/* 431 */         localArrayList.addAll(paramWorld.b(paramClass, localPredicate2));
/*     */       } else {
/* 433 */         localArrayList.addAll(paramWorld.a(paramClass, localPredicate2));
/*     */       }
/*     */       
/*     */     }
/* 437 */     else if (paramString.equals("a")) {
/* 438 */       localArrayList.addAll(paramWorld.b(paramClass, localPredicate1));
/* 439 */     } else if ((paramString.equals("p")) || ((paramString.equals("r")) && (j == 0))) {
/* 440 */       localArrayList.addAll(paramWorld.b(paramClass, localPredicate2));
/*     */     } else {
/* 442 */       localArrayList.addAll(paramWorld.a(paramClass, localPredicate2));
/*     */     }
/*     */     
/* 445 */     return localArrayList;
/*     */   }
/*     */   
/*     */   private static <T extends Entity> List<T> a(List<T> paramList, Map<String, String> paramMap, ICommandListener paramICommandListener, Class<? extends T> paramClass, String paramString, BlockPosition paramBlockPosition) {
/* 449 */     int i = a(paramMap, "c", (paramString.equals("a")) || (paramString.equals("e")) ? 0 : 1);
/*     */     
/* 451 */     if ((paramString.equals("p")) || (paramString.equals("a")) || (paramString.equals("e"))) {
/* 452 */       if (paramBlockPosition != null) {
/* 453 */         Collections.sort(paramList, new Comparator()
/*     */         {
/*     */           public int a(Entity paramAnonymousEntity1, Entity paramAnonymousEntity2) {
/* 456 */             return ComparisonChain.start().compare(paramAnonymousEntity1.b(this.a), paramAnonymousEntity2.b(this.a)).result();
/*     */           }
/*     */         });
/*     */       }
/* 460 */     } else if (paramString.equals("r")) {
/* 461 */       Collections.shuffle(paramList);
/*     */     }
/*     */     
/* 464 */     Entity localEntity = paramICommandListener.f();
/* 465 */     if ((localEntity != null) && (paramClass.isAssignableFrom(localEntity.getClass())) && (i == 1) && (paramList.contains(localEntity)) && (!"r".equals(paramString))) {
/* 466 */       paramList = Lists.newArrayList(new Entity[] { localEntity });
/*     */     }
/*     */     
/* 469 */     if (i != 0) {
/* 470 */       if (i < 0) {
/* 471 */         Collections.reverse(paramList);
/*     */       }
/* 473 */       paramList = paramList.subList(0, Math.min(Math.abs(i), paramList.size()));
/*     */     }
/* 475 */     return paramList;
/*     */   }
/*     */   
/*     */   private static AxisAlignedBB a(BlockPosition paramBlockPosition, int paramInt1, int paramInt2, int paramInt3) {
/* 479 */     int i = paramInt1 < 0 ? 1 : 0;
/* 480 */     int j = paramInt2 < 0 ? 1 : 0;
/* 481 */     int k = paramInt3 < 0 ? 1 : 0;
/* 482 */     int m = paramBlockPosition.getX() + (i != 0 ? paramInt1 : 0);
/* 483 */     int n = paramBlockPosition.getY() + (j != 0 ? paramInt2 : 0);
/* 484 */     int i1 = paramBlockPosition.getZ() + (k != 0 ? paramInt3 : 0);
/* 485 */     int i2 = paramBlockPosition.getX() + (i != 0 ? 0 : paramInt1) + 1;
/* 486 */     int i3 = paramBlockPosition.getY() + (j != 0 ? 0 : paramInt2) + 1;
/* 487 */     int i4 = paramBlockPosition.getZ() + (k != 0 ? 0 : paramInt3) + 1;
/* 488 */     return new AxisAlignedBB(m, n, i1, i2, i3, i4);
/*     */   }
/*     */   
/*     */   public static int a(int paramInt) {
/* 492 */     paramInt %= 360;
/* 493 */     if (paramInt >= 160) {
/* 494 */       paramInt -= 360;
/*     */     }
/* 496 */     if (paramInt < 0) {
/* 497 */       paramInt += 360;
/*     */     }
/* 499 */     return paramInt;
/*     */   }
/*     */   
/*     */   private static BlockPosition b(Map<String, String> paramMap, BlockPosition paramBlockPosition) {
/* 503 */     return new BlockPosition(a(paramMap, "x", paramBlockPosition.getX()), a(paramMap, "y", paramBlockPosition.getY()), a(paramMap, "z", paramBlockPosition.getZ()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean h(Map<String, String> paramMap)
/*     */   {
/* 511 */     for (String str : d) {
/* 512 */       if (paramMap.containsKey(str)) {
/* 513 */         return true;
/*     */       }
/*     */     }
/* 516 */     return false;
/*     */   }
/*     */   
/*     */   private static int a(Map<String, String> paramMap, String paramString, int paramInt) {
/* 520 */     return paramMap.containsKey(paramString) ? MathHelper.a((String)paramMap.get(paramString), paramInt) : paramInt;
/*     */   }
/*     */   
/*     */   private static String b(Map<String, String> paramMap, String paramString) {
/* 524 */     return (String)paramMap.get(paramString);
/*     */   }
/*     */   
/*     */   public static Map<String, Integer> a(Map<String, String> paramMap) {
/* 528 */     HashMap localHashMap = Maps.newHashMap();
/*     */     
/* 530 */     for (String str : paramMap.keySet()) {
/* 531 */       if ((str.startsWith("score_")) && (str.length() > "score_".length())) {
/* 532 */         localHashMap.put(str.substring("score_".length()), Integer.valueOf(MathHelper.a((String)paramMap.get(str), 1)));
/*     */       }
/*     */     }
/*     */     
/* 536 */     return localHashMap;
/*     */   }
/*     */   
/*     */   public static boolean isList(String paramString) {
/* 540 */     Matcher localMatcher = a.matcher(paramString);
/*     */     
/* 542 */     if (localMatcher.matches()) {
/* 543 */       Map localMap = c(localMatcher.group(2));
/* 544 */       String str = localMatcher.group(1);
/* 545 */       int i = ("a".equals(str)) || ("e".equals(str)) ? 0 : 1;
/* 546 */       return a(localMap, "c", i) != 1;
/*     */     }
/*     */     
/* 549 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isPattern(String paramString) {
/* 553 */     return a.matcher(paramString).matches();
/*     */   }
/*     */   
/*     */   private static Map<String, String> c(String paramString) {
/* 557 */     HashMap localHashMap = Maps.newHashMap();
/*     */     
/* 559 */     if (paramString == null) {
/* 560 */       return localHashMap;
/*     */     }
/*     */     
/* 563 */     int i = 0;
/* 564 */     int j = -1;
/*     */     
/* 566 */     Matcher localMatcher = b.matcher(paramString);
/* 567 */     Object localObject; while (localMatcher.find()) {
/* 568 */       localObject = null;
/*     */       
/* 570 */       switch (i++) {
/*     */       case 0: 
/* 572 */         localObject = "x";
/* 573 */         break;
/*     */       case 1: 
/* 575 */         localObject = "y";
/* 576 */         break;
/*     */       case 2: 
/* 578 */         localObject = "z";
/* 579 */         break;
/*     */       case 3: 
/* 581 */         localObject = "r";
/*     */       }
/*     */       
/*     */       
/* 585 */       if ((localObject != null) && (localMatcher.group(1).length() > 0)) {
/* 586 */         localHashMap.put(localObject, localMatcher.group(1));
/*     */       }
/* 588 */       j = localMatcher.end();
/*     */     }
/*     */     
/* 591 */     if (j < paramString.length()) {
/* 592 */       localObject = c.matcher(j == -1 ? paramString : paramString.substring(j));
/* 593 */       while (((Matcher)localObject).find()) {
/* 594 */         localHashMap.put(((Matcher)localObject).group(1), ((Matcher)localObject).group(2));
/*     */       }
/*     */     }
/*     */     
/* 598 */     return localHashMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PlayerSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */