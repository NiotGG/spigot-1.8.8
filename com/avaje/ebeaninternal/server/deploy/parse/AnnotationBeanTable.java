/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebean.config.NamingConvention;
/*    */ import com.avaje.ebean.config.TableName;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanTable;
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
/*    */ public class AnnotationBeanTable
/*    */   extends AnnotationBase
/*    */ {
/*    */   final DeployBeanTable beanTable;
/*    */   
/*    */   public AnnotationBeanTable(DeployUtil util, DeployBeanTable beanTable)
/*    */   {
/* 37 */     super(util);
/* 38 */     this.beanTable = beanTable;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void parse()
/*    */   {
/* 46 */     TableName tableName = this.namingConvention.getTableName(this.beanTable.getBeanType());
/*    */     
/* 48 */     this.beanTable.setBaseTable(tableName.getQualifiedName());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationBeanTable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */