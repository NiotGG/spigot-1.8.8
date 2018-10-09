/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.ArrayUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scoreboard.DisplaySlot;
/*     */ import org.bukkit.scoreboard.Objective;
/*     */ import org.bukkit.scoreboard.Score;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ import org.bukkit.scoreboard.ScoreboardManager;
/*     */ import org.bukkit.scoreboard.Team;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ @Deprecated
/*     */ public class ScoreboardCommand extends VanillaCommand
/*     */ {
/*  32 */   private static final List<String> MAIN_CHOICES = ImmutableList.of("objectives", "players", "teams");
/*  33 */   private static final List<String> OBJECTIVES_CHOICES = ImmutableList.of("list", "add", "remove", "setdisplay");
/*  34 */   private static final List<String> OBJECTIVES_CRITERIA = ImmutableList.of("health", "playerKillCount", "totalKillCount", "deathCount", "dummy");
/*  35 */   private static final List<String> PLAYERS_CHOICES = ImmutableList.of("set", "add", "remove", "reset", "list");
/*  36 */   private static final List<String> TEAMS_CHOICES = ImmutableList.of("add", "remove", "join", "leave", "empty", "list", "option");
/*  37 */   private static final List<String> TEAMS_OPTION_CHOICES = ImmutableList.of("color", "friendlyfire", "seeFriendlyInvisibles");
/*  38 */   private static final Map<String, DisplaySlot> OBJECTIVES_DISPLAYSLOT = com.google.common.collect.ImmutableMap.of("belowName", DisplaySlot.BELOW_NAME, "list", DisplaySlot.PLAYER_LIST, "sidebar", DisplaySlot.SIDEBAR);
/*  39 */   private static final Map<String, ChatColor> TEAMS_OPTION_COLOR = com.google.common.collect.ImmutableMap.builder()
/*  40 */     .put("aqua", ChatColor.AQUA)
/*  41 */     .put("black", ChatColor.BLACK)
/*  42 */     .put("blue", ChatColor.BLUE)
/*  43 */     .put("bold", ChatColor.BOLD)
/*  44 */     .put("dark_aqua", ChatColor.DARK_AQUA)
/*  45 */     .put("dark_blue", ChatColor.DARK_BLUE)
/*  46 */     .put("dark_gray", ChatColor.DARK_GRAY)
/*  47 */     .put("dark_green", ChatColor.DARK_GREEN)
/*  48 */     .put("dark_purple", ChatColor.DARK_PURPLE)
/*  49 */     .put("dark_red", ChatColor.DARK_RED)
/*  50 */     .put("gold", ChatColor.GOLD)
/*  51 */     .put("gray", ChatColor.GRAY)
/*  52 */     .put("green", ChatColor.GREEN)
/*  53 */     .put("italic", ChatColor.ITALIC)
/*  54 */     .put("light_purple", ChatColor.LIGHT_PURPLE)
/*  55 */     .put("obfuscated", ChatColor.MAGIC)
/*  56 */     .put("red", ChatColor.RED)
/*  57 */     .put("reset", ChatColor.RESET)
/*  58 */     .put("strikethrough", ChatColor.STRIKETHROUGH)
/*  59 */     .put("underline", ChatColor.UNDERLINE)
/*  60 */     .put("white", ChatColor.WHITE)
/*  61 */     .put("yellow", ChatColor.YELLOW)
/*  62 */     .build();
/*  63 */   private static final List<String> BOOLEAN = ImmutableList.of("true", "false");
/*     */   
/*     */   public ScoreboardCommand() {
/*  66 */     super("scoreboard");
/*  67 */     this.description = "Scoreboard control";
/*  68 */     this.usageMessage = "/scoreboard";
/*  69 */     setPermission("bukkit.command.scoreboard");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*     */   {
/*  74 */     if (!testPermission(sender))
/*  75 */       return true;
/*  76 */     if ((args.length < 1) || (args[0].length() == 0)) {
/*  77 */       sender.sendMessage(ChatColor.RED + "Usage: /scoreboard <objectives|players|teams>");
/*  78 */       return false;
/*     */     }
/*     */     
/*  81 */     Scoreboard mainScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
/*     */     
/*  83 */     if (args[0].equalsIgnoreCase("objectives")) {
/*  84 */       if (args.length == 1) {
/*  85 */         sender.sendMessage(ChatColor.RED + "Usage: /scoreboard objectives <list|add|remove|setdisplay>");
/*  86 */         return false;
/*     */       }
/*  88 */       if (args[1].equalsIgnoreCase("list")) {
/*  89 */         Set<Objective> objectives = mainScoreboard.getObjectives();
/*  90 */         if (objectives.isEmpty()) {
/*  91 */           sender.sendMessage(ChatColor.RED + "There are no objectives on the scoreboard");
/*  92 */           return false;
/*     */         }
/*  94 */         sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + objectives.size() + " objective(s) on scoreboard");
/*  95 */         for (Objective objective : objectives) {
/*  96 */           sender.sendMessage("- " + objective.getName() + ": displays as '" + objective.getDisplayName() + "' and is type '" + objective.getCriteria() + "'");
/*     */         }
/*  98 */       } else if (args[1].equalsIgnoreCase("add")) {
/*  99 */         if (args.length < 4) {
/* 100 */           sender.sendMessage(ChatColor.RED + "/scoreboard objectives add <name> <criteriaType> [display name ...]");
/* 101 */           return false;
/*     */         }
/* 103 */         String name = args[2];
/* 104 */         String criteria = args[3];
/*     */         
/* 106 */         if (criteria == null) {
/* 107 */           sender.sendMessage(ChatColor.RED + "Invalid objective criteria type. Valid types are: " + stringCollectionToString(OBJECTIVES_CRITERIA));
/* 108 */         } else if (name.length() > 16) {
/* 109 */           sender.sendMessage(ChatColor.RED + "The name '" + name + "' is too long for an objective, it can be at most 16 characters long");
/* 110 */         } else if (mainScoreboard.getObjective(name) != null) {
/* 111 */           sender.sendMessage(ChatColor.RED + "An objective with the name '" + name + "' already exists");
/*     */         } else {
/* 113 */           String displayName = null;
/* 114 */           if (args.length > 4) {
/* 115 */             displayName = StringUtils.join(ArrayUtils.subarray(args, 4, args.length), ' ');
/* 116 */             if (displayName.length() > 32) {
/* 117 */               sender.sendMessage(ChatColor.RED + "The name '" + displayName + "' is too long for an objective, it can be at most 32 characters long");
/* 118 */               return false;
/*     */             }
/*     */           }
/* 121 */           Objective objective = mainScoreboard.registerNewObjective(name, criteria);
/* 122 */           if ((displayName != null) && (displayName.length() > 0)) {
/* 123 */             objective.setDisplayName(displayName);
/*     */           }
/* 125 */           sender.sendMessage("Added new objective '" + name + "' successfully");
/*     */         }
/* 127 */       } else if (args[1].equalsIgnoreCase("remove")) {
/* 128 */         if (args.length != 3) {
/* 129 */           sender.sendMessage(ChatColor.RED + "/scoreboard objectives remove <name>");
/* 130 */           return false;
/*     */         }
/* 132 */         String name = args[2];
/* 133 */         Objective objective = mainScoreboard.getObjective(name);
/* 134 */         if (objective == null) {
/* 135 */           sender.sendMessage(ChatColor.RED + "No objective was found by the name '" + name + "'");
/*     */         } else {
/* 137 */           objective.unregister();
/* 138 */           sender.sendMessage("Removed objective '" + name + "' successfully");
/*     */         }
/* 140 */       } else if (args[1].equalsIgnoreCase("setdisplay")) {
/* 141 */         if ((args.length != 3) && (args.length != 4)) {
/* 142 */           sender.sendMessage(ChatColor.RED + "/scoreboard objectives setdisplay <slot> [objective]");
/* 143 */           return false;
/*     */         }
/* 145 */         String slotName = args[2];
/* 146 */         DisplaySlot slot = (DisplaySlot)OBJECTIVES_DISPLAYSLOT.get(slotName);
/* 147 */         if (slot == null) {
/* 148 */           sender.sendMessage(ChatColor.RED + "No such display slot '" + slotName + "'");
/*     */         }
/* 150 */         else if (args.length == 4) {
/* 151 */           String objectiveName = args[3];
/* 152 */           Objective objective = mainScoreboard.getObjective(objectiveName);
/* 153 */           if (objective == null) {
/* 154 */             sender.sendMessage(ChatColor.RED + "No objective was found by the name '" + objectiveName + "'");
/* 155 */             return false;
/*     */           }
/*     */           
/* 158 */           objective.setDisplaySlot(slot);
/* 159 */           sender.sendMessage("Set the display objective in slot '" + slotName + "' to '" + objective.getName() + "'");
/*     */         } else {
/* 161 */           Objective objective = mainScoreboard.getObjective(slot);
/* 162 */           if (objective != null) {
/* 163 */             objective.setDisplaySlot(null);
/*     */           }
/* 165 */           sender.sendMessage("Cleared objective display slot '" + slotName + "'");
/*     */         }
/*     */       } } else { String playerName;
/*     */       Object score;
/* 169 */       if (args[0].equalsIgnoreCase("players")) {
/* 170 */         if (args.length == 1) {
/* 171 */           sender.sendMessage(ChatColor.RED + "/scoreboard players <set|add|remove|reset|list>");
/* 172 */           return false; }
/*     */         int value;
/* 174 */         if ((args[1].equalsIgnoreCase("set")) || (args[1].equalsIgnoreCase("add")) || (args[1].equalsIgnoreCase("remove"))) {
/* 175 */           if (args.length != 5) {
/* 176 */             if (args[1].equalsIgnoreCase("set")) {
/* 177 */               sender.sendMessage(ChatColor.RED + "/scoreboard players set <player> <objective> <score>");
/* 178 */             } else if (args[1].equalsIgnoreCase("add")) {
/* 179 */               sender.sendMessage(ChatColor.RED + "/scoreboard players add <player> <objective> <count>");
/*     */             } else {
/* 181 */               sender.sendMessage(ChatColor.RED + "/scoreboard players remove <player> <objective> <count>");
/*     */             }
/* 183 */             return false;
/*     */           }
/* 185 */           String objectiveName = args[3];
/* 186 */           Objective objective = mainScoreboard.getObjective(objectiveName);
/* 187 */           if (objective == null) {
/* 188 */             sender.sendMessage(ChatColor.RED + "No objective was found by the name '" + objectiveName + "'");
/* 189 */             return false; }
/* 190 */           if (!objective.isModifiable()) {
/* 191 */             sender.sendMessage(ChatColor.RED + "The objective '" + objectiveName + "' is read-only and cannot be set");
/* 192 */             return false;
/*     */           }
/*     */           
/* 195 */           String valueString = args[4];
/*     */           try
/*     */           {
/* 198 */             value = Integer.parseInt(valueString);
/*     */           } catch (NumberFormatException localNumberFormatException) { int value;
/* 200 */             sender.sendMessage(ChatColor.RED + "'" + valueString + "' is not a valid number");
/* 201 */             return false;
/*     */           }
/* 203 */           if ((value < 1) && (!args[1].equalsIgnoreCase("set"))) {
/* 204 */             sender.sendMessage(ChatColor.RED + "The number you have entered (" + value + ") is too small, it must be at least 1");
/* 205 */             return false;
/*     */           }
/*     */           
/* 208 */           playerName = args[2];
/* 209 */           if (playerName.length() > 16) {
/* 210 */             sender.sendMessage(ChatColor.RED + "'" + playerName + "' is too long for a player name");
/* 211 */             return false;
/*     */           }
/* 213 */           Score score = objective.getScore(playerName);
/*     */           int newScore;
/* 215 */           int newScore; if (args[1].equalsIgnoreCase("set")) {
/* 216 */             newScore = value; } else { int newScore;
/* 217 */             if (args[1].equalsIgnoreCase("add")) {
/* 218 */               newScore = score.getScore() + value;
/*     */             } else
/* 220 */               newScore = score.getScore() - value;
/*     */           }
/* 222 */           score.setScore(newScore);
/* 223 */           sender.sendMessage("Set score of " + objectiveName + " for player " + playerName + " to " + newScore);
/* 224 */         } else if (args[1].equalsIgnoreCase("reset")) {
/* 225 */           if (args.length != 3) {
/* 226 */             sender.sendMessage(ChatColor.RED + "/scoreboard players reset <player>");
/* 227 */             return false;
/*     */           }
/* 229 */           String playerName = args[2];
/* 230 */           if (playerName.length() > 16) {
/* 231 */             sender.sendMessage(ChatColor.RED + "'" + playerName + "' is too long for a player name");
/* 232 */             return false;
/*     */           }
/* 234 */           mainScoreboard.resetScores(playerName);
/* 235 */           sender.sendMessage("Reset all scores of player " + playerName);
/* 236 */         } else if (args[1].equalsIgnoreCase("list")) {
/* 237 */           if (args.length > 3) {
/* 238 */             sender.sendMessage(ChatColor.RED + "/scoreboard players list <player>");
/* 239 */             return false;
/*     */           }
/* 241 */           if (args.length == 2) {
/* 242 */             Set<String> entries = mainScoreboard.getEntries();
/* 243 */             if (entries.isEmpty()) {
/* 244 */               sender.sendMessage(ChatColor.RED + "There are no tracked players on the scoreboard");
/*     */             } else {
/* 246 */               sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + entries.size() + " tracked players on the scoreboard");
/* 247 */               sender.sendMessage(stringCollectionToString(entries));
/*     */             }
/*     */           } else {
/* 250 */             String playerName = args[2];
/* 251 */             if (playerName.length() > 16) {
/* 252 */               sender.sendMessage(ChatColor.RED + "'" + playerName + "' is too long for a player name");
/* 253 */               return false;
/*     */             }
/* 255 */             Set<Score> scores = mainScoreboard.getScores(playerName);
/* 256 */             if (scores.isEmpty()) {
/* 257 */               sender.sendMessage(ChatColor.RED + "Player " + playerName + " has no scores recorded");
/*     */             } else {
/* 259 */               sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + scores.size() + " tracked objective(s) for " + playerName);
/* 260 */               for (value = scores.iterator(); value.hasNext();) { score = (Score)value.next();
/* 261 */                 sender.sendMessage("- " + ((Score)score).getObjective().getDisplayName() + ": " + ((Score)score).getScore() + " (" + ((Score)score).getObjective().getName() + ")");
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 266 */       } else if (args[0].equalsIgnoreCase("teams")) {
/* 267 */         if (args.length == 1) {
/* 268 */           sender.sendMessage(ChatColor.RED + "/scoreboard teams <list|add|remove|empty|join|leave|option>");
/* 269 */           return false;
/*     */         }
/* 271 */         if (args[1].equalsIgnoreCase("list")) {
/* 272 */           if (args.length == 2) {
/* 273 */             Set<Team> teams = mainScoreboard.getTeams();
/* 274 */             if (teams.isEmpty()) {
/* 275 */               sender.sendMessage(ChatColor.RED + "There are no teams registered on the scoreboard");
/*     */             } else {
/* 277 */               sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + teams.size() + " teams on the scoreboard");
/* 278 */               for (score = teams.iterator(); ((Iterator)score).hasNext();) { Team team = (Team)((Iterator)score).next();
/* 279 */                 sender.sendMessage("- " + team.getName() + ": '" + team.getDisplayName() + "' has " + team.getSize() + " players");
/*     */               }
/*     */             }
/* 282 */           } else if (args.length == 3) {
/* 283 */             String teamName = args[2];
/* 284 */             Team team = mainScoreboard.getTeam(teamName);
/* 285 */             if (team == null) {
/* 286 */               sender.sendMessage(ChatColor.RED + "No team was found by the name '" + teamName + "'");
/*     */             } else {
/* 288 */               Object players = team.getPlayers();
/* 289 */               if (((Set)players).isEmpty()) {
/* 290 */                 sender.sendMessage(ChatColor.RED + "Team " + team.getName() + " has no players");
/*     */               } else {
/* 292 */                 sender.sendMessage(ChatColor.DARK_GREEN + "Showing " + ((Set)players).size() + " player(s) in team " + team.getName());
/* 293 */                 sender.sendMessage(offlinePlayerSetToString((Set)players));
/*     */               }
/*     */             }
/*     */           } else {
/* 297 */             sender.sendMessage(ChatColor.RED + "/scoreboard teams list [name]");
/* 298 */             return false;
/*     */           }
/* 300 */         } else if (args[1].equalsIgnoreCase("add")) {
/* 301 */           if (args.length < 3) {
/* 302 */             sender.sendMessage(ChatColor.RED + "/scoreboard teams add <name> [display name ...]");
/* 303 */             return false;
/*     */           }
/* 305 */           String name = args[2];
/* 306 */           if (name.length() > 16) {
/* 307 */             sender.sendMessage(ChatColor.RED + "The name '" + name + "' is too long for a team, it can be at most 16 characters long");
/* 308 */           } else if (mainScoreboard.getTeam(name) != null) {
/* 309 */             sender.sendMessage(ChatColor.RED + "A team with the name '" + name + "' already exists");
/*     */           } else {
/* 311 */             String displayName = null;
/* 312 */             if (args.length > 3) {
/* 313 */               displayName = StringUtils.join(ArrayUtils.subarray(args, 3, args.length), ' ');
/* 314 */               if (displayName.length() > 32) {
/* 315 */                 sender.sendMessage(ChatColor.RED + "The display name '" + displayName + "' is too long for a team, it can be at most 32 characters long");
/* 316 */                 return false;
/*     */               }
/*     */             }
/* 319 */             Team team = mainScoreboard.registerNewTeam(name);
/* 320 */             if ((displayName != null) && (displayName.length() > 0)) {
/* 321 */               team.setDisplayName(displayName);
/*     */             }
/* 323 */             sender.sendMessage("Added new team '" + team.getName() + "' successfully");
/*     */           }
/* 325 */         } else if (args[1].equalsIgnoreCase("remove")) {
/* 326 */           if (args.length != 3) {
/* 327 */             sender.sendMessage(ChatColor.RED + "/scoreboard teams remove <name>");
/* 328 */             return false;
/*     */           }
/* 330 */           String name = args[2];
/* 331 */           Team team = mainScoreboard.getTeam(name);
/* 332 */           if (team == null) {
/* 333 */             sender.sendMessage(ChatColor.RED + "No team was found by the name '" + name + "'");
/*     */           } else {
/* 335 */             team.unregister();
/* 336 */             sender.sendMessage("Removed team " + name);
/*     */           }
/* 338 */         } else if (args[1].equalsIgnoreCase("empty")) {
/* 339 */           if (args.length != 3) {
/* 340 */             sender.sendMessage(ChatColor.RED + "/scoreboard teams clear <name>");
/* 341 */             return false;
/*     */           }
/* 343 */           String name = args[2];
/* 344 */           Team team = mainScoreboard.getTeam(name);
/* 345 */           if (team == null) {
/* 346 */             sender.sendMessage(ChatColor.RED + "No team was found by the name '" + name + "'");
/*     */           } else {
/* 348 */             Object players = team.getPlayers();
/* 349 */             if (((Set)players).isEmpty()) {
/* 350 */               sender.sendMessage(ChatColor.RED + "Team " + team.getName() + " is already empty, cannot remove nonexistant players");
/*     */             } else {
/* 352 */               for (OfflinePlayer player : (Set)players) {
/* 353 */                 team.removePlayer(player);
/*     */               }
/* 355 */               sender.sendMessage("Removed all " + ((Set)players).size() + " player(s) from team " + team.getName());
/*     */             }
/*     */           }
/* 358 */         } else if (args[1].equalsIgnoreCase("join")) {
/* 359 */           if ((sender instanceof Player) ? args.length < 3 : args.length < 4) {
/* 360 */             sender.sendMessage(ChatColor.RED + "/scoreboard teams join <team> [player...]");
/* 361 */             return false;
/*     */           }
/* 363 */           String teamName = args[2];
/* 364 */           Team team = mainScoreboard.getTeam(teamName);
/* 365 */           if (team == null) {
/* 366 */             sender.sendMessage(ChatColor.RED + "No team was found by the name '" + teamName + "'");
/*     */           } else {
/* 368 */             Object addedPlayers = new HashSet();
/* 369 */             if (((sender instanceof Player)) && (args.length == 3)) {
/* 370 */               team.addPlayer((Player)sender);
/* 371 */               ((Set)addedPlayers).add(sender.getName());
/*     */             } else {
/* 373 */               for (int i = 3; i < args.length; i++) {
/* 374 */                 String playerName = args[i];
/*     */                 
/* 376 */                 Player player = Bukkit.getPlayerExact(playerName);
/* 377 */                 OfflinePlayer offlinePlayer; OfflinePlayer offlinePlayer; if (player != null) {
/* 378 */                   offlinePlayer = player;
/*     */                 } else {
/* 380 */                   offlinePlayer = Bukkit.getOfflinePlayer(playerName);
/*     */                 }
/* 382 */                 team.addPlayer(offlinePlayer);
/* 383 */                 ((Set)addedPlayers).add(offlinePlayer.getName());
/*     */               }
/*     */             }
/* 386 */             sender.sendMessage("Added " + ((Set)addedPlayers).size() + " player(s) to team " + team.getName() + ": " + stringCollectionToString((Collection)addedPlayers));
/*     */           }
/* 388 */         } else if (args[1].equalsIgnoreCase("leave")) {
/* 389 */           if ((!(sender instanceof Player)) && (args.length < 3)) {
/* 390 */             sender.sendMessage(ChatColor.RED + "/scoreboard teams leave [player...]");
/* 391 */             return false;
/*     */           }
/* 393 */           Set<String> left = new HashSet();
/* 394 */           Set<String> noTeam = new HashSet();
/* 395 */           if (((sender instanceof Player)) && (args.length == 2)) {
/* 396 */             Team team = mainScoreboard.getPlayerTeam((Player)sender);
/* 397 */             if (team != null) {
/* 398 */               team.removePlayer((Player)sender);
/* 399 */               left.add(sender.getName());
/*     */             } else {
/* 401 */               noTeam.add(sender.getName());
/*     */             }
/*     */           } else {
/* 404 */             for (int i = 2; i < args.length; i++) {
/* 405 */               String playerName = args[i];
/*     */               
/* 407 */               Player player = Bukkit.getPlayerExact(playerName);
/* 408 */               OfflinePlayer offlinePlayer; OfflinePlayer offlinePlayer; if (player != null) {
/* 409 */                 offlinePlayer = player;
/*     */               } else {
/* 411 */                 offlinePlayer = Bukkit.getOfflinePlayer(playerName);
/*     */               }
/* 413 */               Team team = mainScoreboard.getPlayerTeam(offlinePlayer);
/* 414 */               if (team != null) {
/* 415 */                 team.removePlayer(offlinePlayer);
/* 416 */                 left.add(offlinePlayer.getName());
/*     */               } else {
/* 418 */                 noTeam.add(offlinePlayer.getName());
/*     */               }
/*     */             }
/*     */           }
/* 422 */           if (!left.isEmpty()) {
/* 423 */             sender.sendMessage("Removed " + left.size() + " player(s) from their teams: " + stringCollectionToString(left));
/*     */           }
/* 425 */           if (!noTeam.isEmpty()) {
/* 426 */             sender.sendMessage("Could not remove " + noTeam.size() + " player(s) from their teams: " + stringCollectionToString(noTeam));
/*     */           }
/* 428 */         } else if (args[1].equalsIgnoreCase("option")) {
/* 429 */           if ((args.length != 4) && (args.length != 5)) {
/* 430 */             sender.sendMessage(ChatColor.RED + "/scoreboard teams option <team> <friendlyfire|color|seefriendlyinvisibles> <value>");
/* 431 */             return false;
/*     */           }
/* 433 */           String teamName = args[2];
/* 434 */           Team team = mainScoreboard.getTeam(teamName);
/* 435 */           if (team == null) {
/* 436 */             sender.sendMessage(ChatColor.RED + "No team was found by the name '" + teamName + "'");
/* 437 */             return false;
/*     */           }
/* 439 */           String option = args[3].toLowerCase();
/* 440 */           if ((!option.equals("friendlyfire")) && (!option.equals("color")) && (!option.equals("seefriendlyinvisibles"))) {
/* 441 */             sender.sendMessage(ChatColor.RED + "/scoreboard teams option <team> <friendlyfire|color|seefriendlyinvisibles> <value>");
/* 442 */             return false;
/*     */           }
/* 444 */           if (args.length == 4) {
/* 445 */             if (option.equals("color")) {
/* 446 */               sender.sendMessage(ChatColor.RED + "Valid values for option color are: " + stringCollectionToString(TEAMS_OPTION_COLOR.keySet()));
/*     */             } else {
/* 448 */               sender.sendMessage(ChatColor.RED + "Valid values for option " + option + " are: true and false");
/*     */             }
/*     */           } else {
/* 451 */             String value = args[4].toLowerCase();
/* 452 */             if (option.equals("color")) {
/* 453 */               ChatColor color = (ChatColor)TEAMS_OPTION_COLOR.get(value);
/* 454 */               if (color == null) {
/* 455 */                 sender.sendMessage(ChatColor.RED + "Valid values for option color are: " + stringCollectionToString(TEAMS_OPTION_COLOR.keySet()));
/* 456 */                 return false;
/*     */               }
/* 458 */               team.setPrefix(color.toString());
/* 459 */               team.setSuffix(ChatColor.RESET.toString());
/*     */             } else {
/* 461 */               if ((!value.equals("true")) && (!value.equals("false"))) {
/* 462 */                 sender.sendMessage(ChatColor.RED + "Valid values for option " + option + " are: true and false");
/* 463 */                 return false;
/*     */               }
/* 465 */               if (option.equals("friendlyfire")) {
/* 466 */                 team.setAllowFriendlyFire(value.equals("true"));
/*     */               } else {
/* 468 */                 team.setCanSeeFriendlyInvisibles(value.equals("true"));
/*     */               }
/*     */             }
/* 471 */             sender.sendMessage("Set option " + option + " for team " + team.getName() + " to " + value);
/*     */           }
/*     */         }
/*     */       } else {
/* 475 */         sender.sendMessage(ChatColor.RED + "Usage: /scoreboard <objectives|players|teams>");
/* 476 */         return false;
/*     */       } }
/* 478 */     return true;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
/*     */   {
/* 483 */     Validate.notNull(sender, "Sender cannot be null");
/* 484 */     Validate.notNull(args, "Arguments cannot be null");
/* 485 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 487 */     if (args.length == 1) {
/* 488 */       return (List)StringUtil.copyPartialMatches(args[0], MAIN_CHOICES, new ArrayList());
/*     */     }
/* 490 */     if (args.length > 1) {
/* 491 */       if (args[0].equalsIgnoreCase("objectives")) {
/* 492 */         if (args.length == 2) {
/* 493 */           return (List)StringUtil.copyPartialMatches(args[1], OBJECTIVES_CHOICES, new ArrayList());
/*     */         }
/* 495 */         if (args[1].equalsIgnoreCase("add")) {
/* 496 */           if (args.length == 4) {
/* 497 */             return (List)StringUtil.copyPartialMatches(args[3], OBJECTIVES_CRITERIA, new ArrayList());
/*     */           }
/* 499 */         } else if (args[1].equalsIgnoreCase("remove")) {
/* 500 */           if (args.length == 3) {
/* 501 */             return (List)StringUtil.copyPartialMatches(args[2], getCurrentObjectives(), new ArrayList());
/*     */           }
/* 503 */         } else if (args[1].equalsIgnoreCase("setdisplay")) {
/* 504 */           if (args.length == 3) {
/* 505 */             return (List)StringUtil.copyPartialMatches(args[2], OBJECTIVES_DISPLAYSLOT.keySet(), new ArrayList());
/*     */           }
/* 507 */           if (args.length == 4) {
/* 508 */             return (List)StringUtil.copyPartialMatches(args[3], getCurrentObjectives(), new ArrayList());
/*     */           }
/*     */         }
/* 511 */       } else if (args[0].equalsIgnoreCase("players")) {
/* 512 */         if (args.length == 2) {
/* 513 */           return (List)StringUtil.copyPartialMatches(args[1], PLAYERS_CHOICES, new ArrayList());
/*     */         }
/* 515 */         if ((args[1].equalsIgnoreCase("set")) || (args[1].equalsIgnoreCase("add")) || (args[1].equalsIgnoreCase("remove"))) {
/* 516 */           if (args.length == 3) {
/* 517 */             return super.tabComplete(sender, alias, args);
/*     */           }
/* 519 */           if (args.length == 4) {
/* 520 */             return (List)StringUtil.copyPartialMatches(args[3], getCurrentObjectives(), new ArrayList());
/*     */           }
/*     */         }
/* 523 */         else if (args.length == 3) {
/* 524 */           return (List)StringUtil.copyPartialMatches(args[2], getCurrentEntries(), new ArrayList());
/*     */         }
/*     */       }
/* 527 */       else if (args[0].equalsIgnoreCase("teams")) {
/* 528 */         if (args.length == 2) {
/* 529 */           return (List)StringUtil.copyPartialMatches(args[1], TEAMS_CHOICES, new ArrayList());
/*     */         }
/* 531 */         if (args[1].equalsIgnoreCase("join")) {
/* 532 */           if (args.length == 3) {
/* 533 */             return (List)StringUtil.copyPartialMatches(args[2], getCurrentTeams(), new ArrayList());
/*     */           }
/* 535 */           if (args.length >= 4)
/* 536 */             return super.tabComplete(sender, alias, args);
/*     */         } else {
/* 538 */           if (args[1].equalsIgnoreCase("leave"))
/* 539 */             return super.tabComplete(sender, alias, args);
/* 540 */           if (args[1].equalsIgnoreCase("option")) {
/* 541 */             if (args.length == 3) {
/* 542 */               return (List)StringUtil.copyPartialMatches(args[2], getCurrentTeams(), new ArrayList());
/*     */             }
/* 544 */             if (args.length == 4) {
/* 545 */               return (List)StringUtil.copyPartialMatches(args[3], TEAMS_OPTION_CHOICES, new ArrayList());
/*     */             }
/* 547 */             if (args.length == 5) {
/* 548 */               if (args[3].equalsIgnoreCase("color")) {
/* 549 */                 return (List)StringUtil.copyPartialMatches(args[4], TEAMS_OPTION_COLOR.keySet(), new ArrayList());
/*     */               }
/* 551 */               return (List)StringUtil.copyPartialMatches(args[4], BOOLEAN, new ArrayList());
/*     */             }
/*     */             
/*     */           }
/* 555 */           else if (args.length == 3) {
/* 556 */             return (List)StringUtil.copyPartialMatches(args[2], getCurrentTeams(), new ArrayList());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 562 */     return ImmutableList.of();
/*     */   }
/*     */   
/*     */   private static String offlinePlayerSetToString(Set<OfflinePlayer> set) {
/* 566 */     StringBuilder string = new StringBuilder();
/* 567 */     String lastValue = null;
/* 568 */     for (OfflinePlayer value : set) {
/* 569 */       string.append(lastValue = value.getName()).append(", ");
/*     */     }
/* 571 */     string.delete(string.length() - 2, Integer.MAX_VALUE);
/* 572 */     if (string.length() != lastValue.length()) {
/* 573 */       string.insert(string.length() - lastValue.length(), "and ");
/*     */     }
/* 575 */     return string.toString();
/*     */   }
/*     */   
/*     */   private static String stringCollectionToString(Collection<String> set)
/*     */   {
/* 580 */     StringBuilder string = new StringBuilder();
/* 581 */     String lastValue = null;
/* 582 */     for (String value : set) {
/* 583 */       string.append(lastValue = value).append(", ");
/*     */     }
/* 585 */     string.delete(string.length() - 2, Integer.MAX_VALUE);
/* 586 */     if (string.length() != lastValue.length()) {
/* 587 */       string.insert(string.length() - lastValue.length(), "and ");
/*     */     }
/* 589 */     return string.toString();
/*     */   }
/*     */   
/*     */   private List<String> getCurrentObjectives() {
/* 593 */     List<String> list = new ArrayList();
/* 594 */     for (Objective objective : Bukkit.getScoreboardManager().getMainScoreboard().getObjectives()) {
/* 595 */       list.add(objective.getName());
/*     */     }
/* 597 */     Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
/* 598 */     return list;
/*     */   }
/*     */   
/*     */   private List<String> getCurrentEntries() {
/* 602 */     List<String> list = new ArrayList();
/* 603 */     for (String entry : Bukkit.getScoreboardManager().getMainScoreboard().getEntries()) {
/* 604 */       list.add(entry);
/*     */     }
/* 606 */     Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
/* 607 */     return list;
/*     */   }
/*     */   
/*     */   private List<String> getCurrentTeams() {
/* 611 */     List<String> list = new ArrayList();
/* 612 */     for (Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
/* 613 */       list.add(team.getName());
/*     */     }
/* 615 */     Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
/* 616 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ScoreboardCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */