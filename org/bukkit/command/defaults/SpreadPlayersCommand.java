/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scoreboard.Scoreboard;
/*     */ import org.bukkit.scoreboard.Team;
/*     */ 
/*     */ @Deprecated
/*     */ public class SpreadPlayersCommand extends VanillaCommand
/*     */ {
/*  21 */   private static final Random random = new Random();
/*     */   
/*     */   public SpreadPlayersCommand() {
/*  24 */     super("spreadplayers");
/*  25 */     this.description = "Spreads players around a point";
/*  26 */     this.usageMessage = "/spreadplayers <x> <z> <spreadDistance> <maxRange> <respectTeams true|false> <player ...>";
/*  27 */     setPermission("bukkit.command.spreadplayers");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String commandLabel, String[] args)
/*     */   {
/*  32 */     if (!testPermission(sender)) {
/*  33 */       return true;
/*     */     }
/*     */     
/*  36 */     if (args.length < 6) {
/*  37 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  38 */       return false;
/*     */     }
/*     */     
/*  41 */     double x = getDouble(sender, args[0], -3.0E7D, 3.0E7D);
/*  42 */     double z = getDouble(sender, args[1], -3.0E7D, 3.0E7D);
/*  43 */     double distance = getDouble(sender, args[2]);
/*  44 */     double range = getDouble(sender, args[3]);
/*     */     
/*  46 */     if (distance < 0.0D) {
/*  47 */       sender.sendMessage(ChatColor.RED + "Distance is too small.");
/*  48 */       return false;
/*     */     }
/*     */     
/*  51 */     if (range < distance + 1.0D) {
/*  52 */       sender.sendMessage(ChatColor.RED + "Max range is too small.");
/*  53 */       return false;
/*     */     }
/*     */     
/*  56 */     String respectTeams = args[4];
/*  57 */     boolean teams = false;
/*     */     
/*  59 */     if (respectTeams.equalsIgnoreCase("true")) {
/*  60 */       teams = true;
/*  61 */     } else if (!respectTeams.equalsIgnoreCase("false")) {
/*  62 */       sender.sendMessage(String.format(ChatColor.RED + "'%s' is not true or false", new Object[] { args[4] }));
/*  63 */       return false;
/*     */     }
/*     */     
/*  66 */     List<Player> players = Lists.newArrayList();
/*  67 */     World world = null;
/*     */     
/*  69 */     for (int i = 5; i < args.length; i++) {
/*  70 */       Player player = org.bukkit.Bukkit.getPlayerExact(args[i]);
/*  71 */       if (player != null)
/*     */       {
/*     */ 
/*     */ 
/*  75 */         if (world == null) {
/*  76 */           world = player.getWorld();
/*     */         }
/*  78 */         players.add(player);
/*     */       }
/*     */     }
/*  81 */     if (world == null) {
/*  82 */       return true;
/*     */     }
/*     */     
/*  85 */     double xRangeMin = x - range;
/*  86 */     double zRangeMin = z - range;
/*  87 */     double xRangeMax = x + range;
/*  88 */     double zRangeMax = z + range;
/*     */     
/*  90 */     int spreadSize = teams ? getTeams(players) : players.size();
/*     */     
/*  92 */     Location[] locations = getSpreadLocations(world, spreadSize, xRangeMin, zRangeMin, xRangeMax, zRangeMax);
/*  93 */     int rangeSpread = range(world, distance, xRangeMin, zRangeMin, xRangeMax, zRangeMax, locations);
/*     */     
/*  95 */     if (rangeSpread == -1) {
/*  96 */       sender.sendMessage(String.format("Could not spread %d %s around %s,%s (too many players for space - try using spread of at most %s)", new Object[] { Integer.valueOf(spreadSize), teams ? "teams" : "players", Double.valueOf(x), Double.valueOf(z) }));
/*  97 */       return false;
/*     */     }
/*     */     
/* 100 */     double distanceSpread = spread(world, players, locations, teams);
/*     */     
/* 102 */     sender.sendMessage(String.format("Succesfully spread %d %s around %s,%s", new Object[] { Integer.valueOf(locations.length), teams ? "teams" : "players", Double.valueOf(x), Double.valueOf(z) }));
/* 103 */     if (locations.length > 1) {
/* 104 */       sender.sendMessage(String.format("(Average distance between %s is %s blocks apart after %s iterations)", new Object[] { teams ? "teams" : "players", String.format("%.2f", new Object[] { Double.valueOf(distanceSpread) }), Integer.valueOf(rangeSpread) }));
/*     */     }
/* 106 */     return true;
/*     */   }
/*     */   
/*     */   private int range(World world, double distance, double xRangeMin, double zRangeMin, double xRangeMax, double zRangeMax, Location[] locations) {
/* 110 */     boolean flag = true;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 115 */     for (int i = 0; (i < 10000) && (flag); i++) {
/* 116 */       flag = false;
/* 117 */       double max = 3.4028234663852886E38D;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 122 */       for (int k = 0; k < locations.length; k++) {
/* 123 */         Location loc2 = locations[k];
/*     */         
/* 125 */         int j = 0;
/* 126 */         Location loc1 = new Location(world, 0.0D, 0.0D, 0.0D);
/*     */         
/* 128 */         for (int l = 0; l < locations.length; l++) {
/* 129 */           if (k != l) {
/* 130 */             Location loc3 = locations[l];
/* 131 */             double dis = loc2.distanceSquared(loc3);
/*     */             
/* 133 */             max = Math.min(dis, max);
/* 134 */             if (dis < distance) {
/* 135 */               j++;
/* 136 */               loc1.add(loc3.getX() - loc2.getX(), 0.0D, 0.0D);
/* 137 */               loc1.add(loc3.getZ() - loc2.getZ(), 0.0D, 0.0D);
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 142 */         if (j > 0) {
/* 143 */           loc2.setX(loc2.getX() / j);
/* 144 */           loc2.setZ(loc2.getZ() / j);
/* 145 */           double d7 = Math.sqrt(loc1.getX() * loc1.getX() + loc1.getZ() * loc1.getZ());
/*     */           
/* 147 */           if (d7 > 0.0D) {
/* 148 */             loc1.setX(loc1.getX() / d7);
/* 149 */             loc2.add(-loc1.getX(), 0.0D, -loc1.getZ());
/*     */           } else {
/* 151 */             double x = xRangeMin >= xRangeMax ? xRangeMin : random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin;
/* 152 */             double z = zRangeMin >= zRangeMax ? zRangeMin : random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin;
/* 153 */             loc2.setX(x);
/* 154 */             loc2.setZ(z);
/*     */           }
/*     */           
/* 157 */           flag = true;
/*     */         }
/*     */         
/* 160 */         boolean swap = false;
/*     */         
/* 162 */         if (loc2.getX() < xRangeMin) {
/* 163 */           loc2.setX(xRangeMin);
/* 164 */           swap = true;
/* 165 */         } else if (loc2.getX() > xRangeMax) {
/* 166 */           loc2.setX(xRangeMax);
/* 167 */           swap = true;
/*     */         }
/*     */         
/* 170 */         if (loc2.getZ() < zRangeMin) {
/* 171 */           loc2.setZ(zRangeMin);
/* 172 */           swap = true;
/* 173 */         } else if (loc2.getZ() > zRangeMax) {
/* 174 */           loc2.setZ(zRangeMax);
/* 175 */           swap = true;
/*     */         }
/* 177 */         if (swap) {
/* 178 */           flag = true;
/*     */         }
/*     */       }
/*     */       
/* 182 */       if (!flag) {
/* 183 */         Location[] locs = locations;
/* 184 */         int i1 = locations.length;
/*     */         
/* 186 */         for (int j = 0; j < i1; j++) {
/* 187 */           Location loc1 = locs[j];
/* 188 */           if (world.getHighestBlockYAt(loc1) == 0) {
/* 189 */             double x = xRangeMin >= xRangeMax ? xRangeMin : random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin;
/* 190 */             double z = zRangeMin >= zRangeMax ? zRangeMin : random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin;
/* 191 */             locations[i] = new Location(world, x, 0.0D, z);
/* 192 */             loc1.setX(x);
/* 193 */             loc1.setZ(z);
/* 194 */             flag = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 200 */     if (i >= 10000) {
/* 201 */       return -1;
/*     */     }
/* 203 */     return i;
/*     */   }
/*     */   
/*     */   private double spread(World world, List<Player> list, Location[] locations, boolean teams)
/*     */   {
/* 208 */     double distance = 0.0D;
/* 209 */     int i = 0;
/* 210 */     Map<Team, Location> hashmap = Maps.newHashMap();
/*     */     
/* 212 */     for (int j = 0; j < list.size(); j++) {
/* 213 */       Player player = (Player)list.get(j);
/*     */       Location location;
/*     */       Location location;
/* 216 */       if (teams) {
/* 217 */         Team team = player.getScoreboard().getPlayerTeam(player);
/*     */         
/* 219 */         if (!hashmap.containsKey(team)) {
/* 220 */           hashmap.put(team, locations[(i++)]);
/*     */         }
/*     */         
/* 223 */         location = (Location)hashmap.get(team);
/*     */       } else {
/* 225 */         location = locations[(i++)];
/*     */       }
/*     */       
/* 228 */       player.teleport(new Location(world, Math.floor(location.getX()) + 0.5D, world.getHighestBlockYAt((int)location.getX(), (int)location.getZ()), Math.floor(location.getZ()) + 0.5D));
/* 229 */       double value = Double.MAX_VALUE;
/*     */       
/* 231 */       for (int k = 0; k < locations.length; k++) {
/* 232 */         if (location != locations[k]) {
/* 233 */           double d = location.distanceSquared(locations[k]);
/* 234 */           value = Math.min(d, value);
/*     */         }
/*     */       }
/*     */       
/* 238 */       distance += value;
/*     */     }
/*     */     
/* 241 */     distance /= list.size();
/* 242 */     return distance;
/*     */   }
/*     */   
/*     */   private int getTeams(List<Player> players) {
/* 246 */     Set<Team> teams = Sets.newHashSet();
/*     */     
/* 248 */     for (Player player : players) {
/* 249 */       teams.add(player.getScoreboard().getPlayerTeam(player));
/*     */     }
/*     */     
/* 252 */     return teams.size();
/*     */   }
/*     */   
/*     */   private Location[] getSpreadLocations(World world, int size, double xRangeMin, double zRangeMin, double xRangeMax, double zRangeMax) {
/* 256 */     Location[] locations = new Location[size];
/*     */     
/* 258 */     for (int i = 0; i < size; i++) {
/* 259 */       double x = xRangeMin >= xRangeMax ? xRangeMin : random.nextDouble() * (xRangeMax - xRangeMin) + xRangeMin;
/* 260 */       double z = zRangeMin >= zRangeMax ? zRangeMin : random.nextDouble() * (zRangeMax - zRangeMin) + zRangeMin;
/* 261 */       locations[i] = new Location(world, x, 0.0D, z);
/*     */     }
/*     */     
/* 264 */     return locations;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SpreadPlayersCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */