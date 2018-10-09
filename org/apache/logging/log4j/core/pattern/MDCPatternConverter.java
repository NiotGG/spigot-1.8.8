/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.TreeSet;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name="MDCPatternConverter", category="Converter")
/*    */ @ConverterKeys({"X", "mdc", "MDC"})
/*    */ public final class MDCPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/*    */   private final String key;
/*    */   
/*    */   private MDCPatternConverter(String[] paramArrayOfString)
/*    */   {
/* 47 */     super((paramArrayOfString != null) && (paramArrayOfString.length > 0) ? "MDC{" + paramArrayOfString[0] + "}" : "MDC", "mdc");
/* 48 */     this.key = ((paramArrayOfString != null) && (paramArrayOfString.length > 0) ? paramArrayOfString[0] : null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static MDCPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 58 */     return new MDCPatternConverter(paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/* 66 */     Map localMap = paramLogEvent.getContextMap();
/*    */     
/*    */     Object localObject;
/* 69 */     if (this.key == null)
/*    */     {
/*    */ 
/* 72 */       if ((localMap == null) || (localMap.size() == 0)) {
/* 73 */         paramStringBuilder.append("{}");
/* 74 */         return;
/*    */       }
/* 76 */       localObject = new StringBuilder("{");
/* 77 */       TreeSet localTreeSet = new TreeSet(localMap.keySet());
/* 78 */       for (String str : localTreeSet) {
/* 79 */         if (((StringBuilder)localObject).length() > 1) {
/* 80 */           ((StringBuilder)localObject).append(", ");
/*    */         }
/* 82 */         ((StringBuilder)localObject).append(str).append("=").append((String)localMap.get(str));
/*    */       }
/*    */       
/* 85 */       ((StringBuilder)localObject).append("}");
/* 86 */       paramStringBuilder.append((CharSequence)localObject);
/* 87 */     } else if (localMap != null)
/*    */     {
/* 89 */       localObject = localMap.get(this.key);
/*    */       
/* 91 */       if (localObject != null) {
/* 92 */         paramStringBuilder.append(localObject);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\MDCPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */