/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URI;
/*    */ import java.net.URL;
/*    */ import java.net.URLDecoder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class FileUtils
/*    */ {
/*    */   private static final String PROTOCOL_FILE = "file";
/*    */   private static final String JBOSS_FILE = "vfsfile";
/* 40 */   private static final Logger LOGGER = ;
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
/*    */   public static File fileFromURI(URI paramURI)
/*    */   {
/* 53 */     if ((paramURI == null) || ((paramURI.getScheme() != null) && (!"file".equals(paramURI.getScheme())) && (!"vfsfile".equals(paramURI.getScheme()))))
/*    */     {
/* 55 */       return null;
/*    */     }
/* 57 */     if (paramURI.getScheme() == null) {
/*    */       try {
/* 59 */         paramURI = new File(paramURI.getPath()).toURI();
/*    */       } catch (Exception localException) {
/* 61 */         LOGGER.warn("Invalid URI " + paramURI);
/* 62 */         return null;
/*    */       }
/*    */     }
/*    */     try {
/* 66 */       return new File(URLDecoder.decode(paramURI.toURL().getFile(), "UTF8"));
/*    */     } catch (MalformedURLException localMalformedURLException) {
/* 68 */       LOGGER.warn("Invalid URL " + paramURI, localMalformedURLException);
/*    */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/* 70 */       LOGGER.warn("Invalid encoding: UTF8", localUnsupportedEncodingException);
/*    */     }
/* 72 */     return null;
/*    */   }
/*    */   
/*    */   public static boolean isFile(URL paramURL) {
/* 76 */     return (paramURL != null) && ((paramURL.getProtocol().equals("file")) || (paramURL.getProtocol().equals("vfsfile")));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static void mkdir(File paramFile, boolean paramBoolean)
/*    */     throws IOException
/*    */   {
/* 87 */     if (!paramFile.exists()) {
/* 88 */       if (!paramBoolean) {
/* 89 */         throw new IOException("The directory " + paramFile.getAbsolutePath() + " does not exist.");
/*    */       }
/* 91 */       if (!paramFile.mkdirs()) {
/* 92 */         throw new IOException("Could not create directory " + paramFile.getAbsolutePath());
/*    */       }
/*    */     }
/* 95 */     if (!paramFile.isDirectory()) {
/* 96 */       throw new IOException("File " + paramFile + " exists and is not a directory. Unable to create directory.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */