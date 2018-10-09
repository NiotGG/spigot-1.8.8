/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ public abstract class AbstractPatternConverter
/*    */   implements PatternConverter
/*    */ {
/* 35 */   protected static final Logger LOGGER = ;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private final String name;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private final String style;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected AbstractPatternConverter(String paramString1, String paramString2)
/*    */   {
/* 54 */     this.name = paramString1;
/* 55 */     this.style = paramString2;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public final String getName()
/*    */   {
/* 67 */     return this.name;
/*    */   }
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
/*    */   public String getStyleClass(Object paramObject)
/*    */   {
/* 81 */     return this.style;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\AbstractPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */