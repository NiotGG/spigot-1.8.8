/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class CommandSpreadPlayers
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  18 */     return "spreadplayers";
/*     */   }
/*     */   
/*     */   public int a() {
/*  22 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener icommandlistener) {
/*  26 */     return "commands.spreadplayers.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener icommandlistener, String[] astring) throws CommandException {
/*  30 */     if (astring.length < 6) {
/*  31 */       throw new ExceptionUsage("commands.spreadplayers.usage", new Object[0]);
/*     */     }
/*  33 */     byte b0 = 0;
/*  34 */     BlockPosition blockposition = icommandlistener.getChunkCoordinates();
/*  35 */     double d0 = blockposition.getX();
/*  36 */     int i = b0 + 1;
/*  37 */     double d1 = b(d0, astring[b0], true);
/*  38 */     double d2 = b(blockposition.getZ(), astring[(i++)], true);
/*  39 */     double d3 = a(astring[(i++)], 0.0D);
/*  40 */     double d4 = a(astring[(i++)], d3 + 1.0D);
/*  41 */     boolean flag = d(astring[(i++)]);
/*  42 */     ArrayList arraylist = Lists.newArrayList();
/*     */     
/*  44 */     while (i < astring.length) {
/*  45 */       String s = astring[(i++)];
/*     */       
/*  47 */       if (PlayerSelector.isPattern(s)) {
/*  48 */         List list = PlayerSelector.getPlayers(icommandlistener, s, Entity.class);
/*     */         
/*  50 */         if (list.size() == 0) {
/*  51 */           throw new ExceptionEntityNotFound();
/*     */         }
/*     */         
/*  54 */         arraylist.addAll(list);
/*     */       } else {
/*  56 */         EntityPlayer entityplayer = MinecraftServer.getServer().getPlayerList().getPlayer(s);
/*     */         
/*  58 */         if (entityplayer == null) {
/*  59 */           throw new ExceptionPlayerNotFound();
/*     */         }
/*     */         
/*  62 */         arraylist.add(entityplayer);
/*     */       }
/*     */     }
/*     */     
/*  66 */     icommandlistener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, arraylist.size());
/*  67 */     if (arraylist.isEmpty()) {
/*  68 */       throw new ExceptionEntityNotFound();
/*     */     }
/*  70 */     icommandlistener.sendMessage(new ChatMessage("commands.spreadplayers.spreading." + (flag ? "teams" : "players"), new Object[] { Integer.valueOf(arraylist.size()), Double.valueOf(d4), Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3) }));
/*  71 */     a(icommandlistener, arraylist, new Location2D(d1, d2), d3, d4, ((Entity)arraylist.get(0)).world, flag);
/*     */   }
/*     */   
/*     */   private void a(ICommandListener icommandlistener, List<Entity> list, Location2D commandspreadplayers_location2d, double d0, double d1, World world, boolean flag)
/*     */     throws CommandException
/*     */   {
/*  77 */     Random random = new Random();
/*  78 */     double d2 = commandspreadplayers_location2d.a - d1;
/*  79 */     double d3 = commandspreadplayers_location2d.b - d1;
/*  80 */     double d4 = commandspreadplayers_location2d.a + d1;
/*  81 */     double d5 = commandspreadplayers_location2d.b + d1;
/*  82 */     Location2D[] acommandspreadplayers_location2d = a(random, flag ? b(list) : list.size(), d2, d3, d4, d5);
/*  83 */     int i = a(commandspreadplayers_location2d, d0, world, random, d2, d3, d4, d5, acommandspreadplayers_location2d, flag);
/*  84 */     double d6 = a(list, world, acommandspreadplayers_location2d, flag);
/*     */     
/*  86 */     a(icommandlistener, this, "commands.spreadplayers.success." + (flag ? "teams" : "players"), new Object[] { Integer.valueOf(acommandspreadplayers_location2d.length), Double.valueOf(commandspreadplayers_location2d.a), Double.valueOf(commandspreadplayers_location2d.b) });
/*  87 */     if (acommandspreadplayers_location2d.length > 1) {
/*  88 */       icommandlistener.sendMessage(new ChatMessage("commands.spreadplayers.info." + (flag ? "teams" : "players"), new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d6) }), Integer.valueOf(i) }));
/*     */     }
/*     */   }
/*     */   
/*     */   private int b(List<Entity> list)
/*     */   {
/*  94 */     HashSet hashset = Sets.newHashSet();
/*  95 */     Iterator iterator = list.iterator();
/*     */     
/*  97 */     while (iterator.hasNext()) {
/*  98 */       Entity entity = (Entity)iterator.next();
/*     */       
/* 100 */       if ((entity instanceof EntityHuman)) {
/* 101 */         hashset.add(((EntityHuman)entity).getScoreboardTeam());
/*     */       } else {
/* 103 */         hashset.add(null);
/*     */       }
/*     */     }
/*     */     
/* 107 */     return hashset.size();
/*     */   }
/*     */   
/*     */   private int a(Location2D commandspreadplayers_location2d, double d0, World world, Random random, double d1, double d2, double d3, double d4, Location2D[] acommandspreadplayers_location2d, boolean flag) throws CommandException {
/* 111 */     boolean flag1 = true;
/* 112 */     double d5 = 3.4028234663852886E38D;
/*     */     
/*     */ 
/*     */ 
/* 116 */     for (int i = 0; (i < 10000) && (flag1); i++) {
/* 117 */       flag1 = false;
/* 118 */       d5 = 3.4028234663852886E38D;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 123 */       for (int k = 0; k < acommandspreadplayers_location2d.length; k++) {
/* 124 */         Location2D commandspreadplayers_location2d2 = acommandspreadplayers_location2d[k];
/*     */         
/* 126 */         int j = 0;
/* 127 */         Location2D commandspreadplayers_location2d1 = new Location2D();
/*     */         
/* 129 */         for (int l = 0; l < acommandspreadplayers_location2d.length; l++) {
/* 130 */           if (k != l) {
/* 131 */             Location2D commandspreadplayers_location2d3 = acommandspreadplayers_location2d[l];
/* 132 */             double d6 = commandspreadplayers_location2d2.a(commandspreadplayers_location2d3);
/*     */             
/* 134 */             d5 = Math.min(d6, d5);
/* 135 */             if (d6 < d0) {
/* 136 */               j++;
/* 137 */               commandspreadplayers_location2d1.a += commandspreadplayers_location2d3.a - commandspreadplayers_location2d2.a;
/* 138 */               commandspreadplayers_location2d1.b += commandspreadplayers_location2d3.b - commandspreadplayers_location2d2.b;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 143 */         if (j > 0) {
/* 144 */           commandspreadplayers_location2d1.a /= j;
/* 145 */           commandspreadplayers_location2d1.b /= j;
/* 146 */           double d7 = commandspreadplayers_location2d1.b();
/*     */           
/* 148 */           if (d7 > 0.0D) {
/* 149 */             commandspreadplayers_location2d1.a();
/* 150 */             commandspreadplayers_location2d2.b(commandspreadplayers_location2d1);
/*     */           } else {
/* 152 */             commandspreadplayers_location2d2.a(random, d1, d2, d3, d4);
/*     */           }
/*     */           
/* 155 */           flag1 = true;
/*     */         }
/*     */         
/* 158 */         if (commandspreadplayers_location2d2.a(d1, d2, d3, d4)) {
/* 159 */           flag1 = true;
/*     */         }
/*     */       }
/*     */       
/* 163 */       if (!flag1) {
/* 164 */         Location2D[] acommandspreadplayers_location2d1 = acommandspreadplayers_location2d;
/* 165 */         int i1 = acommandspreadplayers_location2d.length;
/*     */         
/* 167 */         for (int j = 0; j < i1; j++) {
/* 168 */           Location2D commandspreadplayers_location2d1 = acommandspreadplayers_location2d1[j];
/* 169 */           if (!commandspreadplayers_location2d1.b(world)) {
/* 170 */             commandspreadplayers_location2d1.a(random, d1, d2, d3, d4);
/* 171 */             flag1 = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 177 */     if (i >= 10000) {
/* 178 */       throw new CommandException("commands.spreadplayers.failure." + (flag ? "teams" : "players"), new Object[] { Integer.valueOf(acommandspreadplayers_location2d.length), Double.valueOf(commandspreadplayers_location2d.a), Double.valueOf(commandspreadplayers_location2d.b), String.format("%.2f", new Object[] { Double.valueOf(d5) }) });
/*     */     }
/* 180 */     return i;
/*     */   }
/*     */   
/*     */   private double a(List<Entity> list, World world, Location2D[] acommandspreadplayers_location2d, boolean flag)
/*     */   {
/* 185 */     double d0 = 0.0D;
/* 186 */     int i = 0;
/* 187 */     HashMap hashmap = Maps.newHashMap();
/*     */     
/* 189 */     for (int j = 0; j < list.size(); j++) {
/* 190 */       Entity entity = (Entity)list.get(j);
/*     */       Location2D commandspreadplayers_location2d;
/*     */       Location2D commandspreadplayers_location2d;
/* 193 */       if (flag) {
/* 194 */         ScoreboardTeamBase scoreboardteambase = (entity instanceof EntityHuman) ? ((EntityHuman)entity).getScoreboardTeam() : null;
/*     */         
/* 196 */         if (!hashmap.containsKey(scoreboardteambase)) {
/* 197 */           hashmap.put(scoreboardteambase, acommandspreadplayers_location2d[(i++)]);
/*     */         }
/*     */         
/* 200 */         commandspreadplayers_location2d = (Location2D)hashmap.get(scoreboardteambase);
/*     */       } else {
/* 202 */         commandspreadplayers_location2d = acommandspreadplayers_location2d[(i++)];
/*     */       }
/*     */       
/* 205 */       entity.enderTeleportTo(MathHelper.floor(commandspreadplayers_location2d.a) + 0.5F, commandspreadplayers_location2d.a(world), MathHelper.floor(commandspreadplayers_location2d.b) + 0.5D);
/* 206 */       double d1 = Double.MAX_VALUE;
/*     */       
/* 208 */       for (int k = 0; k < acommandspreadplayers_location2d.length; k++) {
/* 209 */         if (commandspreadplayers_location2d != acommandspreadplayers_location2d[k]) {
/* 210 */           double d2 = commandspreadplayers_location2d.a(acommandspreadplayers_location2d[k]);
/*     */           
/* 212 */           d1 = Math.min(d2, d1);
/*     */         }
/*     */       }
/*     */       
/* 216 */       d0 += d1;
/*     */     }
/*     */     
/* 219 */     d0 /= list.size();
/* 220 */     return d0;
/*     */   }
/*     */   
/*     */   private Location2D[] a(Random random, int i, double d0, double d1, double d2, double d3) {
/* 224 */     Location2D[] acommandspreadplayers_location2d = new Location2D[i];
/*     */     
/* 226 */     for (int j = 0; j < acommandspreadplayers_location2d.length; j++) {
/* 227 */       Location2D commandspreadplayers_location2d = new Location2D();
/*     */       
/* 229 */       commandspreadplayers_location2d.a(random, d0, d1, d2, d3);
/* 230 */       acommandspreadplayers_location2d[j] = commandspreadplayers_location2d;
/*     */     }
/*     */     
/* 233 */     return acommandspreadplayers_location2d;
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(ICommandListener icommandlistener, String[] astring, BlockPosition blockposition) {
/* 237 */     return (astring.length >= 1) && (astring.length <= 2) ? b(astring, 0, blockposition) : null;
/*     */   }
/*     */   
/*     */ 
/*     */   public int compareTo(ICommand o)
/*     */   {
/* 243 */     return a(o);
/*     */   }
/*     */   
/*     */   static class Location2D
/*     */   {
/*     */     double a;
/*     */     double b;
/*     */     
/*     */     Location2D() {}
/*     */     
/*     */     Location2D(double d0, double d1)
/*     */     {
/* 255 */       this.a = d0;
/* 256 */       this.b = d1;
/*     */     }
/*     */     
/*     */     double a(Location2D commandspreadplayers_location2d) {
/* 260 */       double d0 = this.a - commandspreadplayers_location2d.a;
/* 261 */       double d1 = this.b - commandspreadplayers_location2d.b;
/*     */       
/* 263 */       return Math.sqrt(d0 * d0 + d1 * d1);
/*     */     }
/*     */     
/*     */     void a() {
/* 267 */       double d0 = b();
/*     */       
/* 269 */       this.a /= d0;
/* 270 */       this.b /= d0;
/*     */     }
/*     */     
/*     */     float b() {
/* 274 */       return MathHelper.sqrt(this.a * this.a + this.b * this.b);
/*     */     }
/*     */     
/*     */     public void b(Location2D commandspreadplayers_location2d) {
/* 278 */       this.a -= commandspreadplayers_location2d.a;
/* 279 */       this.b -= commandspreadplayers_location2d.b;
/*     */     }
/*     */     
/*     */     public boolean a(double d0, double d1, double d2, double d3) {
/* 283 */       boolean flag = false;
/*     */       
/* 285 */       if (this.a < d0) {
/* 286 */         this.a = d0;
/* 287 */         flag = true;
/* 288 */       } else if (this.a > d2) {
/* 289 */         this.a = d2;
/* 290 */         flag = true;
/*     */       }
/*     */       
/* 293 */       if (this.b < d1) {
/* 294 */         this.b = d1;
/* 295 */         flag = true;
/* 296 */       } else if (this.b > d3) {
/* 297 */         this.b = d3;
/* 298 */         flag = true;
/*     */       }
/*     */       
/* 301 */       return flag;
/*     */     }
/*     */     
/*     */     public int a(World world) {
/* 305 */       BlockPosition blockposition = new BlockPosition(this.a, 256.0D, this.b);
/*     */       do
/*     */       {
/* 308 */         if (blockposition.getY() <= 0) {
/* 309 */           return 257;
/*     */         }
/*     */         
/* 312 */         blockposition = blockposition.down();
/* 313 */       } while (getType(world, blockposition).getBlock().getMaterial() == Material.AIR);
/*     */       
/* 315 */       return blockposition.getY() + 1;
/*     */     }
/*     */     
/*     */     public boolean b(World world) {
/* 319 */       BlockPosition blockposition = new BlockPosition(this.a, 256.0D, this.b);
/*     */       
/*     */       Material material;
/*     */       do
/*     */       {
/* 324 */         if (blockposition.getY() <= 0) {
/* 325 */           return false;
/*     */         }
/*     */         
/* 328 */         blockposition = blockposition.down();
/* 329 */         material = getType(world, blockposition).getBlock().getMaterial();
/* 330 */       } while (material == Material.AIR);
/*     */       
/* 332 */       return (!material.isLiquid()) && (material != Material.FIRE);
/*     */     }
/*     */     
/*     */     public void a(Random random, double d0, double d1, double d2, double d3) {
/* 336 */       this.a = MathHelper.a(random, d0, d2);
/* 337 */       this.b = MathHelper.a(random, d1, d3);
/*     */     }
/*     */     
/*     */     private static IBlockData getType(World world, BlockPosition position)
/*     */     {
/* 342 */       ((ChunkProviderServer)world.chunkProvider).getChunkAt(position.getX() >> 4, position.getZ() >> 4);
/* 343 */       return world.getType(position);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandSpreadPlayers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */