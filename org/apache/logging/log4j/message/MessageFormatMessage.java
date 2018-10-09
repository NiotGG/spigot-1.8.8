/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.IllegalFormatException;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ public class MessageFormatMessage
/*     */   implements Message
/*     */ {
/*  34 */   private static final Logger LOGGER = ;
/*     */   
/*     */   private static final long serialVersionUID = -665975803997290697L;
/*     */   
/*     */   private static final int HASHVAL = 31;
/*     */   private String messagePattern;
/*     */   private transient Object[] argArray;
/*     */   private String[] stringArgs;
/*     */   private transient String formattedMessage;
/*     */   private transient Throwable throwable;
/*     */   
/*     */   public MessageFormatMessage(String paramString, Object... paramVarArgs)
/*     */   {
/*  47 */     this.messagePattern = paramString;
/*  48 */     this.argArray = paramVarArgs;
/*  49 */     if ((paramVarArgs != null) && (paramVarArgs.length > 0) && ((paramVarArgs[(paramVarArgs.length - 1)] instanceof Throwable))) {
/*  50 */       this.throwable = ((Throwable)paramVarArgs[(paramVarArgs.length - 1)]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/*  60 */     if (this.formattedMessage == null) {
/*  61 */       this.formattedMessage = formatMessage(this.messagePattern, this.argArray);
/*     */     }
/*  63 */     return this.formattedMessage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormat()
/*     */   {
/*  72 */     return this.messagePattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/*  81 */     if (this.argArray != null) {
/*  82 */       return this.argArray;
/*     */     }
/*  84 */     return this.stringArgs;
/*     */   }
/*     */   
/*     */   protected String formatMessage(String paramString, Object... paramVarArgs) {
/*     */     try {
/*  89 */       return MessageFormat.format(paramString, paramVarArgs);
/*     */     } catch (IllegalFormatException localIllegalFormatException) {
/*  91 */       LOGGER.error("Unable to format msg: " + paramString, localIllegalFormatException); }
/*  92 */     return paramString;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  98 */     if (this == paramObject) {
/*  99 */       return true;
/*     */     }
/* 101 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 102 */       return false;
/*     */     }
/*     */     
/* 105 */     MessageFormatMessage localMessageFormatMessage = (MessageFormatMessage)paramObject;
/*     */     
/* 107 */     if (this.messagePattern != null ? !this.messagePattern.equals(localMessageFormatMessage.messagePattern) : localMessageFormatMessage.messagePattern != null) {
/* 108 */       return false;
/*     */     }
/* 110 */     if (!Arrays.equals(this.stringArgs, localMessageFormatMessage.stringArgs)) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 119 */     int i = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
/* 120 */     i = 31 * i + (this.stringArgs != null ? Arrays.hashCode(this.stringArgs) : 0);
/* 121 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 127 */     return "StringFormatMessage[messagePattern=" + this.messagePattern + ", args=" + Arrays.toString(this.argArray) + "]";
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException
/*     */   {
/* 132 */     paramObjectOutputStream.defaultWriteObject();
/* 133 */     getFormattedMessage();
/* 134 */     paramObjectOutputStream.writeUTF(this.formattedMessage);
/* 135 */     paramObjectOutputStream.writeUTF(this.messagePattern);
/* 136 */     paramObjectOutputStream.writeInt(this.argArray.length);
/* 137 */     this.stringArgs = new String[this.argArray.length];
/* 138 */     int i = 0;
/* 139 */     for (Object localObject : this.argArray) {
/* 140 */       this.stringArgs[i] = localObject.toString();
/* 141 */       i++;
/*     */     }
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 146 */     paramObjectInputStream.defaultReadObject();
/* 147 */     this.formattedMessage = paramObjectInputStream.readUTF();
/* 148 */     this.messagePattern = paramObjectInputStream.readUTF();
/* 149 */     int i = paramObjectInputStream.readInt();
/* 150 */     this.stringArgs = new String[i];
/* 151 */     for (int j = 0; j < i; j++) {
/* 152 */       this.stringArgs[j] = paramObjectInputStream.readUTF();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Throwable getThrowable()
/*     */   {
/* 163 */     return this.throwable;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\MessageFormatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */