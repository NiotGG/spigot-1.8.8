/*    */ package org.apache.logging.log4j.core.jmx;
/*    */ 
/*    */ import javax.management.ObjectName;
/*    */ import org.apache.logging.log4j.core.helpers.Assert;
/*    */ import org.apache.logging.log4j.core.selector.ContextSelector;
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
/*    */ public class ContextSelectorAdmin
/*    */   implements ContextSelectorAdminMBean
/*    */ {
/*    */   private final ObjectName objectName;
/*    */   private final ContextSelector selector;
/*    */   
/*    */   public ContextSelectorAdmin(ContextSelector paramContextSelector)
/*    */   {
/* 39 */     this.selector = ((ContextSelector)Assert.isNotNull(paramContextSelector, "ContextSelector"));
/*    */     try {
/* 41 */       this.objectName = new ObjectName("org.apache.logging.log4j2:type=ContextSelector");
/*    */     } catch (Exception localException) {
/* 43 */       throw new IllegalStateException(localException);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ObjectName getObjectName()
/*    */   {
/* 54 */     return this.objectName;
/*    */   }
/*    */   
/*    */   public String getImplementationClassName()
/*    */   {
/* 59 */     return this.selector.getClass().getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\jmx\ContextSelectorAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */