/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
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
/*     */ public class StringFormattedMessage
/*     */   implements Message
/*     */ {
/*  33 */   private static final Logger LOGGER = ;
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
/*     */   public StringFormattedMessage(String paramString, Object... paramVarArgs)
/*     */   {
/*  46 */     this.messagePattern = paramString;
/*  47 */     this.argArray = paramVarArgs;
/*  48 */     if ((paramVarArgs != null) && (paramVarArgs.length > 0) && ((paramVarArgs[(paramVarArgs.length - 1)] instanceof Throwable))) {
/*  49 */       this.throwable = ((Throwable)paramVarArgs[(paramVarArgs.length - 1)]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/*  59 */     if (this.formattedMessage == null) {
/*  60 */       this.formattedMessage = formatMessage(this.messagePattern, this.argArray);
/*     */     }
/*  62 */     return this.formattedMessage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormat()
/*     */   {
/*  71 */     return this.messagePattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/*  80 */     if (this.argArray != null) {
/*  81 */       return this.argArray;
/*     */     }
/*  83 */     return this.stringArgs;
/*     */   }
/*     */   
/*     */   protected String formatMessage(String paramString, Object... paramVarArgs) {
/*     */     try {
/*  88 */       return String.format(paramString, paramVarArgs);
/*     */     } catch (IllegalFormatException localIllegalFormatException) {
/*  90 */       LOGGER.error("Unable to format msg: " + paramString, localIllegalFormatException); }
/*  91 */     return paramString;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  97 */     if (this == paramObject) {
/*  98 */       return true;
/*     */     }
/* 100 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 101 */       return false;
/*     */     }
/*     */     
/* 104 */     StringFormattedMessage localStringFormattedMessage = (StringFormattedMessage)paramObject;
/*     */     
/* 106 */     if (this.messagePattern != null ? !this.messagePattern.equals(localStringFormattedMessage.messagePattern) : localStringFormattedMessage.messagePattern != null) {
/* 107 */       return false;
/*     */     }
/*     */     
/* 110 */     return Arrays.equals(this.stringArgs, localStringFormattedMessage.stringArgs);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 115 */     int i = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
/* 116 */     i = 31 * i + (this.stringArgs != null ? Arrays.hashCode(this.stringArgs) : 0);
/* 117 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 123 */     return "StringFormatMessage[messagePattern=" + this.messagePattern + ", args=" + Arrays.toString(this.argArray) + "]";
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException
/*     */   {
/* 128 */     paramObjectOutputStream.defaultWriteObject();
/* 129 */     getFormattedMessage();
/* 130 */     paramObjectOutputStream.writeUTF(this.formattedMessage);
/* 131 */     paramObjectOutputStream.writeUTF(this.messagePattern);
/* 132 */     paramObjectOutputStream.writeInt(this.argArray.length);
/* 133 */     this.stringArgs = new String[this.argArray.length];
/* 134 */     int i = 0;
/* 135 */     for (Object localObject : this.argArray) {
/* 136 */       this.stringArgs[i] = localObject.toString();
/* 137 */       i++;
/*     */     }
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 142 */     paramObjectInputStream.defaultReadObject();
/* 143 */     this.formattedMessage = paramObjectInputStream.readUTF();
/* 144 */     this.messagePattern = paramObjectInputStream.readUTF();
/* 145 */     int i = paramObjectInputStream.readInt();
/* 146 */     this.stringArgs = new String[i];
/* 147 */     for (int j = 0; j < i; j++) {
/* 148 */       this.stringArgs[j] = paramObjectInputStream.readUTF();
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
/* 159 */     return this.throwable;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\StringFormattedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */