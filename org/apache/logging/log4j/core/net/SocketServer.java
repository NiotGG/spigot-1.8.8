/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.OptionalDataException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.AbstractServer;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationFactory;
/*     */ import org.apache.logging.log4j.core.config.ConfigurationFactory.ConfigurationSource;
/*     */ import org.apache.logging.log4j.core.config.XMLConfiguration;
/*     */ import org.apache.logging.log4j.core.config.XMLConfigurationFactory;
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
/*     */ public class SocketServer
/*     */   extends AbstractServer
/*     */   implements Runnable
/*     */ {
/*     */   private final Logger logger;
/*     */   private static final int MAX_PORT = 65534;
/*  56 */   private volatile boolean isActive = true;
/*     */   
/*     */   private final ServerSocket server;
/*     */   
/*  60 */   private final ConcurrentMap<Long, SocketHandler> handlers = new ConcurrentHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SocketServer(int paramInt)
/*     */     throws IOException
/*     */   {
/*  68 */     this.server = new ServerSocket(paramInt);
/*  69 */     this.logger = LogManager.getLogger(getClass().getName() + '.' + paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void main(String[] paramArrayOfString)
/*     */     throws Exception
/*     */   {
/*  77 */     if ((paramArrayOfString.length < 1) || (paramArrayOfString.length > 2)) {
/*  78 */       System.err.println("Incorrect number of arguments");
/*  79 */       printUsage();
/*  80 */       return;
/*     */     }
/*  82 */     int i = Integer.parseInt(paramArrayOfString[0]);
/*  83 */     if ((i <= 0) || (i >= 65534)) {
/*  84 */       System.err.println("Invalid port number");
/*  85 */       printUsage();
/*  86 */       return;
/*     */     }
/*  88 */     if ((paramArrayOfString.length == 2) && (paramArrayOfString[1].length() > 0)) {
/*  89 */       ConfigurationFactory.setConfigurationFactory(new ServerConfigurationFactory(paramArrayOfString[1]));
/*     */     }
/*  91 */     SocketServer localSocketServer = new SocketServer(i);
/*  92 */     Thread localThread = new Thread(localSocketServer);
/*  93 */     localThread.start();
/*  94 */     Charset localCharset = Charset.defaultCharset();
/*  95 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(System.in, localCharset));
/*     */     for (;;) {
/*  97 */       String str = localBufferedReader.readLine();
/*  98 */       if ((str == null) || (str.equalsIgnoreCase("Quit")) || (str.equalsIgnoreCase("Stop")) || (str.equalsIgnoreCase("Exit"))) {
/*  99 */         localSocketServer.shutdown();
/* 100 */         localThread.join();
/* 101 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void printUsage() {
/* 107 */     System.out.println("Usage: ServerSocket port configFilePath");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void shutdown()
/*     */   {
/* 114 */     this.isActive = false;
/* 115 */     Thread.currentThread().interrupt();
/*     */   }
/*     */   
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     Object localObject;
/*     */     
/* 123 */     while (this.isActive) {
/*     */       try
/*     */       {
/* 126 */         Socket localSocket = this.server.accept();
/* 127 */         localSocket.setSoLinger(true, 0);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 133 */         localObject = new SocketHandler(localSocket);
/* 134 */         this.handlers.put(Long.valueOf(((SocketHandler)localObject).getId()), localObject);
/* 135 */         ((SocketHandler)localObject).start();
/*     */       } catch (IOException localIOException) {
/* 137 */         System.out.println("Exception encountered on accept. Ignoring. Stack Trace :");
/* 138 */         localIOException.printStackTrace();
/*     */       }
/*     */     }
/* 141 */     for (Iterator localIterator = this.handlers.entrySet().iterator(); localIterator.hasNext();) { localObject = (Map.Entry)localIterator.next();
/* 142 */       SocketHandler localSocketHandler = (SocketHandler)((Map.Entry)localObject).getValue();
/* 143 */       localSocketHandler.shutdown();
/*     */       try {
/* 145 */         localSocketHandler.join();
/*     */       }
/*     */       catch (InterruptedException localInterruptedException) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class SocketHandler
/*     */     extends Thread
/*     */   {
/*     */     private final ObjectInputStream ois;
/*     */     
/* 158 */     private boolean shutdown = false;
/*     */     
/*     */     public SocketHandler(Socket paramSocket) throws IOException
/*     */     {
/* 162 */       this.ois = new ObjectInputStream(paramSocket.getInputStream());
/*     */     }
/*     */     
/*     */     public void shutdown() {
/* 166 */       this.shutdown = true;
/* 167 */       interrupt();
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/* 172 */       int i = 0;
/*     */       try {
/*     */         try {
/* 175 */           while (!this.shutdown) {
/* 176 */             LogEvent localLogEvent = (LogEvent)this.ois.readObject();
/* 177 */             if (localLogEvent != null) {
/* 178 */               SocketServer.this.log(localLogEvent);
/*     */             }
/*     */           }
/*     */         } catch (EOFException localEOFException) {
/* 182 */           i = 1;
/*     */         } catch (OptionalDataException localOptionalDataException) {
/* 184 */           SocketServer.this.logger.error("OptionalDataException eof=" + localOptionalDataException.eof + " length=" + localOptionalDataException.length, localOptionalDataException);
/*     */         } catch (ClassNotFoundException localClassNotFoundException) {
/* 186 */           SocketServer.this.logger.error("Unable to locate LogEvent class", localClassNotFoundException);
/*     */         } catch (IOException localIOException) {
/* 188 */           SocketServer.this.logger.error("IOException encountered while reading from socket", localIOException);
/*     */         }
/* 190 */         if (i == 0) {
/*     */           try {
/* 192 */             this.ois.close();
/*     */           }
/*     */           catch (Exception localException) {}
/*     */         }
/*     */       }
/*     */       finally {
/* 198 */         SocketServer.this.handlers.remove(Long.valueOf(getId()));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class ServerConfigurationFactory
/*     */     extends XMLConfigurationFactory
/*     */   {
/*     */     private final String path;
/*     */     
/*     */     public ServerConfigurationFactory(String paramString)
/*     */     {
/* 211 */       this.path = paramString;
/*     */     }
/*     */     
/*     */     public Configuration getConfiguration(String paramString, URI paramURI)
/*     */     {
/* 216 */       if ((this.path != null) && (this.path.length() > 0)) {
/* 217 */         File localFile = null;
/* 218 */         ConfigurationFactory.ConfigurationSource localConfigurationSource = null;
/*     */         try {
/* 220 */           localFile = new File(this.path);
/* 221 */           FileInputStream localFileInputStream = new FileInputStream(localFile);
/* 222 */           localConfigurationSource = new ConfigurationFactory.ConfigurationSource(localFileInputStream, localFile);
/*     */         }
/*     */         catch (FileNotFoundException localFileNotFoundException) {}
/*     */         
/* 226 */         if (localConfigurationSource == null) {
/*     */           try {
/* 228 */             URL localURL = new URL(this.path);
/* 229 */             localConfigurationSource = new ConfigurationFactory.ConfigurationSource(localURL.openStream(), this.path);
/*     */           }
/*     */           catch (MalformedURLException localMalformedURLException) {}catch (IOException localIOException) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 238 */           if (localConfigurationSource != null) {
/* 239 */             return new XMLConfiguration(localConfigurationSource);
/*     */           }
/*     */         }
/*     */         catch (Exception localException) {}
/*     */         
/* 244 */         System.err.println("Unable to process configuration at " + this.path + ", using default.");
/*     */       }
/* 246 */       return super.getConfiguration(paramString, paramURI);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\SocketServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */