/*     */ package com.avaje.ebeaninternal.server.lib.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class MailSender
/*     */   implements Runnable
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(MailSender.class.getName());
/*     */   
/*  38 */   int traceLevel = 0;
/*     */   
/*     */   Socket sserver;
/*     */   
/*     */   String server;
/*     */   
/*     */   BufferedReader in;
/*     */   
/*     */   OutputStreamWriter out;
/*     */   
/*     */   MailMessage message;
/*  49 */   MailListener listener = null;
/*     */   
/*     */ 
/*     */   private static final int SMTP_PORT = 25;
/*     */   
/*     */ 
/*     */   public MailSender(String server)
/*     */   {
/*  57 */     this.server = server;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setMailListener(MailListener listener)
/*     */   {
/*  64 */     this.listener = listener;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void run()
/*     */   {
/*  71 */     send(this.message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void sendInBackground(MailMessage message)
/*     */   {
/*  78 */     this.message = message;
/*  79 */     Thread thread = new Thread(this);
/*  80 */     thread.start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void send(MailMessage message)
/*     */   {
/*     */     try
/*     */     {
/*  89 */       Iterator<MailAddress> i = message.getRecipientList();
/*  90 */       while (i.hasNext()) {
/*  91 */         MailAddress recipientAddress = (MailAddress)i.next();
/*  92 */         this.sserver = new Socket(this.server, 25);
/*  93 */         send(message, this.sserver, recipientAddress);
/*  94 */         this.sserver.close();
/*     */         
/*  96 */         if (this.listener != null) {
/*  97 */           MailEvent event = new MailEvent(message, null);
/*  98 */           this.listener.handleEvent(event);
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/* 102 */       if (this.listener != null) {
/* 103 */         MailEvent event = new MailEvent(message, ex);
/* 104 */         this.listener.handleEvent(event);
/*     */       } else {
/* 106 */         logger.log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void send(MailMessage message, Socket sserver, MailAddress recipientAddress)
/*     */     throws IOException
/*     */   {
/* 114 */     InetAddress localhost = sserver.getLocalAddress();
/* 115 */     String localaddress = localhost.getHostAddress();
/* 116 */     MailAddress sender = message.getSender();
/* 117 */     message.setCurrentRecipient(recipientAddress);
/*     */     
/*     */ 
/* 120 */     if (message.getHeader("Date") == null) {
/* 121 */       message.addHeader("Date", new Date().toString());
/*     */     }
/* 123 */     if (message.getHeader("From") == null) {
/* 124 */       message.addHeader("From", sender.getAlias() + " <" + sender.getEmailAddress() + ">");
/*     */     }
/*     */     
/*     */ 
/* 128 */     message.addHeader("To", recipientAddress.getAlias() + " <" + recipientAddress.getEmailAddress() + ">");
/*     */     
/*     */ 
/* 131 */     this.out = new OutputStreamWriter(sserver.getOutputStream());
/* 132 */     this.in = new BufferedReader(new InputStreamReader(sserver.getInputStream()));
/* 133 */     String sintro = readln();
/* 134 */     if (!sintro.startsWith("220")) {
/* 135 */       logger.fine("SmtpSender: intro==" + sintro);
/* 136 */       return;
/*     */     }
/*     */     
/* 139 */     writeln("EHLO " + localaddress);
/* 140 */     if (!expect250()) {
/* 141 */       return;
/*     */     }
/*     */     
/* 144 */     writeln("MAIL FROM:<" + sender.getEmailAddress() + ">");
/* 145 */     if (!expect250()) {
/* 146 */       return;
/*     */     }
/* 148 */     writeln("RCPT TO:<" + recipientAddress.getEmailAddress() + ">");
/* 149 */     if (!expect250()) {
/* 150 */       return;
/*     */     }
/* 152 */     writeln("DATA");
/*     */     for (;;) {
/* 154 */       String line = readln();
/* 155 */       if (line.startsWith("3"))
/*     */         break;
/* 157 */       if (!line.startsWith("2")) {
/* 158 */         logger.fine("SmtpSender.send reponse to DATA: " + line);
/* 159 */         return;
/*     */       }
/*     */     }
/* 162 */     Iterator<String> hi = message.getHeaderFields();
/* 163 */     while (hi.hasNext()) {
/* 164 */       String key = (String)hi.next();
/* 165 */       writeln(key + ": " + message.getHeader(key));
/*     */     }
/* 167 */     writeln("");
/* 168 */     Iterator<String> e = message.getBodyLines();
/* 169 */     while (e.hasNext()) {
/* 170 */       String bline = (String)e.next();
/* 171 */       if (bline.startsWith(".")) {
/* 172 */         bline = "." + bline;
/*     */       }
/* 174 */       writeln(bline);
/*     */     }
/* 176 */     writeln(".");
/* 177 */     expect250();
/* 178 */     writeln("QUIT");
/*     */   }
/*     */   
/*     */   private boolean expect250() throws IOException
/*     */   {
/* 183 */     String line = readln();
/* 184 */     if (!line.startsWith("2")) {
/* 185 */       logger.info("SmtpSender.expect250: " + line);
/* 186 */       return false;
/*     */     }
/* 188 */     return true;
/*     */   }
/*     */   
/*     */   private void writeln(String s) throws IOException {
/* 192 */     if (this.traceLevel > 2) {
/* 193 */       logger.fine("From client: " + s);
/*     */     }
/* 195 */     this.out.write(s + "\r\n");
/* 196 */     this.out.flush();
/*     */   }
/*     */   
/*     */   private String readln() throws IOException {
/* 200 */     String line = this.in.readLine();
/* 201 */     if (this.traceLevel > 1) {
/* 202 */       logger.fine("From server: " + line);
/*     */     }
/* 204 */     return line;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTraceLevel(int traceLevel)
/*     */   {
/* 212 */     this.traceLevel = traceLevel;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getLocalHostName()
/*     */   {
/*     */     try
/*     */     {
/* 220 */       InetAddress ipaddress = InetAddress.getLocalHost();
/* 221 */       String localHost = ipaddress.getHostName();
/* 222 */       if (localHost == null) {
/* 223 */         return "localhost";
/*     */       }
/* 225 */       return localHost;
/*     */     }
/*     */     catch (UnknownHostException e) {}
/* 228 */     return "localhost";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\util\MailSender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */