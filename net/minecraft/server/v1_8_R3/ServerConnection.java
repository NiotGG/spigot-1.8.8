/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import io.netty.bootstrap.ServerBootstrap;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.epoll.Epoll;
/*     */ import io.netty.channel.epoll.EpollEventLoopGroup;
/*     */ import io.netty.channel.epoll.EpollServerSocketChannel;
/*     */ import io.netty.channel.local.LocalEventLoopGroup;
/*     */ import io.netty.channel.nio.NioEventLoopGroup;
/*     */ import io.netty.channel.socket.nio.NioServerSocketChannel;
/*     */ import io.netty.handler.timeout.ReadTimeoutHandler;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class ServerConnection
/*     */ {
/*  32 */   private static final Logger e = ;
/*  33 */   public static final LazyInitVar<NioEventLoopGroup> a = new LazyInitVar() {
/*     */     protected NioEventLoopGroup a() {
/*  35 */       return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build());
/*     */     }
/*     */     
/*     */     protected Object init() {
/*  39 */       return a();
/*     */     }
/*     */   };
/*  42 */   public static final LazyInitVar<EpollEventLoopGroup> b = new LazyInitVar() {
/*     */     protected EpollEventLoopGroup a() {
/*  44 */       return new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build());
/*     */     }
/*     */     
/*     */     protected Object init() {
/*  48 */       return a();
/*     */     }
/*     */   };
/*  51 */   public static final LazyInitVar<LocalEventLoopGroup> c = new LazyInitVar() {
/*     */     protected LocalEventLoopGroup a() {
/*  53 */       return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Server IO #%d").setDaemon(true).build());
/*     */     }
/*     */     
/*     */     protected Object init() {
/*  57 */       return a();
/*     */     }
/*     */   };
/*     */   private final MinecraftServer f;
/*     */   public volatile boolean d;
/*  62 */   private final List<ChannelFuture> g = Collections.synchronizedList(Lists.newArrayList());
/*  63 */   private final List<NetworkManager> h = Collections.synchronizedList(Lists.newArrayList());
/*     */   
/*     */   public ServerConnection(MinecraftServer minecraftserver) {
/*  66 */     this.f = minecraftserver;
/*  67 */     this.d = true;
/*     */   }
/*     */   
/*     */   public void a(InetAddress inetaddress, int i)
/*     */     throws java.io.IOException
/*     */   {
/*  73 */     synchronized (this.g)
/*     */     {
/*     */       Class oclass;
/*     */       LazyInitVar lazyinitvar;
/*  77 */       if ((Epoll.isAvailable()) && (this.f.ai())) {
/*  78 */         Class oclass = EpollServerSocketChannel.class;
/*  79 */         LazyInitVar lazyinitvar = b;
/*  80 */         e.info("Using epoll channel type");
/*     */       } else {
/*  82 */         oclass = NioServerSocketChannel.class;
/*  83 */         lazyinitvar = a;
/*  84 */         e.info("Using default channel type");
/*     */       }
/*     */       
/*  87 */       this.g.add(
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
/* 102 */         ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(oclass)).childHandler(new ChannelInitializer() {
/*     */           protected void initChannel(Channel channel) throws Exception {
/*     */             try {
/*  90 */               channel.config().setOption(io.netty.channel.ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
/*     */             }
/*     */             catch (ChannelException localChannelException) {}
/*     */             
/*     */ 
/*  95 */             channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("legacy_query", new LegacyPingHandler(ServerConnection.this)).addLast("splitter", new PacketSplitter()).addLast("decoder", new PacketDecoder(EnumProtocolDirection.SERVERBOUND)).addLast("prepender", new PacketPrepender()).addLast("encoder", new PacketEncoder(EnumProtocolDirection.CLIENTBOUND));
/*  96 */             NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.SERVERBOUND);
/*     */             
/*  98 */             ServerConnection.this.h.add(networkmanager);
/*  99 */             channel.pipeline().addLast("packet_handler", networkmanager);
/* 100 */             networkmanager.a(new HandshakeListener(ServerConnection.this.f, networkmanager));
/*     */           }
/* 102 */         }).group((io.netty.channel.EventLoopGroup)lazyinitvar.c()).localAddress(inetaddress, i)).bind().syncUninterruptibly());
/*     */     }
/*     */   }
/*     */   
/*     */   public void b() {
/* 107 */     this.d = false;
/* 108 */     Iterator iterator = this.g.iterator();
/*     */     
/* 110 */     while (iterator.hasNext()) {
/* 111 */       ChannelFuture channelfuture = (ChannelFuture)iterator.next();
/*     */       try
/*     */       {
/* 114 */         channelfuture.channel().close().sync();
/*     */       } catch (InterruptedException localInterruptedException) {
/* 116 */         e.error("Interrupted whilst closing channel");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void c()
/*     */   {
/* 125 */     synchronized (this.h)
/*     */     {
/*     */ 
/* 128 */       if ((SpigotConfig.playerShuffle > 0) && (MinecraftServer.currentTick % SpigotConfig.playerShuffle == 0))
/*     */       {
/* 130 */         Collections.shuffle(this.h);
/*     */       }
/*     */       
/* 133 */       Iterator iterator = this.h.iterator();
/*     */       
/* 135 */       while (iterator.hasNext()) {
/* 136 */         final NetworkManager networkmanager = (NetworkManager)iterator.next();
/*     */         
/* 138 */         if (!networkmanager.h()) {
/* 139 */           if (!networkmanager.g())
/*     */           {
/*     */ 
/* 142 */             if (!networkmanager.preparing)
/*     */             {
/* 144 */               iterator.remove();
/* 145 */               networkmanager.l();
/*     */             }
/*     */           } else {
/* 148 */             try { networkmanager.a();
/*     */             } catch (Exception exception) {
/* 150 */               if (networkmanager.c()) {
/* 151 */                 CrashReport crashreport = CrashReport.a(exception, "Ticking memory connection");
/* 152 */                 CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Ticking connection");
/*     */                 
/* 154 */                 crashreportsystemdetails.a("Connection", new Callable() {
/*     */                   public String a() throws Exception {
/* 156 */                     return networkmanager.toString();
/*     */                   }
/*     */                   
/*     */                   public Object call() throws Exception {
/* 160 */                     return a();
/*     */                   }
/* 162 */                 });
/* 163 */                 throw new ReportedException(crashreport);
/*     */               }
/*     */               
/* 166 */               e.warn("Failed to handle packet for " + networkmanager.getSocketAddress(), exception);
/* 167 */               final ChatComponentText chatcomponenttext = new ChatComponentText("Internal server error");
/*     */               
/* 169 */               networkmanager.a(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener() {
/*     */                 public void operationComplete(Future future) throws Exception {
/* 171 */                   networkmanager.close(chatcomponenttext);
/*     */                 }
/* 173 */               }, new GenericFutureListener[0]);
/* 174 */               networkmanager.k();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public MinecraftServer d()
/*     */   {
/* 184 */     return this.f;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ServerConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */