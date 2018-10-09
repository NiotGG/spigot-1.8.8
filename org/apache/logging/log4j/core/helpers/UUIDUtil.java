/*     */ package org.apache.logging.log4j.core.helpers;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public final class UUIDUtil
/*     */ {
/*     */   public static final String UUID_SEQUENCE = "org.apache.logging.log4j.uuidSequence";
/*     */   private static final String ASSIGNED_SEQUENCES = "org.apache.logging.log4j.assignedSequences";
/*  44 */   private static AtomicInteger count = new AtomicInteger(0);
/*     */   
/*     */   private static final long TYPE1 = 4096L;
/*     */   
/*     */   private static final byte VARIANT = -128;
/*     */   
/*     */   private static final int SEQUENCE_MASK = 16383;
/*     */   
/*     */   private static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 122192928000000000L;
/*     */   
/*  54 */   private static long uuidSequence = PropertiesUtil.getProperties().getLongProperty("org.apache.logging.log4j.uuidSequence", 0L);
/*     */   
/*     */   private static long least;
/*     */   private static final long LOW_MASK = 4294967295L;
/*     */   private static final long MID_MASK = 281470681743360L;
/*     */   private static final long HIGH_MASK = 1152640029630136320L;
/*     */   private static final int NODE_SIZE = 8;
/*     */   private static final int SHIFT_2 = 16;
/*     */   private static final int SHIFT_4 = 32;
/*     */   private static final int SHIFT_6 = 48;
/*     */   private static final int HUNDRED_NANOS_PER_MILLI = 10000;
/*     */   
/*     */   static
/*     */   {
/*  68 */     byte[] arrayOfByte = null;
/*     */     try {
/*  70 */       InetAddress localInetAddress = InetAddress.getLocalHost();
/*     */       try {
/*  72 */         NetworkInterface localNetworkInterface = NetworkInterface.getByInetAddress(localInetAddress);
/*  73 */         Object localObject1; if ((localNetworkInterface != null) && (!localNetworkInterface.isLoopback()) && (localNetworkInterface.isUp())) {
/*  74 */           localObject1 = localNetworkInterface.getClass().getMethod("getHardwareAddress", new Class[0]);
/*  75 */           if (localObject1 != null) {
/*  76 */             arrayOfByte = (byte[])((Method)localObject1).invoke(localNetworkInterface, new Object[0]);
/*     */           }
/*     */         }
/*     */         
/*  80 */         if (arrayOfByte == null) {
/*  81 */           localObject1 = NetworkInterface.getNetworkInterfaces();
/*  82 */           while ((((Enumeration)localObject1).hasMoreElements()) && (arrayOfByte == null)) {
/*  83 */             localNetworkInterface = (NetworkInterface)((Enumeration)localObject1).nextElement();
/*  84 */             if ((localNetworkInterface != null) && (localNetworkInterface.isUp()) && (!localNetworkInterface.isLoopback())) {
/*  85 */               localObject2 = localNetworkInterface.getClass().getMethod("getHardwareAddress", new Class[0]);
/*  86 */               if (localObject2 != null) {
/*  87 */                 arrayOfByte = (byte[])((Method)localObject2).invoke(localNetworkInterface, new Object[0]);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       } catch (Exception localException) {
/*  93 */         localException.printStackTrace();
/*     */       }
/*     */       
/*  96 */       if ((arrayOfByte == null) || (arrayOfByte.length == 0)) {
/*  97 */         arrayOfByte = localInetAddress.getAddress();
/*     */       }
/*     */     }
/*     */     catch (UnknownHostException localUnknownHostException) {}
/*     */     
/* 102 */     SecureRandom localSecureRandom = new SecureRandom();
/* 103 */     if ((arrayOfByte == null) || (arrayOfByte.length == 0)) {
/* 104 */       arrayOfByte = new byte[6];
/* 105 */       localSecureRandom.nextBytes(arrayOfByte);
/*     */     }
/* 107 */     int i = arrayOfByte.length >= 6 ? 6 : arrayOfByte.length;
/* 108 */     int j = arrayOfByte.length >= 6 ? arrayOfByte.length - 6 : 0;
/* 109 */     Object localObject2 = new byte[8];
/* 110 */     localObject2[0] = -128;
/* 111 */     localObject2[1] = 0;
/* 112 */     for (int k = 2; k < 8; k++) {
/* 113 */       localObject2[k] = 0;
/*     */     }
/* 115 */     System.arraycopy(arrayOfByte, j, localObject2, j + 2, i);
/* 116 */     ByteBuffer localByteBuffer = ByteBuffer.wrap((byte[])localObject2);
/* 117 */     long l1 = uuidSequence;
/* 118 */     Runtime localRuntime = Runtime.getRuntime();
/* 119 */     synchronized (localRuntime) {
/* 120 */       String str1 = PropertiesUtil.getProperties().getStringProperty("org.apache.logging.log4j.assignedSequences");
/*     */       long[] arrayOfLong1;
/* 122 */       if (str1 == null) {
/* 123 */         arrayOfLong1 = new long[0];
/*     */       } else {
/* 125 */         String[] arrayOfString1 = str1.split(",");
/* 126 */         arrayOfLong1 = new long[arrayOfString1.length];
/* 127 */         int n = 0;
/* 128 */         for (String str2 : arrayOfString1) {
/* 129 */           arrayOfLong1[n] = Long.parseLong(str2);
/* 130 */           n++;
/*     */         }
/*     */       }
/* 133 */       if (l1 == 0L) {
/* 134 */         l1 = localSecureRandom.nextLong();
/*     */       }
/* 136 */       l1 &= 0x3FFF;
/*     */       int m;
/*     */       do {
/* 139 */         m = 0;
/* 140 */         for (long l2 : arrayOfLong1) {
/* 141 */           if (l2 == l1) {
/* 142 */             m = 1;
/* 143 */             break;
/*     */           }
/*     */         }
/* 146 */         if (m != 0) {
/* 147 */           l1 = l1 + 1L & 0x3FFF;
/*     */         }
/* 149 */       } while (m != 0);
/* 150 */       str1 = str1 + "," + Long.toString(l1);
/* 151 */       System.setProperty("org.apache.logging.log4j.assignedSequences", str1);
/*     */     }
/*     */     
/* 154 */     least = localByteBuffer.getLong() | l1 << 48;
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
/*     */   public static UUID getTimeBasedUUID()
/*     */   {
/* 180 */     long l1 = System.currentTimeMillis() * 10000L + 122192928000000000L + count.incrementAndGet() % 10000;
/*     */     
/* 182 */     long l2 = (l1 & 0xFFFFFFFF) << 32;
/* 183 */     long l3 = (l1 & 0xFFFF00000000) >> 16;
/* 184 */     long l4 = (l1 & 0xFFF000000000000) >> 48;
/* 185 */     long l5 = l2 | l3 | 0x1000 | l4;
/* 186 */     return new UUID(l5, least);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\UUIDUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */