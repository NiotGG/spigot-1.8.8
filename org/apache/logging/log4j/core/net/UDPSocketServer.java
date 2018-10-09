/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.OptionalDataException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
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
/*     */ 
/*     */ 
/*     */ public class UDPSocketServer
/*     */   extends AbstractServer
/*     */   implements Runnable
/*     */ {
/*     */   private final Logger logger;
/*     */   private static final int MAX_PORT = 65534;
/*  53 */   private volatile boolean isActive = true;
/*     */   
/*     */ 
/*     */   private final DatagramSocket server;
/*     */   
/*  58 */   private final int maxBufferSize = 67584;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UDPSocketServer(int paramInt)
/*     */     throws IOException
/*     */   {
/*  69 */     this.server = new DatagramSocket(paramInt);
/*  70 */     this.logger = LogManager.getLogger(getClass().getName() + '.' + paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] paramArrayOfString)
/*     */     throws Exception
/*     */   {
/*  82 */     if ((paramArrayOfString.length < 1) || (paramArrayOfString.length > 2)) {
/*  83 */       System.err.println("Incorrect number of arguments");
/*  84 */       printUsage();
/*  85 */       return;
/*     */     }
/*  87 */     int i = Integer.parseInt(paramArrayOfString[0]);
/*  88 */     if ((i <= 0) || (i >= 65534)) {
/*  89 */       System.err.println("Invalid port number");
/*  90 */       printUsage();
/*  91 */       return;
/*     */     }
/*  93 */     if ((paramArrayOfString.length == 2) && (paramArrayOfString[1].length() > 0)) {
/*  94 */       ConfigurationFactory.setConfigurationFactory(new ServerConfigurationFactory(paramArrayOfString[1]));
/*     */     }
/*  96 */     UDPSocketServer localUDPSocketServer = new UDPSocketServer(i);
/*  97 */     Thread localThread = new Thread(localUDPSocketServer);
/*  98 */     localThread.start();
/*  99 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(System.in));
/*     */     for (;;) {
/* 101 */       String str = localBufferedReader.readLine();
/* 102 */       if ((str == null) || (str.equalsIgnoreCase("Quit")) || (str.equalsIgnoreCase("Stop")) || (str.equalsIgnoreCase("Exit"))) {
/* 103 */         localUDPSocketServer.shutdown();
/* 104 */         localThread.join();
/* 105 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void printUsage() {
/* 111 */     System.out.println("Usage: ServerSocket port configFilePath");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void shutdown()
/*     */   {
/* 118 */     this.isActive = false;
/* 119 */     Thread.currentThread().interrupt();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/* 127 */     while (this.isActive) {
/*     */       try {
/* 129 */         byte[] arrayOfByte = new byte[67584];
/* 130 */         DatagramPacket localDatagramPacket = new DatagramPacket(arrayOfByte, arrayOfByte.length);
/* 131 */         this.server.receive(localDatagramPacket);
/* 132 */         ObjectInputStream localObjectInputStream = new ObjectInputStream(new ByteArrayInputStream(localDatagramPacket.getData(), localDatagramPacket.getOffset(), localDatagramPacket.getLength()));
/* 133 */         LogEvent localLogEvent = (LogEvent)localObjectInputStream.readObject();
/* 134 */         if (localLogEvent != null) {
/* 135 */           log(localLogEvent);
/*     */         }
/*     */       } catch (OptionalDataException localOptionalDataException) {
/* 138 */         this.logger.error("OptionalDataException eof=" + localOptionalDataException.eof + " length=" + localOptionalDataException.length, localOptionalDataException);
/*     */       } catch (ClassNotFoundException localClassNotFoundException) {
/* 140 */         this.logger.error("Unable to locate LogEvent class", localClassNotFoundException);
/*     */       } catch (EOFException localEOFException) {
/* 142 */         this.logger.info("EOF encountered");
/*     */       } catch (IOException localIOException) {
/* 144 */         this.logger.error("Exception encountered on accept. Ignoring. Stack Trace :", localIOException);
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
/* 157 */       this.path = paramString;
/*     */     }
/*     */     
/*     */     public Configuration getConfiguration(String paramString, URI paramURI)
/*     */     {
/* 162 */       if ((this.path != null) && (this.path.length() > 0)) {
/* 163 */         File localFile = null;
/* 164 */         ConfigurationFactory.ConfigurationSource localConfigurationSource = null;
/*     */         try {
/* 166 */           localFile = new File(this.path);
/* 167 */           FileInputStream localFileInputStream = new FileInputStream(localFile);
/* 168 */           localConfigurationSource = new ConfigurationFactory.ConfigurationSource(localFileInputStream, localFile);
/*     */         }
/*     */         catch (FileNotFoundException localFileNotFoundException) {}
/*     */         
/* 172 */         if (localConfigurationSource == null) {
/*     */           try {
/* 174 */             URL localURL = new URL(this.path);
/* 175 */             localConfigurationSource = new ConfigurationFactory.ConfigurationSource(localURL.openStream(), this.path);
/*     */           }
/*     */           catch (MalformedURLException localMalformedURLException) {}catch (IOException localIOException) {}
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 184 */           if (localConfigurationSource != null) {
/* 185 */             return new XMLConfiguration(localConfigurationSource);
/*     */           }
/*     */         }
/*     */         catch (Exception localException) {}
/*     */         
/* 190 */         System.err.println("Unable to process configuration at " + this.path + ", using default.");
/*     */       }
/* 192 */       return super.getConfiguration(paramString, paramURI);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\UDPSocketServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */