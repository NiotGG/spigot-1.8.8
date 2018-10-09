/*     */ package org.apache.logging.log4j.core.appender.routing;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Appender;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
/*     */ import org.apache.logging.log4j.core.config.AppenderControl;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.Node;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
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
/*     */ @Plugin(name="Routing", category="Core", elementType="appender", printObject=true)
/*     */ public final class RoutingAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private static final String DEFAULT_KEY = "ROUTING_APPENDER_DEFAULT";
/*     */   private final Routes routes;
/*     */   private final Route defaultRoute;
/*     */   private final Configuration config;
/*  52 */   private final ConcurrentMap<String, AppenderControl> appenders = new ConcurrentHashMap();
/*     */   
/*     */   private final RewritePolicy rewritePolicy;
/*     */   
/*     */   private RoutingAppender(String paramString, Filter paramFilter, boolean paramBoolean, Routes paramRoutes, RewritePolicy paramRewritePolicy, Configuration paramConfiguration)
/*     */   {
/*  58 */     super(paramString, paramFilter, null, paramBoolean);
/*  59 */     this.routes = paramRoutes;
/*  60 */     this.config = paramConfiguration;
/*  61 */     this.rewritePolicy = paramRewritePolicy;
/*  62 */     Object localObject = null;
/*  63 */     for (Route localRoute : paramRoutes.getRoutes()) {
/*  64 */       if (localRoute.getKey() == null) {
/*  65 */         if (localObject == null) {
/*  66 */           localObject = localRoute;
/*     */         } else {
/*  68 */           error("Multiple default routes. Route " + localRoute.toString() + " will be ignored");
/*     */         }
/*     */       }
/*     */     }
/*  72 */     this.defaultRoute = ((Route)localObject);
/*     */   }
/*     */   
/*     */   public void start()
/*     */   {
/*  77 */     Map localMap = this.config.getAppenders();
/*     */     
/*  79 */     for (Route localRoute : this.routes.getRoutes()) {
/*  80 */       if (localRoute.getAppenderRef() != null) {
/*  81 */         Appender localAppender = (Appender)localMap.get(localRoute.getAppenderRef());
/*  82 */         if (localAppender != null) {
/*  83 */           String str = localRoute == this.defaultRoute ? "ROUTING_APPENDER_DEFAULT" : localRoute.getKey();
/*  84 */           this.appenders.put(str, new AppenderControl(localAppender, null, null));
/*     */         } else {
/*  86 */           LOGGER.error("Appender " + localRoute.getAppenderRef() + " cannot be located. Route ignored");
/*     */         }
/*     */       }
/*     */     }
/*  90 */     super.start();
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/*  95 */     super.stop();
/*  96 */     Map localMap = this.config.getAppenders();
/*  97 */     for (Map.Entry localEntry : this.appenders.entrySet()) {
/*  98 */       String str = ((AppenderControl)localEntry.getValue()).getAppender().getName();
/*  99 */       if (!localMap.containsKey(str)) {
/* 100 */         ((AppenderControl)localEntry.getValue()).getAppender().stop();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void append(LogEvent paramLogEvent)
/*     */   {
/* 107 */     if (this.rewritePolicy != null) {
/* 108 */       paramLogEvent = this.rewritePolicy.rewrite(paramLogEvent);
/*     */     }
/* 110 */     String str = this.config.getStrSubstitutor().replace(paramLogEvent, this.routes.getPattern());
/* 111 */     AppenderControl localAppenderControl = getControl(str, paramLogEvent);
/* 112 */     if (localAppenderControl != null) {
/* 113 */       localAppenderControl.callAppender(paramLogEvent);
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized AppenderControl getControl(String paramString, LogEvent paramLogEvent) {
/* 118 */     AppenderControl localAppenderControl = (AppenderControl)this.appenders.get(paramString);
/* 119 */     if (localAppenderControl != null) {
/* 120 */       return localAppenderControl;
/*     */     }
/* 122 */     Object localObject1 = null;
/* 123 */     for (Object localObject3 : this.routes.getRoutes()) {
/* 124 */       if ((((Route)localObject3).getAppenderRef() == null) && (paramString.equals(((Route)localObject3).getKey()))) {
/* 125 */         localObject1 = localObject3;
/* 126 */         break;
/*     */       }
/*     */     }
/* 129 */     if (localObject1 == null) {
/* 130 */       localObject1 = this.defaultRoute;
/* 131 */       localAppenderControl = (AppenderControl)this.appenders.get("ROUTING_APPENDER_DEFAULT");
/* 132 */       if (localAppenderControl != null) {
/* 133 */         return localAppenderControl;
/*     */       }
/*     */     }
/* 136 */     if (localObject1 != null) {
/* 137 */       ??? = createAppender((Route)localObject1, paramLogEvent);
/* 138 */       if (??? == null) {
/* 139 */         return null;
/*     */       }
/* 141 */       localAppenderControl = new AppenderControl((Appender)???, null, null);
/* 142 */       this.appenders.put(paramString, localAppenderControl);
/*     */     }
/*     */     
/* 145 */     return localAppenderControl;
/*     */   }
/*     */   
/*     */   private Appender createAppender(Route paramRoute, LogEvent paramLogEvent) {
/* 149 */     Node localNode1 = paramRoute.getNode();
/* 150 */     for (Node localNode2 : localNode1.getChildren()) {
/* 151 */       if (localNode2.getType().getElementName().equals("appender")) {
/* 152 */         Node localNode3 = new Node(localNode2);
/* 153 */         this.config.createConfiguration(localNode3, paramLogEvent);
/* 154 */         if ((localNode3.getObject() instanceof Appender)) {
/* 155 */           Appender localAppender = (Appender)localNode3.getObject();
/* 156 */           localAppender.start();
/* 157 */           return localAppender;
/*     */         }
/* 159 */         LOGGER.error("Unable to create Appender of type " + localNode2.getName());
/* 160 */         return null;
/*     */       }
/*     */     }
/* 163 */     LOGGER.error("No Appender was configured for route " + paramRoute.getKey());
/* 164 */     return null;
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
/*     */   @PluginFactory
/*     */   public static RoutingAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("ignoreExceptions") String paramString2, @PluginElement("Routes") Routes paramRoutes, @PluginConfiguration Configuration paramConfiguration, @PluginElement("RewritePolicy") RewritePolicy paramRewritePolicy, @PluginElement("Filters") Filter paramFilter)
/*     */   {
/* 187 */     boolean bool = Booleans.parseBoolean(paramString2, true);
/* 188 */     if (paramString1 == null) {
/* 189 */       LOGGER.error("No name provided for RoutingAppender");
/* 190 */       return null;
/*     */     }
/* 192 */     if (paramRoutes == null) {
/* 193 */       LOGGER.error("No routes defined for RoutingAppender");
/* 194 */       return null;
/*     */     }
/* 196 */     return new RoutingAppender(paramString1, paramFilter, bool, paramRoutes, paramRewritePolicy, paramConfiguration);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\routing\RoutingAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */