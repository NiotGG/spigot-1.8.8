/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalizedMessage
/*     */   implements Message, LoggerNameAwareMessage
/*     */ {
/*     */   private static final long serialVersionUID = 3893703791567290742L;
/*     */   private String bundleId;
/*     */   private transient ResourceBundle bundle;
/*     */   private Locale locale;
/*  46 */   private transient StatusLogger logger = StatusLogger.getLogger();
/*     */   
/*     */   private String loggerName;
/*     */   
/*     */   private String messagePattern;
/*     */   
/*     */   private String[] stringArgs;
/*     */   
/*     */   private transient Object[] argArray;
/*     */   
/*     */   private String formattedMessage;
/*     */   
/*     */   private transient Throwable throwable;
/*     */   
/*     */   public LocalizedMessage(String paramString, Object[] paramArrayOfObject)
/*     */   {
/*  62 */     this((ResourceBundle)null, (Locale)null, paramString, paramArrayOfObject);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/*  66 */     this(paramString1, (Locale)null, paramString2, paramArrayOfObject);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle paramResourceBundle, String paramString, Object[] paramArrayOfObject) {
/*  70 */     this(paramResourceBundle, (Locale)null, paramString, paramArrayOfObject);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String paramString1, Locale paramLocale, String paramString2, Object[] paramArrayOfObject) {
/*  74 */     this.messagePattern = paramString2;
/*  75 */     this.argArray = paramArrayOfObject;
/*  76 */     this.throwable = null;
/*  77 */     setup(paramString1, null, paramLocale);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle paramResourceBundle, Locale paramLocale, String paramString, Object[] paramArrayOfObject)
/*     */   {
/*  82 */     this.messagePattern = paramString;
/*  83 */     this.argArray = paramArrayOfObject;
/*  84 */     this.throwable = null;
/*  85 */     setup(null, paramResourceBundle, paramLocale);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(Locale paramLocale, String paramString, Object[] paramArrayOfObject) {
/*  89 */     this((ResourceBundle)null, paramLocale, paramString, paramArrayOfObject);
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String paramString, Object paramObject) {
/*  93 */     this((ResourceBundle)null, (Locale)null, paramString, new Object[] { paramObject });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String paramString1, String paramString2, Object paramObject) {
/*  97 */     this(paramString1, (Locale)null, paramString2, new Object[] { paramObject });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle paramResourceBundle, String paramString, Object paramObject) {
/* 101 */     this(paramResourceBundle, (Locale)null, paramString, new Object[] { paramObject });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String paramString1, Locale paramLocale, String paramString2, Object paramObject) {
/* 105 */     this(paramString1, paramLocale, paramString2, new Object[] { paramObject });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle paramResourceBundle, Locale paramLocale, String paramString, Object paramObject) {
/* 109 */     this(paramResourceBundle, paramLocale, paramString, new Object[] { paramObject });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(Locale paramLocale, String paramString, Object paramObject) {
/* 113 */     this((ResourceBundle)null, paramLocale, paramString, new Object[] { paramObject });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String paramString, Object paramObject1, Object paramObject2) {
/* 117 */     this((ResourceBundle)null, (Locale)null, paramString, new Object[] { paramObject1, paramObject2 });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String paramString1, String paramString2, Object paramObject1, Object paramObject2) {
/* 121 */     this(paramString1, (Locale)null, paramString2, new Object[] { paramObject1, paramObject2 });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle paramResourceBundle, String paramString, Object paramObject1, Object paramObject2) {
/* 125 */     this(paramResourceBundle, (Locale)null, paramString, new Object[] { paramObject1, paramObject2 });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(String paramString1, Locale paramLocale, String paramString2, Object paramObject1, Object paramObject2)
/*     */   {
/* 130 */     this(paramString1, paramLocale, paramString2, new Object[] { paramObject1, paramObject2 });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(ResourceBundle paramResourceBundle, Locale paramLocale, String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 135 */     this(paramResourceBundle, paramLocale, paramString, new Object[] { paramObject1, paramObject2 });
/*     */   }
/*     */   
/*     */   public LocalizedMessage(Locale paramLocale, String paramString, Object paramObject1, Object paramObject2) {
/* 139 */     this((ResourceBundle)null, paramLocale, paramString, new Object[] { paramObject1, paramObject2 });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLoggerName(String paramString)
/*     */   {
/* 148 */     this.loggerName = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLoggerName()
/*     */   {
/* 157 */     return this.loggerName;
/*     */   }
/*     */   
/*     */   private void setup(String paramString, ResourceBundle paramResourceBundle, Locale paramLocale) {
/* 161 */     this.bundleId = paramString;
/* 162 */     this.bundle = paramResourceBundle;
/* 163 */     this.locale = paramLocale;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getFormattedMessage()
/*     */   {
/* 172 */     if (this.formattedMessage != null) {
/* 173 */       return this.formattedMessage;
/*     */     }
/* 175 */     ResourceBundle localResourceBundle = this.bundle;
/* 176 */     if (localResourceBundle == null) {
/* 177 */       if (this.bundleId != null) {
/* 178 */         localResourceBundle = getBundle(this.bundleId, this.locale, false);
/*     */       } else {
/* 180 */         localResourceBundle = getBundle(this.loggerName, this.locale, true);
/*     */       }
/*     */     }
/* 183 */     String str1 = getFormat();
/* 184 */     String str2 = (localResourceBundle == null) || (!localResourceBundle.containsKey(str1)) ? str1 : localResourceBundle.getString(str1);
/*     */     
/* 186 */     Object[] arrayOfObject = this.argArray == null ? this.stringArgs : this.argArray;
/* 187 */     FormattedMessage localFormattedMessage = new FormattedMessage(str2, arrayOfObject);
/* 188 */     this.formattedMessage = localFormattedMessage.getFormattedMessage();
/* 189 */     this.throwable = localFormattedMessage.getThrowable();
/* 190 */     return this.formattedMessage;
/*     */   }
/*     */   
/*     */   public String getFormat()
/*     */   {
/* 195 */     return this.messagePattern;
/*     */   }
/*     */   
/*     */   public Object[] getParameters()
/*     */   {
/* 200 */     if (this.argArray != null) {
/* 201 */       return this.argArray;
/*     */     }
/* 203 */     return this.stringArgs;
/*     */   }
/*     */   
/*     */   public Throwable getThrowable()
/*     */   {
/* 208 */     return this.throwable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ResourceBundle getBundle(String paramString, Locale paramLocale, boolean paramBoolean)
/*     */   {
/* 220 */     ResourceBundle localResourceBundle = null;
/*     */     
/* 222 */     if (paramString == null) {
/* 223 */       return null;
/*     */     }
/*     */     try {
/* 226 */       if (paramLocale != null) {
/* 227 */         localResourceBundle = ResourceBundle.getBundle(paramString, paramLocale);
/*     */       } else {
/* 229 */         localResourceBundle = ResourceBundle.getBundle(paramString);
/*     */       }
/*     */     } catch (MissingResourceException localMissingResourceException1) {
/* 232 */       if (!paramBoolean) {
/* 233 */         this.logger.debug("Unable to locate ResourceBundle " + paramString);
/* 234 */         return null;
/*     */       }
/*     */     }
/*     */     
/* 238 */     String str = paramString;
/*     */     int i;
/* 240 */     while ((localResourceBundle == null) && ((i = str.lastIndexOf('.')) > 0)) {
/* 241 */       str = str.substring(0, i);
/*     */       try {
/* 243 */         if (paramLocale != null) {
/* 244 */           localResourceBundle = ResourceBundle.getBundle(str, paramLocale);
/*     */         } else {
/* 246 */           localResourceBundle = ResourceBundle.getBundle(str);
/*     */         }
/*     */       } catch (MissingResourceException localMissingResourceException2) {
/* 249 */         this.logger.debug("Unable to locate ResourceBundle " + str);
/*     */       }
/*     */     }
/* 252 */     return localResourceBundle;
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 256 */     paramObjectOutputStream.defaultWriteObject();
/* 257 */     getFormattedMessage();
/* 258 */     paramObjectOutputStream.writeUTF(this.formattedMessage);
/* 259 */     paramObjectOutputStream.writeUTF(this.messagePattern);
/* 260 */     paramObjectOutputStream.writeUTF(this.bundleId);
/* 261 */     paramObjectOutputStream.writeInt(this.argArray.length);
/* 262 */     this.stringArgs = new String[this.argArray.length];
/* 263 */     int i = 0;
/* 264 */     for (Object localObject : this.argArray) {
/* 265 */       this.stringArgs[i] = localObject.toString();
/* 266 */       i++;
/*     */     }
/*     */   }
/*     */   
/*     */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 271 */     paramObjectInputStream.defaultReadObject();
/* 272 */     this.formattedMessage = paramObjectInputStream.readUTF();
/* 273 */     this.messagePattern = paramObjectInputStream.readUTF();
/* 274 */     this.bundleId = paramObjectInputStream.readUTF();
/* 275 */     int i = paramObjectInputStream.readInt();
/* 276 */     this.stringArgs = new String[i];
/* 277 */     for (int j = 0; j < i; j++) {
/* 278 */       this.stringArgs[j] = paramObjectInputStream.readUTF();
/*     */     }
/* 280 */     this.logger = StatusLogger.getLogger();
/* 281 */     this.bundle = null;
/* 282 */     this.argArray = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\LocalizedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */