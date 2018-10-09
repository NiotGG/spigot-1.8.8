/*    */ package org.apache.logging.log4j.core.web;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import java.util.Set;
/*    */ import javax.servlet.DispatcherType;
/*    */ import javax.servlet.FilterRegistration.Dynamic;
/*    */ import javax.servlet.ServletContainerInitializer;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.UnavailableException;
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
/*    */ public class Log4jServletContainerInitializer
/*    */   implements ServletContainerInitializer
/*    */ {
/*    */   public void onStartup(Set<Class<?>> paramSet, ServletContext paramServletContext)
/*    */     throws ServletException
/*    */   {
/* 37 */     if (paramServletContext.getMajorVersion() > 2) {
/* 38 */       paramServletContext.log("Log4jServletContainerInitializer starting up Log4j in Servlet 3.0+ environment.");
/*    */       
/* 40 */       Log4jWebInitializer localLog4jWebInitializer = Log4jWebInitializerImpl.getLog4jWebInitializer(paramServletContext);
/* 41 */       localLog4jWebInitializer.initialize();
/* 42 */       localLog4jWebInitializer.setLoggerContext();
/*    */       
/* 44 */       paramServletContext.addListener(new Log4jServletContextListener());
/*    */       
/* 46 */       FilterRegistration.Dynamic localDynamic = paramServletContext.addFilter("log4jServletFilter", new Log4jServletFilter());
/*    */       
/* 48 */       if (localDynamic == null) {
/* 49 */         throw new UnavailableException("In a Servlet 3.0+ application, you must not define a log4jServletFilter in web.xml. Log4j 2 defines this for you automatically.");
/*    */       }
/*    */       
/* 52 */       localDynamic.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, new String[] { "/*" });
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\web\Log4jServletContainerInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */