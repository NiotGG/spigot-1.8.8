/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.util.concurrent.ListeningExecutorService;
/*    */ import com.google.common.util.concurrent.MoreExecutors;
/*    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.net.URLEncoder;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class HttpUtilities
/*    */ {
/* 23 */   public static final ListeningExecutorService a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true).setNameFormat("Downloader %d").build()));
/* 24 */   private static final AtomicInteger b = new AtomicInteger(0);
/* 25 */   private static final Logger c = LogManager.getLogger();
/*    */   
/*    */ 
/*    */   public static String a(Map<String, Object> paramMap)
/*    */   {
/* 30 */     StringBuilder localStringBuilder = new StringBuilder();
/*    */     
/* 32 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 33 */       if (localStringBuilder.length() > 0) {
/* 34 */         localStringBuilder.append('&');
/*    */       }
/*    */       try
/*    */       {
/* 38 */         localStringBuilder.append(URLEncoder.encode((String)localEntry.getKey(), "UTF-8"));
/*    */       } catch (UnsupportedEncodingException localUnsupportedEncodingException1) {
/* 40 */         localUnsupportedEncodingException1.printStackTrace();
/*    */       }
/*    */       
/* 43 */       if (localEntry.getValue() != null) {
/* 44 */         localStringBuilder.append('=');
/*    */         try {
/* 46 */           localStringBuilder.append(URLEncoder.encode(localEntry.getValue().toString(), "UTF-8"));
/*    */         } catch (UnsupportedEncodingException localUnsupportedEncodingException2) {
/* 48 */           localUnsupportedEncodingException2.printStackTrace();
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 53 */     return localStringBuilder.toString();
/*    */   }
/*    */   
/*    */   public static String a(URL paramURL, Map<String, Object> paramMap, boolean paramBoolean) {
/* 57 */     return a(paramURL, a(paramMap), paramBoolean);
/*    */   }
/*    */   
/*    */   private static String a(URL paramURL, String paramString, boolean paramBoolean) {
/*    */     try {
/* 62 */       Proxy localProxy = MinecraftServer.getServer() == null ? null : MinecraftServer.getServer().ay();
/* 63 */       if (localProxy == null) {
/* 64 */         localProxy = Proxy.NO_PROXY;
/*    */       }
/* 66 */       HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURL.openConnection(localProxy);
/* 67 */       localHttpURLConnection.setRequestMethod("POST");
/* 68 */       localHttpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/*    */       
/* 70 */       localHttpURLConnection.setRequestProperty("Content-Length", "" + paramString.getBytes().length);
/* 71 */       localHttpURLConnection.setRequestProperty("Content-Language", "en-US");
/*    */       
/* 73 */       localHttpURLConnection.setUseCaches(false);
/* 74 */       localHttpURLConnection.setDoInput(true);
/* 75 */       localHttpURLConnection.setDoOutput(true);
/*    */       
/*    */ 
/* 78 */       DataOutputStream localDataOutputStream = new DataOutputStream(localHttpURLConnection.getOutputStream());
/* 79 */       localDataOutputStream.writeBytes(paramString);
/* 80 */       localDataOutputStream.flush();
/* 81 */       localDataOutputStream.close();
/*    */       
/*    */ 
/* 84 */       BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream()));
/*    */       
/* 86 */       StringBuffer localStringBuffer = new StringBuffer();
/*    */       String str;
/* 88 */       while ((str = localBufferedReader.readLine()) != null) {
/* 89 */         localStringBuffer.append(str);
/* 90 */         localStringBuffer.append('\r');
/*    */       }
/*    */       
/* 93 */       localBufferedReader.close();
/* 94 */       return localStringBuffer.toString();
/*    */     } catch (Exception localException) {
/* 96 */       if (!paramBoolean)
/* 97 */         c.error("Could not post to " + paramURL, localException);
/*    */     }
/* 99 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\HttpUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */