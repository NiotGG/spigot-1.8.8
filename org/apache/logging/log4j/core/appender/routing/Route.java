/*     */ package org.apache.logging.log4j.core.appender.routing;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.Node;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginNode;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ @Plugin(name="Route", category="Core", printObject=true, deferChildren=true)
/*     */ public final class Route
/*     */ {
/*  32 */   private static final Logger LOGGER = ;
/*     */   private final Node node;
/*     */   private final String appenderRef;
/*     */   private final String key;
/*     */   
/*     */   private Route(Node paramNode, String paramString1, String paramString2)
/*     */   {
/*  39 */     this.node = paramNode;
/*  40 */     this.appenderRef = paramString1;
/*  41 */     this.key = paramString2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Node getNode()
/*     */   {
/*  49 */     return this.node;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getAppenderRef()
/*     */   {
/*  57 */     return this.appenderRef;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getKey()
/*     */   {
/*  65 */     return this.key;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  70 */     StringBuilder localStringBuilder = new StringBuilder("Route(");
/*  71 */     localStringBuilder.append("type=");
/*  72 */     if (this.appenderRef != null) {
/*  73 */       localStringBuilder.append("static Reference=").append(this.appenderRef);
/*  74 */     } else if (this.node != null) {
/*  75 */       localStringBuilder.append("dynamic - type=").append(this.node.getName());
/*     */     } else {
/*  77 */       localStringBuilder.append("invalid Route");
/*     */     }
/*  79 */     if (this.key != null) {
/*  80 */       localStringBuilder.append(" key='").append(this.key).append("'");
/*     */     } else {
/*  82 */       localStringBuilder.append(" default");
/*     */     }
/*  84 */     localStringBuilder.append(")");
/*  85 */     return localStringBuilder.toString();
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
/*     */   @PluginFactory
/*     */   public static Route createRoute(@PluginAttribute("ref") String paramString1, @PluginAttribute("key") String paramString2, @PluginNode Node paramNode)
/*     */   {
/* 100 */     if ((paramNode != null) && (paramNode.hasChildren())) { Node localNode;
/* 101 */       for (Iterator localIterator = paramNode.getChildren().iterator(); localIterator.hasNext(); localNode = (Node)localIterator.next()) {}
/*     */       
/*     */ 
/* 104 */       if (paramString1 != null) {
/* 105 */         LOGGER.error("A route cannot be configured with an appender reference and an appender definition");
/* 106 */         return null;
/*     */       }
/*     */     }
/* 109 */     else if (paramString1 == null) {
/* 110 */       LOGGER.error("A route must specify an appender reference or an appender definition");
/* 111 */       return null;
/*     */     }
/*     */     
/* 114 */     return new Route(paramNode, paramString1, paramString2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\routing\Route.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */