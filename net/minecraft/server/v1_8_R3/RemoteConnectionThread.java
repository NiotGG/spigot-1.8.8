/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.ServerSocket;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ public abstract class RemoteConnectionThread implements Runnable
/*     */ {
/*  13 */   private static final AtomicInteger h = new AtomicInteger(0);
/*     */   protected boolean a;
/*     */   protected IMinecraftServer b;
/*     */   protected final String c;
/*     */   protected Thread d;
/*  18 */   protected int e = 5;
/*  19 */   protected List<DatagramSocket> f = Lists.newArrayList();
/*  20 */   protected List<ServerSocket> g = Lists.newArrayList();
/*     */   
/*     */   protected RemoteConnectionThread(IMinecraftServer paramIMinecraftServer, String paramString) {
/*  23 */     this.b = paramIMinecraftServer;
/*  24 */     this.c = paramString;
/*  25 */     if (this.b.isDebugging()) {
/*  26 */       c("Debugging is enabled, performance maybe reduced!");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public synchronized void a()
/*     */   {
/*  34 */     this.d = new Thread(this, this.c + " #" + h.incrementAndGet());
/*  35 */     this.d.start();
/*  36 */     this.a = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean c()
/*     */   {
/*  75 */     return this.a;
/*     */   }
/*     */   
/*     */   protected void a(String paramString) {
/*  79 */     this.b.h(paramString);
/*     */   }
/*     */   
/*     */   protected void b(String paramString) {
/*  83 */     this.b.info(paramString);
/*     */   }
/*     */   
/*     */   protected void c(String paramString) {
/*  87 */     this.b.warning(paramString);
/*     */   }
/*     */   
/*     */   protected void d(String paramString) {
/*  91 */     this.b.g(paramString);
/*     */   }
/*     */   
/*     */   protected int d() {
/*  95 */     return this.b.I();
/*     */   }
/*     */   
/*     */   protected void a(DatagramSocket paramDatagramSocket) {
/*  99 */     a("registerSocket: " + paramDatagramSocket);
/* 100 */     this.f.add(paramDatagramSocket);
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
/*     */   protected boolean a(DatagramSocket paramDatagramSocket, boolean paramBoolean)
/*     */   {
/* 113 */     a("closeSocket: " + paramDatagramSocket);
/* 114 */     if (null == paramDatagramSocket) {
/* 115 */       return false;
/*     */     }
/*     */     
/* 118 */     boolean bool = false;
/* 119 */     if (!paramDatagramSocket.isClosed()) {
/* 120 */       paramDatagramSocket.close();
/* 121 */       bool = true;
/*     */     }
/*     */     
/* 124 */     if (paramBoolean) {
/* 125 */       this.f.remove(paramDatagramSocket);
/*     */     }
/*     */     
/* 128 */     return bool;
/*     */   }
/*     */   
/*     */   protected boolean b(ServerSocket paramServerSocket) {
/* 132 */     return a(paramServerSocket, true);
/*     */   }
/*     */   
/*     */   protected boolean a(ServerSocket paramServerSocket, boolean paramBoolean) {
/* 136 */     a("closeSocket: " + paramServerSocket);
/* 137 */     if (null == paramServerSocket) {
/* 138 */       return false;
/*     */     }
/*     */     
/* 141 */     boolean bool = false;
/*     */     try {
/* 143 */       if (!paramServerSocket.isClosed()) {
/* 144 */         paramServerSocket.close();
/* 145 */         bool = true;
/*     */       }
/*     */     } catch (IOException localIOException) {
/* 148 */       c("IO: " + localIOException.getMessage());
/*     */     }
/*     */     
/* 151 */     if (paramBoolean) {
/* 152 */       this.g.remove(paramServerSocket);
/*     */     }
/*     */     
/* 155 */     return bool;
/*     */   }
/*     */   
/*     */   protected void e() {
/* 159 */     a(false);
/*     */   }
/*     */   
/*     */   protected void a(boolean paramBoolean) {
/* 163 */     int i = 0;
/* 164 */     for (Iterator localIterator = this.f.iterator(); localIterator.hasNext();) { localObject = (DatagramSocket)localIterator.next();
/* 165 */       if (a((DatagramSocket)localObject, false))
/* 166 */         i++;
/*     */     }
/*     */     Object localObject;
/* 169 */     this.f.clear();
/*     */     
/* 171 */     for (localIterator = this.g.iterator(); localIterator.hasNext();) { localObject = (ServerSocket)localIterator.next();
/* 172 */       if (a((ServerSocket)localObject, false)) {
/* 173 */         i++;
/*     */       }
/*     */     }
/* 176 */     this.g.clear();
/*     */     
/* 178 */     if ((paramBoolean) && (0 < i)) {
/* 179 */       c("Force closed " + i + " sockets");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RemoteConnectionThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */