/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.net.InetAddress;
/*    */ import java.net.NetworkInterface;
/*    */ import java.net.SocketException;
/*    */ import java.net.UnknownHostException;
/*    */ import java.util.Enumeration;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NetUtils
/*    */ {
/* 33 */   private static final Logger LOGGER = ;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String getLocalHostname()
/*    */   {
/*    */     try
/*    */     {
/* 47 */       InetAddress localInetAddress1 = InetAddress.getLocalHost();
/* 48 */       return localInetAddress1.getHostName();
/*    */     } catch (UnknownHostException localUnknownHostException) {
/*    */       try {
/* 51 */         Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
/* 52 */         while (localEnumeration1.hasMoreElements()) {
/* 53 */           NetworkInterface localNetworkInterface = (NetworkInterface)localEnumeration1.nextElement();
/* 54 */           Enumeration localEnumeration2 = localNetworkInterface.getInetAddresses();
/* 55 */           while (localEnumeration2.hasMoreElements()) {
/* 56 */             InetAddress localInetAddress2 = (InetAddress)localEnumeration2.nextElement();
/* 57 */             if (!localInetAddress2.isLoopbackAddress()) {
/* 58 */               String str = localInetAddress2.getHostName();
/* 59 */               if (str != null) {
/* 60 */                 return str;
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       } catch (SocketException localSocketException) {
/* 66 */         LOGGER.error("Could not determine local host name", localUnknownHostException);
/* 67 */         return "UNKNOWN_LOCALHOST";
/*    */       }
/* 69 */       LOGGER.error("Could not determine local host name", localUnknownHostException); }
/* 70 */     return "UNKNOWN_LOCALHOST";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\NetUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */