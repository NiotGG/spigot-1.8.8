/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.text.FieldPosition;
/*     */ import java.text.Format;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompositeFormat
/*     */   extends Format
/*     */ {
/*     */   private static final long serialVersionUID = -4329119827877627683L;
/*     */   private final Format parser;
/*     */   private final Format formatter;
/*     */   
/*     */   public CompositeFormat(Format paramFormat1, Format paramFormat2)
/*     */   {
/*  53 */     this.parser = paramFormat1;
/*  54 */     this.formatter = paramFormat2;
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
/*     */   public StringBuffer format(Object paramObject, StringBuffer paramStringBuffer, FieldPosition paramFieldPosition)
/*     */   {
/*  69 */     return this.formatter.format(paramObject, paramStringBuffer, paramFieldPosition);
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
/*     */   public Object parseObject(String paramString, ParsePosition paramParsePosition)
/*     */   {
/*  84 */     return this.parser.parseObject(paramString, paramParsePosition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Format getParser()
/*     */   {
/*  93 */     return this.parser;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Format getFormatter()
/*     */   {
/* 102 */     return this.formatter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String reformat(String paramString)
/*     */     throws ParseException
/*     */   {
/* 113 */     return format(parseObject(paramString));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\text\CompositeFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */