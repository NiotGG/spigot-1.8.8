/*     */ package org.apache.logging.log4j.core.appender.db.jpa.converter;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import javax.persistence.AttributeConverter;
/*     */ import javax.persistence.Converter;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
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
/*     */ @Converter(autoApply=false)
/*     */ public class ThrowableAttributeConverter
/*     */   implements AttributeConverter<Throwable, String>
/*     */ {
/*     */   private static final int CAUSED_BY_STRING_LENGTH = 10;
/*     */   private static final Field THROWABLE_CAUSE;
/*     */   private static final Field THROWABLE_MESSAGE;
/*     */   
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  45 */       THROWABLE_CAUSE = Throwable.class.getDeclaredField("cause");
/*  46 */       THROWABLE_CAUSE.setAccessible(true);
/*  47 */       THROWABLE_MESSAGE = Throwable.class.getDeclaredField("detailMessage");
/*  48 */       THROWABLE_MESSAGE.setAccessible(true);
/*     */     } catch (NoSuchFieldException localNoSuchFieldException) {
/*  50 */       throw new IllegalStateException("Something is wrong with java.lang.Throwable.", localNoSuchFieldException);
/*     */     }
/*     */   }
/*     */   
/*     */   public String convertToDatabaseColumn(Throwable paramThrowable)
/*     */   {
/*  56 */     if (paramThrowable == null) {
/*  57 */       return null;
/*     */     }
/*     */     
/*  60 */     StringBuilder localStringBuilder = new StringBuilder();
/*  61 */     convertThrowable(localStringBuilder, paramThrowable);
/*  62 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private void convertThrowable(StringBuilder paramStringBuilder, Throwable paramThrowable) {
/*  66 */     paramStringBuilder.append(paramThrowable.toString()).append('\n');
/*  67 */     for (StackTraceElement localStackTraceElement : paramThrowable.getStackTrace()) {
/*  68 */       paramStringBuilder.append("\tat ").append(localStackTraceElement).append('\n');
/*     */     }
/*  70 */     if (paramThrowable.getCause() != null) {
/*  71 */       paramStringBuilder.append("Caused by ");
/*  72 */       convertThrowable(paramStringBuilder, paramThrowable.getCause());
/*     */     }
/*     */   }
/*     */   
/*     */   public Throwable convertToEntityAttribute(String paramString)
/*     */   {
/*  78 */     if (Strings.isEmpty(paramString)) {
/*  79 */       return null;
/*     */     }
/*     */     
/*  82 */     List localList = Arrays.asList(paramString.split("(\n|\r\n)"));
/*  83 */     return convertString(localList.listIterator(), false);
/*     */   }
/*     */   
/*     */   private Throwable convertString(ListIterator<String> paramListIterator, boolean paramBoolean) {
/*  87 */     String str1 = (String)paramListIterator.next();
/*  88 */     if (paramBoolean) {
/*  89 */       str1 = str1.substring(10);
/*     */     }
/*  91 */     int i = str1.indexOf(":");
/*     */     
/*  93 */     String str2 = null;
/*  94 */     String str3; if (i > 1) {
/*  95 */       str3 = str1.substring(0, i);
/*  96 */       if (str1.length() > i + 1) {
/*  97 */         str2 = str1.substring(i + 1).trim();
/*     */       }
/*     */     } else {
/* 100 */       str3 = str1;
/*     */     }
/*     */     
/* 103 */     ArrayList localArrayList = new ArrayList();
/* 104 */     Throwable localThrowable = null;
/* 105 */     while (paramListIterator.hasNext()) {
/* 106 */       String str4 = (String)paramListIterator.next();
/*     */       
/* 108 */       if (str4.startsWith("Caused by ")) {
/* 109 */         paramListIterator.previous();
/* 110 */         localThrowable = convertString(paramListIterator, true);
/* 111 */         break;
/*     */       }
/*     */       
/* 114 */       localArrayList.add(StackTraceElementAttributeConverter.convertString(str4.trim().substring(3).trim()));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 119 */     return getThrowable(str3, str2, localThrowable, (StackTraceElement[])localArrayList.toArray(new StackTraceElement[localArrayList.size()]));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private Throwable getThrowable(String paramString1, String paramString2, Throwable paramThrowable, StackTraceElement[] paramArrayOfStackTraceElement)
/*     */   {
/*     */     try
/*     */     {
/* 128 */       Class localClass = Class.forName(paramString1);
/*     */       
/* 130 */       if (!Throwable.class.isAssignableFrom(localClass)) {
/* 131 */         return null;
/*     */       }
/*     */       
/*     */       Throwable localThrowable;
/* 135 */       if ((paramString2 != null) && (paramThrowable != null)) {
/* 136 */         localThrowable = getThrowable(localClass, paramString2, paramThrowable);
/* 137 */         if (localThrowable == null) {
/* 138 */           localThrowable = getThrowable(localClass, paramThrowable);
/* 139 */           if (localThrowable == null) {
/* 140 */             localThrowable = getThrowable(localClass, paramString2);
/* 141 */             if (localThrowable == null) {
/* 142 */               localThrowable = getThrowable(localClass);
/* 143 */               if (localThrowable != null) {
/* 144 */                 THROWABLE_MESSAGE.set(localThrowable, paramString2);
/* 145 */                 THROWABLE_CAUSE.set(localThrowable, paramThrowable);
/*     */               }
/*     */             } else {
/* 148 */               THROWABLE_CAUSE.set(localThrowable, paramThrowable);
/*     */             }
/*     */           } else {
/* 151 */             THROWABLE_MESSAGE.set(localThrowable, paramString2);
/*     */           }
/*     */         }
/* 154 */       } else if (paramThrowable != null) {
/* 155 */         localThrowable = getThrowable(localClass, paramThrowable);
/* 156 */         if (localThrowable == null) {
/* 157 */           localThrowable = getThrowable(localClass);
/* 158 */           if (localThrowable != null) {
/* 159 */             THROWABLE_CAUSE.set(localThrowable, paramThrowable);
/*     */           }
/*     */         }
/* 162 */       } else if (paramString2 != null) {
/* 163 */         localThrowable = getThrowable(localClass, paramString2);
/* 164 */         if (localThrowable == null) {
/* 165 */           localThrowable = getThrowable(localClass);
/* 166 */           if (localThrowable != null) {
/* 167 */             THROWABLE_MESSAGE.set(localThrowable, paramThrowable);
/*     */           }
/*     */         }
/*     */       } else {
/* 171 */         localThrowable = getThrowable(localClass);
/*     */       }
/*     */       
/* 174 */       if (localThrowable == null) {
/* 175 */         return null;
/*     */       }
/* 177 */       localThrowable.setStackTrace(paramArrayOfStackTraceElement);
/* 178 */       return localThrowable;
/*     */     } catch (Exception localException) {}
/* 180 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   private Throwable getThrowable(Class<Throwable> paramClass, String paramString, Throwable paramThrowable)
/*     */   {
/*     */     try
/*     */     {
/* 188 */       Constructor[] arrayOfConstructor1 = (Constructor[])paramClass.getConstructors();
/* 189 */       for (Constructor localConstructor : arrayOfConstructor1) {
/* 190 */         Class[] arrayOfClass = localConstructor.getParameterTypes();
/* 191 */         if (arrayOfClass.length == 2) {
/* 192 */           if ((String.class == arrayOfClass[0]) && (Throwable.class.isAssignableFrom(arrayOfClass[1])))
/* 193 */             return (Throwable)localConstructor.newInstance(new Object[] { paramString, paramThrowable });
/* 194 */           if ((String.class == arrayOfClass[1]) && (Throwable.class.isAssignableFrom(arrayOfClass[0])))
/*     */           {
/* 196 */             return (Throwable)localConstructor.newInstance(new Object[] { paramThrowable, paramString });
/*     */           }
/*     */         }
/*     */       }
/* 200 */       return null;
/*     */     } catch (Exception localException) {}
/* 202 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   private Throwable getThrowable(Class<Throwable> paramClass, Throwable paramThrowable)
/*     */   {
/*     */     try
/*     */     {
/* 210 */       Constructor[] arrayOfConstructor1 = (Constructor[])paramClass.getConstructors();
/* 211 */       for (Constructor localConstructor : arrayOfConstructor1) {
/* 212 */         Class[] arrayOfClass = localConstructor.getParameterTypes();
/* 213 */         if ((arrayOfClass.length == 1) && (Throwable.class.isAssignableFrom(arrayOfClass[0]))) {
/* 214 */           return (Throwable)localConstructor.newInstance(new Object[] { paramThrowable });
/*     */         }
/*     */       }
/* 217 */       return null;
/*     */     } catch (Exception localException) {}
/* 219 */     return null;
/*     */   }
/*     */   
/*     */   private Throwable getThrowable(Class<Throwable> paramClass, String paramString)
/*     */   {
/*     */     try {
/* 225 */       return (Throwable)paramClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { paramString });
/*     */     } catch (Exception localException) {}
/* 227 */     return null;
/*     */   }
/*     */   
/*     */   private Throwable getThrowable(Class<Throwable> paramClass)
/*     */   {
/*     */     try {
/* 233 */       return (Throwable)paramClass.newInstance();
/*     */     } catch (Exception localException) {}
/* 235 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\converter\ThrowableAttributeConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */