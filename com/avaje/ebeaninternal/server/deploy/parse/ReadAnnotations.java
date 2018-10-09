/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
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
/*    */ public class ReadAnnotations
/*    */ {
/*    */   public void readInitial(DeployBeanInfo<?> info)
/*    */   {
/*    */     try
/*    */     {
/* 40 */       new AnnotationClass(info).parse();
/* 41 */       new AnnotationFields(info).parse();
/*    */     }
/*    */     catch (RuntimeException e) {
/* 44 */       String msg = "Error reading annotations for " + info;
/* 45 */       throw new RuntimeException(msg, e);
/*    */     }
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
/*    */ 
/*    */ 
/*    */   public void readAssociations(DeployBeanInfo<?> info, BeanDescriptorManager factory)
/*    */   {
/*    */     try
/*    */     {
/* 64 */       new AnnotationAssocOnes(info, factory).parse();
/* 65 */       new AnnotationAssocManys(info, factory).parse();
/*    */       
/*    */ 
/*    */ 
/* 69 */       new AnnotationSql(info).parse();
/*    */     }
/*    */     catch (RuntimeException e) {
/* 72 */       String msg = "Error reading annotations for " + info;
/* 73 */       throw new RuntimeException(msg, e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\ReadAnnotations.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */