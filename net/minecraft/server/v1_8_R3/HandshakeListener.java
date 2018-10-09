/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class HandshakeListener implements PacketHandshakingInListener
/*     */ {
/*  10 */   private static final com.google.gson.Gson gson = new com.google.gson.Gson();
/*     */   
/*  12 */   private static final HashMap<InetAddress, Long> throttleTracker = new HashMap();
/*  13 */   private static int throttleCounter = 0;
/*     */   
/*     */   private final MinecraftServer a;
/*     */   private final NetworkManager b;
/*     */   
/*     */   public HandshakeListener(MinecraftServer minecraftserver, NetworkManager networkmanager)
/*     */   {
/*  20 */     this.a = minecraftserver;
/*  21 */     this.b = networkmanager;
/*     */   }
/*     */   
/*     */   public void a(PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
/*  25 */     switch (SyntheticClass_1.a[packethandshakinginsetprotocol.a().ordinal()]) {
/*     */     case 1: 
/*  27 */       this.b.a(EnumProtocol.LOGIN);
/*     */       
/*     */ 
/*     */       try
/*     */       {
/*  32 */         long currentTime = System.currentTimeMillis();
/*  33 */         long connectionThrottle = MinecraftServer.getServer().server.getConnectionThrottle();
/*  34 */         InetAddress address = ((InetSocketAddress)this.b.getSocketAddress()).getAddress();
/*     */         
/*  36 */         synchronized (throttleTracker) {
/*  37 */           if ((throttleTracker.containsKey(address)) && (!"127.0.0.1".equals(address.getHostAddress())) && (currentTime - ((Long)throttleTracker.get(address)).longValue() < connectionThrottle)) {
/*  38 */             throttleTracker.put(address, Long.valueOf(currentTime));
/*  39 */             ChatComponentText chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
/*  40 */             this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext));
/*  41 */             this.b.close(chatcomponenttext);
/*  42 */             return;
/*     */           }
/*     */           
/*  45 */           throttleTracker.put(address, Long.valueOf(currentTime));
/*  46 */           throttleCounter += 1;
/*  47 */           if (throttleCounter > 200) {
/*  48 */             throttleCounter = 0;
/*     */             
/*     */ 
/*  51 */             Iterator iter = throttleTracker.entrySet().iterator();
/*  52 */             while (iter.hasNext()) {
/*  53 */               java.util.Map.Entry<InetAddress, Long> entry = (java.util.Map.Entry)iter.next();
/*  54 */               if (((Long)entry.getValue()).longValue() > connectionThrottle) {
/*  55 */                 iter.remove();
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         ChatComponentText chatcomponenttext;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         ChatComponentText chatcomponenttext;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         String[] split;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         ChatComponentText chatcomponenttext;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 100 */         this.b.a(EnumProtocol.STATUS);
/*     */       }
/*     */       catch (Throwable t)
/*     */       {
/*  61 */         org.apache.logging.log4j.LogManager.getLogger().debug("Failed to check connection throttle", t);
/*     */         
/*     */ 
/*     */ 
/*  65 */         if (packethandshakinginsetprotocol.b() > 47) {
/*  66 */           chatcomponenttext = new ChatComponentText(java.text.MessageFormat.format(org.spigotmc.SpigotConfig.outdatedServerMessage, new Object[] { "1.8.8" }));
/*  67 */           this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext));
/*  68 */           this.b.close(chatcomponenttext);
/*  69 */         } else if (packethandshakinginsetprotocol.b() < 47) {
/*  70 */           chatcomponenttext = new ChatComponentText(java.text.MessageFormat.format(org.spigotmc.SpigotConfig.outdatedClientMessage, new Object[] { "1.8.8" }));
/*  71 */           this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext));
/*  72 */           this.b.close(chatcomponenttext);
/*     */         } else {
/*  74 */           this.b.a(new LoginListener(this.a, this.b));
/*     */           
/*  76 */           if (org.spigotmc.SpigotConfig.bungee) {
/*  77 */             split = packethandshakinginsetprotocol.hostname.split("\000");
/*  78 */             if ((split.length == 3) || (split.length == 4)) {
/*  79 */               packethandshakinginsetprotocol.hostname = split[0];
/*  80 */               this.b.l = new InetSocketAddress(split[1], ((InetSocketAddress)this.b.getSocketAddress()).getPort());
/*  81 */               this.b.spoofedUUID = com.mojang.util.UUIDTypeAdapter.fromString(split[2]);
/*     */             }
/*     */             else {
/*  84 */               chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
/*  85 */               this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext));
/*  86 */               this.b.close(chatcomponenttext);
/*  87 */               return;
/*     */             }
/*  89 */             if (split.length == 4)
/*     */             {
/*  91 */               this.b.spoofedProfile = ((com.mojang.authlib.properties.Property[])gson.fromJson(split[3], com.mojang.authlib.properties.Property[].class));
/*     */             }
/*     */           }
/*     */           
/*  95 */           ((LoginListener)this.b.getPacketListener()).hostname = (packethandshakinginsetprotocol.hostname + ":" + packethandshakinginsetprotocol.port);
/*     */         }
/*     */       }
/*     */     
/*     */ 
/*     */     case 2: 
/* 101 */       this.b.a(new PacketStatusListener(this.a, this.b));
/* 102 */       break;
/*     */     
/*     */     default: 
/* 105 */       throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.a());
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public void a(IChatBaseComponent ichatbasecomponent) {}
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 114 */     static final int[] a = new int[EnumProtocol.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 118 */         a[EnumProtocol.LOGIN.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 124 */         a[EnumProtocol.STATUS.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\HandshakeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */