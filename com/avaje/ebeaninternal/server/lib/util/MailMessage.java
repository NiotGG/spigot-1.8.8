/*     */ package com.avaje.ebeaninternal.server.lib.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class MailMessage
/*     */ {
/*     */   ArrayList<String> bodylines;
/*     */   MailAddress senderAddress;
/*  48 */   HashMap<String, String> header = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   MailAddress currentRecipient;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  58 */   ArrayList<MailAddress> recipientList = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */   public MailMessage()
/*     */   {
/*  64 */     this.bodylines = new ArrayList();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setCurrentRecipient(MailAddress currentRecipient)
/*     */   {
/*  71 */     this.currentRecipient = currentRecipient;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MailAddress getCurrentRecipient()
/*     */   {
/*  78 */     return this.currentRecipient;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addRecipient(String alias, String emailAddress)
/*     */   {
/*  85 */     this.recipientList.add(new MailAddress(alias, emailAddress));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSender(String alias, String senderEmail)
/*     */   {
/*  92 */     this.senderAddress = new MailAddress(alias, senderEmail);
/*     */   }
/*     */   
/*     */ 
/*     */   public MailAddress getSender()
/*     */   {
/*  98 */     return this.senderAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Iterator<MailAddress> getRecipientList()
/*     */   {
/* 105 */     return this.recipientList.iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addHeader(String key, String val)
/*     */   {
/* 112 */     this.header.put(key, val);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setSubject(String subject)
/*     */   {
/* 119 */     addHeader("Subject", subject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getSubject()
/*     */   {
/* 126 */     return getHeader("Subject");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void addBodyLine(String line)
/*     */   {
/* 133 */     this.bodylines.add(line);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Iterator<String> getBodyLines()
/*     */   {
/* 140 */     return this.bodylines.iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Iterator<String> getHeaderFields()
/*     */   {
/* 147 */     return this.header.keySet().iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getHeader(String key)
/*     */   {
/* 154 */     return (String)this.header.get(key);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 158 */     StringBuilder sb = new StringBuilder(100);
/* 159 */     sb.append("Sender: " + this.senderAddress + "\tRecipient: " + this.recipientList + "\n");
/* 160 */     Iterator<String> hi = this.header.keySet().iterator();
/* 161 */     while (hi.hasNext()) {
/* 162 */       String key = (String)hi.next();
/* 163 */       String hline = key + ": " + (String)this.header.get(key) + "\n";
/* 164 */       sb.append(hline);
/*     */     }
/* 166 */     sb.append("\n");
/* 167 */     Iterator<String> e = this.bodylines.iterator();
/* 168 */     while (e.hasNext()) {
/* 169 */       sb.append((String)e.next()).append("\n");
/*     */     }
/* 171 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\util\MailMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */