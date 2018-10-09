/*     */ package io.netty.channel.socket.nio;
/*     */ 
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.socket.DatagramChannelConfig;
/*     */ import io.netty.channel.socket.DefaultDatagramChannelConfig;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketException;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.NetworkChannel;
/*     */ import java.util.Enumeration;
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
/*     */ class NioDatagramChannelConfig
/*     */   extends DefaultDatagramChannelConfig
/*     */ {
/*     */   private static final Object IP_MULTICAST_TTL;
/*     */   private static final Object IP_MULTICAST_IF;
/*     */   private static final Object IP_MULTICAST_LOOP;
/*     */   private static final Method GET_OPTION;
/*     */   private static final Method SET_OPTION;
/*     */   private final DatagramChannel javaChannel;
/*     */   
/*     */   static
/*     */   {
/*  43 */     ClassLoader localClassLoader = PlatformDependent.getClassLoader(DatagramChannel.class);
/*  44 */     Class localClass1 = null;
/*     */     try {
/*  46 */       localClass1 = Class.forName("java.net.SocketOption", true, localClassLoader);
/*     */     }
/*     */     catch (Exception localException1) {}
/*     */     
/*  50 */     Class localClass2 = null;
/*     */     try {
/*  52 */       localClass2 = Class.forName("java.net.StandardSocketOptions", true, localClassLoader);
/*     */     }
/*     */     catch (Exception localException2) {}
/*     */     
/*     */ 
/*  57 */     Object localObject1 = null;
/*  58 */     Object localObject2 = null;
/*  59 */     Object localObject3 = null;
/*  60 */     Method localMethod1 = null;
/*  61 */     Method localMethod2 = null;
/*  62 */     if (localClass1 != null) {
/*     */       try {
/*  64 */         localObject1 = localClass2.getDeclaredField("IP_MULTICAST_TTL").get(null);
/*     */       } catch (Exception localException3) {
/*  66 */         throw new Error("cannot locate the IP_MULTICAST_TTL field", localException3);
/*     */       }
/*     */       try
/*     */       {
/*  70 */         localObject2 = localClass2.getDeclaredField("IP_MULTICAST_IF").get(null);
/*     */       } catch (Exception localException4) {
/*  72 */         throw new Error("cannot locate the IP_MULTICAST_IF field", localException4);
/*     */       }
/*     */       try
/*     */       {
/*  76 */         localObject3 = localClass2.getDeclaredField("IP_MULTICAST_LOOP").get(null);
/*     */       } catch (Exception localException5) {
/*  78 */         throw new Error("cannot locate the IP_MULTICAST_LOOP field", localException5);
/*     */       }
/*     */       try
/*     */       {
/*  82 */         localMethod1 = NetworkChannel.class.getDeclaredMethod("getOption", new Class[] { localClass1 });
/*     */       } catch (Exception localException6) {
/*  84 */         throw new Error("cannot locate the getOption() method", localException6);
/*     */       }
/*     */       try
/*     */       {
/*  88 */         localMethod2 = NetworkChannel.class.getDeclaredMethod("setOption", new Class[] { localClass1, Object.class });
/*     */       } catch (Exception localException7) {
/*  90 */         throw new Error("cannot locate the setOption() method", localException7);
/*     */       }
/*     */     }
/*  93 */     IP_MULTICAST_TTL = localObject1;
/*  94 */     IP_MULTICAST_IF = localObject2;
/*  95 */     IP_MULTICAST_LOOP = localObject3;
/*  96 */     GET_OPTION = localMethod1;
/*  97 */     SET_OPTION = localMethod2;
/*     */   }
/*     */   
/*     */ 
/*     */   NioDatagramChannelConfig(NioDatagramChannel paramNioDatagramChannel, DatagramChannel paramDatagramChannel)
/*     */   {
/* 103 */     super(paramNioDatagramChannel, paramDatagramChannel.socket());
/* 104 */     this.javaChannel = paramDatagramChannel;
/*     */   }
/*     */   
/*     */   public int getTimeToLive()
/*     */   {
/* 109 */     return ((Integer)getOption0(IP_MULTICAST_TTL)).intValue();
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setTimeToLive(int paramInt)
/*     */   {
/* 114 */     setOption0(IP_MULTICAST_TTL, Integer.valueOf(paramInt));
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public InetAddress getInterface()
/*     */   {
/* 120 */     NetworkInterface localNetworkInterface = getNetworkInterface();
/* 121 */     if (localNetworkInterface == null) {
/* 122 */       return null;
/*     */     }
/* 124 */     Enumeration localEnumeration = localNetworkInterface.getInetAddresses();
/* 125 */     if (localEnumeration.hasMoreElements()) {
/* 126 */       return (InetAddress)localEnumeration.nextElement();
/*     */     }
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setInterface(InetAddress paramInetAddress)
/*     */   {
/*     */     try
/*     */     {
/* 135 */       setNetworkInterface(NetworkInterface.getByInetAddress(paramInetAddress));
/*     */     } catch (SocketException localSocketException) {
/* 137 */       throw new ChannelException(localSocketException);
/*     */     }
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public NetworkInterface getNetworkInterface()
/*     */   {
/* 144 */     return (NetworkInterface)getOption0(IP_MULTICAST_IF);
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setNetworkInterface(NetworkInterface paramNetworkInterface)
/*     */   {
/* 149 */     setOption0(IP_MULTICAST_IF, paramNetworkInterface);
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isLoopbackModeDisabled()
/*     */   {
/* 155 */     return ((Boolean)getOption0(IP_MULTICAST_LOOP)).booleanValue();
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setLoopbackModeDisabled(boolean paramBoolean)
/*     */   {
/* 160 */     setOption0(IP_MULTICAST_LOOP, Boolean.valueOf(paramBoolean));
/* 161 */     return this;
/*     */   }
/*     */   
/*     */   public DatagramChannelConfig setAutoRead(boolean paramBoolean)
/*     */   {
/* 166 */     super.setAutoRead(paramBoolean);
/* 167 */     return this;
/*     */   }
/*     */   
/*     */   protected void autoReadCleared()
/*     */   {
/* 172 */     ((NioDatagramChannel)this.channel).setReadPending(false);
/*     */   }
/*     */   
/*     */   private Object getOption0(Object paramObject) {
/* 176 */     if (PlatformDependent.javaVersion() < 7) {
/* 177 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     try {
/* 180 */       return GET_OPTION.invoke(this.javaChannel, new Object[] { paramObject });
/*     */     } catch (Exception localException) {
/* 182 */       throw new ChannelException(localException);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setOption0(Object paramObject1, Object paramObject2)
/*     */   {
/* 188 */     if (PlatformDependent.javaVersion() < 7) {
/* 189 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     try {
/* 192 */       SET_OPTION.invoke(this.javaChannel, new Object[] { paramObject1, paramObject2 });
/*     */     } catch (Exception localException) {
/* 194 */       throw new ChannelException(localException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\socket\nio\NioDatagramChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */