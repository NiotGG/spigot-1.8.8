/*    */ package org.apache.logging.log4j.core.net;
/*    */ 
/*    */ import javax.jms.JMSException;
/*    */ import javax.jms.Message;
/*    */ import javax.jms.MessageListener;
/*    */ import javax.jms.ObjectMessage;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.NameNotFoundException;
/*    */ import javax.naming.NamingException;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.AbstractServer;
/*    */ import org.apache.logging.log4j.core.LogEvent;
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
/*    */ public abstract class AbstractJMSReceiver
/*    */   extends AbstractServer
/*    */   implements MessageListener
/*    */ {
/* 38 */   protected Logger logger = LogManager.getLogger(getClass().getName());
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onMessage(Message paramMessage)
/*    */   {
/*    */     try
/*    */     {
/* 48 */       if ((paramMessage instanceof ObjectMessage)) {
/* 49 */         ObjectMessage localObjectMessage = (ObjectMessage)paramMessage;
/* 50 */         log((LogEvent)localObjectMessage.getObject());
/*    */       } else {
/* 52 */         this.logger.warn("Received message is of type " + paramMessage.getJMSType() + ", was expecting ObjectMessage.");
/*    */       }
/*    */     }
/*    */     catch (JMSException localJMSException) {
/* 56 */       this.logger.error("Exception thrown while processing incoming message.", localJMSException);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected Object lookup(Context paramContext, String paramString)
/*    */     throws NamingException
/*    */   {
/*    */     try
/*    */     {
/* 70 */       return paramContext.lookup(paramString);
/*    */     } catch (NameNotFoundException localNameNotFoundException) {
/* 72 */       this.logger.error("Could not find name [" + paramString + "].");
/* 73 */       throw localNameNotFoundException;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\AbstractJMSReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */