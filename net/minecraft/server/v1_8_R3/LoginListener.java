/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.security.KeyPair;
/*     */ import java.security.PrivateKey;
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.Waitable;
/*     */ import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
/*     */ import org.bukkit.event.player.PlayerPreLoginEvent;
/*     */ import org.bukkit.event.player.PlayerPreLoginEvent.Result;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class LoginListener implements PacketLoginInListener, IUpdatePlayerListBox
/*     */ {
/*  30 */   private static final AtomicInteger b = new AtomicInteger(0);
/*  31 */   private static final org.apache.logging.log4j.Logger c = LogManager.getLogger();
/*  32 */   private static final Random random = new Random();
/*  33 */   private final byte[] e = new byte[4];
/*     */   private final MinecraftServer server;
/*     */   public final NetworkManager networkManager;
/*     */   private EnumProtocolState g;
/*     */   private int h;
/*     */   private GameProfile i;
/*     */   private String j;
/*     */   private javax.crypto.SecretKey loginKey;
/*     */   private EntityPlayer l;
/*  42 */   public String hostname = "";
/*     */   
/*     */   public LoginListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
/*  45 */     this.g = EnumProtocolState.HELLO;
/*  46 */     this.j = "";
/*  47 */     this.server = minecraftserver;
/*  48 */     this.networkManager = networkmanager;
/*  49 */     random.nextBytes(this.e);
/*     */   }
/*     */   
/*     */   public void c() {
/*  53 */     if (this.g == EnumProtocolState.READY_TO_ACCEPT) {
/*  54 */       b();
/*  55 */     } else if (this.g == EnumProtocolState.e) {
/*  56 */       EntityPlayer entityplayer = this.server.getPlayerList().a(this.i.getId());
/*     */       
/*  58 */       if (entityplayer == null) {
/*  59 */         this.g = EnumProtocolState.READY_TO_ACCEPT;
/*  60 */         this.server.getPlayerList().a(this.networkManager, this.l);
/*  61 */         this.l = null;
/*     */       }
/*     */     }
/*     */     
/*  65 */     if (this.h++ == 600) {
/*  66 */       disconnect("Took too long to log in");
/*     */     }
/*     */   }
/*     */   
/*     */   public void disconnect(String s)
/*     */   {
/*     */     try {
/*  73 */       c.info("Disconnecting " + d() + ": " + s);
/*  74 */       ChatComponentText chatcomponenttext = new ChatComponentText(s);
/*     */       
/*  76 */       this.networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
/*  77 */       this.networkManager.close(chatcomponenttext);
/*     */     } catch (Exception exception) {
/*  79 */       c.error("Error whilst disconnecting player", exception);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void initUUID()
/*     */   {
/*     */     UUID uuid;
/*     */     UUID uuid;
/*  88 */     if (this.networkManager.spoofedUUID != null)
/*     */     {
/*  90 */       uuid = this.networkManager.spoofedUUID;
/*     */     }
/*     */     else {
/*  93 */       uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.i.getName()).getBytes(Charsets.UTF_8));
/*     */     }
/*     */     
/*  96 */     this.i = new GameProfile(uuid, this.i.getName());
/*     */     
/*  98 */     if (this.networkManager.spoofedProfile != null) {
/*     */       Property[] arrayOfProperty;
/* 100 */       int k = (arrayOfProperty = this.networkManager.spoofedProfile).length; for (int m = 0; m < k; m++) { Property property = arrayOfProperty[m];
/*     */         
/* 102 */         this.i.getProperties().put(property.getName(), property);
/*     */       }
/*     */     }
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
/*     */   public void b()
/*     */   {
/* 118 */     EntityPlayer s = this.server.getPlayerList().attemptLogin(this, this.i, this.hostname);
/*     */     
/* 120 */     if (s != null)
/*     */     {
/*     */ 
/*     */ 
/* 124 */       this.g = EnumProtocolState.ACCEPTED;
/* 125 */       if ((this.server.aK() >= 0) && (!this.networkManager.c())) {
/* 126 */         this.networkManager.a(new PacketLoginOutSetCompression(this.server.aK()), new ChannelFutureListener() {
/*     */           public void a(ChannelFuture channelfuture) throws Exception {
/* 128 */             LoginListener.this.networkManager.a(LoginListener.this.server.aK());
/*     */           }
/*     */           
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/* 132 */             a(future);
/*     */           }
/* 134 */         }, new io.netty.util.concurrent.GenericFutureListener[0]);
/*     */       }
/*     */       
/* 137 */       this.networkManager.handle(new PacketLoginOutSuccess(this.i));
/* 138 */       EntityPlayer entityplayer = this.server.getPlayerList().a(this.i.getId());
/*     */       
/* 140 */       if (entityplayer != null) {
/* 141 */         this.g = EnumProtocolState.e;
/* 142 */         this.l = this.server.getPlayerList().processLogin(this.i, s);
/*     */       } else {
/* 144 */         this.server.getPlayerList().a(this.networkManager, this.server.getPlayerList().processLogin(this.i, s));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(IChatBaseComponent ichatbasecomponent)
/*     */   {
/* 151 */     c.info(d() + " lost connection: " + ichatbasecomponent.c());
/*     */   }
/*     */   
/*     */   public String d() {
/* 155 */     return this.i != null ? this.i.toString() + " (" + this.networkManager.getSocketAddress().toString() + ")" : String.valueOf(this.networkManager.getSocketAddress());
/*     */   }
/*     */   
/*     */   public void a(PacketLoginInStart packetlogininstart) {
/* 159 */     Validate.validState(this.g == EnumProtocolState.HELLO, "Unexpected hello packet", new Object[0]);
/* 160 */     this.i = packetlogininstart.a();
/* 161 */     if ((this.server.getOnlineMode()) && (!this.networkManager.c())) {
/* 162 */       this.g = EnumProtocolState.KEY;
/* 163 */       this.networkManager.handle(new PacketLoginOutEncryptionBegin(this.j, this.server.Q().getPublic(), this.e));
/*     */     }
/*     */     else {
/* 166 */       initUUID();
/* 167 */       new Thread(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           try {
/* 172 */             new LoginListener.LoginHandler(LoginListener.this).fireEvents();
/*     */           } catch (Exception ex) {
/* 174 */             LoginListener.this.disconnect("Failed to verify username!");
/* 175 */             LoginListener.this.server.server.getLogger().log(Level.WARNING, "Exception verifying " + LoginListener.this.i.getName(), ex);
/*     */           }
/*     */         }
/*     */       })
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
/* 178 */         .start();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(PacketLoginInEncryptionBegin packetlogininencryptionbegin)
/*     */   {
/* 185 */     Validate.validState(this.g == EnumProtocolState.KEY, "Unexpected key packet", new Object[0]);
/* 186 */     PrivateKey privatekey = this.server.Q().getPrivate();
/*     */     
/* 188 */     if (!Arrays.equals(this.e, packetlogininencryptionbegin.b(privatekey))) {
/* 189 */       throw new IllegalStateException("Invalid nonce!");
/*     */     }
/* 191 */     this.loginKey = packetlogininencryptionbegin.a(privatekey);
/* 192 */     this.g = EnumProtocolState.AUTHENTICATING;
/* 193 */     this.networkManager.a(this.loginKey);
/* 194 */     new Thread("User Authenticator #" + b.incrementAndGet()) {
/*     */       public void run() {
/* 196 */         GameProfile gameprofile = LoginListener.this.i;
/*     */         try
/*     */         {
/* 199 */           String s = new BigInteger(MinecraftEncryption.a(LoginListener.this.j, LoginListener.this.server.Q().getPublic(), LoginListener.this.loginKey)).toString(16);
/*     */           
/* 201 */           LoginListener.this.i = LoginListener.this.server.aD().hasJoinedServer(new GameProfile(null, gameprofile.getName()), s);
/* 202 */           if (LoginListener.this.i != null)
/*     */           {
/* 204 */             if (!LoginListener.this.networkManager.g()) {
/* 205 */               return;
/*     */             }
/*     */             
/* 208 */             new LoginListener.LoginHandler(LoginListener.this).fireEvents();
/* 209 */           } else if (LoginListener.this.server.T()) {
/* 210 */             LoginListener.c.warn("Failed to verify username but will let them in anyway!");
/* 211 */             LoginListener.this.i = LoginListener.this.a(gameprofile);
/* 212 */             LoginListener.this.g = LoginListener.EnumProtocolState.READY_TO_ACCEPT;
/*     */           } else {
/* 214 */             LoginListener.this.disconnect("Failed to verify username!");
/* 215 */             LoginListener.c.error("Username '" + gameprofile.getName() + "' tried to join with an invalid session");
/*     */           }
/*     */         } catch (AuthenticationUnavailableException localAuthenticationUnavailableException) {
/* 218 */           if (LoginListener.this.server.T()) {
/* 219 */             LoginListener.c.warn("Authentication servers are down but will let them in anyway!");
/* 220 */             LoginListener.this.i = LoginListener.this.a(gameprofile);
/* 221 */             LoginListener.this.g = LoginListener.EnumProtocolState.READY_TO_ACCEPT;
/*     */           } else {
/* 223 */             LoginListener.this.disconnect("Authentication servers are down. Please try again later, sorry!");
/* 224 */             LoginListener.c.error("Couldn't verify username because servers are unavailable");
/*     */           }
/*     */         }
/*     */         catch (Exception exception) {
/* 228 */           LoginListener.this.disconnect("Failed to verify username!");
/* 229 */           LoginListener.this.server.server.getLogger().log(Level.WARNING, "Exception verifying " + gameprofile.getName(), exception);
/*     */         }
/*     */       }
/*     */     }.start();
/*     */   }
/*     */   
/*     */   public class LoginHandler
/*     */   {
/*     */     public LoginHandler() {}
/*     */     
/*     */     public void fireEvents()
/*     */       throws Exception
/*     */     {
/* 242 */       String playerName = LoginListener.this.i.getName();
/* 243 */       InetAddress address = ((InetSocketAddress)LoginListener.this.networkManager.getSocketAddress()).getAddress();
/* 244 */       UUID uniqueId = LoginListener.this.i.getId();
/* 245 */       final CraftServer server = LoginListener.this.server.server;
/*     */       
/* 247 */       AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(playerName, address, uniqueId);
/* 248 */       server.getPluginManager().callEvent(asyncEvent);
/*     */       
/* 250 */       if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
/* 251 */         final PlayerPreLoginEvent event = new PlayerPreLoginEvent(playerName, address, uniqueId);
/* 252 */         if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
/* 253 */           event.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
/*     */         }
/* 255 */         Waitable<PlayerPreLoginEvent.Result> waitable = new Waitable()
/*     */         {
/*     */           protected PlayerPreLoginEvent.Result evaluate() {
/* 258 */             server.getPluginManager().callEvent(event);
/* 259 */             return event.getResult();
/*     */           }
/* 261 */         };
/* 262 */         LoginListener.this.server.processQueue.add(waitable);
/* 263 */         if (waitable.get() != PlayerPreLoginEvent.Result.ALLOWED) {
/* 264 */           LoginListener.this.disconnect(event.getKickMessage());
/*     */         }
/*     */         
/*     */       }
/* 268 */       else if (asyncEvent.getLoginResult() != org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.ALLOWED) {
/* 269 */         LoginListener.this.disconnect(asyncEvent.getKickMessage());
/* 270 */         return;
/*     */       }
/*     */       
/*     */ 
/* 274 */       LoginListener.c.info("UUID of player " + LoginListener.this.i.getName() + " is " + LoginListener.this.i.getId());
/* 275 */       LoginListener.this.g = LoginListener.EnumProtocolState.READY_TO_ACCEPT;
/*     */     }
/*     */   }
/*     */   
/*     */   protected GameProfile a(GameProfile gameprofile)
/*     */   {
/* 281 */     UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + gameprofile.getName()).getBytes(Charsets.UTF_8));
/*     */     
/* 283 */     return new GameProfile(uuid, gameprofile.getName());
/*     */   }
/*     */   
/*     */   static enum EnumProtocolState
/*     */   {
/* 288 */     HELLO,  KEY,  AUTHENTICATING,  READY_TO_ACCEPT,  e,  ACCEPTED;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\LoginListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */