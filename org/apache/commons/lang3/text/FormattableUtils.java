/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.util.Formattable;
/*     */ import java.util.Formatter;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FormattableUtils
/*     */ {
/*     */   private static final String SIMPLEST_FORMAT = "%s";
/*     */   
/*     */   public static String toString(Formattable paramFormattable)
/*     */   {
/*  66 */     return String.format("%s", new Object[] { paramFormattable });
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
/*     */   public static Formatter append(CharSequence paramCharSequence, Formatter paramFormatter, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  83 */     return append(paramCharSequence, paramFormatter, paramInt1, paramInt2, paramInt3, ' ', null);
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
/*     */   public static Formatter append(CharSequence paramCharSequence, Formatter paramFormatter, int paramInt1, int paramInt2, int paramInt3, char paramChar)
/*     */   {
/* 100 */     return append(paramCharSequence, paramFormatter, paramInt1, paramInt2, paramInt3, paramChar, null);
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
/*     */   public static Formatter append(CharSequence paramCharSequence1, Formatter paramFormatter, int paramInt1, int paramInt2, int paramInt3, CharSequence paramCharSequence2)
/*     */   {
/* 118 */     return append(paramCharSequence1, paramFormatter, paramInt1, paramInt2, paramInt3, ' ', paramCharSequence2);
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
/*     */   public static Formatter append(CharSequence paramCharSequence1, Formatter paramFormatter, int paramInt1, int paramInt2, int paramInt3, char paramChar, CharSequence paramCharSequence2)
/*     */   {
/* 136 */     Validate.isTrue((paramCharSequence2 == null) || (paramInt3 < 0) || (paramCharSequence2.length() <= paramInt3), "Specified ellipsis '%1$s' exceeds precision of %2$s", new Object[] { paramCharSequence2, Integer.valueOf(paramInt3) });
/*     */     
/* 138 */     StringBuilder localStringBuilder = new StringBuilder(paramCharSequence1);
/* 139 */     if ((paramInt3 >= 0) && (paramInt3 < paramCharSequence1.length())) {
/* 140 */       CharSequence localCharSequence = (CharSequence)ObjectUtils.defaultIfNull(paramCharSequence2, "");
/* 141 */       localStringBuilder.replace(paramInt3 - localCharSequence.length(), paramCharSequence1.length(), localCharSequence.toString());
/*     */     }
/* 143 */     int i = (paramInt1 & 0x1) == 1 ? 1 : 0;
/* 144 */     for (int j = localStringBuilder.length(); j < paramInt2; j++) {
/* 145 */       localStringBuilder.insert(i != 0 ? j : 0, paramChar);
/*     */     }
/* 147 */     paramFormatter.format(localStringBuilder.toString(), new Object[0]);
/* 148 */     return paramFormatter;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\FormattableUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */