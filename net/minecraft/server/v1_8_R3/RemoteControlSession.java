/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketTimeoutException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class RemoteControlSession
/*     */   extends RemoteConnectionThread
/*     */ {
/*  16 */   private static final Logger h = ;
/*     */   
/*     */ 
/*     */   private boolean i;
/*     */   
/*     */ 
/*     */   private Socket j;
/*     */   
/*  24 */   private byte[] k = new byte['ִ'];
/*     */   private String l;
/*     */   
/*     */   RemoteControlSession(IMinecraftServer paramIMinecraftServer, Socket paramSocket) {
/*  28 */     super(paramIMinecraftServer, "RCON Client");
/*  29 */     this.j = paramSocket;
/*     */     try
/*     */     {
/*  32 */       this.j.setSoTimeout(0);
/*     */     } catch (Exception localException) {
/*  34 */       this.a = false;
/*     */     }
/*     */     
/*  37 */     this.l = paramIMinecraftServer.a("rcon.password", "");
/*  38 */     b("Rcon connection from: " + paramSocket.getInetAddress());
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*     */     try {
/*  44 */       while (this.a) {
/*  45 */         BufferedInputStream localBufferedInputStream = new BufferedInputStream(this.j.getInputStream());
/*  46 */         int m = localBufferedInputStream.read(this.k, 0, 1460);
/*     */         
/*  48 */         if (10 > m) {
/*     */           return;
/*     */         }
/*     */         
/*  52 */         int n = 0;
/*  53 */         int i1 = StatusChallengeUtils.b(this.k, 0, m);
/*  54 */         if (i1 != m - 4) {
/*     */           return;
/*     */         }
/*     */         
/*  58 */         n += 4;
/*  59 */         int i2 = StatusChallengeUtils.b(this.k, n, m);
/*  60 */         n += 4;
/*     */         
/*  62 */         int i3 = StatusChallengeUtils.b(this.k, n);
/*  63 */         n += 4;
/*  64 */         switch (i3) {
/*     */         case 3: 
/*  66 */           String str1 = StatusChallengeUtils.a(this.k, n, m);
/*  67 */           n += str1.length();
/*  68 */           if ((0 != str1.length()) && (str1.equals(this.l))) {
/*  69 */             this.i = true;
/*  70 */             a(i2, 2, "");
/*     */           } else {
/*  72 */             this.i = false;
/*  73 */             f();
/*     */           }
/*  75 */           break;
/*     */         case 2: 
/*  77 */           if (this.i) {
/*  78 */             String str2 = StatusChallengeUtils.a(this.k, n, m);
/*     */             try {
/*  80 */               a(i2, this.b.executeRemoteCommand(str2));
/*     */             } catch (Exception localException2) {
/*  82 */               a(i2, "Error executing: " + str2 + " (" + localException2.getMessage() + ")");
/*     */             }
/*     */           } else {
/*  85 */             f();
/*     */           }
/*  87 */           break;
/*     */         default: 
/*  89 */           a(i2, String.format("Unknown request %s", new Object[] { Integer.toHexString(i3) }));
/*     */         
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*     */     catch (SocketTimeoutException localSocketTimeoutException) {}catch (IOException localIOException) {}catch (Exception localException1)
/*     */     {
/*  97 */       h.error("Exception whilst parsing RCON input", localException1);
/*     */     } finally {
/*  99 */       g();
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(int paramInt1, int paramInt2, String paramString)
/*     */     throws IOException
/*     */   {
/* 106 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(1248);
/* 107 */     DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
/* 108 */     byte[] arrayOfByte = paramString.getBytes("UTF-8");
/* 109 */     localDataOutputStream.writeInt(Integer.reverseBytes(arrayOfByte.length + 10));
/* 110 */     localDataOutputStream.writeInt(Integer.reverseBytes(paramInt1));
/* 111 */     localDataOutputStream.writeInt(Integer.reverseBytes(paramInt2));
/* 112 */     localDataOutputStream.write(arrayOfByte);
/* 113 */     localDataOutputStream.write(0);
/* 114 */     localDataOutputStream.write(0);
/* 115 */     this.j.getOutputStream().write(localByteArrayOutputStream.toByteArray());
/*     */   }
/*     */   
/*     */   private void f() throws IOException {
/* 119 */     a(-1, 2, "");
/*     */   }
/*     */   
/*     */   private void a(int paramInt, String paramString) throws IOException {
/* 123 */     int m = paramString.length();
/*     */     for (;;)
/*     */     {
/* 126 */       int n = 4096 <= m ? 4096 : m;
/* 127 */       a(paramInt, 0, paramString.substring(0, n));
/* 128 */       paramString = paramString.substring(n);
/* 129 */       m = paramString.length();
/* 130 */       if (0 == m) {
/*     */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void g()
/*     */   {
/* 143 */     if (null == this.j) {
/* 144 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 148 */       this.j.close();
/*     */     } catch (IOException localIOException) {
/* 150 */       c("IO: " + localIOException.getMessage());
/*     */     }
/* 152 */     this.j = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RemoteControlSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */