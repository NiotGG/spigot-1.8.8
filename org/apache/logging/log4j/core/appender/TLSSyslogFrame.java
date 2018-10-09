/*    */ package org.apache.logging.log4j.core.appender;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TLSSyslogFrame
/*    */ {
/*    */   public static final char SPACE = ' ';
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private String message;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private int messageLengthInBytes;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public TLSSyslogFrame(String paramString)
/*    */   {
/* 29 */     setMessage(paramString);
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 33 */     return this.message;
/*    */   }
/*    */   
/*    */   public void setMessage(String paramString) {
/* 37 */     this.message = paramString;
/* 38 */     setLengthInBytes();
/*    */   }
/*    */   
/*    */   private void setLengthInBytes() {
/* 42 */     this.messageLengthInBytes = this.message.length();
/*    */   }
/*    */   
/*    */   public byte[] getBytes() {
/* 46 */     String str = toString();
/* 47 */     return str.getBytes();
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 52 */     String str = Integer.toString(this.messageLengthInBytes);
/* 53 */     return str + ' ' + this.message;
/*    */   }
/*    */   
/*    */   public boolean equals(Object paramObject)
/*    */   {
/* 58 */     return super.equals(paramObject);
/*    */   }
/*    */   
/*    */   public boolean equals(TLSSyslogFrame paramTLSSyslogFrame) {
/* 62 */     return (isLengthEquals(paramTLSSyslogFrame)) && (isMessageEquals(paramTLSSyslogFrame));
/*    */   }
/*    */   
/*    */   private boolean isLengthEquals(TLSSyslogFrame paramTLSSyslogFrame) {
/* 66 */     return this.messageLengthInBytes == paramTLSSyslogFrame.messageLengthInBytes;
/*    */   }
/*    */   
/*    */   private boolean isMessageEquals(TLSSyslogFrame paramTLSSyslogFrame) {
/* 70 */     return this.message.equals(paramTLSSyslogFrame.message);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\TLSSyslogFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */