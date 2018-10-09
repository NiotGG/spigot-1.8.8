/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Date;
/*     */ import java.util.Properties;
/*     */ import javax.mail.Authenticator;
/*     */ import javax.mail.Message.RecipientType;
/*     */ import javax.mail.MessagingException;
/*     */ import javax.mail.PasswordAuthentication;
/*     */ import javax.mail.Session;
/*     */ import javax.mail.Transport;
/*     */ import javax.mail.internet.InternetHeaders;
/*     */ import javax.mail.internet.MimeBodyPart;
/*     */ import javax.mail.internet.MimeMessage;
/*     */ import javax.mail.internet.MimeMultipart;
/*     */ import javax.mail.internet.MimeUtility;
/*     */ import javax.mail.util.ByteArrayDataSource;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.LoggingException;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractManager;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.helpers.CyclicBuffer;
/*     */ import org.apache.logging.log4j.core.helpers.NameUtil;
/*     */ import org.apache.logging.log4j.core.helpers.NetUtils;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.util.PropertiesUtil;
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
/*     */ public class SMTPManager
/*     */   extends AbstractManager
/*     */ {
/*  54 */   private static final SMTPManagerFactory FACTORY = new SMTPManagerFactory(null);
/*     */   
/*     */   private final Session session;
/*     */   
/*     */   private final CyclicBuffer<LogEvent> buffer;
/*     */   
/*     */   private volatile MimeMessage message;
/*     */   
/*     */   private final FactoryData data;
/*     */   
/*     */   protected SMTPManager(String paramString, Session paramSession, MimeMessage paramMimeMessage, FactoryData paramFactoryData)
/*     */   {
/*  66 */     super(paramString);
/*  67 */     this.session = paramSession;
/*  68 */     this.message = paramMimeMessage;
/*  69 */     this.data = paramFactoryData;
/*  70 */     this.buffer = new CyclicBuffer(LogEvent.class, paramFactoryData.numElements);
/*     */   }
/*     */   
/*     */   public void add(LogEvent paramLogEvent) {
/*  74 */     this.buffer.add(paramLogEvent);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static SMTPManager getSMTPManager(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, int paramInt1, String paramString9, String paramString10, boolean paramBoolean, String paramString11, int paramInt2)
/*     */   {
/*  82 */     if (Strings.isEmpty(paramString7)) {
/*  83 */       paramString7 = "smtp";
/*     */     }
/*     */     
/*  86 */     StringBuilder localStringBuilder = new StringBuilder();
/*  87 */     if (paramString1 != null) {
/*  88 */       localStringBuilder.append(paramString1);
/*     */     }
/*  90 */     localStringBuilder.append(":");
/*  91 */     if (paramString2 != null) {
/*  92 */       localStringBuilder.append(paramString2);
/*     */     }
/*  94 */     localStringBuilder.append(":");
/*  95 */     if (paramString3 != null) {
/*  96 */       localStringBuilder.append(paramString3);
/*     */     }
/*  98 */     localStringBuilder.append(":");
/*  99 */     if (paramString4 != null) {
/* 100 */       localStringBuilder.append(paramString4);
/*     */     }
/* 102 */     localStringBuilder.append(":");
/* 103 */     if (paramString5 != null) {
/* 104 */       localStringBuilder.append(paramString5);
/*     */     }
/* 106 */     localStringBuilder.append(":");
/* 107 */     if (paramString6 != null) {
/* 108 */       localStringBuilder.append(paramString6);
/*     */     }
/* 110 */     localStringBuilder.append(":");
/* 111 */     localStringBuilder.append(paramString7).append(":").append(paramString8).append(":").append("port").append(":");
/* 112 */     if (paramString9 != null) {
/* 113 */       localStringBuilder.append(paramString9);
/*     */     }
/* 115 */     localStringBuilder.append(":");
/* 116 */     if (paramString10 != null) {
/* 117 */       localStringBuilder.append(paramString10);
/*     */     }
/* 119 */     localStringBuilder.append(paramBoolean ? ":debug:" : "::");
/* 120 */     localStringBuilder.append(paramString11);
/*     */     
/* 122 */     String str = "SMTP:" + NameUtil.md5(localStringBuilder.toString());
/*     */     
/* 124 */     return (SMTPManager)getManager(str, FACTORY, new FactoryData(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramInt1, paramString9, paramString10, paramBoolean, paramInt2));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sendEvents(Layout<?> paramLayout, LogEvent paramLogEvent)
/*     */   {
/* 134 */     if (this.message == null) {
/* 135 */       connect();
/*     */     }
/*     */     try {
/* 138 */       LogEvent[] arrayOfLogEvent = (LogEvent[])this.buffer.removeAll();
/*     */       
/*     */ 
/* 141 */       byte[] arrayOfByte1 = formatContentToBytes(arrayOfLogEvent, paramLogEvent, paramLayout);
/*     */       
/* 143 */       String str1 = paramLayout.getContentType();
/* 144 */       String str2 = getEncoding(arrayOfByte1, str1);
/* 145 */       byte[] arrayOfByte2 = encodeContentToBytes(arrayOfByte1, str2);
/*     */       
/* 147 */       InternetHeaders localInternetHeaders = getHeaders(str1, str2);
/* 148 */       MimeMultipart localMimeMultipart = getMimeMultipart(arrayOfByte2, localInternetHeaders);
/*     */       
/* 150 */       sendMultipartMessage(this.message, localMimeMultipart);
/*     */     } catch (MessagingException localMessagingException) {
/* 152 */       LOGGER.error("Error occurred while sending e-mail notification.", localMessagingException);
/* 153 */       throw new LoggingException("Error occurred while sending email", localMessagingException);
/*     */     } catch (IOException localIOException) {
/* 155 */       LOGGER.error("Error occurred while sending e-mail notification.", localIOException);
/* 156 */       throw new LoggingException("Error occurred while sending email", localIOException);
/*     */     } catch (RuntimeException localRuntimeException) {
/* 158 */       LOGGER.error("Error occurred while sending e-mail notification.", localRuntimeException);
/* 159 */       throw new LoggingException("Error occurred while sending email", localRuntimeException);
/*     */     }
/*     */   }
/*     */   
/*     */   protected byte[] formatContentToBytes(LogEvent[] paramArrayOfLogEvent, LogEvent paramLogEvent, Layout<?> paramLayout) throws IOException
/*     */   {
/* 165 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 166 */     writeContent(paramArrayOfLogEvent, paramLogEvent, paramLayout, localByteArrayOutputStream);
/* 167 */     return localByteArrayOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   private void writeContent(LogEvent[] paramArrayOfLogEvent, LogEvent paramLogEvent, Layout<?> paramLayout, ByteArrayOutputStream paramByteArrayOutputStream)
/*     */     throws IOException
/*     */   {
/* 173 */     writeHeader(paramLayout, paramByteArrayOutputStream);
/* 174 */     writeBuffer(paramArrayOfLogEvent, paramLogEvent, paramLayout, paramByteArrayOutputStream);
/* 175 */     writeFooter(paramLayout, paramByteArrayOutputStream);
/*     */   }
/*     */   
/*     */   protected void writeHeader(Layout<?> paramLayout, OutputStream paramOutputStream) throws IOException {
/* 179 */     byte[] arrayOfByte = paramLayout.getHeader();
/* 180 */     if (arrayOfByte != null) {
/* 181 */       paramOutputStream.write(arrayOfByte);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void writeBuffer(LogEvent[] paramArrayOfLogEvent, LogEvent paramLogEvent, Layout<?> paramLayout, OutputStream paramOutputStream) throws IOException
/*     */   {
/* 187 */     for (LogEvent localLogEvent : paramArrayOfLogEvent) {
/* 188 */       byte[] arrayOfByte = paramLayout.toByteArray(localLogEvent);
/* 189 */       paramOutputStream.write(arrayOfByte);
/*     */     }
/*     */     
/* 192 */     ??? = paramLayout.toByteArray(paramLogEvent);
/* 193 */     paramOutputStream.write((byte[])???);
/*     */   }
/*     */   
/*     */   protected void writeFooter(Layout<?> paramLayout, OutputStream paramOutputStream) throws IOException {
/* 197 */     byte[] arrayOfByte = paramLayout.getFooter();
/* 198 */     if (arrayOfByte != null) {
/* 199 */       paramOutputStream.write(arrayOfByte);
/*     */     }
/*     */   }
/*     */   
/*     */   protected String getEncoding(byte[] paramArrayOfByte, String paramString) {
/* 204 */     ByteArrayDataSource localByteArrayDataSource = new ByteArrayDataSource(paramArrayOfByte, paramString);
/* 205 */     return MimeUtility.getEncoding(localByteArrayDataSource);
/*     */   }
/*     */   
/*     */   protected byte[] encodeContentToBytes(byte[] paramArrayOfByte, String paramString) throws MessagingException, IOException
/*     */   {
/* 210 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/* 211 */     encodeContent(paramArrayOfByte, paramString, localByteArrayOutputStream);
/* 212 */     return localByteArrayOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   protected void encodeContent(byte[] paramArrayOfByte, String paramString, ByteArrayOutputStream paramByteArrayOutputStream) throws MessagingException, IOException
/*     */   {
/* 217 */     OutputStream localOutputStream = MimeUtility.encode(paramByteArrayOutputStream, paramString);
/* 218 */     localOutputStream.write(paramArrayOfByte);
/* 219 */     localOutputStream.close();
/*     */   }
/*     */   
/*     */   protected InternetHeaders getHeaders(String paramString1, String paramString2) {
/* 223 */     InternetHeaders localInternetHeaders = new InternetHeaders();
/* 224 */     localInternetHeaders.setHeader("Content-Type", paramString1 + "; charset=UTF-8");
/* 225 */     localInternetHeaders.setHeader("Content-Transfer-Encoding", paramString2);
/* 226 */     return localInternetHeaders;
/*     */   }
/*     */   
/*     */   protected MimeMultipart getMimeMultipart(byte[] paramArrayOfByte, InternetHeaders paramInternetHeaders) throws MessagingException
/*     */   {
/* 231 */     MimeMultipart localMimeMultipart = new MimeMultipart();
/* 232 */     MimeBodyPart localMimeBodyPart = new MimeBodyPart(paramInternetHeaders, paramArrayOfByte);
/* 233 */     localMimeMultipart.addBodyPart(localMimeBodyPart);
/* 234 */     return localMimeMultipart;
/*     */   }
/*     */   
/*     */   protected void sendMultipartMessage(MimeMessage paramMimeMessage, MimeMultipart paramMimeMultipart) throws MessagingException {
/* 238 */     synchronized (paramMimeMessage) {
/* 239 */       paramMimeMessage.setContent(paramMimeMultipart);
/* 240 */       paramMimeMessage.setSentDate(new Date());
/* 241 */       Transport.send(paramMimeMessage);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String to;
/*     */     
/*     */     private final String cc;
/*     */     
/*     */     private final String bcc;
/*     */     private final String from;
/*     */     private final String replyto;
/*     */     private final String subject;
/*     */     private final String protocol;
/*     */     private final String host;
/*     */     private final int port;
/*     */     private final String username;
/*     */     private final String password;
/*     */     private final boolean isDebug;
/*     */     private final int numElements;
/*     */     
/*     */     public FactoryData(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, int paramInt1, String paramString9, String paramString10, boolean paramBoolean, int paramInt2)
/*     */     {
/* 266 */       this.to = paramString1;
/* 267 */       this.cc = paramString2;
/* 268 */       this.bcc = paramString3;
/* 269 */       this.from = paramString4;
/* 270 */       this.replyto = paramString5;
/* 271 */       this.subject = paramString6;
/* 272 */       this.protocol = paramString7;
/* 273 */       this.host = paramString8;
/* 274 */       this.port = paramInt1;
/* 275 */       this.username = paramString9;
/* 276 */       this.password = paramString10;
/* 277 */       this.isDebug = paramBoolean;
/* 278 */       this.numElements = paramInt2;
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void connect() {
/* 283 */     if (this.message != null) {
/* 284 */       return;
/*     */     }
/*     */     try {
/* 287 */       this.message = new MimeMessageBuilder(this.session).setFrom(this.data.from).setReplyTo(this.data.replyto).setRecipients(Message.RecipientType.TO, this.data.to).setRecipients(Message.RecipientType.CC, this.data.cc).setRecipients(Message.RecipientType.BCC, this.data.bcc).setSubject(this.data.subject).getMimeMessage();
/*     */     }
/*     */     catch (MessagingException localMessagingException)
/*     */     {
/* 291 */       LOGGER.error("Could not set SMTPAppender message options.", localMessagingException);
/* 292 */       this.message = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static class SMTPManagerFactory
/*     */     implements ManagerFactory<SMTPManager, SMTPManager.FactoryData>
/*     */   {
/*     */     public SMTPManager createManager(String paramString, SMTPManager.FactoryData paramFactoryData)
/*     */     {
/* 303 */       String str = "mail." + SMTPManager.FactoryData.access$800(paramFactoryData);
/*     */       
/* 305 */       Properties localProperties = PropertiesUtil.getSystemProperties();
/* 306 */       localProperties.put("mail.transport.protocol", SMTPManager.FactoryData.access$800(paramFactoryData));
/* 307 */       if (localProperties.getProperty("mail.host") == null)
/*     */       {
/* 309 */         localProperties.put("mail.host", NetUtils.getLocalHostname());
/*     */       }
/*     */       
/* 312 */       if (null != SMTPManager.FactoryData.access$900(paramFactoryData)) {
/* 313 */         localProperties.put(str + ".host", SMTPManager.FactoryData.access$900(paramFactoryData));
/*     */       }
/* 315 */       if (SMTPManager.FactoryData.access$1000(paramFactoryData) > 0) {
/* 316 */         localProperties.put(str + ".port", String.valueOf(SMTPManager.FactoryData.access$1000(paramFactoryData)));
/*     */       }
/*     */       
/* 319 */       Authenticator localAuthenticator = buildAuthenticator(SMTPManager.FactoryData.access$1100(paramFactoryData), SMTPManager.FactoryData.access$1200(paramFactoryData));
/* 320 */       if (null != localAuthenticator) {
/* 321 */         localProperties.put(str + ".auth", "true");
/*     */       }
/*     */       
/* 324 */       Session localSession = Session.getInstance(localProperties, localAuthenticator);
/* 325 */       localSession.setProtocolForAddress("rfc822", SMTPManager.FactoryData.access$800(paramFactoryData));
/* 326 */       localSession.setDebug(SMTPManager.FactoryData.access$1300(paramFactoryData));
/*     */       MimeMessage localMimeMessage;
/*     */       try
/*     */       {
/* 330 */         localMimeMessage = new MimeMessageBuilder(localSession).setFrom(SMTPManager.FactoryData.access$700(paramFactoryData)).setReplyTo(SMTPManager.FactoryData.access$600(paramFactoryData)).setRecipients(Message.RecipientType.TO, SMTPManager.FactoryData.access$500(paramFactoryData)).setRecipients(Message.RecipientType.CC, SMTPManager.FactoryData.access$400(paramFactoryData)).setRecipients(Message.RecipientType.BCC, SMTPManager.FactoryData.access$300(paramFactoryData)).setSubject(SMTPManager.FactoryData.access$200(paramFactoryData)).getMimeMessage();
/*     */       }
/*     */       catch (MessagingException localMessagingException)
/*     */       {
/* 334 */         SMTPManager.LOGGER.error("Could not set SMTPAppender message options.", localMessagingException);
/* 335 */         localMimeMessage = null;
/*     */       }
/*     */       
/* 338 */       return new SMTPManager(paramString, localSession, localMimeMessage, paramFactoryData);
/*     */     }
/*     */     
/*     */     private Authenticator buildAuthenticator(final String paramString1, final String paramString2) {
/* 342 */       if ((null != paramString2) && (null != paramString1)) {
/* 343 */         new Authenticator() {
/* 344 */           private final PasswordAuthentication passwordAuthentication = new PasswordAuthentication(paramString1, paramString2);
/*     */           
/*     */ 
/*     */           protected PasswordAuthentication getPasswordAuthentication()
/*     */           {
/* 349 */             return this.passwordAuthentication;
/*     */           }
/*     */         };
/*     */       }
/* 353 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\SMTPManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */