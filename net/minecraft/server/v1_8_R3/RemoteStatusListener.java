/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.PortUnreachableException;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class RemoteStatusListener
/*     */   extends RemoteConnectionThread
/*     */ {
/*     */   private long h;
/*     */   private int i;
/*     */   private int j;
/*     */   private int k;
/*     */   private String l;
/*     */   private String m;
/*     */   private DatagramSocket n;
/*  30 */   private byte[] o = new byte['ִ'];
/*     */   
/*     */   private DatagramPacket p;
/*     */   private Map<SocketAddress, String> q;
/*     */   private String r;
/*     */   private String s;
/*     */   private Map<SocketAddress, RemoteStatusChallenge> t;
/*     */   private long u;
/*     */   private RemoteStatusReply v;
/*     */   private long w;
/*     */   
/*     */   public RemoteStatusListener(IMinecraftServer paramIMinecraftServer)
/*     */   {
/*  43 */     super(paramIMinecraftServer, "Query Listener");
/*     */     
/*  45 */     this.i = paramIMinecraftServer.a("query.port", 0);
/*  46 */     this.s = paramIMinecraftServer.E();
/*  47 */     this.j = paramIMinecraftServer.F();
/*  48 */     this.l = paramIMinecraftServer.G();
/*  49 */     this.k = paramIMinecraftServer.J();
/*  50 */     this.m = paramIMinecraftServer.U();
/*     */     
/*     */ 
/*  53 */     this.w = 0L;
/*     */     
/*  55 */     this.r = "0.0.0.0";
/*     */     
/*     */ 
/*  58 */     if ((0 == this.s.length()) || (this.r.equals(this.s)))
/*     */     {
/*  60 */       this.s = "0.0.0.0";
/*     */       try {
/*  62 */         InetAddress localInetAddress = InetAddress.getLocalHost();
/*  63 */         this.r = localInetAddress.getHostAddress();
/*     */       } catch (UnknownHostException localUnknownHostException) {
/*  65 */         c("Unable to determine local host IP, please set server-ip in '" + paramIMinecraftServer.b() + "' : " + localUnknownHostException.getMessage());
/*     */       }
/*     */     } else {
/*  68 */       this.r = this.s;
/*     */     }
/*     */     
/*     */ 
/*  72 */     if (0 == this.i)
/*     */     {
/*  74 */       this.i = this.j;
/*  75 */       b("Setting default query port to " + this.i);
/*  76 */       paramIMinecraftServer.a("query.port", Integer.valueOf(this.i));
/*  77 */       paramIMinecraftServer.a("debug", Boolean.valueOf(false));
/*  78 */       paramIMinecraftServer.a();
/*     */     }
/*     */     
/*  81 */     this.q = Maps.newHashMap();
/*  82 */     this.v = new RemoteStatusReply(1460);
/*  83 */     this.t = Maps.newHashMap();
/*  84 */     this.u = new Date().getTime();
/*     */   }
/*     */   
/*     */   private void a(byte[] paramArrayOfByte, DatagramPacket paramDatagramPacket) throws IOException {
/*  88 */     this.n.send(new DatagramPacket(paramArrayOfByte, paramArrayOfByte.length, paramDatagramPacket.getSocketAddress()));
/*     */   }
/*     */   
/*     */   private boolean a(DatagramPacket paramDatagramPacket) throws IOException {
/*  92 */     byte[] arrayOfByte = paramDatagramPacket.getData();
/*  93 */     int i1 = paramDatagramPacket.getLength();
/*  94 */     SocketAddress localSocketAddress = paramDatagramPacket.getSocketAddress();
/*  95 */     a("Packet len " + i1 + " [" + localSocketAddress + "]");
/*  96 */     if ((3 > i1) || (-2 != arrayOfByte[0]) || (-3 != arrayOfByte[1]))
/*     */     {
/*  98 */       a("Invalid packet [" + localSocketAddress + "]");
/*  99 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 103 */     a("Packet '" + StatusChallengeUtils.a(arrayOfByte[2]) + "' [" + localSocketAddress + "]");
/* 104 */     switch (arrayOfByte[2])
/*     */     {
/*     */     case 9: 
/* 107 */       d(paramDatagramPacket);
/* 108 */       a("Challenge [" + localSocketAddress + "]");
/* 109 */       return true;
/*     */     
/*     */ 
/*     */     case 0: 
/* 113 */       if (!c(paramDatagramPacket).booleanValue()) {
/* 114 */         a("Invalid challenge [" + localSocketAddress + "]");
/* 115 */         return false;
/*     */       }
/*     */       
/* 118 */       if (15 == i1)
/*     */       {
/* 120 */         a(b(paramDatagramPacket), paramDatagramPacket);
/* 121 */         a("Rules [" + localSocketAddress + "]");
/*     */       }
/*     */       else {
/* 124 */         RemoteStatusReply localRemoteStatusReply = new RemoteStatusReply(1460);
/* 125 */         localRemoteStatusReply.a(0);
/* 126 */         localRemoteStatusReply.a(a(paramDatagramPacket.getSocketAddress()));
/* 127 */         localRemoteStatusReply.a(this.l);
/* 128 */         localRemoteStatusReply.a("SMP");
/* 129 */         localRemoteStatusReply.a(this.m);
/* 130 */         localRemoteStatusReply.a(Integer.toString(d()));
/* 131 */         localRemoteStatusReply.a(Integer.toString(this.k));
/* 132 */         localRemoteStatusReply.a((short)this.j);
/* 133 */         localRemoteStatusReply.a(this.r);
/*     */         
/* 135 */         a(localRemoteStatusReply.a(), paramDatagramPacket);
/* 136 */         a("Status [" + localSocketAddress + "]");
/*     */       }
/*     */       break;
/*     */     }
/* 140 */     return true;
/*     */   }
/*     */   
/*     */   private byte[] b(DatagramPacket paramDatagramPacket) throws IOException {
/* 144 */     long l1 = MinecraftServer.az();
/* 145 */     Object localObject2; if (l1 < this.w + 5000L)
/*     */     {
/* 147 */       localObject1 = this.v.a();
/* 148 */       localObject2 = a(paramDatagramPacket.getSocketAddress());
/* 149 */       localObject1[1] = localObject2[0];
/* 150 */       localObject1[2] = localObject2[1];
/* 151 */       localObject1[3] = localObject2[2];
/* 152 */       localObject1[4] = localObject2[3];
/*     */       
/* 154 */       return (byte[])localObject1;
/*     */     }
/*     */     
/* 157 */     this.w = l1;
/*     */     
/* 159 */     this.v.b();
/* 160 */     this.v.a(0);
/* 161 */     this.v.a(a(paramDatagramPacket.getSocketAddress()));
/* 162 */     this.v.a("splitnum");
/* 163 */     this.v.a(128);
/* 164 */     this.v.a(0);
/*     */     
/*     */ 
/* 167 */     this.v.a("hostname");
/* 168 */     this.v.a(this.l);
/* 169 */     this.v.a("gametype");
/* 170 */     this.v.a("SMP");
/* 171 */     this.v.a("game_id");
/* 172 */     this.v.a("MINECRAFT");
/* 173 */     this.v.a("version");
/* 174 */     this.v.a(this.b.getVersion());
/* 175 */     this.v.a("plugins");
/* 176 */     this.v.a(this.b.getPlugins());
/* 177 */     this.v.a("map");
/* 178 */     this.v.a(this.m);
/* 179 */     this.v.a("numplayers");
/* 180 */     this.v.a("" + d());
/* 181 */     this.v.a("maxplayers");
/* 182 */     this.v.a("" + this.k);
/* 183 */     this.v.a("hostport");
/* 184 */     this.v.a("" + this.j);
/* 185 */     this.v.a("hostip");
/* 186 */     this.v.a(this.r);
/* 187 */     this.v.a(0);
/* 188 */     this.v.a(1);
/*     */     
/*     */ 
/*     */ 
/* 192 */     this.v.a("player_");
/* 193 */     this.v.a(0);
/*     */     
/* 195 */     Object localObject1 = this.b.getPlayers();
/* 196 */     for (String str : localObject1) {
/* 197 */       this.v.a(str);
/*     */     }
/* 199 */     this.v.a(0);
/*     */     
/* 201 */     return this.v.a();
/*     */   }
/*     */   
/*     */   private byte[] a(SocketAddress paramSocketAddress) {
/* 205 */     return ((RemoteStatusChallenge)this.t.get(paramSocketAddress)).c();
/*     */   }
/*     */   
/*     */   private Boolean c(DatagramPacket paramDatagramPacket) {
/* 209 */     SocketAddress localSocketAddress = paramDatagramPacket.getSocketAddress();
/* 210 */     if (!this.t.containsKey(localSocketAddress))
/*     */     {
/* 212 */       return Boolean.valueOf(false);
/*     */     }
/*     */     
/* 215 */     byte[] arrayOfByte = paramDatagramPacket.getData();
/* 216 */     if (((RemoteStatusChallenge)this.t.get(localSocketAddress)).a() != StatusChallengeUtils.c(arrayOfByte, 7, paramDatagramPacket.getLength()))
/*     */     {
/* 218 */       return Boolean.valueOf(false);
/*     */     }
/*     */     
/*     */ 
/* 222 */     return Boolean.valueOf(true);
/*     */   }
/*     */   
/*     */   private void d(DatagramPacket paramDatagramPacket) throws IOException {
/* 226 */     RemoteStatusChallenge localRemoteStatusChallenge = new RemoteStatusChallenge(paramDatagramPacket);
/* 227 */     this.t.put(paramDatagramPacket.getSocketAddress(), localRemoteStatusChallenge);
/*     */     
/* 229 */     a(localRemoteStatusChallenge.b(), paramDatagramPacket);
/*     */   }
/*     */   
/*     */   private void f() {
/* 233 */     if (!this.a) {
/* 234 */       return;
/*     */     }
/*     */     
/* 237 */     long l1 = MinecraftServer.az();
/* 238 */     if (l1 < this.h + 30000L) {
/* 239 */       return;
/*     */     }
/* 241 */     this.h = l1;
/*     */     
/* 243 */     Iterator localIterator = this.t.entrySet().iterator();
/* 244 */     while (localIterator.hasNext()) {
/* 245 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 246 */       if (((RemoteStatusChallenge)localEntry.getValue()).a(l1).booleanValue()) {
/* 247 */         localIterator.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/* 254 */     b("Query running on " + this.s + ":" + this.i);
/* 255 */     this.h = MinecraftServer.az();
/* 256 */     this.p = new DatagramPacket(this.o, this.o.length);
/*     */     try
/*     */     {
/* 259 */       while (this.a) {
/*     */         try {
/* 261 */           this.n.receive(this.p);
/*     */           
/*     */ 
/* 264 */           f();
/*     */           
/*     */ 
/* 267 */           a(this.p);
/*     */         }
/*     */         catch (SocketTimeoutException localSocketTimeoutException) {
/* 270 */           f();
/*     */ 
/*     */         }
/*     */         catch (PortUnreachableException localPortUnreachableException) {}catch (IOException localIOException)
/*     */         {
/* 275 */           a(localIOException);
/*     */         }
/*     */       }
/*     */     } finally {
/* 279 */       e();
/*     */     }
/*     */   }
/*     */   
/*     */   public void a()
/*     */   {
/* 285 */     if (this.a) {
/* 286 */       return;
/*     */     }
/*     */     
/* 289 */     if ((0 >= this.i) || (65535 < this.i)) {
/* 290 */       c("Invalid query port " + this.i + " found in '" + this.b.b() + "' (queries disabled)");
/* 291 */       return;
/*     */     }
/*     */     
/* 294 */     if (g()) {
/* 295 */       super.a();
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(Exception paramException) {
/* 300 */     if (!this.a) {
/* 301 */       return;
/*     */     }
/*     */     
/*     */ 
/* 305 */     c("Unexpected exception, buggy JRE? (" + paramException.toString() + ")");
/*     */     
/*     */ 
/* 308 */     if (!g()) {
/* 309 */       d("Failed to recover from buggy JRE, shutting down!");
/* 310 */       this.a = false;
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean g() {
/*     */     try {
/* 316 */       this.n = new DatagramSocket(this.i, InetAddress.getByName(this.s));
/* 317 */       a(this.n);
/* 318 */       this.n.setSoTimeout(500);
/* 319 */       return true;
/*     */     } catch (SocketException localSocketException) {
/* 321 */       c("Unable to initialise query system on " + this.s + ":" + this.i + " (Socket): " + localSocketException.getMessage());
/*     */     } catch (UnknownHostException localUnknownHostException) {
/* 323 */       c("Unable to initialise query system on " + this.s + ":" + this.i + " (Unknown Host): " + localUnknownHostException.getMessage());
/*     */     } catch (Exception localException) {
/* 325 */       c("Unable to initialise query system on " + this.s + ":" + this.i + " (E): " + localException.getMessage());
/*     */     }
/*     */     
/* 328 */     return false;
/*     */   }
/*     */   
/*     */   class RemoteStatusChallenge {
/*     */     private long time;
/*     */     private int token;
/*     */     private byte[] identity;
/*     */     private byte[] response;
/*     */     private String f;
/*     */     
/*     */     public RemoteStatusChallenge(DatagramPacket paramDatagramPacket) {
/* 339 */       this.time = new Date().getTime();
/* 340 */       byte[] arrayOfByte = paramDatagramPacket.getData();
/* 341 */       this.identity = new byte[4];
/* 342 */       this.identity[0] = arrayOfByte[3];
/* 343 */       this.identity[1] = arrayOfByte[4];
/* 344 */       this.identity[2] = arrayOfByte[5];
/* 345 */       this.identity[3] = arrayOfByte[6];
/* 346 */       this.f = new String(this.identity);
/* 347 */       this.token = new Random().nextInt(16777216);
/* 348 */       this.response = String.format("\t%s%d\000", new Object[] { this.f, Integer.valueOf(this.token) }).getBytes();
/*     */     }
/*     */     
/*     */     public Boolean a(long paramLong) {
/* 352 */       return Boolean.valueOf(this.time < paramLong);
/*     */     }
/*     */     
/*     */     public int a() {
/* 356 */       return this.token;
/*     */     }
/*     */     
/*     */     public byte[] b() {
/* 360 */       return this.response;
/*     */     }
/*     */     
/*     */     public byte[] c() {
/* 364 */       return this.identity;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RemoteStatusListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */