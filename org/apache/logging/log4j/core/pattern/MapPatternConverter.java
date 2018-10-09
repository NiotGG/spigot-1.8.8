/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.TreeSet;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.message.MapMessage;
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
/*    */ @Plugin(name="MapPatternConverter", category="Converter")
/*    */ @ConverterKeys({"K", "map", "MAP"})
/*    */ public final class MapPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/*    */   private final String key;
/*    */   
/*    */   private MapPatternConverter(String[] paramArrayOfString)
/*    */   {
/* 47 */     super((paramArrayOfString != null) && (paramArrayOfString.length > 0) ? "MAP{" + paramArrayOfString[0] + "}" : "MAP", "map");
/* 48 */     this.key = ((paramArrayOfString != null) && (paramArrayOfString.length > 0) ? paramArrayOfString[0] : null);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static MapPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 58 */     return new MapPatternConverter(paramArrayOfString);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void format(LogEvent paramLogEvent, StringBuilder paramStringBuilder)
/*    */   {
/*    */     MapMessage localMapMessage;
/*    */     
/* 67 */     if ((paramLogEvent.getMessage() instanceof MapMessage)) {
/* 68 */       localMapMessage = (MapMessage)paramLogEvent.getMessage();
/*    */     } else {
/* 70 */       return;
/*    */     }
/* 72 */     Map localMap = localMapMessage.getData();
/*    */     
/*    */     Object localObject;
/* 75 */     if (this.key == null) {
/* 76 */       if (localMap.size() == 0) {
/* 77 */         paramStringBuilder.append("{}");
/* 78 */         return;
/*    */       }
/* 80 */       localObject = new StringBuilder("{");
/* 81 */       TreeSet localTreeSet = new TreeSet(localMap.keySet());
/* 82 */       for (String str : localTreeSet) {
/* 83 */         if (((StringBuilder)localObject).length() > 1) {
/* 84 */           ((StringBuilder)localObject).append(", ");
/*    */         }
/* 86 */         ((StringBuilder)localObject).append(str).append("=").append((String)localMap.get(str));
/*    */       }
/*    */       
/* 89 */       ((StringBuilder)localObject).append("}");
/* 90 */       paramStringBuilder.append((CharSequence)localObject);
/*    */     }
/*    */     else {
/* 93 */       localObject = (String)localMap.get(this.key);
/*    */       
/* 95 */       if (localObject != null) {
/* 96 */         paramStringBuilder.append((String)localObject);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\MapPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */