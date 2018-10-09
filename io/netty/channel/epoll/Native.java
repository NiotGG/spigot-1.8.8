/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.channel.DefaultFileRegion;
/*     */ import io.netty.util.internal.NativeLibraryLoader;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Locale;
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
/*     */ final class Native
/*     */ {
/*  38 */   private static final byte[] IPV4_MAPPED_IPV6_PREFIX = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1 };
/*     */   public static final int EPOLLIN = 1;
/*     */   public static final int EPOLLOUT = 2;
/*     */   
/*  42 */   static { String str = SystemPropertyUtil.get("os.name").toLowerCase(Locale.UK).trim();
/*  43 */     if (!str.startsWith("linux")) {
/*  44 */       throw new IllegalStateException("Only supported on Linux");
/*     */     }
/*  46 */     NativeLibraryLoader.load("netty-transport-native-epoll", PlatformDependent.getClassLoader(Native.class));
/*     */   }
/*     */   
/*     */ 
/*     */   public static final int EPOLLACCEPT = 4;
/*     */   
/*     */   public static final int EPOLLRDHUP = 8;
/*     */   
/*  54 */   public static final int IOV_MAX = iovMax();
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
/*     */   public static int sendTo(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3, InetAddress paramInetAddress, int paramInt4)
/*     */     throws IOException
/*     */   {
/*     */     byte[] arrayOfByte;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     int i;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  87 */     if ((paramInetAddress instanceof Inet6Address)) {
/*  88 */       arrayOfByte = paramInetAddress.getAddress();
/*  89 */       i = ((Inet6Address)paramInetAddress).getScopeId();
/*     */     }
/*     */     else {
/*  92 */       i = 0;
/*  93 */       arrayOfByte = ipv4MappedIpv6Address(paramInetAddress.getAddress());
/*     */     }
/*  95 */     return sendTo(paramInt1, paramByteBuffer, paramInt2, paramInt3, arrayOfByte, i, paramInt4);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int sendToAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3, InetAddress paramInetAddress, int paramInt4)
/*     */     throws IOException
/*     */   {
/*     */     byte[] arrayOfByte;
/*     */     
/*     */     int i;
/*     */     
/* 107 */     if ((paramInetAddress instanceof Inet6Address)) {
/* 108 */       arrayOfByte = paramInetAddress.getAddress();
/* 109 */       i = ((Inet6Address)paramInetAddress).getScopeId();
/*     */     }
/*     */     else {
/* 112 */       i = 0;
/* 113 */       arrayOfByte = ipv4MappedIpv6Address(paramInetAddress.getAddress());
/*     */     }
/* 115 */     return sendToAddress(paramInt1, paramLong, paramInt2, paramInt3, arrayOfByte, i, paramInt4);
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
/*     */   public static int socketStreamFd()
/*     */   {
/*     */     try
/*     */     {
/* 130 */       return socketStream();
/*     */     } catch (IOException localIOException) {
/* 132 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int socketDgramFd() {
/*     */     try {
/* 138 */       return socketDgram();
/*     */     } catch (IOException localIOException) {
/* 140 */       throw new ChannelException(localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void bind(int paramInt1, InetAddress paramInetAddress, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 147 */     NativeInetAddress localNativeInetAddress = toNativeInetAddress(paramInetAddress);
/* 148 */     bind(paramInt1, localNativeInetAddress.address, localNativeInetAddress.scopeId, paramInt2);
/*     */   }
/*     */   
/*     */   private static byte[] ipv4MappedIpv6Address(byte[] paramArrayOfByte) {
/* 152 */     byte[] arrayOfByte = new byte[16];
/* 153 */     System.arraycopy(IPV4_MAPPED_IPV6_PREFIX, 0, arrayOfByte, 0, IPV4_MAPPED_IPV6_PREFIX.length);
/* 154 */     System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 12, paramArrayOfByte.length);
/* 155 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public static boolean connect(int paramInt1, InetAddress paramInetAddress, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 161 */     NativeInetAddress localNativeInetAddress = toNativeInetAddress(paramInetAddress);
/* 162 */     return connect(paramInt1, localNativeInetAddress.address, localNativeInetAddress.scopeId, paramInt2);
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
/*     */ 
/*     */   private static NativeInetAddress toNativeInetAddress(InetAddress paramInetAddress)
/*     */   {
/* 202 */     byte[] arrayOfByte = paramInetAddress.getAddress();
/* 203 */     if ((paramInetAddress instanceof Inet6Address)) {
/* 204 */       return new NativeInetAddress(arrayOfByte, ((Inet6Address)paramInetAddress).getScopeId());
/*     */     }
/*     */     
/* 207 */     return new NativeInetAddress(ipv4MappedIpv6Address(arrayOfByte));
/*     */   }
/*     */   
/*     */   public static native int eventFd();
/*     */   
/*     */   public static native void eventFdWrite(int paramInt, long paramLong);
/*     */   
/*     */   public static native void eventFdRead(int paramInt);
/*     */   
/* 216 */   private static class NativeInetAddress { NativeInetAddress(byte[] paramArrayOfByte, int paramInt) { this.address = paramArrayOfByte;
/* 217 */       this.scopeId = paramInt; }
/*     */     
/*     */     final byte[] address;
/*     */     final int scopeId;
/* 221 */     NativeInetAddress(byte[] paramArrayOfByte) { this(paramArrayOfByte, 0); }
/*     */   }
/*     */   
/*     */   public static native int epollCreate();
/*     */   
/*     */   public static native int epollWait(int paramInt1, long[] paramArrayOfLong, int paramInt2);
/*     */   
/*     */   public static native void epollCtlAdd(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   public static native void epollCtlMod(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   public static native void epollCtlDel(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void close(int paramInt)
/*     */     throws IOException;
/*     */   
/*     */   public static native int write(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3)
/*     */     throws IOException;
/*     */   
/*     */   public static native int writeAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3)
/*     */     throws IOException;
/*     */   
/*     */   public static native long writev(int paramInt1, ByteBuffer[] paramArrayOfByteBuffer, int paramInt2, int paramInt3)
/*     */     throws IOException;
/*     */   
/*     */   public static native long writevAddresses(int paramInt1, long paramLong, int paramInt2)
/*     */     throws IOException;
/*     */   
/*     */   public static native int read(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3)
/*     */     throws IOException;
/*     */   
/*     */   public static native int readAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3)
/*     */     throws IOException;
/*     */   
/*     */   public static native long sendfile(int paramInt, DefaultFileRegion paramDefaultFileRegion, long paramLong1, long paramLong2, long paramLong3)
/*     */     throws IOException;
/*     */   
/*     */   private static native int sendTo(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4, int paramInt5)
/*     */     throws IOException;
/*     */   
/*     */   private static native int sendToAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3, byte[] paramArrayOfByte, int paramInt4, int paramInt5)
/*     */     throws IOException;
/*     */   
/*     */   public static native EpollDatagramChannel.DatagramSocketAddress recvFrom(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3)
/*     */     throws IOException;
/*     */   
/*     */   public static native EpollDatagramChannel.DatagramSocketAddress recvFromAddress(int paramInt1, long paramLong, int paramInt2, int paramInt3)
/*     */     throws IOException;
/*     */   
/*     */   private static native int socketStream()
/*     */     throws IOException;
/*     */   
/*     */   private static native int socketDgram()
/*     */     throws IOException;
/*     */   
/*     */   public static native void bind(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */     throws IOException;
/*     */   
/*     */   public static native void listen(int paramInt1, int paramInt2)
/*     */     throws IOException;
/*     */   
/*     */   public static native boolean connect(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*     */     throws IOException;
/*     */   
/*     */   public static native boolean finishConnect(int paramInt)
/*     */     throws IOException;
/*     */   
/*     */   public static native InetSocketAddress remoteAddress(int paramInt);
/*     */   
/*     */   public static native InetSocketAddress localAddress(int paramInt);
/*     */   
/*     */   public static native int accept(int paramInt)
/*     */     throws IOException;
/*     */   
/*     */   public static native void shutdown(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws IOException;
/*     */   
/*     */   public static native int getReceiveBufferSize(int paramInt);
/*     */   
/*     */   public static native int getSendBufferSize(int paramInt);
/*     */   
/*     */   public static native int isKeepAlive(int paramInt);
/*     */   
/*     */   public static native int isReuseAddress(int paramInt);
/*     */   
/*     */   public static native int isReusePort(int paramInt);
/*     */   
/*     */   public static native int isTcpNoDelay(int paramInt);
/*     */   
/*     */   public static native int isTcpCork(int paramInt);
/*     */   
/*     */   public static native int getSoLinger(int paramInt);
/*     */   
/*     */   public static native int getTrafficClass(int paramInt);
/*     */   
/*     */   public static native int isBroadcast(int paramInt);
/*     */   
/*     */   public static native int getTcpKeepIdle(int paramInt);
/*     */   
/*     */   public static native int getTcpKeepIntvl(int paramInt);
/*     */   
/*     */   public static native int getTcpKeepCnt(int paramInt);
/*     */   
/*     */   public static native void setKeepAlive(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setReceiveBufferSize(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setReuseAddress(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setReusePort(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setSendBufferSize(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setTcpNoDelay(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setTcpCork(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setSoLinger(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setTrafficClass(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setBroadcast(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setTcpKeepIdle(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setTcpKeepIntvl(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native void setTcpKeepCnt(int paramInt1, int paramInt2);
/*     */   
/*     */   public static native String kernelVersion();
/*     */   
/*     */   private static native int iovMax();
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\channel\epoll\Native.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */