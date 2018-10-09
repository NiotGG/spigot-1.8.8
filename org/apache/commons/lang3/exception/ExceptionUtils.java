/*     */ package org.apache.commons.lang3.exception;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.SystemUtils;
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
/*     */ public class ExceptionUtils
/*     */ {
/*     */   static final String WRAPPED_MARKER = " [wrapped] ";
/*  54 */   private static final String[] CAUSE_METHOD_NAMES = { "getCause", "getNextException", "getTargetException", "getException", "getSourceException", "getRootCause", "getCausedByException", "getNested", "getLinkedException", "getNestedException", "getLinkedCause", "getThrowable" };
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
/*     */   @Deprecated
/*     */   public static String[] getDefaultCauseMethodNames()
/*     */   {
/*  91 */     return (String[])ArrayUtils.clone(CAUSE_METHOD_NAMES);
/*     */   }
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
/*     */   @Deprecated
/*     */   public static Throwable getCause(Throwable paramThrowable)
/*     */   {
/* 124 */     return getCause(paramThrowable, CAUSE_METHOD_NAMES);
/*     */   }
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
/*     */   @Deprecated
/*     */   public static Throwable getCause(Throwable paramThrowable, String[] paramArrayOfString)
/*     */   {
/* 142 */     if (paramThrowable == null) {
/* 143 */       return null;
/*     */     }
/*     */     
/* 146 */     if (paramArrayOfString == null) {
/* 147 */       paramArrayOfString = CAUSE_METHOD_NAMES;
/*     */     }
/*     */     
/* 150 */     for (String str : paramArrayOfString) {
/* 151 */       if (str != null) {
/* 152 */         Throwable localThrowable = getCauseUsingMethodName(paramThrowable, str);
/* 153 */         if (localThrowable != null) {
/* 154 */           return localThrowable;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 159 */     return null;
/*     */   }
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
/*     */   public static Throwable getRootCause(Throwable paramThrowable)
/*     */   {
/* 180 */     List localList = getThrowableList(paramThrowable);
/* 181 */     return localList.size() < 2 ? null : (Throwable)localList.get(localList.size() - 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Throwable getCauseUsingMethodName(Throwable paramThrowable, String paramString)
/*     */   {
/* 193 */     Method localMethod = null;
/*     */     try {
/* 195 */       localMethod = paramThrowable.getClass().getMethod(paramString, new Class[0]);
/*     */     }
/*     */     catch (NoSuchMethodException localNoSuchMethodException) {}catch (SecurityException localSecurityException) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 202 */     if ((localMethod != null) && (Throwable.class.isAssignableFrom(localMethod.getReturnType()))) {
/*     */       try {
/* 204 */         return (Throwable)localMethod.invoke(paramThrowable, new Object[0]);
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException) {}catch (IllegalArgumentException localIllegalArgumentException) {}catch (InvocationTargetException localInvocationTargetException) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 213 */     return null;
/*     */   }
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
/*     */   public static int getThrowableCount(Throwable paramThrowable)
/*     */   {
/* 234 */     return getThrowableList(paramThrowable).size();
/*     */   }
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
/*     */   public static Throwable[] getThrowables(Throwable paramThrowable)
/*     */   {
/* 257 */     List localList = getThrowableList(paramThrowable);
/* 258 */     return (Throwable[])localList.toArray(new Throwable[localList.size()]);
/*     */   }
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
/*     */   public static List<Throwable> getThrowableList(Throwable paramThrowable)
/*     */   {
/* 281 */     ArrayList localArrayList = new ArrayList();
/* 282 */     while ((paramThrowable != null) && (!localArrayList.contains(paramThrowable))) {
/* 283 */       localArrayList.add(paramThrowable);
/* 284 */       paramThrowable = getCause(paramThrowable);
/*     */     }
/* 286 */     return localArrayList;
/*     */   }
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
/*     */   public static int indexOfThrowable(Throwable paramThrowable, Class<?> paramClass)
/*     */   {
/* 305 */     return indexOf(paramThrowable, paramClass, 0, false);
/*     */   }
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
/*     */   public static int indexOfThrowable(Throwable paramThrowable, Class<?> paramClass, int paramInt)
/*     */   {
/* 328 */     return indexOf(paramThrowable, paramClass, paramInt, false);
/*     */   }
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
/*     */   public static int indexOfType(Throwable paramThrowable, Class<?> paramClass)
/*     */   {
/* 348 */     return indexOf(paramThrowable, paramClass, 0, true);
/*     */   }
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
/*     */   public static int indexOfType(Throwable paramThrowable, Class<?> paramClass, int paramInt)
/*     */   {
/* 372 */     return indexOf(paramThrowable, paramClass, paramInt, true);
/*     */   }
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
/*     */   private static int indexOf(Throwable paramThrowable, Class<?> paramClass, int paramInt, boolean paramBoolean)
/*     */   {
/* 387 */     if ((paramThrowable == null) || (paramClass == null)) {
/* 388 */       return -1;
/*     */     }
/* 390 */     if (paramInt < 0) {
/* 391 */       paramInt = 0;
/*     */     }
/* 393 */     Throwable[] arrayOfThrowable = getThrowables(paramThrowable);
/* 394 */     if (paramInt >= arrayOfThrowable.length)
/* 395 */       return -1;
/*     */     int i;
/* 397 */     if (paramBoolean) {
/* 398 */       for (i = paramInt; i < arrayOfThrowable.length; i++) {
/* 399 */         if (paramClass.isAssignableFrom(arrayOfThrowable[i].getClass())) {
/* 400 */           return i;
/*     */         }
/*     */       }
/*     */     } else {
/* 404 */       for (i = paramInt; i < arrayOfThrowable.length; i++) {
/* 405 */         if (paramClass.equals(arrayOfThrowable[i].getClass())) {
/* 406 */           return i;
/*     */         }
/*     */       }
/*     */     }
/* 410 */     return -1;
/*     */   }
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
/*     */   public static void printRootCauseStackTrace(Throwable paramThrowable)
/*     */   {
/* 433 */     printRootCauseStackTrace(paramThrowable, System.err);
/*     */   }
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
/*     */   public static void printRootCauseStackTrace(Throwable paramThrowable, PrintStream paramPrintStream)
/*     */   {
/* 456 */     if (paramThrowable == null) {
/* 457 */       return;
/*     */     }
/* 459 */     if (paramPrintStream == null) {
/* 460 */       throw new IllegalArgumentException("The PrintStream must not be null");
/*     */     }
/* 462 */     String[] arrayOfString1 = getRootCauseStackTrace(paramThrowable);
/* 463 */     for (String str : arrayOfString1) {
/* 464 */       paramPrintStream.println(str);
/*     */     }
/* 466 */     paramPrintStream.flush();
/*     */   }
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
/*     */   public static void printRootCauseStackTrace(Throwable paramThrowable, PrintWriter paramPrintWriter)
/*     */   {
/* 489 */     if (paramThrowable == null) {
/* 490 */       return;
/*     */     }
/* 492 */     if (paramPrintWriter == null) {
/* 493 */       throw new IllegalArgumentException("The PrintWriter must not be null");
/*     */     }
/* 495 */     String[] arrayOfString1 = getRootCauseStackTrace(paramThrowable);
/* 496 */     for (String str : arrayOfString1) {
/* 497 */       paramPrintWriter.println(str);
/*     */     }
/* 499 */     paramPrintWriter.flush();
/*     */   }
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
/*     */   public static String[] getRootCauseStackTrace(Throwable paramThrowable)
/*     */   {
/* 517 */     if (paramThrowable == null) {
/* 518 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*     */     }
/* 520 */     Throwable[] arrayOfThrowable = getThrowables(paramThrowable);
/* 521 */     int i = arrayOfThrowable.length;
/* 522 */     ArrayList localArrayList = new ArrayList();
/* 523 */     List localList1 = getStackFrameList(arrayOfThrowable[(i - 1)]);
/* 524 */     int j = i; for (;;) { j--; if (j < 0) break;
/* 525 */       List localList2 = localList1;
/* 526 */       if (j != 0) {
/* 527 */         localList1 = getStackFrameList(arrayOfThrowable[(j - 1)]);
/* 528 */         removeCommonFrames(localList2, localList1);
/*     */       }
/* 530 */       if (j == i - 1) {
/* 531 */         localArrayList.add(arrayOfThrowable[j].toString());
/*     */       } else {
/* 533 */         localArrayList.add(" [wrapped] " + arrayOfThrowable[j].toString());
/*     */       }
/* 535 */       for (int k = 0; k < localList2.size(); k++) {
/* 536 */         localArrayList.add(localList2.get(k));
/*     */       }
/*     */     }
/* 539 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void removeCommonFrames(List<String> paramList1, List<String> paramList2)
/*     */   {
/* 551 */     if ((paramList1 == null) || (paramList2 == null)) {
/* 552 */       throw new IllegalArgumentException("The List must not be null");
/*     */     }
/* 554 */     int i = paramList1.size() - 1;
/* 555 */     int j = paramList2.size() - 1;
/* 556 */     while ((i >= 0) && (j >= 0))
/*     */     {
/*     */ 
/* 559 */       String str1 = (String)paramList1.get(i);
/* 560 */       String str2 = (String)paramList2.get(j);
/* 561 */       if (str1.equals(str2)) {
/* 562 */         paramList1.remove(i);
/*     */       }
/* 564 */       i--;
/* 565 */       j--;
/*     */     }
/*     */   }
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
/*     */   public static String getStackTrace(Throwable paramThrowable)
/*     */   {
/* 583 */     StringWriter localStringWriter = new StringWriter();
/* 584 */     PrintWriter localPrintWriter = new PrintWriter(localStringWriter, true);
/* 585 */     paramThrowable.printStackTrace(localPrintWriter);
/* 586 */     return localStringWriter.getBuffer().toString();
/*     */   }
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
/*     */   public static String[] getStackFrames(Throwable paramThrowable)
/*     */   {
/* 603 */     if (paramThrowable == null) {
/* 604 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*     */     }
/* 606 */     return getStackFrames(getStackTrace(paramThrowable));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static String[] getStackFrames(String paramString)
/*     */   {
/* 619 */     String str = SystemUtils.LINE_SEPARATOR;
/* 620 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, str);
/* 621 */     ArrayList localArrayList = new ArrayList();
/* 622 */     while (localStringTokenizer.hasMoreTokens()) {
/* 623 */       localArrayList.add(localStringTokenizer.nextToken());
/*     */     }
/* 625 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
/*     */   }
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
/*     */   static List<String> getStackFrameList(Throwable paramThrowable)
/*     */   {
/* 641 */     String str1 = getStackTrace(paramThrowable);
/* 642 */     String str2 = SystemUtils.LINE_SEPARATOR;
/* 643 */     StringTokenizer localStringTokenizer = new StringTokenizer(str1, str2);
/* 644 */     ArrayList localArrayList = new ArrayList();
/* 645 */     int i = 0;
/* 646 */     while (localStringTokenizer.hasMoreTokens()) {
/* 647 */       String str3 = localStringTokenizer.nextToken();
/*     */       
/* 649 */       int j = str3.indexOf("at");
/* 650 */       if ((j != -1) && (str3.substring(0, j).trim().isEmpty())) {
/* 651 */         i = 1;
/* 652 */         localArrayList.add(str3);
/* 653 */       } else { if (i != 0)
/*     */           break;
/*     */       }
/*     */     }
/* 657 */     return localArrayList;
/*     */   }
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
/*     */   public static String getMessage(Throwable paramThrowable)
/*     */   {
/* 672 */     if (paramThrowable == null) {
/* 673 */       return "";
/*     */     }
/* 675 */     String str1 = ClassUtils.getShortClassName(paramThrowable, null);
/* 676 */     String str2 = paramThrowable.getMessage();
/* 677 */     return str1 + ": " + StringUtils.defaultString(str2);
/*     */   }
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
/*     */   public static String getRootCauseMessage(Throwable paramThrowable)
/*     */   {
/* 692 */     Throwable localThrowable = getRootCause(paramThrowable);
/* 693 */     localThrowable = localThrowable == null ? paramThrowable : localThrowable;
/* 694 */     return getMessage(localThrowable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\exception\ExceptionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */