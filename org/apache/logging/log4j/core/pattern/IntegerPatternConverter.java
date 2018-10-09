/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.util.Date;
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
/*    */ @Plugin(name="IntegerPatternConverter", category="FileConverter")
/*    */ @ConverterKeys({"i", "index"})
/*    */ public final class IntegerPatternConverter
/*    */   extends AbstractPatternConverter
/*    */   implements ArrayPatternConverter
/*    */ {
/* 34 */   private static final IntegerPatternConverter INSTANCE = new IntegerPatternConverter();
/*    */   
/*    */ 
/*    */ 
/*    */   private IntegerPatternConverter()
/*    */   {
/* 40 */     super("Integer", "integer");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static IntegerPatternConverter newInstance(String[] paramArrayOfString)
/*    */   {
/* 51 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void format(StringBuilder paramStringBuilder, Object... paramVarArgs)
/*    */   {
/* 56 */     for (Object localObject : paramVarArgs) {
/* 57 */       if ((localObject instanceof Integer)) {
/* 58 */         format(localObject, paramStringBuilder);
/* 59 */         break;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void format(Object paramObject, StringBuilder paramStringBuilder)
/*    */   {
/* 69 */     if ((paramObject instanceof Integer)) {
/* 70 */       paramStringBuilder.append(paramObject.toString());
/*    */     }
/*    */     
/* 73 */     if ((paramObject instanceof Date)) {
/* 74 */       paramStringBuilder.append(Long.toString(((Date)paramObject).getTime()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\IntegerPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */