/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.Ebean;
/*    */ import com.avaje.ebean.config.GlobalProperties;
/*    */ import com.avaje.ebeaninternal.server.lib.ShutdownManager;
/*    */ import java.util.logging.Logger;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletContextEvent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServletContextListener
/*    */   implements javax.servlet.ServletContextListener
/*    */ {
/* 41 */   private static final Logger logger = Logger.getLogger(ServletContextListener.class.getName());
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void contextDestroyed(ServletContextEvent event) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void contextInitialized(ServletContextEvent event)
/*    */   {
/*    */     try
/*    */     {
/* 60 */       ServletContext servletContext = event.getServletContext();
/* 61 */       GlobalProperties.setServletContext(servletContext);
/*    */       
/* 63 */       if (servletContext != null) {
/* 64 */         String servletRealPath = servletContext.getRealPath("");
/* 65 */         GlobalProperties.put("servlet.realpath", servletRealPath);
/* 66 */         logger.info("servlet.realpath=[" + servletRealPath + "]");
/*    */       }
/*    */       
/* 69 */       Ebean.getServer(null);
/*    */     }
/*    */     catch (Exception ex) {
/* 72 */       ex.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\ServletContextListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */