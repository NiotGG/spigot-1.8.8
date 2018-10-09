/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Topic;
/*     */ import javax.jms.TopicConnection;
/*     */ import javax.jms.TopicConnectionFactory;
/*     */ import javax.jms.TopicPublisher;
/*     */ import javax.jms.TopicSession;
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
/*     */ public class JMSTopicManager
/*     */   extends AbstractJMSManager
/*     */ {
/*  37 */   private static final JMSTopicManagerFactory FACTORY = new JMSTopicManagerFactory(null);
/*     */   
/*     */ 
/*     */   private TopicInfo info;
/*     */   
/*     */ 
/*     */   private final String factoryBindingName;
/*     */   
/*     */ 
/*     */   private final String topicBindingName;
/*     */   
/*     */ 
/*     */   private final String userName;
/*     */   
/*     */   private final String password;
/*     */   
/*     */   private final Context context;
/*     */   
/*     */ 
/*     */   protected JMSTopicManager(String paramString1, Context paramContext, String paramString2, String paramString3, String paramString4, String paramString5, TopicInfo paramTopicInfo)
/*     */   {
/*  58 */     super(paramString1);
/*  59 */     this.context = paramContext;
/*  60 */     this.factoryBindingName = paramString2;
/*  61 */     this.topicBindingName = paramString3;
/*  62 */     this.userName = paramString4;
/*  63 */     this.password = paramString5;
/*  64 */     this.info = paramTopicInfo;
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
/*     */   public static JMSTopicManager getJMSTopicManager(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
/*     */   {
/*  87 */     if (paramString6 == null) {
/*  88 */       LOGGER.error("No factory name provided for JMSTopicManager");
/*  89 */       return null;
/*     */     }
/*  91 */     if (paramString7 == null) {
/*  92 */       LOGGER.error("No topic name provided for JMSTopicManager");
/*  93 */       return null;
/*     */     }
/*     */     
/*  96 */     String str = "JMSTopic:" + paramString6 + '.' + paramString7;
/*  97 */     return (JMSTopicManager)getManager(str, FACTORY, new FactoryData(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9));
/*     */   }
/*     */   
/*     */ 
/*     */   public void send(Serializable paramSerializable)
/*     */     throws Exception
/*     */   {
/* 104 */     if (this.info == null) {
/* 105 */       this.info = connect(this.context, this.factoryBindingName, this.topicBindingName, this.userName, this.password, false);
/*     */     }
/*     */     try {
/* 108 */       super.send(paramSerializable, this.info.session, this.info.publisher);
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
/*     */     private final String topicBindingName;
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
/* 164 */       this.topicBindingName = paramString7;
/* 165 */       this.userName = paramString8;
/* 166 */       this.password = paramString9;
/*     */     }
/*     */   }
/*     */   
/*     */   private static TopicInfo connect(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean) throws Exception
/*     */   {
/*     */     try
/*     */     {
/* 174 */       TopicConnectionFactory localTopicConnectionFactory = (TopicConnectionFactory)lookup(paramContext, paramString1);
/*     */       TopicConnection localTopicConnection;
/* 176 */       if (paramString3 != null) {
/* 177 */         localTopicConnection = localTopicConnectionFactory.createTopicConnection(paramString3, paramString4);
/*     */       } else {
/* 179 */         localTopicConnection = localTopicConnectionFactory.createTopicConnection();
/*     */       }
/* 181 */       TopicSession localTopicSession = localTopicConnection.createTopicSession(false, 1);
/* 182 */       Topic localTopic = (Topic)lookup(paramContext, paramString2);
/* 183 */       TopicPublisher localTopicPublisher = localTopicSession.createPublisher(localTopic);
/* 184 */       localTopicConnection.start();
/* 185 */       return new TopicInfo(localTopicConnection, localTopicSession, localTopicPublisher);
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
/*     */   private static class TopicInfo
/*     */   {
/*     */     private final TopicConnection conn;
/*     */     private final TopicSession session;
/*     */     private final TopicPublisher publisher;
/*     */     
/*     */     public TopicInfo(TopicConnection paramTopicConnection, TopicSession paramTopicSession, TopicPublisher paramTopicPublisher) {
/* 207 */       this.conn = paramTopicConnection;
/* 208 */       this.session = paramTopicSession;
/* 209 */       this.publisher = paramTopicPublisher;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class JMSTopicManagerFactory
/*     */     implements ManagerFactory<JMSTopicManager, JMSTopicManager.FactoryData>
/*     */   {
/*     */     public JMSTopicManager createManager(String paramString, JMSTopicManager.FactoryData paramFactoryData)
/*     */     {
/*     */       try
/*     */       {
/* 221 */         Context localContext = AbstractJMSManager.createContext(JMSTopicManager.FactoryData.access$400(paramFactoryData), JMSTopicManager.FactoryData.access$500(paramFactoryData), JMSTopicManager.FactoryData.access$600(paramFactoryData), JMSTopicManager.FactoryData.access$700(paramFactoryData), JMSTopicManager.FactoryData.access$800(paramFactoryData));
/*     */         
/* 223 */         JMSTopicManager.TopicInfo localTopicInfo = JMSTopicManager.connect(localContext, JMSTopicManager.FactoryData.access$900(paramFactoryData), JMSTopicManager.FactoryData.access$1000(paramFactoryData), JMSTopicManager.FactoryData.access$1100(paramFactoryData), JMSTopicManager.FactoryData.access$1200(paramFactoryData), true);
/*     */         
/* 225 */         return new JMSTopicManager(paramString, localContext, JMSTopicManager.FactoryData.access$900(paramFactoryData), JMSTopicManager.FactoryData.access$1000(paramFactoryData), JMSTopicManager.FactoryData.access$1100(paramFactoryData), JMSTopicManager.FactoryData.access$1200(paramFactoryData), localTopicInfo);
/*     */       }
/*     */       catch (NamingException localNamingException) {
/* 228 */         JMSTopicManager.LOGGER.error("Unable to locate resource", localNamingException);
/*     */       } catch (Exception localException) {
/* 230 */         JMSTopicManager.LOGGER.error("Unable to connect", localException);
/*     */       }
/*     */       
/* 233 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\JMSTopicManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */