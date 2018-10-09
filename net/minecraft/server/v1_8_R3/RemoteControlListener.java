/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.ServerSocket;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class RemoteControlListener extends RemoteConnectionThread
/*     */ {
/*     */   private int h;
/*     */   private int i;
/*     */   private String j;
/*     */   private ServerSocket k;
/*     */   private String l;
/*     */   private Map<java.net.SocketAddress, RemoteControlSession> m;
/*     */   
/*     */   public RemoteControlListener(IMinecraftServer paramIMinecraftServer)
/*     */   {
/*  20 */     super(paramIMinecraftServer, "RCON Listener");
/*  21 */     this.h = paramIMinecraftServer.a("rcon.port", 0);
/*  22 */     this.l = paramIMinecraftServer.a("rcon.password", "");
/*  23 */     this.j = paramIMinecraftServer.E();
/*  24 */     this.i = paramIMinecraftServer.F();
/*  25 */     if (0 == this.h)
/*     */     {
/*  27 */       this.h = (this.i + 10);
/*  28 */       b("Setting default rcon port to " + this.h);
/*  29 */       paramIMinecraftServer.a("rcon.port", Integer.valueOf(this.h));
/*  30 */       if (0 == this.l.length()) {
/*  31 */         paramIMinecraftServer.a("rcon.password", "");
/*     */       }
/*  33 */       paramIMinecraftServer.a();
/*     */     }
/*     */     
/*  36 */     if (0 == this.j.length()) {
/*  37 */       this.j = "0.0.0.0";
/*     */     }
/*     */     
/*  40 */     f();
/*  41 */     this.k = null;
/*     */   }
/*     */   
/*     */   private void f() {
/*  45 */     this.m = com.google.common.collect.Maps.newHashMap();
/*     */   }
/*     */   
/*     */   private void g() {
/*  49 */     Iterator localIterator = this.m.entrySet().iterator();
/*  50 */     while (localIterator.hasNext()) {
/*  51 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/*  52 */       if (!((RemoteControlSession)localEntry.getValue()).c()) {
/*  53 */         localIterator.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*  60 */     b("RCON running on " + this.j + ":" + this.h);
/*     */     try {
/*  62 */       while (this.a) {
/*     */         try
/*     */         {
/*  65 */           java.net.Socket localSocket = this.k.accept();
/*  66 */           localSocket.setSoTimeout(500);
/*  67 */           RemoteControlSession localRemoteControlSession = new RemoteControlSession(this.b, localSocket);
/*  68 */           localRemoteControlSession.a();
/*  69 */           this.m.put(localSocket.getRemoteSocketAddress(), localRemoteControlSession);
/*     */           
/*     */ 
/*  72 */           g();
/*     */         }
/*     */         catch (java.net.SocketTimeoutException localSocketTimeoutException) {
/*  75 */           g();
/*     */         } catch (IOException localIOException) {
/*  77 */           if (this.a) {
/*  78 */             b("IO: " + localIOException.getMessage());
/*     */           }
/*     */         }
/*     */       }
/*     */     } finally {
/*  83 */       b(this.k);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a()
/*     */   {
/*  89 */     if (0 == this.l.length()) {
/*  90 */       c("No rcon password set in '" + this.b.b() + "', rcon disabled!");
/*  91 */       return;
/*     */     }
/*     */     
/*  94 */     if ((0 >= this.h) || (65535 < this.h)) {
/*  95 */       c("Invalid rcon port " + this.h + " found in '" + this.b.b() + "', rcon disabled!");
/*  96 */       return;
/*     */     }
/*     */     
/*  99 */     if (this.a) {
/* 100 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 104 */       this.k = new ServerSocket(this.h, 0, java.net.InetAddress.getByName(this.j));
/* 105 */       this.k.setSoTimeout(500);
/* 106 */       super.a();
/*     */     } catch (IOException localIOException) {
/* 108 */       c("Unable to initialise rcon on " + this.j + ":" + this.h + " : " + localIOException.getMessage());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RemoteControlListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */