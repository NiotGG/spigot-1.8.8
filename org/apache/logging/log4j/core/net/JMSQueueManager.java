/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Queue;
/*     */ import javax.jms.QueueConnection;
/*     */ import javax.jms.QueueConnectionFactory;
/*     */ import javax.jms.QueueSender;
/*     */ import javax.jms.QueueSession;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.NamingException;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
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
/*     */ public class JMSQueueManager
/*     */   extends AbstractJMSManager
/*     */ {
/*  37 */   private static final JMSQueueManagerFactory FACTORY = new JMSQueueManagerFactory(null);
/*     */   
/*     */ 
/*     */   private QueueInfo info;
/*     */   
/*     */ 
/*     */   private final String factoryBindingName;
/*     */   
/*     */ 
/*     */   private final String queueBindingName;
/*     */   
/*     */ 
/*     */   private final String userName;
/*     */   
/*     */ 
/*     */   private final String password;
/*     */   
/*     */   private final Context context;
/*     */   
/*     */ 
/*     */   protected JMSQueueManager(String paramString1, Context paramContext, String paramString2, String paramString3, String paramString4, String paramString5, QueueInfo paramQueueInfo)
/*     */   {
/*  59 */     super(paramString1);
/*  60 */     this.context = paramContext;
/*  61 */     this.factoryBindingName = paramString2;
/*  62 */     this.queueBindingName = paramString3;
/*  63 */     this.userName = paramString4;
/*  64 */     this.password = paramString5;
/*  65 */     this.info = paramQueueInfo;
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
/*     */   public static JMSQueueManager getJMSQueueManager(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
/*     */   {
/*  88 */     if (paramString6 == null) {
/*  89 */       LOGGER.error("No factory name provided for JMSQueueManager");
/*  90 */       return null;
/*     */     }
/*  92 */     if (paramString7 == null) {
/*  93 */       LOGGER.error("No topic name provided for JMSQueueManager");
/*  94 */       return null;
/*     */     }
/*     */     
/*  97 */     String str = "JMSQueue:" + paramString6 + '.' + paramString7;
/*  98 */     return (JMSQueueManager)getManager(str, FACTORY, new FactoryData(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9));
/*     */   }
/*     */   
/*     */   public synchronized void send(Serializable paramSerializable)
/*     */     throws Exception
/*     */   {
/* 104 */     if (this.info == null) {
/* 105 */       this.info = connect(this.context, this.factoryBindingName, this.queueBindingName, this.userName, this.password, false);
/*     */     }
/*     */     try {
/* 108 */       super.send(paramSerializable, this.info.session, this.info.sender);
/*     */     } catch (Exception localException) {
/* 110 */       cleanup(true);
/* 111 */       throw localException;
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseSub()
/*     */   {
/* 117 */     if (this.info != null) {
/* 118 */       cleanup(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void cleanup(boolean paramBoolean) {
/*     */     try {
/* 124 */       this.info.session.close();
/*     */     } catch (Exception localException1) {
/* 126 */       if (!paramBoolean) {
/* 127 */         LOGGER.error("Error closing session for " + getName(), localException1);
/*     */       }
/*     */     }
/*     */     try {
/* 131 */       this.info.conn.close();
/*     */     } catch (Exception localException2) {
/* 133 */       if (!paramBoolean) {
/* 134 */         LOGGER.error("Error closing connection for " + getName(), localException2);
/*     */       }
/*     */     }
/* 137 */     this.info = null;
/*     */   }
/*     */   
/*     */ 
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String factoryName;
/*     */     
/*     */     private final String providerURL;
/*     */     
/*     */     private final String urlPkgPrefixes;
/*     */     
/*     */     private final String securityPrincipalName;
/*     */     private final String securityCredentials;
/*     */     private final String factoryBindingName;
/*     */     private final String queueBindingName;
/*     */     private final String userName;
/*     */     private final String password;
/*     */     
/*     */     public FactoryData(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
/*     */     {
/* 158 */       this.factoryName = paramString1;
/* 159 */       this.providerURL = paramString2;
/* 160 */       this.urlPkgPrefixes = paramString3;
/* 161 */       this.securityPrincipalName = paramString4;
/* 162 */       this.securityCredentials = paramString5;
/* 163 */       this.factoryBindingName = paramString6;
/* 164 */       this.queueBindingName = paramString7;
/* 165 */       this.userName = paramString8;
/* 166 */       this.password = paramString9;
/*     */     }
/*     */   }
/*     */   
/*     */   private static QueueInfo connect(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean) throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 174 */       QueueConnectionFactory localQueueConnectionFactory = (QueueConnectionFactory)lookup(paramContext, paramString1);
/*     */       QueueConnection localQueueConnection;
/* 176 */       if (paramString3 != null) {
/* 177 */         localQueueConnection = localQueueConnectionFactory.createQueueConnection(paramString3, paramString4);
/*     */       } else {
/* 179 */         localQueueConnection = localQueueConnectionFactory.createQueueConnection();
/*     */       }
/* 181 */       QueueSession localQueueSession = localQueueConnection.createQueueSession(false, 1);
/* 182 */       Queue localQueue = (Queue)lookup(paramContext, paramString2);
/* 183 */       QueueSender localQueueSender = localQueueSession.createSender(localQueue);
/* 184 */       localQueueConnection.start();
/* 185 */       return new QueueInfo(localQueueConnection, localQueueSession, localQueueSender);
/*     */     } catch (NamingException localNamingException) {
/* 187 */       LOGGER.warn("Unable to locate connection factory " + paramString1, localNamingException);
/* 188 */       if (!paramBoolean) {
/* 189 */         throw localNamingException;
/*     */       }
/*     */     } catch (JMSException localJMSException) {
/* 192 */       LOGGER.warn("Unable to create connection to queue " + paramString2, localJMSException);
/* 193 */       if (!paramBoolean) {
/* 194 */         throw localJMSException;
/*     */       }
/*     */     }
/* 197 */     return null;
/*     */   }
/*     */   
/*     */   private static class QueueInfo
/*     */   {
/*     */     private final QueueConnection conn;
/*     */     private final QueueSession session;
/*     */     private final QueueSender sender;
/*     */     
/*     */     public QueueInfo(QueueConnection paramQueueConnection, QueueSession paramQueueSession, QueueSender paramQueueSender) {
/* 207 */       this.conn = paramQueueConnection;
/* 208 */       this.session = paramQueueSession;
/* 209 */       this.sender = paramQueueSender;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class JMSQueueManagerFactory
/*     */     implements ManagerFactory<JMSQueueManager, JMSQueueManager.FactoryData>
/*     */   {
/*     */     public JMSQueueManager createManager(String paramString, JMSQueueManager.FactoryData paramFactoryData)
/*     */     {
/*     */       try
/*     */       {
/* 221 */         Context localContext = AbstractJMSManager.createContext(JMSQueueManager.FactoryData.access$400(paramFactoryData), JMSQueueManager.FactoryData.access$500(paramFactoryData), JMSQueueManager.FactoryData.access$600(paramFactoryData), JMSQueueManager.FactoryData.access$700(paramFactoryData), JMSQueueManager.FactoryData.access$800(paramFactoryData));
/*     */         
/* 223 */         JMSQueueManager.QueueInfo localQueueInfo = JMSQueueManager.connect(localContext, JMSQueueManager.FactoryData.access$900(paramFactoryData), JMSQueueManager.FactoryData.access$1000(paramFactoryData), JMSQueueManager.FactoryData.access$1100(paramFactoryData), JMSQueueManager.FactoryData.access$1200(paramFactoryData), true);
/*     */         
/* 225 */         return new JMSQueueManager(paramString, localContext, JMSQueueManager.FactoryData.access$900(paramFactoryData), JMSQueueManager.FactoryData.access$1000(paramFactoryData), JMSQueueManager.FactoryData.access$1100(paramFactoryData), JMSQueueManager.FactoryData.access$1200(paramFactoryData), localQueueInfo);
/*     */       }
/*     */       catch (NamingException localNamingException) {
/* 228 */         JMSQueueManager.LOGGER.error("Unable to locate resource", localNamingException);
/*     */       } catch (Exception localException) {
/* 230 */         JMSQueueManager.LOGGER.error("Unable to connect", localException);
/*     */       }
/*     */       
/* 233 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\JMSQueueManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */