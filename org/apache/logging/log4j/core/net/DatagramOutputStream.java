/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ public class DatagramOutputStream
/*     */   extends OutputStream
/*     */ {
/*  39 */   protected static final Logger LOGGER = ;
/*     */   
/*     */   private static final int SHIFT_1 = 8;
/*     */   
/*     */   private static final int SHIFT_2 = 16;
/*     */   
/*     */   private static final int SHIFT_3 = 24;
/*     */   
/*     */   private DatagramSocket ds;
/*     */   
/*     */   private final InetAddress address;
/*     */   
/*     */   private final int port;
/*     */   
/*     */   private byte[] data;
/*     */   
/*     */   private final byte[] header;
/*     */   private final byte[] footer;
/*     */   
/*     */   public DatagramOutputStream(String paramString, int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */   {
/*  60 */     this.port = paramInt;
/*  61 */     this.header = paramArrayOfByte1;
/*  62 */     this.footer = paramArrayOfByte2;
/*     */     String str;
/*  64 */     try { this.address = InetAddress.getByName(paramString);
/*     */     } catch (UnknownHostException localUnknownHostException) {
/*  66 */       str = "Could not find host " + paramString;
/*  67 */       LOGGER.error(str, localUnknownHostException);
/*  68 */       throw new AppenderLoggingException(str, localUnknownHostException);
/*     */     }
/*     */     try
/*     */     {
/*  72 */       this.ds = new DatagramSocket();
/*     */     } catch (SocketException localSocketException) {
/*  74 */       str = "Could not instantiate DatagramSocket to " + paramString;
/*  75 */       LOGGER.error(str, localSocketException);
/*  76 */       throw new AppenderLoggingException(str, localSocketException);
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */   {
/*  82 */     copy(paramArrayOfByte, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public synchronized void write(int paramInt) throws IOException
/*     */   {
/*  87 */     copy(new byte[] { (byte)(paramInt >>> 24), (byte)(paramInt >>> 16), (byte)(paramInt >>> 8), (byte)paramInt }, 0, 4);
/*     */   }
/*     */   
/*     */   public synchronized void write(byte[] paramArrayOfByte) throws IOException
/*     */   {
/*  92 */     copy(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   public synchronized void flush() throws IOException
/*     */   {
/*     */     try {
/*  98 */       if ((this.data != null) && (this.ds != null) && (this.address != null)) {
/*  99 */         if (this.footer != null) {
/* 100 */           copy(this.footer, 0, this.footer.length);
/*     */         }
/* 102 */         DatagramPacket localDatagramPacket = new DatagramPacket(this.data, this.data.length, this.address, this.port);
/* 103 */         this.ds.send(localDatagramPacket);
/*     */       }
/*     */     } finally {
/* 106 */       this.data = null;
/* 107 */       if (this.header != null) {
/* 108 */         copy(this.header, 0, this.header.length);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void close() throws IOException
/*     */   {
/* 115 */     if (this.ds != null) {
/* 116 */       if (this.data != null) {
/* 117 */         flush();
/*     */       }
/* 119 */       this.ds.close();
/* 120 */       this.ds = null;
/*     */     }
/*     */   }
/*     */   
/*     */   private void copy(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/* 125 */     int i = this.data == null ? 0 : this.data.length;
/* 126 */     byte[] arrayOfByte = new byte[paramInt2 + i];
/* 127 */     if (this.data != null) {
/* 128 */       System.arraycopy(this.data, 0, arrayOfByte, 0, this.data.length);
/*     */     }
/* 130 */     System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, i, paramInt2);
/* 131 */     this.data = arrayOfByte;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\DatagramOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */