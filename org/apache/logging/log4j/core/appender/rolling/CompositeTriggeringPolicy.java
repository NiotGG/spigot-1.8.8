/*    */ package org.apache.logging.log4j.core.appender.rolling;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*    */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
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
/*    */ @Plugin(name="Policies", category="Core", printObject=true)
/*    */ public final class CompositeTriggeringPolicy
/*    */   implements TriggeringPolicy
/*    */ {
/*    */   private final TriggeringPolicy[] policies;
/*    */   
/*    */   private CompositeTriggeringPolicy(TriggeringPolicy... paramVarArgs)
/*    */   {
/* 33 */     this.policies = paramVarArgs;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void initialize(RollingFileManager paramRollingFileManager)
/*    */   {
/* 42 */     for (TriggeringPolicy localTriggeringPolicy : this.policies) {
/* 43 */       localTriggeringPolicy.initialize(paramRollingFileManager);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isTriggeringEvent(LogEvent paramLogEvent)
/*    */   {
/* 54 */     for (TriggeringPolicy localTriggeringPolicy : this.policies) {
/* 55 */       if (localTriggeringPolicy.isTriggeringEvent(paramLogEvent)) {
/* 56 */         return true;
/*    */       }
/*    */     }
/* 59 */     return false;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 64 */     StringBuilder localStringBuilder = new StringBuilder("CompositeTriggeringPolicy{");
/* 65 */     int i = 1;
/* 66 */     for (TriggeringPolicy localTriggeringPolicy : this.policies) {
/* 67 */       if (i == 0) {
/* 68 */         localStringBuilder.append(", ");
/*    */       }
/* 70 */       localStringBuilder.append(localTriggeringPolicy.toString());
/* 71 */       i = 0;
/*    */     }
/* 73 */     localStringBuilder.append("}");
/* 74 */     return localStringBuilder.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @PluginFactory
/*    */   public static CompositeTriggeringPolicy createPolicy(@PluginElement("Policies") TriggeringPolicy... paramVarArgs)
/*    */   {
/* 85 */     return new CompositeTriggeringPolicy(paramVarArgs);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\CompositeTriggeringPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */