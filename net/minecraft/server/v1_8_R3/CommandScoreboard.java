/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandScoreboard
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  34 */     return "scoreboard";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  39 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  44 */     return "commands.scoreboard.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  49 */     if (b(paramICommandListener, paramArrayOfString)) {
/*  50 */       return;
/*     */     }
/*     */     
/*  53 */     if (paramArrayOfString.length < 1) {
/*  54 */       throw new ExceptionUsage("commands.scoreboard.usage", new Object[0]);
/*     */     }
/*     */     
/*  57 */     if (paramArrayOfString[0].equalsIgnoreCase("objectives")) {
/*  58 */       if (paramArrayOfString.length == 1)
/*  59 */         throw new ExceptionUsage("commands.scoreboard.objectives.usage", new Object[0]);
/*  60 */       if (paramArrayOfString[1].equalsIgnoreCase("list")) {
/*  61 */         d(paramICommandListener);
/*  62 */       } else if (paramArrayOfString[1].equalsIgnoreCase("add")) {
/*  63 */         if (paramArrayOfString.length >= 4) {
/*  64 */           b(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/*  66 */           throw new ExceptionUsage("commands.scoreboard.objectives.add.usage", new Object[0]);
/*     */         }
/*  68 */       } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
/*  69 */         if (paramArrayOfString.length == 3) {
/*  70 */           h(paramICommandListener, paramArrayOfString[2]);
/*     */         } else {
/*  72 */           throw new ExceptionUsage("commands.scoreboard.objectives.remove.usage", new Object[0]);
/*     */         }
/*  74 */       } else if (paramArrayOfString[1].equalsIgnoreCase("setdisplay")) {
/*  75 */         if ((paramArrayOfString.length == 3) || (paramArrayOfString.length == 4)) {
/*  76 */           j(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/*  78 */           throw new ExceptionUsage("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
/*     */         }
/*     */       } else {
/*  81 */         throw new ExceptionUsage("commands.scoreboard.objectives.usage", new Object[0]);
/*     */       }
/*  83 */     } else if (paramArrayOfString[0].equalsIgnoreCase("players")) {
/*  84 */       if (paramArrayOfString.length == 1)
/*  85 */         throw new ExceptionUsage("commands.scoreboard.players.usage", new Object[0]);
/*  86 */       if (paramArrayOfString[1].equalsIgnoreCase("list")) {
/*  87 */         if (paramArrayOfString.length <= 3) {
/*  88 */           k(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/*  90 */           throw new ExceptionUsage("commands.scoreboard.players.list.usage", new Object[0]);
/*     */         }
/*  92 */       } else if (paramArrayOfString[1].equalsIgnoreCase("add")) {
/*  93 */         if (paramArrayOfString.length >= 5) {
/*  94 */           l(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/*  96 */           throw new ExceptionUsage("commands.scoreboard.players.add.usage", new Object[0]);
/*     */         }
/*  98 */       } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
/*  99 */         if (paramArrayOfString.length >= 5) {
/* 100 */           l(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 102 */           throw new ExceptionUsage("commands.scoreboard.players.remove.usage", new Object[0]);
/*     */         }
/* 104 */       } else if (paramArrayOfString[1].equalsIgnoreCase("set")) {
/* 105 */         if (paramArrayOfString.length >= 5) {
/* 106 */           l(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 108 */           throw new ExceptionUsage("commands.scoreboard.players.set.usage", new Object[0]);
/*     */         }
/* 110 */       } else if (paramArrayOfString[1].equalsIgnoreCase("reset")) {
/* 111 */         if ((paramArrayOfString.length == 3) || (paramArrayOfString.length == 4)) {
/* 112 */           m(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 114 */           throw new ExceptionUsage("commands.scoreboard.players.reset.usage", new Object[0]);
/*     */         }
/* 116 */       } else if (paramArrayOfString[1].equalsIgnoreCase("enable")) {
/* 117 */         if (paramArrayOfString.length == 4) {
/* 118 */           n(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 120 */           throw new ExceptionUsage("commands.scoreboard.players.enable.usage", new Object[0]);
/*     */         }
/* 122 */       } else if (paramArrayOfString[1].equalsIgnoreCase("test")) {
/* 123 */         if ((paramArrayOfString.length == 5) || (paramArrayOfString.length == 6)) {
/* 124 */           o(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 126 */           throw new ExceptionUsage("commands.scoreboard.players.test.usage", new Object[0]);
/*     */         }
/* 128 */       } else if (paramArrayOfString[1].equalsIgnoreCase("operation")) {
/* 129 */         if (paramArrayOfString.length == 7) {
/* 130 */           p(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 132 */           throw new ExceptionUsage("commands.scoreboard.players.operation.usage", new Object[0]);
/*     */         }
/*     */       } else {
/* 135 */         throw new ExceptionUsage("commands.scoreboard.players.usage", new Object[0]);
/*     */       }
/* 137 */     } else if (paramArrayOfString[0].equalsIgnoreCase("teams")) {
/* 138 */       if (paramArrayOfString.length == 1)
/* 139 */         throw new ExceptionUsage("commands.scoreboard.teams.usage", new Object[0]);
/* 140 */       if (paramArrayOfString[1].equalsIgnoreCase("list")) {
/* 141 */         if (paramArrayOfString.length <= 3) {
/* 142 */           f(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 144 */           throw new ExceptionUsage("commands.scoreboard.teams.list.usage", new Object[0]);
/*     */         }
/* 146 */       } else if (paramArrayOfString[1].equalsIgnoreCase("add")) {
/* 147 */         if (paramArrayOfString.length >= 3) {
/* 148 */           c(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 150 */           throw new ExceptionUsage("commands.scoreboard.teams.add.usage", new Object[0]);
/*     */         }
/* 152 */       } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
/* 153 */         if (paramArrayOfString.length == 3) {
/* 154 */           e(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 156 */           throw new ExceptionUsage("commands.scoreboard.teams.remove.usage", new Object[0]);
/*     */         }
/* 158 */       } else if (paramArrayOfString[1].equalsIgnoreCase("empty")) {
/* 159 */         if (paramArrayOfString.length == 3) {
/* 160 */           i(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 162 */           throw new ExceptionUsage("commands.scoreboard.teams.empty.usage", new Object[0]);
/*     */         }
/* 164 */       } else if (paramArrayOfString[1].equalsIgnoreCase("join")) {
/* 165 */         if ((paramArrayOfString.length >= 4) || ((paramArrayOfString.length == 3) && ((paramICommandListener instanceof EntityHuman)))) {
/* 166 */           g(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 168 */           throw new ExceptionUsage("commands.scoreboard.teams.join.usage", new Object[0]);
/*     */         }
/* 170 */       } else if (paramArrayOfString[1].equalsIgnoreCase("leave")) {
/* 171 */         if ((paramArrayOfString.length >= 3) || ((paramICommandListener instanceof EntityHuman))) {
/* 172 */           h(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 174 */           throw new ExceptionUsage("commands.scoreboard.teams.leave.usage", new Object[0]);
/*     */         }
/* 176 */       } else if (paramArrayOfString[1].equalsIgnoreCase("option")) {
/* 177 */         if ((paramArrayOfString.length == 4) || (paramArrayOfString.length == 5)) {
/* 178 */           d(paramICommandListener, paramArrayOfString, 2);
/*     */         } else {
/* 180 */           throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
/*     */         }
/*     */       } else {
/* 183 */         throw new ExceptionUsage("commands.scoreboard.teams.usage", new Object[0]);
/*     */       }
/*     */     } else {
/* 186 */       throw new ExceptionUsage("commands.scoreboard.usage", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean b(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException {
/* 191 */     int i = -1;
/* 192 */     for (int j = 0; j < paramArrayOfString.length; j++) {
/* 193 */       if (isListStart(paramArrayOfString, j))
/*     */       {
/*     */ 
/* 196 */         if ("*".equals(paramArrayOfString[j])) {
/* 197 */           if (i < 0) {
/* 198 */             i = j;
/*     */           } else
/* 200 */             throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
/*     */         }
/*     */       }
/*     */     }
/* 204 */     if (i < 0) {
/* 205 */       return false;
/*     */     }
/* 207 */     ArrayList localArrayList1 = Lists.newArrayList(d().getPlayers());
/* 208 */     String str1 = paramArrayOfString[i];
/* 209 */     ArrayList localArrayList2 = Lists.newArrayList();
/*     */     
/* 211 */     for (String str2 : localArrayList1) {
/* 212 */       paramArrayOfString[i] = str2;
/*     */       try
/*     */       {
/* 215 */         execute(paramICommandListener, paramArrayOfString);
/* 216 */         localArrayList2.add(str2);
/*     */       } catch (CommandException localCommandException) {
/* 218 */         ChatMessage localChatMessage = new ChatMessage(localCommandException.getMessage(), localCommandException.getArgs());
/* 219 */         localChatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/* 220 */         paramICommandListener.sendMessage(localChatMessage);
/*     */       }
/*     */     }
/* 223 */     paramArrayOfString[i] = str1;
/*     */     
/* 225 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, localArrayList2.size());
/* 226 */     if (localArrayList2.size() == 0) {
/* 227 */       throw new ExceptionUsage("commands.scoreboard.allMatchesFailed", new Object[0]);
/*     */     }
/* 229 */     return true;
/*     */   }
/*     */   
/*     */   protected Scoreboard d() {
/* 233 */     return MinecraftServer.getServer().getWorldServer(0).getScoreboard();
/*     */   }
/*     */   
/*     */   protected ScoreboardObjective a(String paramString, boolean paramBoolean) throws CommandException {
/* 237 */     Scoreboard localScoreboard = d();
/* 238 */     ScoreboardObjective localScoreboardObjective = localScoreboard.getObjective(paramString);
/*     */     
/* 240 */     if (localScoreboardObjective == null)
/* 241 */       throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { paramString });
/* 242 */     if ((paramBoolean) && (localScoreboardObjective.getCriteria().isReadOnly())) {
/* 243 */       throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { paramString });
/*     */     }
/*     */     
/* 246 */     return localScoreboardObjective;
/*     */   }
/*     */   
/*     */   protected ScoreboardTeam e(String paramString) throws CommandException {
/* 250 */     Scoreboard localScoreboard = d();
/* 251 */     ScoreboardTeam localScoreboardTeam = localScoreboard.getTeam(paramString);
/*     */     
/* 253 */     if (localScoreboardTeam == null) {
/* 254 */       throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { paramString });
/*     */     }
/*     */     
/* 257 */     return localScoreboardTeam;
/*     */   }
/*     */   
/*     */   protected void b(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 261 */     String str1 = paramArrayOfString[(paramInt++)];
/* 262 */     String str2 = paramArrayOfString[(paramInt++)];
/* 263 */     Scoreboard localScoreboard = d();
/* 264 */     IScoreboardCriteria localIScoreboardCriteria = (IScoreboardCriteria)IScoreboardCriteria.criteria.get(str2);
/*     */     
/* 266 */     if (localIScoreboardCriteria == null) {
/* 267 */       throw new ExceptionUsage("commands.scoreboard.objectives.add.wrongType", new Object[] { str2 });
/*     */     }
/* 269 */     if (localScoreboard.getObjective(str1) != null) {
/* 270 */       throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { str1 });
/*     */     }
/* 272 */     if (str1.length() > 16) {
/* 273 */       throw new ExceptionInvalidSyntax("commands.scoreboard.objectives.add.tooLong", new Object[] { str1, Integer.valueOf(16) });
/*     */     }
/* 275 */     if (str1.length() == 0) {
/* 276 */       throw new ExceptionUsage("commands.scoreboard.objectives.add.usage", new Object[0]);
/*     */     }
/*     */     
/* 279 */     if (paramArrayOfString.length > paramInt) {
/* 280 */       String str3 = a(paramICommandListener, paramArrayOfString, paramInt).c();
/*     */       
/* 282 */       if (str3.length() > 32) {
/* 283 */         throw new ExceptionInvalidSyntax("commands.scoreboard.objectives.add.displayTooLong", new Object[] { str3, Integer.valueOf(32) });
/*     */       }
/* 285 */       if (str3.length() > 0) {
/* 286 */         localScoreboard.registerObjective(str1, localIScoreboardCriteria).setDisplayName(str3);
/*     */       } else {
/* 288 */         localScoreboard.registerObjective(str1, localIScoreboardCriteria);
/*     */       }
/*     */     } else {
/* 291 */       localScoreboard.registerObjective(str1, localIScoreboardCriteria);
/*     */     }
/*     */     
/* 294 */     a(paramICommandListener, this, "commands.scoreboard.objectives.add.success", new Object[] { str1 });
/*     */   }
/*     */   
/*     */   protected void c(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 298 */     String str1 = paramArrayOfString[(paramInt++)];
/* 299 */     Scoreboard localScoreboard = d();
/*     */     
/* 301 */     if (localScoreboard.getTeam(str1) != null) {
/* 302 */       throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { str1 });
/*     */     }
/* 304 */     if (str1.length() > 16) {
/* 305 */       throw new ExceptionInvalidSyntax("commands.scoreboard.teams.add.tooLong", new Object[] { str1, Integer.valueOf(16) });
/*     */     }
/* 307 */     if (str1.length() == 0) {
/* 308 */       throw new ExceptionUsage("commands.scoreboard.teams.add.usage", new Object[0]);
/*     */     }
/*     */     
/* 311 */     if (paramArrayOfString.length > paramInt) {
/* 312 */       String str2 = a(paramICommandListener, paramArrayOfString, paramInt).c();
/* 313 */       if (str2.length() > 32) {
/* 314 */         throw new ExceptionInvalidSyntax("commands.scoreboard.teams.add.displayTooLong", new Object[] { str2, Integer.valueOf(32) });
/*     */       }
/* 316 */       if (str2.length() > 0) {
/* 317 */         localScoreboard.createTeam(str1).setDisplayName(str2);
/*     */       } else {
/* 319 */         localScoreboard.createTeam(str1);
/*     */       }
/*     */     } else {
/* 322 */       localScoreboard.createTeam(str1);
/*     */     }
/*     */     
/* 325 */     a(paramICommandListener, this, "commands.scoreboard.teams.add.success", new Object[] { str1 });
/*     */   }
/*     */   
/*     */   protected void d(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 329 */     ScoreboardTeam localScoreboardTeam = e(paramArrayOfString[(paramInt++)]);
/* 330 */     if (localScoreboardTeam == null) {
/* 331 */       return;
/*     */     }
/* 333 */     String str1 = paramArrayOfString[(paramInt++)].toLowerCase();
/*     */     
/* 335 */     if ((!str1.equalsIgnoreCase("color")) && (!str1.equalsIgnoreCase("friendlyfire")) && (!str1.equalsIgnoreCase("seeFriendlyInvisibles")) && (!str1.equalsIgnoreCase("nametagVisibility")) && (!str1.equalsIgnoreCase("deathMessageVisibility"))) {
/* 336 */       throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
/*     */     }
/*     */     
/* 339 */     if (paramArrayOfString.length == 4) {
/* 340 */       if (str1.equalsIgnoreCase("color"))
/* 341 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(EnumChatFormat.a(true, false)) });
/* 342 */       if ((str1.equalsIgnoreCase("friendlyfire")) || (str1.equalsIgnoreCase("seeFriendlyInvisibles")))
/* 343 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(Arrays.asList(new String[] { "true", "false" })) });
/* 344 */       if ((str1.equalsIgnoreCase("nametagVisibility")) || (str1.equalsIgnoreCase("deathMessageVisibility"))) {
/* 345 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(ScoreboardTeamBase.EnumNameTagVisibility.a()) });
/*     */       }
/*     */       
/* 348 */       throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
/*     */     }
/*     */     
/* 351 */     String str2 = paramArrayOfString[paramInt];
/*     */     Object localObject;
/* 353 */     if (str1.equalsIgnoreCase("color")) {
/* 354 */       localObject = EnumChatFormat.b(str2);
/* 355 */       if ((localObject == null) || (((EnumChatFormat)localObject).isFormat())) {
/* 356 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(EnumChatFormat.a(true, false)) });
/*     */       }
/* 358 */       localScoreboardTeam.a((EnumChatFormat)localObject);
/* 359 */       localScoreboardTeam.setPrefix(((EnumChatFormat)localObject).toString());
/* 360 */       localScoreboardTeam.setSuffix(EnumChatFormat.RESET.toString());
/* 361 */     } else if (str1.equalsIgnoreCase("friendlyfire")) {
/* 362 */       if ((!str2.equalsIgnoreCase("true")) && (!str2.equalsIgnoreCase("false"))) {
/* 363 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(Arrays.asList(new String[] { "true", "false" })) });
/*     */       }
/* 365 */       localScoreboardTeam.setAllowFriendlyFire(str2.equalsIgnoreCase("true"));
/* 366 */     } else if (str1.equalsIgnoreCase("seeFriendlyInvisibles")) {
/* 367 */       if ((!str2.equalsIgnoreCase("true")) && (!str2.equalsIgnoreCase("false"))) {
/* 368 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(Arrays.asList(new String[] { "true", "false" })) });
/*     */       }
/* 370 */       localScoreboardTeam.setCanSeeFriendlyInvisibles(str2.equalsIgnoreCase("true"));
/* 371 */     } else if (str1.equalsIgnoreCase("nametagVisibility")) {
/* 372 */       localObject = ScoreboardTeamBase.EnumNameTagVisibility.a(str2);
/* 373 */       if (localObject == null) {
/* 374 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(ScoreboardTeamBase.EnumNameTagVisibility.a()) });
/*     */       }
/* 376 */       localScoreboardTeam.setNameTagVisibility((ScoreboardTeamBase.EnumNameTagVisibility)localObject);
/* 377 */     } else if (str1.equalsIgnoreCase("deathMessageVisibility")) {
/* 378 */       localObject = ScoreboardTeamBase.EnumNameTagVisibility.a(str2);
/* 379 */       if (localObject == null) {
/* 380 */         throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] { str1, a(ScoreboardTeamBase.EnumNameTagVisibility.a()) });
/*     */       }
/* 382 */       localScoreboardTeam.b((ScoreboardTeamBase.EnumNameTagVisibility)localObject);
/*     */     }
/*     */     
/* 385 */     a(paramICommandListener, this, "commands.scoreboard.teams.option.success", new Object[] { str1, localScoreboardTeam.getName(), str2 });
/*     */   }
/*     */   
/*     */   protected void e(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 389 */     Scoreboard localScoreboard = d();
/* 390 */     ScoreboardTeam localScoreboardTeam = e(paramArrayOfString[paramInt]);
/* 391 */     if (localScoreboardTeam == null) {
/* 392 */       return;
/*     */     }
/*     */     
/* 395 */     localScoreboard.removeTeam(localScoreboardTeam);
/* 396 */     a(paramICommandListener, this, "commands.scoreboard.teams.remove.success", new Object[] { localScoreboardTeam.getName() });
/*     */   }
/*     */   
/*     */   protected void f(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 400 */     Scoreboard localScoreboard = d();
/*     */     Object localObject1;
/* 402 */     Object localObject2; Object localObject3; if (paramArrayOfString.length > paramInt) {
/* 403 */       localObject1 = e(paramArrayOfString[paramInt]);
/* 404 */       if (localObject1 == null) {
/* 405 */         return;
/*     */       }
/*     */       
/* 408 */       localObject2 = ((ScoreboardTeam)localObject1).getPlayerNameSet();
/* 409 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, ((Collection)localObject2).size());
/* 410 */       if (((Collection)localObject2).size() > 0) {
/* 411 */         localObject3 = new ChatMessage("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(((Collection)localObject2).size()), ((ScoreboardTeam)localObject1).getName() });
/* 412 */         ((ChatMessage)localObject3).getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 413 */         paramICommandListener.sendMessage((IChatBaseComponent)localObject3);
/* 414 */         paramICommandListener.sendMessage(new ChatComponentText(a(((Collection)localObject2).toArray())));
/*     */       } else {
/* 416 */         throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { ((ScoreboardTeam)localObject1).getName() });
/*     */       }
/*     */     } else {
/* 419 */       localObject1 = localScoreboard.getTeams();
/*     */       
/* 421 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, ((Collection)localObject1).size());
/* 422 */       if (((Collection)localObject1).size() > 0) {
/* 423 */         localObject2 = new ChatMessage("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(((Collection)localObject1).size()) });
/* 424 */         ((ChatMessage)localObject2).getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 425 */         paramICommandListener.sendMessage((IChatBaseComponent)localObject2);
/*     */         
/* 427 */         for (localObject3 = ((Collection)localObject1).iterator(); ((Iterator)localObject3).hasNext();) { ScoreboardTeam localScoreboardTeam = (ScoreboardTeam)((Iterator)localObject3).next();
/* 428 */           paramICommandListener.sendMessage(new ChatMessage("commands.scoreboard.teams.list.entry", new Object[] { localScoreboardTeam.getName(), localScoreboardTeam.getDisplayName(), Integer.valueOf(localScoreboardTeam.getPlayerNameSet().size()) }));
/*     */         }
/*     */       } else {
/* 431 */         throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void g(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 437 */     Scoreboard localScoreboard = d();
/* 438 */     String str1 = paramArrayOfString[(paramInt++)];
/* 439 */     HashSet localHashSet1 = Sets.newHashSet();
/* 440 */     HashSet localHashSet2 = Sets.newHashSet();
/*     */     String str2;
/* 442 */     if (((paramICommandListener instanceof EntityHuman)) && (paramInt == paramArrayOfString.length)) {
/* 443 */       str2 = b(paramICommandListener).getName();
/*     */       
/* 445 */       if (localScoreboard.addPlayerToTeam(str2, str1)) {
/* 446 */         localHashSet1.add(str2);
/*     */       } else {
/* 448 */         localHashSet2.add(str2);
/*     */       }
/*     */     } else {
/* 451 */       while (paramInt < paramArrayOfString.length) {
/* 452 */         str2 = paramArrayOfString[(paramInt++)];
/* 453 */         Object localObject; if (str2.startsWith("@")) {
/* 454 */           localObject = c(paramICommandListener, str2);
/* 455 */           for (Entity localEntity : (List)localObject) {
/* 456 */             String str3 = e(paramICommandListener, localEntity.getUniqueID().toString());
/* 457 */             if (localScoreboard.addPlayerToTeam(str3, str1)) {
/* 458 */               localHashSet1.add(str3);
/*     */             } else {
/* 460 */               localHashSet2.add(str3);
/*     */             }
/*     */           }
/*     */         } else {
/* 464 */           localObject = e(paramICommandListener, str2);
/* 465 */           if (localScoreboard.addPlayerToTeam((String)localObject, str1)) {
/* 466 */             localHashSet1.add(localObject);
/*     */           } else {
/* 468 */             localHashSet2.add(localObject);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 474 */     if (!localHashSet1.isEmpty()) {
/* 475 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, localHashSet1.size());
/* 476 */       a(paramICommandListener, this, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(localHashSet1.size()), str1, a(localHashSet1.toArray(new String[localHashSet1.size()])) });
/*     */     }
/* 478 */     if (!localHashSet2.isEmpty()) {
/* 479 */       throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] { Integer.valueOf(localHashSet2.size()), str1, a(localHashSet2.toArray(new String[localHashSet2.size()])) });
/*     */     }
/*     */   }
/*     */   
/*     */   protected void h(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 484 */     Scoreboard localScoreboard = d();
/* 485 */     HashSet localHashSet1 = Sets.newHashSet();
/* 486 */     HashSet localHashSet2 = Sets.newHashSet();
/*     */     String str1;
/* 488 */     if (((paramICommandListener instanceof EntityHuman)) && (paramInt == paramArrayOfString.length)) {
/* 489 */       str1 = b(paramICommandListener).getName();
/*     */       
/* 491 */       if (localScoreboard.removePlayerFromTeam(str1)) {
/* 492 */         localHashSet1.add(str1);
/*     */       } else {
/* 494 */         localHashSet2.add(str1);
/*     */       }
/*     */     } else {
/* 497 */       while (paramInt < paramArrayOfString.length) {
/* 498 */         str1 = paramArrayOfString[(paramInt++)];
/* 499 */         Object localObject; if (str1.startsWith("@")) {
/* 500 */           localObject = c(paramICommandListener, str1);
/* 501 */           for (Entity localEntity : (List)localObject) {
/* 502 */             String str2 = e(paramICommandListener, localEntity.getUniqueID().toString());
/* 503 */             if (localScoreboard.removePlayerFromTeam(str2)) {
/* 504 */               localHashSet1.add(str2);
/*     */             } else {
/* 506 */               localHashSet2.add(str2);
/*     */             }
/*     */           }
/*     */         } else {
/* 510 */           localObject = e(paramICommandListener, str1);
/* 511 */           if (localScoreboard.removePlayerFromTeam((String)localObject)) {
/* 512 */             localHashSet1.add(localObject);
/*     */           } else {
/* 514 */             localHashSet2.add(localObject);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 520 */     if (!localHashSet1.isEmpty()) {
/* 521 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, localHashSet1.size());
/* 522 */       a(paramICommandListener, this, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(localHashSet1.size()), a(localHashSet1.toArray(new String[localHashSet1.size()])) });
/*     */     }
/* 524 */     if (!localHashSet2.isEmpty()) {
/* 525 */       throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(localHashSet2.size()), a(localHashSet2.toArray(new String[localHashSet2.size()])) });
/*     */     }
/*     */   }
/*     */   
/*     */   protected void i(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 530 */     Scoreboard localScoreboard = d();
/* 531 */     ScoreboardTeam localScoreboardTeam = e(paramArrayOfString[paramInt]);
/* 532 */     if (localScoreboardTeam == null) {
/* 533 */       return;
/*     */     }
/*     */     
/* 536 */     ArrayList localArrayList = Lists.newArrayList(localScoreboardTeam.getPlayerNameSet());
/* 537 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, localArrayList.size());
/* 538 */     if (localArrayList.isEmpty()) {
/* 539 */       throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { localScoreboardTeam.getName() });
/*     */     }
/*     */     
/* 542 */     for (String str : localArrayList) {
/* 543 */       localScoreboard.removePlayerFromTeam(str, localScoreboardTeam);
/*     */     }
/*     */     
/* 546 */     a(paramICommandListener, this, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(localArrayList.size()), localScoreboardTeam.getName() });
/*     */   }
/*     */   
/*     */   protected void h(ICommandListener paramICommandListener, String paramString) throws CommandException {
/* 550 */     Scoreboard localScoreboard = d();
/* 551 */     ScoreboardObjective localScoreboardObjective = a(paramString, false);
/*     */     
/* 553 */     localScoreboard.unregisterObjective(localScoreboardObjective);
/*     */     
/* 555 */     a(paramICommandListener, this, "commands.scoreboard.objectives.remove.success", new Object[] { paramString });
/*     */   }
/*     */   
/*     */   protected void d(ICommandListener paramICommandListener) throws CommandException {
/* 559 */     Scoreboard localScoreboard = d();
/* 560 */     Collection localCollection = localScoreboard.getObjectives();
/*     */     
/* 562 */     if (localCollection.size() > 0) {
/* 563 */       ChatMessage localChatMessage = new ChatMessage("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(localCollection.size()) });
/* 564 */       localChatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 565 */       paramICommandListener.sendMessage(localChatMessage);
/*     */       
/* 567 */       for (ScoreboardObjective localScoreboardObjective : localCollection) {
/* 568 */         paramICommandListener.sendMessage(new ChatMessage("commands.scoreboard.objectives.list.entry", new Object[] { localScoreboardObjective.getName(), localScoreboardObjective.getDisplayName(), localScoreboardObjective.getCriteria().getName() }));
/*     */       }
/*     */     } else {
/* 571 */       throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void j(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 576 */     Scoreboard localScoreboard = d();
/* 577 */     String str = paramArrayOfString[(paramInt++)];
/* 578 */     int i = Scoreboard.getSlotForName(str);
/* 579 */     ScoreboardObjective localScoreboardObjective = null;
/*     */     
/* 581 */     if (paramArrayOfString.length == 4) {
/* 582 */       localScoreboardObjective = a(paramArrayOfString[paramInt], false);
/*     */     }
/*     */     
/* 585 */     if (i < 0) {
/* 586 */       throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { str });
/*     */     }
/*     */     
/* 589 */     localScoreboard.setDisplaySlot(i, localScoreboardObjective);
/*     */     
/* 591 */     if (localScoreboardObjective != null) {
/* 592 */       a(paramICommandListener, this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.getSlotName(i), localScoreboardObjective.getName() });
/*     */     } else {
/* 594 */       a(paramICommandListener, this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.getSlotName(i) });
/*     */     }
/*     */   }
/*     */   
/*     */   protected void k(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 599 */     Scoreboard localScoreboard = d();
/*     */     Object localObject1;
/* 601 */     Object localObject2; if (paramArrayOfString.length > paramInt) {
/* 602 */       localObject1 = e(paramICommandListener, paramArrayOfString[paramInt]);
/* 603 */       localObject2 = localScoreboard.getPlayerObjectives((String)localObject1);
/*     */       
/* 605 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, ((Map)localObject2).size());
/* 606 */       if (((Map)localObject2).size() > 0) {
/* 607 */         ChatMessage localChatMessage = new ChatMessage("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(((Map)localObject2).size()), localObject1 });
/* 608 */         localChatMessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 609 */         paramICommandListener.sendMessage(localChatMessage);
/*     */         
/* 611 */         for (ScoreboardScore localScoreboardScore : ((Map)localObject2).values()) {
/* 612 */           paramICommandListener.sendMessage(new ChatMessage("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(localScoreboardScore.getScore()), localScoreboardScore.getObjective().getDisplayName(), localScoreboardScore.getObjective().getName() }));
/*     */         }
/*     */       } else {
/* 615 */         throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { localObject1 });
/*     */       }
/*     */     } else {
/* 618 */       localObject1 = localScoreboard.getPlayers();
/*     */       
/* 620 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, ((Collection)localObject1).size());
/* 621 */       if (((Collection)localObject1).size() > 0) {
/* 622 */         localObject2 = new ChatMessage("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(((Collection)localObject1).size()) });
/* 623 */         ((ChatMessage)localObject2).getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/* 624 */         paramICommandListener.sendMessage((IChatBaseComponent)localObject2);
/* 625 */         paramICommandListener.sendMessage(new ChatComponentText(a(((Collection)localObject1).toArray())));
/*     */       } else {
/* 627 */         throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void l(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 633 */     String str1 = paramArrayOfString[(paramInt - 1)];
/* 634 */     int i = paramInt;
/* 635 */     String str2 = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
/* 636 */     if (str2.length() > 40) {
/* 637 */       throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] { str2, Integer.valueOf(40) });
/*     */     }
/* 639 */     ScoreboardObjective localScoreboardObjective = a(paramArrayOfString[(paramInt++)], true);
/* 640 */     int j = str1.equalsIgnoreCase("set") ? a(paramArrayOfString[(paramInt++)]) : a(paramArrayOfString[(paramInt++)], 0);
/*     */     
/* 642 */     if (paramArrayOfString.length > paramInt) {
/* 643 */       localObject = b(paramICommandListener, paramArrayOfString[i]);
/*     */       try {
/* 645 */         NBTTagCompound localNBTTagCompound1 = MojangsonParser.parse(a(paramArrayOfString, paramInt));
/* 646 */         NBTTagCompound localNBTTagCompound2 = new NBTTagCompound();
/* 647 */         ((Entity)localObject).e(localNBTTagCompound2);
/* 648 */         if (!GameProfileSerializer.a(localNBTTagCompound1, localNBTTagCompound2, true)) {
/* 649 */           throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] { str2 });
/*     */         }
/*     */       } catch (MojangsonParseException localMojangsonParseException) {
/* 652 */         throw new CommandException("commands.scoreboard.players.set.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*     */       }
/*     */     }
/*     */     
/* 656 */     Object localObject = d();
/* 657 */     ScoreboardScore localScoreboardScore = ((Scoreboard)localObject).getPlayerScoreForObjective(str2, localScoreboardObjective);
/* 658 */     if (str1.equalsIgnoreCase("set")) {
/* 659 */       localScoreboardScore.setScore(j);
/* 660 */     } else if (str1.equalsIgnoreCase("add")) {
/* 661 */       localScoreboardScore.addScore(j);
/*     */     } else {
/* 663 */       localScoreboardScore.removeScore(j);
/*     */     }
/*     */     
/* 666 */     a(paramICommandListener, this, "commands.scoreboard.players.set.success", new Object[] { localScoreboardObjective.getName(), str2, Integer.valueOf(localScoreboardScore.getScore()) });
/*     */   }
/*     */   
/*     */   protected void m(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 670 */     Scoreboard localScoreboard = d();
/* 671 */     String str = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
/*     */     
/* 673 */     if (paramArrayOfString.length > paramInt) {
/* 674 */       ScoreboardObjective localScoreboardObjective = a(paramArrayOfString[(paramInt++)], false);
/* 675 */       localScoreboard.resetPlayerScores(str, localScoreboardObjective);
/* 676 */       a(paramICommandListener, this, "commands.scoreboard.players.resetscore.success", new Object[] { localScoreboardObjective.getName(), str });
/*     */     } else {
/* 678 */       localScoreboard.resetPlayerScores(str, null);
/* 679 */       a(paramICommandListener, this, "commands.scoreboard.players.reset.success", new Object[] { str });
/*     */     }
/*     */   }
/*     */   
/*     */   protected void n(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 684 */     Scoreboard localScoreboard = d();
/* 685 */     String str = d(paramICommandListener, paramArrayOfString[(paramInt++)]);
/* 686 */     if (str.length() > 40) {
/* 687 */       throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] { str, Integer.valueOf(40) });
/*     */     }
/* 689 */     ScoreboardObjective localScoreboardObjective = a(paramArrayOfString[paramInt], false);
/* 690 */     if (localScoreboardObjective.getCriteria() != IScoreboardCriteria.c) {
/* 691 */       throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] { localScoreboardObjective.getName() });
/*     */     }
/* 693 */     ScoreboardScore localScoreboardScore = localScoreboard.getPlayerScoreForObjective(str, localScoreboardObjective);
/* 694 */     localScoreboardScore.a(false);
/* 695 */     a(paramICommandListener, this, "commands.scoreboard.players.enable.success", new Object[] { localScoreboardObjective.getName(), str });
/*     */   }
/*     */   
/*     */   protected void o(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 699 */     Scoreboard localScoreboard = d();
/* 700 */     String str = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
/* 701 */     if (str.length() > 40) {
/* 702 */       throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] { str, Integer.valueOf(40) });
/*     */     }
/* 704 */     ScoreboardObjective localScoreboardObjective = a(paramArrayOfString[(paramInt++)], false);
/* 705 */     if (!localScoreboard.b(str, localScoreboardObjective)) {
/* 706 */       throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] { localScoreboardObjective.getName(), str });
/*     */     }
/*     */     
/* 709 */     int i = paramArrayOfString[paramInt].equals("*") ? Integer.MIN_VALUE : a(paramArrayOfString[paramInt]);
/* 710 */     paramInt++;
/* 711 */     int j = (paramInt >= paramArrayOfString.length) || (paramArrayOfString[paramInt].equals("*")) ? Integer.MAX_VALUE : a(paramArrayOfString[paramInt], i);
/*     */     
/* 713 */     ScoreboardScore localScoreboardScore = localScoreboard.getPlayerScoreForObjective(str, localScoreboardObjective);
/* 714 */     if ((localScoreboardScore.getScore() < i) || (localScoreboardScore.getScore() > j)) {
/* 715 */       throw new CommandException("commands.scoreboard.players.test.failed", new Object[] { Integer.valueOf(localScoreboardScore.getScore()), Integer.valueOf(i), Integer.valueOf(j) });
/*     */     }
/* 717 */     a(paramICommandListener, this, "commands.scoreboard.players.test.success", new Object[] { Integer.valueOf(localScoreboardScore.getScore()), Integer.valueOf(i), Integer.valueOf(j) });
/*     */   }
/*     */   
/*     */   protected void p(ICommandListener paramICommandListener, String[] paramArrayOfString, int paramInt) throws CommandException {
/* 721 */     Scoreboard localScoreboard = d();
/* 722 */     String str1 = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
/* 723 */     ScoreboardObjective localScoreboardObjective1 = a(paramArrayOfString[(paramInt++)], true);
/*     */     
/* 725 */     String str2 = paramArrayOfString[(paramInt++)];
/* 726 */     String str3 = e(paramICommandListener, paramArrayOfString[(paramInt++)]);
/* 727 */     ScoreboardObjective localScoreboardObjective2 = a(paramArrayOfString[paramInt], false);
/*     */     
/* 729 */     if (str1.length() > 40) {
/* 730 */       throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] { str1, Integer.valueOf(40) });
/*     */     }
/* 732 */     if (str3.length() > 40) {
/* 733 */       throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] { str3, Integer.valueOf(40) });
/*     */     }
/*     */     
/* 736 */     ScoreboardScore localScoreboardScore1 = localScoreboard.getPlayerScoreForObjective(str1, localScoreboardObjective1);
/* 737 */     if (!localScoreboard.b(str3, localScoreboardObjective2)) {
/* 738 */       throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] { localScoreboardObjective2.getName(), str3 });
/*     */     }
/* 740 */     ScoreboardScore localScoreboardScore2 = localScoreboard.getPlayerScoreForObjective(str3, localScoreboardObjective2);
/*     */     
/* 742 */     if (str2.equals("+=")) {
/* 743 */       localScoreboardScore1.setScore(localScoreboardScore1.getScore() + localScoreboardScore2.getScore());
/* 744 */     } else if (str2.equals("-=")) {
/* 745 */       localScoreboardScore1.setScore(localScoreboardScore1.getScore() - localScoreboardScore2.getScore());
/* 746 */     } else if (str2.equals("*=")) {
/* 747 */       localScoreboardScore1.setScore(localScoreboardScore1.getScore() * localScoreboardScore2.getScore());
/* 748 */     } else if (str2.equals("/=")) {
/* 749 */       if (localScoreboardScore2.getScore() != 0) {
/* 750 */         localScoreboardScore1.setScore(localScoreboardScore1.getScore() / localScoreboardScore2.getScore());
/*     */       }
/* 752 */     } else if (str2.equals("%=")) {
/* 753 */       if (localScoreboardScore2.getScore() != 0) {
/* 754 */         localScoreboardScore1.setScore(localScoreboardScore1.getScore() % localScoreboardScore2.getScore());
/*     */       }
/* 756 */     } else if (str2.equals("=")) {
/* 757 */       localScoreboardScore1.setScore(localScoreboardScore2.getScore());
/* 758 */     } else if (str2.equals("<")) {
/* 759 */       localScoreboardScore1.setScore(Math.min(localScoreboardScore1.getScore(), localScoreboardScore2.getScore()));
/* 760 */     } else if (str2.equals(">")) {
/* 761 */       localScoreboardScore1.setScore(Math.max(localScoreboardScore1.getScore(), localScoreboardScore2.getScore()));
/* 762 */     } else if (str2.equals("><")) {
/* 763 */       int i = localScoreboardScore1.getScore();
/* 764 */       localScoreboardScore1.setScore(localScoreboardScore2.getScore());
/* 765 */       localScoreboardScore2.setScore(i);
/*     */     } else {
/* 767 */       throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] { str2 });
/*     */     }
/* 769 */     a(paramICommandListener, this, "commands.scoreboard.players.operation.success", new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 775 */     if (paramArrayOfString.length == 1) {
/* 776 */       return a(paramArrayOfString, new String[] { "objectives", "players", "teams" });
/*     */     }
/*     */     
/* 779 */     if (paramArrayOfString[0].equalsIgnoreCase("objectives")) {
/* 780 */       if (paramArrayOfString.length == 2) {
/* 781 */         return a(paramArrayOfString, new String[] { "list", "add", "remove", "setdisplay" });
/*     */       }
/*     */       
/* 784 */       if (paramArrayOfString[1].equalsIgnoreCase("add")) {
/* 785 */         if (paramArrayOfString.length == 4) {
/* 786 */           Set localSet = IScoreboardCriteria.criteria.keySet();
/* 787 */           return a(paramArrayOfString, localSet);
/*     */         }
/* 789 */       } else if (paramArrayOfString[1].equalsIgnoreCase("remove")) {
/* 790 */         if (paramArrayOfString.length == 3) {
/* 791 */           return a(paramArrayOfString, a(false));
/*     */         }
/* 793 */       } else if (paramArrayOfString[1].equalsIgnoreCase("setdisplay")) {
/* 794 */         if (paramArrayOfString.length == 3)
/* 795 */           return a(paramArrayOfString, Scoreboard.h());
/* 796 */         if (paramArrayOfString.length == 4) {
/* 797 */           return a(paramArrayOfString, a(false));
/*     */         }
/*     */       }
/* 800 */     } else if (paramArrayOfString[0].equalsIgnoreCase("players")) {
/* 801 */       if (paramArrayOfString.length == 2) {
/* 802 */         return a(paramArrayOfString, new String[] { "set", "add", "remove", "reset", "list", "enable", "test", "operation" });
/*     */       }
/*     */       
/* 805 */       if ((paramArrayOfString[1].equalsIgnoreCase("set")) || (paramArrayOfString[1].equalsIgnoreCase("add")) || (paramArrayOfString[1].equalsIgnoreCase("remove")) || (paramArrayOfString[1].equalsIgnoreCase("reset"))) {
/* 806 */         if (paramArrayOfString.length == 3)
/* 807 */           return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/* 808 */         if (paramArrayOfString.length == 4) {
/* 809 */           return a(paramArrayOfString, a(true));
/*     */         }
/* 811 */       } else if (paramArrayOfString[1].equalsIgnoreCase("enable")) {
/* 812 */         if (paramArrayOfString.length == 3)
/* 813 */           return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/* 814 */         if (paramArrayOfString.length == 4) {
/* 815 */           return a(paramArrayOfString, e());
/*     */         }
/* 817 */       } else if ((paramArrayOfString[1].equalsIgnoreCase("list")) || (paramArrayOfString[1].equalsIgnoreCase("test"))) {
/* 818 */         if (paramArrayOfString.length == 3)
/* 819 */           return a(paramArrayOfString, d().getPlayers());
/* 820 */         if ((paramArrayOfString.length == 4) && (paramArrayOfString[1].equalsIgnoreCase("test"))) {
/* 821 */           return a(paramArrayOfString, a(false));
/*     */         }
/* 823 */       } else if (paramArrayOfString[1].equalsIgnoreCase("operation")) {
/* 824 */         if (paramArrayOfString.length == 3)
/* 825 */           return a(paramArrayOfString, d().getPlayers());
/* 826 */         if (paramArrayOfString.length == 4)
/* 827 */           return a(paramArrayOfString, a(true));
/* 828 */         if (paramArrayOfString.length == 5)
/* 829 */           return a(paramArrayOfString, new String[] { "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><" });
/* 830 */         if (paramArrayOfString.length == 6)
/* 831 */           return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/* 832 */         if (paramArrayOfString.length == 7) {
/* 833 */           return a(paramArrayOfString, a(false));
/*     */         }
/*     */       }
/* 836 */     } else if (paramArrayOfString[0].equalsIgnoreCase("teams")) {
/* 837 */       if (paramArrayOfString.length == 2) {
/* 838 */         return a(paramArrayOfString, new String[] { "add", "remove", "join", "leave", "empty", "list", "option" });
/*     */       }
/*     */       
/* 841 */       if (paramArrayOfString[1].equalsIgnoreCase("join")) {
/* 842 */         if (paramArrayOfString.length == 3)
/* 843 */           return a(paramArrayOfString, d().getTeamNames());
/* 844 */         if (paramArrayOfString.length >= 4)
/* 845 */           return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*     */       } else {
/* 847 */         if (paramArrayOfString[1].equalsIgnoreCase("leave"))
/* 848 */           return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/* 849 */         if ((paramArrayOfString[1].equalsIgnoreCase("empty")) || (paramArrayOfString[1].equalsIgnoreCase("list")) || (paramArrayOfString[1].equalsIgnoreCase("remove"))) {
/* 850 */           if (paramArrayOfString.length == 3) {
/* 851 */             return a(paramArrayOfString, d().getTeamNames());
/*     */           }
/* 853 */         } else if (paramArrayOfString[1].equalsIgnoreCase("option")) {
/* 854 */           if (paramArrayOfString.length == 3)
/* 855 */             return a(paramArrayOfString, d().getTeamNames());
/* 856 */           if (paramArrayOfString.length == 4)
/* 857 */             return a(paramArrayOfString, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility" });
/* 858 */           if (paramArrayOfString.length == 5) {
/* 859 */             if (paramArrayOfString[3].equalsIgnoreCase("color"))
/* 860 */               return a(paramArrayOfString, EnumChatFormat.a(true, false));
/* 861 */             if ((paramArrayOfString[3].equalsIgnoreCase("nametagVisibility")) || (paramArrayOfString[3].equalsIgnoreCase("deathMessageVisibility")))
/* 862 */               return a(paramArrayOfString, ScoreboardTeamBase.EnumNameTagVisibility.a());
/* 863 */             if ((paramArrayOfString[3].equalsIgnoreCase("friendlyfire")) || (paramArrayOfString[3].equalsIgnoreCase("seeFriendlyInvisibles"))) {
/* 864 */               return a(paramArrayOfString, new String[] { "true", "false" });
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 870 */     return null;
/*     */   }
/*     */   
/*     */   protected List<String> a(boolean paramBoolean) {
/* 874 */     Collection localCollection = d().getObjectives();
/* 875 */     ArrayList localArrayList = Lists.newArrayList();
/*     */     
/* 877 */     for (ScoreboardObjective localScoreboardObjective : localCollection) {
/* 878 */       if ((!paramBoolean) || (!localScoreboardObjective.getCriteria().isReadOnly())) {
/* 879 */         localArrayList.add(localScoreboardObjective.getName());
/*     */       }
/*     */     }
/*     */     
/* 883 */     return localArrayList;
/*     */   }
/*     */   
/*     */   protected List<String> e() {
/* 887 */     Collection localCollection = d().getObjectives();
/* 888 */     ArrayList localArrayList = Lists.newArrayList();
/*     */     
/* 890 */     for (ScoreboardObjective localScoreboardObjective : localCollection) {
/* 891 */       if (localScoreboardObjective.getCriteria() == IScoreboardCriteria.c) {
/* 892 */         localArrayList.add(localScoreboardObjective.getName());
/*     */       }
/*     */     }
/*     */     
/* 896 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 901 */     if (paramArrayOfString[0].equalsIgnoreCase("players")) {
/* 902 */       if ((paramArrayOfString.length > 1) && (paramArrayOfString[1].equalsIgnoreCase("operation"))) {
/* 903 */         return (paramInt == 2) || (paramInt == 5);
/*     */       }
/* 905 */       return paramInt == 2; }
/* 906 */     if (paramArrayOfString[0].equalsIgnoreCase("teams")) {
/* 907 */       return paramInt == 2;
/*     */     }
/*     */     
/* 910 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandScoreboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */