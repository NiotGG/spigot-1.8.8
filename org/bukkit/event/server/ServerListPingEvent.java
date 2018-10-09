/*     */ package org.bukkit.event.server;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.util.CachedServerIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerListPingEvent
/*     */   extends ServerEvent
/*     */   implements Iterable<Player>
/*     */ {
/*     */   private static final int MAGIC_PLAYER_COUNT = Integer.MIN_VALUE;
/*  17 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final InetAddress address;
/*     */   private String motd;
/*     */   private final int numPlayers;
/*     */   private int maxPlayers;
/*     */   
/*     */   public ServerListPingEvent(InetAddress address, String motd, int numPlayers, int maxPlayers) {
/*  24 */     Validate.isTrue(numPlayers >= 0, "Cannot have negative number of players online", numPlayers);
/*  25 */     this.address = address;
/*  26 */     this.motd = motd;
/*  27 */     this.numPlayers = numPlayers;
/*  28 */     this.maxPlayers = maxPlayers;
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
/*     */   protected ServerListPingEvent(InetAddress address, String motd, int maxPlayers)
/*     */   {
/*  41 */     this.numPlayers = Integer.MIN_VALUE;
/*  42 */     this.address = address;
/*  43 */     this.motd = motd;
/*  44 */     this.maxPlayers = maxPlayers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InetAddress getAddress()
/*     */   {
/*  53 */     return this.address;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMotd()
/*     */   {
/*  62 */     return this.motd;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMotd(String motd)
/*     */   {
/*  71 */     this.motd = motd;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNumPlayers()
/*     */   {
/*  80 */     int numPlayers = this.numPlayers;
/*  81 */     if (numPlayers == Integer.MIN_VALUE) {
/*  82 */       numPlayers = 0;
/*  83 */       for (Iterator localIterator = iterator(); localIterator.hasNext();) { ((Player)localIterator.next());
/*  84 */         numPlayers++;
/*     */       }
/*     */     }
/*  87 */     return numPlayers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getMaxPlayers()
/*     */   {
/*  96 */     return this.maxPlayers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setMaxPlayers(int maxPlayers)
/*     */   {
/* 105 */     this.maxPlayers = maxPlayers;
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
/*     */   public void setServerIcon(CachedServerIcon icon)
/*     */     throws IllegalArgumentException, UnsupportedOperationException
/*     */   {
/* 119 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public HandlerList getHandlers()
/*     */   {
/* 124 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 128 */     return handlers;
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
/*     */ 
/*     */   public Iterator<Player> iterator()
/*     */     throws UnsupportedOperationException
/*     */   {
/* 144 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\ServerListPingEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */