/*    */ package org.apache.logging.log4j.core.net;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import javax.mail.Message.RecipientType;
/*    */ import javax.mail.MessagingException;
/*    */ import javax.mail.Session;
/*    */ import javax.mail.internet.AddressException;
/*    */ import javax.mail.internet.InternetAddress;
/*    */ import javax.mail.internet.MimeMessage;
/*    */ import org.apache.logging.log4j.core.helpers.Charsets;
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
/*    */ public class MimeMessageBuilder
/*    */ {
/*    */   private final MimeMessage message;
/*    */   
/*    */   public MimeMessageBuilder(Session paramSession)
/*    */   {
/* 35 */     this.message = new MimeMessage(paramSession);
/*    */   }
/*    */   
/*    */   public MimeMessageBuilder setFrom(String paramString) throws MessagingException {
/* 39 */     InternetAddress localInternetAddress = parseAddress(paramString);
/*    */     
/* 41 */     if (null != localInternetAddress) {
/* 42 */       this.message.setFrom(localInternetAddress);
/*    */     } else {
/*    */       try {
/* 45 */         this.message.setFrom();
/*    */       } catch (Exception localException) {
/* 47 */         this.message.setFrom((InternetAddress)null);
/*    */       }
/*    */     }
/* 50 */     return this;
/*    */   }
/*    */   
/*    */   public MimeMessageBuilder setReplyTo(String paramString) throws MessagingException {
/* 54 */     InternetAddress[] arrayOfInternetAddress = parseAddresses(paramString);
/*    */     
/* 56 */     if (null != arrayOfInternetAddress) {
/* 57 */       this.message.setReplyTo(arrayOfInternetAddress);
/*    */     }
/* 59 */     return this;
/*    */   }
/*    */   
/*    */   public MimeMessageBuilder setRecipients(Message.RecipientType paramRecipientType, String paramString) throws MessagingException
/*    */   {
/* 64 */     InternetAddress[] arrayOfInternetAddress = parseAddresses(paramString);
/*    */     
/* 66 */     if (null != arrayOfInternetAddress) {
/* 67 */       this.message.setRecipients(paramRecipientType, arrayOfInternetAddress);
/*    */     }
/* 69 */     return this;
/*    */   }
/*    */   
/*    */   public MimeMessageBuilder setSubject(String paramString) throws MessagingException {
/* 73 */     if (paramString != null) {
/* 74 */       this.message.setSubject(paramString, Charsets.UTF_8.name());
/*    */     }
/* 76 */     return this;
/*    */   }
/*    */   
/*    */   public MimeMessage getMimeMessage() {
/* 80 */     return this.message;
/*    */   }
/*    */   
/*    */   private static InternetAddress parseAddress(String paramString) throws AddressException {
/* 84 */     return paramString == null ? null : new InternetAddress(paramString);
/*    */   }
/*    */   
/*    */   private static InternetAddress[] parseAddresses(String paramString) throws AddressException {
/* 88 */     return paramString == null ? null : InternetAddress.parse(paramString, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\MimeMessageBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */