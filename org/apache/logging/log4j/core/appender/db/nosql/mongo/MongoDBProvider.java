/*     */ package org.apache.logging.log4j.core.appender.db.nosql.mongo;
/*     */ 
/*     */ import com.mongodb.DB;
/*     */ import com.mongodb.Mongo;
/*     */ import com.mongodb.MongoClient;
/*     */ import com.mongodb.ServerAddress;
/*     */ import com.mongodb.WriteConcern;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*     */ import org.apache.logging.log4j.core.appender.db.nosql.NoSQLProvider;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.NameUtil;
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
/*     */ 
/*     */ 
/*     */ @Plugin(name="MongoDb", category="Core", printObject=true)
/*     */ public final class MongoDBProvider
/*     */   implements NoSQLProvider<MongoDBConnection>
/*     */ {
/*  42 */   private static final Logger LOGGER = ;
/*     */   
/*     */   private final String collectionName;
/*     */   
/*     */   private final DB database;
/*     */   private final String description;
/*     */   private final WriteConcern writeConcern;
/*     */   
/*     */   private MongoDBProvider(DB paramDB, WriteConcern paramWriteConcern, String paramString1, String paramString2)
/*     */   {
/*  52 */     this.database = paramDB;
/*  53 */     this.writeConcern = paramWriteConcern;
/*  54 */     this.collectionName = paramString1;
/*  55 */     this.description = ("mongoDb{ " + paramString2 + " }");
/*     */   }
/*     */   
/*     */   public MongoDBConnection getConnection()
/*     */   {
/*  60 */     return new MongoDBConnection(this.database, this.writeConcern, this.collectionName);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  65 */     return this.description;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static MongoDBProvider createNoSQLProvider(@PluginAttribute("collectionName") String paramString1, @PluginAttribute("writeConcernConstant") String paramString2, @PluginAttribute("writeConcernConstantClass") String paramString3, @PluginAttribute("databaseName") String paramString4, @PluginAttribute("server") String paramString5, @PluginAttribute("port") String paramString6, @PluginAttribute("username") String paramString7, @PluginAttribute("password") String paramString8, @PluginAttribute("factoryClassName") String paramString9, @PluginAttribute("factoryMethodName") String paramString10)
/*     */   {
/*     */     Object localObject1;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     Object localObject2;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     DB localDB;
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
/* 104 */     if ((paramString9 != null) && (paramString9.length() > 0) && (paramString10 != null) && (paramString10.length() > 0))
/*     */       try
/*     */       {
/* 107 */         Class localClass = Class.forName(paramString9);
/* 108 */         localObject1 = localClass.getMethod(paramString10, new Class[0]);
/* 109 */         localObject2 = ((Method)localObject1).invoke(null, new Object[0]);
/*     */         
/* 111 */         if ((localObject2 instanceof DB)) {
/* 112 */           localDB = (DB)localObject2;
/* 113 */         } else if ((localObject2 instanceof MongoClient)) {
/* 114 */           if ((paramString4 != null) && (paramString4.length() > 0)) {
/* 115 */             localDB = ((MongoClient)localObject2).getDB(paramString4);
/*     */           } else {
/* 117 */             LOGGER.error("The factory method [{}.{}()] returned a MongoClient so the database name is required.", new Object[] { paramString9, paramString10 });
/*     */             
/* 119 */             return null;
/*     */           }
/* 121 */         } else { if (localObject2 == null) {
/* 122 */             LOGGER.error("The factory method [{}.{}()] returned null.", new Object[] { paramString9, paramString10 });
/* 123 */             return null;
/*     */           }
/* 125 */           LOGGER.error("The factory method [{}.{}()] returned an unsupported type [{}].", new Object[] { paramString9, paramString10, localObject2.getClass().getName() });
/*     */           
/* 127 */           return null;
/*     */         }
/*     */         
/* 130 */         str = "database=" + localDB.getName();
/* 131 */         List localList = localDB.getMongo().getAllAddress();
/* 132 */         if (localList.size() == 1) {
/* 133 */           str = str + ", server=" + ((ServerAddress)localList.get(0)).getHost() + ", port=" + ((ServerAddress)localList.get(0)).getPort();
/*     */         } else {
/* 135 */           str = str + ", servers=[";
/* 136 */           for (ServerAddress localServerAddress : localList) {
/* 137 */             str = str + " { " + localServerAddress.getHost() + ", " + localServerAddress.getPort() + " } ";
/*     */           }
/* 139 */           str = str + "]";
/*     */         }
/*     */       } catch (ClassNotFoundException localClassNotFoundException) {
/* 142 */         LOGGER.error("The factory class [{}] could not be loaded.", new Object[] { paramString9, localClassNotFoundException });
/* 143 */         return null;
/*     */       } catch (NoSuchMethodException localNoSuchMethodException) {
/* 145 */         LOGGER.error("The factory class [{}] does not have a no-arg method named [{}].", new Object[] { paramString9, paramString10, localNoSuchMethodException });
/*     */         
/* 147 */         return null;
/*     */       } catch (Exception localException1) {
/* 149 */         LOGGER.error("The factory method [{}.{}()] could not be invoked.", new Object[] { paramString9, paramString10, localException1 });
/*     */         
/* 151 */         return null;
/*     */       }
/* 153 */     if ((paramString4 != null) && (paramString4.length() > 0)) {
/* 154 */       str = "database=" + paramString4;
/*     */       try {
/* 156 */         if ((paramString5 != null) && (paramString5.length() > 0)) {
/* 157 */           int i = AbstractAppender.parseInt(paramString6, 0);
/* 158 */           str = str + ", server=" + paramString5;
/* 159 */           if (i > 0) {
/* 160 */             str = str + ", port=" + i;
/* 161 */             localDB = new MongoClient(paramString5, i).getDB(paramString4);
/*     */           } else {
/* 163 */             localDB = new MongoClient(paramString5).getDB(paramString4);
/*     */           }
/*     */         } else {
/* 166 */           localDB = new MongoClient().getDB(paramString4);
/*     */         }
/*     */       } catch (Exception localException2) {
/* 169 */         LOGGER.error("Failed to obtain a database instance from the MongoClient at server [{}] and port [{}].", new Object[] { paramString5, paramString6 });
/*     */         
/* 171 */         return null;
/*     */       }
/*     */     } else {
/* 174 */       LOGGER.error("No factory method was provided so the database name is required.");
/* 175 */       return null;
/*     */     }
/*     */     
/* 178 */     if (!localDB.isAuthenticated()) {
/* 179 */       if ((paramString7 != null) && (paramString7.length() > 0) && (paramString8 != null) && (paramString8.length() > 0)) {
/* 180 */         str = str + ", username=" + paramString7 + ", passwordHash=" + NameUtil.md5(new StringBuilder().append(paramString8).append(MongoDBProvider.class.getName()).toString());
/*     */         
/* 182 */         MongoDBConnection.authenticate(localDB, paramString7, paramString8);
/*     */       } else {
/* 184 */         LOGGER.error("The database is not already authenticated so you must supply a username and password for the MongoDB provider.");
/*     */         
/* 186 */         return null;
/*     */       }
/*     */     }
/*     */     
/*     */     WriteConcern localWriteConcern;
/* 191 */     if ((paramString2 != null) && (paramString2.length() > 0)) {
/* 192 */       if ((paramString3 != null) && (paramString3.length() > 0)) {
/*     */         try {
/* 194 */           localObject1 = Class.forName(paramString3);
/* 195 */           localObject2 = ((Class)localObject1).getField(paramString2);
/* 196 */           localWriteConcern = (WriteConcern)((Field)localObject2).get(null);
/*     */         } catch (Exception localException3) {
/* 198 */           LOGGER.error("Write concern constant [{}.{}] not found, using default.", new Object[] { paramString3, paramString2 });
/*     */           
/* 200 */           localWriteConcern = WriteConcern.ACKNOWLEDGED;
/*     */         }
/*     */       } else {
/* 203 */         localWriteConcern = WriteConcern.valueOf(paramString2);
/* 204 */         if (localWriteConcern == null) {
/* 205 */           LOGGER.warn("Write concern constant [{}] not found, using default.", new Object[] { paramString2 });
/* 206 */           localWriteConcern = WriteConcern.ACKNOWLEDGED;
/*     */         }
/*     */       }
/*     */     } else {
/* 210 */       localWriteConcern = WriteConcern.ACKNOWLEDGED;
/*     */     }
/*     */     
/* 213 */     return new MongoDBProvider(localDB, localWriteConcern, paramString1, str);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\mongo\MongoDBProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */