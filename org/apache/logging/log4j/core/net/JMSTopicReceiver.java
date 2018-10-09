/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.charset.Charset;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Topic;
/*     */ import javax.jms.TopicConnection;
/*     */ import javax.jms.TopicConnectionFactory;
/*     */ import javax.jms.TopicSession;
/*     */ import javax.jms.TopicSubscriber;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMSTopicReceiver
/*     */   extends AbstractJMSReceiver
/*     */ {
/*     */   public JMSTopicReceiver(String paramString1, String paramString2, String paramString3, String paramString4)
/*     */   {
/*     */     try
/*     */     {
/*  49 */       InitialContext localInitialContext = new InitialContext();
/*     */       
/*  51 */       TopicConnectionFactory localTopicConnectionFactory = (TopicConnectionFactory)lookup(localInitialContext, paramString1);
/*  52 */       TopicConnection localTopicConnection = localTopicConnectionFactory.createTopicConnection(paramString3, paramString4);
/*  53 */       localTopicConnection.start();
/*  54 */       TopicSession localTopicSession = localTopicConnection.createTopicSession(false, 1);
/*  55 */       Topic localTopic = (Topic)localInitialContext.lookup(paramString2);
/*  56 */       TopicSubscriber localTopicSubscriber = localTopicSession.createSubscriber(localTopic);
/*  57 */       localTopicSubscriber.setMessageListener(this);
/*     */     } catch (JMSException localJMSException) {
/*  59 */       this.logger.error("Could not read JMS message.", localJMSException);
/*     */     } catch (NamingException localNamingException) {
/*  61 */       this.logger.error("Could not read JMS message.", localNamingException);
/*     */     } catch (RuntimeException localRuntimeException) {
/*  63 */       this.logger.error("Could not read JMS message.", localRuntimeException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] paramArrayOfString)
/*     */     throws Exception
/*     */   {
/*  73 */     if (paramArrayOfString.length != 4) {
/*  74 */       usage("Wrong number of arguments.");
/*     */     }
/*     */     
/*  77 */     String str1 = paramArrayOfString[0];
/*  78 */     String str2 = paramArrayOfString[1];
/*  79 */     String str3 = paramArrayOfString[2];
/*  80 */     String str4 = paramArrayOfString[3];
/*     */     
/*  82 */     new JMSTopicReceiver(str1, str2, str3, str4);
/*     */     
/*  84 */     Charset localCharset = Charset.defaultCharset();
/*  85 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(System.in, localCharset));
/*     */     
/*  87 */     System.out.println("Type \"exit\" to quit JMSTopicReceiver.");
/*     */     for (;;) {
/*  89 */       String str5 = localBufferedReader.readLine();
/*  90 */       if ((str5 == null) || (str5.equalsIgnoreCase("exit"))) {
/*  91 */         System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
/*     */         
/*  93 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void usage(String paramString) {
/*  99 */     System.err.println(paramString);
/* 100 */     System.err.println("Usage: java " + JMSTopicReceiver.class.getName() + " TopicConnectionFactoryBindingName TopicBindingName username password");
/*     */     
/* 102 */     System.exit(1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\JMSTopicReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */