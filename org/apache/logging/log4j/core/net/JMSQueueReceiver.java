/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.charset.Charset;
/*     */ import javax.jms.JMSException;
/*     */ import javax.jms.Queue;
/*     */ import javax.jms.QueueConnection;
/*     */ import javax.jms.QueueConnectionFactory;
/*     */ import javax.jms.QueueReceiver;
/*     */ import javax.jms.QueueSession;
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
/*     */ 
/*     */ public class JMSQueueReceiver
/*     */   extends AbstractJMSReceiver
/*     */ {
/*     */   public JMSQueueReceiver(String paramString1, String paramString2, String paramString3, String paramString4)
/*     */   {
/*     */     try
/*     */     {
/*  50 */       InitialContext localInitialContext = new InitialContext();
/*     */       
/*  52 */       QueueConnectionFactory localQueueConnectionFactory = (QueueConnectionFactory)lookup(localInitialContext, paramString1);
/*  53 */       QueueConnection localQueueConnection = localQueueConnectionFactory.createQueueConnection(paramString3, paramString4);
/*  54 */       localQueueConnection.start();
/*  55 */       QueueSession localQueueSession = localQueueConnection.createQueueSession(false, 1);
/*  56 */       Queue localQueue = (Queue)localInitialContext.lookup(paramString2);
/*  57 */       QueueReceiver localQueueReceiver = localQueueSession.createReceiver(localQueue);
/*  58 */       localQueueReceiver.setMessageListener(this);
/*     */     } catch (JMSException localJMSException) {
/*  60 */       this.logger.error("Could not read JMS message.", localJMSException);
/*     */     } catch (NamingException localNamingException) {
/*  62 */       this.logger.error("Could not read JMS message.", localNamingException);
/*     */     } catch (RuntimeException localRuntimeException) {
/*  64 */       this.logger.error("Could not read JMS message.", localRuntimeException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] paramArrayOfString)
/*     */     throws Exception
/*     */   {
/*  74 */     if (paramArrayOfString.length != 4) {
/*  75 */       usage("Wrong number of arguments.");
/*     */     }
/*     */     
/*  78 */     String str1 = paramArrayOfString[0];
/*  79 */     String str2 = paramArrayOfString[1];
/*  80 */     String str3 = paramArrayOfString[2];
/*  81 */     String str4 = paramArrayOfString[3];
/*     */     
/*  83 */     new JMSQueueReceiver(str1, str2, str3, str4);
/*     */     
/*  85 */     Charset localCharset = Charset.defaultCharset();
/*  86 */     BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(System.in, localCharset));
/*     */     
/*  88 */     System.out.println("Type \"exit\" to quit JMSQueueReceiver.");
/*     */     for (;;) {
/*  90 */       String str5 = localBufferedReader.readLine();
/*  91 */       if ((str5 == null) || (str5.equalsIgnoreCase("exit"))) {
/*  92 */         System.out.println("Exiting. Kill the application if it does not exit due to daemon threads.");
/*     */         
/*  94 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void usage(String paramString)
/*     */   {
/* 101 */     System.err.println(paramString);
/* 102 */     System.err.println("Usage: java " + JMSQueueReceiver.class.getName() + " QueueConnectionFactoryBindingName QueueBindingName username password");
/*     */     
/* 104 */     System.exit(1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\JMSQueueReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */