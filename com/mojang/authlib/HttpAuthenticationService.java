/*     */ package com.mojang.authlib;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public abstract class HttpAuthenticationService extends BaseAuthenticationService
/*     */ {
/*  17 */   private static final Logger LOGGER = ;
/*     */   private final Proxy proxy;
/*     */   
/*     */   protected HttpAuthenticationService(Proxy paramProxy)
/*     */   {
/*  22 */     Validate.notNull(paramProxy);
/*  23 */     this.proxy = paramProxy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Proxy getProxy()
/*     */   {
/*  32 */     return this.proxy;
/*     */   }
/*     */   
/*     */   protected HttpURLConnection createUrlConnection(URL paramURL) throws IOException {
/*  36 */     Validate.notNull(paramURL);
/*  37 */     LOGGER.debug("Opening connection to " + paramURL);
/*  38 */     HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURL.openConnection(this.proxy);
/*  39 */     localHttpURLConnection.setConnectTimeout(15000);
/*  40 */     localHttpURLConnection.setReadTimeout(15000);
/*  41 */     localHttpURLConnection.setUseCaches(false);
/*  42 */     return localHttpURLConnection;
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
/*     */   public String performPostRequest(URL paramURL, String paramString1, String paramString2)
/*     */     throws IOException
/*     */   {
/*  59 */     Validate.notNull(paramURL);
/*  60 */     Validate.notNull(paramString1);
/*  61 */     Validate.notNull(paramString2);
/*  62 */     HttpURLConnection localHttpURLConnection = createUrlConnection(paramURL);
/*  63 */     byte[] arrayOfByte = paramString1.getBytes(Charsets.UTF_8);
/*     */     
/*  65 */     localHttpURLConnection.setRequestProperty("Content-Type", paramString2 + "; charset=utf-8");
/*  66 */     localHttpURLConnection.setRequestProperty("Content-Length", "" + arrayOfByte.length);
/*  67 */     localHttpURLConnection.setDoOutput(true);
/*     */     
/*  69 */     LOGGER.debug("Writing POST data to " + paramURL + ": " + paramString1);
/*     */     
/*  71 */     java.io.OutputStream localOutputStream = null;
/*     */     try {
/*  73 */       localOutputStream = localHttpURLConnection.getOutputStream();
/*  74 */       IOUtils.write(arrayOfByte, localOutputStream);
/*     */     } finally {
/*  76 */       IOUtils.closeQuietly(localOutputStream);
/*     */     }
/*     */     
/*  79 */     LOGGER.debug("Reading data from " + paramURL);
/*     */     
/*  81 */     java.io.InputStream localInputStream = null;
/*     */     try {
/*  83 */       localInputStream = localHttpURLConnection.getInputStream();
/*  84 */       String str1 = IOUtils.toString(localInputStream, Charsets.UTF_8);
/*  85 */       LOGGER.debug("Successful read, server response was " + localHttpURLConnection.getResponseCode());
/*  86 */       LOGGER.debug("Response: " + str1);
/*  87 */       return str1;
/*     */     } catch (IOException localIOException) { String str2;
/*  89 */       IOUtils.closeQuietly(localInputStream);
/*  90 */       localInputStream = localHttpURLConnection.getErrorStream();
/*     */       
/*  92 */       if (localInputStream != null) {
/*  93 */         LOGGER.debug("Reading error page from " + paramURL);
/*  94 */         str2 = IOUtils.toString(localInputStream, Charsets.UTF_8);
/*  95 */         LOGGER.debug("Successful read, server response was " + localHttpURLConnection.getResponseCode());
/*  96 */         LOGGER.debug("Response: " + str2);
/*  97 */         return str2;
/*     */       }
/*  99 */       LOGGER.debug("Request failed", localIOException);
/* 100 */       throw localIOException;
/*     */     }
/*     */     finally {
/* 103 */       IOUtils.closeQuietly(localInputStream);
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
/*     */ 
/*     */ 
/*     */   public String performGetRequest(URL paramURL)
/*     */     throws IOException
/*     */   {
/* 119 */     Validate.notNull(paramURL);
/* 120 */     HttpURLConnection localHttpURLConnection = createUrlConnection(paramURL);
/*     */     
/* 122 */     LOGGER.debug("Reading data from " + paramURL);
/*     */     
/* 124 */     java.io.InputStream localInputStream = null;
/*     */     try {
/* 126 */       localInputStream = localHttpURLConnection.getInputStream();
/* 127 */       String str1 = IOUtils.toString(localInputStream, Charsets.UTF_8);
/* 128 */       LOGGER.debug("Successful read, server response was " + localHttpURLConnection.getResponseCode());
/* 129 */       LOGGER.debug("Response: " + str1);
/* 130 */       return str1;
/*     */     } catch (IOException localIOException) { String str2;
/* 132 */       IOUtils.closeQuietly(localInputStream);
/* 133 */       localInputStream = localHttpURLConnection.getErrorStream();
/*     */       
/* 135 */       if (localInputStream != null) {
/* 136 */         LOGGER.debug("Reading error page from " + paramURL);
/* 137 */         str2 = IOUtils.toString(localInputStream, Charsets.UTF_8);
/* 138 */         LOGGER.debug("Successful read, server response was " + localHttpURLConnection.getResponseCode());
/* 139 */         LOGGER.debug("Response: " + str2);
/* 140 */         return str2;
/*     */       }
/* 142 */       LOGGER.debug("Request failed", localIOException);
/* 143 */       throw localIOException;
/*     */     }
/*     */     finally {
/* 146 */       IOUtils.closeQuietly(localInputStream);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static URL constantURL(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 160 */       return new URL(paramString);
/*     */     } catch (MalformedURLException localMalformedURLException) {
/* 162 */       throw new Error("Couldn't create constant for " + paramString, localMalformedURLException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String buildQuery(java.util.Map<String, Object> paramMap)
/*     */   {
/* 173 */     if (paramMap == null) return "";
/* 174 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/* 176 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 177 */       if (localStringBuilder.length() > 0) {
/* 178 */         localStringBuilder.append('&');
/*     */       }
/*     */       try
/*     */       {
/* 182 */         localStringBuilder.append(java.net.URLEncoder.encode((String)localEntry.getKey(), "UTF-8"));
/*     */       } catch (UnsupportedEncodingException localUnsupportedEncodingException1) {
/* 184 */         LOGGER.error("Unexpected exception building query", localUnsupportedEncodingException1);
/*     */       }
/*     */       
/* 187 */       if (localEntry.getValue() != null) {
/* 188 */         localStringBuilder.append('=');
/*     */         try {
/* 190 */           localStringBuilder.append(java.net.URLEncoder.encode(localEntry.getValue().toString(), "UTF-8"));
/*     */         } catch (UnsupportedEncodingException localUnsupportedEncodingException2) {
/* 192 */           LOGGER.error("Unexpected exception building query", localUnsupportedEncodingException2);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 197 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static URL concatenateURL(URL paramURL, String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 209 */       if ((paramURL.getQuery() != null) && (paramURL.getQuery().length() > 0)) {
/* 210 */         return new URL(paramURL.getProtocol(), paramURL.getHost(), paramURL.getPort(), paramURL.getFile() + "&" + paramString);
/*     */       }
/* 212 */       return new URL(paramURL.getProtocol(), paramURL.getHost(), paramURL.getPort(), paramURL.getFile() + "?" + paramString);
/*     */     }
/*     */     catch (MalformedURLException localMalformedURLException) {
/* 215 */       throw new IllegalArgumentException("Could not concatenate given URL with GET arguments!", localMalformedURLException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\HttpAuthenticationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */