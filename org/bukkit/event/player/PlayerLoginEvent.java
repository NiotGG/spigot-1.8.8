/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerLoginEvent
/*     */   extends PlayerEvent
/*     */ {
/*  12 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final InetAddress address;
/*     */   private final String hostname;
/*  15 */   private Result result = Result.ALLOWED;
/*  16 */   private String message = "";
/*     */   
/*     */ 
/*     */   private final InetAddress realAddress;
/*     */   
/*     */ 
/*     */   @Deprecated
/*     */   public PlayerLoginEvent(Player player)
/*     */   {
/*  25 */     this(player, "", null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public PlayerLoginEvent(Player player, String hostname)
/*     */   {
/*  35 */     this(player, hostname, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PlayerLoginEvent(Player player, String hostname, InetAddress address, InetAddress realAddress)
/*     */   {
/*  48 */     super(player);
/*  49 */     this.hostname = hostname;
/*  50 */     this.address = address;
/*     */     
/*  52 */     this.realAddress = realAddress;
/*     */   }
/*     */   
/*     */   public PlayerLoginEvent(Player player, String hostname, InetAddress address) {
/*  56 */     this(player, hostname, address, address);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public PlayerLoginEvent(Player player, Result result, String message)
/*     */   {
/*  69 */     this(player, "", null, result, message, null);
/*     */   }
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
/*     */   public PlayerLoginEvent(Player player, String hostname, InetAddress address, Result result, String message, InetAddress realAddress)
/*     */   {
/*  83 */     this(player, hostname, address, realAddress);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InetAddress getRealAddress()
/*     */   {
/*  95 */     return this.realAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Result getResult()
/*     */   {
/* 105 */     return this.result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setResult(Result result)
/*     */   {
/* 114 */     this.result = result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getKickMessage()
/*     */   {
/* 124 */     return this.message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setKickMessage(String message)
/*     */   {
/* 133 */     this.message = message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getHostname()
/*     */   {
/* 143 */     return this.hostname;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void allow()
/*     */   {
/* 150 */     this.result = Result.ALLOWED;
/* 151 */     this.message = "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void disallow(Result result, String message)
/*     */   {
/* 161 */     this.result = result;
/* 162 */     this.message = message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InetAddress getAddress()
/*     */   {
/* 174 */     return this.address;
/*     */   }
/*     */   
/*     */   public HandlerList getHandlers()
/*     */   {
/* 179 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 183 */     return handlers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum Result
/*     */   {
/* 191 */     ALLOWED, 
/*     */     
/*     */ 
/*     */ 
/* 195 */     KICK_FULL, 
/*     */     
/*     */ 
/*     */ 
/* 199 */     KICK_BANNED, 
/*     */     
/*     */ 
/*     */ 
/* 203 */     KICK_WHITELIST, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 208 */     KICK_OTHER;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerLoginEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */