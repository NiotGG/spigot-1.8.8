/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.net.Advertiser;
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
/*    */ @Plugin(name="default", category="Core", elementType="advertiser", printObject=false)
/*    */ public class DefaultAdvertiser
/*    */   implements Advertiser
/*    */ {
/*    */   public Object advertise(Map<String, String> paramMap)
/*    */   {
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public void unadvertise(Object paramObject) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\DefaultAdvertiser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */