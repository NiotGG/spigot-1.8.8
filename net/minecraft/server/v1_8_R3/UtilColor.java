/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UtilColor
/*    */ {
/* 10 */   private static final Pattern a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
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
/*    */   public static boolean b(String paramString)
/*    */   {
/* 28 */     return StringUtils.isEmpty(paramString);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\UtilColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */