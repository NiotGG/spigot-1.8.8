/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebean.annotation.Sql;
/*    */ import com.avaje.ebean.annotation.SqlSelect;
/*    */ import com.avaje.ebeaninternal.server.deploy.DRawSqlMeta;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
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
/*    */ public class AnnotationSql
/*    */   extends AnnotationParser
/*    */ {
/*    */   public AnnotationSql(DeployBeanInfo<?> info)
/*    */   {
/* 32 */     super(info);
/*    */   }
/*    */   
/*    */   public void parse() {
/* 36 */     Class<?> cls = this.descriptor.getBeanType();
/* 37 */     Sql sql = (Sql)cls.getAnnotation(Sql.class);
/* 38 */     if (sql != null) {
/* 39 */       setSql(sql);
/*    */     }
/*    */     
/*    */ 
/* 43 */     SqlSelect sqlSelect = (SqlSelect)cls.getAnnotation(SqlSelect.class);
/* 44 */     if (sqlSelect != null) {
/* 45 */       setSqlSelect(sqlSelect);
/*    */     }
/*    */   }
/*    */   
/*    */   private void setSql(Sql sql) {
/* 50 */     SqlSelect[] select = sql.select();
/* 51 */     for (int i = 0; i < select.length; i++) {
/* 52 */       setSqlSelect(select[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   private void setSqlSelect(SqlSelect sqlSelect)
/*    */   {
/* 58 */     DRawSqlMeta rawSqlMeta = new DRawSqlMeta(sqlSelect);
/* 59 */     this.descriptor.add(rawSqlMeta);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */