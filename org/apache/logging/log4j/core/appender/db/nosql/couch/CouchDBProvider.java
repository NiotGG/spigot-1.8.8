/*     */ package org.apache.logging.log4j.core.appender.db.nosql.couch;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLProvider;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.NameUtil;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
/*     */ import org.lightcouch.CouchDbClient;
/*     */ import org.lightcouch.CouchDbProperties;
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
/*     */ @Plugin(name="CouchDb", category="Core", printObject=true)
/*     */ public final class CouchDBProvider
/*     */   implements NoSQLProvider<CouchDBConnection>
/*     */ {
/*     */   private static final int HTTP = 80;
/*     */   private static final int HTTPS = 443;
/*  40 */   private static final Logger LOGGER = ;
/*     */   private final CouchDbClient client;
/*     */   private final String description;
/*     */   
/*     */   private CouchDBProvider(CouchDbClient paramCouchDbClient, String paramString)
/*     */   {
/*  46 */     this.client = paramCouchDbClient;
/*  47 */     this.description = ("couchDb{ " + paramString + " }");
/*     */   }
/*     */   
/*     */   public CouchDBConnection getConnection()
/*     */   {
/*  52 */     return new CouchDBConnection(this.client);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  57 */     return this.description;
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
/*     */   @PluginFactory
/*     */   public static CouchDBProvider createNoSQLProvider(@PluginAttribute("databaseName") String paramString1, @PluginAttribute("protocol") String paramString2, @PluginAttribute("server") String paramString3, @PluginAttribute("port") String paramString4, @PluginAttribute("username") String paramString5, @PluginAttribute("password") String paramString6, @PluginAttribute("factoryClassName") String paramString7, @PluginAttribute("factoryMethodName") String paramString8)
/*     */   {
/*     */     CouchDbClient localCouchDbClient;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     String str;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  93 */     if ((paramString7 != null) && (paramString7.length() > 0) && (paramString8 != null) && (paramString8.length() > 0))
/*     */       try
/*     */       {
/*  96 */         Class localClass = Class.forName(paramString7);
/*  97 */         Method localMethod = localClass.getMethod(paramString8, new Class[0]);
/*  98 */         Object localObject = localMethod.invoke(null, new Object[0]);
/*     */         
/* 100 */         if ((localObject instanceof CouchDbClient)) {
/* 101 */           localCouchDbClient = (CouchDbClient)localObject;
/* 102 */           str = "uri=" + localCouchDbClient.getDBUri();
/* 103 */         } else if ((localObject instanceof CouchDbProperties)) {
/* 104 */           CouchDbProperties localCouchDbProperties = (CouchDbProperties)localObject;
/* 105 */           localCouchDbClient = new CouchDbClient(localCouchDbProperties);
/* 106 */           str = "uri=" + localCouchDbClient.getDBUri() + ", username=" + localCouchDbProperties.getUsername() + ", passwordHash=" + NameUtil.md5(new StringBuilder().append(paramString6).append(CouchDBProvider.class.getName()).toString()) + ", maxConnections=" + localCouchDbProperties.getMaxConnections() + ", connectionTimeout=" + localCouchDbProperties.getConnectionTimeout() + ", socketTimeout=" + localCouchDbProperties.getSocketTimeout();
/*     */         }
/*     */         else
/*     */         {
/* 110 */           if (localObject == null) {
/* 111 */             LOGGER.error("The factory method [{}.{}()] returned null.", new Object[] { paramString7, paramString8 });
/* 112 */             return null;
/*     */           }
/* 114 */           LOGGER.error("The factory method [{}.{}()] returned an unsupported type [{}].", new Object[] { paramString7, paramString8, localObject.getClass().getName() });
/*     */           
/* 116 */           return null;
/*     */         }
/*     */       } catch (ClassNotFoundException localClassNotFoundException) {
/* 119 */         LOGGER.error("The factory class [{}] could not be loaded.", new Object[] { paramString7, localClassNotFoundException });
/* 120 */         return null;
/*     */       } catch (NoSuchMethodException localNoSuchMethodException) {
/* 122 */         LOGGER.error("The factory class [{}] does not have a no-arg method named [{}].", new Object[] { paramString7, paramString8, localNoSuchMethodException });
/*     */         
/* 124 */         return null;
/*     */       } catch (Exception localException) {
/* 126 */         LOGGER.error("The factory method [{}.{}()] could not be invoked.", new Object[] { paramString7, paramString8, localException });
/*     */         
/* 128 */         return null;
/*     */       }
/* 130 */     if ((paramString1 != null) && (paramString1.length() > 0)) {
/* 131 */       if ((paramString2 != null) && (paramString2.length() > 0)) {
/* 132 */         paramString2 = paramString2.toLowerCase();
/* 133 */         if ((!paramString2.equals("http")) && (!paramString2.equals("https"))) {
/* 134 */           LOGGER.error("Only protocols [http] and [https] are supported, [{}] specified.", new Object[] { paramString2 });
/* 135 */           return null;
/*     */         }
/*     */       } else {
/* 138 */         paramString2 = "http";
/* 139 */         LOGGER.warn("No protocol specified, using default port [http].");
/*     */       }
/*     */       
/* 142 */       int i = AbstractAppender.parseInt(paramString4, paramString2.equals("https") ? 443 : 80);
/*     */       
/* 144 */       if (Strings.isEmpty(paramString3)) {
/* 145 */         paramString3 = "localhost";
/* 146 */         LOGGER.warn("No server specified, using default server localhost.");
/*     */       }
/*     */       
/* 149 */       if ((Strings.isEmpty(paramString5)) || (Strings.isEmpty(paramString6))) {
/* 150 */         LOGGER.error("You must provide a username and password for the CouchDB provider.");
/* 151 */         return null;
/*     */       }
/*     */       
/* 154 */       localCouchDbClient = new CouchDbClient(paramString1, false, paramString2, paramString3, i, paramString5, paramString6);
/* 155 */       str = "uri=" + localCouchDbClient.getDBUri() + ", username=" + paramString5 + ", passwordHash=" + NameUtil.md5(new StringBuilder().append(paramString6).append(CouchDBProvider.class.getName()).toString());
/*     */     }
/*     */     else {
/* 158 */       LOGGER.error("No factory method was provided so the database name is required.");
/* 159 */       return null;
/*     */     }
/*     */     
/* 162 */     return new CouchDBProvider(localCouchDbClient, str);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\couch\CouchDBProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */