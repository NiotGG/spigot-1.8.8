/*    */ package org.apache.logging.log4j.core.pattern;
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
/*    */ public abstract class NamePatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/*    */   private final NameAbbreviator abbreviator;
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
/*    */   protected NamePatternConverter(String paramString1, String paramString2, String[] paramArrayOfString)
/*    */   {
/* 37 */     super(paramString1, paramString2);
/*    */     
/* 39 */     if ((paramArrayOfString != null) && (paramArrayOfString.length > 0)) {
/* 40 */       this.abbreviator = NameAbbreviator.getAbbreviator(paramArrayOfString[0]);
/*    */     } else {
/* 42 */       this.abbreviator = NameAbbreviator.getDefaultAbbreviator();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected final String abbreviate(String paramString)
/*    */   {
/* 53 */     return this.abbreviator.abbreviate(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\NamePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */