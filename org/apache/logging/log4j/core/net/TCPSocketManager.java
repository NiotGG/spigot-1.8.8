/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.OutputStreamManager;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
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
/*     */ public class TCPSocketManager
/*     */   extends AbstractSocketManager
/*     */ {
/*     */   public static final int DEFAULT_RECONNECTION_DELAY = 30000;
/*     */   private static final int DEFAULT_PORT = 4560;
/*  50 */   private static final TCPSocketManagerFactory FACTORY = new TCPSocketManagerFactory();
/*     */   
/*     */   private final int reconnectionDelay;
/*     */   
/*  54 */   private Reconnector connector = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Socket socket;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final boolean retry;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final boolean immediateFail;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TCPSocketManager(String paramString1, OutputStream paramOutputStream, Socket paramSocket, InetAddress paramInetAddress, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  77 */     super(paramString1, paramOutputStream, paramInetAddress, paramString2, paramInt1, paramLayout);
/*  78 */     this.reconnectionDelay = paramInt2;
/*  79 */     this.socket = paramSocket;
/*  80 */     this.immediateFail = paramBoolean;
/*  81 */     this.retry = (paramInt2 > 0);
/*  82 */     if (paramSocket == null) {
/*  83 */       this.connector = new Reconnector(this);
/*  84 */       this.connector.setDaemon(true);
/*  85 */       this.connector.setPriority(1);
/*  86 */       this.connector.start();
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
/*     */   public static TCPSocketManager getSocketManager(String paramString, int paramInt1, int paramInt2, boolean paramBoolean, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  99 */     if (Strings.isEmpty(paramString)) {
/* 100 */       throw new IllegalArgumentException("A host name is required");
/*     */     }
/* 102 */     if (paramInt1 <= 0) {
/* 103 */       paramInt1 = 4560;
/*     */     }
/* 105 */     if (paramInt2 == 0) {
/* 106 */       paramInt2 = 30000;
/*     */     }
/* 108 */     return (TCPSocketManager)getManager("TCP:" + paramString + ":" + paramInt1, new FactoryData(paramString, paramInt1, paramInt2, paramBoolean, paramLayout), FACTORY);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/* 114 */     if (this.socket == null) {
/* 115 */       if ((this.connector != null) && (!this.immediateFail)) {
/* 116 */         this.connector.latch();
/*     */       }
/* 118 */       if (this.socket == null) {
/* 119 */         String str1 = "Error writing to " + getName() + " socket not available";
/* 120 */         throw new AppenderLoggingException(str1);
/*     */       }
/*     */     }
/* 123 */     synchronized (this) {
/*     */       try {
/* 125 */         getOutputStream().write(paramArrayOfByte, paramInt1, paramInt2);
/*     */       } catch (IOException localIOException) {
/* 127 */         if ((this.retry) && (this.connector == null)) {
/* 128 */           this.connector = new Reconnector(this);
/* 129 */           this.connector.setDaemon(true);
/* 130 */           this.connector.setPriority(1);
/* 131 */           this.connector.start();
/*     */         }
/* 133 */         String str2 = "Error writing to " + getName();
/* 134 */         throw new AppenderLoggingException(str2, localIOException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected synchronized void close()
/*     */   {
/* 141 */     super.close();
/* 142 */     if (this.connector != null) {
/* 143 */       this.connector.shutdown();
/* 144 */       this.connector.interrupt();
/* 145 */       this.connector = null;
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
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 158 */     HashMap localHashMap = new HashMap(super.getContentFormat());
/* 159 */     localHashMap.put("protocol", "tcp");
/* 160 */     localHashMap.put("direction", "out");
/* 161 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class Reconnector
/*     */     extends Thread
/*     */   {
/* 169 */     private final CountDownLatch latch = new CountDownLatch(1);
/*     */     
/* 171 */     private boolean shutdown = false;
/*     */     private final Object owner;
/*     */     
/*     */     public Reconnector(OutputStreamManager paramOutputStreamManager)
/*     */     {
/* 176 */       this.owner = paramOutputStreamManager;
/*     */     }
/*     */     
/*     */     public void latch() {
/*     */       try {
/* 181 */         this.latch.await();
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */     }
/*     */     
/*     */     public void shutdown()
/*     */     {
/* 188 */       this.shutdown = true;
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 193 */       while (!this.shutdown) {
/*     */         try {
/* 195 */           sleep(TCPSocketManager.this.reconnectionDelay);
/* 196 */           Socket localSocket = TCPSocketManager.this.createSocket(TCPSocketManager.this.address, TCPSocketManager.this.port);
/* 197 */           OutputStream localOutputStream = localSocket.getOutputStream();
/* 198 */           synchronized (this.owner) {
/*     */             try {
/* 200 */               TCPSocketManager.this.getOutputStream().close();
/*     */             }
/*     */             catch (IOException localIOException2) {}
/*     */             
/*     */ 
/* 205 */             TCPSocketManager.this.setOutputStream(localOutputStream);
/* 206 */             TCPSocketManager.this.socket = localSocket;
/* 207 */             TCPSocketManager.this.connector = null;
/* 208 */             this.shutdown = true;
/*     */           }
/* 210 */           TCPSocketManager.LOGGER.debug("Connection to " + TCPSocketManager.this.host + ":" + TCPSocketManager.this.port + " reestablished.");
/*     */         } catch (InterruptedException localInterruptedException) {
/* 212 */           TCPSocketManager.LOGGER.debug("Reconnection interrupted.");
/*     */         } catch (ConnectException localConnectException) {
/* 214 */           TCPSocketManager.LOGGER.debug(TCPSocketManager.this.host + ":" + TCPSocketManager.this.port + " refused connection");
/*     */         } catch (IOException localIOException1) {
/* 216 */           TCPSocketManager.LOGGER.debug("Unable to reconnect to " + TCPSocketManager.this.host + ":" + TCPSocketManager.this.port);
/*     */         } finally {
/* 218 */           this.latch.countDown();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected Socket createSocket(InetAddress paramInetAddress, int paramInt) throws IOException {
/* 225 */     return createSocket(paramInetAddress.getHostName(), paramInt);
/*     */   }
/*     */   
/*     */   protected Socket createSocket(String paramString, int paramInt) throws IOException {
/* 229 */     return new Socket(paramString, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String host;
/*     */     
/*     */     private final int port;
/*     */     private final int delay;
/*     */     private final boolean immediateFail;
/*     */     private final Layout<? extends Serializable> layout;
/*     */     
/*     */     public FactoryData(String paramString, int paramInt1, int paramInt2, boolean paramBoolean, Layout<? extends Serializable> paramLayout)
/*     */     {
/* 244 */       this.host = paramString;
/* 245 */       this.port = paramInt1;
/* 246 */       this.delay = paramInt2;
/* 247 */       this.immediateFail = paramBoolean;
/* 248 */       this.layout = paramLayout;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected static class TCPSocketManagerFactory
/*     */     implements ManagerFactory<TCPSocketManager, TCPSocketManager.FactoryData>
/*     */   {
/*     */     public TCPSocketManager createManager(String paramString, TCPSocketManager.FactoryData paramFactoryData)
/*     */     {
/*     */       InetAddress localInetAddress;
/*     */       
/*     */       try
/*     */       {
/* 262 */         localInetAddress = InetAddress.getByName(TCPSocketManager.FactoryData.access$900(paramFactoryData));
/*     */       } catch (UnknownHostException localUnknownHostException) {
/* 264 */         TCPSocketManager.LOGGER.error("Could not find address of " + TCPSocketManager.FactoryData.access$900(paramFactoryData), localUnknownHostException);
/* 265 */         return null;
/*     */       }
/*     */       Object localObject;
/* 268 */       try { Socket localSocket = new Socket(TCPSocketManager.FactoryData.access$900(paramFactoryData), TCPSocketManager.FactoryData.access$1100(paramFactoryData));
/* 269 */         localObject = localSocket.getOutputStream();
/* 270 */         return new TCPSocketManager(paramString, (OutputStream)localObject, localSocket, localInetAddress, TCPSocketManager.FactoryData.access$900(paramFactoryData), TCPSocketManager.FactoryData.access$1100(paramFactoryData), TCPSocketManager.FactoryData.access$1200(paramFactoryData), TCPSocketManager.FactoryData.access$1300(paramFactoryData), TCPSocketManager.FactoryData.access$1400(paramFactoryData));
/*     */       }
/*     */       catch (IOException localIOException) {
/* 273 */         TCPSocketManager.LOGGER.error("TCPSocketManager (" + paramString + ") " + localIOException);
/* 274 */         localObject = new ByteArrayOutputStream();
/*     */         
/* 276 */         if (TCPSocketManager.FactoryData.access$1200(paramFactoryData) == 0)
/* 277 */           return null;
/*     */       }
/* 279 */       return new TCPSocketManager(paramString, (OutputStream)localObject, null, localInetAddress, TCPSocketManager.FactoryData.access$900(paramFactoryData), TCPSocketManager.FactoryData.access$1100(paramFactoryData), TCPSocketManager.FactoryData.access$1200(paramFactoryData), TCPSocketManager.FactoryData.access$1300(paramFactoryData), TCPSocketManager.FactoryData.access$1400(paramFactoryData));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\TCPSocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */