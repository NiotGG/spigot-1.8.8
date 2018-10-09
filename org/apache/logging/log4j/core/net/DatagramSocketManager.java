/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
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
/*     */ public class DatagramSocketManager
/*     */   extends AbstractSocketManager
/*     */ {
/*  35 */   private static final DatagramSocketManagerFactory FACTORY = new DatagramSocketManagerFactory(null);
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
/*     */   protected DatagramSocketManager(String paramString1, OutputStream paramOutputStream, InetAddress paramInetAddress, String paramString2, int paramInt, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  48 */     super(paramString1, paramOutputStream, paramInetAddress, paramString2, paramInt, paramLayout);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DatagramSocketManager getSocketManager(String paramString, int paramInt, Layout<? extends Serializable> paramLayout)
/*     */   {
/*  59 */     if (Strings.isEmpty(paramString)) {
/*  60 */       throw new IllegalArgumentException("A host name is required");
/*     */     }
/*  62 */     if (paramInt <= 0) {
/*  63 */       throw new IllegalArgumentException("A port value is required");
/*     */     }
/*  65 */     return (DatagramSocketManager)getManager("UDP:" + paramString + ":" + paramInt, new FactoryData(paramString, paramInt, paramLayout), FACTORY);
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
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/*  78 */     HashMap localHashMap = new HashMap(super.getContentFormat());
/*  79 */     localHashMap.put("protocol", "udp");
/*  80 */     localHashMap.put("direction", "out");
/*     */     
/*  82 */     return localHashMap;
/*     */   }
/*     */   
/*     */ 
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String host;
/*     */     private final int port;
/*     */     private final Layout<? extends Serializable> layout;
/*     */     
/*     */     public FactoryData(String paramString, int paramInt, Layout<? extends Serializable> paramLayout)
/*     */     {
/*  94 */       this.host = paramString;
/*  95 */       this.port = paramInt;
/*  96 */       this.layout = paramLayout;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static class DatagramSocketManagerFactory
/*     */     implements ManagerFactory<DatagramSocketManager, DatagramSocketManager.FactoryData>
/*     */   {
/*     */     public DatagramSocketManager createManager(String paramString, DatagramSocketManager.FactoryData paramFactoryData)
/*     */     {
/* 108 */       DatagramOutputStream localDatagramOutputStream = new DatagramOutputStream(DatagramSocketManager.FactoryData.access$100(paramFactoryData), DatagramSocketManager.FactoryData.access$200(paramFactoryData), DatagramSocketManager.FactoryData.access$300(paramFactoryData).getHeader(), DatagramSocketManager.FactoryData.access$300(paramFactoryData).getFooter());
/*     */       InetAddress localInetAddress;
/*     */       try {
/* 111 */         localInetAddress = InetAddress.getByName(DatagramSocketManager.FactoryData.access$100(paramFactoryData));
/*     */       } catch (UnknownHostException localUnknownHostException) {
/* 113 */         DatagramSocketManager.LOGGER.error("Could not find address of " + DatagramSocketManager.FactoryData.access$100(paramFactoryData), localUnknownHostException);
/* 114 */         return null;
/*     */       }
/* 116 */       return new DatagramSocketManager(paramString, localDatagramOutputStream, localInetAddress, DatagramSocketManager.FactoryData.access$100(paramFactoryData), DatagramSocketManager.FactoryData.access$200(paramFactoryData), DatagramSocketManager.FactoryData.access$300(paramFactoryData));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\DatagramSocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */