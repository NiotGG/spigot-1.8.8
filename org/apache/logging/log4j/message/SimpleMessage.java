/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleMessage
/*     */   implements Message
/*     */ {
/*     */   private static final long serialVersionUID = -8398002534962715992L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String message;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleMessage()
/*     */   {
/*  31 */     this(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public SimpleMessage(String paramString)
/*     */   {
/*  39 */     this.message = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/*  48 */     return this.message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormat()
/*     */   {
/*  57 */     return this.message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/*  66 */     return null;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  71 */     if (this == paramObject) {
/*  72 */       return true;
/*     */     }
/*  74 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/*  75 */       return false;
/*     */     }
/*     */     
/*  78 */     SimpleMessage localSimpleMessage = (SimpleMessage)paramObject;
/*     */     
/*  80 */     return this.message != null ? this.message.equals(localSimpleMessage.message) : localSimpleMessage.message == null;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  85 */     return this.message != null ? this.message.hashCode() : 0;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  90 */     return "SimpleMessage[message=" + this.message + "]";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getThrowable()
/*     */   {
/* 100 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\SimpleMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */