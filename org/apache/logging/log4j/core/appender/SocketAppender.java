/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.layout.SerializedLayout;
/*     */ import org.apache.logging.log4j.core.net.AbstractSocketManager;
/*     */ import org.apache.logging.log4j.core.net.Advertiser;
/*     */ import org.apache.logging.log4j.core.net.DatagramSocketManager;
/*     */ import org.apache.logging.log4j.core.net.Protocol;
/*     */ import org.apache.logging.log4j.core.net.TCPSocketManager;
/*     */ import org.apache.logging.log4j.util.EnglishEnums;
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
/*     */ @Plugin(name="Socket", category="Core", elementType="appender", printObject=true)
/*     */ public class SocketAppender
/*     */   extends AbstractOutputStreamAppender
/*     */ {
/*     */   private Object advertisement;
/*     */   private final Advertiser advertiser;
/*     */   
/*     */   protected SocketAppender(String paramString, Layout<? extends Serializable> paramLayout, Filter paramFilter, AbstractSocketManager paramAbstractSocketManager, boolean paramBoolean1, boolean paramBoolean2, Advertiser paramAdvertiser)
/*     */   {
/*  51 */     super(paramString, paramLayout, paramFilter, paramBoolean1, paramBoolean2, paramAbstractSocketManager);
/*  52 */     if (paramAdvertiser != null) {
/*  53 */       HashMap localHashMap = new HashMap(paramLayout.getContentFormat());
/*  54 */       localHashMap.putAll(paramAbstractSocketManager.getContentFormat());
/*  55 */       localHashMap.put("contentType", paramLayout.getContentType());
/*  56 */       localHashMap.put("name", paramString);
/*  57 */       this.advertisement = paramAdvertiser.advertise(localHashMap);
/*     */     }
/*  59 */     this.advertiser = paramAdvertiser;
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/*  64 */     super.stop();
/*  65 */     if (this.advertiser != null) {
/*  66 */       this.advertiser.unadvertise(this.advertisement);
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
/*     */   @PluginFactory
/*     */   public static SocketAppender createAppender(@PluginAttribute("host") String paramString1, @PluginAttribute("port") String paramString2, @PluginAttribute("protocol") String paramString3, @PluginAttribute("reconnectionDelay") String paramString4, @PluginAttribute("immediateFail") String paramString5, @PluginAttribute("name") String paramString6, @PluginAttribute("immediateFlush") String paramString7, @PluginAttribute("ignoreExceptions") String paramString8, @PluginElement("Layout") Layout<? extends Serializable> paramLayout, @PluginElement("Filters") Filter paramFilter, @PluginAttribute("advertise") String paramString9, @PluginConfiguration Configuration paramConfiguration)
/*     */   {
/* 102 */     boolean bool1 = Booleans.parseBoolean(paramString7, true);
/* 103 */     boolean bool2 = Boolean.parseBoolean(paramString9);
/* 104 */     boolean bool3 = Booleans.parseBoolean(paramString8, true);
/* 105 */     boolean bool4 = Booleans.parseBoolean(paramString5, true);
/* 106 */     int i = AbstractAppender.parseInt(paramString4, 0);
/* 107 */     int j = AbstractAppender.parseInt(paramString2, 0);
/* 108 */     if (paramLayout == null) {
/* 109 */       paramLayout = SerializedLayout.createLayout();
/*     */     }
/*     */     
/* 112 */     if (paramString6 == null) {
/* 113 */       LOGGER.error("No name provided for SocketAppender");
/* 114 */       return null;
/*     */     }
/*     */     
/* 117 */     Protocol localProtocol = (Protocol)EnglishEnums.valueOf(Protocol.class, paramString3 != null ? paramString3 : Protocol.TCP.name());
/* 118 */     if (localProtocol.equals(Protocol.UDP)) {
/* 119 */       bool1 = true;
/*     */     }
/*     */     
/* 122 */     AbstractSocketManager localAbstractSocketManager = createSocketManager(localProtocol, paramString1, j, i, bool4, paramLayout);
/* 123 */     if (localAbstractSocketManager == null) {
/* 124 */       return null;
/*     */     }
/*     */     
/* 127 */     return new SocketAppender(paramString6, paramLayout, paramFilter, localAbstractSocketManager, bool3, bool1, bool2 ? paramConfiguration.getAdvertiser() : null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected static AbstractSocketManager createSocketManager(Protocol paramProtocol, String paramString, int paramInt1, int paramInt2, boolean paramBoolean, Layout<? extends Serializable> paramLayout)
/*     */   {
/* 134 */     switch (paramProtocol) {
/*     */     case TCP: 
/* 136 */       return TCPSocketManager.getSocketManager(paramString, paramInt1, paramInt2, paramBoolean, paramLayout);
/*     */     case UDP: 
/* 138 */       return DatagramSocketManager.getSocketManager(paramString, paramInt1, paramLayout);
/*     */     }
/* 140 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\SocketAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */