/*     */ package org.bukkit.craftbukkit.v1_8_R3.command;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.CommandAbstract;
/*     */ import net.minecraft.server.v1_8_R3.EntityMinecartCommandBlock;
/*     */ import net.minecraft.server.v1_8_R3.ICommandListener;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.command.BlockCommandSender;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.command.ProxiedCommandSender;
/*     */ import org.bukkit.command.RemoteConsoleCommandSender;
/*     */ import org.bukkit.command.defaults.VanillaCommand;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMinecartCommand;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.minecart.CommandMinecart;
/*     */ 
/*     */ public final class VanillaCommandWrapper extends VanillaCommand
/*     */ {
/*     */   protected final CommandAbstract vanillaCommand;
/*     */   
/*     */   public VanillaCommandWrapper(CommandAbstract vanillaCommand)
/*     */   {
/*  26 */     super(vanillaCommand.getCommand());
/*  27 */     this.vanillaCommand = vanillaCommand;
/*     */   }
/*     */   
/*     */   public VanillaCommandWrapper(CommandAbstract vanillaCommand, String usage) {
/*  31 */     super(vanillaCommand.getCommand());
/*  32 */     this.vanillaCommand = vanillaCommand;
/*  33 */     this.description = "A Mojang provided command.";
/*  34 */     this.usageMessage = usage;
/*  35 */     setPermission("minecraft.command." + vanillaCommand.getCommand());
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args)
/*     */   {
/*  40 */     if (!testPermission(sender)) { return true;
/*     */     }
/*  42 */     ICommandListener icommandlistener = getListener(sender);
/*  43 */     dispatchVanillaCommand(sender, icommandlistener, args);
/*  44 */     return true;
/*     */   }
/*     */   
/*     */   public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*     */   {
/*  49 */     Validate.notNull(sender, "Sender cannot be null");
/*  50 */     Validate.notNull(args, "Arguments cannot be null");
/*  51 */     Validate.notNull(alias, "Alias cannot be null");
/*  52 */     return this.vanillaCommand.tabComplete(getListener(sender), args, new BlockPosition(0, 0, 0));
/*     */   }
/*     */   
/*  55 */   public static CommandSender lastSender = null;
/*     */   
/*     */   /* Error */
/*     */   public final int dispatchVanillaCommand(CommandSender bSender, ICommandListener icommandlistener, String[] as)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_3
/*     */     //   2: invokespecial 114	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:getPlayerListSize	([Ljava/lang/String;)I
/*     */     //   5: istore 4
/*     */     //   7: iconst_0
/*     */     //   8: istore 5
/*     */     //   10: invokestatic 120	net/minecraft/server/v1_8_R3/MinecraftServer:getServer	()Lnet/minecraft/server/v1_8_R3/MinecraftServer;
/*     */     //   13: getfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   16: astore 6
/*     */     //   18: invokestatic 120	net/minecraft/server/v1_8_R3/MinecraftServer:getServer	()Lnet/minecraft/server/v1_8_R3/MinecraftServer;
/*     */     //   21: astore 7
/*     */     //   23: aload 7
/*     */     //   25: aload 7
/*     */     //   27: getfield 128	net/minecraft/server/v1_8_R3/MinecraftServer:worlds	Ljava/util/List;
/*     */     //   30: invokeinterface 134 1 0
/*     */     //   35: anewarray 136	net/minecraft/server/v1_8_R3/WorldServer
/*     */     //   38: putfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   41: aload 7
/*     */     //   43: getfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   46: iconst_0
/*     */     //   47: aload_2
/*     */     //   48: invokeinterface 142 1 0
/*     */     //   53: checkcast 136	net/minecraft/server/v1_8_R3/WorldServer
/*     */     //   56: aastore
/*     */     //   57: iconst_0
/*     */     //   58: istore 8
/*     */     //   60: iconst_1
/*     */     //   61: istore 9
/*     */     //   63: goto +54 -> 117
/*     */     //   66: aload 7
/*     */     //   68: getfield 128	net/minecraft/server/v1_8_R3/MinecraftServer:worlds	Ljava/util/List;
/*     */     //   71: iload 8
/*     */     //   73: iinc 8 1
/*     */     //   76: invokeinterface 150 2 0
/*     */     //   81: checkcast 136	net/minecraft/server/v1_8_R3/WorldServer
/*     */     //   84: astore 10
/*     */     //   86: aload 7
/*     */     //   88: getfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   91: iconst_0
/*     */     //   92: aaload
/*     */     //   93: aload 10
/*     */     //   95: if_acmpne +9 -> 104
/*     */     //   98: iinc 9 -1
/*     */     //   101: goto +13 -> 114
/*     */     //   104: aload 7
/*     */     //   106: getfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   109: iload 9
/*     */     //   111: aload 10
/*     */     //   113: aastore
/*     */     //   114: iinc 9 1
/*     */     //   117: iload 9
/*     */     //   119: aload 7
/*     */     //   121: getfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   124: arraylength
/*     */     //   125: if_icmplt -59 -> 66
/*     */     //   128: aload_0
/*     */     //   129: getfield 31	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:vanillaCommand	Lnet/minecraft/server/v1_8_R3/CommandAbstract;
/*     */     //   132: aload_2
/*     */     //   133: invokevirtual 154	net/minecraft/server/v1_8_R3/CommandAbstract:canUse	(Lnet/minecraft/server/v1_8_R3/ICommandListener;)Z
/*     */     //   136: ifeq +256 -> 392
/*     */     //   139: iload 4
/*     */     //   141: iconst_m1
/*     */     //   142: if_icmple +225 -> 367
/*     */     //   145: aload_2
/*     */     //   146: aload_3
/*     */     //   147: iload 4
/*     */     //   149: aaload
/*     */     //   150: ldc -100
/*     */     //   152: invokestatic 162	net/minecraft/server/v1_8_R3/PlayerSelector:getPlayers	(Lnet/minecraft/server/v1_8_R3/ICommandListener;Ljava/lang/String;Ljava/lang/Class;)Ljava/util/List;
/*     */     //   155: astore 9
/*     */     //   157: aload_3
/*     */     //   158: iload 4
/*     */     //   160: aaload
/*     */     //   161: astore 10
/*     */     //   163: aload_2
/*     */     //   164: getstatic 166	net/minecraft/server/v1_8_R3/CommandObjectiveExecutor$EnumCommandResult:AFFECTED_ENTITIES	Lnet/minecraft/server/v1_8_R3/CommandObjectiveExecutor$EnumCommandResult;
/*     */     //   167: aload 9
/*     */     //   169: invokeinterface 134 1 0
/*     */     //   174: invokeinterface 170 3 0
/*     */     //   179: aload 9
/*     */     //   181: invokeinterface 174 1 0
/*     */     //   186: astore 11
/*     */     //   188: goto +160 -> 348
/*     */     //   191: aload 11
/*     */     //   193: invokeinterface 182 1 0
/*     */     //   198: checkcast 156	net/minecraft/server/v1_8_R3/Entity
/*     */     //   201: astore 12
/*     */     //   203: getstatic 18	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:lastSender	Lorg/bukkit/command/CommandSender;
/*     */     //   206: astore 13
/*     */     //   208: aload_1
/*     */     //   209: putstatic 18	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:lastSender	Lorg/bukkit/command/CommandSender;
/*     */     //   212: aload_3
/*     */     //   213: iload 4
/*     */     //   215: aload 12
/*     */     //   217: invokevirtual 186	net/minecraft/server/v1_8_R3/Entity:getUniqueID	()Ljava/util/UUID;
/*     */     //   220: invokevirtual 189	java/util/UUID:toString	()Ljava/lang/String;
/*     */     //   223: aastore
/*     */     //   224: aload_0
/*     */     //   225: getfield 31	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:vanillaCommand	Lnet/minecraft/server/v1_8_R3/CommandAbstract;
/*     */     //   228: aload_2
/*     */     //   229: aload_3
/*     */     //   230: invokevirtual 192	net/minecraft/server/v1_8_R3/CommandAbstract:execute	(Lnet/minecraft/server/v1_8_R3/ICommandListener;[Ljava/lang/String;)V
/*     */     //   233: iinc 5 1
/*     */     //   236: goto +107 -> 343
/*     */     //   239: astore 14
/*     */     //   241: new 194	net/minecraft/server/v1_8_R3/ChatMessage
/*     */     //   244: dup
/*     */     //   245: ldc -60
/*     */     //   247: iconst_1
/*     */     //   248: anewarray 198	java/lang/Object
/*     */     //   251: dup
/*     */     //   252: iconst_0
/*     */     //   253: new 194	net/minecraft/server/v1_8_R3/ChatMessage
/*     */     //   256: dup
/*     */     //   257: aload 14
/*     */     //   259: invokevirtual 201	net/minecraft/server/v1_8_R3/ExceptionUsage:getMessage	()Ljava/lang/String;
/*     */     //   262: aload 14
/*     */     //   264: invokevirtual 205	net/minecraft/server/v1_8_R3/ExceptionUsage:getArgs	()[Ljava/lang/Object;
/*     */     //   267: invokespecial 208	net/minecraft/server/v1_8_R3/ChatMessage:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   270: aastore
/*     */     //   271: invokespecial 208	net/minecraft/server/v1_8_R3/ChatMessage:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   274: astore 15
/*     */     //   276: aload 15
/*     */     //   278: invokevirtual 212	net/minecraft/server/v1_8_R3/ChatMessage:getChatModifier	()Lnet/minecraft/server/v1_8_R3/ChatModifier;
/*     */     //   281: getstatic 218	net/minecraft/server/v1_8_R3/EnumChatFormat:RED	Lnet/minecraft/server/v1_8_R3/EnumChatFormat;
/*     */     //   284: invokevirtual 224	net/minecraft/server/v1_8_R3/ChatModifier:setColor	(Lnet/minecraft/server/v1_8_R3/EnumChatFormat;)Lnet/minecraft/server/v1_8_R3/ChatModifier;
/*     */     //   287: pop
/*     */     //   288: aload_2
/*     */     //   289: aload 15
/*     */     //   291: invokeinterface 228 2 0
/*     */     //   296: aload 13
/*     */     //   298: putstatic 18	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:lastSender	Lorg/bukkit/command/CommandSender;
/*     */     //   301: goto +47 -> 348
/*     */     //   304: astore 14
/*     */     //   306: aload_2
/*     */     //   307: aload_0
/*     */     //   308: getfield 31	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:vanillaCommand	Lnet/minecraft/server/v1_8_R3/CommandAbstract;
/*     */     //   311: iconst_1
/*     */     //   312: aload 14
/*     */     //   314: invokevirtual 229	net/minecraft/server/v1_8_R3/CommandException:getMessage	()Ljava/lang/String;
/*     */     //   317: aload 14
/*     */     //   319: invokevirtual 230	net/minecraft/server/v1_8_R3/CommandException:getArgs	()[Ljava/lang/Object;
/*     */     //   322: invokestatic 233	net/minecraft/server/v1_8_R3/CommandAbstract:a	(Lnet/minecraft/server/v1_8_R3/ICommandListener;Lnet/minecraft/server/v1_8_R3/ICommand;ILjava/lang/String;[Ljava/lang/Object;)V
/*     */     //   325: aload 13
/*     */     //   327: putstatic 18	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:lastSender	Lorg/bukkit/command/CommandSender;
/*     */     //   330: goto +18 -> 348
/*     */     //   333: astore 16
/*     */     //   335: aload 13
/*     */     //   337: putstatic 18	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:lastSender	Lorg/bukkit/command/CommandSender;
/*     */     //   340: aload 16
/*     */     //   342: athrow
/*     */     //   343: aload 13
/*     */     //   345: putstatic 18	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:lastSender	Lorg/bukkit/command/CommandSender;
/*     */     //   348: aload 11
/*     */     //   350: invokeinterface 237 1 0
/*     */     //   355: ifne -164 -> 191
/*     */     //   358: aload_3
/*     */     //   359: iload 4
/*     */     //   361: aload 10
/*     */     //   363: aastore
/*     */     //   364: goto +414 -> 778
/*     */     //   367: aload_2
/*     */     //   368: getstatic 166	net/minecraft/server/v1_8_R3/CommandObjectiveExecutor$EnumCommandResult:AFFECTED_ENTITIES	Lnet/minecraft/server/v1_8_R3/CommandObjectiveExecutor$EnumCommandResult;
/*     */     //   371: iconst_1
/*     */     //   372: invokeinterface 170 3 0
/*     */     //   377: aload_0
/*     */     //   378: getfield 31	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:vanillaCommand	Lnet/minecraft/server/v1_8_R3/CommandAbstract;
/*     */     //   381: aload_2
/*     */     //   382: aload_3
/*     */     //   383: invokevirtual 192	net/minecraft/server/v1_8_R3/CommandAbstract:execute	(Lnet/minecraft/server/v1_8_R3/ICommandListener;[Ljava/lang/String;)V
/*     */     //   386: iinc 5 1
/*     */     //   389: goto +389 -> 778
/*     */     //   392: new 194	net/minecraft/server/v1_8_R3/ChatMessage
/*     */     //   395: dup
/*     */     //   396: ldc -17
/*     */     //   398: iconst_0
/*     */     //   399: anewarray 198	java/lang/Object
/*     */     //   402: invokespecial 208	net/minecraft/server/v1_8_R3/ChatMessage:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   405: astore 9
/*     */     //   407: aload 9
/*     */     //   409: invokevirtual 212	net/minecraft/server/v1_8_R3/ChatMessage:getChatModifier	()Lnet/minecraft/server/v1_8_R3/ChatModifier;
/*     */     //   412: getstatic 218	net/minecraft/server/v1_8_R3/EnumChatFormat:RED	Lnet/minecraft/server/v1_8_R3/EnumChatFormat;
/*     */     //   415: invokevirtual 224	net/minecraft/server/v1_8_R3/ChatModifier:setColor	(Lnet/minecraft/server/v1_8_R3/EnumChatFormat;)Lnet/minecraft/server/v1_8_R3/ChatModifier;
/*     */     //   418: pop
/*     */     //   419: aload_2
/*     */     //   420: aload 9
/*     */     //   422: invokeinterface 228 2 0
/*     */     //   427: goto +351 -> 778
/*     */     //   430: astore 9
/*     */     //   432: new 194	net/minecraft/server/v1_8_R3/ChatMessage
/*     */     //   435: dup
/*     */     //   436: ldc -60
/*     */     //   438: iconst_1
/*     */     //   439: anewarray 198	java/lang/Object
/*     */     //   442: dup
/*     */     //   443: iconst_0
/*     */     //   444: new 194	net/minecraft/server/v1_8_R3/ChatMessage
/*     */     //   447: dup
/*     */     //   448: aload 9
/*     */     //   450: invokevirtual 201	net/minecraft/server/v1_8_R3/ExceptionUsage:getMessage	()Ljava/lang/String;
/*     */     //   453: aload 9
/*     */     //   455: invokevirtual 205	net/minecraft/server/v1_8_R3/ExceptionUsage:getArgs	()[Ljava/lang/Object;
/*     */     //   458: invokespecial 208	net/minecraft/server/v1_8_R3/ChatMessage:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   461: aastore
/*     */     //   462: invokespecial 208	net/minecraft/server/v1_8_R3/ChatMessage:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   465: astore 10
/*     */     //   467: aload 10
/*     */     //   469: invokevirtual 212	net/minecraft/server/v1_8_R3/ChatMessage:getChatModifier	()Lnet/minecraft/server/v1_8_R3/ChatModifier;
/*     */     //   472: getstatic 218	net/minecraft/server/v1_8_R3/EnumChatFormat:RED	Lnet/minecraft/server/v1_8_R3/EnumChatFormat;
/*     */     //   475: invokevirtual 224	net/minecraft/server/v1_8_R3/ChatModifier:setColor	(Lnet/minecraft/server/v1_8_R3/EnumChatFormat;)Lnet/minecraft/server/v1_8_R3/ChatModifier;
/*     */     //   478: pop
/*     */     //   479: aload_2
/*     */     //   480: aload 10
/*     */     //   482: invokeinterface 228 2 0
/*     */     //   487: invokestatic 120	net/minecraft/server/v1_8_R3/MinecraftServer:getServer	()Lnet/minecraft/server/v1_8_R3/MinecraftServer;
/*     */     //   490: aload 6
/*     */     //   492: putfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   495: goto +291 -> 786
/*     */     //   498: astore 9
/*     */     //   500: aload_2
/*     */     //   501: aload_0
/*     */     //   502: getfield 31	org/bukkit/craftbukkit/v1_8_R3/command/VanillaCommandWrapper:vanillaCommand	Lnet/minecraft/server/v1_8_R3/CommandAbstract;
/*     */     //   505: iconst_1
/*     */     //   506: aload 9
/*     */     //   508: invokevirtual 229	net/minecraft/server/v1_8_R3/CommandException:getMessage	()Ljava/lang/String;
/*     */     //   511: aload 9
/*     */     //   513: invokevirtual 230	net/minecraft/server/v1_8_R3/CommandException:getArgs	()[Ljava/lang/Object;
/*     */     //   516: invokestatic 233	net/minecraft/server/v1_8_R3/CommandAbstract:a	(Lnet/minecraft/server/v1_8_R3/ICommandListener;Lnet/minecraft/server/v1_8_R3/ICommand;ILjava/lang/String;[Ljava/lang/Object;)V
/*     */     //   519: invokestatic 120	net/minecraft/server/v1_8_R3/MinecraftServer:getServer	()Lnet/minecraft/server/v1_8_R3/MinecraftServer;
/*     */     //   522: aload 6
/*     */     //   524: putfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   527: goto +259 -> 786
/*     */     //   530: astore 9
/*     */     //   532: new 194	net/minecraft/server/v1_8_R3/ChatMessage
/*     */     //   535: dup
/*     */     //   536: ldc -15
/*     */     //   538: iconst_0
/*     */     //   539: anewarray 198	java/lang/Object
/*     */     //   542: invokespecial 208	net/minecraft/server/v1_8_R3/ChatMessage:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   545: astore 10
/*     */     //   547: aload 10
/*     */     //   549: invokevirtual 212	net/minecraft/server/v1_8_R3/ChatMessage:getChatModifier	()Lnet/minecraft/server/v1_8_R3/ChatModifier;
/*     */     //   552: getstatic 218	net/minecraft/server/v1_8_R3/EnumChatFormat:RED	Lnet/minecraft/server/v1_8_R3/EnumChatFormat;
/*     */     //   555: invokevirtual 224	net/minecraft/server/v1_8_R3/ChatModifier:setColor	(Lnet/minecraft/server/v1_8_R3/EnumChatFormat;)Lnet/minecraft/server/v1_8_R3/ChatModifier;
/*     */     //   558: pop
/*     */     //   559: aload_2
/*     */     //   560: aload 10
/*     */     //   562: invokeinterface 228 2 0
/*     */     //   567: aload_2
/*     */     //   568: invokeinterface 245 1 0
/*     */     //   573: instanceof 247
/*     */     //   576: ifeq +74 -> 650
/*     */     //   579: getstatic 251	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*     */     //   582: getstatic 257	org/apache/logging/log4j/Level:WARN	Lorg/apache/logging/log4j/Level;
/*     */     //   585: ldc_w 259
/*     */     //   588: iconst_3
/*     */     //   589: anewarray 198	java/lang/Object
/*     */     //   592: dup
/*     */     //   593: iconst_0
/*     */     //   594: aload_2
/*     */     //   595: invokeinterface 263 1 0
/*     */     //   600: invokevirtual 266	net/minecraft/server/v1_8_R3/BlockPosition:getX	()I
/*     */     //   603: invokestatic 272	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   606: aastore
/*     */     //   607: dup
/*     */     //   608: iconst_1
/*     */     //   609: aload_2
/*     */     //   610: invokeinterface 263 1 0
/*     */     //   615: invokevirtual 275	net/minecraft/server/v1_8_R3/BlockPosition:getY	()I
/*     */     //   618: invokestatic 272	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   621: aastore
/*     */     //   622: dup
/*     */     //   623: iconst_2
/*     */     //   624: aload_2
/*     */     //   625: invokeinterface 263 1 0
/*     */     //   630: invokevirtual 278	net/minecraft/server/v1_8_R3/BlockPosition:getZ	()I
/*     */     //   633: invokestatic 272	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   636: aastore
/*     */     //   637: invokestatic 282	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   640: aload 9
/*     */     //   642: invokeinterface 288 4 0
/*     */     //   647: goto +107 -> 754
/*     */     //   650: aload_2
/*     */     //   651: instanceof 290
/*     */     //   654: ifeq +77 -> 731
/*     */     //   657: aload_2
/*     */     //   658: checkcast 290	net/minecraft/server/v1_8_R3/CommandBlockListenerAbstract
/*     */     //   661: astore 11
/*     */     //   663: getstatic 251	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*     */     //   666: getstatic 257	org/apache/logging/log4j/Level:WARN	Lorg/apache/logging/log4j/Level;
/*     */     //   669: ldc_w 292
/*     */     //   672: iconst_3
/*     */     //   673: anewarray 198	java/lang/Object
/*     */     //   676: dup
/*     */     //   677: iconst_0
/*     */     //   678: aload 11
/*     */     //   680: invokevirtual 293	net/minecraft/server/v1_8_R3/CommandBlockListenerAbstract:getChunkCoordinates	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*     */     //   683: invokevirtual 266	net/minecraft/server/v1_8_R3/BlockPosition:getX	()I
/*     */     //   686: invokestatic 272	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   689: aastore
/*     */     //   690: dup
/*     */     //   691: iconst_1
/*     */     //   692: aload 11
/*     */     //   694: invokevirtual 293	net/minecraft/server/v1_8_R3/CommandBlockListenerAbstract:getChunkCoordinates	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*     */     //   697: invokevirtual 275	net/minecraft/server/v1_8_R3/BlockPosition:getY	()I
/*     */     //   700: invokestatic 272	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   703: aastore
/*     */     //   704: dup
/*     */     //   705: iconst_2
/*     */     //   706: aload 11
/*     */     //   708: invokevirtual 293	net/minecraft/server/v1_8_R3/CommandBlockListenerAbstract:getChunkCoordinates	()Lnet/minecraft/server/v1_8_R3/BlockPosition;
/*     */     //   711: invokevirtual 278	net/minecraft/server/v1_8_R3/BlockPosition:getZ	()I
/*     */     //   714: invokestatic 272	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   717: aastore
/*     */     //   718: invokestatic 282	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   721: aload 9
/*     */     //   723: invokeinterface 288 4 0
/*     */     //   728: goto +26 -> 754
/*     */     //   731: getstatic 251	net/minecraft/server/v1_8_R3/MinecraftServer:LOGGER	Lorg/apache/logging/log4j/Logger;
/*     */     //   734: getstatic 257	org/apache/logging/log4j/Level:WARN	Lorg/apache/logging/log4j/Level;
/*     */     //   737: ldc_w 295
/*     */     //   740: iconst_0
/*     */     //   741: anewarray 198	java/lang/Object
/*     */     //   744: invokestatic 282	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   747: aload 9
/*     */     //   749: invokeinterface 288 4 0
/*     */     //   754: invokestatic 120	net/minecraft/server/v1_8_R3/MinecraftServer:getServer	()Lnet/minecraft/server/v1_8_R3/MinecraftServer;
/*     */     //   757: aload 6
/*     */     //   759: putfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   762: goto +24 -> 786
/*     */     //   765: astore 17
/*     */     //   767: invokestatic 120	net/minecraft/server/v1_8_R3/MinecraftServer:getServer	()Lnet/minecraft/server/v1_8_R3/MinecraftServer;
/*     */     //   770: aload 6
/*     */     //   772: putfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   775: aload 17
/*     */     //   777: athrow
/*     */     //   778: invokestatic 120	net/minecraft/server/v1_8_R3/MinecraftServer:getServer	()Lnet/minecraft/server/v1_8_R3/MinecraftServer;
/*     */     //   781: aload 6
/*     */     //   783: putfield 124	net/minecraft/server/v1_8_R3/MinecraftServer:worldServer	[Lnet/minecraft/server/v1_8_R3/WorldServer;
/*     */     //   786: aload_2
/*     */     //   787: getstatic 298	net/minecraft/server/v1_8_R3/CommandObjectiveExecutor$EnumCommandResult:SUCCESS_COUNT	Lnet/minecraft/server/v1_8_R3/CommandObjectiveExecutor$EnumCommandResult;
/*     */     //   790: iload 5
/*     */     //   792: invokeinterface 170 3 0
/*     */     //   797: iload 5
/*     */     //   799: ireturn
/*     */     // Line number table:
/*     */     //   Java source line #59	-> byte code offset #0
/*     */     //   Java source line #60	-> byte code offset #7
/*     */     //   Java source line #63	-> byte code offset #10
/*     */     //   Java source line #64	-> byte code offset #18
/*     */     //   Java source line #65	-> byte code offset #23
/*     */     //   Java source line #66	-> byte code offset #41
/*     */     //   Java source line #67	-> byte code offset #57
/*     */     //   Java source line #68	-> byte code offset #60
/*     */     //   Java source line #69	-> byte code offset #66
/*     */     //   Java source line #70	-> byte code offset #86
/*     */     //   Java source line #71	-> byte code offset #98
/*     */     //   Java source line #72	-> byte code offset #101
/*     */     //   Java source line #74	-> byte code offset #104
/*     */     //   Java source line #68	-> byte code offset #114
/*     */     //   Java source line #78	-> byte code offset #128
/*     */     //   Java source line #79	-> byte code offset #139
/*     */     //   Java source line #80	-> byte code offset #145
/*     */     //   Java source line #81	-> byte code offset #157
/*     */     //   Java source line #83	-> byte code offset #163
/*     */     //   Java source line #84	-> byte code offset #179
/*     */     //   Java source line #86	-> byte code offset #188
/*     */     //   Java source line #87	-> byte code offset #191
/*     */     //   Java source line #89	-> byte code offset #203
/*     */     //   Java source line #90	-> byte code offset #208
/*     */     //   Java source line #92	-> byte code offset #212
/*     */     //   Java source line #93	-> byte code offset #224
/*     */     //   Java source line #94	-> byte code offset #233
/*     */     //   Java source line #95	-> byte code offset #236
/*     */     //   Java source line #96	-> byte code offset #241
/*     */     //   Java source line #97	-> byte code offset #276
/*     */     //   Java source line #98	-> byte code offset #288
/*     */     //   Java source line #102	-> byte code offset #296
/*     */     //   Java source line #99	-> byte code offset #304
/*     */     //   Java source line #100	-> byte code offset #306
/*     */     //   Java source line #102	-> byte code offset #325
/*     */     //   Java source line #101	-> byte code offset #333
/*     */     //   Java source line #102	-> byte code offset #335
/*     */     //   Java source line #103	-> byte code offset #340
/*     */     //   Java source line #102	-> byte code offset #343
/*     */     //   Java source line #86	-> byte code offset #348
/*     */     //   Java source line #105	-> byte code offset #358
/*     */     //   Java source line #106	-> byte code offset #364
/*     */     //   Java source line #107	-> byte code offset #367
/*     */     //   Java source line #108	-> byte code offset #377
/*     */     //   Java source line #109	-> byte code offset #386
/*     */     //   Java source line #111	-> byte code offset #389
/*     */     //   Java source line #112	-> byte code offset #392
/*     */     //   Java source line #113	-> byte code offset #407
/*     */     //   Java source line #114	-> byte code offset #419
/*     */     //   Java source line #116	-> byte code offset #427
/*     */     //   Java source line #117	-> byte code offset #432
/*     */     //   Java source line #118	-> byte code offset #467
/*     */     //   Java source line #119	-> byte code offset #479
/*     */     //   Java source line #135	-> byte code offset #487
/*     */     //   Java source line #120	-> byte code offset #498
/*     */     //   Java source line #121	-> byte code offset #500
/*     */     //   Java source line #135	-> byte code offset #519
/*     */     //   Java source line #122	-> byte code offset #530
/*     */     //   Java source line #123	-> byte code offset #532
/*     */     //   Java source line #124	-> byte code offset #547
/*     */     //   Java source line #125	-> byte code offset #559
/*     */     //   Java source line #126	-> byte code offset #567
/*     */     //   Java source line #127	-> byte code offset #579
/*     */     //   Java source line #128	-> byte code offset #647
/*     */     //   Java source line #129	-> byte code offset #657
/*     */     //   Java source line #130	-> byte code offset #663
/*     */     //   Java source line #131	-> byte code offset #728
/*     */     //   Java source line #132	-> byte code offset #731
/*     */     //   Java source line #135	-> byte code offset #754
/*     */     //   Java source line #134	-> byte code offset #765
/*     */     //   Java source line #135	-> byte code offset #767
/*     */     //   Java source line #136	-> byte code offset #775
/*     */     //   Java source line #135	-> byte code offset #778
/*     */     //   Java source line #137	-> byte code offset #786
/*     */     //   Java source line #138	-> byte code offset #797
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	800	0	this	VanillaCommandWrapper
/*     */     //   0	800	1	bSender	CommandSender
/*     */     //   0	800	2	icommandlistener	ICommandListener
/*     */     //   0	800	3	as	String[]
/*     */     //   5	355	4	i	int
/*     */     //   8	790	5	j	int
/*     */     //   16	766	6	prev	net.minecraft.server.v1_8_R3.WorldServer[]
/*     */     //   21	99	7	server	net.minecraft.server.v1_8_R3.MinecraftServer
/*     */     //   58	14	8	bpos	int
/*     */     //   61	57	9	pos	int
/*     */     //   155	25	9	list	java.util.List<net.minecraft.server.v1_8_R3.Entity>
/*     */     //   405	16	9	chatmessage	net.minecraft.server.v1_8_R3.ChatMessage
/*     */     //   430	24	9	exceptionusage	net.minecraft.server.v1_8_R3.ExceptionUsage
/*     */     //   498	14	9	commandexception	net.minecraft.server.v1_8_R3.CommandException
/*     */     //   530	218	9	throwable	Throwable
/*     */     //   84	28	10	world	net.minecraft.server.v1_8_R3.WorldServer
/*     */     //   161	201	10	s2	String
/*     */     //   465	16	10	chatmessage1	net.minecraft.server.v1_8_R3.ChatMessage
/*     */     //   545	16	10	chatmessage3	net.minecraft.server.v1_8_R3.ChatMessage
/*     */     //   186	163	11	iterator	java.util.Iterator<net.minecraft.server.v1_8_R3.Entity>
/*     */     //   661	46	11	listener	net.minecraft.server.v1_8_R3.CommandBlockListenerAbstract
/*     */     //   201	15	12	entity	net.minecraft.server.v1_8_R3.Entity
/*     */     //   206	138	13	oldSender	CommandSender
/*     */     //   239	24	14	exceptionusage	net.minecraft.server.v1_8_R3.ExceptionUsage
/*     */     //   304	14	14	commandexception	net.minecraft.server.v1_8_R3.CommandException
/*     */     //   274	16	15	chatmessage	net.minecraft.server.v1_8_R3.ChatMessage
/*     */     //   333	8	16	localObject1	Object
/*     */     //   765	11	17	localObject2	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   212	236	239	net/minecraft/server/v1_8_R3/ExceptionUsage
/*     */     //   212	236	304	net/minecraft/server/v1_8_R3/CommandException
/*     */     //   212	296	333	finally
/*     */     //   304	325	333	finally
/*     */     //   128	427	430	net/minecraft/server/v1_8_R3/ExceptionUsage
/*     */     //   128	427	498	net/minecraft/server/v1_8_R3/CommandException
/*     */     //   128	427	530	java/lang/Throwable
/*     */     //   128	487	765	finally
/*     */     //   498	519	765	finally
/*     */     //   530	754	765	finally
/*     */   }
/*     */   
/*     */   private ICommandListener getListener(CommandSender sender)
/*     */   {
/* 142 */     if ((sender instanceof Player)) {
/* 143 */       return ((CraftPlayer)sender).getHandle();
/*     */     }
/* 145 */     if ((sender instanceof BlockCommandSender)) {
/* 146 */       return ((CraftBlockCommandSender)sender).getTileEntity();
/*     */     }
/* 148 */     if ((sender instanceof CommandMinecart)) {
/* 149 */       return ((EntityMinecartCommandBlock)((CraftMinecartCommand)sender).getHandle()).getCommandBlock();
/*     */     }
/* 151 */     if ((sender instanceof RemoteConsoleCommandSender)) {
/* 152 */       return net.minecraft.server.v1_8_R3.RemoteControlCommandListener.getInstance();
/*     */     }
/* 154 */     if ((sender instanceof ConsoleCommandSender)) {
/* 155 */       return ((CraftServer)sender.getServer()).getServer();
/*     */     }
/* 157 */     if ((sender instanceof ProxiedCommandSender)) {
/* 158 */       return ((ProxiedNativeCommandSender)sender).getHandle();
/*     */     }
/* 160 */     throw new IllegalArgumentException("Cannot make " + sender + " a vanilla command listener");
/*     */   }
/*     */   
/*     */   private int getPlayerListSize(String[] as) {
/* 164 */     for (int i = 0; i < as.length; i++) {
/* 165 */       if ((this.vanillaCommand.isListStart(as, i)) && (net.minecraft.server.v1_8_R3.PlayerSelector.isList(as[i]))) {
/* 166 */         return i;
/*     */       }
/*     */     }
/* 169 */     return -1;
/*     */   }
/*     */   
/*     */   public static String[] dropFirstArgument(String[] as) {
/* 173 */     String[] as1 = new String[as.length - 1];
/* 174 */     for (int i = 1; i < as.length; i++) {
/* 175 */       as1[(i - 1)] = as[i];
/*     */     }
/*     */     
/* 178 */     return as1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\command\VanillaCommandWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */