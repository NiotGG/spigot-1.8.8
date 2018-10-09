/*     */ package org.apache.logging.log4j.core.appender.db.jpa;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseAppender;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="JPA", category="Core", elementType="appender", printObject=true)
/*     */ public final class JPAAppender
/*     */   extends AbstractDatabaseAppender<JPADatabaseManager>
/*     */ {
/*     */   private final String description;
/*     */   
/*     */   private JPAAppender(String paramString, Filter paramFilter, boolean paramBoolean, JPADatabaseManager paramJPADatabaseManager)
/*     */   {
/*  45 */     super(paramString, paramFilter, paramBoolean, paramJPADatabaseManager);
/*  46 */     this.description = (getName() + "{ manager=" + getManager() + " }");
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  51 */     return this.description;
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
/*     */   @PluginFactory
/*     */   public static JPAAppender createAppender(@PluginAttribute("name") String paramString1, @PluginAttribute("ignoreExceptions") String paramString2, @PluginElement("Filter") Filter paramFilter, @PluginAttribute("bufferSize") String paramString3, @PluginAttribute("entityClassName") String paramString4, @PluginAttribute("persistenceUnitName") String paramString5)
/*     */   {
/*  76 */     if ((Strings.isEmpty(paramString4)) || (Strings.isEmpty(paramString5))) {
/*  77 */       LOGGER.error("Attributes entityClassName and persistenceUnitName are required for JPA Appender.");
/*  78 */       return null;
/*     */     }
/*     */     
/*  81 */     int i = AbstractAppender.parseInt(paramString3, 0);
/*  82 */     boolean bool = Booleans.parseBoolean(paramString2, true);
/*     */     
/*     */     try
/*     */     {
/*  86 */       Class localClass = Class.forName(paramString4);
/*     */       
/*     */ 
/*  89 */       if (!AbstractLogEventWrapperEntity.class.isAssignableFrom(localClass)) {
/*  90 */         LOGGER.error("Entity class [{}] does not extend AbstractLogEventWrapperEntity.", new Object[] { paramString4 });
/*  91 */         return null;
/*     */       }
/*     */       try
/*     */       {
/*  95 */         localClass.getConstructor(new Class[0]);
/*     */       } catch (NoSuchMethodException localNoSuchMethodException2) {
/*  97 */         LOGGER.error("Entity class [{}] does not have a no-arg constructor. The JPA provider will reject it.", new Object[] { paramString4 });
/*     */         
/*  99 */         return null;
/*     */       }
/*     */       
/* 102 */       Constructor localConstructor = localClass.getConstructor(new Class[] { LogEvent.class });
/*     */       
/*     */ 
/* 105 */       String str = "jpaManager{ description=" + paramString1 + ", bufferSize=" + i + ", persistenceUnitName=" + paramString5 + ", entityClass=" + localClass.getName() + "}";
/*     */       
/*     */ 
/* 108 */       JPADatabaseManager localJPADatabaseManager = JPADatabaseManager.getJPADatabaseManager(str, i, localClass, localConstructor, paramString5);
/*     */       
/*     */ 
/* 111 */       if (localJPADatabaseManager == null) {
/* 112 */         return null;
/*     */       }
/*     */       
/* 115 */       return new JPAAppender(paramString1, paramFilter, bool, localJPADatabaseManager);
/*     */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 117 */       LOGGER.error("Could not load entity class [{}].", new Object[] { paramString4, localClassNotFoundException });
/* 118 */       return null;
/*     */     } catch (NoSuchMethodException localNoSuchMethodException1) {
/* 120 */       LOGGER.error("Entity class [{}] does not have a constructor with a single argument of type LogEvent.", new Object[] { paramString4 });
/*     */     }
/* 122 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\JPAAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */