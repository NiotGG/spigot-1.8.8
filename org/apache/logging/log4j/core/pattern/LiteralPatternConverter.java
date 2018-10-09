/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.Configuration;
/*    */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LiteralPatternConverter
/*    */   extends LogEventPatternConverter
/*    */   implements ArrayPatternConverter
/*    */ {
/*    */   private final String literal;
/*    */   private final Configuration config;
/*    */   private final boolean substitute;
/*    */   
/*    */   public LiteralPatternConverter(Configuration paramConfiguration, String paramString)
/*    */   {
/* 43 */     super("Literal", "literal");
/* 44 */     this.literal = paramString;
/* 45 */     this.config = paramConfiguration;
/* 46 */     this.substitute = ((paramConfiguration != null) && (paramString.contains("${")));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 54 */     paramStringBuilder.append(this.substitute ? this.config.getStrSubstitutor().replace(paramLogEvent, this.literal) : this.literal);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void format(Object paramObject, StringBuilder paramStringBuilder)
/*    */   {
/* 61 */     paramStringBuilder.append(this.substitute ? this.config.getStrSubstitutor().replace(this.literal) : this.literal);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(StringBuilder paramStringBuilder, Object... paramVarArgs)
/*    */   {
/* 69 */     paramStringBuilder.append(this.substitute ? this.config.getStrSubstitutor().replace(this.literal) : this.literal);
/*    */   }
/*    */   
/*    */   public String getLiteral() {
/* 73 */     return this.literal;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\LiteralPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */