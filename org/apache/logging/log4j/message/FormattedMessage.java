/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.text.Format;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class FormattedMessage
/*     */   implements Message
/*     */ {
/*     */   private static final long serialVersionUID = -665975803997290697L;
/*     */   private static final int HASHVAL = 31;
/*     */   private static final String FORMAT_SPECIFIER = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
/*  36 */   private static final Pattern MSG_PATTERN = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");
/*     */   private String messagePattern;
/*     */   private transient Object[] argArray;
/*     */   private String[] stringArgs;
/*     */   private transient String formattedMessage;
/*     */   private final Throwable throwable;
/*     */   private Message message;
/*     */   
/*     */   public FormattedMessage(String paramString, Object[] paramArrayOfObject, Throwable paramThrowable)
/*     */   {
/*  46 */     this.messagePattern = paramString;
/*  47 */     this.argArray = paramArrayOfObject;
/*  48 */     this.throwable = paramThrowable;
/*     */   }
/*     */   
/*     */   public FormattedMessage(String paramString, Object[] paramArrayOfObject) {
/*  52 */     this.messagePattern = paramString;
/*  53 */     this.argArray = paramArrayOfObject;
/*  54 */     this.throwable = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FormattedMessage(String paramString, Object paramObject)
/*     */   {
/*  63 */     this.messagePattern = paramString;
/*  64 */     this.argArray = new Object[] { paramObject };
/*  65 */     this.throwable = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FormattedMessage(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/*  75 */     this(paramString, new Object[] { paramObject1, paramObject2 });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/*  85 */     if (this.formattedMessage == null) {
/*  86 */       if (this.message == null) {
/*  87 */         this.message = getMessage(this.messagePattern, this.argArray, this.throwable);
/*     */       }
/*  89 */       this.formattedMessage = this.message.getFormattedMessage();
/*     */     }
/*  91 */     return this.formattedMessage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormat()
/*     */   {
/* 100 */     return this.messagePattern;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] getParameters()
/*     */   {
/* 109 */     if (this.argArray != null) {
/* 110 */       return this.argArray;
/*     */     }
/* 112 */     return this.stringArgs;
/*     */   }
/*     */   
/*     */   protected Message getMessage(String paramString, Object[] paramArrayOfObject, Throwable paramThrowable) {
/*     */     try {
/* 117 */       MessageFormat localMessageFormat = new MessageFormat(paramString);
/* 118 */       Format[] arrayOfFormat = localMessageFormat.getFormats();
/* 119 */       if ((arrayOfFormat != null) && (arrayOfFormat.length > 0)) {
/* 120 */         return new MessageFormatMessage(paramString, paramArrayOfObject);
/*     */       }
/*     */     }
/*     */     catch (Exception localException1) {}
/*     */     try
/*     */     {
/* 126 */       if (MSG_PATTERN.matcher(paramString).find()) {
/* 127 */         return new StringFormattedMessage(paramString, paramArrayOfObject);
/*     */       }
/*     */     }
/*     */     catch (Exception localException2) {}
/*     */     
/* 132 */     return new ParameterizedMessage(paramString, paramArrayOfObject, paramThrowable);
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 137 */     if (this == paramObject) {
/* 138 */       return true;
/*     */     }
/* 140 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 141 */       return false;
/*     */     }
/*     */     
/* 144 */     FormattedMessage localFormattedMessage = (FormattedMessage)paramObject;
/*     */     
/* 146 */     if (this.messagePattern != null ? !this.messagePattern.equals(localFormattedMessage.messagePattern) : localFormattedMessage.messagePattern != null) {
/* 147 */       return false;
/*     */     }
/* 149 */     if (!Arrays.equals(this.stringArgs, localFormattedMessage.stringArgs)) {
/* 150 */       return false;
/*     */     }
/*     */     
/* 153 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 158 */     int i = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
/* 159 */     i = 31 * i + (this.stringArgs != null ? Arrays.hashCode(this.stringArgs) : 0);
/* 160 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public String toString()
/*     */   {
/* 166 */     return "FormattedMessage[messagePattern=" + this.messagePattern + ", args=" + Arrays.toString(this.argArray) + "]";
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException
/*     */   {
/* 171 */     paramObjectOutputStream.defaultWriteObject();
/* 172 */     getFormattedMessage();
/* 173 */     paramObjectOutputStream.writeUTF(this.formattedMessage);
/* 174 */     paramObjectOutputStream.writeUTF(this.messagePattern);
/* 175 */     paramObjectOutputStream.writeInt(this.argArray.length);
/* 176 */     this.stringArgs = new String[this.argArray.length];
/* 177 */     int i = 0;
/* 178 */     for (Object localObject : this.argArray) {
/* 179 */       this.stringArgs[i] = localObject.toString();
/* 180 */       i++;
/*     */     }
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 185 */     paramObjectInputStream.defaultReadObject();
/* 186 */     this.formattedMessage = paramObjectInputStream.readUTF();
/* 187 */     this.messagePattern = paramObjectInputStream.readUTF();
/* 188 */     int i = paramObjectInputStream.readInt();
/* 189 */     this.stringArgs = new String[i];
/* 190 */     for (int j = 0; j < i; j++) {
/* 191 */       this.stringArgs[j] = paramObjectInputStream.readUTF();
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
/* 202 */     if (this.throwable != null) {
/* 203 */       return this.throwable;
/*     */     }
/* 205 */     if (this.message == null) {
/* 206 */       this.message = getMessage(this.messagePattern, this.argArray, this.throwable);
/*     */     }
/* 208 */     return this.message.getThrowable();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\FormattedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */