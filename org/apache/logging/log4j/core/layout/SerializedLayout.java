/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
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
/*     */ @Plugin(name="SerializedLayout", category="Core", elementType="layout", printObject=true)
/*     */ public final class SerializedLayout
/*     */   extends AbstractLayout<LogEvent>
/*     */ {
/*     */   private static byte[] header;
/*     */   
/*     */   static
/*     */   {
/*  39 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*     */     try {
/*  41 */       ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
/*  42 */       localObjectOutputStream.close();
/*  43 */       header = localByteArrayOutputStream.toByteArray();
/*     */     } catch (Exception localException) {
/*  45 */       LOGGER.error("Unable to generate Object stream header", localException);
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
/*     */   public byte[] toByteArray(LogEvent paramLogEvent)
/*     */   {
/*  60 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*     */     try {
/*  62 */       PrivateObjectOutputStream localPrivateObjectOutputStream = new PrivateObjectOutputStream(localByteArrayOutputStream);
/*     */       try {
/*  64 */         localPrivateObjectOutputStream.writeObject(paramLogEvent);
/*  65 */         localPrivateObjectOutputStream.reset();
/*     */       } finally {
/*  67 */         localPrivateObjectOutputStream.close();
/*     */       }
/*     */     } catch (IOException localIOException) {
/*  70 */       LOGGER.error("Serialization of LogEvent failed.", localIOException);
/*     */     }
/*  72 */     return localByteArrayOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LogEvent toSerializable(LogEvent paramLogEvent)
/*     */   {
/*  83 */     return paramLogEvent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static SerializedLayout createLayout()
/*     */   {
/*  93 */     return new SerializedLayout();
/*     */   }
/*     */   
/*     */   public byte[] getHeader()
/*     */   {
/*  98 */     return header;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, String> getContentFormat()
/*     */   {
/* 107 */     return new HashMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 116 */     return "application/octet-stream";
/*     */   }
/*     */   
/*     */   private class PrivateObjectOutputStream
/*     */     extends ObjectOutputStream
/*     */   {
/*     */     public PrivateObjectOutputStream(OutputStream paramOutputStream)
/*     */       throws IOException
/*     */     {
/* 125 */       super();
/*     */     }
/*     */     
/*     */     protected void writeStreamHeader() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\SerializedLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */