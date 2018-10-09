/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Properties;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Message;
/*     */ import javax.jms.MessageProducer;
/*     */ import javax.jms.ObjectMessage;
/*     */ import javax.jms.Session;
/*     */ import javax.jms.TextMessage;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NameNotFoundException;
/*     */ import javax.naming.NamingException;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.AbstractManager;
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
/*     */ public abstract class AbstractJMSManager
/*     */   extends AbstractManager
/*     */ {
/*     */   public AbstractJMSManager(String paramString)
/*     */   {
/*  44 */     super(paramString);
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
/*     */   protected static Context createContext(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/*     */     throws NamingException
/*     */   {
/*  63 */     Properties localProperties = getEnvironment(paramString1, paramString2, paramString3, paramString4, paramString5);
/*     */     
/*  65 */     return new InitialContext(localProperties);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static Object lookup(Context paramContext, String paramString)
/*     */     throws NamingException
/*     */   {
/*     */     try
/*     */     {
/*  77 */       return paramContext.lookup(paramString);
/*     */     } catch (NameNotFoundException localNameNotFoundException) {
/*  79 */       LOGGER.warn("Could not find name [" + paramString + "].");
/*  80 */       throw localNameNotFoundException;
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
/*     */   protected static Properties getEnvironment(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
/*     */   {
/*  97 */     Properties localProperties = new Properties();
/*  98 */     if (paramString1 != null) {
/*  99 */       localProperties.put("java.naming.factory.initial", paramString1);
/* 100 */       if (paramString2 != null) {
/* 101 */         localProperties.put("java.naming.provider.url", paramString2);
/*     */       } else {
/* 103 */         LOGGER.warn("The InitialContext factory name has been provided without a ProviderURL. This is likely to cause problems");
/*     */       }
/*     */       
/* 106 */       if (paramString3 != null) {
/* 107 */         localProperties.put("java.naming.factory.url.pkgs", paramString3);
/*     */       }
/* 109 */       if (paramString4 != null) {
/* 110 */         localProperties.put("java.naming.security.principal", paramString4);
/* 111 */         if (paramString5 != null) {
/* 112 */           localProperties.put("java.naming.security.credentials", paramString5);
/*     */         } else {
/* 114 */           LOGGER.warn("SecurityPrincipalName has been set without SecurityCredentials. This is likely to cause problems.");
/*     */         }
/*     */       }
/*     */       
/* 118 */       return localProperties;
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract void send(Serializable paramSerializable)
/*     */     throws Exception;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void send(Serializable paramSerializable, Session paramSession, MessageProducer paramMessageProducer)
/*     */     throws Exception
/*     */   {
/*     */     try
/*     */     {
/*     */       Object localObject;
/*     */       
/*     */ 
/*     */ 
/* 141 */       if ((paramSerializable instanceof String)) {
/* 142 */         localObject = paramSession.createTextMessage();
/* 143 */         ((TextMessage)localObject).setText((String)paramSerializable);
/*     */       } else {
/* 145 */         localObject = paramSession.createObjectMessage();
/* 146 */         ((ObjectMessage)localObject).setObject(paramSerializable);
/*     */       }
/* 148 */       paramMessageProducer.send((Message)localObject);
/*     */     } catch (JMSException localJMSException) {
/* 150 */       LOGGER.error("Could not publish message via JMS " + getName());
/* 151 */       throw localJMSException;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\AbstractJMSManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */