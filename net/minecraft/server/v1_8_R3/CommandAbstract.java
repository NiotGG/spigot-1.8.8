/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Functions;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.primitives.Doubles;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CommandAbstract
/*     */   implements ICommand
/*     */ {
/*     */   private static ICommandDispatcher a;
/*     */   
/*     */   public int a()
/*     */   {
/*  30 */     return 4;
/*     */   }
/*     */   
/*     */   public List<String> b()
/*     */   {
/*  35 */     return Collections.emptyList();
/*     */   }
/*     */   
/*     */   public boolean canUse(ICommandListener paramICommandListener)
/*     */   {
/*  40 */     return paramICommandListener.a(a(), getCommand());
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/*  46 */     return null;
/*     */   }
/*     */   
/*     */   public static int a(String paramString) throws ExceptionInvalidNumber {
/*     */     try {
/*  51 */       return Integer.parseInt(paramString);
/*     */     } catch (NumberFormatException localNumberFormatException) {
/*  53 */       throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString });
/*     */     }
/*     */   }
/*     */   
/*     */   public static int a(String paramString, int paramInt) throws ExceptionInvalidNumber {
/*  58 */     return a(paramString, paramInt, Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public static int a(String paramString, int paramInt1, int paramInt2) throws ExceptionInvalidNumber {
/*  62 */     int i = a(paramString);
/*  63 */     if (i < paramInt1)
/*  64 */       throw new ExceptionInvalidNumber("commands.generic.num.tooSmall", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt1) });
/*  65 */     if (i > paramInt2) {
/*  66 */       throw new ExceptionInvalidNumber("commands.generic.num.tooBig", new Object[] { Integer.valueOf(i), Integer.valueOf(paramInt2) });
/*     */     }
/*  68 */     return i;
/*     */   }
/*     */   
/*     */   public static long b(String paramString) throws ExceptionInvalidNumber {
/*     */     try {
/*  73 */       return Long.parseLong(paramString);
/*     */     } catch (NumberFormatException localNumberFormatException) {
/*  75 */       throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static long a(String paramString, long paramLong1, long paramLong2)
/*     */     throws ExceptionInvalidNumber
/*     */   {
/*  84 */     long l = b(paramString);
/*  85 */     if (l < paramLong1)
/*  86 */       throw new ExceptionInvalidNumber("commands.generic.num.tooSmall", new Object[] { Long.valueOf(l), Long.valueOf(paramLong1) });
/*  87 */     if (l > paramLong2) {
/*  88 */       throw new ExceptionInvalidNumber("commands.generic.num.tooBig", new Object[] { Long.valueOf(l), Long.valueOf(paramLong2) });
/*     */     }
/*  90 */     return l;
/*     */   }
/*     */   
/*     */   public static BlockPosition a(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt, boolean paramBoolean) throws ExceptionInvalidNumber {
/*  94 */     BlockPosition localBlockPosition = paramICommandListener.getChunkCoordinates();
/*  95 */     return new BlockPosition(b(localBlockPosition.getX(), paramArrayOfString[paramInt], -30000000, 30000000, paramBoolean), b(localBlockPosition.getY(), paramArrayOfString[(paramInt + 1)], 0, 256, false), b(localBlockPosition.getZ(), paramArrayOfString[(paramInt + 2)], -30000000, 30000000, paramBoolean));
/*     */   }
/*     */   
/*     */ 
/*     */   public static double c(String paramString)
/*     */     throws ExceptionInvalidNumber
/*     */   {
/*     */     try
/*     */     {
/* 104 */       double d = Double.parseDouble(paramString);
/* 105 */       if (!Doubles.isFinite(d)) {
/* 106 */         throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString });
/*     */       }
/* 108 */       return d;
/*     */     } catch (NumberFormatException localNumberFormatException) {
/* 110 */       throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { paramString });
/*     */     }
/*     */   }
/*     */   
/*     */   public static double a(String paramString, double paramDouble) throws ExceptionInvalidNumber {
/* 115 */     return a(paramString, paramDouble, Double.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public static double a(String paramString, double paramDouble1, double paramDouble2) throws ExceptionInvalidNumber {
/* 119 */     double d = c(paramString);
/* 120 */     if (d < paramDouble1)
/* 121 */       throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d), Double.valueOf(paramDouble1) });
/* 122 */     if (d > paramDouble2) {
/* 123 */       throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[] { Double.valueOf(d), Double.valueOf(paramDouble2) });
/*     */     }
/* 125 */     return d;
/*     */   }
/*     */   
/*     */   public static boolean d(String paramString) throws CommandException {
/* 129 */     if ((paramString.equals("true")) || (paramString.equals("1")))
/* 130 */       return true;
/* 131 */     if ((paramString.equals("false")) || (paramString.equals("0"))) {
/* 132 */       return false;
/*     */     }
/* 134 */     throw new CommandException("commands.generic.boolean.invalid", new Object[] { paramString });
/*     */   }
/*     */   
/*     */   public static EntityPlayer b(ICommandListener paramICommandListener) throws ExceptionPlayerNotFound
/*     */   {
/* 139 */     if ((paramICommandListener instanceof EntityPlayer)) {
/* 140 */       return (EntityPlayer)paramICommandListener;
/*     */     }
/* 142 */     throw new ExceptionPlayerNotFound("You must specify which player you wish to perform this action on.", new Object[0]);
/*     */   }
/*     */   
/*     */   public static EntityPlayer a(ICommandListener paramICommandListener, String paramString) throws ExceptionPlayerNotFound
/*     */   {
/* 147 */     EntityPlayer localEntityPlayer = PlayerSelector.getPlayer(paramICommandListener, paramString);
/*     */     
/* 149 */     if (localEntityPlayer == null) {
/*     */       try {
/* 151 */         localEntityPlayer = MinecraftServer.getServer().getPlayerList().a(UUID.fromString(paramString));
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException) {}
/*     */     }
/* 155 */     if (localEntityPlayer == null) {
/* 156 */       localEntityPlayer = MinecraftServer.getServer().getPlayerList().getPlayer(paramString);
/*     */     }
/*     */     
/* 159 */     if (localEntityPlayer == null) {
/* 160 */       throw new ExceptionPlayerNotFound();
/*     */     }
/*     */     
/* 163 */     return localEntityPlayer;
/*     */   }
/*     */   
/*     */   public static Entity b(ICommandListener paramICommandListener, String paramString) throws ExceptionEntityNotFound {
/* 167 */     return a(paramICommandListener, paramString, Entity.class);
/*     */   }
/*     */   
/*     */   public static <T extends Entity> T a(ICommandListener paramICommandListener, String paramString, Class<? extends T> paramClass) throws ExceptionEntityNotFound
/*     */   {
/* 172 */     Object localObject = PlayerSelector.getEntity(paramICommandListener, paramString, paramClass);
/*     */     
/* 174 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/* 175 */     if (localObject == null) {
/* 176 */       localObject = localMinecraftServer.getPlayerList().getPlayer(paramString);
/*     */     }
/*     */     
/* 179 */     if (localObject == null) {
/*     */       try {
/* 181 */         UUID localUUID = UUID.fromString(paramString);
/* 182 */         localObject = localMinecraftServer.a(localUUID);
/* 183 */         if (localObject == null) {
/* 184 */           localObject = localMinecraftServer.getPlayerList().a(localUUID);
/*     */         }
/*     */       } catch (IllegalArgumentException localIllegalArgumentException) {
/* 187 */         throw new ExceptionEntityNotFound("commands.generic.entity.invalidUuid", new Object[0]);
/*     */       }
/*     */     }
/*     */     
/* 191 */     if ((localObject == null) || (!paramClass.isAssignableFrom(localObject.getClass()))) {
/* 192 */       throw new ExceptionEntityNotFound();
/*     */     }
/*     */     
/* 195 */     return (T)localObject;
/*     */   }
/*     */   
/*     */   public static List<Entity> c(ICommandListener paramICommandListener, String paramString) throws ExceptionEntityNotFound {
/* 199 */     if (PlayerSelector.isPattern(paramString)) {
/* 200 */       return PlayerSelector.getPlayers(paramICommandListener, paramString, Entity.class);
/*     */     }
/* 202 */     return Lists.newArrayList(new Entity[] { b(paramICommandListener, paramString) });
/*     */   }
/*     */   
/*     */   public static String d(ICommandListener paramICommandListener, String paramString) throws ExceptionPlayerNotFound {
/*     */     try {
/* 207 */       return a(paramICommandListener, paramString).getName();
/*     */     } catch (ExceptionPlayerNotFound localExceptionPlayerNotFound) {
/* 209 */       if (PlayerSelector.isPattern(paramString)) {
/* 210 */         throw localExceptionPlayerNotFound;
/*     */       }
/*     */     }
/*     */     
/* 214 */     return paramString;
/*     */   }
/*     */   
/*     */   public static String e(ICommandListener paramICommandListener, String paramString) throws ExceptionEntityNotFound {
/*     */     try {
/* 219 */       return a(paramICommandListener, paramString).getName();
/*     */     } catch (ExceptionPlayerNotFound localExceptionPlayerNotFound) {
/*     */       try {
/* 222 */         return b(paramICommandListener, paramString).getUniqueID().toString();
/*     */       } catch (ExceptionEntityNotFound localExceptionEntityNotFound) {
/* 224 */         if (PlayerSelector.isPattern(paramString)) {
/* 225 */           throw localExceptionEntityNotFound;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 230 */     return paramString;
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent a(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws ExceptionPlayerNotFound {
/* 234 */     return b(paramICommandListener, paramArrayOfString, paramInt, false);
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent b(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt, boolean paramBoolean) throws ExceptionPlayerNotFound {
/* 238 */     ChatComponentText localChatComponentText = new ChatComponentText("");
/*     */     
/* 240 */     for (int i = paramInt; i < paramArrayOfString.length; i++) {
/* 241 */       if (i > paramInt) {
/* 242 */         localChatComponentText.a(" ");
/*     */       }
/* 244 */       Object localObject = new ChatComponentText(paramArrayOfString[i]);
/*     */       
/* 246 */       if (paramBoolean) {
/* 247 */         IChatBaseComponent localIChatBaseComponent = PlayerSelector.getPlayerNames(paramICommandListener, paramArrayOfString[i]);
/*     */         
/* 249 */         if (localIChatBaseComponent == null) {
/* 250 */           if (PlayerSelector.isPattern(paramArrayOfString[i])) {
/* 251 */             throw new ExceptionPlayerNotFound();
/*     */           }
/*     */         } else {
/* 254 */           localObject = localIChatBaseComponent;
/*     */         }
/*     */       }
/*     */       
/* 258 */       localChatComponentText.addSibling((IChatBaseComponent)localObject);
/*     */     }
/*     */     
/* 261 */     return localChatComponentText;
/*     */   }
/*     */   
/*     */   public static String a(String[] paramArrayOfString, int paramInt) {
/* 265 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/* 267 */     for (int i = paramInt; i < paramArrayOfString.length; i++) {
/* 268 */       if (i > paramInt) {
/* 269 */         localStringBuilder.append(" ");
/*     */       }
/* 271 */       String str = paramArrayOfString[i];
/*     */       
/* 273 */       localStringBuilder.append(str);
/*     */     }
/*     */     
/* 276 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public static CommandNumber a(double paramDouble, String paramString, boolean paramBoolean) throws ExceptionInvalidNumber {
/* 280 */     return a(paramDouble, paramString, -30000000, 30000000, paramBoolean);
/*     */   }
/*     */   
/*     */   public static CommandNumber a(double paramDouble, String paramString, int paramInt1, int paramInt2, boolean paramBoolean) throws ExceptionInvalidNumber {
/* 284 */     boolean bool1 = paramString.startsWith("~");
/* 285 */     if ((bool1) && (Double.isNaN(paramDouble))) {
/* 286 */       throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { Double.valueOf(paramDouble) });
/*     */     }
/* 288 */     double d = 0.0D;
/*     */     
/* 290 */     if ((!bool1) || (paramString.length() > 1)) {
/* 291 */       boolean bool2 = paramString.contains(".");
/* 292 */       if (bool1) {
/* 293 */         paramString = paramString.substring(1);
/*     */       }
/*     */       
/* 296 */       d += c(paramString);
/*     */       
/* 298 */       if ((!bool2) && (!bool1) && (paramBoolean)) {
/* 299 */         d += 0.5D;
/*     */       }
/*     */     }
/*     */     
/* 303 */     if ((paramInt1 != 0) || (paramInt2 != 0)) {
/* 304 */       if (d < paramInt1)
/* 305 */         throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d), Integer.valueOf(paramInt1) });
/* 306 */       if (d > paramInt2) {
/* 307 */         throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[] { Double.valueOf(d), Integer.valueOf(paramInt2) });
/*     */       }
/*     */     }
/*     */     
/* 311 */     return new CommandNumber(d + (bool1 ? paramDouble : 0.0D), d, bool1);
/*     */   }
/*     */   
/*     */   public static double b(double paramDouble, String paramString, boolean paramBoolean) throws ExceptionInvalidNumber {
/* 315 */     return b(paramDouble, paramString, -30000000, 30000000, paramBoolean);
/*     */   }
/*     */   
/*     */   public static double b(double paramDouble, String paramString, int paramInt1, int paramInt2, boolean paramBoolean) throws ExceptionInvalidNumber {
/* 319 */     boolean bool1 = paramString.startsWith("~");
/* 320 */     if ((bool1) && (Double.isNaN(paramDouble))) {
/* 321 */       throw new ExceptionInvalidNumber("commands.generic.num.invalid", new Object[] { Double.valueOf(paramDouble) });
/*     */     }
/* 323 */     double d = bool1 ? paramDouble : 0.0D;
/*     */     
/* 325 */     if ((!bool1) || (paramString.length() > 1)) {
/* 326 */       boolean bool2 = paramString.contains(".");
/* 327 */       if (bool1) {
/* 328 */         paramString = paramString.substring(1);
/*     */       }
/*     */       
/* 331 */       d += c(paramString);
/*     */       
/* 333 */       if ((!bool2) && (!bool1) && (paramBoolean)) {
/* 334 */         d += 0.5D;
/*     */       }
/*     */     }
/*     */     
/* 338 */     if ((paramInt1 != 0) || (paramInt2 != 0)) {
/* 339 */       if (d < paramInt1)
/* 340 */         throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[] { Double.valueOf(d), Integer.valueOf(paramInt1) });
/* 341 */       if (d > paramInt2) {
/* 342 */         throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[] { Double.valueOf(d), Integer.valueOf(paramInt2) });
/*     */       }
/*     */     }
/*     */     
/* 346 */     return d;
/*     */   }
/*     */   
/*     */   public static class CommandNumber {
/*     */     private final double a;
/*     */     private final double b;
/*     */     private final boolean c;
/*     */     
/*     */     protected CommandNumber(double paramDouble1, double paramDouble2, boolean paramBoolean) {
/* 355 */       this.a = paramDouble1;
/* 356 */       this.b = paramDouble2;
/* 357 */       this.c = paramBoolean;
/*     */     }
/*     */     
/*     */     public double a() {
/* 361 */       return this.a;
/*     */     }
/*     */     
/*     */     public double b() {
/* 365 */       return this.b;
/*     */     }
/*     */     
/*     */     public boolean c() {
/* 369 */       return this.c;
/*     */     }
/*     */   }
/*     */   
/*     */   public static Item f(ICommandListener paramICommandListener, String paramString) throws ExceptionInvalidNumber {
/* 374 */     MinecraftKey localMinecraftKey = new MinecraftKey(paramString);
/* 375 */     Item localItem = (Item)Item.REGISTRY.get(localMinecraftKey);
/*     */     
/* 377 */     if (localItem == null) {
/* 378 */       throw new ExceptionInvalidNumber("commands.give.item.notFound", new Object[] { localMinecraftKey });
/*     */     }
/*     */     
/* 381 */     return localItem;
/*     */   }
/*     */   
/*     */   public static Block g(ICommandListener paramICommandListener, String paramString) throws ExceptionInvalidNumber {
/* 385 */     MinecraftKey localMinecraftKey = new MinecraftKey(paramString);
/* 386 */     if (!Block.REGISTRY.d(localMinecraftKey)) {
/* 387 */       throw new ExceptionInvalidNumber("commands.give.block.notFound", new Object[] { localMinecraftKey });
/*     */     }
/*     */     
/* 390 */     Block localBlock = (Block)Block.REGISTRY.get(localMinecraftKey);
/* 391 */     if (localBlock == null) {
/* 392 */       throw new ExceptionInvalidNumber("commands.give.block.notFound", new Object[] { localMinecraftKey });
/*     */     }
/*     */     
/* 395 */     return localBlock;
/*     */   }
/*     */   
/*     */   public static String a(Object[] paramArrayOfObject) {
/* 399 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/* 401 */     for (int i = 0; i < paramArrayOfObject.length; i++) {
/* 402 */       String str = paramArrayOfObject[i].toString();
/*     */       
/* 404 */       if (i > 0) {
/* 405 */         if (i == paramArrayOfObject.length - 1) {
/* 406 */           localStringBuilder.append(" and ");
/*     */         } else {
/* 408 */           localStringBuilder.append(", ");
/*     */         }
/*     */       }
/*     */       
/* 412 */       localStringBuilder.append(str);
/*     */     }
/*     */     
/* 415 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent a(List<IChatBaseComponent> paramList) {
/* 419 */     ChatComponentText localChatComponentText = new ChatComponentText("");
/*     */     
/* 421 */     for (int i = 0; i < paramList.size(); i++) {
/* 422 */       if (i > 0) {
/* 423 */         if (i == paramList.size() - 1) {
/* 424 */           localChatComponentText.a(" and ");
/* 425 */         } else if (i > 0) {
/* 426 */           localChatComponentText.a(", ");
/*     */         }
/*     */       }
/*     */       
/* 430 */       localChatComponentText.addSibling((IChatBaseComponent)paramList.get(i));
/*     */     }
/*     */     
/* 433 */     return localChatComponentText;
/*     */   }
/*     */   
/*     */   public static String a(Collection<String> paramCollection) {
/* 437 */     return a(paramCollection.toArray(new String[paramCollection.size()]));
/*     */   }
/*     */   
/*     */   public static List<String> a(String[] paramArrayOfString, int paramInt, BlockPosition paramBlockPosition)
/*     */   {
/* 442 */     if (paramBlockPosition == null) {
/* 443 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 447 */     int i = paramArrayOfString.length - 1;
/* 448 */     String str; if (i == paramInt) {
/* 449 */       str = Integer.toString(paramBlockPosition.getX());
/* 450 */     } else if (i == paramInt + 1) {
/* 451 */       str = Integer.toString(paramBlockPosition.getY());
/* 452 */     } else if (i == paramInt + 2) {
/* 453 */       str = Integer.toString(paramBlockPosition.getZ());
/*     */     } else {
/* 455 */       return null;
/*     */     }
/* 457 */     return Lists.newArrayList(new String[] { str });
/*     */   }
/*     */   
/*     */   public static List<String> b(String[] paramArrayOfString, int paramInt, BlockPosition paramBlockPosition)
/*     */   {
/* 462 */     if (paramBlockPosition == null) {
/* 463 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 467 */     int i = paramArrayOfString.length - 1;
/* 468 */     String str; if (i == paramInt) {
/* 469 */       str = Integer.toString(paramBlockPosition.getX());
/* 470 */     } else if (i == paramInt + 1) {
/* 471 */       str = Integer.toString(paramBlockPosition.getZ());
/*     */     } else {
/* 473 */       return null;
/*     */     }
/* 475 */     return Lists.newArrayList(new String[] { str });
/*     */   }
/*     */   
/*     */   public static boolean a(String paramString1, String paramString2) {
/* 479 */     return paramString2.regionMatches(true, 0, paramString1, 0, paramString1.length());
/*     */   }
/*     */   
/*     */   public static List<String> a(String[] paramArrayOfString1, String... paramVarArgs) {
/* 483 */     return a(paramArrayOfString1, Arrays.asList(paramVarArgs));
/*     */   }
/*     */   
/*     */   public static List<String> a(String[] paramArrayOfString, Collection<?> paramCollection) {
/* 487 */     String str = paramArrayOfString[(paramArrayOfString.length - 1)];
/* 488 */     ArrayList localArrayList = Lists.newArrayList();
/*     */     Iterator localIterator;
/* 490 */     Object localObject; if (!paramCollection.isEmpty()) {
/* 491 */       for (localIterator = Iterables.transform(paramCollection, Functions.toStringFunction()).iterator(); localIterator.hasNext();) { localObject = (String)localIterator.next();
/* 492 */         if (a(str, (String)localObject)) {
/* 493 */           localArrayList.add(localObject);
/*     */         }
/*     */       }
/*     */       
/* 497 */       if (localArrayList.isEmpty()) {
/* 498 */         for (localIterator = paramCollection.iterator(); localIterator.hasNext();) { localObject = localIterator.next();
/* 499 */           if (((localObject instanceof MinecraftKey)) && 
/* 500 */             (a(str, ((MinecraftKey)localObject).a()))) {
/* 501 */             localArrayList.add(String.valueOf(localObject));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 508 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 513 */     return false;
/*     */   }
/*     */   
/*     */   public static void a(ICommandListener paramICommandListener, ICommand paramICommand, String paramString, Object... paramVarArgs) {
/* 517 */     a(paramICommandListener, paramICommand, 0, paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   public static void a(ICommandListener paramICommandListener, ICommand paramICommand, int paramInt, String paramString, Object... paramVarArgs) {
/* 521 */     if (a != null) {
/* 522 */       a.a(paramICommandListener, paramICommand, paramInt, paramString, paramVarArgs);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void a(ICommandDispatcher paramICommandDispatcher) {
/* 527 */     a = paramICommandDispatcher;
/*     */   }
/*     */   
/*     */   public int a(ICommand paramICommand)
/*     */   {
/* 532 */     return getCommand().compareTo(paramICommand.getCommand());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandAbstract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */